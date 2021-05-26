package serverSide.sharedRegions;

import commInfra.AttributeTypes;
import commInfra.Message;
import commInfra.MessageType;
import serverSide.entities.HostessInterface;
import serverSide.entities.PassengerInterface;
import serverSide.entities.PilotInterface;
import genclass.GenericIO;
import serverSide.main.PlaneMain;

/**
 *  Interface to the Plane.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Plane and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class PlaneInterface {

    /**
     * Reference to the Plane.
     */

    private final Plane plane;

    /**
     *  Instantiation of an interface to the Plane.
     *
     *    @param plane reference to the plane
     */

    public PlaneInterface (Plane plane) {
        this.plane = plane;
    }

    /**
     * Process and reply.
     *
     * The request is processed and an outgoing message is generated.
     *
     * @param inMessage input message
     * @return outgoing message
     */

    public Message processAndReply(Message inMessage) {
        Message outMessage = null;

        switch(inMessage.getType()) {
            case MessageType.FINAL_REPORT:
                plane.reportFinalReport();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                break;

            case MessageType.PARK_AT_TRANSFER_GATE:
                if ((inMessage.getAttributesSize() != 1 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER))
                    { GenericIO.writelnString ("Invalid message! -> PARK_AT_TRANSFER_GATE"); System.exit(1); }
                ((PilotInterface) Thread.currentThread ()).setPilotState ((int) inMessage.getAttributes()[0]);
                plane.parkAtTransferGate();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(1);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER});
                outMessage.setAttributes(new Object[]{((PilotInterface) Thread.currentThread()).getPilotState()});
                break;

            case MessageType.INFORM_PLANE_READY_FOR_BOARDING:
                if ((inMessage.getAttributesSize() != 1 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER))
                    { GenericIO.writelnString ("Invalid message! -> INFORM_PLANE_READY_FOR_BOARDING"); System.exit(1); }
                ((PilotInterface) Thread.currentThread ()).setPilotState ((int) inMessage.getAttributes()[0]);
                plane.informPlaneReadyForBoarding();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(1);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER});
                outMessage.setAttributes(new Object[]{((PilotInterface) Thread.currentThread()).getPilotState()});
                break;

            case MessageType.WAIT_FOR_NEXT_FLIGHT:
                if ((inMessage.getAttributesSize() != 2 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                        || (inMessage.getAttributesType()[1] != AttributeTypes.INTEGER))
                    { GenericIO.writelnString ("Invalid message! -> WAIT_FOR_NEXT_FLIGHT"); System.exit(1); }
                ((HostessInterface) Thread.currentThread ()).setHostessState ((int) inMessage.getAttributes()[0]);
                ((HostessInterface) Thread.currentThread ()).setCheckedPassengers ((int) inMessage.getAttributes()[1]);
                plane.waitForNextFlight((boolean) inMessage.getParameters()[0]);
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(2);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER});
                outMessage.setAttributes(new Object[]{((HostessInterface) Thread.currentThread()).getHostessState(),
                        ((HostessInterface) Thread.currentThread()).getCheckedPassengers()});
                break;

            case MessageType.WAIT_FOR_ALL_IN_BOARDING:
                if ((inMessage.getAttributesSize() != 1 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER))
                    { GenericIO.writelnString ("Invalid message! -> WAIT_FOR_ALL_IN_BOARDING"); System.exit(1); }
                ((PilotInterface) Thread.currentThread ()).setPilotState ((int) inMessage.getAttributes()[0]);
                plane.waitForAllInBoarding();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(1);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER});
                outMessage.setAttributes(new Object[]{((PilotInterface) Thread.currentThread()).getPilotState()});
                break;

            case MessageType.INFORM_PLANE_READY_TO_TAKE_OFF:
                if ((inMessage.getAttributesSize() != 1 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER))
                    { GenericIO.writelnString ("Invalid message! -> INFORM_PLANE_READY_TO_TAKE_OFF"); System.exit(1); }
                ((HostessInterface) Thread.currentThread ()).setHostessState ((int) inMessage.getAttributes()[0]);
                plane.informPlaneReadyToTakeOff();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(1);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER});
                outMessage.setAttributes(new Object[]{((HostessInterface) Thread.currentThread()).getHostessState()});
                break;

            case MessageType.WAIT_FOR_END_OFF_FLIGHT:
                plane.waitForEndOfFlight();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                break;

            case MessageType.ANNOUNCE_ARRIVAL:
                if ((inMessage.getAttributesSize() != 2 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                        || (inMessage.getAttributesType()[1] != AttributeTypes.INTEGER))
                    { GenericIO.writelnString ("Invalid message! -> ANNOUNCE_ARRIVAL"); System.exit(1); }
                ((PilotInterface) Thread.currentThread ()).setPilotState ((int) inMessage.getAttributes()[0]);
                ((PilotInterface) Thread.currentThread ()).setTransportedPassengers ((int) inMessage.getAttributes()[1]);
                plane.announceArrival();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(2);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER});
                outMessage.setAttributes(new Object[]{((PilotInterface) Thread.currentThread()).getPilotState(),
                        ((PilotInterface) Thread.currentThread()).getTransportedPassengers()});
                break;

            case MessageType.LEAVE_THE_PLANE:
                if ((inMessage.getAttributesSize() != 2 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                        || (inMessage.getAttributesType()[1] != AttributeTypes.INTEGER))
                    { GenericIO.writelnString ("Invalid message! -> LEAVE_THE_PLANE"); System.exit(1); }
                ((PassengerInterface) Thread.currentThread ()).setPassengerState ((int) inMessage.getAttributes()[0]);
                ((PassengerInterface) Thread.currentThread ()).setPassengerId ((int) inMessage.getAttributes()[1]);
                plane.leaveThePlane();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(2);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER});
                outMessage.setAttributes(new Object[]{((PassengerInterface) Thread.currentThread()).getPassengerState(),
                        ((PassengerInterface) Thread.currentThread()).getPassengerId()});
                break;

            case MessageType.SHUT:
                PlaneMain.endConnection();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
        }

        return (outMessage);
    }
}
