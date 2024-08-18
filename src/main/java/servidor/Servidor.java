package servidor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import pojos.Cuenta;
import pojos.Producto;
import pojos.Restaurante;

import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Servidor extends Thread {

    private Socket clientSocket = null;
    private boolean isCorriendo = true;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Servidor(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {


        try {


            while (isCorriendo) {
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                Gson gson = new Gson();


                ReadContext ctx = JsonPath.parse(in.readUTF());


                String tipoRequest = ctx.read("RequestType");


                if (tipoRequest.equals("registroCuenta")) {

                    String nombre = ctx.read("nombre");
                    String contra = ctx.read("contra");
                    String ciudad = ctx.read("ciudad");
                    String email = ctx.read("email");


                    GestionCuenta gestion = new GestionCuenta();
                    boolean resultadoRegistro = gestion.registrarCuenta(nombre, contra, email, ciudad);
                    if (resultadoRegistro) {
                        dos.writeUTF("Registro exitoso");
                    } else {
                        dos.writeUTF("Registro fallido");
                    }

                } else if (tipoRequest.equals("inicioSesion")) {


                    String contra = ctx.read("contra");
                    String email = ctx.read("email");

                    GestionCuenta gestion = new GestionCuenta();

                    boolean cuentaIniciada = gestion.iniciarSesion(email, contra);


                    if (cuentaIniciada) {
                        dos.writeUTF("true");
                    } else {
                        dos.writeUTF("false");
                    }
                } else if (tipoRequest.equals("getRestaurantes")) {
                    GestionRestaurantes gestionRestaurantes = new GestionRestaurantes();
                    Restaurante[] restaurantes = gestionRestaurantes.getRestaurantes();

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("Restaurantes", gson.toJson(restaurantes));

                    String mensaje = jsonObject.toString();

                    dos.writeUTF(gson.toJson(restaurantes));

                } else if (tipoRequest.equals("getProductos")) {
                    GestionRestaurantes gestionRestaurantes = new GestionRestaurantes();
                    String restaurante = ctx.read("restaurante");


                    Producto[] productos = gestionRestaurantes.getProductos(restaurante);

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("Productos", gson.toJson(productos));


                    dos.writeUTF(gson.toJson(productos));


                } else if (tipoRequest.equals("crearPedido")) {
                    GestionPedidos gestionPedidos = new GestionPedidos();

                    String emailCliente = ctx.read("emailCliente");
                    String nombreRestaurante = ctx.read("restaurante");
                    double precioD = ctx.read("precio");
                    Float precio = Float.valueOf(Double.toString(precioD));
                    String factura = ctx.read("factura");
                    boolean resultado = gestionPedidos.crearPedido(emailCliente,nombreRestaurante,factura, precio);

                    dos.writeUTF(String.valueOf(resultado));

                }
                else if (tipoRequest.equals("actualizarPedido")) {
                    GestionPedidos gestionPedidos = new GestionPedidos();

                    String emailCliente = ctx.read("emailCliente");
                    String nombreRestaurante = ctx.read("restaurante");
                    double precioD = ctx.read("precio");
                    Float precio = Float.valueOf(Double.toString(precioD));
                    String factura = ctx.read("factura");
                    boolean resultado = gestionPedidos.actualizarPedido(emailCliente,nombreRestaurante,factura, precio);

                    dos.writeUTF(String.valueOf(resultado));

                } else if (tipoRequest.equals("close")) {
                    clientSocket = null;
                    isCorriendo = false;
                    dos.close();
                    in.close();
                }

            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

}
