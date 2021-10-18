package cycle_components;

import java.util.ArrayList;
import java.util.Random;

import cell_data_holders.Cell;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;

public class RepManagerCA extends RepManager{
	
	
	

	

	protected void replication(int i, ArrayList<Cell> cells, MoveManagerCA moveMan)  
	{
		//System.out.println("rep");
		getBirths().add(new int[]{SingletonStatStore.getTotalCells(), SingletonHolder.getIncrement()});
		SingletonStatStore.incrementBirths();
		Cell cell = cells.get(i);
		int pheno = cell.getPheno();
		double vect = moveMan.genOpposite(cell.getVect());
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

	
}
