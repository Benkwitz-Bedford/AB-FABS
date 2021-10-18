package cycle_components;

import java.util.ArrayList;
import java.util.Random;

import cell_data_holders.Cell;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import trajectory_components.RandomTrajMaker;
import trajectory_components.RouletteTrajMaker;

public class TrajectoryManager {
	   
	private RandomTrajMaker ranMaker = new RandomTrajMaker(SingletonHolder.getDeafultValueSet());
	private RouletteTrajMaker rouMaker = new RouletteTrajMaker(SingletonHolder.getDeafultValueSet());

	public ArrayList<Cell> genNextTragForAll(ArrayList<Cell> cells,Posi posi) 
	{
		Random randomiser = SingletonHolder.getMasterRandom();
		for(int i = 0; i < cells.size(); i++)
		{
			Cell c =cells.get(i);
			if(c.isLiving() && SingletonHolder.getJumpCounter()-c.getLastBounce() > SingletonHolder.getBezSize()/c.getSpeed()+2)
			{
				if(randomiser.nextInt(100001)<= c.getTurnChance())
				{
					if(SingletonHolder.isBezFlag() && c.isFollowingCurve())
					{
						//System.out.println(SingletonHolder.isBezFlag());
					}else
					{
						this.genNextTraj(c,posi);
						//System.out.println("vec "+c.getVect() );
					}
				}
			}
		}
		return cells;
	}
	
	public ArrayList<Cell> genNextAntTragForAll(ArrayList<Cell> cells, Posi posi) {
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
	
	public void genNextTraj(Cell cell, Posi posi) 
	{
		if(cell.getTrajType().equals("random"))
		{
			getRanMaker().genNext(cell,posi);
		}else
		{
			if(cell.getTrajType().equals("roulette"))
			{
				getRouMaker().genNext(cell, posi);
			}else
			{
				if(cell.getTrajType().equals("brownian"))
				{
					if(cell.getLastBounce() < SingletonHolder.getJumpCounter()-5 && cell.getOverlapLast() < SingletonHolder.getJumpCounter()-(cell.getCellSize())/cell.getSpeed())
					{
						//cell.setVect(getRanMaker().getVectorGaus());
						Random randomiser = SingletonHolder.getMasterRandom();
						double poi = randomiser.nextGaussian()*cell.getSpeed();
						poi = Math.sqrt(Math.pow(poi, 2));
						cell.setIncSpeed(poi);
						getRanMaker().genNext(cell, posi);
					}
					//cell.setVect(getRanMaker().getVectorGausFeed(cell.getVect()));
				}
			}
		}
		cell.incrementTurns();

		cell.getPack().addTotalTurns(1.00);
		//SingletonStatStore.pollTurnType(cell,posi);
		int[] p = getRouMaker().getPicked();
		//slight bias here not worth alooot of extra effort right now
		/*if(p[0] != SingletonHolder.getChosenSectOne() ||p[1] != SingletonHolder.getChosenSectTwo() ||p[2] != SingletonHolder.getChosenSectThree() ||
				p[3] != SingletonHolder.getChosenSectFour() ||p[4] != SingletonHolder.getChosenSectFive() ||p[5] != SingletonHolder.getChosenSectSix() ||
				p[6] != SingletonHolder.getChosenSectSeven() ||p[7] != SingletonHolder.getChosenSectEight() )
		{
			System.out.println("mismatch "+cell.getPrevVect()+" "+cell.getVect());
			System.exit(0);
		}*/
	}
	public void genNextAntTraj(Cell cell,  Posi posi) 
	{
		if(cell.getTrajType().equals("random"))
		{
			//System.out.println("oof1");
			getRanMaker().genNext(cell, posi);
		}else
		{
			if(cell.getTrajType().equals("roulette"))
			{
				//System.out.println("oof2");
				getRouMaker().genNextAntWeighted(cell,posi);
			}
		}
		cell.incrementTurns();

		cell.getPack().addTotalTurns(1.00);
		//SingletonStatStore.pollTurnType(cell,posi);
		int[] p = getRouMaker().getPicked();
		//slight bias here not worth alooot of extra effort right now
		/*if(p[0] != SingletonHolder.getChosenSectOne() ||p[1] != SingletonHolder.getChosenSectTwo() ||p[2] != SingletonHolder.getChosenSectThree() ||
				p[3] != SingletonHolder.getChosenSectFour() ||p[4] != SingletonHolder.getChosenSectFive() ||p[5] != SingletonHolder.getChosenSectSix() ||
				p[6] != SingletonHolder.getChosenSectSeven() ||p[7] != SingletonHolder.getChosenSectEight() )
		{
			System.out.println("mismatch "+cell.getPrevVect()+" "+cell.getVect());
			System.exit(0);
		}*/
	}
	
	/*public void genNextWeightedTraj(Cell cell, PositionGrid posi, int ratio) 
	{
		if(cell.getTrajType().equals("random"))
		{
			getRanMaker().genNext(cell);
		}else
		{
			if(cell.getTrajType().equals("roulette"))
			{
				getRouMaker().genNextWeighted(cell, posi, ratio);
			}
		}
		cell.incrementTurns();
		SingletonStatStore.incrementTurns();
		this.pollTurnType(cell);
		int[] p = getRouMaker().getPicked();
		//slight bias here not worth alooot of extra effort right now
		/*if(p[0] != SingletonHolder.getChosenSectOne() ||p[1] != SingletonHolder.getChosenSectTwo() ||p[2] != SingletonHolder.getChosenSectThree() ||
				p[3] != SingletonHolder.getChosenSectFour() ||p[4] != SingletonHolder.getChosenSectFive() ||p[5] != SingletonHolder.getChosenSectSix() ||
				p[6] != SingletonHolder.getChosenSectSeven() ||p[7] != SingletonHolder.getChosenSectEight() )
		{
			System.out.println("mismatch "+cell.getPrevVect()+" "+cell.getVect());
			System.exit(0);
		}
	}*/
	
	/*public void pollTurnType(Cell cell) {
		double o = cell.getVect();
		double l = cell.getPrevVect();
		/*if(o < l)
		{
			o += 360;
		}
		double m = o-l;
		if(m < 0)
		{
			m = Math.pow(m, 2);
			m = Math.sqrt(m);
		}
		double m = o-l;
		if(m < 0)
		{
			m = 360+m;
		}
		if(m < 0)
		{
			System.out.println("!");
		}
		if(o !=l)
		{
			if( m <45)
			{
				SingletonStatStore.incChosenSectOne();
			}else
			{
				if(m<90)
				{
					SingletonStatStore.incChosenSectTwo();
				}else
				{
					if(m<135)
					{
						SingletonStatStore.incChosenSectThree();
					}else
					{
						if(m<180)
						{
							SingletonStatStore.incChosenSectFour();
						}else
						{
							if(m<225)
							{
								SingletonStatStore.incChosenSectFive(); 
							}else
							{
								if(m<270)
								{
									SingletonStatStore.incChosenSectSix();
								}else
								{
									if(m<315)
									{
										SingletonStatStore.incChosenSectSeven();
									}else
									{
										SingletonStatStore.incChosenSectEight();
									}
								}
							}
						}
					}
				}
			}
		}
		
	}*/

	public RandomTrajMaker getRanMaker() {
		return ranMaker;
	}

	public void setRanMaker(RandomTrajMaker ranMaker) {
		this.ranMaker = ranMaker;
	}

	public RouletteTrajMaker getRouMaker() {
		return rouMaker;
	}

	public void setRouMaker(RouletteTrajMaker rouMaker) {
		this.rouMaker = rouMaker;
	}

	

}
