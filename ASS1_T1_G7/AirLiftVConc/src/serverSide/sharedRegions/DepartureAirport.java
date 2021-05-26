package serverSide.sharedRegions;

import commInfra.*;
import serverSide.main.*;
import serverSide.entities.*;
import genclass.GenericIO;

/**
 *    Departure Airport.
 *
 *    It is responsible to keep a continuously updated account of the entities inside the departure airport
 *    and is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 *    There are four internal synchronization points: a single blocking point for the hostess, where she waits until
 *    the plane is ready for boarding so that she may proceed to the next flight;
 *    another single blocking point for the hostess, where she waits for the passengers to arrive at the airport;
 *    another single blocking point for the hostess, where she waits for the passenger at the front of the queue to
 *    show her his documents;
 *    and an array of blocking points, one per each passenger, where he both waits his turn to show the hostess
 *    his documents and waits until she has checked his documents and calls the next passenger.
 */

public class DepartureAirport {
    /**
     * Number of passengers in queue waiting for to show their documents to the hostess.
     */

    private int inQ;

    /**
     * Number of passengers waiting in the plane.
     */

    private int inP;

    /**
     * Reference to passenger threads.
     */

    private final PassengerInterface[] passengers;

    /**
     * Reference to hostess thread.
     */

    private HostessInterface hostess;

    /**
     * Waiting queue at the transfer gate.
     */

    private MemFIFO<Integer> boardingQueue;

    /**
     * Reference to the stub of the general repository.
     */

    private final GeneralReposStub  repos;

    /**
     * True if the hostess can check next passenger documents.
     */

    private boolean readyForNextPassenger;

    /**
     * True if the passenger has given his documents to the hostess for her to check.
     */

    private boolean readyToCheckDocuments;

    /**
     * True if the passenger can board the plane after showing documents to hostess.
     */

    private boolean canBoardThePlane;

    /**
     * Set if hostess is ready to check documents of the next passenger
     *
     * @param bool ready for next passenger
     */

    /**
     * Departure airport instantiation.
     *
     * @param repos reference to the stub of the general repository
     */

    public DepartureAirport(GeneralReposStub  repos) {
        hostess = null;
        passengers = new PassengerInterface[SimulPar.N];
        this.readyForNextPassenger = false;
        this.readyToCheckDocuments = false;
        for (int i = 0; i < SimulPar.N; i++)
            passengers[i] = null;
        try {
            boardingQueue = new MemFIFO<>(new Integer[SimulPar.N]);
        } catch (MemException e) {
            GenericIO.writelnString("Instantiation of boarding FIFO failed: " + e.getMessage());
            boardingQueue = null;
            System.exit(1);
        }
        this.repos = repos;
    }

    /**
     * Operation queue empty
     * <p>
     * It is called to check if the passenger queue is currently empty
     */

    public boolean queueEmpty() { return inQ == 0; }

    /**
     * Operation prepare for pass boarding
     * <p>
     * It is called by the hostess while waiting for passengers to arrive at the airport.
     */

