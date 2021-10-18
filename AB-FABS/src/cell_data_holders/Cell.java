package cell_data_holders;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import cycle_components.StaticCalculations;
import environment_data_holders.Posi;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;

public class Cell {
	
	private int oldID = 0;
	double vect = 0;
	private double prevVect = 0;
	double positionX = 0;
	double positionY = 0;
	//push pos usually
	private double positionX2 = 0;
	private double positionY2 = 0;
	
	private int repTabooSize = 0;
	private int repTabooTimer = 0;
	
	private int overlapLast = 0;
	private double bezLeaver = 0;
	
	private boolean corporeal = true;
	private boolean living = true;
	
	private int repChance = 0;
	private int death = 0; 
	//gather leftovers of int
	private double speed = 0.00;
	private int deathTimer = 0;
	private String deathType = "uhhh";
	private int deathTime = 0;
	private int lastBounce = 0;
	private int unique = 0;
	
	private int curve = 0;
	private int curveDirection = 0;
	private boolean followingCurve = false;
	private ArrayList<double[]> previousPoints = new ArrayList<double[]>();
	
	private ArrayList<double[]> tail = new ArrayList<double[]>();
	private int tailPointer = 0;
	
	private String lastAction = "none";
	private  double[] prevTrig = new double[]{0,0};
	//cellData:
		//distance travelled
	private double distance = 0.00;
		//collisions
	private int collisions = 0;
		//turns
	private int turns = 0;
		//times chosen bez
	private int bezChosen = 0;
		//times replicated
	private int replicated = 0;
	private int pointsMax = 10;
	
	private int cellSize = 2;
	
	private long[] turnWeights;
	
	private int turnChance = 0;
	private String trajType = "random";
	private int overlappers = 0;
	private ArrayList<Integer> bounceSequence = new ArrayList<Integer>();
	
	private long antRatio = 0;
	
	private int pheno = 0;
	//position and speed up to that point
	private ArrayList<double[]> brownianList = new ArrayList<double[]>();
	
	private CellDataPackage pack = new CellDataPackage(); 
	
	private int xCrossed = 0;
	private int yCrossed = 0;
	
	private double projectedJump = 0.00;
	
	
	private Color colour = Color.white;
	
	private double incSpeed = 0.00;
	
	private int spawnTime = 0;

	private ArrayList<Double> xPositionList = new ArrayList<Double>();
	private ArrayList<Double> yPositionList = new ArrayList<Double>();
	
	public Cell(double d, double x, double y, int rC, int de, double command, int unique, int size, long[] weights, int chance) 
	{
		//vect, x, y, rep chance, death chance, speed, unique id, 
		vect = d;
		positionX = x;
		positionY = y;
		this.setRepChance(rC);
		this.setDeath(de);
		this.setSpeed(command);
		this.setUnique(unique);
		previousPoints.add(new double[]{0,0});
		cellSize = size;
		turnChance = chance;
		turnWeights = weights;
		brownianList.add(new double[]{positionX,positionY,speed,SingletonHolder.getIncrement(),xCrossed,yCrossed});
		spawnTime = SingletonHolder.getIncrement();
	}

	
	public Cell(Cell cell) 
	{
		vect = cell.getVect();
		positionX = cell.getPositionX();
		positionY = cell.getPositionY();
		positionX2 = cell.getPositionX2();
		positionY2 = cell.getPositionY2();
		repTabooSize = cell.getTabooSize();
		overlapLast = cell.getOverlapLast();
		corporeal = cell.isCorporeal();
		living = cell.isLiving();
		repChance = cell.getRepChance();
		death = cell.getDeath();
		speed = cell.getSpeed();
		deathTimer = cell.getDeathTimer();
		unique = cell.getUnique();
		curve = cell.getCurve();
		curveDirection =cell.getCurveDirection();
		followingCurve = cell.isFollowingCurve();
		previousPoints = cell.getPreviousPoints();
		lastAction = cell.getLastAction();
		bezLeaver = cell.getBezLeaver();
		distance = cell.getDistance();
		collisions = cell.getCollisions();
		turns = cell.getTurns();
		bezChosen = cell.getBezChosen();
		replicated = cell.getReplicated();
		tail =cell.getTail();
		tailPointer = cell.getTailPointer();
		prevVect = cell.getPrevVect();
		cellSize = cell.getCellSize();
		turnWeights = cell.getTurnWeights();
		turnChance = cell.getTurnChance();
		deathType = cell.getDeathType();
		deathTime = cell.getDeathTime();
		trajType = cell.getTrajType();
		pheno = cell.getPheno();
		oldID = cell.getOldID();
		antRatio = cell.getAntRatio();
		brownianList = cell.getBrownianList();
		pack = new CellDataPackage(cell.getPack());
		xCrossed = cell.getxCrossed();
		yCrossed = cell.getyCrossed();
		this.updatePosiList();
	}
	private void updatePosiList() {
		getxPositionList().add(positionX);
		getyPositionList().add(positionY);
		
	}

