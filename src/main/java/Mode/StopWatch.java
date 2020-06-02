package Mode;

import java.util.Calendar;

import javax.swing.JButton;



public class StopWatch extends  Mode{

    private int cursor=-1;
    private int flag=0;
    
    private long start=0L;
    private long end=0L;
    private long elapse=0L;
    private long elapsePrevious=0L;
    private long lap=0L;

    private Calendar cal=null;
    private Calendar calLap=null;
    
    private boolean isOn=false;

	public StopWatch() {
		resetStopWatch();
	}
    
    public void work(JButton button) {

    	String text = button.getText();
    	if(text.equals("Button1")) {

    		storeLapTime();
    	}
    	else if(text.equals("Button2")) {

    		if(isOn==false) {
    			startStopWatch();
    		}
    		else {
    			stopStopWatch();
    		}
    		
    	}
    	else if(text.equals("Button3")) {
 
    		stopStopWatch();
    	}
    	else if(text.equals("Button4")) {

    		resetStopWatch();
    	}
    	else {
    		System.err.println("error button");
    	}
    	
    }

    public void startStopWatch(){

    	this.isOn=true;
    	
    	this.start=calculateTime(this.start,this.cal);
    }

    public void stopStopWatch(){
    	this.isOn=false;
    	
    	this.elapsePrevious=this.elapse;

    	

    }
    
    private long calculateTime(long time,Calendar cal) {
    	cal=Calendar.getInstance();
        int hour=cal.get(Calendar.HOUR);
        int min=cal.get(Calendar.MINUTE);
        int sec=cal.get(Calendar.SECOND);
        
        time=(hour*3600+min*60+sec);
        
    	return time;
    }
    
    private void splitElapse(long elapse,Calendar cal) {
    	long hour=elapse/3600;
    	long min=(elapse%3600/60);
    	long sec=(elapse%3600)%60;
    	
    	cal.set(Calendar.HOUR, (int) hour);
    	cal.set(Calendar.MINUTE, (int) min);
    	cal.set(Calendar.SECOND, (int) sec);
    	System.out.println("시 :"+hour+"분: "+min+"초: "+sec);
    }


    public void resetStopWatch(){

    	this.start=0L;
    	this.end=0L;
    	this.elapse=0L;
    	this.elapsePrevious=0L;
    	this.isOn=false;
    	cal=Calendar.getInstance();
    	cal.set(Calendar.HOUR, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	this.calLap=null;
    	this.lap=0L;
    }

    public void storeLapTime(){

//    	this.lap=this.elapse;
//    	this.calLap=Calendar.getInstance();
    	
    }


	public Calendar getStopWatch() {
		// TODO Auto-generated method stub

		
		
		
		
		if(this.isOn==true) {//return elapse
			this.end=calculateTime(this.end,this.cal);
			this.elapse=this.elapsePrevious+(this.end-this.start);
			splitElapse(elapse,this.cal);
			

			
		}

		
		
		
		
		return cal;
	}

	public int getCursor() {
		// TODO Auto-generated method stub
		return this.cursor;
	}

	public int get_flag() {
		// TODO Auto-generated method stub
		return flag;
	}

	public Calendar getLapTime() {
		// TODO Auto-generated method stub
		return this.calLap;
	}

	public void showStopWatch() {
		// TODO Auto-generated method stub
		
	}
}
