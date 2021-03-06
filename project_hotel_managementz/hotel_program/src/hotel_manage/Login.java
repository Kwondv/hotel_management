package hotel_manage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.sql.*;
import java.awt.Color;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField id_textField;
	private JTextField room_textField;
	private JPasswordField passwd_passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					
					 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		new ChatServer();
	}
	
	public Login() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 800);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		id_textField = new JTextField();
		id_textField.setBackground(SystemColor.inactiveCaptionBorder);
		id_textField.setFont(new Font("?????????? Bold", Font.PLAIN, 25));
		id_textField.setBounds(253, 317, 567, 101);
		contentPane.add(id_textField);
		id_textField.setColumns(10);
		
		passwd_passwordField = new JPasswordField();
		passwd_passwordField.setBackground(SystemColor.inactiveCaptionBorder);
		passwd_passwordField.setFont(new Font("?????????? Bold", Font.PLAIN, 25));
		passwd_passwordField.setBounds(253, 441, 567, 101);
		passwd_passwordField.setEchoChar('*');
		contentPane.add(passwd_passwordField);
		
		room_textField = new JTextField();
		room_textField.setBackground(SystemColor.inactiveCaptionBorder);
		room_textField.setFont(new Font("?????????? Bold", Font.PLAIN, 25));
		room_textField.setColumns(10);
		room_textField.setBounds(253, 573, 567, 101);
		contentPane.add(room_textField);
		
		JButton login_button = new JButton("\uB85C\uADF8\uC778");
		login_button.setBackground(SystemColor.activeCaption);
		login_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hotelDTO dto = new hotelDTO();
				hotelDAO dao = new hotelDAO();
				//?α??? ?? ???? ???? ?????ϱ?
				dao.Login_Check(id_textField.getText(), new String(passwd_passwordField.getPassword()), Integer.parseInt(room_textField.getText()), dto);
			}
		});
		login_button.setFont(new Font("?????????? Bold", Font.PLAIN, 50));
		login_button.setBounds(822, 317, 200, 356);
		contentPane.add(login_button);
		
		JLabel login_id_label = new JLabel("\uC544\uC774\uB514");
		login_id_label.setFont(new Font("?????????? Bold", Font.PLAIN, 30));
		login_id_label.setHorizontalAlignment(SwingConstants.CENTER);
		login_id_label.setBounds(12, 317, 229, 101);
		contentPane.add(login_id_label);
		
		JLabel login_passwd_label = new JLabel("\uBE44\uBC00\uBC88\uD638");
		login_passwd_label.setHorizontalAlignment(SwingConstants.CENTER);
		login_passwd_label.setFont(new Font("?????????? Bold", Font.PLAIN, 30));
		login_passwd_label.setBounds(12, 441, 229, 101);
		contentPane.add(login_passwd_label);
		
		JLabel login_Room_label = new JLabel("\uD638\uC2E4 \uBC88\uD638");
		login_Room_label.setHorizontalAlignment(SwingConstants.CENTER);
		login_Room_label.setFont(new Font("?????????? Bold", Font.PLAIN, 30));
		login_Room_label.setBounds(12, 573, 229, 101);
		contentPane.add(login_Room_label);
		
		JLabel login_main_label = new JLabel("\uD638\uD154 \uB85C\uADF8\uC778");
		login_main_label.setHorizontalAlignment(SwingConstants.CENTER);
		login_main_label.setFont(new Font("?????????? Bold", Font.PLAIN, 90));
		login_main_label.setBounds(68, 66, 927, 179);
		contentPane.add(login_main_label);
		
	}
}
