package graphics_modules;

import gui_main.GridPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import singleton_holders.SingletonHolder;
import cell_data_holders.Cell;
import cycle_components.StaticCalculations;
import cycles.Cycle;
import cycles.LiveCycle;

public class CircleModule extends GraphModule{
	
	public Graphics2D genNext(Graphics2D g2, GridPanel grid, Cycle cycle, AffineTransform oldXForm, Color ent)
	{
		List<Ellipse2D> circles2 = cycle.getCircles();
		Ellipse2D circ = new Ellipse2D.Double(0,0,grid.getPrefW(),grid.getPrefH());
		Color entStart = ent;
		for (int i = 0; i < cycle.getCells().size(); i++)
		{
			if(cycle.getCells().get(i).isCorporeal())
			{
				circ = circles2.get(i);
				Cell c = cycle.getCells().get(i);
				if(c.getColour() == Color.white)
		    	{
		    		ent = entStart;
		    	}else
		    	{
		    		ent = c.getColour();
		    	}
				int x = 0;
				int y = 0;
				if(SingletonHolder.getPush() != 0)
				{
					x = (int) (c.getPositionX()+SingletonHolder.getPush());
					y = (int) (c.getPositionY()+SingletonHolder.getPush());
					
				}else
				{
					x = (int) c.getPositionX();
					y = (int) c.getPositionY();
				}
				g2.rotate(Math.toRadians(c.getVect()),x,y);
				circ.setFrame(x-c.getCellSize()/2,y-c.getCellSize()/2,c.getCellSize(),c.getCellSize());
				g2.setColor(ent);
		    	g2.fill(circ);
		    	g2.setTransform(oldXForm);
				double vect = Math.toRadians(c.getVect());
				double x2 = x + Math.cos(vect)*20;
				double y2 = y + Math.sin(vect)*20;
		    	g2.drawLine((int)x,(int)y,(int) x2,(int) y2);
		    	if(SingletonHolder.isTail())
		    	{
		    		g2.setStroke(new BasicStroke((float) 1));
		    		ArrayList<double[]> tail = c.getTail();
		    		for(int l = 1; l < tail.size(); l++)
		    		{
		    			double[] p1 = tail.get(l-1);
		    			double[] p2 = tail.get(l);
		    			if(StaticCalculations.getEuclidean(p1[0], p1[1], p2[0], p2[1])< 10)
		    			{
		    				g2.drawLine((int)p1[0]+SingletonHolder.getPush(),(int) p1[1]+SingletonHolder.getPush(),(int) p2[0]+SingletonHolder.getPush(),(int) p2[1]+SingletonHolder.getPush());
		    			}
		    		}
		    	}
		    	//g2.fillRect((int)c.getPositionX()-20/2,(int)c.getPositionY()-20/2,20,20);
			}
		 }
		return g2;
	}

}
