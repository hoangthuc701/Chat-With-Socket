package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javafx.util.Pair;

public class ChatView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_search;
	private JTextField txt_chat_content;
	private JLabel lbl_userchatname;
	private JButton btn_call;
	private JButton btn_video_call;
	private JButton btn_send;
	private JButton btn_send_file;
	private JButton btn_send_icon;
	private JList list_chat;
	private JScrollPane scrollPane;
	private JPanel panel_chatbox;
	private JButton new_chat_btn;
	private JList list_user_online;
	public String chat_id;
	public HashMap<String, Pair<String, ArrayList<String>>> messages;
	private int file_server_port;



	public ChatView() {
		this.messages = new HashMap<String, Pair<String, ArrayList<String>>>();
		this.file_server_port = getAvailablePort();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1169, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setForeground(Color.MAGENTA);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		list_chat = new JList();
		list_chat.setBounds(10, 49, 189, 380);
		panel.add(list_chat);

		txt_search = new JTextField();
		txt_search.setBounds(10, 11, 189, 27);
		txt_search.setVisible(false);
		panel.add(txt_search);
		txt_search.setColumns(10);

		btn_send_file = new JButton("Send file");
		btn_send_file.setActionCommand("SEND_FILE");
		btn_send_file.setBounds(209, 394, 102, 24);
		panel.add(btn_send_file);

		btn_send_icon = new JButton("Send icon");
		btn_send_icon.setVisible(false);
		btn_send_icon.setActionCommand("SEND_ICON");
		btn_send_icon.setBounds(268, 394, 49, 24);
		panel.add(btn_send_icon);

		txt_chat_content = new JTextField();
		txt_chat_content.setBounds(321, 394, 454, 24);
		panel.add(txt_chat_content);
		txt_chat_content.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(209, 53, 677, 330);
		panel.add(scrollPane);
		
		panel_chatbox = new JPanel();
		scrollPane.setViewportView(panel_chatbox);
		panel_chatbox.setLayout(new BoxLayout(panel_chatbox, BoxLayout.Y_AXIS));

		btn_send = new JButton("Send text");
		btn_send.setActionCommand("SEND_TEXT");
		btn_send.setBounds(785, 394, 101, 24);
		panel.add(btn_send);

		btn_video_call = new JButton("Video call");
		btn_video_call.setVisible(false);
		btn_video_call.setActionCommand("VIDEO_CALL");
		btn_video_call.setBounds(844, 12, 42, 25);
		panel.add(btn_video_call);

		btn_call = new JButton("Call");
		btn_call.setVisible(false);
		btn_call.setActionCommand("CALL");
		btn_call.setBounds(792, 12, 42, 25);
		panel.add(btn_call);

		lbl_userchatname = new JLabel("");
		lbl_userchatname.setBounds(209, 11, 566, 27);
		panel.add(lbl_userchatname);
		
		list_user_online = new JList();
		list_user_online.setBounds(915, 49, 207, 334);
		panel.add(list_user_online);
		
		JLabel lblNewLabel = new JLabel("Online user");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(1030, 9, 91, 27);
		panel.add(lblNewLabel);
		
		new_chat_btn = new JButton("New chat");
		new_chat_btn.setActionCommand("CREATE_NEW_CHAT");
		new_chat_btn.setBounds(915, 13, 89, 23);
		panel.add(new_chat_btn);
	}

	public JLabel getUserChatName() {
		return lbl_userchatname;
	}

	public JButton getButtonCall() {
		return btn_call;
	}

	public JButton getButtonVideoCall() {
		return btn_video_call;
	}

	public JButton getButtonSend() {
		return btn_send;
	}

	public JButton getButtonSendFile() {
		return btn_send_file;
	}

	public JButton getButtonSendIcon() {
		return btn_send_icon;
	}
	
	public JList getListChat() {
		return list_chat;
	}
	
	public JPanel getPanelChatbox() {
		return panel_chatbox;
	}
	
	public JButton getButtonNewChat() {
		return new_chat_btn;
	}
	
	public JList getListUserOnline() {
		return list_user_online;
	}

	public JTextField getMessageContent() {
		return txt_chat_content;
	}
	
	public int getFileServerPort() {
		return this.file_server_port;
	}
	
	private static int getAvailablePort() {
		ServerSocket s = null;
		try {
			s = new ServerSocket(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s.getLocalPort();
	}
}
