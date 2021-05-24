package clientSide.stubs;

import commInfra.ClientCom;
import commInfra.Message;
import commInfra.MessageType;
import genclass.GenericIO;

/**
 * Stub to the destination airport.
 *
 * It instantiates a remote reference to the destination airport.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */

public class DestinationAirportStub {

    /**
     *  Name of the platform where is located the destination airport server.
     */

    private String serverHostName;

    /**
     *  Port number for listening to service requests.
     */

    private int serverPortNumb;

    /**
     *  Instantiation of a stub to the destination airport.
     *
     *  @param serverHostName name of the platform where is located the barber shop server
     *  @param serverPortNumb port number for listening to service requests
     */

    public DestinationAirportStub (String serverHostName, int serverPortNumb)
    {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     *   Operation server shutdown.
     *
     *   New operation.
     */

    public void shutdown ()
    {
        ClientCom com;      // communication channel
        Message outMessage, // outgoing message
                inMessage;  // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())    // waits for a connection to be established
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SHUT);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getType() != MessageType.SHUTDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
    }
}
