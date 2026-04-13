# mvn install && sudo usermod -a -G dialout $USER &&  sudo java -jar target/quectel-at-client-1.0-SNAPSHOT.jar /dev/ttyUSB2 | tee logE5GUE.txt


# captura tudo (stdout+stdin) do processo em um pty e grava em logE5GUE.txt
mvn install && sudo usermod -a -G dialout $USER &&  script -q -c "sudo java -jar target/quectel-at-client-1.0-SNAPSHOT.jar /dev/ttyUSB2" logE5GUE.txt

