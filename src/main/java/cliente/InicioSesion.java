package cliente;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import pojos.Cuenta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class InicioSesion extends JFrame {
    private JPanel MainPanel;
    private JTextField usuario;
    private JPasswordField password;
    private JButton botonIniciarSesion;
    private JLabel Titulo;
    private JLabel labelContrasena;
    private JLabel labelUsuario;
    private JButton botonRegistrar;

    public InicioSesion() {

        setContentPane(MainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("October Eats - Login");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setVisible(true);


        botonIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //agarra la info en los input fields cliente
                String email = usuario.getText();
                String contra = password.getText();
                String nombre = "";
                String ciudad = "";

                //enviar a servidor

                try {
                    Socket socket = new Socket("localhost", 8080);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    //escribir el gson
                    Gson gson = new Gson();

                    Cuenta cuenta = new Cuenta(nombre,email,ciudad,contra);

                    JsonElement jsonElement = gson.toJsonTree(cuenta);

                    jsonElement.getAsJsonObject().addProperty("RequestType", "inicioSesion");

                    String mensaje = gson.toJson(jsonElement);

                    dos.writeUTF(mensaje);

                    System.out.println(mensaje);

                    String respuesta = in.readUTF();

                    if(respuesta.equals("true")){


                        MenuPrincipal mp = new MenuPrincipal();
                        mp.setVisible(true);
                        setVisible(false);
                        dos.close();
                        in.close();
                        socket.close();
                    }else{

                        JOptionPane.showMessageDialog(null,"La contraseña o el correo electrónico son incorrectos" +
                                "\n Por favor ingrese credenciales correctas o cree una cuenta nueva.");
                    }




                } catch (IOException ex) {

                    throw new RuntimeException(ex);
                }

            }
        });

        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre = usuario.getText();
                String apellido = usuario.getText();
                String ciudad = usuario.getText();
                String email = usuario.getText();

                try {
                    RegistroCuenta du = new RegistroCuenta();
                    du.setVisible(true);
                    setVisible(false);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
