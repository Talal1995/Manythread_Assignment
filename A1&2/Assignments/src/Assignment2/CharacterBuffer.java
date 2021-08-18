package Assignment2;

public class CharacterBuffer {
    private char bufferedChar; // shared resource
    private boolean hasCharacter = false;
    private JGUIAssignment2 gui;

    public CharacterBuffer(JGUIAssignment2 gui)
    {
        this.gui = gui;
    }


    public void clearBuffer(){
        bufferedChar = Character.MIN_VALUE;
        hasCharacter = false;
    }

    public void asyncPut(char c) {
        bufferedChar = c;
        hasCharacter = true;
    }

    public char asyncGet()
    {
        return bufferedChar;
    }
    public synchronized void put(char c){
        while(hasCharacter){
            try {
                gui.setTextWaitingFull();
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        bufferedChar = c;
        hasCharacter = true;
        notifyAll();
    }

    /*
    * Method for retriving a character from this buffert
     */

    public synchronized char get() {
        while (!hasCharacter){
           try {
               gui.setTextWaitingEmpty();
               wait();
           } catch (InterruptedException e) {
               break;
           }
        }

        hasCharacter = false;
        notifyAll();
        return bufferedChar;

    }
}

