echo "Transfering data to the general repository node."
sshpass -f password ssh sd107@l040101-ws05.ua.pt 'mkdir -p ~/generalrepos'
sshpass -f password ssh sd107@l040101-ws05.ua.pt 'rm -rf ~/generalrepos/*'
sshpass -f password scp generalrepos.zip sd107@l040101-ws05.ua.pt:~/generalrepos/
echo "Decompressing data sent to the general repository node."
sshpass -f password ssh sd107@l040101-ws05.ua.pt 'cd ~/generalrepos/ ; unzip -uq generalrepos.zip'
echo "Executing program at the general repository node."
sshpass -f password ssh sd107@l040101-ws05.ua.pt 'cd ~/generalrepos/ ; java -cp .:genclass.jar serverSide.main.GeneralReposMain