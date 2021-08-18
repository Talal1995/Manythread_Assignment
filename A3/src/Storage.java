import java.util.concurrent.Semaphore;

/**
 * This is the storage class that responses to Producer and Consumer,
 * @author abdulsamisahil
 * @since 2020-05-14
 */
public class Storage {
    private Object[] elements;
    private int size = 0;
    private Semaphore consumerSemaphore = new Semaphore(0);
    private Semaphore producerSemaphore = new Semaphore(50);
    private Semaphore mutex = new Semaphore(1);

    public Storage(int capacity) {
        elements = new Object[ capacity ];
    }

    /**
     * The method is called by one producer at a time, mutexSemaphore
     * @param elem is added by one producer at a time.
     * @throws InterruptedException
     */
    public void add( Object elem ) throws InterruptedException {
        mutex.acquire();
        producerSemaphore.acquire();
        System.out.println("Producer semaphore is working and is acquired! ");
        elements[ size++ ] = elem;
        consumerSemaphore.release();
        System.out.println("Consumer semaphore is released!");
        mutex.release();
    }

    public Object remove() throws InterruptedException {
        mutex.acquire();
        consumerSemaphore.acquire();
        System.out.println("Consumer semaphore is busy and is acquired! ");
        Object value = elements[ 0 ];
        size--;
        for(int i=1; i<size; i++) {
            elements[i-1] = elements[i];
        }
        elements[size] = null;
        producerSemaphore.release();
        System.out.println("Consumer semaphore is released! ");
        mutex.release();
        return value;
    }

    public Object element() {
        if( size==0 ) {
            throw new QueueException("peek: Queue is empty");
        }
        return elements[ 0 ];
    }

    public boolean isEmpty() {
        return (size==0);
    }

    public boolean isFull() {
        return (size==elements.length);
    }

    public int size() {
        return size;
    }
}
