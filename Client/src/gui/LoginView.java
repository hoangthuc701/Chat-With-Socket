package gui;

import java.awt.BorderLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_username;
	private JTextField txt_password;
	private JButton btn_login;
	private JButton btn_register;

	public LoginView() {
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		Panel panel = new Panel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(64, 106, 77, 22);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setBounds(64, 139, 77, 22);
		panel.add(lblNewLabel_1);

		txt_username = new JTextField();
		txt_username.setBounds(130, 107, 207, 20);
		panel.add(txt_username);
		txt_username.setColumns(10);

		txt_password = new JTextField();
		txt_password.setBounds(130, 139, 207, 20);
		panel.add(txt_password);
		txt_password.setColumns(10);

		btn_login = new JButton("Login");
		btn_login.setActionCommand("LOGIN");
		btn_login.setBounds(177, 170, 89, 23);
		panel.add(btn_login);

		JLabel lblNewLabel_2 = new JLabel("Or");
		lblNewLabel_2.setBounds(220, 194, 18, 14);
		panel.add(lblNewLabel_2);

		btn_register = new JButton("Register");
		btn_register.setActionCommand("CHANGE_TO_REGISTER_VIEW");
		btn_register.setBounds(177, 219, 89, 23);
		panel.add(btn_register);
	}

	public JTextField getFieldUsername() {
		return txt_username;
	}

	public JTextField getFieldPassword() {
		return txt_password;
	}

	public JButton getButtonLogin() {
		return btn_login;
	}

	public JButton getButtonRegister() {
		return btn_register;
	}

}
