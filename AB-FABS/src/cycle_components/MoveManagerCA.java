package cycle_components;

import java.awt.geom.CubicCurve2D.Double;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cell_data_holders.Cell;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import environment_data_holders.CurveIndicator;
import environment_data_holders.GCell;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;
import environment_data_holders.PositionGridCA;

public class MoveManagerCA extends MoveManager{


	int pos = 0;
	
	public MoveManagerCA(int po)
	{
		
		pos = po;
	}
	
	private double genBoundBounce(Cell cell,double simSize, Posi posi) 
	{
		cell.setVect(genOpposite(cell.getVect()), posi);
		SingletonStatStore.pollBounceTurn(cell);
		return cell.getVect();
	}
	
	public ArrayList<Cell> incrementAllCells(ArrayList<Cell> cells, double size,List<Ellipse2D> circles,Posi posi) {
		for(int i = 0; i < cells.size();i++)
		{
			if(i == 0)
			{
				//System.out.println(+store.getNum());
			}
			if(cells.get(i).isLiving()&&cells.get(i).isCorporeal())
			{
				//System.out.println("pre inc "+cells.get(i).getPositionX()+", "+cells.get(i).getPositionY());
				incrementCell(cells.get(i),i, size, circles, posi);
				//System.out.println("post inc "+cells.get(i).getPositionX()+", "+cells.get(i).getPositionY());
			}
		}
		return cells;
	}
	
	public void genPosGrid(int dimensions, int posSize) {
		positionGrid = new PositionGridCA(dimensions,posSize);
		
	}
	
	public void genPosGrid(int dimensions, int posSize, int[][] heat) {
		positionGrid = new PositionGridCA(dimensions,posSize,heat);
		
	}
	double genOpposite(double vect)
	{
		if(vect >22.5 && vect <=67.5)
		{
			//vect = 45;
			vect = 225;
		}else
		{
			if(vect >67.5 && vect <=112.5)
			{
				//vect = 90;
				vect = 270;
			}else
			{
				if(vect >112.5 && vect <=157.5)
				{
					//vect = 135;
					vect = 315;
				}else
				{
					if(vect >157.5 && vect <=202.5)
					{
						//vect = 180;
						vect = 0;
					}else
					{
						if(vect >202.5 && vect <=247.5)
						{
							//vect = 225;
							vect = 45;
						}else
						{
							if(vect >247.5 && vect <=292.5)
							{
								//vect = 270;
								vect = 90;
							}else
							{
								if(vect >292.5 && vect <=337.5)
								{
									//vect = 315;
									vect = 135;
								}else
								{
									if(vect > 337.5 || vect < 22.5 )
									{
										//vect = 0;
										vect = 180;
									}else
									{
										System.out.println("out gen opp");
									}
								}
							}
						}
					}
				}
			}
		}
		
		return vect;
	}
	
	
	
	
	//when doing jump varience change all jump size/increment algorithms
	public double[] projectNext(Cell c, double jump, double dimensions) 
	{
		double[] next = new double[2];
		int x = 0;
		int y = 0;
		x = genNextJumpX(c);
		y = genNextJumpY(c);
		if(x < 0)
		{
			x = (int) (dimensions-1);
		}
		if(y < 0)
		{
			y = (int) (dimensions-1);
		}
		if(x > dimensions)
		{
			x = 1;
		}
		if(y > dimensions)
		{
			y = 1;
		}
		next[0] = x;
		next[1] = y;
		return next;
	}
	
	

	private int genNextJumpY(Cell c) {
		int y = (int) c.getPositionY();
		double vect = c.getVect();
		if(vect >22.5 && vect <=67.5)
		{
			//vect = 45;
			y = y-pos;
		}else
		{
			
			if(vect >112.5 && vect <=157.5)
			{
				//vect = 135;
				y = y+pos;
			}else
			{
				if(vect >157.5 && vect <=202.5)
				{
					//vect = 180;
					y = y+pos;
				}else
				{
					if(vect >202.5 && vect <=247.5)
					{
						//vect = 225;
						y = y+pos;
					}else
					{
						
						if(vect >292.5 && vect <=337.5)
						{
							//vect = 315;
							y = y-pos;
						}else
						{
							if(vect > 337.5 || vect < 22.5 )
							{
								//vect = 0;
								y = y-pos;
							}else
							{
								//System.out.println("y vect out "+vect);
							}
						}
							
						
					}
				}
			}
		}
		return y;
	}

