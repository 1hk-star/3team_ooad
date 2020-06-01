package Format;

import java.util.Date;

public class Format {

    private int hour;
    private int min;
    private int sec;
    private String weekDay;
    private int month;
    private int day;
    
    private Date diff;
    
    private String dday_memo;

    private int lapTimeHour;
    private int lapTimeMin;
    private int lapTimeSec;

    private int elapsedTime;

    private int cursor;
    // -1 커서 위치 없음.
    // 0 월
    // 1 일
    // 2 요일
    // 3 시
    // 4 분
    // 5 초

    public void setTime(int seconds){
    	//seconds 만큼 더하라.
    	
    }

    public void elapsedTime(){

    }

    public String get_dday_memo(){

        return " ";
    }

    public static void getFormat(){
    	
    }

    public void refreshTime(){
    	
    }

}
