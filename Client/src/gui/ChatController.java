package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ChatController implements ActionListener {

	private ChatView view;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;

	public ChatController(ChatView view,  DataInputStream dis, DataOutputStream dos) {
		this.view = view;
		this.dis = dis;
		this.dos = dos;
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
