package Mode;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;

import Type.watch_Type;
import UI.Button;

public class FunctionActivator extends Mode{
	
	private boolean[] active_function = new boolean[5];
	private String[] active_function_name = new String[5];
	private int active_count = 0;
	private int position = 0;
    // -1 Ŀ�� ��ġ ����.
    // 0 alarm
	// 1 worldtime
    // 2 stopwatch
    // 3 dday
    // 4 timer
	Queue<Integer> cursorQ = new LinkedList<Integer>();
	Queue<Integer> modeQ = new LinkedList<Integer>();
	public FunctionActivator(Queue<Integer> modeQ) {
		active_function_name[0] = "arm";
		active_function_name[1] = "wdt";
		active_function_name[2] = "stw";
		active_function_name[3] = "ddy";
		active_function_name[4] = "tmr";
		this.modeQ = modeQ;
		for(int i = 0; i < 5; i++) {
			cursorQ.offer(i);
			if(i < 3)
				active_function[i] = true;
			else
				active_function[i] = false;
		}
	}
    @Override
    public void work(JButton button) {
    	String text = button.getText();
    	if(text.equals("Button1")) {
			nextActivateFunction();
    	}
    	else if(text.equals("Button2")) {
    		onOffFunction();
    	}
    	else if(text.equals("Button3")) {
    		// mode
    	}
    	else if(text.equals("Button4")) {
    		// end
    		active_count = 0;
    		for (int i = 0; i < active_function.length; i++) {
				if(active_function[i] == true) {
					active_count++;
				}
			}
    		System.out.println(active_count);
    		if(active_count == 3) {
        		confirmActive();
    		}
    	}
    	else {
    		System.err.println("error button");
    	}
    }
    
    public void nextActivateFunction(){
    	position++;
		if(position == 5)
			position = 0;
    }

    public void onOffFunction(){
    	active_function[position] = active_function[position] ? false : true;
    }

    public boolean confirmActive(){
		cursorQ.clear();
		cursorQ.offer(0);
		cursorQ.offer(1);
		cursorQ.offer(2);
		cursorQ.offer(3);
		cursorQ.offer(4);
		position = 0;
        return true;
    }
    
    public boolean get_active(int num) {
    	active_count = 0;
    	for (int i = 0; i < active_function.length; i++) {
			if(active_function[i] == true) {
				active_count++;
			}
		}
    	return active_function[num];
    }
    
    public String get_active_name(int num) {
    	return active_function_name[num];
    }
    
    public int get_active_count() {
    	return active_count;
    }
    
    public Queue<Integer> get_modeQ() {
    	modeQ.clear();
		for (int i = 0; i < active_function.length; i++) {
			if(active_function[i] == true) {
				modeQ.offer(i + 1);
			}
		}
    	return modeQ;
    }
    
    public int get_position() {
    	return position;
    }
}
