package serverSide.sharedRegions;

import commInfra.AttributeTypes;
import commInfra.Message;
import commInfra.MessageType;
import genclass.GenericIO;
import serverSide.entities.PassengerInterface;
import serverSide.main.DestinationAirportMain;

/**
 *  Interface to the Destination Airport.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Destination Airport and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class DestinationAirportInterface {

    /**
     * Reference to the Destination Airport.
     */

    private final DestinationAirport desAirport;

    /**
     *  Instantiation of an interface to the Destination Airport.
     *
     *    @param desAirport reference to the destination airport
     */

    public DestinationAirportInterface (DestinationAirport desAirport) {
        this.desAirport = desAirport;
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
            case MessageType.LEAVE_THE_PLANE:
                if ((inMessage.getAttributesSize() != 2 ) || (inMessage.getAttributesType()[0] != AttributeTypes.INTEGER)
                        || (inMessage.getAttributesType()[1] != AttributeTypes.INTEGER))
                { GenericIO.writelnString ("Invalid message! -> LEAVE_THE_PLANE"); System.exit(1); }
                ((PassengerInterface) Thread.currentThread ()).setPassengerState ((int) inMessage.getAttributes()[0]);
                ((PassengerInterface) Thread.currentThread ()).setPassengerId ((int) inMessage.getAttributes()[1]);
                boolean lastPassenger;
                lastPassenger = desAirport.leaveThePlane((int) inMessage.getParameters()[0]);
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                outMessage.setAttributesSize(2);
                outMessage.setParametersSize(1);
                outMessage.setAttributesType(new int[]{AttributeTypes.INTEGER, AttributeTypes.INTEGER});
                outMessage.setParametersType(new int[]{AttributeTypes.BOOLEAN});
                outMessage.setAttributes(new Object[]{((PassengerInterface) Thread.currentThread()).getPassengerState(),
                        ((PassengerInterface) Thread.currentThread()).getPassengerId()});
                outMessage.setParameters(new Object[]{lastPassenger});
                break;

            case MessageType.SHUT:
                DestinationAirportMain.endConnection();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
        }

        return (outMessage);
    }
}
