package environment_data_holders;

public class PositionGrid extends Posi{
	
	

	public PositionGrid(double dimension, double cSize) 
	{
		dimensions = dimension;
		cellSize = cSize;
		//System.out.println("resetting"+dimension+" "+cSize);
		grid = new GCell[(int) ((dimension/cellSize)+1)][(int) ((dimension/cellSize)+1)];
		setHeat(new int[(int) ((dimension/cellSize)+1)][(int) ((dimension/cellSize)+1)]);
		setHeatTemp(new int[(int) ((dimension/cellSize)+1)][(int) ((dimension/cellSize)+1)]);
		for(int x = 0; x <grid.length;x++)
		   {
			   for(int y = 0; y < grid[x].length;y++)
			   {
				   grid[x][y] = new GCell(x,y);
				   heat[x][y] = 0;
			   }
		   }
	}

	public PositionGrid(int dimension, int cSize, int[][] heat2) {
		dimensions = dimension;
		cellSize = cSize;
		grid = new GCell[(int) ((dimension/cellSize)+1)][(int) ((dimension/cellSize)+1)];
		heat = heat2;
		heatTemp = heat2;
		for(int x = 0; x <grid.length;x++)
		   {
			   for(int y = 0; y < grid[x].length;y++)
			   {
				   grid[x][y] = new GCell(x,y);
			   }
		   }
	}

	
	public PositionGrid copy() {
		PositionGrid pos = new PositionGrid(dimensions, cellSize*2);
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
		int[][] heatTempCopy = new int[heatTemp.length][heatTemp.length];
		for(int x = 0 ; x < heatTemp.length; x++)
		{
			for(int y = 0; y < heatTemp[x].length; y++)
			{
				heatTempCopy[x][y] = heatTemp[x][y];
			}
		}
		pos.setHeatTemp(heatTempCopy);
		return pos;
	}
}