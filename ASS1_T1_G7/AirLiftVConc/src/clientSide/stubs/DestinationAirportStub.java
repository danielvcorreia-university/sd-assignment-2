package clientSide.stubs;

/**
 * Stub to the destination airport.
 *
 * It instantiates a remote reference to the destination airport.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */

public class DestinationAirportStub {

    /**
     *  Name of the platform where is located the destination airport server.
     */

    private String serverHostName;

    /**
     *  Port number for listening to service requests.
     */

    private int serverPortNumb;

    /**
     *  Instantiation of a stub to the destination airport.
     *
     *  @param serverHostName name of the platform where is located the barber shop server
     *  @param serverPortNumb port number for listening to service requests
     */

    public DestinationAirportStub (String serverHostName, int serverPortNumb)
    {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

}
