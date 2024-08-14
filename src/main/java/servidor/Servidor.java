package servidor;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.io.*;
import java.net.*;

public class Servidor extends Thread{

    private Socket clientSocket = null;
    private boolean isCorriendo = true;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    public Servidor(Socket clientSocket){
        this.clientSocket=clientSocket;
    }

    @Override
    public void run() {
        try {


            while(isCorriendo){

                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                Gson gson = new Gson();




                ReadContext ctx = JsonPath.parse(in.readUTF());



                String tipoRequest = ctx.read("RequestType");


                if(tipoRequest.equals("registroCuenta")){

                    String nombre = ctx.read("nombre");
                    String contra = ctx.read("contra");
                    String ciudad = ctx.read("ciudad");
                    String email = ctx.read("email");



                    GestionCuenta gestion = new GestionCuenta();
                    boolean resultadoRegistro = gestion.registrarCuenta(nombre,contra,email,ciudad);
                    if (resultadoRegistro){
                        dos.writeUTF("Registro exitoso");
                    }else{
                        dos.writeUTF("Registro fallido");
                    }

                }
                else if(tipoRequest.equals("inicioSesion")){



                    String contra = ctx.read("contra");
                    String email = ctx.read("email");
                    System.out.println(email);
                    System.out.println(contra);
                    GestionCuenta gestion = new GestionCuenta();

                    boolean respuesta = gestion.iniciarSesion(email,contra);


                    if(respuesta){
                        dos.writeUTF("true");
                    }
                    else{
                        dos.writeUTF("false");
                    }
                }

                dos.close();
                in.close();
                clientSocket.close();

            }





        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

}
