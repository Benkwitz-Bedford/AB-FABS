package environment_data_holders;

import java.util.ArrayList;

import cell_data_holders.Cell;

public class GCell 
{
	private ArrayList<Integer> cellPositions = new ArrayList<Integer>();
	private int x = 0;
	private int y = 0;
	private boolean isWall = false;
	private boolean isAttract = false;

	private CurveIndicator curves = new CurveIndicator(x,y);
	private ArrayList<double[]> nearbyPointHolder = new ArrayList<double[]>();
	
	private double bezStrength = 0;
	
	//attraction/deflection zone info
	private double attractionStrength = 0;
	private double attractX = 0;
	private double attractY = 0;
	private double deflectX = 0;
	private double deflectY = 0;
	
	//angle stuff
	private int[] directions = new int[8];
	private int[] relativeAngle = new int[16];
	private int[] constantDirections = new int[8];
	
	
	public GCell(int x2, int y2) 
	{
		//System.out.println("reset "+x2+" "+y2);
		x = x2;
		y = y2;
	}
	public GCell copy() {
		GCell copy = new GCell(x, y);
//		System.out.println("copy");
		ArrayList<Integer> copyPositions = new ArrayList<Integer>();
		copyPositions.addAll(cellPositions);
		copy.setCellPositions(copyPositions);
		copy.setWall(isWall);
		copy.setAttract(isAttract);
		copy.setBezStrength(bezStrength); 
		copy.setCurves(curves.copy());
		ArrayList<double[]> pointHold = new ArrayList<double[]>();
		pointHold.addAll(nearbyPointHolder);

		int[] directionsHold = new int[8];
		for(int i = 0; i < directions.length;i++)
		{
			directionsHold[i] = directions[i];
		}
		copy.setDirections(directionsHold);
		int[] relativeAngleHold = new int[16];
		for(int i = 0; i < relativeAngle.length;i++)
		{
			relativeAngleHold[i] = relativeAngle[i];
		}
		copy.setRelativeAngle(relativeAngleHold);
		int[] constantDirectionsHold = new int[8];
		for(int i = 0; i < constantDirections.length;i++)
		{
			constantDirectionsHold[i] = constantDirections[i];
		}
		copy.setConstantDirections(constantDirectionsHold);
		copy.setNearbyPointHolder(pointHold);
		copy.setAttractionStrength(attractionStrength);
		copy.setAttractX(attractX);
		copy.setAttractY(attractY);
		copy.setDeflectX(deflectX);
		copy.setDeflectY(deflectY);
		return copy;
	}
	
	public void resetTurnHeats()
	{
		directions = new int[8];
		relativeAngle = new int[16];
		constantDirections = new int[8];
	}
	
	public void copyBez(GCell g) {
		curves = g.getCurves().copy();
		isWall = g.isWall();
		isAttract = g.isAttract();
		bezStrength = g.getBezStrength();
		nearbyPointHolder = new ArrayList<double[]>();
		nearbyPointHolder.addAll(g.getNearbyPointHolder());
		
	}
	
	public void addDeflect(double d, double e, double stren, int r, double falloff) {
		deflectX = d;
		deflectY = e;
		for(int i = 0; i < r;i++)
		{
			stren = stren - ((stren/100)*falloff);
		}
		attractionStrength += -stren;
		
	}
	public void addAttract(double d, double e, double stren, int r,
			double falloff) {
		//System.out.println("here");
		attractX = d;
		attractY = e;
		for(int i = 0; i < r;i++)
		{
			stren = stren - ((stren/100)*falloff);
		}
		attractionStrength += stren;
		
	}
	
