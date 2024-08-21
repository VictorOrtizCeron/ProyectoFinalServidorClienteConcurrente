package View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import Model.Pedido;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Perfil extends JFrame {

    private JButton cerrarSesionButton;
    private JPanel perfilVentana;
    private JButton botonAtras;
    private JLabel Nombre;
    private JLabel Correo;
    private JLabel Ciudad;
    private JScrollPane historialPane;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Perfil(String email) {
        this.email = email;
        setContentPane(perfilVentana);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("October Eats - Menu Principal");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setVisible(true);


        new Thread(()->{
            try{
                Socket socket = new Socket("localhost",8080);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
                Gson gson = new Gson();

                //get datos de perfil
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("RequestType", "getDatos");
                jsonObject.addProperty("emailCliente", getEmail());
                dos.writeUTF(jsonObject.toString());

                ReadContext respuestaDatos = JsonPath.parse(in.readUTF());
                Nombre.setText("Nombre del usuario: "+respuestaDatos.read("nombre"));
                Correo.setText("Correo electrónico: "+this.email);
                Ciudad.setText("Ciudad de residencia: "+respuestaDatos.read("ciudad"));

                //obtine historial
                jsonObject = new JsonObject();
                jsonObject.addProperty("RequestType", "getHistorial");
                jsonObject.addProperty("emailCliente", getEmail());

                dos.writeUTF(jsonObject.toString());

                String respuesta = in.readUTF();

                Pedido[] historial = gson.fromJson(respuesta, Pedido[].class);

                JPanel listPanel = new JPanel();
                listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
                int i = 1;
                for(Pedido ped : historial){
                    JPanel tempPanel = new JPanel();
                    JLabel label = new JLabel(i+". ");
                    JLabel label2 = new JLabel("ID de la orden: "+ped.getId_pedido());
                    JButton button = new JButton("Ver factura");
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null,ped.getFactura());
                        }
                    });
                    tempPanel.add(label);
                    tempPanel.add(label2);
                    tempPanel.add(button, BorderLayout.EAST);
                    tempPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                    listPanel.add(tempPanel);
                    i++;
                }
                historialPane.setViewportView(listPanel);
                JsonObject close = new JsonObject();
                close.addProperty("RequestType", "close");
                dos.writeUTF(close.toString());

            }catch( IOException ie){
                ie.printStackTrace();
            }

        }).start();



        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MenuPrincipal mp = new MenuPrincipal();
                    mp.setEmail(getEmail());
                    mp.setVisible(true);
                    dispose();

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    InicioSesion login = new InicioSesion();
                    login.setVisible(true);
                    dispose();



                }    catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}