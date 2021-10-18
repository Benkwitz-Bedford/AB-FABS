package gui_options;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JFormattedTextField;

import singleton_holders.SingletonHolder;
import singleton_holders.SingletonSanitisationFields;
import file_manipulation.MockUpTrajMaker;

public class CellOptionsPanel extends JPanel implements ActionListener{
	//37
	//0-4 repCheck, repChance, repTaboo, repVarianceCheck, repVarience
	//5-10 deathCheck, deathChance, deathVarienceCheck, deathVarience, deathType, deathTimedNum
	//11-14 speedCheck, speedAmount, speedVarience, speedType
	//15-16 changeChance, changeVarience
	//17 noOfCells
	//18-19 wOne, oneVar,
	//20-21 wTwo, twoVar,
	//22-23 wThree, threeVar,
	//24-25 wFour, fourVar,
	//26-27 wFive, fiveVar,
	//28-29 wSix, sixVar,
	//30-31 wSeven, sevenVar,
	//32-33 wEight, eightVar,
	//34rand/rou
	//35-36 cell size and cell varience
	private String[] values = new String[40];

	
	String type = "random";
	

	JCheckBox replication = new JCheckBox("Replication");
	JLabel repChanceLabel = new JLabel("chance");
	JFormattedTextField repChanceField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JLabel repTabooLabel = new JLabel("Minimum rest");
	JFormattedTextField repTabooField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JCheckBox repVar = new JCheckBox("varience");
	JFormattedTextField repVarField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);

	JCheckBox death = new JCheckBox("Death");
	JLabel deathChanceLabel = new JLabel("chance");
	JFormattedTextField deathChanceField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JCheckBox deathVar = new JCheckBox("varience");
	JFormattedTextField deathVarField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JRadioButton remain = new JRadioButton("remain");
	JRadioButton clear = new JRadioButton("clear");
	JRadioButton timed = new JRadioButton("timed");
	JFormattedTextField deathTimedField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);

	JCheckBox variableSpeed = new JCheckBox("Speed");
	JLabel varSpeedLabel = new JLabel("mean speed");
	JFormattedTextField varSpeedField = new JFormattedTextField(NumberFormat.Field.DECIMAL_SEPARATOR);
	JLabel varSpeedVarLabel = new JLabel("varience");
	JFormattedTextField varSpeedVarField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JRadioButton randomSpeed = new JRadioButton("randomSpeed");
	JRadioButton normallyDist = new JRadioButton("normal dist");
	
	JFormattedTextField changeChance = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField changeVarience = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField noOfCells = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	
	JFormattedTextField wOne = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wTwo = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wThree = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wFour = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wFive = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wSix = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wSeven = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wEight = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	
	JFormattedTextField wOneVarience = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wTwoVarience = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wThreeVarience = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wFourVarience = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wFiveVarience = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wSixVarience = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wSevenVarience = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField wEightVarience = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	
	JRadioButton random = new JRadioButton("Random");
	JRadioButton roulette = new JRadioButton("Roulette");
	JRadioButton brown = new JRadioButton("Brownian");
	
	ButtonGroup corpseDisposal = new ButtonGroup();
	ButtonGroup speedSpread = new ButtonGroup();

	JLabel lO = new JLabel("seg one");
	JLabel lT = new JLabel("seg two");
	JLabel lTh = new JLabel("seg thre");
	JLabel lF = new JLabel("seg four");
	JLabel lFi = new JLabel("seg five");
	JLabel lSi = new JLabel("seg six");
	JLabel lSe = new JLabel("seg seven");
	JLabel lE = new JLabel("seg eight");
	
	JLabel valueHead = new JLabel("Values");
	JLabel varienceHead = new JLabel("Varience");
	JLabel valueHead1 = new JLabel("Values");
	JLabel varienceHead1 = new JLabel("Varience");
	JLabel valueHead2 = new JLabel("Values");
	JLabel varienceHead2 = new JLabel("Varience");
	
	JLabel cellSizeLab = new JLabel("cell size");
	JFormattedTextField cellSize = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JFormattedTextField cellSizeVarience = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	
	JCheckBox antPath = new JCheckBox("AntPath");
	JLabel antWeightLabel = new JLabel("weight");
	JFormattedTextField antWeightField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JLabel antVarLabel = new JLabel("varience");
	JFormattedTextField antVarField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	
	JButton delete = new JButton("delete");
	private boolean deletePoss = true;
	
	String handleType = "live";
	
	private Boolean active = true;
	private int position = 0;
	
	//ideally create separate grid types for slapping on interfaces but for now hard code in 2 places
	public CellOptionsPanel(String string, String string2)
	{
		handleType = string2;
		
		JLabel lCC = new JLabel("trajectory change");
		JLabel lNOC = new JLabel("total cells");
		
		
		
		
		this.setPreferredSize(new Dimension(1000,200));
		Dimension buttonSize = new Dimension(50,20);
		Dimension fieldSize = new Dimension(100,20);
		Dimension textFieldSize = new Dimension(100,20);
		type = string;
		this.setLayout(new GridBagLayout());
		
		replication.setPreferredSize(textFieldSize);
		death.setPreferredSize(textFieldSize);
		variableSpeed.setPreferredSize(textFieldSize);
		
		repChanceLabel.setPreferredSize(fieldSize);
		repChanceField.setPreferredSize(textFieldSize);
		repTabooLabel.setPreferredSize(fieldSize);
		repTabooField.setPreferredSize(textFieldSize);
		repVar.setPreferredSize(fieldSize); 
		repVarField.setPreferredSize(textFieldSize);
		
		repChanceField.setText(""+SingletonHolder.getRepChanceDeafult());
		repChanceField.setToolTipText("the chance of each cell replicating at each iteration");
		repTabooField.setText(""+SingletonHolder.getRepTabooDeafult());
		repTabooField.setToolTipText("the number of iterations forced between replications");
		repVarField.setText(""+SingletonHolder.getRepVarienceDeafult());
		repVarField.setToolTipText("the varience of replication chance between entities");
		repVar.addActionListener(this);
		repVar.setActionCommand("repVar");
		
		antPath.setPreferredSize(textFieldSize);
		antWeightLabel.setPreferredSize(fieldSize) ;
		antWeightField.setPreferredSize(textFieldSize) ;
		antVarLabel.setPreferredSize(fieldSize) ;
		antVarField.setPreferredSize(textFieldSize) ;
		
		antWeightLabel.setToolTipText("the weight of pheromone following") ;
		antWeightField.setText(""+SingletonHolder.getANTRATIO_DEAFULT()) ;
		antVarLabel.setToolTipText("the varience of following weight");
		antVarField.setText(""+SingletonHolder.getANTVARIENCERATIO_DEAFULT());
		
		
		deathChanceLabel.setPreferredSize(fieldSize);
		deathChanceField.setPreferredSize(textFieldSize);
		deathVar.setPreferredSize(fieldSize);
		deathVarField.setPreferredSize(textFieldSize);
		remain.setPreferredSize(textFieldSize);
		clear.setPreferredSize(textFieldSize);
		timed.setPreferredSize(textFieldSize);
		deathTimedField.setPreferredSize(textFieldSize);
		
	
		deathChanceField.setText(""+SingletonHolder.getDeathChanceDeafult());
		deathChanceField.setToolTipText("the chance of each cell dying at each iteration");
		deathVar.setActionCommand("deathVar");
		deathVarField.setToolTipText("the varience of death chance between entities");
		deathVarField.setText(""+SingletonHolder.getDeathVarienceDeafult());
		remain.setToolTipText("corpses will remain");
		clear.setToolTipText("corpses will clear");
		timed.setToolTipText("corpses will clear after a certain time");
		clear.setSelected(true);
		remain.setActionCommand("remain");
		clear.setActionCommand("clear");
		timed.setActionCommand("timed");
		deathTimedField.setText(""+SingletonHolder.getDeathTTRDeafult());
		deathTimedField.setToolTipText("the time taken for corspes to dissapear");

		varSpeedLabel.setPreferredSize(fieldSize);
		varSpeedField.setPreferredSize(textFieldSize);
		varSpeedVarLabel.setPreferredSize(fieldSize);
		varSpeedVarField.setPreferredSize(textFieldSize);
		randomSpeed.setPreferredSize(textFieldSize);
		normallyDist.setPreferredSize(textFieldSize);
		
		varSpeedField.setText(""+SingletonHolder.getSpeedDeafult());
		varSpeedField.setToolTipText("the mean heterogeneous speed ");
		varSpeedVarField.setText(""+SingletonHolder.getSpeedVarDeafult());
		varSpeedVarField.setToolTipText("the varience of speed bear in mind any values below 0 will default to 1");
		randomSpeed.setActionCommand("randomSpeed");
		randomSpeed.setSelected(true);
		normallyDist.setActionCommand("normal");
		
	    cellSizeLab.setPreferredSize(fieldSize);
	    cellSize.setPreferredSize(fieldSize);
	    cellSizeVarience.setPreferredSize(fieldSize);
	    cellSize.setText(""+SingletonHolder.getCELLSIZE_DEAFULT());
	    cellSizeVarience.setText(""+SingletonHolder.getCELLSIZEVARIENCE_DEAFULT());
		
		
		changeChance.setPreferredSize(fieldSize);
		changeVarience.setPreferredSize(fieldSize);
		noOfCells.setPreferredSize(fieldSize);
		
		wOne.setPreferredSize(fieldSize);
		wTwo.setPreferredSize(fieldSize);
		wThree.setPreferredSize(fieldSize);
		wFour.setPreferredSize(fieldSize);
		wFive.setPreferredSize(fieldSize);
		wSix.setPreferredSize(fieldSize);
		wSeven.setPreferredSize(fieldSize);
		wEight.setPreferredSize(fieldSize);
		
		wOneVarience.setPreferredSize(fieldSize);
		wTwoVarience.setPreferredSize(fieldSize);
		wThreeVarience.setPreferredSize(fieldSize);
		wFourVarience.setPreferredSize(fieldSize);
		wFiveVarience.setPreferredSize(fieldSize);
		wSixVarience.setPreferredSize(fieldSize);
		wSevenVarience.setPreferredSize(fieldSize);
		wEightVarience.setPreferredSize(fieldSize);
		
		
		lCC.setPreferredSize(textFieldSize);
		lNOC.setPreferredSize(textFieldSize);
		valueHead.setPreferredSize(textFieldSize);
		varienceHead.setPreferredSize(textFieldSize);
		valueHead2.setPreferredSize(textFieldSize);
		varienceHead2.setPreferredSize(textFieldSize);
		
		random.setPreferredSize(textFieldSize);
		roulette.setPreferredSize(textFieldSize);
		
		corpseDisposal.add(remain);
		corpseDisposal.add(clear);
		corpseDisposal.add(timed);
		
		speedSpread.add(randomSpeed);
		speedSpread.add(normallyDist);
		
		delete.setPreferredSize(textFieldSize);
		
		delete.setActionCommand("delete");
		delete.addActionListener(this);
		
		
		GridBagConstraints con = new GridBagConstraints();
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(replication, con);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(repChanceLabel, con);
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(repChanceField, con);
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(repTabooLabel, con);
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(repTabooField, con);
		con = new GridBagConstraints();
		con.gridx = 5;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(repVar, con);
		con = new GridBagConstraints();
		con.gridx = 6;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(repVarField, con);
		
		con = new GridBagConstraints();
		con.gridx = 8;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(delete, con);
		
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(death, con);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(deathChanceLabel, con);
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(deathChanceField, con);
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(deathVar, con);
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(deathVarField, con);
		con = new GridBagConstraints();
		con.gridx = 5;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(remain, con);
		con = new GridBagConstraints();
		con.gridx = 6;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(clear, con);
		con = new GridBagConstraints();
		con.gridx = 7;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(timed, con);
		con = new GridBagConstraints();
		con.gridx = 8;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(deathTimedField, con);
		
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(variableSpeed, con);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(varSpeedLabel, con);
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(varSpeedField, con);
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(varSpeedVarLabel, con);
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(varSpeedVarField, con);
		con = new GridBagConstraints();
		con.gridx = 5;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(random, con);
		con = new GridBagConstraints();
		con.gridx = 6;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(normallyDist, con);
		
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 3;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(antPath, con);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 3;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(antWeightLabel, con);
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 3;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(antWeightField, con);
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 3;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(antVarLabel, con);
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 3;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(antVarField, con);
		
		
		//radio
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 4;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(random, con);
		random.setActionCommand("random");
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 4;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(roulette, con);
		roulette.setActionCommand("roulette");
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 4;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(brown, con);
		brown.setActionCommand("brownian");
		
		//headers
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 5;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(valueHead, con);
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 5;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(varienceHead, con);
		
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 5;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(valueHead1, con);
		con = new GridBagConstraints();
		con.gridx = 5;
		con.gridy = 5;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(varienceHead1, con);
		
		con = new GridBagConstraints();
		con.gridx = 7;
		con.gridy = 5;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(valueHead2, con);
		con = new GridBagConstraints();
		con.gridx = 8;
		con.gridy = 5;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(varienceHead2, con);
		
		
		
		
		//chance of change per iteration
		changeChance.setText(""+SingletonHolder.getCHANGE_DEAFULT());
		changeChance.setToolTipText("chance of changing direction out of 100,000  min:"+SingletonSanitisationFields.getMinCHANGE_DEAFULT()+" max:"+SingletonSanitisationFields.getMaxCHANGE_DEAFULT());
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 6;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(lCC, con);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 6;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(changeChance, con);
		changeVarience.setText("0");
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 6;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(changeVarience, con);
		
		//number of cells
		noOfCells.setText(""+SingletonHolder.getCELLS_DEAFULT());
		noOfCells.setToolTipText("total numbr of cells  min:"+SingletonSanitisationFields.getMinCELLS_DEAFULT()+" max:"+SingletonSanitisationFields.getMaxCELLS_DEAFULT());
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 6;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(lNOC, con);
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 6;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(noOfCells, con);	
		
		con = new GridBagConstraints();
		con.gridx = 6;
		con.gridy = 6;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(cellSizeLab, con);
		con = new GridBagConstraints();
		con.gridx = 7;
		con.gridy = 6;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(cellSize, con);	
		con = new GridBagConstraints();
		con.gridx = 8;
		con.gridy = 6;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		this.add(cellSizeVarience, con);	
		
		lO.setPreferredSize(textFieldSize);
		lT.setPreferredSize(textFieldSize);
		lTh.setPreferredSize(textFieldSize);
		lF.setPreferredSize(textFieldSize);
		lFi.setPreferredSize(textFieldSize);
		lSi.setPreferredSize(textFieldSize);
		lSe.setPreferredSize(textFieldSize);
		lE.setPreferredSize(textFieldSize);
		
		
		
		//8 seg weight
		 wOne.setText("15000");
		 wOne.setToolTipText("weight for segment one, each segment is contiguously running clockwise  min:"+SingletonSanitisationFields.getMinWONE_DEAFULT()+" max:"+SingletonSanitisationFields.getMaxWONE_DEAFULT());
		 con = new GridBagConstraints();
		 con.gridx = 0;
		 con.gridy = 7;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(lO, con);
		 con = new GridBagConstraints();
		 con.gridx = 1;
		 con.gridy = 7;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wOne, con);
		 wOneVarience.setText("0");
		 con = new GridBagConstraints();
		 con.gridx = 2;
		 con.gridy = 7;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wOneVarience, con);
		 
		 wTwo.setText(""+SingletonHolder.getWTWO_DEAFULT());
		 wTwo.setToolTipText("weight for segment two  min:"+SingletonSanitisationFields.getMinWTWO_DEAFULT()+" max:"+SingletonSanitisationFields.getMaxWTWO_DEAFULT());
		 con = new GridBagConstraints();
		 con.gridx = 3;
		 con.gridy = 7;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		 this.add(lT, con);
		 con = new GridBagConstraints();
		 con.gridx = 4;
		 con.gridy = 7;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wTwo, con);
		 wTwoVarience.setText("0");
		 con = new GridBagConstraints();
		 con.gridx = 5;
		 con.gridy = 7;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wTwoVarience, con);
		 
		 wThree.setText(""+SingletonHolder.getWTHREE_DEAFULT());
		 wThree.setToolTipText("weight for segment three  min:"+SingletonSanitisationFields.getMinWTHREE_DEAFULT()+" max:"+SingletonSanitisationFields.getMaxWTHREE_DEAFULT());
		 con = new GridBagConstraints();
		 con.gridx = 6;
		 con.gridy = 7;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		 this.add(lTh, con);
		 con = new GridBagConstraints();
		 con.gridx = 7;
		 con.gridy = 7;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wThree, con);
		 wThreeVarience.setText("0");
		 con = new GridBagConstraints();
		 con.gridx = 8;
		 con.gridy = 7;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wThreeVarience, con);
		 
		 wFour.setText(""+SingletonHolder.getWFOUR_DEAFULT());
		 wFour.setToolTipText("weight for sgement four  min:"+SingletonSanitisationFields.getMinWFOUR_DEAFULT()+" max:"+SingletonSanitisationFields.getMaxWFOUR_DEAFULT());
		 con = new GridBagConstraints();
		 con.gridx = 0;
		 con.gridy = 8;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		 this.add(lF, con);
		 con = new GridBagConstraints();
		 con.gridx = 1;
		 con.gridy = 8;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wFour, con);
		 wFourVarience.setText("0");
		 con = new GridBagConstraints();
		 con.gridx = 2;
		 con.gridy = 8;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wFourVarience, con);
		 
		 wFive.setText(""+SingletonHolder.getWFIVE_DEAFULT());
		 wFive.setToolTipText("weight for segment five  min:"+SingletonSanitisationFields.getMinWFIVE_DEAFULT()+" max:"+SingletonSanitisationFields.getMaxWFIVE_DEAFULT());
		 con = new GridBagConstraints();
		 con.gridx = 3;
		 con.gridy = 8;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		 this.add(lFi, con);
		 con = new GridBagConstraints();
		 con.gridx = 4;
		 con.gridy = 8;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wFive, con);
		 wFiveVarience.setText("0");
		 con = new GridBagConstraints();
		 con.gridx = 5;
		 con.gridy = 8;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wFiveVarience, con);
		 
		 wSix.setText(""+SingletonHolder.getWSIX_DEAFULT());
		 wSix.setToolTipText("weight for segment six  min:"+SingletonSanitisationFields.getMinWSIX_DEAFULT()+" max:"+SingletonSanitisationFields.getMaxWSIX_DEAFULT());
		 con = new GridBagConstraints();
		 con.gridx = 6;
		 con.gridy = 8;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		 this.add(lSi, con);
		 con = new GridBagConstraints();
		 con.gridx = 7;
		 con.gridy = 8;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wSix, con);
		 wSixVarience.setText("0");
		 con = new GridBagConstraints();
		 con.gridx = 8;
		 con.gridy = 8;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wSixVarience, con);
		 
		 wSeven.setText(""+SingletonHolder.getWSEVEN_DEAFULT());
		 wSeven.setToolTipText("weight for segment seven  min:"+SingletonSanitisationFields.getMinWSEVEN_DEAFULT()+" max:"+SingletonSanitisationFields.getMaxWSEVEN_DEAFULT());
		 con = new GridBagConstraints();
		 con.gridx = 0;
		 con.gridy = 9;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		 this.add(lSe, con);
		 con = new GridBagConstraints();
		 con.gridx = 1;
		 con.gridy = 9;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wSeven, con);
		 wSevenVarience.setText("0");
		 con = new GridBagConstraints();
		 con.gridx = 2;
		 con.gridy = 9;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wSevenVarience, con);
		 
		 wEight.setText(""+SingletonHolder.getWEIGHT_DEAFULT());
		 wEight.setToolTipText("weight for segment eight  min:"+SingletonSanitisationFields.getMinWEIGHT_DEAFULT()+" max:"+SingletonSanitisationFields.getMaxWEIGHT_DEAFULT());
		 con = new GridBagConstraints();
		 con.gridx = 3;
		 con.gridy = 9;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		 this.add(lE, con);
		 con = new GridBagConstraints();
		 con.gridx = 4;
		 con.gridy = 9;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wEight, con);		
		 wEightVarience.setText("0");
		 con = new GridBagConstraints();
		 con.gridx = 5;
		 con.gridy = 9;
		 con.gridwidth = 1;
		 con.gridheight = 1;
		 //con.fill = GridBagConstraints.BOTH;
		 this.add(wEightVarience, con);		
		 
		 random.addActionListener(this);
		 roulette.addActionListener(this);
		 brown.addActionListener(this);
			
		//setVisible(true);
	    roulette.setSelected(true);
	    this.rouletteSelect();
	    this.validate();
	    //this.randomSelect();
	}

	

	public void actionPerformed(ActionEvent arg0) 
	{
		//>>>>>>>>>FIX<<<<<<<<<<
		//gridSize.setText("1500"); 
		
		if(arg0.getActionCommand().equals("random"))
		{
			this.randomSelect();
			
		}else
		{
			if(arg0.getActionCommand().equals("roulette"))
			{
				this.rouletteSelect();
				
			}else
			{
				if(arg0.getActionCommand().equals("delete"))
				{
					this.setVisible(false);
					setActive(false);
				}else
				{
					if(arg0.getActionCommand().equals("brownian"))
					{
						this.brownSelect();
						
					}
				}
			}
		}
		/*if(handleType.equals("once"))
		{
			MockUpTrajMaker fakes = new MockUpTrajMaker();
			if(arg0.getActionCommand().equals("ok"))
			{
				ArrayList<String> values = new ArrayList<String>();
				values.add(type);
				values.add(fileName.getText().replace(",", ""));
				values.add(iterations.getText().replace(",", ""));
				values.add(changeChance.getText().replace(",", ""));
				values.add(noOfCells.getText().replace(",", ""));
				values.add(gridSize.getText().replace(",", ""));
				fakes.buildRandom(values);
				
				if(type.equals("roulette"))
				{
					//8 seg weight
					values.add(wOne.getText().replace(",", ""));
					values.add(wTwo.getText().replace(",", ""));
					values.add(wThree.getText().replace(",", ""));
					values.add(wFour.getText().replace(",", ""));
					values.add(wFive.getText().replace(",", ""));
					values.add(wSix.getText().replace(",", ""));
					values.add(wSeven.getText().replace(",", ""));
					values.add(wEight.getText().replace(",", ""));
					fakes.buildRoulette(values);
				}
				SingletonHolder.setValueSet(values);
				setVisible(false);
			}
		}else
		{
			if(handleType.equals("live"))
			{
				if(arg0.getActionCommand().equals("ok"))
				{
					ArrayList<String> values = new ArrayList<String>();
					values.add(type);
					values.add(fileName.getText().replace(",", ""));
					values.add(iterations.getText().replace(",", ""));
					values.add(changeChance.getText().replace(",", ""));
					values.add(noOfCells.getText().replace(",", ""));
					values.add(gridSize.getText().replace(",", ""));
					
					SingletonHolder.setReplication(replication.isSelected());
					SingletonHolder.setDeath(death.isSelected());
					SingletonHolder.setSpeedFlag(variableSpeed.isSelected());
					SingletonHolder.setRepChance(Integer.parseInt(repChanceField.getText().replace(",", "")));
					SingletonHolder.setRepTaboo(Integer.parseInt(repTabooField.getText().replace(",", "")));
					SingletonHolder.setRepVar(repVar.isSelected());
					SingletonHolder.setRepVarience(Integer.parseInt(repVarField.getText().replace(",", "")));
					
					SingletonHolder.setDeathChance(Integer.parseInt(deathChanceField.getText().replace(",", "")));
					SingletonHolder.setDeathType(corpseDisposal.getSelection().getActionCommand());
					SingletonHolder.setDeathVar(deathVar.isSelected());
					SingletonHolder.setDeathVarience(Integer.parseInt(deathVarField.getText().replace(",", "")));
					SingletonHolder.setDeathTTR(Integer.parseInt(deathTimedField.getText().replace(",", "")));
					
					SingletonHolder.setSpeed(Integer.parseInt(varSpeedField.getText().replace(",", "")));
					SingletonHolder.setSpeedVar(Integer.parseInt(varSpeedVarField.getText().replace(",", "")));
					SingletonHolder.setSpeedType(speedSpread.getSelection().getActionCommand());
					
					if(type.equals("roulette"))
					{
						//8 seg weight
						values.add(wOne.getText().replace(",", ""));
						values.add(wTwo.getText().replace(",", ""));
						values.add(wThree.getText().replace(",", ""));
						values.add(wFour.getText().replace(",", ""));
						values.add(wFive.getText().replace(",", ""));
						values.add(wSix.getText().replace(",", ""));
						values.add(wSeven.getText().replace(",", ""));
						values.add(wEight.getText().replace(",", ""));
					}
					SingletonHolder.setValueSet(values);
				}
			}
		}*/
	}


	private void toggleRouletteValues(Boolean vis) 
	{
		
		wOne.setVisible(vis);
		wTwo.setVisible(vis);
		wThree.setVisible(vis);
		wFour.setVisible(vis);
		wFive.setVisible(vis);
		wSix.setVisible(vis);
		wSeven.setVisible(vis);
		wEight.setVisible(vis);
		
		wOneVarience.setVisible(vis);
		wTwoVarience.setVisible(vis);
		wThreeVarience.setVisible(vis);
		wFourVarience.setVisible(vis);
		wFiveVarience.setVisible(vis);
		wSixVarience.setVisible(vis);
		wSevenVarience.setVisible(vis);
		wEightVarience.setVisible(vis);
		
		lO.setVisible(vis);
		lT.setVisible(vis);
		lTh.setVisible(vis);
		lF.setVisible(vis);
		lFi.setVisible(vis);
		lSi.setVisible(vis);
		lSe.setVisible(vis);
		lE.setVisible(vis);
		valueHead.setVisible(vis);
		varienceHead.setVisible(vis);
		valueHead1.setVisible(vis);
		varienceHead1.setVisible(vis);
		valueHead2.setVisible(vis);
		varienceHead2.setVisible(vis);
		
	}
	private void rouletteSelect() 
	{
		//make type roulette
		type = "roulette";
		this.setPreferredSize(new Dimension(900, 300));
		//uncheck other option
		random.setSelected(false);
		//hide roulette values
		this.toggleRouletteValues(true);
		
	}

	private void randomSelect() 
	{
		//make type random
		type = "random";
		//uncheck other option
		roulette.setSelected(false);
		//hide roulette values
		this.toggleRouletteValues(false);
		this.validate();
		
	}
	
	private void brownSelect() 
	{
		//make type random
		type = "brownian";
		//uncheck other option
		roulette.setSelected(false);
		random.setSelected(false);
		brown.setSelected(true);
		//hide roulette values
		this.toggleRouletteValues(false);
		changeChance.setText("100000");
		this.validate();
		
	}
	
	public String[] getValues()
	{
		this.emptyClean();
		this.sanitise();
		this.harvestValues();
		return values;
	}


	private void sanitise()
	{
		repChanceField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinRepChanceDeafult(),SingletonSanitisationFields.getMaxRepChanceDeafult(), Integer.parseInt(repChanceField.getText().replace(",", ""))));
		repTabooField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinRepTabooDeafult(),SingletonSanitisationFields.getMaxRepTabooDeafult(), Integer.parseInt(repTabooField.getText().replace(",", ""))));
		repVarField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinRepVarienceDeafult(),SingletonSanitisationFields.getMaxRepVarienceDeafult(), Integer.parseInt(repVarField.getText().replace(",", ""))));
		deathChanceField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinDeathChanceDeafult(),SingletonSanitisationFields.getMaxDeathChanceDeafult(), Integer.parseInt(deathChanceField.getText().replace(",", ""))));
		deathVarField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinDeathVarienceDeafult(),SingletonSanitisationFields.getMaxDeathVarienceDeafult(), Integer.parseInt(deathVarField.getText().replace(",", ""))));
		deathTimedField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinDeathTTRDeafult(),SingletonSanitisationFields.getMaxDeathTTRDeafult(), Integer.parseInt(deathTimedField.getText().replace(",", ""))));
		varSpeedField.setText(""+SingletonSanitisationFields.sanitiseDouble(SingletonSanitisationFields.getMinSpeedDeafult(),SingletonSanitisationFields.getMaxSpeedDeafult(), Double.parseDouble(varSpeedField.getText().replace(",", ""))));
		varSpeedVarField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinSpeedVarDeafult(),SingletonSanitisationFields.getMaxSpeedVarDeafult(), Integer.parseInt(varSpeedVarField.getText().replace(",", ""))));
		changeChance.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinCHANGE_DEAFULT(),SingletonSanitisationFields.getMaxCHANGE_DEAFULT(), Integer.parseInt(changeChance.getText().replace(",", ""))));
		changeVarience.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinVarChangeDeafult(),SingletonSanitisationFields.getMaxVarChangeDeafult(), Integer.parseInt(changeVarience.getText().replace(",", ""))));
		noOfCells.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinCELLS_DEAFULT(),SingletonSanitisationFields.getMaxCELLS_DEAFULT(), Integer.parseInt(noOfCells.getText().replace(",", ""))));
		wOne.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWONE_DEAFULT(),SingletonSanitisationFields.getMaxWONE_DEAFULT(), Integer.parseInt(wOne.getText().replace(",", ""))));
		wOneVarience.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWOneVarDeafult(),SingletonSanitisationFields.getMaxWOneVarDeafult(), Integer.parseInt(wOneVarience.getText().replace(",", ""))));
		wTwo.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWTWO_DEAFULT(),SingletonSanitisationFields.getMaxWTWO_DEAFULT(), Integer.parseInt(wTwo.getText().replace(",", ""))));
		wTwoVarience.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWTwoVarDeafult(),SingletonSanitisationFields.getMaxWOneVarDeafult(), Integer.parseInt(wTwoVarience.getText().replace(",", ""))));
		wThree.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWTHREE_DEAFULT(),SingletonSanitisationFields.getMaxWTHREE_DEAFULT(), Integer.parseInt(wThree.getText().replace(",", ""))));
		wThreeVarience.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWThreeVarDeafult(),SingletonSanitisationFields.getMaxWThreeVarDeafult(), Integer.parseInt(wThreeVarience.getText().replace(",", ""))));
		wFour.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWFOUR_DEAFULT(),SingletonSanitisationFields.getMaxWFOUR_DEAFULT(), Integer.parseInt(wFour.getText().replace(",", ""))));
		wFourVarience.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWFourVarDeafult(),SingletonSanitisationFields.getMaxWFourVarDeafult(), Integer.parseInt(wFourVarience.getText().replace(",", ""))));
		wFive.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWFIVE_DEAFULT(),SingletonSanitisationFields.getMaxWFIVE_DEAFULT(), Integer.parseInt(wFive.getText().replace(",", ""))));
		wFiveVarience.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWFiveVarDeafult(),SingletonSanitisationFields.getMaxWFiveVarDeafult(), Integer.parseInt(wFiveVarience.getText().replace(",", ""))));
		wSix.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWSIX_DEAFULT(),SingletonSanitisationFields.getMaxWSIX_DEAFULT(), Integer.parseInt(wSix.getText().replace(",", ""))));
		wSixVarience.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWSixVarDeafult(),SingletonSanitisationFields.getMaxWSixVarDeafult(), Integer.parseInt(wSixVarience.getText().replace(",", ""))));
		wSeven.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWSEVEN_DEAFULT(),SingletonSanitisationFields.getMaxWSEVEN_DEAFULT(), Integer.parseInt(wSeven.getText().replace(",", ""))));
		wSevenVarience.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWSevenVarDeafult(),SingletonSanitisationFields.getMaxWSevenVarDeafult(), Integer.parseInt(wSevenVarience.getText().replace(",", ""))));
		wEight.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWEIGHT_DEAFULT(),SingletonSanitisationFields.getMaxWEIGHT_DEAFULT(), Integer.parseInt(wEight.getText().replace(",", ""))));
		wEightVarience.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinWEightVarDeafult(),SingletonSanitisationFields.getMaxWEightVarDeafult(), Integer.parseInt(wEightVarience.getText().replace(",", ""))));
		cellSize.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinCELL_SIZE_DEAFULT(),SingletonSanitisationFields.getMaxCELL_SIZE_DEAFULT(), Integer.parseInt(cellSize.getText().replace(",", ""))));
		cellSizeVarience.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinCellSizeVarDeafult(),SingletonSanitisationFields.getMaxCellSizeVarDeafult(), Integer.parseInt(cellSizeVarience.getText().replace(",", ""))));
		antWeightField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinAntWeightDeafult(),SingletonSanitisationFields.getMaxAntWeightDeafult(), Long.parseLong(antWeightField.getText().replace(",", ""))));
		antVarField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinAntVarDeafult(),SingletonSanitisationFields.getMaxAntVarDeafult(), Long.parseLong(antVarField.getText().replace(",", ""))));
		
		
	}

			/*}
			
		}
		
	}*/

	private void emptyClean() 
	{
		if(repChanceField.getText().replace(",", "").isEmpty()) { repChanceField.setText("0");}
		if(repTabooField.getText().replace(",", "").isEmpty()) { repTabooField.setText("0");}
		if(repVarField.getText().replace(",", "").isEmpty()) { repVarField.setText("0");}
		if(deathChanceField.getText().replace(",", "").isEmpty()) { deathChanceField.setText("0");}
		if(deathVarField.getText().replace(",", "").isEmpty()) { deathVarField.setText("0");}
		if(deathTimedField.getText().replace(",", "").isEmpty()) { deathTimedField.setText("0");}
		if(varSpeedField.getText().replace(",", "").isEmpty()) { varSpeedField.setText("0.00");}
		if(varSpeedVarField.getText().replace(",", "").isEmpty()) { varSpeedVarField.setText("0");}
		if(changeChance.getText().replace(",", "").isEmpty()) { changeChance.setText("0");}
		if(changeVarience.getText().replace(",", "").isEmpty()) { changeVarience.setText("0");}
		if(noOfCells.getText().replace(",", "").isEmpty()) { noOfCells.setText("0");}
		if(wOne.getText().replace(",", "").isEmpty()) { wOne.setText("0");}
		if(wOneVarience.getText().replace(",", "").isEmpty()) {wOneVarience.setText("0");}
		if(wTwo.getText().replace(",", "").isEmpty()) { wTwo.setText("0");}
		if(wTwoVarience.getText().replace(",", "").isEmpty()) { wTwoVarience.setText("0");}
		if(wThree.getText().replace(",", "").isEmpty()) { wThree.setText("0");}
		if(wThreeVarience.getText().replace(",", "").isEmpty()) { wThreeVarience.setText("0");}
		if(wFour.getText().replace(",", "").isEmpty()) { wFour.setText("0");}
		if(wFourVarience.getText().replace(",", "").isEmpty()) { wFourVarience.setText("0");}
		if(wFive.getText().replace(",", "").isEmpty()) { wFive.setText("0");}
		if(wFiveVarience.getText().replace(",", "").isEmpty()) { wFiveVarience.setText("0");}
		if(wSix.getText().replace(",", "").isEmpty()) { wSix.setText("0");}
		if(wSixVarience.getText().replace(",", "").isEmpty()) { wSixVarience.setText("0");}
		if(wSeven.getText().replace(",", "").isEmpty()) { wSeven.setText("0");}
		if(wSevenVarience.getText().replace(",", "").isEmpty()) { wSevenVarience.setText("0");}
		if(wEight.getText().replace(",", "").isEmpty()) { wEight.setText("0");}
		if(wEightVarience.getText().replace(",", "").isEmpty()) { wEightVarience.setText("0");}
		if(cellSize.getText().replace(",", "").isEmpty()) { cellSize.setText("0");}
		if(cellSizeVarience.getText().replace(",", "").isEmpty()) { cellSizeVarience.setText("0");}
		if(antWeightField.getText().replace(",", "").isEmpty()) { antWeightField.setText("0");}
		if(antVarField.getText().replace(",", "").isEmpty()) { antVarField.setText("0");}
		
	}

	private void harvestValues() {
		//37
		//0-4 repCheck, repChance, repTaboo, repVarianceCheck, repVarience
		//5-10 deathCheck, deathChance, deathVarienceCheck, deathVarience, deathType, deathTimedNum
		//11-14 speedCheck, speedAmount, speedVarience, speedType
		//15-16 changeChance, changeVarience
		//17 noOfCells
		//18-19 wOne, oneVar,
		//20-21 wTwo, twoVar,
		//22-23 wThree, threeVar,
		//24-25 wFour, fourVar,
		//26-27 wFive, fiveVar,
		//28-29 wSix, sixVar,
		//30-31 wSeven, sevenVar,
		//32-33 wEight, eightVar,
		//34rand/rou
		//35-36 cell size and cell varience
		values[0] = ""+replication.isSelected();
		values[1] = repChanceField.getText().replace(",", "");
		values[2] = repTabooField.getText().replace(",", "");
		values[3] =  ""+repVar.isSelected();
		values[4] = repVarField.getText().replace(",", "");

		values[5] = ""+death.isSelected();
		values[6] = deathChanceField.getText().replace(",", "");
		values[7] = ""+deathVar.isSelected();
		values[8] = deathVarField.getText().replace(",", "");
		values[9] = corpseDisposal.getSelection().getActionCommand();
		values[10] = deathTimedField.getText().replace(",", "");

		values[11] = ""+variableSpeed.isSelected();
		values[12] = varSpeedField.getText().replace(",", "");
		values[13] = varSpeedVarField.getText().replace(",", "");
		values[14] = speedSpread.getSelection().getActionCommand();
		
		values[15] = changeChance.getText().replace(",", "");
		values[16] = changeVarience.getText().replace(",", "");
		values[17] = noOfCells.getText().replace(",", "");
		
		values[18] = wOne.getText().replace(",", "");
		values[19] = wOneVarience.getText().replace(",", "");
		
		values[20] = wTwo.getText().replace(",", "");
		values[21] = wTwoVarience.getText().replace(",", "");
		
		values[22] = wThree.getText().replace(",", "");
		values[23] = wThreeVarience.getText().replace(",", "");
		
		values[24] = wFour.getText().replace(",", "");
		values[25] = wFourVarience.getText().replace(",", "");
		
		values[26] = wFive.getText().replace(",", "");
		values[27] = wFiveVarience.getText().replace(",", "");
		
		values[28] = wSix.getText().replace(",", "");
		values[29] = wSixVarience.getText().replace(",", "");
		
		values[30] = wSeven.getText().replace(",", "");
		values[31] = wSevenVarience.getText().replace(",", "");
		
		values[32] = wEight.getText().replace(",", "");
		values[33] = wEightVarience.getText().replace(",", "");
		
		values[34] = type;
		
		values[35] = cellSize.getText().replace(",", "");
		values[36] = cellSizeVarience.getText().replace(",", "");
		
		values[37] = ""+antPath.isSelected();
		values[38] = antWeightField.getText().replace(",", "");
		values[39] = antVarField.getText().replace(",", "");
		
	}



	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}



	public int getPosition() {
		return position;
	}



	public void setPosition(int position) {
		this.position = position;
	}



	public boolean isDeletePoss() {
		return deletePoss;
	}



	public void setDeletePoss(boolean deletePoss) {
		this.deletePoss = deletePoss;

		delete.setEnabled(deletePoss);
	}
		
}
