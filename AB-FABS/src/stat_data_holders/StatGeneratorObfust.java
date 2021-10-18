package stat_data_holders;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import singleton_holders.SingletonHolder;
import environment_data_holders.PositionGrid;
import file_manipulation.DataShuffler;
import gui_stat_gen.StatRunFeedbackFrame;

public class StatGeneratorObfust {

	private ArrayList<ArrayList<SnapShotObfust>> snapShots = new ArrayList<ArrayList<SnapShotObfust>>();
	private ArrayList<ArrayList<ArrayList<String>>> runTable = new ArrayList<ArrayList<ArrayList<String>>>();
	
	private int INTPOINTER = 7;
	
	private int INTPOINTEROBFUST = 6;
	
	public StatGeneratorObfust()
	{
		
	}
	
	public void generateMeta(ArrayList<ArrayList<SnapShotObfust>> snaps) 
	{
		setSnapShots(generateMetaDataAndAdd(snaps));
		setTables(this.generateTablesFromMeta(snaps));
	}
	
	public void processShuffledData(DataShuffler shuffle) {
		this.generateMetaShuffle(shuffle);
		this.generateTablesFromMetaShuffle(shuffle);
	}


	//snaps is for runs create a snap at each interval, also has a run of meta data snaps appended
	private ArrayList<ArrayList<ArrayList<String>>> generateTablesFromMeta(ArrayList<ArrayList<SnapShotObfust>> snaps) {
		//for each run is an array of colombs holding an array of strings  for each interval + a colomb of headers, runs is the data for a run  
		ArrayList<ArrayList<ArrayList<String>>> tables = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<String> runHeads =snaps.get(0).get(0).getValueNames();
		runHeads.add(0, "Intervals: ");
		runHeads = this.sizeFit(runHeads);
		for(int i = 0; i < snaps.size()-1; i ++)
		{
			ArrayList<ArrayList<String>> indiRunTable = new ArrayList<ArrayList<String>>();
			indiRunTable.add(runHeads);
			for(int l =  0; l < snaps.get(i).size(); l++)
			{
				ArrayList<String> colomb = new ArrayList<String>();
				SnapShotObfust record = snaps.get(i).get(l);
				colomb.add(" Run "+(i+1)+" interval "+(record.getValues().get(INTPOINTER)));
				for(int m = 0; m <record.getValues().size();m++)
				{
					colomb.add(""+record.getValues().get(m));
				}
				colomb = this.sizeFit(colomb);
				indiRunTable.add(colomb);
			}
			ArrayList<String> colomb = new ArrayList<String>();
			colomb.add(" Run "+(i+1)+" Totals");

			for(int l = 1; l < indiRunTable.get(0).size();l++)
			{
				double tot = 0.00;
				for(int m = 1; m < indiRunTable.size();m++)
				{
					tot+= Double.parseDouble(indiRunTable.get(m).get(l));
				}
				colomb.add(""+tot);
			}
			colomb = this.sizeFit(colomb);
			indiRunTable.add(colomb);
			
			tables.add(indiRunTable);
		}
		int i = snaps.size()-1;
		runHeads = this.metaHeaderGenerationFromObf((AggregateSnapShotObfust) snaps.get(i).get(0));
		runHeads.add(0, "Averaged Intervals Over Runs");
		runHeads = this.sizeFit(runHeads);
		ArrayList<ArrayList<String>> indiRunTable = new ArrayList<ArrayList<String>>();
		indiRunTable.add(runHeads);
		for(int l =  0; l < snaps.get(i).size(); l++)
		{
			ArrayList<String> colomb = new ArrayList<String>();
			AggregateSnapShotObfust record = (AggregateSnapShotObfust) snaps.get(i).get(l);
			colomb.add(" Interval "+(record.getValues().get(INTPOINTER)));
			for(int m = 0; m <record.getValues().size();m++)
			{
				colomb.add(""+record.getTotal().get(m));
				colomb.add(""+record.getAverage().get(m));
				colomb.add(""+record.getsD().get(m));
			}
			
			colomb = this.sizeFit(colomb);
			indiRunTable.add(colomb);
		}
		ArrayList<String> colomb = new ArrayList<String>();
		colomb.add(" Run "+(i+1)+" Totals");

		for(int l = 1; l < indiRunTable.get(0).size();l++)
		{
			double tot = 0.00;
			for(int m = 1; m < indiRunTable.size();m++)
			{
				tot+= Double.parseDouble(indiRunTable.get(m).get(l));
			}
			colomb.add(""+tot);
		}
		colomb = this.sizeFit(colomb);
		indiRunTable.add(colomb);
		tables.add(indiRunTable);
		
		//runs at top for meta data sets
		ArrayList<String> runTops = snaps.get(0).get(0).getValueNames();
		runTops.add(0, "Runs: ");
		runTops = this.sizeFit(runTops);
		//for all iterations
		for(int l = 0; l < snaps.get(0).size(); l ++)
		{
			//make table
			ArrayList<ArrayList<String>> topRunTable = new ArrayList<ArrayList<String>>();
			//add first colomb
			topRunTable.add(runTops);
			//for all runs 
			for(int m = 0; m < snaps.size()-1; m ++)
			{
				//create new colomb
				colomb = new ArrayList<String>();
				//get record for interval and run
				SnapShotObfust record = snaps.get(m).get(l);
				colomb.add(" Run "+(m+1)+" interval "+(record.getValues().get(INTPOINTER)));
				for(int n = 0; n <record.getValues().size();n++)
				{
					colomb.add(""+record.getValues().get(n));
				}
				colomb = this.sizeFit(colomb);
				//add the colomb
				
				topRunTable.add(colomb);
			}
			colomb = new ArrayList<String>();
			colomb.add(" Run "+(i+1)+" Totals");

			for(int k = 1; k < topRunTable.get(0).size();k++)
			{
				double tot = 0.00;
				for(int m = 1; m < topRunTable.size();m++)
				{
					tot+= Double.parseDouble(topRunTable.get(m).get(k));
				}
				colomb.add(""+tot);
			}
			colomb = this.sizeFit(colomb);
			topRunTable.add(colomb);
			//add table
			tables.add(topRunTable);
		}
		
		return tables;
	}



