package hotel_manage;
import java.awt.Color;
import java.awt.SystemColor;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class hotelDAO { //controller
	//db connection �κ�
	private Connection getConnection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/DB�ڸ�?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","���̵�","��й�ȣ");
		return con;
	}
	
	//�α��� �� ���� ������ hotelDTO�� ����
	public void Login_Check(String user_id, String user_pw, int RoomNumber, hotelDTO dto)
	{ 
		try(Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from user_info where userId='"+user_id+"' and password='"+user_pw+ "'");
				){
				if(rs.next()) {
					int check_room=Room_Check(RoomNumber); //�Է��� �濡 �̹� �������� �Խ��߳� Ȯ��
					if(RoomNumber==check_room) { //�ش� ȣ�ǿ� �Խ��� ����� ���ٸ�
						//�����͸� DTO�� ����
						dto.setUserID(rs.getString("userId"));
						dto.setPassword	(rs.getString("password"));
						dto.setUserName(rs.getString("userName"));
						dto.setRoomNumber(RoomNumber);
						dto.setIs_checkin("1");
						dto.setVIP(rs.getString("VIP"));
						User_Checkin(dto.getUserID(), dto.getPassword(), dto); //user_info�� üũ�� ������ ȣ�� ������ ����
						new Hotel_Main(dto); //����ȭ������ �Ѿ
					}else {//ȣ�ǿ� ��� ���� ���
						JOptionPane.showMessageDialog(null, "ȣ�� �Է��� �ٽ� Ȯ�����ּ���.", "�α��� ����", JOptionPane.WARNING_MESSAGE);
					}
				}
				else
				{
					//�α��� ����
					JOptionPane.showMessageDialog(null, "���̵�, ��й�ȣ, ȣ�� �Է� �� ������ �ֽ��ϴ�.", "�α��� ����", JOptionPane.WARNING_MESSAGE);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	//user_info�� �԰� ���� �԰����� ����
	public void User_Checkin(String user_id, String user_pw, hotelDTO dto)
	{ 
		try(Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from user_info where userId='"+user_id+"' and password='"+user_pw+ "'");
				){
				if(rs.next()) {
					
						stmt.executeUpdate(										
								"update user_info set RoomNumber="+dto.getRoomNumber()+", is_checkin='1' where userID='"+dto.getUserID()+"'");
				}
				else
				{
					//�α��� ����
					JOptionPane.showMessageDialog(null, "���̵�, ��й�ȣ, ȣ�� �Է� �� ������ �ֽ��ϴ�.", "�α��� ����", JOptionPane.WARNING_MESSAGE);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	//�԰� ���� ���('0')���� ����
	//user_info�� �԰� ���� ����
	public void User_Checkout(String user_id, String user_pw, hotelDTO dto)
	{ 
		try(Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from user_info where userId='"+user_id+"' and password='"+user_pw+ "'");
				){
				if(rs.next()) {
					if(dto.getIs_checkin().equals("1")) {
						stmt.executeUpdate(										
								"update user_info set RoomNumber="+0+", is_checkin='0' where userID='"+dto.getUserID()+"'");
					}
				}
				else
				{
					//�α��� ����
					JOptionPane.showMessageDialog(null, "������ ������ϴ�. ���α׷��� �ٽ� �������ּ���.", "���α׷� ����", JOptionPane.WARNING_MESSAGE);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	//ȣ���ߺ�üũ
	public int Room_Check(int RoomNumber)
	{ 
		int room=0;
		try(Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from user_info where RoomNumber="+RoomNumber);
			){
				if(rs.next()) { // �濡 ���� �� ����� ������
					room=1;
				}
				else //�濡 �� ����� ������
				{ 
					room=RoomNumber;
				}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return room;
	}
	
	//�Խ�/��� ��ư ����
	public void changeColor(JButton j, JLabel l, String buttonText, hotelDTO dto) //�Խ� ��ư���� �ð� �޾ƿ��� �Ű����� �ֱ�
	{
		if(buttonText.equals("�Խ��ϱ�")||buttonText.equals("����ϱ�")){
			j.setBackground(new Color(255,0,0));
			if(buttonText.equals("�Խ��ϱ�")) {
				j.setText("�Խ���");
				LocalDateTime now = LocalDateTime.now();
				String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				dto.setCheckin_time(formatedNow);
				l.setText(dto.getCheckin_time());
				update_checkin(dto.getCheckin_time(), buttonText, dto);
			}else {
				j.setText("�����");
				LocalDateTime now = LocalDateTime.now();
				String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				dto.setCheckout_time(formatedNow);
				l.setText(dto.getCheckout_time());
				update_checkout(dto.getCheckout_time(), buttonText, dto);//�Խ� ��ư���� �ð� �޾ƿ��� �Ű����� �ֱ�
			}
		}
	}

	//�ԽǸ� �ϰ� ������� �ʾҴٸ�
		public void settingColor(JButton j, JLabel l, hotelDTO dto) //�Խǹ�ư, �ԽǶ�, dto
		{
			j.setBackground(new Color(255,0,0));
			j.setText("�Խ���");
			l.setText(dto.getCheckin_time());
		}
		
	//�Խ� ��� ����
	public void update_checkin(String formatNow, String buttonText, hotelDTO dto)
	{ //�Խ��ϱ����� ����ϱ����� �޾Ƽ� �׿� ���� insert�� 2�� ����ҵ�!!
		if(buttonText.equals("�Խ��ϱ�")){
			try(Connection conn = getConnection();
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from user_info where userId='"+dto.getUserID()+"'");
					){
					if(rs.next()) {
						stmt.executeUpdate(String.format(
								"insert into check_inout(userID, RoomNumber, checkin_time) values('%s', '%s', '%s')", 
								dto.getUserID(), dto.getRoomNumber(), formatNow)); //#
					}
					else
					{
						JOptionPane.showMessageDialog(null, "������ �߻��߽��ϴ�. ���α׷��� �ٽ� �������ּ���.", "���α׷� ����", JOptionPane.WARNING_MESSAGE);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
		}
	}
	
	// ��/��� �� check_inout ���̺��� ���� ��� �� ����
	public void update_checkout(String formatNow, String buttonText, hotelDTO dto)
	{ //�Խ��ϱ����� ����ϱ����� �޾Ƽ� �׿� ���� insert�� 2�� ����ҵ�!!
		if(buttonText.equals("����ϱ�")){
			try(Connection conn = getConnection();
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from user_info where userId='"+dto.getUserID()+"'");
					){
					if(rs.next()) {
						stmt.executeUpdate(String.format(
								"update check_inout set checkout_time='"+dto.getCheckout_time()+"' where userID='"+dto.getUserID()+"' and checkin_time='"+dto.getCheckin_time()+"'"));
					}
					else
					{
						JOptionPane.showMessageDialog(null, "������ �߻��߽��ϴ�. ���α׷��� �ٽ� �������ּ���.", "���α׷� ����", JOptionPane.WARNING_MESSAGE);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
		}
	}
	// ��/��� �� �ʱ�ȭ ����
	public void reset_check(hotelDTO dto) // ��� ����� �ִٸ� �ʱ�ȭ
	{
		if(dto.getCheckout_time()!=null) {
			dto.setCheckin_time(null);
			dto.setCheckout_time(null);
		}
	}
}