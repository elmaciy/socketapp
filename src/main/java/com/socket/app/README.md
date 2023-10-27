To compile classes run this command
go to project root directory :
open command line. 

#this command starts server socket
java -cp ./target/SocketProject-1.0-SNAPSHOT.jar com.socket.app.MyServer

#these commands starts clients and connects to the server and writes names and surnames, then disconnects
java -cp ./target/SocketProject-1.0-SNAPSHOT.jar com.socket.app.MyClient 1 Hamza Elmaci
java -cp ./target/SocketProject-1.0-SNAPSHOT.jar com.socket.app.MyClient 2 Zeynep Elmaci
java -cp ./target/SocketProject-1.0-SNAPSHOT.jar com.socket.app.MyClient 3 Selim Kasmaz
java -cp ./target/SocketProject-1.0-SNAPSHOT.jar com.socket.app.MyClient 4 Ahmet Sevgili
java -cp ./target/SocketProject-1.0-SNAPSHOT.jar com.socket.app.MyClient 5 Leyla Uzun


#these commands starts clients and connects to the server and reads the data they wrote before back
java -cp ./target/SocketProject-1.0-SNAPSHOT.jar com.socket.app.MyClient 1
java -cp ./target/SocketProject-1.0-SNAPSHOT.jar com.socket.app.MyClient 2
java -cp ./target/SocketProject-1.0-SNAPSHOT.jar com.socket.app.MyClient 3
java -cp ./target/SocketProject-1.0-SNAPSHOT.jar com.socket.app.MyClient 4
java -cp ./target/SocketProject-1.0-SNAPSHOT.jar com.socket.app.MyClient 5