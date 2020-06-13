package Mode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JButton;
import Type.dday_data;

public class Dday extends Mode{

    //what is the type of next?
	private int flag_set=0;
	private Calendar[] Dday_list = new Calendar[3];
	private String[] Dday_memo = new String[3];
	private int max_num = 3;
	private int dday_num = 0;
	private dday_data current_page = null;
	private dday_data setting_page = null;
	
	private Queue<Integer> cursorQ = new LinkedList<Integer>();
	private int cur_cursor = -1;
	
	private Queue<dday_data> ddayQ = new LinkedList<dday_data>();
	private int ddayQ_num = 0;
	
	private int string_cur = 0;
	
	public Dday() {
		//cursor q
		cursorQ.offer(0);
		cursorQ.offer(1);
		cursorQ.offer(2);
		cursorQ.offer(7);
		Calendar cal = Calendar.getInstance();
		//ddayQ.offer(new dday_data(cal,"no1"));
		//ddayQ.offer(new dday_data(cal,"no2"));
		//ddayQ.offer(new dday_data(cal,"no3"));
	}
	
    @Override
    public void work(JButton button) {
    	String text = button.getText();
    	if(text.equals("Button1")) {
    		if(flag_set == 0)
    			setDday();
    		else 
    			moveCursor_Dday();
    	}
    	else if(text.equals("Button2")) {
    		if(flag_set == 0)  //no setting
    			showNextDday();
    		else  //setting mode
    			plusDay();
    	}
    	else if(text.equals("Button3")) {
    		//change mode
    		//System.out.println("current string : " + current_page.get_memo());
			//System.out.println("next string : " + ddayQ.peek().get_memo());
    		if(current_page != null) {
    			ddayQ.offer(current_page);
    			for(int i =0; i < ddayQ.size()-1 ; i ++) {
    				dday_data tmp = ddayQ.poll();
    				ddayQ.offer(tmp);
    			}
    		}
    		cursorQ.clear();
        	cursorQ.offer(0);
    		cursorQ.offer(1);
    		cursorQ.offer(2);
    		cursorQ.offer(7);
    		cur_cursor = -1;
    		string_cur = 0;
    		setting_page = null;
    		flag_set = 0;
    		
    	}
    	else if(text.equals("Button4")) {
    		if(flag_set == 0)  //no setting	
    			deleteDday();
    		else  //setting mode
    			confirmDday();
    	}
    	else {
    		System.err.println("error button");
    	}
    }


	public void resetDday(){
		ddayQ = new LinkedList<dday_data>();
		current_page = null;
	}

    public void setDday(){
    	flag_set = 1;
    	cur_cursor = cursorQ.poll();
    	if(setting_page == null) {
    		setting_page = new dday_data(Calendar.getInstance(), "AAA");
    	}
    	
    }
    public dday_data getDday() {
    	if(flag_set == 0)
    		return current_page;
    	else {
    		return setting_page;
    	}
    }

    public void showDday(){ // copy ddayq to ddayq_tmp
    	if(!ddayQ.isEmpty())
    		current_page = ddayQ.poll();
    	else
    		current_page = null;
        return;
    }
    public int get_flag() {
    	return flag_set;
    }

    public int getCursor(){
    	return cur_cursor;
    }

    private void deleteDday(){
    	if(current_page != null) {
			if(!ddayQ.isEmpty())
				current_page = ddayQ.poll();
			else {
				current_page = null;
			}
    	}
        return ;
    }
    public List<String> cmpday(Calendar cal) {
    	
    	dday_data tmp ;
    	List<String> list_memo = new ArrayList<String>();
    	synchronized (ddayQ) {
    		if(ddayQ.isEmpty())
    			return null;
    		String str = null;
    		for(int i =0; i < ddayQ.size(); i ++) {
    			tmp = ddayQ.poll();
    			long t1 = cal.getTimeInMillis() / (24*60*60*1000);
        		long t2 = tmp.get_cal().getTimeInMillis() / (24*60*60*1000);
        		long sub = t2 - t1;
        		if(sub == 0) {
        			str = tmp.get_memo();
        			list_memo.add(str);
        		}
        		ddayQ.offer(tmp);
    		}
    		//System.out.println(list_memo);
    		if(list_memo.isEmpty())
    			return null;
    		return list_memo;
		}
    	
    }

    private void showNextDday(){
    	//move next dday
		if(current_page != null) {
			ddayQ.offer(current_page);
			current_page = ddayQ.poll();
		}
    }

    private void moveCursor_Dday(){
    	if(cur_cursor == 2) {
    		if(string_cur == 2) {
    			string_cur = 0;
    			cursorQ.offer(cur_cursor);
            	cur_cursor = cursorQ.poll();
    		}else {
    			string_cur +=1;
    		}
    	}else {
    		cursorQ.offer(cur_cursor);
        	cur_cursor = cursorQ.poll();
    	}
    	System.out.println("c : "+cur_cursor+", s : "+string_cur);
    }

