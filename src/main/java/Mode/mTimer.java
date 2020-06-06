package Mode;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;

public class mTimer extends Mode{
   
   int flag_set = 0; //       甄 ,  틈求 .
   int flag_sp = 0; //start硫� 1 pause硫� 0
   int flag_pause = 0;
   Calendar timer_time = null; // 羌        챨 .
   Calendar pre_time = null; //                    .

   
   Queue<Integer> cusorQ = new LinkedList<Integer>();
   private int cur_cursor = -1;
   
   Timer m_timer = new Timer();
   TimerTask m_task = new TimerTask() {
      @Override
      public void run() {
         if(pre_time==null)
             return;
         else if(pre_time.get(Calendar.HOUR_OF_DAY)==0&&pre_time.get(Calendar.MINUTE)==0&&
                pre_time.get(Calendar.SECOND)==0) {
            flag_pause = 0;
            pauseTimer();
             return;
          }else {
             flag_pause = 1;
          }
          pre_time.add(Calendar.SECOND,-1);
      }
   };
   
   public mTimer() {
      cusorQ.offer(3);
      cusorQ.offer(4);
      cusorQ.offer(5);
   }

    @Override
    public void work(JButton button) {
       String text = button.getText();
       if(text.equals("Button1")) {
          if(flag_set == 0)
             setTimer();
          else {
             moveCursor_timer();
          }
       }
       else if(text.equals("Button2")) {
          //set Activeif
          if(flag_set == 1) {
             plusTimer();
          }else {
             if(flag_sp==0)
                startTimer();
             else
                pauseTimer();
          }
  
       }
       else if(text.equals("Button3")) {
          //changemode
          cusorQ.clear();
          cusorQ.offer(3);
         cusorQ.offer(4);
         cusorQ.offer(5);
          flag_set =0;
          cur_cursor = -1;
          timer_time = null;
       }
       else if(text.equals("Button4")) {
          if(flag_set == 1) {
             confirmTimer();
          }else {
             stopTimer();
          }
       }
       else {
          System.err.println("error button");
       }
    }
    
    public Calendar getTimer() {
       if(flag_set == 0)
          return pre_time;
       return timer_time;
    }
    
    public int getCursor() {
       return cur_cursor;
    }
    
    public int get_flag() {
       return flag_set;
    }

    public void showTimer(){
       
    }
    
    public Calendar getTimerTime() {
       return pre_time;
    }
    public int getPauseFlag() {
       return flag_pause;
    }

    public void startTimer(){
       if(pre_time==null)
          return;
       System.out.println("start");
       flag_sp = 1;
      m_timer.schedule(m_task,0,1000);
    }

    public void stopTimer(){
       pre_time = null;
       pauseTimer();
    }

    public void pauseTimer(){
       System.out.println("pause");
       flag_sp = 0;
       m_task.cancel();
       m_timer = new Timer();
       m_task = new TimerTask() {
         public void run() {
            if(pre_time==null)
                return;
            else if(pre_time.get(Calendar.HOUR_OF_DAY)==0&&pre_time.get(Calendar.MINUTE)==0&&
                   pre_time.get(Calendar.SECOND)==0) {
               flag_pause = 0;
               pauseTimer();
                return;
             } else {
                flag_pause = 1;
             }
             pre_time.add(Calendar.SECOND,-1);
         }
       };
    }

    public void setTimer(){
       if(flag_set == 0) { //  챨          杉 .
          flag_set = 1;
          cur_cursor = cusorQ.poll(); // 첬         究 .
          if(pre_time == null) {
             timer_time = Calendar.getInstance();
             timer_time.set(Calendar.HOUR_OF_DAY, 0);
             timer_time.set(Calendar.MINUTE, 0);
             timer_time.set(Calendar.SECOND, 0);
          }
          else {
             timer_time = (Calendar)pre_time.clone(); 
          }
       }
       else { //  챨                .
       }
    }

    public void moveCursor_timer(){
       cusorQ.offer(cur_cursor);
       cur_cursor = cusorQ.poll();
    }

    public void plusTimer(){
       switch(cur_cursor) {
       case 3:
          int hour = timer_time.get(Calendar.HOUR_OF_DAY);
          timer_time.set(Calendar.HOUR_OF_DAY, (hour+1)%24);
       break;
       case 4:
          int mit = timer_time.get(Calendar.MINUTE);
          timer_time.set(Calendar.MINUTE, (mit+1)%60);
       break;
       case 5:
          int sec = timer_time.get(Calendar.SECOND);
          timer_time.set(Calendar.SECOND, (sec+1)%60);
       break;
       default:
       System.err.println("cursor add error.");
       break;
       }
    }

    public boolean confirmTimer(){
       //      狗 
       flag_set = 0;
       cusorQ.clear();
      cusorQ.offer(3);
      cusorQ.offer(4);
      cusorQ.offer(5);
       cur_cursor = -1;
       pre_time = (Calendar) timer_time.clone();
        return true;
    }

    public void minusTimer(){
       //System.out.println("cur_cusor: "+cur_cursor);
       switch(cur_cursor) {
       case 3:
          int hour = timer_time.get(Calendar.HOUR_OF_DAY);
          timer_time.set(Calendar.HOUR_OF_DAY, (hour-1)%24);
       break;
       case 4:
          int mit = timer_time.get(Calendar.MINUTE);
          timer_time.set(Calendar.MINUTE, (mit-1)%60);
       break;
       case 5:
          int sec = timer_time.get(Calendar.SECOND);
          timer_time.set(Calendar.SECOND, (sec-1)%60);
       break;
       default:
       System.err.println("cursor add ���ъ��.");
       break;
       }
    }


}