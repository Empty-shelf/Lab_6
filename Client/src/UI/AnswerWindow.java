package UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AnswerWindow extends JFrame{
    public AnswerWindow(ArrayList<String> deque){
        setSize(600, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.WEST;
        for(String s : deque){
            add(new JLabel(s), c);
            c.gridy+=2;
        }
        pack();
        setVisible(true);
    }
}
