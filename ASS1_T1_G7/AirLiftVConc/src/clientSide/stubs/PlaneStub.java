package clientSide.stubs;

import commInfra.AttributeTypes;
import commInfra.ClientCom;
import commInfra.Message;
import commInfra.MessageType;
import clientSide.entities.*;
import genclass.GenericIO;

/**
 * Stub to the plane.
 *
 * It instantiates a remote reference to the plane.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */

public class PlaneStub {

    /**
     *  Name of the platform where is located the plane server.
     */

    private String serverHostName;

    /**
     *  Port number for listening to service requests.
     */

    private int serverPortNumb;

    /**
     *  Instantiation of a stub to the plane.
     *
     *  @param serverHostName name of the platform where is located plane server
     *  @param serverPortNumb port number for listening to service requests
     */

    public PlaneStub (String serverHostName, int serverPortNumb)
    {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     *  Operation final report.
     *
     * It is called by the pilot after he parks the plane at the transfer gate and there are no more passengers to transport
     *
     */

    public void reportFinalReport() {
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

        outMessage = new Message(MessageType.FINAL_REPORT);
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
     *  Operation park at transfer gate.
     *
     * It is called by the pilot after he arrived at departure airport.
     *
     */

    public void parkAtTransferGate() {
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

        outMessage = new Message(MessageType.PARK_AT_TRANSFER_GATE);
        outMessage.setAttributesSize(1);
        outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER});
        outMessage.setAttributes(new Object[]{((Pilot) Thread.currentThread()).getPilotState()});
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
        if (((int) inMessage.getAttributes()[0]) != PilotStates.AT_TRANSFER_GATE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid pilot state after parkAtTransferGate!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((Pilot) Thread.currentThread ()).setPilotState ((int) inMessage.getAttributes()[0]);
    }

    /**
     *  Operation inform plane ready for boarding.
     *
     * It is called by the pilot to inform the hostess that the plane is ready for boarding.
     *
     */

    public void informPlaneReadyForBoarding() {
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

        outMessage = new Message(MessageType.INFORM_PLANE_READY_FOR_BOARDING);
        outMessage.setAttributesSize(1);
        outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER});
        outMessage.setAttributes(new Object[]{((Pilot) Thread.currentThread()).getPilotState()});
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
        if (((int) inMessage.getAttributes()[0]) != PilotStates.READY_FOR_BOARDING)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid pilot state after informPlaneReadyForBoarding!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((Pilot) Thread.currentThread ()).setPilotState ((int) inMessage.getAttributes()[0]);
    }

    /**
     *  Operation wait for next flight.
     *
     * It is called by the hostess while waiting for plane to be ready for boarding.
     *
     * @param first is True if is the first call from hostess and false if it is before the first
     */

    public void waitForNextFlight(boolean first) {
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

        outMessage = new Message(MessageType.WAIT_FOR_NEXT_FLIGHT);
        outMessage.setAttributesSize(2);
        outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER});
        outMessage.setParametersSize(1);
        outMessage.setParametersType(new int[]{AttributeTypes.BOOLEAN});
        outMessage.setAttributes(new Object[]{((Hostess) Thread.currentThread()).getHostessState(),
                ((Hostess) Thread.currentThread()).getCheckedPassengers()});
        outMessage.setParameters(new Object[]{first});
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
        if (((int) inMessage.getAttributes()[0]) != HostessStates.WAIT_FOR_FLIGHT)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid hostess state after waitForNextFlight!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((Hostess) Thread.currentThread ()).setHostessState ((int) inMessage.getAttributes()[0]);
        ((Hostess) Thread.currentThread ()).setCheckedPassengers ((int) inMessage.getAttributes()[1]);
    }

    /**
     *  Operation wait for all in boarding.
     *
     * It is called by the pilot after he announced the hostess
     * that the plane is ready for boarding .
     *
     */

    public void waitForAllInBoarding() {
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

        outMessage = new Message(MessageType.WAIT_FOR_ALL_IN_BOARDING);
        outMessage.setAttributesSize(1);
        outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER});
        outMessage.setAttributes(new Object[]{((Pilot) Thread.currentThread()).getPilotState()});
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
        if (((int) inMessage.getAttributes()[0]) != PilotStates.FLYING_FORWARD)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid pilot state after waitForAllInBoarding!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((Pilot) Thread.currentThread ()).setPilotState ((int) inMessage.getAttributes()[0]);
    }

    /**
     *  Operation inform plane ready to take off
     *
     * It is called by the hostess when she ended the check in of the passengers.
     *
     */

    public void informPlaneReadyToTakeOff() {
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

        outMessage = new Message(MessageType.INFORM_PLANE_READY_TO_TAKE_OFF);
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
        if (((int) inMessage.getAttributes()[0]) != HostessStates.READY_TO_FLY)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid hostess state after informPlaneReadyToTakeOff!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((Hostess) Thread.currentThread ()).setHostessState ((int) inMessage.getAttributes()[0]);
    }

    /**
     *  Operation wait for end of flight
     *
     * It is called by the passengers when they are inside the plane and begin their waiting journey.
     *
     */

    public void waitForEndOfFlight() {
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

        outMessage = new Message(MessageType.WAIT_FOR_END_OFF_FLIGHT);
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
     *  Operation announce arrival
     *
     * It is called by the pilot when he arrived at destination airport.
     *
     */

    public void announceArrival() {
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

        outMessage = new Message(MessageType.ANNOUNCE_ARRIVAL);
        outMessage.setAttributesSize(2);
        outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER});
        outMessage.setAttributes(new Object[]{((Pilot) Thread.currentThread()).getPilotState(),
                ((Pilot) Thread.currentThread()).getTransportedPassengers()});
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
        if (((int) inMessage.getAttributes()[0]) != PilotStates.FLYING_BACK)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid pilot state after announceArrival!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((Pilot) Thread.currentThread ()).setPilotState ((int) inMessage.getAttributes()[0]);
        ((Pilot) Thread.currentThread ()).setTransportedPassengers ((int) inMessage.getAttributes()[1]);
    }

    /**
     *  Operation leave the plane.
     *
     * It is called by the passengers when they leave the plane.
     *
     */

    public void leaveThePlane() {
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
        if (((int) inMessage.getAttributes()[0]) != PassengerStates.AT_DESTINATION)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid passenger state after leaveThePlane!");
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
