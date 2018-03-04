import java.io.*;
import java.net.*;

public class ClientListener {
	public static void main(String[] args) throws Exception {
		Socket client = new Socket("localhost", 9527);
		PrintWriter out = null;
		boolean flag = true;
		out = new PrintWriter(client.getOutputStream(),true);
		while(flag) {
			ClientThread ClientThread = new ClientThread(client);
			ClientThread.start();
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				if (userInput.equalsIgnoreCase("$exit")) {
					flag = false;
					break;
				}
			}
		}
	}
}
