package cliente;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class LoginForm extends JFrame {
    private JPanel MainPanel;
    private JTextField usuario;
    private JPasswordField password;
    private JButton botonIniciarSesion;
    private JLabel Titulo;
    private JLabel labelContrasena;
    private JLabel labelUsuario;
    private JButton botonRegistrar;

    public LoginForm() {

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
                String nombre = usuario.getText();
                String contra = password.getText();

                //enviar a servidor

                try {
                    Socket socket = new Socket("localhost", 8080);
                    DataOutputStream mensaje = new DataOutputStream(socket.getOutputStream());
                    //escribir el gson
                    Gson gson = new Gson();
                    Persona persona = new Persona(nombre, contra);
                    mensaje.writeUTF(gson.toJson(persona));
                    mensaje.close();
                    socket.close();
                    MenuPrincipal mp = new MenuPrincipal();
                    mp.setVisible(true);
                    setVisible(false);


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                //valida que sean validos servidor
                //retorna exito o fallo servidor
                //despliega mensaje de respuesta cliente

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
                    InfoUser du = new InfoUser();
                    du.setVisible(true);
                    setVisible(false);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
