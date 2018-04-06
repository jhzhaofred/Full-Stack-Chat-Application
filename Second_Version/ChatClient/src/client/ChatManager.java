package client;

import java.io.*;
import java.net.*;
import client.view.MainWindow;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;;
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
	JSONParser parser = new JSONParser();
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
					window.initial();
					String line;
					while ((line = reader.readLine()) != null) {	
						if (!line.equals("")) {
							JSONObject obj = new JSONObject();
							try {
								obj = (JSONObject) parser.parse(line);
								String MSG; 
								if (obj.containsKey("System")) {
									window.appendText((String) obj.get("System"));
									if (obj.get("cmd") != null) 
									{
										switch((String) obj.get("cmd")) {
											case "ResetUserName" : window.resetUserName();
																break;
										}
									}
								} else if (obj.containsKey("init")) {
									window.push_userlist(obj);
								} else if (obj.containsKey("newUser")) {
									window.new_user((String) obj.get("newUser"));
								} else if (obj.containsKey("removeUser")) {
									window.user_left((String) obj.get("removeUser"));
								} else if(!(MSG = (String) obj.get("Message")).equals("")) {
									if (((String) obj.get("Target")).equalsIgnoreCase("public"))
										window.appendText(obj.get("Name") + " to Public: " + MSG);
									else
										window.appendText(obj.get("Name") + ": " + MSG);
								}
							} catch (ParseException e) {
								window.appendText("System: " + line);
							}
						}
					}
					writer.close();
					reader.close();
					writer = null;
					reader = null;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					window.appendText("Failed to connect to the server, please change to another server or try it later.");
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void send (JSONObject out) {
		if (writer != null && (!out.get("Name").equals("") || !out.get("Message").equals(""))) {
			writer.write(out.toString()+"\n");
			writer.flush();
		} 
	}
}
