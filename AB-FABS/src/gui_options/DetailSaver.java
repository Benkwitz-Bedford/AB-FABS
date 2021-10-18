package gui_options;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import singleton_holders.SingletonHolder;

@SuppressWarnings("serial")
public class DetailSaver extends JFrame implements ActionListener
{
	
	JTextField name = new JTextField();
	JTextField email = new JTextField();

	JButton button = new JButton("ok");
	
	public DetailSaver()
	{
		JLabel lFN = new JLabel("Name: ");
		JLabel lI = new JLabel("E-mail: ");
		name.setText("nom de plume");
		email.setText("someone@something.somewhere");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setPreferredSize(new Dimension(300, 150));
		Dimension buttonSize = new Dimension(50,20);
		Dimension fieldSize = new Dimension(200,20);
		Dimension textFieldSize = new Dimension(50,20);
		button.setPreferredSize(buttonSize);
		this.setLayout(new GridBagLayout());
		
		name.setPreferredSize(fieldSize);
		email.setPreferredSize(fieldSize);
		
		lFN.setPreferredSize(textFieldSize);
		lI.setPreferredSize(textFieldSize);
		
		GridBagConstraints con = new GridBagConstraints();
		//iterations
		name.setToolTipText("usually a first and second name but any unique identifier will do");
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		getContentPane().add(lFN, con);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		getContentPane().add(name, con);
		
		//chance of change per iteration
		email.setToolTipText("where we can contact you");
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		getContentPane().add(lI, con);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		//con.fill = GridBagConstraints.BOTH;
		getContentPane().add(email, con);
		
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 2;
		con.gridwidth = 2;
		con.gridheight = 1;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(button, con);
		

		button.setActionCommand("ok");
		button.addActionListener(this);
		this.pack();
		//setVisible(true);
	    this.setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getActionCommand().equals("ok"))
		{
			this.offLoad();
			this.setVisible(false);
		}
		
	}

	public void start()
	{
		this.setVisible(true);
	}
	
	private void offLoad() {
		File file2 = new File(name.getText()+".txt");
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println("Name: "+name.getText());
			writer.println("Email: "+email.getText());
			ArrayList<String> values = SingletonHolder.generateAllValues();
			for(int i = 0 ; i < values.size();i++)
			{
				writer.println(values.get(i));
			}
			
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File file3 = new File(name.getText()+" details.txt");
		try {
			PrintWriter writer = new PrintWriter(file3);
			String[] values = SingletonHolder.grabAllValues();
			writer.println(SingletonHolder.getMasterSeed());
			for(int i = 0 ; i < values.length;i++)
			{
				writer.println(values[i]);
			}
			
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
