package serverSide.entities;

/**
 *  Interface to the Pilot.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method
 *    and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public interface PilotInterface {

    /**
     * Set pilot id.
     *
     * @param id pilot id
     */

    void setPilotId(int id);

    /**
     * Get pilot id.
     *
     * @return pilot id
     */

    int getPilotId();

    /**
     * Set number of passengers which pilot has transported.
     *
     * @param nTransportedPassengers number of passengers checked
     */

    void setTransportedPassengers(int nTransportedPassengers);

    /**
     * Get number of passengers which pilot has transported.
     *
     * @return hostess count
     */

    int getTransportedPassengers();

    /**
     * Set if hostess has informed the pilot that the plane is ready to take off.
     *
     * @param bool ready to take off
     */

    void setReadyToTakeOff(boolean bool);

    /**
     * Get ready to take off
     *
     * @return True if ready to take off
     */

    boolean getReadyToTakeOff();

    /**
     * Set pilot state.
     *
     * @param state new pilot state
     */

    void setPilotState(int state);

    /**
     * Get pilot state.
     *
     * @return pilot state
     */

    int getPilotState();
}
