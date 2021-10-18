package graphics_modules;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import cycles.Cycle;
import cycles.LiveCycle;
import singleton_holders.SingletonHolder;

public class BezDrawModule extends GraphModule{

	ArrayList<Path2D> greens = new  ArrayList<Path2D>();
	ArrayList<Path2D> reds = new  ArrayList<Path2D>();
	
	public void gRChange(Cycle cycle) {
		System.out.println("getting past flag");
		cycle.getBezMan().populateGR(SingletonHolder.getBezRed(),SingletonHolder.getBezGreen(),cycle.getPositionGrid(), SingletonHolder.getBezSize());
		SingletonHolder.setBezGRChanges(false);
		greens = new ArrayList<Path2D>();
		reds = new ArrayList<Path2D>();
		ArrayList<ArrayList<double[]>> green = cycle.getBezMan().getGreenStrands();
		System.out.println("into green "+green.size());
		for(int i = 0; i < green.size(); i++)
		{
			ArrayList<double[]> strand = green.get(i);
			Path2D path = new Path2D.Double();
			path.moveTo(strand.get(0)[0],strand.get(0)[1]);
			path.lineTo(strand.get(0)[0],strand.get(0)[1]);
			for(int l = 1; l < strand.size();l++)
			{
				path.lineTo(strand.get(l)[0],strand.get(l)[1]);
			}
			
			path.closePath();
			greens.add(path);
		}
		ArrayList<ArrayList<double[]>> red = cycle.getBezMan().getRedStrands();
		System.out.println("into red "+red.size());
		for(int i = 0; i < red.size(); i++)
		{
			ArrayList<double[]> strand = red.get(i);
			Path2D path = new Path2D.Double();
			path.moveTo(strand.get(0)[0],strand.get(0)[1]);
			for(int l = 1; l < strand.size();l++)
			{
				path.lineTo(strand.get(l)[0],strand.get(l)[1]);
			}
			path.closePath();
			reds.add(path);
		}
		System.out.println("g size "+greens.size()+" r size"+reds.size());
		
	}

