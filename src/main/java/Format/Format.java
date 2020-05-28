package Format;

public class Format {


    private volatile  static Format uniqueInstance;

    /*
	 volatile 키워드는 다중 스레드 환경에서 한 스레드만 읽기와 쓰기를 하고,
	 나머지 스레드는 읽기만  할 때 가장 최신의 값을 보장해줍니다..

	 */





    private int hour;
    private int min;
    private int sec;

    private String dday_memo;

    private int lapTimeHour;
    private int lapTimeMin;
    private int lapTimeSec;

    private int elapsedTime;

    private int cursor;

    private String weekDay;
    private int month;
    private int day;


    private Format(){
        //유일성을 보장하기 위해 일부러 private한 것...
    }

    public void setTime(){

    }

    public void elapsedTime(){

    }

    public String get_dday_memo(){

        return " ";
    }

    public static Format getFormat(){
        //전역 접근 보장 메소드, Format을 활용하려면 이 메소드를 활용하시면 됩니다.

        if(uniqueInstance==null) {
            synchronized(Format.class) {
                if(uniqueInstance==null) {
                    uniqueInstance=new Format();
                }
            }
        }
        return uniqueInstance;
    }

    public void refreshTime(){}

}