	private ArrayList<String> metaHeaderGenerationFromObf(AggregateSnapShotObfust snap) {
		ArrayList<String> aggerHeads = new ArrayList<String>();
		for(int i = 0; i < snap.getTotalNames().size();i++)
		{
			aggerHeads.add(snap.getTotalNames().get(i));
			aggerHeads.add(snap.getAverageNames().get(i));
			aggerHeads.add(snap.getsDNames().get(i));
		}
		return aggerHeads;
	}

	


	private ArrayList<String> sizeFit(ArrayList<String> colomb) {
		int longest = 0;
		for(int i = 0; i < colomb.size(); i++)
		{
			if(colomb.get(i).length() > longest)
			{
				longest = colomb.get(i).length();
			}
		}
		for(int i = 0; i < colomb.size(); i++)
		{
			String str = colomb.get(i);
			int len = str.length();
			StringBuilder sb = new StringBuilder();
			sb.append(str);
			if(len != longest+2)
			{
				for(int l = 0; l < (longest-len+2);l++)
				{
					sb.append(" ");
				}
				colomb.set(i, sb.toString());
			}
		}
		return colomb;
	}

	private ArrayList<ArrayList<SnapShotObfust>> generateMetaDataAndAdd(ArrayList<ArrayList<SnapShotObfust>> snaps) 
	{
		
		
		ArrayList<SnapShotObfust> snips = new ArrayList<SnapShotObfust>();
		for(int i = 0 ; i < snaps.get(0).size(); i++)
		{
			
			AggregateSnapShotObfust shot = new AggregateSnapShotObfust();
			shot.setValues(snaps.get(0).get(i).getValues());
			//gen the data in there
			//merge grids
			//int[][] positions2 = this.mergePopGrids(snaps,i);
			//shot.setPopHeat(shot.generateHeatMap(positions2));
			//int[][] positions = this.mergeGrids(snaps,i);
			//shot.setHeat(shot.generateHeatMap(positions));
			shot.setSettings(snaps.get(0).get(i).getSettings());
			ArrayList<Double> totals = new ArrayList<Double>();
			ArrayList<String> totalNames = new ArrayList<String>();
			//for each value 
			ArrayList<String> ref = snaps.get(i).get(0).getValueNames();
			for(int m = 0; m < ref.size();m++)
			{
				//add new name
				/*
				double totalTotalDistanceTravelled = 0.00;
				double totalAverageDistanceTravelled = 0.00;
				*/
				totalNames.add("Total "+ref.get(m));
				totals.add(0.00);
				//generate two values
				/*
				for(int l = 0; l <snaps.size(); l++)
			{
				totalTotalDistanceTravelled = totalTotalDistanceTravelled + snaps.get(l).get(i).getTotalDistanceTravelled();
				totalAverageDistanceTravelled = totalAverageDistanceTravelled + snaps.get(l).get(i).getAverageDistanceTravelled();
			}
				 */
				for(int l = 0; l <snaps.size(); l++)
				{
					totals.set(m, totals.get(m)+snaps.get(l).get(i).getValues().get(m));
				}
			}
			ArrayList<Double> average = new ArrayList<Double>();
			ArrayList<String> averageNames = new ArrayList<String>();
			//gen averages
			/*
			 * double averageTotalDistanceTravelled = totalTotalDistanceTravelled/snaps.size();
				double averageAverageDistanceTravelled = totalAverageDistanceTravelled/snaps.size();
				double sDTotalDistanceTravelled = 0.00;
				double sDAverageDistanceTravelled = 0.00;
			 */
			for(int q = 0; q < ref.size();q++)
			{
				average.set(q,totals.get(q)/snaps.size());
				averageNames.set(q, "Average "+ref.get(q));
			}
			//SD
			ArrayList<Double> SD = new ArrayList<Double>();
			ArrayList<String> SDNames = new ArrayList<String>();
			for(int q = 0; q < ref.size();q++)
			{
				SDNames.set(q, "sD "+ref.get(q));
				/*
				 * //standard deviation aggregation loop
					for(int l = 0; l <snaps.size(); l++)
					{
						//remove - nums (might invalidate sd?)
						sDTotalDistanceTravelled = Math.sqrt(Math.pow(sDTotalDistanceTravelled, 2));
						sDAverageDistanceTravelled = Math.sqrt(Math.pow(sDAverageDistanceTravelled, 2));
						
						double holder = (snaps.get(l).get(i).getTotalDistanceTravelled()-averageTotalDistanceTravelled);
						sDTotalDistanceTravelled = sDTotalDistanceTravelled + Math.sqrt(holder);
						holder = (snaps.get(l).get(i).getAverageDistanceTravelled()-averageAverageDistanceTravelled);
						holder = Math.sqrt(Math.pow(holder,2));
						sDAverageDistanceTravelled = sDAverageDistanceTravelled + Math.sqrt(holder);
				
					}
				 */
				double sDHolder = 0.00;
				for(int l = 0; l <snaps.size(); l++)
				{
					sDHolder = Math.sqrt(Math.pow(sDHolder, 2));
					double holder = snaps.get(l).get(i).getValues().get(q)-average.get(q);
					holder = Math.sqrt(Math.pow(holder,2));
					sDHolder = sDHolder + Math.sqrt(holder);
					
				}
				//final step
				/*
				 * double holder = 0;
					holder = sDTotalDistanceTravelled/snaps.size();
					sDTotalDistanceTravelled = Math.sqrt(holder);
					holder = sDAverageDistanceTravelled/snaps.size();
					sDAverageDistanceTravelled = Math.sqrt(holder);
				 */
				double holder = 0.00;
				holder = sDHolder/snaps.size();
				sDHolder = Math.sqrt(holder);
				SD.set(q,sDHolder);
			}
			/*
			 * //store values
					shot.setTotalDistanceTravelled(totalTotalDistanceTravelled);
					shot.setAverageDistanceTravelled(totalAverageDistanceTravelled);
			 */
			shot.setTotalNames(totalNames);
			shot.setTotal(totals);
			shot.setAverageNames(averageNames);
			shot.setAverage(average);
			shot.setsDNames(SDNames);
			shot.setsD(SD);			
			snips.add(shot);
		}
		snaps.add(snips);
		return snaps;
	}
	
