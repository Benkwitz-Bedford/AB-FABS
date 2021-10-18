package cycle_components;

import java.util.ArrayList;
import java.util.Random;

import cell_data_holders.Cell;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;

public class DeathManager {
	

   private ArrayList<int[]> deaths = new ArrayList<int[]>();
   private ArrayList<Integer> deathQueue = new ArrayList<Integer>();
   private ArrayList<Integer[]> clearQueue = new ArrayList<Integer[]>();

   public ArrayList<Cell> checkAll(ArrayList<Cell> cells) 
   {
	   Random randomiser = SingletonHolder.getMasterRandom();
	   for(int i = 0; i < cells.size(); i++)
	   {
		   if(cells.get(i).isLiving() && cells.get(i).getDeath()>0)
		   {
			   if(randomiser.nextInt(100001)<= cells.get(i).getDeath())
			   {
				   this.kill(i, cells);
			   }
		   }
	   }
	   return cells;
   }
	
	protected void kill(int i, ArrayList<Cell> cells) 
	{
		getDeaths().add(new int[]{cells.get(i).getUnique(), SingletonHolder.getIncrement()});
		SingletonStatStore.incrementDeaths();
		Cell cll = cells.get(i);
		if(cll.getDeathType().equals("remain"))
		{
			cll.setLiving(false); 
		}else
		{
			if(cll.getDeathType().equals("clear"))
			{
				cll.setLiving(false);
				cll.setCorporeal(false);
				/*boolean found = false;
				int l = 0;
				//awful way of doing it fix when needs must
				do
				{
					if(circles.get(l).getX() == cll.getPositionX() && circles.get(l).getY() == cll.getPositionY())
					{
						found = true;
						circles.get(l).setFrame(0, 0, 0, 0);
					}
					l++;
				}while (found == false);
				clearQueue.add(new Integer[]{i, cll.getUnique()});*/
			}else
			{
				if(cll.getDeathType().equals("timed"))
				{
					//System.out.println("set "+SingletonHolder.getIncrement());
					cells.get(i).setLiving(false);
					cells.get(i).setDeathTimer(SingletonHolder.getIncrement());
					getDeathQueue().add(i);
				}
			}
		}
	}
	
	public void checkDeathQueue(ArrayList<Cell> cells)
	{
		if(getDeathQueue().size() >0)
		{
			do
			{
				if((SingletonHolder.getIncrement()-cells.get(getDeathQueue().get(0)).getDeathTimer()) > SingletonHolder.getDeathTTR())
				{
					int removed = getDeathQueue().remove(0);
					Cell cll = cells.get(removed);
					cll.setCorporeal(false);
					getClearQueue().add(new Integer[]{removed,cll.getUnique()});
				}
			}while((SingletonHolder.getIncrement()-cells.get(getDeathQueue().get(0)).getDeathTimer()) > SingletonHolder.getDeathTTR());
		}
	}

	public ArrayList<int[]> getDeaths() {
		return deaths;
	}

	public void setDeaths(ArrayList<int[]> deaths) {
		this.deaths = deaths;
	}

	public ArrayList<Integer> getDeathQueue() {
		return deathQueue;
	}

	public void setDeathQueue(ArrayList<Integer> deathQueue) {
		this.deathQueue = deathQueue;
	}

	public ArrayList<Integer[]> getClearQueue() {
		return clearQueue;
	}

	public void setClearQueue(ArrayList<Integer[]> clearQueue) {
		this.clearQueue = clearQueue;
	}

}
