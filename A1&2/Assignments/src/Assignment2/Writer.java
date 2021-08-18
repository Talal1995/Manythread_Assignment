package Assignment2;


import java.util.Random;

public class Writer implements Runnable {
    private CharacterBuffer buffer; //shared data
    private String stringToWrite;
    private String stringWritten = "";
    private Thread thread = null;
    private boolean sync;
    JGUIAssignment2 gui;


    public Writer(CharacterBuffer buffer, JGUIAssignment2 gui){
        this.buffer = buffer;
        this.gui = gui;
    }

    public void setString(String str)
    {
        stringToWrite = str;
    }

    public void start(boolean sync){
        this.sync = sync;
        if(thread == null){
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
    @Override
    public void run() {
        Random rnd = new Random();
        int pause;

        for(int i = 0; i<stringToWrite.length(); i++){
            char c = stringToWrite.charAt(i);

            if(sync) {
                gui.setTextWriting(c);
                buffer.put(c);
                stringWritten += c;

            }else if(!sync){
                gui.setTextWriting(c);
                buffer.asyncPut(c);
                stringWritten += c;
            }
            gui.setTextTransmitted(stringWritten);

            pause = rnd.nextInt(3000);
            try {
                Thread.sleep(pause);
            }catch (InterruptedException e){
                break;
            }
        }
        buffer.clearBuffer();
        System.out.println("String written " + stringWritten);
        stringWritten = "";
        thread = null;


    }

}