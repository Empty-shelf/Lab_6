package UI;

import javax.swing.*;
import java.awt.*;

public class MultiInputWindow extends JFrame {
    private JButton submit;

    private JTextField distance = new JTextField();
    private JTextField name = new JTextField();
    private JTextField coord_x = new JTextField();
    private JTextField coord_y = new JTextField();
    private JTextField loc_from = new JTextField();
    private JTextField from_x = new JTextField();
    private JTextField from_y = new JTextField();
    private JTextField loc_to = new JTextField();
    private JTextField to_x = new JTextField();
    private JTextField to_y = new JTextField();

    public String getName() {return name.getText();}
    public String getDistance(){return distance.getText();}
    public String getCoord_x(){return coord_x.getText();}
    public String getCoord_y(){return coord_y.getText();}
    public String getLoc_from(){return loc_from.getText();}
    public String getFrom_x(){return from_x.getText();}
    public String getFrom_y(){return from_y.getText();}
    public String getLoc_to(){return loc_to.getText();}
    public String getTo_x(){return to_x.getText();}
    public String getTo_y(){return to_y.getText();}


    public MultiInputWindow() {
        submit = new JButton("Submit");
        submit.addActionListener((e -> {
            setVisible(false);
        }));

        setSize(1000, 70);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 10));

        JLabel label = new JLabel("Input arguments:");
        JButton submit = new JButton("Submit");
        submit.addActionListener((e -> dispose()));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2,2,2,2);

        add(submit, c);

        c.gridy+=2;

        add(to_y, 0); add(to_x, 1); add(loc_to, 2); add(from_y, 3);
        add(from_x, 4); add(loc_from, 5); add(coord_y, 6); add(coord_x, 7);
        add(name, 8); add(distance, 9);

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.CENTER;

        add(new JLabel("to_y"), 0); add(new JLabel("to_x"), 1);
        add(new JLabel("loc_to"), 2); add(new JLabel("from_y"), 3);
        add(new JLabel("from_x"), 4); add(new JLabel("loc_from"), 5);
        add(new JLabel("coord_y"), 6); add(new JLabel("coord_x"), 7);
        add(new JLabel("name"), 8); add(new JLabel("distance"), 9);


        c.gridy+=2;
        add(label, c);

        setVisible(true);
    }
}
