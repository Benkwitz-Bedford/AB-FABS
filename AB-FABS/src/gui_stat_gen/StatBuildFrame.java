package gui_stat_gen;

import gui_main.GridPanel;
import gui_options.CellOptionsFrame;
import gui_options.EnvironmentalOptionsFrame;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;

import singleton_holders.SingletonHolder;
import singleton_holders.SingletonSanitisationFields;
import cycles.StatCycle;

public class StatBuildFrame extends JFrame implements ActionListener
{
	
	Boolean ca = false;
	
	NumberFormat doub = new DecimalFormat("#0.00"); 
	JLabel nameLabel = new JLabel("Run name;");
	JFormattedTextField nameField = new JFormattedTextField();
	JLabel numberLabel = new JLabel("Iterations;");
	JFormattedTextField numberField = new JFormattedTextField(NumberFormat.getIntegerInstance());
	JLabel intervalLabel = new JLabel("Data intervals;");
	JFormattedTextField intervalField = new JFormattedTextField(NumberFormat.getIntegerInstance());
	JLabel runsLabel = new JLabel("Runs;");
	JFormattedTextField runsField = new JFormattedTextField(NumberFormat.getIntegerInstance());
	
	JLabel sizeLabel = new JLabel("Jump Size;");
	JFormattedTextField sizeField = new JFormattedTextField(doub);
	
	JLabel perLabel = new JLabel("Jumps/interval;");
	JFormattedTextField perField = new JFormattedTextField(doub);

	JButton additionalButton = new JButton("environment");
	JButton optionsButton = new JButton("cell");
	JButton commitButton = new JButton("Start");
	JButton splitButton = new JButton("add split");
	
	EnvironmentalOptionsFrame additional = new EnvironmentalOptionsFrame(null);
	CellOptionsFrame options = new CellOptionsFrame("random", "live",null);
	
	JCheckBox bezBox = new JCheckBox("retain curves? ");
	JCheckBox shuffleBox = new JCheckBox("shuffle run? ");
	
	JLabel heatLabel = new JLabel("Heat map max;");
	JFormattedTextField heatField = new JFormattedTextField(NumberFormat.getNumberInstance());

	JFormattedTextField fPSField = new JFormattedTextField(NumberFormat.getNumberInstance());
	JLabel firstIntervalLabel = new JLabel("First data interval:");
	JFormattedTextField firstIntervalField = new JFormattedTextField(NumberFormat.getNumberInstance());

	Thread gridThread = null;
	Thread feedThread = null;
	
	ArrayList<String[]> settingsStack = new ArrayList<String[]>();
	ArrayList<Integer> triggerStack = new ArrayList<Integer>(); 
	ArrayList<StatRunSplitSettingsPanel> splitPanels = new ArrayList<StatRunSplitSettingsPanel>(); 

	RunningFeedbackFrame feed = new RunningFeedbackFrame();

	GridPanel stat = null;
	private JPanel mainList;
	private JScrollPane scroller;
	
	Double[][] dataEvalValues;
	String[][] variableVals;
	
	boolean fitting = false;
	
	public StatBuildFrame(Boolean CA)
	{
		ca = CA;
		statBuild();
		
	}
	
	public StatBuildFrame(Boolean CA, Double[][] values )
	{
		fitting = true;
		ca = CA;
		dataEvalValues = values;
		statBuild();
		
	}
	

	public StatBuildFrame(String[][] metrics, String[][] builders) {
		fitting = true;
		Double[][] holder =  new Double[metrics.length][];
		for(int i = 0; i < metrics.length;i++)
		{
			Double[] vals = new Double[metrics[i].length];
			for(int l = 0; l < metrics[i].length;l++)
			{
				vals[l] = Double.parseDouble(metrics[i][l]);
			}
			holder[i] = vals;
		}
		dataEvalValues = holder;
		variableVals = builders;
		statBuild();
	} 

