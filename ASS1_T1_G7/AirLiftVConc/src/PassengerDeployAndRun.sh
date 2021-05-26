echo "Transfering data to the passenger node."
sshpass -f password ssh sd107@l040101-ws08.ua.pt 'mkdir -p ~/passenger'
sshpass -f password ssh sd107@l040101-ws08.ua.pt 'rm -rf ~/passenger/*'
sshpass -f password scp passenger.zip sd107@l040101-ws08.ua.pt:~/passenger/
echo "Decompressing data sent to the passenger node."
sshpass -f password ssh sd107@l040101-ws08.ua.pt 'cd ~/passenger/ ; unzip -uq passenger.zip'
echo "Executing program at the passenger node."
sshpass -f password ssh sd107@l040101-ws08.ua.pt 'cd ~/passenger/passenger/ ; java -cp .:genclass.jar clientSide.main.PassengerMain'