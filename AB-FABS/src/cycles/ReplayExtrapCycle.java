package cycles;

import gui_stat_gen.StatRunFeedbackFrame;
import heatmaps.MosaicHeatmap;
import running_modules_increment.PopSiftModule;
import running_modules_increment.StorkModule;
import running_modules_increment.TurnModule;
import running_modules_jump.IncrementModule;
import running_modules_jump.OverlapModule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;

import cycle_components.MusicBox;
import cycle_components.StaticCalculations;
import cell_data_holders.Cell;
import cell_data_holders.CellHandler;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import stat_data_holders.SnapShotObfust;
import stat_data_holders.StatGeneratorObfust;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;
import file_manipulation.DataShuffler;
import file_manipulation.ExtrapTrajFileInterpreter;

public class ReplayExtrapCycle extends Cycle
{
	double[][] life;
	double[][] death;
	double[][] traj;
	int lPointer = 0;
	int dPointer = 0;
	int tPointer = 0;
	int time = 0;
	ArrayList<Integer> collided = null;
	boolean recording = false;
	ExtrapTrajFileInterpreter storedTFI;
	
	File[] extraps = null;
	private int f = 0;
	public ReplayExtrapCycle(ExtrapTrajFileInterpreter tFI)
	{
		storedTFI = tFI;
		dimensions = tFI.getGridSize();
		SingletonHolder.setPanelSize(dimensions);
		//System.out.println("num "+cellNum);
		Dimension dim = new Dimension(dimensions,dimensions);
		this.setEventArrays(tFI);
		shuffle = new DataShuffler(storedTFI.getFileName().replace(".txt", ""));
		this.setType("replay");
	 }
	
	public ReplayExtrapCycle(File[] files)
	{
		extraps = files;
		String[] nam =  new String[files.length];
		for(int i = 0; i < files.length;i++)
		{
			nam[i] = files[i].getName();
		}
		names = nam;
		this.setType("replay");
	 }
	
	boolean reset = false;	  
	
