package trajectory_components;

import java.util.ArrayList;
import java.util.Random;

import cell_data_holders.Cell;
import environment_data_holders.Posi;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import file_manipulation.MockUpTrajMaker;

public class RandomTrajMaker extends TrajMaker
{

	public RandomTrajMaker(ArrayList<String> values) {
		super(values);
		setValue1(values.get(1));
		setTimeSteps(Integer.parseInt(values.get(2)));
		setChanceOfChange(Integer.parseInt(values.get(3)));
		setCellNum(Integer.parseInt(values.get(4)));
		setGridSize(Integer.parseInt(values.get(5)));
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
			cellArray[i] = new Cell(randomiser.nextInt(361),randomiser.nextInt(this.getGridSize()+1),randomiser.nextInt(this.getGridSize()+1),SingletonHolder.getRepChance(), SingletonHolder.getDeathChance(),SingletonHolder.getSpeed(),SingletonStatStore.getTotalCells(), SingletonHolder.getCellSize(), SingletonHolder.genWeights(SingletonHolder.getCellValues()), SingletonHolder.getChange());
			SingletonStatStore.incTotalCells();
		}
		
		//make copy of starting vectors
		Cell[] cellOrigin = new Cell[this.getCellNum()];
		for(int i = 0; i < cellArray.length; i++)
		{
			cellOrigin[i] = new Cell(cellArray[i].getVect(),cellArray[i].getPositionX(),cellArray[i].getPositionY(),SingletonHolder.getRepChance(), SingletonHolder.getDeathChance(),SingletonHolder.getSpeed(),SingletonStatStore.getTotalCells(), SingletonHolder.getCellSize(), SingletonHolder.genWeights(SingletonHolder.getCellValues()), SingletonHolder.getChange());
			SingletonStatStore.incTotalCells();
		}
		
		//generate random weighted vector changes over time
		for(int i = 0; i < this.getTimeSteps(); i++) 
		{
			for(int l = 0; l < this.getCellNum(); l++)
			{
				if(randomiser.nextInt(100001)<= this.getChanceOfChange())
				{
					double vector = this.getVector();
					cellArray[l].setVect(vector);
					changeLog.add(new double[]{i,l,vector});
				}
			}
		}
		new MockUpTrajMaker().offLoad(this.getValue1(),changeLog,cellOrigin,this.getTimeSteps(),this.getGridSize());
	}*/

	public void genNext(Cell cell, Posi posi) 
	{
		cell.setVect(this.getVector(),posi);
		
	}

}
