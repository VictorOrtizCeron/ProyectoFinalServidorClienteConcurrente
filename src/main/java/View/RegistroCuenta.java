package View;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import Model.Cuenta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
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

                String ciudadUsuarioNuevo = ciudad.getText();
                String emailUsuarioNuevo = email.getText();
                String contraUsuarioNuevo = password.getText();




                try {
                    Socket socket = new Socket("localhost", 8080);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    Gson gson = new Gson();
                    Cuenta cuentaARegistrar = new Cuenta(nombreUsuarioNuevo,ciudadUsuarioNuevo,emailUsuarioNuevo,contraUsuarioNuevo);

                    String mensaje = gson.toJson(cuentaARegistrar, Cuenta.class);

                    JsonElement jsonElement = gson.toJsonTree(cuentaARegistrar);
                    jsonElement.getAsJsonObject().addProperty("RequestType", "registroCuenta");
                    mensaje = gson.toJson(jsonElement);
                    dos.writeUTF(mensaje);

                    JOptionPane.showMessageDialog(null,in.readUTF());
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("RequestType","close");
                    dos.writeUTF(jsonObject.toString());

                    in.close();
                    dos.close();
                    socket.close();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    // Aquí va el código para manejar la conexión o enviar datos
                    MenuPrincipal mp = new MenuPrincipal();
                    mp.setEmail(emailUsuarioNuevo);
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

                try {
                InicioSesion panel1 = new InicioSesion();
                panel1.setVisible(true);
                setVisible(false);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

    }
}




