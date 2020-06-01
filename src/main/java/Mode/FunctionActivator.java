package Mode;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;

import UI.Button;

public class FunctionActivator extends Mode{
	
	private boolean[] active_function = new boolean[6];
	private int active_count = 4;
	private int cur_cursor = -1;
    // -1 커서 위치 없음.
    // 0 timekeeping
    // 1 alarm
	// 2 worldtime
    // 3 stopwatch
    // 4 dday
    // 5 timer
	Queue<Integer> cursorQ = new LinkedList<Integer>();
	Queue<Integer> modeQ = new LinkedList<Integer>();
	public FunctionActivator(Queue<Integer> modeQ) {
		this.modeQ = modeQ;
		for(int i = 0; i < 6; i++) {
			cursorQ.offer(i);
			if(i < 4)
				active_function[i] = true;
			else
				active_function[i] = false;
		}
	}
    @Override
    public void work(JButton button) {
    	String text = button.getText();
    	if(text.equals("Button1")) {
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
    		confirmActive();
    	}
    	else {
    		System.err.println("error button");
    	}
    }
    
    public void setActivateFunction(){
    	
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
    	active_function[cur_cursor] = active_function[cur_cursor] ? true : false;
    }

    public boolean confirmActive(){
    	cursorQ.clear();
		cursorQ.offer(0);
		cursorQ.offer(1);
		cursorQ.offer(2);
		cursorQ.offer(3);
		cursorQ.offer(4);
		cursorQ.offer(5);
		cur_cursor= -1;
        return true;
    }
    
    public boolean get_active(int num) {
    	return active_function[num];
    }
    
    public int getCursor() {
    	return cur_cursor;
    }
}