	public ArrayList<Integer> getCellPositions() {
		/*if(cellPositions.size()!=0)
		{
			System.out.println(cellPositions.size());
		}*/
		return cellPositions;
	}
	public void setCellPositions(ArrayList<Integer> cellPositions) {
		this.cellPositions = cellPositions;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isWall() {
		return isWall;
	}
	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}
	public boolean isAttract() {
		return isAttract;
	}
	public void setAttract(boolean isAttract) {
		this.isAttract = isAttract;
	}
	public void addCell(int i) 
	{
		cellPositions.add(i);
	}
	public int getSize() 
	{
		return cellPositions.size();
	}
	public void remove(int i) 
	{
		for(int l = 0; l < cellPositions.size(); l++)
		{
			if(cellPositions.size() > 0 && l>= 0 && cellPositions.get(l) == i)
			{
				cellPositions.remove(l);
				l--;
			}
		}
		
		
	}
	public int getCell(int n) 
	{
		return cellPositions.get(n);
	}
	public void reportContents(Cell[] cells) 
	{
		//System.out.println(x+" "+y);
		for(int i = 0; i < cellPositions.size(); i++)
		{
			System.out.println(i+" is "+cells[cellPositions.get(i)].getPositionX()+" "+cells[cellPositions.get(i)].getPositionY());
		}
	}
	
	public void addAbs(double m) {
		m = m+90;
		if(m >360)
		{
			m = m-360;
		}
		if(m < 0)
		{
			m = 360.00+m;
		}
		if(m > 337.5 ||m <= 22.5)
		{
			constantDirections[0]++;
		}else
		{
			if(m <= 67.5)
			{
				constantDirections[1]++;
			}else
			{
				if(m <= 112.5)
				{
					constantDirections[2]++;
				}else
				{
					if(m <= 157.5)
					{
						constantDirections[3]++;
					}else
					{
						if(m <= 202.5)
						{
							constantDirections[4]++;
						}else
						{
							if(m <= 247.5)
							{
								constantDirections[5]++;
							}else
							{
								if(m <= 292.5)
								{
									constantDirections[6]++;
								}else
								{
									if(m <= 337.5)
									{
										constantDirections[7]++;
									}
								}
							}
						}
					}
				}
			}
		}
		
	}
	public CurveIndicator getCurves() {
		return curves;
	}
	public void setCurves(CurveIndicator curves) {
		this.curves = curves;
	}
	public ArrayList<double[]> getNearbyPointHolder() {
		return nearbyPointHolder;
	}
	public void setNearbyPointHolder(ArrayList<double[]> nearbyPointHolder) {
		this.nearbyPointHolder = nearbyPointHolder;
	}
	public void resetCurves() {
		curves = new CurveIndicator(x,y);
		nearbyPointHolder = new ArrayList<double[]>();
		
	}
	
	public double getBezStrength() {
		return bezStrength;
	}
	public void setBezStrength(double bezStrength) {
		this.bezStrength = bezStrength;
	}
	public double getAttractionStrength() {
		return attractionStrength;
	}
	public void setAttractionStrength(double attractionStrength) {
		this.attractionStrength = attractionStrength;
	}
	public double getAttractX() {
		return attractX;
	}
	public void setAttractX(double attractX) {
		this.attractX = attractX;
	}
	public double getAttractY() {
		return attractY;
	}
	public void setAttractY(double attractY) {
		this.attractY = attractY;
	}
	
	public double getDeflectX() {
		return deflectX;
	}
	public void setDeflectX(double deflectX) {
		this.deflectX = deflectX;
	}
	public double getDeflectY() {
		return deflectY;
	}
	public void setDeflectY(double deflectY) {
		this.deflectY = deflectY;
	}
	public int[] getDirections() {
		return directions;
	}
	public void setDirections(int[] directions) {
		this.directions = directions;
	}
	public int[] getRelativeAngle() {
		/*for(int i = 0; i < relativeAngle.length;i++)
		{
			if(relativeAngle[i] !=0)
			{
				System.out.println("ANGLE "+relativeAngle[i]);
			}
		}*/
		return relativeAngle;
	}
	public void setRelativeAngle(int[] relativeAngle) {
		this.relativeAngle = relativeAngle;
	}
	public int[] getConstantDirections() {
		return constantDirections;
	}
	public void setConstantDirections(int[] constantDirections) {
		this.constantDirections = constantDirections;
	}
	
}
