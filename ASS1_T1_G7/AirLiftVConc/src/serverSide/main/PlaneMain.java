package serverSide.main;

import commInfra.ServerCom;
import genclass.GenericIO;
import serverSide.entities.PlaneProxy;
import serverSide.sharedRegions.GeneralReposStub;
import serverSide.sharedRegions.Plane;
import serverSide.sharedRegions.PlaneInterface;

/**
 *    Server side of the Plane.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class PlaneMain {

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
        Plane plane;                                              // plane (service to be rendered)
        PlaneInterface planeInterface;                                // interface to the plane
        GeneralReposStub reposStub;                                    // stub to the general repository
        ServerCom scon, sconi;                                         // communication channels
        int portNumb = 22161;                                             // port number for listening to service requests
        String reposServerName = "sd107@l040101-ws05.ua.pt";              // name of the platform where is located the server for the general repository
        int reposPortNumb = 22163;                                        // port nunber where the server for the general repository is listening to service requests

        /* service is established */

        reposStub = new GeneralReposStub (reposServerName, reposPortNumb);       // communication to the general repository is instantiated
        plane = new Plane (reposStub);                                      // service is instantiated
        planeInterface = new PlaneInterface (plane);                            // interface to the service is instantiated
        scon = new ServerCom (portNumb);                                         // listening channel at the public port is established
        scon.start ();
        GenericIO.writelnString ("Service is established!");
        GenericIO.writelnString ("Server is listening for service requests.");

        /* service request processing */

        PlaneProxy planeProxy;                                // service provider agent

        waitConnection = true;
        while (waitConnection)
        {
            sconi = scon.accept ();                                    // enter listening procedure
            planeProxy = new PlaneProxy (sconi, planeInterface);    // start a service provider agent to address
            planeProxy.start ();                                         //   the request of service
        }
        reposStub.shutdown();
        reposStub.shutdown();
        scon.end ();                                                   // operations termination
        GenericIO.writelnString ("Server was shutdown.");
    }
}