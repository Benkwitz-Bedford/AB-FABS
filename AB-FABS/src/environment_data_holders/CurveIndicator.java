package environment_data_holders;

import java.util.ArrayList;

import cycle_components.StaticCalculations;

public class CurveIndicator 
{
	private ArrayList<Integer> curves = new ArrayList<Integer>();
	private ArrayList<double[]> nearestPoints = new ArrayList<double[]>();
	private ArrayList<double[]> eastPoints = new ArrayList<double[]>();
	private ArrayList<double[]> westPoints = new ArrayList<double[]>();
	private double x = 0;
	private double y = 0;
	
	public CurveIndicator(double x,double y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	public ArrayList<Integer> getCurves() {
		return curves;
	}
	public void setCurves(ArrayList<Integer> curves) {
		this.curves = curves;
	}
	public ArrayList<double[]> getNearestPoints() {
		return nearestPoints;
	}
	public void setNearestPoints(ArrayList<double[]> nearestPoints) {
		this.nearestPoints = nearestPoints;
	}
	public ArrayList<double[]> getEastPoints() {
		return eastPoints;
	}
	public void setEastPoints(ArrayList<double[]> eastPoints) {
		this.eastPoints = eastPoints;
	}
	public ArrayList<double[]> getWestPoints() {
		return westPoints;
	}
	public void setWestPoints(ArrayList<double[]> westPoints) {
		this.westPoints = westPoints;
	}
	public int getNearestCurvePoint(double positionX, double positionY) 
	{
		int dist = 1000;
		int point = 0;
		for(int i = 0 ; i < nearestPoints.size(); i++)
		{
			double thisDist = this.getEuc(positionX,positionY,nearestPoints.get(i)[0],nearestPoints.get(i)[1]);
			if(thisDist < dist)
			{
				point = i;
			}
		}
		return point;
	}
	
	public int getAngularNearestCurvePoint(double vect, double positionX, double positionY) 
	{
		int dist = 1000;
		int point = 0;
		for(int i = 0 ; i < nearestPoints.size(); i++)
		{
			double thisDist = StaticCalculations.getAngularDistance(vect,positionX,positionY,nearestPoints.get(i)[0],nearestPoints.get(i)[1]);
			if(thisDist < dist)
			{
				point = i;
			}
		}
		return point;
	}
	
	private double getEuc(double x, double y, double oX, double oY) {
		// TODO Auto-generated method stub
		return Math.sqrt(((Math.pow(x-oX, 2))+(Math.pow(y-oY, 2))));
	}
	
	
	
	public boolean doesntInclude(int point) {
		boolean found = true;
		for(int i = 0; i <curves.size();i++)
		{
			if(curves.get(i) == point)
			{
				found = false;
			}
		}
		return found;
	}
	public int getNearestCurvePointAngular(double vect, double positionX, double positionY) {
		int dist = 1000;
		int point = 0;
		for(int i = 0 ; i < nearestPoints.size(); i++)
		{
			double thisDist = StaticCalculations.getAngularDistance(vect, positionX,positionY,nearestPoints.get(i)[0],nearestPoints.get(i)[1]);
			if(thisDist < dist)
			{
				point = i;
			}
		}
		return point;
		
	}
	public int getPositionOfCurve(int curve) {
		int point = 0;
		for(int i = 0; i < curves.size(); i++ )
		{
			if(curves.get(i)== curve)
			{
				point = i;
			}
		}
		return point;
	}
	public CurveIndicator copy() {
		CurveIndicator copy = new CurveIndicator(x,y);
		ArrayList<Integer> curves2 = new ArrayList<Integer>();
		curves2.addAll(curves);
		copy.setCurves(curves2);
		ArrayList<double[]> points2 = new ArrayList<double[]>();
		points2.addAll(nearestPoints);
		copy.setNearestPoints(points2);
		ArrayList<double[]> east2 = new ArrayList<double[]>();
		east2.addAll(eastPoints);
		copy.setEastPoints(east2);
		ArrayList<double[]> west2 = new ArrayList<double[]>();
		west2.addAll(westPoints);
		copy.setWestPoints(west2);
		return copy;
	}
	public void setFindSetNearest() {
		double nearest = 10000;
		for(int i = 0; i < nearestPoints.size(); i++)
		{
			double dist = this.getEuc(getX(), getY(), nearestPoints.get(i)[0], nearestPoints.get(i)[1]);
			if( dist < nearest)
			{
				nearest = dist; 
				if(i != 0)
				{
					nearestPoints.remove(0);
				}
			}else
			{
				nearestPoints.remove(i);
				i--;
			}
		}
		
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
