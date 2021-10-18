package trajectory_components;

import java.util.ArrayList;
import java.util.Random;

import cycle_components.StaticCalculations;
import singleton_holders.SingletonHolder;

public class TrajMaker 
{
	private String value1 = "";
	private int timeSteps =  0;
	private int chanceOfChange = 0;
	private int cellNum = 0;
	private int gridSize = 0;
	private int picked[] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	public TrajMaker(ArrayList<String> values)
	{
		setValue1(values.get(1));
		timeSteps =  Integer.parseInt(values.get(2));
		chanceOfChange = Integer.parseInt(values.get(3));
		cellNum = Integer.parseInt(values.get(4));
		gridSize = Integer.parseInt(values.get(5));
	}
	
	public int getRandSegment(long[] segmentsTwo)
	{
		int segmentChosen = 0;
		long total = 0;
		for(int i = 0; i < segmentsTwo.length; i++)
		{
			total = total + segmentsTwo[i];
			//System.out.print(", "+segments[i]);
		}
		//System.out.println(segments.length);
		Random randomiser = SingletonHolder.getMasterRandom();
		long chosen = (long) (randomiser.nextDouble()*(total));
		boolean notFound = true;
		long cumulativeSegments = 0;
		long prevCumulative = -1;
		int i = 0;
		if(total > 0)
		{
			do 
			{
				cumulativeSegments = cumulativeSegments + segmentsTwo[i];
				if(chosen <=cumulativeSegments && chosen > prevCumulative)
				{
					notFound = false;
					segmentChosen = i;
				}
				
				//System.out.println(segments[i]);
				prevCumulative = cumulativeSegments;
				i++;
				
			}while(notFound && i < segmentsTwo.length);
			if(notFound)
			{
				System.out.println("ERROR"+chosen+", "+total+", "+cumulativeSegments);
				System.exit(0);
			}
		}else
		{
			segmentChosen =  randomiser.nextInt(segmentsTwo.length);
		}
		return segmentChosen;
	}
	
	public double getVector() 
	{
		Random randomiser = SingletonHolder.getMasterRandom();
		int pointer = randomiser.nextInt(360001);
		double poi = pointer/1000.00; 
		return poi;
	}
	
	public double getVectorGaus() 
	{
		Random randomiser = SingletonHolder.getMasterRandom();
		double poi = randomiser.nextGaussian()*180.00;
		poi = StaticCalculations.counterClockwiseClean(poi);
		if(poi < 0)
		{
			System.out.println("out of bounds "+poi);
		}
		return poi;
	}
	
	public double getVectorGausFeed(Double x) 
	{
		Random randomiser = SingletonHolder.getMasterRandom();
		double poi = randomiser.nextGaussian()*180.00+x;
		poi = StaticCalculations.counterClockwiseClean(poi);
		return poi;
	}
	
	public double getWeightedVector(int segments, double vector, int chosenSegment)
	{
		//sec 0 = 0-45
		//sec 1 = 45-90
		//sec 2 = 90-135
		//sec 3 = 135-180
		//sec 4 = 180-225
		//sec 5 = 225-270
		//sec 6 = 270-315
		//sec 7 = 315-360
		//if(segments == 8)
		//{
		getPicked()[chosenSegment]++;
		//}
		double newVector = 0;
		int range = 360/segments;
		Random randomiser = SingletonHolder.getMasterRandom();
		int pointer = randomiser.nextInt(range*10000);
		newVector = chosenSegment*range+(pointer/10000.00)+vector;
		if(newVector>360)
		{
			newVector = newVector-360.00;
		}
		//System.out.println(""+newVector);
		return newVector;
	}
	
	/*public double getWeightedVectorPointed(int segments, double vector, int chosenSegment)
	{
		//sec 0 = 0-45
		//sec 1 = 45-90
		//sec 2 = 90-135
		//sec 3 = 135-180
		//sec 4 = 180-225
		//sec 5 = 225-270
		//sec 6 = 270-315
		//sec 7 = 315-360
		//if(segments == 8)
		//{
		getPicked()[chosenSegment]++;
		//}
		double newVector = 0;
		int range = 360/segments;
		Random randomiser = SingletonHolder.getMasterRandom();
		int pointer = randomiser.nextInt(range);
		newVector = chosenSegment*range+pointer;
		if(newVector>360)
		{
			newVector = newVector-360;
		}
		return newVector;
	}*/

	public int getTimeSteps() {
		return timeSteps;
	}

	public void setTimeSteps(int timeSteps) {
		this.timeSteps = timeSteps;
	}

	public int getChanceOfChange() {
		return chanceOfChange;
	}

	public void setChanceOfChange(int chanceOfChange) {
		this.chanceOfChange = chanceOfChange;
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public int getCellNum() {
		return cellNum;
	}

	public void setCellNum(int cells) {
		this.cellNum = cells;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public int[] getPicked() {
		return picked;
	}

	public void setPicked(int picked[]) {
		this.picked = picked;
	}
}
