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

}
