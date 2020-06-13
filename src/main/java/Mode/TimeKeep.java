package Mode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JButton;

import Controller.Watch;
import Type.watch_Type;

public class TimeKeep extends Mode{
	private List<String> dday_memo;
	private List<String> origin;
	private int flag_set = 0; //세팅모드인지 아닌지 플래그.
	private Calendar current_time;
	private Calendar setting_time;
	private Long diff = null;
	private int tmp_flag = 0;
	private int cur_cursor = -1;
	    // -1 기본값
	    // 0 월
	    // 1 일
		// 2 요일
	    // 3 시
	    // 4 분
	    // 5 초
		// 6 시
		// 7 분(연도)
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
    	if(flag_set == 2) {
    		flag_set = 0;
    	}
    	String text = button.getText();
    	if(text.equals("Button1")) {
    		if(flag_set == 0)
    			setCurrentTime();
    		else if(flag_set == 1) {
    			moveCursor_time();
    		}
    	}
    	else if(text.equals("Button2")) {
    		//set Active
    		if(flag_set == 0) {
    			setActiveFunction();
    		}
    		else {
    			plusTime_time();
    		}
    	}
    	else if(text.equals("Button3")) {
    		//changemode
    		cusorQ.clear();
			cusorQ.offer(0);
			cusorQ.offer(1);
			cusorQ.offer(3);
			cusorQ.offer(4);
			cusorQ.offer(5);
			cusorQ.offer(7);
    		flag_set =0;
    		cur_cursor = -1;
    		tmp_flag =0;
    	}
    	else if(text.equals("Button4")) {
    		//end
    		if(flag_set == 2)
    			flag_set = 0;
    		confirmTime();
    	}
    	else {
    		System.err.println("error button");
    	}
    }

    public void showTimeKeeping(){
    	
    	current_time = Calendar.getInstance();
    	if(diff == null) { 
    		//current_time = Calendar.getInstance();
    		return;
    	}else {
    		current_time.add(Calendar.SECOND, Integer.parseInt(String.valueOf(Math.round(diff/1000))));
    	}
    }
    
    public String getdday() {
    	if( dday_memo == null || dday_memo.isEmpty())
    		return null;
    	String tmp = dday_memo.get(0);
    	dday_memo.remove(0);
    	dday_memo.add(tmp);
    	return tmp;
    }

	public List<String> getoriginTime() {
		return origin;
	}
	public List<String> getddaymemoTime() {
		return dday_memo;
	}
    public void setdday(List<String> str) {
    	if(str == null) {
    		origin = null;
    		dday_memo = null;
    	}
    	else if(origin == null || dday_memo == null) {
    		origin = new ArrayList<String>(str);
    		dday_memo = new ArrayList<String>(str);
    		Collections.copy(origin, str);
        	Collections.copy(dday_memo, str);
    	}
    	else if(str.containsAll(origin)) { //내가 가지고 있는 정보가 같을때.
    		return;
    	}else { //다를때
    		Collections.copy(origin, str);
        	Collections.copy(dday_memo, str);
    	}
    	
    }
    public Calendar gettime() {
    	if(flag_set == 1)
    		return setting_time;
    	return current_time;
    }
    
    public Calendar getRealTime() {
    	return current_time;
    }
   
    public void setCurrentTime(){
    	if(flag_set == 0) {
    		flag_set = 1;
    		cur_cursor = cusorQ.poll(); 
    		if(tmp_flag == 0) {
    			setting_time = (Calendar) current_time.clone();
    			tmp_flag = 1;
    		}
    	}
    }
    
    public void setActiveFunction() {
    	if(flag_set == 0) {
    		flag_set = 2;
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
//private
    public void moveCursor_time(){
    	cusorQ.offer(cur_cursor);
    	cur_cursor = cusorQ.poll();
    }

    public void plusTime_time(){
    	switch(cur_cursor) {
    	case 0:
    		//setting_time.add(Calendar.MONTH, 1);
    		int month = setting_time.get(Calendar.MONTH);
    		int dt = setting_time.get(Calendar.DATE);
			setting_time.set(Calendar.DATE, 1);
			setting_time.set(Calendar.MONTH, (month+1)%12);
			int max = setting_time.getActualMaximum(Calendar.DAY_OF_MONTH);
    		if(dt > max){
				setting_time.set(Calendar.DATE, 1);
			}
    		else{
				setting_time.set(Calendar.DATE, dt);
			}
    	break;
    	case 1:
    		int date = setting_time.get(Calendar.DATE);
    		int month2 = setting_time.get(Calendar.MONTH) + 1;
    		int year2 = setting_time.get(Calendar.YEAR);
    		if(month2 == 1 || month2 == 3 || month2 == 5 || month2 == 7 || month2 == 8 || month2 == 10 || month2 == 12) {
    			// 31일
    			date = (date % 31) + 1;
    		} else if(month2 == 4 || month2 == 6 || month2 == 9 || month2 == 11) {
    			// 30일
    			date = (date % 30) + 1;
    		} else if(month2 == 2) {
    			if(year2 % 4 == 0 || (year2 % 4 == 0 && year2 % 100 == 0 && year2 % 400 == 0)) {
    				// 윤년
    				date = (date % 29) + 1;
    			}
    			else if (year2 % 4 == 0 && year2 % 100 == 0) {
    				//  1 ~ 28
    				date = (date % 28) +1;
    			}
    		}
    		setting_time.set(Calendar.DATE, date);
    	break;
    	case 3:
    		int hour = setting_time.get(Calendar.HOUR_OF_DAY);
    		setting_time.set(Calendar.HOUR_OF_DAY, (hour+1)%24);
    	break;
    	case 4:
    		int mit = setting_time.get(Calendar.MINUTE);
    		setting_time.set(Calendar.MINUTE, (mit+1)%60);
    	break;
    	case 5:
    		int sec = setting_time.get(Calendar.SECOND);
    		setting_time.set(Calendar.SECOND, (sec+1)%60);
    	break;
    	case 7:
    		//1980 ~ 2079
    		int year = setting_time.get(Calendar.YEAR);
    		if((year+1) > 2079) {
    			year = 1980;
    			setting_time.set(Calendar.YEAR, year);
    		}
    		else {
    			setting_time.set(Calendar.YEAR, year+1);
    		}
    		
    	break;
    	default:
    	System.err.println("cursor add ���ъ��.");
    	break;
    	}
    }

    private void confirmTime(){
    	if(flag_set == 1) {
			flag_set = 0;
			cusorQ.clear();
			cusorQ.offer(0);
			cusorQ.offer(1);
			cusorQ.offer(3);
			cusorQ.offer(4);
			cusorQ.offer(5);
			cusorQ.offer(7);
			cur_cursor= -1;

			Calendar temp = null;
			if(tmp_flag == 0) { 
				temp = Calendar.getInstance();
				tmp_flag = 1;
			}else {
				temp = current_time;
			}
			
			tmp_flag = 0;
			diff = setting_time.getTimeInMillis() - temp.getTimeInMillis();
			current_time.add(Calendar.SECOND, Integer.parseInt(String.valueOf(Math.round(diff/1000))));
		}
    }
}
