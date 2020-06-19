package Mode;

import
        java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;


public class Buzzer{

    private int leftTime = 15;
    private int buzzer_flag = 0; //0 no buzzer, 1 alarm, 2 timer
    private Clip clip;

    public int getLeftTime(){
        return leftTime;
    }

    private void soundBuzzer(){
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
            clip.start();
            clip.loop(10);
            
     } catch (Exception e) {
            System.out.println("err : " + e);
            }
    }

    public int getbuzzer() {
    	return buzzer_flag;
    }
    public void onBuzzer(int type) {
    	buzzer_flag =type;
    	leftTime = 15;
    	try {
            soundBuzzer();
      } catch(Exception e) {
    	    System.err.println("happened error in buzzer on");
        }
    }
    
    public void subTimeBuzzer() {
    	if(leftTime == 0)
            return;
    	else
    	    leftTime -=1;

    }
    public void turnOffBuzzer(){
    	buzzer_flag = 0;
    	if(clip != null) {
    		if(clip.isActive()) {
        		clip.stop();
        	}
    	}
    }


}
