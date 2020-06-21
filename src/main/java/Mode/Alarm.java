package Mode;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;

public class Alarm extends  Mode {
	
	private int flag_set = 0; //세팅 모드인지 아닌지
	private Calendar alarm_time = null; //현재 보고있는 설정된 알람 시간.
	private Calendar setting_time = null; //설정중인 시간.
	
	private Queue<Integer> cusorQ = new LinkedList<Integer>();
	private int cur_cursor = -1;

	private Queue<Calendar> alarmQ = new LinkedList<Calendar>();
	
	public Alarm() {
		cusorQ.offer(3);
		cusorQ.offer(4);
		cusorQ.offer(5);
	}
	
    @Override
    public void work(JButton button) {
    	String text = button.getText();
    	if(text.equals("Button1")) {
    		if(flag_set == 0)
    			setAlarm();
    		else {
    			moveCursor_alarm();
    		}
    	}
    	else if(text.equals("Button2")) {
    		//set Activeif
    		if(flag_set == 1) {
    			plusTime_alarm();
    		}else{
    			showNextAlarm();
			}
    	}
    	else if(text.equals("Button3")) {
    		//changemode
			if(alarm_time != null){
				alarmQ.offer(alarm_time);
				for(int i =0; i < alarmQ.size()-1 ; i ++) {
					Calendar tmp = alarmQ.poll();
					alarmQ.offer(tmp);
				}
			}
    		cusorQ.clear();
			cusorQ.offer(3);
			cusorQ.offer(4);
			cusorQ.offer(5);
    		flag_set =0;
    		cur_cursor = -1;
    		setting_time = null;
    	}
    	else if(text.equals("Button4")) {
    		if(flag_set == 1) {
    			confirmAlarm();
    		}else {
    			deleteAlarm();
    		}
    	}
    	else {
    		System.err.println("error button");
    	}
    }

    public void resetAlarm(){
		Queue<Calendar> alarmQ = new LinkedList<Calendar>();
		alarm_time = null;
		setting_time = null;
	}
	private void showNextAlarm(){
		if(alarm_time != null){
			alarmQ.offer(alarm_time);
			alarm_time = alarmQ.poll();
		}
	}
    public void showAlarm(){
    	if(alarmQ.isEmpty())
    		alarm_time = null;
    	else{
			alarm_time = alarmQ.poll();
		}
    }
    public int getCursor() {
    	return cur_cursor;
    }
    
    public Calendar getAlarm() {
    	if(flag_set == 0)
    		return alarm_time;
    	return setting_time;
    }
    
    public int cmpAlarm(Calendar cal) {

		System.out.println("cmpAlarm : "+cal.get(Calendar.SECOND)+", "+alarm_time+", "+alarmQ);
		Calendar tmp;
		int h2 = cal.get(Calendar.HOUR_OF_DAY);
		int m2 = cal.get(Calendar.MINUTE);
		int s2 = cal.get(Calendar.SECOND);
		if(alarm_time == null){
			return 0;
		}
		else{
			tmp = (Calendar) alarm_time.clone();
			int h1 = tmp.get(Calendar.HOUR_OF_DAY);
			int m1 = tmp.get(Calendar.MINUTE);
			int s1 = tmp.get(Calendar.SECOND);
			if(h1 == h2 && m1 == m2 && s1 == s2){
				return 1;
			}
		}
		int flag = 0;
		synchronized (alarmQ){
			if(alarmQ.isEmpty())
				return 0;
			for(int i =0; i < alarmQ.size(); i ++) {
				tmp = alarmQ.poll();

				int h1 = tmp.get(Calendar.HOUR_OF_DAY);
				int m1 = tmp.get(Calendar.MINUTE);
				int s1 = tmp.get(Calendar.SECOND);
				if(h1 == h2 && m1 == m2 && s1 == s2){
					flag = 1;
				}
				alarmQ.offer(tmp);
			}
		}
    	return flag;
    }
    
    public int get_flag() {
    	return flag_set;
    }

    private void setAlarm() {
		if (flag_set == 0) {
			flag_set = 1;
			cur_cursor = cusorQ.poll();
			if (setting_time == null) {
				setting_time = Calendar.getInstance();
				setting_time.set(Calendar.HOUR_OF_DAY, 0);
				setting_time.set(Calendar.MINUTE, 0);
				setting_time.set(Calendar.SECOND, 0);
			}
		}
	}

    private void confirmAlarm(){

		if(alarm_time == null){
			alarm_time = (Calendar) setting_time.clone();
			/*alarmQ.offer(alarm_time);

			alarm_time = null;
			alarm_time = alarmQ.poll();*/
		}
		else if(alarmQ.size() == 3){
			alarmQ.poll();
			alarmQ.offer(alarm_time);
			alarm_time = (Calendar) setting_time.clone();
		/*	alarmQ.offer(alarm_time);

			alarm_time = null;
			alarm_time = alarmQ.poll();*/
		}
		else{
			alarmQ.offer(alarm_time);
			alarm_time = (Calendar) setting_time.clone();
			/*alarmQ.offer(alarm_time);

			alarm_time = null;
			alarm_time = alarmQ.poll();*/
		}
    	cusorQ.clear();
		cusorQ.offer(3);
		cusorQ.offer(4);
		cusorQ.offer(5);
    	flag_set = 0;
    	cur_cursor = -1;
    	setting_time = null;
    	return;
    }

    private void plusTime_alarm(){

    	switch(cur_cursor) {
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
    	default:
    	System.err.println("cursor add error.");
    	break;
    	}
    }

    private boolean deleteAlarm(){
		if(alarm_time != null) {
			if (alarmQ.isEmpty())
				alarm_time = null;
			else {
				alarm_time = alarmQ.poll();

			}
		}

        return true;
    }

    private void moveCursor_alarm(){
    	cusorQ.offer(cur_cursor);
    	cur_cursor = cusorQ.poll();
    }
}
