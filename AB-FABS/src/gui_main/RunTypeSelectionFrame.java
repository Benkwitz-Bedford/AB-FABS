package gui_main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


import cycles.FakerRandomDavideConstructCreator;
import cycles.ReplayExtrapCycle;
import cell_data_holders.CellDataPackage;
import cell_data_holders.CellHandler;
import running_modules_increment.DataModule;
import running_modules_increment.PopSiftModule;
import singleton_holders.SingletonHolder;
import stat_data_holders.SnapShotObfust;
import stat_data_holders.StatGeneratorObfust;
import trajectory_components.PotentialProcessor;
import file_manipulation.DataShuffler;
import file_manipulation.ExtrapTrajFileInterpreter;
import file_manipulation.Sifter;
import file_manipulation.TrajExtrapolator;
import file_manipulation.TrajFileInterpreter;
import gui_options.CellOptionsFrame;
import gui_stat_gen.StatBuildFrame;
import gui_stat_gen.StatRunFeedbackFrame;
import gui_stat_gen_definition.MetricSelectionFrame;
import gui_stat_gen_definition.PrioriiFrame;

@SuppressWarnings("serial")
public class RunTypeSelectionFrame extends JFrame implements ActionListener
{
	int optionSelected = 0;

	
	private JComboBox<String> c = new JComboBox<String>();


	public RunTypeSelectionFrame()
	{

	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 Dimension buttonSize = new Dimension(50,20);
		 
		 c.addItem("run a live traj manipulation");
		 c.addItem("run a data gathering set");
		 c.addItem("run a live traj manipulation from details");
		 c.addItem("run a data gathering set from details");
		 c.addItem("load sequential extraps");
		 c.addItem("extrapolate from trackmate t,x,y csv");
		 JButton b = new JButton("ok");
		 b.setActionCommand("ok");
		 b.setPreferredSize(buttonSize);
		 this.setLayout(new GridBagLayout());
		 GridBagConstraints con = new GridBagConstraints();
		 con.gridx = 0;
		 con.gridy = 0;
		 con.gridwidth = 3;
		 con.gridheight = 1;
		 //con.ipadx = 965;
		 con.fill = GridBagConstraints.BOTH;
		 getContentPane().add(c, con);
		 con.gridx = 3;
		 getContentPane().add(b, con);
		 b.addActionListener(this);
		 this.setPreferredSize(new Dimension(1080, 200)); 
		 pack();
		 this.setTitle("Options");
		 c.setSelectedIndex(0);
	     setVisible(true);
	     
	     this.setLocationRelativeTo(null);
		 
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getActionCommand().equals("ok"))
		{
			optionSelected = c.getSelectedIndex();
			
			if(optionSelected == 0)
			{
	
				SingletonHolder.setRunType("live");
				Visualiser vis = new Visualiser(new File(System.getProperty("user.dir")),false);
				vis.run();
			}else
			{
				if(optionSelected == 1)
				{
	
					SingletonHolder.setRunType("stat");
					StatBuildFrame stats = new StatBuildFrame(false);
					stats.run();
				}else
				{
					if(optionSelected == 2)
					{
	
						SingletonHolder.setRunType("live");
						final JFileChooser fc = new JFileChooser();
						fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
						fc.setDialogTitle("details file");
						FileFilter filter = new FileNameExtensionFilter("txt", "txt");
						fc.setFileFilter(filter);
						fc.showOpenDialog(null);
						File file = fc.getSelectedFile();
						SingletonHolder.prepFromDetails(file);
						Visualiser vis = new Visualiser(new File(System.getProperty("user.dir")),false);
						vis.run();
					}else
					{
						if(optionSelected == 3)
						{
							SingletonHolder.setRunType("stat");
							final JFileChooser fc = new JFileChooser();
							fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
							fc.setDialogTitle("details file");
							FileFilter filter = new FileNameExtensionFilter("txt", "txt");
							fc.setFileFilter(filter);
							fc.showOpenDialog(null);
							File file = fc.getSelectedFile();
							SingletonHolder.prepFromDetails(file);
							StatBuildFrame stats = new StatBuildFrame(false);
							stats.run();
						}else
						{
							if(optionSelected == 4)
							{
								SingletonHolder.setRunType("replay");
								final JFileChooser fc = new JFileChooser();
								fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
								fc.setDialogTitle("Trajectory file");
								FileFilter filter = new FileNameExtensionFilter("txt", "txt");
								fc.setMultiSelectionEnabled(true);
								fc.setFileFilter(filter);
								fc.showOpenDialog(null);
								File[] file = fc.getSelectedFiles();
								//input traj data
								//hand cells to visualiser and run
								Visualiser vis = new Visualiser("ex_rep_with_"+file.length,file, false);
								SingletonHolder.setFileName("ex_rep_with_"+file.length);
								vis.run();
							}else
							{
								
								if(optionSelected == 5)
								{
									final JFileChooser fc = new JFileChooser();
									fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
									fc.setDialogTitle("Trajectory file");
									FileFilter filter = new FileNameExtensionFilter("csv", "csv");
									fc.setFileFilter(filter);
									fc.showOpenDialog(null);
									File file = fc.getSelectedFile();
									TrajExtrapolator extrap = new TrajExtrapolator();
									try {
										extrap.extrapolateFromTrackMateCSV(file);
									} catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
											
										
									
								
							
						}
					}
				}
				}
			}
			//this.setVisible(false);
			
		}
		
	}
	public int getOption() {
		// TODO Auto-generated method stub
		return optionSelected;
	}
}
