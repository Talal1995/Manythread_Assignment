package Assignment4;

/**
 * The modifier  thread checks  the  written  string(s) for a
 * find and replace operation  of  the  given substring at a position in
 * which the saved element (string) contains the search string, and if found,
 * it makes the modification at that position, changing the status of this position to Checked.
 */
public class Modifier extends Thread {
    private BoundedBuffer buffer;
    private int arrayLength;
    private boolean notify;

    public Modifier(BoundedBuffer buffer, int arrayLength, boolean notify){
        this.buffer = buffer;
        this.arrayLength = arrayLength;
        this.notify=notify;
    }

    public void run() {
    	modify();
    }

    /**
     * Modify data in the buffer. Performed for each sentence in the source text.
     */
    public void modify(){
        for(int i = 0; i < arrayLength; i++) {
            try {
				buffer.modifyData(notify);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
}