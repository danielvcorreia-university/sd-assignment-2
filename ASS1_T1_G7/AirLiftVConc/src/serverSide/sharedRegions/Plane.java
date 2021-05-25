package serverSide.sharedRegions;

import serverSide.entities.*;
import serverSide.main.*;
import genclass.GenericIO;

/**
 *    Plane.
 *
 *    It is responsible to keep a continuously updated account of the entities inside the plane
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

public class Plane {
    /**
     * Reference to hostess thread.
     */

    private HostessInterface hostess;

    /**
     * Reference to pilot thread.
     */

    private PilotInterface pilot;

    /**
     * Reference to number of passengers in the plane.
     */

    private static int inF;

    /**
     * True if the pilot communicated to the hostess that the plane is ready for boarding.
     */

    private boolean nextFlight;

    /**
     * Reference to passenger threads.
     */

    private final PassengerInterface[] passengers;

    /**
     * Reference to the stub of the general repository.
     */

    private final GeneralReposStub repos;

    /**
     * Plane instantiation.
     *
     * @param repos reference to the stub of the general repository
     */

    public Plane(GeneralReposStub repos) {
        inF = 0;
        nextFlight = false;
        passengers = new PassengerInterface[SimulPar.N];
        for (int i = 0; i < SimulPar.N; i++)
            passengers[i] = null;
        this.repos = repos;
    }

    /**
     * Operation to report the final report
     * <p>
     * It is called by the pilot after he parks the plane at the transfer gate and there are no more passengers to transport
     */

    public synchronized void reportFinalReport() {
        repos.reportFinalInfo();
    }

    /**
     * Operation park at transfer gate
     * <p>
     * It is called by the pilot after he arrived at departure airport.
     */

    public synchronized void parkAtTransferGate() {

        pilot = (PilotInterface) Thread.currentThread();
        ((PilotInterface) Thread.currentThread()).setPilotState(PilotStates.AT_TRANSFER_GATE);
        repos.setPilotState(((PilotInterface) Thread.currentThread()).getPilotState());
    }

    /**
     * Operation inform plane ready for boarding
     * <p>
     * It is called by the pilot to inform the hostess that the plane is ready for boarding.
     */

    public synchronized void informPlaneReadyForBoarding() {

        nextFlight = true;
        ((PilotInterface) Thread.currentThread()).setPilotState(PilotStates.READY_FOR_BOARDING);
        repos.setPilotState(((PilotInterface) Thread.currentThread()).getPilotState());
        notifyAll();
    }

    /**
     * Operation wait for next flight
     * <p>
     * It is called by the hostess while waiting for plane to be ready for boarding.
     */

    public synchronized void waitForNextFlight(boolean first) {
        int hostessId;                                          //hostess id

        hostess = (HostessInterface) Thread.currentThread();
        hostessId = ((HostessInterface) Thread.currentThread()).getHostessId();
        ((HostessInterface) Thread.currentThread()).setHostessState(HostessStates.WAIT_FOR_FLIGHT);
        if(!first)
            repos.setHostessState(hostessId, ((HostessInterface) Thread.currentThread()).getHostessState());

        hostess.setCheckedPassengers(hostess.getCheckedPassengers() + inF);

        if (!(hostess.getCheckedPassengers() == SimulPar.N)) {
            while(!nextFlight)
            {
                try {
                    wait();
                } catch (InterruptedException e) {
                    GenericIO.writelnString("Interruption: " + e.getMessage());
                    System.exit(1);
                }
            }
        }
        nextFlight = false;

    }

    /**
     * Operation wait for all passengers to board the plane.
     * <p>
     * It is called by the pilot after he announced the hostess
     * that the plane is ready for boarding .
     */

    public synchronized void waitForAllInBoarding() {
        ((PilotInterface) Thread.currentThread()).setPilotState(PilotStates.WAITING_FOR_BOARDING);
        repos.setPilotState(((PilotInterface) Thread.currentThread()).getPilotState());
        while (!((PilotInterface) Thread.currentThread()).getReadyToTakeOff()) {
            try {
                wait();
            } catch (InterruptedException e) {
                GenericIO.writelnString("While waiting for passenger boarding: " + e.getMessage());
                System.exit(1);
            }
        }
        pilot.setReadyToTakeOff(false);
        ((PilotInterface) Thread.currentThread()).setPilotState(PilotStates.FLYING_FORWARD);
        repos.setPilotState(((PilotInterface) Thread.currentThread()).getPilotState());
    }

    /**
     * Operation inform the pilot that the plane is ready to departure.
     * <p>
     * It is called by the hostess when she ended the check in of the passengers.
     */

    public synchronized void informPlaneReadyToTakeOff() {
        int hostessId;

        pilot.setReadyToTakeOff(true);
        hostessId = ((HostessInterface) Thread.currentThread()).getHostessId();
        ((HostessInterface) Thread.currentThread()).setHostessState(HostessStates.READY_TO_FLY);
        repos.setHostessState(hostessId, ((HostessInterface) Thread.currentThread()).getHostessState());
        notifyAll();
    }

    /**
     * Operation wait for end of flight
     * <p>
     * It is called by the passengers when they are inside the plane and begin their waiting journey.
     */

    public synchronized void waitForEndOfFlight() {
        int passengerId;                                            // passenger id

        passengerId = ((PassengerInterface) Thread.currentThread()).getPassengerId();
        passengers[passengerId] = (PassengerInterface) Thread.currentThread();
        inF += 1;

        while ((pilot.getPilotState() != PilotStates.DEBOARDING)) {
            try {
                wait();
            } catch (InterruptedException e) {
                GenericIO.writelnString("Interruption: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    /**
     * Operation inform the pilot that the plane is ready to departure.
     * <p>
     * It is called by the pilot when he arrived at destination airport.
     */

    public synchronized void announceArrival() {
        ((PilotInterface) Thread.currentThread()).setPilotState(PilotStates.DEBOARDING);
        repos.setPilotState(((PilotInterface) Thread.currentThread()).getPilotState());

        pilot.setTransportedPassengers(pilot.getTransportedPassengers() + inF);

        notifyAll();

        while (inF != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                GenericIO.writelnString("Interruption: " + e.getMessage());
                System.exit(1);
            }
        }

        ((PilotInterface) Thread.currentThread()).setPilotState(PilotStates.FLYING_BACK);
        repos.setPilotState(((PilotInterface) Thread.currentThread()).getPilotState());
    }

    /**
     * Operation leave the plane
     * <p>
     * It is called by the passengers when they leave the plane.
     */

    public synchronized void leaveThePlane() {
        int passengerId;                                            // passenger id

        inF -= 1;

        passengerId = ((PassengerInterface) Thread.currentThread()).getPassengerId();
        passengers[passengerId].setPassengerState(PassengerStates.AT_DESTINATION);
        repos.setPassengerState(passengerId, passengers[passengerId].getPassengerState());

        if (inF == 0) { notifyAll(); }
    }
}
