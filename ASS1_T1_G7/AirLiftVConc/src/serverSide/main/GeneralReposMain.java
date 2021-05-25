package serverSide.main;

import commInfra.*;
import serverSide.sharedRegions.*;
import serverSide.entities.*;
import genclass.GenericIO;

/**
 *    Server side of the General Repository of Information.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class GeneralReposMain {

    /**
     *  Flag signaling the service is active.
     */

    public static boolean waitConnection = true;

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
        GeneralRepos repos;                                              // general repository (service to be rendered)
        GeneralReposInterface reposInterface;                                // interface to the general repository
        ServerCom scon, sconi;                                         // communication channels
        int portNumb = 22163;                                             // port number for listening to service requests

        /* service is established */

        repos = new GeneralRepos ("log");                                      // service is instantiated
        reposInterface = new GeneralReposInterface (repos);                            // interface to the service is instantiated
        scon = new ServerCom (portNumb);                                         // listening channel at the public port is established
        scon.start ();
        GenericIO.writelnString ("Service is established!");
        GenericIO.writelnString ("Server is listening for service requests.");

        /* service request processing */

        GeneralReposProxy reposProxy;                                // service provider agent

        waitConnection = true;
        while (waitConnection)
        {
            sconi = scon.accept ();                                    // enter listening procedure
            reposProxy = new GeneralReposProxy (sconi, reposInterface);    // start a service provider agent to address
            reposProxy.start ();                                         //   the request of service
        }
        scon.end ();                                                   // operations termination
        GenericIO.writelnString ("Server was shutdown.");
    }
}