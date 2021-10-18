package gui_stat_gen_definition;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cell_data_holders.Cell;
import cell_data_holders.CellDataPackage;

public class PrioriiPanel extends JPanel{

	private Boolean active = true;
	private int position = 0;
	private boolean deletePoss = true;
	
	JTextField lower = new JTextField();
	JLabel lowerLabel = new JLabel("lowerBound");
	JTextField upper = new JTextField();
	JLabel upperLabel = new JLabel("upperBound");
	
	JCheckBox invertBox = new JCheckBox("Invert");
	JLabel invertLabel = new JLabel("invert");
	
	JComboBox<String> options = new JComboBox<String>();
	
	public PrioriiPanel()
	{
		Dimension textFieldSize = new Dimension(100,20);		

		this.setLayout(new GridBagLayout());
		
		options.setPreferredSize(new Dimension(200,20));
		lower.setPreferredSize(textFieldSize);
		lowerLabel.setPreferredSize(textFieldSize);
		upper.setPreferredSize(textFieldSize);
		upperLabel.setPreferredSize(textFieldSize);
		
		lower.setToolTipText("the lower bound of the range for population selection");
		upper.setToolTipText("the upper bound of the range for population selection");
		invertBox.setToolTipText("if inverted the population will consist of things outside the range");
		
		CellDataPackage c = new CellDataPackage();
		ArrayList<String> values = c.getDataNames(); 
		for(int i = 0; i < values.size();i++)
		{
			options.addItem(values.get(i));
		}
		options.addItem("distance from start");
		options.addItem("Chosen Varience");
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(options, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(invertLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(invertBox, gbc);
		
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
		this.setPreferredSize(new Dimension(1000,40));
		this.validate();
	}
	public String[] getValues() {
		String[] vals = new String[4];
		vals[0] = ""+options.getSelectedIndex();
		if(invertBox.isSelected())
		{
			vals[1] = ""+1;
		}else
		{
			vals[1] = ""+0;
		}
		vals[2] = lower.getText().replace(",", "");
		vals[3] = upper.getText().replace(",", "");
								
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
