import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
	public void publish(ChatSocket cs, String out){
		if (out.trim().length() == 0) {
			message.remove(cs.name);
		} else {
			for (String user:message.keySet()) {
				ChatSocket csChatSocket = message.get(user);
				if (!cs.equals(csChatSocket)) {
					csChatSocket.out(cs.name+": "+out);
				}
			}
		}
	}
	public void CheckAlive(String out){
		for (String user:message.keySet()) {
			ChatSocket csChatSocket = message.get(user);
			csChatSocket.out(out);
		}
	}
	public void talk(ChatSocket cs, String name, String out) {
		message.get(name).out(cs.name+": "+out);
	}
	public void userlist(ChatSocket cs, String out) {
		cs.out(out);
	}
	public void ERROR(ChatSocket cs, String out) {
		message.get(cs.name).out(out);
	}
}
