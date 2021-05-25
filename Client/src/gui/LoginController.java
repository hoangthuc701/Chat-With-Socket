package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

	private LoginView view;

	public LoginController(LoginView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(view.getButtonLogin().getActionCommand())) {
			
		} else if (e.getActionCommand().equals(view.getButtonRegister().getActionCommand())) {

		}
	}
}
