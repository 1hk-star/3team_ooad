package Controller;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Mode.Alarm;
import Mode.Buzzer;
import Mode.Dday;
import Mode.FunctionActivator;

import Mode.StopWatch;
import Mode.TimeKeep;
import Mode.mTimer;
import Mode.WorldTime;
import Type.dday_data;
import Type.watch_Type;


public class Watch extends JFrame implements Runnable{

    private JButton button1 = new JButton("Button1");
    private JButton button2 = new JButton("Button2");
    private JButton button3 = new JButton("Button3");
    private JButton button4 = new JButton("Button4");
 
    private JLabel[] text = new JLabel[9];
    
    private JPanel container = new JPanel();
    private JPanel leftButton = new JPanel();
    private JPanel centerText = new JPanel();
    private JPanel rightButton = new JPanel();
    
    private TimeKeep mode_time;
    private Alarm mode_alarm;
    private WorldTime mode_world;
    private StopWatch mode_stop;
    private Dday mode_dday;
    private mTimer mode_timer;
    private FunctionActivator mode_fa;
    private Buzzer mode_bz;
    private int dday_memo_flag = 0;
    private String dday_memo;
	private Thread thread;
	private int flag = 0;
	
    //public Type type;
    private int currentMode = watch_Type.TIMEKEEPING.ordinal();
    private int previousMode = watch_Type.TIMEKEEPING.ordinal();
    private Queue<Integer> modeQ = new LinkedList<Integer>();

    public Watch(){
        super("Digital Watch - 0");
        init();
        //Format format = null;
       // display(format);
    }

