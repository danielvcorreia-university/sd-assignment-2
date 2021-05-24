package clientSide.main;

import clientSide.stubs.DestinationAirportStub;
import clientSide.stubs.PlaneStub;
import clientSide.entities.Pilot;
import genclass.GenericIO;

/**
 *    Client side of the Air Lift (pilot).
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class PilotMain {

    /**
     * Main method.
     *
     * @param args runtime arguments
     */

    public static void main(String[] args) {

        /* getting information about the servers locations */

        String hostName = "localhost";      // name of the platform where is located the pilot server
        int planePortNumb = 50001;          // number of the listening port at the computational system where the Plane server is located
        int desAirportPortNumb = 50002;     // number of the listening port at the computational system where the Destination Airport server is located

        /* message exchange */

        Pilot pilot;                        // reference to the pilot thread
        PlaneStub plane;                    // reference to the Plane remote object
        DestinationAirportStub desAirport;  // reference to the Destination Airport remote object

        /* problem initialization */

        plane = new PlaneStub(hostName, planePortNumb);
        desAirport = new DestinationAirportStub(hostName, desAirportPortNumb);
        pilot = new Pilot("Pilot_" + (1), 0, plane, desAirport);

        /* start of the simulation */

        pilot.start();

        /* waiting for the end of the simulation */

        GenericIO.writelnString ();
        try
        { pilot.join ();
        }
        catch (InterruptedException e) {}
        GenericIO.writelnString("The pilot " + (1) + " has terminated.");
        GenericIO.writelnString ();
    }
}
