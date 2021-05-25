package serverSide.sharedRegions;

import commInfra.AttributeTypes;
import commInfra.ClientCom;
import commInfra.Message;
import commInfra.MessageType;
import genclass.GenericIO;

/**
 *  Stub to the general repository.
 *
 *    It instantiates a remote reference to the general repository.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class GeneralReposStub
{
    /**
     *  Name of the platform where is located the general repository server.
     */

    private String serverHostName;

    /**
     *  Port number for listening to service requests.
     */

    private int serverPortNumb;

    /**
     *  Instantiation of a stub to the general repository.
     *
     *  @param serverHostName name of the platform where is located the general repository server
     *  @param serverPortNumb port number for listening to service requests
     */

    public GeneralReposStub (String serverHostName, int serverPortNumb)
    {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     * Set passenger state.
     *
     * @param id    passenger id
     * @param state passenger state
     */

    public void setPassengerState(int id, int state) {
        ClientCom com;      // communication channel
        Message outMessage, // outgoing message
                inMessage;  // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())    // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.SET_PASSENGER_STATE);
        outMessage.setParametersSize(2);
        outMessage.setParametersType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER});
        outMessage.setParameters(new Object[]{id, state});
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getType() != MessageType.RETURN)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
    }

    /**
     * Set hostess state.
     *
     * @param idHostess unique identifier of hostess
     * @param state hostess state
     */

    public void setHostessState(int idHostess, int state) {
        ClientCom com;      // communication channel
        Message outMessage, // outgoing message
                inMessage;  // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())    // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.SET_HOSTESS_STATE);
        outMessage.setParametersSize(2);
        outMessage.setParametersType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER});
        outMessage.setParameters(new Object[]{idHostess, state});
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getType() != MessageType.RETURN)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
    }

    /**
     * Set pilot state.
     *
     * @param state pilot state
     */

    public void setPilotState(int state) {
        ClientCom com;      // communication channel
        Message outMessage, // outgoing message
                inMessage;  // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())    // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.SET_PILOT_STATE);
        outMessage.setParametersSize(1);
        outMessage.setParametersType(new int[]{AttributeTypes.INTEGER});
        outMessage.setParameters(new Object[]{state});
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getType() != MessageType.RETURN)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
    }

    /**
     * Report the final report of the General Repository when the pilot ended all the flights
     * <p>
     * It prints all the flights performed and the amount of passengers that were in each one
     */

    public void reportFinalInfo() {
        ClientCom com;      // communication channel
        Message outMessage, // outgoing message
                inMessage;  // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())    // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.REPORT_FINAL_INFO);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getType() != MessageType.RETURN)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
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
