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
	//db connection 부분
	private Connection getConnection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/DB자리?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","아이디","비밀번호");
		return con;
	}
	
	//로그인 시 유저 정보를 hotelDTO에 세팅
	public void Login_Check(String user_id, String user_pw, int RoomNumber, hotelDTO dto)
	{ 
		try(Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from user_info where userId='"+user_id+"' and password='"+user_pw+ "'");
				){
				if(rs.next()) {
					int check_room=Room_Check(RoomNumber); //입력한 방에 이미 누군가가 입실했나 확인
					if(RoomNumber==check_room) { //해당 호실에 입실한 사람이 없다면
						//데이터를 DTO에 저장
						dto.setUserID(rs.getString("userId"));
						dto.setPassword	(rs.getString("password"));
						dto.setUserName(rs.getString("userName"));
						dto.setRoomNumber(RoomNumber);
						dto.setIs_checkin("1");
						dto.setVIP(rs.getString("VIP"));
						User_Checkin(dto.getUserID(), dto.getPassword(), dto); //user_info의 체크인 정보와 호실 정보를 변경
						new Hotel_Main(dto); //메인화면으로 넘어감
					}else {//호실에 사람 있을 경우
						JOptionPane.showMessageDialog(null, "호실 입력을 다시 확인해주세요.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
					}
				}
				else
				{
					//로그인 실패
					JOptionPane.showMessageDialog(null, "아이디, 비밀번호, 호실 입력 중 오류가 있습니다.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	//user_info에 입관 설정 입관으로 변경
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
					//로그인 실패
					JOptionPane.showMessageDialog(null, "아이디, 비밀번호, 호실 입력 중 오류가 있습니다.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	//입관 설정 퇴관('0')으로 변경
	//user_info에 입관 설정 변경
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
					//로그인 실패
					JOptionPane.showMessageDialog(null, "오류가 생겼습니다. 프로그램을 다시 실행해주세요.", "프로그램 오류", JOptionPane.WARNING_MESSAGE);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	//호실중복체크
	public int Room_Check(int RoomNumber)
	{ 
		int room=0;
		try(Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from user_info where RoomNumber="+RoomNumber);
			){
				if(rs.next()) { // 방에 누가 들어간 사람이 있으면
					room=1;
				}
				else //방에 들어간 사람이 없으면
				{ 
					room=RoomNumber;
				}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return room;
	}
	
	//입실/퇴실 버튼 설정
	public void changeColor(JButton j, JLabel l, String buttonText, hotelDTO dto) //입실 버튼에서 시간 받아오는 매개변수 넣기
	{
		if(buttonText.equals("입실하기")||buttonText.equals("퇴실하기")){
			j.setBackground(new Color(255,0,0));
			if(buttonText.equals("입실하기")) {
				j.setText("입실함");
				LocalDateTime now = LocalDateTime.now();
				String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				dto.setCheckin_time(formatedNow);
				l.setText(dto.getCheckin_time());
				update_checkin(dto.getCheckin_time(), buttonText, dto);
			}else {
				j.setText("퇴실함");
				LocalDateTime now = LocalDateTime.now();
				String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				dto.setCheckout_time(formatedNow);
				l.setText(dto.getCheckout_time());
				update_checkout(dto.getCheckout_time(), buttonText, dto);//입실 버튼에서 시간 받아오는 매개변수 넣기
			}
		}
	}

	//입실만 하고 퇴실하지 않았다면
		public void settingColor(JButton j, JLabel l, hotelDTO dto) //입실버튼, 입실라벨, dto
		{
			j.setBackground(new Color(255,0,0));
			j.setText("입실함");
			l.setText(dto.getCheckin_time());
		}
		
	//입실 기록 적기
	public void update_checkin(String formatNow, String buttonText, hotelDTO dto)
	{ //입실하기인지 퇴실하기인지 받아서 그에 따라 insert문 2개 써야할듯!!
		if(buttonText.equals("입실하기")){
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
						JOptionPane.showMessageDialog(null, "오류가 발생했습니다. 프로그램을 다시 실행해주세요.", "프로그램 오류", JOptionPane.WARNING_MESSAGE);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
		}
	}
	
	// 입/퇴실 시 check_inout 테이블에 정보 기록 및 변경
	public void update_checkout(String formatNow, String buttonText, hotelDTO dto)
	{ //입실하기인지 퇴실하기인지 받아서 그에 따라 insert문 2개 써야할듯!!
		if(buttonText.equals("퇴실하기")){
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
						JOptionPane.showMessageDialog(null, "오류가 발생했습니다. 프로그램을 다시 실행해주세요.", "프로그램 오류", JOptionPane.WARNING_MESSAGE);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
		}
	}
	// 입/퇴실 시 초기화 여부
	public void reset_check(hotelDTO dto) // 퇴실 기록이 있다면 초기화
	{
		if(dto.getCheckout_time()!=null) {
			dto.setCheckin_time(null);
			dto.setCheckout_time(null);
		}
	}
}