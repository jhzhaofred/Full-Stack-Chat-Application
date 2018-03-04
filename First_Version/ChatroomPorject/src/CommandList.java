public class CommandList {
	private CommandList() {}
	public static void getCommandList(ChatSocket cs, String cmd) {
		switch(cmd) {
		case "uls" :
			int numbers = ChatManager.getChatManager().message.size();
			ChatManager.getChatManager().userlist(cs, numbers + " online users: ");
			for (String name : ChatManager.getChatManager().message.keySet()) {
				ChatManager.getChatManager().userlist(cs, name);
			};
			break;
		case "exit" :
			System.out.println(cs.name+" left chat room.");
			ChatManager.getChatManager().message.get(cs.name).close();
			ChatManager.getChatManager().message.remove(cs.name);
			break;
		}
	}
}
