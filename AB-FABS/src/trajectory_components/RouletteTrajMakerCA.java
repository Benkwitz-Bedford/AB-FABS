package trajectory_components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cell_data_holders.Cell;
import cycle_components.StaticCalculations;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;
import file_manipulation.MockUpTrajMaker;

public class RouletteTrajMakerCA extends RouletteTrajMaker
{
	private long segWeightOne = 0;
	private long segWeightTwo = 0;
	private long segWeightThree = 0;
	private long segWeightFour = 0;
	private long segWeightFive = 0;
	private long segWeightSix = 0;
	private long segWeightSeven = 0;
	private long segWeightEight = 0;
	
	long[] segments = new long[0];
	long[] segmentsTwo = new long[16];
	
	public RouletteTrajMakerCA(ArrayList<String> values) {
		super(values);
		setValue1(values.get(1));
		setTimeSteps(Integer.parseInt(values.get(2)));
		setChanceOfChange(Integer.parseInt(values.get(3)));
		setCellNum(Integer.parseInt(values.get(4)));
		setGridSize(Integer.parseInt(values.get(5)));
		
		// out of 100,000
		this.setWeights(values.subList(6, 14));
		
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
		newVector = chosenSegment;
		if(newVector>360)
		{
			newVector = newVector-360.00;
		}
		if(newVector == 0)
		{
			newVector = 0;
			
		}else
		{
			if(newVector == 1)
			{
				newVector = 45;
			}else
			{
				if(newVector == 2)
				{
					newVector = 90;
					
				}else
				{
					if(newVector == 3)
					{
						newVector = 135;
						
					}else
					{
						if(newVector == 4)
						{
							newVector = 180;
							
						}else
						{
							if(newVector == 5)
							{
								newVector = 225;
								
							}else
							{
								if(newVector == 6)
								{
									newVector = 270;
									
								}else
								{
									newVector = 315;
									
								}
							}
						}
					}
				}
			}
		}
		vector += newVector;
		vector = StaticCalculations.counterClockwiseClean(vector);
		return vector;
	}
	
	
	public void genNext(Cell cell,Posi posi) {
		this.setWeights(cell.getTurnWeights());
		double vector = 0;
		int rand = getRandSegment(segments);
		vector = getWeightedVector(segments.length,cell.getVect(),rand);
		cell.setVect(vector,posi);
	}
	
	private void setWeights(long[] turnWeights) {
		segments = turnWeights;
		
	}

	public long getSegWeightOne() {
		return segWeightOne;
	}

	public void setSegWeightOne(int segWeightOne) {
		this.segWeightOne = segWeightOne;
	}

	public long getSegWeightTwo() {
		return segWeightTwo;
	}

	public void setSegWeightTwo(int segWeightTwo) {
		this.segWeightTwo = segWeightTwo;
	}

	public long getSegWeightThree() {
		return segWeightThree;
	}

	public void setSegWeightThree(int segWeightThree) {
		this.segWeightThree = segWeightThree;
	}

	public long getSegWeightFour() {
		return segWeightFour;
	}

	public void setSegWeightFour(int segWeightFour) {
		this.segWeightFour = segWeightFour;
	}

	public long getSegWeightFive() {
		return segWeightFive;
	}

	public void setSegWeightFive(int segWeightFive) {
		this.segWeightFive = segWeightFive;
	}

	public long getSegWeightSix() {
		return segWeightSix;
	}

	public void setSegWeightSix(int segWeightSix) {
		this.segWeightSix = segWeightSix;
	}

	public long getSegWeightSeven() {
		return segWeightSeven;
	}

	public void setSegWeightSeven(int segWeightSeven) {
		this.segWeightSeven = segWeightSeven;
	}

	public long getSegWeighteight() {
		return segWeightEight;
	}

	public void setSegWeighteight(int segWeighteight) {
		this.segWeightEight = segWeighteight;
	}

	

	protected void setWeights(List<String> values) {

		segWeightOne = Long.parseLong(values.get(0));
		segWeightTwo = Long.parseLong(values.get(1));
		segWeightThree = Long.parseLong(values.get(2));
		segWeightFour = Long.parseLong(values.get(3));
		segWeightFive = Long.parseLong(values.get(4));
		segWeightSix = Long.parseLong(values.get(5));
		segWeightSeven = Long.parseLong(values.get(6));
		segWeightEight = Long.parseLong(values.get(7));
		segments = new long[]{segWeightOne,segWeightTwo,segWeightThree,segWeightFour,segWeightFive,segWeightSix,segWeightSeven,segWeightEight};
		
	}
	
