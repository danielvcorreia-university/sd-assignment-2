echo "Transfering data to the pilot node."
sshpass -f ../password ssh sd104@l040101-ws06.ua.pt 'mkdir -p ~/pilot'
sshpass -f ../password ssh sd104@l040101-ws06.ua.pt 'rm -rf ~/pilot/*'
sshpass -f ../password scp pilot.zip sd104@l040101-ws06.ua.pt:~/pilot/
echo "Decompressing data sent to the pilot node."
sshpass -f ../password ssh sd104@l040101-ws06.ua.pt 'cd ~/pilot/ ; unzip -uq pilot.zip'
echo "Executing program at the pilot node."
sshpass -f password ssh sd104@l040101-ws06.ua.pt 'cd ~/pilot/ ; java -cp .:genclass.jar clientSide.main.PilotMain
