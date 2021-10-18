package gui_stat_gen;

import heatmaps.HeatMap;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;


import singleton_holders.SingletonHolder;
import singleton_holders.SingletonSanitisationFields;
import stat_data_holders.SnapShotObfust;
import file_manipulation.DataShuffler;
import file_manipulation.StatOffLoader;
import file_manipulation.TrackOffloader;

public class StatRunFeedbackFrame extends JFrame implements ActionListener, MouseWheelListener{
	
	JComboBox<String> runs = new JComboBox<String>();
	JComboBox<String> interval = new JComboBox<String>();
	JComboBox<String> imageType = new JComboBox<String>();
	
	//JButton commit = new JButton("File Offload");
	JComboBox<String> commit = new JComboBox<String>(); 
	
	DataFeedbackPanel data = new DataFeedbackPanel();
	
	JLabel imageHolder = new JLabel();
	
	JScrollPane dataScroll = null;
	JScrollPane imageScroll = null;
	
	String name = null;
	
	Boolean snapple;
	
	HeatMap heater = new HeatMap(3000,3000);
	
	NumberFormat doub = new DecimalFormat("#0.00"); 
	JFormattedTextField flatField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JButton flatButton = new JButton("Flat");
	
	JFormattedTextField gammaField = new JFormattedTextField(doub);
	JButton gammaButton = new JButton("gamma");
	
	JComboBox<String> runs2 = new JComboBox<String>();
	JComboBox<String> interval2 = new JComboBox<String>();
	JComboBox<String> imageType2 = new JComboBox<String>();
	
	JButton commit2 = new JButton("File Offload");
	
	DataFeedbackPanel data2 = new DataFeedbackPanel();
	
	JLabel imageHolder2 = new JLabel();
	
	JScrollPane dataScroll2 = null;
	JScrollPane imageScroll2 = null;
	
	Boolean snapple2;
	
	JFormattedTextField flatField2 = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JButton flatButton2 = new JButton("Flat");
	
	JFormattedTextField gammaField2 = new JFormattedTextField(doub);
	JButton gammaButton2 = new JButton("gamma");
	
	private ArrayList<ArrayList<SnapShotObfust>> snaps = null;
	private ArrayList<ArrayList<ArrayList<String>>> tables = new ArrayList<ArrayList<ArrayList<String>>>();
	private DataShuffler files;
	Dimension buttonSize = new Dimension(100,30);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double width = screenSize.getWidth();
	double height = screenSize.getHeight();
	Dimension boxSize = new Dimension((int)width/2-20,(int) height/2-50);
	
	
	String[] fileNames;

	public void handAndShowSnapSet(ArrayList<ArrayList<SnapShotObfust>> snapShots, ArrayList<ArrayList<ArrayList<String>>> tableList, String runName, String[] names) 
	{
		System.out.println("Name "+runName);
		snaps = snapShots;
		name = runName;
		tables = tableList;
		
		
	    for(int i = 0; i < snaps.get(0).size()-1; i++)
	    {
	    	//interval.addItem(name+" run "+i);
	    }
	    snapple = true;
	    this.setup(runName);
	    for(int i = 0;i < names.length;i++)
		{
			names[i] = names[i].replace("extrap", "");
			names[i] = names[i].replace(".txt", "");
		}
	    fileNames = names;
	    
	    this.addShuffleRuns();
	    this.pack();
	    this.setVisible(true);
	}

	private void buttonSetup() {
		flatField.setPreferredSize(buttonSize);
		flatButton.setPreferredSize(buttonSize);
		
		gammaField.setPreferredSize(buttonSize);
		gammaButton.setPreferredSize(buttonSize);
		
		flatField.setToolTipText("the flat top end value (set to 0 for deafult)");
		flatField.setText("0");
		gammaField.setToolTipText("gamma transformed value (set 1 for deafult)");
		gammaField.setText("1");
		
		flatButton.setActionCommand("intervalSelection");
		gammaButton.setActionCommand("intervalSelection");
		
		flatButton.addActionListener(this);
		gammaButton.addActionListener(this);
		
		GridBagConstraints con = new GridBagConstraints();
		
		commit.addItem("File Offload");
		commit.addItem("Offload results structure");
		commit.addItem("Off Load All Images");
		commit.addItem("Off Load Current Images");
		commit.addItem("Off Load Track Images");
		
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 0;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(flatField, con);
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 0;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(flatButton, con);
		
		
		con = new GridBagConstraints();
		con.gridx = 5;
		con.gridy = 0;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(gammaField, con);
		con = new GridBagConstraints();
		con.gridx = 6;
		con.gridy = 0;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(gammaButton, con);
		
		//this.pack();
		
	}

