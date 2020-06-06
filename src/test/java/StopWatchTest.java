import static org.junit.jupiter.api.Assertions.*;

import Controller.Watch;
import Mode.StopWatch;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Calendar;


class StopWatchTest {

	@Test
	void resetStopWatchTest() {
		Watch watch=new Watch();
		StopWatch st=new StopWatch();

		st.setStart(12);
		st.setEnd(22);
		st.setElapse(10);
		st.setElapsePrevious(1);
		st.setOn(true);

		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR, 9);
		cal.set(Calendar.MINUTE, 2);
		cal.set(Calendar.SECOND, 5);

		st.setCal(cal);

		Calendar calLap=Calendar.getInstance();
		calLap.set(Calendar.HOUR, 5);
		calLap.set(Calendar.MINUTE, 2);
		calLap.set(Calendar.SECOND, 2);

		st.setCalLap(calLap);

		assertEquals(12,st.getStart());
		assertEquals(22,st.getEnd());
		assertEquals(10,st.getElapse());
		assertEquals(1,st.getElapsePrevious());
		assertEquals(true,st.isOn());

		assertEquals(9,st.getCal().get(Calendar.HOUR));
		assertEquals(2,st.getCal().get(Calendar.MINUTE));
		assertEquals(5,st.getCal().get(Calendar.SECOND));
		assertEquals(5,st.getCalLap().get(Calendar.HOUR));
		assertEquals(2,st.getCalLap().get(Calendar.MINUTE));
		assertEquals(2,st.getCalLap().get(Calendar.SECOND));

		JButton button=new JButton();
		button.setText("Button4");
        st.work(button);

		assertEquals(0,st.getStart());
		assertEquals(0,st.getEnd());
		assertEquals(0,st.getElapse());
		assertEquals(0,st.getElapsePrevious());
		assertEquals(false,st.isOn());

		assertEquals(0,st.getCal().get(Calendar.HOUR));
		assertEquals(0,st.getCal().get(Calendar.MINUTE));
		assertEquals(0,st.getCal().get(Calendar.SECOND));
		assertEquals(0,st.getCalLap().get(Calendar.HOUR));
		assertEquals(0,st.getCalLap().get(Calendar.MINUTE));
		assertEquals(0,st.getCalLap().get(Calendar.SECOND));


	}

}
