package cycle_components;

import java.awt.Color;
import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;
import java.util.Random;

import cell_data_holders.Cell;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import environment_data_holders.CurveIndicator;
import environment_data_holders.GCell;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;

public class BezManager {


   private ArrayList<CubicCurve2D.Double> curves = new ArrayList<CubicCurve2D.Double>();
   private ArrayList<double[][]> curvePoints = new ArrayList<double[][]>(); 


   private int failRate = 100;
   
   private ArrayList<GCell> prevPoints = new ArrayList<GCell>();
   private ArrayList<ArrayList<double[]>> redStrands = new ArrayList<ArrayList<double[]>>();
   private ArrayList<ArrayList<double[]>> greenStrands = new ArrayList<ArrayList<double[]>>();

   private int attE = 0;
   private int attW = 0;
   private int travW = 0;
   private int travE = 0;
   
   
	public ArrayList<CubicCurve2D.Double> getCurves() {
		return curves;
	}
	public void setCurves(ArrayList<CubicCurve2D.Double> curves) {
		this.curves = curves;
	}
	public ArrayList<double[][]> getCurvePoints() {
		return curvePoints;
	}
	public void setCurvePoints(ArrayList<double[][]> curvePoints) {
		this.curvePoints = curvePoints;
	}
	
	public Cell checkAgainstCurves(Cell c, Posi posi, TrajectoryManager trajMan,  double size) {

		//bez handling before collision
		if(SingletonHolder.isBezFlag())
			{
			if(c.getOverlapLast() < SingletonHolder.getJumpCounter()-(c.getCellSize())/c.getSpeed()&& c.getBezLeaver() < SingletonHolder.getJumpCounter()-(c.getCellSize()+SingletonHolder.getBezSize())/c.getSpeed())
			{
				//check if already in curve
				if(c.isFollowingCurve() && SingletonHolder.isBezBounceFlag()==false)
				{
					GCell point = posi.getGCell(c.getPreviousPoint()[0],c.getPreviousPoint()[1]);
					CurveIndicator cur = point.getCurves();
					//if yes check if still in curve
					if(this.isCurveOverlap(c,curves.get(c.getCurve()), (int) (SingletonHolder.getBezSize()*c.getCellSize()), posi))
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
							setTravW(getTravW() + 1);
						}else
						{
							if(c.getCurveDirection()==1)
							{
								next = cur.getEastPoints().get(curvePoint);
								c.setLastAction("follow east");
								setTravE(getTravE() + 1);
							}
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
								double angleOfTheLine = Math.toDegrees(Math.atan2(c.getPositionY()-next[1], c.getPositionX()-next[0]));
								//should be to closest edge but it gets silly to calculate that every time, could be fixed by giving points the distance and direction to closest edge but not worth the time atm
								if(rand.nextBoolean())
								{
									angleOfTheLine+=90.00;
								}else
								{
									angleOfTheLine+=-90.00;
								}
								angleOfTheLine = StaticCalculations.counterClockwiseClean(angleOfTheLine);
								//angleOfTheLine = Math.toRadians(angleOfTheLine);
								c.setVect(angleOfTheLine, posi);
								c.setLastBounce((int) SingletonHolder.getJumpCounter());
								c.setColour(Color.red);
							}else
							{
								if(inTabooList(next, c.getPreviousPoints()))
								{
									///direct away from next point
									c.setLastAction("oot");
									//System.out.println("oot "+SingletonHolder.getBezChance());
									c.setBezLeaver(SingletonHolder.getJumpCounter());
									double angleOfTheLine = Math.toDegrees(Math.atan2(c.getPositionY()-next[1], c.getPositionX()-next[0]));
									if(rand.nextBoolean())
									{
										angleOfTheLine+=90.00;
									}else
									{
										angleOfTheLine+=-90.00;
									}
									//angleOfTheLine = StaticCalculations.counterClockwiseClean(angleOfTheLine);
									//angleOfTheLine = Math.toRadians(angleOfTheLine);
									c.setVect(angleOfTheLine, posi);
									c.setLastBounce((int) SingletonHolder.getJumpCounter());
									c.setColour(Color.red);
								}else
								{
									//direct to next point
									double direct = Math.atan2(next[1]-c.getPositionY(), next[0]-c.getPositionX());
									c.addPreviousPoint(next);
									direct = Math.toDegrees(direct);
									//direct = this.setForClock(direct);
									direct = StaticCalculations.counterClockwiseClean(direct);
									//direct = StaticCalculations.getOppositeAngle(direct);
									/*System.out.println("c "+c.getVect()+" "+c.getPositionX()+", "+c.getPositionY());
									System.out.println("tw "+direct+" "+next[0]+", "+next[1]);
									System.out.println("curves "+cur.getCurves().size()+" "+cur.getEastPoints().size()+", "+cur.getWestPoints().size());*/
									if(direct < 0)
									{
										System.out.println("here");
									}
									c.setVect(direct, posi);
									c.setColour(Color.CYAN);
								}
									
								
							}
							
							if(outMap(c, size))
							{
								///direct away from next point
								c.setLastAction("oot");
								System.out.println("oot "+point.getBezStrength());
								c.setBezLeaver(SingletonHolder.getJumpCounter());
								double angleOfTheLine = Math.toDegrees(Math.atan2(c.getPositionY()-next[1], c.getPositionX()-next[0]));
								if(rand.nextBoolean())
								{
									angleOfTheLine+=90.00;
								}else
								{
									angleOfTheLine+=-90.00;
								}
								angleOfTheLine = StaticCalculations.counterClockwiseClean(angleOfTheLine);
								//angleOfTheLine = Math.toRadians(angleOfTheLine);
								c.setVect(angleOfTheLine, posi);
								c.setLastBounce((int) SingletonHolder.getJumpCounter());
								c.setColour(Color.red);
							}
							//System.out.println("called");
							c.incrementFollowingBez();
							c.getPack().addTotalBezChosen(1.00);
						
						}else
						{
							//direct away from next point
							c.setLastAction("oot");
							//System.out.println("oot "+SingletonHolder.getBezChance());
							trajMan.genNextTraj(c,posi); 
							c.setBezLeaver(SingletonHolder.getJumpCounter());
							double angleOfTheLine = Math.toDegrees(Math.atan2(c.getPositionY()-next[1], c.getPositionX()-next[0]));
							if(rand.nextBoolean())
							{
								angleOfTheLine+=90.00;
							}else
							{
								angleOfTheLine+=-90.00;
							}
							angleOfTheLine = StaticCalculations.counterClockwiseClean(angleOfTheLine);
							//angleOfTheLine = Math.toRadians(angleOfTheLine);
							c.setVect(angleOfTheLine, posi);
							c.setLastBounce((int) SingletonHolder.getJumpCounter());
							c.setColour(Color.red);
						}
						//this.checkForCurvesAndAttatch(c);
						/*ArrayList<double[]> points = point.getNearbyPointHolder();
						GCell target = getNearestFromNearby(c,points);
						//attatches to the first curve at that point not closest needs >>>>>>>>>>fixing<<<<<<<<<<
						GCell tet = positionGrid.getGCell(target.getCurves().getEastPoints().get(0)[0], target.getCurves().getEastPoints().get(0)[1]);
						GCell twt = positionGrid.getGCell(target.getCurves().getWestPoints().get(0)[0], target.getCurves().getWestPoints().get(0)[1]);
						
						double[] tw = tet.getCurves().getWestPoints().get(0);
						double[] te = twt.getCurves().getEastPoints().get(0);
						double westAngDist = this.getEuclidean(c.getPositionX(),c.getPositionY(),tw[0],tw[1]);
						double eastAngDist = this.getEuclidean(c.getPositionX(),c.getPositionY(),te[0],te[1]);
						
						if(westAngDist > eastAngDist)
						{
							c.setCurveDirection(1);
							c.setCurve(target.getCurves().getCurves().get(0));
							c.setFollowingCurve(true);
							double angle = Math.atan2(c.getPositionY()-te[1], c.getPositionX()-te[0]);
							angle = Math.toDegrees(angle);
							angle = this.getOppositeAngle(angle);
							c.setVect(angle);
						}else
						{
							c.setCurveDirection(0);
							c.setCurve(target.getCurves().getCurves().get(0));
							c.setFollowingCurve(true);
							double angle = Math.atan2(c.getPositionY()-tw[1], c.getPositionX()-tw[0]);
							angle = Math.toDegrees(angle);
							angle = this.getOppositeAngle(angle);
							c.setVect(angle);
						}*/
						
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
			}else
			{
				c.setColour(Color.red);
			}
		}
		return c;
	}
	
	boolean isCurveOverlap(Cell c,java.awt.geom.CubicCurve2D.Double curve, int size, Posi posi) {
		boolean overlap = false;
		if(posi.getGCell(c.getPositionX(), c.getPositionY()).getNearbyPointHolder().size()>0)
		{
			overlap = true;
		}
		/*size = 2;
		Rectangle2D rec = new Rectangle2D.Double(c.getPositionX()-size/2,c.getPositionY()-size/2,size,size);
		if(curve.intersects(rec))
		{
			overlap = true;
			//System.out.println(""+c.getPositionX()+" "+c.getPositionY());
			//System.exit(0);
		}*/
		return overlap;
	}
	
	private GCell getNearestFromNearby(Cell c, ArrayList<double[]> points, Posi posi) {
		double nearest = 1000;
		int pointer = 0;
		
		for(int l = 0; l < points.size(); l++)
		{
			//System.out.println("point "+l);
			GCell poss = posi.getGCell(points.get(l)[0], points.get(l)[1]);
			//System.out.println("poss nearest size "+poss.getCurves().getNearestPoints().size());
			//System.out.println("points "+points.get(l)[0]+" "+points.get(l)[1]);
			int nearestHere = poss.getCurves().getNearestCurvePointAngular(c.getVect(),c.getPositionX(),c.getPositionY());
			ArrayList<double[]> pre = poss.getCurves().getNearestPoints();
			double[] curve = pre.get(nearestHere);
			double vect = Math.atan2(curve[1]-c.getPositionY(), curve[0]-c.getPositionX());
			vect = StaticCalculations.counterClockwiseClean(vect);
			//double dist = this.getAngularDistance(vect,c.getPositionX(), c.getPositionY(), curve[0], curve[1]);
			double dist = this.getAngularDistance(vect,c.getPositionX(), c.getPositionY(), points.get(l)[0], points.get(l)[1]);
			//System.out.println("test 0,0 0,1 90 "+this.getAngularDistance(90,0, 0, 0, 1));
			//System.out.println("test 0,0 -1,0 90 "+this.getAngularDistance(90,0, 0, -1, 0));
			//System.exit(0);
			if(dist < nearest)
			{
				nearest = dist;
				pointer = l;
			}
		}
		//System.out.println("pointer "+pointer);
		GCell point = posi.getGCell(points.get(pointer)[0],points.get(pointer)[1]);
		return point;
	}
	
	private double getAngularDistance(double vect, double d, double e, double points, double points2) 
	{
		double angle = Math.atan2(points2-e, points-d);
		angle = Math.toDegrees(angle);
		//angle = StaticCalculations.getOppositeAngle(angle);
		angle = StaticCalculations.counterClockwiseClean(angle);
		double difference = Math.sqrt(Math.pow(vect - angle , 2));
		double difference2 = 0.00;
		if(vect < angle)
		{
			difference2  = Math.sqrt(Math.pow(vect+360.00 - angle , 2));
			
		}else
		{

			difference2  = Math.sqrt(Math.pow(vect - angle+360.00 , 2));
		}
		if(difference > difference2)
		{
			difference = difference2;
		}
		//System.out.println("x "+d+" y "+e+" x2 "+points+" y2 "+points2+" angle "+angle+" vect "+vect+" difference "+difference);
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
	
	private boolean checkForCurvesAndAttatch(Cell c, Posi posi) 
	{
		GCell point = posi.getGCell(c.getPositionX(),c.getPositionY());
		boolean found = false;
		CurveIndicator cur = point.getCurves();
		ArrayList<double[]> poss = point.getNearbyPointHolder();
		ArrayList<Integer> poss2 = new ArrayList<Integer>();
		Random rand = SingletonHolder.getMasterRandom();
		if(poss.size()>0 && SingletonHolder.isBezBounceFlag() == false && rand.nextInt(100001) < point.getBezStrength())	
		{
			c.setColour(Color.BLUE);
			//System.out.println("follow");
			found = true;
			GCell target = getNearestFromNearby(c,poss, posi);
			//attatches to the first curve at that point not closest needs >>>>>>>>>>fixing<<<<<<<<<<
			int curv = rand.nextInt(target.getCurves().getCurves().size());
			double[] tw = target.getCurves().getWestPoints().get(curv);
			double[] te = target.getCurves().getEastPoints().get(curv);
			//System.out.println("clear");
			//needs to be modified for north
			//int p = target.getCurves().getNearestCurvePointAngular(c.getVect(),c.getPositionX(), c.getPositionY());
			//System.out.println("chosen point "+target.getCurves().getNearestPoints().get(p)[0]+" "+target.getCurves().getNearestPoints().get(p)[1]);
			double westAngDist = this.getAngularDistance(c.getVect(),c.getPositionX(),c.getPositionY(),tw[0],tw[1]);
			double eastAngDist = this.getAngularDistance(c.getVect(),c.getPositionX(),c.getPositionY(),te[0],te[1]);
			
			//System.out.println("west "+westAngDist+" east "+eastAngDist+" attE "+attE+" attW"+attW);
			if(westAngDist > eastAngDist)
			{
				setAttE(getAttE() + 1);
				c.setCurveDirection(1);
				c.setCurve(curv);
				c.setFollowingCurve(true);
				c.addPreviousPoint(new double[]{te[0],te[1]});
				double angle = Math.atan2(c.getPositionY()-te[1], c.getPositionX()-te[0]);
				//angle = Math.toDegrees(angle);
				//angle = this.setForClock(angle);
				angle = StaticCalculations.getOppositeAngle(angle);
				/*double angle2 = Math.atan2(1-0, 1-1);
				angle2 = Math.toDegrees(angle2);
				angle2 = this.setForClock(angle2);
				angle2 = this.getOppositeAngle(angle2);
				double angle3 = Math.atan2(1-1, 1-0);
				angle3 = Math.toDegrees(angle3);
				angle3 = this.setForClock(angle3);
				angle3 = this.getOppositeAngle(angle3);
				System.out.println("test 1,1 1,0 90 "+angle2);
				System.out.println("test 1,1 0,1 90 "+angle3);
				System.exit(0);
				System.out.println("c "+c.getVect()+" "+c.getPositionX()+", "+c.getPositionY());
				System.out.println("te "+angle+" "+te[0]+", "+te[1]);
				System.out.println("target "+target.getX()+", "+target.getY());
				System.exit(0);
				System.out.println("c "+c.getVect()+" "+c.getPositionX()+", "+c.getPositionY());
				System.out.println("tw "+angle+" "+te[0]+", "+te[1]);
				System.out.println("target "+target.getX()+", "+target.getY());
				c.setVect(angle);*/
				//c.setVect(angle);
				c.setLastAction("attatch east");
			}else
			{
				setAttW(getAttW() + 1);
				c.setCurveDirection(0);
				c.setCurve(curv);
				c.setFollowingCurve(true);
				c.addPreviousPoint(new double[]{tw[0],tw[1]});
				double angle = Math.atan2(c.getPositionY()-tw[1], c.getPositionX()-tw[0]);
				//angle = Math.toDegrees(angle);
				//angle = this.setForClock(angle);
				angle = StaticCalculations.getOppositeAngle(angle);
				/*System.out.println("c "+c.getVect()+" "+c.getPositionX()+", "+c.getPositionY());
				System.out.println("tw "+angle+" "+tw[0]+", "+tw[1]);
				System.out.println("target "+target.getX()+", "+target.getY());
				System.exit(0);*/
				//c.setVect(angle);
				c.setLastAction("attatch west");
			}
			//c.addPreviousPoint(new double[]{tw[0],tw[1]});
			//double angle = Math.atan2(c.getPositionY()-target.getY(), c.getPositionX()-target.getX());
			//angle = StaticCalculations.getOppositeAngle(angle);
			//c.setVect(angle);
			c.incrementFollowingBez();
			c.getPack().addTotalBezChosen(1.00);
		}else
		{
			if(poss.size() == 0)
			{

				c.setColour(Color.white);
			}
			c.setFollowingCurve(false);
			if(SingletonHolder.isBezBounceFlag() && poss.size()>0 )
			{
				if(rand.nextInt(100001) < point.getBezStrength())
				{
					if(c.getBounceSequence().size() !=5 ||c.getBounceSequentialDifference() != c.getBounceSequence().size()-1 || SingletonHolder.getJumpCounter()-c.getLastBounce() > SingletonHolder.getBezSize()/c.getSpeed()+2)
					{
						c.setColour(Color.red);
						//c.setVect(StaticCalculations.getOppositeAngle(c.getVect()));
						double angleOfTheLine = Math.toDegrees(Math.atan2(c.getPositionY()-poss.get(0)[1], c.getPositionX()-poss.get(0)[0]));
						//angleOfTheLine+=90;
						//angleOfTheLine = StaticCalculations.counterClockwiseClean(angleOfTheLine);
						//angleOfTheLine = Math.toRadians(angleOfTheLine);
						c.setVect(angleOfTheLine, posi);
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
					c.setLastBounce((int) SingletonHolder.getJumpCounter());
				}
			}
		}
		
		return found;
	}
	
	
	
	
	public void bezChange(ArrayList<Cell> cells, Posi posi) 
	{
		for(int i = 0 ; i < cells.size(); i ++)
		{
			cells.get(i).resetCurves();
		}
		double[][] startsEnds = new double[SingletonHolder.getBezNum()][4];
		System.out.println("Creating curves "+SingletonHolder.getBezNum()+" "+SingletonHolder.getBezChance());
		posi.resetBez();
		if(SingletonHolder.getBezType().equals("random"))
		{

			for(int i=0 ; i < startsEnds.length;i++)
			{
				startsEnds[i] = this.genRandomEdges();
			}
		}else
		{
			startsEnds = this.genUniformEdges(startsEnds.length,SingletonHolder.getSize());

		}
		double[][] mids = this.createMids(startsEnds,SingletonHolder.getBezMin(),SingletonHolder.getBezMax(), SingletonHolder.getBezVarience());
		
		curves = new ArrayList<CubicCurve2D.Double>();
		for(int i = 0; i < startsEnds.length; i++)
		{
			double[] p = startsEnds[i];
			double[] m = mids[i];
			CubicCurve2D.Double curve = new CubicCurve2D.Double();
			//System.out.println(" "+m[0]+" "+m[1]+" "+m[2]+" "+m[3]);
			curve.setCurve(p[0],p[1],m[0],m[1],m[2],m[3],p[2],p[3]);
			curves.add(curve);
		}
		curvePoints = new ArrayList<double[][]>();
		for(int i = 0 ; i < curves.size();i++)
		{
			double distance = 0; 
			int tries = 0;
			int tryLimit = 10;
			double num = 1500.00;
			double varience = 1.00;
			double[][] points;
			do
			{
				//System.out.println("dist "+distance+" tried "+tries+" var "+varience);
				points = this.genPoints(curves.get(i), num*varience);
				tries++;
				varience = varience-0.05;
				//System.out.println(points.length);
				if(points.length > 1)
				{
					distance = StaticCalculations.getEuclidean(points[0][0], points[0][1], points[1][0], points[1][1]);
				}
			}while(distance < 9 && tries < tryLimit );
			//System.out.println("commit: dist "+distance+" tried "+tries+" var "+varience);
			curvePoints.add(points);
		}
		for(int i = 0 ; i < curvePoints.size();i++)
		{
			posi.setCurve(curvePoints.get(i), i, SingletonHolder.getSize(), SingletonHolder.getBezSize(),SingletonHolder.getBezChance() , SingletonHolder.isBezDist(), SingletonHolder.getBezSVar());
		}
		/*CubicCurve2D.Double curve = new CubicCurve2D.Double();
		curve.setCurve(0,0,SingletonHolder.getSize()/4,SingletonHolder.getSize()/4,SingletonHolder.getSize()/4*3,SingletonHolder.getSize()/4*3,SingletonHolder.getSize(),SingletonHolder.getSize());
		curves.add(curve);*/
		SingletonHolder.setBezGRChanges(true);
	}
	
	private double[] genRandomEdges()
	{
		
		double[] edges = new double[4];
		double[] ed = this.genEdge();
		edges[0] = ed[0];
		edges[1] = ed[1];
		ed = this.genEdge();
		edges[2] = ed[0];
		edges[3] = ed[1];
		return edges;
	}

	private double[] genEdge() 
	{
		Random rand = SingletonHolder.getMasterRandom();
		int side = rand.nextInt(4);
		int point = rand.nextInt(SingletonHolder.getSize()+1);
		double[] ed = new double[2];
		if(side == 0)
		{
			ed[0] = 0;
			ed[1] = point;
		}else
		{
			if(side ==1)
			{
				ed[0] = point;
				ed[1] = 0;
			}else
			{
				if(side == 2)
				{
					ed[0] = SingletonHolder.getSize();
					ed[1] = point;
				}else
				{
					if(side == 3)
					{
						ed[1] = SingletonHolder.getSize();
						ed[0] = point;
					}
				}
			}
		}
		return ed;
	}
	
	private double[][] genUniformEdges(int length, int side) 
	{
		double[][] edges = new double[length][4];
		Random rand = SingletonHolder.getMasterRandom();
		double interval = (side*4)/(length*2);
		ArrayList<Double> points = new ArrayList<Double>();
		double counter = 0.00;
		for(int i = 0; i < (length*2); i++)
		{
			points.add(counter);
			counter = counter+interval;
		}
		boolean opposite = true;
		if(opposite == false)
		{
			for(int i = 0 ; i < length; i++)
			{
				double pointA = points.remove(rand.nextInt(points.size()));
				double pointB = points.remove(rand.nextInt(points.size()));
				double[] ed = this.transformDoubleToCords(pointA, side); 
				edges[i][0] = ed[0];
				edges[i][1] = ed[1];
				ed = this.transformDoubleToCords(pointB, side);
				edges[i][2] = ed[0];
				edges[i][3] = ed[1];
			}
		}else
		{
			//x = x+points/4+points/4-x
			int slide = points.size()/4;
			for(int i = 0 ; i < length; i++)
			{
				double pointA = points.get(i);
				double pointB = points.get(i+length);
				double[] ed = this.transformDoubleToCords(pointA, side); 
				edges[i][0] = ed[0];
				edges[i][1] = ed[1];
				ed = this.transformDoubleToCords(pointB, side);
				edges[i][2] = ed[0];
				edges[i][3] = ed[1];
			
			}
		}
		
		return edges;
	}

	private double[] transformDoubleToCords(double point, int side) 
	{
		double[] ed =  new double[2];
		if(point <= side)
		{
			ed[0] = point;
			ed[1] = 0;
		}else
		{
			if(point <= side*2)
			{
				ed[0] = side;
				ed[1] = point - side;
			}else
			{
				if(point <= side*3)
				{
					ed[0] = side - (point - (side*2));
					ed[1] = side;
				}else
				{
					if(point <= side*4)
					{
						ed[0] = 0;
						ed[1] = side - (point - (side*3));
					}
				}
			}
		}
		return ed;
	}
	
	private double[][] createMids(double[][] startsEnds, int min, int max, int var) 
	{
		double[][] mids = new double[startsEnds.length][4];
		int range = max-min+1;
		Random rand = SingletonHolder.getMasterRandom();
		for(int i = 0; i < mids.length; i++)
		{
			if(min == 0&& max ==0)
			{
				mids[i] = new double[]{startsEnds[i][0],startsEnds[i][1],startsEnds[i][2],startsEnds[i][3]};
			}else
			{
				int rand1 = rand.nextInt(range)+min;
				int rand2 = rand.nextInt(range)+min;
				double perc1 = rand1/100000.00;
				double perc2 = rand2/100000.00;
				double per1 = 1.00/perc1;
				double per2 = 1.00/perc2;
				//System.out.println(rand1+" | "+rand2+" | "+perc1+" | "+perc2+" | "+per1+" | "+per2+" | "+min+" | "+max);
				double xOver = startsEnds[i][0]+startsEnds[i][2];
				double yOver = startsEnds[i][1]+startsEnds[i][3];
				mids[i] = new double[]{xOver/per1,yOver/per1,xOver/per2,yOver/per2};
				mids[i][0] = mids[i][0] +(rand.nextInt(var*2)-var);
				mids[i][1] = mids[i][1] +(rand.nextInt(var*2)-var);
				mids[i][2] = mids[i][2] +(rand.nextInt(var*2)-var);
				mids[i][3] = mids[i][3] +(rand.nextInt(var*2)-var);
			}
		}
		return mids;
	}
	
	private double[][] genPoints(java.awt.geom.CubicCurve2D.Double curve, double num) 
	{
		double[][] points =new double[(int) num][2];
		double increment = SingletonHolder.getSize()/num;
		increment = increment/num;
		double t = 0.00;
		for (int i = 0; i < num-1;i++) 
		{
	        double xValue = Math.pow((1-t), 3) * curve.getX1() + 3 * Math.pow((1-t), 2) * t * curve.getCtrlX1() + 3 * (1-t) * Math.pow(t, 2) * curve.getCtrlX2() + Math.pow(t, 3) * curve.getX2();
	        double yValue = Math.pow((1-t), 3) * curve.getY1() + 3 * Math.pow((1-t), 2) * t * curve.getCtrlY1() + 3 * (1-t) * Math.pow(t, 2) * curve.getCtrlY2() + Math.pow(t, 3) * curve.getY2();
	        points[i][0] = xValue;
	        points[i][1] = yValue;
	        t= t + increment;
		}
		points = this.trimCurveForBounds(points,SingletonHolder.getSize());
		return points;
	}
	
	private double[][] trimCurveForBounds(double[][] points, int size) {
		ArrayList<double[]> clean = new ArrayList<double[]>();
		for(int i = 0; i < points.length; i++)
		{
			if(this.inBoundswithEasing(points[i],size, 0))
			{
				clean.add(points[i]);
			}
		}
		double[][] points2 = new double[clean.size()][2];
		for(int i = 0; i < points2.length; i++)
		{ 
			points2[i] = clean.get(i);
		}
		return points2;
	}
	
	private boolean inBoundswithEasing(double[] ds, int size, int i) {
		boolean in  = false;
		if(ds[0] >= 0-i && ds[0] <= size+i && ds[1] >= 0-i && ds[1] <= size+i )
		{
			in = true;
		}
		return in;
	}
	
	public void bezReproduce(ArrayList<java.awt.geom.CubicCurve2D.Double> oldCurves,ArrayList<double[][]> oldPoints, ArrayList<Cell> cells, Posi posi, ArrayList<GCell> allPoints) 
	{
		for(int i = 0 ; i < cells.size(); i ++)
		{
			cells.get(i).resetCurves();
		}
		System.out.println("Creating curves "+SingletonHolder.getBezNum());
		posi.resetBez();
		
		curves = oldCurves;
		curvePoints = oldPoints;
		
		posi.setOldCurve(allPoints);
		
		/*CubicCurve2D.Double curve = new CubicCurve2D.Double();
		curve.setCurve(0,0,SingletonHolder.getSize()/4,SingletonHolder.getSize()/4,SingletonHolder.getSize()/4*3,SingletonHolder.getSize()/4*3,SingletonHolder.getSize(),SingletonHolder.getSize());
		curves.add(curve);*/
				
	}
	public void populateGR(int red, int green, Posi posi,int rad) {
		ArrayList<GCell> allPoints = new ArrayList<GCell>();
		System.out.println("populating ");
		//needs to look at all point g cells and their strength 
		//create starands based on hyp trig and give back the unsorted starands
		//[curve][strand][x,y,str]
		ArrayList<ArrayList<ArrayList<double[]>>> allList = posi.getCurveCellStrands(curvePoints, rad);
		//this.strandifyEastWest(posi, allList);
		//go along strands and collate adjacent points into lengths, singles included
		//[green/red][strands]
		ArrayList<ArrayList<ArrayList<double[]>>> strands = new ArrayList<ArrayList<ArrayList<double[]>>>();
		strands = this.strandConnect(allList,green, red);
		setRedStrands(strands.get(1));
		setGreenStrands(strands.get(0));
		
		
	}
	private void strandifyEastWest(Posi posi, ArrayList<ArrayList<ArrayList<double[]>>> all) {
		//[curve][strand][x,y,str]
		for(int c = 0; c < all.size();c++)
		{
			for(int s = 0; s < all.get(c).size();s++)
			{
				for(int l = 1; l < all.get(c).get(s).size()-1;l++)
				{
					ArrayList<ArrayList<double[]>> al = all.get(c);
					ArrayList<double[]> ll = al.get(s);
					double[] posC = ll.get(l);
					double[] posE = ll.get(l-1);
					double[] posW = ll.get(l+1);
					CurveIndicator cur = posi.getGCell(posC[0], posC[1]).getCurves();
					if(cur.getCurves().size()<2)
					{
						//cur.setWestPoints(new ArrayList<double[]>());
						//cur.setEastPoints(new ArrayList<double[]>());
					}
						//cur.getEastPoints().add(new double[] {posW[0],posW[1]});
						//cur.getWestPoints().add(new double[] {posE[0],posE[1]});
				}
			}
		}
		
	}
	private ArrayList<ArrayList<ArrayList<double[]>>> strandConnect(ArrayList<ArrayList<ArrayList<double[]>>> list, int green, int red) {
		ArrayList<ArrayList<double[]>> greenStrings = new ArrayList<ArrayList<double[]>>();
		ArrayList<ArrayList<double[]>> redStrings = new ArrayList<ArrayList<double[]>>();
		int cutOffDist = 7;
		//for each curve
		for(int i = 0 ; i < list.size(); i++)
		{
			//for each strand
			for(int l = 0; l < list.get(i).size();l++)
			{
				ArrayList<double[]> strand = list.get(i).get(l);
				ArrayList<double[]> string = new ArrayList<double[]>();
				if(strand.size() > 0)
				{
					int lastType = 0;
					for(int m = 0; m < strand.size(); m++)
					{
						int type = 0;
						double[] str = strand.get(m);
						if(str[2] > green)
						{
							type = 1;
						}else
						{
							if(str[2] < red)
							{
								type = 2;
							}
						}
						if(str[0] > SingletonHolder.getSize() || str[0]< 0 || str[1] > SingletonHolder.getSize() | str[1] < 0)
						{
							type = 0;
						}
						if(string.size() > 0 && type == lastType && StaticCalculations.getEuclidean(str[0], str[1], string.get(string.size()-1)[0], string.get(string.size()-1)[1]) < cutOffDist)
						{
							if(type == 0)
							{
								//ignore
								/*//push the current string
								if(string.size()>0 && string.get(0)[2] > green)
								{
									greenStrings.add(string);
									string = new ArrayList<double[]>();
								}else
								{
	
									redStrings.add(string);
									string = new ArrayList<double[]>();
								}*/
							}else
							{
								//add to current string
								string.add(strand.get(m));
							}
						}else
						{
							if(type == 0)
							{
								//push the current string
								if(string.size() > 0)
								{
									if(string.get(0)[2] > green)
									{
										greenStrings.add(string);
										string = new ArrayList<double[]>();
									}else
									{
		
										redStrings.add(string);
										string = new ArrayList<double[]>();
									}
								}
							}else
							{
	
								//push the current string
								if(string.size() > 0)
								{
									if(string.get(0)[2] > green)
									{
										greenStrings.add(string);
										string = new ArrayList<double[]>();
									}else
									{
		
										redStrings.add(string);
										string = new ArrayList<double[]>();
									}
								}
								//add to new string
								string.add(strand.get(m));
							}
						}
						lastType = type;
					}
					if(string.size() > 0)
					{
						if(string.get(0)[2] > green)
						{
							greenStrings.add(string);
							string = new ArrayList<double[]>();
						}else
						{

							redStrings.add(string);
							string = new ArrayList<double[]>();
						}
					}
					//while failed = false remove succes and add them to string
					//when failed if string length is > 0 returner add string
					//if next is other start other string otherwise jump
				}
			}
			
		}
		ArrayList<ArrayList<ArrayList<double[]>>> returner = new ArrayList<ArrayList<ArrayList<double[]>>>();
		returner.add(greenStrings);
		returner.add(redStrings);
		return returner;
	}
	private ArrayList<double[]> filterAdjacentByDistance(ArrayList<double[]> list) {
		System.out.println("list size in "+list.size());
		int limit = 7;
		double maxDist = 6.00;
		failRate = 0;
		for(int i = 0; i < list.size()-(limit+1);i++)
		{
			/*
			0 
			1 fails
			1
			0 and 1 < 6
			2
			0 and 2, 1 and 2 < 6
			3
			0 and 3, 1 and 3, 2 and 3 <6
			4
			0 and 4, 1 and 4, 2 and 4, 3 and 4 < 6
			*/
			ArrayList<double[]> l = new ArrayList<double[]>();
			for(int n = 0; n <= limit;n++)
			{
				l.add(list.get(i+n));
			}
			int fail = 0;
			int cyc = limit;
			do
			{
				boolean failed = false;
				int m = cyc-1;
				do
				{
				
					if(StaticCalculations.getEuclidean(l.get(cyc)[0], l.get(cyc)[1], l.get(m)[0], l.get(m)[1]) > maxDist)
					{
						failed = true;
						fail++;
						failRate++;
					}
					m--;
				
				}while(failed == false && m >=0);
				cyc --;
			}while( cyc > 0);
			//(x1+x2)/2,(y1+y2)/2
			//System.out.println("limit "+limit+" fail "+fail);
			double[] a2 = list.get(i);
			double[] b2 = list.get(i+limit-fail);	
			
			double size = StaticCalculations.getEuclidean(a2[0],a2[1],b2[0],b2[1]);
			System.out.println("size "+size+" "+a2[0]+" "+a2[1]+" "+b2[0]+" "+b2[1]+" ");
			double[] rep = new double[]{(a2[0]+b2[0])/2,(a2[1]+b2[1])/2,size};
			list.set(i, rep);
			int count = limit -fail;
			do
			{
				list.remove(i+limit-fail);
				count --;
			}while(count > 0);
		}
		for(int i = 0; i < list.size();i++)
		{
			double[] a = list.get(i);
			if(a.length == 2)
			{
				double[] b = new double[]{a[0],a[1],1};
				list.set(i, b);
				
			}
		}
		System.out.println("list size out "+list.size());
		return list;
		
	}
	public void setPreviousPoints(Posi posi, int rad) {
		setPrevPoints(new ArrayList<GCell>());
		//System.out.println("bez c a");
		for(int i = 0 ; i < curvePoints.size();i++)
		{
			getPrevPoints().addAll(posi.getCurveCellStrengths(curvePoints.get(i), rad));
			//System.out.println("bez c a a");
		}
		//System.out.println("bez c b");
		
	}
	public ArrayList<GCell> getPrevPoints() {
		return prevPoints;
	}
	public void setPrevPoints(ArrayList<GCell> prevPoints) {
		this.prevPoints = prevPoints;
	}
	public ArrayList<ArrayList<double[]>> getRedStrands() {
		return redStrands;
	}
	public void setRedStrands(ArrayList<ArrayList<double[]>> redStrands) {
		this.redStrands = redStrands;
	}
	public ArrayList<ArrayList<double[]>> getGreenStrands() {
		return greenStrands;
	}
	public void setGreenStrands(ArrayList<ArrayList<double[]>> greenStrands) {
		this.greenStrands = greenStrands;
	}
	public int getAttE() {
		return attE;
	}
	public void setAttE(int attE) {
		this.attE = attE;
	}
	public int getAttW() {
		return attW;
	}
	public void setAttW(int attW) {
		this.attW = attW;
	}
	public int getTravW() {
		return travW;
	}
	public void setTravW(int travW) {
		this.travW = travW;
	}
	public int getTravE() {
		return travE;
	}
	public void setTravE(int travE) {
		this.travE = travE;
	}
}
