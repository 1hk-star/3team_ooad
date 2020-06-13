import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Mode.WorldTime;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class WorldTimeTest {
	@Test
	void changeCountryTest(){
		WorldTime worldTime = new WorldTime();
		JButton jbutton = new JButton("");
		jbutton.setText("Button1");

		// init
		int cursor = -1;
		assertEquals(cursor, worldTime.getCursor());

		// after changeCountry() Method
		worldTime.work(jbutton);
		cursor = 2;
		assertEquals(cursor, worldTime.getCursor());
	}
	@Test
	void nextCountryTest(){
		WorldTime worldTime = new WorldTime();
		JButton jbutton = new JButton("");
		jbutton.setText("Button2");

		//init
		Queue<String> countriesQ = new LinkedList();
		countriesQ.offer("LIS");
		countriesQ.offer("LON");
		countriesQ.offer("ROM");
		countriesQ.offer("TYO");
		countriesQ.offer("LAX");
		countriesQ.offer("DEN");
		assertEquals(countriesQ, worldTime.get_countriesQ());

		String country = "SEL";
		assertEquals(country, worldTime.getKey());

		//after nextCountry() Method
		countriesQ.offer(country);
		country = countriesQ.poll();
		jbutton.setText("Button1");
		worldTime.work(jbutton);
		jbutton.setText("Button2");
		worldTime.work(jbutton);
		assertEquals(countriesQ, worldTime.get_countriesQ());
		assertEquals(country, worldTime.getKey());
	}
	@Test
	void changeModeTest(){
		WorldTime worldTime = new WorldTime();
		JButton jbutton = new JButton("");
		jbutton.setText("Button3");

		worldTime.work(jbutton);
		Queue<String> countriesQ = new LinkedList();
		countriesQ.offer("LIS");
		countriesQ.offer("LON");
		countriesQ.offer("ROM");
		countriesQ.offer("TYO");
		countriesQ.offer("LAX");
		countriesQ.offer("DEN");
		assertEquals(countriesQ, worldTime.get_countriesQ());

		String country = "SEL";
		assertEquals(country, worldTime.getKey());

		int cursor = -1;
		assertEquals(cursor, worldTime.getCursor());
	}
	@Test
	void confirmCountryTest(){
		WorldTime worldTime = new WorldTime();
		JButton jbutton = new JButton("");

		//changeCountry()
		jbutton.setText("Button1");
		worldTime.work(jbutton);

		//nexCountry()
		jbutton.setText("Button2");
		worldTime.work(jbutton);

		//confirmCountry
		jbutton.setText("Button4");
		worldTime.work(jbutton);

		int cursor = -1;
		assertEquals(cursor, worldTime.getCursor());

		Queue<String> countriesQ = new LinkedList();
		countriesQ.offer("LON");
		countriesQ.offer("ROM");
		countriesQ.offer("TYO");
		countriesQ.offer("LAX");
		countriesQ.offer("DEN");
		countriesQ.offer("SEL");
		assertEquals(countriesQ, worldTime.get_countriesQ_temp());

		String country = "LIS";
		assertEquals(country, worldTime.getKey());
	}
	@Test
	void get_keyTest(){
		WorldTime worldTime = new WorldTime();
		String initCountry = "SEL";
		assertEquals(initCountry, worldTime.getKey());
	}
	@Test
	void get_valueTest(){
		WorldTime worldTime = new WorldTime();
		Map<String, String> countries = new HashMap<String, String>();
		countries.put("SEL", "Asia/Seoul");
		String value = countries.get("SEL");

		assertEquals(value, worldTime.getValue());
	}
	@Test
	void getCursorTest(){
		WorldTime worldTime = new WorldTime();
		int cursor = -1;
		assertEquals(cursor, worldTime.getCursor());
	}
}