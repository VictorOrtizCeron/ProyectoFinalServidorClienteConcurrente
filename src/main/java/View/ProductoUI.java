package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductoUI extends JPanel {

    private JLabel nombre;
    private JButton descripcionButton;
    private JLabel precio;
    private JButton restar;
    private JButton sumar;
    private JLabel cantidad;
    private JLabel total;
    private JScrollPane scrollText;
    private int cantidadInt ;

    public JLabel getCantidad() {
        return cantidad;
    }

    public JLabel getNombre() {
        return nombre;
    }

    public JLabel getPrecio() {
        return precio;
    }

    public void setPrecio(JLabel precio) {
        this.precio = precio;
    }

    public void setNombre(JLabel nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(JLabel cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadInt() {
        return cantidadInt;
    }

    public void setCantidadInt(int cantidadInt) {
        this.cantidadInt = cantidadInt;
    }

    public JLabel getTotal() {
        return total;
    }

    public void setTotal(JLabel total) {
        this.total = total;
    }

    public ProductoUI(String nombre, String desc, String precio){
       this.cantidadInt = 0;
        this.nombre.setText(nombre);
        this.precio.setText(precio+"$");
        this.cantidad.setText("0");
        this.total.setText("0.0");

        JPanel subpanel = new JPanel();
        subpanel.setBorder(BorderFactory.createLineBorder(Color.black,1));
        subpanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        setAlignmentY(BoxLayout.Y_AXIS);


        subpanel.add(this.nombre);
        subpanel.add(this.descripcionButton);
        subpanel.add(this.precio);
        subpanel.add(this.restar);
        subpanel.add(this.cantidad);
        subpanel.add(this.sumar);
        subpanel.add(this.total);

        add(subpanel);

        setBackground(Color.white);





        descripcionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,desc);
            }
        });
        sumar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cantidadInt += 1;
                getCantidad().setText(Integer.toString(cantidadInt));

                Float total = Float.parseFloat(precio)*cantidadInt;
                String totalstr = String.format("%.2f", total);
                getTotal().setText(totalstr);
            }
        });
        restar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(getCantidad().getText()) == 0){
                    //do nothing
                }
                else{
                    cantidadInt += -1;
                    getCantidad().setText(Integer.toString(cantidadInt));
                    Float total = Float.parseFloat(precio)*cantidadInt;
                    String totalstr = String.format("%.2f", total);
                    getTotal().setText(totalstr);
                }

            }
        });

    }
    public Float getCosto(){
        Float totalFinal = Float.parseFloat(getTotal().getText());
        return totalFinal;
    }
}
