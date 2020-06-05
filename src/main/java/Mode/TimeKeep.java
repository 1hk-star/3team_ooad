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
	String dday_flag;
	int flag_set = 0; // ��媛� �ㅼ�� ������.
	Calendar current_time;
	Calendar setting_time;
	Long diff = null;
	int tmp_flag = 0; //
	int tmp2_flag = 0; // �명���� �댁���� ��踰����� ������.
	private int cur_cursor = -1;
	    // -1 而ㅼ�� ��移� ����.
	    // 0 ��
	    // 1 ��
		// 2 ����
	    // 3 ��
	    // 4 遺�
	    // 5 珥�
		// 6 ��
		// 7 遺�(��)
	 	// 8 珥�
	Queue<Integer> cusorQ = new LinkedList<Integer>();
	FunctionActivator func; // FunctionActivator濡� ����
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
    	
    	//�ъ�⑹��媛� ��媛� �ㅼ���� ������.
    	current_time = Calendar.getInstance();
    	if(diff == null) { //�닿� �댁���� ���ъ��媛��� �ㅼ������.
    		//current_time = Calendar.getInstance();
    		return;
    	}else {
    		current_time.add(Calendar.SECOND, Integer.parseInt(String.valueOf(Math.round(diff/1000))));
    	}
    	
    	
    	//�ъ�⑹��媛� ��媛� �ㅼ���� ������.
    	//(aaa = ���� ��媛� - �ъ�⑹�� �ㅼ�� ��媛�) ����
    	// ���ъ��媛� - aaa format�� ����.
    	
    }
    
    public String getdday() {
    	return dday_flag;
    }
    public void setdday(String str) {
    	dday_flag = str;
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
    	if(flag_set == 0) { // ��媛� �ㅼ�� ������.
    		flag_set = 1;
    		cur_cursor = cusorQ.poll(); //��遺��� �ㅼ������.
    		if(tmp_flag == 0) {
    			setting_time = (Calendar) current_time.clone();
    			tmp_flag = 1;
    		}
    	}
    	else { // ��媛� �ㅼ�� ���� ����.
    		
    	}
    }
    
    public void setActiveFunction() {
    	if(flag_set == 0) { // ��媛� �ㅼ�� ������.
    		flag_set = 2;
    	}
    }
    
    public int get_flag() {
    	//�닿� 吏�湲� 臾댁�� 紐⑤���몄�.
    	return flag_set;
    }
    
    public void addseconds(){
    	current_time.add(Calendar.SECOND, 1);
    		//System.out.println("set : " + setting_time);
    	
    	//System.out.println("cur : " + current_time);
    }
    
    public int getCursor() {
    	return cur_cursor;
    }

    public void moveCursor_time(){
    	cusorQ.offer(cur_cursor);
    	cur_cursor = cusorQ.poll();
    }

    public void plusTime_time(){
    	//System.out.println("cur_cusor: "+cur_cursor);
    	switch(cur_cursor) {
    	case 0:
    		//setting_time.add(Calendar.MONTH, 1);
    		int month = setting_time.get(Calendar.MONTH);
    		setting_time.set(Calendar.MONTH, (month+1)%12);
    	break;
    	case 1:
    		int date = setting_time.get(Calendar.DATE);
    		int month2 = setting_time.get(Calendar.MONTH) + 1;
    		int year2 = setting_time.get(Calendar.YEAR);
    		if(month2 == 1 || month2 == 3 || month2 == 5 || month2 == 7 || month2 == 8 || month2 == 10 || month2 == 12) {
    			// 31��
    			date = (date % 31) + 1;
    		} else if(month2 == 4 || month2 == 6 || month2 == 9 || month2 == 11) {
    			// 30��
    			date = (date % 30) + 1;
    		} else if(month2 == 2) {
    			if(year2 % 4 == 0 || (year2 % 4 == 0 && year2 % 100 == 0 && year2 % 400 == 0)) {
    				// �ㅻ��
    				date = (date % 29) + 1;
    			}
    			else if (year2 % 4 == 0 && year2 % 100 == 0) {
    				// ���� 1 ~ 28
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
			cur_cursor= -1;
			//�닿� ����媛��� current_time
			Calendar temp = null;
			if(tmp_flag == 0) { // 泥��� �ㅼ������寃�
				temp = Calendar.getInstance();
				tmp_flag = 1;
			}else {
				temp = current_time;
			}
			
			//temp : end ��瑜� ��媛�, current_tiem : �ㅼ�� ��媛�
			//diff媛� ����硫� 誘몃����, diff媛� ����硫� 怨쇨굅��.
			tmp_flag = 0;
			diff = setting_time.getTimeInMillis() - temp.getTimeInMillis();
			current_time.add(Calendar.SECOND, Integer.parseInt(String.valueOf(Math.round(diff/1000))));
		}
    	
        return  true;
    }

    public String setDday(){

        return "1";
    }


}