	public Cell(int i, String[] val, int phe, int uni) {
		
		Random rand = SingletonHolder.getMasterRandom();
		vect = rand.nextInt(360001)/1000;
		positionX = rand.nextInt(i*1000+1)/1000;
		positionY = rand.nextInt(i*1000+1)/1000;
		this.attributesFromArray(val);
		pheno = phe;
		this.setUnique(uni);
		brownianList.add(new double[]{positionX,positionY,speed,SingletonHolder.getIncrement(),xCrossed,yCrossed});
		this.updatePosiList();
		
		
	}
	
	public Cell(int i, String[] val, int phe, int uni, double d, double e) {
		
		Random rand = SingletonHolder.getMasterRandom();
		vect = rand.nextInt(360001)/1000;
		//System.out.println("placing "+d+", "+e);
		positionX = d;
		positionY = e;
		this.attributesFromArray(val);
		pheno = phe;
		this.setUnique(uni);
		brownianList.add(new double[]{positionX,positionY,speed,SingletonHolder.getIncrement(),xCrossed,yCrossed});
		
		
	}

	public int getBounceSequentialDifference() {
		int count = 0;
		for(int i = 0; i < bounceSequence.size()-1;i++)
		{
			count += bounceSequence.get(i+1)-bounceSequence.get(i);
			//System.out.println("."+bounceSequence.get(i));
		}
		//System.out.println(count);
		return count;
	}
	
	public void attributesFromArray(String[] val) {
		
		//0-1  repChance, repTaboo
		//2-4  deathChance, deathType, deathTimed
		//5 speed
		//6 change
		//7 wOne
		//8 wTwo
		//9 wThree
		//10 wFour
		//11 wFive
		//12 wSix
		//13 wSeven
		//14 wEight
		//15 trajType
		//16 size
		//17 antRatio
		repChance = (int) Math.round( Double.parseDouble(val[0]));
		repTabooSize =  (int) Math.round( Double.parseDouble(val[1]));
		death = (int) Math.round( Double.parseDouble(val[2]));
		deathType = val[3];
		deathTime =  (int) Math.round( Double.parseDouble(val[4]));
		speed  = Double.parseDouble(val[5]);
		turnChance = (int) Math.round( Double.parseDouble(val[6]));
		long[] wei = new long[8];
		for(int l = 0 ; l < wei.length;l++)
		{
			wei[l] = Math.round( Double.parseDouble(val[7+l]));
		}
		turnWeights = wei;
		trajType = val[15];
		cellSize = (int) Math.round( Double.parseDouble(val[16]));
		antRatio = Math.round( Double.parseDouble(val[17]));
	}

	public ArrayList<String[]> fullReport()
	{
		ArrayList<String[]> strings = new ArrayList<String[]>();
		
		strings.add(new String[]{"vector: ", ""+vect});
		strings.add(new String[]{"positionX:  ", ""+positionX});
		strings.add(new String[]{"positionY: ", ""+positionY});
		strings.add(new String[]{"positionX2: ", ""+positionX2});
		strings.add(new String[]{"positionY2: ", ""+positionY2});
		strings.add(new String[]{"repTabooSize: ", ""+repTabooSize});
		strings.add(new String[]{"overlapLast: ", ""+overlapLast});
		strings.add(new String[]{"corporeal: ", ""+corporeal});
		strings.add(new String[]{"living: ", ""+living});
		strings.add(new String[]{"repChance: ", ""+repChance});
		strings.add(new String[]{"death: ", ""+death});
		strings.add(new String[]{"speed: ", ""+speed});
		strings.add(new String[]{"deathTimer: ", ""+deathTimer});
		strings.add(new String[]{"unique: ", ""+unique});
		strings.add(new String[]{"curve: ", ""+curve});
		strings.add(new String[]{"curveDirection: ", ""+curveDirection});
		strings.add(new String[]{"followingCurve: ", ""+followingCurve});
		strings.add(new String[]{"previousPoint: ", ""+previousPoints.get(0)});
		strings.add(new String[]{"lastAction: ", ""+lastAction});
		strings.add(new String[]{"bezLeaver: ", ""+bezLeaver});
		strings.add(new String[]{"distance: ", ""+distance});
		strings.add(new String[]{"collisions: ", ""+collisions});
		strings.add(new String[]{"turns: ", ""+turns});
		strings.add(new String[]{"bezChosen: ", ""+replicated});
		
		return strings;
	}
	
	public double getVect()
	{
		return vect;
	}
	
