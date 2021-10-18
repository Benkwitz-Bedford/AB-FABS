package stat_data_holders;

import heatmaps.HeatMap;

import java.util.ArrayList;
import java.util.Arrays;

import cycle_components.StaticCalculations;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import cell_data_holders.Cell;
import cell_data_holders.CellDataPackage;
import environment_data_holders.GCell;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;

public class SnapShotObfust {
	
	//data:
		String type = "SnapShot";
		
		private ArrayList<String> valueNames = new ArrayList<String>();
		private ArrayList<Double> values =  new ArrayList<Double>();
		
		//image HeatMapPanel
		/*private HeatMap heat = new HeatMap();
		private HeatMap directionHeatImg = new HeatMap();
		private HeatMap directionConstantHeatImg = new HeatMap();
		private HeatMap relativeHeatImg = new HeatMap();
		private HeatMap angleHeatImg = new HeatMap();
		private HeatMap popHeat = new HeatMap();*/
		
		
			//run settings
				//array of settings generated from singleton
		private ArrayList<String> settings = null;
		
		//cell data
			//current
		private ArrayList<ArrayList<String[]>> cellData = new ArrayList<ArrayList<String[]>>();
				//cell unique Id
				//distance travelled
				//collisions
				//turns
				//times chosen bez
				//times replicated
			//deaths since last
				//cell unique Id
		private ArrayList<Integer> deathIds = new ArrayList<Integer>(); 
				//cell tod
		private ArrayList<Integer> tods = new ArrayList<Integer>(); 
			//births since last
				//cell unique Id
		private ArrayList<Integer> birthIds = new ArrayList<Integer>(); 
				//cell tob
		private ArrayList<Integer> tobs = new ArrayList<Integer>(); 
		
		//turn metrics
		//meta data
		//totals and averages (averages is at each snapshot floating average will be harder and added at a later time but do-able)
			//distance travelled
		
		private ArrayList<ArrayList<Double[]>> cellPositions = new ArrayList<ArrayList<Double[]>>();
		
		
		private int[][] popSectors; 
		private int[][] positions;

		private int[][][] directionHeat;

		private int[][][] directionConstantHeat;

		private int[][][] relativeHeat;

		private int[][][] angleHeat; 
		
		public void populateNamesList()
		{
			//when adding change d shuffle size in DataShuffler
			valueNames = new ArrayList<String>();
			
					//deaths
			valueNames.add("Total Deaths");
					//births
			valueNames.add("Total Births");
					//cell number
			valueNames.add("Active Cells");
					//increments
			valueNames.add("Increments");
					//jumps
			valueNames.add("Jumps");
	
			
			//brown dist
			valueNames.add("Distance from start");
			valueNames.add("Speed");
			
			//clustering metrics
			valueNames.add("Total Nearest");
			valueNames.add("Closest");
			valueNames.add("Furthest");
			
			
		}
		
		//remember to clear births and deaths after each
		public SnapShotObfust(ArrayList<int[]> deaths, ArrayList<int[]> births, ArrayList<Cell> cells, Posi posi, Boolean withGrid)
		{
			if(withGrid)
			{
				withGrid(deaths,births,cells,posi);
			}else
			{
				noGrid(deaths,births,cells,posi);
			}
		}
		
