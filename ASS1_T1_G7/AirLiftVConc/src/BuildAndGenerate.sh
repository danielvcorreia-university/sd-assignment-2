echo "Compiling source code."
javac */*.java */*/*.java
echo "Distributing intermediate code to the different execution environments."
echo "  General Repository of Information"
rm -rf generalrepos
mkdir -p generalrepos generalrepos/serverSide generalrepos/serverSide/main generalrepos/serverSide/entities generalrepos/serverSide/sharedRegions \
         generalrepos/commInfra
cp serverSide/main/SimulPar.class serverSide/main/GeneralReposMain.class generalrepos/serverSide/main
cp serverSide/entities/PilotStates.class serverSide/entities/HostessStates.class serverSide/entities/PassengerStates.class serverSide/entities/GeneralReposProxy.class generalrepos/serverSide/entities
cp serverSide/sharedRegions/GeneralReposInterface.class serverSide/sharedRegions/GeneralRepos.class generalrepos/serverSide/sharedRegions
cp commInfra/*.class generalrepos/commInfra
cp ../genclass.jar generalrepos
echo "  Departure Airport"
rm -rf departureairport
mkdir -p departureairport departureairport/serverSide departureairport/serverSide/main departureairport/serverSide/entities departureairport/serverSide/sharedRegions \
         departureairport/commInfra
cp serverSide/main/SimulPar.class serverSide/main/DepartureAirportMain.class departureairport/serverSide/main
cp serverSide/entities/HostessInterface.class serverSide/entities/PilotInterface.class serverSide/entities/PassengerInterface.class serverSide/entities/PilotStates.class \ 
         serverSide/entities/HostessStates.class serverSide/entities/PassengerStates.class serverSide/entities/DepartureAirportProxy.class departureairport/serverSide/entities
cp serverSide/sharedRegions/DepartureAirportInterface.class serverSide/sharedRegions/DepartureAirport.class serverSide/sharedRegions/GeneralReposStub.class departureairport/serverSide/sharedRegions
cp commInfra/*.class departureairport/commInfra
cp ../genclass.jar departureairport
echo "  Plane"
rm -rf plane
mkdir -p plane plane/serverSide plane/serverSide/main plane/serverSide/entities plane/serverSide/sharedRegions \
         plane/commInfra
cp serverSide/main/SimulPar.class serverSide/main/PlaneMain.class plane/serverSide/main
cp serverSide/entities/HostessInterface.class serverSide/entities/PilotInterface.class serverSide/entities/PassengerInterface.class serverSide/entities/PilotStates.class \
         serverSide/entities/HostessStates.class serverSide/entities/PassengerStates.class serverSide/entities/PlaneProxy.class plane/serverSide/entities
cp serverSide/sharedRegions/PlaneInterface.class serverSide/sharedRegions/Plane.class serverSide/sharedRegions/GeneralReposStub.class plane/serverSide/sharedRegions
cp commInfra/*.class plane/commInfra
cp ../genclass.jar plane
echo "  Destination Airport"
rm -rf destinationairport
mkdir -p destinationairport destinationairport/serverSide destinationairport/serverSide/main destinationairport/serverSide/entities destinationairport/serverSide/sharedRegions \
         destinationairport/commInfra
cp serverSide/main/SimulPar.class serverSide/main/DestinationAirportMain.class destinationairport/serverSide/main
cp serverSide/entities/HostessInterface.class serverSide/entities/PilotInterface.class serverSide/entities/PassengerInterface.class \
         serverSide/entities/DestinationAirportProxy.class destinationairport/serverSide/entities
cp serverSide/sharedRegions/DestinationAirportInterface.class serverSide/sharedRegions/DestinationAirport.class serverSide/sharedRegions/GeneralReposStub.class destinationairport/serverSide/sharedRegions
cp commInfra/*.class destinationairport/commInfra
cp ../genclass.jar destinationairport
echo "  Pilot"
rm -rf pilot
mkdir -p pilot/clientSide pilot/clientSide/main pilot/clientSide/entities pilot/clientSide/stubs pilot/commInfra
cp clientSide/main/PilotMain.class clientSide/main/SimulPar.class pilot/clientSide/main
cp clientSide/entities/Pilot.class clientSide/entities/PilotStates.class pilot/clientSide/entities
cp clientSide/stubs/GeneralReposStub.class clientSide/stubs/DepartureAirportStub.class clientSide/stubs/PlaneStub.class \
         clientSide/stubs/DestinationAirportStub.class pilot/clientSide/stubs
cp commInfra/*.class pilot/commInfra
cp ../genclass.jar pilot
echo "  Hostess"
rm -rf hostess
mkdir -p hostess/clientSide hostess/clientSide/main hostess/clientSide/entities hostess/clientSide/stubs hostess/commInfra
cp clientSide/main/HostessMain.class clientSide/main/SimulPar.class hostess/clientSide/main
cp clientSide/entities/Hostess.class clientSide/entities/HostessStates.class hostess/clientSide/entities
cp clientSide/stubs/GeneralReposStub.class clientSide/stubs/DepartureAirportStub.class clientSide/stubs/PlaneStub.class \
         clientSide/stubs/DestinationAirportStub.class hostess/clientSide/stubs
cp commInfra/*.class hostess/commInfra
cp ../genclass.jar hostess
echo "  Passengers"
rm -rf passenger
mkdir -p passenger/clientSide passenger/clientSide/main passenger/clientSide/entities passenger/clientSide/stubs passenger/commInfra
cp clientSide/main/PassengerMain.class clientSide/main/SimulPar.class passenger/clientSide/main
cp clientSide/entities/Passenger.class clientSide/entities/PassengerStates.class passenger/clientSide/entities
cp clientSide/stubs/GeneralReposStub.class clientSide/stubs/DepartureAirportStub.class clientSide/stubs/PlaneStub.class \
         clientSide/stubs/DestinationAirportStub.class passenger/clientSide/stubs
cp commInfra/*.class passenger/commInfra
cp ../genclass.jar passenger
echo "Compressing execution environments."
echo "  General Repository of Information"
rm -f  generalrepos.zip
zip -rq generalrepos.zip generalrepos
echo "  Departure Airport"
rm -f  departureairport.zip
zip -rq departureairport.zip departureairport
echo "  Plane"
rm -f  plane.zip
zip -rq plane.zip plane
echo "  Destination Airport"
rm -f  destinationairport.zip
zip -rq destinationairport.zip destinationairport
echo "  Pilot"
rm -f  pilot.zip
zip -rq pilot.zip pilot
echo "  Hostess"
rm -f  hostess.zip
zip -rq hostess.zip hostess
echo "  Passengers"
rm -f  passenger.zip
zip -rq passenger.zip passenger