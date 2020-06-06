import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Mode.WorldTime;

class WorldTimeTest {

	@Test
	void getCursorTest() {
		WorldTime world = new WorldTime();
		int result = world.getCursor();
		assertEquals(-1, result);
	}
}
