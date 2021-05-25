package serverSide.sharedRegions;

import commInfra.AttributeTypes;
import commInfra.Message;
import commInfra.MessageType;
import genclass.GenericIO;
import serverSide.main.GeneralReposMain;

/**
 *  General Repository of information.
 *
 *    It is responsible to keep the visible internal state of the problem and to provide means for it
 *    to be printed in the logging file.
 *    It is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 *    There are no internal synchronization points.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class GeneralReposInterface {

    /**
     * Reference to the General Repository of Information.
     */

    private final GeneralRepos generalRepos;

    /**
     *  Instantiation of an interface to the General Repository of Information.
     *
     *    @param generalRepos reference to the general repository of information
     */

    public GeneralReposInterface (GeneralRepos generalRepos) {
        this.generalRepos = generalRepos;
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
            case MessageType.SET_PASSENGER_STATE:
                if (inMessage.getParametersSize() != 2 || inMessage.getParametersType()[0] != AttributeTypes.INTEGER
                        || inMessage.getParametersType()[1] != AttributeTypes.INTEGER)
                    { GenericIO.writelnString ("Invalid message! -> SET_PASSENGER_STATE"); System.exit(1); }
                generalRepos.setPassengerState((int) inMessage.getParameters()[0], (int) inMessage.getParameters()[1]);
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                break;

            case MessageType.SET_HOSTESS_STATE:
                if (inMessage.getParametersSize() != 2 || inMessage.getParametersType()[0] != AttributeTypes.INTEGER
                        || inMessage.getParametersType()[1] != AttributeTypes.INTEGER)
                    { GenericIO.writelnString ("Invalid message! -> SET_HOSTESS_STATE"); System.exit(1); }
                generalRepos.setHostessState((int) inMessage.getParameters()[0], (int) inMessage.getParameters()[1]);
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                break;

            case MessageType.SET_PILOT_STATE:
                if (inMessage.getParametersSize() != 1 || inMessage.getParametersType()[0] != AttributeTypes.INTEGER)
                    { GenericIO.writelnString ("Invalid message! -> SET_PILOT_STATE"); System.exit(1); }
                generalRepos.setPilotState((int) inMessage.getParameters()[0]);
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                break;

            case MessageType.REPORT_FINAL_INFO:
                generalRepos.reportFinalInfo();
                // outMessage
                outMessage = new Message(MessageType.RETURN);
                break;

            case MessageType.SHUT:
                GeneralReposMain.endConnection();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
        }

        return (outMessage);
    }
}
