package graphics_modules;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import cell_data_holders.Cell;
import cycle_components.StaticCalculations;
import cycles.Cycle;
import environment_data_holders.GCell;
import gui_main.GridPanel;
import singleton_holders.SingletonHolder;

public class ZoneModule extends GraphModule{

	
	public void genNext(Graphics2D g, Cycle cycle, GridPanel grid)
	{
		Color zoneColPlus = Color.magenta;
		Color zoneColMinus = Color.ORANGE;
		ArrayList<double[]> zones = cycle.getSim().getMoveMan().getPositionGrid().getZoneList();
		for (int i = 0; i < zones.size(); i++)
		{
			double[] z = zones.get(i);
			int x = (int) z[0];
			int y = (int) z[1];
			double siz = z[2];
			double stren = z[3];
			double falloff = z[4];
			GCell[][] GGrid = cycle.getPositionGrid().getGrid();
			int cellSize = cycle.getPosSize();
			for(int x2 = (int) (x-siz/2);x2<x+siz/2;x2++)
			{
				for(int y2 = (int) (y-siz/2);y2<y+siz/2;y2++)
				{
					if(x2 >0 && x2 < GGrid.length && y2 >0 &&y2 < GGrid.length && GGrid[x2][y2].getAttractionStrength() !=0)
					{
						Rectangle rec = new Rectangle(x2*cellSize-(cellSize/2),y2*cellSize-(cellSize/2),cellSize,cellSize);
						if(stren > 0)
						{
							g.setColor(zoneColPlus);
						}else
						{
							g.setColor(zoneColMinus);
							
						}
						g.fill(rec);
					}
				}
			}
			/*for(int r = 0; r < siz;r++)
			{
				for(int l = 0; l < siz;l++)
				{
					int xA =  (x+r+l);
					int xB =  (x-r+l);
					int yA =  (y+r+l);
					int yB =  (y-r+l);
					if(xA >0 && xA < GGrid.length && y >0 &&y < GGrid.length)
					{
						Rectangle rec = new Rectangle(xA*cellSize-(cellSize/2),y*cellSize-(cellSize/2),cellSize,cellSize);
						g.setColor(zoneCol);
						g.fill(rec);
					}
					if(xB >0 && xB < GGrid.length&& y >0 &&y < GGrid.length)
					{
						Rectangle rec = new Rectangle(xB*cellSize-(cellSize/2),y*cellSize-(cellSize/2),cellSize,cellSize);
						g.setColor(zoneCol);
						g.fill(rec);
					}
					if(yA >0 && yA < GGrid.length&& x >0 &&x < GGrid.length)
					{
						Rectangle rec = new Rectangle(x*cellSize-(cellSize/2),yA*cellSize-(cellSize/2),cellSize,cellSize);
						g.setColor(zoneCol);
						g.fill(rec);
					}
					if(yB >0 && yB < GGrid.length&& x >0 &&x < GGrid.length)
					{
						Rectangle rec = new Rectangle(x*cellSize-(cellSize/2),yB*cellSize-(cellSize/2),cellSize,cellSize);
						g.setColor(zoneCol);
						g.fill(rec);
					}
					if(yB >0 && yB < GGrid.length && xA >0 && xA < GGrid.length)
					{
						Rectangle rec = new Rectangle(xA*cellSize-(cellSize/2),yB*cellSize-(cellSize/2),cellSize,cellSize);
						g.setColor(zoneCol);
						g.fill(rec);
					}
					if(yB >0 && yB < GGrid.length && xB >0 && xB < GGrid.length)
					{
						Rectangle rec = new Rectangle(xB*cellSize-(cellSize/2),yB*cellSize-(cellSize/2),cellSize,cellSize);
						g.setColor(zoneCol);
						g.fill(rec);
					}
					if(yA >0 && yA < GGrid.length && xA >0 && xA < GGrid.length)
					{
						Rectangle rec = new Rectangle(xA*cellSize-(cellSize/2),yA*cellSize-(cellSize/2),cellSize,cellSize);
						g.setColor(zoneCol);
						g.fill(rec);
					}
					if(yA >0 && yA < GGrid.length && xB >0 && xB < GGrid.length)
					{
						Rectangle rec = new Rectangle(xB*cellSize-(cellSize/2),yA*cellSize-(cellSize/2),cellSize,cellSize);
						g.setColor(zoneCol);
						g.fill(rec);
					}
					
				}
							
			}*/
		}
	}
			
	
}
