package hotel_manage;

import static org.junit.jupiter.api.Assertions.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.junit.jupiter.api.Test;
import org.junit.BeforeClass;

class Check_inoutTest {

	//입실 시 입실 시간이 제대로 저장되었나 테스트
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
	}
	
	@Test
	void test() {
		hotelDTO dto=new hotelDTO();
		hotelDAO dao=new hotelDAO();
		dto.setUserID("1111");
		dto.setPassword("1111");
		dto.setRoomNumber(111);
		JButton check_in_button = new JButton("\uC785\uC2E4\uD558\uAE30");
		JLabel check_inTime_label = new JLabel("\uC785\uC2E4 \uC2DC \uC785\uC2E4\uD558\uAE30 \uBC84\uD2BC\uC744 \uB20C\uB7EC \uC785\uC2E4\uD574\uC8FC\uC138\uC694");
		
		dao.changeColor(check_in_button, check_inTime_label, check_in_button.getText(),dto);
		assertNotNull(dto.getCheckin_time());
	}

}
