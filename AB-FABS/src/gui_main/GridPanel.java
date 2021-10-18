package gui_main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cell_data_holders.Cell;
import cell_data_holders.CellHandler;
import cycle_components.BezManager;
import cycle_components.CellManager;
import cycle_components.DeathManager;
import cycle_components.MoveManager;
import cycle_components.RepManager;
import cycle_components.StaticCalculations;
import cycle_components.TrajectoryManager;
import cycles.Cycle;
import cycles.LiveCycle;
import singleton_holders.SingletonHolder;
import trajectory_components.RandomTrajMaker;
import trajectory_components.RouletteTrajMaker;
import environment_data_holders.CurveIndicator;
import environment_data_holders.GCell;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;
import graphics_modules.BezDrawModule;
import graphics_modules.BoundryAndBackModule;
import graphics_modules.CAModule;
import graphics_modules.CircleModule;
import graphics_modules.EllipseModule;
import graphics_modules.GraphModule;
import graphics_modules.MosaicModule;
import graphics_modules.ZoneModule;

@SuppressWarnings("serial")
public class GridPanel extends JPanel implements Runnable
{

	Cycle cycle;
	
	private static final double PREF_W = 1500;
  	private static final double PREF_H = 1500;  	
   
   int dimensions = 0;
   int cellNum = 0;
   
   int painting = 0;
   int pos = 0;
   
   MosaicModule mos;
   BoundryAndBackModule back;
   BezDrawModule bez;
   ZoneModule zon;

private CircleModule circle;

private GraphModule ellipse;

   public GridPanel(Cycle cycle2 , Boolean ca)
   {
	   dimensions = SingletonHolder.getSize();
	   SingletonHolder.setPanelSize(dimensions); 
	   cycle = cycle2;
	   cycle.giveGraphics(this.getGraphics(), this);
	   cycle.giveDims(dimensions, cellNum);
	   Dimension dim = new Dimension(dimensions,dimensions);
	   this.setPreferredSize(dim);
	   this.setBackground(Color.GRAY);
	   cycle.setup();
	   pos = cycle.getPosSize();
	   mos = new MosaicModule(dimensions, pos);
	   back = new BoundryAndBackModule();
	   bez = new BezDrawModule();
	   circle = new CircleModule();
	   zon = new ZoneModule();
	   if(ca)
	   {
		   ellipse = new CAModule();
	   }else
	   {
		   ellipse = new EllipseModule();
	   }
   }
   
   @SuppressWarnings("unchecked")

	@Override
   protected void paintComponent(Graphics g) 
   {
		   
	   super.paintComponent(g);
	   Graphics2D g2 = (Graphics2D) g;
	   //g2.drawImage(buffer2,0,0,this);
	   /* List<Polygon> polys2 = polys;
		  
		Polygon pol = new Polygon();
		for(int i = 0; i < cells.length; i ++)
		{
			pol = polys2.get(i);
			int[][] polyPoints = this.generatePoly(cells[i]);
			//polyPoints = translatePoly(polyPoints,cells[i].getVect());
			pol = new Polygon(polyPoints[0],polyPoints[1],polyPoints[0].length);
			g2.setColor(Color.WHITE);
			g2.fill(pol);
		}*/

	   
	   
		Color col = Color.WHITE;
		
		Color bg = Color.DARK_GRAY;
		if(SingletonHolder.isInvertHeat())
		{
			bg = Color.WHITE;
		}
		
		//Mosaic module
		if(SingletonHolder.isMos()&&SingletonHolder.isBack()==false)
		{
			mos.genNext(g2, cycle, this);
		}
		
		
		if(SingletonHolder.isShowZones())
		{
			zon.genNext(g2,cycle,this);
		}
		//boundry and background
		back.genNext(g2, this);
		
		//bezGR
		
		AffineTransform oldXForm = g2.getTransform();
		if(SingletonHolder.isBezGRChanges())
		{
			bez.gRChange(cycle);
			
		}
		if(SingletonHolder.isBezFlag())
		{
			bez.genNext(g2, cycle, oldXForm);
		}
		//shouldnt really be here dont modularise fix
		/*if(SingletonHolder.getPush() > 0)
		{
			for (int i = 0; i < cycle.getCells().size(); i++)
			{
				Cell cell = cycle.getCells().get(i);
				cell.setPositionX2(cell.getPositionX()+SingletonHolder.getPush());
				cell.setPositionY2(cell.getPositionY()+SingletonHolder.getPush());
			}
		}*/
		
		Color ent = Color.white;
		if(SingletonHolder.isInvertHeat())
		{
			ent = Color.BLACK;
		}
		if(SingletonHolder.isCirc() ==false) 
		{
			g2 =ellipse.genNext(g2, this, cycle,  oldXForm, ent);
			
		}else
		{
		
			if(SingletonHolder.isCirc()) 
			{
				circle.genNext(g2,this, cycle, oldXForm, ent);
			
			}
		}
		
	  
   }
   