		public void withGrid(ArrayList<int[]> deaths, ArrayList<int[]> births, ArrayList<Cell> cells, Posi posi)
		{
			boolean cleanout = true;
			this.generateCellPositions(cells);
			
			positions = posi.getHeatTemp().clone();
			if(cleanout)
			{
				posi.cleanHeatTemp();
			}
			relativeHeat=posi.getRelativeHeat();
			/*for(int x = 0; x < relativeHeat.length;x++)
			{

				for(int y = 0; y < relativeHeat.length;y++)
				{

					for(int z = 0; z < relativeHeat[0][0].length;z++)
					{
						if(relativeHeat[x][y][z] !=0)
						{
							System.out.println("rela not empty here");
						}
					}
				}
			}*/
			directionHeat = posi.getDirectionHeat();
			
			directionConstantHeat = posi.getDirectionConstantHeat();
			/*for(int x = 0; x < directionConstantHeat.length;x++)
			{

				for(int y = 0; y < directionConstantHeat.length;y++)
				{

					for(int z = 0; z < directionConstantHeat[0][0].length;z++)
					{
						if(directionConstantHeat[x][y][z] !=0)
						{
							System.out.println("constant not empty here");
						}
					}
				}
			}*/
			
			angleHeat=posi.getAllAngleHeat();
			
			values.add((double) SingletonStatStore.getTotalDeaths());
			values.add((double) SingletonStatStore.getTotalBirths());
			values.add(activeCells(deaths,cells));
			values.add((double) SingletonHolder.getIncrement());
			values.add(SingletonHolder.getJumpCounter());
			setSettings(SingletonHolder.generateAllValues());
			
			
			
			
			values.add(this.generateBrownian(cells));
			values.add(this.getSpeed(cells));
			
			//heat = this.generateHeatMap(positions);
			values.addAll(this.generatePopMetrics(posi, cells));
			//popHeat = this.generateHeatMap(getPopSectors());
			int cellValStart = values.size();
			this.populateNamesList();
			
			this.popNamesFromCells(cells);
			this.popValueTotalsFromCells(cells);
			ArrayList<Double> cong = new ArrayList<Double>();
			//collision, 4 +8
			for(int i = 4+cellValStart;i<4+cellValStart+8;i++)
			{
				cong.add(values.get(i));
			}
			values.addAll(this.congSet(cong));
			valueNames.add("Collision Varience");
			valueNames.add("Collision Varience Total");
			cong = new ArrayList<Double>();
			//bounce, 12 +8
			for(int i = 12+cellValStart;i<12+cellValStart+8;i++)
			{
				cong.add(values.get(i));
			}
			values.addAll(this.congSet(cong));
			valueNames.add("Bounce Varience");
			valueNames.add("Bounce Varience Total");
			cong = new ArrayList<Double>();
			//chosen, 20+ 16
			for(int i = 20+cellValStart;i<20+cellValStart+16;i++)
			{
				cong.add(values.get(i));
			}
			values.addAll(this.congSet(cong));
			valueNames.add("Chosen Varience");
			valueNames.add("Chosen Varience Total");
			cong = new ArrayList<Double>();
			//abs, 36 +8
			for(int i = 36+cellValStart;i<36+cellValStart+8;i++)
			{
				cong.add(values.get(i));
			}
			values.addAll(this.congSet(cong));
			valueNames.add("Absolute Varience");
			valueNames.add("Absolute Varience Total");
			cong = new ArrayList<Double>();
			//chosen dist, 44 + 16
			for(int i = 44+cellValStart;i<44+cellValStart+16;i++)
			{
				cong.add(values.get(i));
			}
			values.addAll(this.congSet(cong));
			valueNames.add("Chosen distance Varience");
			valueNames.add("Chosen distance Varience Total");
			cong = new ArrayList<Double>();
			interleaveAverages();
		}
		
