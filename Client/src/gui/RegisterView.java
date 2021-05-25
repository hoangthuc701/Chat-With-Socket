package gui;

import java.awt.BorderLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class RegisterView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_username;
	private JTextField txt_name;
	private JTextField txt_password;
	private JTextField txt_repeat_password;
	private JButton btn_register;
	private JButton btn_login;

	public RegisterView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 328);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		Panel panel = new Panel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(34, 95, 79, 14);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setBounds(34, 120, 46, 14);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Password:");
		lblNewLabel_2.setBounds(34, 145, 64, 14);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Repeat Password:");
		lblNewLabel_3.setBounds(34, 170, 88, 14);
		panel.add(lblNewLabel_3);

		txt_username = new JTextField();
		txt_username.setBounds(132, 92, 232, 20);
		panel.add(txt_username);
		txt_username.setColumns(10);

		txt_name = new JTextField();
		txt_name.setBounds(131, 117, 233, 20);
		panel.add(txt_name);
		txt_name.setColumns(10);

		txt_password = new JTextField();
		txt_password.setBounds(132, 142, 232, 20);
		panel.add(txt_password);
		txt_password.setColumns(10);

		txt_repeat_password = new JTextField();
		txt_repeat_password.setBounds(132, 167, 232, 20);
		panel.add(txt_repeat_password);
		txt_repeat_password.setColumns(10);

		btn_register = new JButton("Register");
		btn_register.setBounds(166, 198, 89, 23);
		panel.add(btn_register);

		JLabel lblNewLabel_4 = new JLabel("Or");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(189, 220, 46, 29);
		panel.add(lblNewLabel_4);

		btn_login = new JButton("Login");
		btn_login.setBounds(166, 245, 89, 23);
		panel.add(btn_login);
	}

	public JTextField getFieldUsername() {
		return txt_username;
	}

	public JTextField getFielddName() {
		return txt_name;
	}

	public JTextField getFieldPassword() {
		return txt_password;
	}

	public JTextField getFieldRepeatPassword() {
		return txt_repeat_password;
	}

	public JButton getButtonLogin() {
		return btn_login;
	}

	public JButton getButtonRegister() {
		return btn_register;
	}

}
