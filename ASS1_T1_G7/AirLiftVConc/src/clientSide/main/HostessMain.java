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

        String hostName = "localhost";      // name of the platform where is located the pilot server
        int depAirportPortNumb = 50000;     // number of the listening port at the computational system where the Departure Airport server is located
        int planePortNumb = 50001;          // number of the listening port at the computational system where the Plane server is located
        int desAirportPortNumb = 50002;     // number of the listening port at the computational system where the Destination Airport server is located

        /* message exchange */

        Hostess hostess;                    // reference to the hostess thread
        DepartureAirportStub depAirport;    // reference to the Departure Airport remote object
        PlaneStub plane;                    // reference to the Plane remote object
        DestinationAirportStub desAirport;  // reference to the Destination Airport remote object

        /* problem initialization */

        depAirport = new DepartureAirportStub(hostName, depAirportPortNumb);
        plane = new PlaneStub(hostName, planePortNumb);
        desAirport = new DestinationAirportStub(hostName, desAirportPortNumb);
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
