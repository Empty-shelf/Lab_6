package UI;

import Common.CommandShell;
import Connection.Manager;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(String login, Manager manager){
        setSize(1100, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        TablePanel panel = new TablePanel(manager, login);
        panel.init();
        add(panel, BorderLayout.SOUTH);
        VisualWindow v = new VisualWindow();
        add(v, BorderLayout.CENTER);

        setVisible(true);

    }
}
