package cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Perfil extends JFrame {
    private JButton verMisPedidosEnButton;
    private JButton verMisPedidosCompletadosButton;
    private JButton cerrarSesiónButton;
    private JPanel perfilVentana;
    private JButton botonAtras;

    public Perfil() {
        setContentPane(perfilVentana);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("October Eats - Menu Principal");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setVisible(true);

        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MenuPrincipal mp = new MenuPrincipal();
                    mp.setVisible(true);
                    setVisible(false);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        cerrarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    InicioSesion login = new InicioSesion();
                    login.setVisible(true);
                    setVisible(false);

                }    catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}