package Assignment1;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class DisplayThread  extends Thread {
    private String displayText;
    private Random random;
    private int x, y;
 //   private static GUIAssignment1 gui = new GUIAssignment1();
    private boolean keepRunning;
    private int pause;
    private JPanel panel;
    private JLabel moveString;
    public DisplayThread(JPanel panel, JLabel lbl){
        displayText = "DisplayThread";
        this.moveString = lbl;
        random = new Random();
        keepRunning = true;
        pause = 1000;
        this.panel = panel;
    }
    @Override
    public void run() {


           while (keepRunning){
               try {
                   setMoveString();
                  // displayString();
                   try {
                       Thread.sleep(pause);
                   } catch (InterruptedException ex) {
                           ex.printStackTrace();
                   }
               }catch (Exception ex){
                   ex.printStackTrace();
               }


           }

    }
    public void stopDisplay(){
        keepRunning = false;
    }
    // drawString method
    private void displayString(){
        Graphics2D g = (Graphics2D) panel.getGraphics();
        Font font = new Font("Arial", Font.BOLD+Font.PLAIN, 16);
        g.setFont(font);
        x = random.nextInt(panel.getWidth() - 49);
        y = random.nextInt(panel.getHeight() - 49);
        g.setColor(new Color(random.nextInt(246), random.nextInt(246), random.nextInt(246)));
        g.drawString(displayText, x, y);
    }
    private void setMoveString(){
        int wi = 100;
        int hi = 30;
        x = random.nextInt(panel.getWidth() - 49);
        y = random.nextInt(panel.getHeight() - 49);
        moveString.setBounds(x, y, wi, hi);
        panel.add(moveString);
    }

}
