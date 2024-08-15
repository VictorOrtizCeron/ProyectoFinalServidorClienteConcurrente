package cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PedidosCompletados extends JFrame {
    private JList list1;
    private JButton menuPrincipalButton;
    private JPanel pedidosCompletadosPanel;

    public PedidosCompletados() {

        setContentPane(pedidosCompletadosPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("October Eats - Confirmacion de compra");
        setSize(1280, 720);
        setLocationRelativeTo(null);


        menuPrincipalButton.addActionListener(new ActionListener() {
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
    }
}
