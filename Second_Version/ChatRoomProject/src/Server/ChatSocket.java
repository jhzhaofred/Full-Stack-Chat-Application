package Server;
import java.io.*;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class ChatSocket extends Thread {
	public String name;
	private Socket socket;	
	JSONParser parser = new JSONParser();
	
	public ChatSocket(Socket s, String name) {
		this.socket = s;
		this.name = name;
	}
	public void close () {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void out(String out) {
		try {
			socket.getOutputStream().write(out.getBytes("UTF-8"));
			socket.getOutputStream().write("\n".getBytes("UTF-8"));
		} catch (IOException e) {
			System.out.println(name + " left chat room.");
			ChatManager.getChatManager().message.remove(name);
			ChatManager.getChatManager().CheckAlive(JSONBuilder.build("System", name + " left chat room.").toJSONString());
			ChatManager.getChatManager().remove_me(name);
		}
	}
	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream(),"UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (!line.equals("")) {
					JSONObject obj = (JSONObject) parser.parse(line);
					String TalkTo = (String) obj.get("Target");
					if(TalkTo.equals("Public")) {
						ChatManager.getChatManager().publish(this, obj);
					} else {
						try {
							ChatManager.getChatManager().talk(this, TalkTo, obj);
						} catch (NullPointerException e) {
							ChatManager.getChatManager().ERROR(this,"User name "+ TalkTo +" does not exist!");
						}
					}		
				}
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
