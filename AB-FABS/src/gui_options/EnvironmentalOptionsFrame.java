package gui_options;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import cycles.Cycle;
import cycles.LiveCycle;

import javax.swing.JFormattedTextField;

import singleton_holders.SingletonHolder;
import singleton_holders.SingletonSanitisationFields;

public class EnvironmentalOptionsFrame extends JFrame implements ActionListener {

	
	JCheckBox attractiveBezier = new JCheckBox("Bezier");
	JCheckBox bounceBez = new JCheckBox("Bounce");
	JCheckBox boundary = new JCheckBox("Boundary");
	
	
	JFormattedTextField fileName = new JFormattedTextField();
	JFormattedTextField iterations = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField gridSize = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	
	
	
	JLabel bezMinDistanceLabel = new JLabel("Minimum gap");
	JFormattedTextField bezMinDistanceField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JLabel bezMaxDistanceLabel = new JLabel("Maximum gap");
	JFormattedTextField bezMaxDistanceField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JLabel bezChanceLabel = new JLabel("Attatchment");
	JFormattedTextField bezChanceField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JLabel bezNumLabel = new JLabel("Number");
	JFormattedTextField bezNumField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JLabel bezSizeLabel = new JLabel("Size");
	JFormattedTextField bezSizeField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JLabel bezVarLabel = new JLabel("Point Varience");
	JFormattedTextField bezVarField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JRadioButton randomBez = new JRadioButton("random spread");
	JRadioButton evenBez = new JRadioButton("evenly spread");
	//varience non 0 = yes
	JLabel bezSVarLabel = new JLabel("Strength Varience");
	JFormattedTextField bezSVarField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	//vairience type random or normal
	JCheckBox distBez = new JCheckBox("Normal");
	//redline value 
	JLabel bezRedLabel = new JLabel("Redline");
	JFormattedTextField bezRedField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	//greenline value
	JLabel bezGreenLabel = new JLabel("GreenLine");
	JFormattedTextField bezGreenField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	

	JLabel boundSizeLabel = new JLabel("side/radius");
	JFormattedTextField boundSizeField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JRadioButton square = new JRadioButton("square");
	JRadioButton circular = new JRadioButton("circle");
	
	ButtonGroup squareCircle = new ButtonGroup();
	
	ButtonGroup bez = new ButtonGroup();
	
	JButton button = new JButton("apply");
	
	JLabel dist = new JLabel("Distribution");
	JRadioButton random = new JRadioButton("random");
	JRadioButton even = new JRadioButton("even");
	JRadioButton norm = new JRadioButton("norm");

	JCheckBox attract = new JCheckBox("Attractive Zones");
	JLabel attractSizeLabel = new JLabel("size"); 
	JLabel attractStrengthLabel = new JLabel("strength"); 
	JLabel attractFalloffLabel = new JLabel("fall off");
	JLabel attractZonesLabel = new JLabel("number"); 
	JLabel attractSizeVarLabel = new JLabel("size varience"); 
	JLabel attractStrengthVarLabel = new JLabel("strength varience"); 
	
	JFormattedTextField attractSize = new JFormattedTextField(NumberFormat.INTEGER_FIELD); 
	JFormattedTextField attractStrength = new JFormattedTextField(NumberFormat.INTEGER_FIELD); 
	JFormattedTextField attractFalloff = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField attractZones = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField attractSizeVar = new JFormattedTextField(NumberFormat.INTEGER_FIELD); 
	JFormattedTextField attractStrengthVar = new JFormattedTextField(NumberFormat.INTEGER_FIELD); 
	
	JLabel attractEye = new JLabel("eye size");
	JFormattedTextField eye = new JFormattedTextField(NumberFormat.INTEGER_FIELD); 
	

	JCheckBox deflect = new JCheckBox("Deflective Zones");
	JLabel deflectSizeLabel = new JLabel("size"); 
	JLabel deflectStrengthLabel = new JLabel("strength"); 
	JLabel deflectFalloffLabel = new JLabel("fall off");
	JLabel deflectZonesLabel = new JLabel("number"); 
	JLabel deflectSizeVarLabel = new JLabel("size varience"); 
	JLabel deflectStrengthVarLabel = new JLabel("strength varience"); 
	
