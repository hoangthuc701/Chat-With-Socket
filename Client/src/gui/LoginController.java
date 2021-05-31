package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import utils.Json;

public class LoginController implements ActionListener {

	private LoginView view;
	private DataInputStream dis;
	private DataOutputStream dos;

	public LoginController(LoginView view, DataInputStream dis, DataOutputStream dos) {
		this.view = view;
		this.dis = dis;
		this.dos = dos;
		view.getButtonLogin().addActionListener(this);
		view.getButtonRegister().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(view.getButtonLogin().getActionCommand())) {
			HashMap<String, String> message =new HashMap<String, String>(); 
			message.put("command", "LOGIN");
			message.put("username", "admin");
			message.put("password", "admin");
			String json = Json.JsonEncode(message);
			try {
				this.dos.writeUTF(json);
				System.out.print("send to server");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} else if (e.getActionCommand().equals(view.getButtonRegister().getActionCommand())) {
			HashMap<String, String> message =new HashMap<String, String>(); 
			message.put("username", "admin");
			message.put("password", "admin");
			message.put("name","name");
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
