echo "Transfering data to the hostess node."
sshpass -f password ssh sd107@l040101-ws07.ua.pt 'mkdir -p ~/hostess'
sshpass -f password ssh sd107@l040101-ws07.ua.pt 'rm -rf ~/hostess/*'
sshpass -f password scp hostess.zip sd107@l040101-ws07.ua.pt:~/hostess/
echo "Decompressing data sent to the hostess node."
sshpass -f password ssh sd107@l040101-ws07.ua.pt 'cd ~/hostess/ ; unzip -uq hostess.zip'
echo "Executing program at the hostess node."
sshpass -f password ssh sd107@l040101-ws07.ua.pt 'cd ~/hostess/hostess/ ; java -cp .:genclass.jar clientSide.main.HostessMain'