import static org.junit.jupiter.api.Assertions.*;

import Mode.mTimer;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Calendar;

class TimerTest {

	@Test
	void getCursortest() {
		mTimer mtimer = new mTimer();
		int cursor = mtimer.getCursor();
		assertEquals(-1,cursor);
	}

	@Test
	void getFlagtest(){
		mTimer mtimer = new mTimer();
		int flag = mtimer.get_flag();
		assertEquals(0,flag);
	}

	@Test
	void getPauseFlagtest(){
		mTimer mtimer = new mTimer();
		int flag = mtimer.getPauseFlag();
		assertEquals(0,flag);
	}

	@Test
	void getTimerTimetest(){
		mTimer mtimer = new mTimer();
		Calendar c = mtimer.getTimerTime();
		assertEquals(null,c);
	}

	@Test
	void setTimertest(){
		mTimer mtimer = new mTimer();
		JButton button = new JButton();
		button.setText("Button1");
		mtimer.work(button);
		int flag = mtimer.get_flag();
		assertEquals(1,flag);
	}

	@Test
	void moveCursorTimertest(){
		mTimer mtimer = new mTimer();
		JButton button = new JButton();
		button.setText("Button1");//set모드
		mtimer.work(button);
		int cur_cursor = mtimer.getCursor();
		assertEquals(3,cur_cursor);
	}

	@Test
	void plusTimertest(){
		mTimer mtimer = new mTimer();
		JButton button = new JButton();
		button.setText("Button1");//set모드
		mtimer.work(button);
		button.setText("Button2");//1증가
		mtimer.work(button);
		button.setText("Button4");//확인
		mtimer.work(button);
		Calendar pre_time = mtimer.getTimer();
		assertEquals(1,pre_time.get(Calendar.HOUR_OF_DAY));
		assertEquals(0,pre_time.get(Calendar.MINUTE));
		assertEquals(0,pre_time.get(Calendar.SECOND));
	}

	@Test
	void confirmTimertest(){
		mTimer mtimer = new mTimer();
		JButton button = new JButton();
		button.setText("Button1");//set모드
		mtimer.work(button);
		button.setText("Button4");//확인
		mtimer.work(button);
		int flag_set = mtimer.get_flag();
		int cur_cursor = mtimer.getCursor();
		assertEquals(0,flag_set);
		assertEquals(-1,cur_cursor);
	}

	@Test
	void stopTimertest(){
		mTimer mtimer = new mTimer();
		JButton button = new JButton();
		button.setText("Button1");//set모드
		mtimer.work(button);
		button.setText("Button2");//1증가
		mtimer.work(button);
		button.setText("Button4");//확인
		mtimer.work(button);
		button.setText("Button4");//stop
		mtimer.work(button);
		Calendar pre_time = mtimer.getTimer();
		assertEquals(null,pre_time);

	}

	@Test
	void startTimertest(){
		mTimer mtimer = new mTimer();
		JButton button = new JButton();
		button.setText("Button1");//set모드
		mtimer.work(button);
		button.setText("Button2");//1증가
		mtimer.work(button);
		button.setText("Button4");//확인
		mtimer.work(button);
		button.setText("Button2");//start
		mtimer.work(button);
		int flag = mtimer.get_flag_sp();
		assertEquals(1,flag);
	}
	@Test
	void pauseTimertest(){
		mTimer mtimer = new mTimer();
		JButton button = new JButton();
		button.setText("Button1");//set모드
		mtimer.work(button);
		button.setText("Button2");//1증가
		mtimer.work(button);
		button.setText("Button4");//확인
		mtimer.work(button);
		button.setText("Button2");//start
		mtimer.work(button);
		button.setText("Button2");//pause
		mtimer.work(button);
		int flag = mtimer.get_flag_sp();
		assertEquals(0,flag);
	}

}
