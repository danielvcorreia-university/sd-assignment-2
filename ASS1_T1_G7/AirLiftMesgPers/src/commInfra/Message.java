package commInfra;

import java.io.Serializable;

/**
 * Represents a message that is going to be sent between regions.
 * The class is serializable, and has constructors to every kind
 * of message needed. Also has getters to get the values from the fields.
 * @author Daniel Vala Correia
 * @author Alexandre Abreu
 */
public class Message implements Serializable {

    /**
     * Serial version of the class.
     */
    private static final long serialVersionUID = 2021L;

    /**
     * Type of the message.
     */
    private MessageType type;

    /**
     * Pilot identification.
     */
    private int pilotId;

    /**
     * Pilot state.
     */
    private int pilotState;

    /**
     * Hostess identification.
     */
    private int hostessId;

    /**
     * Hostess state.
     */
    private int hostessState;

    /**
     * Passenger identification.
     */
    private int passengerId;

    /**
     * Passenger state.
     */
    private int passengerState;

    /**
     * Number of passengers in queue waiting for to show their documents to the hostess.
     */
    private int inQ;

    /**
     * Waiting queue at the transfer gate.
     */
    private MemFIFO<Integer> boardingQueue;

    /**
     * Empty constructor for the message that initializes the default
     * values for all the variables.
     */
    public Message(){
        /* CHANGE */
    }

    /**
     * Constructor with only the type of the message.
     * @param type type of the message
     */
    public Message(MessageType type){
        this();
        this.type = type;
    }

    /**
     * Constructor with the type of the message and an integer argument.
     * @param type type of the message
     * @param value integer argument
     */
    public Message(MessageType type, int value){
        this();
        this.type = type;

        switch(type){
            case GET_INQ:
                inQ = value;
                break;

        }
    }

    /**
     * Constructor with the type of the message and an integer argument.
     * @param type type of the message
     * @param id id argument
     * @param state state argument
     * @param inQueue inQ argument
     */
    public Message(MessageType type, int id, int state, int inQueue, ){
        this();
        this.type = type;

        switch(type){
            case PREPARE_FOR_PASS_BOARDING:
                hostessId = id;
                hostessState = state;
                inQ = inQueue;
                break;
        }
    }

    public Message(MessageType type, int id, int state, int inQueue, MemFIFO<Integer> boardingQ){
        this();
        this.type = type;

        switch(type){
            case WAIT_IN_QUEUE:
                passengerId = id;
                passengerState = state;
                inQ = inQueue;
                boardingQueue = boardingQ;
                break;
        }
    }

}