	private int genNextJumpX(Cell c) {
		int x = (int) c.getPositionX();
		double vect = c.getVect();
		if(vect >22.5 && vect <=67.5)
		{
			//vect = 45;
			x = x+pos;
		}else
		{
			if(vect >67.5 && vect <=112.5)
			{
				//vect = 90;
				x = x+pos;
			}else
			{
				if(vect >112.5 && vect <=157.5)
				{
					//vect = 135;
					x = x+pos;
				}else
				{
					
					if(vect >202.5 && vect <=247.5)
					{
						//vect = 225;
						x = x-pos;
					}else
					{
						if(vect >247.5 && vect <=292.5)
						{
							//vect = 270;
							x = x-pos;
						}else
						{
							if(vect >292.5 && vect <=337.5)
							{
								//vect = 315;
								x = x-pos;
							}else
							{
								//System.out.println("x vect out "+vect);
							}
						}
					}
					
				}
			}
		}
		return x;
	}

	
	
	protected void incrementCell(Cell c ,int i, double dimensions,List<Ellipse2D> circles,Posi positionGrid) 
	{
		//System.out.println("ca inc");
		//add boundary conditions
		//Cell c = getStore().getCells().get(i);
		double olX = c.getPositionX();
		double olY = c.getPositionY();
		double x = this.genNextJumpX(c);
		double y = this.genNextJumpY(c);
		
		if(x < 0)
		{
			x = dimensions-1;
		}
		if(y < 0)
		{
			y = dimensions-1;
		}
		if(x > dimensions)
		{
			x = 1;
		}
		if(y > dimensions)
		{
			y = 1;
		}
		c.setPositionX(x);
		c.setPositionY(y);
		if(SingletonHolder.isTail())
		{
			c.addTailPoint(new double[]{x,y});
		}
		c.incrementDistance(pos);
		c.getPack().addTotalDistanceTravelled(pos);
		/*if(SingletonHolder.isCirc())
		{
			circles.get(i).setFrame(x-c.getCellSize()/2,y-c.getCellSize()/2,c.getCellSize(),c.getCellSize());
			
		}else
		{
			circles.get(i).setFrame(x-c.getCellSize()/2,y-c.getCellSize()/4,c.getCellSize(),c.getCellSize()/2);
		}
		//circles.get(i).setFrame(x,y,SingletonHolder.getCellLength(),SingletonHolder.getCellLength());
		 * */

		positionGrid.updatePosition(olX,olY,c,i);
		c.setPrevTrig(new double[]{x,y});
		if(c.getSpeed()== 0)
		{
			System.out.println(c.getSpeed());
		}
	}
	
	protected void detectOverlapAndFix( int i, ArrayList<Cell> cells, Posi positionGrid, ArrayList<Double> curves, TrajectoryManager trajMan, BezManager bezMan, double size, List<Ellipse2D> circles) 
	{
		//System.out.println("overlapp 1");
		Cell c = cells.get(i);
		if(c.isCorporeal())
		{
			c = bezMan.checkAgainstCurves(c, positionGrid, trajMan, size);
			
			
			//positionGrid.fullReport(cellMan.getStore().getCells());
			//System.exit(0);
			//cells2[i] = c;
			//cellMan.getStore().setCells(cells2);
	
			ArrayList<Integer> possibles = positionGrid.getPossibleOverlap(c,i,cells);
			if(possibles.isEmpty() == false)
			{
				/*if(possibles.size() > 1)
				{
					System.out.println("greater than 1 in grid");
				}*/
				c.setOverlapLast((int) SingletonHolder.getJumpCounter());
				c.incrementCollisions();
				c.getPack().addTotalCollisions(1.00);
				c.setVect(this.genOpposite(c.getVect()), positionGrid);
				
			}
		}
	
	}

