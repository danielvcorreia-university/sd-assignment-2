package clientSide.stubs;

import clientSide.entities.Passenger;
import clientSide.entities.PassengerStates;
import commInfra.AttributeTypes;
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
     *  Operation leave the plane.
     *
     * It is called by the passengers when they leave the plane.
     *
     */

    public boolean leaveThePlane(int inF) {
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

        outMessage = new Message(MessageType.LEAVE_THE_PLANE);
        outMessage.setAttributesSize(2);
        outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER});
        outMessage.setParametersSize(1);
        outMessage.setParametersType(new int[]{AttributeTypes.INTEGER});
        outMessage.setAttributes(new Object[]{((Passenger) Thread.currentThread()).getPassengerState(),
                ((Passenger) Thread.currentThread()).getPassengerId()});
        outMessage.setParameters(new Object[]{inF});
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getType() != MessageType.RETURN)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getAttributesSize() != 2 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                || (inMessage.getAttributesType()[1] != AttributeTypes.INTEGER))
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid attribute size or type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if (((int) inMessage.getAttributes()[0]) != PassengerStates.AT_DESTINATION)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid passenger state after leaveThePlane!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((Passenger) Thread.currentThread ()).setPassengerState ((int) inMessage.getAttributes()[0]);
        ((Passenger) Thread.currentThread ()).setPassengerId ((int) inMessage.getAttributes()[1]);

        return ((boolean) inMessage.getParameters()[0]);
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
