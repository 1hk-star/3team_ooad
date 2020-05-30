package Controller;

import Format.Format;
import Mode.Mode;
import UI.Button;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Queue;

public class Watch extends JFrame implements Runnable{
    private Mode currentMode;
    private Mode previousMode;
    private Queue<Mode> queue;

    JButton button1 = new JButton("Button1");
    JButton button2 = new JButton("Button2");
    JButton button3 = new JButton("Button3");
    JButton button4 = new JButton("Button4");
    JTextField text1 = new JTextField(30);
    JTextField text2 = new JTextField(30);
    JTextField text3 = new JTextField(30);

    JPanel container = new JPanel();
    JPanel leftButton = new JPanel();
    JPanel centerText = new JPanel();
    JPanel rightButton = new JPanel();

    public Watch(){
        super("Digital Watch");
        init();
        Format format = null;
        display(format);
    }

    public void init(){
        getContentPane().add(container);

        leftButton.setLayout(new GridLayout(2, 1));
        centerText.setLayout(new GridLayout(3, 1));
        rightButton.setLayout(new GridLayout(2, 1));

        leftButton.add(button1);
        leftButton.add(button2);
        centerText.add(text1);
        centerText.add(text2);
        centerText.add(text3);
        rightButton.add(button3);
        rightButton.add(button4);
        
        button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		button2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

        container.setLayout(new BorderLayout());
        container.add(leftButton, BorderLayout.WEST);
        container.add(centerText, BorderLayout.CENTER);
        container.add(rightButton, BorderLayout.EAST);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,200);
        setVisible(true);
    }

    public void pressButton(Button button){

    }

    public void display(Format format){
    	text1.setText("hello");
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

    public static void main(String[] args) {
        Watch app = new Watch();
    }

    @Override
    public void run() {

    }

}