	/*public Cell checkAgainstCurves(Cell c, Posi posi, TrajectoryManager trajMan,  double size, BezManager bezMan) {
		//bez handling before collision
		if(SingletonHolder.isBezFlag() && c.getOverlapLast() < SingletonHolder.getJumpCounter()-(c.getCellSize())/c.getSpeed()&& c.getBezLeaver() < SingletonHolder.getJumpCounter()-(c.getCellSize()+SingletonHolder.getBezSize())/c.getSpeed())
		{
			//check if already in curve
			if(c.isFollowingCurve())
			{
				GCell point = posi.getGCell(c.getPreviousPoint()[0],c.getPreviousPoint()[1]);
				CurveIndicator cur = point.getCurves();
				//if yes check if still in curve
				if(bezMan.isCurveOverlap(c,bezMan.getCurves().get(c.getCurve()), (int) (SingletonHolder.getBezSize()*c.getCellSize()), posi))
				{
					//if this cur doesnt have a point get the nearest cur with one
					if(cur.getCurves().size() == 0)
					{
						point = posi.getGCell(c.getPositionX(),c.getPositionY());
						ArrayList<double[]> points = point.getNearbyPointHolder();
						//System.out.println("point size "+ point.getNearbyPointHolder().size());
						if(point.getNearbyPointHolder().size() == 0)
						{
							//System.out.println("0 point "+c.getPositionX()+", "+c.getPositionY());
							
						}
						for(int l = 0; l < points.size(); l++)
						{
							//System.out.println("points: "+points.get(l)[0]+", "+points.get(l)[1]);
						}
						point = this.getNearestFromNearby(c,points, posi);
						
						cur = point.getCurves();
					}else
					{
						//System.out.println("curves not empty");
					}
					int curvePoint = 0;
					//check its only curve as well
					if(cur.getCurves().size() > 1)
					{
						//System.out.println("called "+cur.getCurves().size());
						//this.checkForCurvesAndAttatch(c);
						curvePoint = cur.getPositionOfCurve(c.getCurve());
					}
					
						
					//if yes and rolls remain iterate towards next point
					//System.out.println(""+cur.getCurves().size()+" "+cur.getEastPoints().size()+" "+cur.getNearestPoints().size()+" "+cur.getWestPoints().size());
					double[] next = new double[2];
					if(c.getCurveDirection() == 0)
					{
						next = cur.getWestPoints().get(curvePoint);
						c.setLastAction("follow west");
					}else
					{
						next = cur.getEastPoints().get(curvePoint);
						c.setLastAction("follow east");
					}
					Random rand = SingletonHolder.getMasterRandom();
					if(rand.nextInt(100001) < point.getBezStrength())
					{
						
						//System.out.println(previous[0]);
						if(next[0] == 0 && next[1] == 0)
						{
							///direct away from next point
							c.setLastAction("oot");
							//System.out.println("oot "+SingletonHolder.getBezChance());
							c.setBezLeaver(SingletonHolder.getJumpCounter());
						}else
						{
							if(inTabooList(next, c.getPreviousPoints()))
							{
								///direct away from next point
								c.setLastAction("oot");
								//System.out.println("oot "+SingletonHolder.getBezChance());
								c.setBezLeaver(SingletonHolder.getJumpCounter());
							}else
							{
								//direct to next point
								double direct = angleFromPositions(c.getPositionX(),c.getPositionY(),next[0],next[1]);
								direct = StaticCalculations.getOppositeAngle(direct);
								c.addPreviousPoint(next);
								c.setVect(direct);
							}
								
							
						}
						
						if(outMap(c, size))
						{
							///direct away from next point
							c.setLastAction("oot");
							System.out.println("oot "+point.getBezStrength());
							c.setBezLeaver(SingletonHolder.getJumpCounter());
						}
						//System.out.println("called");
						c.incrementFollowingBez();
						c.getPack().addTotalBezChosen(1.00);
					
					}else
					{
						//direct away from next point
						c.setLastAction("oot");
						//System.out.println("oot "+SingletonHolder.getBezChance());
						trajMan.genNextTraj(c); 
						c.setBezLeaver(SingletonHolder.getJumpCounter());
					}
					
				}else
				{
					//
					//if not make sure it isnt in a new one
					c.setFollowingCurve(this.checkForCurvesAndAttatch(c, posi));
					
				}
			}else
			{
				this.checkForCurvesAndAttatch(c, posi);
			}
		}
		return c;
	}*/
	
	
	private double angleFromPositions(double x, double y, double x2, double y2) {
		double ang = 0;
		if(x < x2 && y < y2)
		{
			ang = 315.00;
		}else
		{
			if(x > x2 && y < y2)
			{
				ang = 45.00;
			}else
			{
				if(x == x2 && y < y2)
				{
					ang = 0.00;
				}else
				{
					if(x < x2 && y > y2)
					{
						ang = 225.00;
					}else
					{
						if(x < x2 && y == y2)
						{
							ang = 270.00;
						}else
						{
							if(x > x2 && y > y2)
							{
								ang = 135.00;
							}else
							{
								if(x > x2 && y == y2)
								{
									ang = 90.00;
								}else
								{
									if(x == x2 && y > y2)
									{
										ang = 180.00;
									}else
									{
										ang = 360.00;
									}
								}
							}
						}
					}
				}
			}
		}
		return ang;
	}

