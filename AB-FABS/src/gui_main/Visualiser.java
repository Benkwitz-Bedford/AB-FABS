package gui_main;

import gui_options.CellOptionsFrame;
import gui_options.DetailSaver;
import gui_options.EnvironmentalOptionsFrame;
import gui_stat_gen.TextUpdaterLabel;
import gui_stat_gen_definition.AnalyticsFrame;
import heatmaps.HeatMapFrame;
import heatmaps.HeatMapPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import cell_data_holders.CellHandler;
import cycles.LiveCycle;
import cycles.ReplayCycle;
import cycles.ReplayExtrapCycle;
import singleton_holders.SingletonHolder;
import file_manipulation.ExtrapTrajFileInterpreter; 

@SuppressWarnings("serial")
public class Visualiser extends JFrame implements ActionListener 
{
	
	
	private String fileName = null;
	
	private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

	private GridPanel grid = null;
	Thread gridThread = null;
	Thread feedThread = null;
	TextUpdaterLabel feedback = new TextUpdaterLabel();
	
	JScrollPane scroll = null;

	
	DetailSaver save = new DetailSaver();
	CellOptionsFrame trajBuild = new CellOptionsFrame("random","live",null);
	EnvironmentalOptionsFrame addOptions = new EnvironmentalOptionsFrame(null);
	JButton b3 = new JButton("-JumpSize");
	JButton b4 = new JButton("+JumpSize");
	
	//JButton b9 = new JButton("death "+SingletonHolder.isDeathFlag());
	//JButton b10 = new JButton("prolif "+SingletonHolder.isReplicationFlag());

	AnalyticsFrame anly = new AnalyticsFrame();

	public Visualiser(CellHandler cells, String fileNameInput, File file2, Boolean ca) 
	{
		SingletonHolder.setVidFileName(file2.getName());
		SingletonHolder.setFileName(fileNameInput);

		this.setTitle(SingletonHolder.getFileName());
		size = Toolkit.getDefaultToolkit().getScreenSize();
		size.setSize(size.getWidth()-1, size.getHeight()-1);
		this.setLocation(0, 0);
		fileName = fileNameInput;
		this.setPreferredSize(size);
		this.setLayout(new GridBagLayout());

		this.setTitle(SingletonHolder.getFileName());
		
		this.addGubbinz();
		
		grid = new GridPanel(new ReplayCycle(cells),ca);
		trajBuild.setCycle(grid.getCycle());
		addOptions.setCycle(grid.getCycle());
	    
	    
	   // getContentPane().add(grid);
	    pack();
	   // setLocationRelativeTo(null);

		scroll.getVerticalScrollBar().setMinimum(scroll.getHeight()/2);
		scroll.getHorizontalScrollBar().setMinimum(scroll.getWidth()/2);

	    setVisible(true);
	    
	    gridThread = new Thread(grid, "grid");
	    feedThread = new Thread(feedback, "feed");
	}
	
	

	public Visualiser(File file2, Boolean ca) 
	{

		this.setTitle(SingletonHolder.getFileName());
		size = Toolkit.getDefaultToolkit().getScreenSize();
		size.setSize(size.getWidth(), size.getHeight());
		this.setLocation(0, 0);
		fileName = "Live";
		SingletonHolder.setFileName(fileName);
		this.setPreferredSize(size);
		this.setLayout(new GridBagLayout());

		this.setTitle(SingletonHolder.getFileName());

		grid = new GridPanel(new LiveCycle(ca),ca);
		trajBuild.setCycle(grid.getCycle());
		addOptions.setCycle(grid.getCycle());
		this.addGubbinz();
	    
	    
	   // getContentPane().add(grid);
	    pack();
	   // setLocationRelativeTo(null);


		//System.out.println(scroll.getHeight()+" "+scroll.getWidth());
	    setVisible(true);
	    

		scroll.getVerticalScrollBar().setMinimum((int) (scroll.getHeight()/2-(scroll.getVerticalScrollBar().getSize().getHeight()/2)));
		scroll.getHorizontalScrollBar().setMinimum((int) (scroll.getWidth()/2-(scroll.getHorizontalScrollBar().getSize().getWidth()/2)));
		scroll.getVerticalScrollBar().setMinimum(250);
		scroll.getHorizontalScrollBar().setMinimum(300);
	    
	    gridThread = new Thread(grid, "grid");
	    feedThread = new Thread(feedback, "feed");
	}
	
