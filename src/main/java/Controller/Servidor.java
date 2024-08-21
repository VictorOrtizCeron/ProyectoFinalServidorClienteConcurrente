package Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import Model.Cuenta;
import Model.Pedido;
import Model.Producto;
import Model.Restaurante;

import java.io.*;
import java.net.*;

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


                }else if (tipoRequest.equals("getHistorial")) {
                    GestionPedidos gestionPedidos = new GestionPedidos();

                    String emailCliente = ctx.read("emailCliente");

                    Pedido[] historial = gestionPedidos.getHistorial(emailCliente);

                    dos.writeUTF(gson.toJson(historial));


                } else if(tipoRequest.equals("getDatos")){
                    GestionCuenta gestionCuenta = new GestionCuenta();
                    String emailCliente = ctx.read("emailCliente");
                    Cuenta cuenta = gestionCuenta.getInfoCuenta(emailCliente);
                    String mensaje = gson.toJson(cuenta , Cuenta.class);
                    dos.writeUTF(mensaje);

                }else if (tipoRequest.equals("close")) {
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
