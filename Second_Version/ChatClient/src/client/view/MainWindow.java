package client.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import client.ChatManager;

import javax.swing.JTextArea;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.json.simple.*;
import javax.swing.JList;
public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txt = new JTextArea();
	private JTextField ip;
	private JTextField send;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_UserList;
	private JTextField Port;
	private JTextField Target;
	private JList<String> list;
	private JButton btnSend;
	private JButton btnConnect;
	private MouseAdapter ML;
	
	/**
	 * Launch the application.
	 */
	private JSONObject obj = new JSONObject();
	
	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		
		ip = new JTextField();
		ip.setText("127.0.0.1");
		ip.setColumns(10);
		
		btnSend = new JButton("Send");
		btnSend.setEnabled(false);
		btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(ML = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				obj.remove("Name");
				ChatManager.getChatManager().connect(ip.getText(),Integer.parseInt(Port.getText()));
			}
		});
		
		send = new JTextField();
		send.setEditable(false);
		send.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					SendEvent(obj);
				}
			}
		});
		send.setColumns(10);
			
		Port = new JTextField();
		Port.setText("9527");
		Port.setColumns(10);
		
		JLabel IPLabel = new JLabel("IP: ");
		
		JLabel PortLabel = new JLabel("Port:");
		
		Target = new JTextField();
		Target.setText("Public");
		Target.setEditable(false);
		Target.setColumns(10);
		
		scrollPane_UserList = new JScrollPane();

		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(Target, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(send, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(IPLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ip, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(PortLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Port, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnConnect, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
							.addGap(2))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane_UserList, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(ip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnConnect)
						.addComponent(IPLabel)
						.addComponent(Port, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(PortLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(send, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(Target, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSend)))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(scrollPane_UserList, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		list = new JList<String>();
		scrollPane_UserList.setViewportView(list);
		
		list.setEnabled(false);
		txt.setText("Please click on connect buttom to start.....");
		scrollPane.setViewportView(txt);
		contentPane.setLayout(gl_contentPane);
		scrollPane.setAutoscrolls(true);
	}
	public void appendText (String in) {
		txt.append("\n"+in);
		txt.setCaretPosition(txt.getText().length());
		txt.setLineWrap(true);
		txt.setWrapStyleWord(true);
	}
	public void initial () {
		send.setEditable(true);
		Target.setEditable(true);
		btnSend.setEnabled(true);
		list.setEnabled(true);
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SendEvent(obj);
			}
		});
		btnConnect.removeMouseListener(ML);
		btnConnect.setEnabled(false);
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!((String) obj.get("Name")).equals(list.getSelectedValue())) {
					Target.setText(list.getSelectedValue().toString());
				}
			}
		});
	}
	DefaultListModel<String> uls = new DefaultListModel<String>();
	
	@SuppressWarnings("unchecked")
	public void push_userlist (JSONObject obj) {
		ArrayList<String> userlist = (ArrayList<String>) obj.get("init");
		for(String user: userlist) {
			uls.addElement(user);
		}
		list.setModel(uls);
	}
	public void new_user (String newUser) {
		uls.addElement(newUser);
	}
	public void user_left (String oldUser) {
		uls.removeElement(oldUser);
	}
	public void resetUserName() {
		obj.remove("Name");
	}
	
	@SuppressWarnings("unchecked")
	private void SendEvent (JSONObject obj) {
		if (obj.containsKey("Name")) {
			obj.put("Message", send.getText());
			obj.put("Target", Target.getText());
			ChatManager.getChatManager().send(obj);
			if (!send.getText().equals("")) {
				appendText("Me to "+Target.getText()+"："+send.getText());
				send.setText("");
			}
		} else {
			String spaceRemove = send.getText();
			if (!send.getText().equals("") && 
					!spaceRemove.replace(" ", "").equals("") && 
					!send.getText().equalsIgnoreCase("public")) {
				obj.put("Name", send.getText());
				ChatManager.getChatManager().send(obj);
			} else {
				appendText("Username is invalid!");
			}
			send.setText("");
		}
	}
}
