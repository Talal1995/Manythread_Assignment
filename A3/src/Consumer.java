/**
 * This class consumes items from the queue
 * @author abdulsamisahil
 */
public class Consumer extends Thread {
    private Storage queue;
    private Controller controller;
    private int totalNumberOfItems, totalWeight, totalVolume, type, items = 0, weight = 0, volume = 0;
    private boolean running=true, cont;

    /**
     * Creates a Consumer-object
     *
     * @param type the type
     * @param queue the queue of foodItems
     * @param controller the controller class
     * //@param semaphore the semaphore
     * @param totalNumberOfItems the number of item the consumer can take
     * @param totalWeight the weight the consumer can take
     * @param totalVolume the volume the consumer can take
     * @param cont if the consumer will continue when full
     */
    public Consumer(int type, Storage queue, Controller controller, int totalNumberOfItems, int totalWeight, int totalVolume, boolean cont) {
        this.queue = queue;
        this.controller=controller;
        this.totalNumberOfItems=totalNumberOfItems;
        this.totalWeight=totalWeight;
        this.totalVolume=totalVolume;
        this.type=type;
        this.cont=cont;
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    public synchronized void run() {
        while (running){
            if(items<totalNumberOfItems && weight<totalWeight && volume<totalVolume) {
                try{
//					if(queue.isEmpty()) {
//						setStatus("Idle");
//						wait();
//					}
                    FoodItem foodITEM = (FoodItem) queue.remove();
                    setItem(foodITEM);
                    items++;
                    weight+= foodITEM.getWeight();
                    volume+= foodITEM.getVolume();
                    Thread.sleep(1000);
                }catch (InterruptedException ex){
                    System.out.println("Consumer Read INTERRUPTED");
                }
            }
            else if(cont)
                empty();
            else
                stopThread();
        }
    }
    public void stopThread() {
        running=false;
        setStatus("Stopped");
    }
    /**
     * Empties the consumer
     */
    public void empty() {
        items=0;
        weight=0;
        volume=0;
        switch (type) {
            case 1:controller.emptyIcaText();break;
            case 2:controller.emptyCoopText();break;
            case 3:controller.emptyCityGrossText();break;
        }
    }
    /**
     * @param foodITEM sets the food item that is passed as an argument
     */
    private void setItem(FoodItem foodITEM) {
        switch(type) {
            case 1: controller.setIcaList(foodITEM.getName()); break;
            case 2: controller.setCoopList(foodITEM.getName()); break;
            case 3: controller.setCityGrossList(foodITEM.getName()); break;
        }
    }
    /**
     * @param status Sets the status
     */
    private void setStatus(String status) {
        switch(type) {
            case 1: controller.setIcaStatus(status); break;
            case 2: controller.setCoopStatus(status); break;
            case 3: controller.setCityGrossStatus(status); break;
        }
    }
}