	/*private Image genBuffer(int width, int height) 
	{
		  
		Image image = this.createVolatileImage((int)width, (int)height);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		 List<Polygon> polys2 = polys;
		  
		Polygon pol = new Polygon();
		for(int i = 0; i < cells.length; i ++)
		{
			pol = polys2.get(i);
			int[][] polyPoints = this.generatePoly(cells[i]);
			//polyPoints = translatePoly(polyPoints,cells[i].getVect());
			pol = new Polygon(polyPoints[0],polyPoints[1],polyPoints[0].length);
			g2.setColor(Color.WHITE);
			g2.fill(pol);
		}

		Color col = Color.WHITE;
		Color bg = Color.DARK_GRAY;
		image.getGraphics().setColor(Color.darkGray);
		if(SingletonHolder.isBoundary())
		{
			this.setBackground(col);
			if(SingletonHolder.getBoundaryType().equals("circular"))
			{
				Ellipse2D circ3 = new Ellipse2D.Double((SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,(SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength(),SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength());
				g2.setColor(bg);
				g2.fill(circ3);
				g2.setColor(col);
				//g2.draw(circ3);
			}else
			{
				Rectangle2D circ3 = new Rectangle2D.Double((SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,(SingletonHolder.getSize()/2)+SingletonHolder.getPush()-(SingletonHolder.getBoundarySize())-SingletonHolder.getCellLength()/2,SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength(),SingletonHolder.getBoundarySize()*2+SingletonHolder.getCellLength());
				g2.setColor(bg);
				g2.fill(circ3);
				g2.setColor(col);
			}
		}else
		{
			this.setBackground(bg);
		}
		List<Ellipse2D> circles2 = getCircles();
		Ellipse2D circ = new Ellipse2D.Double(0,0,getPrefW(),getPrefH());
		AffineTransform oldXForm = g2.getTransform();
		if(SingletonHolder.isBezFlag())
		{

			//System.out.println("pull "+curves.size());
			for(int i = 0; i < curves.size(); i++)
			{
				g2.setColor(Color.green);
				CubicCurve2D.Double curve = new  CubicCurve2D.Double();
				curve.setCurve(curves.get(i));
				// curve.setCurve(new Point2D[]{curve.getP1(), curve.getCtrlP1(),curve.getCtrlP2(),curve.getP2()}, SingletonHolder.getPush());
				g2.setStroke(new BasicStroke((float) (SingletonHolder.getBezSize()*SingletonHolder.getCellLength())));
				g2.translate(SingletonHolder.getPush(), SingletonHolder.getPush());
				g2.draw(curve);
				  
				int pointer = 1;
				for(int l = 0 ; l < curvePoints.get(i).length;l++)
				{
					g2.setColor(Color.black); 
					g2.fillOval((int)curvePoints.get(i)[l][0], (int)curvePoints.get(i)[l][1], 5, 5);
					if(l/(pointer*4) == 1)
					{
						g.drawString((int)curvePoints.get(i)[l][0]+" "+(int)curvePoints.get(i)[l][1], (int)curvePoints.get(i)[l][0],(int)curvePoints.get(i)[l][1]);
						pointer++;
					}
					for(int x = (int) (curvePoints.get(i)[l][0]-1) ; x < curvePoints.get(i)[l][0]+2;x++)
					{
						for(int y = (int) (curvePoints.get(i)[l][1]-1) ; y < curvePoints.get(i)[l][1]+2;y++)
						{
							g2.setColor(Color.MAGENTA); 
							g2.fillOval(x, y, 1, 1);
						}
					}
				}
				  
				  
		    	g2.setTransform(oldXForm);
			}
			  
			 for(int l = 0 ; l < SingletonHolder.getSize()/cellSize;l++)
			{
				for(int m = 0 ; m < SingletonHolder.getSize()/cellSize;m++)
				{
					if(positionGrid.getGCell(l*cellSize, m*cellSize).getNearbyPointHolder().size()>0)
					{
						//System.out.println(""+positionGrid.getGCell(l, m).getNearbyPointHolder().size()+" "+l+" "+m);
						g2.setColor(Color.blue); 
						g2.fillOval((int)(l*cellSize),(int)(m*cellSize), 5, 5);
					}
				}
			}
			for(int l = 0 ; l < SingletonHolder.getSize()/cellSize;l++)
			{
				for(int m = 0 ; m < SingletonHolder.getSize()/cellSize;m++)
				{
					if(positionGrid.getGCell(l*cellSize, m*cellSize).getCurves().getCurves().size()>0)
					{
						//System.out.println(""+positionGrid.getGCell(l, m).getNearbyPointHolder().size()+" "+l+" "+m);
						g2.setColor(Color.orange); 
						g2.fillOval((int)(l*cellSize),(int)(m*cellSize), 5, 5);
					}
				}
			}
			CubicCurve2D.Double curve = new CubicCurve2D.Double();
			curve.setCurve(0,0,800.00,900.00,700.00,800.00,SingletonHolder.getSize(),SingletonHolder.getSize());
			g2.setColor(Color.GREEN);
			g2.draw(curve);
			CubicCurve2D.Double curve2 = new CubicCurve2D.Double();
			curve2.setCurve(0,0,700.00,800.00,800.00,900.00,SingletonHolder.getSize(),SingletonHolder.getSize());
			g2.setColor(Color.GREEN);
			g2.draw(curve2);
		}
		  
		  
		for (int i = 0; i < getCells().size(); i++)
		{
			Cell cell = getCells().get(i);
			cell.setPositionX2(cell.getPositionX()+SingletonHolder.getPush());
			cell.setPositionY2(cell.getPositionY()+SingletonHolder.getPush());
		}
		  
		for (int i = 0; i < getCells().size(); i++)
		{
			circ = circles2.get(i);
			g2.rotate(Math.toRadians(getCells().get(i).getVect()),getCells().get(i).getPositionX2(),getCells().get(i).getPositionY2());
			circ.setFrame(getCells().get(i).getPositionX2(),getCells().get(i).getPositionY2(),SingletonHolder.getCellLength(),SingletonHolder.getCellLength()/2);
			g2.setColor(col);
			//g.drawString(i+"", (int)getCells()[i].getPositionX2(),(int)getCells()[i].getPositionY2());
			//g.drawString(getCells()[i].getLastAction(), (int)getCells()[i].getPositionX2(),(int)getCells()[i].getPositionY2());
			//g.drawString(getCells()[i].getPositionX2()+", "+getCells()[i].getPositionY2(), (int)getCells()[i].getPositionX2(),(int)getCells()[i].getPositionY2());
			//Shape circ2 = AffineTransform.getRotateInstance(cells[i].getVect()).createTransformedShape(circ);
			//System.out.println(+SingletonHolder.getPush());
	    	g2.fill(circ);
	    	g2.setTransform(oldXForm);
	    	Cell c = getCells().get(i);
	    	//g2.fillRect((int)c.getPositionX()-20/2,(int)c.getPositionY()-20/2,20,20);
		 }
		return image;
	}*/
	
   public double getPrefW() {
		return PREF_W;
	}

	public double getPrefH() {
		return PREF_H;
	}

	@Override
	public Dimension getPreferredSize() 
	{
		return new Dimension((int)getPrefW(),(int)getPrefH());
	}

	@Override
	public void run() {
		cycle.run();
		
	}

	public Posi getPositionGrid() {
		// TODO Auto-generated method stub
		return cycle.getPositionGrid();
	}

	

	public Cycle getCycle() {
		// TODO Auto-generated method stub
		return cycle;
	}

	
	
	
}
