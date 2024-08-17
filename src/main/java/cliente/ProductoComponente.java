package cliente;

import javax.swing.*;
import java.awt.*;

public class ProductoComponente extends JPanel {
    private JLabel label1;
    private JButton button1;
    private JLabel label2;
    private JButton button2;

    public ProductoComponente(){

        // Initialize components
        label1 = new JLabel("Label 1");
        button1 = new JButton("Button 1");
        label2 = new JLabel("Label 2");
        button2 = new JButton("Button 2");

        // Set layout manager for the panel
        this.setLayout(new FlowLayout());

        // Add components to the panel
        add(label1);
        add(button1);
        add(label2);
        add(button2);
    }
}
