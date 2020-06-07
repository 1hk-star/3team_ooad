import static org.junit.jupiter.api.Assertions.*;

import Mode.FunctionActivator;
import Mode.WorldTime;
import Type.watch_Type;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;

class FunctionActivatorTest {

	@Test
	void nextActivateFunctionTest() {
		Queue<Integer> modeQ = new LinkedList<Integer>();
		modeQ.offer(watch_Type.ALARM.ordinal());
		modeQ.offer(watch_Type.WORLDTIME.ordinal());
		modeQ.offer(watch_Type.STOPWATCH.ordinal());
		FunctionActivator functionActivator = new FunctionActivator(modeQ);

		JButton jButton = new JButton("");
		jButton.setText("Button1");

		//init
		//position == 0
		int position = 0;
		assertEquals(position, functionActivator.get_position());

		//position 0 -> 1
		functionActivator.work(jButton);
		position = 1;
		assertEquals(position, functionActivator.get_position());

		// position 5 -> 0
		functionActivator.work(jButton);
		functionActivator.work(jButton);
		functionActivator.work(jButton);
		functionActivator.work(jButton);

		position = 0;
		assertEquals(position, functionActivator.get_position());
	}
	@Test
	void onOffFunctionTest() {
		Queue<Integer> modeQ = new LinkedList<Integer>();
		modeQ.offer(watch_Type.ALARM.ordinal());
		modeQ.offer(watch_Type.WORLDTIME.ordinal());
		modeQ.offer(watch_Type.STOPWATCH.ordinal());
		FunctionActivator functionActivator = new FunctionActivator(modeQ);

		JButton jButton = new JButton("");
		jButton.setText("Button2");

		int position = 0;
		boolean active_function = true;
		assertEquals(active_function ,functionActivator.get_active_function(position));

		functionActivator.work(jButton);
		active_function = false;
		assertEquals(active_function, functionActivator.get_active_function(position));
	}
	@Test
	void confirmActiveTest() {
		Queue<Integer> modeQ = new LinkedList<Integer>();
		modeQ.offer(watch_Type.ALARM.ordinal());
		modeQ.offer(watch_Type.WORLDTIME.ordinal());
		modeQ.offer(watch_Type.STOPWATCH.ordinal());
		FunctionActivator functionActivator = new FunctionActivator(modeQ);
		JButton jButton = new JButton("");

		//nextActivateFunction()
		//position 0 -> 1
		int position = 1;
		jButton.setText("Button1");
		functionActivator.work(jButton);
		assertEquals(position, functionActivator.get_position());

		//onOffFunction()
		//active_count 3 -> 2
		int active_count = 2;
		jButton.setText("Button2");
		functionActivator.work(jButton);
		assertEquals(active_count, functionActivator.get_active_count());

		//confirmActive - not confirm
		//position 1 -> 1
		position = 1;
		jButton.setText("Button4");
		functionActivator.work(jButton);
		assertEquals(active_count, functionActivator.get_active_count());
		assertEquals(position, functionActivator.get_position());

		//onOffFunction()
		//active_count 2 -> 3
		active_count = 3;
		jButton.setText("Button2");
		functionActivator.work(jButton);
		assertEquals(active_count, functionActivator.get_active_count());

		//confirmActive - confirm
		//position 1 -> 0
		position = 0;
		jButton.setText("Button4");
		functionActivator.work(jButton);
		assertEquals(active_count, functionActivator.get_active_count());
		assertEquals(position, functionActivator.get_position());
	}
	@Test
	void get_activeTest() {
		Queue<Integer> modeQ = new LinkedList<Integer>();
		modeQ.offer(watch_Type.ALARM.ordinal());
		modeQ.offer(watch_Type.WORLDTIME.ordinal());
		modeQ.offer(watch_Type.STOPWATCH.ordinal());
		FunctionActivator functionActivator = new FunctionActivator(modeQ);

		boolean active_function = true;
		int position = 0;
		assertEquals(active_function, functionActivator.get_active(position));
	}
	@Test
	void get_active_nameTest() {
		Queue<Integer> modeQ = new LinkedList<Integer>();
		modeQ.offer(watch_Type.ALARM.ordinal());
		modeQ.offer(watch_Type.WORLDTIME.ordinal());
		modeQ.offer(watch_Type.STOPWATCH.ordinal());
		FunctionActivator functionActivator = new FunctionActivator(modeQ);

		String active_function_name = "arm";
		int position = 0;
		assertEquals(active_function_name, functionActivator.get_active_name(position));
	}
	@Test
	void get_active_countTest() {
		Queue<Integer> modeQ = new LinkedList<Integer>();
		modeQ.offer(watch_Type.ALARM.ordinal());
		modeQ.offer(watch_Type.WORLDTIME.ordinal());
		modeQ.offer(watch_Type.STOPWATCH.ordinal());
		FunctionActivator functionActivator = new FunctionActivator(modeQ);

		int active_count = 3;
		assertEquals(active_count, functionActivator.get_active_count());
	}
	@Test
	void get_modeQTest() {
		Queue<Integer> modeQ = new LinkedList<Integer>();
		modeQ.offer(watch_Type.ALARM.ordinal());
		modeQ.offer(watch_Type.WORLDTIME.ordinal());
		modeQ.offer(watch_Type.STOPWATCH.ordinal());
		FunctionActivator functionActivator = new FunctionActivator(modeQ);

		assertEquals(modeQ, functionActivator.get_modeQ());
	}
	@Test
	void get_positionTest() {
		Queue<Integer> modeQ = new LinkedList<Integer>();
		modeQ.offer(watch_Type.ALARM.ordinal());
		modeQ.offer(watch_Type.WORLDTIME.ordinal());
		modeQ.offer(watch_Type.STOPWATCH.ordinal());
		FunctionActivator functionActivator = new FunctionActivator(modeQ);

		int position = 0;
		assertEquals(position, functionActivator.get_position());
	}
}
