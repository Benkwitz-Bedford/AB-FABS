package gui_stat_gen;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import singleton_holders.SingletonHolder;

public class RunningFeedbackFrame extends JFrame implements Runnable
{
	TextUpdaterLabel update = new TextUpdaterLabel();
	JLabel feed = new JLabel();
	
	Thread feedThread = null;
	
	public RunningFeedbackFrame()
	{
		this.setPreferredSize(new Dimension(1000,100));
		this.setLayout(new GridBagLayout());
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		Dimension preferredSize = new Dimension(1000,30);
		update.setPreferredSize(preferredSize);
		feed.setPreferredSize(preferredSize);
		
		GridBagConstraints con = new GridBagConstraints();
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(feed, con);
		
		con = new GridBagConstraints();
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 1;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(update, con);
		
		feed.setText("generating...");
		this.pack();
	    feedThread = new Thread(update, "feed");
		
	}

	public void run() {

		this.setVisible(true);
		if(feedThread.isInterrupted())
		{
			feedThread = new Thread(update, "feed");
		}
		feedThread.start();
		
		long lastMeasure = 0;
		boolean going = true;
		do
		{
			long time = System.currentTimeMillis() - lastMeasure ;
			
			if(time > 100)
			{
				lastMeasure = System.currentTimeMillis();
				if(feed.getText().equals("generating   "))
				{
					feed.setText("generating.  ");
				}else
				{
					if(feed.getText().equals("generating.  "))
					{
						feed.setText("generating.. ");
					}else
					{
						if(feed.getText().equals("generating.. "))
						{
							feed.setText("generating...");
						}else
						{
							if(feed.getText().equals("generating..."))
							{
								feed.setText("generating   ");
							}
						}
					}
				}
			}
			if(SingletonHolder.isRunning() != true)
			{
				feed.setText("done         ");
				feedThread.interrupt();
				going = false;
			}
		}while(going);
		//feed.setText(SingletonHolder.getStatEngine().printout());
	}
}
