package Mode;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;

import Format.Format;
import UI.Button;

public class Dday extends Mode{

    //what is the type of next?
	int flag_set=0;
	Calendar[] Dday_list = new Calendar[3];
	String[] Dday_memo = new String[3];
	int max_num = 3;
	int dday_num = 0;
	int current_page = 0;
	
	Queue<Integer> cusorQ = new LinkedList<Integer>();
	private int cur_cursor = -1;
	
	Queue<Integer> ddayQ = new LinkedList<Integer>();
	private int ddayQ_num = -1;
	
	public Dday() {
		cusorQ.offer(0);
		cusorQ.offer(1);
		cusorQ.offer(2);
		cusorQ.offer(7);
	}
	
    @Override
    public void work(JButton button) {
    	String text = button.getText();
    	if(text.equals("Button1")) {
    	
    	}
    	else if(text.equals("Button2")) {
    		if(flag_set == 0) { //no setting
    			//move next dday
    			current_page += 1;
    			if(current_page == 3)
    				current_page = 0;
    		}
    		else { //setting mode
    			
    		}
  
    	}
    	else if(text.equals("Button3")) {
    		
    	}
    	else if(text.equals("Button4")) {
    		if(flag_set == 0) { //no setting
    			//move next dday
    			current_page += 1;
    			if(current_page == 3)
    				current_page = 0;
    		}
    		else { //setting mode
    			
    		}
    	}
    	else {
    		System.err.println("error button");
    	}
    }

    public void setDday(){

    }
    public Calendar getDday() {
    	return Dday_list[current_page];
    }
    public String getMemo() {
    	return Dday_memo[current_page];
    }

    public Format showDday(){

        return null;
    }
    public int get_flag() {
    	return flag_set;
    }
    
    public int get_dday_num() {
    	return dday_num;
    }
    public void setDdayName(){

    }
    public void refreshDday(){

    }

    public boolean deleteDday(){

        return false;
    }

    public boolean ddayHasCome(){

        return false;
    }

    public boolean showNextDday(){

        return false;
    }

    public void moveCursor_Dday(){

    }

    public void plusDaynMonth(){

    }

    public boolean confirmDday(){

        return false;
    }

    public void plusName(){

    }
}
