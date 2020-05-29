package Controller;

import Format.Format;
import Mode.Mode;
import UI.Button;

import java.util.Queue;

public class Watch implements  Runnable{
    private Mode currentMode;
    private Mode previousMode;
    private Queue<Mode> queue;

    public void init(){

    }

    public void pressButton(Button button){

    }

    public void display(Format format){

    }

    public boolean changeMode(){

        return false;
    }

    private boolean modeTimeOut(){

        return false;
    }

    private boolean changeModeBuzzer(){

        return false;
    }


    private boolean changeMainEverySeconds(){

        return false;
    }

    private boolean checkAlarm(){

        return false;
    }

    private boolean checkDday(){

        return false;
    }

    private boolean checkTimer(){

        return false;
    }

    private boolean addCurrentModeTime(){

        return false;
    }


    @Override
    public void run() {

    }

}
