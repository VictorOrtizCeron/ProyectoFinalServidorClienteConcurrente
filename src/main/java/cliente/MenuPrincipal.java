package cliente;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import pojos.Producto;
import pojos.Restaurante;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MenuPrincipal extends JFrame {
    private JPanel menuPrincipal;
    private JButton carritoButton;
    private JButton agregarItemsSeleccionadosAlButton;
    private JButton perfilButton;
    private JScrollPane ScrollRestaurantes;
    private JList list1;
    private JPanel menu;

    public MenuPrincipal() {
        setContentPane(menuPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("October Eats - Menu Principal");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        DefaultListModel listModel = new DefaultListModel();






        new Thread(()-> {
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



                Restaurante[] restaurantes  = gson.fromJson(restInput,Restaurante[].class);



                for(int i = 0 ; i<restaurantes.length; i++){
                    listModel.addElement(restaurantes[i].getNombre());
                    System.out.println(restaurantes[i].getNombre());
                }


                list1.setModel(listModel);
                list1.setSelectedIndex(0);
                list1.setVisibleRowCount(restaurantes.length);
                ScrollRestaurantes.add(list1);




                JsonObject close = new JsonObject();
                close.addProperty("RequestType", "close");
                dos.writeUTF(close.toString());

            } catch (IOException e) {
                e.printStackTrace();

            }

        }).start();



        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                new Thread(()-> {

                    Socket socket = null;
                    try {
                        socket = new Socket("localhost", 8080);

                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        Gson gson = new Gson();
                        JList source = (JList) e.getSource();
                        String restaurante = (String) source.getSelectedValue();

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("RequestType", "getProductos");
                        jsonObject.addProperty("restaurante", restaurante);

                        String mensaje = jsonObject.toString();
                        dos.writeUTF(mensaje);



                        String response = in.readUTF();

                        Producto[] productos  = gson.fromJson(response,Producto[].class);

                        menu.setLayout(new BoxLayout(menu,BoxLayout.Y_AXIS));
                        menu.removeAll();
                        menu.repaint();
                        for (Producto producto:productos){
                            //extraer y formatear
                            for(int i = 0 ; i<5 ; i++){
                                String desc = producto.getDescripcion().substring(0,50);
                                menu.add(new component(producto.getNombre(),
                                        producto.getDescripcion(),
                                        Float.toString(producto.getPrecio())));
                            }


                        }

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
                    Perfil perfil = new Perfil();
                    perfil.setVisible(true);
                    setVisible(false);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }


}