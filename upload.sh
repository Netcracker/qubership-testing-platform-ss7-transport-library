export SSHPASS="some_pass"
sshpass -e sftp -oBatchMode=no -b - some_user@some_ip << !
   cd /path-to-some-dir
   put ./target/ss7.jar
!
