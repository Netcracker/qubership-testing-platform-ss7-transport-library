export SSHPASS="some_pass"
sshpass -e sftp -oBatchMode=no -b - some_user@some_ip << !
   cd /path-to-some-dir
   get dump.pcap
!
