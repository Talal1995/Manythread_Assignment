package Assignment1;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GraphicsThread extends Thread{
 //   Arrays used to drawPolygon
 //   private int x [] = {20, 50, 80};
 //   private int y [] = {80, 20, 80};
    private Random random;
    private boolean keepRunning;
    private int pause;
    private JPanel pnlRotate;

    public GraphicsThread(JPanel pnlRotate){
        random = new Random();
        keepRunning = true;
        pause = 500;
        this.pnlRotate = pnlRotate;
    }
    @Override
    public void run(){
        while (keepRunning){
            try {
                drawGraphics();
                Thread.sleep(pause);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void stopRotating(){
        keepRunning = false;
    }

    private void drawGraphics(){
        Graphics2D graphics2D = (Graphics2D) pnlRotate.getGraphics();
      //  graphics2D.drawPolygon(x, y, 3);

        graphics2D.setColor(Color.WHITE);
      //  graphics2D.fillPolygon(x, y, 3);
        graphics2D.fillRect(0, 0, pnlRotate.getWidth(), pnlRotate.getHeight());
        graphics2D.setColor(new Color(random.nextInt(246), random.nextInt(246), random.nextInt(246)));
        //  graphics2D.fillPolygon(x, y, 3);
        graphics2D.fillRect(random.nextInt(pnlRotate.getWidth() - 69), random.nextInt(pnlRotate.getHeight() - 69), 69, 69);
    }
}
