package trajectory_components;

import java.util.ArrayList;
import java.util.Random;

import cell_data_holders.Cell;
import environment_data_holders.Posi;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import file_manipulation.MockUpTrajMaker;

public class RandomTrajMakerCA extends RandomTrajMaker
{

	public RandomTrajMakerCA(ArrayList<String> values) {
		super(values);
		setValue1(values.get(1));
		setTimeSteps(Integer.parseInt(values.get(2)));
		setChanceOfChange(Integer.parseInt(values.get(3)));
		setCellNum(Integer.parseInt(values.get(4)));
		setGridSize(Integer.parseInt(values.get(5)));
	}
	
	

	public void genNext(Cell cell,Posi posi) 
	{
		cell.setVect(this.getVector(),posi);
		
	}

	public double getVector() 
	{
		//System.out.println("rand");
		Random randomiser = SingletonHolder.getMasterRandom();
		int pointer = randomiser.nextInt(8);
		
			if(pointer == 0)
			{
				pointer = 45;
			}else
			{
				if(pointer == 1)
				{
					pointer = 90;
					
				}else
				{
					if(pointer == 2)
					{
						pointer = 135;
						
					}else
					{
						if(pointer == 3)
						{
							pointer = 180;
							
						}else
						{
							if(pointer == 4)
							{
								pointer = 225;
								
							}else
							{
								if(pointer == 5)
								{
									pointer = 270;
									
								}else
								{
									if(pointer == 6)
									{
										pointer = 315;
										
									}else
									{
										pointer = 0;
										
									}
									
								}
							}
						}
					}
				
			}
		}
		return pointer;
	}
	
}
