package Controller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Mode.Alarm;
import Mode.Buzzer;
import Mode.Dday;
import Mode.FunctionActivator;
import Mode.Mode;
import Mode.StopWatch;
import Mode.TimeKeep;
import Mode.mTimer;
import Mode.WorldTime;
import Type.watch_Type;

public class Watch extends JFrame implements Runnable{

    JButton button1 = new JButton("Button1");
    JButton button2 = new JButton("Button2");
    JButton button3 = new JButton("Button3");
    JButton button4 = new JButton("Button4");
 
    JLabel[] text = new JLabel[9];
    
    //text1 : 占쎈쐻占쎈짗占쎌굲, text2 : 占쎈쐻占쎈짗占쎌굲, text3 : 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲
    //text4 : 占쎈쐻占쎈짗占쎌굲, text5 : 占쎈쐻占쎈짗占쎌굲, text6 : 占쎈쐻占쎈짗占쎌굲
    //text7 : 占쎈쐻占쎈짗占쎌굲, text8 : 占쎈쐻占쎈짗占쎌굲(占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲), text9 : 占쎈쐻占쎈짗占쎌굲

    JPanel container = new JPanel();
    JPanel leftButton = new JPanel();
    JPanel centerText = new JPanel();
    JPanel rightButton = new JPanel();
    
    TimeKeep mode_time;
	Alarm mode_alarm;
	WorldTime mode_world;
	StopWatch mode_stop;
	Dday mode_dday;
	mTimer mode_timer;
	FunctionActivator mode_fa;
	Buzzer mode_bz;
	
	private Thread thread;
	
    //public Type type;
    private int currentMode = watch_Type.TIMEKEEPING.ordinal();
    private int previousMode = watch_Type.TIMEKEEPING.ordinal();
    Queue<Integer> modeQ = new LinkedList<Integer>();

