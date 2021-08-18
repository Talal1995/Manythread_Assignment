package Assignment1;


public class MainController {
    private GUIAssignment1 ui;
    public MainController(){
        ui = new GUIAssignment1();
        Thread thread = new Thread(ui);
        thread.start();
    }
    public static void main(String[] args) {
       new MainController();
    }
}