	private double getAngularDistance(double vect, double d, double e, double points, double points2) 
	{
		double angle = Math.atan2(e-points2, d-points);
		angle = Math.toDegrees(angle);
		angle = StaticCalculations.getOppositeAngle(angle);
		//System.out.println("angle "+angle);
		double difference = Math.sqrt(Math.pow(vect - angle , 2));
		return difference;
	}
	private boolean inTabooList(double[] a,ArrayList<double[]> b) {
		boolean found = false;
		int i = 0;
		do{
			if(a[0] == b.get(i)[0]&&a[1] == b.get(i)[1])
			{
				found = true;
			}
			i++;
			
		}while (found == false && i < b.size());
		return found;
	}
	
	private boolean outMap(Cell c, double dimensions) {
		boolean oot = false;
		double x = c.getPositionX();
		double y = c.getPositionY();
		//System.out.println("outcheck" +x+" "+y );
		double vect = Math.toRadians(c.getVect());
		x = x + Math.cos(vect)*c.getSpeed();
		y = y + Math.sin(vect)*c.getSpeed();
		if(x < 0 || y < 0 || x > dimensions || y > dimensions)
		{
			oot = true;
			//System.out.println("hoot" +x+" "+y );
		}
		
		if(SingletonHolder.isBoundary() && oot == false)
		{
			if(StaticCalculations.getEuclidean(x, y, dimensions/2, dimensions/2) >= SingletonHolder.getBoundarySize()  )
			{
				oot = true;
				//System.out.println("toot");
			}
		}
		return oot;
	}
	