	public ArrayList<String> givenValuesGenerateMeta(ArrayList<String> valuesAcrossSnaps, ArrayList<Integer> cellNumbers)
	{
		
		ArrayList<String> returnValues = new ArrayList<String>();
		double total = 0.00;
		double average = 0.00;
			
		//aggregation loop
		for(int i = 0; i <valuesAcrossSnaps.size(); i++)
		{
			total = total + Double.parseDouble(valuesAcrossSnaps.get(i));
			average = average + Double.parseDouble(valuesAcrossSnaps.get(i))/(Double.parseDouble(""+cellNumbers.get(i)));
			
		}
		
		//gen averages
		double averageTotal = total/valuesAcrossSnaps.size();
		double averageAverage = average/valuesAcrossSnaps.size();
		double sDTotal = 0.00;
		double sDAverage = 0.00;
		
		
		//standard deviation aggregation loop
		for(int l = 0; l <valuesAcrossSnaps.size(); l++)
		{
			//remove - nums (might invalidate sd?)
			sDTotal = Math.sqrt(Math.pow(sDTotal, 2));
			sDAverage = Math.sqrt(Math.pow(sDAverage, 2));
			
			
			double holder = (Double.parseDouble(valuesAcrossSnaps.get(l))-averageTotal);
			holder = Math.sqrt(holder);
			sDTotal = sDTotal + holder;
			holder = (Double.parseDouble(valuesAcrossSnaps.get(l))-averageAverage);
			holder = Math.sqrt(Math.pow(holder,2));
			holder = Math.sqrt(holder);
			sDAverage = sDAverage + holder;
		}
		double holder = 0;
		holder = sDTotal/valuesAcrossSnaps.size();
		sDTotal = Math.sqrt(holder);
		holder = sDAverage/valuesAcrossSnaps.size();
		sDAverage = Math.sqrt(holder);
		
		returnValues.add(""+total);
		returnValues.add(""+average);
		returnValues.add(""+averageTotal);
		returnValues.add(""+averageAverage);
		returnValues.add(""+sDTotal);
		returnValues.add(""+sDAverage);
		return returnValues;
		
	}
	
