
public class HeartBeats extends Thread {
	@Override
	public void run() {
		while(true) {
			ChatManager.getChatManager().CheckAlive("");
			try {
				sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
