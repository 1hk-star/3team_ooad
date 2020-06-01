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
	public FunctionActivator() {
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
    	cursorQ.offer(cur_cursor);
    	cur_cursor = cursorQ.poll();
    }

    public void onOffFunction(){
    	String s = active_function[cur_cursor] ? "on" : "off";
    }

    public boolean confirmActive(){

        return false;
    }
}
