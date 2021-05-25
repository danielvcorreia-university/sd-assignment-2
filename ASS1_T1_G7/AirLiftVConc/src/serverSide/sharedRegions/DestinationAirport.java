package serverSide.sharedRegions;

import clientSide.stubs.*;

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
     * Reference to the stub of the general repository.
     */

    private final GeneralReposStub repos;

    /**
     * Destination airport instantiation.
     *
     * @param repos reference to the stub of the general repository
     */

    public DestinationAirport(GeneralReposStub repos) { this.repos = repos; }

}