	private void setEventArrays(ExtrapTrajFileInterpreter tFI) 
	{
		ArrayList<double[]> lives = tFI.getLifeEvents();
		ArrayList<double[]> deathEvn = tFI.getDeathEvents();
		ArrayList<double[]> trajs = tFI.getTrajEvents();
		life = new double[lives.size()][];
		death = new double[deathEvn.size()][];
		traj = new double[trajs.size()][];
		for(int i = 0; i < life.length; i++)
		{
			life[i] = lives.get(i).clone();
		}
		for(int i = 0; i < death.length; i++)
		{
			death[i] = deathEvn.get(i).clone();
		}
		for(int i = 0; i < traj.length; i++)
		{
			traj[i] = trajs.get(i).clone();
		}
		lPointer = 0;
		dPointer = 0;
		tPointer = 0;
		sim.getCellMan().setShapes(new ArrayList<Ellipse2D>(),new ArrayList<Rectangle2D>());
		sim.getCellMan().setStore(new CellHandler());
		sim.getCellMan().getStore().setCells(new ArrayList<Cell>());
		sim.getCellMan().getStore().setTime(tFI.getTimeSteps()+1);
		setCells(new ArrayList<Cell>());
		//SingletonHolder.setDeathType("clear");
		//SingletonHolder.setDeath(true);
		//SingletonHolder.setCells(tFI.getTotalCells());
		SingletonHolder.setJumpsPerIncrement(tFI.getStepSize());
		sim.getMoveMan().genPosGrid(dimensions,getPosSize());
		SingletonHolder.setDrawMode(0);
		try {
			muse = new MusicBox();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sim.setMos(new MosaicHeatmap(dimensions/getPosSize(),dimensions/getPosSize(), sim.getMoveMan().getPositionGrid()));
		
		sim.resetMos();
		SingletonHolder.clean();
	   for(int i = 0; i < sim.getCellMan().getCells().size();i++)
		{
			sim.getCellMan().getCells().get(i).getPack().clean();
		}
	}

	public void setup() 
	   {
		   
		   f = 0;
		   SingletonHolder.setIterations(SingletonHolder.getIterations());
	   }

		@Override
	public void run() 
	{
			shuffle = new DataShuffler(extraps[0].getName());
			this.setSetSize(extraps.length);
			IncrementModule inc = new IncrementModule();
			this.loadIncrementModule(inc);
			time = 10000000;
			for(int i = 0; i < extraps.length; i++)
			{
				int l = ExtrapTrajFileInterpreter.grabLength(extraps[i]);
				if(l < time)
				{
					time = l;
				}
			}
		for(f = 0; f < extraps.length; f++)
		{
			SingletonHolder.clean();
			for(int i = 0; i < sim.getCellMan().getCells().size();i++)
			{
				sim.getCellMan().getCells().get(i).getPack().clean();
			}
			ExtrapTrajFileInterpreter tFI = new ExtrapTrajFileInterpreter(extraps[f]);
			storedTFI = tFI;
			dimensions = tFI.getGridSize();
			
			SingletonHolder.setPanelSize(dimensions);
			//System.out.println("num "+cellNum);
			this.setEventArrays(tFI);
			SingletonHolder.setIterations(time);
			long previousTime = System.currentTimeMillis();
			if(getIterationList()[0] != null)
			{
				  getIterationList()[0].setActive(true);
				  if(getIterationList()[0].getModType().equals("pop"))
				  {
					  ((PopSiftModule) getIterationList()[0]).setCellArray(tFI.getTotalCells(),tFI.getTimeSteps());
					  ((PopSiftModule) getIterationList()[0]).setExtrap(extraps[f]);
				  }
			}
			//System.out.println(".."+isMp4Gen());
			//time = (int) ((getStore().getTime()*SingletonHolder.getJumpsPerIncrement()-1)/30);
			
			//SingletonHolder.setFileShuffleFlag(true);
			//System.out.println(life.length);
			do{

				if(SingletonHolder.isRunning())
				{
					if(System.currentTimeMillis() > previousTime+SingletonHolder.getBrakes())
					{	
						//this.run();
						previousTime = System.currentTimeMillis();
						//poll direction first and find cells last
						if(SingletonHolder.isShowingOverlappers()||dataGatheringFlag)
						{
							ArrayList<Cell> ells = sim.getCellMan().getStore().getCells();
							if(collided != null)
							{
								for(int i = 0; i < collided.size();i++)
								{

									ells.get(collided.get(i)).getPack().addTotalCollisions(1.00);
									SingletonStatStore.pollCollisionTurn(ells.get(collided.get(i)));
								}
							}
						}

						jumpList[0].genNext(sim);
						//cycle can iterate through record with iteration being decided by travel cycles between each, apply traj changes for current increment then commit and gate off until distance met 
						if(SingletonHolder.getJumpCounter()/SingletonHolder.getJumpsPerIncrement()>SingletonHolder.getIncrement())
						{
							
							/*ignoreCounter++;
							if(ignoreCounter == ignoreNum)
							{
								SingletonStatStore.setGather(true);
								ignoreCounter = 0;
							}else
							{
								SingletonStatStore.setGather(false);
								
							}*/
							//if this ever gets moved PopSift needs the remainer variable fixed for the sshift relationship between when it is measured and births added etc
							for(int i = 0; i < getIterationList().length;i++)
							{
								if(getIterationList()[i] != null )
								{
								if(getIterationList()[i].getModType().equals("pop"))
								  {
								  }else
								  {
									getIterationList()[i].genNext(sim);
								  }
								}
							}
							
							//check births
							if(lPointer < life.length && life[lPointer][0] <= SingletonHolder.getIncrement() )
							{
								do
								{
									double[] command = life[lPointer];
									if(command[1] != sim.getCellMan().getStore().getCells().size())
									{
										System.out.println("Cell Desync");
									}
									//execute command 
									//[time interval,cell num, x, y, vector, speed]
									//sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime()+","+sub.getOldID()
									//vect, x, y, rep chance, death chance, speed, unique id, 
									//Cell cell = new Cell(command[0], command[1], command[2], 0, 0,command[3],(int) command[4], SingletonHolder.getCellSize(), new int[]{0,1}, SingletonHolder.getChange());
									//0 at vect to fic starting vect futz
									Cell cell = new Cell(command[4], command[2], command[3], 0, 0,command[5],(int) command[1], SingletonHolder.getCellLength(), new long[]{0,1}, SingletonHolder.getChange());
									if(command.length == 7)
									{
										cell.setOldID((int) command[6]);
									}
									if(sim.getMoveMan().isNear(cell))
									{
										SingletonStatStore.incrementBirths();
									}
									this.addCircle(cell,sim.getCellMan().getStore().getCells().size());
									sim.getCellMan().getStore().getCells().add(cell);
									SingletonStatStore.incTotalCells(); 
									/*if(iterationList[0]!= null && iterationList[0].getModType().equals("pop"))
									  {
										((PopSiftModule) iterationList[0]).checkOnly(sim.getCellMan().getStore().getCells().size()-1, sim);
									  }*/
									lPointer++;
									//inc pointer
								}while(lPointer < life.length && life[lPointer][0]<= SingletonHolder.getIncrement());
							}
							//check deaths
							if(dPointer < death.length && death[dPointer][0] == SingletonHolder.getIncrement())
							{
								do
								{
									double[] command = death[dPointer];
									//execute command 
									//[time interval, cell num]
									//System.out.println(""+death[dPointer][0]+" "+death[dPointer][1]+" "+dPointer+" ");
									Cell c = sim.getCellMan().getStore().getCells().get((int)command[1]);
									c.setCorporeal(false);
									sim.getMoveMan().getPositionGrid().getGCell(c.getPositionX(), c.getPositionY()).remove(c.getUnique());
									dPointer++;
									SingletonStatStore.incrementDeaths();
									//inc pointer
								}while(dPointer < death.length && death[dPointer][0] == SingletonHolder.getIncrement());
							}
							if(SingletonHolder.isShowingOverlappers()||dataGatheringFlag)
							{
								ArrayList<Cell> ells = sim.getCellMan().getStore().getCells();
								collided = new ArrayList<Integer>();
								for(int i = 0; i < ells.size();i++)
								{
									Cell c = ells.get(i);
									c.setOverlappers(0);
									ArrayList<Integer> possibles = new ArrayList<Integer>();
									possibles = sim.getMoveMan().getPositionGrid().getPossibleOverlap(c,i,ells);
									int added = 0;
									ArrayList<Integer> poss = new ArrayList<Integer>();
									for(int l = 0; l < possibles.size();l++)
									{
										boolean notFound = true;
										for(int m = 0; m < poss.size();m++)
										{
											//System.out.println("comparing "+poss.get(m)+" "+possibles.get(l));
											if(poss.get(m).intValue()==possibles.get(l).intValue())
											{
												notFound = false;
											}
		
											//System.out.println("notFound "+notFound);
										}
										if(notFound)
										{
											//System.out.println("adding "+possibles.get(l));
											poss.add(possibles.get(l));
										}else
										{
											//System.out.println("not adding "+possibles.get(l));
										}
									}
									possibles = poss;
									for(int l = 0; l < possibles.size(); l++)
									{
										//System.out.print(", "+possibles.get(l));
										if(possibles.get(l) != i && sim.getMoveMan().isOverlappingVis(i, possibles.get(l), sim.getCellMan().getCircles()))
										{
											c.setOverlappers(c.getOverlappers()+1);
											added++;
										}
									}
									/*for(int l = 0; l < possibles.size(); l++)
									{
										//System.out.print(", "+possibles.get(l));
										if(possibles.get(l) != i && this.samePos(l,i,ells))
										{
											c.setOverlappers(c.getOverlappers()+1);
											added++;
										}
									}*/
									if(added ==0)
									{
										c.setOverlappers(0);
									}else
									{
										collided.add(i);
										//System.exit(0);
										//System.out.println();
									}
								}
							}
							//check traj events
							if(tPointer < traj.length && traj[tPointer][0] == SingletonHolder.getIncrement() )
							{
								do
								{
									double[] command = traj[tPointer];
									//System.out.println("traj command"+command[0]+" "+command[1]+" "+command[2]+" "+command[3]+" "+store.getCells().size());
									//[time interval, cell num, vector, speed]
									//execute command 
									if(sim.getCellMan().getStore().getCells().size() > command[1])
									{
										if(sim.getCellMan().getStore().getCells().get((int)command[1]).isCorporeal())
										{
											Cell cell = sim.getCellMan().getStore().getCells().get((int)command[1]);
											//SingletonStatStore.pollTurnType(cell,sim.getMoveMan().getPositionGrid());
											cell.setVect(command[2], this.getSim().getMoveMan().getPositionGrid());
											cell.setSpeed(command[3]);
											cell.getPack().addTotalTurns(1.00);
										}
										//System.out.println(command[3]);
									}else
									{
										System.out.println("traj command pointing out"+command[0]+" "+command[1]+" "+command[2]+" "+command[3]+" "+sim.getCellMan().getStore().getCells().size());
									}
									tPointer++;
									//inc pointer
								}while(tPointer < traj.length && traj[tPointer][0] == SingletonHolder.getIncrement());
							}
							if(getIterationList()[0]!= null && getIterationList()[0].getModType().equals("pop"))
							{
								  getIterationList()[0].genNext(sim);
							}
							
							SingletonHolder.incIncrement();
							//this.storkService();
							//feedback.setText("Filename: "+fileName+" Running: "+running+" Jump Size: "+jumpSize+" Jumps Per Increment: "+jumpsPerIncrement+" Brake size: "+brakes+" Increment: "+increment);
						}
						//move cells a jump length along the vector
						for(int i = 1; i < jumpList.length;i++)
						{
							jumpList[i].genNext(sim);
						}
						//checking for collision has to be done once cell nums are set but movement from colision hasnt occured
						
						SingletonHolder.incJumpCounter();
						this.updateCells(getStore().getCells());
					}
				}
				
			
					
			}while(SingletonHolder.getIncrement() <= SingletonHolder.getIterations());
			collided = new ArrayList<Integer>();
			
			/*if(SingletonHolder.getIncrement() >=getStore().getTime())
			{
				SingletonHolder.setIncrement(0);
				this.setEventArrays(storedTFI);
				setCircles(new ArrayList<Ellipse2D>());
				sim.setMos(new MosaicHeatmap(dimensions/getPosSize(),dimensions/getPosSize(), sim.getMoveMan().getPositionGrid()));
				//this.run();
			}
			if(dataFlag)
			{
				//SingletonHolder.setRunning(false);
				
			}*/
			if(f == extraps.length-1)
			{
				//f = -1;
			}
			if(dataFlag)
			{
				f--;
				dataFlag = false;
			}
		}
		
	}

	private boolean samePos(int l, int i, ArrayList<Cell> ells) {
			Cell a = ells.get(i);
			Cell b = ells.get(l);
			boolean ret = false;
			if(StaticCalculations.getEuclidean(a.getPositionX(), a.getPositionY(), b.getPositionX(), b.getPositionY()) < 5) /*a.getPositionX() == b.getPositionY() && a.getPositionX() == b.getPositionY())*/
			{
				ret = true;
			}
			return ret;
		}

	public void checkFlags() 
	   {
		
	   }

	protected CellHandler copy(CellHandler cells2) 
	{
		CellHandler copy = new CellHandler();
		copy.setNum(cells2.getNum());
		copy.setSize(cells2.getSize());
		copy.setTime(cells2.getTime());
		copy.giveStarts(cells2.getStarts());
		copy.giveTrajectories(cells2.getTrajectories());
		return copy;
	}
			
	
	
	
	private void applyTrajChanges(ArrayList<double[]> arrayList, Posi posi) 
	{
		for(int i = 0; i < arrayList.size();i++)
		{
			double[] change = arrayList.get(i);
			getStore().getCell(change[1]).setVect(change[2], posi);
		}
		
	}

}
