package gui_stat_gen;

import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextPane;

import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;

@SuppressWarnings("serial")
public class TextUpdaterLabel extends JLabel implements Runnable
{

	public void run() {
		long time = System.currentTimeMillis();
		long previousTime = time;
		int counter = 0;
		do
		{
			if(SingletonHolder.isRunning())
			{
				time = System.currentTimeMillis();
				counter = 0;
				if(time-previousTime > 50)
				{
					previousTime = time;
					this.setText("Jump Size: "+SingletonHolder.getJumpSize()+"      Jumps Per Increment: "+SingletonHolder.getJumpsPerIncrement()+"      Brake size: "+SingletonHolder.getBrakes()+"     Increment: "+SingletonHolder.getIncrement()+"     Jumps: "+SingletonHolder.getJumpCounter()+"     cells: "+SingletonStatStore.getTotalCells()/*+"     collisions: "+SingletonStatStore.getTotalCollisions()*/);
				}
			}else
			{
				if(counter == 0) 
				{
					counter++;
					//this.setText("Jump Size: "+SingletonHolder.getJumpSize()+"      Jumps Per Increment: "+SingletonHolder.getJumpsPerIncrement()+"      Brake size: "+SingletonHolder.getBrakes()+"     Increment: "+SingletonHolder.getIncrement()+"     Jumps: "+SingletonHolder.getJumpCounter());
				}
			}
		}while(true);
	}

}