    // GUI를
	// 초기화 해주는 함수
    // JPanel을 왼쪽, 가운데, 오른쪽으로 나누고
    // 버튼과 라벨들을 저장 후 container에 합침
    // 그리고 각각의 버튼에 이벤트 클릭 리스너를 달아줌
    private void init_swing() {
    	 getContentPane().add(container);

         leftButton.setLayout(new GridLayout(2, 1));
         centerText.setLayout(new GridLayout(3, 3));
         rightButton.setLayout(new GridLayout(2, 1));

         leftButton.add(button1);
         leftButton.add(button3);
         
         
         for(int i=0; i <text.length; i++) {
        	 text[i] = new JLabel("0");
        	 text[i].setFont(new Font("Serif", Font.BOLD, 30));
        	 text[i].setHorizontalAlignment(JLabel.CENTER);
        	 centerText.add(text[i]);
         }
         
         rightButton.add(button2);
         rightButton.add(button4);

         container.setLayout(new BorderLayout());
         container.add(leftButton, BorderLayout.WEST);
         container.add(centerText, BorderLayout.CENTER);
         container.add(rightButton, BorderLayout.EAST);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         setSize(1000,700);
         setVisible(true);
         
         button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(mode_bz.getbuzzer() > 0) {
					mode_bz.turnOffBuzzer();
				}else {
					pressButton(button1);	
				}
			}
        	 
         });
         button2.addActionListener(new ActionListener() {

 			@Override
 			public void actionPerformed(ActionEvent arg0) {
 				// TODO Auto-generated method stub
 				if(mode_bz.getbuzzer() > 0) {
					mode_bz.turnOffBuzzer();
				}else {
					pressButton(button2);	
				}
 			}
         	 
          });
         button3.addActionListener(new ActionListener() {

  			@Override
  			public void actionPerformed(ActionEvent arg0) {
  				// TODO Auto-generated method stub
  				if(mode_bz.getbuzzer() > 0) {
					mode_bz.turnOffBuzzer();
				}else {
					pressButton(button3);	
				}
  			}
          	 
           });
         button4.addActionListener(new ActionListener() {

  			@Override
  			public void actionPerformed(ActionEvent arg0) {
  				// TODO Auto-generated method stub
  				if(mode_bz.getbuzzer() > 0) {
					mode_bz.turnOffBuzzer();
				}else {
					pressButton(button4);	
				}
  			}
          	 
           });
    }
    
    private void init_mode() {
    		
//    	mode_fa = new FunctionActivator();
//    	mode_bz = new Buzzer();
    	
    	mode_time = new TimeKeep();
    	mode_alarm = new Alarm();
    	mode_world = new WorldTime();
    	mode_stop = new StopWatch();
    	mode_dday = new Dday();
    	mode_timer = new mTimer();

    	modeQ.offer(watch_Type.ALARM.ordinal());
    	modeQ.offer(watch_Type.WORLDTIME.ordinal());
    	modeQ.offer(watch_Type.STOPWATCH.ordinal());
    	show_mode();

    	mode_fa = new FunctionActivator(modeQ);
    	mode_bz = new Buzzer();
    	
    }
   
    private void init(){
    	init_swing();
    	init_mode();
    	if(thread == null) {
    		thread = new Thread(this);
    		thread.start();
        }
    }

    public void pressButton(JButton button){

    	switch(currentMode){
			case 0://watch_Type.TIMEKEEPING.ordinal():
				mode_time.work(button);
				if(mode_time.get_flag() == 1) {
					display();
				}
				else if(mode_time.get_flag() == 2) {
					currentMode = watch_Type.FUNCTION.ordinal();
					display();
				}

				break;

			case 1://watch_Type.ALARM.ordinal()
				mode_alarm.work(button);
				display();
				break;

			case 2://watch_Type.WORLDTIME.ordinal()
				mode_world.work(button);
				display();

				break;

			case 3:// watch_Type.STOPWATCH.ordinal()
				mode_stop.work(button);
				display();
				break;

			case 4:// watch_Type.DDAY.ordinal()
				mode_dday.work(button);
				if(button.getText().equals("Button4")){
					List<String> rs = mode_dday.cmpday(mode_time.getRealTime());
					if(rs == null) {
						mode_time.setdday(null);
						dday_memo = null;
						return;
					}
					else {
						mode_time.setdday(rs);
						dday_memo = mode_time.getdday();
						return;
					}
				}
				display();
				break;

			case 5://watch_Type.TIMER.ordinal()
				mode_timer.work(button);
				display();
				break;

			case 6://watch_Type.FUNCTION.ordinal()
				if(button.getText().equals("Button4") && mode_fa.get_active_count() == 3) {
					modeQ.clear();
					modeQ = mode_fa.get_modeQ();
					currentMode = watch_Type.TIMEKEEPING.ordinal();
					if(!modeQ.contains(watch_Type.ALARM.ordinal())){
						mode_alarm.resetAlarm();
					}
					if(!modeQ.contains(watch_Type.DDAY.ordinal())){
						mode_dday.resetDday();
					}
				}
				mode_fa.work(button);
				display();
				break;


			default:
				System.err.println("oh what mode?");

				break;
		}//switch

    	
    	if(button.getText().equals("Button3") && currentMode != watch_Type.FUNCTION.ordinal()) {
    		changeMode();
    	}
    }
    
    private void show_mode(){
    	if(currentMode == watch_Type.TIMEKEEPING.ordinal()) {
    		mode_time.showTimeKeeping();
    	}
    	else if(currentMode == watch_Type.ALARM.ordinal()) {
    		mode_alarm.showAlarm();
    	}
    	else if(currentMode == watch_Type.WORLDTIME.ordinal()) {
    		//mode_world.showWorldTime();
    		//don't use
    	}
    	else if(currentMode == watch_Type.STOPWATCH.ordinal()) {
    		//don't use
    	}
    	else if(currentMode == watch_Type.DDAY.ordinal()) {
    		mode_dday.showDday();
    	}
    	else if(currentMode == watch_Type.TIMER.ordinal()) {
    		mode_timer.getTimer();
    	}
    	else {
    		System.err.println("oh what mode?");
    	}
    }
    
    private int get_currentMode_flag(){
    	if(currentMode == watch_Type.TIMEKEEPING.ordinal()) {
    		return mode_time.get_flag();
    	}
    	else if(currentMode == watch_Type.ALARM.ordinal()) {
    		return mode_alarm.get_flag();
    	}
    	else if(currentMode == watch_Type.WORLDTIME.ordinal()) {
    		return 0;
    	}
    	else if(currentMode == watch_Type.STOPWATCH.ordinal()) {
    		return 0;
    	}
    	else if(currentMode == watch_Type.DDAY.ordinal()) {
    		return 0;
    	}
    	else if(currentMode == watch_Type.TIMER.ordinal()) {
    		return this.mode_timer.get_flag();
    	}
    	else if(currentMode == watch_Type.FUNCTION.ordinal()) {
    		return 0;
    	}
    	else {
    		System.err.println("oh what mode?");
    		return -1;
    	}
    }

    private void display(){

    	switch(currentMode){
			case 0://watch_Type.TIMEKEEPING.ordinal()
				Calendar cal = mode_time.gettime();
				text[0].setText(Integer.toString(cal.get(Calendar.MONTH)+1));
				text[1].setText(Integer.toString(cal.get(Calendar.DATE)));
				if(dday_memo != null && dday_memo_flag == 0 && mode_time.get_flag() == 0)
					text[2].setText(dday_memo);
				else if(dday_memo != null && dday_memo_flag == 1 && mode_time.get_flag() == 0)
					text[2].setText(dow(cal.get(Calendar.DAY_OF_WEEK)));
				else
					text[2].setText(dow(cal.get(Calendar.DAY_OF_WEEK)));
				text[3].setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
				text[4].setText(Integer.toString(cal.get(Calendar.MINUTE)));
				text[5].setText(Integer.toString(cal.get(Calendar.SECOND)));
				text[7].setText(Integer.toString(cal.get(Calendar.YEAR)));
				text[6].setText("");
				text[8].setText("");

				dday_memo_flag = (dday_memo_flag + 1) % 2;
				break;

			case 1://watch_Type.Alarm.ordinal()
				 Calendar alr = mode_alarm.getAlarm();
				if(alr == null) {
					text[0].setText("");
					text[1].setText("");
					text[2].setText("");
					text[3].setText("O");
					text[4].setText("F");
					text[5].setText("F");
					text[7].setText("");
					text[6].setText("");
					text[8].setText("");
				}
				else {
					text[0].setText("");
					text[1].setText("");
					text[2].setText("");
					text[3].setText(Integer.toString(alr.get(Calendar.HOUR_OF_DAY)));
					text[4].setText(Integer.toString(alr.get(Calendar.MINUTE)));
					text[5].setText(Integer.toString(alr.get(Calendar.SECOND)));
					text[7].setText("");
					text[6].setText("");
					text[8].setText("");
				}
				break;

			case 2://watch_Type.WORLDTIME.ordinal()
				GregorianCalendar wor = new GregorianCalendar(TimeZone.getTimeZone(mode_world.getValue()));
				text[0].setText(Integer.toString(wor.get(Calendar.MONTH)+1));
				text[1].setText(Integer.toString(wor.get(Calendar.DATE)));
				text[2].setText(mode_world.getKey());
				text[3].setText(Integer.toString(wor.get(Calendar.HOUR_OF_DAY)));
				text[4].setText(Integer.toString(wor.get(Calendar.MINUTE)));
				text[5].setText(Integer.toString(wor.get(Calendar.SECOND)));
				// String s = mode_world.get_key() == mode_world.get_key_temp() ? "on" : "off";
				text[7].setText(Integer.toString(wor.get(Calendar.YEAR)));
				text[6].setText("");
				text[8].setText("");
				break;

			case 3://watch_Type.STOPWATCH.ordinal()
				Calendar sto=this.mode_stop.getStopWatch();
				Calendar lap=this.mode_stop.getLapTime();

				text[0].setText("");
				text[1].setText("");
				text[2].setText("");
				text[3].setText(Integer.toString(sto.get(Calendar.HOUR)));
				text[4].setText(Integer.toString(sto.get(Calendar.MINUTE)));
				text[5].setText(Integer.toString(sto.get(Calendar.SECOND)));
				text[6].setText(Integer.toString(lap.get(Calendar.HOUR)));
				text[7].setText(Integer.toString(lap.get(Calendar.MINUTE)));
				text[8].setText(Integer.toString(lap.get(Calendar.SECOND)));
				break;

			case 4://watch_Type.DDAY.ordinal()
				if(mode_dday.getDday() == null) { //no dday
					text[0].setText("O");
					text[1].setText("F");
					text[2].setText("F");
					text[3].setText("");
					text[4].setText("");
					text[5].setText("");
					text[6].setText("");
					text[7].setText("");
					text[8].setText("");
					return;
				}
				//display dday left day
				dday_data data =this.mode_dday.getDday();
				Calendar current_time = mode_time.getRealTime();
				Calendar dday_time = data.get_cal();
				long t1 = current_time.getTimeInMillis() / (24*60*60*1000);
				long t2 = dday_time.getTimeInMillis() / (24*60*60*1000);
				long sub = t2 - t1;
				if(mode_dday.get_flag() == 0) {
					if(sub > 0)
						text[0].setText("-");
					else
						text[0].setText("+");
					text[1].setText(""+sub);
					text[2].setText(data.get_memo());
					text[3].setText("");
					text[4].setText("");
					text[5].setText("");
					text[6].setText("");
					text[7].setText("");
					text[8].setText("");
				}
				else {
					text[0].setText(Integer.toString(data.get_cal().get(Calendar.MONTH)+1));
					text[1].setText(Integer.toString(data.get_cal().get(Calendar.DATE)));
					text[2].setText(data.get_memo());
					text[3].setText("");
					text[4].setText("");
					text[5].setText("");
					text[6].setText("");
					text[7].setText(Integer.toString(data.get_cal().get(Calendar.YEAR)));
					text[8].setText("");
				}

				break;


			case 5://watch_Type.TIMER.ordinal()
				Calendar tim = mode_timer.getTimer();
				if(tim == null) {
					text[0].setText("");
					text[1].setText("");
					text[2].setText("");
					text[3].setText("0");
					text[4].setText("0");
					text[5].setText("0");
					text[7].setText("");
					text[6].setText("");
					text[8].setText("");
				}
				else {
					text[0].setText("");
					text[1].setText("");
					text[2].setText("");
					text[3].setText(Integer.toString(tim.get(Calendar.HOUR_OF_DAY)));
					text[4].setText(Integer.toString(tim.get(Calendar.MINUTE)));
					text[5].setText(Integer.toString(tim.get(Calendar.SECOND)));
					text[7].setText("");
					text[6].setText("");
					text[8].setText("");
				}

				break;

			case 6://watch_Type.FUNCTION.ordinal()
				text[0].setText("");
				text[1].setText(mode_fa.get_active(mode_fa.get_position()) ? "on" : "off"); // on off
				text[2].setText(mode_fa.get_active_name(mode_fa.get_position())); // arm //
				for(int i = 3; i < 9; i++) {
					text[i].setText("");
				}
				break;

			default:break;
		}
    	

   }
    
    private void display_blink(){
    	if(currentMode == watch_Type.TIMEKEEPING.ordinal()) {	
    			int cur = mode_time.getCursor();
    			blink_cursor(cur);
    	}
    	else if(currentMode == watch_Type.FUNCTION.ordinal()) {
			blink_cursor(1);
    	}
    	else if(currentMode == watch_Type.WORLDTIME.ordinal()) {
    		int cur = mode_world.getCursor();
			blink_cursor(cur);
    	}
    	else if(currentMode == watch_Type.ALARM.ordinal()) {	
			int cur = mode_alarm.getCursor();
			blink_cursor(cur);
    	}
    	else if(currentMode==watch_Type.STOPWATCH.ordinal()) {
            //don't use
    	}
    	else if(currentMode==watch_Type.TIMER.ordinal()) {
    		int cur=this.mode_timer.getCursor();
    		blink_cursor(cur);
    	}
    	else if(currentMode==watch_Type.DDAY.ordinal()) {
    		int cur=this.mode_dday.getCursor();
    		blink_cursor(cur);
    	}
   }
    
    private void blink_cursor(int cur_num) {
    	
    	if(cur_num == -1)
    		return;
    	for(int i =0; i < text.length; i++) {
    		if(i == cur_num) {
    			if(text[i].isVisible()) {
    				text[i].setVisible(false);
    			}
    			else {
    				text[i].setVisible(true);
    			}
    		}
    		if((i != cur_num) && !text[i].isVisible()) {
    			text[i].setVisible(true);
    		}
    	}
    	return;
    }
    
    private void visible_all() {
    	for(int i =0; i < text.length; i ++) {
    		if(!text[i].isVisible()) {
    			text[i].setVisible(true);
    		}
    	}
    }
    
    private String dow(int week_of_day_num) {
    	String strWeek = null;
    	switch(week_of_day_num) {
    	case Calendar.MONDAY:
    	strWeek = "MON";
    	break;
    	case Calendar.TUESDAY:
    	strWeek = "TUE";
    	break;
    	case Calendar.WEDNESDAY:
    	strWeek = "WED";
    	break;
    	case Calendar.THURSDAY:
    	strWeek = "THU";
    	break;
    	case Calendar.FRIDAY:
    	strWeek = "FRI";
    	break;
    	case Calendar.SATURDAY:
    	strWeek = "SAT";
    	break;
    	default:
    	strWeek = "SUN";
    	}
		return strWeek;
    }

    private boolean changeMode(){
    	previousMode = currentMode;
    	
    	currentMode = modeQ.poll();
    	
    	modeQ.offer(previousMode);
    	visible_all();
    	show_mode();
    	setTitle("Digital Watch - "+ currentMode);
        return false;
    }

    private void checkAlarm(){
    	if(mode_bz.getbuzzer() == 1) { 
    		if(mode_bz.getLeftTime() == 0) { 
    			mode_bz.turnOffBuzzer();
    		}
    		else {
    			mode_bz.subTimeBuzzer();
    		}
    	}
    	Calendar t1 = mode_time.getRealTime();
    	if(t1 == null)
    		return;

    	int rs = mode_alarm.cmpAlarm((Calendar) t1.clone());
		System.out.println("rs : "+rs);
    	if(rs == 0)
    		return;
    	else{
			mode_bz.onBuzzer(1);
		}

        return;
    }
    private void checkTimer(){
    	if(currentMode == watch_Type.TIMER.ordinal()) {
    		if(mode_bz.getbuzzer() == 2) { 
        		if(mode_bz.getLeftTime() == 0) {
        			mode_bz.turnOffBuzzer();
        			return;
        		}
        		else {
        			mode_bz.subTimeBuzzer();
        			return;
        		}
        	}
        	Calendar timeover = mode_timer.getTimerTime();
        	int temp = mode_timer.getPauseFlag();
        	if(timeover==null)
        		return;
        	else if(timeover.get(Calendar.HOUR_OF_DAY)==0&&timeover.get(Calendar.MINUTE)==0&&
        			timeover.get(Calendar.SECOND)==0&&temp==1)
        		mode_bz.onBuzzer(2);
            return;
    	}
    	return;
    }
    

    private void checkDday(){
    	if(currentMode == watch_Type.TIMEKEEPING.ordinal()) {
    		if(dday_memo_flag == 1) {
    			List<String> rs = mode_dday.cmpday(mode_time.getRealTime());
        		if(rs == null) {
        			mode_time.setdday(null);
        			dday_memo = null;
        			return;
        		}
        		else {
        			mode_time.setdday(rs);
        			dday_memo = mode_time.getdday();
        			return;
        		}
    		}
    		
    	}
    	else
    		return;
    }


    public static void main(String[] args) {
        Watch app = new Watch();
    }

    @Override
    public void run() {
    	while(true) {
    		checkAlarm();
    		checkTimer();
    		checkDday();
    		
    		flag = get_currentMode_flag();

    		if(flag == 0 || flag == 2){
    			visible_all();
    			display();
    		}
    		
    		display_blink();
    		
    		mode_time.addseconds();
    		
    		try {
    			Thread.sleep(500);
    		}
    		catch(InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		display_blink();
    		try {
    			Thread.sleep(500);
    		}
    		catch(InterruptedException e) {
    			e.printStackTrace();
    		}
    		flag = 0;
    	}
    }

}
