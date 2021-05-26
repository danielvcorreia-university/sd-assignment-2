package clientSide.main;

import clientSide.stubs.DepartureAirportStub;
import clientSide.stubs.DestinationAirportStub;
import clientSide.stubs.PlaneStub;
import clientSide.entities.Hostess;
import genclass.GenericIO;

/**
 *    Client side of the Air Lift (hostess).
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class HostessMain {

    /**
     * Main method.
     *
     * @param args runtime arguments
     */

    public static void main(String[] args) {

        /* getting information about the servers locations */

        int depAirportPortNumb = 22160;     // number of the listening port at the computational system where the Departure Airport server is located
        int planePortNumb = 22161;          // number of the listening port at the computational system where the Plane server is located
        int desAirportPortNumb = 22162;     // number of the listening port at the computational system where the Destination Airport server is located

        /* message exchange */

        Hostess hostess;                    // reference to the hostess thread
        DepartureAirportStub depAirport;    // reference to the Departure Airport remote object
        PlaneStub plane;                    // reference to the Plane remote object
        DestinationAirportStub desAirport;  // reference to the Destination Airport remote object

        /* problem initialization */

        depAirport = new DepartureAirportStub("l040101-ws01.ua.pt", depAirportPortNumb);
        plane = new PlaneStub("l040101-ws02.ua.pt", planePortNumb);
        desAirport = new DestinationAirportStub("l040101-ws03.ua.pt", desAirportPortNumb);
        hostess = new Hostess("Hostess_" + (1), 0, depAirport, plane, desAirport);

        /* start of the simulation */

        hostess.start();

        /* waiting for the end of the simulation */

        GenericIO.writelnString ();
        try
        { hostess.join ();
        }
        catch (InterruptedException e) {}
        GenericIO.writelnString("The hostess " + (1) + " has terminated.");
        GenericIO.writelnString ();
    }
}
