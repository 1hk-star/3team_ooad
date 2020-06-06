import static org.junit.jupiter.api.Assertions.*;

import Mode.Buzzer;
import Mode.WorldTime;
import org.junit.jupiter.api.Test;

class BuzzerTest {

	@Test
	void testgetbuzzer() {
		Buzzer bz = new Buzzer();
		int result = bz.getbuzzer();
		assertEquals(0, result);
	}
	@Test
	void testonBuzzer(){
		Buzzer bz = new Buzzer();
		bz.onBuzzer(1);
		assertEquals(1,bz.getbuzzer());
		assertEquals(15,bz.getLeftTime());

		bz.onBuzzer(2);
		assertEquals(2,bz.getbuzzer());
		assertEquals(15,bz.getLeftTime());
	}

	@Test
	void testsubTimeBuzzer(){
		Buzzer bz = new Buzzer();
		bz.onBuzzer(1);
		assertEquals(15,bz.getLeftTime());

		bz.subTimeBuzzer();
		assertEquals(14,bz.getLeftTime());
	}

	@Test
	void turnOffBuzzer(){
		Buzzer bz = new Buzzer();
		bz.onBuzzer(1);
		assertEquals(1,bz.getbuzzer());

		bz.turnOffBuzzer();
		assertEquals(0,bz.getbuzzer());
	}

}