	public void genNextAntWeighted(Cell cell, Posi posi) {
		// TODO Auto-generated method stub
		this.setWeights(cell.getTurnWeights());
		this.addWeights(posi,cell);
		double vector = getWeightedVector(segmentsTwo.length,cell.getVect(),getRandSegment(segmentsTwo));
		cell.setVect(vector, posi);
	}
	
	

	private void addWeights(Posi posi, Cell cell) {
		segmentsTwo = new long[16];
		//System.out.println("bdoof");
		long ratio = cell.getAntRatio();
		int x = (int) (cell.getPositionX()/posi.getCellSize());
		int y = (int) (cell.getPositionY()/posi.getCellSize());
		//1 = x,y-1 * ratio + segWeightOne
		segmentsTwo[0] = posi.getHeatXY(x,y-1)*ratio ;
		//2 = x+1,y-1 *ratio +segWeightOne
		segmentsTwo[1] = posi.getHeatXY(x+1,y-1)*ratio ;
		//3 = x+1,y-1 *ratio +segWeightTwo
		segmentsTwo[2] = posi.getHeatXY(x+1,y-1)*ratio ;
		//4 = x+1 y0 * ratio + segWeighttwo
		segmentsTwo[3] = posi.getHeatXY(x+1,y)*ratio ;
		//5 = x+1 y0 * ratio + segWeightthree
		segmentsTwo[4] = posi.getHeatXY(x+1,y)*ratio ;
		//6 = x+1 y+1 * ratio + segWeightthree
		segmentsTwo[5] = posi.getHeatXY(x+1,y+1)*ratio ;
		//7 = x+1 y+1 * ratio + segWeightfour
		segmentsTwo[6] = posi.getHeatXY(x+1,y+1)*ratio ;
		//8 = x y+1 * ratio + segWeightfour
		segmentsTwo[7] = posi.getHeatXY(x,y+1)*ratio ;
		//9 = x y+1 * ratio + segWeightfive
		segmentsTwo[8] = posi.getHeatXY(x,y+1)*ratio ;
		//10 = x-1 y+1 * ratio + segWeightfive
		segmentsTwo[9] = posi.getHeatXY(x-1,y+1)*ratio ;
		//11 = x-1 y+1 * ratio + segWeightsix
		segmentsTwo[10] = posi.getHeatXY(x-1,y+1)*ratio ;
		//12 = x-1 y * ratio + segWeightsix
		segmentsTwo[11] = posi.getHeatXY(x-1,y)*ratio ;
		//13 = x-1 y * ratio + segWeightseven
		segmentsTwo[12] = posi.getHeatXY(x-1,y)*ratio ;
		//14 = x-1 y-1 * ratio + segWeightseven
		segmentsTwo[13] = posi.getHeatXY(x-1,y-1)*ratio ;
		//15 = x-1 y-1 * ratio + segWeighteight
		segmentsTwo[14] = posi.getHeatXY(x-1,y-1)*ratio ;
		//16 = x y-1 * ratio + segWeighteight
		segmentsTwo[15] = posi.getHeatXY(x,y-1)*ratio ;
		//shift segments to cell vect
		double seg = 360/segmentsTwo.length;
		int shift = (int) (cell.getVect()/(seg));
		//shift++;
		long[] tempSeg = new long[16];
		for(int i = 0; i < segmentsTwo.length; i++)
		{
			int poi = i-shift;
			if(poi <0)
			{
				poi = segmentsTwo.length+poi;
			}
			tempSeg[poi] = segmentsTwo[i];
		}
		segmentsTwo = tempSeg;
		int inc = 0;
		int poi = 0;
		for(int i = 0; i < segmentsTwo.length;i++)
		{
			segmentsTwo[i] +=segments[poi];
			inc++;
			if(inc == 2)
			{
				inc = 0;
				poi++;
			}
		}
		for(int i = 0; i < segmentsTwo.length;i++)
		{
			if(segmentsTwo[i] <0)
			{
				segmentsTwo[i] = 0;
			}
		}
	}

	

	
	
	

	

	
}
