package serverSide.sharedRegions;

import serverSide.entities.*;

/**
 *    Destination Airport.
 *
 *    It is responsible to keep a continuously updated account of the number of passengers that are already
 *    in the destination airport.
 *    There are two methods. One to get the total number os passengers in destination airport
 *    and one to increment by one the number of passenger in destination airport.
 */

public class DestinationAirport {

    /**
     * For each flight, the number of passengers that left the plane.
     */

    private int PTAL;

    /**
     * Reference to the stub of the general repository.
     */

    private final GeneralReposStub repos;

    /**
     * Destination airport instantiation.
     *
     * @param repos reference to the stub of the general repository
     */

    public DestinationAirport(GeneralReposStub repos) { this.PTAL = 0; this.repos = repos; }

    /**
     * Operation leave the plane
     * <p>
     * It is called by the passengers when they leave the plane.
     *
     * @param inF Number of passengers that flew in this flight.
     * @return Return true if this is the last passenger to leave the plane. Returns false otherwise.
     */

    public synchronized boolean leaveThePlane(int inF) {
        boolean lastPassenger = false;
        PTAL += 1;

        ((PassengerInterface) Thread.currentThread()).setPassengerState(PassengerStates.AT_DESTINATION);
        repos.setPassengerState(((PassengerInterface) Thread.currentThread()).getPassengerId(), ((PassengerInterface) Thread.currentThread()).getPassengerState());

        if (PTAL == inF) { PTAL = 0; lastPassenger = true; }

        return lastPassenger;
    }

}
