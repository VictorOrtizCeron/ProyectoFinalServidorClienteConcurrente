package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServidor {

    public static void main(String[] args)  {
        try{
            ServerSocket serverSocket = new ServerSocket(5000);
            Socket cliente = serverSocket.accept();
            System.out.println("Se ha conectado un cliente");
        }
        catch (IOException e){
            System.out.println("Error");
        }
    }

}
