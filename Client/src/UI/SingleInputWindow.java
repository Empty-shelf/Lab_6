package UI;

import javax.swing.*;
import java.awt.*;

public class SingleInputWindow extends JFrame {
    private String input_str;

    public String getString(){
        return input_str;
    }

    public SingleInputWindow() {
        setSize(200, 200);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JLabel label = new JLabel("Input argument:");
        JTextField input = new JTextField();
        JButton submit = new JButton("Submit");
        submit.addActionListener((e -> {
            input_str=input.getText().trim();
            dispose();
        }));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.insets = new Insets(4,4,4,4);
        add(label, c);
        c.gridy+=2; c.fill = GridBagConstraints.HORIZONTAL;
        add(input, c);
        c.gridy+=2;
        add(submit, c);

        setVisible(true);

    }
}
