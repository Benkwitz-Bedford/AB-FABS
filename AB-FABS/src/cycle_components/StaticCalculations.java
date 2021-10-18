package cycle_components;

public class StaticCalculations {

	public static double getOppositeAngle(double poss2) {
		double opp = poss2-180;
		opp = StaticCalculations.counterClockwiseClean(opp);
		return opp;
	}
	
	public static double counterClockwiseClean(double opp) {
		//System.out.println("opp "+opp);
		if(opp<0.00)
		{
			do
			{
				opp = opp +360.00;
			}while(opp < 360.00);
		}
		
		if(opp > 360.00)
		{
			do
			{
				opp = opp -360.00;
			}while(opp > 360.00);
		}
		return opp;
	}
	
	public static double getAngularDistance(double vect, double d, double e, double points, double points2) {
		//System.out.println("here");
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
		//System.out.println("angle "+angle+" vect "+vect+" difference "+difference);
		return difference;
	}
	
	public static double getEuclidean(double x, double y, double oX, double oY) {
		// TODO Auto-generated method stub
		return Math.sqrt(((Math.pow(x-oX, 2))+(Math.pow(y-oY, 2))));
	}
	
	private static double setForClock(double angle) 
	{
		if(angle < 0)
		{
			//System.out.println("fuck");
			//angle = angle-90;
		}else
		{

			angle = angle-90;
		}
		angle = StaticCalculations.getOppositeAngle(angle);
		//System.out.println("angle "+angle);
		return angle;
	}

	public static double[] getAngularDistance(double vect1, double vect2) 
	{
		double[] ans = new double[2];
		double clock = 0.00;
		double cClock = 0.00;
		if(vect1 > vect2)
		{

			clock += vect1 - vect2;
			cClock = 360- vect1 +vect2;
			
			if(clock < cClock)
			{
				ans[0] = clock;
				ans[1] = 0;
			}else
			{
				ans[0] = cClock;
				ans[1] = 1;
			}
			
		}else
		{
			clock += vect2 - vect1;
			cClock = 360-vect2+vect1;
			if(clock < cClock)
			{
				ans[0] = clock;
				ans[1] = 1;
			}else
			{
				ans[0] = cClock;
				ans[1] = 0;
			}
		}
		return ans;
	}
	
}
