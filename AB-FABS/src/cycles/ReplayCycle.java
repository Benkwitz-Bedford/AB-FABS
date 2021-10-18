package cycles;

import heatmaps.MosaicHeatmap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import cell_data_holders.Cell;
import cell_data_holders.CellHandler;
import environment_data_holders.Posi;
import singleton_holders.SingletonHolder;

public class ReplayCycle extends Cycle
{
	 public ReplayCycle(CellHandler cells)
	 {
		   this.setStore(cells);
		   setStore2(this.copy(cells));
		   int dimensions = cells.getSize();
		   SingletonHolder.setPanelSize(dimensions);
		   ArrayList<Cell> cells2 = cells.getCells();
		   //System.out.println("num "+cellNum);
		   Dimension dim = new Dimension(dimensions,dimensions); 
		   for(int i =0; i < cells2.size(); i++)
		   {
			   this.addCircle(cells2.get(i),i);
			   //this.addPoly(cells2[i]);
		   }
	 }

	  

	@Override
	public void run() 
	{
		long previousTime = System.currentTimeMillis();
		int start = 0;
		do{
			if(SingletonHolder.isRunning())
			{
				if(System.currentTimeMillis() > previousTime+SingletonHolder.getBrakes())
				{
					previousTime = System.currentTimeMillis();
					ArrayList<double[]> trajectories = getStore().getTrajectories();
					//cycle can iterate through record with iteration being decided by travel cycles between each, apply traj changes for current increment then commit and gate off until distance met 
					if(SingletonHolder.getJumpCounter()/SingletonHolder.getJumpsPerIncrement()>SingletonHolder.getIncrement())
					{
						//move pointer change traj etc
						
						//System.out.println(" size "+trajectories.size()+" size2 "+trajectories.get(incrementPointer+1).size());
						//System.out.println("traj inc: "+trajectories.get(incrementPointer+1).get(0)[0]);
						//check we are at the right increment if not it goes back around 
						if(trajectories.get(start)[0] == SingletonHolder.getIncrement())
						{
							ArrayList<double[]> incrementRecords = new ArrayList<double[]>();
							int i = start;
							do
							{
								incrementRecords.add(trajectories.get(i));
								i++;
							}while(i < trajectories.size() && trajectories.get(i)[0] == SingletonHolder.getIncrement());
							start = i;
							this.applyTrajChanges(incrementRecords,this.getSim().getMoveMan().getPositionGrid());
						}
						SingletonHolder.incIncrement();
						//feedback.setText("Filename: "+fileName+" Running: "+running+" Jump Size: "+jumpSize+" Jumps Per Increment: "+jumpsPerIncrement+" Brake size: "+brakes+" Increment: "+increment);
					}
					//move cells a jump length along the vector
					sim.getCellMan().getStore().setCells(sim.getMoveMan().incrementAllCells(sim.getCellMan().getStore().getCells(), SingletonHolder.getSize(), sim.getCellMan().getCircles(), sim.getMoveMan().getPositionGrid()));
					SingletonHolder.incJumpCounter();
					this.updateCells(getStore().getCells());
					//System.out.println("update "+jumpCounter+" inc pointer "+incrementPointer+" increment "+increment);
				}
			}
			if(isMp4Gen())
			{
				this.recordVidImages(sim);
				
				if(images.size() == 100)
				{
					mp4Gen=false;
				}
			}
		
			
		}while(SingletonHolder.getIncrement() < getStore().getTime());
		if(SingletonHolder.getIncrement() >=getStore().getTime())
		{
			SingletonHolder.setIncrement(0);
			ArrayList<Cell> cells2 = getStore2().getCells();
			setStore(this.copy(getStore2()));
			setShapes(new ArrayList<Ellipse2D>(),new ArrayList<Rectangle2D>());
			for(int i =0; i < cells2.size(); i++)
			{
			   this.addCircle(cells2.get(i),i);
			}
			sim.setMos(new MosaicHeatmap(dimensions/getPosSize(),dimensions/getPosSize(), sim.getMoveMan().getPositionGrid()));
			this.run();
		}
	}

	protected CellHandler copy(CellHandler cells2) 
	 {
		CellHandler copy = new CellHandler();
		copy.setNum(cells2.getNum());
		copy.setSize(cells2.getSize());
		copy.setTime(cells2.getTime());
		copy.giveStarts(cells2.getStarts());
		copy.giveTrajectories(cells2.getTrajectories());
		return copy;
	 }
		
	public void checkFlags() 
	{
		
	}


	private void applyTrajChanges(ArrayList<double[]> arrayList, Posi posi) 
	{
		for(int i = 0; i < arrayList.size();i++)
		{
			double[] change = arrayList.get(i);
			getStore().getCell(change[1]).setVect(change[2], posi);
		}
		
	}
	   
	   

	
	   
}
