package cycles;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;

import cell_data_holders.Cell;
import cell_data_holders.CellHandler;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import stat_data_holders.SnapShotObfust;
import stat_data_holders.StatGeneratorObfust;
import cycle_components.BezManager;
import cycle_components.CellManager;
import cycle_components.DeathManager;
import cycle_components.MoveManager;
import cycle_components.MusicBox;
import cycle_components.RepManager;
import cycle_components.TrajectoryManager;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;
import file_manipulation.DataShuffler;
import gui_main.GridPanel;
import gui_stat_gen.StatRunFeedbackFrame;
import heatmaps.MosaicHeatmap;
import running_modules_increment.Bundle;
import running_modules_increment.DataBundle;
import running_modules_increment.DataBundleCA;
import running_modules_increment.DataModule;
import running_modules_increment.DeathModule;
import running_modules_increment.Module;
import running_modules_increment.PopSiftModule;
import running_modules_increment.ReplicationModule;
import running_modules_increment.StorkModule;
import running_modules_increment.TurnModule;
import running_modules_jump.BoundryModule;
import running_modules_jump.IncrementModule;
import running_modules_jump.MusicModule;
import running_modules_jump.OverlapModule;

public class Cycle {
	
	//temp to fix other classes, remove when mod system working
	ArrayList<ArrayList<SnapShotObfust>> snaps = new ArrayList<ArrayList<SnapShotObfust>>();
	protected int statInts = 90;
	protected int dataStart = 1;
	protected int lastRead = 0;
	private int snapTrigger = 0;
	ArrayList<SnapShotObfust> shots = new ArrayList<SnapShotObfust>();
	private DataShuffler shuff;
	
	protected int[] previousTrack = new int[]{0,0,0,0,0,0,0,0};
   	protected int[] currentTrack = new int[8];
   	protected MusicBox muse; 
   	private boolean music = true;
   	
   	protected File imageFolder;
	   
  	private String vidName = null;
  	protected ArrayList<String> images = new ArrayList<String>();
  	protected boolean mp4Gen = false;
  	private long genStart = 0;
  	private long vidLength = 10000;
  	private int imCount = 0;
  	
  	private String type = "live";
  	

	int ignoreCounter = 0;
	int ignoreNum = 10;
  	
  	//temp ends
	
	
	
  	//private double cellSize = 10;
  	private List<Polygon> polys = new ArrayList<Polygon>();

   
  	protected boolean stork = false;
  	protected boolean vidImgOffload = true;

  	protected boolean repVarNeeded = false;
  	protected boolean deathVarNeeded = false;
  	protected boolean speedVarNeeded = false;
  	protected boolean bezVarNeeded = false;
   
  	private boolean repVarLast = false;
  	private boolean deathVarLast = false;
  	private boolean speedVarLast = false;
  	private boolean bezVarLast = false;
   
  	private boolean repNeeded = false;
  	private boolean deathNeeded = false;
  	private boolean speedNeeded = false;
   
  	private boolean repLast = false;
  	private boolean deathLast = false;
   	private boolean speedLast = false;
   	private boolean bezLast = false;
   	
   	
   	private Boolean vidGen = false;
   	protected int posSize =2;
   
   	//private Image buffer = this.createVolatileImage((int)PREF_W, (int)PREF_H);
   	//private Image buffer2 = this.createVolatileImage((int)PREF_W, (int)PREF_H);
   
   	private int setSize = 1;
   
   	
   	
   
   	int dimensions = 0;
    int cellNum = 0;
    Graphics graph;
	protected GridPanel parent;
   	protected Bundle sim = new DataBundle( dimensions, posSize, parent);
    
	
	
	
	protected boolean dataFlag = false;
	protected boolean dataGatheringFlag = false;
	
	
	protected DataShuffler shuffle; 
    
	/*
	 * 0 data
	 * rep
	 * death
	 * stork
	 * turn
	 */
	Module[] activeIteration = new Module[6];
	/*
	 * music
	 * boundry
	 * increment
	 * overlap
	 * vid
	 */
	Module[] activeJump = new Module[5];
	
	private Module[] iterationList = new Module[6];
	Module[] jumpList = new Module[5];
	
	protected String[] names = new String[]{"live"};
	
    public Cycle()
    {
    	
    }
	
    public void giveDims(int dim, int cell)
    {
    	dimensions = dim;
    	sim.setDimensions(dim);
    	cellNum = cell;
    }
    
