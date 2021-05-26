echo "Transfering data to the departure airport node."
sshpass -f password ssh sd107@l040101-ws01.ua.pt 'mkdir -p ~/departureairport'
sshpass -f password ssh sd107@l040101-ws01.ua.pt 'rm -rf ~/departureairport/*'
sshpass -f password scp departureairport.zip sd107@l040101-ws01.ua.pt:~/departureairport/
echo "Decompressing data sent to the departure airport node."
sshpass -f password ssh sd107@l040101-ws01.ua.pt 'cd ~/departureairport/ ; unzip -uq departureairport.zip'
echo "Executing program at the departure airport node."
sshpass -f password ssh sd107@l040101-ws01.ua.pt 'cd ~/departureairport/ ; java -cp .:genclass.jar serverSide.main.DepartureAirportMain