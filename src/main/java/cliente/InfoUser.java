package cliente;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoUser extends JFrame {
    private JLabel labelIngresarDatos;
    private JTextField nombre;
    private JTextField ciudad;
    private JTextField apellido;
    private JButton iniciarButton;
    private JPanel panelRegistrar;
    private JTextField email;
    private JTextField password;
    private JButton botonAtras;

    public InfoUser() {
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
                LoginForm panel1 = new LoginForm();
                panel1.setVisible(true);
                setVisible(false);
            }
        });
    }
}




