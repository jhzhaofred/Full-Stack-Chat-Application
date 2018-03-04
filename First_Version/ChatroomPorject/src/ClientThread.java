import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread{
	private Socket client;
	public ClientThread(Socket s) {
		this.client = s;
	}
	@Override
	public void run () {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String lines;
			while ((lines = in.readLine()) != null) {
				if(lines.length()!=0) System.out.println(lines);
			}
		} catch (IOException e) {
			System.err.println("Left chat room.");
		}
	}
}