	public void setup() 
   {
	   
	   SingletonHolder.clean();
	   for(int i = 0; i < sim.getCellMan().getCells().size();i++)
		{
			sim.getCellMan().getCells().get(i).getPack().clean();
		}
	   dimensions = SingletonHolder.getSize();
	  // System.out.println("Cycle cells"+sim.getCellMan().getStore().getCells().size());
	   sim.getCellMan().setStore(sim.getCellMan().createCells(sim.getCellMan()));
	   sim.getCellMan().setCells(sim.getCellMan().getStore().getCells());
	   //System.out.println("Cycle cells2"+sim.getCellMan().getStore().getCells().size());
	   sim.getMoveMan().genPosGrid(dimensions,getPosSize());
	   sim.getMoveMan().getPositionGrid().addAllCellPositions(sim.getCellMan().getStore().getCells());
	   ArrayList<Cell> cells2 = sim.getCellMan().getStore().getCells();
	   for(int i =0; i < cells2.size(); i++)
	   {
		   this.addCircle(cells2.get(i),i);
		   //System.out.println("circ add "+i);
		   
		   //this.addPoly(cells2[i]);
	   }
	   sim.setMos(new MosaicHeatmap(dimensions/getPosSize(),dimensions/getPosSize(), sim.getMoveMan().getPositionGrid()));
	   sim.resetMos();
	   TurnModule tur =  new TurnModule();
	   loadTurnModule(tur);
	   IncrementModule inc = new IncrementModule();
	   loadIncrementModule(inc);
	   OverlapModule ovr = new OverlapModule();
	   loadOverlapModule(ovr);
	   SingletonHolder.setBoundCheckNeeded(true);
	   SingletonHolder.setAttractZoneChanges(true);
	   SingletonHolder.setDeflectZoneChanges(true);
	   this.checkFlags();
   }

   private int getSmallestCellLength() {
	int smallest = 1000;
	for(int i = 0; i < sim.getCellMan().getStore().getCells().size();i++)
	{
		int size = sim.getCellMan().getStore().getCells().get(i).getCellSize(); 
		if(size < smallest)
		{
			smallest = size;
		}
	}
	
	return smallest;
}



   public void run() {
	   long previousTime = System.currentTimeMillis();
		boolean runs = true;
		do{
			
			//System.out.println("A");
			if(SingletonHolder.isRunning())
			{
				//System.out.println("B");
				if(System.currentTimeMillis() > previousTime+SingletonHolder.getBrakes())
				{
					
					
					
					//System.out.println("C");
					
					previousTime = System.currentTimeMillis();
					
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
						//iterate inc modules
						//data
						//replication
						//death
						//turn
						//stork
						for(int i = 0; i < getIterationList().length;i++)
						{
							getIterationList()[i].genNext(sim);
							//System.out.println(iterationList.get(i).getModType());
						}
						
						//System.out.println("D");
						//move pointer change traj etc
						//10000000
						
						
						SingletonHolder.incIncrement();
						//feedback.setText("Filename: "+fileName+" Running: "+running+" Jump Size: "+jumpSize+" Jumps Per Increment: "+jumpsPerIncrement+" Brake size: "+brakes+" Increment: "+increment);
					}
					//jump side modules
					//increment
					//boundry
					//overlap
					//vid
					//music
					for(int i = 1; i < jumpList.length;i++)
					{
						jumpList[i].genNext(sim);
						//System.out.println(jumpList.get(i).getModType());
					}
					//move cells a jump length along the vector
					
					SingletonHolder.incJumpCounter();
					this.updateCells(sim.getCellMan().getStore().getCells());
					//buffer = this.genBuffer((int)PREF_W, (int)PREF_H);
					//buffer2 = buffer;
					//System.out.println("update "+jumpCounter+" inc pointer "+incrementPointer+" increment "+increment);
				}
			}
		}while(runs);
		
		
	}
   
   

