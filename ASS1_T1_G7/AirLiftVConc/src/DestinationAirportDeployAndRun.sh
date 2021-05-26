echo "Transfering data to the destination airport node."
sshpass -f password ssh sd104@l040101-ws03.ua.pt 'mkdir -p ~/destinationairport'
sshpass -f password ssh sd104@l040101-ws03.ua.pt 'rm -rf ~/destinationairport/*'
sshpass -f password scp destinationairport.zip sd104@l040101-ws03.ua.pt:~/destinationairport/
echo "Decompressing data sent to the destination airport node."
sshpass -f password ssh sd104@l040101-ws03.ua.pt 'cd ~/destinationairport/ ; unzip -uq destinationairport.zip'
echo "Executing program at the destination airport node."
sshpass -f password ssh sd104@l040101-ws03.ua.pt 'cd ~/destinationairport/ ; java -cp .:genclass.jar serverSide.main.DestinationAirportMain