	public void handAndShowShuffleSet(DataShuffler shuffle,String[] names) {
		setFiles(shuffle);
		name = shuffle.getName();
		snapple = false;
		for(int i = 0;i < names.length;i++)
		{
			names[i] = names[i].replace("extrap", "");
			names[i] = names[i].replace(".txt", "");
		}
		this.fileNames = names;
	    this.setup(name);
	    this.buttonSetup();
	    this.setup2();
	    this.buttonSetup2();
		
		this.addShuffleRuns();
		this.pack();
		this.setVisible(true);
		//runs.validate();
	    //this.addShuffleRuns();
	}
	
	private void buttonSetup2() {
		flatField2.setPreferredSize(buttonSize);
		flatButton2.setPreferredSize(buttonSize);
		
		gammaField2.setPreferredSize(buttonSize);
		gammaButton2.setPreferredSize(buttonSize);
		
		flatField2.setToolTipText("the flat top end value (set to 0 for deafult)");
		flatField2.setText("0");
		gammaField2.setToolTipText("gamma transformed value (set 1 for deafult)");
		gammaField2.setText("1");
		
		flatButton2.setActionCommand("intervalSelection2");
		gammaButton2.setActionCommand("intervalSelection2");
		
		flatButton2.addActionListener(this);
		gammaButton2.addActionListener(this);
		
		GridBagConstraints con = new GridBagConstraints();
		
		
		
		con = new GridBagConstraints();
		con.gridx = 11;
		con.gridy = 0;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(flatField2, con);
		con = new GridBagConstraints();
		con.gridx = 12;
		con.gridy = 0;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(flatButton2, con);
		
		
		con = new GridBagConstraints();
		con.gridx = 14;
		con.gridy = 0;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(gammaField2, con);
		con = new GridBagConstraints();
		con.gridx = 15;
		con.gridy = 0;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(gammaButton2, con);
		
		//this.pack();
		
	}

	private void setup2() {
		runs2.setActionCommand("runSelection2");
	    interval2.setActionCommand("intervalSelection2");
	    imageType2.setActionCommand("imageSelection2");
	    runs2.addActionListener(this);
	    interval2.addActionListener(this);
	    imageType2.addActionListener(this);
	    commit2.addActionListener(this);
	    
	    runs2.setPreferredSize(buttonSize);
	    interval2.setPreferredSize(buttonSize);
	    imageType2.setPreferredSize(buttonSize);
	    commit2.setPreferredSize(buttonSize);

	    imageType2.addItem("moveGrid");
	    imageType2.addItem("popGrid");
	    imageType2.addItem("turnSectors");
	    imageType2.addItem("bounceSectors");
	    imageType2.addItem("collisionSectors");
	    imageType2.addItem("absoluteSectors");
	    imageType2.addItem("turnSectorsDist");
		imageType2.addItem("Directional_Turns");
		imageType2.addItem("Directional_Constant");
		imageType2.addItem("Relative_Turns");
		for(int i = 0; i < 16;i++)
		{
			imageType2.addItem("Angle_"+i);
		}
	    
		dataScroll2 = new JScrollPane(data2);
		imageScroll2 = new JScrollPane(imageHolder2);
		dataScroll2.setPreferredSize(boxSize);
		imageScroll2.setPreferredSize(boxSize);
		data2.setPreferredSize(new Dimension(2000,8000));
		imageHolder2.setSize(new Dimension(1000,900));
		imageScroll2.addMouseWheelListener(this);

		imageHolder2.setHorizontalAlignment(JLabel.CENTER);
		GridBagConstraints con = new GridBagConstraints();
		con.gridx = 8;
		con.gridy = 0;
		//con.gridwidth = 2;
		//con.gridheight = 1;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(runs2, con);
		con = new GridBagConstraints();
		con.gridx = 9;
		con.gridy = 0;
		//con.gridwidth = 2;
		//con.gridheight = 1;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(interval2, con);
		con = new GridBagConstraints();
		con.gridx = 10;
		con.gridy = 0;
		//con.gridwidth = 2;
		//con.gridheight = 1;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(imageType2, con);
		con = new GridBagConstraints();
		con.gridx = 8;
		con.gridy = 1;
		con.gridwidth = 8;
		con.fill = GridBagConstraints.BOTH;
		//con.fill = con.HORIZONTAL;
		getContentPane().add(imageScroll2, con);
		con = new GridBagConstraints();
		con.gridx = 8;
		con.gridy = 2;
		con.gridwidth = 8;
		con.fill = GridBagConstraints.BOTH;
		//con.fill = con.HORIZONTAL;
		getContentPane().add(dataScroll2, con);
	    
		//this.setVisible(true);
	}

