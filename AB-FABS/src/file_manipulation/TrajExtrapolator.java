package file_manipulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import singleton_holders.SingletonHolder;
import cell_data_holders.TempCellConstruct;
import gui_main.Visualiser;

public class TrajExtrapolator {

	public void extrapolateFromMatTxtLinearMorph(File file) throws FileNotFoundException 
	{
		//need cell num, time steps, step size, grid size
		//for all cells need start vector, position x position y and start time
		//all changes need to be [time, cell, vector, speed] sorted by time
		int cellNum = 0;
		int timeSteps = 0;
		int stepSize = 6;
		int gridSize = 1500;
		ArrayList<double[]> cellStarts = new ArrayList<double[]>();
		ArrayList<double[]> events = new ArrayList<double[]>();
		//seek max lifetime
		Scanner read = new Scanner (file);
		read.nextLine();
		int largest = 0;
		String[] split;
		String first;
		do
		{
			first = read.nextLine();
			split = first.split(",");
			int comparator = Integer.parseInt(split[1]);
			if(comparator > largest)
			{
				largest = comparator;
			}
		}while(read.hasNextLine());
		
		//create 'cell' constructs with b time d time l time start pos and x/y * l time positions
		//extrapolate positions into trajectory changes, time, cell, vector, speed
		ArrayList<TempCellConstruct> cells = new ArrayList<TempCellConstruct>();
		
		read = new Scanner (file);
		read.nextLine();
		do
		{
			first = read.nextLine();
			TempCellConstruct temp = new TempCellConstruct(first, cells.size(),largest, stepSize);
			temp.setPositions(temp.extrapolateFromString(first, largest));
			cells.add(temp);
		}while(read.hasNextLine());
		
		//for all events find the smallest and largest x and y
		double smallestX = 0.00;
		double smallestY = 0.00;
		double largestX = 0.00;
		double largestY = 0.00;
		for(int i = 0; i < cells.size(); i++)
		{
			double sX = cells.get(i).getSmallestXPoint();
			double sY = cells.get(i).getSmallestYPoint();
			double lX = cells.get(i).getLargestXPoint();
			double lY = cells.get(i).getLargestYPoint();
			if (sX < smallestX )
			{
				smallestX = sX;
			}
			if (sY < smallestY )
			{
				smallestY = sY;
			}
			if (lX > largestX )
			{
				largestX = lX;
			}
			if (lY > largestY )
			{
				largestY = lY;
			}
		}
		//mod all to increment and make smallest 0
		for(int i = 0; i < cells.size(); i++)
		{
			cells.get(i).moveAllPositionsBy(0-smallestX,0-smallestY);
		}
		//compress all by ratio of largest to ideal display area
		for(int i = 0; i < cells.size(); i++)
		{
			cells.get(i).compressAllPositionsByWithFlip(gridSize/largestX,gridSize/largestY, gridSize);
		}
		for(int i = 0; i < cells.size(); i++)
		{
			if(cells.get(i).getPositions().size() == 1)
			{
				cells.remove(i);
				i--;
			}else
			{
				cells.get(i).setEvents(cells.get(i).extrapolateEventsFromPositions());
				
			}
		}
		//get largest point
		double larg = 0.00;
		for(int i = 0; i < cells.size(); i++)
		{
			double comp = cells.get(i).getLargestPoint(); 
			if (comp > larg)
			{
				larg = comp;
			}
		}
		
		//shuffle all events into ordered pile
		ArrayList<Integer> polling = new ArrayList<Integer>();
		ArrayList<Double[]> pile = new ArrayList<Double[]>();
		for(int i = 0; i < cells.size(); i++)
		{
			polling .add(i);
		}
		int timePointer = 0;
		
		do
		{
			for(int i = 0; i < polling.size();i++)
			{
				ArrayList<Double[]> evns = cells.get(polling.get(i)).getEvents();
				do
				{
					if(evns.get(0)[0]==timePointer)
					{
						pile.add(evns.remove(0));
					}
				}while(evns.size()> 0 && evns.get(0)[0]==timePointer);
				if(evns.size() == 0)
				{
					polling.remove(i);
					i--;
				}
			}
			timePointer++;
		}while (polling.size() != 0); 
		//output top line //need cell num, time steps, step size, grid size
		String top =  "cellNum:"+cells.size()+" timeSteps:"+largest+" stepSize:"+stepSize+" gridSize:"+(gridSize);
		//output starts
		//for all cells need start vector, position x, position y, start speed, start time and life time
		
		String starts = "starting vectors:";
		StringBuilder st = new StringBuilder();
		st.append(starts);
		for(int i = 0; i < cells.size(); i++)
		{
			TempCellConstruct sub = cells.get(i);
			st.append("n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime()+","+sub.getOldID());
			//starts += "n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime();
		}
		
		//output events
		//all changes need to be [time, cell, vector, speed] sorted by time
		String eventVect = "changing vectors:";
		System.out.println("p "+pile.size());
		StringBuilder sb = new StringBuilder();
		sb.append(eventVect);
		for(int i = 0; i < pile.size(); i++)
		{
			Double[] evn = pile.get(i);
			sb.append("n"+evn[0]+","+evn[1]+","+evn[2]+","+evn[3]);
			//eventVect += "n"+evn[0]+","+evn[1]+","+evn[2]+","+evn[3];
		}
		File file2 = new File(System.getProperty("user.dir")+"//"+file.getName().replace(".txt", "")+"extrapolated.txt");
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println(top);
			writer.println(st.toString());
			writer.println(sb.toString());
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void extrapolateFromCSV(File file) throws FileNotFoundException 
	{
		//need cell num, time steps, step size, grid size
		//for all cells need start vector, position x position y and start time
		//all changes need to be [time, cell, vector, speed] sorted by time
		
		int stepSize = 2;
		int gridSize = 1500;
		//seek max lifetime
		Scanner read = new Scanner (file);
		read.nextLine();
		int largest = 0;
		
		//create 'cell' constructs with b time d time l time start pos and x/y * l time positions
		//extrapolate positions into trajectory changes, time, cell, vector, speed
		ArrayList<TempCellConstruct> cells = new ArrayList<TempCellConstruct>();
		int previousID = 1;
		String[] splitter = null;
		do
		{
			splitter = read.nextLine().split(",");
			previousID = Integer.parseInt(splitter[1]);
			TempCellConstruct temp = new TempCellConstruct(stepSize, previousID);
			ArrayList<Double[]> positions = new ArrayList<Double[]>();
			do
			{
				positions.add(new Double[]{Double.parseDouble(splitter[3]),Double.parseDouble(splitter[4]),(double) Integer.parseInt(splitter[2])});
				if(Integer.parseInt(splitter[2]) > largest)
				{
					largest = Integer.parseInt(splitter[2]);
				}
				//System.out.println(""+uni);
				if(read.hasNextLine())
				{
					splitter = read.nextLine().split(",");
				}
			}while(Integer.parseInt(splitter[1]) == previousID && read.hasNextLine());
			temp.setPositions(positions);
			temp.figureMetaFromPositions();
			cells.add(temp);
		}while(read.hasNextLine());
		
		//for all events find the smallest and largest x and y
		double smallestX = 0.00;
		double smallestY = 0.00;
		double largestX = 0.00;
		double largestY = 0.00;
		for(int i = 0; i < cells.size(); i++)
		{
			double sX = cells.get(i).getSmallestXPoint();
			double sY = cells.get(i).getSmallestYPoint();
			double lX = cells.get(i).getLargestXPoint();
			double lY = cells.get(i).getLargestYPoint();
			if (sX < smallestX )
			{
				smallestX = sX;
			}
			if (sY < smallestY )
			{
				smallestY = sY;
			}
			if (lX > largestX )
			{
				largestX = lX;
			}
			if (lY > largestY )
			{
				largestY = lY;
			}
		}
		//mod all to increment and make smallest 0
		for(int i = 0; i < cells.size(); i++)
		{
			cells.get(i).moveAllPositionsBy(0-smallestX,0-smallestY);
			//cells.get(i).moveAllPositionsBy(0.1,0.1);
			
		}
		//compress all by ratio of largest to ideal display area
		System.out.println("multi by "+gridSize/largestX+" and "+gridSize/largestY);
		for(int i = 0; i < cells.size(); i++)
		{
			cells.get(i).compressAllPositionsByWithoutFlip(gridSize/largestX,gridSize/largestY, gridSize);
			//cells.get(i).compressAllPositionsBy(54.00,54.00, gridSize);
		}
		for(int i = 0; i < cells.size(); i++)
		{
			//System.out.println("extrap "+i);
			if(cells.get(i).getPositions().size() == 1)
			{
				cells.remove(i);
				i--;
			}else
			{
				cells.get(i).setEvents(cells.get(i).extrapolateEventsFromPositions());
				
			}
		}
		//get largest point
		double larg = 0.00;
		for(int i = 0; i < cells.size(); i++)
		{
			double comp = cells.get(i).getLargestPoint(); 
			if (comp > larg)
			{
				larg = comp;
			}
		}
		System.out.println("not stuk0");
		
		//shuffle all events into ordered pile
		ArrayList<Integer> polling = new ArrayList<Integer>();
		ArrayList<Double[]> pile = new ArrayList<Double[]>();
		//sort cells by spawn time and reassign id
		 int n = cells.size();  
	     TempCellConstruct temp = null;  
	     for(int i=0; i < n; i++)
	     {  
	    	 for(int j=1; j < (n-i); j++)
	    	 {  
	    		 if(cells.get(j-1).getBirthTime() > cells.get(j).getBirthTime())
	    		 {  
	    			 //swap elements  
	                 temp = cells.get(j-1).copy(); 
	                 cells.set(j-1, cells.get(j));  
	                 cells.set(j, temp);  
	             }  
	                          
	    	 }  
	     }  
	     for(int i = 0; i < cells.size(); i++)
	     {
	    	 //System.out.println(""+cells.get(i).getBirthTime());
	    	 cells.get(i).setIdUpdate(i);
	     }
	     //System.exit(0);
		for(int i = 0; i < cells.size(); i++)
		{
			polling .add(i);
		}
		int timePointer = 0;
		System.out.println("not stuk1");
		do
		{
			for(int i = 0; i < polling.size();i++)
			{
				ArrayList<Double[]> evns = cells.get(polling.get(i)).getEvents();
				do
				{
					if(evns.get(0)[0]==timePointer)
					{
						pile.add(evns.remove(0));
					}
				}while(evns.size()> 0 && evns.get(0)[0]==timePointer);
				if(evns.size() == 0)
				{
					polling.remove(i);
					i--;
				}
			}
			timePointer++;
		}while (polling.size() != 0); 
		
		System.out.println("not stuk2");
		//output top line //need cell num, time steps, step size, grid size
		String top =  "cellNum:"+cells.size()+" timeSteps:"+largest+" stepSize:"+stepSize+" gridSize:"+(gridSize);
		//output starts
		//for all cells need start vector, position x, position y, start speed, start time and life time
		String starts = "starting vectors:";
		System.out.println("c "+cells.size());
		StringBuilder st = new StringBuilder();
		st.append(starts);
		for(int i = 0; i < cells.size(); i++)
		{
			TempCellConstruct sub = cells.get(i);
			st.append("n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime()+","+sub.getOldID());
			//starts += "n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime();
		}
		//output events
		//all changes need to be [time, cell, vector, speed] sorted by time
		String eventVect = "changing vectors:";
		System.out.println("p "+pile.size());
		StringBuilder sb = new StringBuilder();
		sb.append(eventVect);
		for(int i = 0; i < pile.size(); i++)
		{
			Double[] evn = pile.get(i);
			sb.append("n"+evn[0]+","+evn[1]+","+evn[2]+","+evn[3]);
			//eventVect += "n"+evn[0]+","+evn[1]+","+evn[2]+","+evn[3];
		}
		File file2 = new File(System.getProperty("user.dir")+"//"+file.getName().replace(".csv", "")+"extrapolated.txt");
		System.out.println("pile on construct done "+pile.size());
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println(top);
			writer.println(st.toString());
			writer.println(sb.toString());
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExtrapTrajFileInterpreter tFI = new ExtrapTrajFileInterpreter(file2);
		//hand cells to visualiser and run
		Visualiser vis = new Visualiser(file2.getName(),tFI,false);
		SingletonHolder.setFileName(file2.getName());
		vis.run();
	}
	public void extrapolateFromTrackMateCSV(File file) throws FileNotFoundException 
	{
		//need cell num, time steps, step size, grid size
		//for all cells need start vector, position x position y and start time
		//all changes need to be [time, cell, vector, speed] sorted by time
		
		int stepSize = 6;
		double gridSize = 1500.00;
		//seek max lifetime
		Scanner read = new Scanner (file);
		read.nextLine();
		read.nextLine();
		read.nextLine();
		read.nextLine();
		double largest = 0.0;
		
		//create 'cell' constructs with b time d time l time start pos and x/y * l time positions
		//extrapolate positions into trajectory changes, time, cell, vector, speed
		ArrayList<TempCellConstruct> cells = new ArrayList<TempCellConstruct>();
		int previousID = 1;
		String[] splitter = null;
		splitter = read.nextLine().split(",");
		int uni = 0;
		do
		{
			TempCellConstruct temp = new TempCellConstruct(stepSize, uni);
			//x,y,time
			ArrayList<Double[]> positions = new ArrayList<Double[]>();
			do
			{
				positions.add(new Double[]{Double.parseDouble(splitter[4]),Double.parseDouble(splitter[5]),(double) Double.parseDouble(splitter[7])});
				if(Double.parseDouble(splitter[7]) > largest)
				{
					largest = Double.parseDouble(splitter[7]);
				}
				//System.out.println(""+uni);
				if(read.hasNextLine())
				{
					splitter = read.nextLine().split(",");
				}
			//}while(Integer.parseInt(splitter[0]) == (positions.get(positions.size()-1)[2].intValue()+1)||Integer.parseInt(splitter[0]) == (positions.get(positions.size()-1)[2].intValue()) && read.hasNextLine());
			}while(Double.parseDouble(splitter[7]) == (positions.get(positions.size()-1)[2]+1) && read.hasNextLine());
			temp.setPositions(positions);
			temp.figureMetaFromPositions();
			cells.add(temp);
			uni++;
		}while(read.hasNextLine());
		
		//for all events find the smallest and largest x and y
		double smallestX = 2000.00;
		double smallestY = 2000.00;
		double largestX = 0.00;
		double largestY = 0.00;
		for(int i = 0; i < cells.size(); i++)
		{
			double sX = cells.get(i).getSmallestXPoint();
			double sY = cells.get(i).getSmallestYPoint();
			double lX = cells.get(i).getLargestXPoint();
			double lY = cells.get(i).getLargestYPoint();
			if (sX < smallestX )
			{
				smallestX = sX;
			}
			if (sY < smallestY )
			{
				smallestY = sY;
			}
			if (lX > largestX )
			{
				largestX = lX;
			}
			if (lY > largestY )
			{
				largestY = lY;
			}
		}
		//mod all to increment and make smallest 0
		for(int i = 0; i < cells.size(); i++)
		{
			cells.get(i).moveAllPositionsBy(0-smallestX,0-smallestY);
			//cells.get(i).moveAllPositionsBy(0.1,0.1);
			
		}
		//compress all by ratio of largest to ideal display area
		System.out.println("multi by "+gridSize/largestX+" and "+gridSize/largestY);
		for(int i = 0; i < cells.size(); i++)
		{
			//cells.get(i).compressAllPositionsByWithoutFlip(gridSize/largestX,gridSize/largestY, gridSize);
			cells.get(i).compressAllPositionsByWithFlip(gridSize/largestX,gridSize/largestY,(int) gridSize);
			//cells.get(i).compressAllPositionsBy(54.00,54.00, gridSize);
		}
		for(int i = 0; i < cells.size(); i++)
		{
			//System.out.println("extrap "+i);
			if(cells.get(i).getPositions().size() == 1)
			{
				cells.remove(i);
				i--;
			}else
			{
				cells.get(i).setEvents(cells.get(i).extrapolateEventsFromPositions());
				
			}
		}
		//get largest point
		double larg = 0.00;
		for(int i = 0; i < cells.size(); i++)
		{
			double comp = cells.get(i).getLargestPoint(); 
			if (comp > larg)
			{
				larg = comp;
			}
		}
		System.out.println("not stuk0");
		
		//shuffle all events into ordered pile
		ArrayList<Integer> polling = new ArrayList<Integer>();
		ArrayList<Double[]> pile = new ArrayList<Double[]>();
		//sort cells by spawn time and reassign id
		 int n = cells.size();  
	     TempCellConstruct temp = null;  
	     for(int i=0; i < n; i++)
	     {  
	    	 for(int j=1; j < (n-i); j++)
	    	 {  
	    		 if(cells.get(j-1).getBirthTime() > cells.get(j).getBirthTime())
	    		 {  
	    			 //swap elements  
	                 temp = cells.get(j-1).copy(); 
	                 cells.set(j-1, cells.get(j));  
	                 cells.set(j, temp);  
	             }  
	                          
	    	 }  
	     }  
	     for(int i = 0; i < cells.size(); i++)
	     {
	    	 //System.out.println(""+cells.get(i).getBirthTime());
	    	 cells.get(i).setIdUpdate(i);
	     }
	     //System.exit(0);
		for(int i = 0; i < cells.size(); i++)
		{
			polling .add(i);
		}
		int timePointer = 0;
		System.out.println("not stuk1");
		do
		{
			for(int i = 0; i < polling.size();i++)
			{
				ArrayList<Double[]> evns = cells.get(polling.get(i)).getEvents();
				do
				{
					if(evns.get(0)[0]==timePointer)
					{
						pile.add(evns.remove(0));
					}
				}while(evns.size()> 0 && evns.get(0)[0]==timePointer);
				if(evns.size() == 0)
				{
					polling.remove(i);
					i--;
				}
			}
			timePointer++;
		}while (polling.size() != 0); 
		
		System.out.println("not stuk2");
		//output top line //need cell num, time steps, step size, grid size
		String top =  "cellNum:"+cells.size()+" timeSteps:"+(int)largest+" stepSize:"+stepSize+" gridSize:"+(gridSize);
		//output starts
		//for all cells need start vector, position x, position y, start speed, start time and life time
		String starts = "starting vectors:";
		System.out.println("c "+cells.size());
		StringBuilder st = new StringBuilder();
		st.append(starts);
		for(int i = 0; i < cells.size(); i++)
		{
			TempCellConstruct sub = cells.get(i);
			st.append("n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime()+","+sub.getOldID());
			//starts += "n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime();
		}
		//output events
		//all changes need to be [time, cell, vector, speed] sorted by time
		String eventVect = "changing vectors:";
		System.out.println("p "+pile.size());
		StringBuilder sb = new StringBuilder();
		sb.append(eventVect);
		for(int i = 0; i < pile.size(); i++)
		{
			Double[] evn = pile.get(i);
			sb.append("n"+evn[0]+","+evn[1]+","+evn[2]+","+evn[3]);
			//eventVect += "n"+evn[0]+","+evn[1]+","+evn[2]+","+evn[3];
		}
		File file2 = new File(System.getProperty("user.dir")+"//"+file.getName().replace(".csv", "")+"extrapolated.txt");
		System.out.println("pile on construct done "+pile.size());
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println(top);
			writer.println(st.toString());
			writer.println(sb.toString());
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*ExtrapTrajFileInterpreter tFI = new ExtrapTrajFileInterpreter(file2);
		//hand cells to visualiser and run
		Visualiser vis = new Visualiser(file2.getName(),tFI,false);
		SingletonHolder.setFileName(file2.getName());
		vis.run();*/

		System.exit(0);
	}
	
	public void extrapolateFromDavideMatTxtLinearMorph(File file) throws FileNotFoundException 
	{
		//need cell num, time steps, step size, grid size
		//for all cells need start vector, position x position y and start time
		//all changes need to be [time, cell, vector, speed] sorted by time
		
		int gridSize = 1500;
		//seek max lifetime
		Scanner read = new Scanner (file);
		String[] pointers = read.nextLine().split(",");
		String[] classes = read.nextLine().split(",");
		read.nextLine();
		String first;
		int largest = 401;
		
		/*JOptionPane opt = new JOptionPane();
		opt.setInitialValue(""+0);
		double dt = Double.parseDouble(JOptionPane.showInputDialog( "x shift", ""+0));
		
		opt.setInitialValue(""+1);
		int stepSize = Integer.parseInt(JOptionPane.showInputDialog( "steps", ""+1));*/
		
		double dt = 0;
		int stepSize = 3;
		//create 'cell' constructs with b time d time l time start pos and x/y * l time positions
		//extrapolate positions into trajectory changes, time, cell, vector, speed
		ArrayList<TempCellConstruct> cells = new ArrayList<TempCellConstruct>();
		
		int uni = 0;
		do
		{
			first = read.nextLine();
			//>=0
			//1 for in bound
			if(Integer.parseInt(classes[uni])>=1)
			{
				TempCellConstruct temp = new TempCellConstruct(stepSize, uni);
				//code to switch between dupe coping and NaN coping
				boolean naan = first.contains("NaN");
				if(naan)
				{
					if(temp.parseDavideLineForNaN(first))
					{
						
						temp.setOldID(Integer.parseInt(pointers[uni+1]));
						cells.add(temp);
					}else
					{
						uni--;
					}
				}else
				{
					temp.parseDavideLineForDupes(first);
					temp.setOldID(Integer.parseInt(pointers[uni+1]));
					cells.add(temp);
				}
			}
			uni++;
		}while(read.hasNextLine());
		
		//for all events find the smallest and largest x and y
		double smallestX = 9999999.00;
		double smallestY = 9999999.00;
		double largestX = 0.00;
		double largestY = 0.00;
		largest = 0;
		for(int i = 0; i < cells.size(); i++)
		{
			double sX = cells.get(i).getSmallestXPoint();
			double sY = cells.get(i).getSmallestYPoint();
			double lX = cells.get(i).getLargestXPoint();
			double lY = cells.get(i).getLargestYPoint();
			if(cells.get(i).getDeathTime()>largest)
			{
				largest = cells.get(i).getDeathTime();
			}
			if (sX < smallestX )
			{
				smallestX = sX;
			}
			if (sY < smallestY )
			{
				smallestY = sY;
			}
			if (lX > largestX )
			{
				largestX = lX;
			}
			if (lY > largestY )
			{
				largestY = lY;
			}
		}
		//mod all to increment and make smallest 0
		//tab or un tab here for davide stuff
		for(int i = 0; i < cells.size(); i++)
		{
			cells.get(i).moveAllPositionsBy(0-smallestX+dt,0-smallestY);
			//cells.get(i).moveAllPositionsBy(0.1,0.1);
			//cells.get(i).moveAllPositionsBy(0.1,0.1);
			
		}
		//compress all by ratio of largest to ideal display area
		System.out.println("multi by "+(gridSize-100)/largestX+" and "+(gridSize-100)/largestY);
		for(int i = 0; i < cells.size(); i++)
		{
			cells.get(i).compressAllPositionsByWithFlip((gridSize)/largestX,(gridSize)/largestY, (gridSize));
			//cells.get(i).compressAllPositionsByWithoutFlip(gridSize/largestX,gridSize/largestY, gridSize);
			//cells.get(i).compressAllPositionsByWithFlip(54.00,54.00, gridSize);  
			//cells.get(i).compressAllPositionsByWithFlip(50.00,50.00, gridSize);
		}
		for(int i = 0; i < cells.size(); i++)
		{
			//System.out.println("extrap "+i);
			if(cells.get(i).getPositions().size() == 1)
			{
				cells.remove(i);
				i--;
			}else
			{
				cells.get(i).setEvents(cells.get(i).extrapolateEventsFromPositions());
				
			}
		}
		//get largest point
		double larg = 0.00;
		for(int i = 0; i < cells.size(); i++)
		{
			double comp = cells.get(i).getLargestPoint(); 
			if (comp > larg)
			{
				larg = comp;
			}
		}
		System.out.println("not stuk0");
		
		//shuffle all events into ordered pile
		ArrayList<Integer> polling = new ArrayList<Integer>();
		ArrayList<Double[]> pile = new ArrayList<Double[]>();
		//sort cells by spawn time and reassign id
		 int n = cells.size();  
	     TempCellConstruct temp = null;  
	     for(int i=0; i < n; i++)
	     {  
	    	 for(int j=1; j < (n-i); j++)
	    	 {  
	    		 if(cells.get(j-1).getBirthTime() > cells.get(j).getBirthTime())
	    		 {  
	    			 //swap elements  
	                 temp = cells.get(j-1).copy(); 
	                 cells.set(j-1, cells.get(j));  
	                 cells.set(j, temp);  
	             }  
	                          
	    	 }  
	     }  
	     for(int i = 0; i < cells.size(); i++)
	     {
	    	 //System.out.println(""+cells.get(i).getBirthTime());
	    	 cells.get(i).setIdUpdate(i);
	     }
	     //System.exit(0);
		for(int i = 0; i < cells.size(); i++)
		{
			polling .add(i);
		}
		int timePointer = 0;
		System.out.println("not stuk1");
		//TempCellConstruct a = cells.get(1578); 
		//TempCellConstruct b = cells.get(1152);
		/*if(a.getPositions().size()>= b.getPositions().size())
		{
			int difference = a.getPositions().size()- b.getPositions().size();
			for(int i = 0; i < b.getPositions().size()-1;i++)
			{
				double[] ab = a.getEvents().get(i+difference);
				double[] ba = b.getEvents().get(i);
				String abs = "|"+ab[0]+"|"+ab[1]+"|"+ab[2]+"|"+ab[3];
				String bas = "|"+ba[0]+"|"+ba[1]+"|"+ba[2]+"|"+ba[3];
				System.out.println("a "+a.getPositions().get(i+difference)[0]+", "+a.getPositions().get(i+difference)[1]+" evn "+abs);
				System.out.println("b "+b.getPositions().get(i)[0]+", "+b.getPositions().get(i)[1]+" evn "+bas);
			}
		}else
		{
			int difference = b.getPositions().size()- a.getPositions().size();
			for(int i = 0; i < a.getPositions().size()-1;i++)
			{
				double[] ab = a.getEvents().get(i);
				double[] ba = b.getEvents().get(i+difference);
				String abs = "|"+ab[0]+"|"+ab[1]+"|"+ab[2]+"|"+ab[3]+"|";
				String bas = "|"+ba[0]+"|"+ba[1]+"|"+ba[2]+"|"+ba[3]+"|";
				System.out.println("b "+b.getPositions().get(i+difference)[0]+", "+b.getPositions().get(i+difference)[1]+" evn "+bas);
				System.out.println("a "+a.getPositions().get(i)[0]+", "+a.getPositions().get(i)[1]+" evn "+abs);
			}
		}*/
		do
		{
			for(int i = 0; i < polling.size();i++)
			{
				ArrayList<Double[]> evns = cells.get(polling.get(i)).getEvents();
				do
				{
					if(evns.get(0)[0]==timePointer)
					{
						pile.add(evns.remove(0));
					}
				}while(evns.size()> 0 && evns.get(0)[0]==timePointer);
				if(evns.size() == 0)
				{
					polling.remove(i);
					i--;
				}
			}
			timePointer++;
		}while (polling.size() != 0); 
		
		System.out.println("not stuk2");
		//output top line //need cell num, time steps, step size, grid size
		String top =  "cellNum:"+cells.size()+" timeSteps:"+largest+" stepSize:"+stepSize+" gridSize:"+(gridSize);
		//output starts
		//for all cells need start vector, position x, position y, start speed, start time and life time
		String starts = "starting vectors:";
		System.out.println("c "+cells.size());
		StringBuilder st = new StringBuilder();
		st.append(starts);
		for(int i = 0; i < cells.size(); i++)
		{
			TempCellConstruct sub = cells.get(i);
			st.append("n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime()+","+sub.getOldID());
			//starts += "n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime();
		}
		//output events
		//all changes need to be [time, cell, vector, speed] sorted by time
		String eventVect = "changing vectors:";
		System.out.println("p "+pile.size());
		StringBuilder sb = new StringBuilder();
		sb.append(eventVect);
		for(int i = 0; i < pile.size(); i++)
		{
			Double[] evn = pile.get(i);
			sb.append("n"+evn[0]+","+evn[1]+","+evn[2]+","+evn[3]);
			//eventVect += "n"+evn[0]+","+evn[1]+","+evn[2]+","+evn[3];
		}
		//change to all or not all for in or out bound stuff
		File file2 = new File(System.getProperty("user.dir")+"//"+file.getName().replace(".txt", "")+"extrap.txt");
		System.out.println("pile on construct done "+pile.size());
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println(top);
			writer.println(st.toString());
			writer.println(sb.toString());
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*ExtrapTrajFileInterpreter tFI = new ExtrapTrajFileInterpreter(file2);
		//hand cells to visualiser and run
		Visualiser vis = new Visualiser(file2.getName(),tFI, false);
		SingletonHolder.setFileName(file2.getName());
		vis.run();*/
	}
	public void extrapolateFromTrackMate(File file) throws FileNotFoundException 
	{
		//need cell num, time steps, step size, grid size
		//for all cells need start vector, position x position y and start time
		//all changes need to be [time, cell, vector, speed] sorted by time
		
		int stepSize = 6;
		double gridSize = 1500.00;
		//seek max lifetime
		Scanner read = new Scanner (file);
		read.nextLine();
		read.nextLine();
		read.nextLine();
		read.nextLine();
		int largest = 0;
		
		//create 'cell' constructs with b time d time l time start pos and x/y * l time positions
		//extrapolate positions into trajectory changes, time, cell, vector, speed
		ArrayList<TempCellConstruct> cells = new ArrayList<TempCellConstruct>();
		int previousID = 1;
		String[] splitter = null;
		splitter = read.nextLine().split(",");
		int uni = 0;
		do
		{
			TempCellConstruct temp = new TempCellConstruct(stepSize, uni);
			//x,y,time
			ArrayList<Double[]> positions = new ArrayList<Double[]>();
			do
			{
				positions.add(new Double[]{Double.parseDouble(splitter[4]),Double.parseDouble(splitter[5]),(double) Integer.parseInt(splitter[1])});
				if(Integer.parseInt(splitter[1]) > largest)
				{
					largest = Integer.parseInt(splitter[1]);
				}
				//System.out.println(""+uni);
				if(read.hasNextLine())
				{
					splitter = read.nextLine().split(",");
				}
			//}while(Integer.parseInt(splitter[0]) == (positions.get(positions.size()-1)[2].intValue()+1)||Integer.parseInt(splitter[0]) == (positions.get(positions.size()-1)[2].intValue()) && read.hasNextLine());
			}while(Integer.parseInt(splitter[1]) == (positions.get(positions.size()-1)[2].intValue()+1) && read.hasNextLine());
			temp.setPositions(positions);
			temp.figureMetaFromPositions();
			cells.add(temp);
			uni++;
		}while(read.hasNextLine());

		//for all events find the smallest and largest x and y
		double smallestX = 9999999.00;
		double smallestY = 9999999.00;
		double largestX = 0.00;
		double largestY = 0.00;
		largest = 0;
		for(int i = 0; i < cells.size(); i++)
		{
			double sX = cells.get(i).getSmallestXPoint();
			double sY = cells.get(i).getSmallestYPoint();
			double lX = cells.get(i).getLargestXPoint();
			double lY = cells.get(i).getLargestYPoint();
			if(cells.get(i).getDeathTime()>largest)
			{
				largest = cells.get(i).getDeathTime();
			}
			if (sX < smallestX )
			{
				smallestX = sX;
			}
			if (sY < smallestY )
			{
				smallestY = sY;
			}
			if (lX > largestX )
			{
				largestX = lX;
			}
			if (lY > largestY )
			{
				largestY = lY;
			}
		}
		//mod all to increment and make smallest 0
		//tab or un tab here for davide stuff
//		for(int i = 0; i < cells.size(); i++)
//		{
//			cells.get(i).moveAllPositionsBy(0-smallestX+dt,0-smallestY);
//			//cells.get(i).moveAllPositionsBy(0.1,0.1);
//			//cells.get(i).moveAllPositionsBy(0.1,0.1);
//			
//		}
//		//compress all by ratio of largest to ideal display area
//		System.out.println("multi by "+(gridSize-100)/largestX+" and "+(gridSize-100)/largestY);
//		for(int i = 0; i < cells.size(); i++)
//		{
//			cells.get(i).compressAllPositionsByWithFlip((gridSize)/largestX,(gridSize)/largestY, (gridSize));
//			//cells.get(i).compressAllPositionsByWithoutFlip(gridSize/largestX,gridSize/largestY, gridSize);
//			//cells.get(i).compressAllPositionsByWithFlip(54.00,54.00, gridSize);  
//			//cells.get(i).compressAllPositionsByWithFlip(50.00,50.00, gridSize);
//		}
		for(int i = 0; i < cells.size(); i++)
		{
			//System.out.println("extrap "+i);
			if(cells.get(i).getPositions().size() == 1)
			{
				cells.remove(i);
				i--;
			}else
			{
				cells.get(i).setEvents(cells.get(i).extrapolateEventsFromPositions());
				
			}
		}
		//get largest point
		double larg = 0.00;
		for(int i = 0; i < cells.size(); i++)
		{
			double comp = cells.get(i).getLargestPoint(); 
			if (comp > larg)
			{
				larg = comp;
			}
		}
		System.out.println("not stuk0");
		
		//shuffle all events into ordered pile
		ArrayList<Integer> polling = new ArrayList<Integer>();
		ArrayList<Double[]> pile = new ArrayList<Double[]>();
		//sort cells by spawn time and reassign id
		 int n = cells.size();  
	     TempCellConstruct temp = null;  
	     for(int i=0; i < n; i++)
	     {  
	    	 for(int j=1; j < (n-i); j++)
	    	 {  
	    		 if(cells.get(j-1).getBirthTime() > cells.get(j).getBirthTime())
	    		 {  
	    			 //swap elements  
	                 temp = cells.get(j-1).copy(); 
	                 cells.set(j-1, cells.get(j));  
	                 cells.set(j, temp);  
	             }  
	                          
	    	 }  
	     }  
	     for(int i = 0; i < cells.size(); i++)
	     {
	    	 //System.out.println(""+cells.get(i).getBirthTime());
	    	 cells.get(i).setIdUpdate(i);
	     }
	     //System.exit(0);
		for(int i = 0; i < cells.size(); i++)
		{
			polling .add(i);
		}
		int timePointer = 0;
		System.out.println("not stuk1");
		//TempCellConstruct a = cells.get(1578); 
		//TempCellConstruct b = cells.get(1152);
		/*if(a.getPositions().size()>= b.getPositions().size())
		{
			int difference = a.getPositions().size()- b.getPositions().size();
			for(int i = 0; i < b.getPositions().size()-1;i++)
			{
				double[] ab = a.getEvents().get(i+difference);
				double[] ba = b.getEvents().get(i);
				String abs = "|"+ab[0]+"|"+ab[1]+"|"+ab[2]+"|"+ab[3];
				String bas = "|"+ba[0]+"|"+ba[1]+"|"+ba[2]+"|"+ba[3];
				System.out.println("a "+a.getPositions().get(i+difference)[0]+", "+a.getPositions().get(i+difference)[1]+" evn "+abs);
				System.out.println("b "+b.getPositions().get(i)[0]+", "+b.getPositions().get(i)[1]+" evn "+bas);
			}
		}else
		{
			int difference = b.getPositions().size()- a.getPositions().size();
			for(int i = 0; i < a.getPositions().size()-1;i++)
			{
				double[] ab = a.getEvents().get(i);
				double[] ba = b.getEvents().get(i+difference);
				String abs = "|"+ab[0]+"|"+ab[1]+"|"+ab[2]+"|"+ab[3]+"|";
				String bas = "|"+ba[0]+"|"+ba[1]+"|"+ba[2]+"|"+ba[3]+"|";
				System.out.println("b "+b.getPositions().get(i+difference)[0]+", "+b.getPositions().get(i+difference)[1]+" evn "+bas);
				System.out.println("a "+a.getPositions().get(i)[0]+", "+a.getPositions().get(i)[1]+" evn "+abs);
			}
		}*/
		do
		{
			for(int i = 0; i < polling.size();i++)
			{
				ArrayList<Double[]> evns = cells.get(polling.get(i)).getEvents();
				do
				{
					if(evns.get(0)[0]==timePointer)
					{
						pile.add(evns.remove(0));
					}
				}while(evns.size()> 0 && evns.get(0)[0]==timePointer);
				if(evns.size() == 0)
				{
					polling.remove(i);
					i--;
				}
			}
			timePointer++;
		}while (polling.size() != 0); 
		
		System.out.println("not stuk2");
		//output top line //need cell num, time steps, step size, grid size
		String top =  "cellNum:"+cells.size()+" timeSteps:"+largest+" stepSize:"+stepSize+" gridSize:"+(gridSize);
		//output starts
		//for all cells need start vector, position x, position y, start speed, start time and life time
		String starts = "starting vectors:";
		System.out.println("c "+cells.size());
		StringBuilder st = new StringBuilder();
		st.append(starts);
		for(int i = 0; i < cells.size(); i++)
		{
			TempCellConstruct sub = cells.get(i);
			st.append("n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime()+","+sub.getOldID());
			//starts += "n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime();
		}
		//output events
		//all changes need to be [time, cell, vector, speed] sorted by time
		String eventVect = "changing vectors:";
		System.out.println("p "+pile.size());
		StringBuilder sb = new StringBuilder();
		sb.append(eventVect);
		for(int i = 0; i < pile.size(); i++)
		{
			Double[] evn = pile.get(i);
			sb.append("n"+evn[0]+","+evn[1]+","+evn[2]+","+evn[3]);
			//eventVect += "n"+evn[0]+","+evn[1]+","+evn[2]+","+evn[3];
		}
		//change to all or not all for in or out bound stuff
		File file2 = new File(System.getProperty("user.dir")+"//"+file.getName().replace(".txt", "")+"extrap.txt");
		System.out.println("pile on construct done "+pile.size());
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println(top);
			writer.println(st.toString());
			writer.println(sb.toString());
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*ExtrapTrajFileInterpreter tFI = new ExtrapTrajFileInterpreter(file2);
		//hand cells to visualiser and run
		Visualiser vis = new Visualiser(file2.getName(),tFI, false);
		SingletonHolder.setFileName(file2.getName());
		vis.run();*/
	}
}