public void checkFlags() 
   {
	   if(SingletonHolder.isBoundCheckNeeded())
		{
		   
			System.out.println("bound checked");
			SingletonHolder.setBoundCheckNeeded(false);
			if(SingletonHolder.isBoundary())
			{
				BoundryModule rep = new BoundryModule();
				StorkModule stork = new StorkModule();
				loadBoundaryModule(rep);
				loadStorkModule(stork);
				sim.getCellMan().getStore().setCells(sim.getMoveMan().checkAllAndFixWithinBounds(sim.getCellMan().getStore().getCells(), sim.getMoveMan().getPositionGrid()));
				sim.getCellMan().getStore().setCells(sim.getMoveMan().checkAllAndFixWithinBounds(sim.getCellMan().getStore().getCells(), sim.getMoveMan().getPositionGrid()));
				sim.getCellMan().getStore().setCells(sim.getMoveMan().checkAllAndFixWithinBounds(sim.getCellMan().getStore().getCells(), sim.getMoveMan().getPositionGrid()));
			}else
			{
				unLoadBoundryModule();
				
			}
		}
		//need to add a comparable array here to check if change is needed
		repVarNeeded = SingletonHolder.isRepVar();
		deathVarNeeded = SingletonHolder.isDeathVar();
		speedVarNeeded = SingletonHolder.isSpeedFlag();
		bezVarNeeded = SingletonHolder.isBezFlag();
		repNeeded = SingletonHolder.isReplication();
		deathNeeded = SingletonHolder.isDeath();
		
		if(SingletonHolder.isChanged())
		{
			SingletonHolder.setChanged(false);
			repVarLast = SingletonHolder.isRepVar();
			deathVarLast = SingletonHolder.isDeathVar();
			speedVarLast = SingletonHolder.isSpeedFlag();
			bezVarLast = SingletonHolder.isBezFlag();
			repLast = SingletonHolder.isRepVar();
			deathLast = SingletonHolder.isDeathVar();
			speedLast = SingletonHolder.isSpeedFlag();
			bezLast = SingletonHolder.isBezFlag();

			this.setup();
			//repChange();
			//deathChange();
			//speedChange();
			//bezChange();
			cellChanges();
			//System.out.println("here a");
		}
		if(repVarNeeded != repVarLast || repNeeded != repLast)
		{
			repVarLast = repVarNeeded;
			repLast = repNeeded;
			//repChange();
			this.setup();
			cellChanges();
			
			//System.out.println("here b");
			if(SingletonHolder.isReplication())
			{
				ReplicationModule rep = new ReplicationModule();
				StorkModule stork = new StorkModule();
				loadRepModule(rep);
				loadStorkModule(stork);
			}else
			{
				unloadRepModule();
				if(SingletonHolder.isReplication() == SingletonHolder.isDeath())
				{
					unloadStorkModule();
				}
			}
		}
		if(deathVarNeeded != deathVarLast || deathNeeded != deathLast)
		{
			deathVarLast = deathVarNeeded;
			deathLast = deathNeeded;
			//deathChange();
			this.setup();
			cellChanges();
			
			//System.out.println("here c");
			if(SingletonHolder.isDeath())
			{
				DeathModule rep = new DeathModule();
				StorkModule stork = new StorkModule();
				loadDeathModule(rep);
				loadStorkModule(stork);
			}else
			{
				unloadDeathModule();
				if(SingletonHolder.isReplication() == SingletonHolder.isDeath())
				{
					unloadStorkModule();
				}
			}
		}
		if(speedVarNeeded != speedVarLast || SingletonHolder.isJumpsChanged())
		{
			SingletonHolder.setJumpsChanged(false);
			speedVarLast = speedVarNeeded;
			//speedChange();
			this.setup();
			cellChanges();
			//System.out.println("here d");
		}
		
		if(bezVarNeeded != bezVarLast || SingletonHolder.isBezChanges())
		{
			sim.getBezMan().bezChange(sim.getCellMan().getCells(), sim.getMoveMan().getPositionGrid());
			bezVarLast = bezVarNeeded;
			SingletonHolder.setBezChanges(false);
			//System.out.println("here e");
		}
		
		if(SingletonHolder.isRepChanges())
		{
			SingletonHolder.setRepChanges(false);
			System.out.println("brap");
			//repChange();
			this.setup();
			cellChanges();
			if(SingletonHolder.isReplication())
			{
				ReplicationModule rep = new ReplicationModule();
				StorkModule stork = new StorkModule();
				loadRepModule(rep);
				loadStorkModule(stork);
			}else
			{
				unloadRepModule();
				if(SingletonHolder.isReplication() == SingletonHolder.isDeath())
				{
					unloadStorkModule();
				}
			}
		}
		if(SingletonHolder.isDeathChanges())
		{
			SingletonHolder.setDeathChanges(false);
			//deathChange();
			this.setup();
			cellChanges();
			
		}
		if(SingletonHolder.isSpeedChanges())
		{
			SingletonHolder.setSpeedChanges(false);
			//speedChange();
			this.setup();
			cellChanges();
		}
		
		if(SingletonHolder.isBezChanges())
		{
			SingletonHolder.setBezChanges(false);
			this.setup();
			cellChanges();
			sim.getBezMan().bezChange(sim.getCellMan().getCells(), sim.getMoveMan().getPositionGrid());
			
		}
		if(SingletonHolder.isTurnChanged())
		{
			//turnChange();
			SingletonHolder.setTurnChanged(false);
			this.setup();
			cellChanges();
		}
		
		if(SingletonHolder.isAntChanges())
		{
			//turnChange();
			SingletonHolder.setAntChanges(false);
			this.setup();
			cellChanges();
			
		}
		
		if(SingletonHolder.isDistChanges())
		{
			SingletonHolder.setDistChanges(false);
			this.setup();
			cellChanges();
			sim.getBezMan().bezChange(sim.getCellMan().getCells(), sim.getMoveMan().getPositionGrid());
		}
		
		if(SingletonHolder.isAttractZoneChanges()||SingletonHolder.isDeflectZoneChanges())
		{
			//System.out.println("higher here");
			SingletonHolder.setAttractZoneChanges(false);
			SingletonHolder.setDeflectZoneChanges(false);
			//this.setup();
			sim.getMoveMan().getPositionGrid().resetZones();
			if(SingletonHolder.isAttractZoneFlag())
			{
				sim.getMoveMan().getPositionGrid().genAttractZones();
			}
			if(SingletonHolder.isDeflectZoneFlag())
			{
				sim.getMoveMan().getPositionGrid().genDeflectZones();
			}
			sim.getMoveMan().getPositionGrid().applyZones();
		}
		
		
		
	
   }


	
	private boolean inBounds(double[] ds, int size) {
		boolean in  = false;
		if(ds[0] >= 0 && ds[0] <= size && ds[1] >= 0 && ds[1] <= size )
		{
			in = true;
		}
		return in;
	}

	protected void cellChanges()
	{
		System.out.println("cell change");
		//check to see if cell nums changed
		if(SingletonHolder.getCellTotalFromSets() == sim.getCellMan().getStore().getCells().size())
		{
			int setPointer = 0;
			int cellPointer = 0;
			// if same
			//apply new behaviours across them
			do
			{
				for(int i = 0 ; i < Double.parseDouble(SingletonHolder.getCellSets()[setPointer][17]);i++)
				{
					sim.getCellMan().getStore().getCells().get(cellPointer).attributesFromArray(SingletonHolder.genCellValuesFromSet(SingletonHolder.getCellSets()[setPointer]));
					cellPointer++;
				}
				setPointer++;
			}while(setPointer<SingletonHolder.getCellSets().length);
		}else
		{
			if(SingletonHolder.getCellTotalFromSets() < sim.getCellMan().getStore().getCells().size())
			{
				int setPointer = 0;
				int cellPointer = 0;
				// if less
				//apply new behaviours across them
				do
				{
					for(int i = 0 ; i < Integer.parseInt(SingletonHolder.getCellSets()[setPointer][17]);i++)
					{
						sim.getCellMan().getStore().getCells().get(cellPointer).attributesFromArray(SingletonHolder.genCellValuesFromSet(SingletonHolder.getCellSets()[setPointer]));
						cellPointer++;
					}
					setPointer++;
				}while(setPointer<SingletonHolder.getCellSets().length);
				int tot = SingletonHolder.getCellTotalFromSets();
				//remove extra
				do
				{
					sim.getCellMan().getStore().getCells().remove(tot);
				}while(tot != sim.getCellMan().getStore().getCells().size());
			}else
			{
				//if more apply and add extra
				int setPointer = 0;
				int cellPointer = 0;
				int tot = SingletonHolder.getCellTotalFromSets();
				//System.out.println("adding " +tot+ "running "+SingletonHolder.isRunning());
				do
				{
					sim.getCellMan().getStore().getCells().add(new Cell(SingletonHolder.getSize(),SingletonHolder.genCellValuesFromSet(SingletonHolder.getCellSets()[setPointer]),setPointer,sim.getCellMan().getStore().getCells().size() ));
				}while(tot != sim.getCellMan().getStore().getCells().size());
				do
				{
					for(int i = 0 ; i < Integer.parseInt(SingletonHolder.getCellSets()[setPointer][17]);i++)
					{
						sim.getCellMan().getStore().getCells().get(cellPointer).attributesFromArray(SingletonHolder.genCellValuesFromSet(SingletonHolder.getCellSets()[setPointer]));
						cellPointer++;
					}
					setPointer++;
				}while(setPointer<SingletonHolder.getCellSets().length);
			}

		}

		sim.getCellMan().setCells(sim.getCellMan().getStore().getCells());
		sim.getMoveMan().genPosGrid(dimensions,getPosSize());
		
		//sim.getMoveMan().getPositionGrid().applyZones();
		sim.getMoveMan().getPositionGrid().addAllCellPositions(sim.getCellMan().getCells());
		for(int i = 0 ; i < sim.getBezMan().getCurvePoints().size();i++)
		{
			sim.getMoveMan().getPositionGrid().setCurve(sim.getBezMan().getCurvePoints().get(i), i, SingletonHolder.getSize(), SingletonHolder.getBezSize(),SingletonHolder.getBezChance() , SingletonHolder.isBezDist(), SingletonHolder.getBezVarience());
		}
		sim.getCellMan().setShapes(new ArrayList<Ellipse2D>(),new ArrayList<Rectangle2D>());
		for(int i =0; i < sim.getCellMan().getCells().size(); i++)
	    {
		   sim.getCellMan().addShape(sim.getCellMan().getCells().get(i));
		   //System.out.println("circ add "+i);
		   
		   //this.addPoly(cells2[i]);
	    }
		System.out.println("circs "+sim.getCellMan().getCircles().size()+ "running "+SingletonHolder.isRunning());
		repVarLast = repVarNeeded;
		repLast = repNeeded;
	
		deathVarLast = deathVarNeeded;
		deathLast = deathNeeded;
	
		speedVarLast = speedVarNeeded;
		SingletonHolder.setJumpsChanged(false);
		SingletonStatStore.setTotalCells(sim.getCellMan().getCells().size());
			
	}
	
	protected void speedChange() 
	{
		if(speedVarNeeded)
		{
			if(SingletonHolder.getSpeedType().equals("random"))
			{
				Random rand = SingletonHolder.getMasterRandom();
				for(int i = 0 ; i < sim.getCellMan().getStore().getCells().size();i++)
				{
					double bottom = SingletonHolder.getSpeed()-SingletonHolder.getSpeedVar();
					double top = SingletonHolder.getSpeed()+SingletonHolder.getSpeedVar();
					double range = top-bottom+1;
					int chosen = rand.nextInt((int)(range*100000.00+1.00));
					double chosen2 = chosen/100000;
					chosen2 = bottom + chosen2;
					//System.out.println("speed 2 "+chosen2);
					sim.getCellMan().getStore().getCells().get(i).setSpeed(chosen2);
				}
			}else
			{
				Random rand = SingletonHolder.getMasterRandom();
				for(int i = 0 ; i < sim.getCellMan().getStore().getCells().size();i++)
				{
					double gaus = rand.nextGaussian()*0.5;
					do 
					{
						gaus = rand.nextGaussian()*0.5;
					}while(gaus > 1 || gaus < -1);
					gaus = gaus-0.5;
					double bottom = SingletonHolder.getSpeed()-SingletonHolder.getSpeedVar();
					double top = SingletonHolder.getSpeed()+SingletonHolder.getSpeedVar();
					double range = top-bottom;
					double mid = SingletonHolder.getSpeed();
					double chosen = (int) (mid + (range*gaus));
					//System.out.println("speed 1 "+chosen);
					sim.getCellMan().getStore().getCells().get(i).setSpeed(chosen);
				}
			}
		}else
		{
			for(int i = 0 ; i < sim.getCellMan().getStore().getCells().size();i++)
			{
				sim.getCellMan().getStore().getCells().get(i).setSpeed((int) SingletonHolder.getJumpSize());
				//System.out.println("set speed to "+SingletonHolder.getJumpSize());
			}
		}
	}

	protected void deathChange() 
	{
		if(deathVarNeeded)
		{
			Random rand = SingletonHolder.getMasterRandom();
			for(int i = 0 ; i < sim.getCellMan().getStore().getCells().size();i++)
			{
				int bottom = SingletonHolder.getDeathChance()-SingletonHolder.getDeathVarience();
				int top = SingletonHolder.getDeathChance()+SingletonHolder.getDeathVarience();
				int range = top-bottom+1;
				int chosen = rand.nextInt(range);
				chosen = bottom + chosen;
				sim.getCellMan().getStore().getCells().get(i).setDeath(chosen);
			}
		}else
		{
			for(int i = 0 ; i < sim.getCellMan().getStore().getCells().size();i++)
			{
				sim.getCellMan().getStore().getCells().get(i).setDeath(SingletonHolder.getDeathChance());
			}
		}
	}

	protected void repChange() 
	{
		if(repVarNeeded)
		{
			Random rand = SingletonHolder.getMasterRandom();
			for(int i = 0 ; i < sim.getCellMan().getStore().getCells().size();i++)
			{
				int bottom = SingletonHolder.getRepChance()-SingletonHolder.getRepVarience();
				int top = SingletonHolder.getRepChance()+SingletonHolder.getRepVarience();
				int range = top-bottom+1;
				int chosen = rand.nextInt(range);
				chosen = bottom + chosen;
				sim.getCellMan().getStore().getCells().get(i).setRepChance(chosen);
			}
		}else
		{
			for(int i = 0 ; i < sim.getCellMan().getStore().getCells().size();i++)
			{
				sim.getCellMan().getStore().getCells().get(i).setRepChance(SingletonHolder.getRepChance());
			}
		}
	}
	public void trimIterationModules(Module[] activeIteration)
	{
		int num = 0;
		for(int i = 0; i < activeIteration.length;i++)
		{
			if(activeIteration[i] != null)
			{
				num++;
			}
		}
		setIterationList(new Module[num]);
		int point = 0;
		for(int i = 0; i < activeIteration.length;i++)
		{
			if(activeIteration[i]!=null)
			{
				getIterationList()[point] = activeIteration[i];
				point++;
				//System.out.println(activeIteration[i].getModType());
			}
		}
		//System.out.println("");
	}
	public void trimJumpModules(Module[] activeJump)
	{
		int num = 0;
		for(int i = 0; i < activeJump.length;i++)
		{
			if(activeJump[i] != null)
			{
				num++;
			}
		}
		jumpList = new Module[num];
		int point = 0;
		for(int i = 0; i < activeJump.length;i++)
		{
			if(activeJump[i]!=null)
			{
				jumpList[point] = activeJump[i];
				point++;
				//System.out.println(activeIteration[i].getModType());
			}
		}
		//System.out.println("");
	}
	/*
	protected void storkServiceLegacy() 
	{
		ArrayList<Cell> next = new ArrayList<Cell>();
		for(int i = 0; i < sim.getCellMan().getStore().getCells().length;i++)
		{
			if(sim.getCellMan().getStore().getCells()[i].isCorporeal())
			{
				next.add(sim.getCellMan().getStore().getCells()[i]);
				
			}
		}
		int difference = sim.getCellMan().getStore().getCells().length-next.size();
		ArrayList<int[]> oldPlace = new ArrayList<int[]>();
		for(int i =0; i < deathQueue.size();i++)
		{
			oldPlace.add(new int[]{deathQueue.get(i),sim.getCellMan().getStore().getCells()[deathQueue.get(i)].getUnique(),i});
		}
		//Realistically it should be a decrementing distance but death q would have to sort by position not time of death
		Cell[] newStore = new Cell[next.size()+storkWaiting.size()];
		int p = 0;
		for(int i = 0; i < next.size();i++)
		{
			newStore[p] = new Cell(next.get(i));
			p++;
		}
		for(int i = 0; i < storkWaiting.size();i++)
		{
			newStore[p] = new Cell(storkWaiting.get(i));
			p++;
		}
		//should replace old positions with the updated ones without scanning whole array each time, complexity should be be a multiple of the difference each time
		for(int i =0; i < oldPlace.size(); i++)
		{
			int[] old = oldPlace.get(i);
			p = old[0]-difference;
			if(p<0)
			{
				p = 0;
			}
			boolean notFound = true;
			do
			{
				//System.out.println(newStore[p].getUnique()+" "+old[1]+" "+p+" "+difference);
				if(newStore[p].getUnique() == old[1])
				{
					deathQueue.set(old[2], p);
					notFound = false;					
				}
				p++;
			}while(notFound);
		}
		sim.getCellMan().getStore().setCells(newStore);
		storkWaiting = new ArrayList<Cell>();
		Cell[] cells2 = sim.getCellMan().getStore().getCells();
		//there is a better way of doing this somewhere where by positions are updated not overwritten but i dont have time or need to fix right now
		setPositionGrid(new PositionGrid(dimensions,SingletonHolder.getCellLength(),positionGrid.getHeat()));
		for(int i =0; i < cells2.length; i++)
		{
			this.addCircle(cells2[i],i);
		}
		
	}*/
	
	private int[][] translatePoly(int[][] polyPoints, double vect) {
		int[][] transPoly = new int[polyPoints.length][polyPoints[0].length];
		int vectI = (int) vect;
		for(int i = 0; i < polyPoints[0].length; i++)
		{
			transPoly[0][i] = (int) ((polyPoints[0][i]*Math.cos(vectI))-(polyPoints[1][i]*Math.sin(vectI)));
			transPoly[1][i] = (int) ((polyPoints[1][i]*Math.cos(vectI))+(polyPoints[0][i]*Math.sin(vectI)));
		}
		return transPoly;
	}
	private int[][] generatePoly(Cell cell) {
		int[][] points = new int[2][4];
		points[0][0] = (int) cell.getPositionX();
		points[1][0] = (int) cell.getPositionY();
		points[0][1] = (int) cell.getPositionX()+10;
		points[1][1] = (int) cell.getPositionY();
		points[0][2] = (int) cell.getPositionX()+5;
		points[1][2] = (int) cell.getPositionY()+3;
		points[0][3] = (int) cell.getPositionX()-5;
		points[1][3] = (int) cell.getPositionY()-3;
		return points;
	}
	
	public void updateCells(ArrayList<Cell> arrayList)
	{
		setCells(arrayList);
		if(parent!=null)
		{
			parent.repaint();
		}
	}

	public void addCircle(Cell c, int i)
	{	
		int x = (int) (c.getPositionX()/sim.getPosSize());
		int y =(int) (c.getPositionY()/sim.getPosSize());
		//System.out.println(+x+" "+y);
		getPositionGrid().addCellPosition(c,i);
		Ellipse2D circ = new Ellipse2D.Double(c.getPositionX(),c.getPositionY(),c.getCellSize(),c.getCellSize());
		getCircles().add(circ);
		Rectangle2D rec = new Rectangle2D.Double(x, y, sim.getPosSize(),sim.getPosSize() );
		sim.getCellMan().getRecs().add(rec);
	}
	
	public void addPoly(Cell c)
	{	
		Polygon pol = new Polygon();
		int[][] polyPoints = this.generatePoly(c);
	  	polyPoints = translatePoly(polyPoints,c.getVect());
	  	pol = new Polygon(polyPoints[0],polyPoints[1],polyPoints[0].length);
		polys.add(pol);
	}

	 



	

	public CellHandler getStore2() {
		return sim.getCellMan().getStore2();
	}

	public void setStore2(CellHandler cellS) {
		this.sim.getCellMan().setStore2(cellS);
	}


	

	

	public CellHandler getStore() {
		return sim.getCellMan().getStore();
	}

	public void setStore(CellHandler stre) {
		this.sim.getCellMan().setStore(stre);
	}

	
	public ArrayList<Cell> getCells() {
		return sim.getCellMan().getCells();
	}

	public void setCells(ArrayList<Cell> arrayList) {
		sim.getCellMan().setCells(arrayList);
	}

	public List<Ellipse2D> getCircles() {
		return sim.getCellMan().getCircles();
	}

	public void setShapes(List<Ellipse2D> circles, List<Rectangle2D> recs) {
		sim.getCellMan().setShapes(circles,recs);
	}

	/*public double getCellSize() {
		return cellSize;
	}

	public void setCellSize(double cellSize) {
		this.cellSize = cellSize;
	}*/

	public Posi getPositionGrid() {
		return sim.getMoveMan().getPositionGrid();
	}

	public void setPositionGrid(Posi positionGrid) {
		this.sim.getMoveMan().setPositionGrid(positionGrid);
	}

	public ArrayList<CubicCurve2D.Double> getCurves() {
		return sim.getBezMan().getCurves();
	}

	public void setCurves(ArrayList<CubicCurve2D.Double> curves) {
		sim.getBezMan().setCurves(curves);
	}


	public void giveGraphics(Graphics graphics, GridPanel dis) {
		graph = graphics;
		parent = dis;
		sim.setParent(dis);
		
	}

	

	public int getPosSize() {
		return posSize;
	}

	public void setPosSize(int posSize) {
		this.posSize = posSize;
	}

	public BezManager getBezMan() {
		// TODO Auto-generated method stub
		return sim.getBezMan();
	}

	

	public boolean isDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(boolean dataFlag) {
		this.dataFlag = dataFlag;
	}

	public boolean isDataGatheringFlag() {
		return dataGatheringFlag;
	}

	public void setDataGatheringFlag(boolean dataGatheringFlag) {
		this.dataGatheringFlag = dataGatheringFlag;
	}

	public Bundle getSim() {
		return sim;
	}

	public void setSim(DataBundle sim) {
		this.sim = sim;
	}
	
	public Boolean isMp4Gen() {
		// TODO Auto-generated method stub
		return vidGen;
	}
	/*
	 * 0 data
	 * rep
	 * death
	 * stork
	 * turn
	 */
	public void loadDataModule(DataModule dat) {
		/*if(SingletonHolder.getIterations() > getStore().getTime())
		{
			SingletonHolder.setIterations(getStore().getTime()-1);
		}*/
		
		if(dat.getDataStart() < SingletonHolder.getIncrement())
		{
			SingletonHolder.setMasterRandom(new Random(SingletonHolder.getMasterSeed()));
			this.setup();
			//this.run();
		}
		if(this.getType().equals("replay"))
		{
			
		}else
		{
			dat.setActive(true);
		}
		activeIteration[0] = dat;
		trimIterationModules(activeIteration);
		
	}
	
	public void loadPopSiftModule(PopSiftModule dat) {
		
		activeIteration[0] = dat;
		trimIterationModules(activeIteration);
		
	}
	public void loadPopSiftModuleAdd(PopSiftModule dat) {
		
		activeIteration[5] = dat;
		trimIterationModules(activeIteration);
		
	}
	public void unLoadDataModule()
	{
		activeIteration[0] = null;
		trimIterationModules(activeIteration);
	}
	
	public void loadRepModule(ReplicationModule rep)
	{
		activeIteration[2] = rep;
		trimIterationModules(activeIteration);
	}
	
	public void unloadRepModule()
	{
		activeIteration[2] = null;
		trimIterationModules(activeIteration);
	}
	
	public void loadDeathModule(DeathModule rep)
	{
		activeIteration[1] = rep;
		trimIterationModules(activeIteration);
	}
	
	public void unloadDeathModule()
	{
		activeIteration[1] = null;
		trimIterationModules(activeIteration);
	}
	
	public void loadStorkModule(StorkModule rep)
	{
		activeIteration[4] = rep;
		trimIterationModules(activeIteration);
	}
	
	public void unloadStorkModule()
	{
		activeIteration[4] = null;
		trimIterationModules(activeIteration);
	}
	
	public void loadTurnModule(TurnModule rep)
	{
		activeIteration[3] = rep;
		trimIterationModules(activeIteration);
	}
	
	public void unloadTurnModule()
	{
		activeIteration[3] = null;
		trimIterationModules(activeIteration);
	}
	
	
	/*
	 * music
	 * boundry
	 * increment
	 * overlap
	 * vid
	 */
	public void loadMusicModule(MusicModule vid) {

		activeJump[1] = vid;
		trimJumpModules(activeJump);
		
	}
	
	private void unLoadMusicModule()
	{

		activeJump[1] = null;
		trimJumpModules(activeJump);
	}
	
	public void loadBoundaryModule(BoundryModule vid) {

		activeJump[3] = vid;
		trimJumpModules(activeJump);
		
	}
	
	protected void unLoadBoundryModule()
	{

		activeJump[3] = null;
		trimJumpModules(activeJump);
	}
	
	public void loadIncrementModule(IncrementModule vid) {

		activeJump[0] = vid;
		trimJumpModules(activeJump);
		
	}
	
	private void unLoadIncrementModule()
	{

		activeJump[0] = null;
		trimJumpModules(activeJump);
	}
	
	public void loadOverlapModule(OverlapModule vid) {

		activeJump[2] = vid;
		trimJumpModules(activeJump);
		
	}
	
	private void unLoadOverlapModule()
	{

		activeJump[2] = null;
		trimJumpModules(activeJump);
	}
	
	public void loadVidModule(Module vid) {

		activeJump[4] = vid;
		vid.setActive(true);
		trimJumpModules(activeJump);
		vidGen = true;
		
	}
	
	private void unLoadVidModule()
	{

		activeJump[4] = null;
		trimJumpModules(activeJump);
		vidGen = false;
	}
	//temp methods
	protected void recordVidImages(Bundle sim2) 
	{
	   if(images.size() == 0 )
		{
			File file = new File(System.getProperty("user.dir"));
			imageFolder = new File(file.getAbsolutePath()+"//Vid Image folder");
			imageFolder.mkdir();
		}
			//(imCount);
			BufferedImage buff = new BufferedImage(sim2.getDimensions(),sim2.getDimensions(), BufferedImage.TYPE_INT_RGB);
			buff.createGraphics();
			Graphics g =buff.getGraphics();
			//Graphics2D g2 = (Graphics2D) g;
			/*
			Ellipse2D circ = new Ellipse2D.Double(0,0,getPrefW(),getPrefH());
			for (int i = 0; i < this.getCells().size(); i++)
		      {
		    	  circ = getCircles().get(i);
		    	  circ = new Ellipse2D.Double(getCells().get(i).getPositionX(),getCells().get(i).getPositionY(),SingletonHolder.getCellLength(),SingletonHolder.getCellLength());
		    	  g2.setColor(Color.WHITE);
		    	  g2.fill(circ);
		      }
			g2.dispose();*/
			sim2.getParent().print(g);
			g.dispose();
			
			//File pic = new File(imageFolder.getAbsolutePath()+"//DR"+images.size()+".png");
			try {
				ImageIO.write(buff, "png",Files.newOutputStream(Paths.get(imageFolder.getAbsolutePath()+"//Live"+images.size()+".png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("adding "+imageFolder.getAbsolutePath()+"//live"+images.size()+".png");
			images.add(imageFolder.getAbsolutePath()+"//Live"+images.size()+".png");
			
			//long target = System.currentTimeMillis()+31;
			//do
			//{
				
			//}while(System.currentTimeMillis() < target);
			//vidz.giveNextImage(buff,sim.getMoveMan());
	}

	public void generateMp4() 
	{
		//need to set up a descriptive auto gen for file names
		images =new ArrayList<String>();
		vidName = SingletonHolder.getFileName()+"_Js"+SingletonHolder.getJumpSize()+"_Jpi"+SingletonHolder.getJumpsPerIncrement()+"_B"+SingletonHolder.getBrakes();
		genStart =System.currentTimeMillis();
		mp4Gen =true;
		
	}
	
	protected int[] pollTurnTrack() {
		int[] in = new int[8];
		/*in[0] = SingletonStatStore.getChosenSectOne();
		in[1] = SingletonStatStore.getChosenSectTwo();
		in[2] = SingletonStatStore.getChosenSectThree();
		in[3] = SingletonStatStore.getChosenSectFour();
		in[4] = SingletonStatStore.getChosenSectFive();
		in[5] = SingletonStatStore.getChosenSectSix();
		in[6] = SingletonStatStore.getChosenSectSeven();
		in[7] = SingletonStatStore.getChosenSectEight();*/
		return in;
	}
	
	protected void storkService(DataBundle sim) 
	{
		//if the death count is above 10k for example do the re-shuffle
		ArrayList<Integer[]> clearQueue = sim.getDeathMan().getClearQueue();
		ArrayList<Cell> storkWaiting = sim.getRepMan().getStorkWaiting();
		if(SingletonHolder.isDeath() && clearQueue.size() > 10000)
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
					sim.getCellMan().getStore().getCells().remove(foundPos);
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
							sim.getCellMan().getStore().getCells().remove(i);
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
				addShapes(sim.getCellMan().getStore().getCells().get(i),i, sim);
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
			addShapes(storkWaiting.get(i),i, sim);
		}
		sim.getRepMan().setStorkWaiting(new ArrayList<Cell>());
		
	}
	public void addShapes(Cell c, int i, DataBundle sim)
	{	
		int x = (int) (c.getPositionX()/c.getCellSize());
		int y =(int) (c.getPositionY()/c.getCellSize());
		//System.out.println(+x+" "+y);
		sim.getMoveMan().getPositionGrid().addCellPosition(c,i);
		Ellipse2D circ = new Ellipse2D.Double(c.getPositionX(),c.getPositionY(),c.getCellSize(),c.getCellSize());
		sim.getCellMan().getCircles().add(circ);
	}
	//temp methods end

	public int getSetSize() {
		return setSize;
	}

	public void setSetSize(int setSize) {
		this.setSize = setSize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public Module[] getIterationList() {
		return iterationList;
	}

	public void setIterationList(Module[] iterationList) {
		this.iterationList = iterationList;
	}
}
