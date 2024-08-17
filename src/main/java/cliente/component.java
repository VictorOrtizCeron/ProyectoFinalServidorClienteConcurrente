package cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class component extends JPanel {

    private JLabel nombre;
    private JButton descripcionButton;
    private JLabel precio;
    private JButton restar;
    private JButton sumar;
    private JLabel cantidad;
    private JLabel total;
    private JScrollPane scrollText;

    public component(String nombre,String desc,String precio){
        this.nombre.setText(nombre);
        this.precio.setText(precio);


        descripcionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,desc);
            }
        });

        add(this.nombre);
        add(this.descripcionButton);
        add(this.precio);
        add(this.restar);
        add(this.cantidad);
        add(this.sumar);
        add(this.total);

    }

}
