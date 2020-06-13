import static org.junit.jupiter.api.Assertions.*;

import Mode.TimeKeep;
import Mode.mTimer;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Calendar;

class TimeKeepingTest {

	@Test
	void showTimeKeepingtest() {
		TimeKeep tk = new TimeKeep();
		tk.showTimeKeeping();
		Calendar current_time = tk.gettime();
		assertNotNull(current_time);
	}

	@Test
	void getddaytest(){
		TimeKeep tk = new TimeKeep();
		assertEquals(null,tk.getdday());
	}

	@Test
	void setddaytest(){
		TimeKeep tk = new TimeKeep();
		tk.setdday(null);
		assertEquals(null,tk.getoriginTime());
		assertEquals(null,tk.getddaymemoTime());
	}

	@Test
	void gettimetest(){
		TimeKeep tk = new TimeKeep();
		assertEquals(null,tk.gettime());
	}

	@Test
	void getRealTimetest(){
		TimeKeep tk = new TimeKeep();
		assertEquals(null,tk.getRealTime());
	}


	@Test
	void setCurrentTimetest(){
		TimeKeep tk = new TimeKeep();
		tk.showTimeKeeping();
		tk.setCurrentTime();
		assertEquals(1,tk.get_flag());
		assertEquals(0,tk.getCursor());
	}

	@Test
	void setActiveFunctiontest(){
		TimeKeep tk = new TimeKeep();
		tk.setActiveFunction();
		assertEquals(2,tk.get_flag());
	}

	@Test
	void get_flagtest(){
		TimeKeep tk = new TimeKeep();
		assertEquals(null,tk.gettime());
	}

	@Test
	void getCursortest(){
		TimeKeep tk = new TimeKeep();
		assertEquals(null,tk.getRealTime());
	}

	@Test
	void addsecondstest(){
		TimeKeep tk = new TimeKeep();
		tk.showTimeKeeping();
		Calendar current_time = (Calendar) tk.gettime().clone();
//		System.out.println("currenttiem " +current_time.get(Calendar.SECOND));
		tk.addseconds();
		assertEquals(current_time.get(Calendar.SECOND)+1,tk.gettime().get(Calendar.SECOND));
	}

	@Test
	void moveCursorTimetest(){
		TimeKeep tk = new TimeKeep();
		tk.moveCursor_time();
		int cur_cursor = tk.getCursor();
		assertEquals(0,cur_cursor);
	}

	@Test
	void plusTime_timetest(){
		TimeKeep tk = new TimeKeep();
		JButton button = new JButton();
		tk.showTimeKeeping();
		button.setText("Button1");//set모드
		tk.work(button);
		Calendar setting_time = (Calendar) tk.gettime().clone();
		button.setText("Button2");//1증가
		tk.work(button);
		button.setText("Button4");//확인
		tk.work(button);
		assertEquals(setting_time.get(Calendar.MONTH)+1,tk.gettime().get(Calendar.MONTH));
	}

	@Test
	void confirmTimetest(){
		TimeKeep tk = new TimeKeep();
		JButton button = new JButton();
		tk.showTimeKeeping();
		button.setText("Button1");//settime
		tk.work(button);
		button.setText("Button4");//confirm
		tk.work(button);
		assertEquals(0,tk.get_flag());
		assertEquals(-1,tk.getCursor());
	}

}
