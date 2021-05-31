package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import gui.ChatController;
import gui.ChatView;
import gui.LoginController;
import gui.LoginView;
import gui.RegisterController;
import gui.RegisterView;
import utils.Json;

public class ClientMain {
	public static class Listener implements Runnable {
		private DataInputStream dis;
		private LoginView login_view;
		private RegisterView register_view;
		private ChatView chat_view;
		
		public Listener(DataInputStream dis, LoginView login_view, RegisterView register_view, ChatView chat_view) {
			this.dis = dis;
			this.login_view = login_view;
			this.register_view = register_view;
			this.chat_view = chat_view;
			
		}

		public void run() {
			String line;
			try {
				while ((line = this.dis.readUTF()) != null) {
					JSONObject tmp = Json.JsonDecode(line);
					String command = (String) tmp.get("command");
					System.out.println(command);
					if (command.equals("LOGIN_SUCCESS")) {
						login_view.setVisible(false);
						chat_view.setVisible(true);
					} else if (command.contentEquals("LOGIN_FAIL")) {

					} else if (command.equals("REGISTER_SUCCESS")) {

					} else if (command.contentEquals("REGISTER_FAIL")) {

					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {

		String host = "127.0.0.1";
		int port = 1234;
		Socket socket = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		socket = new Socket(host, port);

		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());

		
		LoginView login_view = new LoginView();
		new LoginController(login_view, dis, dos);
		login_view.setVisible(true);
		
		RegisterView register_view = new RegisterView();
		new RegisterController(register_view, dis, dos);
		register_view.setVisible(false);
		
		ChatView chat_view = new ChatView();
		new ChatController(chat_view, dis, dos);
		chat_view.setVisible(false);
		
		
		Runnable runable = new Listener(dis, login_view, register_view, chat_view);
		(new Thread(runable)).start();
	}

}