	public ArrayList<String> givenValuesGenerateMeta2(ArrayList<String> valuesAcrossSnaps, ArrayList<Integer> cellNumbers)
	{
		
		ArrayList<String> returnValues = new ArrayList<String>();
		int size = valuesAcrossSnaps.size();
		double[] data = new double[size];
		for(int i = 0; i < data.length; i++)
		{
			data[i] = Double.parseDouble(valuesAcrossSnaps.get(i));
		}
		double[] av = new double[size];
		for(int i = 0; i < av.length; i++)
		{
			av[i] = data[i]/cellNumbers.get(i);
		}
		double total = 0.00;
		double average = 0.00;
		for(int i = 0; i < data.length; i++)
		{
			total += data[i];
			average += av[i];
		}
		average = average/size;
		double num = 0.00;
		for(int i = 0; i < cellNumbers.size();i++)
		{
			num+=cellNumbers.get(i);
		}
		//gen averages
		double averageTotal = total/size;
		double averageAverage = averageTotal/num;
		double sDTotal = getStdDev(getVariance(data,size));
		double sDAverage = getStdDev(getVariance(av,size));
		
		
		returnValues.add(""+total);
		returnValues.add(""+average);
		returnValues.add(""+averageTotal);
		returnValues.add(""+averageAverage);
		returnValues.add(""+sDTotal);
		returnValues.add(""+sDAverage);
		return returnValues;
		
	}

