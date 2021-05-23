package commInfra;

/**
 * Definition of the different message Types
 * exchanged between the clients and the servers
 *
 *  * @author Daniel Vala Correia
 *  * @author Alexandre Abreu
 */

public class MessageType
{
    /**
     *  Initialization of the logging file name and the number of iterations (service request).
     */

    public static final int SETNFIC = 1;

    /**
     *  Logging file was initialized (reply).
     */
    public static final int NFICDONE = 2;

    /**
     * It is called by the hostess while waiting for passengers to arrive at the airport.
     */

    public static final int PREPARE_FOR_PASS_BOARDING = 3;

    /**
     * It is called by a passenger while waiting for his turn to show his documents to the hostess.
     */

    public static final int WAIT_IN_QUEUE = 4;

    /**
     * It is called by the hostess while waiting for the first costumer in queue to show his documents.
     */

    public static final int CHECK_DOCUMENTS = 5;

    /**
     * It is called by a passenger if the hostess has called him to check his documents.
     */

    public static final int SHOW_DOCUMENTS = 6;

    /**
     * It is called by the hostess while waiting for the next passenger in queue.
     */

    public static final int WAIT_FOR_NEXT_PASSENGER = 7;

    /**
     * It is called by the passengers when they are allowed to enter the plane.
     */

    public static final int BOARD_THE_PLANE = 8;

    /**
     * It is called by the pilot after he parks the plane at the transfer gate and there are no more passengers to transport
     */

    public static final int FINAL_REPORT = 9;

    /**
     * It is called by the pilot after he arrived at departure airport.
     */

    public static final int PARK_AT_TRANSFER_GATE = 10;

    /**
     * It is called by the pilot to inform the hostess that the plane is ready for boarding.
     */

    public static final int INFORM_PLANE_READY_FOR_BOARDING = 11;

    /**
     * It is called by the hostess while waiting for plane to be ready for boarding.
     */

    public static final int WAIT_FOR_NEXT_FLIGHT = 12;

    /**
     * It is called by the pilot after he announced the hostess
     * that the plane is ready for boarding .
     */

    public static final int WAIT_FOR_ALL_IN_BOARDING = 13;

    /**
     * It is called by the hostess when she ended the check in of the passengers.
     */

    public static final int INFORM_PLANE_READY_TO_TAKE_OFF = 14;

    /**
     * It is called by the passengers when they are inside the plane and begin their waiting journey.
     */

    public static final int WAIT_FOR_END_OFF_FLIGHT = 15;

    /**
     * It is called by the hostess when she ended the check in of the passengers.
     */

    public static final int ANNOUNCE_ARRIVAL = 16;

    /**
     * It is called by the passengers when they leave the plane.
     */

    public static final int LEAVE_THE_PLANE = 17;

    /**
     * Set passenger state.
     */

    public static final int SET_PASSENGER_STATE = 18;

    /**
     * Set hostess state.
     */

    public static final int SET_HOSTESS_STATE = 19;

    /**
     * Set pilot state.
     */

    public static final int SET_PILOT_STATE = 20;

    /**
     * Initial status of the General Repository
     */

    public static final int REPORT_INITIAL_STATUS = 21;

    /**
     * It prints the current states of the hostess, pilot and passengers when one of them changes states.
     */

    public static final int REPORT_STATUS = 22;

    /**
     * It prints all the flights performed and the amount of passengers that were in each one
     */

    public static final int REPORT_FINAL_INFO = 23;

    /**
     *  Return Message.
     */

    public static final int RETURN = 24;

    /**
     *  Server shutdown.
     */

    public static final int SHUT = 25;

    /**
     *  Server was shutdown.
     */

    public static final int SHUTDONE = 26;
}