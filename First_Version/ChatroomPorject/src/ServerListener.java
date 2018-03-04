import java.io.*;
import java.net.*;
public class ServerListener extends Thread {
	@Override
	public void run () {
		while(true) {
			try {
				ServerSocket serverSocket = new ServerSocket(9527);
				Socket socket = serverSocket.accept();
				socket.getOutputStream().write("Welcome to anonymous chat room! \n".getBytes("UTF-8"));
				BufferedReader user = null; 
				String name = null;
				while (name == null || name.equals("")) {
					socket.getOutputStream().write("Please enter your ID: \n".getBytes("UTF-8"));
					user = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
					name = user.readLine().trim();
					if (ChatManager.getChatManager().message.containsKey(name)) {
						socket.getOutputStream().write(("ID "+name+" existed! \n").getBytes("UTF-8"));
						name = "";
					}
					if (name.equalsIgnoreCase("public")) {
						socket.getOutputStream().write(("This name is reserved! \n").getBytes("UTF-8"));
						name = "";
					}
				} 
				System.out.println(name+" entered the chatting room.");
				socket.getOutputStream().write("Initializing... \n".getBytes("UTF-8"));
				socket.getOutputStream().write("Now you can chat! \n".getBytes("UTF-8"));
				System.out.println(name+" left");
				ChatSocket userEntry = new ChatSocket(socket, name);
				userEntry.start();
				ChatManager.getChatManager().add(userEntry);
				System.out.println(ChatManager.getChatManager().message.keySet());
				HeartBeats HB = new HeartBeats();
				HB.start();
			} catch (IOException e) {
				
			}
		}
	}
}
