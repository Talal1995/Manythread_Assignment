package Assignment2;

import java.util.Random;

public class Reader implements Runnable {
    private CharacterBuffer buffer; //shared data
    private String stringRead = "";
    private String realStringToRead;
    private Thread thread = null;
    private Boolean sync;
    private JGUIAssignment2 gui;
    private int length;

    public Reader(CharacterBuffer buffer, JGUIAssignment2 gui){
        this.buffer = buffer;
        this.gui = gui;
    }


    public void setLength(int length){
        this.length = length;
    }

    public void setRealStringToRead(String str) {
    realStringToRead = str;
    }

    public void start(boolean sync){
        this.sync = sync;
        if(thread ==null){
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop(){
        if(thread!=null){
            thread.interrupt();
            thread = null;
        }
    }

    public void run(){
        Random rnd = new Random();
        int pause;
        for(int i = 0; i<length; i++) {
            if(sync){
                char c = buffer.get();
                gui.setTextReading(c);
                stringRead += c;
            }else if(!sync){
                char c = buffer.asyncGet();
                gui.setTextReading(c);
                stringRead += c;
            }
            gui.setTextRec(stringRead);

            pause = rnd.nextInt(3000);
            try {
                Thread.sleep(pause);
            }catch(InterruptedException e){
                break;
            }
        }
        System.out.println("String read;" + stringRead);

        gui.setMatch(stringRead.equals((realStringToRead)));

        stringRead = "";
        thread = null;

    }
}
