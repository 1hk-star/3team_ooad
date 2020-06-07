package Type;

import java.util.Calendar;

public class dday_data {
	private Calendar cal = null;
	private String memo = null;
	
	public dday_data() {
		
	}
	public dday_data(Calendar cal , String memo) {
		this.cal = (Calendar)cal.clone();
		this.memo = memo;
	}
	
	public void set_cal(Calendar cal) {
		this.cal = (Calendar)cal.clone();
	}
	public void set_memo(String memo) {
		this.memo = memo;
	}
	public Calendar get_cal() {
		return cal;
	}
	public String get_memo() {
		return memo;
	}
}
