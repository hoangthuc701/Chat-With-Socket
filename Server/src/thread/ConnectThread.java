package thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import service.Authenticate;
import utils.Json;

public class ConnectThread extends Thread {

	protected Socket socket;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	ArrayList<ConnectThread> listConect;
	public String userId = "-1";
	private Authenticate authenticateService;

	public ConnectThread(Socket socket, ArrayList<ConnectThread> listConect, String dbURL, String userName, String password) {
		this.listConect = listConect;
		this.socket = socket;
		this.authenticateService = new Authenticate(dbURL, userName, password);
	}

	public void run() {

		try {
			String line = "";
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			System.out.println(
					"New client IP:" + socket.getInetAddress().toString() + ", Port:" + socket.getPort() + " Connect!");
			try {
				while ((line = dis.readUTF()) != null) {
					JSONObject tmp = Json.JsonDecode(line);
					String command = (String) tmp.get("command");
					if (command.equals("QUIT")) {

					} else if (command.equals("SEND_MESSAGE")) {
						String group_id = (String) tmp.get("group_id");
						String content = (String) tmp.get("content");
						String user_send = "user_send";
						synchronized (this) {
							// Todo: get list of username in group
							ArrayList<String> users_in_groups = new ArrayList<String>();
							// send message to username

							// prepare data
							HashMap<String, String> message = new HashMap<String, String>();
							message.put("username", "");
							message.put("content", "");

							// send data
							String json = Json.JsonEncode(message);
							for (int i = 0; i < users_in_groups.size(); i++) {
								if (listConect.get(i) != null && listConect.get(i) != this) {
									listConect.get(i).dos.writeUTF(json);
								}
							}

						}

					} else if (command.equals("SEND_ICON")) {

					} else if (command.contentEquals("SEND_FILE")) {

					} else if (command.equals("REGISTER")) {
						String username = (String) tmp.get("username");
						String password = (String) tmp.get("password");
						String name = (String) tmp.get("name");
						boolean result = authenticateService.Register(username, password, name);
						if (result) {
							HashMap<String, String> message = new HashMap<String, String>();
							message.put("command", "REGISTER_SUCCESS");
							message.put("message", "Register successfully.");
							String json = Json.JsonEncode(message);
							this.dos.writeUTF(json);
						} else {
							HashMap<String, String> message = new HashMap<String, String>();
							message.put("command", "REGISTER_FAIL");
							message.put("message", "Register fail.");
							String json = Json.JsonEncode(message);
							this.dos.writeUTF(json);
						}

					} else if (command.contentEquals("LOGIN")) {
						System.out.println("Server send");
						String username = (String) tmp.get("username");
						String password = (String) tmp.get("password");
						String result = null;
						try {
							result = authenticateService.Login(username, password);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (!result.equals("-1")) {
							this.userId = result;
							HashMap<String, String> message = new HashMap<String, String>();
							message.put("command", "LOGIN_SUCCESS");
							String json = Json.JsonEncode(message);
							this.dos.writeUTF(json);
						} else {
							HashMap<String, String> message = new HashMap<String, String>();
							message.put("command", "LOGIN_FAIL");
							message.put("message", "The username or password is incorrect");
							String json = Json.JsonEncode(message);
							this.dos.writeUTF(json);
						}

					} else if (command.equals("LOAD_MESSAGE")) {
						// todo
					}
				}
			} catch (IOException ex) {
				System.out.println("Sever IP:" + socket.getInetAddress().toString() + ", Port:" + socket.getPort()
						+ " Disconnect!");
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
