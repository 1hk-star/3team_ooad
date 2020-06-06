import static org.junit.jupiter.api.Assertions.*;

import Controller.Watch;
import Mode.Alarm;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Calendar;

class AlarmTest {

	@Test
	void testCursor() {
		Alarm al = new Alarm();
		JButton btn1 = new JButton("Button1");
		JButton btn4 = new JButton("Button4");
		//초기 커서 위치 테스트
		assertEquals(-1,al.getCursor());

		//커서 위치 이동 테스트
		al.work(btn1);
		assertEquals(3,al.getCursor());

		al.work(btn1);
		assertEquals(4,al.getCursor());

		al.work(btn1);
		assertEquals(5,al.getCursor());

		al.work(btn1);
		assertEquals(3,al.getCursor());

		//초기 커서 위치 돌아오는지 테스트
		al.work(btn4);
		assertEquals(-1,al.getCursor());

	}

	@Test
	void testsetAlarm() {
		Alarm al = new Alarm();
		JButton btn1 = new JButton("Button1");
		JButton btn4 = new JButton("Button4");
		//초기 플래그값 확인
		assertEquals(0,al.get_flag());

		//set 플래그 확인
		al.work(btn1);
		assertEquals(1,al.get_flag());
		//설정화면 나왔을때.
		al.work(btn4);
		assertEquals(0,al.get_flag());

	}
	@Test
	void testconfirmAlarm(){
		Alarm al = new Alarm();
		JButton btn1 = new JButton("Button1");
		JButton btn4 = new JButton("Button4");

		//세팅 모드 진입
		al.work(btn1);
		assertEquals(1,al.get_flag());
		assertEquals(3,al.getCursor());

		//confirm 후
		al.work(btn4);
		assertEquals(0,al.get_flag());
		assertEquals(-1,al.getCursor());
	}

	@Test
	void testplusTime_alarm(){
		Alarm al = new Alarm();
		JButton btn1 = new JButton("Button1");
		JButton btn2 = new JButton("Button2");

		//세팅 모드 진입
		al.work(btn1);
		assertEquals(0,al.getAlarm().get(Calendar.HOUR_OF_DAY));
		assertEquals(0,al.getAlarm().get(Calendar.MINUTE));
		assertEquals(0,al.getAlarm().get(Calendar.SECOND));

		//시 plus 후
		al.work(btn2);
		assertEquals(1,al.getAlarm().get(Calendar.HOUR_OF_DAY));
		assertEquals(0,al.getAlarm().get(Calendar.MINUTE));
		assertEquals(0,al.getAlarm().get(Calendar.SECOND));

		//분 plus 후
		al.work(btn1);
		al.work(btn2);
		assertEquals(1,al.getAlarm().get(Calendar.HOUR_OF_DAY));
		assertEquals(1,al.getAlarm().get(Calendar.MINUTE));
		assertEquals(0,al.getAlarm().get(Calendar.SECOND));

		//초 plus 후
		al.work(btn1);
		al.work(btn2);
		assertEquals(1,al.getAlarm().get(Calendar.HOUR_OF_DAY));
		assertEquals(1,al.getAlarm().get(Calendar.MINUTE));
		assertEquals(1,al.getAlarm().get(Calendar.SECOND));

	}

	@Test
	void testresetAlarm(){
		Alarm al = new Alarm();
		JButton btn1 = new JButton("Button1");
		JButton btn4 = new JButton("Button4");

		//세팅 호출 및 저장
		al.work(btn1);
		al.work(btn4);

		//저장 값  not null 확인
		assertNotNull(al.getAlarm());

		//리셋 호출
		al.work(btn4);
		//값 null 확인
		assertNull(al.getAlarm());
	}
}
