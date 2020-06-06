import static org.junit.jupiter.api.Assertions.*;

import Controller.Watch;
import Mode.Dday;
import Type.dday_data;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Calendar;

class DdayTest {

	@Test
	void setDdayTest() {
		Watch watch=new Watch();
		Dday d=new Dday();

		d.setFlag_set(0);
		d.setSetting_page(null);

		JButton button=new JButton();
		button.setText("Button1");
		d.work(button);

		assertEquals(1,d.getFlag_set());
		assertEquals(0,d.getCur_cursor());

		assertEquals("AAA",d.getSetting_page().get_memo());
	}

	@Test
	void moveCursor_Dday_Test(){
		Watch watch=new Watch();
		Dday d=new Dday();

		//if && if
		d.setCur_cursor(2);

		d.setString_cur(2);

		d.setFlag_set(1);
		d.setSetting_page(null);

		JButton button=new JButton();
		button.setText("Button1");
		d.work(button);

		assertEquals(0,d.getString_cur());
		assertEquals(0,d.getCur_cursor());
		//if&&else
		d.setCur_cursor(2);
		d.setString_cur(1);
		d.setFlag_set(1);
		d.setSetting_page(null);


		d.work(button);

		assertEquals(2,d.getString_cur());
		assertEquals(2,d.getCur_cursor());

		d.setString_cur(0);

		//else

		d.setCur_cursor(7);
		d.setFlag_set(1);
		d.setSetting_page(null);


		d.work(button);

		assertEquals(1,d.getCur_cursor());

	}

	@Test
	void showNextDdayTest(){
		Watch watch=new Watch();
		Dday d=new Dday();

		d.setFlag_set(0);
		d.setSetting_page(null);

		JButton button=new JButton();
		button.setText("Button1");
		d.work(button);

		dday_data current_page;

		current_page = new dday_data();

		current_page.set_memo(d.getSetting_page().get_memo());

		d.setCurrent_page(current_page);

		 button=new JButton();
		button.setText("Button2");
		d.work(button);

		assertEquals("AAA",d.getCurrent_page().get_memo());

	}

	@Test
	void plusDayTest(){
		Watch watch=new Watch();
		Dday d=new Dday();

		d.setFlag_set(1);

		d.setCur_cursor(0);
		dday_data setting_page=new dday_data();
		Calendar cal= Calendar.getInstance();
		cal.set(Calendar.MONTH,10);

		setting_page.set_cal(cal);
		setting_page.set_memo("aaa");

		d.setSetting_page(setting_page);

		JButton button=new JButton();
		button.setText("Button2");
		d.work(button);

		assertEquals(11,d.getSetting_page().get_cal().get(Calendar.MONTH));
	}

	@Test
	void deleteDdayTest(){

		Watch watch=new Watch();
		Dday d=new Dday();

		d.setFlag_set(0);

		dday_data current_page;

		current_page = new dday_data();

		current_page.set_memo("111");

		d.setCurrent_page(current_page);
		d.getDdayQ().clear();

		JButton button=new JButton();
		button.setText("Button4");
		d.work(button);

		assertEquals(null,d.getCurrent_page());
	}

	@Test
	void confirmDdayTest(){

		Watch watch=new Watch();
		Dday d=new Dday();

		d.setFlag_set(1);


		dday_data setting_page=new dday_data();
		Calendar cal= Calendar.getInstance();
		cal.set(Calendar.MONTH,10);

		setting_page.set_cal(cal);
		setting_page.set_memo("no1");
		d.setSetting_page(setting_page);


		JButton button=new JButton();
		button.setText("Button4");
		d.work(button);

		assertEquals("no1",d.getCurrent_page().get_memo());
	}

}
