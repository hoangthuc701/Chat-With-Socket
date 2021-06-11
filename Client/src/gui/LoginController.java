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
	private RegisterView register_view;
	private ChatView chat_view;

	private DataInputStream dis;
	private DataOutputStream dos;

	public LoginController(LoginView view, RegisterView register_view, ChatView chat_view, DataInputStream dis,
			DataOutputStream dos) {
		this.view = view;
		this.register_view = register_view;
		this.chat_view = chat_view;
		this.dis = dis;
		this.dos = dos;
		view.getButtonLogin().addActionListener(this);
		view.getButtonRegister().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(view.getButtonLogin().getActionCommand())) {
			HashMap<String, String> message = new HashMap<String, String>();
			message.put("command", "LOGIN");
			message.put("username", view.getFieldUsername().getText());
			message.put("password", view.getFieldPassword().getText());
			String json = Json.JsonEncode(message);
			try {
				this.dos.writeUTF(json);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getActionCommand().equals(view.getButtonRegister().getActionCommand())) {
			view.setVisible(false);
			register_view.setVisible(true);
		}
	}
}
