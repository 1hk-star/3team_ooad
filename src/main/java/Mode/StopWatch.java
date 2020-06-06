package Mode;

import java.util.Calendar;

import javax.swing.JButton;



public class StopWatch extends  Mode{

	//private attributes 
    
    private long start=0L;
    private long end=0L;
    private long elapse=0L;
    private long elapsePrevious=0L;

    private Calendar cal=null;
    private Calendar calLap=null;
    
    private boolean isOn=false;

    //constructor
	public StopWatch() {
		resetStopWatch();
	}
    
	//method implemented
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

    //start and stop
    public void startStopWatch(){

    	this.isOn=true;
    	
    	this.start=calculateTime(this.start,this.cal);
    }

    public void stopStopWatch(){
    	this.isOn=false;
    	
    	this.elapsePrevious=this.elapse;

    }
    
 

    //init or reset
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
    	
    	this.calLap=Calendar.getInstance();
    	calLap.set(Calendar.HOUR, 0);
    	calLap.set(Calendar.MINUTE, 0);
    	calLap.set(Calendar.SECOND, 0);
    	
    }

    //store laptime
    public void storeLapTime(){

    	if(this.isOn==false){
    		return;
    	}
    	
    	long hour=elapse/3600;
    	long min=(elapse%3600/60);
    	long sec=(elapse%3600)%60;
    	
    	calLap.set(Calendar.HOUR, (int) hour);
    	calLap.set(Calendar.MINUTE, (int) min);
    	calLap.set(Calendar.SECOND, (int) sec);
    	
    	System.out.println("laptime"+"시 :"+hour+"분: "+min+"초: "+sec);
    }


    //get attributes of stopwatch
	public Calendar getStopWatch() {
		// TODO Auto-generated method stub

		
		
		
		
		if(this.isOn==true) {//return elapse
			this.end=calculateTime(this.end,this.cal);
			this.elapse=this.elapsePrevious+(this.end-this.start);
			splitElapse(elapse,this.cal);
			

			
		}

		
		
		
		
		return cal;
	}


	public Calendar getLapTime() {
		// TODO Auto-generated method stub
		return this.calLap;
	}


	
	//Calendar -> long
	   private long calculateTime(long time,Calendar cal) {
	    	cal=Calendar.getInstance();
	        int hour=cal.get(Calendar.HOUR);
	        int min=cal.get(Calendar.MINUTE);
	        int sec=cal.get(Calendar.SECOND);
	        
	        time=(hour*3600+min*60+sec);
	        
	    	return time;
	    }
	    
	   //long->Calendar
	    private void splitElapse(long elapse,Calendar cal) {
	    	long hour=elapse/3600;
	    	long min=(elapse%3600/60);
	    	long sec=(elapse%3600)%60;
	    	
	    	cal.set(Calendar.HOUR, (int) hour);
	    	cal.set(Calendar.MINUTE, (int) min);
	    	cal.set(Calendar.SECOND, (int) sec);
	    	
	    	
	    	
	    	System.out.println("시 :"+hour+"분: "+min+"초: "+sec);
	    }
	    


}
