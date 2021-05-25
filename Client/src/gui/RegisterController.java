package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController implements ActionListener {
	private RegisterView view;
	
	public RegisterController(RegisterView view) {
		this.view = view;
		view.getButtonLogin().addActionListener(this);
		view.getButtonRegister().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(view.getButtonLogin().getActionCommand())) {
			
		} else if (e.getActionCommand().equals(view.getButtonRegister().getActionCommand())) {
			
		}
		
	}
}
