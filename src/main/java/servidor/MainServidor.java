package servidor;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//swing , gson y sockets


public class MainServidor {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            DataInputStream in = new DataInputStream(new BufferedInputStream(serverSocket.accept().getInputStream()));
            System.out.println(in.read());

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
