package cycle_components;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cell_data_holders.Cell;
import environment_data_holders.Posi;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import trajectory_components.PotentialProcessor;

public class MoveManagerPotential extends MoveManager{
	
	private Double[][][] potGrid;
	private double ratio;
	public MoveManagerPotential(PotentialProcessor pot) {
		this.potGrid = pot.getPotGrid();
		double length = potGrid.length-1;
		double size = SingletonHolder.getSize();
		ratio = length/size;
	}

	public ArrayList<Cell> incrementAllCells(ArrayList<Cell> cells, double size,List<Ellipse2D> circles,Posi posi) {
		//System.out.println("cells size Move manager "+cells.size());
		for(int i = 0; i < cells.size();i++)
		{
			if(i == 0)
			{
				//System.out.println(+store.getNum());
			}
			if(cells.get(i).isLiving()&&cells.get(i).isCorporeal())
			{
				//System.out.println("pre inc "+cells.get(i).getPositionX()+", "+cells.get(i).getPositionY()+" vec "+cells.get(i).getVect());
				incrementCell(cells.get(i),i, size, circles, posi);
				cells.get(i).getPack().addLifeTime(1);
				//System.out.println("post inc "+cells.get(i).getPositionX()+", "+cells.get(i).getPositionY());
			}
		}
		return cells;
	}
	//you add  -(V(x+1)-V(x))/dx*dt
	private void incrementCell(Cell c ,int i, double dimensions,List<Ellipse2D> circles,Posi posi) 
	{
		//add boundary conditions
		//Cell c = getStore().getCells().get(i);
		double x = c.getPositionX();
		double y = c.getPositionY();
		double oX = x;
		double oY = y;
		double jump = c.getSpeed();
		if(jump != 0.0)
		{
			if(c.getTrajType().equals("brownian"))
			{
				jump = c.getProjectedJump();
				if(jump == 0.00)
				{
					Random randomiser = SingletonHolder.getMasterRandom();
					double poi = randomiser.nextGaussian()*c.getSpeed();
					poi = Math.pow(poi, 2);
					poi = Math.sqrt(poi);
					jump = poi;
					
				}
			}
			double vect = Math.toRadians(c.getVect());
			x = x + Math.cos(vect)*jump;
			y = y + Math.sin(vect)*jump;
			x = x - potGrid[(int) (oY*ratio)][(int) (oX*ratio)][0];
			y = y - potGrid[(int) (oY*ratio)][(int) (oX*ratio)][1];
			//towards the second
			//c.setVect(Math.toDegrees(Math.atan2(oY-y, oX-x)), posi);
			c.setVect(Math.toDegrees(Math.atan2(y-oY, x-oX)), posi);
			SingletonStatStore.pollTurnType(c, posi);
			//x = (x*Math.cos(vect))+(dist*Math.sin(vect));
			//y = (-y*Math.sin(vect))+(dist*Math.cos(vect));
			/*
			double euc = this.getEuclidean(x,y,oX,oY);
			if(euc > SingletonHolder.getJumpSize()+1)
			{
				System.out.println(+c.getVect()+", "+euc+", "+SingletonHolder.getJumpSize());
				//System.exit(0);
			}
			*/
			if(x < 0)
			{
				x = dimensions-1+x;
				c.setxCrossed(c.getxCrossed()-1);
			}
			if(y < 0)
			{
				y = dimensions-1+y;
				c.setyCrossed(c.getyCrossed()-1);
			}
			if(x > dimensions)
			{
				x = 1+x-dimensions;
				c.setxCrossed(c.getxCrossed()+1);
			}
			if(y > dimensions)
			{
				y = 1+y-dimensions;
				c.setyCrossed(c.getyCrossed()+1);
			}
			c.setPositionX(x);
			c.setPositionY(y);
			if(SingletonHolder.isTail())
			{
				c.addTailPoint(new double[]{x,y});
			}
			c.incrementDistance(jump);
			c.getPack().addTotalDistanceTravelled(jump);
			if(SingletonHolder.isCirc())
			{
				//circles.get(i).setFrame(x-c.getCellSize()/2,y-c.getCellSize()/2,c.getCellSize(),c.getCellSize());
				
			}else
			{
				//circles.get(i).setFrame(x-c.getCellSize()/2,y-c.getCellSize()/4,c.getCellSize(),c.getCellSize()/2);
			}
			//circles.get(i).setFrame(x,y,SingletonHolder.getCellLength(),SingletonHolder.getCellLength());
		}else
		{
			//System.out.println("0.0 speed");
		}
		posi.updatePosition(oX,oY,c,i);
		c.setPrevTrig(new double[]{x,y});
		if(c.getSpeed()== 0)
		{
			//System.out.println(c.getSpeed());
		}
		c.setProjectedJump(0.00);
		//System.out.println("vect "+c.getVect());
	}

}
