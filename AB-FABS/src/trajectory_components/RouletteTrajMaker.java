package trajectory_components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cell_data_holders.Cell;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;
import file_manipulation.MockUpTrajMaker;

public class RouletteTrajMaker extends TrajMaker
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
	
	public RouletteTrajMaker(ArrayList<String> values) {
		super(values);
		setValue1(values.get(1));
		setTimeSteps(Integer.parseInt(values.get(2)));
		setChanceOfChange(Integer.parseInt(values.get(3)));
		setCellNum(Integer.parseInt(values.get(4)));
		setGridSize(Integer.parseInt(values.get(5)));
		
		// out of 100,000
		this.setWeights(values.subList(6, 14));
		
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
		if(cell.getPositionX()<SingletonHolder.getSize()-posi.getCellSize()*2 && cell.getPositionX()>0+-posi.getCellSize()*2 && cell.getPositionY()<SingletonHolder.getSize()-posi.getCellSize()*2 && cell.getPositionY()>0+-posi.getCellSize()*2 )
		{
			this.addWeights(posi,cell);
		}
		double vector = getWeightedVector(segmentsTwo.length,cell.getVect(),getRandSegment(segmentsTwo));
		cell.setVect(vector,posi);
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
		int shift = (int) (cell.getVect()/(seg)+0.5);
		shift+=4;
		if(shift > segmentsTwo.length )
		{
			shift = shift -segmentsTwo.length;
		}
		if(shift < 0)
		{
			shift += segmentsTwo.length;
		}
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

	private void setWeights(String[] strings) {

		//0-4 repCheck, repChance, repTaboo, repVarianceCheck, repVarience
		//5-10 deathCheck, deathChance, deathVarienceCheck, deathVarience, deathType, deathTimedNum
		//11-14 speedCheck, speedAmount, speedVarience, speedType
		//15-16 changeChance, changeVarience
		//17 noOfCells
		//18-19 wOne, oneVar,
		//20-21 wTwo, twoVar,
		//22-23 wThree, threeVar,
		//24-25 wFour, fourVar,
		//26-27 wFive, fiveVar,
		//28-29 wSix, sixVar,
		//30-31 wSeven, sevenVar,
		//32-33 wEight, eightVar,
		//34rand/rou
		//35-36 cell size and cell varience
		segWeightOne = Long.parseLong(strings[18]);
		segWeightTwo = Long.parseLong(strings[20]);
		segWeightThree = Long.parseLong(strings[22]);
		segWeightFour = Long.parseLong(strings[24]);
		segWeightFive = Long.parseLong(strings[26]);
		segWeightSix = Long.parseLong(strings[28]);
		segWeightSeven = Long.parseLong(strings[30]);
		segWeightEight = Long.parseLong(strings[32]);
		segments = new long[]{segWeightOne,segWeightTwo,segWeightThree,segWeightFour,segWeightFive,segWeightSix,segWeightSeven,segWeightEight};
		
	}

	/*public void fullMockup()
	{
		
		Cell[] cellArray = new Cell[this.getCellNum()];
		Random randomiser = SingletonHolder.getMasterRandom();
		//[time,cell,vector]
		ArrayList<double[]> changeLog = new ArrayList<double[]>();
		//make cells and give vect and position
		for(int i = 0; i < cellArray.length; i++)
		{
			cellArray[i] = new Cell(randomiser.nextInt(361),randomiser.nextInt(this.getGridSize()+1),randomiser.nextInt(this.getGridSize()+1),SingletonHolder.getRepChance(), SingletonHolder.getDeathChance(),SingletonHolder.getSpeed(),SingletonStatStore.getTotalCells(), SingletonHolder.getCellSize(),SingletonHolder.genWeights(SingletonHolder.getCellValues()), SingletonHolder.getChange());
			SingletonStatStore.incTotalCells(); 
		}
		
		//make copy of starting vectors
		Cell[] cellOrigin = new Cell[this.getCellNum()];
		for(int i = 0; i < cellArray.length; i++)
		{
			cellOrigin[i] = new Cell(cellArray[i].getVect(),cellArray[i].getPositionX(),cellArray[i].getPositionY(),SingletonHolder.getRepChance(), SingletonHolder.getDeathChance(),SingletonHolder.getSpeed(),SingletonStatStore.getTotalCells(), SingletonHolder.getCellSize(),SingletonHolder.genWeights(SingletonHolder.getCellValues()), SingletonHolder.getChange());
			SingletonStatStore.incTotalCells();
		}
		
		//generate random weighted vector changes over time
		for(int i = 0; i < this.getTimeSteps(); i++) 
		{
			for(int l = 0; l < this.getCellNum(); l++)
			{
				if(randomiser.nextInt(100001)<=this.getChanceOfChange())
				{
					double vector = cellArray[l].getVect();
					if(i == segments.length+1)
					{
						
					}else
					{
						vector = getWeightedVector(segments.length,cellArray[l].getVect(),i);
					}
					
					cellArray[l].setVect(vector);
					changeLog.add(new double[]{i,l,vector});
				}
			}
		}
		new MockUpTrajMaker().offLoad(this.getValue1(),changeLog,cellOrigin,this.getTimeSteps(),this.getGridSize());
	}*/
	
	public void genNext(Cell cell, Posi posi) {
		this.setWeights(cell.getTurnWeights());
		double vector = getWeightedVector(segments.length,cell.getVect(),getRandSegment(segments));
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

	

	

	
}
