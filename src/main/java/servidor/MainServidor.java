package servidor;

import cliente.Persona;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

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


            while(true){
                Socket socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                Gson gson = new Gson();




                ReadContext ctx = JsonPath.parse(in.readUTF());



                String tipoRequest = ctx.read("RequestType");


                if(tipoRequest.equals("registroCuenta")){

                    String nombre = ctx.read("nombre");
                    String contra = ctx.read("contra");
                    String ciudad = ctx.read("ciudad");
                    String email = ctx.read("email");



                    GestionCuenta gestion = new GestionCuenta();
                    gestion.registrarCuenta(nombre,contra,email,ciudad);

                }


                System.out.println(tipoRequest);
                in.close();
                socket.close();
            }





        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