	/*private boolean checkForCurvesAndAttatch(Cell c, Posi posi) 
	{
		GCell point = posi.getGCell(c.getPositionX(),c.getPositionY());
		boolean found = false;
		CurveIndicator cur = point.getCurves();
		ArrayList<double[]> poss = point.getNearbyPointHolder();
		ArrayList<Integer> poss2 = new ArrayList<Integer>();
		Random rand = SingletonHolder.getMasterRandom();
		if(poss.size()>0 && SingletonHolder.isBezBounceFlag() == false && rand.nextInt(100001) < point.getBezStrength())
				
		{
			//System.out.println("follow");
			found = true;
			GCell target = getNearestFromNearby(c,poss, posi);
			//attatches to the first curve at that point not closest needs >>>>>>>>>>fixing<<<<<<<<<<
			double[] tw = target.getCurves().getWestPoints().get(0);
			double[] te = target.getCurves().getEastPoints().get(0);
			double westAngDist = Math.sqrt(c.getVect()-angleFromPositions(c.getPositionX(),c.getPositionY(),tw[0],tw[1]));
			double eastAngDist = Math.sqrt(c.getVect()-angleFromPositions(c.getPositionX(),c.getPositionY(),te[0],te[1]));
			if(westAngDist > eastAngDist)
			{
				c.setCurveDirection(1);
				c.setCurve(target.getCurves().getCurves().get(0));
				c.setFollowingCurve(true);
				c.addPreviousPoint(new double[]{te[0],te[0]});
				double angle = angleFromPositions(c.getPositionX(),c.getPositionY(),te[0],te[1]);
				angle = StaticCalculations.getOppositeAngle(angle);
				
				c.setLastAction("attatch east");
				c.setVect(angle);
			}else
			{
				c.setCurveDirection(0);
				c.setCurve(target.getCurves().getCurves().get(0));
				c.setFollowingCurve(true);
				c.addPreviousPoint(new double[]{tw[0],tw[0]});
				double angle = angleFromPositions(c.getPositionX(),c.getPositionY(),tw[0],tw[1]);
				angle = StaticCalculations.getOppositeAngle(angle);
				c.setVect(angle);
				c.setLastAction("attatch west");
			}
			c.incrementFollowingBez();

			c.getPack().addTotalBezChosen(1.00);
		}else
		{
			c.setFollowingCurve(false);
			if(SingletonHolder.isBezBounceFlag() && poss.size()>0 )
			{
				if(rand.nextInt(100001) < point.getBezStrength())
				{
					if(c.getBounceSequence().size() !=5 ||c.getBounceSequentialDifference() != c.getBounceSequence().size()-1 || SingletonHolder.getJumpCounter()-c.getLastBounce() > 1)
					{
						c.setVect(StaticCalculations.getOppositeAngle(c.getVect()));
						c.setLastBounce((int) SingletonHolder.getJumpCounter());
						SingletonStatStore.pollBounceTurn(c);
						//System.out.println("boing");
					}else
					{

						c.setBezLeaver(SingletonHolder.getJumpCounter());
					}
				}else
				{
					c.setBezLeaver(SingletonHolder.getJumpCounter());
				}
			}
		}
		
		return found;
	}*/
	
	
	private GCell getNearestFromNearby(Cell c, ArrayList<double[]> points, Posi posi) {
		double nearest = 1000;
		int pointer = 0;
		
		for(int l = 0; l < points.size(); l++)
		{
			//System.out.println("point "+l);
			GCell poss = posi.getGCell(points.get(l)[0], points.get(l)[1]);
			//System.out.println("poss nearest size "+poss.getCurves().getNearestPoints().size());
			//System.out.println(""+points.get(l)[0]+" "+points.get(l)[1]);
			int nearestHere = poss.getCurves().getNearestCurvePointAngular(c.getVect(),c.getPositionX(),c.getPositionY());
			ArrayList<double[]> pre = poss.getCurves().getNearestPoints();
			double[] curve = pre.get(nearestHere);
			double dist = this.getAngularDistance(c.getVect(),c.getPositionX(), c.getPositionY(), curve[0], curve[1]);
			//System.out.println("test 0,0 0,1 90 "+this.getAngularDistance(90,0, 0, 0, 1));
			//System.out.println("test 0,0 -1,0 90 "+this.getAngularDistance(90,0, 0, -1, 0));
			//System.exit(0);
			if(dist < nearest)
			{
				nearest = dist;
				pointer = l;
			}
		}
		GCell point = posi.getGCell(points.get(pointer)[0],points.get(pointer)[1]);
		return point;
	}
	

	

	

	
	public ArrayList<Cell> checkAllAndFixWithinBounds(ArrayList<Cell> cells, Posi posi) {
		//System.out.println("checked");
		for(int i = 0; i < cells.size(); i++)
		{
			this.checkAndFixWithinBounds(cells, i, posi);
		}
		return cells;
	}
	
	protected void checkAndFixWithinBounds(ArrayList<Cell> cells, int i, Posi posi) 
	{	
		Cell cll = cells.get(i);
		if(violatesBound(new double[]{cll.getPositionX(),cll.getPositionY()},SingletonHolder.getSize(),SingletonHolder.getBoundaryType(),SingletonHolder.getBoundarySize()))
		{
			//System.out.println("violation: "+cll.getPositionX()+" "+cll.getPositionY()+" "+i+" centre-"+SingletonHolder.getSize()+" bound "+SingletonHolder.getBoundarySize());
			placeWithinBound(i,SingletonHolder.getSize()/2, SingletonHolder.getBoundarySize(),cells, posi);
		}
	}
	
	private void placeWithinBound(int i, double centre, double boundSize, ArrayList<Cell> cells, Posi posi)
	{
		Random rand = SingletonHolder.getMasterRandom();
		double bottom = centre - boundSize;
		double top = centre+boundSize;
		double range = top-bottom;
		double x = 0;
		double y = 0;
		Cell c = cells.get(i);
		do
		{
			int rangeGiven3dp = (int) (range*1000);
			int randomPos = rand.nextInt(rangeGiven3dp);
			randomPos = randomPos/1000;
			x = bottom+randomPos;
			randomPos = rand.nextInt(rangeGiven3dp);
			randomPos = randomPos/1000;
			y = bottom+randomPos;
		}while(violatesBound(new double[]{x,y},SingletonHolder.getSize(),SingletonHolder.getBoundaryType(),SingletonHolder.getBoundarySize()));

		double x2 = c.getPositionX();
		double y2 = c.getPositionY();
		cells.get(i).setPositionX(x);
		cells.get(i).setPositionY(y);
		//System.out.println("placed: "+x+" "+y+" "+i);
		posi.updatePosition(x2, y2, cells.get(i), i);
		//System.out.println("cell is : "+cells.get(i).getPositionX()+" "+cells.get(i).getPositionY()+" "+i);
	}
	
