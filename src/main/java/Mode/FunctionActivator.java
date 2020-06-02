package Mode;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;

import Type.watch_Type;
import UI.Button;

public class FunctionActivator extends Mode{
	
	private int flag_set = 0;
	private boolean[] active_function = new boolean[5];
	private int active_count = 0;
	private int cur_cursor = -1;
    // -1 커서 위치 없음.
    // 0 alarm
	// 1 worldtime
    // 2 stopwatch
    // 3 dday
    // 4 timer
	Queue<Integer> cursorQ = new LinkedList<Integer>();
	Queue<Integer> modeQ = new LinkedList<Integer>();
	public FunctionActivator(Queue<Integer> modeQ) {
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
    		System.out.println(flag_set);
    		if(flag_set == 0) {
    			setActivateFunction();
    		}
    		moveCursor_active();
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
    
    public void setActivateFunction(){
    	if(flag_set == 0) { // 시간 설정 안했다.
    		flag_set = 2;
    	}
    	else { // 시간 설정 전에 했음.
    		
    	}
    }

    public void moveCursor_active(){
    	if(cur_cursor == -1) {
    		cur_cursor = cursorQ.poll();
    	}
    	else {
        	cursorQ.offer(cur_cursor);
        	cur_cursor = cursorQ.poll();
    	}
    }

    public void onOffFunction(){
    	if(cur_cursor != -1) {
    		active_function[cur_cursor] = active_function[cur_cursor] ? false : true;
    	}
    }

    public boolean confirmActive(){
    	if (flag_set == 2) {
    		flag_set = 0;
    		cursorQ.clear();
    		cursorQ.offer(0);
    		cursorQ.offer(1);
    		cursorQ.offer(2);
    		cursorQ.offer(3);
    		cursorQ.offer(4);
    		modeQ.clear();
    		modeQ.offer(watch_Type.TIMEKEEPING.ordinal());
    		cur_cursor= -1;
    	}
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
    
    public int getCursor() {
    	return cur_cursor;
    }
    
    public int get_active_count() {
    	return active_count;
    }
    
    public Queue<Integer> get_modeQ() {
		for (int i = 0; i < active_function.length; i++) {
			if(active_function[i] == true) {
				modeQ.offer(i + 1);
			}
		}
    	return modeQ;
    }
    
    public int get_flag() {
    	return flag_set;
    }
}
