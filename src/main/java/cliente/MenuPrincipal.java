package cliente;
import javax.swing.*;

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
}
}