	public void setVect(double vector, Posi posi)
	{
		//System.out.println("vect set "+vector);
		if(vector < 0 || vector > 360)
		{
			vector = StaticCalculations.counterClockwiseClean(vector);
		}
		prevVect = vect;
		vect = vector;
		if(SingletonHolder.getPot() == null)
		{
			SingletonStatStore.pollTurnType(this, posi);
		}
	}
	
	public double getPositionX()
	{
		return positionX;
	}
	
	public void setPositionX(double d)
	{
		
		positionX = d;
		getxPositionList().add(positionX);
	}
	
	public double getPositionY()
	{
		return positionY;
	}
	
	public void setPositionY(double d)
	{
		positionY = d;
		getyPositionList().add(positionY);
	}

	public double getPositionX2() {
		return positionX2;
	}

	public void setPositionX2(double positionX2) {
		this.positionX2 = positionX2;
	}

	public double getPositionY2() {
		return positionY2;
	}

	public void setPositionY2(double positionY2) {
		this.positionY2 = positionY2;
	}

	public int getOverlapLast() {
		return overlapLast;
	}

	public void setOverlapLast(int overlapLast) {
		this.overlapLast = overlapLast;
	}

	public int getTabooSize() {
		return repTabooSize;
	}

	public void setTabooSize(int tabooSize) {
		this.repTabooSize = tabooSize;
	}

	public void resetRepTaboo()
	{
		repTabooTimer = this.getTabooSize();
		
	}

	public boolean isCorporeal() {
		return corporeal;
	}

	public void setCorporeal(boolean corporeal) {
		this.corporeal = corporeal;
		//this.getPack().setLifetime(SingletonHolder.getIncrement()-spawnTime);
	}

	public boolean isLiving() {
		return living;
	}

	public void setLiving(boolean living) {
		this.living = living;
	}

	public void decTabooSize() {
		repTabooSize--;
		
	}

	public int getRepChance() {
		return repChance;
	}

	public void setRepChance(int repChance) {
		if(repChance < 0)
		{
			repChance = 0;
		}
		this.repChance = repChance;
	}

	public int getDeath() {
		return death;
	}

	public void setDeath(int death) {
		if(death < 0)
		{
			death = 0;
		}
		this.death = death;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		if(speed < 0.00)
		{
			speed = 0.00;
		}

		brownianList.add(new double[]{positionX,positionY,this.speed,SingletonHolder.getIncrement(),xCrossed,yCrossed});
		//xCrossed = 0;
		//yCrossed = 0;
		this.speed = speed;
	}

	public int getDeathTimer() {
		return deathTimer;
	}

	public void setDeathTimer(int deathTimer) {
		this.deathTimer = deathTimer;
	}

	public int getUnique() {
		return unique;
	}

	public void setUnique(int unique) {
		this.unique = unique;
	}

	public int getCurve() {
		return curve;
	}

	public void setCurve(int curve) {
		this.curve = curve;
	}

	public int getCurveDirection() {
		return curveDirection;
	}

	public void setCurveDirection(int curveDirection) {
		this.curveDirection = curveDirection;
	}

	public boolean isFollowingCurve() {
		return followingCurve;
	}

	public void setFollowingCurve(boolean followingCurve) {
		this.followingCurve = followingCurve;
	}

	
	public void resetCurves() 
	{
		curve = 0;
		curveDirection = 2;
		followingCurve = false;
		previousPoints = new ArrayList<double[]>();
		
	}

