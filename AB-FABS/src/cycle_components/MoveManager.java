package cycle_components;

import java.awt.geom.CubicCurve2D.Double;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cell_data_holders.Cell;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import environment_data_holders.GCell;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;

public class MoveManager {


	protected Posi positionGrid = null;
	
	
	
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
		double[] newPos = new double[]{x,y,c.getSpeed(),0,0,0};
		cells.get(i).getBrownianList().set(0, newPos);
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
				//System.out.println("violated bound");
				cll.setVect(this.genBoundBounce(cll,SingletonHolder.getSize()), posi);
			}
		}
	
	}
	
	protected double genBoundBounce(Cell cell,double simSize) 
	{
		double centre = simSize/2;
		double x = cell.getPositionX();
		double y = cell.getPositionY();
		double delta_x = x - centre;
		double delta_y = y - centre;
		double theta_radians = Math.atan2(delta_y, delta_x);
		
		theta_radians = Math.toDegrees(theta_radians);
		theta_radians = StaticCalculations.getOppositeAngle(theta_radians);
		theta_radians = StaticCalculations.counterClockwiseClean(theta_radians);
		
		SingletonStatStore.pollBounceTurn(cell);
		return theta_radians;
	}
	
	
	
	
	
	//when doing jump varience change all jump size/increment algorithms
	public double[] projectNext(Cell c, double jump, double dimensions) 
	{
		if(c.getTrajType().equals("brownian"))
		{
			Random randomiser = SingletonHolder.getMasterRandom();
			double poi = randomiser.nextGaussian()*jump;
			poi = Math.pow(poi, 2);
			poi = Math.sqrt(poi);
			jump = poi;
		}
		c.setProjectedJump(jump);
		double[] next = new double[2];
		double x = c.getPositionX();
		double y = c.getPositionY();
		double vect = Math.toRadians(c.getVect());
		x = x + Math.cos(vect)*jump;
		y = y + Math.sin(vect)*jump;
		if(x < 0)
		{
			x = dimensions-1+x;
		}
		if(y < 0)
		{
			y = dimensions-1+y;
		}
		if(x > dimensions)
		{
			x = 1+x-dimensions;
		}
		if(y > dimensions)
		{
			y = 1+y-dimensions;
		}
		next[0] = x;
		next[1] = y;
		
		return next;
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
				jump = c.getIncSpeed();
				/*jump = c.getProjectedJump();
				if(jump == 0.00)
				{
					Random randomiser = SingletonHolder.getMasterRandom();
					double poi = randomiser.nextGaussian()*c.getSpeed();
					//poi = Math.pow(poi, 2);
					//poi = poi*2;
					//poi = Math.sqrt(poi);
					//System.out.println("poi "+poi);
					jump = poi;
					
				}*/
			}
			double vect = Math.toRadians(c.getVect());
			x = x + Math.cos(vect)*jump;
			y = y + Math.sin(vect)*jump;
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
			//System.out.println("jump "+jump);
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
			//jump = Math.sqrt(Math.pow(jump, 2));
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

	public Posi getPositionGrid() {
		return positionGrid;
	}

	public void setPositionGrid(Posi positionGrid) {
		this.positionGrid = positionGrid;
	}

	public ArrayList<Cell> detectAllOverlapAndFix(ArrayList<Cell> cells,ArrayList<java.awt.geom.CubicCurve2D.Double> curves, Posi posi, TrajectoryManager trajMan, BezManager bezMan, double size, List<Ellipse2D> circles) 
	{
		//System.out.println("Move Manager cellMan.getStore() size "+cells.size());
		for(int i = 0; i < cells.size(); i++)
		{
			this.detectOverlapAndFix(i, cells, posi, curves, trajMan, bezMan, size, circles);
			//System.out.println("overlap complete "+i);
			/*if(i == cellMan.getStore().getCells().length )
			{
				System.exit(0);
			}*/
			
		}
		return cells;
		
	}
	
	protected void detectOverlapAndFix( int i, ArrayList<Cell> cells, Posi posi, ArrayList<Double> curves, TrajectoryManager trajMan, BezManager bezMan, double size, List<Ellipse2D> circles) 
	{
		//System.out.println("overlapp 1");
		Cell c = cells.get(i);
		//System.out.println("overlap vect "+c.getVect());
		if(c.isCorporeal())
		{
			c = bezMan.checkAgainstCurves(c, posi, trajMan, size);
			
			
			//positionGrid.fullReport(cellMan.getStore().getCells());
			//System.exit(0);
			//cells2[i] = c;
			//cellMan.getStore().setCells(cells2);
	
			ArrayList<Integer> possibles = posi.getPossibleOverlap(c,i,cells);
			//System.out.println("overlap vect 1 "+c.getVect());
			for(int l = 0; l < possibles.size();l++)
			{
				//System.out.println("overlapp 2 "+possibles.get(l)+" ");
				Cell c2 = cells.get(possibles.get(l));
				if(isOverlappingEuc(c,c2)){
					//if(isOverlappingVis(i,possibles.get(l), circles))
					//{
						//System.out.println("overlap 3");
						//>>>>>>>>>>>>>>>>>>>>>>>PROBLEM HERE<<<<<<<<<<<<<<<<<<<<<<
						cells.get(possibles.get(l)).setOverlapLast((int) SingletonHolder.getJumpCounter());
						c.setOverlapLast((int) SingletonHolder.getJumpCounter());
						c.incrementCollisions();
						c.getPack().addTotalCollisions(1.00);
						//System.out.println(c.getVect()+" "+c2.getVect()+" "+SingletonHolder.getJumpCounter());
						//this.collisionChangeReverse(i,possibles.get(l), cells);
						this.collisionChangeBounce(i,possibles.get(l), cells, posi);
						//System.out.println(c.getVect()+" "+c2.getVect()+" "+SingletonHolder.getJumpCounter());
						c.setFollowingCurve(false);
						//this.collisionSlice(i,possibles.get(l), cells);
						//System.out.println(c.getVect()+" "+c2.getVect()+" "+SingletonHolder.getJumpCounter());
					//}
				}
			}
			if(SingletonHolder.isAttractZoneFlag() || SingletonHolder.isDeflectZoneFlag())
			{
				GCell g = posi.getGCell(c.getPositionX(), c.getPositionY());
				double catcher = SingletonHolder.getAttractEye();
				if(g.getAttractionStrength()!=0 && StaticCalculations.getEuclidean(c.getPositionX(), c.getPositionY(), g.getAttractX(), g.getAttractY())>catcher)
				{
					this.turnCellTowards(c,g,posi);
				}
			}
		}

		//System.out.println("overlap vect 2 "+c.getVect());
	}

	
	
	

	private void turnCellTowards(Cell c, GCell g, Posi posi) {

		//System.out.println("here "+c.getVect());
		double x1 = g.getAttractX();
		double x2 = c.getPositionX();
		double y1 = g.getAttractY();
		double y2 = c.getPositionY();
		if(g.getAttractionStrength() < 0)
		{
			x1 = c.getPositionX();
			x2 = g.getAttractX();
			y1 = c.getPositionY();
			y2 = g.getAttractY();
		}
		double angle = Math.atan2(y1-y2, x1-x2);
		//double angle = Math.atan2(c.getPositionY()-g.getAttractY(), c.getPositionX()-g.getAttractX());
		angle = Math.toDegrees(angle);
		//angle = StaticCalculations.getOppositeAngle(angle);
		angle = StaticCalculations.counterClockwiseClean(angle);
		/*double difference = Math.sqrt(Math.pow(c.getVect() - angle , 2));
		double difference2 = 0.00;
		boolean clockwise = false;
		if(c.getVect() < angle)
		{
			difference2  = Math.sqrt(Math.pow(c.getVect()+360.00 - angle , 2));
			clockwise = true;
		}else
		{

			difference2  = Math.sqrt(Math.pow(c.getVect() - angle+360.00 , 2));
		}
		if(difference > difference2)
		{
			difference = difference2;
		}
		double close = (difference/100)*Math.sqrt(Math.pow(g.getAttractionStrength(),2));
		c.setVect(StaticCalculations.counterClockwiseClean(c.getVect()+close));*/
		
		
		double vect = c.getVect();
		double target = angle;
		boolean larger = false;
		if(target>vect)
		{
			larger = true;
		}
		//diff 1 is larger - the smaller
		double diff1 = 0;
		if(larger)
		{
			diff1 = target - vect;
		}else
		{
			diff1 = vect - target;
		}
		//diff 2 is 360-larger +smaller
		double diff2 = 0;
		if(larger)
		{
			diff2 = 360.00-target+vect;
		}else
		{
			diff2 = 360-vect+target;
		}
		boolean diff = true;
		double close = 0;
		if(diff2 < diff1)
		{
			diff = false;
			close = (diff2/100)*Math.sqrt(Math.pow(g.getAttractionStrength(),2));
		}else
		{
			close = (diff1/100)*Math.sqrt(Math.pow(g.getAttractionStrength(),2));
		}
		//if diff 1 smaller
		if(diff)
		{
			if(larger)
			{
				//if ang is larger is ang - adjust
				c.setVect(StaticCalculations.counterClockwiseClean(c.getVect()+close), posi);
			}else
			{
				//if and is smaller its ang + adjust
				c.setVect(StaticCalculations.counterClockwiseClean(c.getVect()-close), posi);
			}
			
		}else
		{
			if(larger)
			{
				// if small its and - then clean
				c.setVect(StaticCalculations.counterClockwiseClean(c.getVect()-close), posi);
			}else
			{
				//if larger is + then clean
				c.setVect(StaticCalculations.counterClockwiseClean(c.getVect()+close), posi);
			}
		}
			
		
		
		
		//System.out.println(""+g.getAttractionStrength()+" ,"+close+" , "+c.getVect()+" ,"+angle+" ,"+difference);
		//double angA = StaticCalculations.getAngularDistance(c.getVect()-close, c.getPositionX(),c.getPositionY(),g.getAttractX(),g.getAttractY());
		//double angB = StaticCalculations.getAngularDistance(c.getVect()+close, c.getPositionX(),c.getPositionY(),g.getAttractX(),g.getAttractY());
		/*if(angA < angB)
		{
			c.setVect(c.getVect()-close);
		}else
		{
			c.setVect(c.getVect()+close);
		}
		if(clockwise)
		{
			c.setVect(StaticCalculations.counterClockwiseClean(c.getVect()-close));
		}else
		{
			c.setVect(StaticCalculations.counterClockwiseClean(c.getVect()+close));
		}*/
		//System.out.println("new "+c.getVect());
	}

	public boolean isOverlappingVis(int i, int l, List<Ellipse2D> circles) 
	{
		Ellipse2D circ1 = circles.get(i);
		Ellipse2D circ2 = circles.get(l);
		/*int m = 10;
		Ellipse2D cir1 = new Ellipse2D.Double(circ1.getX(),circ1.getY(),m,m); 
		Ellipse2D cir2 = new Ellipse2D.Double(circ2.getX(),circ2.getY(),m,m); 
		boolean overlap = false;
		if(cir1.intersects(cir2.getBounds()))
		{
			overlap = true;
		}
		if(cir2.intersects(cir1.getBounds()))
		{
			overlap = true;
		}*/
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

	private void collisionChangeReverse(int i, Integer l, ArrayList<Cell> cells, Posi posi) {
		Cell cell = cells.get(i);
		Cell cell2 = cells.get(l);
		double a = StaticCalculations.getOppositeAngle(cell.getVect());
		double b = StaticCalculations.getOppositeAngle(cell2.getVect());
		if(i == 0)
		{
			//System.out.println(a+" "+b+" "+cell.getVect()+" "+cell2.getVect()+" "+i+" "+l);
		}
		//System.exit(0);
		cell.setVect(a, posi);
		cell2.setVect(b, posi);
		if(i == 0)
		{
			//System.out.println(a+" "+b+" "+cellMan.getStore().getCells()[i].getVect()+" "+cellMan.getStore().getCells()[l].getVect());
		}
		//cell.setVect(180);
	}

	//should probably write a proper collision algorithm that takes into account mass speed shape and bounciness/adhesion modifiers at some point but this should just create slicing collisions, could add a variable for spinning off as well
	private void collisionSlice(int i, Integer l, ArrayList<Cell> cells, Posi posi)
	{
		Cell cell = cells.get(i);
		Cell cell2 = cells.get(l);
		double vect1 = cell.getVect();
		double vect2 = cell2.getVect();
		double weight1 = cell.getCellSize();
		double weight2 = cell2.getCellSize();
		double speed1 = cell.getSpeed();
		double speed2 = cell2.getSpeed();
		//get the smallest distance between vectors and momentum
		double clock = 0.00;
		double cClock = 0.00;
		double diff = 0.00;
		//if one score is over twice the other  it deafults to their value, greater one range in relation to the other the closer it needs to be to them, get the range between the two normalise to 0 on the smallest then the line is at the largest - smallest  correct to largest if final value is bigger
		double momentum1 = speed1*weight1;
		double momentum2 = speed2*weight2;
		//the result needs to be closer to chosen than not the % is % closer on the half of the heaviest
		int bigger = 0;
		double small = 0.00;
		double large = 0.00;

		//set east and west vector from vector line based on size if weight + speed = 0 impact this should be their current vector so range is + - size vector so the outer is always on the outer line
		if(momentum1 < momentum2)
		{
			bigger = 1;
			small = momentum1;
			large = momentum2;
		}else
		{
			bigger = 0;
			small = momentum2;
			large = momentum1;
		}
		//the *2 dictates the max momentum needed point for Bounce off, some action could be added to bounce off
		double upper = small*2;
		double cent = large /upper;
		double fVect = 0.00;

		//establish vector line based on weight and speed 
		if(vect1 > vect2)
		{

			clock += vect1 - vect2;
			cClock = 360- vect1 +vect2;
			//diff = Math.sqrt(Math.pow(clock, 2))
			double puntToLarge = this.getDistToAddPoint(clock,cClock,cent);
			//if a-b angle and a is heaviest smallest angle between them halved - % of half 1-b
			if(clock < cClock)
			{
				if(bigger == 0)
				{
					fVect = vect2 + (clock/2)+puntToLarge;
				}else
				{
					fVect = vect2 + (clock/2)-puntToLarge;
				}
			}else
			{
				if(bigger == 1)
				{
					fVect = vect1 + (cClock/2)+puntToLarge;
				}else
				{
					fVect = vect1 + (clock/2)-puntToLarge;
				}
			}
			
		}else
		{
			clock += vect2 - vect1;
			cClock = 360-vect2+vect1;
			double puntToLarge = this.getDistToAddPoint(clock,cClock,cent);
			if(clock < cClock)
			{
				if(bigger == 0)
				{
					fVect = vect1 + (clock/2)-puntToLarge;
				}else
				{
					fVect = vect1 + (clock/2)+puntToLarge;
				}
			}else
			{
				if(bigger == 1)
				{
					fVect = vect2 + (cClock/2)-puntToLarge;
				}else
				{
					fVect = vect2 + (clock/2)+puntToLarge;
				}
			}
		}
		fVect = StaticCalculations.counterClockwiseClean(fVect);
		//figure knockout amount based on momentum comparison if heavier is > threshold value then it keeps same and other moves i, 
		//compare current vector to shared vector the change needs to continue in that direction for a distance defined by momentum
		//could add in spin variable to kill momentum for time and move from old vect to new but not needed
		
		//if past threshold larger one maintains vector
		//other orients to closest degree of vector line then adds base spacing and momentum shake (shake would ideally be a combination of momentum + impact point but for now will have to do)
		//min distance
		double a1 = cell.getCellSize()/2;
		double a2 = cell2.getCellSize()/2;
		//3 = the num of free jumps needed to be given to clear each other
		double c = cell.getSpeed()*3;
		double b = cell2.getSpeed()*3;
		double A1 = 0.00;
		double A2 = 0.00;
		A1 = Math.pow(b, 2)+Math.pow(c, 2)-Math.pow(a1, 2);
		A2 = Math.pow(b, 2)+Math.pow(c, 2)-Math.pow(a2, 2);
		A1 = A1/(2*b*c);
		A2 = A2/(2*b*c);
		A1 = Math.acos(A1);
		A2 = Math.acos(A2);
		//A1 = Math.toDegrees(A1);
		//A2 = Math.toDegrees(A2);
		
		if(cent > 1 )
		{
			if(bigger == 0)
			{
				//its momentum, competing momentum, old vector, new conjoined vector, the distance that needs to be added for minimum  
				cell2.setVect(this.calcVectPlusShakeArb(momentum2,momentum1,cell2.getVect(), fVect, A1+A2), posi);
			}else
			{
				cell.setVect(this.calcVectPlusShakeArb(momentum1,momentum2,cell.getVect(), fVect, A1+A2), posi);
			}
		}else
		{
			cell.setVect(this.calcVectPlusShakeArb(momentum1,momentum2,cell.getVect(), fVect, A1), posi);
			cell2.setVect(this.calcVectPlusShakeArb(momentum2,momentum1,cell2.getVect(), fVect, A2), posi);
		}
		// assign new vector with direction travelling being based on nearest direction

		SingletonStatStore.pollCollisionTurn(cell);
		SingletonStatStore.pollCollisionTurn(cell2);
	}
	
	private double calcVectPlusShakeArb(double fst, double snd, double vect, double fVect, double baseBounce) 
	{
		double newAng = 0.00;
		//for now make the cap 30 so 1st > 2*snd = 0, same equation as before with % of 30 being answer
		//amount of difference has to be on a sliding scale up to 30 largest possible distance between the two is *2 smallest if largest > *2 smallest  largest = 0 smallest = 30 the amount of distance as a % of the total possible times 30 if distance between them is > smallest smallest bounce = 30 largest = 0 otherwise its as a % of smallest into % of 30 
		double bigger = 0.00;
		double small = 0.00;
		double large = 0.00;
		double cap = 30;
		if(fst < snd)
		{
			bigger = 1;
			small = fst;
			large = snd;
		}else
		{
			bigger = 0;
			small = snd;
			large = fst;
		}
		//75 and 50, 100 and 50
		double difference = large - small;//25, 50
		double diffCent = difference/small;//0.5, 1
		double total = cap*diffCent;//15, 30
		if(total > cap)
		{
			total = 30;
		}
		//30 = 1 if large is twice small
		//the range is now 30 * ratio capped at 30
		//within the range twice small = 0, 30 - difference % of small * 30 for the larger 
		//large = range - the size of the difference into small %, small = the remainder
		//diff into small = 1, 2
		//15 - ^*15 = 0, 7.5 
		//inverse = 30, 7.5
		//cant figure it out easily atm just make the cap the safe dist + arbitrary
		
		double centOf = large + small;//125, 150
		double lCent = large/centOf;//0.6, 0.6
		double sCent = small/centOf;//0.4, 0.4
		double largest = total - (lCent*total);//6, 12
		double smaller = total - sCent*total;//9, 18
		
		double bounceDist = 0.00;
		if(bigger == 1)
		{
			bounceDist = smaller;
		}else
		{
			bounceDist = largest;
		}
		//rather than getting % and grabbing that on a line you could just use this momentum equation as the offset for hitting, use flat amount for now
		
		//determine which direction is smaller and if its a + or - direction
		double[] dist1 = StaticCalculations.getAngularDistance(vect, fVect);
		double[] dist2 = StaticCalculations.getAngularDistance(vect, StaticCalculations.getOppositeAngle(fVect));
		if(dist1[0] < dist2[0])
		{
			if(dist1[1] == 0)
			{
				newAng = fVect - bounceDist;
				newAng = StaticCalculations.counterClockwiseClean(newAng);
			}else
			{

				newAng = fVect  + bounceDist;
				newAng = StaticCalculations.counterClockwiseClean(newAng);
			}
		}else
		{
			if(dist2[1] == 0)
			{
				newAng = StaticCalculations.getOppositeAngle(fVect)  - bounceDist;
				newAng = StaticCalculations.counterClockwiseClean(newAng);
				
			}else
			{
				newAng = StaticCalculations.getOppositeAngle(fVect)  + bounceDist;
				newAng = StaticCalculations.counterClockwiseClean(newAng);
			}
		}
		return newAng;
	}
	
	private double calcVectPlusShakePercent(double fst, double snd, double vect, double fVect, double baseBounce) 
	{
		double newAng = 0.00;
		double bounceDist = 0.00;
		//rather than getting % and grabbing that on a line you could just use this momentum equation as the offset for hitting
		//determine which direction is smaller and if its a + or - direction
		double[] dist1 = StaticCalculations.getAngularDistance(vect, fVect);
		double[] dist2 = StaticCalculations.getAngularDistance(vect, StaticCalculations.getOppositeAngle(fVect));
		if(dist1[0] < dist2[0])
		{
			if(dist1[1] == 0)
			{
				newAng = fVect - baseBounce - bounceDist;
				newAng = StaticCalculations.counterClockwiseClean(newAng);
			}else
			{

				newAng = fVect + baseBounce + bounceDist;
				newAng = StaticCalculations.counterClockwiseClean(newAng);
			}
		}else
		{
			if(dist2[1] == 0)
			{
				newAng = StaticCalculations.getOppositeAngle(fVect) - baseBounce - bounceDist;
				newAng = StaticCalculations.counterClockwiseClean(newAng);
				
			}else
			{
				newAng = StaticCalculations.getOppositeAngle(fVect) + baseBounce + bounceDist;
				newAng = StaticCalculations.counterClockwiseClean(newAng);
			}
		}
		return 0;
	}

	private double getDistToAddPoint(double clock, double cClock, double cent) {
		double dist = 0.00;
		if(clock < cClock)
		{
			dist = (clock/2)*cent;
			if(dist > clock/2)
			{
				dist = clock/2;
			}
		}else
		{
			dist = (cClock/2)*cent;
			if(dist > cClock/2)
			{
				dist = cClock/2;
			}
		}
		
		return dist;
	}

	private void collisionChangeBounce(int i, Integer l, ArrayList<Cell> cells, Posi posi) 
	{
		Cell cell = cells.get(i);
		Cell cell2 = cells.get(l);
		
		//cell = new Cell(20, 20, 20);
		//cell2 = new Cell(5, 5, 21);
		/*System.out.println(i+" "+l);
		System.out.println(cell.getPositionX()+" "+cell.getPositionY()+" "+cell2.getPositionX()+" "+cell2.getPositionY());
		System.out.println(cell.getVect()+" "+cell2.getVect());*/
		double x1 = cell.getPositionX();
		double x2 = cell2.getPositionX();
		double y1 = cell.getPositionY();
		double y2 = cell2.getPositionY();
		/*double y3 = cell.getPositionY()+5;
		double y4 = cell2.getPositionY()+5;
		//bottom
		double c = this.getEuclidean(x1, y1, x2, y2);
		//one side
		double b = this.getEuclidean(x1, y1, x1, y3);
		//other side
		double d = this.getEuclidean(x2, y2, x2, y4);
		//middle
		double a = this.getEuclidean(x1, y3, x2, y2);*/
		//System.out.println(c+" "+b+" "+d+" "+a);
		double poss2 = Math.atan2((y2-y1), (x2-x1));
		poss2= Math.toDegrees(poss2);
		double A = poss2;
		//double A = 90-poss2;
		//System.out.println(A);
		A = StaticCalculations.counterClockwiseClean(A);
		double B = StaticCalculations.getOppositeAngle(A);
		double larger = 0;
		double smaller = 0;
		if(A>B)
		{
			larger = A;
			smaller = B;
		}else
		{
			larger = B;
			smaller = A;
		}
		if(x1<x2)
		{
			//c1 is leftmost
			//side = b
			//this is 360-(180-A) the other is 180 - A
			/*double cosA = ((Math.pow(b, 2)) + (Math.pow(c, 2)) - (Math.pow(a, 2))) / (2*b*c);
			double A = Math.acos(cosA);
			double iA = 360-(180-A);
			double poss = Math.atan2(a, c);*/
			//System.out.println(""+A+" "+iA+" "+poss+" "+Math.toDegrees(poss2));
			cell.setVect(larger, posi);
			cell2.setVect(smaller, posi);
		}else
		{
			//c2 is leftmost
			//side = d
			/*
			b = d;double cosA = ((Math.pow(b, 2)) + (Math.pow(c, 2)) - (Math.pow(a, 2))) / (2*b*c);
			double A = Math.acos(cosA);
			double iA = 360-(180-A);
			double poss = Math.atan2(a, c);
			*/
			//System.out.println(""+A+" "+iA+" "+poss+" "+Math.toDegrees(poss2));
			cell.setVect(smaller, posi);
			cell2.setVect(larger, posi);
		}
		SingletonStatStore.pollCollisionTurn(cell);
		SingletonStatStore.pollCollisionTurn(cell2);
		if(x1<x2)
		{
			if(y1<y2)
			{
				/*System.out.println(cell.getPositionX()+" "+cell.getPositionY()+" "+cell2.getPositionX()+" "+cell2.getPositionY());
				System.out.println(cell.getVect()+" "+cell2.getVect());
				System.exit(0);*/
			}else
			{

				/*System.out.println(cell.getPositionX()+" "+cell.getPositionY()+" "+cell2.getPositionX()+" "+cell2.getPositionY());
				System.out.println(cell.getVect()+" "+cell2.getVect());
				System.exit(0);*/
			}
		}else
		{
			if(y1<y2)
			{

				/*System.out.println(cell.getPositionX()+" "+cell.getPositionY()+" "+cell2.getPositionX()+" "+cell2.getPositionY());
				System.out.println(cell.getVect()+" "+cell2.getVect());
				System.exit(0);*/
			}else
			{

				/*System.out.println(cell.getPositionX()+" "+cell.getPositionY()+" "+cell2.getPositionX()+" "+cell2.getPositionY());
				System.out.println(cell.getVect()+" "+cell2.getVect());
				System.exit(0);*/
			}
		}
		/*System.out.println(cell.getPositionX()+" "+cell.getPositionY()+" "+cell2.getPositionX()+" "+cell2.getPositionY());
		System.out.println(cell.getVect()+" "+cell2.getVect());
		System.exit(0);*/
		//System.out.println("boing");
		
	}

	

	

	

	private boolean isOverlappingEuc(Cell c, Cell c2) 
	{
		boolean overlapping = false;
		double dist = StaticCalculations.getEuclidean(c.getPositionX(), c.getPositionY(), c2.getPositionX(), c2.getPositionY());
		double dist2 = StaticCalculations.getEuclidean(c2.getPositionX(), c2.getPositionY(), c.getPositionX(), c.getPositionY());
		if(dist<= c.getCellSize() || dist2 <= c2.getCellSize() || dist <= c2.getCellSize() || dist2 <= c.getCellSize())
		{
			overlapping = true;
		}
		return overlapping;
	}

	public boolean isNear(Cell c) {
		//System.out.println("cell "+c.getUnique());
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

	public void genPosGrid(int dimensions, int posSize) {
		positionGrid = new PositionGrid(dimensions,posSize);
		if(SingletonHolder.isAttractZoneFlag())
		{
			positionGrid.genAttractZones();
		}
		if(SingletonHolder.isDeflectZoneFlag())
		{
			positionGrid.genDeflectZones();
		}
		positionGrid.applyZones();
	}

	public void genPosGrid(int dimensions, int posSize, int[][] heat) {
		positionGrid = new PositionGrid(dimensions,posSize,heat);
		if(SingletonHolder.isAttractZoneFlag())
		{
			positionGrid.genAttractZones();
		}
		if(SingletonHolder.isDeflectZoneFlag())
		{
			positionGrid.genDeflectZones();
		}
		positionGrid.applyZones();
	}
	
}