		private void noGrid(ArrayList<int[]> deaths, ArrayList<int[]> births, ArrayList<Cell> cells, Posi posi) {
			this.generateCellPositions(cells);
			values.add((double) SingletonStatStore.getTotalDeaths());
			values.add((double) SingletonStatStore.getTotalBirths());
			values.add(activeCells(deaths,cells));
			values.add((double) SingletonHolder.getIncrement());
			values.add(SingletonHolder.getJumpCounter());
			setSettings(SingletonHolder.generateAllValues());
			
			
			
			
			values.add(this.generateBrownian(cells));
			values.add(this.getSpeed(cells));
			values.addAll(this.generatePopMetrics(posi, cells));
			
			//popHeat = this.generateHeatMap(getPopSectors());
			int cellValStart = values.size();
			this.populateNamesList();
			
			this.popNamesFromCells(cells);
			this.popValueTotalsFromCells(cells);
			ArrayList<Double> cong = new ArrayList<Double>();
			//collision, 4 +8
			for(int i = 4+cellValStart;i<4+cellValStart+8;i++)
			{
				cong.add(values.get(i));
			}
			values.addAll(this.congSet(cong));
			valueNames.add("Collision Varience");
			valueNames.add("Collision Varience Total");
			cong = new ArrayList<Double>();
			//bounce, 12 +8
			for(int i = 12+cellValStart;i<12+cellValStart+8;i++)
			{
				cong.add(values.get(i));
			}
			values.addAll(this.congSet(cong));
			valueNames.add("Bounce Varience");
			valueNames.add("Bounce Varience Total");
			cong = new ArrayList<Double>();
			//chosen, 20+ 16
			for(int i = 20+cellValStart;i<20+cellValStart+16;i++)
			{
				cong.add(values.get(i));
			}
			values.addAll(this.congSet(cong));
			valueNames.add("Chosen Varience");
			valueNames.add("Chosen Varience Total");
			cong = new ArrayList<Double>();
			//abs, 36 +8
			for(int i = 36+cellValStart;i<36+cellValStart+8;i++)
			{
				cong.add(values.get(i));
			}
			values.addAll(this.congSet(cong));
			valueNames.add("Absolute Varience");
			valueNames.add("Absolute Varience Total");
			cong = new ArrayList<Double>();
			//chosen dist, 44 + 16
			for(int i = 44+cellValStart;i<44+cellValStart+16;i++)
			{
				cong.add(values.get(i));
			}
			values.addAll(this.congSet(cong));
			valueNames.add("Chosen distance Varience");
			valueNames.add("Chosen distance Varience Total");
			cong = new ArrayList<Double>();
			interleaveAverages();
			
		}

		
		
		private void generateCellPositions(ArrayList<Cell> cells) {
			
			for(int i = 0; i< cells.size();i++)
			{
				ArrayList<Double[]> posi = new ArrayList<Double[]>();
				ArrayList<Double> xPosi = cells.get(i).getxPositionList();
				ArrayList<Double> yPosi = cells.get(i).getyPositionList();
				for(int l = 0; l < xPosi.size();l++)
				{
					posi.add(new Double[] {xPosi.get(l),yPosi.get(l)});
				}
				getCellPositions().add(posi);
			}
			
		}

		private ArrayList<Double> congSet(ArrayList<Double> cong) {
			Double total = 0.00;
			for(int i = 0;i < cong.size();i++)
			{
				total+=cong.get(i);
			}
			Double mean = total/cong.size();
			Double totalVar = 0.00;
			for(int i = 0;i < cong.size();i++)
			{
				Double a = (cong.get(i)-mean)/mean;
				Double b = Math.pow(a, 2);
				Double c = Math.sqrt(b);
				totalVar += c;
			}
			Double congVal = totalVar/cong.size();
			ArrayList<Double> returner = new ArrayList<Double>();
			returner.add(congVal);
			returner.add(totalVar);
			return  returner;
		}

		private Double activeCells(ArrayList<int[]> deaths,
				ArrayList<Cell> cells) {
			Double tot = 0.00;
			//tot+=deaths.size();
			for(int i = 0; i < cells.size();i++)
			{
				if(cells.get(i).isCorporeal())
				{
					tot++;
				}
			}
			return tot;
		}

		private void popValueTotalsFromCells(ArrayList<Cell> cells) {
			ArrayList<Double> vals = new ArrayList<Double>();
			CellDataPackage cd = new CellDataPackage();
			vals.addAll(cd.getData());
			for(int i = 0; i < cells.size();i++)
			{
				for(int l = 0; l < vals.size();l++)
				{
					vals.set(l,vals.get(l)+cells.get(i).getPack().getData().get(l));
				}
			}
			values.addAll(vals);
		}

		private void popNamesFromCells(ArrayList<Cell> cells) {
			CellDataPackage cd = new CellDataPackage();
			ArrayList<String> names = cd.getDataNames();
			valueNames.addAll(names);
		}

		private Double getSpeed(ArrayList<Cell> cells) {
			double tot = 0.0;
			for(int i = 0; i < cells.size();i++)
			{
				Cell c = cells.get(i);
				tot+=c.getSpeed();
			}
			return tot;
		}

