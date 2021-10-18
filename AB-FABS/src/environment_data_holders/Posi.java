package environment_data_holders;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import cycle_components.StaticCalculations;
import cell_data_holders.Cell;
import singleton_holders.SingletonHolder;

public class Posi{
	
	protected GCell[][] grid = null;
	protected double dimensions = 0;
	protected double cellSize = 0;
	
	protected int[][] heat = new int[100][100];
	protected int[][] heatTemp = new int[100][100];

	private ArrayList<double[]> zoneList = new ArrayList<double[]>();

	public ArrayList<Integer> getPossibleOverlap(Cell c, int i, ArrayList<Cell> arrayList) 
	{
		int x = this.genXPos(c);
		int y = this.genYPos(c);
		ArrayList<Integer> possibles = new ArrayList<Integer>();
		int z = 1;
		//for(int l = -6; l < 7; l++)
		for(int l = -z; l < z+1; l++)
		{
			//for(int m = -6; m < 7; m++)
			for(int m = -z; m < z+1; m++)
			{
				int xl = x+l;
				int ym =y+m;
				if(xl <0)
				{
					xl = grid.length-1;
				}else
				{
					if(xl >=grid.length)
					{
						xl = 0;
					}
				}
				if(ym <0)
				{
					ym = grid[xl].length-1;
				}else
				{
					if(ym >=grid[xl].length)
					{
						ym = 0;
					}
				}
				//System.out.println(+xl+" "+ym+" "+positionGrid.length+" "+positionGrid[xl].length+" "+positionGrid[xl][ym].size());
				ArrayList<Integer> taboo = new ArrayList<Integer>();
				GCell g = grid[xl][ym];
				for(int n = 0; n < g.getSize(); n++)
				{
					int o = g.getCell(n);
					//System.out.println("adding "+n+"at "+o+" size "+arrayList.size()+" cell  "+xl+" "+ym);
					if(arrayList.get(o).isCorporeal())
					{
						int a = arrayList.get(o).getOverlapLast();
						//a = 0;
						double b = SingletonHolder.getJumpCounter()-(arrayList.get(o).getCellSize()/arrayList.get(o).getSpeed())-2;
						//b = SingletonHolder.getJumpCounter()-3;
						//System.out.println(a+" "+b);
						if( o !=i && a < b)
						{
							//System.out.println(a+" "+b);
							//System.exit(0);
							boolean clean = true;
							for(int p = 0; p < taboo.size();p++)
							{
								if(taboo.get(p) == o)
								{
									clean = false;
								}
							}
							//System.out.println("adding: "+o+" ");
							if(clean)
							{
								possibles.add(o);
								taboo.add(o);
							}
						}
					}
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
		grid[(int) (oX)][(int) (oY)].remove(i); 
		int x = (int) oX;
		int y = (int) oY;	
		//grid[x][y].addCell(i);
		if(cellSize<c.getCellSize())
		{
			double ratio = c.getCellSize()/cellSize;
			int count = 0;
			for(int a = (int) (x-(ratio)); a <= x+ratio+1;a++)
			{
				for(int b = (int) (y-(ratio)); b <= y+ratio+1;b++)
				{
					int l = a;
					int m = b;
					if(a > grid.length-1)
					{
						l = 0;
					}else
					{
						if(a < 0)
						{
							l = grid.length-1;
						}
					}
					
					if(b > grid.length-1)
					{
						m = 0;
					}else
					{
						if(b < 0)
						{
							m = grid.length-1;
						}
					}

					grid[l][m].remove(i);
					//System.out.println("removed "+i+" from "+l+", "+m);
					count ++;
				}
			}
		}else
		{
			grid[x][y].remove(i);
		}
		
		//add new
		
		x = this.genXPos(c);
		y = this.genYPos(c);	
		//grid[x][y].addCell(i);
		if(cellSize<c.getCellSize())
		{
			double ratio = c.getCellSize()/cellSize;
			//System.out.println("ratio "+ratio+" "+x+" "+y);
			ratio = ratio /2;
			int count = 0;
			for(int a = (int) (x-(ratio)); a <= x+ratio+1;a++)
			{
				for(int b = (int) (y-(ratio)); b <= y+ratio+1;b++)
				{
					int l = a;
					int m = b;
					if(a > grid.length-1)
					{
						l = 0;
					}else
					{
						if(a < 0)
						{
							l = grid.length-1;
						}
					}
					
					if(b > grid.length-1)
					{
						m = 0;
					}else
					{
						if(b < 0)
						{
							m = grid.length-1;
						}
					}

					//System.out.println("pop "+l+" "+m+" "+count);
					grid[l][m].addCell(i);
					//System.out.println("added "+i+" to "+l+", "+m);
					count++;
				}
			}
		}else
		{
			grid[x][y].addCell(i);
		}
		//grid[(int) (c.getPositionX()/cellSize)][(int) (c.getPositionY()/cellSize)].addCell(i);
		
		if(same)
		{
			getHeat()[(int) (c.getPositionX()/cellSize)][(int) (c.getPositionY()/cellSize)]++;
			getHeatTemp()[(int) (c.getPositionX()/cellSize)][(int) (c.getPositionY()/cellSize)]++;
		}else
		{
			getHeat()[(int) (c.getPositionX()/cellSize)][(int) (c.getPositionY()/cellSize)]++;
			getHeatTemp()[(int) (c.getPositionX()/cellSize)][(int) (c.getPositionY()/cellSize)]++;
		}
		grid[x][y].addAbs(c.getVect());
	}

	//zone size, attraction strength, attraction falloff, number of zones, size varience, strength varience
	public void genAttractZones()
	{
		double size = SingletonHolder.getAttractSize();
		double strength = SingletonHolder.getAttractStrength();
		double falloff = SingletonHolder.getAttractFalloff();
		double zones = SingletonHolder.getAttractZones();
		double sizeVar = SingletonHolder.getAttractSizeVar();
		double strengthVar = SingletonHolder.getAttractStrengthVar();
		Random rand = SingletonHolder.getMasterRandom();
		for(int i = 0; i < zones;i++)
		{
			int x = rand.nextInt(grid.length);
			int y = rand.nextInt(grid.length);
			double siz = (rand.nextDouble()*sizeVar*2)+(size-sizeVar);
			double stren = (rand.nextDouble()*strengthVar*2)+(strength-strengthVar);
			getZoneList().add(new double[]{x,y,siz,stren,falloff});
			
		}
	}
	public void applyZones() {
		for(int i = 0; i < getZoneList().size();i++)
		{
			double[] z = getZoneList().get(i);
			int x = (int) z[0];
			int y = (int) z[1];
			double siz = z[2];
			double stren = z[3];
			double falloff = z[4];
			for(int x2 = (int) (x-siz/2);x2<x+siz/2;x2++)
			{
				for(int y2 = (int) (y-siz/2);y2<y+siz/2;y2++)
				{
					if(x2 >0 && x2 < grid.length && y2 >0 &&y2 < grid.length)
					{
						GCell g = grid[x2][(int) y2];
						int r = (int) (StaticCalculations.getEuclidean(x2, y2, x, y)/cellSize);
						g.addAttract(x*cellSize,y*cellSize,stren,r,falloff);
					}
				}
			}
			/*for(int r = 0; r < siz;r++)
			{
				for(int l = 0; l < siz;l++)
				{
					int xA =  (x+r+l);
					int xB =  (x-r+l);
					int yA =  (y+r+l);
					int yB =  (y-r+l);
					if(xA >0 && xA < grid.length&& y >0 && y < grid.length)
					{
						GCell g = grid[xA][(int) y];
						g.addAttract(x*cellSize,y*cellSize,stren,r,falloff);
					}
					if(xB >0 && xB < grid.length&& y >0 && y < grid.length)
					{
						GCell g = grid[xB][y];
						g.addAttract(x*cellSize,y*cellSize,stren,r,falloff);
					}
					if(yA >0 && yA < grid.length&& x >0 && x < grid.length)
					{
						GCell g = grid[x][yA];
						g.addAttract(x*cellSize,y*cellSize,stren,r,falloff);
					}
					if(yB >0 && yB < grid.length&& x >0 && x < grid.length)
					{
						GCell g = grid[x][yB];
						g.addAttract(x*cellSize,y*cellSize,stren,r,falloff);
					}
					if(yB >0 && yB < grid.length && xA >0 && xA < grid.length)
					{
						GCell g = grid[xA][yB];
						g.addAttract(x*cellSize,y*cellSize,stren,r,falloff);
					}
					if(yB >0 && yB < grid.length && xB >0 && xB < grid.length)
					{
						GCell g = grid[xB][yB];
						g.addAttract(x*cellSize,y*cellSize,stren,r,falloff);
					}
					if(yA >0 && yA < grid.length && xA >0 && xA < grid.length)
					{
						GCell g = grid[xA][yA];
						g.addAttract(x*cellSize,y*cellSize,stren,r,falloff);
					}
					if(yA >0 && yA < grid.length && xB >0 && xB < grid.length)
					{
						GCell g = grid[xB][yA];
						g.addAttract(x*cellSize,y*cellSize,stren,r,falloff);
					}
				}
						
			}*/
		}
		
	}
	
	public void removeZones() {
		for(int i = 0; i < getZoneList().size();i++)
		{
			double[] z = getZoneList().get(i);
			int x = (int) z[0];
			int y = (int) z[1];
			double siz = z[2];
			for(int x2 = (int) (x-siz/2);x2<x+siz/2;x2++)
			{
				for(int y2 = (int) (y-siz/2);y2<y+siz/2;y2++)
				{
					if(x2 >0 && x2 < grid.length && y2 >0 &&y2 < grid.length)
					{
						GCell g = grid[x2][(int) y2];
						g.setAttractionStrength(0.00);
					}
				}
			}
			
		}
		
	}
	//zone size, attraction strength, attraction falloff, number of zones, size varience, strength varience
	public void genDeflectZones()
	{
		double size = SingletonHolder.getDeflectSize();
		double strength = SingletonHolder.getDeflectStrength();
		double falloff = SingletonHolder.getDeflectFalloff();
		double zones = SingletonHolder.getDeflectZones();
		double sizeVar = SingletonHolder.getDeflectSizeVar();
		double strengthVar = SingletonHolder.getDeflectStrengthVar();
		Random rand = SingletonHolder.getMasterRandom();
		for(int i = 0; i < zones;i++)
		{
			int x = rand.nextInt(grid.length);
			int y = rand.nextInt(grid.length);
			double siz = (rand.nextDouble()*sizeVar*2)+(size-sizeVar);
			double stren = (rand.nextDouble()*strengthVar*2)+(strength-strengthVar);

			getZoneList().add(new double[]{x,y,siz,-stren,falloff});
		}
	}
	
	public void addCellPosition(Cell c, int i) 
	{
		int x = this.genXPos(c);
		int y = this.genYPos(c);	
		//grid[x][y].addCell(i);
		if(cellSize<c.getCellSize())
		{
			double ratio = c.getCellSize()/cellSize;
			for(int a = (int) (x-(ratio)); a <= x+ratio+1;a++)
			{
				for(int b = (int) (y-(ratio)); b <= y+ratio+1;b++)
				{
					int l = a;
					int m = b;
					if(a > grid.length-1)
					{
						l = 0;
					}else
					{
						if(a < 0)
						{
							l = grid.length-1;
						}
					}
					
					if(b > grid.length-1)
					{
						m = 0;
					}else
					{
						if(b < 0)
						{
							m = grid.length-1;
						}
					}

					grid[l][m].addCell(i);
				}
			}
		}else
		{
			//System.out.println(grid.length+" "+x+" "+y);
			grid[x][y].addCell(i);
		}
		//getHeat()[(int) (c.getPositionX()/cellSize)][(int) (c.getPositionY()/cellSize)]++;
	}

	protected int genXPos(Cell c) 
	{
		int x = (int) (c.getPositionX()/cellSize);
		return x;
	}

	protected int genYPos(Cell c) 
	{
		int y = (int) (c.getPositionY()/cellSize);
		return y;
	}

	public void fullReport(Cell[] cells) 
	{
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid[x].length; y++)
			{
				System.out.println(x+" of "+grid.length+" "+y+" of "+grid[x].length+"reporting");
				grid[x][y].reportContents(cells);
			}
		}
		
	}

	public int[][] getHeat() {
		return heat;
	}
	
	public int[][] getAngleHeat(int i) {
		int[][] gridh = new int [grid.length][grid[0].length];
		for(int x = 0; x < gridh.length;x++)
		{
			for(int y = 0; y < gridh.length;y++)
			{
				gridh[x][y] = grid[x][y].getRelativeAngle()[i];
			}
		}
		return gridh;
	}
	
	public int[][][] getAllAngleHeat() {
		int[][][] gridh = new int [grid.length][grid[0].length][];
		for(int x = 0; x < gridh.length;x++)
		{
			for(int y = 0; y < gridh.length;y++)
			{
				
				gridh[x][y] = grid[x][y].getRelativeAngle();
				
			}
		}
		return gridh;
	}
	
	public int[][][] getDirectionHeat() {
		int[][][] gridh = new int [grid.length][grid[0].length][];
		for(int x = 0; x < gridh.length;x++)
		{
			for(int y = 0; y < gridh[0].length;y++)
			{
				gridh[x][y] = grid[x][y].getDirections();
			}
		}
		return gridh;
	}
	
	public int[][][] getDirectionConstantHeat() {
		int[][][] gridh = new int [grid.length][grid[0].length][];
		for(int x = 0; x < gridh.length;x++)
		{
			for(int y = 0; y < gridh[0].length;y++)
			{
				gridh[x][y] = grid[x][y].getConstantDirections();
			}
		}
		return gridh;
	}
	
	public int[][][] getRelativeHeat() {
		int[][][] gridh = new int [grid.length][grid[0].length][];
		for(int x = 0; x < gridh.length;x++)
		{
			for(int y = 0; y < gridh[0].length;y++)
			{
				
				gridh[x][y] = grid[x][y].getRelativeAngle();
				/*for(int i = 0; i < grid[x][y].getRelativeAngle().length;i++ )
				{
					if (grid[x][y].getRelativeAngle()[i] != 0)
					{
						System.out.println("valid here");
					}
				}*/
			}
		}
		return gridh;
	}

	public void setHeat(int[][] heat) {
		this.heat = heat;
	}

	public void cleanHeat()
	{
		setHeat(new int[(int) ((dimensions/cellSize)+1)][(int) ((dimensions/cellSize)+1)]);
	}

	public GCell getGCell(double x, double y)
	{
		//System.out.println("cell search: "+x+", "+y);
		return grid[(int) (x/cellSize)][(int) (y/cellSize)];
	}
	
	/*public GCell getGCell(int x, int y)
	{
		//System.out.println("cell search: "+x+", "+y);
		return grid[x][y];
	}*/
	
	//could add static distance calculations to slightly reduce real time overhead at the cost of flexibility
	public void setCurve(double[][] ds,int point,int size, int curveRad, double str, boolean norm, double var) 
	{
		curveRad = curveRad/2;
		//System.out.println(ds.length);
		double previousX = 0;
		double previousY = 0;
		for(int i = 0; i < ds.length; i++)
		{
			boolean zeroPoint = false;
			//20% chance to not count
			if(zeroPoint && SingletonHolder.getMasterRandom().nextInt(101) < 50)
			{
			}else
			{
				int x = (int) (ds[i][0]/cellSize);
				int y = (int) (ds[i][1]/cellSize);
				//System.out.println("added  "+x+" "+y);
				//System.out.println("raw  "+ds[i][0]+" "+ds[i][1]);
				//System.out.println(grid.length);
				
				if(x >=0 && y >=0 && x <grid.length && y < grid.length)
				{
					CurveIndicator c = grid[x][y].getCurves();
					for(int x2 = x-curveRad; x2 <= x+curveRad; x2++ )
					{
						for(int y2 = y-curveRad; y2 <= y+curveRad; y2++ )
						{
							if(x2>=0 && x2<grid.length && y2 >= 0 && y2 < grid.length)
							{
								//System.out.println("added "+x2+" "+y2+" "+x+" "+y);
								ArrayList<double[]> ey = grid[x2][y2].getNearbyPointHolder();
								ey.add(new double[]{ds[i][0],ds[i][1]});
								grid[x2][y2].setNearbyPointHolder(ey);
								grid[x2][y2].setBezStrength(figureBezStr(str,norm,var));
							}
						}
					}
					//System.exit(0);
					//was blocking multi passes so first posi goes in not second
					/*if(c.doesntInclude(point))
					{
						c.getCurves().add(point);
						c.getEastPoints().add(new double[]{size,size});
						if(i > 0)
						{
							int x2 = (int) (ds[i-1][0]/cellSize);
							int y2 = (int) (ds[i-1][1]/cellSize);
							CurveIndicator c2 = grid[x2][y2].getCurves();
							c2.getEastPoints().set(c2.getPositionOfCurve(point), ds[i]);
						}
						if(i >0 && i < ds.length-1)
						{
							c.getNearestPoints().add(ds[i]);
							c.getWestPoints().add(new double[]{previousX,previousY});
						}else
						{
							if(i == 0)
							{
								c.getNearestPoints().add(ds[i]);
								c.getWestPoints().add(new double[]{0,0});
							}else
							{
								c.getNearestPoints().add(ds[i]);
								c.getWestPoints().add(new double[]{previousX,previousY});
							}
						}
						previousX = ds[i][0];
						previousY = ds[i][1];
					}*/
					
					if(c.doesntInclude(point))
					{
						c.getCurves().add(point);;
						if(i > 0)
						{
							c.getEastPoints().add(ds[i-1]);
							c.getNearestPoints().add(ds[i-1]);
						}else
						{
							c.getEastPoints().add(new double[] {size,size});
							c.getNearestPoints().add(new double[] {size,size});
						}
						if(i <ds.length-1)
						{
							c.getWestPoints().add(ds[i+1]);
							c.getNearestPoints().add(ds[i+1]);
							
						}else
						{
							c.getWestPoints().add(new double[] {size,size});
							c.getNearestPoints().add(new double[] {size,size});
						}
						
					}
					/*for(int m = 1 ; m < c.getEastPoints().size();m++)
					{
						if(c.getEastPoints().get(m-1)[0] == c.getEastPoints().get(m)[0] && c.getEastPoints().get(m-1)[1] == c.getEastPoints().get(m)[1])
						{
							System.out.println("DUPLICITY EAST");
						}
					}
					
					for(int m = 1 ; m < c.getWestPoints().size();m++)
					{
						if(c.getWestPoints().get(m-1)[0] == c.getWestPoints().get(m)[0] && c.getWestPoints().get(m-1)[1] == c.getWestPoints().get(m)[1])
						{
							System.out.println("DUPLICITY EAST");
						}
					}*/
					grid[x][y].setCurves(c);
					//c.setFindSetNearest();
				}
				
			}	
		}
		//System.out.println(counter);
	}
	
	public void setOldCurve(ArrayList<GCell> allPoints) {
		
		for(int i = 0; i < allPoints.size();i++)
		{
			grid[allPoints.get(i).getX()][allPoints.get(i).getY()].copyBez(allPoints.get(i));
		}
	}

	private double figureBezStr(double str, boolean norm, double var) {
		Random rand = SingletonHolder.getMasterRandom();
		double d = rand.nextGaussian()*0.5;
		do 
		{
			d = rand.nextGaussian()*0.5;
		}while(d > 1 || d < -1);
		double sel = 0.00;
		double add = var*d;
		/*if(rand.nextBoolean())
		{
			sel = str+add;
		}else
		{
			sel = str-add;
		}*/
		if(norm)
		{
			sel = str+add;
		}else
		{
			int ran = rand.nextInt((int) (var*100+1));
			double ran2 = ran/100;
			if(rand.nextBoolean())
			{
				sel = str+ran2;
			}else
			{
				sel = str-ran2;
			}
		}
		if(sel < 0.00)
		{
			sel = 0.00;
		}
		//System.out.println("sel = "+sel);
		return sel;
	}

	public ArrayList<GCell> getCurveCellStrengths(double[][] ds,int curveRad) {
		ArrayList<GCell> str = new ArrayList<GCell>(); 
		//System.out.println("bez c a a a");
		for(int i = 0; i < ds.length; i++)
		{
			//System.out.println("bez c a a b");
			int x = (int) (ds[i][0]/cellSize);
			int y = (int) (ds[i][1]/cellSize);
			//System.out.println("added  "+x+" "+y);
			//System.out.println("raw  "+ds[i][0]+" "+ds[i][1]);
			//System.out.println(grid.length);
			if(x >=0 && y >=0 && x <grid.length && y < grid.length)
			{
				//System.out.println("bez c a a c");
				for(int x2 = x-curveRad; x2 <= x+curveRad; x2++ )
				{
					//System.out.println("bez c a a d");
					for(int y2 = y-curveRad; y2 <= y+curveRad; y2++ )
					{
						//System.out.println("bez c a a e");
						if(x2>=0 && x2<grid.length && y2 >= 0 && y2 < grid.length)
						{
							//System.out.println("bez c a a f");
							str.add(grid[x2][y2].copy());
						}
					}
				}
			}
		}
		return str;
	}
	
	public ArrayList<ArrayList<ArrayList<double[]>>> getCurveCellStrands(ArrayList<double[][]> points, int rad) {
		rad++;
		rad++;
		rad++;
		rad++;
		SingletonHolder.setBoxHolder(new ArrayList<ArrayList<double[]>>());
		int iterator = 2;
		System.out.println("1,1 within 0,0 2,0 0,2 2,2? "+this.isWithinPoly(1, 1, 0, 0, 2, 0, 0, 2, 2, 2));
		//[curve][strand][point][x,y,str]]
		//use aList for filtering in next step and solidify later
		//[curve][strand[x,y,str]
		ArrayList<ArrayList<ArrayList<double[]>>> god = new ArrayList<ArrayList<ArrayList<double[]>>>();
		for(int l = 0; l < points.size();l++)
		{
			double[][] ds = points.get(l);
			ArrayList<ArrayList<double[]>> curveGod = new ArrayList<ArrayList<double[]>>();
			for(int i = 0; i < (rad)*2+1; i++)
			{
				ArrayList<double[]> strandGod = new ArrayList<double[]>();
				curveGod.add(strandGod);
			}
			int jump = 10;
			for(int i = 0; i < ds.length-jump; i++)
			{
				/*int inc = 0;
				double dist = 0;
				do
				{
					inc ++;
					dist = StaticCalculations.getEuclidean(ds[i][0], ds[i][1], ds[i+inc][0], ds[i+inc][1]);
				}while(dist <75 && i+inc < ds.length);
				jump = inc;*/				//create the boxes
				//double[][] boxes = new double[rad*2-2][9];
				//double angleOfTheLine = Math.toDegrees(Math.atan2(ds[i][0]-ds[i+jump][0], ds[i][1]-ds[i+jump][1]));
				double angleOfTheLine = Math.toDegrees(Math.atan2(ds[i][1]-ds[i+jump][1], ds[i][0]-ds[i+jump][0]));
				//double turnDir = angleOfTheLine;
				//double ninty = Math.toRadians(90.00);
				//angleOfTheLine = angleOfTheLine/2;
				angleOfTheLine+=90;
				angleOfTheLine = StaticCalculations.counterClockwiseClean(angleOfTheLine);
				angleOfTheLine = Math.toRadians(angleOfTheLine);
				double[][][] bottom = new double[2][rad+2][2];
				double[][][] minBottom = new double[2][rad+2][2];
				for(int a = 0; a <= rad+1; a++)
				{
					
					//create + box and - box
					//bottom cords for this point
					double x = ds[i][0];
					double y = ds[i][1];
					//double angleOfTheLine = Math.toRadians(angleOfTheLine);
					//double angleOfTheLine = Math.toDegrees(angleOfTheLine);
					x = x + Math.cos(angleOfTheLine)*a;
					y = y + Math.sin(angleOfTheLine)*a;
					bottom[0][a][0] = x;
					bottom[0][a][1] = y;
					
					x = 0.00;
					y = 0.00;
					x = ds[i][0];
					y = ds[i][1];
					//angleOfTheLine = Math.toRadians(angleOfTheLine);
					//angleOfTheLine = Math.toDegrees(angleOfTheLine);
					x = x - Math.cos(angleOfTheLine)*a;
					y = y - Math.sin(angleOfTheLine)*a;
					minBottom[0][a][0] = x;
					minBottom[0][a][1] = y;
					
					//bottom cords for next point
					x = 0.00;
					y = 0.00;
					x = ds[i+jump][0];
					y = ds[i+jump][1];
					//angleOfTheLine = Math.toRadians(angleOfTheLine);
					//angleOfTheLine = Math.toDegrees(angleOfTheLine);
					x = x + Math.cos(angleOfTheLine)*a;
					y = y + Math.sin(angleOfTheLine)*a;
					bottom[1][a][0] = x;
					bottom[1][a][1] = y;
					
					x = 0.00;
					y = 0.00;
					x = ds[i+jump][0];
					y = ds[i+jump][1];
					//angleOfTheLine = Math.toRadians(angleOfTheLine);
					//angleOfTheLine = Math.toDegrees(angleOfTheLine);
					x = x - Math.cos(angleOfTheLine)*a;
					y = y - Math.sin(angleOfTheLine)*a;
					minBottom[1][a][0] = x;
					minBottom[1][a][1] = y;
					
				}
				angleOfTheLine = Math.toDegrees(angleOfTheLine);
				//total num of points = bottom size + minbottom size
				//create a total points array
				double[][] allPointsTop = new double[rad*2+1+iterator][2];
				double[][] allPointsBot = new double[rad*2+1+iterator][2];
				int pointer = 0;
				for(int a = minBottom[0].length-1; a >= 0; a--)
				{
					allPointsBot[pointer] = minBottom[0][a];
					pointer++;
				}
				for(int a = 1; a < bottom[0].length; a++)
				{
					allPointsBot[pointer] = bottom[0][a];
					pointer++;
				}
				pointer = 0;
				for(int a = minBottom[1].length-1; a >= 0; a--)
				{
					allPointsTop[pointer] = minBottom[1][a];
					pointer++;
				}
				for(int a = 1; a < bottom[1].length; a++)
				{
					allPointsTop[pointer] = bottom[1][a];
					pointer++;
				}
				ArrayList<double[]> newBoxes = new ArrayList<double[]>();
				/*//if total is divisable by 3
				double divThree = allPointsTop.length/iterator;
				boolean inter = false;
				int round  = 0;
				int middle = allPointsTop.length/2;
				//needs to be radius *2 +1
				boolean middled = false;
				pointer = 0;
				do
				{
					if ((divThree == Math.floor(divThree)) && !Double.isInfinite(divThree)) {
						// integer type
						inter = false;
						round ++;
						divThree = (allPointsTop.length+round)/2.00;
					}else
					{

						inter = true;
					}
				}while(inter == false);
				//else iterate till divisible by 3 and middle = remainder, if 1 extra middle is size +1 if 2 extra middle is +2
				//iteration = 2
				//do while iteration < points.size
				do{
					//if i + iteration < middle its normal so just
					if(pointer + iterator < middle || middled == true)
					{
						//ix,iy,i+iterationx,x+iteration,y
						double[] box = new double[]{allPointsTop[pointer][0],allPointsTop[pointer][1],allPointsTop[pointer+iterator][0],allPointsTop[pointer+iterator][1],
													allPointsBot[pointer][0],allPointsBot[pointer][1],allPointsBot[pointer+iterator][0],allPointsBot[pointer+iterator][1],angleOfTheLine};
						newBoxes.add(box);
						pointer+=iterator;
					}else
					{
						//if i + iteration > middle and mid added = false 
						//ix,iy,i+iteration+extrax,i+iteration+extray
						double[] box = new double[]{allPointsTop[pointer][0],allPointsTop[pointer][1],allPointsTop[pointer+iterator+round][0],allPointsTop[pointer+iterator+round][1],
													allPointsBot[pointer][0],allPointsBot[pointer][1],allPointsBot[pointer+iterator+round][0],allPointsBot[pointer+iterator+round][1],angleOfTheLine};
						newBoxes.add(box);
						pointer+=iterator+round;
						middled = true;
						//iteration = pointer + extra
						//midAdded = true
					}
					//min0 = far left px bottom max = far right
				}while(pointer < allPointsTop.length-1);
				*/
				for(int o = 0 ; o < rad*2+1-iterator;o++)
				{
					double[] box = new double[]{allPointsTop[o][0],allPointsTop[o][1],allPointsTop[o+iterator][0],allPointsTop[o+iterator][1],
							allPointsBot[o][0],allPointsBot[o][1],allPointsBot[o+iterator][0],allPointsBot[o+iterator][1],angleOfTheLine};
					newBoxes.add(box);
				}
				/*for(int a = 0; a < rad-1; a++)
				{
					//top left starting with min [1] top [0] bottom and bottom right
					boxes[a] = new double[]{minBottom[1][a][0],minBottom[1][a][1],minBottom[1][a+1][0],minBottom[1][a+1][1],minBottom[0][a][0],minBottom[0][a][1],minBottom[0][a+1][0],minBottom[0][a+1][1],turnDir};
				}
				for(int a = 0; a < rad-1; a++)
				{
					//construct a 4 point polygon object
					boxes[a] = new double[]{bottom[1][a][0],bottom[1][a][1],bottom[1][a+1][0],bottom[1][a+1][1],bottom[0][a][0],bottom[0][a][1],bottom[0][a+1][0],bottom[0][a+1][1],turnDir};
				}*/
				SingletonHolder.getBoxHolder().add(newBoxes);;
				int x = (int) (ds[i][0]/cellSize);
				int y = (int) (ds[i][1]/cellSize);
				if(x >=0 && y >=0 && x <grid.length && y < grid.length)
				{
					for(int x2 = x-rad; x2 <= x+rad; x2++ )
					{
						for(int y2 = y-rad; y2 <= y+rad; y2++ )
						{
							if(x2>=0 && x2<grid.length && y2 >= 0 && y2 < grid.length)
							{
								//if its within the box add it to the 
								if(grid[x2][y2].getBezStrength() != 0)
								{
									boolean looking = true;
									int a = 0;
									do
									{
										double o = grid[x2][y2].getX()*cellSize;
										double p = grid[x2][y2].getY()*cellSize;
										double[] box = newBoxes.get(a);
										if(isWithinPoly(o,p,box[0],box[1],box[2],box[3],box[4],box[5],box[6],box[7]))
										{
											looking = false;
											curveGod.get(a).add(new double[]{o,p,grid[x2][y2].getBezStrength()});
										}
										a++;
									}while(looking && a < newBoxes.size());
								}
							}
						}
					}
				}
			}

			god.add(curveGod);
		}
	
		return god;
	}
	
	private boolean isWithinPoly(double o, double p, double d, double e,
			double f, double g, double h, double i, double j, double k) {
		
		Path2D path = new Path2D.Double();
		//needs to go round like a circuit
		path.moveTo(d, e);
		path.lineTo(f, g);
		path.lineTo(j, k);
		path.lineTo(h, i);
		path.lineTo(d, e);
		path.closePath();
		boolean inside = false;
		if(path.contains(o, p))
		{
			inside = true;
		}

		//System.out.println(""+o+","+p+" within "+d+","+e+" "+f+","+g+" "+h+","+i+" "+j+","+k+"?"+inside);
		return inside;
	}

	private boolean isWithinBox(double x, double y, double bLX, double bLY, double bRX, double bRY) {
		boolean within = false;
		if( bLX <= x && x <= bRX && bLY <= y && y <= bRY ) {
		    // Point is in bounding box
			//bb is the bounding box, (ix,iy) are its top-left coordinates, and (ax,ay) its bottom-right coordinates.  p is the point and (x,y) its coordinates.
			within = true;
		}
		return within;
	}

	
	
	public void resetBez() {
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid.length; y++)
			{
				grid[x][y].resetCurves();
			}
		}
		
	}

	

	public GCell[][] gridOfCopies(GCell[][] grid2) {
		GCell[][]copy = new GCell[grid2.length][grid2[0].length];
		for(int x = 0 ; x < grid2.length; x++)
		{
			for(int y = 0; y < grid2[x].length; y++)
			{
				copy[x][y] = grid2[x][y].copy();
			}
		}
		return copy;
	}

	protected void setCellSize(double cellSize2) {
		cellSize = cellSize2;
		
	}

	protected void setDimensions(double dimensions2) {
		dimensions = dimensions2;
		
	}

	public GCell[][] getGrid() {
		return grid;
		
	}
	
	protected void setGrid(GCell[][] grid2) {
		grid = grid2;
		
	}

	public double getCellSize() {
		// TODO Auto-generated method stub
		return cellSize;
	}

	public void addAllCellPositions(ArrayList<Cell> cells) {
		for(int i = 0; i < cells.size();i++)
		{
			this.addCellPosition(cells.get(i), i);
		}
		
	}

	public double[] getHeatByCell(Cell c) 
	{
		int x = (int) (c.getPositionX()/cellSize);
		int y = (int) (c.getPositionY()/cellSize);
		int hea = heat[x][y];
		return new double[]{x,y,hea};
	}

	public int getHeatXY(int x, int y) {
		if(x > heat.length )
		{
			x = 0;
		}else
		{
			if(x < 0)
			{
				x = heat.length-1;
			}
		}
		if(y > heat.length )
		{
			y = 0;
		}else
		{
			if(y < 0)
			{
				y = heat.length-1;
			}
		}
		return heat[x][y];
	}

	public Posi copy() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<double[]> getZoneList() {
		return zoneList;
	}

	public void setZoneList(ArrayList<double[]> zoneList) {
		this.zoneList = zoneList;
	}

	public void resetZones() {
		removeZones();
		zoneList = new ArrayList<double[]>();
		
	}

	public void resetPop() {
		// TODO Auto-generated method stub
		
	}

	public int[][] getHeatTemp() {
		return heatTemp;
	}

	public void setHeatTemp(int[][] heatTemp) {
		this.heatTemp = heatTemp;
	}

	public void cleanHeatTemp() {

		setHeatTemp(new int[(int) ((dimensions/cellSize)+1)][(int) ((dimensions/cellSize)+1)]);
	}

	

	

	

	

	

	

	

	

}
