package Mode;

import Format.Format;
import UI.Button;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.swing.JButton;

public class WorldTime extends Mode{

	private int flag_set = 0;
	private String country;
	private String country_temp;
    private Map<String, String> countries;
    int cur_cursor = -1;
    Queue<String> countriesQ = new LinkedList<String>();
    
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
		for(Map.Entry<String, String> elem : countries.entrySet()) {
			if(elem.getKey().equals(country)) {
				continue;
			}
			countriesQ.offer(elem.getKey());
		}
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
    		countriesQ.clear();
    		country = country_temp;
    		
    		for(Map.Entry<String, String> elem : countries.entrySet()) {
    			if(elem.getKey().equals(country)) {
    				continue;
    			}
    			countriesQ.offer(elem.getKey());
    		}
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

    public void showWorldTime(){
    	
    }
    
    public void changeCountry() {
    	if(flag_set == 0) {
    		flag_set = 1;
    		cur_cursor = 2;
    	}
    }

    public void nextCountry(){
    	if(flag_set == 1) {
    		countriesQ.offer(country);
        	country = countriesQ.poll();
    	}
    }

    public boolean confirmCountry(){
    	country_temp = country;
    	if(flag_set == 1) {
    		flag_set = 0;
    		cur_cursor = -1;
    	}
        return false;
    }
    
    public String get_key() {
    	return country;
    }
    
//    public String get_key_temp() {
//    	return country_temp;
//    }
    
    public String get_value() {
    	return countries.get(country);
    }
    
    public int get_flag() {
    	return flag_set;
    }
    
    public int getCursor() {
    	return cur_cursor;
    }
}