		private Double generateBrownian(ArrayList<Cell> cells) {
			//euc distance between div speed div by increments
			//total dist from starts
			double tot = 0.00;
			for(int i = 0; i < cells.size();i++)
			{
				Cell c = cells.get(i);
				ArrayList<double[]> points = c .getBrownianList();
				double[] p = points.get(points.size()-1);
				
				double x = c.getPositionX();
				double y = c.getPositionY();
				
				//x
				if(c.getxCrossed() < 0)
				{
					x=-(SingletonHolder.getSize()-x);
					if(c.getxCrossed()<-1)
					{
						x+=(Math.sqrt(Math.pow((c.getxCrossed()-1),2)))*SingletonHolder.getSize();
					}
				}else
				{
					if(c.getxCrossed()>0)
					{
						x=SingletonHolder.getSize()+x;
						if(c.getxCrossed()>1)
						{
							x+=(Math.sqrt(Math.pow((c.getxCrossed()-1),2)))*SingletonHolder.getSize();
						}
					}
				}
				if(c.getyCrossed() < 0)
				{
					y=-(SingletonHolder.getSize()-y);
					if(c.getyCrossed()<-1)
					{
						y+=(Math.sqrt(Math.pow((c.getyCrossed()-1),2)))*SingletonHolder.getSize();
					}
				}else
				{
					if(c.getyCrossed()>0)
					{
						y=SingletonHolder.getSize()+y;
						if(c.getyCrossed()>1)
						{
							y+=(Math.sqrt(Math.pow((c.getyCrossed()-1),2)))*SingletonHolder.getSize();
						}
					}
				}
				//figure the total distance travelled to a common speed point then divide by increments
				double pTot = StaticCalculations.getEuclidean(p[0], p[1], x, y)/p[2];
				if(SingletonHolder.getIncrement() > 350)
				{
					//System.out.println("pause");
				}
				/*if(points.size()>1)
				{
					for(int l = points.size()-2; l >0;l--)
					{
						p = points.get(0);
						double[] o = points.get(l+1);
						if(p[2] ==0 || p[0] == o[0] && p[1] == o[1])
						{
						}else
						{
							x = o[0];
							y = o[1];
							//x
							if(o[4] < 0)
							{
								x+=o[0];
								if(o[4]<-1)
								{
									x+=(Math.sqrt(Math.pow((o[4]-1),2)))*SingletonHolder.getSize();
								}
							}else
							{
								if(o[4]>0)
								{
									x+=SingletonHolder.getSize()-o[0];
									if(o[4]>1)
									{
										x+=(Math.sqrt(Math.pow((o[4]-1),2)))*SingletonHolder.getSize();
									}
								}
							}
							if(o[5] < 0)
							{
								y+=o[1];
								if(o[5]<-1)
								{
									y+=(Math.sqrt(Math.pow((o[5]-1),2)))*SingletonHolder.getSize();
								}
							}else
							{
								if(o[5]>0)
								{
									y+=SingletonHolder.getSize()-o[1];
									if(o[5]>1)
									{
										y+=(Math.sqrt(Math.pow((o[5]-1),2)))*SingletonHolder.getSize();
									}
								}
							}
							pTot += StaticCalculations.getEuclidean(p[0], p[1], x, y)/p[2]/(o[3]-p[3]);
							
						}
					}
				}*/
				tot+= pTot;
			}
			return tot;
		}

		public void interleaveAverages() {
			ArrayList<String> valNames = new ArrayList<String>();
			ArrayList<Double> vals = new ArrayList<Double>();
			for(int i = 0; i < values.size();i++)
			{
				valNames.add(valueNames.get(i));
				valNames.add("Average "+valueNames.get(i));
				vals.add(values.get(i));
				//div by total cells
				vals.add(values.get(i)/values.get(2));
			}
			values = vals;
			valueNames = valNames;
		}
		
