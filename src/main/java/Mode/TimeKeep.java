package Mode;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;

import Controller.Watch;
import Format.Format;
import Type.watch_Type;
import UI.Button;

public class TimeKeep extends Mode{
	
	int flag_set = 0; // 시간 설정 안했다.
	Calendar current_time;
	Calendar setting_time;
	Long diff = null;
	int tmp_flag = 0;
	private int cur_cursor = -1;
	    // -1 커서 위치 없음.
	    // 0 월
	    // 1 일
		// 2 요일
	    // 3 시
	    // 4 분
	    // 5 초
		// 6 시
		// 7 분(연)
	 	// 8 초
	Queue<Integer> cusorQ = new LinkedList<Integer>();
	public TimeKeep() {
		cusorQ.offer(0);
		cusorQ.offer(1);
		cusorQ.offer(3);
		cusorQ.offer(4);
		cusorQ.offer(5);
		cusorQ.offer(7);
	}
	
    @Override
    public void work(JButton button) {
    	
    	String text = button.getText();
    	if(text.equals("Button1")) {
    		if(flag_set == 0)
    			setCurrentTime();
    		else {
    			moveCursor_time();
    		}
    	}
    	else if(text.equals("Button2")) {
    		//set Active
    		if(flag_set == 1) {
    			plusTime_time();
    		}
    	}
    	else if(text.equals("Button3")) {
    		//changemode
    		
    	}
    	else if(text.equals("Button4")) {
    		//end
    		confirmTime();
    	}
    	else {
    		System.err.println("error button");
    	}
    }

    public void showTimeKeeping(){
    	
    	//사용자가 시간 설정을 안했다.
    	//format에 현재 시간 저장.
    	current_time = Calendar.getInstance();
    	if(diff == null) {
    		//current_time = Calendar.getInstance();
    		return;
    	}else {
    		current_time.add(Calendar.SECOND, Integer.parseInt(String.valueOf(Math.round(diff/1000))));
    	}
    	
    	
    	//사용자가 시간 설정을 했었다.
    	//(aaa = 현재 시간 - 사용자 설정 시간) 저장
    	// 현재시간 - aaa format에 저장.
    	
    }
    
    public Calendar gettime() {
    	return current_time;
    }

   
    public void setCurrentTime(){
    	if(flag_set == 0) { // 시간 설정 안했다.
    		flag_set = 1;
    		cur_cursor = cusorQ.poll(); //월부터 설정하셈.
    		if(tmp_flag == 0)
    			setting_time = Calendar.getInstance();
    		tmp_flag = 1;
    	}
    	else { // 시간 설정 전에 했음.
    		
    	}
    }
    
    public int get_flag() {
    	return flag_set;
    }
    
    public void addseconds(){
    	current_time.add(Calendar.SECOND, 1);
    }
    
    public int getCursor() {
    	return cur_cursor;
    }

    public void moveCursor_time(){
    	cusorQ.offer(cur_cursor);
    	cur_cursor = cusorQ.poll();
    }

    public void plusTime_time(){
    	System.out.println("cur_cusor: "+cur_cursor);
    	switch(cur_cursor) {
    	case 0:
    		setting_time.add(Calendar.MONTH, 1);
    	break;
    	case 1:
    		setting_time.add(Calendar.DATE, 1);
    	break;
    	case 3:
    		setting_time.add(Calendar.HOUR, 1);
    	break;
    	case 4:
    		setting_time.add(Calendar.MINUTE, 1);
    	break;
    	case 5:
    		setting_time.add(Calendar.SECOND, 1);
    	break;
    	case 7:
    		setting_time.add(Calendar.YEAR, 1);
    	break;
    	default:
    	System.err.println("cursor add 에러임.");
    	break;
    	}
    }

    public boolean confirmTime(){
    	if(flag_set == 1) {
			flag_set = 0;
			cusorQ.clear();
			cusorQ.offer(0);
			cusorQ.offer(1);
			cusorQ.offer(3);
			cusorQ.offer(4);
			cusorQ.offer(5);
			cusorQ.offer(7);
			
			//내가 정한값은 current_time
			Calendar temp = Calendar.getInstance();
			//temp : end 누른 시간, current_tiem : 설정 시간
			//diff가 음수면 미래임, diff가 양수면 과거임.
			diff = temp.getTimeInMillis() - setting_time.getTimeInMillis();
		}
    	
        return  true;
    }

    public String setDday(){

        return "1";
    }


}
