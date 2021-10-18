package file_manipulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import cell_data_holders.CellHandler;
import cell_data_holders.Trajectories;

public class TrajFileInterpreter {

	CellHandler cells = new CellHandler();
	
	public TrajFileInterpreter(File file)
	{
		String first = null;
		String second = null;
		String third = null;
		Scanner read;
		try {
			read = new Scanner (file);
		
		first = read.nextLine();
		second = read.nextLine();
		third = read.nextLine();
		read.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("loading failed");
			System.exit(0);
			e.printStackTrace();
		}
		String[] firstBreak = first.split(" ");
		String[] cellSplit = firstBreak[0].split(":");
		String[] timeSplit = firstBreak[1].split(":");
		String[] sizeSplit = firstBreak[2].split(":");
		cells.setNum(Integer.parseInt(cellSplit[1]));
		cells.setTime(Integer.parseInt(timeSplit[1]));
		cells.setSize(Integer.parseInt(sizeSplit[1]));
		
		String[] secondBreak = second.split(":");
		String[] breakOrigin = secondBreak[1].split(",");
		double[][] cellStarts = new double[cells.getNum()][3];
		for(int i = 1; i < cellStarts.length;i++)
		{
			System.out.println(breakOrigin[i]);
			String[] startBreakOne = breakOrigin[i].split("#");
			String[] breakOneBreak = startBreakOne[1].split(" ");
			//System.out.println(secondBreak[0]);
			//System.out.println(secondBreak[1]);
			//System.out.println(breakOrigin[0]);
			//System.out.println(breakOrigin[1]);
			//System.out.println(startBreakOne[0]);
			//System.out.println(startBreakOne[1]);
			//System.out.println(breakOneBreak[0]);
			//System.out.println(breakOneBreak[1]);
			cellStarts[i] = new double[]{Double.parseDouble(startBreakOne[0]),Double.parseDouble(breakOneBreak[0]),Double.parseDouble(breakOneBreak[1])};
		}
		cells.giveStarts(cellStarts);
		secondBreak = third.split(":");
		breakOrigin = secondBreak[1].split("n");
		ArrayList<double[]> changes = new ArrayList<double[]>();
		ArrayList<double[]> rawChanges= new ArrayList<double[]>();
		for(int i = 1; i < breakOrigin.length;i++)
		{
			String[] startBreakOne = breakOrigin[i].split(",");
			rawChanges.add(new double[]{Double.parseDouble(startBreakOne[0]),Double.parseDouble(startBreakOne[1]),Double.parseDouble(startBreakOne[2])});
		}
		/*//very bad implementation but easy to understand
		for(int i = 0; i < cells.getTime();i++)
		{
			ArrayList<double[]> raws = new ArrayList<double[]>();
			ArrayList<Integer> removal = new ArrayList<Integer>();
			for(int l = 0; l < rawChanges.size();l++)
			{
				double[] a = rawChanges.get(l);
				if(a[0] == i)
				{
					raws.add(a);
					removal.add(l);
				}
			}
			if(raws.size() > 0)
			{
				changes.add(raws);
			}
			for(int l = removal.size()-1; l > 0; l--)
			{
				int m = removal.get(l);
				rawChanges.remove(m);
			}
		}*/
		changes = rawChanges;
		//sorted by time increment its an undetermined number of increments with an undetermined number of records per increment listing time, cell, vector
		cells.giveTrajectories(changes);
	}
	 
	

	public Trajectories getTrajectories() {
		// TODO Auto-generated method stub
		return null;
	}



	public CellHandler getCellHandler() {
		// TODO Auto-generated method stub
		return cells;
	}

}
