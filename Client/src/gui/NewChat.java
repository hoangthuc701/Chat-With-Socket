package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class NewChat extends JDialog {

	public class ReturnObject {
		private boolean _status;
		private ArrayList<String> _listUser;
		private String chat_name;

		public boolean is_status() {
			return _status;
		}

		public void set_status(boolean _status) {
			this._status = _status;
		}

		public ArrayList<String> get_listUser() {
			return _listUser;
		}

		public void set_listUser(ArrayList<String> _listUser) {
			this._listUser = _listUser;
		}

		public ReturnObject(ArrayList<String> listUser, boolean status, String chat_name) {
			this._listUser = listUser;
			this._status = status;
			this.chat_name = chat_name;
		}

		public String getChat_name() {
			return chat_name;
		}

		public void setChat_name(String chat_name) {
			this.chat_name = chat_name;
		}
	}

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private ArrayList<String> listUserOnline = new ArrayList<String>();
	private CheckBoxList list;
	private ArrayList<String> result = new ArrayList<String>();
	private boolean _status =false;
	private JTextField textField;
	private String chat_name;

	public NewChat() {
		super((java.awt.Frame) null, true);
		setTitle("Create new chat");
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 323);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Select user to create new chat");
			lblNewLabel.setBounds(10, 11, 183, 14);
			contentPanel.add(lblNewLabel);
		}
		{

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			list = new CheckBoxList();
			scrollPane.setBounds(10, 34, 414, 183);

			scrollPane.setViewportView(list);
			list.setLayoutOrientation(JList.VERTICAL);
			contentPanel.add(scrollPane);
		}
		
		textField = new JTextField();
		textField.setBounds(120, 220, 304, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("New label");
		label.setBounds(147, 223, 46, 14);
		contentPanel.add(label);
		
		JLabel lblChatName = new JLabel("Chat name");
		lblChatName.setBounds(20, 223, 69, 14);
		contentPanel.add(lblChatName);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Create");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						if (textField.getText().length()==0) {
							JOptionPane.showMessageDialog(null, "Please input chat name.");
							return;
						}
						
						ListModel curentList = list.getModel();
						for (int i = 0; i < curentList.getSize(); i++) {
							if (((JCheckBox) (curentList.getElementAt(i))).isSelected()) {
								result.add(((JCheckBox) (curentList.getElementAt(i))).getText());
							}
						}
						_status = true;
						chat_name = textField.getText();
						dispose();
					}
				});
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						 
						_status = false;
						dispose();
						
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

	public ReturnObject showDialog(ArrayList<String> listUserOnline) {
		this.listUserOnline = listUserOnline;

		for (int i = 0; i < listUserOnline.size(); i++) {
			JCheckBox checkbox = new JCheckBox(this.listUserOnline.get(i));
			list.addCheckbox(checkbox);
		}

		repaint();
		setVisible(true);
		return new ReturnObject(result, this._status, this.chat_name);
	}
}
