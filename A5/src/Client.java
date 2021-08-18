import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Client class that communicates with server
 * @author abdulsamisahil
 * @since 2020-05-27
 */
public class Client {
	private GUI gui;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	/**
	 * Constructs a Client object
	 * @param port the port of the server
	 */
	public Client(String ipAddress, int port, GUI gui) {
		this.gui = gui;
		gui.setClient(this);
		Socket socket;
		try {
			socket = new Socket(ipAddress, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			new ClientListeningToServer().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sends a message to the server
	 * @param message the message that is written back to server
	 */
	public synchronized void sendMessage(String message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The inner thread class that listens and reads objects from Server
	 */
	private class ClientListeningToServer extends Thread
	{
		public synchronized void run() {
			while(true) {
				try {
					Object obj = ois.readObject();
					if(obj instanceof String) {
						gui.setMessage(obj.toString());
					}
				}
				catch(IOException e) {
					break;
				}
				catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		/*for (int i = 0; i < 7; i++){
			new Client("localhost", 3490, new GUI("Client" + i));
		}*/
		Client u1 = new Client("localhost", 3490, new GUI("Client"));
		Client u2 = new Client("localhost", 3490, new GUI("Client"));
		//	Client u3 = new Client("localhost", 3490, new GUI("Client"));
		//	Client u4 = new Client("localhost", 3490, new GUI("Client"));
		//	Client u5 = new Client("localhost", 3490, new GUI("Client"));
		//	Client u6 = new Client("localhost", 3490, new GUI("Client"));
	}
}

