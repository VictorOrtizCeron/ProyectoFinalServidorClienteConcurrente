package cliente;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import pojos.Restaurante;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public class MenuPrincipal extends JFrame {
    private JPanel menuPrincipal;
    private JButton carritoButton;
    private JButton agregarItemsSeleccionadosAlButton;
    private JButton perfilButton;
    private JScrollPane ScrollRestaurantes;
    private JList list1;

    public MenuPrincipal() {
        setContentPane(menuPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("October Eats - Menu Principal");
        setSize(1280, 720);
        setLocationRelativeTo(null);


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

                //menuPrincipal.updateUI();
                JsonObject close = new JsonObject();
                close.addProperty("RequestType", "close");
                dos.writeUTF(close.toString());
                setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();

            }

        }).start();





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



        carritoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Carrito carrito = new Carrito();
                    carrito.setVisible(true);
                    setVisible(false);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


}