		private ArrayList<Double> generatePopMetrics(Posi posi, ArrayList<Cell> cells) {
			double totalNearest = 0.00;
			double closest = 100000.00;
			double furthest = 0.00;
			if(cells.size()>1)
			{
				for(int i = 0; i < cells.size();i++)
				{
					//System.out.println("pop "+pos.getGrid().length);
					//double close = this.findNearest(i,posi,cells);
					double close = 0;
					if(close < closest)
					{
						closest = close;
					}
					if(close > furthest)
					{
						furthest = close;
					}
					totalNearest+=closest;
				}
			
			}
			int ratio = 10;
			setPopSectors(new int[SingletonHolder.getSize()/ratio+1][SingletonHolder.getSize()/ratio+1]);
			double gSize = posi.getCellSize();
			GCell[][] grid = posi.getGrid();
			for(int x = 0; x < grid.length;x++)
			{
				for(int y = 0; y < grid.length;y++)
				{
					getPopSectors()[(int) (x*gSize/ratio)][(int) (y*gSize/ratio)] += grid[x][y].getCellPositions().size();
				}
			}
			ArrayList<Double> vals = new ArrayList<Double>();
			vals.add(totalNearest);
			vals.add(closest);
			vals.add(furthest);
			return vals;
		}

		private double findNearest(int i, Posi posi, ArrayList<Cell> cells) {
			boolean found = false;
			//System.out.println("cells "+cells.size());
			int easing = 0;
			ArrayList<Cell> finds = new ArrayList<Cell>();
			int gridX = (int) (cells.get(i).getPositionX()/posi.getCellSize());
			int gridY = (int) (cells.get(i).getPositionY()/posi.getCellSize());
			double closest = 100000;
			do
			{
				if(easing < 100)
				{
					for(int x = gridX-easing; x < gridX+easing+1;x++)
					{
						for(int y = gridY-easing; y < gridY+easing+1;y++)
						{
							int x2 = x;
							int y2 = y;
							int p = posi.getGrid().length-1;
							if(x2 < 0)
							{
								x2 = p + x;
							}else
							{
								if(x2 >= p)
								{
									x2 = 0 + x-p;
								}
							}
							if(y2 < 0)
							{
								y2 = p + y;
							}else
							{
								if(y2 > p)
								{
									y2 = 0 + y-p;
								}
							}
							
							
							//System.out.println(x2+" "+y2);
							for(int l = 0; l < posi.getGCell(x2, y2).getCellPositions().size();l++)
							{
								finds.add(cells.get(posi.getGCell(x2, y2).getCellPositions().get(l)));
							}
						}
					}
				}else
				{
					finds.addAll(cells);
				}
				for(int l = 0; l < finds.size(); l++)
				{
					if(finds.get(l).getUnique()==cells.get(i).getUnique())
					{
						finds.remove(l);
						l--;
					}
				}
				if(finds.size()>0)
				{
					for(int l = 0; l < finds.size(); l++)
					{
						double dist = this.getEuclidian(cells.get(i), finds.get(l));
						if(dist< closest)
						{
							closest = dist;
						}
					}
					found= true;
				}
				easing++;
			}while(found == false);
			return closest;
		}
		
		private double getEuclidian(Cell cell, Cell cell2) 
		{
			double x = cell.getPositionX();
			double y = cell.getPositionY();
			double oX = cell2.getPositionX();
			double oY = cell2.getPositionY();
			return Math.sqrt(((Math.pow(x-oX, 2))+(Math.pow(y-oY, 2))));
		}
		

