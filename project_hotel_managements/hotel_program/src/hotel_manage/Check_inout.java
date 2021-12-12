package hotel_manage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Check_inout extends JFrame {

	private JPanel contentPane;
	hotelDAO dao=new hotelDAO();
	public Check_inout(hotelDTO dto) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 800);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel check_main_label = new JLabel("\uC785\uC2E4/\uD1F4\uC2E4");
		check_main_label.setFont(new Font("³ª´®½ºÄù¾î Bold", Font.PLAIN, 45));
		check_main_label.setBounds(12, 10, 491, 85);
		contentPane.add(check_main_label);
		
		JLabel check_room_label = new JLabel("\uD638");
		check_room_label.setText(dto.getRoomNumber()+"È£");
		check_room_label.setFont(new Font("³ª´®½ºÄù¾î Bold", Font.PLAIN, 35));
		check_room_label.setBounds(12, 105, 491, 85);
		contentPane.add(check_room_label);
		
		JLabel check_inMain_label = new JLabel("\uC785\uC2E4 \uC2DC\uAC04");
		check_inMain_label.setFont(new Font("³ª´®½ºÄù¾î Bold", Font.PLAIN, 19));
		check_inMain_label.setBounds(295, 263, 727, 49);
		contentPane.add(check_inMain_label);
		
		JLabel check_outMain_label = new JLabel("\uD1F4\uC2E4 \uC2DC\uAC04");
		check_outMain_label.setFont(new Font("³ª´®½ºÄù¾î Bold", Font.PLAIN, 19));
		check_outMain_label.setBounds(295, 582, 727, 49);
		contentPane.add(check_outMain_label);
		
		JLabel check_inTime_label = new JLabel("\uC785\uC2E4 \uC2DC \uC785\uC2E4\uD558\uAE30 \uBC84\uD2BC\uC744 \uB20C\uB7EC \uC785\uC2E4\uD574\uC8FC\uC138\uC694");
		check_inTime_label.setOpaque(true);
		check_inTime_label.setBackground(SystemColor.inactiveCaptionBorder);
		check_inTime_label.setFont(new Font("³ª´®½ºÄù¾î Bold", Font.PLAIN, 30));
		check_inTime_label.setBounds(295, 322, 727, 110);
		contentPane.add(check_inTime_label);
		
		JLabel check_outTime_label = new JLabel("\uD1F4\uC2E4 \uC2DC \uD1F4\uC2E4\uD558\uAE30 \uBC84\uD2BC\uC744 \uB20C\uB7EC \uD1F4\uC2E4\uD574\uC8FC\uC138\uC694");
		check_outTime_label.setOpaque(true);
		check_outTime_label.setBackground(SystemColor.inactiveCaptionBorder);
		check_outTime_label.setFont(new Font("³ª´®½ºÄù¾î Bold", Font.PLAIN, 30));
		check_outTime_label.setBounds(295, 641, 727, 110);
		contentPane.add(check_outTime_label);
		
		JButton check_close_button = new JButton("\uB2EB\uAE30");
		check_close_button.setBackground(SystemColor.activeCaption);
		check_close_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dao.reset_check(dto);
				dispose();
			}
		});
		check_close_button.setFont(new Font("³ª´®½ºÄù¾î Bold", Font.PLAIN, 25));
		check_close_button.setBounds(850, 10, 172, 62);
		contentPane.add(check_close_button);
		
		JButton check_in_button = new JButton("\uC785\uC2E4\uD558\uAE30");
		check_in_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dao.changeColor(check_in_button, check_inTime_label, check_in_button.getText(), dto);
			}
		});
		check_in_button.setBackground(SystemColor.inactiveCaption);
		check_in_button.setFont(new Font("³ª´®½ºÄù¾î Bold", Font.PLAIN, 30));
		check_in_button.setBounds(12, 263, 283, 169);
		contentPane.add(check_in_button);
		
		JButton check_out_button = new JButton("\uD1F4\uC2E4\uD558\uAE30");
		check_out_button.addActionListener(new ActionListener() {
			hotelDAO dao=new hotelDAO();
			public void actionPerformed(ActionEvent e) {
				if(check_out_button.getText().equals("Åð½ÇÇÏ±â")) {
					dao.changeColor(check_out_button, check_outTime_label, check_out_button.getText(), dto); //»ö»ó º¯°æ
				}
			}
		});
		check_out_button.setBackground(SystemColor.inactiveCaption);
		check_out_button.setFont(new Font("³ª´®½ºÄù¾î Bold", Font.PLAIN, 30));
		check_out_button.setBounds(12, 582, 283, 169);
		contentPane.add(check_out_button);
		
		if(dto.getCheckin_time()==null || dto.getCheckin_time().equals(null)||dto.getCheckin_time().equals("0")) {
		}else
		{
			dao.settingColor(check_in_button, check_inTime_label, dto);
			dao.changeColor(check_in_button, check_inTime_label, check_in_button.getText(), dto);
		}
	}
}
