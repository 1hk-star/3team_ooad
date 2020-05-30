package Controller;

import Format.Format;
import Mode.Alarm;
import Mode.Mode;
import UI.Button;
import java.awt.*;
import java.awt.event.*;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import Mode.*;
import Mode.Timer;
import Type.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class Watch extends JFrame implements Runnable{

    JButton button1 = new JButton("Button1");
    JButton button2 = new JButton("Button2");
    JButton button3 = new JButton("Button3");
    JButton button4 = new JButton("Button4");
 
    JLabel[] text = new JLabel[9];
    
    //text1 : 월, text2 : 일, text3 : 요일
    //text4 : 시, text5 : 분, text6 : 초
    //text7 : 시, text8 : 분(연도), text9 : 초

    JPanel container = new JPanel();
    JPanel leftButton = new JPanel();
    JPanel centerText = new JPanel();
    JPanel rightButton = new JPanel();
    
    TimeKeep mode_time;
	Alarm mode_alarm;
	WorldTime mode_world;
	StopWatch mode_stop;
	Dday mode_dday;
	Timer mode_timer;
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
        Format format = null;
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
				pressButton(button1);
			}
        	 
         });
         button2.addActionListener(new ActionListener() {

 			@Override
 			public void actionPerformed(ActionEvent arg0) {
 				// TODO Auto-generated method stub
 				pressButton(button2);
 			}
         	 
          });
         button3.addActionListener(new ActionListener() {

  			@Override
  			public void actionPerformed(ActionEvent arg0) {
  				// TODO Auto-generated method stub
  				pressButton(button3);
  			}
          	 
           });
         button4.addActionListener(new ActionListener() {

  			@Override
  			public void actionPerformed(ActionEvent arg0) {
  				// TODO Auto-generated method stub
  				pressButton(button4);
  			}
          	 
           });
    }
    
    public void init_mode() {
    		
    	mode_fa = new FunctionActivator();
    	mode_bz = new Buzzer();
    	
    	mode_time = new TimeKeep();
    	mode_alarm = new Alarm();
    	mode_world = new WorldTime();
    	mode_stop = new StopWatch();
    	
    	modeQ.offer(watch_Type.ALARM.ordinal());
    	modeQ.offer(watch_Type.WORLDTIME.ordinal());
    	modeQ.offer(watch_Type.STOPWATCH.ordinal());
    	show_mode();
    	
    	mode_dday = new Dday();
    	mode_timer = new Timer();
    	mode_fa = new FunctionActivator();
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

    public void pressButton(JButton button ){
    	if(button.getText().equals("Button3")) {
    		changeMode();
    	}
    	if(currentMode == watch_Type.TIMEKEEPING.ordinal()) {
    		mode_time.work(button);
    		if(mode_time.get_flag() == 1)
    			display();
    	}
    	else if(currentMode == watch_Type.ALARM.ordinal()) {
    		mode_alarm.work(button);
    	}
    	else if(currentMode == watch_Type.WORLDTIME.ordinal()) {
    		mode_world.work(button);
    	}
    	else if(currentMode == watch_Type.STOPWATCH.ordinal()) {
    		mode_stop.work(button);
    	}
    	else if(currentMode == watch_Type.DDAY.ordinal()) {
    		mode_dday.work(button);
    	}
    	else if(currentMode == watch_Type.TIMER.ordinal()) {
    		mode_timer.work(button);
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

    public void display(){
    	//text1 : 월, text2 : 일, text3 : 요일
        //text4 : 시, text5 : 분, text6 : 초
        //text7 : 시, text8 : 분(연도), text9 : 초
    	if(currentMode == watch_Type.TIMEKEEPING.ordinal()) {
    			Calendar cal = mode_time.gettime();
    			text[0].setText(Integer.toString(cal.get(Calendar.MONTH)+1));
    			text[1].setText(Integer.toString(cal.get(Calendar.DATE)));
    			text[2].setText(dow(cal.get(Calendar.DAY_OF_WEEK)));
    			text[3].setText(Integer.toString(cal.get(Calendar.HOUR)));
    			text[4].setText(Integer.toString(cal.get(Calendar.MINUTE)));
    			text[5].setText(Integer.toString(cal.get(Calendar.SECOND)));
    			text[7].setText(Integer.toString(cal.get(Calendar.YEAR)));
    			text[6].setText("");
    			text[8].setText("");
    	}
   }
    public void display_blink(){
    	//text1 : 월, text2 : 일, text3 : 요일
        //text4 : 시, text5 : 분, text6 : 초
        //text7 : 시, text8 : 분(연도), text9 : 초
    	if(currentMode == watch_Type.TIMEKEEPING.ordinal()) {	
    			int cur = mode_time.getCursor();
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
    
    public String dow(int week_of_day_num) {
    	String strWeek = null;
    	switch(week_of_day_num) {
    	case Calendar.MONDAY:
    	strWeek = "월";
    	break;
    	case Calendar.TUESDAY:
    	strWeek = "화";
    	break;
    	case Calendar.WEDNESDAY:
    	strWeek = "수";
    	break;
    	case Calendar.THURSDAY:
    	strWeek = "목";
    	break;
    	case Calendar.FRIDAY:
    	strWeek = "금";
    	break;
    	case Calendar.SATURDAY:
    	strWeek = "토";
    	break;
    	default:
    	strWeek = "일";
    	}
		return strWeek;
    }

    public boolean changeMode(){
    	previousMode = currentMode;
    	
    	//pop한 모드를 현재모드로 바꾸고
    	currentMode = modeQ.poll();
    	//이전 모드는 다시 큐에 삽입.
    	modeQ.offer(previousMode);
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

    private boolean checkAlarm(){

        return false;
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
    		
    		if(((currentMode == watch_Type.TIMEKEEPING.ordinal()) && (mode_time.get_flag() ==1)))
    			flag = 1;
    		if(flag == 0)
    			display();
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