    public Watch(){
        super("Digital Watch - TIMEKEEPING");
        init();
        //Format format = null;
       // display(format);
    }

    
    public void init_swing() {
    	 getContentPane().add(container);

         leftButton.setLayout(new GridLayout(2, 1));
         centerText.setLayout(new GridLayout(3, 3));
         rightButton.setLayout(new GridLayout(2, 1));

         leftButton.add(button1);
         leftButton.add(button3);
         
         for(int i=0; i <text.length; i++) {
        	 text[i] = new JLabel("0");
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
				if(mode_bz.getbuzzer() == 1) {
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
 				if(mode_bz.getbuzzer() == 1) {
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
  				if(mode_bz.getbuzzer() == 1) {
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
  				if(mode_bz.getbuzzer() == 1) {
					mode_bz.turnOffBuzzer();
				}else {
					pressButton(button4);	
				}
  			}
          	 
           });
    }
    
    public void init_mode() {
    		
//    	mode_fa = new FunctionActivator();
//    	mode_bz = new Buzzer();
    	
    	mode_time = new TimeKeep();
    	mode_alarm = new Alarm();
    	mode_world = new WorldTime();
    	mode_stop = new StopWatch();
    	
    	modeQ.offer(watch_Type.ALARM.ordinal());
    	modeQ.offer(watch_Type.WORLDTIME.ordinal());
    	modeQ.offer(watch_Type.STOPWATCH.ordinal());
    	show_mode();
    	
    	mode_dday = new Dday();
    	mode_timer = new mTimer();
    	mode_fa = new FunctionActivator(modeQ);
    	mode_bz = new Buzzer();
    	
    }
   
    public void init(){
    	init_swing();
    	init_mode();
    	if(thread == null) {
    		thread = new Thread(this);
    		thread.start();
        }
    }

    public void pressButton(JButton button){
    	if(button.getText().equals("Button3") && currentMode != watch_Type.FUNCTION.ordinal()) {
    		changeMode();
    	}
    	if(currentMode == watch_Type.TIMEKEEPING.ordinal()) {
    		mode_time.work(button);
    		if(mode_time.get_flag() == 1) {
    			display();
    		}
    		else if(mode_time.get_flag() == 2) {
    			currentMode = watch_Type.FUNCTION.ordinal();
    			display();
    		}
    	}
    	else if(currentMode == watch_Type.ALARM.ordinal()) {
    		mode_alarm.work(button);
    		if(mode_alarm.get_flag() == 1) {
    			display();
    		}
    	}
    	else if(currentMode == watch_Type.WORLDTIME.ordinal()) {
    		if(button.getText().equals("Button4")) {
    			previousMode = currentMode;
    	    	currentMode = modeQ.poll();
    	    	modeQ.offer(previousMode);
        	}
    		mode_world.work(button);
    		display();
    	}
    	else if(currentMode == watch_Type.STOPWATCH.ordinal()) {
    		mode_stop.work(button);
    		display();
    	}
    	else if(currentMode == watch_Type.DDAY.ordinal()) {
    		mode_dday.work(button);
    	}
    	else if(currentMode == watch_Type.TIMER.ordinal()) {
    		mode_timer.work(button);
    		display();
    	}
    	else if(currentMode == watch_Type.FUNCTION.ordinal()) {
    		if(button.getText().equals("Button4") && mode_fa.get_active_count() == 3) {
    			modeQ.clear();
    			modeQ = mode_fa.get_modeQ();
    			currentMode = watch_Type.TIMEKEEPING.ordinal();
        	}
    		mode_fa.work(button);
    		display();
    	}
    	else {
    		System.err.println("oh what mode?");
    	}
    }
    
    public void show_mode(){
    	if(currentMode == watch_Type.TIMEKEEPING.ordinal()) {
    		mode_time.showTimeKeeping();
    	}
    	else if(currentMode == watch_Type.ALARM.ordinal()) {
    		mode_alarm.showAlarm();
    	}
    	else if(currentMode == watch_Type.WORLDTIME.ordinal()) {
    		mode_world.showWorldTime();
    	}
    	else if(currentMode == watch_Type.STOPWATCH.ordinal()) {
    		mode_stop.showStopWatch();
    	}
    	else if(currentMode == watch_Type.DDAY.ordinal()) {
    		mode_dday.showDday();
    	}
    	else if(currentMode == watch_Type.TIMER.ordinal()) {
    		mode_timer.showTimer();
    	}
    	else {
    		System.err.println("oh what mode?");
    	}
    }
    
    public int get_currentMode_flag(){
    	if(currentMode == watch_Type.TIMEKEEPING.ordinal()) {
    		return mode_time.get_flag();
    	}
    	else if(currentMode == watch_Type.ALARM.ordinal()) {
    		return mode_alarm.get_flag();
    	}
    	else if(currentMode == watch_Type.WORLDTIME.ordinal()) {
    		return 1;
    	}
    	else if(currentMode == watch_Type.STOPWATCH.ordinal()) {
    		return mode_stop.get_flag();
    	}
    	else if(currentMode == watch_Type.DDAY.ordinal()) {
    		return 1;
    	}
    	else if(currentMode == watch_Type.TIMER.ordinal()) {
    		return 1;
    	}
    	else if(currentMode == watch_Type.FUNCTION.ordinal()) {
    		return mode_fa.get_flag();
    	}
    	else {
    		System.err.println("oh what mode?");
    		return -1;
    	}
    }

    public void display(){
    	//text1 : 占쎈쐻占쎈짗占쎌굲, text2 : 占쎈쐻占쎈짗占쎌굲, text3 : 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲
        //text4 : 占쎈쐻占쎈짗占쎌굲, text5 : 占쎈쐻占쎈짗占쎌굲, text6 : 占쎈쐻占쎈짗占쎌굲
        //text7 : 占쎈쐻占쎈짗占쎌굲, text8 : 占쎈쐻占쎈짗占쎌굲(占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲), text9 : 占쎈쐻占쎈짗占쎌굲
    	
    	if(currentMode == watch_Type.TIMEKEEPING.ordinal()) {
    		Calendar cal = mode_time.gettime();
    		if(mode_world.get_flag() == 1) {
    			cal.add(Calendar.HOUR_OF_DAY, mode_world.get_value());
    		}
			text[0].setText(Integer.toString(cal.get(Calendar.MONTH)+1));
			text[1].setText(Integer.toString(cal.get(Calendar.DATE)));
			text[2].setText(dow(cal.get(Calendar.DAY_OF_WEEK)));
			text[3].setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
			text[4].setText(Integer.toString(cal.get(Calendar.MINUTE)));
			text[5].setText(Integer.toString(cal.get(Calendar.SECOND)));
			text[7].setText(Integer.toString(cal.get(Calendar.YEAR)));
			text[6].setText("");
			text[8].setText("");
			
			//System.out.println("getflag : "+mode_time.get_flag());
			//System.out.println("seconds : "+ cal.get(Calendar.SECOND));
    	}
    	else if(currentMode == watch_Type.ALARM.ordinal()) {
			Calendar cal = mode_alarm.getAlarm();
			if(cal == null) {
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
				text[3].setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
				text[4].setText(Integer.toString(cal.get(Calendar.MINUTE)));
				text[5].setText(Integer.toString(cal.get(Calendar.SECOND)));
				text[7].setText("");
				text[6].setText("");
				text[8].setText("");	
			}
    	}
    	else if (currentMode==watch_Type.WORLDTIME.ordinal()) {
    		for (int i = 0; i < text.length; i++) {
    			text[i].setText("");
    			if(i == 2) {
    				text[i].setText(mode_world.get_key());
    			}
			}
    	}
    	else if(currentMode==watch_Type.STOPWATCH.ordinal()) {
    		
    		Calendar cal=this.mode_stop.getStopWatch();
    		Calendar lap=this.mode_stop.getLapTime();
    		
    		text[0].setText("");
    		text[1].setText("");
    		text[2].setText("");
    		text[3].setText(Integer.toString(cal.get(Calendar.HOUR)));
    		text[4].setText(Integer.toString(cal.get(Calendar.MINUTE)));
    		text[5].setText(Integer.toString(cal.get(Calendar.SECOND)));
    		text[6].setText(Integer.toString(lap.get(Calendar.HOUR)));
    		text[7].setText(Integer.toString(lap.get(Calendar.MINUTE)));
    		text[8].setText(Integer.toString(lap.get(Calendar.SECOND)));
    	}
    	else if(currentMode == watch_Type.FUNCTION.ordinal()) {
    		for(watch_Type type : watch_Type.values()) {
    			int num = type.ordinal();
    			if(type == watch_Type.valueOf("TIMEKEEPING")) {
    				continue;
    			}
    			else if(type == watch_Type.valueOf("FUNCTION"))
					break;
				else {
					String s = mode_fa.get_active(num - 1) ? "(on)" : "(off)";
					text[num - 1].setText(type + s);
				}
			}
			for(int i = 5; i < 9; i++) {
				text[i].setText("");
			}
    	}
   }
    
    public void display_blink(){
    	//text1 : 占쎈쐻占쎈짗占쎌굲, text2 : 占쎈쐻占쎈짗占쎌굲, text3 : 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲
        //text4 : 占쎈쐻占쎈짗占쎌굲, text5 : 占쎈쐻占쎈짗占쎌굲, text6 : 占쎈쐻占쎈짗占쎌굲
        //text7 : 占쎈쐻占쎈짗占쎌굲, text8 : 占쎈쐻占쎈짗占쎌굲(占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲), text9 : 占쎈쐻占쎈짗占쎌굲
    	if(currentMode == watch_Type.TIMEKEEPING.ordinal()) {	
    			int cur = mode_time.getCursor();
    			blink_cursor(cur);
    	}
    	else if(currentMode == watch_Type.FUNCTION.ordinal()) {
    		int cur = mode_fa.getCursor();
			blink_cursor(cur);
    	}
    	else if(currentMode == watch_Type.WORLDTIME.ordinal()) {
			blink_cursor(4);
    	}
    	else if(currentMode == watch_Type.ALARM.ordinal()) {	
			int cur = mode_alarm.getCursor();
			blink_cursor(cur);
    	}
    	else if(currentMode==watch_Type.STOPWATCH.ordinal()) {
    		int cur=this.mode_stop.getCursor();
    		blink_cursor(cur);
    	}
   }
    
    public void blink_cursor(int cur_num) {
    	
    	if(cur_num == -1)
    		return;
    	for(int i =0; i < text.length; i++) {
    		if(i == cur_num) {
    			if(text[i].isVisible() == true) {
    				text[i].setVisible(false);
    			}
    			else {
    				text[i].setVisible(true);
    			}
    		}
    		if((i != cur_num) && text[i].isVisible() == false) {
    			text[i].setVisible(true);
    		}
    	}
    	return;
    }
    
    public void visible_all() {
    	for(int i =0; i < text.length; i ++) {
    		if(text[i].isVisible() == false) {
    			text[i].setVisible(true);
    		}
    	}
    }
    
    public String dow(int week_of_day_num) {
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

    public boolean changeMode(){
    	previousMode = currentMode;
    	
    	//pop占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈짗占쎌굲玲곷엪�삕 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻�뜝占� 占쎈쐻占쎈솏熬곷㈇��占쎌굲
    	currentMode = modeQ.poll();
    	//占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈솏占쎈짗占쎌굲 占쎄괠占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲.
    	modeQ.offer(previousMode);
    	visible_all();
    	show_mode();
    	setTitle("Digital Watch - "+ currentMode);
        return false;
    }

    private boolean modeTimeOut(){

        return false;
    }

    private boolean changeModeBuzzer(){

        return false;
    }

    private boolean changeMainEverySeconds(){

        return false;
    }

    private void checkAlarm(){
    	if(mode_bz.getbuzzer() == 1) { //占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈뼓筌뤿슣�굲
    		if(mode_bz.getLeftTime() == 0) { //占쎈쐻占쎈뻻�ⓦ끉�굲 占쎈쐻占쎈짗占쎌굲占쎈쐻�뜝占� 占쎈쐻占쎈뼣筌뚭쑴�굲
    			mode_bz.turnOffBuzzer();
    			return;
    		}
    		else {
    			mode_bz.subTimeBuzer();
    			return;
    		}
    	}
    	Calendar t1 = mode_time.getRealTime();
    	if(t1 == null)
    		return;
    	int t1_h = t1.get(Calendar.HOUR_OF_DAY);
    	int t1_m = t1.get(Calendar.MINUTE);
    	int t1_s = t1.get(Calendar.SECOND);
    	 
    	Calendar t2 = mode_alarm.getRealAlarm();
    	if(t2 ==null)
    		return;
    	int t2_h = t2.get(Calendar.HOUR_OF_DAY);
    	int t2_m = t2.get(Calendar.MINUTE);
    	int t2_s = t2.get(Calendar.SECOND);
    	
    	if((t1_h == t2_h) && (t1_m == t2_m) && (t1_s == t2_s)) {
    		mode_bz.onBuzzer();
    	}else {
    	}
        return;
    }

    private boolean checkDday(){

        return false;
    }

    private boolean checkTimer(){

        return false;
    }

    private boolean addCurrentModeTime(){

        return false;
    }

    public static void main(String[] args) {
        Watch app = new Watch();
    }

    int flag = 0;
    @Override
    public void run() {
    	while(true) {
    		
    		//占쎈쐻占쎈뼑占쎌뵛占쎌굲筌ｋ똾寃�, 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲.
    		checkAlarm();
    		
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