	double getMean(double[] data, int size)
    {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/size;
    }

    double getVariance(double[] data, int size)
    {
        double mean = getMean(data, size);
        double temp = 0;
        for(double a :data)
            temp += (a-mean)*(a-mean);
        return temp/(size-1);
    }

    double getStdDev(double variance)
    {
        return Math.sqrt(variance);
    }
	

	private int[][] mergePopGrids(ArrayList<ArrayList<SnapShotObfust>> snaps, int i) {
		int[][] all = new int[snaps.get(0).get(0).getPopSectors().length][snaps.get(0).get(0).getPopSectors()[0].length];
		for(int l = 0; l < snaps.size(); l++)
		{
			all = this.mergePosHeats(all,snaps.get(l).get(i).getPopSectors() );
		}
		
		return all;
	}


	/*private int[][] mergeGrids(ArrayList<ArrayList<SnapShotObfust>> snaps, int  i) {
		//int[][] all = new int[snaps.get(0).get(0).getHeat().getHeats().length][snaps.get(0).get(0).getHeat().getHeats()[0].length];
		int[][] all = new int[snaps.get(0).get(0).getPositions().length][snaps.get(0).get(0).getPositions()[0].length];
		//System.out.println("new merge "+i+" "+all.getHeat()[0][0]);
		for(int l = 0; l < snaps.size(); l++)
		{
			//System.out.println("merge "+i+" "+l);
			all = this.mergePosHeats(all,snaps.get(l).get(i).getPositions() );
		}
		/*for(int l = 0; l < snaps.get(0).size(); l++)
		{
			int[][] grid = snaps.get(0).get(l).getHeat().getPositions().getHeat();
			for(int x = 0; x < 2;x++)
			{
				for(int y = 0 ; y < grid[x].length;y++)
				{
					System.out.print("|"+grid[y][x]);
				}
				System.out.println("");
			}
			System.out.println(snaps.get(0).get(l).getHeat().getPositions().getHeat());
		}
		return all;
	}*/



	private int[][] mergeHeatGrids(DataShuffler shuffle, int i) {
		//create grid 1
		int[][] grid1 = shuffle.getHeatGridFast(new File(shuffle.getSnapHeatFiles().get(0).get(i)));
		//create grid 2 and merge
		for(int l = 1; l < shuffle.getSnapHeatFiles().size(); l++)
		{
				
					int[][] grid2 = shuffle.getHeatGridFast(new File(shuffle.getSnapHeatFiles().get(l).get(i)));
					grid1 = this.mergePosHeats(grid1, grid2);
				
			
		}
		//repeat
		return grid1;
	}
	
	private int[][][] mergeDirectionalGrids(DataShuffler shuffle, int i,String target)
	{
		//create grid 1
		int[][][] grid1 = null;
		
		if(target.equals("Directional"))
		{
			grid1 = shuffle.getDirectionalGridFast(0,i,target);
			//create grid 2 and merge
			for(int l = 1; l < shuffle.getSnapHeatFiles().size(); l++)
			{
					
						int[][][] grid2 = shuffle.getDirectionalGridFast(l,i,target);
						grid1 = this.mergeDirectionalHeats(grid1, grid2);
					
				
			}
			
		}else
		{
		if(target.equals("DirectionalConstant"))
		{
			grid1 = shuffle.getDirectionalGridFast(0,i,target);
			//create grid 2 and merge
			for(int l = 1; l < shuffle.getSnapHeatFiles().size(); l++)
			{
					
						int[][][] grid2 = shuffle.getDirectionalGridFast(l,i,target);
						grid1 = this.mergeDirectionalHeats(grid1, grid2);
					
				
			}
		}else
		{
		if(target.equals("Relative"))
		{
			grid1 = shuffle.getDirectionalGridFast(0,i,target);
			//create grid 2 and merge
			for(int l = 1; l < shuffle.getSnapHeatFiles().size(); l++)
			{
					
						int[][][] grid2 = shuffle.getDirectionalGridFast(l,i,target);
						grid1 = this.mergeDirectionalHeats(grid1, grid2);
					
				
			}
		}else
		{
		if(target.equals("Angle"))
		{
			grid1 = shuffle.getDirectionalGridFast(0,i,target);
			//create grid 2 and merge
			for(int l = 1; l < shuffle.getSnapHeatFiles().size(); l++)
			{
					
						int[][][] grid2 = shuffle.getDirectionalGridFast(l,i,target);
						grid1 = this.mergeDirectionalHeats(grid1, grid2);
					
				
			}
		}}}}
		
		//repeat
		return grid1;
	}
	
