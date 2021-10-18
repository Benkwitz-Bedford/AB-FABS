package environment_data_holders;

import java.awt.Polygon;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import cycle_components.StaticCalculations;
import cell_data_holders.Cell;
import singleton_holders.SingletonHolder;

public class PositionGridCA extends Posi{
	


	public PositionGridCA(double dimension, double cSize) 
	{
		dimensions = dimension;
		cellSize = cSize;
		System.out.println(dimension+" "+cSize);
		grid = new GCell[(int) ((dimension/cellSize)+1)][(int) ((dimension/cellSize)+1)];
		setHeat(new int[(int) ((dimension/cellSize)+1)][(int) ((dimension/cellSize)+1)]);
		for(int x = 0; x <grid.length;x++)
		   {
			   for(int y = 0; y < grid[x].length;y++)
			   {
				   grid[x][y] = new GCell(x,y);
				   heat[x][y] = 0;
			   }
		   }
	}

	public PositionGridCA(int dimension, int cSize, int[][] heat2) {
		dimensions = dimension;
		cellSize = cSize;
		grid = new GCell[(int) ((dimension/cellSize)+1)][(int) ((dimension/cellSize)+1)];
		heat = heat2;
		for(int x = 0; x <grid.length;x++)
		   {
			   for(int y = 0; y < grid[x].length;y++)
			   {
				   grid[x][y] = new GCell(x,y);
			   }
		   }
	}
	
	public PositionGridCA copy() {
		PositionGridCA pos = new PositionGridCA(dimensions, cellSize*2);
		pos.setGrid(this.gridOfCopies(grid));
		pos.setDimensions(dimensions);
		pos.setCellSize(cellSize);
		
		int[][] heatCopy = new int[heat.length][heat.length];
		for(int x = 0 ; x < heat.length; x++)
		{
			for(int y = 0; y < heat[x].length; y++)
			{
				heatCopy[x][y] = heat[x][y];
			}
		}
		pos.setHeat(heatCopy);
		return pos;
	}

	public ArrayList<Integer> getPossibleOverlap(Cell c, int i, ArrayList<Cell> arrayList) 
	{
		int x = this.genXPos(c);
		int y = this.genYPos(c);
		ArrayList<Integer> possibles = new ArrayList<Integer>();
		if(grid[x][y].getCellPositions().size() >=2)
		{
			for(int l = 0; l < grid[x][y].getCellPositions().size();l++)
			{
				int m = grid[x][y].getCellPositions().get(l);
				if(m !=i && arrayList.get(m).isCorporeal())
				{
					possibles.add(m);
				}
			}
		}
		
		return possibles;
	}

	public void updatePosition(double oX, double oY, Cell c, int i) 
	{
		Boolean same = false;
		if((int) c.getPrevTrig()[0]/cellSize == (int) c.getPositionX()/cellSize && (int) c.getPrevTrig()[1]/cellSize == (int) c.getPositionY()/cellSize )
		{
			same = true;
		}
		//remove old pointer
		oX = oX/cellSize;
		oY = oY/cellSize;
		int x = (int) oX;
		int y = (int) oY;	
		//grid[x][y].addCell(i);
		grid[x][y].remove(c.getUnique());
		
		
		
		//add new
		
		x = this.genXPos(c);
		y = this.genYPos(c);	
		//grid[x][y].addCell(i);
		grid[x][y].addCell(i);
		
		//grid[(int) (c.getPositionX()/cellSize)][(int) (c.getPositionY()/cellSize)].addCell(i);
		
		if(same)
		{
			getHeat()[(int) (c.getPositionX()/cellSize)][(int) (c.getPositionY()/cellSize)]++;
		}else
		{
			getHeat()[(int) (c.getPositionX()/cellSize)][(int) (c.getPositionY()/cellSize)]++;
		}
		
	}

	public void addCellPosition(Cell c, int i) 
	{
		int x = this.genXPos(c);
		int y = this.genYPos(c);	
		//grid[x][y].addCell(i);
		
		//System.out.println(grid.length+" "+x+" "+y);
		grid[x][y].addCell(i);
		
		//getHeat()[(int) (c.getPositionX()/cellSize)][(int) (c.getPositionY()/cellSize)]++;
	}

	

	

}
