package gui_stat_gen;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import cycles.StatCycle;
import running_modules_increment.PopSiftModule;
import singleton_holders.SingletonHolder;

public class Top10Frame extends JFrame implements ActionListener{
	

	JLabel label = new JLabel();
	
	JScrollPane scroll = null;

	JComboBox<String> run = new JComboBox<String>();

	JComboBox<String> output = new JComboBox<String>();
	
	ArrayList<ArrayList<String>> topData;
	ArrayList<ArrayList<String>> topSettings;
	ArrayList<Double> scores;

	Dimension buttonSize = new Dimension(100,30);

	public Top10Frame(ArrayList<ArrayList<String>> topData, ArrayList<ArrayList<String>> topSettings,
			ArrayList<Double> scores) {
		this.topData = topData;
		this.topSettings = topSettings;
		this.scores = scores;
		run.setPreferredSize(buttonSize);
		output.setPreferredSize(buttonSize);
		output.addItem("gen extrap");
		output.setSize(buttonSize);
		run.setSize(buttonSize);
		for(int i = 0; i < scores.size();i++)
		{
			run.addItem("run score "+scores.get(i));
		}
		int x = 0;
		String sb = this.genData(x);

		label.setHorizontalAlignment(JLabel.LEFT);
		label.setVerticalAlignment(JLabel.TOP);
		label.setSize(new Dimension(1000,500));
		label.setPreferredSize(new Dimension(1000,10000));
		label.setText(sb);
		scroll = new JScrollPane(label);
		scroll.setPreferredSize(new Dimension(500,500));
		scroll.setSize(new Dimension(500,500));
		this.setSize(new Dimension(1000,1000));
	    this.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(run, con);
		
		con = new GridBagConstraints();
		con.gridx = 5;
		con.gridy = 0;
		con.fill = GridBagConstraints.BOTH;
		getContentPane().add(output, con);
		
		
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 1;
		con.fill = GridBagConstraints.BOTH;
		//con.gridwidth=10;
		con.gridheight = 5;
		getContentPane().add(scroll, con);
		
		run.setActionCommand("run");
		run.addActionListener(this);
		output.setActionCommand("output");
		output.addActionListener(this);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private String genData(int x) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><pre>");
		sb.append("<br> Data:");
		for(int y =0; y < topData.get(x).size();y++)
		{
			sb.append("<br>"+topData.get(x).get(y));
		}
		sb.append("<br>");
		sb.append("<br> Settings:");
		for(int y =0; y < topSettings.get(x).size();y++)
		{
			sb.append("<br>"+topSettings.get(x).get(y));
		}
		sb.append("</html></pre>");
		return sb.toString();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("run"))
		{
			String sb = genData(run.getSelectedIndex());

			label.setText(sb);
		}else
		{
			if(e.getActionCommand().equals("output"))
			{
				if(output.getSelectedIndex() == 0)
				{
					int point = run.getSelectedIndex();
					SingletonHolder.inputAllValues(this.split(topSettings.get(point)));
					//SingletonHolder.prepFromDetails(file);

					ArrayList<String[]> settingsStack = new ArrayList<String[]>();
					ArrayList<Integer> triggerStack = new ArrayList<Integer>(); 
					settingsStack.add(SingletonHolder.grabAllValues());
					triggerStack.add(0);
					StatCycle stat = new StatCycle((""+scores.get(point)), SingletonHolder.getIterations(), SingletonHolder.getIterations(), 1,settingsStack , triggerStack, false, false, new Double[0][0],new String[0][0]);
					stat.setRecording(true);
					String cells = topData.get(point).get(4).split(";")[1];
					stat.setFinalCells((int) Double.parseDouble(cells));
					stat.run();
				}
			}
		}
		
	}

	private String[] split(ArrayList<String> arry) {
		String[] returner = new String[arry.size()-1];
		int shift = 0;
		for(int i = 0; i < arry.size();i++)
		{
			if(arry.get(i).contains(":"))
			{
				String split = arry.get(i).split(": ")[1];
				returner[i-shift] = split;
			}else
			{
				shift++;
			}
		}
		return returner;
	}

	public void run() {
		this.setVisible(true);
		
	}

}
