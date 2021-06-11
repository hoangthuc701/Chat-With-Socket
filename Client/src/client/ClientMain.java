package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONObject;

import gui.ChatController;
import gui.ChatView;
import gui.LoginController;
import gui.LoginView;
import gui.RegisterController;
import gui.RegisterView;
import javafx.util.Pair;
import thread.ReceiveFileThread;
import thread.SendFileManager;
import utils.*;

public class ClientMain {
	public static class Listener implements Runnable {
		private DataInputStream dis;
		private DataOutputStream dos;
		private LoginView login_view;
		private RegisterView register_view;
		private ChatView chat_view;

		public Listener(DataInputStream dis, DataOutputStream dos, LoginView login_view, RegisterView register_view,
				ChatView chat_view) {
			this.dis = dis;
			this.dos = dos;
			this.login_view = login_view;
			this.register_view = register_view;
			this.chat_view = chat_view;

		}

		public String[] convertArrayListToStringArray(ArrayList<String> arr) {
			String str[] = new String[arr.size()];
			for (int j = 0; j < arr.size(); j++) {
				str[j] = (String) arr.get(j);
			}

			return str;
		}

		public void repaint(String chat_id) {
			chat_view.getPanelChatbox().removeAll();
			ArrayList<String> arrays = chat_view.messages.get(chat_id).getValue();

			for (int i = 0; i < arrays.size(); i++) {
				String message = arrays.get(i);
				JLabel lbl_userchatname = new JLabel("<html><font color='black'>" + message + "</font></html>");
				lbl_userchatname.setBorder(new EmptyBorder(0, 0, 10, 0));
				lbl_userchatname.setBounds(209, 11, 566, 27);
				chat_view.getPanelChatbox().add(lbl_userchatname);
			}
			chat_view.getPanelChatbox().revalidate();
			chat_view.getPanelChatbox().repaint();
		}

		public void run() {
			String line;
			try {
				while ((line = this.dis.readUTF()) != null) {
					JSONObject tmp = Json.JsonDecode(line);
					String command = (String) tmp.get("command");
					System.out.println(command);
					if (command.equals("LOGIN_SUCCESS")) {
						String username = (String) tmp.get("username");
						chat_view.setTitle(username);
						login_view.setVisible(false);
						chat_view.setVisible(true);
					} else if (command.contentEquals("LOGIN_FAIL")) {
						String error_message = (String) tmp.get("message");
						JOptionPane.showMessageDialog(null, error_message);
					} else if (command.equals("REGISTER_SUCCESS")) {
						JOptionPane.showMessageDialog(null,
								"You have registered successfully. Please login to continue.");
						register_view.setVisible(false);
						login_view.setVisible(true);

					} else if (command.equals("REGISTER_FAIL")) {
						String error_message = (String) tmp.get("message");
						JOptionPane.showMessageDialog(null, error_message);

					} else if (command.equals("RECEIVE_MESSAGE")) {
						String chat_id = (String) tmp.get("chat_id");
						String message = (String) tmp.get("content");
						String conservation_name = chat_view.messages.get(chat_id).getKey();

						// add to list
						ArrayList<String> messages_content = chat_view.messages.get(chat_id).getValue();
						messages_content.add(message);
						chat_view.messages.put(chat_id, new Pair<>(conservation_name, messages_content));

						// repaint
						if (chat_id.equals(chat_view.chat_id)) {
							repaint(chat_id);
						}

					} else if (command.equals("RECEIVE_FILE")) {
						int result = JOptionPane.showConfirmDialog(null,
								"you received a file? Would you like to receive it?", "Confirm",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							// connect to server and receive it
							String file_server_host = (String) tmp.get("file_server_host");
							int file_server_port = Integer.parseInt((String) tmp.get("file_server_port"));
							String file_request = (String) tmp.get("file_request");
							
							
							Path path = Paths.get(file_request);
							Path fileName = path.getFileName();
							String file_output = fileName.toString();

							System.out.println(file_server_host);
							System.out.println(file_request);
							System.out.println(file_server_port);
							System.out.println(file_output);

							new ReceiveFileThread(file_server_host, file_server_port, file_request, file_output).start();
						}
					} else if (command.equals("NEW_CHAT")) {
						String chat_id = (String) tmp.get("chat_id");
						String conservation_name = (String) tmp.get("conservation_name");
						String content = (String) tmp.get("content");
						String author = (String) tmp.get("author");
						ArrayList<String> messages_content = new ArrayList<String>();
						messages_content.add(content);
						chat_view.messages.put(chat_id, new Pair<>(conservation_name, messages_content));

						// add to list chat new message
						ArrayList<String> conservations = new ArrayList<String>();

						for (Entry<String, Pair<String, ArrayList<String>>> entry : chat_view.messages.entrySet()) {
							String key = entry.getKey();
							Pair<String, ArrayList<String>> value = entry.getValue();
							conservations.add(value.getKey());
						}

						chat_view.getListChat().setListData(this.convertArrayListToStringArray(conservations));

					} else if (command.equals("UPDATE_USER_ONLINE")) {
						// add to list user online
						String s = (String) tmp.get("users");
						ArrayList<String> users = new ArrayList<String>(
								Arrays.asList(s.substring(1, s.length() - 1).split(",")));
						chat_view.getListUserOnline().setListData(this.convertArrayListToStringArray(users));
					}
				}
			} catch (

			IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws IOException {

		String host = "127.0.0.1";
		int port = 7899;
		Socket socket = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		socket = new Socket(host, port);

		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());

		LoginView login_view = new LoginView();
		RegisterView register_view = new RegisterView();
		ChatView chat_view = new ChatView();

		new LoginController(login_view, register_view, chat_view, dis, dos);
		login_view.setVisible(true);

		new RegisterController(login_view, register_view, chat_view, dis, dos);
		register_view.setVisible(false);

		new ChatController(login_view, register_view, chat_view, dis, dos);
		chat_view.setVisible(false);

		Runnable runable = new Listener(dis, dos, login_view, register_view, chat_view);
		(new Thread(runable)).start();

		// start server to listen and send file
		int current_file_server_port = chat_view.getFileServerPort();
		SendFileManager send_file_manager = new SendFileManager(current_file_server_port);
		send_file_manager.start();
	}

}
