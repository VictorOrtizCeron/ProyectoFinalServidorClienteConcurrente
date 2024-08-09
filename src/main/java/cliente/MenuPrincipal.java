package cliente;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends  JFrame {
    private JPanel menuPrincipal;
    private JTabbedPane tabbedPane1;
    private JButton carritoButton;
    private JButton agregarItemsSeleccionadosAlButton;
    private JButton perfilButton;

    public  MenuPrincipal(){
    setContentPane(menuPrincipal);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("October Eats - Menu Principal");
    setSize(1280,720);
    setLocationRelativeTo(null);
    setVisible(true);

            //Envia nombre de restaurante seleccionado a backend
            //backend retorna los productos del restaurante
            //cliente formatea los productos para que se vean bien


        perfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                Perfil perfil = new Perfil();
                perfil.setVisible(true);
                setVisible(false);

            }    catch (Exception ex) {
                throw new RuntimeException(ex);
                }
            }
        });
    }
}