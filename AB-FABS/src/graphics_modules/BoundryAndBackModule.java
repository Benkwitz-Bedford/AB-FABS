package graphics_modules;

import gui_main.GridPanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import singleton_holders.SingletonHolder;

public class BoundryAndBackModule extends GraphModule{

	public void genNext(Graphics2D g, GridPanel grid)
	{
		Color col = Color.WHITE;
		Color bg = Color.DARK_GRAY;
		if(SingletonHolder.isInvertHeat())
		{
			bg = Color.WHITE;
		}
		if(SingletonHolder.isBoundary())
		{
			
		
			if(SingletonHolder.isBack())
			{
				g.drawImage(SingletonHolder.getBackImg(), 0, 0,SingletonHolder.getSize(),SingletonHolder.getSize(), null);
				if(SingletonHolder.getBoundaryType().equals("circular"))
				{
					//Ellipse2D circ3 = new Ellipse2D.Double((SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,(SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength(),SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength());
					//g2.setColor(bg);
					//g2.fill(circ3);
					//g2.setColor(col);
					//g2.draw(circ3);
					g.drawImage(SingletonHolder.getBackImg(), 0, 0,SingletonHolder.getSize(),SingletonHolder.getSize(), null);
	
	                Area outter = new Area(new Rectangle(0, 0, grid.getWidth(), grid.getHeight()));
	                Ellipse2D inner = new Ellipse2D.Double((SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,(SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength(),SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength());
	                outter.subtract(new Area(inner));
	
	                g.setColor(col);
	                g.fill(outter);
				}else
				{
					//Rectangle2D circ3 = new Rectangle2D.Double((SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,(SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength(),SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength());
					//g2.setColor(bg);
					//g2.fill(circ3);
					//g2.setColor(col);
					g.drawImage(SingletonHolder.getBackImg(), 0, 0,SingletonHolder.getSize(),SingletonHolder.getSize(), null);
	
	                Area outter = new Area(new Rectangle(0, 0, grid.getWidth(), grid.getHeight()));
	                Rectangle inner = new Rectangle((SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,(SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength(),SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength());
	                outter.subtract(new Area(inner));
	
	                g.setColor(col);
	                g.fill(outter);
				}
			}else
			{
				grid.setBackground(bg);
				if(SingletonHolder.getBoundaryType().equals("circular"))
				{
					Area outter = new Area(new Rectangle(0, 0, grid.getWidth(), grid.getHeight()));
	                Ellipse2D inner = new Ellipse2D.Double((SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,(SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength(),SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength());
	                outter.subtract(new Area(inner));
	                
	                g.setColor(col);
	                g.fill(outter);
				}else
				{
					
	                Area outter = new Area(new Rectangle(0, 0, grid.getWidth(), grid.getHeight()));
	                Rectangle inner = new Rectangle((SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,(SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength(),SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength());
	                outter.subtract(new Area(inner));
	
	                g.setColor(col);
	                g.fill(outter);
				}
			}
			
		}else
		{
			if(SingletonHolder.isBack())
			{
				g.drawImage(SingletonHolder.getBackImg(), 0, 0,SingletonHolder.getSize(),SingletonHolder.getSize(), null);
			}else
			{
				{
					grid.setBackground(bg);
				}
			}
		}
	}
}
