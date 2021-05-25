package serverSide.sharedRegions;

import commInfra.AttributeTypes;
import commInfra.Message;
import commInfra.MessageType;
import serverSide.entities.PassengerInterface;
import serverSide.entities.HostessInterface;
import genclass.GenericIO;
import serverSide.main.DepartureAirportMain;

/**
 *  Interface to the Departure Airport.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Departure Airport and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class DepartureAirportInterface {

    /**
     * Reference to the Departure Airport.
     */

    private final DepartureAirport depAirport;

    /**
     *  Instantiation of an interface to the Departure Airport.
     *
     *    @param depAirport reference to the departure airport
     */

    public DepartureAirportInterface (DepartureAirport depAirport) {
        this.depAirport = depAirport;
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
            case MessageType.PREPARE_FOR_PASS_BOARDING:
                if (inMessage.getAttributesSize() != 1 || inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                    { GenericIO.writelnString ("Invalid message! -> PREPARE_FOR_PASS_BOARDING"); System.exit(1); }
                ((HostessInterface) Thread.currentThread ()).setHostessState ((int) inMessage.getAttributes()[0]);
                ((HostessInterface) Thread.currentThread ()).setHostessCount(0);
                depAirport.prepareForPassBoarding();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(1);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER});
                outMessage.setAttributes(new Object[]{((HostessInterface) Thread.currentThread()).getHostessState()});
                break;

            case MessageType.WAIT_IN_QUEUE:
                if ((inMessage.getAttributesSize() != 3 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                        || (inMessage.getAttributesType()[1] != AttributeTypes.INTEGER) || (inMessage.getAttributesType()[2] != AttributeTypes.BOOLEAN))
                    { GenericIO.writelnString ("Invalid message! -> WAIT_IN_QUEUE"); System.exit(1); }
                ((PassengerInterface) Thread.currentThread ()).setPassengerState ((int) inMessage.getAttributes()[0]);
                ((PassengerInterface) Thread.currentThread ()).setPassengerId ((int) inMessage.getAttributes()[1]);
                ((PassengerInterface) Thread.currentThread ()).setReadyToShowDocuments ((boolean) inMessage.getAttributes()[2]);
                depAirport.waitInQueue();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(3);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER, AttributeTypes.BOOLEAN});
                outMessage.setAttributes(new Object[]{((PassengerInterface) Thread.currentThread()).getPassengerState(),
                        ((PassengerInterface) Thread.currentThread()).getPassengerId(), ((PassengerInterface) Thread.currentThread()).getReadyToShowDocuments()});
                break;

            case MessageType.CHECK_DOCUMENTS:
                if ((inMessage.getAttributesSize() != 3 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                        || (inMessage.getAttributesType()[1] != AttributeTypes.BOOLEAN) || (inMessage.getAttributesType()[2] != AttributeTypes.BOOLEAN))
                    { GenericIO.writelnString ("Invalid message! -> CHECK_DOCUMENTS"); System.exit(1); }
                ((HostessInterface) Thread.currentThread ()).setHostessState ((int) inMessage.getAttributes()[0]);
                ((HostessInterface) Thread.currentThread ()).setPassengerInQueue ((boolean) inMessage.getAttributes()[1]);
                ((HostessInterface) Thread.currentThread ()).setReadyToCheckDocuments ((boolean) inMessage.getAttributes()[2]);
                depAirport.checkDocuments();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(3);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.BOOLEAN, AttributeTypes.BOOLEAN});
                outMessage.setAttributes(new Object[]{((HostessInterface) Thread.currentThread()).getHostessState(),
                        ((HostessInterface) Thread.currentThread()).getPassengerInQueue(), ((HostessInterface) Thread.currentThread()).getReadyToCheckDocuments()});
                break;

            case MessageType.SHOW_DOCUMENTS:
                if ((inMessage.getAttributesSize() != 2 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                        || (inMessage.getAttributesType()[1] != AttributeTypes.INTEGER))
                    { GenericIO.writelnString ("Invalid message! -> SHOW_DOCUMENTS"); System.exit(1); }
                depAirport.checkDocuments();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                break;

            case MessageType.WAIT_FOR_NEXT_PASSENGER:
                if ((inMessage.getAttributesSize() != 4 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                        || (inMessage.getAttributesType()[1] != AttributeTypes.INTEGER) || (inMessage.getAttributesType()[2] != AttributeTypes.BOOLEAN)
                        || (inMessage.getAttributesType()[3] != AttributeTypes.BOOLEAN))
                    { GenericIO.writelnString ("Invalid message! -> WAIT_FOR_NEXT_PASSENGER"); System.exit(1); }
                ((HostessInterface) Thread.currentThread ()).setHostessState ((int) inMessage.getAttributes()[0]);
                ((HostessInterface) Thread.currentThread ()).setHostessCount ((int) inMessage.getAttributes()[1]);
                ((HostessInterface) Thread.currentThread ()).setReadyForNextPassenger ((boolean) inMessage.getAttributes()[2]);
                ((HostessInterface) Thread.currentThread ()).setPassengerInQueue ((boolean) inMessage.getAttributes()[3]);
                depAirport.waitForNextPassenger();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(4);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER, AttributeTypes.BOOLEAN, AttributeTypes.BOOLEAN});
                outMessage.setAttributes(new Object[]{((HostessInterface) Thread.currentThread()).getHostessState(), ((HostessInterface) Thread.currentThread()).getHostessCount(),
                        ((HostessInterface) Thread.currentThread()).getReadyForNextPassenger(), ((HostessInterface) Thread.currentThread()).getPassengerInQueue()});
                break;

            case MessageType.BOARD_THE_PLANE:
                if ((inMessage.getAttributesSize() != 2 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                        || (inMessage.getAttributesType()[1] != AttributeTypes.INTEGER))
                    { GenericIO.writelnString ("Invalid message! -> BOARD_THE_PLANE"); System.exit(1); }
                ((PassengerInterface) Thread.currentThread ()).setPassengerState ((int) inMessage.getAttributes()[0]);
                ((PassengerInterface) Thread.currentThread ()).setPassengerId ((int) inMessage.getAttributes()[1]);
                depAirport.boardThePlane();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(2);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER});
                outMessage.setAttributes(new Object[]{((PassengerInterface) Thread.currentThread()).getPassengerState(),
                        ((PassengerInterface) Thread.currentThread()).getPassengerId()});
                break;

            case MessageType.SHUT:
                DepartureAirportMain.endConnection();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
        }

        return (outMessage);
    }
}
