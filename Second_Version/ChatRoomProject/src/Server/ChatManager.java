package Server;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.DefaultListModel;

import org.json.simple.JSONObject;;
public class ChatManager {
	private ChatManager () {}
	private static final ChatManager cm = new ChatManager();
	public static ChatManager getChatManager() {
		return cm;
	}
	Map<String, ChatSocket> message = new ConcurrentHashMap<String, ChatSocket>();	
	public void add(ChatSocket cs) {
		message.put(cs.name, cs);
	}
	public void publish(ChatSocket cs, JSONObject out){		
		for (String user:message.keySet()) {
			ChatSocket csChatSocket = message.get(user);
			if (!cs.equals(csChatSocket)) {
				csChatSocket.out(out.toString());
			}
		}
	}
	public void publish_me(ChatSocket cs, JSONObject out){		
		for (String user:message.keySet()) {
			ChatSocket csChatSocket = message.get(user);
			if (!cs.equals(csChatSocket)) {
				csChatSocket.out(out.toString());
			}
		}
	}
	public void remove_me(String name){		
		JSONObject out = new JSONObject();
		out.put("removeUser", name);
		for (String user:message.keySet()) {
			message.get(user).out(out.toString());
		}
	}
	public void CheckAlive(String out){
		for (String user:message.keySet()) {
			ChatSocket csChatSocket = message.get(user);
			csChatSocket.out(out);
		}
	}
	public void talk(ChatSocket cs, String name, JSONObject out) {
		message.get(name).out(out.toString());
	}
	public void userlist(ChatSocket cs) {
		ArrayList<String> userlist = new ArrayList<String>();
		userlist.add("Public");
		for(String user : message.keySet()) {
			userlist.add(user);
		}
		JSONObject obj = new JSONObject();
		obj.put("init", userlist);
		cs.out(obj.toString());
	}
	public void ERROR(ChatSocket cs, String out) {
		message.get(cs.name).out(out);
	}
}
