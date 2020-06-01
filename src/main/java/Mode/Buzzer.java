package Mode;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JButton;

import UI.Button;

public class Buzzer extends Mode{

    private int leftTime = 15;
    private int buzzer_flag = 0;
    Clip clip;
    @Override
    public void work(JButton button) {
    	
    }

    public int getLeftTime(){

        return leftTime;
    }

    public void soundBuzzer(){
    	File bgm;
        AudioInputStream stream;
        AudioFormat format;
        DataLine.Info info;
        
        bgm = new File("./alarm.wav");
        try {
            stream = AudioSystem.getAudioInputStream(bgm);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(stream);
            System.out.println("sound");
            clip.start();
            clip.loop(10);
            
     } catch (Exception e) {
            System.out.println("err : " + e);
            }
    }

    public int getbuzzer() {
    	return buzzer_flag;
    }
    public void onBuzzer() {
    	buzzer_flag =1;
    	System.out.println("on buzzer");
    	try {
            soundBuzzer();
      } catch(Exception e) {
      }
    	
    }
    
    public void subTimeBuzer() {
    	if(leftTime != 0)
    		leftTime -=1;
    	else
    		return;
    	System.out.println("leftTime : "+leftTime);
    }
    public void turnOffBuzzer(){
    	buzzer_flag = 0;
    	System.out.println("off buzzer");
    	if(clip != null) {
    		if(clip.isActive()) {
        		clip.stop();
        	}
    	}
    	leftTime = 15;
    }


}