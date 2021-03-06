package hotel_manage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.awt.Color;
import java.awt.SystemColor;

public class Hotel_Main extends JFrame {

	private JPanel contentPane;

	public Hotel_Main(hotelDTO dto) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 800);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton main_chat_Button = new JButton("\uCC44\uD305");
		main_chat_Button.setBackground(SystemColor.activeCaption);
		main_chat_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChatClient();
			}
		});
		main_chat_Button.setFont(new Font("?????????? Bold", Font.PLAIN, 20));
		main_chat_Button.setBounds(521, 382, 501, 240);
		contentPane.add(main_chat_Button);
		
		JButton main_check_Button = new JButton("\uC785\uC2E4/\uD1F4\uC2E4");
		main_check_Button.setBackground(SystemColor.activeCaption);
		main_check_Button.addActionListener(new ActionListener() {
			hotelDAO dao = new hotelDAO();
			public void actionPerformed(ActionEvent e) {
				new Check_inout(dto);
			}
		});
		main_check_Button.setFont(new Font("?????????? Bold", Font.PLAIN, 20));
		main_check_Button.setBounds(12, 382, 497, 240);
		contentPane.add(main_check_Button);
		
		JLabel main_label = new JLabel("\uBA54\uC778 \uD654\uBA74");
		main_label.setFont(new Font("?????????? Bold", Font.PLAIN, 50));
		main_label.setBounds(12, 10, 575, 83);
		contentPane.add(main_label);
		
		JButton main_logout_Button = new JButton("\uB85C\uADF8\uC544\uC6C3");
		main_logout_Button.setBackground(SystemColor.activeCaption);
		main_logout_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hotelDAO dao=new hotelDAO();
				dao.User_Checkout(dto.getUserID(), dto.getPassword(), dto);
				dispose();
			}
		});
		main_logout_Button.setFont(new Font("?????????? Bold", Font.PLAIN, 20));
		main_logout_Button.setBounds(833, 10, 189, 59);
		contentPane.add(main_logout_Button);
	}
}
