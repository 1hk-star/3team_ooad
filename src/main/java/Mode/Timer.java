package Mode;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;

import Format.Format;
import UI.Button;

public class Timer extends Mode{
	
	int flag_set = 0; //셋 모드이다, 아니다.
	int flag_sp = 0; //start상태인지 pause상태인지
	Calendar timer_time = null; //알람 설정 시간.
	Calendar pre_time = null; //이전정보 가지고 있음.
	
	Queue<Integer> cusorQ = new LinkedList<Integer>();
	private int cur_cursor = -1;
	
	public Timer() {
		cusorQ.offer(3);
		cusorQ.offer(4);
		cusorQ.offer(5);
	}

    @Override
    public void work(JButton button) {
    	String text = button.getText();
    	if(text.equals("Button1")) {
    		if(flag_set == 0)
    			setTimer();
    		else {
    			moveCursor_timer();
    		}
    	}
    	else if(text.equals("Button2")) {
    		//set Activeif
    		if(flag_set == 1) {
    			plusTimer();
    		}else {
    			pauseTimer();
    		}
  
    	}
    	else if(text.equals("Button3")) {
    		//changemode
    		cusorQ.clear();
    		cusorQ.offer(3);
			cusorQ.offer(4);
			cusorQ.offer(5);
    		flag_set =0;
    		cur_cursor = -1;
    		timer_time = null;
    	}
    	else if(text.equals("Button4")) {
    		if(flag_set == 1) {
    			confirmTimer();
    		}else {
    			stopTimer();
    		}
    	}
    	else {
    		System.err.println("error button");
    	}
    }
    
    public Calendar getTimer() {
    	if(flag_set == 0)
    		return pre_time;
    	return timer_time;
    }
    
    public int getCursor() {
    	return cur_cursor;
    }

    public void showTimer(){
    	if(pre_time != null)
    		timer_time = (Calendar)pre_time.clone(); 
    }

    public void startTimer(){

    }

    public void stopTimer(){
    	
    }

    public void pauseTimer(){

    }

    public void setTimer(){
    	if(flag_set == 0) { // 시간 설정 안했다.
    		flag_set = 1;
    		cur_cursor = cusorQ.poll(); //시부터 설정하셈.
    		if(pre_time == null) {
    			timer_time = Calendar.getInstance();
    			timer_time.set(Calendar.HOUR_OF_DAY, 0);
    			timer_time.set(Calendar.MINUTE, 0);
    			timer_time.set(Calendar.SECOND, 0);
    		}
    		else {
    			timer_time = (Calendar)pre_time.clone(); 
    		}
    	}
    	else { // 시간 설정 전에 했음.
    	}
    }

    public void moveCursor_timer(){
    	cusorQ.offer(cur_cursor);
    	cur_cursor = cusorQ.poll();
    }

    public void plusTimer(){
    	switch(cur_cursor) {
    	case 3:
    		int hour = timer_time.get(Calendar.HOUR_OF_DAY);
    		timer_time.set(Calendar.HOUR_OF_DAY, (hour+1)%24);
    	break;
    	case 4:
    		int mit = timer_time.get(Calendar.MINUTE);
    		timer_time.set(Calendar.MINUTE, (mit+1)%60);
    	break;
    	case 5:
    		int sec = timer_time.get(Calendar.SECOND);
    		timer_time.set(Calendar.SECOND, (sec+1)%60);
    	break;
    	default:
    	System.err.println("cursor add 에러임.");
    	break;
    	}
    }

    public boolean confirmTimer(){
    	//설정 완료
    	flag_set = 0;
    	cur_cursor = -1;
    	pre_time = (Calendar) timer_time.clone();
        return false;
    }



}