	private int[][] mergePopGrids(DataShuffler shuffle, int i) {
		//create grid 1
		int[][] grid1 = shuffle.getHeatGridFast(new File(shuffle.getSnapPopFiles().get(0).get(i)));
		//create grid 2 and merge
		for(int l = 1; l < shuffle.getSnapHeatFiles().size(); l++)
		{
				
					int[][] grid2 = shuffle.getHeatGridFast(new File(shuffle.getSnapPopFiles().get(l).get(i)));
					grid1 = this.mergePosHeats(grid1, grid2);
				
			
		}
		//repeat
		return grid1;
	}

	private int[][] mergePosHeats(int[][] all, int[][] positions) {
		for(int x = 0 ; x < all.length; x++)
		{
			for(int y = 0; y < all[x].length; y++)
			{
				all[x][y] = all[x][y] + positions[x][y];
			}
		}
		//System.out.println(positions.getHeat()[50][50]);
		//System.out.println(positions.getHeat());
		return all;
	}
	
	private int[][][] mergeDirectionalHeats(int[][][] all, int[][][] positions) {
		for(int x = 0 ; x < all.length; x++)
		{
			for(int y = 0; y < all[x].length; y++)
			{
				for(int z = 0;z< all[x][y].length;z++)
				{
					all[x][y][z] += positions[x][y][z];
				}
			}
		}
		//System.out.println(positions.getHeat()[50][50]);
		//System.out.println(positions.getHeat());
		return all;
	}



	public ArrayList<ArrayList<SnapShotObfust>> getSnapShots() {
		return snapShots;
	}



	public void setSnapShots(ArrayList<ArrayList<SnapShotObfust>> arrayList) {
		this.snapShots = arrayList;
	}


	public ArrayList<ArrayList<ArrayList<String>>> getTables() {
		return runTable;
	}


	public void setTables(ArrayList<ArrayList<ArrayList<String>>> runTable) {
		this.runTable = runTable;
	}


	

