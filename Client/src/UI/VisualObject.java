package UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VisualObject extends JComponent implements Runnable{

    private int width = 2;
    private int height = 2;
    private Color color;
    private static boolean count = true;

    private ArrayList<String[]> data;

    VisualObject(Color color, ArrayList<String[]> data){
        setSize(1000, 400);
        this.color = color;
        this.data = data;
        (new Thread(this)).start();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        draw(g2D);
        count=false;
    }

    private void draw(Graphics2D g2D){
        for (String[] s : data) {
            g2D.setBackground(color);
            g2D.drawOval(Integer.parseInt(s[3]), Integer.parseInt(s[4]), width, height);

        }
    }


   private void anim(){
       for(int i = 0; i<50; i++){
           try {
               Thread.sleep(100);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           width+=1;
           height+=1;
           repaint();
       }
   }

    @Override
    public void run() {
        try {
            //Thread.sleep(2000);
            if(count) anim();
            setForeground(color);
            while (true) {
                if(count)
                repaint();
                Thread.sleep(5000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }
}
