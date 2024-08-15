package cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Carrito extends JFrame {
    private JList list1;
    private JPanel carritoPanel;
    private JButton confirmarButton;
    private JButton cancelarButton;


    public Carrito () {

        setContentPane(carritoPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("October Eats - Confirmacion de compra");
        setSize(1280, 720);
        setLocationRelativeTo(null);


        cancelarButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(null,"Compra cancelada correctamente.");
                    MenuPrincipal mp = new MenuPrincipal();
                    mp.setVisible(true);
                    setVisible(false);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //aqui se podria poner un JOPTIONPANE que de un mensaje de confirmacion que la compra se realizo con exito y deberia de poder verse en el perfil y devovlerse al menu principal

                try {
                    JOptionPane.showMessageDialog(null,"El pago fue realizado correctamente, revisa en la seccion PERFIL para ver el estado de entrega.");
                    MenuPrincipal mp = new MenuPrincipal();
                    mp.setVisible(true);
                    setVisible(false);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

}
