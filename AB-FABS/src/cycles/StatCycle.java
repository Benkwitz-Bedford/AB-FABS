package cycles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import singleton_holders.SingletonHolder;
import file_manipulation.DataShuffler;
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
import running_modules_jump.OverlapModule;

public class StatCycle extends Cycle{
	
	private String runName = null;
	private int intTarget = 0;
	private int runs = 0;
	private boolean vidRun = true;
	private int imCount = 0;
	private int fPS = 30;
	private int time = 15;
	private int dataStart = 0;
	private int lastRead = 0;
	
	ArrayList<String[]> settingsStacks;
	ArrayList<Integer> triggerStacks;
	
	private ArrayList<String[]> legacySettingsStacks;
	ArrayList<Integer> legacyTriggerStacks;
	//Rectangle[][] mosTangles;
	BufferedImage buff = new BufferedImage(SingletonHolder.getSize(), SingletonHolder.getSize(), BufferedImage.TYPE_INT_RGB);
	
	boolean fitting = false;
	boolean vary = false;
	Double[][] fittingValues;
	String[][] varyingVals;
	//int varyTarget = 5;
	
	private boolean recording = false;
	private int finalCells = 0;
	
	public StatCycle(String name, int intervals, int statInterval, int runsNum, ArrayList<String[]> settingsStack, ArrayList<Integer> triggerStack, boolean CA, boolean fitting, Double[][] dataPassValues,String[][] variableVals)
	{
		SingletonHolder.clean();
		runName = name;
		intTarget = intervals;
		statInts=statInterval;
		this.fitting = fitting;
		this.vary = fitting;
		this.time = time;
		fittingValues = dataPassValues;
		varyingVals = variableVals;
		//need the varying variables, standardised choise grabbed from singleton
		ArrayList<String> variableNames = SingletonHolder.generateAllValues();
		
		setRuns(runsNum);
		this.sortStacks(settingsStack, triggerStack);
		if(SingletonHolder.isFileShuffle())
		{
			
			shuffle = new DataShuffler(name);
			
		}
		if(CA)
    	{
    		sim = new DataBundleCA(dimensions, posSize,parent);
    	}

		//this.setup();
	}
	

