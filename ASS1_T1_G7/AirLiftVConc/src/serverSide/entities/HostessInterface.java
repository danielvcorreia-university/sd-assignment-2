package serverSide.entities;

/**
 *  Interface to the Hostess.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method
 *    and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public interface HostessInterface {

    /**
     * Set hostess id.
     *
     * @param id hostess id
     */

    void setHostessId(int id);

    /**
     * Get hostess id.
     *
     * @return hostess id
     */

    int getHostessId();

    /**
     * Set hostess count.
     *
     * @param count hostess count
     */

    void setHostessCount(int count);

    /**
     * Get hostess count.
     *
     * @return hostess count
     */

    int getHostessCount();

    /**
     * Set number of passengers which hostess checked documents.
     *
     * @param nCheckedPassengers number of passengers checked
     */

    void setCheckedPassengers(int nCheckedPassengers);

    /**
     * Get number of passengers which hostess checked documents.
     *
     * @return checked passengers
     */

    int getCheckedPassengers();

    /**
     * Set if there is any passenger in queue for the hostess to process
     *
     * @param bool passenger in queue
     */

    void setPassengerInQueue(boolean bool);

    /**
     * Check if there is any passenger in queue for the hostess to process
     *
     * @return True if passenger in queue
     */

    boolean getPassengerInQueue();

    /**
     * Set if hostess has received the documents from the passenger.
     *
     * @param bool ready to check documents
     */

    void setReadyToCheckDocuments(boolean bool);

    /**
     * Get ready to check documents.
     *
     * @return ready to check documents
     */

    boolean getReadyToCheckDocuments();

    /**
     * Set hostess state.
     *
     * @param state new hostess state
     */

    void setHostessState(int state);

    /**
     * Get hostess state.
     *
     * @return hostess state
     */

    int getHostessState();
}
