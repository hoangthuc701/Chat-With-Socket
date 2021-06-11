package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import utils.Json;

public class RegisterController implements ActionListener {
	private RegisterView view;
	private LoginView login_view;
	private ChatView chat_view;

	private DataInputStream dis;
	private DataOutputStream dos;

	public RegisterController(LoginView login_view, RegisterView view, ChatView chat_view, DataInputStream dis,
			DataOutputStream dos) {
		this.view = view;
		this.login_view = login_view;
		this.chat_view = chat_view;
		this.dis = dis;
		this.dos = dos;
		view.getButtonLogin().addActionListener(this);
		view.getButtonRegister().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(view.getButtonLogin().getActionCommand())) {
			view.setVisible(false);
			login_view.setVisible(true);
		} else if (e.getActionCommand().equals(view.getButtonRegister().getActionCommand())) {
			HashMap<String, String> message = new HashMap<String, String>();
			if (!view.getFieldPassword().getText().equals(view.getFieldRepeatPassword().getText())) {
				JOptionPane.showMessageDialog(null, "Your password and repeat password doesn't match.");
				return;
			}
			
			message.put("command","REGISTER");
			message.put("username", view.getFieldUsername().getText());
			message.put("password", view.getFieldPassword().getText());
			message.put("name", view.getFielddName().getText());
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
