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
		while(true) {
			try {
				ServerSocket serverSocket = new ServerSocket(9527);
				Socket socket = serverSocket.accept();
				socket.getOutputStream().write("Welcome to anonymous chat room! \n".getBytes("UTF-8"));
				BufferedReader user = null; 
				String name = null;
				socket.getOutputStream().write("Please enter your ID: \n".getBytes("UTF-8"));
				user = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
				newuser = (JSONObject) parser.parse(user.readLine());
				name = (String) newuser.get("Name");
				while (ChatManager.getChatManager().message.containsKey(name)) {
					socket.getOutputStream().write("Username exists!\n".getBytes("UTF-8"));
					newuser = (JSONObject) parser.parse(user.readLine());
					name = (String) newuser.get("Name");
				}
				System.out.println(name+" entered the chatting room.");
				socket.getOutputStream().write(("Username: " + name + "\n").getBytes("UTF-8"));
				socket.getOutputStream().write("Now you can chat! \n".getBytes("UTF-8"));
				ChatSocket userEntry = new ChatSocket(socket, name);
				userEntry.start();
				ChatManager.getChatManager().add(userEntry);
				ChatManager.getChatManager().userlist(userEntry);
				JSONObject publish_me = new JSONObject();
				publish_me.put("newUser", name);
				ChatManager.getChatManager().publish_me(userEntry, publish_me);
				System.out.println(ChatManager.getChatManager().message.keySet());
				HeartBeats HB = new HeartBeats();
				HB.start();
			} catch (IOException | ParseException | NullPointerException e) {
				
			}
		}
	}
}
