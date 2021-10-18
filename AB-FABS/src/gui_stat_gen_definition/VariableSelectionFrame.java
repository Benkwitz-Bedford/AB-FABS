package gui_stat_gen_definition;

import gui_stat_gen.StatBuildFrame;

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

public class VariableSelectionFrame extends JFrame implements ActionListener{

	


	
	JButton button = new JButton("generate");
	JButton popAddButton = new JButton("add criteria");
	JButton popRemoveButton = new JButton("remove criteria");
	VariableSelectionPanel builder;
	
	JPanel mainList = new JPanel(new GridBagLayout());
	JScrollPane scroller = new JScrollPane(mainList);
	ArrayList<VariableSelectionPanel> popPanels = new ArrayList<VariableSelectionPanel>();
	
	JCheckBox invertBox = new JCheckBox("Enviro");
	JCheckBox onlyBox = new JCheckBox("Evo");
	private String[][] metrics;
	
	
	public VariableSelectionFrame(String[][] builders)
	{
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		builder = new VariableSelectionPanel(invertBox.isSelected());
		Dimension textFieldSize = new Dimension(100,20);
		metrics = builders;
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
		
		invertBox.setToolTipText("inverts the selection definition");
		onlyBox.setToolTipText("if off cells will only count if withing the range defined, if on they will count if they are ever in that range");
		
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
		con.gridwidth = 22;
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
		this.add(popRemoveButton, con);
		
		
		con = new GridBagConstraints();
		con.gridx = 14;
		con.gridy = 0;
		con.gridwidth = 2;
		con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(invertBox, con);
		
		
		con = new GridBagConstraints();
		con.gridx = 18;
		con.gridy = 0;
		con.gridwidth = 2;
		con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(onlyBox, con);
		
		con = new GridBagConstraints();
		con.gridx = 20;
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
			//StatBuildFrame stats = new StatBuildFrame(false, new double[]{Double.parseDouble(metrics[0][0]),Double.parseDouble(metrics[0][1]),Double.parseDouble(metrics[0][2]),Double.parseDouble(builders[0][0]),Double.parseDouble(builders[0][1]),Double.parseDouble(builders[0][2])});
			StatBuildFrame stats = new StatBuildFrame(metrics, builders); 
			stats.run();
			//run the factory cycle
		}else
		{
			if(arg0.getActionCommand().equals("pop"))
			{
				//System.out.println("pop");
				for(int i = 0; i < popPanels.size();i++)
				{
					if(popPanels.get(i).getActive() == false)
					{
						popPanels.remove(i);
						mainList.remove(i);
						i--;
					}
				}
				VariableSelectionPanel sp = new VariableSelectionPanel(invertBox.isSelected());
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
							VariableSelectionPanel sp = popPanels.get(i);
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
