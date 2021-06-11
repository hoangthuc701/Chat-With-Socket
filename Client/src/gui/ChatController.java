package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JFileChooser;
import java.io.File;

import gui.NewChat.ReturnObject;
import javafx.util.Pair;
import utils.Json;

public class ChatController implements ActionListener {

	private ChatView view;
	private LoginView login_view;
	private RegisterView register_view;

	private DataInputStream dis = null;
	private DataOutputStream dos = null;

	public ChatController(LoginView login_view, RegisterView register_view, ChatView view, DataInputStream dis,
			DataOutputStream dos) {
		this.view = view;
		this.login_view = login_view;
		this.register_view = register_view;
		this.dis = dis;
		this.dos = dos;
		view.getButtonCall().addActionListener(this);
		view.getButtonSend().addActionListener(this);
		view.getButtonSendFile().addActionListener(this);
		view.getButtonSendIcon().addActionListener(this);
		view.getButtonVideoCall().addActionListener(this);
		view.getButtonNewChat().addActionListener(this);
		view.getListChat().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (view.getListChat().getSelectedValue() != null) {
					String selected = view.getListChat().getSelectedValue().toString();

					for (Entry<String, Pair<String, ArrayList<String>>> entry : view.messages.entrySet()) {
						String value = entry.getValue().getKey();
						if (value.equals(selected)) {
							view.chat_id = entry.getKey();
							break;
						}
					}
					// set chat_id
					ArrayList<String> arrays = view.messages.get(view.chat_id).getValue();
					view.getUserChatName().setText(selected);

					view.getPanelChatbox().removeAll();

					for (int i = 0; i < arrays.size(); i++) {
						String message = arrays.get(i);
						JLabel lbl_userchatname = new JLabel("<html><font color='black'>" + message + "</font></html>");
						lbl_userchatname.setBorder(new EmptyBorder(0, 0, 10, 0));
						lbl_userchatname.setBounds(209, 11, 566, 27);
						view.getPanelChatbox().add(lbl_userchatname);
					}
					view.getPanelChatbox().revalidate();
					view.getPanelChatbox().repaint();

				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(view.getButtonCall().getActionCommand())) {

		} else if (e.getActionCommand().equals(view.getButtonSend().getActionCommand())) {
			if (view.chat_id.length() == 0) {
				JOptionPane.showMessageDialog(null, "Please create new conservation. ");
				return;
			}
			// send chat content to server
			HashMap<String, String> message = new HashMap<String, String>();
			message.put("command", "SEND_MESSAGE");
			message.put("chat_id", view.chat_id);
			message.put("content", view.getMessageContent().getText());
			view.getMessageContent().setText("");
			String json = Json.JsonEncode(message);
			try {
				this.dos.writeUTF(json);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getActionCommand().equals(view.getButtonSendFile().getActionCommand())) {

			if (view.chat_id.length() == 0) {
				JOptionPane.showMessageDialog(null, "Please create new conservation. ");
				return;
			}

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();

				String file_server_host = "127.0.0.1";
				String file_server_port = String.valueOf(view.getFileServerPort());
				String file_request = selectedFile.getAbsolutePath();
				String group_id = view.chat_id;

				HashMap<String, String> message = new HashMap<String, String>();
				message.put("command", "SEND_FILE");
				message.put("file_server_host", file_server_host);
				message.put("file_server_port", file_server_port);
				message.put("file_request", file_request);
				message.put("group_id", group_id);

				String json = Json.JsonEncode(message);
				try {
					this.dos.writeUTF(json);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		} else if (e.getActionCommand().equals(view.getButtonNewChat().getActionCommand())) {
			NewChat newChat = new NewChat();
			ListModel model = view.getListUserOnline().getModel();
			ArrayList<String> list = new ArrayList<String>();

			for (int i = 0; i < model.getSize(); i++) {
				String o = (String) model.getElementAt(i);
				list.add(o);
			}

			ReturnObject result = newChat.showDialog(list);
			if (result.is_status()) {
				ArrayList<String> selectedUser = result.get_listUser();
				// process data to create a group

				HashMap<String, String> message = new HashMap<String, String>();
				message.put("command", "CREATE_NEW_CHAT");
				message.put("users", selectedUser.toString());
				message.put("chat_name", result.getChat_name());
				String json = Json.JsonEncode(message);
				try {
					this.dos.writeUTF(json);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

}
