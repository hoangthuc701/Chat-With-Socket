package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class RegisterController implements ActionListener {
	private RegisterView view;
	private DataInputStream dis;
	private  DataOutputStream dos;
	
	public RegisterController(RegisterView view,  DataInputStream dis, DataOutputStream dos) {
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
