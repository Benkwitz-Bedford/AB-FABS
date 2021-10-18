package file_manipulation;

import gui_main.Visualiser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import cell_data_holders.Cell;
import cell_data_holders.CellHandler;
import environment_data_holders.Posi;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import trajectory_components.RouletteTrajMaker;

public class MockUpTrajMaker {

	public MockUpTrajMaker() 
	{
		
	}

	public void buildBezier(ArrayList<String> values) 
	{
		// TODO Auto-generated method stub
		
	}

	public void buildRoulette(ArrayList<String> values) 
	{
		RouletteTrajMaker maker = new RouletteTrajMaker(values);
		//maker.fullMockup();
	}

	public void buildRandom(ArrayList<String> values) 
	{
		/*
		 *  values.add(type);
			values.add(fileName.getText());
			values.add(iterations.getText());
			values.add(changeChance.getText());
			values.add(noOfCells.getText());
			values.add(gridSize.getText());
		 */
		int timeSteps =  Integer.parseInt(values.get(2));
		int chanceOfChange = Integer.parseInt(values.get(3));
		int cells = Integer.parseInt(values.get(4));
		int gridSize = Integer.parseInt(values.get(5));
		Posi posi = new Posi();
		
		Cell[] cellArray = new Cell[cells];
		Random randomiser = SingletonHolder.getMasterRandom();
		//[time,cell,vector]
		ArrayList<double[]> changeLog = new ArrayList<double[]>(); 
		//make cells and give vect and position
		for(int i = 0; i < cellArray.length; i++)
		{
			cellArray[i] = new Cell(randomiser.nextInt(361),randomiser.nextInt(gridSize+1),randomiser.nextInt(gridSize+1),SingletonHolder.getRepChance(), SingletonHolder.getDeathChance(),SingletonHolder.getSpeed(),SingletonStatStore.getTotalCells(), SingletonHolder.getCellSize(), new long[]{0,0}, SingletonHolder.getChange());
			SingletonStatStore.incTotalCells();
		}
		
		//make copy of starting vectors
		Cell[] cellOrigin = new Cell[cells];
		for(int i = 0; i < cellArray.length; i++)
		{
			cellOrigin[i] = new Cell(cellArray[i].getVect(),cellArray[i].getPositionX(),cellArray[i].getPositionY(),SingletonHolder.getRepChance(), SingletonHolder.getDeathChance(),SingletonHolder.getSpeed(), SingletonStatStore.getTotalCells(), SingletonHolder.getCellSize(), new long[]{0,0}, SingletonHolder.getChange());
			SingletonStatStore.incTotalCells();
		}
		
		//generate random weighted vector changes over time
		for(int i = 0; i < timeSteps; i++) 
		{
			for(int l = 0; l < cells; l++)
			{
				if(randomiser.nextInt(100001)<=chanceOfChange)
				{
					double vector = getVector();
					cellArray[l].setVect(vector, posi);
					changeLog.add(new double[]{i,l,vector});
				}
			}
		}
		offLoad(values.get(1),changeLog,cellOrigin,timeSteps,gridSize);
		
	}

	

	public int getRandSegment(int[] segments)
	{
		int segmentChosen = 0;
		int total = 0;
		for(int i = 0; i < segments.length; i++)
		{
			total = total + segments[i];
		}
		Random randomiser = SingletonHolder.getMasterRandom();
		int chosen = randomiser.nextInt(total+1);
		boolean notFound = true;
		int cumulativeSegments = segments[0];
		int prevCumulative = -1;
		int i = 0;
		do 
		{
			if(chosen <=cumulativeSegments && chosen > prevCumulative)
			{
				notFound = false;
				segmentChosen = i;
			}
			
			//System.out.println(segments[i]);
			prevCumulative = cumulativeSegments;
			cumulativeSegments = cumulativeSegments + segments[i];
			i++;
			
		}while(notFound && i < segments.length);
		if(notFound)
		{
			System.out.println("ERROR"+chosen+", "+total+", "+cumulativeSegments);
			System.exit(chosen);
		}
		return segmentChosen;
	}
	
	private double getVector() {
		Random randomiser = SingletonHolder.getMasterRandom();
		int pointer = randomiser.nextInt(360);
		return pointer;
	}
	
	public double getWeightedVector(int segments, double vector, int chosenSegment)
	{
		double newVector = 0;
		int range = 360/segments;
		Random randomiser = SingletonHolder.getMasterRandom();
		int pointer = randomiser.nextInt(range+1);
		newVector = chosenSegment*range+pointer;		
		return newVector;
	}
	
	public void offLoad(String fileName, ArrayList<double[]> changeLog, Cell[] cellOrigin, int timeSteps, int gridSize)
	{
		
		File file2 = new File(fileName+".txt");
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println("cellNum:"+cellOrigin.length+" TimeSteps:"+timeSteps+" gridSize:"+gridSize);
			writer.print("starting vectors:");
			for(int i = 0; i < cellOrigin.length; i++)
			{
				writer.print(","+cellOrigin[i].getVect()+"#"+cellOrigin[i].getPositionX()+" "+cellOrigin[i].getPositionY());
			}
			writer.println();
			writer.print("changing vectors:");
			for(int i = 0; i < changeLog.size(); i++)
			{
				double[] log = changeLog.get(i);
				writer.print("n"+log[0]+","+log[1]+","+log[2]);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.playGenerated(file2);
	}

	private void playGenerated(File file2) {
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		File file = file2;
		FileFilter filter = new FileNameExtensionFilter("avi","avi");
		fc.setFileFilter(filter);
		fc.setDialogTitle("Video file");
		fc.showOpenDialog(null);
		File file3 = fc.getSelectedFile();
		//input traj data
		TrajFileInterpreter tFI = new TrajFileInterpreter(file);
		//interpret to cells
		CellHandler cells  = tFI.getCellHandler();
		//hand cells to visualiser and run
		Visualiser vis = new Visualiser(cells,file.getName(),file3,false);
		SingletonHolder.setFileName(file.getName());
		vis.run();
	}
	
	
}
