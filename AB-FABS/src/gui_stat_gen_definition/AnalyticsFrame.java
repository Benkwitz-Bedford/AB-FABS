package gui_stat_gen_definition;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;

import cycles.Cycle;
import cycles.LiveCycle;
import running_modules_increment.DataModule;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonSanitisationFields;

public class AnalyticsFrame extends JFrame implements ActionListener{
	
	NumberFormat doub = new DecimalFormat("#0.00"); 
	JLabel nameLabel = new JLabel("Run name;");
	JFormattedTextField nameField = new JFormattedTextField();
	JLabel numberLabel = new JLabel("Iterations;");
	JFormattedTextField numberField = new JFormattedTextField(NumberFormat.getIntegerInstance());
	JLabel intervalLabel = new JLabel("Data intervals;");
	JFormattedTextField intervalField = new JFormattedTextField(NumberFormat.getIntegerInstance());
	JLabel startLabel = new JLabel("Starts;");
	JFormattedTextField startField = new JFormattedTextField(NumberFormat.getIntegerInstance());
	JButton commitButton = new JButton("Start");
	
	private Cycle cycle = null;
	
	public AnalyticsFrame()
	{
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
		startLabel.setPreferredSize(fieldSize);
		startField.setPreferredSize(textFieldSize);
		
		startField.setText("10");
		startField.setToolTipText("number of overall runs");
		
		con = new GridBagConstraints();
		con.gridx = 6;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(startLabel, con);
		con = new GridBagConstraints();
		con.gridx = 7;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(startField, con);
		
		intervalField.setText("50");

		intervalLabel.setPreferredSize(fieldSize);
		intervalField.setPreferredSize(textFieldSize);
		
		con = new GridBagConstraints();
		con.gridx = 8;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(intervalLabel, con);
		con = new GridBagConstraints();
		con.gridx = 9;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(intervalField, con);

		
		// commit and start button 
		commitButton.setActionCommand("commit");
		commitButton.addActionListener(this);
		con = new GridBagConstraints();
		con.gridx = 7;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(commitButton, con);
		
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.pack();
	    this.setLocationRelativeTo(null);
	    this.setVisible(false);
}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("commit"))
		{
			this.emptyClean();
			this.sanitise();
			if(startField.getText().equals("0"))
			{
				startField.setText("1");
			}
			if(numberField.getText().equals(startField.getText()))
			{
				int i = Integer.parseInt(startField.getText());
				i--;
				startField.setText(""+i);
			}
			
			if(cycle.getType().equals("replay"))
			{
				
			}else
			{
				SingletonHolder.setIterations((int) Double.parseDouble(numberField.getText().replace(",", "")));
			}
			
			DataModule dat = new DataModule((int) Double.parseDouble(intervalField.getText().replace(",", "")),(int) Double.parseDouble(startField.getText().replace(",", "")),nameField.getText(),getCycle().getSetSize(),getCycle().getNames(),false,new Double[0][0]);
			
			
			getCycle().loadDataModule(dat);
			//set a flag for request
			getCycle().setDataFlag(true);
			//turn flag off when process started
			//if start interval < current reset
			//if end interval > total possible set it to total
			
		}
	}
	
	private void sanitise()
	{
		numberField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinITERATIONS_DEAFULT(),SingletonSanitisationFields.getMaxITERATIONS_DEAFULT(), Integer.parseInt(numberField.getText().replace(",", ""))));
		intervalField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinIntervalSizeDeafult(),SingletonSanitisationFields.getMaxIntervalSizeDeafult(), Integer.parseInt(intervalField.getText().replace(",", ""))));
		startField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinIntervalSizeDeafult(),SingletonSanitisationFields.getMaxIntervalSizeDeafult(), Integer.parseInt(startField.getText().replace(",", ""))));}


	private void emptyClean() 
	{
		if(nameField.getText().isEmpty()) { nameField.setText("0");}
		if(numberField.getText().isEmpty()) { numberField.setText("0");}
		if(intervalField.getText().isEmpty()) { intervalField.setText("0");}
		if(startField.getText().isEmpty()) { startField.setText("0");}
		
	}

	public Cycle getCycle() {
		return cycle;
	}

	public void setCycle(Cycle cycle2) {
		this.cycle = cycle2;
	}
		
	}
