package servidor;

import cliente.Persona;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

//swing , gson y sockets


public class MainServidor {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            Gson gson = new Gson();
            Persona persona = gson.fromJson(in.readUTF(), Persona.class);

            GestionCuenta gestion = new GestionCuenta();
            gestion.registrarCuenta(persona.getNombre(), persona.getContra());


            System.out.println(in.readUTF());

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
