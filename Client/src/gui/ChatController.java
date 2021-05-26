package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatController implements ActionListener {

	private ChatView view;

	public ChatController(ChatView view) {
		this.view = view;
		view.getButtonCall().addActionListener(this);
		view.getButtonSend().addActionListener(this);
		view.getButtonSendFile().addActionListener(this);
		view.getButtonSendIcon().addActionListener(this);
		view.getButtonVideoCall().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(view.getButtonCall().getActionCommand())) {

		} else if (e.getActionCommand().equals(view.getButtonSend().getActionCommand())) {

		} else if (e.getActionCommand().equals(view.getButtonSendFile().getActionCommand())) {

		} else if (e.getActionCommand().equals(view.getButtonSendIcon().getActionCommand())) {

		} else if (e.getActionCommand().equals(view.getButtonVideoCall().getActionCommand())) {

		}
	}

}
