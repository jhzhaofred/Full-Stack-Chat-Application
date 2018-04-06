package Server;
import java.io.*;
import org.json.simple.JSONObject;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.net.*;
public class ServerListener extends Thread {
	@Override
	public void run () {
		JSONObject newuser = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONBuilder.getJSONBuilder();
		while(true) {
			try {
				// Initialization
				@SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket(9527);
				Socket socket = serverSocket.accept();
				socket.getOutputStream().write((JSONBuilder.build("System", 
						"Welcome to anonymous chat room! \nPlease enter your ID: ")
						.toJSONString() + "\n").getBytes("UTF-8"));
				
				// Get a valid user name.
				BufferedReader user = null; 
				String name = null;
				user = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
				newuser = (JSONObject) parser.parse(user.readLine());
				name = (String) newuser.get("Name");
				while (ChatManager.getChatManager().message.containsKey(name)) {
					JSONBuilder.getJSONBuilder();
					socket.getOutputStream().write((JSONBuilder.build(
							new String[] {"System","cmd"}, new String[] {"Username exists!","ResetUserName"})
							.toJSONString() + "\n").getBytes("UTF-8"));
					newuser = (JSONObject) parser.parse(user.readLine());
					name = (String) newuser.get("Name");
				}
				System.out.println(name+" entered the chatting room.");
				JSONBuilder.getJSONBuilder();
				socket.getOutputStream().write((JSONBuilder.build("System", 
						"Username: " + name + "\nNow you can chat! ")
						.toJSONString() + "\n").getBytes("UTF-8"));
				
				// Add Client's thread to ChatManger.
				ChatSocket userEntry = new ChatSocket(socket, name);
				userEntry.start();
				ChatManager.getChatManager().add(userEntry);
				
				// Update the list of online users and broadcast.
				ChatManager.getChatManager().userlist(userEntry);
				JSONBuilder.getJSONBuilder();
				ChatManager.getChatManager().publish_me(userEntry, JSONBuilder.build("newUser", name));
				System.out.println(ChatManager.getChatManager().message.keySet());
				HeartBeats HB = new HeartBeats();
				HB.start();
			} catch (IOException | ParseException | NullPointerException e) {
				
			}
		}
	}
}
