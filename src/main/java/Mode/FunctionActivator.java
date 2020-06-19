package Mode;


import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;


public class FunctionActivator extends Mode{
	
	private boolean[] active_function = new boolean[5];
	private String[] active_function_name = new String[5];
	private int active_count = 0;
	private int position = 0;
    // -1 Ŀ�� ǥ�� X
    // 0 alarm
	// 1 worldtime
    // 2 stopwatch
    // 3 dday
    // 4 timer
	private Queue<Integer> modeQ = new LinkedList<Integer>();
	
	public FunctionActivator(Queue<Integer> modeQ) {
		active_function_name[0] = "arm";
		active_function_name[1] = "wdt";
		active_function_name[2] = "stw";
		active_function_name[3] = "ddy";
		active_function_name[4] = "tmr";
		this.modeQ = modeQ;
		for(int i = 0; i < 5; i++) {
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
    		//�������� �ʴ� ���.
    	}
    	else if(text.equals("Button4")) {
    		// end
    		active_count = 0;
    		for (int i = 0; i < active_function.length; i++) {
				if(active_function[i]) {
					active_count++;
				}
			}
    		if(active_count == 3) {
        		confirmActive();
    		}
    	}
    	else {
    		System.err.println("error button");
    	}
    }
    
    private void nextActivateFunction(){
    	position++;
		if(position == 5)
			position = 0;
    }

    private void onOffFunction(){
    	active_function[position] = active_function[position] ? false : true;
    }

    private void confirmActive(){
		position = 0;
    }
    
    public boolean get_active(int num) {
    	active_count = 0;
    	for (int i = 0; i < active_function.length; i++) {
			if(active_function[i] ) {
				active_count++;
			}
		}
    	return active_function[num];
    }
    
    public String get_active_name(int num) {
    	return active_function_name[num];
    }
    
    public int get_active_count() {
		active_count = 0;
		for (int i = 0; i < active_function.length; i++) {
			if(active_function[i]) {
				active_count++;
			}
		}
		return active_count;
    }
    
    public Queue<Integer> get_modeQ() {
    	modeQ.clear();
		for (int i = 0; i < active_function.length; i++) {
			if(active_function[i] ) {
				modeQ.offer(i + 1);
			}
		}
    	return modeQ;
    }
    
    public int get_position() {
    	return position;
    }

    //Test를 위해 추가한 함수
	public boolean get_active_function(int position){
		return active_function[position];
	}
}
