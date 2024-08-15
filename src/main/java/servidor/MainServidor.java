package servidor;

import cliente.Persona;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//swing , gson y sockets


public class MainServidor {

    public static void main(String[] args) {






        try{
            ServerSocket serverSocket = new ServerSocket(8080);

            int count = 0;

            while (true){
                Socket cliente = serverSocket.accept();
                Servidor servidor = new Servidor(cliente);
                servidor.run();
            }
        }catch (IOException e){
            e.printStackTrace();
        }






    }
}
