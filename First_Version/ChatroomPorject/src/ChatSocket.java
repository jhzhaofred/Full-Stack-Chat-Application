import java.io.*;
import java.net.*;

public class ChatSocket extends Thread {
	public String name;
	private Socket socket;	
	private String TalkTo = "public";
	public ChatSocket(Socket s, String name) {
		this.socket = s;
		this.name = name;
	}
	public void close () {
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
					if (line.substring(0, 1).equals("$")) {
						CommandList.getCommandList(this, line.substring(1));
					} else if(line.substring(0, 1).equals("@")) {
						TalkTo = line.substring(1);
					} else {
						if (!TalkTo.equals("public")) {
							try {
								ChatManager.getChatManager().talk(this, TalkTo, line);
							} catch (NullPointerException e) {
								ChatManager.getChatManager().ERROR(this,"User name "+ TalkTo +" does not exist!");
							}
						} else {
							ChatManager.getChatManager().publish(this, line);
						}
					}
				}
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		}
	}
}
