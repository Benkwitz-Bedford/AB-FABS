package cell_data_holders;

import java.util.ArrayList;

import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;

public class CellHandler 
{

	private int num = 0;
	private int time = 0;
	private int size = 0;
	private double[][] starts = null;
	private ArrayList<Cell> cells = new ArrayList<Cell>();
	private ArrayList<double[]> trajectoriesRecord = new ArrayList<double[]>();
	
	public CellHandler() { 
		// TODO Auto-generated constructor stub
	}

	public void giveTrajectories(ArrayList<double[]> changes) {
		trajectoriesRecord = changes;
		
	}

	public void setNum(int parseInt) {
		num = parseInt;
		//System.out.println("num set at "+num);
	}

	public void setTime(int parseInt) {
		time = parseInt;
		
	}

	public int getNum() {
		// TODO Auto-generated method stub
		return num;
	}

	public int getTime() {
		// TODO Auto-generated method stub
		return time;
	}

	public void giveStarts(double[][] cellStarts) 
	{
		cells  =  new ArrayList<Cell>();
		setStarts(cellStarts);
		if(cellStarts.length != num)
		{
			System.out.println("cell number missmatch: "+cellStarts.length+" != "+num);
		}
		
		for(int i = 0; i < cellStarts.length;i++)
		{
			getCells().set(i,new Cell(cellStarts[i][0],cellStarts[i][1],cellStarts[i][2],SingletonHolder.getRepChance(), SingletonHolder.getDeathChance(),SingletonHolder.getSpeed(),SingletonStatStore.getTotalCells(), SingletonHolder.getCellSize(), new long[]{0,0}, SingletonHolder.getChange()));
			SingletonStatStore.incTotalCells();
		}
		
	}

	public void setSize(int parseInt) {
		size = parseInt;
		
	}
	
	public int getSize()
	{
		return size;
	}

	public  ArrayList<double[]> getTrajectories() {
		// TODO Auto-generated method stub
		return trajectoriesRecord;
	}

	public Cell getCell(double d) {
		int e = (int) d;
		return getCells().get(e);
	}

	public ArrayList<Cell> getCells() {
		// TODO Auto-generated method stub
		return cells;
	}

	public double[][] getStarts() {
		return starts;
	}

	public void setStarts(double[][] starts) {
		this.starts = starts;
	}

	public void setCells(ArrayList<Cell> cells) {
		this.cells = cells;
	}

}