	private void sortStacks(ArrayList<String[]> settingsStack, ArrayList<Integer> triggerStack) { 
		/*triggerStack.add(4);
		triggerStack.add(8);
		triggerStack.add(1);
		triggerStack.add(9);*/
		int j;
	    boolean flag = true;   // set flag to true to begin first pass

	    while ( flag )
	    {
	           flag= false;    //set flag to false awaiting a possible swap
	           for( j=0;  j < triggerStack.size() -1;  j++ )
	           {
	                  if ( triggerStack.get(j) > triggerStack.get(j+1) )   // change to > for ascending sort
	                  {
	                	  triggerStack.add(j, triggerStack.remove(j+1));
	                	  settingsStack.add(j, settingsStack.remove(j+1));
	                      flag = true;              //shows a swap occurred  
	                  } 
	           } 
	    } 
	    /*for(int i = 0; i < triggerStack.size(); i++)
	    {
	    	System.out.println(""+triggerStack.get(i));
	    }*/

		setLegacySettingsStacks(settingsStack);
		legacyTriggerStacks = triggerStack;
	}
	@Override
	public void run()
	{
		String[] names = new String[runs];
		for(int i = 0; i < runs; i++)
		{
			names[i] = "Run "+i;
		}
		DataModule dat = new DataModule(statInts,dataStart,runName,runs,names,fitting,fittingValues/*(int) fittingValues[0],fittingValues[1]*/);
		dat.setActive(true);
		for(int runNum = 0; runNum < runs; runNum++)
		{
			System.out.println("cycle "+runNum);
			activeIteration = new Module[6];
			if(isRecording())
			{
				String[][] falseBuild = new String[1][4];
				falseBuild[0][0] = "0";
				falseBuild[0][1]="0";
				falseBuild[0][2]="0";
				falseBuild[0][3]="99999999999999";
				PopSiftModule sift = new PopSiftModule(falseBuild,0,false,true);
				sift.setExtrap(new File(runName.replace(".", "_")+".txt"));
				sift.setCellArray(getFinalCells(),SingletonHolder.getIterations());
				this.loadPopSiftModuleAdd(sift);
			}
			//System.out.println("statCycle cells-3 "+sim.getCellMan().getCells().size());
			activeJump = new Module[5];
			lastRead = dataStart;
			settingsStacks = (ArrayList<String[]>) getLegacySettingsStacks().clone();
			triggerStacks = (ArrayList<Integer>) legacyTriggerStacks.clone();
			long seed = SingletonHolder.getMasterRandom().nextLong();
			SingletonHolder.setMasterRandom(new Random(seed));
			SingletonHolder.setMasterSeed(seed);
			String[] settings = settingsStacks.remove(0);
			//System.out.println("statCycle cells-2 "+sim.getCellMan().getCells().size());
			if(vary)
			{
				Random rand = new Random();
				if(settings.length < SingletonHolder.getVarEnd()+1)
				{
					String[] cellSet = SingletonHolder.createCellSetFromSettings();
					String[] con = new String[settings.length+cellSet.length];
					for(int i = 0; i < settings.length;i++)
					{
						con[i] = settings[i];
					}
					for(int i = 0; i < cellSet.length;i++) 
					{
						con[i+settings.length] = cellSet[i];
					}
					settings = con;
				}
				for(int i = 0; i < varyingVals.length;i++)
				{
 					String[] vals = varyingVals[i];
 					double grad = rand.nextInt((int) (Double.parseDouble(vals[3])-Double.parseDouble(vals[2]))*Integer.parseInt(vals[4]));
 					grad = grad/Integer.parseInt(vals[4]);
					double val = (Double.parseDouble(vals[2])+grad);
					System.out.println("random in stat cycle "+val+" at "+vals[0]);
					if(Double.parseDouble(vals[0]) == 1)
					{
						settings[ (int)Double.parseDouble(vals[1])] = ""+val;
					}else
					{
						settings[(int) Double.parseDouble(vals[1])+SingletonHolder.getVarEnd()] = ""+val;
					}
				}
			}
			SingletonHolder.setAllValues(settings);
			triggerStacks.remove(0);
			//System.out.println("statCycle cells0 "+sim.getCellMan().getCells().size());
			this.setup();
			//System.out.println("statCycle cells1 "+sim.getCellMan().getCells().size());
			int pos = this.getPosSize();
			
			//this.setVisible(true);
			SingletonHolder.setRunning(true);
			//turn this off for pretty
			//System.out.println("statCycle cells2 "+sim.getCellMan().getCells().size());
			this.checkStatFlags(runNum);
			//System.out.println("statCycle cells3 "+sim.getCellMan().getCells().size());
			//add modules
			TurnModule turn = new TurnModule();
			this.loadTurnModule(turn);
			IncrementModule inc = new IncrementModule();
			this.loadIncrementModule(inc);
			this.loadDataModule(dat);
			OverlapModule ovr = new OverlapModule();
			this.loadOverlapModule(ovr);
			do{
				//cycle can iterate through record with iteration being decided by travel cycles between each, apply traj changes for current increment then commit and gate off until distance met

				jumpList[0].genNext(sim);
				if(SingletonHolder.getJumpCounter()/SingletonHolder.getJumpsPerIncrement()>SingletonHolder.getIncrement())
				{
					if(triggerStacks.size()>0)
					{
						if(SingletonHolder.getIncrement() == triggerStacks.get(0))
						{
							SingletonHolder.setAllValues(settingsStacks.remove(0));
							triggerStacks.remove(0);
							this.checkStatFlags(runNum);
						}
					}
					for(int i = 0; i < getIterationList().length;i++)
					{
						getIterationList()[i].genNext(sim);
						//System.out.println(iterationList.get(i).getModType());
					}
					
					
					SingletonHolder.incIncrement();
					//feedback.setText("Filename: "+fileName+" Running: "+running+" Jump Size: "+jumpSize+" Jumps Per Increment: "+jumpsPerIncrement+" Brake size: "+brakes+" Increment: "+increment);
				}
				
				for(int i = 1; i < jumpList.length;i++)
				{
					jumpList[i].genNext(sim);
					//System.out.println(jumpList.get(i).getModType());
				}
				SingletonHolder.incJumpCounter();
				this.updateCells(sim.getCellMan().getStore().getCells());
		
			}while(SingletonHolder.getIncrement() <= intTarget);
			System.out.println("attE "+sim.getBezMan().getAttE()+" attW "+sim.getBezMan().getAttW()+" travE "+sim.getBezMan().getTravE()+" travW "+sim.getBezMan().getTravW());
		}
		SingletonHolder.setRunning(false);
		dat.top10Check();
		System.out.println("ended");
		
	}
	
	
	public void checkFlags() 
	   {
		
	   }
	private void checkStatFlags(int runNum) {
		System.out.println("checking flags "+runNum);
		if(SingletonHolder.isReplication())
		{
			//System.out.println("brap");
			repVarNeeded = true;
			//repChange();
			cellChanges();
			ReplicationModule rep = new ReplicationModule();
			this.loadRepModule(rep);
			StorkModule str = new StorkModule();
			this.loadStorkModule(str);
		}else
		{
			this.unloadRepModule();
		}
		if(SingletonHolder.isDeath())
		{
			deathVarNeeded = true;
			//deathChange();
			cellChanges();
			DeathModule det = new DeathModule();
			this.loadDeathModule(det);
			StorkModule str = new StorkModule();
			this.loadStorkModule(str);
		}else
		{
			this.unloadDeathModule();
		}
		if(SingletonHolder.isSpeedFlag())
		{
			speedVarNeeded = true;
			//speedChange();
			cellChanges();
		}
		
		if(SingletonHolder.isBezFlag())
		{
			if(runNum > 0 && SingletonHolder.isBezRetainer())
			{
				//System.out.println("repo bez a");
				sim.getBezMan().bezReproduce(sim.getBezMan().getCurves(),sim.getBezMan().getCurvePoints(), sim.getCellMan().getCells(), sim.getMoveMan().getPositionGrid(), sim.getBezMan().getPrevPoints());
				//System.out.println("bez b");
			}else
			{
				bezVarNeeded = true;
				sim.getBezMan().bezChange(sim.getCellMan().getCells(), sim.getMoveMan().getPositionGrid());
				//System.out.println("bez c");
				sim.getBezMan().setPreviousPoints(sim.getMoveMan().getPositionGrid(), SingletonHolder.getBezSize());
				//System.out.println("bez d");
			//SingletonHolder.setBoundCheckNeeded(true);
			}
		}
		if(SingletonHolder.isBoundCheckNeeded())
		{
			if(SingletonHolder.isBoundary())
			{
				sim.getCellMan().getStore().setCells(sim.getMoveMan().checkAllAndFixWithinBounds(sim.getCellMan().getStore().getCells(), sim.getMoveMan().getPositionGrid()));
				//SingletonHolder.setBoundCheckNeeded(false);
				BoundryModule bound = new BoundryModule();
				this.loadBoundaryModule(bound);
			}
		}else
		{
			this.unLoadBoundryModule();
		}
		
	}
	public String getRunName() {
		return runName;
	}

	public void setRunName(String runName) {
		this.runName = runName;
	}

	public int getIntTarget() {
		return intTarget;
	}

	public void setIntTarget(int intTarget) {
		this.intTarget = intTarget;
	}

	
	public int getRuns() {
		return runs;
	}
	public void setRuns(int runs) {
		this.runs = runs;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public boolean isVidRun() {
		return vidRun;
	}
	public void setVidRun(boolean vidRun) {
		this.vidRun = vidRun;
	}
	public void setFPS(int parseInt) {
		fPS = parseInt;
	}
	public int getDataStart() {
		return dataStart;
	}
	public void setDataStart(int dataStart) {
		this.dataStart = dataStart;
	}

	public boolean isRecording() {
		return recording;
	}

	public void setRecording(boolean recording) {
		this.recording = recording;
	}

	public int getFinalCells() {
		return finalCells;
	}

	public void setFinalCells(int finalCells) {
		this.finalCells = finalCells;
	}


	public ArrayList<String[]> getLegacySettingsStacks() {
		return legacySettingsStacks;
	}


	public void setLegacySettingsStacks(ArrayList<String[]> legacySettingsStacks) {
		this.legacySettingsStacks = legacySettingsStacks;
	}

}