	public void genNext(Graphics2D g2, Cycle cycle, AffineTransform oldXForm) {
		boolean showCurves = false;
		if(showCurves)
		{
			for(int i = 0; i < cycle.getCurves().size(); i++)
			{
				g2.setColor(Color.gray);
				CubicCurve2D.Double curve = new  CubicCurve2D.Double();
				curve.setCurve(cycle.getCurves().get(i));
				// curve.setCurve(new Point2D[]{curve.getP1(), curve.getCtrlP1(),curve.getCtrlP2(),curve.getP2()}, SingletonHolder.getPush());
				g2.setStroke(new BasicStroke((float) (SingletonHolder.getBezSize()*2)));
				g2.translate(SingletonHolder.getPush(), SingletonHolder.getPush());
				g2.draw(curve);
				
				/*g2.setColor(Color.green);
				CubicCurve2D.Double curve1 = new  CubicCurve2D.Double();
				curve1.setCurve(cycle.getCurves().get(i));
				Double movedCurve = new CubicCurve2D.Double(); 
				if(curve1.x1 == 0 || curve1.x1 == SingletonHolder.getSize())
				{
					movedCurve.x1 = curve1.x1-4;
					movedCurve.y1 = curve1.y1;

					movedCurve.ctrlx1 = curve1.ctrlx1-4;
					movedCurve.ctrly1 = curve1.ctrly1;
				}else
				{
					movedCurve.x1 = curve1.x1;
					movedCurve.y1 = curve1.y1-4;

					movedCurve.ctrlx1 = curve1.ctrlx1;
					movedCurve.ctrly1 = curve1.ctrly1-4;
				}
				if(curve1.x2 == 0 || curve1.x2 == SingletonHolder.getSize())
				{
					movedCurve.x2 = curve1.x2-4;
					movedCurve.y2 = curve1.y2;
					
					movedCurve.ctrlx2 = curve1.ctrlx2-4;
					movedCurve.ctrly2 = curve1.ctrly2;
				}else
				{
					movedCurve.x2 = curve1.x2;
					movedCurve.y2 = curve1.y2-4;
					
					movedCurve.ctrlx2 = curve1.ctrlx2;
					movedCurve.ctrly2 = curve1.ctrly2-4;
				}
				
				curve1.setCurve(movedCurve);
				// curve.setCurve(new Point2D[]{curve.getP1(), curve.getCtrlP1(),curve.getCtrlP2(),curve.getP2()}, SingletonHolder.getPush());
				g2.setStroke(new BasicStroke((float) (2)));
				g2.translate(SingletonHolder.getPush(), SingletonHolder.getPush());
				g2.draw(curve1);
				
				g2.setColor(Color.red);
				CubicCurve2D.Double curve2 = new  CubicCurve2D.Double();
				curve2.setCurve(cycle.getCurves().get(i));
				movedCurve = new CubicCurve2D.Double(); 
				if(curve2.x1 == 0 || curve2.x1 == SingletonHolder.getSize())
				{
					movedCurve.x1 = curve2.x1+4;
					movedCurve.y1 = curve2.y1;

					movedCurve.ctrlx1 = curve2.ctrlx1+4;
					movedCurve.ctrly1 = curve2.ctrly1;
				}else
				{
					movedCurve.x1 = curve2.x1;
					movedCurve.y1 = curve2.y1+4;

					movedCurve.ctrlx1 = curve2.ctrlx1;
					movedCurve.ctrly1 = curve2.ctrly1+4;
				}
				if(curve1.x2 == 0 || curve2.x2 == SingletonHolder.getSize())
				{
					movedCurve.x2 = curve2.x2+4;
					movedCurve.y2 = curve2.y2;

					movedCurve.ctrlx2 = curve2.ctrlx2+4;
					movedCurve.ctrly2 = curve2.ctrly2;
				}else
				{
					movedCurve.x2 = curve2.x2;
					movedCurve.y2 = curve2.y2+4;

					movedCurve.ctrlx2 = curve2.ctrlx2;
					movedCurve.ctrly2 = curve2.ctrly2+4;
				}
				curve2.setCurve(movedCurve);
				// curve.setCurve(new Point2D[]{curve.getP1(), curve.getCtrlP1(),curve.getCtrlP2(),curve.getP2()}, SingletonHolder.getPush());
				g2.setStroke(new BasicStroke((float) (2)));
				g2.translate(SingletonHolder.getPush(), SingletonHolder.getPush());
				g2.draw(curve2);
				*/
				int pointer = 1;
				/*for(int l = 0 ; l < curvePoints.get(i).length;l++)
				{
					g2.setColor(Color.black); 
					g2.fillOval((int)curvePoints.get(i)[l][0], (int)curvePoints.get(i)[l][1], 5, 5);
					if(l/(pointer*4) == 1)
					{
						//g.drawString((int)curvePoints.get(i)[l][0]+" "+(int)curvePoints.get(i)[l][1], (int)curvePoints.get(i)[l][0],(int)curvePoints.get(i)[l][1]);
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
				}*/
				  
				  
		    	g2.setTransform(oldXForm);
			}
		}
		
		if(SingletonHolder.isBezStrengthFlag())
		{
			g2.setStroke(new BasicStroke((float) (1)));
			//System.out.println("getting past flag");
			for(int i = 0; i < greens.size(); i++)
			{

				//System.out.println("into green");
				//g2.setColor(Color.GREEN);
				//g2.draw(greens.get(i));
				
				g2.setColor(Color.GREEN);
				g2.setStroke(new BasicStroke((float) (1)));
				
				g2.draw(greens.get(i));
			}
			for(int i = 0; i < reds.size(); i++)
			{

				//System.out.println("into red");
				//g2.setColor(Color.RED);
				//g2.draw(reds.get(i));
				g2.setColor(Color.RED);
				g2.setStroke(new BasicStroke((float) (1)));
				
				g2.draw(reds.get(i));
			}
			boolean showBoxes = false;
			if(showBoxes)
			{
				ArrayList<ArrayList<double[]>> bxs = SingletonHolder.getBoxHolder();
				int n = 0;
				int ttl = 0;
				for(int i = 0; i < bxs.size(); i++)
				{
					n++;
					if( n == 1)
					//if(i == 50 || i == 75 || i == 100)
					{
						int m = 0;
						for(int l = 0; l < bxs.get(i).size();l++)
						{
							if (m == 1)
							{
								Path2D path = new Path2D.Double();
								double[] box = bxs.get(i).get(l);
								//top left top right bottom right bottom left to top left again
								path.moveTo(box[0], box[1]);
								path.lineTo(box[2], box[3]);
								path.lineTo(box[6], box[7]);
								path.lineTo(box[4], box[5]);
								path.lineTo(box[0], box[1]);
								path.closePath();
								//g2.rotate(Math.toRadians(bxs.get(i)[l][4]),bxs.get(i)[l][2],bxs.get(i)[l][3]);
								//Rectangle2D rec = new Rectangle2D.Double(bxs.get(i)[l][0],bxs.get(i)[l][1],1,1);
								//System.out.println("bxs size "+bxs.size()+" x "+bxs.get(i)[l][0]+" y "+bxs.get(i)[l][1]);
								g2.setColor(Color.blue);
								g2.setStroke(new BasicStroke((float) (1)));
								//g2.fillRect((int)box[0], (int)box[1],1,1);
								//g2.fillRect((int)box[2], (int)box[3],1,1);
								//g2.fillRect((int)box[6], (int)box[7],1,1);
								//g2.fillRect((int)box[4], (int)box[5],1,1);
								
								g2.draw(path);
								g2.setColor(Color.white);
								ttl++;
								if(ttl >= 30)
								{
									g2.drawString(""+box[8],(int)box[0]+20, (int)box[1]);
									ttl = 0;
									g2.setColor(Color.blue); 
									//g2.fillRect((int)box[0], (int)box[1],1,1);
									//g2.fillRect((int)box[2], (int)box[3],1,1);
									//g2.fillRect((int)box[6], (int)box[7],1,1);
									//g2.fillRect((int)box[4], (int)box[5],1,1);
								}
						    	//g2.setTransform(oldXForm);
						    	n = 0;
						    	//g2.setColor(Color.blue);
								//g2.drawLine((int)bxs.get(i)[l][0],(int)bxs.get(i)[l][1],(int)bxs.get(i)[l][2],(int)bxs.get(i)[l][3]);
						    	m = 0;
							}
							m++;
						}
					}
					//g2.setColor(Color.blue);
					//g2.drawLine((int)bxs.get(i)[0][0],(int)bxs.get(i)[0][1],(int)bxs.get(i)[0][2],(int)bxs.get(i)[0][3]);
				}
			}
			boolean showSpline = false;
			if(showSpline)
			{
				ArrayList<double[][]> curPoints = cycle.getBezMan().getCurvePoints();
				for(int l = 0; l < curPoints.size(); l++)
				{
					double[][] curP = curPoints.get(l);
					for(int m = 0; m < curP.length;m++)
					{
						g2.setColor(Color.orange); 
						g2.fillOval((int)(curP[m][0]),(int)(curP[m][1]), 1, 1);
					}
				}
			}
			/*n = 0;
			if(bxs.get(0).length>3)
			{
				for(int i = 0; i < bxs.size(); i++)
				{
					n++;
					if( n == 3)
					//if(i == 50 || i == 75 || i == 100)
					{
						g2.rotate(Math.toRadians(bxs.get(i)[3][4]),bxs.get(i)[3][0],bxs.get(i)[3][1]);
						//Rectangle2D rec = new Rectangle2D.Double(bxs.get(i)[0][0],bxs.get(i)[0][1],1,4);
						//System.out.println("bxs size "+bxs.size()+" x "+bxs.get(i)[0][0]+" y "+bxs.get(i)[0][1]);
						//g2.setColor(Color.blue);
						//g2.draw(rec);
						g2.setStroke(new BasicStroke((float) (1)));
				    	g2.setTransform(oldXForm);
				    	n = 0;
				    	g2.setColor(Color.green);
						g2.drawLine((int)bxs.get(i)[3][0],(int)bxs.get(i)[3][1],(int)bxs.get(i)[3][2],(int)bxs.get(i)[3][3]);
					}
					//g2.setColor(Color.blue);
					//g2.drawLine((int)bxs.get(i)[0][0],(int)bxs.get(i)[0][1],(int)bxs.get(i)[0][2],(int)bxs.get(i)[0][3]);
				}
			}
			n = 0;
			if(bxs.get(0).length>7)
			{
				for(int i = 0; i < bxs.size(); i++)
				{
					n++;
					if( n == 3)
					//if(i == 50 || i == 75 || i == 100)
					{
						g2.rotate(Math.toRadians(bxs.get(i)[7][4]),bxs.get(i)[7][0],bxs.get(i)[7][1]);
						//Rectangle2D rec = new Rectangle2D.Double(bxs.get(i)[0][0],bxs.get(i)[0][1],1,4);
						//System.out.println("bxs size "+bxs.size()+" x "+bxs.get(i)[0][0]+" y "+bxs.get(i)[0][1]);
						//g2.setColor(Color.blue);
						//g2.draw(rec);
						g2.setStroke(new BasicStroke((float) (1)));
				    	g2.setTransform(oldXForm);
				    	n = 0;
				    	g2.setColor(Color.red);
						g2.drawLine((int)bxs.get(i)[7][0],(int)bxs.get(i)[7][1],(int)bxs.get(i)[7][2],(int)bxs.get(i)[7][3]);
					}
					//g2.setColor(Color.blue);
					//g2.drawLine((int)bxs.get(i)[0][0],(int)bxs.get(i)[0][1],(int)bxs.get(i)[0][2],(int)bxs.get(i)[0][3]);
				}
			}*/
		}
		/*for(int l = 0 ; l < positionGrid.getGrid().length;l++)
		{
			for(int m = 0 ; m < positionGrid.getGrid().length;m++)
			{
				if(positionGrid.getGCell(l, m).getNearbyPointHolder().size()>0)
				{
					//System.out.println(""+positionGrid.getGCell(l, m).getNearbyPointHolder().size()+" "+l+" "+m);
					g2.setColor(Color.blue); 
					g2.fillOval((int)(l*positionGrid.getCellSize()),(int)(m*positionGrid.getCellSize()), 5, 5);
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
		g2.draw(curve2);*/
		/*
			
				for(int x = -SingletonHolder.getBezSize(); x <SingletonHolder.getBezSize(); x++)
				{
					for(int y = -SingletonHolder.getBezSize(); y <SingletonHolder.getBezSize(); y++)
					{
						if(curP[m][0]+x >=0 && curP[m][1]+y >=0 && curP[m][0]+x <SingletonHolder.getSize() && curP[m][1]+y < SingletonHolder.getSize())
						{
							GCell cll = cycle.getPositionGrid().getGCell(curP[m][0]+x, curP[m][1]+y);
							double str = cll.getBezStrength();
							if(str!= 0)
							{
								if(str > SingletonHolder.getBezGreen())
								{
									g2.setColor(Color.green); 
									g2.fillOval(cll.getX(),cll.getY(),1,1);
								}else
								{
									if(str < SingletonHolder.getBezRed())
									{
										g2.setColor(Color.red); 
										g2.fillOval(cll.getX(),cll.getY(),1,1);
									}
								}
							}
						}
					}
				}
			}
		}*/
		
	}

}
