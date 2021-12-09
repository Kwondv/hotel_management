package hotel_manage;

public class hotelDTO { //model
	private String userID;
	private String password;
	private String userName;
	private int RoomNumber;
	private String is_checkin;
	private String VIP;
	
	private String checkin_time;
	private String checkout_time;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getRoomNumber() {
		return RoomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		RoomNumber = roomNumber;
	}
	public String getIs_checkin() {
		return is_checkin;
	}
	public void setIs_checkin(String is_checkin) {
		this.is_checkin = is_checkin;
	}
	public String getVIP() {
		return VIP;
	}
	public void setVIP(String vIP) {
		VIP = vIP;
	}
	public String getCheckin_time() {
		return checkin_time;
	}
	public void setCheckin_time(String checkin_time) {
		this.checkin_time = checkin_time;
	}
	public String getCheckout_time() {
		return checkout_time;
	}
	public void setCheckout_time(String checkout_time) {
		this.checkout_time = checkout_time;
	}
	
	
}