	JFormattedTextField deflectSize = new JFormattedTextField(NumberFormat.INTEGER_FIELD); 
	JFormattedTextField deflectStrength = new JFormattedTextField(NumberFormat.INTEGER_FIELD); 
	JFormattedTextField deflectFalloff = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField deflectZones = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField deflectSizeVar = new JFormattedTextField(NumberFormat.INTEGER_FIELD); 
	JFormattedTextField deflectStrengthVar = new JFormattedTextField(NumberFormat.INTEGER_FIELD); 
	
	ButtonGroup distr = new ButtonGroup();
	private Cycle cyc;
	
	public EnvironmentalOptionsFrame(Cycle cyc)
	{
		this.cyc = cyc;
		JLabel lFN = new JLabel("file name");
		JLabel lI = new JLabel("iterations ");
		JLabel lS = new JLabel("size of the grid");
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setPreferredSize(new Dimension(1200, 300));
		Dimension buttonSize = new Dimension(100,20);
		Dimension fieldSize = new Dimension(100,20);
		Dimension textFieldSize = new Dimension(120,20);
		button.setPreferredSize(buttonSize);
		this.setLayout(new GridBagLayout());
		//textFieldSize = fieldSize;
		
		lFN.setPreferredSize(textFieldSize);
		lI.setPreferredSize(textFieldSize);
		lS.setPreferredSize(textFieldSize);
		
		attractiveBezier.setPreferredSize(textFieldSize);
		bounceBez.setPreferredSize(textFieldSize);
		boundary.setPreferredSize(textFieldSize);
		attract.setPreferredSize(textFieldSize);
		deflect.setPreferredSize(textFieldSize);
		
		fileName.setPreferredSize(fieldSize);
		iterations.setPreferredSize(fieldSize);
		gridSize.setPreferredSize(fieldSize);
		
		bezMinDistanceLabel.setPreferredSize(fieldSize);
		bezMinDistanceField.setPreferredSize(textFieldSize);
		bezMaxDistanceLabel.setPreferredSize(fieldSize);
		bezMaxDistanceField.setPreferredSize(textFieldSize);
		bezChanceLabel.setPreferredSize(fieldSize);
		bezChanceField.setPreferredSize(textFieldSize);
		bezNumLabel.setPreferredSize(fieldSize);
		bezNumField.setPreferredSize(textFieldSize);
		bezSizeLabel.setPreferredSize(fieldSize);
		bezSizeField.setPreferredSize(textFieldSize);
		bezVarLabel.setPreferredSize(fieldSize);
		bezVarField.setPreferredSize(textFieldSize);
		randomBez.setPreferredSize(textFieldSize);
		evenBez.setPreferredSize(textFieldSize);
		bezSVarLabel.setPreferredSize(fieldSize);
		bezSVarField.setPreferredSize(textFieldSize);
		distBez.setPreferredSize(fieldSize);
		bezRedLabel.setPreferredSize(fieldSize);
		bezRedField.setPreferredSize(textFieldSize);
		bezGreenLabel.setPreferredSize(fieldSize);
		bezGreenField.setPreferredSize(textFieldSize);
		
		attractSizeLabel.setPreferredSize(textFieldSize); 
		attractStrengthLabel.setPreferredSize(textFieldSize); 
		attractFalloffLabel.setPreferredSize(textFieldSize);
		attractZonesLabel.setPreferredSize(textFieldSize);
		attractSizeVarLabel.setPreferredSize(textFieldSize); 
		attractStrengthVarLabel.setPreferredSize(textFieldSize); 
		
		attractSize.setPreferredSize(fieldSize); 
		attractStrength.setPreferredSize(fieldSize);
		attractFalloff.setPreferredSize(fieldSize);
		attractZones.setPreferredSize(fieldSize);
		attractSizeVar.setPreferredSize(fieldSize); 
		attractStrengthVar.setPreferredSize(fieldSize); 
		
		attractEye.setPreferredSize(textFieldSize);
		eye.setPreferredSize(fieldSize);
		
		deflectSizeLabel.setPreferredSize(textFieldSize); 
		deflectStrengthLabel.setPreferredSize(textFieldSize);
		deflectFalloffLabel.setPreferredSize(textFieldSize);
		deflectZonesLabel.setPreferredSize(textFieldSize);
		deflectSizeVarLabel.setPreferredSize(textFieldSize);
		deflectStrengthVarLabel.setPreferredSize(textFieldSize); 
		
		deflectSize.setText(""+SingletonHolder.getDeflectSize());
		deflectStrength.setText(""+SingletonHolder.getDeflectStrength());
		deflectFalloff.setText(""+SingletonHolder.getDeflectFalloff());
		deflectZones.setText(""+SingletonHolder.getDeflectZones());
		deflectSizeVar.setText(""+SingletonHolder.getDeflectSizeVar()) ;
		deflectStrengthVar.setText(""+SingletonHolder.getDeflectStrengthVar()); 

		attractSize.setText(""+SingletonHolder.getAttractSize());
		attractStrength.setText(""+SingletonHolder.getAttractStrength());
		attractFalloff.setText(""+SingletonHolder.getAttractFalloff());
		attractZones.setText(""+SingletonHolder.getAttractZones());
		attractSizeVar.setText(""+SingletonHolder.getAttractSizeVar()) ;
		attractStrengthVar.setText(""+SingletonHolder.getAttractStrengthVar()); 
		eye.setText(""+SingletonHolder.getAttractEye());
		
		
		deflectSize.setPreferredSize(fieldSize);
		deflectStrength.setPreferredSize(fieldSize);
		deflectFalloff.setPreferredSize(fieldSize);
		deflectZones.setPreferredSize(fieldSize);
		deflectSizeVar.setPreferredSize(fieldSize); 
		deflectStrengthVar.setPreferredSize(fieldSize); 
		
		dist.setPreferredSize(fieldSize);
		random.setPreferredSize(fieldSize);
		even.setPreferredSize(fieldSize);
		norm.setPreferredSize(fieldSize);
		random.setActionCommand("random");
		even.setActionCommand("even");
		norm.setActionCommand("norm");
		distr.add(random);
		distr.add(even);
		distr.add(norm);
		random.setSelected(true);
		
		bezMinDistanceField.setText(""+SingletonHolder.getBezMinDeafult());
		bezMaxDistanceField.setText(""+SingletonHolder.getBezMaxDeafult());
		bezChanceField.setText(""+SingletonHolder.getBezChanceDeafult());
		bezNumField.setText(""+SingletonHolder.getBezNumDeafult());
		bezSizeField.setText(""+SingletonHolder.getBezSizeDeafult());
		bezVarField.setText(""+SingletonHolder.getBezVarDeafult());
		bezMinDistanceField.setToolTipText("the minimum distance between central points");
		bezMaxDistanceField.setToolTipText("the maximum distance between central points");
		bezChanceField.setToolTipText("the chance of cells remaining within the curve");
		bezNumField.setToolTipText("the number of curves");
		bezSizeField.setToolTipText("the size of curves in units of cells");
		bezVarField.setToolTipText("the varience of the central points for the curve");
		bezSVarLabel.setToolTipText("the varient strength of each point along a curve 0 = no varience");
		bezSVarField.setText(""+0);
		distBez.setToolTipText("the type of varience distribution, ticked = normal, unchecked = random");
		bezRedLabel.setToolTipText("when line strength feedback is on points below this strength will be highlighted");
		bezRedField.setText(""+0);
		bezGreenLabel.setToolTipText("when line strength feedback is on points above this strength will be highlighted");
		bezGreenField.setText(""+0);
		
		randomBez.setSelected(true);
		randomBez.setActionCommand("random");
		evenBez.setActionCommand("even");
		bez.add(randomBez);
		bez.add(evenBez);
		
		boundSizeLabel.setPreferredSize(fieldSize);
		boundSizeField.setPreferredSize(textFieldSize);
		square.setPreferredSize(textFieldSize);
		circular.setPreferredSize(textFieldSize);
		

		boundSizeField.setText(""+SingletonHolder.getBOUNDRYSIZE_DEAFULT());
		boundSizeField.setToolTipText("half the length of a side or the radius of a circle");
		square.setActionCommand("square");
		circular.setActionCommand("circular");
		circular.setSelected(true);
		
		
		
		squareCircle.add(square);
		squareCircle.add(circular);
		

		iterations.setText(""+SingletonHolder.getITERATIONS_DEAFULT());
		iterations.setToolTipText("iterations  min:"+SingletonSanitisationFields.getMinITERATIONS_DEAFULT()+" max:"+SingletonSanitisationFields.getMaxITERATIONS_DEAFULT());
		gridSize.setText(""+SingletonHolder.getSIZE_DEAFULT());
		gridSize.setToolTipText("size of the grid  min:"+SingletonSanitisationFields.getMinGridSizeDeafult()+" max:"+SingletonSanitisationFields.getMaxGridSizeDeafult());
		fileName.setText("randomTraj");
		fileName.setToolTipText("dont worry it  currently doesnt do anything, lookes wierd with just a space here though");
		
		GridBagConstraints con = new GridBagConstraints();
		
		//iterations
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		//con.fill = GridBagConstraints.BOTH;
		getContentPane().add(lI, con);
		con.gridx++;
		//con.fill = GridBagConstraints.BOTH;
		getContentPane().add(iterations, con);
		con.gridx++;
		//grid size
		//con.fill = GridBagConstraints.BOTH;
		getContentPane().add(lS, con);
		con.gridx++;
		//con.fill = GridBagConstraints.BOTH;
		getContentPane().add(gridSize, con);
		con.gridx++;
		//con.fill = GridBagConstraints.BOTH;
		getContentPane().add(lFN, con);
		con.gridx++;
		//con.fill = GridBagConstraints.BOTH;
		getContentPane().add(fileName, con);
		con.gridx = 0;
		con.gridy++;
		getContentPane().add(attractiveBezier, con);
		con.gridx++;
		getContentPane().add(bezMinDistanceLabel, con);
		con.gridx++;
		getContentPane().add(bezMinDistanceField, con);
		con.gridx++;
		getContentPane().add(bezMaxDistanceLabel, con);
		con.gridx++;
		getContentPane().add(bezMaxDistanceField, con);
		con.gridx++;
		getContentPane().add(bezChanceLabel, con);
		con.gridx++;
		getContentPane().add(bezChanceField, con);
		con.gridx++;
		getContentPane().add(bounceBez, con);
		con.gridx = 1;
		con.gridy++;
		getContentPane().add(bezNumLabel, con);
		con.gridx++;
		getContentPane().add(bezNumField, con);
		con.gridx++;
		getContentPane().add(bezSizeLabel, con);
		con.gridx++;
		getContentPane().add(bezSizeField, con);
		con.gridx++;
		getContentPane().add(bezVarLabel, con);
		con.gridx++;
		getContentPane().add(bezVarField, con);
		con.gridx++;
		getContentPane().add(randomBez, con);
		con.gridx++;
		getContentPane().add(evenBez, con);
		con.gridy++;
		con.gridx = 1;
		getContentPane().add(bezSVarLabel, con);
		con.gridx++;
		getContentPane().add(bezSVarField, con);
		con.gridx++;
		getContentPane().add(distBez, con);
		con.gridx++;
		getContentPane().add(bezRedLabel, con);
		con.gridx++;
		getContentPane().add(bezRedField, con);
		con.gridx++;
		getContentPane().add(bezGreenLabel, con);
		con.gridx++;
		getContentPane().add(bezGreenField, con);
		con.gridx = 0;
		con.gridy++;
		getContentPane().add(boundary, con);
		con.gridx++;
		getContentPane().add(boundSizeLabel, con);
		con.gridx++;
		getContentPane().add(boundSizeField, con);
		con.gridx++;
		getContentPane().add(square, con);
		con.gridx++;
		getContentPane().add(circular, con);
		con.gridx++;
		con.gridx = 0;
		con.gridy++;
		getContentPane().add(dist, con);
		con.gridx++;
		getContentPane().add(random, con);
		con.gridx++;
		getContentPane().add(even, con);
		con.gridx++;
		getContentPane().add(norm, con);
		con.gridx = 0;
		con.gridy++;
		getContentPane().add(attract,con);
		con.gridx++;
		con.gridx++;
		getContentPane().add(attractSizeLabel, con); 
		con.gridx++;
		getContentPane().add(attractSize, con); 
		con.gridx++;
		getContentPane().add(attractStrengthLabel, con); 
		con.gridx++;
		getContentPane().add(attractStrength, con);
		con.gridx++;
		getContentPane().add(attractFalloffLabel, con);
		con.gridx++;
		getContentPane().add(attractFalloff, con);
		con.gridx++;
		getContentPane().add(attractEye, con);
		con.gridx++;
		getContentPane().add(eye, con);
		con.gridx++;
		con.gridx =2;
		con.gridy++;
		getContentPane().add(attractZonesLabel, con);
		con.gridx++;
		getContentPane().add(attractZones, con);
		con.gridx++;
		getContentPane().add(attractSizeVarLabel, con); 
		con.gridx++;
		getContentPane().add(attractSizeVar, con); 
		con.gridx++;
		getContentPane().add(attractStrengthVarLabel, con); 
		con.gridx++;
		getContentPane().add(attractStrengthVar, con); 
		con.gridx++;
		con.gridx = 0;
		con.gridy++;
		getContentPane().add(deflect,con);
		con.gridx++;
		con.gridx++;
		getContentPane().add(deflectSizeLabel, con); 
		con.gridx++;
		getContentPane().add(deflectSize, con);
		con.gridx++;
		getContentPane().add(deflectStrengthLabel, con);
		con.gridx++;
		getContentPane().add(deflectStrength, con);
		con.gridx++;
		getContentPane().add(deflectFalloffLabel, con);
		con.gridx++;
		getContentPane().add(deflectFalloff, con);
		con.gridx++;

		con.gridx =2;
		con.gridy++;
		getContentPane().add(deflectZonesLabel, con);
		con.gridx++;
		getContentPane().add(deflectZones, con);
		con.gridx++;
		getContentPane().add(deflectSizeVarLabel, con);
		con.gridx++;
		getContentPane().add(deflectSizeVar, con); 
		con.gridx++;
		getContentPane().add(deflectStrengthVarLabel, con); 
		con.gridx++;
		getContentPane().add(deflectStrengthVar, con); 
		con.gridx++;
		
		con.gridx = 0;
		con.gridy++;
		getContentPane().add(button, con);
		
		
		button.setActionCommand("apply");
		button.addActionListener(this);

		
		this.pack();
		//setVisible(true);
	    this.setLocation(10,50);
	}
	