	public void setup(String runName)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension((int)width,(int) height));
	    
	    runs.setActionCommand("runSelection");
	    interval.setActionCommand("intervalSelection");
	    imageType.setActionCommand("imageSelection");
	    commit.setActionCommand("commitBox");
	    runs.addActionListener(this);
	    interval.addActionListener(this);
	    imageType.addActionListener(this);
	    commit.addActionListener(this);
	    
	    runs.setPreferredSize(buttonSize);
	    interval.setPreferredSize(buttonSize);
	    imageType.setPreferredSize(buttonSize);
	    commit.setPreferredSize(buttonSize);

	    imageType.addItem("moveGrid");
	    imageType.addItem("popGrid");
	    imageType.addItem("turnSectors");
	    imageType.addItem("bounceSectors");
	    imageType.addItem("collisionSectors");
	    imageType.addItem("absoluteSectors");
	    imageType.addItem("turnSectorsDist");
	    
		imageType.addItem("Directional Turns");
		imageType.addItem("Directional Constant");
		imageType.addItem("Relative Turns");
		for(int i = 0; i < 16;i++)
		{
			imageType.addItem("Angle "+i);
		}
	    
		dataScroll = new JScrollPane(data);
		imageScroll = new JScrollPane(imageHolder);
		dataScroll.setPreferredSize(boxSize);
		imageScroll.setPreferredSize(boxSize);
		data.setPreferredSize(new Dimension(2000,8000));
		imageHolder.setSize(new Dimension(1000,900));
		imageScroll.addMouseWheelListener(this);

		imageHolder.setHorizontalAlignment(JLabel.CENTER);
	    this.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		//con.gridwidth = 2;
		//con.gridheight = 1;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(runs, con);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 0;
		//con.gridwidth = 2;
		//con.gridheight = 1;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(interval, con);
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 0;
		//con.gridwidth = 2;
		//con.gridheight = 1;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(imageType, con);
		con = new GridBagConstraints();
		con.gridx = 7;
		con.gridy = 0;
		//con.gridwidth = 2;
		//con.gridheight = 1;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(commit, con);
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 1;
		con.gridwidth = 8;
		con.fill = GridBagConstraints.BOTH;
		//con.fill = con.HORIZONTAL;
		getContentPane().add(imageScroll, con);
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 2;
		con.gridwidth = 8;
		con.fill = GridBagConstraints.BOTH;
		//con.fill = con.HORIZONTAL;
		getContentPane().add(dataScroll, con);
	    
		//pack();
		this.setTitle(runName+" data");
	    //setVisible(true);
	}
	
	private void addRuns() {
		for(int i = 0; i < snaps.size()-1; i++)
	    {
	    	runs.addItem(name+" run "+(i+1));
	    }
	    runs.addItem("Metadata ");
		
	}
	
	private void addShuffleRuns() {
		for(int i = 0; i < fileNames.length; i++)
	    {
	    	runs.addItem(fileNames[i]);
	    	runs2.addItem(fileNames[i]);
	    }
	    runs.addItem("Metadata ");
	    runs2.addItem("Metadata ");
	    runSelection();
	    runSelection2();
	    runs.setSelectedIndex(0);
	    runs2.setSelectedIndex(1);
	    interval.setSelectedIndex(0);
	    interval2.setSelectedIndex(1);
		
	}

	public void actionPerformed(ActionEvent arg0) {
		this.cleanEmpty();
		this.sanitiseFields();
		if(this.isVisible())
		{
		if(arg0.getActionCommand().equals("runSelection"))
		{
			this.runSelection();
			this.imageSelect();				
		}else
		{
			if(arg0.getActionCommand().equals("intervalSelection")&& interval.getSelectedItem() != null)
			{
				this.checkIntervalSelect();		
				this.imageSelect();			
			}else
			{
				if(arg0.getActionCommand().equals("commitBox"))
				{
					if(commit.getSelectedIndex() == 0)
					{
						this.offLoad();
					}else
					{
						if(commit.getSelectedIndex() == 1)
						{
							this.offLoadResults();
						}else
						{
							if(commit.getSelectedIndex() == 2)
							{
								this.offLoadAllImages();
							}else
							{
								if(commit.getSelectedIndex() == 3)
								{
									this.offSetLoadImages();
								}else
								{
									if(commit.getSelectedIndex() == 4)
									{
										this.generateTrackImages();
									}
								}
										
									
								
							}
						}
					}
				}else
				{
					if(arg0.getActionCommand().equals("imageSelection")&& interval.getSelectedItem() != null)
					{
						this.imageSelect();						
					}
				}
			}
		}		
		if(arg0.getActionCommand().equals("runSelection2"))
		{
			this.runSelection2();	
			this.imageSelect2();			
		}else
		{
			if(arg0.getActionCommand().equals("intervalSelection2")&& interval2.getSelectedItem() != null)
			{
				this.checkIntervalSelect2();
				this.imageSelect2();					
			}else
			{
				
				if(arg0.getActionCommand().equals("imageSelection2")&& interval2.getSelectedItem() != null)
				{
					this.imageSelect2();					
				}
			}
		}
		}
	}

	private void generateTrackImages() {
		int i = imageType.getSelectedIndex();
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fc.setDialogTitle("Location");
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		if(file.getPath() == null)
		{
			file = new File(System.getProperty("user.dir"));
		}
		String current = files.getSnapPosiFiles().get(runs.getSelectedIndex()).get(interval.getSelectedIndex());
		try {
			TrackOffloader tracker = new TrackOffloader(fileNames[runs.getSelectedIndex()],interval.getSelectedIndex(),new File(current), file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(current);
		
	}

	private void offSetLoadImages() {
		int i = imageType.getSelectedIndex();
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fc.setDialogTitle("Location");
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		if(file.getPath() == null)
		{
			file = new File(System.getProperty("user.dir"));
		}
		this.offloadImageRow(i,file,""+imageType.getItemAt(i));
		
		
		
	}

	private void offloadImageRow(int i, File file, String name) {

		int y =  interval.getItemCount()-1;
		interval.setSelectedIndex(y);
		imageType.setSelectedIndex(i);
		for(int x = 0; x < runs.getItemCount()-1;x++)
		{
			{
				runs.setSelectedIndex(x);
				try {
				    // retrieve image
				    BufferedImage bi = heater.getBuffer();
				    File iterImage = new File(file.getAbsolutePath().replace(".txt", "")+"//"+this.name+"_"+runs.getItemAt(x)+"_"+interval.getItemAt(y)+"_"+name+".png");
				    ImageIO.write(bi, "png", iterImage);
				} catch (IOException e) {}
			}
		}
	}
		
/*private void offloadImageRow(int i, File file, String name) {
		
		for(int x = 0; x < runs.getItemCount()-1;x++)
		{
			for(int y = 0; y < interval.getItemCount();y++)
			{
				runs.setSelectedIndex(x);
				interval.setSelectedIndex(y);
				imageType.setSelectedIndex(i);
				try {
				    // retrieve image
				    BufferedImage bi = heater.getBuffer();
				    File iterImage = new File(file.getAbsolutePath().replace(".txt", "")+"//"+runs.getItemAt(x)+"_"+interval.getItemAt(y)+"_"+name+".png");
				    ImageIO.write(bi, "png", iterImage);
				} catch (IOException e) {}
			}
		}
	}*/

	private void offLoadAllImages() {
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fc.setDialogTitle("Location");
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		if(file.getPath() == null)
		{
			file = new File(System.getProperty("user.dir"));
		}
		for(int i =0; i < imageType.getItemCount();i++)
		{
			this.offloadImageRow(i,file,""+imageType.getItemAt(i));
			
		}
		
	}


	private void imageSelect2() {
		if(imageType2.getSelectedIndex() == 0)
		{
			this.genHeatImage2();			
		}else
		{
			if(imageType2.getSelectedIndex() == 1)
			{
				this.genPopImage2();				
			}else
			{
				if(imageType2.getSelectedIndex() == 2)
					
				{
					//turn
					this.genTurnImage2(60,2,180,6,16,false);
				
				}else
				{
					if(imageType2.getSelectedIndex() == 3)
					
					{
						//bounce
						//this.genTurnImage(34,2,105,6,8);
						this.genTurnImage2(44,2,132,6,8,false);
					}
				else
				{
					if(imageType2.getSelectedIndex() == 4)
					
					{
						//collision
						this.genTurnImage2(28,2,84,6,8,false);
					
					}else
					{
						if(imageType2.getSelectedIndex() == 5)
						
						{
							//abs
							this.genTurnImage2(92,2,276,6,8,true);
						
						}else
						{
							if(imageType2.getSelectedIndex() == 6)
							
							{
								//turn + dist
								this.genTurnImage2(109,2,325,6,16,false);
							
							}else
							{
								if(imageType2.getSelectedIndex() > 6)
								{
									this.genDirectionalImage2(imageType2.getSelectedIndex()- 7);
								}
							}
						}
					}
				}
				}
			}
		}
		
	}

	private void genTurnImage2(int a, int b, int c, int d, int f, boolean kant) {
		if(runs2.getSelectedIndex() < runs2.getItemCount()-1)
		{
			double[] sects = new double[f];
			int pointer = a;
			for(int i = 0; i < sects.length;i++)
			{
				sects[i] = Double.parseDouble(files.fishOutSnap(pointer, runs2.getSelectedIndex(), interval2.getSelectedIndex()).split(";")[1]);
				pointer+=b;
			}
			
			SectorBuilder sec = new SectorBuilder(800, 800, 160, 400, sects, kant);
			imageHolder2.setIcon(new ImageIcon(sec.getSector()));
			
		}else
		{
			
			double[] sects = new double[f];
			int pointer = c;
			for(int i = 0; i < sects.length;i++)
			{
				sects[i] = Double.parseDouble(files.fishOutMetaSnap(pointer, interval2.getSelectedIndex()).split(";")[1]);
				pointer+=d;
			}
			
			SectorBuilder sec = new SectorBuilder(800, 800, 160, 400, sects, kant);
			imageHolder2.setIcon(new ImageIcon(sec.getSector()));
		}
		
	}

	private void genPopImage2() {
		if(runs2.getSelectedIndex() < runs2.getItemCount()-1)
		{
			heater.drawPureMap(files.getHeatGridFast(new File(files.getSnapPopFiles().get(runs2.getSelectedIndex()).get(interval2.getSelectedIndex())) ), Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			
		}else
		{
			heater.drawPureMap(files.getMetaPopGrid(interval2.getSelectedIndex()), Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
		}
		imageHolder2.setIcon(new ImageIcon(heater.getBuffer()));
		
	}

	private void genDirectionalImage2(int target) 
	{		
		if(runs2.getSelectedIndex() < runs2.getItemCount()-1)
		{
			if(target == 0)
			{
				heater.drawDirectionMap(files.getDirectionalGridFast(runs2.getSelectedIndex(), interval2.getSelectedIndex(), "Directional"), Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			}else
			{
			if(target == 1)
			{
				heater.drawDirectionMap(files.getDirectionalGridFast(runs2.getSelectedIndex(), interval2.getSelectedIndex(), "DirectionalConstant"), Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				
			}else
			{
			if(target == 2)
			{
				heater.drawDirectionMap(files.getDirectionalGridFast(runs2.getSelectedIndex(), interval2.getSelectedIndex(), "Relative"), Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				
			}else
			{
			
				heater.drawPureMap(files.getDirectionalGridFast(runs2.getSelectedIndex(), interval2.getSelectedIndex(), "Angle"),target-3, Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				//heater.drawPureMap(files.getHeatGrid(runs2.getSelectedIndex(), interval2.getSelectedIndex()), Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				
			}}}
		}else
		{
			if(target == 0)
			{
				heater.drawDirectionMap(files.getMetaDirectionalGrid( interval2.getSelectedIndex(), "Directional"), Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			}else
			{
			if(target == 1)
			{
				heater.drawDirectionMap(files.getMetaDirectionalGrid( interval2.getSelectedIndex(), "DirectionalConstant"), Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				
			}else
			{
			if(target == 2)
			{
				heater.drawDirectionMap(files.getMetaDirectionalGrid( interval2.getSelectedIndex(), "Relative"), Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				
			}else
			{
			
				heater.drawPureMap(files.getMetaDirectionalGrid( interval2.getSelectedIndex(), "Angle"),target-3, Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			}}}
		}

		imageHolder2.setIcon(new ImageIcon(heater.getBuffer()));
		//imageHolder2.repaint();
		
	}
	
	private void genHeatImage2() {
		if(runs2.getSelectedIndex() < runs2.getItemCount()-1)
		{
			heater.drawPureMap(files.getHeatGridFast(new File(files.getSnapHeatFiles().get(runs2.getSelectedIndex()).get(interval2.getSelectedIndex())) ), Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			
		}else
		{
			heater.drawPureMap(files.getMetaHeatGrid(interval2.getSelectedIndex()), Integer.parseInt(flatField2.getText().replace(",", "")), Double.parseDouble(gammaField2.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
		}
		imageHolder2.setIcon(new ImageIcon(heater.getBuffer()));
		
	}

	private void checkIntervalSelect2() {
		System.out.println("recalling "+runs2.getSelectedIndex()+" "+interval2.getSelectedIndex()+" "+files.getName() );
		if(runs2.getSelectedIndex() == runs2.getItemCount()-1 && runs2.getSelectedIndex() !=0)
		{
			//meta table top table and settings
			data2.fillOutMetaSnapShuffle(files.getMetaTableWithoutSettings(), files.getTopTableWithSettings(interval2.getSelectedIndex()));
		}else
		{
			
			//norm table
			data2.fillOutSnapShuffle(files.getSnapTableWithSettings(runs2.getSelectedIndex()));
		}
	
		
	}

	private void runSelection2() {
		int cho = interval2.getSelectedIndex();
		interval2.removeAllItems();
		for(int i = 0; i < files.getSnapFiles().get(0).size(); i++)
	    {
	    	interval2.addItem(name+" interval "+(i+1));
	    }
		interval2.setSelectedIndex(cho);
		
	}

	private void imageSelect() {
		if(snapple)
		{
			if(imageType.getSelectedIndex() == 0)
			{
				imageHolder.setIcon(new ImageIcon(snaps.get(runs.getSelectedIndex()).get(interval.getSelectedIndex()).genHeat().getBuffer()));
			}else
			{
				if(imageType.getSelectedIndex() == 1)
				{
					SnapShotObfust snp = snaps.get(runs.getSelectedIndex()).get(interval.getSelectedIndex());
					imageHolder.setIcon(new ImageIcon(snp.generateHeatMap(snp.getPopSectors()).getBuffer()));
				}
			}
		}else
		{
			if(imageType.getSelectedIndex() == 0)
			{
				this.genHeatImage();
			}else
			{
				if(imageType.getSelectedIndex() == 1)
				{
					this.genPopImage();
				}else
				{
					if(imageType.getSelectedIndex() == 2)
						
					{
						//turn
						this.genTurnImage(60,2,180,6,16,false);
					
					}else
					{
						if(imageType.getSelectedIndex() == 3)
						
						{
							//bounce
							//this.genTurnImage(34,2,105,6,8);
							this.genTurnImage(44,2,132,6,8,false);
						}
					else
					{
						if(imageType.getSelectedIndex() == 4)
						
						{
							//collision
							this.genTurnImage(28,2,84,6,8,false);
						
						}else
						{
							if(imageType.getSelectedIndex() == 5)
							
							{
								//abs
								this.genTurnImage(92,2,276,6,8,true);
							
							}else
							{
								if(imageType.getSelectedIndex() == 6)
								
								{
									//turn+dist
									this.genTurnImage(109,2,325,6,16,false);
								
								}else
								{
									if(imageType.getSelectedIndex() > 6)
										
									{
										this.genDirectionalImage(imageType.getSelectedIndex()- 7);
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

	private void genDirectionalImage(int target) 
	{		
		if(runs.getSelectedIndex() < runs.getItemCount()-1)
		{
			if(target == 0)
			{
				heater.drawDirectionMap(files.getDirectionalGridFast(runs.getSelectedIndex(), interval.getSelectedIndex(), "Directional"), Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			}else
			{
			if(target == 1)
			{
				heater.drawDirectionMap(files.getDirectionalGridFast(runs.getSelectedIndex(), interval.getSelectedIndex(), "DirectionalConstant"), Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				
			}else
			{
			if(target == 2)
			{
				heater.drawDirectionMap(files.getDirectionalGridFast(runs.getSelectedIndex(), interval.getSelectedIndex(), "Relative"), Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				
			}else
			{

				System.out.println("target "+target);
				heater.drawPureMap(files.getDirectionalGridFast(runs.getSelectedIndex(), interval.getSelectedIndex(), "Angle"),target-3, Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			}}}
		}else
		{
			if(target == 0)
			{
				heater.drawDirectionMap(files.getMetaDirectionalGrid( interval.getSelectedIndex(), "Directional"), Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			}else
			{
			if(target == 1)
			{
				heater.drawDirectionMap(files.getMetaDirectionalGrid( interval.getSelectedIndex(), "DirectionalConstant"), Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				
			}else
			{
			if(target == 2)
			{
				heater.drawDirectionMap(files.getMetaDirectionalGrid( interval.getSelectedIndex(), "Relative"), Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				
			}else
			{
				System.out.println("target "+target);
				heater.drawPureMap(files.getMetaDirectionalGrid( interval.getSelectedIndex(), "Angle"),target-3, Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			}}}
		}
		
		imageHolder.setIcon(new ImageIcon(heater.getBuffer()));
		
	}

		//a = start b = dist between c = meta start = d = meta inbetween f = num sides
	private void genTurnImage(int a, int b, int c, int d, int f, boolean kant) {
		if(runs.getSelectedIndex() < runs.getItemCount()-1)
		{
			double[] sects = new double[f];
			int pointer = a;
			for(int i = 0; i < sects.length;i++)
			{
				sects[i] = Double.parseDouble(files.fishOutSnap(pointer, runs.getSelectedIndex(), interval.getSelectedIndex()).split(";")[1]);
				pointer+=b;
			}
			
			SectorBuilder sec = new SectorBuilder(800, 800, 160, 400, sects, kant);
			imageHolder.setIcon(new ImageIcon(sec.getSector()));
			
		}else
		{
			
			double[] sects = new double[f];
			int pointer = c;
			for(int i = 0; i < sects.length;i++)
			{
				sects[i] = Double.parseDouble(files.fishOutMetaSnap(pointer, interval.getSelectedIndex()).split(";")[1]);
				pointer+=d;
			}
			
			SectorBuilder sec = new SectorBuilder(800, 800, 160, 400, sects,kant);
			imageHolder.setIcon(new ImageIcon(sec.getSector()));
		}
		
		
	}
	
	

	private void genPopImage() {
		
		if(runs.getSelectedIndex() < runs.getItemCount()-1)
		{
			heater.drawPureMap(files.getHeatGridFast(new File(files.getSnapPopFiles().get(runs.getSelectedIndex()).get(interval.getSelectedIndex())) ), Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			
		}else
		{
			heater.drawPureMap(files.getMetaPopGrid(interval.getSelectedIndex()), Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
		}
		imageHolder.setIcon(new ImageIcon(heater.getBuffer()));
		
		
	}

	private void genHeatImage() {
		if(runs.getSelectedIndex() < runs.getItemCount()-1)
		{
			heater.drawPureMap(files.getHeatGridFast(new File(files.getSnapHeatFiles().get(runs.getSelectedIndex()).get(interval.getSelectedIndex()))), Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			
		}else
		{
			heater.drawPureMap(files.getMetaHeatGrid(interval.getSelectedIndex()), Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
		}
		imageHolder.setIcon(new ImageIcon(heater.getBuffer()));
		
	}

	private void runSelection() {
		int cho = interval.getSelectedIndex();
		if(snapple)
		{
			interval.removeAllItems();
			for(int i = 0; i < snaps.get(runs.getSelectedIndex()).size(); i++)
		    {
		    	interval.addItem(name+" interval "+(i+1));
		    }
		}else
		{
			interval.removeAllItems();
			for(int i = 0; i < files.getSnapFiles().get(0).size(); i++)
		    {
		    	interval.addItem(name+" interval "+(i+1));
		    }
		}
		interval.setSelectedIndex(cho);
	}

	private void offLoad() {
		if(snapple)
		{
			StatOffLoader offload = new StatOffLoader();
			offload.generateFullFileSystemFromObfust(snaps,tables, name,Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")));
		}else
		{
			StatOffLoader offload = new StatOffLoader();
			offload.generateFullFileSystemFromShuffle(files,Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")));
		}
		
	}
	
	private void zsomborfy()
	{
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fc.setDialogTitle("Location");
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		if(file.getPath() == null)
		{
			file = new File(System.getProperty("user.dir"));
		}
		
		for(int x = 0; x < runs.getItemCount()-1;x++)
		{
			for(int y = 0; y < interval.getItemCount();y++)
			{
				heater.drawPureMap(files.getHeatGridFast(new File(files.getSnapHeatFiles().get(x).get(y))), Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				try {
				    // retrieve image
				    BufferedImage bi = heater.getBuffer();
				    Image img = bi.getScaledInstance(265, 265, Image.SCALE_REPLICATE);
				    BufferedImage scale = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
				    Graphics2D bGr = scale.createGraphics();
				    bGr.drawImage(img, 0, 0, null);
				    bGr.dispose();
				    File iterImage = new File(file.getAbsolutePath().replace(".txt", "")+"//"+runs.getItemAt(x)+"_"+interval.getItemAt(y)+".tiff");
				    ImageIO.write(scale, "TIFF", iterImage);
				    bi = heater.getBuffer();
				    iterImage = new File(file.getAbsolutePath().replace(".txt", "")+"//"+runs.getItemAt(x)+"FullSize"+interval.getItemAt(y)+".tiff");
				    ImageIO.write(bi, "TIFF", iterImage);
				   // File iterImage2 = new File(file.getAbsolutePath()+"//"+runs.getItemAt(x)+"_"+interval.getItemAt(y)+".png");
				    //ImageIO.write(scale, "png", iterImage2);
				    /*bi = heater.getBuffer();
				    img = bi.getScaledInstance(265, 265, Image.SCALE_REPLICATE);
				    scale = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
				    bGr = scale.createGraphics();
				    bGr.drawImage(img, 0, 0, null);
				    bGr.dispose();
				    iterImage = new File(file.getAbsolutePath()+"//"+runs.getItemAt(x)+"replicate"+interval.getItemAt(y)+".tiff");
				    ImageIO.write(scale, "TIFF", iterImage);
				    bi = heater.getBuffer();
				    img = bi.getScaledInstance(265, 265, Image.SCALE_AREA_AVERAGING);
				    scale = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
				    bGr = scale.createGraphics();
				    bGr.drawImage(img, 0, 0, null);
				    bGr.dispose();
				    iterImage = new File(file.getAbsolutePath()+"//"+runs.getItemAt(x)+"averaging"+interval.getItemAt(y)+".tiff");
				    ImageIO.write(scale, "TIFF", iterImage);
				    bi = heater.getBuffer();
				    img = bi.getScaledInstance(265, 265, Image.SCALE_DEFAULT);
				    scale = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
				    bGr = scale.createGraphics();
				    bGr.drawImage(img, 0, 0, null);
				    bGr.dispose();
				    iterImage = new File(file.getAbsolutePath()+"//"+runs.getItemAt(x)+"defult"+interval.getItemAt(y)+".tiff");
				    ImageIO.write(scale, "TIFF", iterImage);
				   // File iterImage2 = new File(file.getAbsolutePath()+"//"+runs.getItemAt(x)+"_"+interval.getItemAt(y)+".png");
				    //ImageIO.write(scale, "png", iterImage2);*/
				} catch (IOException e) {}
			}
		}
	}
	
	private void offLoadResults() {
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fc.setDialogTitle("Location");
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		if(file.getPath() == null)
		{
			file = new File(System.getProperty("user.dir"));
		}
		File overAll = files.getTopFolder();
//		try {
//			FileUtils.copyDirectory(overAll, file);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		File names = new File(file.getAbsolutePath()+"\\names.txt");
		try {
			PrintWriter writer = new PrintWriter(names);
			
			for(int m = 0 ; m < fileNames.length;m++)
			{
				writer.println(fileNames[m]);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkIntervalSelect() {
		if(snapple)
		{
			System.out.println("recalling "+runs.getSelectedIndex()+" "+interval.getSelectedIndex() );
			
			if(runs.getSelectedIndex() == runs.getItemCount()-1 && runs.getSelectedIndex() !=0)
			{
				data.fillOut(snaps.get(runs.getSelectedIndex()).get(interval.getSelectedIndex()),tables.get(runs.getSelectedIndex()), true, tables.get(runs.getSelectedIndex()+interval.getSelectedIndex()+1));
			}else
			{
				data.fillOut(snaps.get(runs.getSelectedIndex()).get(interval.getSelectedIndex()),tables.get(runs.getSelectedIndex()), false, null );
				
			}
			if(runs.getSelectedIndex() == runs.getItemCount()-1 && runs.getSelectedIndex() !=0)
			{
				data.fillOut(snaps.get(runs.getSelectedIndex()).get(interval.getSelectedIndex()),tables.get(runs.getSelectedIndex()), true, tables.get(runs.getSelectedIndex()+interval.getSelectedIndex()+1));
			}else
			{
				data.fillOut(snaps.get(runs.getSelectedIndex()).get(interval.getSelectedIndex()),tables.get(runs.getSelectedIndex()), false, null );
				
			}
		}else
		{
			System.out.println("recalling "+runs.getSelectedIndex()+" "+interval.getSelectedIndex()+" "+files.getName() );
			
			if(runs.getSelectedIndex() == runs.getItemCount()-1 && runs.getSelectedIndex() !=0)
			{
				//meta table top table and settings
				data.fillOutMetaSnapShuffle(files.getMetaTableWithoutSettings(), files.getTopTableWithSettings(interval.getSelectedIndex()));
			}else
			{
				
				//norm table
				data.fillOutSnapShuffle(files.getSnapTableWithSettings(runs.getSelectedIndex()));
			}
		}
		
	}

	private void sanitiseFields() {
		flatField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinHeatDeafult(),SingletonSanitisationFields.getMaxHeatDeafult(), Integer.parseInt(flatField.getText().replace(",", ""))));
		gammaField.setText(""+SingletonSanitisationFields.sanitiseDouble(SingletonSanitisationFields.getMinGammaDeafult(),SingletonSanitisationFields.getMaxGammaDeafult(), Double.parseDouble(gammaField.getText().replace(",", ""))));
		
		
	}

	private void cleanEmpty() {

		if(flatField.getText().replace(",", "").isEmpty()) { flatField.setText("0");}
		if(gammaField.getText().replace(",", "").isEmpty()) { gammaField.setText("0");}
		
	}

	public DataShuffler getFiles() {
		return files;
	}

	public void setFiles(DataShuffler files) {
		this.files = files;
	}

	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int imageScrollIncrement = 10;
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			Dimension heatSize = heater.getDimensions();
			if(e.getWheelRotation() < 0)
			{
				heatSize = new Dimension(heatSize.width+(heatSize.width/100*imageScrollIncrement),heatSize.width+(heatSize.width/100*imageScrollIncrement));
				
				if(heatSize.getWidth()>4000)
				{
					heatSize.setSize(4000, heatSize.getHeight());
				}else
				{

					imageScroll.getHorizontalScrollBar().setValue(imageScroll.getHorizontalScrollBar().getValue()+(imageScroll.getHorizontalScrollBar().getValue()/100*imageScrollIncrement));
				}
				if(heatSize.getHeight()>3000)
				{
					heatSize.setSize(heatSize.getWidth(),3000);
				}else
				{
					imageScroll.getVerticalScrollBar().setValue(imageScroll.getVerticalScrollBar().getValue()+(imageScroll.getVerticalScrollBar().getValue()/100*imageScrollIncrement));
					
				}
				heater.setDimensions(heatSize);
				//System.out.println("SCROLLING A "+heatSize.toString());
				imageSelect();
				imageSelect2();
				//img.setSize(new Dimension(img.getPreferredSize().width-imageScrollIncrement,img.getPreferredSize().height-imageScrollIncrement));
			}else
			{
				heatSize = new Dimension(heatSize.width-(heatSize.width/100*imageScrollIncrement),heatSize.width-(heatSize.width/100*imageScrollIncrement));
				if(heatSize.getWidth() <=100 ||heatSize.getHeight() <=100)
				{
					heatSize.setSize(101, 101);
				}
				heater.setDimensions(heatSize);
				//System.out.println("SCROLLING B "+heatSize.toString());
				imageSelect();
				imageSelect2();
				imageScroll.getVerticalScrollBar().setValue(imageScroll.getVerticalScrollBar().getValue()-imageScrollIncrement/2);
				imageScroll.getHorizontalScrollBar().setValue(imageScroll.getHorizontalScrollBar().getValue()-imageScrollIncrement/2);
				//System.out.println("wheel down "+heatSize);
				//img.setSize(new Dimension(img.getPreferredSize().width+imageScrollIncrement,img.getPreferredSize().height+imageScrollIncrement));
				
			}
		}
		
	}
	

}
