package cliente;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import pojos.Cuenta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RegistroCuenta extends JFrame {
    private JLabel labelIngresarDatos;
    private JTextField nombre;
    private JTextField ciudad;
    private JTextField apellido;
    private JButton iniciarButton;
    private JPanel panelRegistrar;
    private JTextField email;
    private JTextField password;
    private JButton botonAtras;

    public RegistroCuenta() {
        setContentPane(panelRegistrar);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("October Eats - Registrar Usuario");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setVisible(true);

        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuarioNuevo = nombre.getText();
                String apellidoUsuarioNuevo = apellido.getText();
                String ciudadUsuarioNuevo = ciudad.getText();
                String emailUsuarioNuevo = email.getText();

                //validar que no sean nulos o vacios
                //enviar al backend con sockets
                //escribir el gson

                try {
                    Socket socket = new Socket("localhost", 8080);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    Gson gson = new Gson();
                    Cuenta cuentaARegistrar = new Cuenta(nombreUsuarioNuevo,apellidoUsuarioNuevo,ciudadUsuarioNuevo,emailUsuarioNuevo,contraUsuarioNuevo);

                    String mensaje = gson.toJson(cuentaARegistrar, Cuenta.class);


                    JsonElement jsonElement = gson.toJsonTree(cuentaARegistrar);
                    jsonElement.getAsJsonObject().addProperty("RequestType", "registroCuenta");
                    mensaje = gson.toJson(jsonElement);
                    dos.writeUTF(mensaje);

                    dos.close();
                    socket.close();


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }



                try {
                    // Aquí va el código para manejar la conexión o enviar datos
                    MenuPrincipal mp = new MenuPrincipal();
                    mp.setVisible(true);
                    setVisible(false);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InicioSesion panel1 = new InicioSesion();
                panel1.setVisible(true);
                setVisible(false);
            }
        });
    }
}




