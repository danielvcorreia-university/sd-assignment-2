package serverSide.entities;

import serverSide.sharedRegions.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *  Service provider agent for access to the Departure Airport.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class DepartureAirportProxy extends Thread implements PilotInterface , HostessInterface, PassengerInterface {

    /**
     *  Number of instantiayed threads.
     */

    private static int nProxy = 0;

    /**
     *  Communication channel.
     */

    private ServerCom sconi;

    /**
     *  Interface to the Departure Airport.
     */

    private DepartureAirportInterface depAirportInterface;

    /**
     *  Pilot state.
     */

    private int pilotState;

    /**
     *  Hostess state.
     */

    private int hostessState;

    /**
     *  Passenger identification.
     */

    private int passengerId;

    /**
     *  Passenger state.
     */

    private int passengerState;

    /**
     * Pilot identification.
     */

    private int pilotId;

    /**
     * Count number of passengers transported by pilot.
     */

    private int transportedPassengers;

    /**
     * True if the plane is ready to take off.
     */

    private boolean readyToTakeOff = false;

    /**
     * Hostess identification.
     */

    private int hostessId;

    /**
     * Count number of passengers on the plane.
     */

    private int hostessCount = 0;

    /**
     * Count number of passengers checked by hostess.
     */

    private int checkedPassengers = 0;

    /**
     * True if there is any passenger in queue for the hostess to process.
     */

    private boolean passengerInQueue = false;

    /**
     * True if the hostess can check next passenger documents.
     */

    private boolean readyForNextPassenger = false;

    /**
     * True if the passenger has given his documents to the hostess for her to check.
     */

    private boolean readyToCheckDocuments = false;

    /**
     * True if the passenger has been called by the hostess to show his documents.
     */

    private boolean readyToShowDocuments = false;

    /**
     *  Instantiation of a client proxy.
     *
     *     @param sconi communication channel
     *     @param depAirportInterface interface to the departure airport
     */

    public DepartureAirportProxy (ServerCom sconi, DepartureAirportInterface depAirportInterface)
    {
        //super ("DepartureAirportProxy_" + DepartureAirportProxy.getProxyId ());
        this.sconi = sconi;
        this.depAirportInterface = depAirportInterface;
    }

    /**
     *  Generation of the instantiation identifier.
     *
     *     @return instantiation identifier
     */

    private static int getProxyId ()
    {
        Class<?> cl = null;                                            // representation of the DepartureAirportProxy object in JVM
        int proxyId;                                                   // instantiation identifier

        try
        { cl = Class.forName ("serverSide.entities.DepartureAirportProxy");
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("Data type DepartureAirportProxy was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
        synchronized (cl)
        { proxyId = nProxy;
            nProxy += 1;
        }
        return proxyId;
    }

    /**
     * Set pilot id.
     *
     * @param id pilot id
     */

    @Override
    public void setPilotId(int id) {
        pilotId = id;
    }

    /**
     * Get pilot id.
     *
     * @return pilot id
     */

    @Override
    public int getPilotId() {
        return pilotId;
    }

    /**
     * Set number of passengers which pilot has transported.
     *
     * @param nTransportedPassengers number of passengers checked
     */

    @Override
    public void setTransportedPassengers(int nTransportedPassengers) { transportedPassengers = nTransportedPassengers; }

    /**
     * Get number of passengers which pilot has transported.
     *
     * @return hostess count
     */

    @Override
    public int getTransportedPassengers() {
        return transportedPassengers;
    }

    /**
     * Set if hostess has informed the pilot that the plane is ready to take off.
     *
     * @param bool ready to take off
     */

    @Override
    public void setReadyToTakeOff(boolean bool) {
        readyToTakeOff = bool;
    }

    /**
     * Get ready to take off
     *
     * @return True if ready to take off
     */

    @Override
    public boolean getReadyToTakeOff() {
        return readyToTakeOff;
    }

    /**
     *   Set pilot state.
     *
     *     @param state new pilot state
     */

    @Override
    public void setPilotState (int state)
    {
        pilotState = state;
    }

    /**
     *   Get pilot state.
     *
     *     @return pilot state
     */

    @Override
    public int getPilotState ()
    {
        return pilotState;
    }

    /**
     * Set hostess id.
     *
     * @param id hostess id
     */

    @Override
    public void setHostessId(int id) {
        hostessId = id;
    }

    /**
     * Get hostess id.
     *
     * @return hostess id
     */

    @Override
    public int getHostessId() {
        return hostessId;
    }

    /**
     * Set hostess count.
     *
     * @param count hostess count
     */

    @Override
    public void setHostessCount(int count) {
        hostessCount = count;
    }

    /**
     * Get hostess count.
     *
     * @return hostess count
     */

    @Override
    public int getHostessCount() {
        return hostessCount;
    }

    /**
     * Set number of passengers which hostess checked documents.
     *
     * @param nCheckedPassengers number of passengers checked
     */

    @Override
    public void setCheckedPassengers(int nCheckedPassengers) { checkedPassengers = nCheckedPassengers; }

    /**
     * Get number of passengers which hostess checked documents.
     *
     * @return checked passengers
     */

    @Override
    public int getCheckedPassengers() {
        return checkedPassengers;
    }

    /**
     * Set if there is any passenger in queue for the hostess to process
     *
     * @param bool passenger in queue
     */

    @Override
    public void setPassengerInQueue(boolean bool) {
        passengerInQueue = bool;
    }

    /**
     * Check if there is any passenger in queue for the hostess to process
     *
     * @return True if passenger in queue
     */

    @Override
    public boolean getPassengerInQueue() {
        return passengerInQueue;
    }

    /**
     * Set if hostess has received the documents from the passenger.
     *
     * @param bool ready to check documents
     */

    @Override
    public void setReadyToCheckDocuments(boolean bool) {
        readyToCheckDocuments = bool;
    }

    /**
     * Get ready to check documents.
     *
     * @return ready to check documents
     */

    @Override
    public boolean getReadyToCheckDocuments() {
        return readyToCheckDocuments;
    }

    /**
     *   Set hostess state.
     *
     *     @param state new hostess state
     */

    @Override
    public void setHostessState (int state)
    {
        hostessState = state;
    }

    /**
     *   Get hostess state.
     *
     *     @return hostess state
     */

    @Override
    public int getHostessState ()
    {
        return hostessState;
    }

    /**
     *   Set passenger state.
     *
     *     @param state new passenger state
     */

    @Override
    public void setPassengerState (int state)
    {
        passengerState = state;
    }

    /**
     *   Get passenger state.
     *
     *     @return passenger state
     */

    @Override
    public int getPassengerState ()
    {
        return passengerState;
    }

    /**
     *   Set passenger id.
     *
     *     @param id passenger id
     */

    @Override
    public void setPassengerId (int id)
    {
        passengerId = id;
    }

    /**
     *   Get passenger id.
     *
     *     @return passenger id
     */

    @Override
    public int getPassengerId ()
    {
        return passengerId;
    }

    /**
     * Set if passenger is ready to show documents to hostess.
     *
     * @param bool ready to show documents
     */

    @Override
    public void setReadyToShowDocuments(boolean bool) {
        readyToShowDocuments = bool;
    }

    /**
     * Get ready to show documents.
     *
     * @return True if ready to show documents
     */

    @Override
    public boolean getReadyToShowDocuments() {
        return readyToShowDocuments;
    }

    /**
     *  Life cycle of the service provider agent.
     */

    @Override
    public void run ()
    {
        Message inMessage = null,                                      // service request
                outMessage = null;                                     // service reply

        /* service providing */

        inMessage = (Message) sconi.readObject ();                     // get service request
        try
        { outMessage = depAirportInterface.processAndReply (inMessage);         // process it
        }
        catch (Exception e)
        { GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
            System.exit (1);
        }
        sconi.writeObject (outMessage);                                // send service reply
        sconi.close ();                                                // close the communication channel
    }
}
