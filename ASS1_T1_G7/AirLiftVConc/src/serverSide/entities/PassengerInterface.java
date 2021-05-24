package serverSide.entities;

/**
 *  Interface to the Passenger.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method
 *    and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

interface PassengerInterface {

    /**
     * Set passenger id.
     *
     * @param id passenger id
     */

    void setPassengerId(int id);

    /**
     * Get passenger id.
     *
     * @return passenger id
     */

    int getPassengerId();

    /**
     * Set if passenger is ready to show documents to hostess.
     *
     * @param bool ready to show documents
     */

    void setReadyToShowDocuments(boolean bool);

    /**
     * Get ready to show documents.
     *
     * @return True if ready to show documents
     */

    boolean getReadyToShowDocuments();

    /**
     * Set passenger state.
     *
     * @param state new passenger state
     */

    void setPassengerState(int state);

    /**
     * Get passenger state.
     *
     * @return passenger state
     */

    int getPassengerState();
}
