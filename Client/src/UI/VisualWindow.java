package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class VisualWindow extends JPanel {
/*
    private ArrayList<Color> colors = new ArrayList<>();
    //хештабл по цвету !!! и логин ! )))
    private void loadColors(){
        colors.add(Color.BLUE);
        colors.add(Color.ORANGE);
        colors.add(Color.RED);
        colors.add(Color.CYAN);
        colors.add(Color.GREEN);
        colors.add(Color.PINK);
        colors.add(Color.MAGENTA);
        colors.add(Color.YELLOW);
    }


 */
    ArrayList<String[]> t = new ArrayList<>();


    VisualWindow() {

        t.add(new String[]{ "1", "1", "2", "77", "90"});
        t.add(new String[]{"1", "1", "2", "6", "9"});
        t.add(new String[]{"1", "1", "2", "405", "300"});


        setSize(800, 300);

        //RoutesTableModel.getData();

        VisualObject objects = new VisualObject(Color.RED, t);
        add(objects);

        setVisible(true);
    }

}
