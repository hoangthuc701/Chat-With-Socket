package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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

	public ChatView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 923, 478);
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
		panel.add(txt_search);
		txt_search.setColumns(10);

		btn_send_file = new JButton("New button");
		btn_send_file.setActionCommand("SEND_FILE");
		btn_send_file.setBounds(209, 394, 49, 24);
		panel.add(btn_send_file);

		btn_send_icon = new JButton("New button");
		btn_send_icon.setActionCommand("SEND_ICON");
		btn_send_icon.setBounds(268, 394, 49, 24);
		panel.add(btn_send_icon);

		txt_chat_content = new JTextField();
		txt_chat_content.setBounds(321, 394, 506, 24);
		panel.add(txt_chat_content);
		txt_chat_content.setColumns(10);

		btn_send = new JButton("New button");
		btn_send.setActionCommand("SEND_TEXT");
		btn_send.setBounds(837, 394, 49, 24);
		panel.add(btn_send);

		btn_video_call = new JButton("New button");
		btn_video_call.setActionCommand("VIDEO_CALL");
		btn_video_call.setBounds(844, 12, 42, 25);
		panel.add(btn_video_call);

		btn_call = new JButton("New button");
		btn_call.setActionCommand("CALL");
		btn_call.setBounds(792, 12, 42, 25);
		panel.add(btn_call);

		lbl_userchatname = new JLabel("Nguyen Hoang Thuc");
		lbl_userchatname.setBounds(209, 11, 566, 27);
		panel.add(lbl_userchatname);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(209, 53, 677, 330);
		panel.add(scrollPane);
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

}
