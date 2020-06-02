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
    private Map<String, Integer> countries;
    Queue<String> countriesQ = new LinkedList<String>();
    
    public WorldTime() {
		countries = new HashMap<String, Integer>();
		countries.put("LIS", -9);
		countries.put("LON", -9);
		countries.put("ROM", -8);
		countries.put("SEL", 0);
		countries.put("TYO", 0);
		countries.put("LAX", 7);
		countries.put("DEN", 8);
		country = country_temp = "SEL";
		for(Map.Entry<String, Integer> elem : countries.entrySet()) {
			if(elem.getKey().equals(country)) {
				continue;
			}
			countriesQ.offer(elem.getKey());
		}
		System.out.println(countriesQ);
	}

    @Override
    public void work(JButton button) {
    	String text = button.getText();
    	if(text.equals("Button1")) {
    		// no
    	}
    	else if(text.equals("Button2")) {
    		// next
    		changeCountry();
    	}
    	else if(text.equals("Button3")) {
    		countriesQ.clear();
    		country = country_temp;
    		
    		for(Map.Entry<String, Integer> elem : countries.entrySet()) {
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

    public void changeCountry(){
    	countriesQ.offer(country);
    	country = countriesQ.poll();
    }

    public boolean confirmCountry(){
    	country_temp = country;
    	flag_set = 1;
        return false;
    }
    
    public String get_key() {
    	return country;
    }
    
    public int get_value() {
    	return countries.get(country);
    }
    
    public int get_flag() {
    	return flag_set;
    }
}
