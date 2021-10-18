package gui_stat_gen_definition;

import gui_options.CellOptionsPanel;

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

import running_modules_increment.PopSiftModule;
import singleton_holders.SingletonHolder;
import cycles.Cycle;
import cycles.LiveCycle;
import cycles.ReplayExtrapCycle;

public class PrioriiFrame extends JFrame implements ActionListener{

	


		
		JButton button = new JButton("generate");
		JButton popAddButton = new JButton("add criteria");
		JButton popRemoveButton = new JButton("remove criteria");
		JLabel intervalLabel = new JLabel("wipe interval");
		JFormattedTextField interval = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
		PrioriiPanel builder;
		
		JPanel mainList = new JPanel(new GridBagLayout());
		JScrollPane scroller = new JScrollPane(mainList);
		ArrayList<PrioriiPanel> popPanels = new ArrayList<PrioriiPanel>();
		
		JCheckBox invertBox = new JCheckBox("Invert");
		JLabel invertLabel = new JLabel("Invert");
		JCheckBox onlyBox = new JCheckBox("Only");
		JLabel onlyLabel = new JLabel("Only");
		
		File[] file = null;
		
		public PrioriiFrame(File[] file2)
		{
			setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			builder = new PrioriiPanel();
			Dimension textFieldSize = new Dimension(100,20);

			this.file = file2;
			this.setSize(new Dimension(1000,770));
			this.setPreferredSize(new Dimension(1000,770));
			

			this.setLayout(new GridBagLayout());
			
			button.setPreferredSize(textFieldSize);
			popAddButton.setPreferredSize(textFieldSize);
			popRemoveButton.setPreferredSize(textFieldSize);
			intervalLabel.setPreferredSize(textFieldSize);
			interval.setPreferredSize(textFieldSize);
			invertLabel.setPreferredSize(textFieldSize);
			onlyLabel.setPreferredSize(textFieldSize);
			
			button.setActionCommand("ok");
			button.addActionListener(this);
			popAddButton.setActionCommand("pop");
			popAddButton.addActionListener(this);
			popRemoveButton.setActionCommand("popr");
			popRemoveButton.addActionListener(this);
			
			interval.setToolTipText("the interval that stats will be reset on, 0 if you dont want one");
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
			con.gridx = 6;
			con.gridy = 0;
			con.gridwidth = 2;
			con.gridheight = 1;
			// con.fill = GridBagConstraints.BOTH;
			this.add(intervalLabel, con);
			
			con = new GridBagConstraints();
			con.gridx = 8;
			con.gridy = 0;
			con.gridwidth = 2;
			con.gridheight = 1;
			// con.fill = GridBagConstraints.BOTH;
			this.add(interval, con);
			
			con = new GridBagConstraints();
			con.gridx = 12;
			con.gridy = 0;
			con.gridwidth = 2;
			con.gridheight = 1;
			// con.fill = GridBagConstraints.BOTH;
			this.add(invertLabel, con);
			
			con = new GridBagConstraints();
			con.gridx = 14;
			con.gridy = 0;
			con.gridwidth = 2;
			con.gridheight = 1;
			// con.fill = GridBagConstraints.BOTH;
			this.add(invertBox, con);
			
			
			con = new GridBagConstraints();
			con.gridx = 16;
			con.gridy = 0;
			con.gridwidth = 2;
			con.gridheight = 1;
			// con.fill = GridBagConstraints.BOTH;
			this.add(onlyLabel, con);
			
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
				//Priori run code here, pass file through and create cycle with stat evaluation
				ReplayExtrapCycle rep = new ReplayExtrapCycle(file);
				PopSiftModule sift = new PopSiftModule(builders,Integer.parseInt(interval.getText()),invertBox.isSelected(),onlyBox.isSelected());
				rep.loadPopSiftModule(sift);
				SingletonHolder.setBrakes(0);
				rep.run();
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
					PrioriiPanel sp = new PrioriiPanel();
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
								PrioriiPanel sp = popPanels.get(i);
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
