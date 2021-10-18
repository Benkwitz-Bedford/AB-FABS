package gui_stat_gen;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import stat_data_holders.SnapShotObfust;

public class DataFeedbackPanel extends JLabel{

	

	public void fillOutSnapShuffle(ArrayList<String> data) 
	{
		String formattedMeta = "<html><pre>";
		StringBuilder sb = new StringBuilder();
		sb.append(formattedMeta);
		for(int i = 0; i < data.size(); i++)
		{
			sb.append("<br>"+data.get(i));
		}

		sb.append("</html></pre>");
		this.setHorizontalAlignment(JLabel.LEFT);
	    this.setVerticalAlignment(JLabel.TOP);
		this.setText(sb.toString());
	}

	public void fillOutMetaSnapShuffle(ArrayList<String> metaTable, ArrayList<String> tableTop) 
	{
		String formattedMeta = "<html><pre>";
		StringBuilder sb = new StringBuilder();
		sb.append(formattedMeta);
		for(int i = 0; i < metaTable.size(); i++)
		{
			sb.append("<br>"+metaTable.get(i));
		}
		sb.append("<br>"+"<br>");
		for(int i = 0; i < tableTop.size(); i++)
		{
			sb.append("<br>"+tableTop.get(i));
		}
		sb.append("</html></pre>");
		this.setHorizontalAlignment(JLabel.LEFT);
	    this.setVerticalAlignment(JLabel.TOP);
		this.setText(sb.toString());
	}

	public void fillOut(SnapShotObfust snapShotObfust,
			ArrayList<ArrayList<String>> colombs, boolean meta,
			ArrayList<ArrayList<String>> runHeads)
	{
		String formattedMeta = "<html><pre>";
		StringBuilder sb = new StringBuilder();
		sb.append(formattedMeta);
		ArrayList<String> tableLines = new ArrayList<String>();
		for(int i = 0; i < colombs.get(0).size(); i ++)
		{
			
			String s = "";
			StringBuilder sc = new StringBuilder();
			sc.append(s);
			for(int l = 0 ; l < colombs.size(); l++)
			{
				sc.append(colombs.get(l).get(i));
			}
			tableLines.add(sc.toString());
		}
		
		for(int i = 0; i < tableLines.size(); i++)
		{
			sb.append("<br>"+tableLines.get(i));
		}
		sb.append("<br>"+"<br>");
		if(meta)
		{
			tableLines = new ArrayList<String>();
			//System.out.println("runHeads "+runHeads.size()+"  "+runHeads.get(0).size() );
			for(int i = 0; i < runHeads.get(0).size(); i ++)
			{
				String s = "";
				StringBuilder sc = new StringBuilder();
				sc.append(s);
				for(int l = 0 ; l < runHeads.size(); l++)
				{
					//System.out.println("i "+i+" l "+l );
					sc.append(runHeads.get(l).get(i));
				}
				tableLines.add(sc.toString());
			}
			
			for(int i = 0; i < tableLines.size(); i++)
			{
				sb.append("<br>"+tableLines.get(i));
			}
			sb.append("<br>"+"<br>");
		}
		ArrayList<String> settings = snapShotObfust.getSettings();
		
		for(int i = 0; i < settings.size(); i++)
		{
			sb.append("<br>"+settings.get(i));
		}
		sb.append("</html></pre>");
		//System.out.println(formattedMeta);
		this.setHorizontalAlignment(JLabel.LEFT);
	    this.setVerticalAlignment(JLabel.TOP);
		this.setText(sb.toString());
		
	}

}
