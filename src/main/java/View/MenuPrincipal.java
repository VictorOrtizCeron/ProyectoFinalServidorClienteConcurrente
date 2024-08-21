package View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import Model.Producto;
import Model.Restaurante;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class MenuPrincipal extends JFrame {
    private JPanel menuPrincipal;
    private JButton salirButton;
    private JButton realizarCompraButton;
    private JButton perfilButton;
    private JScrollPane ScrollRestaurantes;
    private JList listaRestaurantes;
    private JPanel menu;
    private JProgressBar statusEnvio;
    private ProductoUI[] productosUIS;
    private String email;
    private boolean isPedido;
    private ConfirmacionVenta confirmacionVenta;

    public boolean isPedido() {
        return isPedido;
    }

    public void setPedido(boolean pedido) {
        isPedido = pedido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProductoUI[] getProductosUIS() {
        return productosUIS;
    }

    public void setProductosUIS(ProductoUI[] productosUIS) {
        this.productosUIS = productosUIS;
    }

    public ConfirmacionVenta getConfirmacionVenta() {
        return confirmacionVenta;
    }

    public void setConfirmacionVenta(ConfirmacionVenta confirmacionVenta) {
        this.confirmacionVenta = confirmacionVenta;
    }

    public MenuPrincipal() {

        setContentPane(menuPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("October Eats - Menu Principal");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        DefaultListModel listModel = new DefaultListModel();


        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8080);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
                //escribir el gson
                Gson gson = new Gson();

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("RequestType", "getRestaurantes");
                String mensaje = gson.toJson(jsonObject);
                dos.writeUTF(mensaje);


                String restInput = in.readUTF();


                Restaurante[] restaurantes = gson.fromJson(restInput, Restaurante[].class);


                for (int i = 0; i < restaurantes.length; i++) {
                    listModel.addElement(restaurantes[i].getNombre());

                }

                SwingUtilities.invokeLater(() -> {
                    listaRestaurantes.setModel(listModel);
                    listaRestaurantes.repaint();

                });


                ScrollRestaurantes.setViewportView(listaRestaurantes);


                JsonObject close = new JsonObject();
                close.addProperty("RequestType", "close");
                dos.writeUTF(close.toString());


            } catch (IOException e) {
                e.printStackTrace();

            }

        }).start();


        listaRestaurantes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                new Thread(() -> {

                    Socket socket = null;
                    try {
                        socket = new Socket("localhost", 8080);

                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        Gson gson = new Gson();

                        //obtiene nombre del restaurante que está clickeado
                        JList source = (JList) e.getSource();
                        String restaurante = (String) source.getSelectedValue();


                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("RequestType", "getProductos");
                        jsonObject.addProperty("restaurante", restaurante);

                        String mensaje = jsonObject.toString();
                        dos.writeUTF(mensaje);


                        String response = in.readUTF();

                        Producto[] productos = gson.fromJson(response, Producto[].class);

                        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
                        menu.removeAll();
                        menu.repaint();


                        ProductoUI[] productosUIS = new ProductoUI[productos.length];

                        int i = 0;
                        for (Producto producto : productos) {

                            ProductoUI temp = new ProductoUI(producto.getNombre(),
                                    producto.getDescripcion(),
                                    Float.toString(producto.getPrecio()));
                            productosUIS[i] = temp;
                            menu.add(temp);
                            i++;
                        }
                        setProductosUIS(productosUIS);
                        setVisible(true);
                        JsonObject close = new JsonObject();
                        close.addProperty("RequestType", "close");
                        dos.writeUTF(close.toString());

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }).start();


            }
        });

        perfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Perfil perfil = new Perfil(getEmail());
                    perfil.setVisible(true);
                    dispose();

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        realizarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Float total = 0.0F;
                for (ProductoUI productoUI : getProductosUIS()) {
                    total += productoUI.getCosto();

                }

                if (total == 0.0) {
                    JOptionPane.showMessageDialog(null, "La selección de productos está vacía");
                } else {
                    ConfirmacionVenta confirmacion = new ConfirmacionVenta(getProductosUIS());
                    confirmacion.setEmail(getEmail());
                    confirmacion.setRestaurante(listaRestaurantes.getSelectedValue().toString());
                    setConfirmacionVenta(confirmacion);

                }


            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        new Thread(() -> {


            while (true) {

                try {
                    isPedido = getConfirmacionVenta().isCrearPedido();

                    if (isPedido) {

                        repaint();

                        Socket socket = null;
                        try {

                            socket = new Socket("localhost", 8080);
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                            DataInputStream in = new DataInputStream(socket.getInputStream());
                            Gson gson = new Gson();


                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("RequestType", "crearPedido");
                            jsonObject.addProperty("emailCliente", email);
                            jsonObject.addProperty("restaurante", listaRestaurantes.getSelectedValue().toString());
                            jsonObject.addProperty("factura", getConfirmacionVenta().getFactura());
                            jsonObject.addProperty("precio", getConfirmacionVenta().getTotalCosto());

                            dos.writeUTF(jsonObject.toString());
                            String respuesta = in.readUTF();

                            if (respuesta.equals("true")) {
                                new Thread(() -> {
                                    int i = 0;
                                    while (i < 100) {
                                        statusEnvio.setValue(i);
                                        statusEnvio.setString(String.valueOf(i) + "%");

                                        i += 5;
                                        if (i == 50) {
                                            JOptionPane.showMessageDialog(null, "Su pedido fue recogido para ser entregado!");
                                        }
                                        try {

                                            sleep(750);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    statusEnvio.setValue(0);
                                    statusEnvio.setString(String.valueOf(0) + "%");


                                    try {
                                        Socket socketPedido = new Socket("localhost", 8080);
                                        DataOutputStream dosPedido = new DataOutputStream(socketPedido.getOutputStream());

                                        DataInputStream inPedido = new DataInputStream(socketPedido.getInputStream());
                                        Gson gsonPedido = new Gson();


                                        JsonObject jsonObjectPedido = new JsonObject();
                                        jsonObjectPedido.addProperty("RequestType", "actualizarPedido");
                                        jsonObjectPedido.addProperty("emailCliente", email);
                                        jsonObjectPedido.addProperty("restaurante", listaRestaurantes.getSelectedValue().toString());
                                        jsonObjectPedido.addProperty("factura", getConfirmacionVenta().getFactura());
                                        jsonObjectPedido.addProperty("precio", getConfirmacionVenta().getTotalCosto());
                                        dosPedido.writeUTF(jsonObjectPedido.toString());
                                        JOptionPane.showMessageDialog(null, "Su pedido ha sido entregado!");

                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }


                                }).start();


                            }

                            JsonObject close = new JsonObject();
                            close.addProperty("RequestType", "close");
                            dos.writeUTF(close.toString());
                            dos.close();
                            in.close();
                            socket.close();

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        getConfirmacionVenta().setCrearPedido(false);
                        setPedido(false);
                    }
                } catch (NullPointerException e) {
                    //
                }

                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();


    }


}