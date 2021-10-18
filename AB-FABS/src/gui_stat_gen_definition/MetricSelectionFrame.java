package gui_stat_gen_definition;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cycles.ReplayExtrapCycle;
import running_modules_increment.PopSiftModule;
import singleton_holders.SingletonHolder;
import stat_data_holders.SnapShotObfust;

public class MetricSelectionFrame extends JFrame implements ActionListener{
	//needs to take in a shuck object and give the value when asking for the read out
	
	
	JButton button = new JButton("next");
	JButton popAddButton = new JButton("add criteria");
	JButton popRemoveButton = new JButton("remove criteria");
	MetricSelectionPanel builder;
	
	JPanel mainList = new JPanel(new GridBagLayout());
	JScrollPane scroller = new JScrollPane(mainList);
	ArrayList<MetricSelectionPanel> popPanels = new ArrayList<MetricSelectionPanel>();
	private SnapShotObfust snap;
	
	
	public MetricSelectionFrame(SnapShotObfust snap)
	{
		if(snap.getValueNames().size() == 0)
		{
			snap.populateNamesList();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		builder = new MetricSelectionPanel(snap);
		Dimension textFieldSize = new Dimension(150,20);
		this.snap = snap;
		this.setSize(new Dimension(1000,770));
		this.setPreferredSize(new Dimension(1000,770));
		

		this.setLayout(new GridBagLayout());
		
		button.setPreferredSize(textFieldSize);
		popAddButton.setPreferredSize(textFieldSize);
		popRemoveButton.setPreferredSize(textFieldSize);
		
		button.setActionCommand("ok");
		button.addActionListener(this);
		popAddButton.setActionCommand("pop");
		popAddButton.addActionListener(this);
		popRemoveButton.setActionCommand("popr");
		popRemoveButton.addActionListener(this);
		
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
        //mainList.setPreferredSize(new Dimension(1000,1000));
        mainList.add(new JPanel(), gbc);

        
        con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 1;
		con.gridwidth = GridBagConstraints.REMAINDER;//22;
		con.gridheight = GridBagConstraints.REMAINDER;
		scroller = new JScrollPane(mainList);
		scroller.setPreferredSize(new Dimension(900, 600));
		getContentPane().add(scroller, con);
		//scroller.setVisible(false);
		 
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 0;
		con.gridwidth = 2;
		con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(popAddButton, con);
		
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 0;
		con.gridwidth = 2;
		con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(popRemoveButton, con);
		
		
		con = new GridBagConstraints();
		con.gridx = 22;
		con.gridy = 0;
		con.gridwidth = 2;
		con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(button, con);
		//builder.validate();
		

		popAddButton.doClick();
		this.pack();
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
			VariableSelectionFrame fram = new VariableSelectionFrame(builders);
			fram.start();
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
				MetricSelectionPanel sp = new MetricSelectionPanel(snap);
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
			}else
			{
				if(arg0.getActionCommand().equals("popr"))
				{
					
					if(popPanels.size() == 1)
					{

					    
					}else
					{
						mainList.removeAll();
						popPanels.remove(popPanels.size()-1);
						for(int i = 0; i < popPanels.size();i++)
						{
							MetricSelectionPanel sp = popPanels.get(i);
							GridBagConstraints gbc = new GridBagConstraints();
		                    gbc.gridwidth = GridBagConstraints.REMAINDER;
		                    gbc.weightx = 1;
		                    gbc.fill = GridBagConstraints.HORIZONTAL;
		                    mainList.add(sp, gbc, i);
						}
					}
					mainList.validate();
					mainList.repaint();
					scroller.validate();
					scroller.repaint();
					//this.validate();
					//this.repaint();
				}
			}
		}
		
	}

	public void start()
	{
		this.setVisible(true);
	}

}
