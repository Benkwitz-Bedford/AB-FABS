package cycle_components;

import java.util.ArrayList;
import java.util.Random;

import cell_data_holders.Cell;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import trajectory_components.RandomTrajMaker;
import trajectory_components.RandomTrajMakerCA;
import trajectory_components.RouletteTrajMaker;
import trajectory_components.RouletteTrajMakerCA;

public class TrajectoryManagerCA extends TrajectoryManager {
	   
	private RandomTrajMakerCA ranMaker = new RandomTrajMakerCA(SingletonHolder.getDeafultValueSet());
	private RouletteTrajMakerCA rouMaker = new RouletteTrajMakerCA(SingletonHolder.getDeafultValueSet());
	
	public ArrayList<Cell> genNextTragForAll(ArrayList<Cell> cells, Posi posi) 
	{
		Random randomiser = SingletonHolder.getMasterRandom();
		for(int i = 0; i < cells.size(); i++)
		{
			if(cells.get(i).isLiving())
			{
				if(randomiser.nextInt(100001)<= cells.get(i).getTurnChance())
				{
					if(SingletonHolder.isBezFlag() && cells.get(i).isFollowingCurve())
					{
						//System.out.println(SingletonHolder.isBezFlag());
					}else
					{
						this.genNextTraj(cells.get(i), posi);
					}
				}
			}
		}
		return cells;
	}
	
	public ArrayList<Cell> genNextAntTragForAll(ArrayList<Cell> cells, PositionGrid posi) {
		Random randomiser = SingletonHolder.getMasterRandom();
		for(int i = 0; i < cells.size(); i++)
		{
			if(cells.get(i).isLiving())
			{
				if(randomiser.nextInt(100001)<= cells.get(i).getTurnChance())
				{
					if(SingletonHolder.isBezFlag() && cells.get(i).isFollowingCurve())
					{
						//System.out.println(SingletonHolder.isBezFlag());
					}else
					{
						this.genNextAntTraj(cells.get(i), posi);
					}
				}
			}
		}
		return cells;
	}
	
	public void genNextTraj(Cell cell,Posi posi) 
	{
		if(cell.getTrajType().equals("random"))
		{
			ranMaker.genNext(cell, posi);
		}else
		{
			if(cell.getTrajType().equals("roulette"))
			{
				rouMaker.genNext(cell, posi);
			}
		}
		cell.incrementTurns();

		cell.getPack().addTotalTurns(1.00);
		//SingletonStatStore.pollTurnType(cell,posi);
		int[] p = rouMaker.getPicked();
		//slight bias here not worth alooot of extra effort right now
		/*if(p[0] != SingletonHolder.getChosenSectOne() ||p[1] != SingletonHolder.getChosenSectTwo() ||p[2] != SingletonHolder.getChosenSectThree() ||
				p[3] != SingletonHolder.getChosenSectFour() ||p[4] != SingletonHolder.getChosenSectFive() ||p[5] != SingletonHolder.getChosenSectSix() ||
				p[6] != SingletonHolder.getChosenSectSeven() ||p[7] != SingletonHolder.getChosenSectEight() )
		{
			System.out.println("mismatch "+cell.getPrevVect()+" "+cell.getVect());
			System.exit(0);
		}*/
	}
	public void genNextAntTraj(Cell cell,  PositionGrid posi) 
	{
		if(cell.getTrajType().equals("random"))
		{
			//System.out.println("oof1");
			ranMaker.genNext(cell, posi);
		}else
		{
			if(cell.getTrajType().equals("roulette"))
			{
				//System.out.println("oof2");
				rouMaker.genNextAntWeighted(cell,posi);
			}
		}
		cell.incrementTurns();

		cell.getPack().addTotalTurns(1.00);
		//SingletonStatStore.pollTurnType(cell,posi);
		int[] p = rouMaker.getPicked();
		//slight bias here not worth alooot of extra effort right now
		/*if(p[0] != SingletonHolder.getChosenSectOne() ||p[1] != SingletonHolder.getChosenSectTwo() ||p[2] != SingletonHolder.getChosenSectThree() ||
				p[3] != SingletonHolder.getChosenSectFour() ||p[4] != SingletonHolder.getChosenSectFive() ||p[5] != SingletonHolder.getChosenSectSix() ||
				p[6] != SingletonHolder.getChosenSectSeven() ||p[7] != SingletonHolder.getChosenSectEight() )
		{
			System.out.println("mismatch "+cell.getPrevVect()+" "+cell.getVect());
			System.exit(0);
		}*/
	}
	
	
	

}
