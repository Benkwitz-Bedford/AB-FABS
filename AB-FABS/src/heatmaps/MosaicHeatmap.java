package heatmaps;

import java.util.ArrayList;

import singleton_holders.SingletonHolder;
import cell_data_holders.Cell;
import environment_data_holders.GCell;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;

public class MosaicHeatmap {
	private double squareSize = 0;
	private int[][][] colourValues;
	private HeatMap hot = new HeatMap();
	private int topValue = 255*3;
	private int highest = 0;
	private double ratio = 0;
	private double ratio2 = 0;
	private Posi pos2;
	private double gamma = 1.00;
	public MosaicHeatmap(int x, int y, Posi posi)
	{
		colourValues = new int[x+1][y+1][3];
		for(int i = 0; i < x+1; i++)
		{
			for(int l = 0; l < y+1; l++)
			{
				for(int m = 0; m < 3; m++)
				{
					colourValues[i][l][m] = 0;
				}
			}
		}
		pos2 = posi;
	}
	
	public ArrayList<double[]> getUpdatedSites(ArrayList<Cell> cells, Posi posi, int topper, double gamma2, int colSelection, boolean invert)
	{
		gamma = gamma2;
		ArrayList<double[]> blocks = new ArrayList<double[]>();
		pos2 = posi;
		this.setHighest(highest, topper);
		for(int i = 0; i < cells.size(); i++)
		{
			Cell c = cells.get(i);
			double[] xy = posi.getHeatByCell(c);
			if(xy[2]>highest)
			{
				this.setHighest((int)xy[2], topper);
			}
			//System.out.println("here a "+xy[2]+" "+ratio);
			int[] col =  null;
			if(invert)
			{
				col = hot.getRGBValueInvert(  (int) ((int) xy[2]*ratio), colSelection);	
			}else
			{
				col = hot.getRGBValue(  (int) ((int) xy[2]*ratio), colSelection);	
			}
			if(gamma !=1)
			{
				col[0] =  (int) (255.00 * Math.pow(((col[0] / 255.00)),gamma));
				col[1] =  (int) (255.00 * Math.pow(((col[1] / 255.00)),gamma));
				col[2] =  (int) (255.00 * Math.pow(((col[2] / 255.00)),gamma));
				
			}
			//colourValues[(int) xy[0]][(int) xy[1]] = new  int[]{col[0],col[1],col[2]};
			blocks.add(new double[]{xy[0],xy[1],col[0],col[1],col[2]});
		}
		
		
		return blocks;
		
	}
	
	public int[][][] getColourValues() {
		return colourValues;
	}
	public void setColourValues(int[][][] colourValues) {
		this.colourValues = colourValues;
	}
	public double getSquareSize() {
		return squareSize;
	}
	public void setSquareSize(double squareSize) {
		this.squareSize = squareSize;
	}

	public void fullUpdate(Posi posi, int colSelection, boolean invert) {
		int[][] heat = posi.getHeat();
		/*for(int x = 0; x < heat.length; x++)
		{
			for(int y = 0; y < heat[x].length; y++)
			{
				
			}
		}*/
		for(int x = 0; x < heat.length; x++)
		{
			for(int y = 0; y < heat[x].length;y++)
			{
				if(heat[x][y]>highest)
				{
					this.setHighest(heat[x][y],(int) SingletonHolder.getHeat());
				}
				//System.out.println("here b "+heat[x][y]+" "+ratio);
				if(invert)
				{
					colourValues[x][y] = hot.getRGBValueInvert(  (int) (ratio*heat[x][y]), colSelection);	
				}else
				{
					colourValues[x][y] = hot.getRGBValue(  (int) (ratio*heat[x][y]), colSelection);	
				}
			}
		}
		
	}

	public int getTopValue() {
		return topValue;
	}

	public void setTopValue(int topValue) {
		this.topValue = topValue;
	}

	public int getHighest() {
		return highest;
	}

	public void setHighest(int highest2, int topper) {
		this.highest = highest2;
		
		if(topper != 0)
		{
			highest = topper;
			//System.out.println("firing");
		}
		
		if(highest == 0)
		{
			ratio = 0;
		}else
		{
			ratio = (topValue)/highest;
		}
	
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public void updateCol(int colSelection, boolean invert) {
		for(int i = 0; i < colourValues.length; i++)
		{
			for(int l = 0; l < colourValues[i].length; l++)
			{
				//System.out.println("here c "+pos2.getHeat()[i][l]+" "+ratio);
				if(pos2.getHeat()[i][l]>highest)
				{
					this.setHighest(pos2.getHeat()[i][l],(int) SingletonHolder.getHeat());
				}
				if(invert)
				{
					colourValues[i][l] = hot.getRGBValueInvert(  (int) (pos2.getHeat()[i][l]*ratio), colSelection);	
				}else
				{
					colourValues[i][l] = hot.getRGBValue(  (int) (pos2.getHeat()[i][l]*ratio), colSelection);	
				}
				//System.out.println(pos2.getHeat().length);
				if(gamma !=1)
				{
					colourValues[i][l][0] =  (int) (255.00 * Math.pow(((colourValues[i][l][0] / 255.00)),gamma));
					colourValues[i][l][1] =  (int) (255.00 * Math.pow(((colourValues[i][l][1] / 255.00)),gamma));
					colourValues[i][l][2] =  (int) (255.00 * Math.pow(((colourValues[i][l][2] / 255.00)),gamma));
					
				}
			}
		}
		
	}
	

}
