package UI;

import javax.swing.*;
import java.awt.*;

public class InfoWindow extends JFrame {
    public InfoWindow(String info, String level){
        setSize(200, 200);
        JLabel text = new JLabel(info);
        if (level.equals("error")){
            text.setForeground(Color.RED);
        }else text.setForeground(Color.BLACK);

        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 2, 2, 2);

        text.setFont(Font.decode("ITALIC"));
        add(text, constraints);

        JButton ok = new JButton("Ok");
        ok.setFont(Font.decode("ITALIC"));
        ok.addActionListener((e -> {
            dispose();
        }));

        constraints.gridy +=2;
        constraints.insets.top = 15;
        add(ok, constraints);

        pack();
        setVisible(true);
    }
}