	private void generateMetaShuffle(DataShuffler shuffle) {
		ArrayList<ArrayList<String>> snaps = shuffle.getSnapFiles();
		for(int i = 0 ; i < snaps.get(0).size(); i++)
		{
			
			AggregateSnapShotObfust shot = new AggregateSnapShotObfust();
			shot.setValues(shuffle.getObfustValues(snaps.get(0).get(i)));
			//gen the data in there
			//merge grids
			int[][] positions2 = this.mergePopGrids(shuffle,i);
			//shot.setPopHeat(shot.generateHeatMap(positions2));
			shot.setPopSectors(positions2);
			int[][] positions = this.mergeHeatGrids(shuffle,i);
			shot.setPositions(positions);
			//shot.setHeat(shot.generateHeatMap(positions));
			int[][][] posi = this.mergeDirectionalGrids(shuffle,i,"Directional");
			shot.setDirectionHeat(posi);
			posi = this.mergeDirectionalGrids(shuffle,i,"DirectionalConstant");
			shot.setDirectionConstantHeat(posi);
			posi = this.mergeDirectionalGrids(shuffle,i,"Relative");
			shot.setRelativeHeat(posi);
			posi = this.mergeDirectionalGrids(shuffle,i,"Angle");
			shot.setAngleHeat(posi);
			shot.setSettings(shuffle.getSnapSettings(i));
			
			ArrayList<Double> totals = new ArrayList<Double>();
			ArrayList<String> totalNames = new ArrayList<String>();
			//for each value 
			ArrayList<String> ref = shuffle.getObfustValueNames(snaps.get(0).get(i));
			
			for(int m = 0; m < ref.size();m++)
			{
				//add new name
				totalNames.add("Total "+ref.get(m));
				totals.add(0.00);
				//generate two values
				for(int l = 0; l <snaps.size(); l++)
				{
					totals.set(m, totals.get(m)+Double.parseDouble(shuffle.fishOutSnap(m, l, i).split(";")[1]));
				}
			}
			
			ArrayList<Double> average = new ArrayList<Double>();
			ArrayList<String> averageNames = new ArrayList<String>();
			//gen averages
			for(int q = 0; q < ref.size();q++)
			{
				average.add(totals.get(q)/snaps.size());
				averageNames.add( "Average "+ref.get(q));
			}
			
			//SD
			ArrayList<Double> SD = new ArrayList<Double>();
			ArrayList<String> SDNames = new ArrayList<String>();
			for(int q = 0; q < ref.size();q++)
			{
				SDNames.add( "sD "+ref.get(q));
				/*
				 * //standard deviation aggregation loop
				 */
				double sDHolder = 0.00;
				for(int l = 0; l <snaps.size(); l++)
				{
					sDHolder = Math.sqrt(Math.pow(sDHolder, 2));
					double holder = Double.parseDouble(shuffle.fishOutSnap(q, l, i).split(";")[1])-average.get(q);
					holder = Math.sqrt(Math.pow(holder,2));
					sDHolder = sDHolder + Math.sqrt(holder);
					
				}
				//final step
				double holder = 0.00;
				holder = sDHolder/snaps.size();
				sDHolder = Math.sqrt(holder);
				SD.add(sDHolder);
			}
			
			/*
			 * //store values
			 */
			shot.setTotalNames(totalNames);
			shot.setTotal(totals);
			shot.setAverageNames(averageNames);
			shot.setAverage(average);
			shot.setsDNames(SDNames);
			shot.setsD(SD);	
			
			shuffle.metaShuckObfust(shot, i);
		}
		
	}