	public void start() 
	{
		this.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		/*if(arg0.getActionCommand().equals("repVar"))
		{
			if(repVar.isSelected())
			{
				repVarField.setVisible(true);
				this.pack();
			}else
			{
				repVarField.setVisible(false);
				this.pack();
			}
		}else
		{
			if(arg0.getActionCommand().equals("deathVar"))
			{
				if(deathVar.isSelected())
				{
					deathVarField.setVisible(true);
					getContentPane().remove(remain);
					getContentPane().remove(clear);
					getContentPane().remove(timed);
					getContentPane().remove(deathTimedField);
					GridBagConstraints con = new GridBagConstraints();
					con.gridx = 5;
					con.gridy = 1;
					con.gridwidth = 1;
					con.gridheight = 1;
					getContentPane().add(remain, con);
					con = new GridBagConstraints();
					con.gridx = 6;
					con.gridy = 1;
					con.gridwidth = 1;
					con.gridheight = 1;
					getContentPane().add(clear, con);
					con = new GridBagConstraints();
					con.gridx = 7;
					con.gridy = 1;
					con.gridwidth = 1;
					con.gridheight = 1;
					getContentPane().add(timed, con);
					con = new GridBagConstraints();
					con.gridx = 8;
					con.gridy = 1;
					con.gridwidth = 1;
					con.gridheight = 1;
					getContentPane().add(deathTimedField, con);
					this.pack();
				}else
				{
					deathVarField.setVisible(false);
					getContentPane().remove(remain);
					getContentPane().remove(clear);
					getContentPane().remove(timed);
					getContentPane().remove(deathTimedField);
					GridBagConstraints con = new GridBagConstraints();
					con.gridx = 4;
					con.gridy = 1;
					con.gridwidth = 1;
					con.gridheight = 1;
					getContentPane().add(remain, con);
					con = new GridBagConstraints();
					con.gridx = 5;
					con.gridy = 1;
					con.gridwidth = 1;
					con.gridheight = 1;
					getContentPane().add(clear, con);
					con = new GridBagConstraints();
					con.gridx = 6;
					con.gridy = 1;
					con.gridwidth = 1;
					con.gridheight = 1;
					getContentPane().add(timed, con);
					con = new GridBagConstraints();
					con.gridx = 7;
					con.gridy = 1;
					con.gridwidth = 1;
					con.gridheight = 1;
					getContentPane().add(deathTimedField, con);
					this.pack();
				}
			}else
			{
			if(arg0.getActionCommand().equals("remain")||arg0.getActionCommand().equals("clear")||arg0.getActionCommand().equals("timed"))
			{
				if(timed.isSelected())
				{
					deathTimedField.setVisible(true);
				}else
				{
					deathTimedField.setVisible(false);
				}
			}else
			{*/
				if(arg0.getActionCommand().equals("apply"))
				{
					/*if(repChanceField.getText().replace(",", "").equals(""))
					{
						repChanceField.setText("0");
					}
					if(repTabooField.getText().replace(",", "").equals(""))
					{
						repTabooField.setText("0");
					}
					if(deathChanceField.getText().replace(",", "").equals(""))
					{
						deathChanceField.setText("0");
					}
					if(deathVarField.getText().replace(",", "").equals(""))
					{
						deathVarField.setText("0");
					}
					if(deathTimedField.getText().replace(",", "").equals(""))
					{
						deathTimedField.setText("0");
					}
					if(varSpeedField.getText().replace(",", "").equals(""))
					{
						varSpeedField.setText("0");
					}
					if(varSpeedVarField.getText().replace(",", "").equals(""))
					{
						varSpeedVarField.setText("0");
					}
					if(boundSizeField.getText().replace(",", "").equals(""))
					{
						boundSizeField.setText("0");
					}
					if(repVarField.getText().replace(",", "").equals(""))
					{
						repVarField.setText("0");
					}
					if(bezMinDistanceField.getText().replace(",", "").equals(""))
					{
						bezMinDistanceField.setText("0");
					}
					if(bezMaxDistanceField.getText().replace(",", "").equals(""))
					{
						bezMaxDistanceField.setText("0");
					}
					if(bezChanceField.getText().replace(",", "").equals(""))
					{
						bezChanceField.setText("0");
					}
					if(bezNumField.getText().replace(",", "").equals(""))
					{
						bezNumField.setText("0");
					}
					if(bezSizeField.getText().replace(",", "").equals(""))
					{
						bezSizeField.setText("0");
					}
					if(bezVarField.getText().replace(",", "").equals(""))
					{
						bezVarField.setText("0");
					}*/
					
					//filename iterations and grid size
					this.emptyClean();
					this.sanitise();
					this.shuntValues();
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
					
					
				}
			}
	private void shuntValues() 
	{
		
		SingletonHolder.setBoundary(boundary.isSelected());
		SingletonHolder.setBezFlag(attractiveBezier.isSelected());
		SingletonHolder.setBezBounceFlag(bounceBez.isSelected());
		
		SingletonHolder.setFileName(fileName.getText().replace(",", ""));
		SingletonHolder.setIterations((int) Double.parseDouble(iterations.getText().replace(",", "")));
		SingletonHolder.setSize((int) Double.parseDouble(gridSize.getText().replace(",", "")));
		
		
		SingletonHolder.setBezMin((int) Double.parseDouble(bezMinDistanceField.getText().replace(",", "")));
		SingletonHolder.setBezMax((int) Double.parseDouble(bezMaxDistanceField.getText().replace(",", "")));
		SingletonHolder.setBezChance((int) Double.parseDouble(bezChanceField.getText().replace(",", "")));
		SingletonHolder.setBezNum((int) Double.parseDouble(bezNumField.getText().replace(",", "")));
		SingletonHolder.setBezSize((int) Double.parseDouble(bezSizeField.getText().replace(",", "")));
		SingletonHolder.setBezVarience((int) Double.parseDouble(bezVarField.getText().replace(",", "")));
		SingletonHolder.setBezType(bez.getSelection().getActionCommand());
		
		SingletonHolder.setBezSVar((int) Double.parseDouble(bezSVarField.getText().replace(",", "")));
		SingletonHolder.setBezDist(distBez.isSelected());
		SingletonHolder.setBezRed((int) Double.parseDouble(bezRedField.getText().replace(",", "")));
		SingletonHolder.setBezGreen((int) Double.parseDouble(bezGreenField.getText().replace(",", "")));
		
		SingletonHolder.setBoundaryType(squareCircle.getSelection().getActionCommand());
		SingletonHolder.setBoundarySize((int) Double.parseDouble(boundSizeField.getText().replace(",", "")));
		SingletonHolder.setDistribution(distr.getSelection().getActionCommand());
		
		SingletonHolder.setAttractZoneFlag(attract.isSelected());
		SingletonHolder.setDeflectZoneFlag(deflect.isSelected());
		
		SingletonHolder.setAttractSize(Double.parseDouble(attractSize.getText())); 
		SingletonHolder.setAttractStrength(Double.parseDouble(attractStrength.getText())); 
		SingletonHolder.setAttractFalloff(Double.parseDouble(attractFalloff.getText())); 
		SingletonHolder.setAttractZones(Double.parseDouble(attractZones.getText())); 
		SingletonHolder.setAttractSizeVar(Double.parseDouble(attractSizeVar.getText())); 
		SingletonHolder.setAttractStrengthVar(Double.parseDouble(attractStrengthVar.getText())); 
		
		SingletonHolder.setAttractEye(Double.parseDouble(eye.getText()));

		SingletonHolder.setDeflectSize(Double.parseDouble(deflectSize.getText())); 
		SingletonHolder.setDeflectStrength(Double.parseDouble(deflectStrength.getText())); 
		SingletonHolder.setDeflectFalloff(Double.parseDouble(deflectFalloff.getText())); 
		SingletonHolder.setDeflectZones(Double.parseDouble(deflectZones.getText())); 
		SingletonHolder.setDeflectSizeVar(Double.parseDouble(deflectSizeVar.getText())); 
		SingletonHolder.setDeflectStrengthVar(Double.parseDouble(deflectStrengthVar.getText())); 

	}