    public synchronized void prepareForPassBoarding() {

        hostess = (HostessInterface) Thread.currentThread();

        ((HostessInterface) Thread.currentThread()).setHostessState(HostessStates.WAIT_FOR_PASSENGER);
        repos.setHostessState(((HostessInterface) Thread.currentThread()).getHostessId(), ((HostessInterface) Thread.currentThread()).getHostessState());
        ((HostessInterface) Thread.currentThread()).setHostessCount(0);
        inP = 0;
        while (inQ == 0)                             // the hostess waits for a passenger to arrive
        {
            try {
                wait();
            } catch (InterruptedException e) {
                GenericIO.writelnString("Interruption: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    /**
     * Operation wait in queue.
     * <p>
     * It is called by a passenger while waiting for his turn to show his documents to the hostess.
     */

    public synchronized void waitInQueue() {
        int passengerId;                                      // passenger id

        passengerId = ((PassengerInterface) Thread.currentThread()).getPassengerId();
        passengers[passengerId] = (PassengerInterface) Thread.currentThread();
        passengers[passengerId].setPassengerState(PassengerStates.IN_QUEUE);
        repos.setPassengerState(passengerId, passengers[passengerId].getPassengerState());
        inQ++;                                        // the passenger arrives at the airport,

        try {
            boardingQueue.write(passengerId);                    // the customer sits down to wait for his turn
        } catch (MemException e) {
            GenericIO.writelnString("Insertion of customer id in waiting FIFO failed: " + e.getMessage());
            System.exit(1);
        }

        notifyAll();

        while (!(((PassengerInterface) Thread.currentThread()).getReadyToShowDocuments())) {
            try {
                wait();
            } catch (InterruptedException e) {
                GenericIO.writelnString("Interruption: " + e.getMessage());
                System.exit(1);
            }
        }
        ((PassengerInterface) Thread.currentThread()).setReadyToShowDocuments(false);
    }


    /**
     * Operation check documents.
     * <p>
     * It is called by the hostess while waiting for the first costumer in queue to show his documents.
     */

    public synchronized void checkDocuments() {
        int passengerId;                                        //passenger id

        ((HostessInterface) Thread.currentThread()).setHostessState(HostessStates.CHECK_PASSENGER);
        repos.setHostessState(((HostessInterface) Thread.currentThread()).getHostessId(), ((HostessInterface) Thread.currentThread()).getHostessState());

        inQ--;
        ((HostessInterface) Thread.currentThread()).setPassengerInQueue(!queueEmpty());

        try {
            passengerId = boardingQueue.read();                            // the hostess calls the customer
            if ((passengerId < 0) || (passengerId >= SimulPar.N))
                throw new MemException("illegal passenger id!");
        } catch (MemException e) {
            GenericIO.writelnString("Retrieval of passenger id from boarding FIFO failed: " + e.getMessage());
            passengerId = -1;
            System.exit(1);
        }

        passengers[passengerId].setReadyToShowDocuments(true);

        notifyAll();

        while (!readyToCheckDocuments)             // the hostess waits for the passenger to give his documents
        {
            try {
                wait();
            } catch (InterruptedException e) {
                GenericIO.writelnString("Interruption: " + e.getMessage());
                System.exit(1);
            }
        }

        readyToCheckDocuments = false;
    }

    /**
     * Operation show documents.
     * <p>
     * It is called by a passenger if the hostess has called him to check his documents.
     */

    public synchronized void  showDocuments() {
        readyToCheckDocuments = true;

        notifyAll();
        while (!canBoardThePlane)   // the passenger waits until he is clear to proceed
        {
            try {
                wait();
            } catch (InterruptedException e) {
                GenericIO.writelnString("Interruption: " + e.getMessage());
                System.exit(1);
            }
        }
        canBoardThePlane = false;
    }

    /**
     * Operation wait for next passenger.
     * <p>
     * It is called by the hostess while waiting for the next passenger in queue.
     */

    public synchronized void waitForNextPassenger() {
        ((HostessInterface) Thread.currentThread()).setHostessState(HostessStates.WAIT_FOR_PASSENGER);
        repos.setHostessState(((HostessInterface) Thread.currentThread()).getHostessId(), ((HostessInterface) Thread.currentThread()).getHostessState());
        ((HostessInterface) Thread.currentThread()).setHostessCount(((HostessInterface) Thread.currentThread()).getHostessCount()+1);
        canBoardThePlane = true;

        notifyAll();
        while ((inQ == 0 && ((HostessInterface) Thread.currentThread()).getHostessCount() < 5 || (!readyForNextPassenger)) && !((inP + ((HostessInterface) Thread.currentThread()).getCheckedPassengers()) >= SimulPar.N))    // the hostess waits for a passenger to enter the plane
        {
            //System.out.print("\n" + (inP + ((HostessInterface) Thread.currentThread()).getCheckedPassengers()) + "\n");
            //Plane.getInF()
            try {
                wait();
            } catch (InterruptedException e) {
                GenericIO.writelnString("Interruption: " + e.getMessage());
                System.exit(1);
            }
        }

        readyForNextPassenger = false;
        ((HostessInterface) Thread.currentThread()).setPassengerInQueue(!queueEmpty());
    }

    /**
     * Operation boarding the plane
     * <p>
     * It is called by the passengers when they are allowed to enter the plane.
     */

    public synchronized void boardThePlane() {

        readyForNextPassenger = true;
        inP +=1;
        ((PassengerInterface) Thread.currentThread()).setPassengerState(PassengerStates.IN_FLIGHT);
        repos.setPassengerState(((PassengerInterface) Thread.currentThread()).getPassengerId(), ((PassengerInterface) Thread.currentThread()).getPassengerState());
        notifyAll();
    }
}