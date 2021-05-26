package clientSide.stubs;

import commInfra.AttributeTypes;
import commInfra.ClientCom;
import commInfra.Message;
import commInfra.MessageType;
import clientSide.entities.Hostess;
import clientSide.entities.HostessStates;
import clientSide.entities.Passenger;
import clientSide.entities.PassengerStates;
import genclass.GenericIO;

/**
 * Stub to the departure airport.
 *
 * It instantiates a remote reference to the departure airport.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */

public class DepartureAirportStub {

    /**
     *  Name of the platform where is located the departure airport server.
     */

    private String serverHostName;

    /**
     *  Port number for listening to service requests.
     */

    private int serverPortNumb;

    /**
     *  Instantiation of a stub to the departure airport.
     *
     *  @param serverHostName name of the platform where is located the departure airport server
     *  @param serverPortNumb port number for listening to service requests
     */

    public DepartureAirportStub (String serverHostName, int serverPortNumb)
    {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     *  Operation prepare for pass boarding.
     *
     *  It is called by the hostess while waiting for passengers to arrive at the airport.
     *
     */

    public void prepareForPassBoarding() {
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

        outMessage = new Message(MessageType.PREPARE_FOR_PASS_BOARDING);
        outMessage.setAttributesSize(1);
        outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER});
        outMessage.setAttributes(new Object[]{((Hostess) Thread.currentThread()).getHostessState()});
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getType() != MessageType.RETURN)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getAttributesSize() != 1 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER))
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid attribute size or type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if (((int) inMessage.getAttributes()[0]) != HostessStates.WAIT_FOR_PASSENGER)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid hostess state after prepareForPassBoarding!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((Hostess) Thread.currentThread ()).setHostessState ((int) inMessage.getAttributes()[0]);
        ((Hostess) Thread.currentThread ()).setHostessCount(0);
    }

    /**
     *  Operation wait in queue.
     *
     * It is called by a passenger while waiting for his turn to show his documents to the hostess.
     *
     */

    public void waitInQueue() {
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

        outMessage = new Message(MessageType.WAIT_IN_QUEUE);
        outMessage.setAttributesSize(3);
        outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER, AttributeTypes.BOOLEAN});
        outMessage.setAttributes(new Object[]{((Passenger) Thread.currentThread()).getPassengerState(),
                ((Passenger) Thread.currentThread()).getPassengerId(), ((Passenger) Thread.currentThread()).getReadyToShowDocuments()});
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getType() != MessageType.RETURN)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getAttributesSize() != 3 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                || (inMessage.getAttributesType()[1] != AttributeTypes.INTEGER) || (inMessage.getAttributesType()[2] != AttributeTypes.BOOLEAN))
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid attribute size or type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if (((int) inMessage.getAttributes()[0]) != PassengerStates.IN_QUEUE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid passenger state after waitInQueue!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((Passenger) Thread.currentThread ()).setPassengerState ((int) inMessage.getAttributes()[0]);
        ((Passenger) Thread.currentThread ()).setPassengerId ((int) inMessage.getAttributes()[1]);
        ((Passenger) Thread.currentThread ()).setReadyToShowDocuments (false);
    }

    /**
     *  Operation check documents.
     *
     * It is called by the hostess while waiting for the first costumer in queue to show his documents.
     *
     */

    public void checkDocuments() {
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

        outMessage = new Message(MessageType.CHECK_DOCUMENTS);
        outMessage.setAttributesSize(3);
        outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.BOOLEAN, AttributeTypes.BOOLEAN});
        outMessage.setAttributes(new Object[]{((Hostess) Thread.currentThread()).getHostessState(),
                ((Hostess) Thread.currentThread()).getPassengerInQueue(), ((Hostess) Thread.currentThread()).getReadyToCheckDocuments()});
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getType() != MessageType.RETURN)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getAttributesSize() != 3 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                || (inMessage.getAttributesType()[1] != AttributeTypes.BOOLEAN) || (inMessage.getAttributesType()[2] != AttributeTypes.BOOLEAN))
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid attribute size or type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if (((int) inMessage.getAttributes()[0]) != HostessStates.CHECK_PASSENGER)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid hostess state after checkDocuments!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((Hostess) Thread.currentThread ()).setHostessState ((int) inMessage.getAttributes()[0]);
        ((Hostess) Thread.currentThread ()).setPassengerInQueue ((boolean) inMessage.getAttributes()[1]);
        ((Hostess) Thread.currentThread ()).setReadyToCheckDocuments (false);
    }

    /**
     *  Operation show documents.
     *
     * It is called by a passenger if the hostess has called him to check his documents.
     *
     */

    public void  showDocuments() {
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

        outMessage = new Message(MessageType.SHOW_DOCUMENTS);
        outMessage.setAttributesSize(2);
        outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER});
        outMessage.setAttributes(new Object[]{((Passenger) Thread.currentThread()).getPassengerState(),
                ((Passenger) Thread.currentThread()).getPassengerId()});
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
     *  Operation wait for next passenger.
     *
     * It is called by the hostess while waiting for the next passenger in queue.
     *
     */

    public void waitForNextPassenger() {
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

        outMessage = new Message(MessageType.WAIT_FOR_NEXT_PASSENGER);
        outMessage.setAttributesSize(3);
        outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER, AttributeTypes.BOOLEAN});
        outMessage.setAttributes(new Object[]{((Hostess) Thread.currentThread()).getHostessState(), ((Hostess) Thread.currentThread()).getHostessCount(),
                ((Hostess) Thread.currentThread()).getPassengerInQueue()});
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getType() != MessageType.RETURN)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if ((inMessage.getAttributesSize() != 3 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                || (inMessage.getAttributesType()[1] != AttributeTypes.INTEGER) || (inMessage.getAttributesType()[2] != AttributeTypes.BOOLEAN))
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid attribute size or type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if (((int) inMessage.getAttributes()[0]) != HostessStates.WAIT_FOR_PASSENGER)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid hostess state after waitForNextPassenger!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((Hostess) Thread.currentThread ()).setHostessState ((int) inMessage.getAttributes()[0]);
        ((Hostess) Thread.currentThread ()).setHostessCount ((int) inMessage.getAttributes()[1]);
        ((Hostess) Thread.currentThread ()).setPassengerInQueue ((boolean) inMessage.getAttributes()[3]);
    }

    /**
     *  Operation board the plane.
     *
     * It is called by the passengers when they are allowed to enter the plane.
     *
     */

    public void boardThePlane() {
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

        outMessage = new Message(MessageType.BOARD_THE_PLANE);
        outMessage.setAttributesSize(2);
        outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER});
        outMessage.setAttributes(new Object[]{((Passenger) Thread.currentThread()).getPassengerState(),
                ((Passenger) Thread.currentThread()).getPassengerId()});
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
        if (((int) inMessage.getAttributes()[0]) != PassengerStates.IN_FLIGHT)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid passenger state after boardThePlane!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((Passenger) Thread.currentThread ()).setPassengerState ((int) inMessage.getAttributes()[0]);
        ((Passenger) Thread.currentThread ()).setPassengerId ((int) inMessage.getAttributes()[1]);
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
