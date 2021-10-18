package graphics_modules;

import gui_main.GridPanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import cycles.Cycle;
import cycles.LiveCycle;
import singleton_holders.SingletonHolder;

public class MosaicModule extends GraphModule{

	  Rectangle[][] mosTangles;
	  
	public MosaicModule(int dimensions, int pos)
	{
		mosTangles = new Rectangle[dimensions/pos+1][dimensions/pos+1];
		   for(int x = 0; x < mosTangles.length; x++)
		   {
			   for(int y = 0; y < mosTangles[x].length; y++)
			   {
				   mosTangles[x][y] = new Rectangle(x*pos,y*pos,pos,pos);
			   } 
		   }
	}

	public void genNext(Graphics2D g, Cycle cycle, GridPanel grid)
	{
		ArrayList<double[]> list = cycle.getSim().getUpdatedMosSites();
		/*for(int i = 0; i < list.size(); i++)
		{
			double[] p = list.get(i);
			Rectangle rec = mosTangles[(int) p[0]][(int) p[1]];
		}*/
		cycle.getSim().getMos().updateCol( SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
		int[][][] colu = cycle.getSim().getMos().getColourValues();
		Boolean gen = cycle.isMp4Gen();
		if(gen == false)
		{
			Rectangle dim = grid.getVisibleRect();
			double imgX = grid.getWidth();
			double imgY = grid.getHeight();
			double size = SingletonHolder.getSize();
			double xRatio = size/imgX;
			double yRatio = size/imgY;
			int x1 = (int) (dim.x*xRatio);
			int y1 = (int) (dim.y*yRatio);
			int x2 = (int)((dim.x+dim.getWidth())*xRatio);
			int y2 = (int)((dim.y+dim.getHeight())*yRatio);
			double xLen = mosTangles.length;
			double yLen = mosTangles[0].length;
			double xGridRatio = size/xLen;
			double yGridRatio = size/yLen;
			//int count = 0;
			//long time = System.currentTimeMillis();
			for(int x = (int) (x1/xGridRatio); x < mosTangles.length; x++)
			{
				//System.out.println("x "+x);
				for(int y = (int) (y1/yGridRatio); y < mosTangles[x].length; y++)
				{
					
					int[] p = colu[x][y];
					if(p[2]>255)
					{
						//System.out.println(p[0]+" "+p[1]+" "+p[2]);
						p[2] = 255;
					}

					if(p[1]>255)
					{
						//System.out.println(p[0]+" "+p[1]+" "+p[2]);
						p[1] = 255;
					}
					if(p[0]>255)
					{
						//System.out.println(p[0]+" "+p[1]+" "+p[2]);
						p[0] = 255;
					}

					if(p[0]>255)
					{
						//System.out.println(p[0]+" "+p[1]+" "+p[2]);
						p[0] = 255;
					}
					if(p[1]>255)
					{
						//System.out.println(p[0]+" "+p[1]+" "+p[2]);
						p[1] = 255;

					}
					//System.out.println(p[0]+" "+p[1]+" "+p[2]);
					g.setColor(new Color(p[0],p[1],p[2]));
					g.fill(mosTangles[x][y]);
					//count++;
				}
			}
		}else
		{
			for(int x = 0; x < mosTangles.length; x++)
			{
				for(int y = 0; y < mosTangles[x].length;y++)
				{
					
					int[] p = colu[x][y];
					if(p[2]>255)
					{
						//System.out.println(p[0]+" "+p[1]+" "+p[2]);
						p[2] = 255;
					}

					if(p[1]>255)
					{
						//System.out.println(p[0]+" "+p[1]+" "+p[2]);
						p[1] = 255;
					}
					if(p[0]>255)
					{
						//System.out.println(p[0]+" "+p[1]+" "+p[2]);
						p[0] = 255;
					}

					if(p[0]>255)
					{
						//System.out.println(p[0]+" "+p[1]+" "+p[2]);
						p[0] = 255;
					}
					if(p[1]>255)
					{
						//System.out.println(p[0]+" "+p[1]+" "+p[2]);
						p[1] = 255;

					}
					//System.out.println(p[0]+" "+p[1]+" "+p[2]);
					g.setColor(new Color(p[0],p[1],p[2]));
					g.fill(mosTangles[x][y]);
					//count++;
				}
			}
		}
			//System.out.println("mosTangles "+count+" time "+(time-System.currentTimeMillis()));
	}
	
}
