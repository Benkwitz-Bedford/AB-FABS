package gui_options;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import cycles.Cycle;
import cycles.LiveCycle;
import singleton_holders.SingletonHolder;
import file_manipulation.MockUpTrajMaker;

@SuppressWarnings("serial")
public class CellOptionsFrame extends JFrame implements ActionListener
{


	
	JButton button = new JButton("apply");
	JButton popAddButton = new JButton("add pop subset");
	CellOptionsPanel builder;
	
	JPanel mainList = new JPanel(new GridBagLayout());
	JScrollPane scroller = new JScrollPane(mainList);
	private Cycle cyc;
	ArrayList<CellOptionsPanel> popPanels = new ArrayList<CellOptionsPanel>();
	
	public CellOptionsFrame(String string, String string2, LiveCycle cyc)
	{
		this.cyc = cyc;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		builder = new CellOptionsPanel(string, string2);
		Dimension buttonSize = new Dimension(50,20);
		Dimension fieldSize = new Dimension(100,20);
		Dimension textFieldSize = new Dimension(100,20);

		
		this.setSize(new Dimension(1000,770));
		this.setPreferredSize(new Dimension(1000,770));
		

		this.setLayout(new GridBagLayout());
		
		button.setPreferredSize(textFieldSize);
		popAddButton.setPreferredSize(textFieldSize);
		
		button.setActionCommand("ok");
		button.addActionListener(this);
		popAddButton.setActionCommand("pop");
		popAddButton.addActionListener(this);
		
		
		
		GridBagConstraints con = new GridBagConstraints();
		/*con.gridx = 0;
		con.gridy = 0;
		con.gridwidth = 2;
		con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(builder, con);*/
		
		mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);

        
        con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 1;
		con.gridwidth = GridBagConstraints.REMAINDER;
		con.gridheight = GridBagConstraints.REMAINDER;
		scroller = new JScrollPane(mainList);
		scroller.setPreferredSize(new Dimension(900, 600));
		getContentPane().add(scroller, con);
		scroller.setVisible(false);
		 
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 0;
		con.gridwidth = 2;
		con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(popAddButton, con);
		
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 0;
		con.gridwidth = 2;
		con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(button, con);
		//builder.validate();
		

		popAddButton.doClick();
		this.pack();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("ok"))
		{
			String[][] builders = new String[popPanels.size()][];
			for(int i = 0; i < popPanels.size(); i++)
			{
				builders[i] = popPanels.get(i).getValues();
			}
			SingletonHolder.giveFullCellValueSet(builders);
			if(SingletonHolder.getRunType().equals("replay"))
			{
				SingletonHolder.setCellLength(Integer.parseInt(builders[0][35]));
				System.out.println("new length: "+SingletonHolder.getCellLength());
			}
			if(cyc != null)
			{
				SingletonHolder.setRunning(false);
				//needs a wait block as well to make sure it doesnt execute stuff at the same time
				//other options could be to reset any time a setting is changed so just remake the thread with new options, easiest and cleanest
				//otherwise create an interrupt method in thread that clears the current iteration then pauses
				//this way is messy but seems to work for now
				Long time = System.currentTimeMillis();
				do
				{
				}while(System.currentTimeMillis()-time < 1000);
				cyc.checkFlags();
				SingletonHolder.setRunning(true);
			}
		}else
		{
			if(arg0.getActionCommand().equals("pop"))
			{
				for(int i = 0; i < popPanels.size();i++)
				{
					if(popPanels.get(i).getActive() == false)
					{
						popPanels.remove(i);
						mainList.remove(i);
						i--;
					}
				}
				CellOptionsPanel sp = new CellOptionsPanel("random", "live");
				sp.setPosition(popPanels.size());
				if(popPanels.size() == 0)
				{

				    //this.setLocationRelativeTo(null);
					scroller.setVisible(true);
					scroller.validate();
					GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridwidth = GridBagConstraints.REMAINDER;
                    gbc.weightx = 1;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    mainList.add(sp, gbc, popPanels.size());
					sp.setDeletePoss(false);
				}else
				{
					
					
						GridBagConstraints gbc = new GridBagConstraints();
	                    gbc.gridwidth = GridBagConstraints.REMAINDER;
	                    gbc.weightx = 1;
	                    gbc.fill = GridBagConstraints.HORIZONTAL;
	                    mainList.add(sp, gbc, popPanels.size());
	                    for(int i = 1; i < popPanels.size(); i++)
	                    {
	                    	popPanels.get(i).setDeletePoss(true);
	                    }
					
                    
                    
				}
				popPanels.add(sp);
				mainList.validate();
				mainList.repaint();
				scroller.validate();
				scroller.repaint();
				//this.validate();
				//this.repaint();
			}
		}
		
	}

	public void start()
	{
		this.setVisible(true);
	}

	public void setCycle(Cycle cycle) {
		cyc = cycle;
		
	}
	

}
