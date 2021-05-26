package clientSide.entities;

import clientSide.stubs.DepartureAirportStub;
import clientSide.stubs.DestinationAirportStub;
import clientSide.stubs.PlaneStub;
import genclass.GenericIO;
import clientSide.main.*;

/**
 *   Pilot thread.
 *
 *   It simulates the pilot life cycle.
 *   Static solution.
 */

public class Pilot extends Thread {
    /**
     * Pilot identification.
     */

    private int pilotId;

    /**
     * Count number of passengers transported by pilot.
     */

    private int transportedPassengers;

    /**
     * Pilot state.
     */

    private int pilotState;

    /**
     * Reference to the plane.
     */

    private final PlaneStub plane;

    /**
     * Reference to the departure airport.
     */

    private final DepartureAirportStub depAirport;

    /**
     * Reference to the destination airport.
     */

    private final DestinationAirportStub destAirport;

    /**
     * Instantiation of a pilot thread.
     *
     * @param name       thread name
     * @param pilotId    pilot id
     * @param plane      reference to the plane
     * @param destAirport reference to the destination airport
     */

    public Pilot(String name, int pilotId, PlaneStub plane, DestinationAirportStub destAirport, DepartureAirportStub depAirport) {
        super(name);
        this.pilotId = pilotId;
        pilotState = PilotStates.AT_TRANSFER_GATE;
        this.plane = plane;
        this.destAirport = destAirport;
        this.depAirport = depAirport;
    }

    /**
     * Set pilot id.
     *
     * @param id pilot id
     */

    public void setPilotId(int id) {
        pilotId = id;
    }

    /**
     * Get pilot id.
     *
     * @return pilot id
     */

    public int getPilotId() {
        return pilotId;
    }

    /**
     * Set number of passengers which pilot has transported.
     *
     * @param nTransportedPassengers number of passengers checked
     */

    public void setTransportedPassengers(int nTransportedPassengers) { transportedPassengers = nTransportedPassengers; }

    /**
     * Get number of passengers which pilot has transported.
     *
     * @return hostess count
     */

    public int getTransportedPassengers() {
        return transportedPassengers;
    }

    /**
     * Set pilot state.
     *
     * @param state new pilot state
     */

    public void setPilotState(int state) {
        pilotState = state;
    }

    /**
     * Get pilot state.
     *
     * @return pilot state
     */

    public int getPilotState() {
        return pilotState;
    }

    /**
     * Life cycle of the pilot.
     */

    @Override
    public void run() {
        boolean endOp = false;                                       // flag signaling end of operations

        plane.parkAtTransferGate();
        while (!endOp) {
            plane.informPlaneReadyForBoarding();
            plane.waitForAllInBoarding();
            flyToDestinationPoint();
            plane.announceArrival();
            flyToDeparturePoint();
            plane.parkAtTransferGate();
            if (getTransportedPassengers() == SimulPar.N) {
                plane.reportFinalReport();
                endOp = true;
            }
        }

        depAirport.shutdown();
        depAirport.shutdown();
        destAirport.shutdown();
        destAirport.shutdown();
        plane.shutdown();
        plane.shutdown();
    }

    /**
     * Flying the plane to the destination airport.
     * <p>
     * Internal operation. Sleeps for an amount of time.
     */

    private void flyToDestinationPoint() {
        try {
            sleep((long) (1 + 160 * Math.random()));
        } catch (InterruptedException e) {
            GenericIO.writelnString("Interruption: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Flying the plane to the departure airport.
     * <p>
     * Internal operation. Sleeps for an amount of time.
     */

    private void flyToDeparturePoint() {
        try {
            sleep((long) (1 + 149 * Math.random()));
        } catch (InterruptedException e) {
            GenericIO.writelnString("Interruption: " + e.getMessage());
            System.exit(1);
        }
    }
}