    private void plusDay(){
    	switch(cur_cursor) {
    	case 0:
    		//setting_time.add(Calendar.MONTH, 1);
    		int month = setting_page.get_cal().get(Calendar.MONTH);
    		setting_page.get_cal().set(Calendar.MONTH, (month+1)%12);
    	break;
    	case 1:
    		int date = setting_page.get_cal().get(Calendar.DATE);
    		int month2 = setting_page.get_cal().get(Calendar.MONTH) + 1;
    		int year2 = setting_page.get_cal().get(Calendar.YEAR);
    		if(month2 == 1 || month2 == 3 || month2 == 5 || month2 == 7 || month2 == 8 || month2 == 10 || month2 == 12) {
    			
    			date = (date % 31) + 1;
    		} else if(month2 == 4 || month2 == 6 || month2 == 9 || month2 == 11) {
    		
    			date = (date % 30) + 1;
    		} else if(month2 == 2) {
    			if(year2 % 4 == 0 || (year2 % 4 == 0 && year2 % 100 == 0 && year2 % 400 == 0)) {
    			
    				date = (date % 29) + 1;
    			}
    			else if (year2 % 4 == 0 && year2 % 100 == 0) {
    			
    				date = (date % 28) +1;
    			}
    		}
    		setting_page.get_cal().set(Calendar.DATE, date);
    	break;
    	case 2:
    		String str = setting_page.get_memo();
    		char[] tmp = str.toCharArray();
    		tmp[string_cur] += 1;
    		System.out.println(tmp[0]+" : "+(int)tmp[string_cur]);
    		if (tmp[string_cur] == 91)
    			tmp[string_cur] = 65;
    		str = String.valueOf(tmp);
    		setting_page.set_memo(str);
    	break;
    	case 7:
    		//1980 ~ 2079
    		int year = setting_page.get_cal().get(Calendar.YEAR);
    		if((year+1) > 2079) {
    			year = 1980;
    			setting_page.get_cal().set(Calendar.YEAR, year);
    		}
    		else {
    			setting_page.get_cal().set(Calendar.YEAR, year+1);
    		}
    		
    	break;
    	default:
    	System.err.println("cursor add ���ъ��.");
    	break;
    	}
    }

    private void confirmDday(){
    	//가장 최근 설정한 것이 제일 큐 맨앞.
    	if(current_page == null) {
    		//setting data push
    		current_page = new dday_data();
    		current_page.set_cal(setting_page.get_cal());
        	current_page.set_memo(setting_page.get_memo());
        	ddayQ.offer(current_page);
        	
        	current_page = null;
        	current_page = ddayQ.poll();
    	}
    	else if(ddayQ.size() == 2) {
    		current_page.set_cal(setting_page.get_cal());
        	current_page.set_memo(setting_page.get_memo());
        	ddayQ.offer(current_page);
        	
        	current_page = null;
        	current_page = ddayQ.poll();
    	}
    	else {
    		//origin data push
    		ddayQ.offer(current_page);
    		current_page = new dday_data();
    		//setting data push
    		current_page.set_cal(setting_page.get_cal());
        	current_page.set_memo(setting_page.get_memo());
        	ddayQ.offer(current_page);
        	
        	current_page = null;
        	current_page = ddayQ.poll();
    	}
    	cur_cursor = -1;
    	string_cur = 0;
    	flag_set = 0;
    	
    	cursorQ.clear();
    	cursorQ.offer(0);
		cursorQ.offer(1);
		cursorQ.offer(2);
		cursorQ.offer(7);
		setting_page = null;
        return;
    }

	public int getFlag_set() {
		return flag_set;
	}

	public void setFlag_set(int flag_set) {
		this.flag_set = flag_set;
	}

	public Calendar[] getDday_list() {
		return Dday_list;
	}

	public void setDday_list(Calendar[] dday_list) {
		Dday_list = dday_list;
	}

	public String[] getDday_memo() {
		return Dday_memo;
	}

	public void setDday_memo(String[] dday_memo) {
		Dday_memo = dday_memo;
	}

	public int getMax_num() {
		return max_num;
	}

	public void setMax_num(int max_num) {
		this.max_num = max_num;
	}

	public int getDday_num() {
		return dday_num;
	}

	public void setDday_num(int dday_num) {
		this.dday_num = dday_num;
	}

	public dday_data getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(dday_data current_page) {
		this.current_page = current_page;
	}

	public dday_data getSetting_page() {
		return setting_page;
	}

	public void setSetting_page(dday_data setting_page) {
		this.setting_page = setting_page;
	}

	public Queue<Integer> getCursorQ() {
		return cursorQ;
	}

	public void setCursorQ(Queue<Integer> cursorQ) {
		this.cursorQ = cursorQ;
	}

	public int getCur_cursor() {
		return cur_cursor;
	}

	public void setCur_cursor(int cur_cursor) {
		this.cur_cursor = cur_cursor;
	}

	public Queue<dday_data> getDdayQ() {
		return ddayQ;
	}

	public void setDdayQ(Queue<dday_data> ddayQ) {
		this.ddayQ = ddayQ;
	}

	public int getDdayQ_num() {
		return ddayQ_num;
	}

	public void setDdayQ_num(int ddayQ_num) {
		this.ddayQ_num = ddayQ_num;
	}

	public int getString_cur() {
		return string_cur;
	}

	public void setString_cur(int string_cur) {
		this.string_cur = string_cur;
	}
    
    

}
