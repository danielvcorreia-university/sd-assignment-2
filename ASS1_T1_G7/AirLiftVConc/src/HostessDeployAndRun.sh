echo "Transfering data to the hostess node."
sshpass -f ../password ssh sd104@l040101-ws07.ua.pt 'mkdir -p ~/hostess'
sshpass -f ../password ssh sd104@l040101-ws07.ua.pt 'rm -rf ~/hostess/*'
sshpass -f ../password scp hostess.zip sd104@l040101-ws07.ua.pt:~/hostess/
echo "Decompressing data sent to the hostess node."
sshpass -f ../password ssh sd104@l040101-ws07.ua.pt 'cd ~/hostess/ ; unzip -uq hostess.zip'
echo "Executing program at the hostess node."
sshpass -f password ssh sd104@l040101-ws07.ua.pt 'cd ~/hostess/ ; java -cp .:genclass.jar clientSide.main.HostessMain