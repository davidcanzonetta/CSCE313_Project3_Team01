package team01;

import java.util.Timer;
import java.util.TimerTask;

//Similar class referenced from: http://enos.itcollege.ee/~jpoial/docs/tutorial/essential/threads/timer.html

/*

 	Example implementation:
 	Countdown c = new Countdown(5); //5 second count down
  	//...... Code .....
  	c.terminate();

*/

public class Countdown{
	Timer timer;
	
	public Countdown(long seconds){
		timer = new Timer();
		//No time limit when input is 0
		if(seconds == 0)
			timer.cancel();
		else
			timer.schedule(new Time(), seconds);
	}
	
	class Time extends TimerTask{
		public void run(){
			System.out.println("\nTIME");
			timer.cancel();
		}
	}
	
	//Use terminate in another class if functions done before timer up
	public void terminate()
	{
		timer.cancel();
	}
}