	public Visualiser(String name, ExtrapTrajFileInterpreter tFI, Boolean ca) {
		SingletonHolder.setFileName(name);

		this.setTitle(SingletonHolder.getFileName());
		size = Toolkit.getDefaultToolkit().getScreenSize();
		size.setSize(size.getWidth(), size.getHeight());
		this.setLocation(0, 0);
		fileName = name;
		this.setPreferredSize(size);
		this.setLayout(new GridBagLayout());

		this.setTitle(SingletonHolder.getFileName());
		grid = new GridPanel(new ReplayExtrapCycle(tFI),ca);
		trajBuild.setCycle(grid.getCycle());
		addOptions.setCycle(grid.getCycle());
		
		this.addGubbinz();
		
	    
	    
	   // getContentPane().add(grid);
	    pack();
	   // setLocationRelativeTo(null);

		scroll.getVerticalScrollBar().setMinimum(scroll.getHeight()/2);
		scroll.getHorizontalScrollBar().setMinimum(scroll.getWidth()/2);

	    setVisible(true);
	    
	    gridThread = new Thread(grid, "grid");
	    feedThread = new Thread(feedback, "feed");
	}
	
	public Visualiser(String name, File[] files, Boolean ca) {
		SingletonHolder.setFileName(name);

		this.setTitle(SingletonHolder.getFileName());
		size = Toolkit.getDefaultToolkit().getScreenSize();
		size.setSize(size.getWidth(), size.getHeight());
		this.setLocation(0, 0);
		fileName = name;
		this.setPreferredSize(size);
		this.setLayout(new GridBagLayout());

		this.setTitle(SingletonHolder.getFileName());
		grid = new GridPanel(new ReplayExtrapCycle(files),ca);

		trajBuild.setCycle(grid.getCycle());
		addOptions.setCycle(grid.getCycle());
		this.addGubbinz();
		
	    
	    
	   // getContentPane().add(grid);
	    pack();
	   // setLocationRelativeTo(null);

		scroll.getVerticalScrollBar().setMinimum(scroll.getHeight()/2);
		scroll.getHorizontalScrollBar().setMinimum(scroll.getWidth()/2);

	    setVisible(true);
	    
	    gridThread = new Thread(grid, "grid");
	    feedThread = new Thread(feedback, "feed");
	}



	private void addGubbinz() 
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		menuBar.add(menu);
		
		JMenuItem trajWindow = new JMenuItem("Cell Options");
		menu.add(trajWindow);
		trajWindow.setActionCommand("traj");
		trajWindow.addActionListener(this);
		
		JMenuItem addOpWindow = new JMenuItem("Environment Options");
		menu.add(addOpWindow);
		addOpWindow.setActionCommand("addOp");
		addOpWindow.addActionListener(this);
		
		JMenuItem heatMap = new JMenuItem("Generate Heatmap");
		menu.add(heatMap);
		heatMap.setActionCommand("heat");
		heatMap.addActionListener(this);
		
		JMenuItem tails = new JMenuItem("Tails");
		menu.add(tails);
		tails.setActionCommand("tail");
		tails.addActionListener(this);
		
		JMenuItem spheres = new JMenuItem("Circles");
		menu.add(spheres);
		spheres.setActionCommand("circ");
		spheres.addActionListener(this);
		
		JMenuItem back = new JMenuItem("Background");
		menu.add(back);
		back.setActionCommand("back");
		back.addActionListener(this);
		
		JMenuItem mos = new JMenuItem("Mosaic HeatMap");
		menu.add(mos);
		mos.setActionCommand("mosaic");
		mos.addActionListener(this);
		
		JMenuItem iD = new JMenuItem("Show id's");
		menu.add(iD);
		iD.setActionCommand("id");
		iD.addActionListener(this);
		
		JMenuItem ovr = new JMenuItem("Show Overlappers");
		menu.add(ovr);
		ovr.setActionCommand("overlappers");
		ovr.addActionListener(this);
		

		JMenuItem bStr = new JMenuItem("Show Bez Strength");
		menu.add(bStr);
		bStr.setActionCommand("bStrength");
		bStr.addActionListener(this);
		
		JMenuItem dGthr = new JMenuItem("Gather Data");
		menu.add(dGthr);
		dGthr.setActionCommand("dGather");
		dGthr.addActionListener(this);
		
		JMenuItem showZones = new JMenuItem("Show Zones");
		menu.add(showZones);
		showZones.setActionCommand("showZones");
		showZones.addActionListener(this);
		
		this.setJMenuBar(menuBar);
		
