package trajectory_components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PotentialProcessor {

	private double dx = 0.5;
	private Double[][][] potGrid;
	
	//further point minus the current (current point before adding the Brownian background motion), the whole divided by the pixel size dx, that gives (V(x+1)-V(x))/dx 
	public void process(File file, double dx) {
		Scanner read;
		this.setDx(dx);
		ArrayList<String[]> vals = new ArrayList<String[]>();
		try {
			read = new Scanner (file);
			do
			{
				vals.add(read.nextLine().split(","));
			}while(read.hasNextLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Double[][] pots = new Double[vals.size()][vals.get(0).length];
		for(int x = 0; x < pots.length;x++)
		{
			for(int y = 0; y < pots[x].length;y++)
			{
				pots[x][y]= Double.parseDouble(vals.get(x)[y]);
			}
		}
		Double[][][] res = new Double[pots.length][pots[0].length][2];
		for(int x = 0; x < pots.length;x++)
		{
			for(int y = 0; y < pots[x].length-1;y++)
			{
				res[x][y][0] = (pots[x][y+1]-pots[x][y])*dx;
			}
			res[x][pots[x].length-1][0] = 0.00;
		}
		for(int x = 0; x < pots.length-1;x++)
		{
			for(int y = 0; y < pots[x].length;y++)
			{
				res[x][y][1] = (pots[x+1][y]-pots[x][y])*dx;
			}
		}
		
		for(int y = 0; y < pots[0].length;y++)
		{
			res[pots.length-1][y][1] = 0.00;
		}
		setPotGrid(res);
	}

	public Double[][][] getPotGrid() {
		return potGrid;
	}

	public void setPotGrid(Double[][][] potGrid) {
		this.potGrid = potGrid;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

}