	private void sanitise()
	{
		if(Double.parseDouble(bezMinDistanceField.getText().replace(",", "")) > Double.parseDouble(bezMaxDistanceField.getText().replace(",", "")))
		{
			bezMinDistanceField.setText(bezMaxDistanceField.getText().replace(",", ""));
		}
		
		iterations.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinITERATIONS_DEAFULT(),SingletonSanitisationFields.getMaxITERATIONS_DEAFULT(), Integer.parseInt(iterations.getText().replace(",", ""))));
		gridSize.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinGridSizeDeafult(),SingletonSanitisationFields.getMaxGridSizeDeafult(), Integer.parseInt(gridSize.getText().replace(",", ""))));
		bezMinDistanceField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinBezMinDeafult(),SingletonSanitisationFields.getMaxBezMinDeafult(), Integer.parseInt(bezMinDistanceField.getText().replace(",", ""))));
		bezMaxDistanceField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinBezMaxDeafult(),SingletonSanitisationFields.getMaxBezMaxDeafult(), Integer.parseInt(bezMaxDistanceField.getText().replace(",", ""))));
		bezChanceField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinBezChanceDeafult(),SingletonSanitisationFields.getMaxBezChanceDeafult(), Integer.parseInt(bezChanceField.getText().replace(",", ""))));
		bezNumField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinBezNumDeafult(),SingletonSanitisationFields.getMaxBezNumDeafult(), Integer.parseInt(bezNumField.getText().replace(",", ""))));
		bezSizeField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinBezSizeDeafult(),SingletonSanitisationFields.getMaxBezSizeDeafult(), Integer.parseInt(bezSizeField.getText().replace(",", ""))));
		bezVarField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinBezVarDeafult(),SingletonSanitisationFields.getMaxBezVarDeafult(), Integer.parseInt(bezVarField.getText().replace(",", ""))));
		boundSizeField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinBOUNDARY_SIZE_DEAFULT(),SingletonSanitisationFields.getMAXBOUNDARY_SIZE_DEAFULT(), Integer.parseInt(boundSizeField.getText().replace(",", ""))));

	}

			/*}
			
		}
		
	}*/

	private void emptyClean() 
	{
		if(fileName.getText().replace(",", "").isEmpty()) { fileName.setText("0");}
		if(iterations.getText().replace(",", "").isEmpty()) { iterations.setText("0");}
		if(gridSize.getText().replace(",", "").isEmpty()) { gridSize.setText("0");}
		if(bezMinDistanceField.getText().replace(",", "").isEmpty()) { bezMinDistanceField.setText("0");}
		if(bezMaxDistanceField.getText().replace(",", "").isEmpty()) { bezMaxDistanceField.setText("0");}
		if(bezChanceField.getText().replace(",", "").isEmpty()) { bezChanceField.setText("0");}
		if(bezNumField.getText().replace(",", "").isEmpty()) { bezNumField.setText("0");}
		if(bezSizeField.getText().replace(",", "").isEmpty()) { bezSizeField.setText("0");}
		if(bezVarField.getText().replace(",", "").isEmpty()) { bezVarField.setText("0");}
		if(boundSizeField.getText().replace(",", "").isEmpty()) { boundSizeField.setText("0");}
		if(bezSVarField.getText().replace(",", "").isEmpty()) {bezSVarField.setText("0");}
		if(bezRedField.getText().replace(",", "").isEmpty()) {bezRedField.setText("0");}
		if(bezGreenField.getText().replace(",", "").isEmpty()) {bezGreenField.setText("0");}
		
		if( attractSize.getText().replace(",", "").isEmpty()) {attractSize.setText("0");} 
		if( attractStrength.getText().replace(",", "").isEmpty()) {attractStrength.setText("0");} 
		if( attractFalloff.getText().replace(",", "").isEmpty()) {attractFalloff.setText("0");}
		if( attractZones.getText().replace(",", "").isEmpty()) {attractZones.setText("0");}
		if( attractSizeVar.getText().replace(",", "").isEmpty()) {attractSizeVar.setText("0");} 
		if( attractStrengthVar.getText().replace(",", "").isEmpty()) {attractStrengthVar.setText("0");} 
		if( eye.getText().replace(",", "").isEmpty()) {eye.setText("0");} 
		
		if( deflectSize.getText().replace(",", "").isEmpty()) {deflectSize.setText("0");} 
		if( deflectStrength.getText().replace(",", "").isEmpty()) {deflectStrength.setText("0");} 
		if( deflectFalloff.getText().replace(",", "").isEmpty()) {deflectFalloff.setText("0");}
		if( deflectZones.getText().replace(",", "").isEmpty()) {deflectZones.setText("0");}
		if( deflectSizeVar.getText().replace(",", "").isEmpty()) {deflectSizeVar.setText("0");} 
		if( deflectStrengthVar.getText().replace(",", "").isEmpty()) {deflectStrengthVar.setText("0");} 
		
	}

	public void setCycle(Cycle cycle) {
		cyc = cycle;
		
	}

}
