package serverSide.main;

import commInfra.*;
import serverSide.sharedRegions.GeneralReposStub;
import serverSide.sharedRegions.DestinationAirport;
import serverSide.sharedRegions.DestinationAirportInterface;
import serverSide.entities.DestinationAirportProxy;
import genclass.GenericIO;

/**
 *    Server side of the Destination Airport.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class DestinationAirportMain {

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
        DestinationAirport destAirport;                                              // destination airport (service to be rendered)
        DestinationAirportInterface destAirportInterface;                                // interface to the destination airport
        GeneralReposStub reposStub;                                    // stub to the general repository
        ServerCom scon, sconi;                                         // communication channels
        int portNumb = 22162;                                             // port number for listening to service requests
        String reposServerName = "l040101-ws05.ua.pt";              // name of the platform where is located the server for the general repository
        int reposPortNumb = 22163;                                        // port nunber where the server for the general repository is listening to service requests

        /* service is established */

        reposStub = new GeneralReposStub (reposServerName, reposPortNumb);       // communication to the general repository is instantiated
        destAirport = new DestinationAirport (reposStub);                                      // service is instantiated
        destAirportInterface = new DestinationAirportInterface (destAirport);                            // interface to the service is instantiated
        scon = new ServerCom (portNumb);                                         // listening channel at the public port is established
        scon.start ();
        GenericIO.writelnString ("Service is established!");
        GenericIO.writelnString ("Server is listening for service requests.");

        /* service request processing */

        DestinationAirportProxy destAirportProxy;                                // service provider agent

        waitConnection = true;
        while (waitConnection)
        {
            sconi = scon.accept ();                                    // enter listening procedure
            destAirportProxy = new DestinationAirportProxy (sconi, destAirportInterface);    // start a service provider agent to address
            destAirportProxy.start ();                                         //   the request of service
        }
        scon.end ();                                                   // operations termination
        GenericIO.writelnString ("Server was shutdown.");
    }
}