		GridBagConstraints con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		con.gridwidth = 20;
		con.gridheight = 7;
		//con.ipadx = 965;
		con.fill = GridBagConstraints.BOTH;
		scroll = new JScrollPane(grid);
		scroll.setMinimumSize(new Dimension(1000,1000));
		
		
		con.weighty = 10;
	    getContentPane().add(scroll, con);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//scroll.getVerticalScrollBar().setPreferredSize(new Dimension(15,scroll.getHeight()/4));
		//scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(scroll.getWidth()/4,15));
	    
	    /*GridBagConstraints vCon = new GridBagConstraints();
		vCon.gridx = 8;
		vCon.gridy = 0;
		vCon.gridwidth = 7;
		vCon.gridheight = 7;
		vCon.ipadx = 965;
		vCon.fill = GridBagConstraints.BOTH;
		
		getContentPane().add(vid, vCon);*/
		
	    GridBagConstraints con1 = new GridBagConstraints();
	    con1.gridx = 0;
		con1.gridy = 8;
		con1.gridwidth = 10;
		con1.gridheight = 2;
		con1.fill = GridBagConstraints.BOTH;
	    //feedback.setSize(new Dimension(1000,0));
	    this.add(feedback,con1);
	    
	    Dimension buttonSize = new Dimension(130,20);
	    
	    GridBagConstraints con2 = new GridBagConstraints();
	    con2.gridx = 0;
		con2.gridy = 10;
		con2.gridwidth = 1;
		con2.gridheight = 1;
		con2.insets = new Insets(0, 10, 0, 10);
	    JButton b2 = new JButton("Pause");
	    b2.setSize(buttonSize);
	    b2.setVerticalTextPosition(SwingConstants.BOTTOM);
	   // b2.setHorizontalTextPosition(AbstractButton.LEADING);
	    b2.setActionCommand("pause");
	    
	    GridBagConstraints con3 = new GridBagConstraints();
	    con3.gridx = 1;
		con3.gridy = 10;
		con3.gridwidth = 1;
		con3.gridheight = 1;
		con3.insets = new Insets(0, 10, 0, 0);
	    b3 = new JButton("-JumpSize");
	    b3.setSize(buttonSize);
	    b3.setVerticalTextPosition(SwingConstants.BOTTOM);
	   // b3.setHorizontalTextPosition(AbstractButton.CENTER);
	    b3.setActionCommand("-js");
	    
	    GridBagConstraints con4 = new GridBagConstraints();
	    con4.gridx = 2;
		con4.gridy = 10;
		con4.gridwidth = 1;
		con4.gridheight = 1;
		con4.insets = new Insets(0, 0, 0, 10);
	    b4 = new JButton("+JumpSize");
	    b4.setSize(buttonSize);
	    b4.setVerticalTextPosition(SwingConstants.BOTTOM);
	  //  b4.setHorizontalTextPosition(AbstractButton.TRAILING);
	    b4.setActionCommand("+js");
	    
	    GridBagConstraints con5 = new GridBagConstraints();
	    con5.gridx = 3;
		con5.gridy = 10;
		con5.gridwidth = 1;
		con5.gridheight = 1;
		con5.insets = new Insets(0, 10, 0, 0);
	    JButton b5 = new JButton("-JumpsPer");
	    b5.setSize(buttonSize);
	    b5.setVerticalTextPosition(SwingConstants.BOTTOM);
	   // b5.setHorizontalTextPosition(AbstractButton.TRAILING);
	    b5.setActionCommand("-jpi");
	    
	    GridBagConstraints con6 = new GridBagConstraints();
	    con6.gridx = 4;
		con6.gridy = 10;
		con6.gridwidth = 1;
		con6.gridheight = 1;
		con6.insets = new Insets(0, 0, 0, 10);
	    JButton b6 = new JButton("+JumpsPer");
	    b6.setSize(buttonSize);
	    b6.setVerticalTextPosition(SwingConstants.BOTTOM);
	   // b6.setHorizontalTextPosition(AbstractButton.TRAILING);
	    b6.setActionCommand("+jpi");
	    
	    GridBagConstraints con7 = new GridBagConstraints();
	    con7.gridx = 5;
		con7.gridy = 10;
		con7.gridwidth = 1;
		con7.gridheight = 1;
		con7.insets = new Insets(0, 10, 0, 0);
	    JButton b7 = new JButton("Slower");
	    b7.setSize(buttonSize);
	    b7.setVerticalTextPosition(SwingConstants.BOTTOM);
	   // b7.setHorizontalTextPosition(AbstractButton.TRAILING);
	    b7.setActionCommand("slower");
	    
