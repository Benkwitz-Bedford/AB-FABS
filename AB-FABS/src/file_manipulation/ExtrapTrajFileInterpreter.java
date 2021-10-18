package file_manipulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExtrapTrajFileInterpreter {
	//[time interval,cell num, x, y]
	private ArrayList<double[]> lifeEvents = new ArrayList<double[]>();
	//[time interval, cell num]
	private ArrayList<double[]> deathEvents = new ArrayList<double[]>();
	//[time interval, cell num, vector, speed]
	private ArrayList<double[]> trajEvents = new ArrayList<double[]>();
	private int stepSize = 0;
	private int gridSize = 0;
	private int timeSteps = 0;
	private int totalCells = 0;
	private String fileName ="";

	public ExtrapTrajFileInterpreter(File file)
	{
		String first = null;
		String second = null;
		String third = null;
		Scanner read;
		fileName = file.getName();
		try {
			read = new Scanner (file);
		
		first = read.nextLine();
		second = read.nextLine();
		third = read.nextLine();
		read.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("loading failed");
			System.exit(0);
			e.printStackTrace();
		}
		String[] firstBreak = first.split(" ");
		String[] stepSplit = firstBreak[2].split(":");
		String[] gridSplit = firstBreak[3].split(":");
		String[] timeSplit = firstBreak[1].split(":");
		String[] totalSplit = firstBreak[0].split(":");
		stepSize = Integer.parseInt(stepSplit[1]);
		gridSize = (int) Double.parseDouble(gridSplit[1]);
		timeSteps = Integer.parseInt(timeSplit[1]);
		totalCells = Integer.parseInt(totalSplit[1]);
		
		String[] secondBreak = second.split(":");
		String[] breakOrigin = secondBreak[1].split("n");
		double[][] cellStarts = new double[breakOrigin.length-1][6];
		for(int i = 1; i < breakOrigin.length;i++)
		{
			String[] startBreakOne = breakOrigin[i].split(",");
			if(startBreakOne.length == 7)
			{
			cellStarts[i-1] = new double[]{Double.parseDouble(startBreakOne[0]),
					Double.parseDouble(startBreakOne[1]),Double.parseDouble(startBreakOne[2]),
						Double.parseDouble(startBreakOne[3]),Double.parseDouble(startBreakOne[4]),Double.parseDouble(startBreakOne[5]),Double.parseDouble(startBreakOne[6])};
			}else
			{
				cellStarts[i-1] = new double[]{Double.parseDouble(startBreakOne[0]),
						Double.parseDouble(startBreakOne[1]),Double.parseDouble(startBreakOne[2]),
							Double.parseDouble(startBreakOne[3]),Double.parseDouble(startBreakOne[4]),Double.parseDouble(startBreakOne[5])};
			}
		}
		//split into life events and death events
		//'start vector, position x, position y, start speed, start time and life time'
		//[time interval,cell num, x, y, vector, speed]
		//[time interval, cell num]
		for(int i = 0; i < cellStarts.length; i++ )
		{
			if(cellStarts[i].length == 7)
			{
				//st.append("n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime()+","+sub.getOldID());
				double[] life = new double[]{cellStarts[i][4],i,cellStarts[i][1],cellStarts[i][2],cellStarts[i][0],cellStarts[i][3],cellStarts[i][6]};	
			}
			double[] life = new double[]{cellStarts[i][4],i,cellStarts[i][1],cellStarts[i][2],cellStarts[i][0],cellStarts[i][3]};
			double[] death = new double[]{cellStarts[i][4]+cellStarts[i][5],i};
			lifeEvents.add(life);
			deathEvents.add(death);
			
		}
		//split traj events that are [time, cell, vector, speed]
		//[time interval, cell num, vector, speed]
		secondBreak = third.split(":");
		breakOrigin = secondBreak[1].split("n");
		double[][] trajEvn = new double[breakOrigin.length][4];
		for(int i = 1; i < trajEvn.length;i++)
		{
			String[] startBreakOne = breakOrigin[i].split(",");
			trajEvn[i] = new double[]{Double.parseDouble(startBreakOne[0]),
					Double.parseDouble(startBreakOne[1]),Double.parseDouble(startBreakOne[2]),
					Double.parseDouble(startBreakOne[3])};
		}
		for(int i = 0; i < trajEvn.length; i++ )
		{
			double[] evn = new double[]{trajEvn[i][0],trajEvn[i][1],trajEvn[i][2],trajEvn[i][3]};
			trajEvents.add(evn);
			
		}
		//bubble sort all
		int j;
	    boolean flag = true;   // set flag to true to begin first pass
	    double[] temp;   //holding variable

	    while ( flag )
	    {
	        flag= false;    //set flag to false awaiting a possible swap
	        for( j=0;  j < lifeEvents.size() -1;  j++ )
	        {
	        	if ( lifeEvents.get(j)[0] > lifeEvents.get(j+1)[0] )   // change to > for ascending sort
	            {
	                  temp = lifeEvents.get(j);                //swap elements
	                  lifeEvents.set(j,lifeEvents.get(j+1));
	                  lifeEvents.set(j+1,temp);
	                  flag = true;              //shows a swap occurred  
	            } 
	        } 
	    } 
	    flag = true;
	    while ( flag )
	    {
	        flag= false;    //set flag to false awaiting a possible swap
	        for( j=0;  j < deathEvents.size() -1;  j++ )
	        {
	        	if ( deathEvents.get(j)[0] > deathEvents.get(j+1)[0] )   // change to > for ascending sort
	            {
	                  temp = deathEvents.get(j);                //swap elements
	                  deathEvents.set(j,deathEvents.get(j+1));
	                  deathEvents.set(j+1,temp);
	                  flag = true;              //shows a swap occurred  
	            } 
	        } 
	    } 
	    flag = true;
	    
	    while ( flag )
	    {
	        flag= false;    //set flag to false awaiting a possible swap
	        for( j=0;  j < trajEvents.size() -1;  j++ )
	        {
	        	if ( trajEvents.get(j)[0] > trajEvents.get(j+1)[0] )   // change to > for ascending sort
	            {
	                  temp = trajEvents.get(j);                //swap elements
	                  trajEvents.set(j,trajEvents.get(j+1));
	                  trajEvents.set(j+1,temp);
	                  flag = true;              //shows a swap occurred  
	            } 
	        } 
	    } 
	}

	public ArrayList<double[]> getLifeEvents() {
		return lifeEvents;
	}

	public void setLifeEvents(ArrayList<double[]> lifeEvents) {
		this.lifeEvents = lifeEvents;
	}

	public ArrayList<double[]> getDeathEvents() {
		return deathEvents;
	}

	public void setDeathEvents(ArrayList<double[]> deathEvents) {
		this.deathEvents = deathEvents;
	}

	public ArrayList<double[]> getTrajEvents() {
		return trajEvents;
	}

	public void setTrajEvents(ArrayList<double[]> trajEvents) {
		this.trajEvents = trajEvents;
	}

	public int getStepSize() {
		return stepSize;
	}

	public void setStepSize(int stepSize) {
		this.stepSize = stepSize;
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public int getTimeSteps() {
		return timeSteps;
	}

	public void setTimeSteps(int timeSteps) {
		this.timeSteps = timeSteps;
	}

	public int getTotalCells() {
		return totalCells;
	}

	public void setTotalCells(int totalCells) {
		this.totalCells = totalCells;
	}

	public String getFileName() {
		// TODO Auto-generated method stub
		return fileName;
	}

	public static int grabLength(File file) {
		String first = null;
		Scanner read;
		try {
			read = new Scanner (file);
		
		first = read.nextLine();
		read.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("loading failed");
			System.exit(0);
			e.printStackTrace();
		}
		String[] firstBreak = first.split(" ");
		String[] timeSplit = firstBreak[1].split(":");
		int tim = Integer.parseInt(timeSplit[1]);
		return tim;
	}
	
}
