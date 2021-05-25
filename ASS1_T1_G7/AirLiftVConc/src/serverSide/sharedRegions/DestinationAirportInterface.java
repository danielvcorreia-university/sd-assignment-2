package serverSide.sharedRegions;

import commInfra.Message;
import commInfra.MessageType;
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
            case MessageType.SHUT:
                DestinationAirportMain.endConnection();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
        }

        return (outMessage);
    }
}
