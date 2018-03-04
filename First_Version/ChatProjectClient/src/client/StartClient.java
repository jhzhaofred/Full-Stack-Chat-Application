package client;

import java.awt.EventQueue;

import client.view.MainWindow;

public class StartClient {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
					ChatManager.getChatManager().setwindow(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
