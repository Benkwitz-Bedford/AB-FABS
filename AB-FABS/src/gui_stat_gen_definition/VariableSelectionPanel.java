package gui_stat_gen_definition;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cell_data_holders.CellDataPackage;
import singleton_holders.SingletonHolder;

public class VariableSelectionPanel extends JPanel{

	private Boolean active = true;
	private int position = 0;
	private boolean deletePoss = true;
	
	JTextField lower = new JTextField();
	JLabel lowerLabel = new JLabel("lower Bound");
	JTextField upper = new JTextField();
	JLabel upperLabel = new JLabel("upper Bound");
	JTextField decimal = new JTextField();
	JLabel decimalLabel = new JLabel("decimal places");
	int cell = 0;
	
	JComboBox<String> options = new JComboBox<String>();
	
	public VariableSelectionPanel(Boolean cell)
	{
		construct();
		
		if(cell == false)
		{
			addOptionsCell();
		}else
		{
			addOptionsEnviron();
			this.cell = 1;
		}
		
	}
	private void construct() {
		Dimension textFieldSize = new Dimension(100,20);		

		this.setLayout(new GridBagLayout());
		
		options.setPreferredSize(new Dimension(200,20));
		lower.setPreferredSize(textFieldSize);
		lowerLabel.setPreferredSize(textFieldSize);
		upper.setPreferredSize(textFieldSize);
		upperLabel.setPreferredSize(textFieldSize);
		decimal.setPreferredSize(textFieldSize);
		decimalLabel.setPreferredSize(textFieldSize);
		
		lower.setToolTipText("the lower bound of the range for population selection");
		upper.setToolTipText("the upper bound of the range for population selection");
		decimal.setToolTipText("number of decimal places randomising in");
		
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(options, gbc);
		
		
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(lowerLabel, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(lower, gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(upperLabel, gbc);
		
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(upper, gbc);
		
		gbc.gridx = 7;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(decimalLabel, gbc);
		
		gbc.gridx = 8;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(decimal, gbc);
		
		this.setPreferredSize(new Dimension(1000,40));
		this.validate();
		
	}
	private void addOptionsCell() {
		//System.out.println("wah");
		String[] values = SingletonHolder.createCellSetNamesFromSettings();  
		for(int i = 0; i < values.length;i++)
		{
			options.addItem(values[i]);
		}
		
	}
	
	public void addOptionsEnviron()
	{
		//System.out.println("environ");
		String[] values = SingletonHolder.grabAllValueNames();  
		for(int i = 0; i < values.length;i++)
		{
			options.addItem(values[i]);
		}
	}
	
	public String[] getValues() {
		String[] vals = new String[5];
		vals[0] = ""+cell;
		vals[1] = ""+options.getSelectedIndex();
		vals[2] = lower.getText().replace(",", "");
		vals[3] = upper.getText().replace(",", "");
		vals[4] = decimal.getText().replace(",", "");
								
		return vals;
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
	}

}

