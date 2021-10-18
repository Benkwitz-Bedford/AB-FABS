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
import stat_data_holders.SnapShotObfust;

public class MetricSelectionPanel extends JPanel{

	private Boolean active = true;
	private int position = 0;
	private boolean deletePoss = true;
	
	JTextField lower = new JTextField();
	JLabel lowerLabel = new JLabel("weight");
	
	JComboBox<String> options = new JComboBox<String>();
	private SnapShotObfust snap;
	
	public MetricSelectionPanel(SnapShotObfust snap)
	{
		Dimension textFieldSize = new Dimension(100,20);		
		this.snap = snap;
		this.setLayout(new GridBagLayout());
		
		options.setPreferredSize(new Dimension(200,20));
		lower.setPreferredSize(textFieldSize);
		lowerLabel.setPreferredSize(textFieldSize);
		
		lower.setToolTipText("the lower bound of the range for population selection");
		
		
		ArrayList<String> values = snap.getValueNames(); 
		ArrayList<Double> valValues = snap.getValues();
		for(int i = 0; i < values.size();i++)
		{
			String str = values.get(i);
			if(valValues.size() > 0)
			{
				str+=":"+valValues.get(i);
			}
			options.addItem(str);
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
		this.add(lowerLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		// con.fill = GridBagConstraints.BOTH;
		this.add(lower, gbc);
		
		this.setPreferredSize(new Dimension(1000,40));
		this.validate();
	}
	public String[] getValues() {
		String[] vals = new String[3];
		vals[0] = ""+options.getSelectedIndex();
		vals[1] = ""+snap.getValues().get(options.getSelectedIndex());
		vals[2] = lower.getText().replace(",", "");
								
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
