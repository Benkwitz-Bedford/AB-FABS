package cycle_components;

import java.util.ArrayList;
import java.util.Random;

import cell_data_holders.Cell;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;

public class RepManager {
	
	
	
	//data
	private ArrayList<int[]> births = new ArrayList<int[]>();
	private ArrayList<Cell> storkWaiting = new ArrayList<Cell>();

	public ArrayList<Cell> replicateAll(ArrayList<Cell> cells, MoveManager moveMan) {
		Random randomiser = SingletonHolder.getMasterRandom();
		for(int i = 0; i < cells.size(); i++)
		{
			Cell c = cells.get(i);
			if(c.isLiving() && c.getRepChance()>0 )
			{
				if(cells.get(i).getRepTabooTimer() <=0)
				{	
					if(randomiser.nextInt(100001)<= c.getRepChance())
					{
						this.replication(i, cells, moveMan);
					}
				}else
				{
					cells.get(i).decTabooSize();
				}
			}
		}
		return cells;
	}

	protected void replication(int i, ArrayList<Cell> cells, MoveManager moveMan)  
	{
		//System.out.println("rep");
		getBirths().add(new int[]{SingletonStatStore.getTotalCells(), SingletonHolder.getIncrement()});
		SingletonStatStore.incrementBirths();
		Cell cell = cells.get(i);
		int pheno = cell.getPheno();
		double vect = StaticCalculations.getOppositeAngle(cell.getVect());
		double x =cell.getPositionX();
		double y = cell.getPositionY();
		//Random rand = SingletonHolder.getMasterRandom();
		//int l = rand.nextInt(SingletonHolder.getCellSets().length);
		Cell c = new Cell(SingletonHolder.getSize(),SingletonHolder.genCellValuesFromSet(SingletonHolder.getCellSets()[pheno]), pheno, cells.size()+getStorkWaiting().size());
		c.setVect(vect, moveMan.getPositionGrid());
		c.setPositionX(x);
		c.setPositionY(y);
		SingletonStatStore.incTotalCells();
		double[] xy = moveMan.projectNext(c, SingletonHolder.getCellLength(),SingletonHolder.getSize());
		c.setPositionX(xy[0]);
		c.setPositionY(xy[1]);
		c.incrementReplications();
		cell.resetRepTaboo();
		getStorkWaiting().add(c);
	}

	public ArrayList<int[]> getBirths() {
		return births;
	}

	public void setBirths(ArrayList<int[]> births) {
		this.births = births;
	}

	public ArrayList<Cell> getStorkWaiting() {
		return storkWaiting;
	}

	public void setStorkWaiting(ArrayList<Cell> storkWaiting) {
		this.storkWaiting = storkWaiting;
	}
	
}
