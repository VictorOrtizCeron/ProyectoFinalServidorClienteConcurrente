package cliente;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MenuPrincipal extends  JFrame {
    private JComboBox DropDownMenu;
    private JCheckBox hamburguesaCheckBox;
    private JCheckBox pizzaCheckBox;
    private JCheckBox papasCheckBox;
    private JCheckBox cocaColaCheckBox;
    private JCheckBox canadaDryCheckBox;
    private JCheckBox téFrioCheckBox;
    private JButton agregarAlCarritoButton;
    private JPanel menuPrincipal;

    public  MenuPrincipal(){
    setContentPane(menuPrincipal);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("October Eats - Menu Principal");
    setSize(1280,720);
    setLocationRelativeTo(null);
    setVisible(true);

    DropDownMenu.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(DropDownMenu.getSelectedItem());
            //hamburguesaCheckBox.setText(Objects.requireNonNull(DropDownMenu.getSelectedItem()).toString());
            //Envia nombre de restaurante seleccionado a backend
            //backend retorna los productos del restaurante
            //cliente formatea los productos para que se vean bien
        }
    });

    }
}