		public HeatMap generateHeatMap(int[][] positions) {
			HeatMap heater = new HeatMap();
			heater.setHeats(positions);
			heater.drawMap(positions,SingletonHolder.getHeatTopper(), 1, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			return heater;
			//System.out.println("....."+getHeat().getPositions().getHeat()[0][0]);
		}
		
		public HeatMap genHeat() {
			HeatMap heater = new HeatMap();
			heater.setHeats(positions);
			heater.drawMap(positions,SingletonHolder.getHeatTopper(), 1, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			return heater;
		}

		public SnapShotObfust()
		{
			type = "aggregate";
		}

		

		public ArrayList<String> getDataReport() {
			ArrayList<String> report =  new ArrayList<String>();
			for(int i = 0; i < values.size();i++)
			{
				report.add(valueNames.get(i)+";"+values.get(i));
			}
			
			report.addAll(settings);
			
			return report;
		}

		public ArrayList<String> getPopReport() {
			ArrayList<String> report =  new ArrayList<String>();
			if(getPopSectors() == null)
			{
				report.add("0");
			}else
			{
				for(int x = 0; x < getPopSectors().length;x++)
				{
					String a = ""+getPopSectors()[x][0];
					StringBuilder sb = new StringBuilder();
					sb.append(a);
					for(int y = 1; y < getPopSectors()[x].length; y++)
					{
						sb.append(":"+getPopSectors()[x][y]);
					}
					a = sb.toString();
					report.add(a);
				}
			}
			return report;
		}

		public ArrayList<String> getHeatReport() {
			ArrayList<String> report =  new ArrayList<String>();
			if(positions == null)
			{
				report.add("0");
			}else
			{
				for(int x = 0; x < positions.length;x++)
				{
					String a = ""+positions[x][0];
					StringBuilder sb = new StringBuilder();
					sb.append(a);
					//String s = sb.toString();
					for(int y = 1; y < positions[x].length; y++)
					{
						sb.append(":"+positions[x][y]);
					}
					a = sb.toString();
					report.add(a);
				}
			}
			return report;
		}
		
		public ArrayList<String> getPosiReport() {
			ArrayList<String> report =  new ArrayList<String>();
			if(cellPositions == null)
			{
				report.add("0");
			}else
			{
				for(int x = 0; x < cellPositions.size();x++)
				{
					if(cellPositions.get(x).size()>0)
					{
						String a = ""+cellPositions.get(x).get(0)[0]+","+cellPositions.get(x).get(0)[1];
						StringBuilder sb = new StringBuilder();
						sb.append(a);
						//String s = sb.toString();
						for(int y = 1; y < cellPositions.get(x).size(); y++)
						{
							sb.append(":"+cellPositions.get(x).get(y)[0]+","+cellPositions.get(x).get(y)[1]);
						}
						a = sb.toString();
						report.add(a);
					}
				}
			}
			return report;
		}
		
		public ArrayList<String> getFastReport(int[][] posi)
		{
			ArrayList<String> report = new ArrayList<String>();
			if(posi == null)
			{
				report.add("0");
			}else
			{
				ArrayList<Integer> chain = new ArrayList<Integer>();
				
				for(int x = 0; x < posi.length;x++)
				{
					String a = "";
					StringBuilder sb = new StringBuilder();
					sb.append(a);
					//String s = sb.toString();
					for(int y = 0; y < posi[x].length; y++)
					{
						int z = posi[x][y];
						if(z !=0)
						{
							if(chain.size() !=0)
							{
								chain.add(z);
							}else
							{
								chain.add(y);
								chain.add(z);
							}
						}else
						{
							if(chain.size()!=0)
							{
								for(int i = 0; i < chain.size();i++)
								{
									sb.append(chain.get(i));
									if(i<chain.size()-1)
									{
										sb.append(":");
									}
								}
								sb.append(",");
								chain = new ArrayList<Integer>();
							}
						}
					}
					a = sb.toString();
					if(a.equals(""))
					{
						a ="l";
					}
					report.add(a);
					chain = new ArrayList<Integer>();
				}
			}
			
			return report;
		}
		public ArrayList<String> getFastReport(int[][][] posi)
		{
			ArrayList<String> report = new ArrayList<String>();
			if(posi == null)
			{
				report.add("0");
			}else
			{
				ArrayList<Integer[]> chain = new ArrayList<Integer[]>();
				
				for(int x = 0; x < posi.length;x++)
				{
					String a = "";
					StringBuilder sb = new StringBuilder();
					sb.append(a);
					//String s = sb.toString();
					for(int y = 0; y < posi[x].length; y++)
					{
						int[] z = posi[x][y];
						int m = 0;
						for(int i = 0; i < z.length;i++)
						{
							m +=z[i]; 
						}
						if(m !=0)
						{
							if(chain.size() !=0)
							{
								chain.add(Arrays.stream(z).boxed().toArray(Integer[]::new));
							}else
							{
								chain.add(new Integer[] {y});
								chain.add(Arrays.stream(z).boxed().toArray(Integer[]::new));
							}
						}else
						{
							if(chain.size()!=0)
							{
								for(int i = 0; i < chain.size();i++)
								{
									for(int l = 0; l < chain.get(i).length;l++)
									{
										sb.append(chain.get(i)[l]);
										if(l<chain.get(i).length-1)
										{
											sb.append("'");
										}
										
									}
									if(i<chain.size()-1)
									{
										sb.append(":");
									}
								}
								sb.append(",");
								chain = new ArrayList<Integer[]>();
							}
						}
					}
					a = sb.toString();
					if(a.equals(""))
					{
						a ="l";
					}
					//a.replaceAll("$:", ":");
					//a.replaceAll("$,", ",");
					report.add(a);
				}
			}
			
			return report;
		}
		
		public ArrayList<String> getDirectionalHeatReport() {
			ArrayList<String> report =  new ArrayList<String>();
			if(directionHeat == null)
			{
				report.add("0");
			}else
			{
				for(int x = 0; x < directionHeat.length;x++)
				{
					String a = "";
					StringBuilder sb = new StringBuilder();
					sb.append(a);
					//String s = sb.toString();
					for(int y = 0; y < directionHeat[x].length; y++)
					{
						for(int z = 0; z <directionHeat[x][y].length;z++)
						{
							sb.append(directionHeat[x][y][z]+":");
							
						}
						if(y<directionHeat[x].length-1)
						{
							sb.append(",");
						}
					}
					a = sb.toString();
					report.add(a);
				}
			}
			return report;
		}
		
		public ArrayList<String> getDirectionalConstantHeatReport() {
			ArrayList<String> report =  new ArrayList<String>();
			if(directionConstantHeat == null)
			{
				report.add("0");
			}else
			{
				for(int x = 0; x < directionConstantHeat.length;x++)
				{
					String a = "";
					StringBuilder sb = new StringBuilder();
					sb.append(a);
					//String s = sb.toString();
					for(int y = 0; y < directionConstantHeat[x].length; y++)
					{
						for(int z = 0; z <directionConstantHeat[x][y].length;z++)
						{
							sb.append(directionConstantHeat[x][y][z]+":");
							
						}
						if(y<directionConstantHeat[x].length-1)
						{
							sb.append(",");
						}
					}
					a = sb.toString();
					report.add(a);
				}
			}
			return report;
		}

		public ArrayList<String> getRelativeHeatReport() {
			ArrayList<String> report =  new ArrayList<String>();
			if(relativeHeat == null)
			{
				report.add("0");
			}else
			{
				for(int x = 0; x < relativeHeat.length;x++)
				{
					String a = "";
					StringBuilder sb = new StringBuilder();
					sb.append(a);
					//String s = sb.toString();
					for(int y = 0; y < relativeHeat[x].length; y++)
					{
						for(int z = 0; z <relativeHeat[x][y].length;z++)
						{
							sb.append(relativeHeat[x][y][z]+":");
							
						}
						if(y<relativeHeat[x].length-1)
						{
							sb.append(",");
						}
					}
					a = sb.toString();
					report.add(a);
				}
			}
			return report;
		}
		
		public ArrayList<String> getAngleHeatReport() {
			ArrayList<String> report =  new ArrayList<String>();
			if(angleHeat == null)
			{
				report.add("0");
			}else
			{
				for(int x = 0; x < angleHeat.length;x++)
				{
					String a = "";
					StringBuilder sb = new StringBuilder();
					sb.append(a);
					//String s = sb.toString();
					for(int y = 0; y < angleHeat[x].length; y++)
					{
						for(int z = 0; z <angleHeat[x][y].length;z++)
						{
							sb.append(angleHeat[x][y][z]+":");
							
						}
						if(y<angleHeat[x].length-1)
						{
							sb.append(",");
						}
					}
					a = sb.toString();
					report.add(a);
				}
			}
			return report;
		}


		

		public ArrayList<Integer> getDeathIds() {
			return deathIds;
		}


		public void setDeathIds(ArrayList<Integer> deathIds) {
			this.deathIds = deathIds;
		}


		public ArrayList<Integer> getTods() {
			return tods;
		}


		public void setTods(ArrayList<Integer> tods) {
			this.tods = tods;
		}


		public ArrayList<Integer> getBirthIds() {
			return birthIds;
		}


		public void setBirthIds(ArrayList<Integer> birthIds) {
			this.birthIds = birthIds;
		}


		public ArrayList<Integer> getTobs() {
			return tobs;
		}


		public void setTobs(ArrayList<Integer> tobs) {
			this.tobs = tobs;
		}


		public ArrayList<ArrayList<String[]>> getCellData() {
			return cellData;
		}


		public void setCellData(ArrayList<ArrayList<String[]>> cellData) {
			this.cellData = cellData;
		}

		

		public ArrayList<String> getSettings() {
			return settings;
		}

		public void setSettings(ArrayList<String> settings) {
			this.settings = settings;
		}

		
		/*public HeatMap getHeat() {
			return heat;
		}
		
		public void setHeat(HeatMap heat) {
			this.heat = heat;
		}

		public HeatMap getPopHeat() {
			return popHeat;
		}

		public void setPopHeat(HeatMap popHeat) {
			this.popHeat = popHeat;
		}
		
		public HeatMap getDirectionHeatImg() {
			return directionHeatImg;
		}

		public void setDirectionHeatImg(HeatMap directionHeatImg) {
			this.directionHeatImg = directionHeatImg;
		}

		public HeatMap getDirectionConstantHeatImg() {
			return directionConstantHeatImg;
		}

		public void setDirectionConstantHeatImg(HeatMap directionConstantHeatImg) {
			this.directionConstantHeatImg = directionConstantHeatImg;
		}

		public HeatMap getRelativeHeatImg() {
			return relativeHeatImg;
		}

		public void setRelativeHeatImg(HeatMap relativeHeatImg) {
			this.relativeHeatImg = relativeHeatImg;
		}

		public HeatMap getAngleHeatImg() {
			return angleHeatImg;
		}

		public void setAngleHeatImg(HeatMap angleHeatImg) {
			this.angleHeatImg = angleHeatImg;
		}*/

		public int[][] getPositions() {
			return positions;
		}

		public void setPositions(int[][] positions) {
			this.positions = positions;
		}

		public ArrayList<String> getValueNames() {
			return valueNames;
		}

		public void setValueNames(ArrayList<String> valueNames) {
			this.valueNames = valueNames;
		}

		public ArrayList<Double> getValues() {
			return values;
		}

		public void setValues(ArrayList<Double> values) {
			this.values = values;
		}

		public int[][] getPopSectors() {
			return popSectors;
		}

		public void setPopSectors(int[][] popSectors) {
			this.popSectors = popSectors;
		}

		public int[][][] getDirectionHeat() {
			return directionHeat;
		}

		public void setDirectionHeat(int[][][] directionHeat) {
			this.directionHeat = directionHeat;
		}

		public int[][][] getDirectionConstantHeat() {
			return directionConstantHeat;
		}

		public void setDirectionConstantHeat(int[][][] directionConstantHeat) {
			this.directionConstantHeat = directionConstantHeat;
		}

		public int[][][] getRelativeHeat() {
			return relativeHeat;
		}

		public void setRelativeHeat(int[][][] relativeHeat) {
			this.relativeHeat = relativeHeat;
		}

		public int[][][] getAngleHeat() {
			return angleHeat;
		}

		public void setAngleHeat(int[][][] angleHeat) {
			this.angleHeat = angleHeat;
		}

		public ArrayList<ArrayList<Double[]>> getCellPositions() {
			return cellPositions;
		}

		public void setCellPositions(ArrayList<ArrayList<Double[]>> cellPositions) {
			this.cellPositions = cellPositions;
		}

		

		

		
		

}
