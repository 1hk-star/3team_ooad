package Mode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.swing.JButton;

public class WorldTime extends Mode{

	private String country;
	private String country_temp;
    private Map<String, String> countries;
    private int cur_cursor = -1;
    private Queue<String> countriesQ = new LinkedList<String>();
	private Queue<String> countriesQ_temp = new LinkedList<String>();
    
    public WorldTime() {
		countries = new HashMap<String, String>();
		countries.put("LIS", "Europe/Lisbon");
		countries.put("LON", "Europe/London");
		countries.put("ROM", "Europe/Rome");
		countries.put("SEL", "Asia/Seoul");
		countries.put("TYO", "Asia/Tokyo");
		countries.put("LAX", "America/Los_Angeles");
		countries.put("DEN", "America/Denver");
		country = country_temp = "SEL";
		countriesQ.offer("LIS");
		countriesQ.offer("LON");
		countriesQ.offer("ROM");
		countriesQ.offer("TYO");
		countriesQ.offer("LAX");
		countriesQ.offer("DEN");
		countriesQ_temp.addAll(countriesQ);
	}

    @Override
    public void work(JButton button) {
    	String text = button.getText();
    	if(text.equals("Button1")) {
    		// no
    		changeCountry();
    	}
    	else if(text.equals("Button2")) {
    		// next
			nextCountry();
    	}
    	else if(text.equals("Button3")) {
    		changeMode();
    		// mode
    	}
    	else if(text.equals("Button4")) {
    		confirmCountry();
    		// end
    	}
    	else {
    		System.err.println("error button");
    	}
    }

    private void changeCountry() {
		cur_cursor = 2;
    }

    private void nextCountry(){
		if(cur_cursor == 2) {
			countriesQ.offer(country);
			country = countriesQ.poll();
		}
    }

    private void changeMode(){
		country = country_temp;
		countriesQ.clear();
		countriesQ.addAll(countriesQ_temp);
		cur_cursor = -1;
	}

    private void confirmCountry(){
		if(cur_cursor == 2) {
			country_temp = country;
			cur_cursor = -1;
			countriesQ_temp.clear();
			countriesQ_temp.addAll(countriesQ);
		}
    }
    
    public String getKey() {
    	return country;
    }

    public String getValue() {
    	return countries.get(country);
    }

    public int getCursor() {
    	return cur_cursor;
    }

    // Test를 위해 추가한 함수
	public Queue<String> get_countriesQ(){
    	return countriesQ;
	}

	public Queue<String> get_countriesQ_temp(){
		return countriesQ;
	}
}
