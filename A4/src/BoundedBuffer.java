package Assignment4;

import javax.swing.*;

/**
 * BoundedBuffer class that through a producer/consumer adds/removes string items.
 * @author abdulsamisahil
 */
public class BoundedBuffer {
	private String[] elements;
	private Status[] status;
	private int max, writePos, readPos, findPos;
	private String findString, replaceString;

	/**
	 * Constructs BoundedBuffer
	 * @param max max
	 * @param findString the founded strings
	 * @param replaceString are replaced with
	 * @param writePos replaces the following index
	 * @param readPos reads from the index
	 * @param findPos finds the index of the string
	 */
	public BoundedBuffer(int max, String findString, String replaceString, int writePos, int readPos, int findPos) {
		this.writePos = writePos;
		this.readPos = readPos;
		this.findPos = findPos;
		this.max = max;
		elements = new String[ max ];
		status = new Status[max];
		this.findString=findString;
		this.replaceString=replaceString;
		init();
	}

	private synchronized void init() {
		for(int i = 0;i < max; i++){
			status[i]= Status.Empty;
		}
		notifyAll();
	}

	/**
	 * When a producer is entered to the CS, other producers are on hold through Mutual Exclusion wait();
	 * @param data adds data the elements array
	 * @throws InterruptedException throws the exception
	 */
	public synchronized void add( String data ) throws InterruptedException {
		while(status[writePos]!= Status.Empty) {
			wait();
		}
		elements[writePos] = data;
		status[writePos] = Status.New;
		writePos = (writePos+1) % max;
		notify(); // With notify only one, could be a condition, goes to a thread that will be perhaps stuck,
        // In case no other threads are waiting for the monitor,
	}

	/**
	 * When a consumer is entering the CS, checks not to consume when there nothing to consume,
	 * as well as other consumers are on hold until the consumer that is dealing is finished with its job
	 * @return s the element that is being consumed
	 * @throws InterruptedException throws the exception
	 */
	public synchronized String remove() throws InterruptedException {
		while(status[readPos]!= Status.Checked){
			wait();
		}
		String elem = elements[readPos];
		status[readPos]= Status.Empty;
		readPos = (readPos+1) % max;
		notifyAll();
		return elem;
	}

	public synchronized void modifyData(boolean notify) throws InterruptedException {
		boolean replace = true;
		while(status[findPos]!= Status.New) {
			wait();
		}
		String elem = elements[findPos];
		if(notify) {
			replace = question(elem);
		}		
		if(elem.contains(findString) || replace) {
			elem = elem.replace(findString, replaceString);
		}
		elements[findPos]=elem;
		status[findPos]= Status.Checked;
		findPos=(findPos+1) % max;
		notifyAll();
	}
	public boolean question(String elem) {
		int dialogResult  = JOptionPane.showConfirmDialog(null, "Do you want to replace "+elem+" with "+replaceString+"?","Question", JOptionPane.YES_NO_OPTION);
		if(dialogResult== JOptionPane.NO_OPTION)
			return false;
		return true;
	}
}