	    GridBagConstraints con8 = new GridBagConstraints();
	    con8.gridx = 6;
		con8.gridy = 10;
		con8.gridwidth = 1;
		con8.gridheight = 1;
		con8.insets = new Insets(0, 0, 0, 10);
		con8.anchor = GridBagConstraints.WEST;
	    JButton b8 = new JButton("Faster");
	    b8.setSize(buttonSize);
	    b8.setVerticalTextPosition(SwingConstants.BOTTOM);
	  //  b8.setHorizontalTextPosition(AbstractButton.TRAILING);
	    b8.setActionCommand("faster");
	    
	   /* GridBagConstraints con9 = new GridBagConstraints();
	    con9.gridx = 7;
		con9.gridy = 10;
		con9.gridwidth = 1;
		con9.gridheight = 1;
		con9.insets = new Insets(0, 10, 0, 0);
		con9.anchor = GridBagConstraints.EAST;
	    b9.setSize(buttonSize);
	    b9.setVerticalTextPosition(SwingConstants.BOTTOM);
	  //  b8.setHorizontalTextPosition(AbstractButton.TRAILING);
	    b9.setActionCommand("death");
	    
	    GridBagConstraints con10 = new GridBagConstraints();
	    con10.gridx = 8;
		con10.gridy = 10;
		con10.gridwidth = 1;
		con10.gridheight = 1;
		con10.insets = new Insets(0, 0, 0, 10);
		con10.anchor = GridBagConstraints.WEST;
	    b10.setSize(buttonSize);
	    b10.setVerticalTextPosition(SwingConstants.BOTTOM);
	  //  b8.setHorizontalTextPosition(AbstractButton.TRAILING);
	    b10.setActionCommand("rep");
	    */
	    
	    b2.addActionListener(this);
	    b3.addActionListener(this);
	    b4.addActionListener(this);
	    b5.addActionListener(this);
	    b6.addActionListener(this);
	    b7.addActionListener(this);
	    b8.addActionListener(this);
	   // b9.addActionListener(this);
	    //b10.addActionListener(this);
	    