	private void statBuild() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(1000, 170));
		Dimension buttonSize = new Dimension(100,20);
		Dimension fieldSize = new Dimension(100,20);
		Dimension textFieldSize = new Dimension(120,20);
		this.setLayout(new GridBagLayout());
		textFieldSize = fieldSize;
		
		//field for run name
		nameLabel.setPreferredSize(fieldSize);
		nameField.setPreferredSize(textFieldSize);
		
		nameField.setText("Run 1");
		nameField.setToolTipText("the name stat gen will use for files etc");
		
		GridBagConstraints con = new GridBagConstraints();
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(nameLabel, con);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(nameField, con);
		
		//field for run length
		numberLabel.setPreferredSize(fieldSize);
		numberField.setPreferredSize(textFieldSize);
		
		numberField.setText("250");
		numberField.setToolTipText("number of overall iterations performed");
		
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(numberLabel, con);
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(numberField, con);
		
		//field for snapshot interval
		runsLabel.setPreferredSize(fieldSize);
		runsField.setPreferredSize(textFieldSize);
		
		runsField.setText("10");
		runsField.setToolTipText("number of overall runs");
		
		con = new GridBagConstraints();
		con.gridx = 6;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(runsLabel, con);
		con = new GridBagConstraints();
		con.gridx = 7;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(runsField, con);
		
		//field for run number
		intervalLabel.setPreferredSize(fieldSize);
		intervalField.setPreferredSize(textFieldSize);
		
		intervalField.setText("50");
		intervalField.setToolTipText("frequency of stat gethering in intervals");
		
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(intervalLabel, con);
		con = new GridBagConstraints();
		con.gridx = 5;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(intervalField, con);
		
		//button for traj options
		optionsButton.setActionCommand("options");
		optionsButton.addActionListener(this);
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(optionsButton, con);
		
		//button for additional options
		additionalButton.setActionCommand("additional");
		additionalButton.addActionListener(this);
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(additionalButton, con);
		
		splitButton.setActionCommand("split");
		splitButton.addActionListener(this);
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(splitButton, con);
		
		shuffleBox.setPreferredSize(textFieldSize);
		shuffleBox.setToolTipText("shuffles data off to files, will slow data storage and retrieval but make the process infinite(or at least dependant on HDD");
		shuffleBox.setSelected(true);
		con = new GridBagConstraints();
		con.gridx = 5;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(shuffleBox, con);
		
		// commit and start button 
		commitButton.setActionCommand("commit");
		commitButton.addActionListener(this);
		con = new GridBagConstraints();
		con.gridx = 7;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(commitButton, con);
		
		
		//basic settings fields
		sizeLabel.setPreferredSize(fieldSize);
		sizeField.setPreferredSize(textFieldSize);
		
		sizeField.setText(""+SingletonHolder.getJumpSize());
		sizeField.setToolTipText("size of each jump (irrelevant if variant speed is set in add options)");
		
		perLabel.setPreferredSize(fieldSize);
		perField.setPreferredSize(textFieldSize);
		
		perField.setText(""+SingletonHolder.getJumpsPerIncrement());
		perField.setToolTipText("number of jumps per increment");
		
		bezBox.setPreferredSize(textFieldSize);
		
		bezBox.setToolTipText("the same set of bez curves be retained over runs");
		
		
		
		heatLabel.setPreferredSize(fieldSize);
		heatField.setPreferredSize(textFieldSize);
		
		heatField.setText("0");
		heatField.setToolTipText("the top value used for density heat maps");
		
		fPSField.setPreferredSize(textFieldSize);
		fPSField.setToolTipText("number of frames per second");
		fPSField.setValue(30);
		
		firstIntervalLabel.setPreferredSize(fieldSize);
		firstIntervalField.setPreferredSize(textFieldSize);
		firstIntervalField.setToolTipText("the first point data will be gathered at");
		firstIntervalField.setValue(1);
		
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(sizeLabel, con);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(sizeField, con);
		
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 2;
		con.gridwidth = 2;
		con.gridheight = 1;
		getContentPane().add(perLabel, con);
		
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(perField, con);
		
		con = new GridBagConstraints();
		bezBox.setPreferredSize(new Dimension(200,20));
		con.gridx = 5;
		con.gridy = 2;
		con.gridwidth = 2;
		con.gridheight = 1;
		getContentPane().add(bezBox, con);
		
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(heatLabel, con);
		
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(heatField, con);
		
		mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);
        
       
		
		
		con = new GridBagConstraints();
		bezBox.setPreferredSize(new Dimension(200,20));
		con.gridx = 4;
		con.gridy = 3;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(fPSField, con);
		
		con = new GridBagConstraints();
		bezBox.setPreferredSize(new Dimension(200,20));
		con.gridx = 5;
		con.gridy = 3;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(firstIntervalLabel, con);
		
		con = new GridBagConstraints();
		bezBox.setPreferredSize(new Dimension(200,20));
		con.gridx = 6;
		con.gridy = 3;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(firstIntervalField, con);
        
        con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 4;
		con.gridwidth = GridBagConstraints.REMAINDER;
		con.gridheight = GridBagConstraints.REMAINDER;
		scroller = new JScrollPane(mainList);
		scroller.setPreferredSize(new Dimension(900, 600));
		getContentPane().add(scroller, con);
		scroller.setVisible(false);
		
		//button for interval change and addition gubbins (auto expansion and saving of profiles)
		//will need a commit button and settings feedback field, might be worth a new way of doing options panels to give out a settings array rather than directly interact, better programming practice as well 
		//if you want to ensure continuity between changes dont modify traj type?.... should modifying traj type re-set in sci run? Probably not, re-program but if change in cell numbers trim randomly?
		
		this.pack();
		//setVisible(true);
	    this.setLocationRelativeTo(null);
		
	}

	public void run() 
	{
		this.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getActionCommand().equals("options"))
		{
			options.setVisible(true);
		}else
		{
			if(arg0.getActionCommand().equals("additional"))
			{
				additional.setVisible(true);
			}else
			{
				if(arg0.getActionCommand().equals("commit"))
				{
					this.emptyClean();
					this.sanitise();
					SingletonHolder.setJumpSize(Double.parseDouble(sizeField.getText().replace(",", "")));
					SingletonHolder.setJumpsPerIncrement(Double.parseDouble(perField.getText().replace(",", "")));
					SingletonHolder.setBezRetainer(bezBox.isSelected());
					SingletonHolder.setFileShuffleFlag(shuffleBox.isSelected());
					SingletonHolder.setHeatTopper(Integer.parseInt(heatField.getText().replace(",", "")));
					SingletonHolder.setIterations(Integer.parseInt(numberField.getText().replace(",", "")));
					if(splitPanels.size() == 0)
					{
						settingsStack.add(SingletonHolder.grabAllValues());
						triggerStack.add(0);
					}else
					{
						for(int i = 0; i < splitPanels.size();i++)
						{
							if(splitPanels.get(i).getActive())
							{
								splitPanels.get(i).triggerSet();
								settingsStack.add(splitPanels.get(i).getSettings());
								triggerStack.add(splitPanels.get(i).getTriggerValue());
							}
						}
					}
					
					feed.setPreferredSize(getPreferredSize());
					feed = new RunningFeedbackFrame();
					//run unique instance of grid panel and gen stats at specific intervals
					StatCycle statz = new StatCycle(nameField.getText(), Integer.parseInt(numberField.getText()),Integer.parseInt(intervalField.getText()), Integer.parseInt(runsField.getText()), settingsStack, triggerStack, ca, fitting,dataEvalValues,variableVals);
					if(SingletonHolder.getRunType().equals("statRecord"))
					{
						statz = new StatCycle("record", SingletonHolder.getIterations(), SingletonHolder.getIterations(), 1,settingsStack , triggerStack, false, false, new Double[0][0],new String[0][0]);
						SingletonHolder.setRunType("stat");
						statz.setRecording(true);
						int cellTotal = 0;
						SingletonHolder.setAllValues(settingsStack.get(0));
						String[][] sta = SingletonHolder.getCellSets();
						for(int i = 0; i < sta.length;i++)
						{
							cellTotal+= Integer.parseInt(sta[i][17]);
						}
						System.out.println("cell total: "+cellTotal);
						statz.setFinalCells(cellTotal);
					}
						
					statz.setFPS(Integer.parseInt(fPSField.getText().replace(",", "")));
					
					stat = new GridPanel(statz, ca);
					
				    gridThread = new Thread(stat, "grid");
				    feedThread = new Thread(feed, "feed");
				    feedThread.setPriority(Thread.NORM_PRIORITY);
					gridThread.setPriority(Thread.MAX_PRIORITY);
					gridThread.start();
					//stat.run();
					feedThread.start();
				}else
				{
					if(arg0.getActionCommand().equals("split"))
					{
						for(int i = 0; i < splitPanels.size();i++)
						{
							if(splitPanels.get(i).getActive() == false)
							{
								splitPanels.remove(i);
								mainList.remove(i);
								i--;
							}
						}
						StatRunSplitSettingsPanel sp = new StatRunSplitSettingsPanel();
						sp.setPosition(splitPanels.size());
						if(splitPanels.size() == 0)
						{
							StatRunSplitSettingsPanel sp2 = new StatRunSplitSettingsPanel();
							sp2.setImmoveableStart();
							this.setSize(new Dimension(1000,800));
							this.setPreferredSize(new Dimension(1000,800));

						    this.setLocationRelativeTo(null);
							scroller.setVisible(true);
							scroller.validate();
							GridBagConstraints gbc = new GridBagConstraints();
		                    gbc.gridwidth = GridBagConstraints.REMAINDER;
		                    gbc.weightx = 1;
		                    gbc.fill = GridBagConstraints.HORIZONTAL;
		                    mainList.add(sp2, gbc, splitPanels.size());
							splitPanels.add(sp2);
							gbc = new GridBagConstraints();
		                    gbc.gridwidth = GridBagConstraints.REMAINDER;
		                    gbc.weightx = 1;
		                    gbc.fill = GridBagConstraints.HORIZONTAL;
		                    mainList.add(sp, gbc, splitPanels.size());
						}else
						{
							GridBagConstraints gbc = new GridBagConstraints();
		                    gbc.gridwidth = GridBagConstraints.REMAINDER;
		                    gbc.weightx = 1;
		                    gbc.fill = GridBagConstraints.HORIZONTAL;
		                    mainList.add(sp, gbc, splitPanels.size());
		                    
		                    
						}
						splitPanels.add(sp);
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
		
	}
	
	private void sanitise()
	{
		numberField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinITERATIONS_DEAFULT(),SingletonSanitisationFields.getMaxITERATIONS_DEAFULT(), Integer.parseInt(numberField.getText().replace(",", ""))));
		intervalField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinIntervalSizeDeafult(),SingletonSanitisationFields.getMaxIntervalSizeDeafult(), Integer.parseInt(intervalField.getText().replace(",", ""))));
		runsField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinRunNumDeafult(),SingletonSanitisationFields.getMaxRunNumDeafult(), Integer.parseInt(runsField.getText().replace(",", ""))));
		sizeField.setText(""+SingletonSanitisationFields.sanitiseDouble(SingletonSanitisationFields.getMinJumpSize(),SingletonSanitisationFields.getMaxJumpSize(), Double.parseDouble(sizeField.getText().replace(",", ""))));
		perField.setText(""+SingletonSanitisationFields.sanitiseDouble(SingletonSanitisationFields.getMinJumpsPerIncrement(),SingletonSanitisationFields.getMaxJumpsPerIncrement(), Double.parseDouble(perField.getText().replace(",", ""))));
		heatField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinHeatDeafult(),SingletonSanitisationFields.getMaxHeatDeafult(), Integer.parseInt(heatField.getText().replace(",", ""))));
	}


	private void emptyClean() 
	{
		if(nameField.getText().isEmpty()) { nameField.setText("0");}
		if(numberField.getText().isEmpty()) { numberField.setText("0");}
		if(intervalField.getText().isEmpty()) { intervalField.setText("0");}
		if(runsField.getText().isEmpty()) { runsField.setText("0");}
		if(sizeField.getText().isEmpty()) { sizeField.setText("0");}
		if(perField.getText().isEmpty()) { perField.setText("0");}
		if(heatField.getText().isEmpty()) { heatField.setText("0");}
		
	}

	public void genReport() {
		// TODO Auto-generated method stub
		
	}

}
