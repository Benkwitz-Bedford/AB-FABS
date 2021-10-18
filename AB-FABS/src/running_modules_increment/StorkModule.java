package running_modules_increment;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import cell_data_holders.Cell;
import environment_data_holders.PositionGrid;
import singleton_holders.SingletonHolder;

public class StorkModule extends Module{

	public StorkModule()
	{
		modType = "stork";
		//System.out.println("caw");
	}
	
	public void genNext(Bundle sim)
	{
		//System.out.println("cawcaw");
		this.storkService(sim);
	}
	
	protected void storkService(Bundle sim) 
	{
		//System.out.println("stork hit");
		//if the death count is above 10k for example do the re-shuffle
		ArrayList<Integer[]> clearQueue = sim.getDeathMan().getClearQueue();
		ArrayList<Cell> storkWaiting = sim.getRepMan().getStorkWaiting();
		if(SingletonHolder.isDeath() && clearQueue.size() > 0)
		{
			System.out.println("hit cell size: "+sim.getCellMan().getStore().getCells().size());
			int removed = 0;
			//do while death q is full
			do
			{
				int removed2 = 0;
				//set deafult past possible bound to ensure removal of 0 isnt possible
				int foundPos = sim.getCellMan().getStore().getCells().size()+20;
				boolean found = false;
				//System.out.println("clearing "+clearQueue.get(0)[1]+" at" +clearQueue.get(0)[0]);
				//start at varience of no of removed up to the origional pos
				do
				{
					do
					{
						removed2++;
					}while(clearQueue.get(0)[0]-removed2 >= sim.getCellMan().getStore().getCells().size());
					if(clearQueue.get(0)[0]-removed2 < sim.getCellMan().getStore().getCells().size() && clearQueue.get(0)[0]-removed2 > 0)
					{
						Cell a = sim.getCellMan().getStore().getCell(clearQueue.get(0)[0]-removed2);
						int b = clearQueue.get(0)[1];
						if(a.getUnique() ==  b)
						{
							found = true;
							foundPos = clearQueue.get(0)[0]-removed2;
						}
					}
					removed2++;
				}while(removed2 <= removed && found == false);
				//find the cell and remove it
				if(found)
				{
					clearQueue.remove(0);
					Cell c = sim.getCellMan().getStore().getCells().remove(foundPos);
					sim.getMoveMan().getPositionGrid().getGCell(c.getPositionX(), c.getPositionY()).remove(c.getUnique());
				}else
				{
					int i = 0;
					do
					{
						//System.out.println("looping "+i );
						if(sim.getCellMan().getStore().getCell(i).getUnique() ==  clearQueue.get(0)[1])
						{
							found = true;
							clearQueue.remove(0);
							Cell c =sim.getCellMan().getStore().getCells().remove(i);

							sim.getMoveMan().getPositionGrid().getGCell(c.getPositionX(), c.getPositionY()).remove(c.getUnique());
						}
						i++;
					}while(found == false);
				}
				removed++;
			}while(clearQueue.size()> 0);
			
			//create new position grid and initialise the heatmap
			//cellSize might not be posSize but it should be
			sim.getMoveMan().genPosGrid(sim.getDimensions(),sim.getPosSize(),sim.getMoveMan().getPositionGrid().getHeat());
			sim.getCellMan().setShapes( new ArrayList<Ellipse2D>(),new ArrayList<Rectangle2D>());
			for(int i =0; i < sim.getCellMan().getStore().getCells().size(); i++)
			{
				addCircle(sim.getCellMan().getStore().getCells().get(i),i, sim);
			}
			//initialise the whole grid again for filter for the dead cells at and around their old positions
			for(int i = 0 ; i < sim.getBezMan().getCurvePoints().size();i++)
			{
				sim.getMoveMan().getPositionGrid().setCurve(sim.getBezMan().getCurvePoints().get(i), i, SingletonHolder.getSize(), SingletonHolder.getBezSize(),SingletonHolder.getBezChance() , SingletonHolder.isBezDist(), SingletonHolder.getBezVarience());
			}
			System.out.println("shift cell size: "+sim.getCellMan().getStore().getCells().size());
		}
		
		//add the new cells to the list and position grid
		ArrayList<Cell> cellStore = sim.getCellMan().getStore().getCells();
		for(int i = 0; i < storkWaiting.size();i++)
		{
			cellStore.add(new Cell(storkWaiting.get(i)));
			sim.getMoveMan().getPositionGrid().addCellPosition(storkWaiting.get(i), cellStore.size()-1);
		}
		sim.getCellMan().getStore().setCells(cellStore);
		for(int i =0; i < storkWaiting.size(); i++)
		{
			addCircle(storkWaiting.get(i),i, sim);
		}
		sim.getRepMan().setStorkWaiting(new ArrayList<Cell>());
		
	}
	public void addCircle(Cell c, int i, Bundle sim)
	{	
		sim.getCellMan().addShape(c);
		sim.getMoveMan().getPositionGrid().addCellPosition(c,i);
	}
}
