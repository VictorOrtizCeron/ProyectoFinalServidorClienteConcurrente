package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmacionVenta extends JFrame{
    private JButton cancelarButton;
    private JButton aceptarButton;
    private JPanel mainpanel;
    private JTextArea textPane1;
    private String email;
    private String restaurante;
    private String factura;
    private Float totalCosto;
    private boolean crearPedido;

    public Float getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(Float totalCosto) {
        this.totalCosto = totalCosto;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }



    public boolean isCrearPedido() {
        return crearPedido;
    }

    public void setCrearPedido(boolean crearPedido) {
        this.crearPedido = crearPedido;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ConfirmacionVenta(ProductoUI[] productoUIS){

        setContentPane(mainpanel);
        //setDefaultCloseOperation();
        setTitle("October Eats - Menu Principal");
        setSize(600, 600);
        setLocationRelativeTo(null);

        this.textPane1.setText(crearFactura(productoUIS));
        this.factura = crearFactura(productoUIS);
        setResizable(false);
        setVisible(true);

        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setCrearPedido(true);
                dispose();



            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCrearPedido(false);
                dispose();
            }
        });



    }
    public String crearFactura(ProductoUI[] productoUIS){
        String factura =
                "\t\tDesglose de costos\n" +
                "\tProducto-----Precio-----Costo\n\n";
        Float total = 0f;
        for(ProductoUI productoUI : productoUIS){
            if(productoUI.getCosto()!=0.0){
                factura += "\n\t"+productoUI.getNombre().getText()+"-----"+productoUI.getPrecio().getText()+"$-----"+productoUI.getCosto()+"$\n";
                total += productoUI.getCosto();
            }

        }
        this.totalCosto = total;

        factura+= "\n\t Total:"+ total+"$";

        return factura;
    }


}
