package clientSide.main;

import clientSide.stubs.DepartureAirportStub;
import clientSide.stubs.DestinationAirportStub;
import clientSide.stubs.PlaneStub;
import clientSide.entities.*;
import genclass.GenericIO;

/**
 *    Client side of the Air Lift (passengers).
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class PassengerMain {

    /**
     * Main method.
     *
     * @param args runtime arguments
     */

    public static void main(String[] args) {

        /* getting information about the servers locations */

        int depAirportPortNumb = 22160;                      // number of the listening port at the computational system where the Departure Airport server is located
        int planePortNumb = 22161;                           // number of the listening port at the computational system where the Plane server is located
        int desAirportPortNumb = 22162;                      // number of the listening port at the computational system where the Destination Airport server is located

        /* message exchange */

        Passenger[] passenger = new Passenger[SimulPar.N];   // array of references passenger threads
        DepartureAirportStub depAirport;                     // reference to the Departure Airport remote object
        PlaneStub plane;                                     // reference to the Plane remote object
        DestinationAirportStub desAirport;                   // reference to the Destination Airport remote object

        /* problem initialization */

        depAirport = new DepartureAirportStub("l040101-ws01.ua.pt", depAirportPortNumb);
        plane = new PlaneStub("l040101-ws02.ua.pt", planePortNumb);
        desAirport = new DestinationAirportStub("l040101-ws03.ua.pt", desAirportPortNumb);
        for (int i = 0; i < SimulPar.N; i++) { passenger[i] = new Passenger("Passenger_" + (i + 1), i, depAirport, plane, desAirport); }

        /* start of the simulation */

        for (int i = 0; i < SimulPar.N; i++) { passenger[i].start(); }

        /* waiting for the end of the simulation */

        GenericIO.writelnString ();
        for (int i = 0; i < SimulPar.N; i++) {
            try {
                passenger[i].join();
            } catch (InterruptedException e) {
                GenericIO.writelnString("Interruption: " + e.getMessage());
                System.exit(1);
            }
            GenericIO.writelnString("The passenger " + (i + 1) + " has terminated.");
        }
        GenericIO.writelnString();
    }
}
