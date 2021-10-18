package gui_stat_gen;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import singleton_holders.SingletonHolder;

public class StatRunSplitSettingsPanel extends JPanel implements ActionListener{
	private String[] settings = null;
	private ArrayList<String> valueSet = new ArrayList<String>(); 
	
	JLabel values =  new JLabel();
	JLabel triggerLabel = new JLabel("Trigger point: ");
	JTextField triggerField = new JTextField();
	JButton delete = new JButton("delete");
	JButton commit = new JButton("set current");
	
	private int position = 0;
	private int triggerValue = 0;
	
	private Boolean active = true;
		
	public StatRunSplitSettingsPanel()
	{
		triggerField.setText("0");
		triggerField.setToolTipText("set the iteration iut will be triggered on");
		
		delete.setActionCommand("delete");
		commit.setActionCommand("commit");
		
		delete.addActionListener(this);
		commit.addActionListener(this);
		
		this.setPreferredSize(new Dimension(800, 300));
		this.setSize(new Dimension(800, 300));
		Dimension fieldSize = new Dimension(100,20);
		this.setLayout(new GridBagLayout());
		
		//values.setPreferredSize(new Dimension(500, 700));
		
		triggerLabel.setPreferredSize(fieldSize);
		triggerField.setPreferredSize(fieldSize);
		delete.setPreferredSize(fieldSize);
		commit.setPreferredSize(fieldSize);
		
		GridBagConstraints con = new GridBagConstraints();
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		//con.fill = GridBagConstraints.BOTH;
		con.gridwidth = GridBagConstraints.REMAINDER;
		this.add(values, con);
		
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(triggerLabel, con);
		
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(triggerField, con);
		
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(delete, con);
		
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		this.add(commit, con);
		
		this.setVisible(true);
	}
	
	

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("commit"))
		{
			this.commitValues();
			
		}else
		{
			if(arg0.getActionCommand().equals("delete"))
			{
				this.setVisible(false);
				setActive(false);
			}
		}
		
	}

	private void commitValues() {
		settings = SingletonHolder.grabAllValues();
		valueSet = SingletonHolder.generateAllValues();
		String formattedMeta = "<html><pre>";
		for(int i = 0; i < valueSet.size(); i++)
		{
			formattedMeta = formattedMeta +"<br>"+valueSet.get(i);
		}
		formattedMeta = formattedMeta +"</html></pre>";
		//System.out.println(formattedMeta);
		values.setHorizontalAlignment(JLabel.LEFT);
		values.setVerticalAlignment(JLabel.TOP);
		values.setText(formattedMeta);
		this.setPreferredSize(new Dimension(800,1000));
		this.setSize(new Dimension(800,1000));
		this.triggerSet();
	}



	public void triggerSet() {
		triggerValue = Integer.parseInt(triggerField.getText());
		
	}



	public void setImmoveableStart() {
		//this.commitValues();
		triggerField.setEnabled(false);
		//commit.setEnabled(false);
		delete.setEnabled(false);
		
	}

	public ArrayList<String> getValueSet() {
		return valueSet;
	}



	public void setValueSet(ArrayList<String> valueSet) {
		this.valueSet = valueSet;
	}



	public String[] getSettings() {
		return settings;
	}



	public void setSettings(String[] settings) {
		this.settings = settings;
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



	public int getTriggerValue() {
		return triggerValue;
	}



	public void setTriggerValue(int triggerValue) {
		this.triggerValue = triggerValue;
	}



	
	

}
