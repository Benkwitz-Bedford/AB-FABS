package stat_data_holders;

import java.util.ArrayList;

public class AggregateSnapShotObfust extends SnapShotObfust{
	
	private ArrayList<String> averageNames = new ArrayList<String>();
	private ArrayList<Double> average = new ArrayList<Double>();
	
	private ArrayList<String> totalNames = new ArrayList<String>();
	private ArrayList<Double> total = new ArrayList<Double>();
	
	private ArrayList<String> sDNames = new ArrayList<String>();
	private ArrayList<Double> sD = new ArrayList<Double>();
	
	public ArrayList<String> getFullDataReport() {
		ArrayList<String> report = new ArrayList<String>();
		//report.addAll(this.getDataReport());
		for(int i = 0; i < average.size();i++)
		{
			report.add(averageNames.get(i)+";"+average.get(i));
			report.add(totalNames.get(i)+";"+total.get(i));
			report.add(sDNames.get(i)+";"+sD.get(i));
		}
		return report;
	}
	
	public ArrayList<String> getAverageNames() {
		return averageNames;
	}
	public void setAverageNames(ArrayList<String> averageNames) {
		this.averageNames = averageNames;
	}
	public ArrayList<String> getTotalNames() {
		return totalNames;
	}
	public void setTotalNames(ArrayList<String> totalNames) {
		this.totalNames = totalNames;
	}
	public ArrayList<String> getsDNames() {
		return sDNames;
	}
	public void setsDNames(ArrayList<String> sDNames) {
		this.sDNames = sDNames;
	}
	public ArrayList<Double> getAverage() {
		return average;
	}
	public void setAverage(ArrayList<Double> average) {
		this.average = average;
	}
	public ArrayList<Double> getTotal() {
		return total;
	}
	public void setTotal(ArrayList<Double> total) {
		this.total = total;
	}
	public ArrayList<Double> getsD() {
		return sD;
	}
	public void setsD(ArrayList<Double> sD) {
		this.sD = sD;
	}
	

}