	public void generateTablesFromMetaShuffle(DataShuffler shuffle) {
		//for each run is an array of colombs holding an array of strings  for each interval + a colomb of headers, runs is the data for a run  
		ArrayList<ArrayList<String>> snaps = shuffle.getSnapFiles();
		ArrayList<String> runHeads =shuffle.getObfustValueNames(snaps.get(0).get(0));
		runHeads.add(0, "Intervals: ");
		runHeads = this.sizeFit(runHeads);
		for(int i = 0; i < snaps.size(); i ++)
		{
			ArrayList<ArrayList<String>> indiRunTable = new ArrayList<ArrayList<String>>();
			indiRunTable.add(runHeads);
			for(int l =  0; l < snaps.get(i).size(); l++)
			{
				ArrayList<String> colomb = new ArrayList<String>();
				colomb.add(" Run "+(i+1)+" "+(shuffle.fishOutSnap(INTPOINTEROBFUST, i, l).replace(";", " ")));
				for(int m = 0; m <runHeads.size()-1;m++)
				{
					colomb.add(""+shuffle.fishOutSnap(m, i, l).split(";")[1]);
				}
				colomb = this.sizeFit(colomb);
				indiRunTable.add(colomb);
			}
			ArrayList<String> colomb = new ArrayList<String>();
			colomb.add(" Run "+(i+1)+" Totals");

			for(int l = 1; l < indiRunTable.get(0).size();l++)
			{
				double tot = 0.00;
				for(int m = 1; m < indiRunTable.size();m++)
				{
					tot+= Double.parseDouble(indiRunTable.get(m).get(l));
				}
				colomb.add(""+tot);
			}
			colomb = this.sizeFit(colomb);
			indiRunTable.add(colomb);
			shuffle.shuckTableStandardTable(indiRunTable, i);
		}
		int i = snaps.size()-1;
		runHeads = this.metaHeaderGenerationFromObfShuffle(shuffle,i,runHeads.size()-1);
		runHeads.add(0, "Averaged Intervals Over Runs");
		runHeads = this.sizeFit(runHeads);
		ArrayList<ArrayList<String>> indiRunTable = new ArrayList<ArrayList<String>>();
		indiRunTable.add(runHeads);
		for(int l =  0; l < snaps.get(i).size(); l++)
		{
			ArrayList<String> colomb = new ArrayList<String>();
			colomb.add(" Interval "+(shuffle.fishOutSnap(INTPOINTEROBFUST, 0, 1).split(";")[1]));
			for(int o = 0; o < runHeads.size()-1;o++ )
			{
				colomb.add(shuffle.fishOutMetaSnap(o, l).split(";")[1]);
			}
			
			colomb = this.sizeFit(colomb);
			indiRunTable.add(colomb);
		}
		ArrayList<String> colomb = new ArrayList<String>();
		colomb.add(" Run "+(i+1)+" Totals");

		for(int l = 1; l < indiRunTable.get(0).size();l++)
		{
			double tot = 0.00;
			for(int m = 1; m < indiRunTable.size();m++)
			{
				tot+= Double.parseDouble(indiRunTable.get(m).get(l));
			}
			colomb.add(""+tot);
		}
		colomb = this.sizeFit(colomb);
		indiRunTable.add(colomb);
		shuffle.shuckMetaTable(indiRunTable);
		
		//runs at top for meta data sets
		ArrayList<String> runTops = shuffle.getObfustValueNames(snaps.get(0).get(0));
		runTops.add(0, "Runs: ");
		runTops = this.sizeFit(runTops);
		//for all iterations
		for(int l = 0; l < snaps.get(0).size(); l ++)
		{
			//make table
			ArrayList<ArrayList<String>> topRunTable = new ArrayList<ArrayList<String>>();
			//add first colomb
			topRunTable.add(runTops);
			//for all runs 
			for(int m = 0; m < snaps.size(); m ++)
			{
				//create new colomb
				colomb = new ArrayList<String>();
				//get record for interval and run
				colomb.add(" Run "+(m+1)+" "+(shuffle.fishOutSnap(INTPOINTEROBFUST, m, l).split(";")[1]));
				for(int n = 0; n <runTops.size()-1;n++)
				{
					colomb.add(""+shuffle.fishOutSnap(n, m, l).split(";")[1]);
				}
				colomb = this.sizeFit(colomb);
				//add the colomb
				
				topRunTable.add(colomb);
			}
			colomb = new ArrayList<String>();
			colomb.add(" Run "+(i+1)+" Totals");

			for(int k = 1; k < topRunTable.get(0).size();k++)
			{
				double tot = 0.00;
				for(int m = 1; m < topRunTable.size();m++)
				{
					tot+= Double.parseDouble(topRunTable.get(m).get(k));
				}
				colomb.add(""+tot);
			}
			colomb = this.sizeFit(colomb);
			topRunTable.add(colomb);
			shuffle.shuckTopTable(topRunTable, l);
		}
		
		
	}

	private ArrayList<String> metaHeaderGenerationFromObfShuffle(
			DataShuffler shuffle, int i, int j) {
		ArrayList<String> heads = new ArrayList<String>();
		for(int l = 0; l < j*3;l++ )
		{
			heads.add(shuffle.fishOutMetaSnap(l, 0).split(";")[0]);
		}
		return heads;
	}

	
}