	protected boolean violatesBound(double[] cords,double simSize, String type, int boundSize) 
	{
		double x = cords[0];
		double y = cords[1];
		double centre = simSize/2;
		boolean violates = false;
		if(type.equals("circular"))
		{
			if(StaticCalculations.getEuclidean(x, y, centre, centre)>boundSize)
			{
				violates = true;
			}
		}else
		{
			if(type.equals("square"))
			{
				if(x<centre-boundSize||x>centre+boundSize||y<centre-boundSize||y>centre+boundSize)
				{
					violates = true;
				}
			}
		}
		return violates;
	}
	
	public void detectBoundaryViolationAndFix(ArrayList<Cell> arrayList, Posi posi) 
	{
		for(int i = 0; i < arrayList.size(); i++)
		{
			Cell cll = arrayList.get(i);
			if(this.violatesBound(this.projectNext(cll, cll.getSpeed(),SingletonHolder.getSize()), SingletonHolder.getSize(), SingletonHolder.getBoundaryType(), SingletonHolder.getBoundarySize()))
			{
				cll.setVect(this.genBoundBounce(cll,SingletonHolder.getSize()), posi);
			}
		}
	
	}
	
	
	public Posi getPositionGrid() {
		return positionGrid;
	}

	public void setPositionGrid(Posi positionGrid) {
		this.positionGrid = positionGrid;
	}

	public ArrayList<Cell> detectAllOverlapAndFix(ArrayList<Cell> cells,ArrayList<java.awt.geom.CubicCurve2D.Double> curves, Posi posi, TrajectoryManager trajMan, BezManager bezMan, double size, List<Ellipse2D> circles) 
	{
		for(int i = 0; i < cells.size(); i++)
		{
			this.detectOverlapAndFix(i, cells, posi, curves, trajMan, bezMan, size, circles);
			//System.out.println("cellMan.getStore() size "+cellMan.getStore().getCells().length);
			//System.out.println("overlap complete "+i);
			/*if(i == cellMan.getStore().getCells().length )
			{
				System.exit(0);
			}*/
			
		}
		return cells;
		
	}
	
	
	
	
	

	public boolean isOverlappingVis(int i, int l, List<Ellipse2D> circles) 
	{
		Ellipse2D circ1 = circles.get(i);
		Ellipse2D circ2 = circles.get(l);
		//circ1 = new Ellipse2D.Double(0,0,10,5);
		//circ2 = new Ellipse2D.Double(9,0,10,5);
		boolean overlap = false;
		if(circ1.intersects(circ2.getBounds()))
		{
			overlap = true;
		}
		if(circ2.intersects(circ1.getBounds()))
		{
			overlap = true;
		}
		//System.out.println(overlap);
		//Cell circ3 = cellMan.getStore().getCells()[i];
		//Cell circ4 = cellMan.getStore().getCells()[l];
		//System.out.println(circ1.getCenterX()+" "+circ1.getCenterY()+" "+circ2.getCenterX()+" "+circ2.getCenterY()+" "+circ1.getHeight()+" "+circ1.getWidth()+" "+overlap+" "+i+" "+l);
		//System.out.println(circ3.getPositionX()+" "+circ3.getPositionY()+" "+circ4.getPositionX()+" "+circ4.getPositionY()+" "+circ3.getVect()+" "+circ4.getVect()+" "+overlap);
		//System.exit(0);
		return overlap;
	}

	
	
	
	

	public boolean isNear(Cell c) {
		GCell g = positionGrid.getGCell(c.getPositionX(), c.getPositionY());
		boolean near = false;
		int catchment = 1;
		int cX = (int) (c.getPositionX()/positionGrid.getCellSize());
		int cY = (int) (c.getPositionY()/positionGrid.getCellSize());
		for(int x = -catchment; x < cX+2;x++)
		{
			for(int y = -catchment; y < cY+2;y++)
			{
				if(cX+x > 0 && cX+x < positionGrid.getGrid().length && cY+y > 0 && cY+y < positionGrid.getGrid().length)
				{
					g = positionGrid.getGrid()[cX+x][cY+y];
				}
				if(g.getCellPositions().isEmpty() == false)
				{
					near = true;
				}
			}
		}
		
		return near;
	}

	
	


	

	
	
}
