package Mode;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;

import Format.Format;
import UI.Button;

public class Alarm extends  Mode {
	
	int flag_set = 0; //�� ����̴�, �ƴϴ�.
	Calendar alarm_time = null; //�˶� ���� �ð�.
	Calendar pre_time = null; //�������� ������ ����.
	
	Queue<Integer> cusorQ = new LinkedList<Integer>();
	private int cur_cursor = -1;
	
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
    		alarm_time = null;
    	}
    	else if(text.equals("Button4")) {
    		if(flag_set == 1) {
    			confirmAlarm();
    		}else {
    			resetAlarm();
    		}
    	}
    	else {
    		System.err.println("error button");
    	}
    }

    public void showAlarm(){
    	if(pre_time != null)
    		alarm_time = (Calendar)pre_time.clone(); 
    }
    public int getCursor() {
    	return cur_cursor;
    }
    
    public Calendar getAlarm() {
    	if(flag_set == 0)
    		return pre_time;
    	return alarm_time;
    }
    
    public Calendar getRealAlarm() {
    	return pre_time;
    }
    
    public int get_flag() {
    	//���� ���� ���� �������.
    	return flag_set;
    }

    public void setAlarm(){
    	if(flag_set == 0) { // �ð� ���� ���ߴ�.
    		flag_set = 1;
    		cur_cursor = cusorQ.poll(); //�ú��� �����ϼ�.
    		if(pre_time == null) {
    			alarm_time = Calendar.getInstance();
    			alarm_time.set(Calendar.HOUR_OF_DAY, 0);
        		alarm_time.set(Calendar.MINUTE, 0);
        		alarm_time.set(Calendar.SECOND, 0);
    		}
    		else {
    			alarm_time = (Calendar)pre_time.clone(); 
    		}
    	}
    	else { // �ð� ���� ���� ����.
    	}
    }

    public boolean confirmAlarm(){
    	//���� �Ϸ�
    	cusorQ.clear();
		cusorQ.offer(3);
		cusorQ.offer(4);
		cusorQ.offer(5);
    	flag_set = 0;
    	cur_cursor = -1;
    	pre_time = (Calendar) alarm_time.clone();
        return true;
    }

    public void plusTime_alarm(){

    	switch(cur_cursor) {
    	case 3:
    		int hour = alarm_time.get(Calendar.HOUR_OF_DAY);
    		alarm_time.set(Calendar.HOUR_OF_DAY, (hour+1)%24);
    	break;
    	case 4:
    		int mit = alarm_time.get(Calendar.MINUTE);
    		alarm_time.set(Calendar.MINUTE, (mit+1)%60);
    	break;
    	case 5:
    		int sec = alarm_time.get(Calendar.SECOND);
    		alarm_time.set(Calendar.SECOND, (sec+1)%60);
    	break;
    	default:
    	System.err.println("cursor add error.");
    	break;
    	}
    }

    public boolean resetAlarm(){
    	alarm_time = null;
    	pre_time = null;
        return true;
    }

    public void moveCursor_alarm(){
    	cusorQ.offer(cur_cursor);
    	cur_cursor = cusorQ.poll();
    }
}
