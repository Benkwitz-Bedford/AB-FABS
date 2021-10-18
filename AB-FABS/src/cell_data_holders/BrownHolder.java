package cell_data_holders;

import java.util.ArrayList;
import java.util.List;

public class BrownHolder {
	
	private Double[] vectors;
	private Double[][] positions;
	private Double[] speeds;
	
	private double mu;
	private double sigma;
	private int length;
	private double x;
	private double y;
	private double speed;
	private double speedVarience;
	
	public BrownHolder(double mu, double sigma, int length, double x, double y, double speed, double speedVarience)
	{
		this.setMu(mu);
		this.setSigma(sigma);
		this.setLength(length);
		this.setX(x);
		this.setY(y);
		this.setSpeed(speed);
		this.setSpeedVarience(speedVarience);
		this.generateBrowns();
	}
	
	private void generateBrowns() {
		speeds = genSpeeds(mu,sigma,length,speed,speedVarience);
		positions = genPositions(mu,sigma,length,x,y,speeds);
		vectors = extrapVects(positions);
		
	}

	private Double[] extrapVects(Double[][] positions2) {
		// TODO Auto-generated method stub
		return null;
	}

	private Double[][] genPositions(double mu2, double sigma2, int length2, double x2, double y2, Double[] speeds2) {
		// TODO Auto-generated method stub
		return null;
	}

	private Double[] genSpeeds(double mu, double sigma, int length, double speed, double speedVarience) 
	{
	/*	/**
		08
		     * Run the Wiener process for a given period and initial amount with a monthly value that is added every month. The
		09
		     * code calculates the projection of the value, a set of quantiles and the brownian geometric motion based on a
		10
		     * random walk.
		11
		     *
		12
		     * @param mu mean value (annualized)
		13
		     * @param sigma standard deviation (annualized)
		14
		     * @param years projection duration in years
		15
		     * @param initialValue the initial value
		16
		     * @param monthlyValue the value that is added per month
		17
		     * @param breaks quantile breaks
		18
		     * @return a List of double arrays containing the values per month for the given quantile breaks
		19
		     

		double periodizedMu = mu / 12;
		
		double periodizedSigma = sigma / Math.sqrt(12);
		
		int periods = years * 12;
		
		 
		
		List<double[]> result = new ArrayList<double[]>();
		
        double value = initialValue + (monthlyValue * i);

        NormalDistribution normalDistribution = new NormalDistribution(periodizedMu * (i + 1),

                periodizedSigma * sqrt(i + 1));

        double bounds[] = new double[breaks.length];

        for (int j = 0; j < breaks.length; j++) 
        {

            double normInv = normalDistribution.inverseCumulativeProbability(breaks[j]);

            bounds[j] = value * exp(normInv);

        }



        result.add(bounds);

        */

        return null;

	}

	public Double[] getVectors() {
		return vectors;
	}
	public void setVectors(Double[] vectors) {
		this.vectors = vectors;
	}
	public Double[] getSpeeds() {
		return speeds;
	}
	public void setSpeeds(Double[] speeds) {
		this.speeds = speeds;
	}

	public double getMu() {
		return mu;
	}

	public void setMu(double mu) {
		this.mu = mu;
	}

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
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

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSpeedVarience() {
		return speedVarience;
	}

	public void setSpeedVarience(double speedVarience) {
		this.speedVarience = speedVarience;
	}

	
	
}
