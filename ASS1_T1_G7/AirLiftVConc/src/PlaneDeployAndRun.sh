echo "Transfering data to the plane node."
sshpass -f password ssh sd107@l040101-ws02.ua.pt 'mkdir -p ~/plane'
sshpass -f password ssh sd107@l040101-ws02.ua.pt 'rm -rf ~/plane/*'
sshpass -f password scp plane.zip sd107@l040101-ws02.ua.pt:~/plane/
echo "Decompressing data sent to the plane node."
sshpass -f password ssh sd107@l040101-ws02.ua.pt 'cd ~/plane/ ; unzip -uq plane.zip'
echo "Executing program at the plane node."
sshpass -f password ssh sd107@l040101-ws02.ua.pt 'cd ~/plane/ ; java -cp .:genclass.jar serverSide.main.PlaneMain