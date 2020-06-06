import static org.junit.jupiter.api.Assertions.*;

import Controller.Watch;
import Mode.Dday;
import org.junit.jupiter.api.Test;

import javax.swing.*;

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

}
