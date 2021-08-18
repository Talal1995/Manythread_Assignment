import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class
 * @author abdulsamisahil
 * @since 2020-05-27
 */
public class Server {

	private ArrayList<ClientThread> clientList = new ArrayList<ClientThread>();
	private GUI gui;
	private boolean keepRunning;

	/**
	 * Constructs a ServerGUI object
	 * @param serverGUI the GUI for the server
	 * @throws IOException throws ex
	 */
	public Server(GUI serverGUI) throws IOException {
		this.gui = serverGUI;
		gui.setServer(this);
		ServerSocket serverSocket = new ServerSocket(3490);
		gui.setMessage("Waiting for connections...");
		keepRunning = true;
		//creating a pool of 7 threads, fixed size
		//ExecutorService executor = Executors.newFixedThreadPool(7);

		//creating a pool of threads that generates threads on demand
		ExecutorService executor = Executors.newCachedThreadPool();
		while(keepRunning) {
			Socket socket = serverSocket.accept();
			executor.execute(new ClientThread(socket));
			if(!keepRunning) {
				executor.shutdown();
			}
		}
	}
	/**
	 * Broadcast message to all clients that are connected
	 * @param message the message sent from the server
	 */
	public synchronized void handleMessages(String message) {
		for(int i = 0; i < clientList.size(); i++) {
			ClientThread ct = clientList.get(i);
			ct.sendMessage(message);
		}
	}
	public static void main(String argv[]) throws Exception {
		new Server(new GUI("Server"));
	}
	/**
	 * Private class to handle clients
	 */
	private class ClientThread extends Thread
	{
		// Streams that are global variables
		ObjectInputStream ois;
		ObjectOutputStream oos;
		public ClientThread(Socket socket) {
			clientList.add(this);
			setupStreams(socket);
			gui.setMessage("Client connected! ");
		}
		public synchronized void run() {
			boolean running = true;
			while(running) {
				try {
					Object obj = ois.readObject();
					gui.setMessage(obj.toString());
					handleMessages(obj.toString());
				}
				catch (IOException e) {
					e.printStackTrace();
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}
			}
		}
		private void sendMessage(String message) {
			try {
				oos.writeObject(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

        /**
		 * creating streams method
		 * @param socket creating streams through this arg socket
		 */
		private void setupStreams(Socket socket)
		{
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}


