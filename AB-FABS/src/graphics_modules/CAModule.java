package graphics_modules;

import gui_main.GridPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import singleton_holders.SingletonHolder;
import cell_data_holders.Cell;
import cycle_components.StaticCalculations;
import cycles.Cycle;
import cycles.LiveCycle;

public class CAModule extends GraphModule{
	
	List<Rectangle2D> recs = new ArrayList<Rectangle2D>();
	
	public Graphics2D genNext(Graphics2D g2, GridPanel grid, Cycle cycle, AffineTransform oldXForm, Color ent)
	{

		//System.out.println("ca");
		recs = cycle.getSim().getCellMan().getRecs();
		Rectangle2D rec = new Rectangle2D.Double(0,0,grid.getPrefW(),grid.getPrefH());
		for (int i = 0; i < cycle.getCells().size(); i++)
		{

			   //System.out.println("cell num "+getCells().size());
			if(cycle.getCells().get(i).isCorporeal())
			{
				//if(cycle.getCells().get(i).getOldID() == 4384 ||cycle.getCells().get(i).getOldID() == 7760 )
				//{
		    	Cell c = cycle.getCells().get(i);
				rec = recs.get(i);
				int pos = cycle.getSim().getPosSize();
				int x = (int)c.getPositionX()/pos;
				int y = (int) c.getPositionY()/pos;
				x = x*pos;
				y = y*pos;
				if(SingletonHolder.getPush() != 0)
				{
					x +=SingletonHolder.getPush();
					y +=SingletonHolder.getPush();
					
				}
				rec.setFrame(x,y,pos,pos);
				//System.out.println("circ draw  "+x+" "+y+" "+c.getCellSize()+" "+c.getCellSize()/2);
				//circ.setFrame(x,y,SingletonHolder.getCellLength(),SingletonHolder.getCellLength());
				g2.setColor(ent);
				//g.drawString(getCells()[i].getLastAction(), (int)getCells()[i].getPositionX2(),(int)getCells()[i].getPositionY2());
				//g.drawString(getCells()[i].getPositionX2()+", "+getCells()[i].getPositionY2(), (int)getCells()[i].getPositionX2(),(int)getCells()[i].getPositionY2());
				//Shape circ2 = AffineTransform.getRotateInstance(cells[i].getVect()).createTransformedShape(circ);
				//System.out.println(+SingletonHolder.getPush());
		    	g2.fill(rec);
		    	g2.setTransform(oldXForm);
		    	if(c.getOverlappers() != 0 && SingletonHolder.isShowingOverlappers())
		    	{
		    		g2.drawString(""+c.getOverlappers(), (int) x,(int)y);
		    	}
				if(SingletonHolder.isShowID())
				{
					g2.drawString(c.getUnique()+","+c.getOldID(), (int) x,(int)y);
				}
				
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
			//}
			}
		 }
		return g2;
	}

	

}
