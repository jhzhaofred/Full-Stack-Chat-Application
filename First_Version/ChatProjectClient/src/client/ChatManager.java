package client;

import java.io.*;
import java.net.*;

import client.view.MainWindow;

public class ChatManager {
	private ChatManager() {}
	private static final ChatManager cm = new ChatManager();
	public static ChatManager getChatManager() {
		return cm;
	}
	
	MainWindow window;
	String IP;
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	
	public void setwindow (MainWindow window) {
		this.window = window;
	}
	
	public void connect (String ip, int port) {
		this.IP = ip;
		new Thread() {
			@Override
			public void run() {
				try {
					socket = new Socket(ip, port);
					writer = new PrintWriter(
							new OutputStreamWriter(
									socket.getOutputStream()));
					reader = new BufferedReader(
							new InputStreamReader(
									socket.getInputStream()));
					String line;
					while ((line = reader.readLine()) != null) {
						if (!line.equals("")) {
							window.appendText(line);
						}					
					}
					writer.close();
					reader.close();
					writer = null;
					reader = null;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void send (String out) {
		if (writer != null && !out.equals("")) {
			writer.write(out+"\n");
			writer.flush();
		} 
	}
}
