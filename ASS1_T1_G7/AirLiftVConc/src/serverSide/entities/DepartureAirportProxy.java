package serverSide.entities;

import serverSide.sharedRegions.*;
import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *  Service provider agent for access to the Departure Airport.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class DepartureAirportProxy extends Thread implements PilotCloning , HostessCloning, PassengerCloning {

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
     *  Instantiation of a client proxy.
     *
     *     @param sconi communication channel
     *     @param depAirportInterface interface to the departure airport
     */

    public DepartureAirportProxy (ServerCom sconi, DepartureAirportInterface depAirportInterface)
    {
        super ("DepartureAirportProxy_" + DepartureAirportProxy.getProxyId ());
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
     *   Set pilot state.
     *
     *     @param state new pilot state
     */

    public void setPilotState (int state)
    {
        pilotState = state;
    }

    /**
     *   Get pilot state.
     *
     *     @return pilot state
     */

    public int getPilotState ()
    {
        return pilotState;
    }

    /**
     *   Set hostess state.
     *
     *     @param state new hostess state
     */

    public void setHostessState (int state)
    {
        hostessState = state;
    }

    /**
     *   Get hostess state.
     *
     *     @return hostess state
     */

    public int getHostessState ()
    {
        return hostessState;
    }

    /**
     *   Set passenger state.
     *
     *     @param state new passenger state
     */

    public void setPassengerState (int state)
    {
        passengerState = state;
    }

    /**
     *   Get passenger state.
     *
     *     @return passenger state
     */

    public int getPassengerState ()
    {
        return passengerState;
    }

    /**
     *   Set passenger id.
     *
     *     @param id passenger id
     */

    public void setPassengerId (int id)
    {
        passengerId = id;
    }

    /**
     *   Get passenger id.
     *
     *     @return passenger id
     */

    public int getPassengerId ()
    {
        return passengerId;
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
        catch (MessageException e)
        { GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
            GenericIO.writelnString (e.getMessageVal ().toString ());
            System.exit (1);
        }
        sconi.writeObject (outMessage);                                // send service reply
        sconi.close ();                                                // close the communication channel
    }
}