	public String getLastAction() {
		return lastAction;
	}

	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}

	public double getBezLeaver() {
		return bezLeaver;
	}

	public void setBezLeaver(double d) {
		this.bezLeaver = d;
		this.setFollowingCurve(false);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getCollisions() {
		return collisions;
	}

	public void setCollisions(int collisions) {
		this.collisions = collisions;
	}

	public int getBezChosen() {
		return bezChosen;
	}

	public void setBezChosen(int bezChosen) {
		this.bezChosen = bezChosen;
	}

	public int getReplicated() {
		return replicated;
	}

	public void setReplicated(int replicated) {
		this.replicated = replicated;
	}

	public int getTurns() {
		return turns;
	}

	public void setTurns(int turns) {
		this.turns = turns;
	}

	public void incrementDistance(double jump) {
		distance = distance+jump;
		
	}

	public void incrementReplications() {
		replicated++;
		
	}

	public void incrementFollowingBez() {
		bezChosen++;
		
	}

	public void incrementCollisions() {
		collisions++;
		
	}

	public void incrementTurns() {
		turns++;
		
	}
	
	public void addTailPoint(double[] ds)
	{
		tail.add(ds);
		if(tail.size()> 40)
		{
			tail.remove(0);
		}
	}

	public ArrayList<double[]> getTail() {
		return tail;
	}

	public void setTail(ArrayList<double[]> tail) {
		this.tail = tail;
	}

	public int getTailPointer() {
		return tailPointer;
	}

	public void setTailPointer(int tailPointer) {
		this.tailPointer = tailPointer;
	}

	public ArrayList<double[]> getPreviousPoints() {
		return previousPoints;
	}

	public void setPreviousPoints(ArrayList<double[]> previousPoints) {
		this.previousPoints = previousPoints;
	}
	
	public void addPreviousPoint(double[] point)
	{
		if(previousPoints.size()==pointsMax)
		{
			previousPoints.add(0,point);
			previousPoints.remove(pointsMax);
		}else
		{
			previousPoints.add(0,point);
		}
	}

	public double[] getPreviousPoint() {
		// TODO Auto-generated method stub
		return previousPoints.get(0);
	}

	public double getPrevVect() {
		return prevVect;
	}

	public void setPrevVect(double prevVect) {
		this.prevVect = prevVect;
	}

	public double[] getPrevTrig() {
		return prevTrig;
	}

	public void setPrevTrig(double[] prevTrig) {
		this.prevTrig = prevTrig;
	}

	public int getCellSize() {
		return cellSize;
	}

	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

	public long[] getTurnWeights() {
		return turnWeights;
	}

	public void setTurnWeights(long[] turnWeights) {
		this.turnWeights = turnWeights;
	}

	public int getTurnChance() {
		return turnChance;
	}

	public void setTurnChance(int turnChance) {
		this.turnChance = turnChance;
	}

	public String getDeathType() {
		return deathType;
	}

	public void setDeathType(String deathType) {
		this.deathType = deathType;
	}

	public int getDeathTime() {
		return deathTime;
	}

	public void setDeathTime(int deathTime) {
		this.deathTime = deathTime;
	}

	public String getTrajType() {
		return trajType;
	}

	public void setTrajType(String trajType) {
		this.trajType = trajType;
	}

	public int getPheno() {
		return pheno;
	}

	public void setPheno(int pheno) {
		this.pheno = pheno;
	}

	public int getRepTabooTimer() {
		return repTabooTimer;
	}

	public void setRepTabooTimer(int repTabooTimer) {
		this.repTabooTimer = repTabooTimer;
	}

	public int getOldID() {
		return oldID;
	}

	public void setOldID(int oldID) {
		this.oldID = oldID;
	}

	public int getOverlappers() {
		return overlappers;
	}

	public void setOverlappers(int overlappers) {
		this.overlappers = overlappers;
	}

	public int getLastBounce() {
		return lastBounce;
	}

	public void setLastBounce(int lastBounce) {
		this.lastBounce = lastBounce;
		bounceSequence.add(lastBounce);
		if(bounceSequence.size()>5)
		{
			bounceSequence.remove(0);
		}
	}


	public ArrayList<Integer> getBounceSequence() {
		return bounceSequence;
	}


	public void setBounceSequence(ArrayList<Integer> bounceSequence) {
		this.bounceSequence = bounceSequence;
	}


	public long getAntRatio() {
		return antRatio;
	}


	public void setAntRatio(long antRatio) {
		this.antRatio = antRatio;
	}


	public ArrayList<double[]> getBrownianList() {
		return brownianList;
	}


	public void setBrownianList(ArrayList<double[]> brownianList) {
		this.brownianList = brownianList;
	}


	public CellDataPackage getPack() {
		return pack;
	}


	public void setPack(CellDataPackage pack) {
		this.pack = pack;
	}


	public int getxCrossed() {
		return xCrossed;
	}


	public void setxCrossed(int xCrossed) {
		this.xCrossed = xCrossed;
		//System.out.println("x cross: "+xCrossed );
	}


	public int getyCrossed() {
		return yCrossed;
	}


	public void setyCrossed(int yCrossed) {
		this.yCrossed = yCrossed;
	}


	public double getProjectedJump() {
		return projectedJump;
	}


	public void setProjectedJump(double projectedJump) {
		this.projectedJump = projectedJump;
	}




	public Color getColour() {
		return colour;
	}


	public void setColour(Color colour) {
		this.colour = colour;
	}


	public double getIncSpeed() {
		return incSpeed;
	}


	public void setIncSpeed(double incSpeed) {
		this.incSpeed = incSpeed;
	}
	
	public ArrayList<Double> getxPositionList() {
		return xPositionList;
	}


	public void setxPositionList(ArrayList<Double> xPositionList) {
		this.xPositionList = xPositionList;
	}
	
	public ArrayList<Double> getyPositionList() {
		return yPositionList;
	}


	public void setyPositionList(ArrayList<Double> yPositionList) {
		this.yPositionList = yPositionList;
	}

	

}
