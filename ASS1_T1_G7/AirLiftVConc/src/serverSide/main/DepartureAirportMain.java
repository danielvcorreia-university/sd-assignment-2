package serverSide.main;

import commInfra.*;
import serverSide.sharedRegions.DepartureAirport;
import serverSide.sharedRegions.DepartureAirportInterface;
import serverSide.sharedRegions.GeneralReposStub;
import serverSide.entities.DepartureAirportProxy;
import genclass.GenericIO;

/**
 *    Server side of the Departure Airport.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class DepartureAirportMain {

    /**
     *  Flag signaling the service is active.
     */

    public static boolean waitConnection;

    /**
     * End service. Called by the interface after receiving a shut message.
     */

    public static void endConnection() {
        waitConnection = false;
    }

    /**
     *  Main method.
     *
     *    @param args runtime arguments
     */

    public static void main (String [] args)
    {
        DepartureAirport depAirport;                                              // departure airport (service to be rendered)
        DepartureAirportInterface depAirportInterface;                                // interface to the departure airport
        GeneralReposStub reposStub;                                    // stub to the general repository
        ServerCom scon, sconi;                                         // communication channels
        int portNumb = 22160;                                             // port number for listening to service requests
        String reposServerName = "l040101-ws05.ua.pt";              // name of the platform where is located the server for the general repository
        int reposPortNumb = 22163;                                        // port nunber where the server for the general repository is listening to service requests

        /* service is established */

        reposStub = new GeneralReposStub (reposServerName, reposPortNumb);       // communication to the general repository is instantiated
        depAirport = new DepartureAirport (reposStub);                                      // service is instantiated
        depAirportInterface = new DepartureAirportInterface (depAirport);                            // interface to the service is instantiated
        scon = new ServerCom (portNumb);                                         // listening channel at the public port is established
        scon.start ();
        GenericIO.writelnString ("Service is established!");
        GenericIO.writelnString ("Server is listening for service requests.");

        /* service request processing */

        DepartureAirportProxy depAirportProxy;                                // service provider agent

        waitConnection = true;
        while (waitConnection)
        {
            sconi = scon.accept ();                                    // enter listening procedure
            depAirportProxy = new DepartureAirportProxy (sconi, depAirportInterface);    // start a service provider agent to address
            depAirportProxy.start ();                                         //   the request of service
        }
        scon.end ();                                                   // operations termination
        GenericIO.writelnString ("Server was shutdown.");
    }
}