	    this.add(b2, con2);
	    this.add(b3, con3);
	    this.add(b4, con4);
	    this.add(b5, con5);
	    this.add(b6, con6);
	    this.add(b7, con7);
	    this.add(b8, con8);
	   // this.add(b9, con9);
	   // this.add(b10, con10);

		
	}

	public void actionPerformed(ActionEvent e) {
		if(SingletonHolder.isSpeedFlag())
		{
			b3.setEnabled(false);
			b4.setEnabled(false);
		}else
		{
			b3.setEnabled(true);
			b4.setEnabled(true);
		}
		if(e.getActionCommand().equals("pause") && SingletonHolder.isRunning())
		{

			SingletonHolder.setRunning(false);
			//gridThread.interrupt();
			//System.out.println("pressed");
		}else
		{
			if(e.getActionCommand().equals("pause") && SingletonHolder.isRunning() == false)
			{
				SingletonHolder.setRunning(true);
			}else
			{
				if(e.getActionCommand().equals("+js"))
				{
					SingletonHolder.jumpSizeIncrement();
				}else
				{
					if(e.getActionCommand().equals("-js"))
					{
						SingletonHolder.jumpSizeDecrement();
					}else
					{
						if(e.getActionCommand().equals("+jpi"))
						{
							SingletonHolder.jumpsPerIncrementIncrement();
							SingletonHolder.setJumpCounter(SingletonHolder.getIncrement()*SingletonHolder.getJumpsPerIncrement());
						}else
						{
							if(e.getActionCommand().equals("-jpi"))
							{
								SingletonHolder.jumpsPerIncrementDecrement();
								SingletonHolder.setJumpCounter(SingletonHolder.getIncrement()*SingletonHolder.getJumpsPerIncrement());
							}else
							{
								if(e.getActionCommand().equals("slower"))
								{
									SingletonHolder.brakesIncrement();
								}else
								{
									if(e.getActionCommand().equals("faster"))
									{
										SingletonHolder.brakesDecrement();
									}else 
									{
										/*if(e.getActionCommand().equals("death"))
										{
											SingletonHolder.toggleDeath();
											b9.setText("death "+SingletonHolder.isDeathFlag());
											SingletonHolder.setRunning(false);
											//needs a wait block as well to make sure it doesnt execute stuff at the same time
											//other options could be to reset any time a setting is changed so just remake the thread with new options, easiest and cleanest
											//otherwise create an interrupt method in thread that clears the current iteration then pauses
											//this way is messy but seems to work for now
											Long time = System.currentTimeMillis();
											do
											{
											}while(System.currentTimeMillis()-time < 1000);
											grid.getCycle().checkFlags();
											SingletonHolder.setRunning(true);
										}else 
										{
											if(e.getActionCommand().equals("rep"))
											{
												SingletonHolder.toggleRep();
												b10.setText("prolif "+SingletonHolder.isReplicationFlag());
												SingletonHolder.setRunning(false);
												//needs a wait block as well to make sure it doesnt execute stuff at the same time
												//other options could be to reset any time a setting is changed so just remake the thread with new options, easiest and cleanest
												//otherwise create an interrupt method in thread that clears the current iteration then pauses
												//this way is messy but seems to work for now
												Long time = System.currentTimeMillis();
												do
												{
												}while(System.currentTimeMillis()-time < 1000);
												grid.getCycle().checkFlags();
												SingletonHolder.setRunning(true);
												
											}else 
											{*/
											
												if(e.getActionCommand().equals("mp4"))
												{
													//not currently working
													//grid.generateMp4();
												}else
												{
													if(e.getActionCommand().equals("traj"))
													{
														trajBuild.start();
													}else
													{
														if(e.getActionCommand().equals("detailsSaver"))
														{
															save.start();
														}if(e.getActionCommand().equals("addOp"))
														{
															addOptions.start();
														}
														if(e.getActionCommand().equals("heat"))
														{
															HeatMapFrame frame = new HeatMapFrame(810+20, 810+140 );
															frame.generate(grid.getPositionGrid(),SingletonHolder.getHeatTopper());
														}
														if(e.getActionCommand().equals("tail"))
														{
															if(SingletonHolder.isTail())
															{
																SingletonHolder.setTail(false);
															}else
															{
			
																SingletonHolder.setTail(true);
															}
														}
														if(e.getActionCommand().equals("circ"))
														{
															if(SingletonHolder.isCirc())
															{
																SingletonHolder.setCirc(false);
															}else
															{
			
																SingletonHolder.setCirc(true);
															}
														}
														if(e.getActionCommand().equals("back"))
														{
															if(SingletonHolder.isBack())
															{
																SingletonHolder.setBack(false);
															}else
															{
			
																SingletonHolder.setBack(true);
																final JFileChooser fc = new JFileChooser();
																fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
																fc.setDialogTitle("background image");
																FileFilter filter = new FileNameExtensionFilter("png", "png");
																fc.setFileFilter(filter);
																fc.showOpenDialog(null);
																File file = fc.getSelectedFile();
																try {
																	SingletonHolder.setBackImg(ImageIO.read(file));
																} catch (IOException e1) {
																	// TODO Auto-generated catch block
																	e1.printStackTrace();
																}
															}
														}
														if(e.getActionCommand().equals("mosaic"))
														{
															if(SingletonHolder.isMos())
															{
																SingletonHolder.setMos(false);
															}else
															{
			
																SingletonHolder.setMos(true);
																grid.getCycle().getSim().resetMos();
															}
														}
														if(e.getActionCommand().equals("mus"))
														{
															if(SingletonHolder.isMusic())
															{
																SingletonHolder.setMusic(false);
															}else
															{
			
																SingletonHolder.setMusic(true);
															}
														}
														if(e.getActionCommand().equals("id"))
														{
															if(SingletonHolder.isShowID())
															{
																SingletonHolder.setShowID(false);
															}else
															{
			
																SingletonHolder.setShowID(true);
															}
														}
														if(e.getActionCommand().equals("overlappers"))
														{
															if(SingletonHolder.isShowingOverlappers())
															{
																SingletonHolder.setShowingOverlappers(false);
															}else
															{
																SingletonHolder.setShowingOverlappers(true);
															}
														}
														if(e.getActionCommand().equals("bStrength"))
														{
															if(SingletonHolder.isBezStrengthFlag())
															{
																//System.out.println("toggle false");
																SingletonHolder.setBezStrengthFlag(false);
															}else
															{
																//System.out.println("toggle true");
																SingletonHolder.setBezStrengthFlag(true);
															}
														}
														if(e.getActionCommand().equals("dGather"))
														{
															anly.setCycle(grid.getCycle());
															anly.setVisible(true);
														}
														if(e.getActionCommand().equals("showZones"))
														{
															if(SingletonHolder.isShowZones())
															{
																SingletonHolder.setShowZones(false);
															}else
															{
																SingletonHolder.setShowZones(true);
																
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		//}
		
	//}

	public void run() {
		// TODO Auto-generated method stub
		feedThread.setPriority(Thread.NORM_PRIORITY);
		gridThread.setPriority(Thread.MAX_PRIORITY);
		gridThread.start();
		
		feedThread.start();
		//vidThread.start();
	}

}
