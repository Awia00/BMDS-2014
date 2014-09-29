import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A source for sending out messages to a server for forwarding the messages to
 * subscribed receiving sinks.
 */
public class Source {

	public Source(String address) {
		connectoToServer(address);
	}

	private void connectoToServer(String address) {
		try {
            //Set up receiving of user input.
            BufferedReader userIn = new BufferedReader((new InputStreamReader(
                    System.in)));

            // Send user input to the server.
            String userInput = "";
            while (!userInput.equals("exit")) {
                userInput = userIn.readLine();

                // Establish connection.
                Socket serverConnection = new Socket(address, 7777);

                PrintWriter outToServer = new PrintWriter(
                        serverConnection.getOutputStream(), true);

                //Send the message and close the connection.
                outToServer.write(userInput + "\n");
                serverConnection.close();
            }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Runnable r = () -> {
			// For testing. Create a simple server, print out everything it has
			// received.
			try {
				ServerSocket server = new ServerSocket(7777);
				Socket s = server.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));

				String serverIn;
				while ((serverIn = in.readLine()) != null) {
					System.out.println(serverIn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		// Create a Source and connect it to the server.
		Runnable r2 = () -> {
			try {
				new Source("localhost");
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		new Thread(r).start();
		new Thread(r2).start();
	}
}
