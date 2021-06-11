package thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;

import service.Authenticate;
import utils.Json;

public class ConnectThread extends Thread {

	protected Socket socket;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	ArrayList<ConnectThread> listConect;
	public String userId = "-1";
	public String username = "";
	private Authenticate authenticateService;
	ArrayList<String> listUserOnline;
	HashMap<String, ArrayList<String>> messages;

	public ConnectThread(Socket socket, ArrayList<ConnectThread> listConect, String dbURL, String userName,
			String password, ArrayList<String> listUserOnline, HashMap<String, ArrayList<String>> messages) {
		this.listConect = listConect;
		this.socket = socket;
		this.authenticateService = new Authenticate(dbURL, userName, password);
		this.listUserOnline = listUserOnline;
		this.messages = messages;

		System.out.println(this.listConect.toString());
	}

	public String generateRandomId() {
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		return generatedString;
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
						String group_id = (String) tmp.get("chat_id");
						String content = (String) tmp.get("content");
						String user_send = this.username;
						content = user_send + " : " + content;
						synchronized (this) {
							// Todo: get list of username in group
							ArrayList<String> users_in_groups = messages.get(group_id);
							// send message to username

							// prepare data
							HashMap<String, String> message = new HashMap<String, String>();
							message.put("command", "RECEIVE_MESSAGE");
							message.put("chat_id", group_id);
							message.put("content", content);

							// send data
							String json = Json.JsonEncode(message);
							for (int i = 0; i < listConect.size(); i++) {
								if (listConect.get(i) != null && !listConect.get(i).username.equals("")
										&& users_in_groups.contains(listConect.get(i).username.trim())) {
									listConect.get(i).dos.writeUTF(json);
								}
							}
						}

					} else if (command.equals("SEND_ICON")) {

					} else if (command.contentEquals("SEND_FILE")) {
						String file_server_host = (String) tmp.get("file_server_host");
						String file_server_port = (String) tmp.get("file_server_port");
						String file_request = (String) tmp.get("file_request");
						String group_id = (String) tmp.get("group_id");

						synchronized (this) {
							// Todo: get list of username in group
							ArrayList<String> users_in_groups = messages.get(group_id);
							// send message to username

							// prepare data
							HashMap<String, String> message = new HashMap<String, String>();
							message.put("command", "RECEIVE_FILE");
							message.put("file_server_host", file_server_host);
							message.put("file_server_port", file_server_port);
							message.put("file_request", file_request);

							// send data
							String json = Json.JsonEncode(message);
							for (int i = 0; i < listConect.size(); i++) {
								if (listConect.get(i) != null && !listConect.get(i).username.equals("")
										&& users_in_groups.contains(listConect.get(i).username)
										&& listConect.get(i) != this) {
									listConect.get(i).dos.writeUTF(json);
								}
							}
						}

						// RECEIVE_FILE
					} else if (command.equals("REGISTER")) {
						String username = (String) tmp.get("username");
						String password = (String) tmp.get("password");
						String name = (String) tmp.get("name");
						boolean result = false;
						try {
							result = authenticateService.Register(username, password, name);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
						String username = (String) tmp.get("username");
						String password = (String) tmp.get("password");
						String result = null;
						try {
							result = authenticateService.Login(username, password);
						} catch (SQLException e) {
							result = "-1";
							e.printStackTrace();
						}
						if (!result.equals("-1")) {
							this.userId = result;
							HashMap<String, String> message = new HashMap<String, String>();
							message.put("command", "LOGIN_SUCCESS");
							message.put("username", username);
							String json = Json.JsonEncode(message);
							this.userId = result;
							this.username = username;
							this.dos.writeUTF(json);
							this.listUserOnline.add(this.username);

							// announce to other user
							synchronized (this) {
								// prepare data
								HashMap<String, String> message1 = new HashMap<String, String>();
								message1.put("command", "UPDATE_USER_ONLINE");
								message1.put("users", this.listUserOnline.toString());

								// send data
								String json1 = Json.JsonEncode(message1);
								for (int i = 0; i < listConect.size(); i++) {
									if (listConect.get(i) != null) {
										listConect.get(i).dos.writeUTF(json1);
									}
								}
							}

						} else {
							HashMap<String, String> message = new HashMap<String, String>();
							message.put("command", "LOGIN_FAIL");
							message.put("message", "The username or password is incorrect");
							String json = Json.JsonEncode(message);
							this.dos.writeUTF(json);
						}

					} else if (command.equals("LOAD_MESSAGE")) {
						// todo
					} else if (command.equals("CREATE_NEW_CHAT")) {
						String users = (String) tmp.get("users");
						String author = this.username;
						String new_chat = (String) tmp.get("chat_name");
						ArrayList<String> listUsers = new ArrayList<String>(
								Arrays.asList(users.substring(1, users.length() - 1).split(",")));
						
						for (int i = 0; i< listUsers.size(); i++) {
							listUsers.set(i, listUsers.get(i).trim());
						}
						// save to database
						if (!listUsers.contains(author)) {
							listUsers.add(author);
						}
						
						
						System.out.println(listUsers);
						
						System.out.println(this.listConect.toString());
						System.out.println(this.messages.toString());
						
						String chat_id = generateRandomId();
						messages.put(chat_id, listUsers);
						synchronized (this) {
							// prepare data
							HashMap<String, String> message = new HashMap<String, String>();
							message.put("command", "NEW_CHAT");

							message.put("chat_id", chat_id);
							message.put("conservation_name", new_chat);
							message.put("content", "New chat has been create.");
							message.put("author", this.username);

							// send data
							String json = Json.JsonEncode(message);
							for (int i = 0; i < listConect.size(); i++) {
								if (listConect.get(i)!=null) {
									System.out.println(listConect.get(i));
								}
								if (!listConect.get(i).username.equals("")) {
									System.out.println(listConect.get(i).username);
									
									System.out.println(listUsers.contains(listConect.get(i).username));
								}
								
								
								if (listConect.get(i) != null && !listConect.get(i).username.equals("")
										&& listUsers.contains(listConect.get(i).username.trim())) {
									System.out.println("run");
									listConect.get(i).dos.writeUTF(json);
								}
							}

						}

					}
				}
			} catch (IOException ex) {
				System.out.println("Sever IP:" + socket.getInetAddress().toString() + ", Port:" + socket.getPort()
						+ " Disconnect!");

				// send to other user leave
				// announce to other user
				synchronized (this) {
					// prepare data
					HashMap<String, String> message = new HashMap<String, String>();
					message.put("command", "UPDATE_USER_ONLINE");
					this.listUserOnline.remove(this.username);
					message.put("users", this.listUserOnline.toString());
					// send data
					String json1 = Json.JsonEncode(message);
					for (int i = 0; i < listConect.size(); i++) {
						if (listConect.get(i) != null && listConect.get(i) != this) {
							listConect.get(i).dos.writeUTF(json1);
						}
					}
				}
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
