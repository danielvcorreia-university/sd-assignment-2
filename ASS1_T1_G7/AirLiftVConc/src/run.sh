for i in $(seq 1 10)
do
 echo -e "\nRun n. " $1
 java -cp .:genclass.jar serverSide.main.GeneralReposMain &
 java -cp .:genclass.jar serverSide.main.DepartureAirportMain &
 java -cp .:genclass.jar serverSide.main.DestinationAirportMain &
 java -cp .:genclass.jar serverSide.main.PlaneMain &
 sleep 3
 java -cp .:genclass.jar clientSide.main.HostessMain &
 java -cp .:genclass.jar clientSide.main.PassengerMain &
 java -cp .:genclass.jar clientSide.main.PilotMain &
 sleep 45
done
