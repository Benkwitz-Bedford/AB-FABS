package cycles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import cell_data_holders.Cell;
import cell_data_holders.TempCellConstruct;
import cycle_components.StaticCalculations;
import gui_main.GridPanel;
import heatmaps.MosaicHeatmap;
import running_modules_increment.Bundle;
import running_modules_increment.DataBundle;
import running_modules_increment.StorkModule;
import running_modules_increment.TurnModule;
import running_modules_jump.BoundryModule;
import running_modules_jump.IncrementModule;
import singleton_holders.SingletonHolder;

public class FakerRandomDavideConstructCreator {
	
	int time = 401;
	int stepSize = 1;
	int numberOfCells = 6000;
	protected GridPanel parent;
	int dimensions = 243;
	int posSize = 10;
   	protected Bundle sim = new DataBundle( dimensions, posSize, parent);
	
	public void run()
	{	String[] values = new String[40];
		values[0] = "false";//+replication.isSelected();
		values[1] = "0";//repChanceField.getText().replace(",", "");
		values[2] = "0";//repTabooField.getText().replace(",", "");
		values[3] =  "false";//+repVar.isSelected();
		values[4] = "0";//repVarField.getText().replace(",", "");

		values[5] = "false";//""+death.isSelected();
		values[6] = "0";//deathChanceField.getText().replace(",", "");
		values[7] = "false";//""+deathVar.isSelected();
		values[8] = "0";//deathVarField.getText().replace(",", "");
		values[9] = "0";//corpseDisposal.getSelection().getActionCommand();
		values[10] = "0";//deathTimedField.getText().replace(",", "");

		values[11] = "true";//+variableSpeed.isSelected();
		values[12] = "50";//varSpeedField.getText().replace(",", "");
		values[13] = "0";//varSpeedVarField.getText().replace(",", "");
		values[14] = "0";//speedSpread.getSelection().getActionCommand();
		
		values[15] = "100000";//changeChance.getText().replace(",", "");
		values[16] = "0";//changeVarience.getText().replace(",", "");
		values[17] = "10000";//noOfCells.getText().replace(",", "");
		
		values[18] = "0";//wOne.getText().replace(",", "");
		values[19] = "0";//wOneVarience.getText().replace(",", "");
		
		values[20] = "0";//wTwo.getText().replace(",", "");
		values[21] = "0";//wTwoVarience.getText().replace(",", "");
		
		values[22] = "0";//wThree.getText().replace(",", "");
		values[23] = "0";//wThreeVarience.getText().replace(",", "");
		
		values[24] = "0";//wFour.getText().replace(",", "");
		values[25] = "0";//wFourVarience.getText().replace(",", "");
		
		values[26] = "0";//wFive.getText().replace(",", "");
		values[27] = "0";//wFiveVarience.getText().replace(",", "");
		
		values[28] = "0";//wSix.getText().replace(",", "");
		values[29] = "0";//wSixVarience.getText().replace(",", "");
		
		values[30] = "0";//wSeven.getText().replace(",", "");
		values[31] = "0";//wSevenVarience.getText().replace(",", "");
		
		values[32] = "0";//wEight.getText().replace(",", "");
		values[33] = "0";//wEightVarience.getText().replace(",", "");
		
		values[34] = "random";
		
		values[35] = "5";//cellSize.getText().replace(",", "");
		values[36] = "0";//cellSizeVarience.getText().replace(",", "");
		
		values[37] = "false";//""+antPath.isSelected();
		values[38] = "0";//antWeightField.getText().replace(",", "");
		values[39] = "0";//antVarField.getText().replace(",", "");
		
		String[][] sets = new String[][]{values};
		SingletonHolder.giveFullCellValueSet(sets);
		
		SingletonHolder.clean();
		SingletonHolder.setSize(dimensions);
		for(int i = 0; i < sim.getCellMan().getCells().size();i++)
		{
			sim.getCellMan().getCells().get(i).getPack().clean();
		}
		sim.getCellMan().setStore(sim.getCellMan().createCells(sim.getCellMan()));
		sim.getMoveMan().genPosGrid(dimensions,posSize);
		sim.getMoveMan().getPositionGrid().addAllCellPositions(sim.getCellMan().getStore().getCells());
		sim.setMos(new MosaicHeatmap(dimensions/posSize,dimensions/posSize, sim.getMoveMan().getPositionGrid()));
		sim.resetMos();
		SingletonHolder.setBoundary(false);
		SingletonHolder.setBoundaryType("square");
		SingletonHolder.setBoundarySize(dimensions/2-10);
		BoundryModule rep = null;
		if(SingletonHolder.isBoundary())
		{
			rep = new BoundryModule();
		}
		sim.getCellMan().getStore().setCells(sim.getMoveMan().checkAllAndFixWithinBounds(sim.getCellMan().getStore().getCells(), sim.getMoveMan().getPositionGrid()));
		TurnModule tur =  new TurnModule();
		IncrementModule inc = new IncrementModule();
		int counter = 0;
		double[][][] record = new double[sim.getCellMan().getStore().getCells().size()][time][2];
		boolean inting = false;
		do
		{
			tur.genNext(sim);
			for(int i = 0; i < stepSize;i++)
			{
				SingletonHolder.incJumpCounter();
				inc.genNext(sim);
				if (rep != null)
				{
					rep.genNext(sim);
				}
			}
			if(inting)
			{
				for(int i = 0; i < sim.getCellMan().getStore().getCells().size();i++)
					
				{
					Cell c = sim.getCellMan().getStore().getCells().get(i);
					int[] reco = new int[]{(int) c.getPositionX(),(int) c.getPositionY()};
					double[] recor = new double[] {reco[0],reco[1]};
					//System.out.println("x"+ reco[0]+", "+reco[1]);
					record[i][counter] = recor;
				}
			}
			else
			{
				for(int i = 0; i < sim.getCellMan().getStore().getCells().size();i++)
				
				{
					Cell c = sim.getCellMan().getStore().getCells().get(i);
					double[] recor = new double[]{c.getPositionX(),c.getPositionY()};
					record[i][counter] = recor;
				}
			}
			counter++;
		}while(counter < time);
		if(inting)
		{
			//this.offLoadRecordInting(record);
		}else
		{
			this.offLoadRecord(record);
		}
		System.exit(0);
	}
	
	public void brownian()
	{
		double[][][] record = new double[10000][time][2];
		int counter = 0; 
		boolean inting = false;
		
		do
		{
			for(int i =0; i < record.length;i++)
			{
				if(counter == 0)
				{
					Random rand = SingletonHolder.getMasterRandom();
					record[i][counter] = gaussianGen(rand.nextInt(dimensions+1),rand.nextInt(dimensions+1),2);
				}else
				{
					record[i][counter] = gaussianGen(record[i][counter-1][0],record[i][counter-1][1],20);
				}
				double[] dub = record[i][counter];
				double x = dub[0];
				double y = dub[1];
				if(x < 0)
				{
					x = dimensions-1+x;
				}
				if(y < 0)
				{
					y = dimensions-1+y;
				}
				if(x > dimensions)
				{
					x = 1+x-dimensions;
				}
				if(y > dimensions)
				{
					y = 1+y-dimensions;
				}
				dub[0] = x;
				dub[1] = y;
				record[i][counter] = dub;
				if(inting)
				{
					record[i][counter][0] = (int)record[i][counter][0];
					record[i][counter][1] = (int)record[i][counter][1];
				}
			}
			
			counter++;
		}while(counter < time);
		if(inting)
		{
			//this.offLoadRecordInting(record);
		}else
		{
			this.offLoadRecord(record);
		}
		System.exit(0);
	}
	
	public void intGen(String append)
	{
		int[][][] record = new int[5000][time][2];
		int counter = 0; 
		
		do
		{
			for(int i =0; i < record.length;i++)
			{
				if(counter == 0)
				{
					Random rand = new Random();
					//record[i][counter] = new int[] {(int) (rand.nextInt(dimensions*100000+1)/100000.00+0.5),(int) (rand.nextInt(dimensions*100000+1)/100000.00+0.5)};
					record[i][counter] = new int[] {Math.toIntExact((long)(rand.nextInt(dimensions*100000+1)/100000.00)),Math.toIntExact((long)(rand.nextInt(dimensions*100000+1)/100000.00))};
				}else
				{
					record[i][counter] = cellGen(record[i][counter-1][0],record[i][counter-1][1],5);
				}
				int[] dub = record[i][counter];
				record[i][counter] = dub;
				
			}
			
			counter++;
		}while(counter < time);
		
			this.offLoadRecordInting(record,append);
		
	}
	
	public void doubleGen(String append)
	{
		double[][][] record = new double[5000][time][2];
		int counter = 0; 
		
		do
		{
			for(int i =0; i < record.length;i++)
			{
				if(counter == 0)
				{
					Random rand = new Random();
					record[i][counter] = new double[] {rand.nextInt(dimensions*100000+1)/100000.00,rand.nextInt(dimensions*100000+1)/100000.00};
				}else
				{
					record[i][counter] = cellGen(record[i][counter-1][0],record[i][counter-1][1],5.00);
				}
				double[] dub = record[i][counter];
				record[i][counter] = dub;
				
			}
			
			counter++;
		}while(counter < time);
		
			this.offLoadRecordDoubleing(record,append);
		
	}
	
	public void doubleGenIntEnd(String append)
	{
		double[][][] record = new double[5000][time][2];
		int counter = 0; 
		
		do
		{
			for(int i =0; i < record.length;i++)
			{
				if(counter == 0)
				{
					Random rand = new Random();
					record[i][counter] = new double[] {rand.nextInt(dimensions*100000+1)/100000.00,rand.nextInt(dimensions*100000+1)/100000.00};
				}else
				{
					record[i][counter] = cellGen(record[i][counter-1][0],record[i][counter-1][1],5.00);
				}
				double[] dub = record[i][counter];
				record[i][counter] = dub;
				
			}
			
			counter++;
		}while(counter < time);
		
			this.offLoadRecordInting(record,append);
		
	}
	

	public void doubleGenIntEndGaus(String append, int x, int y, boolean overlap) {
		double[][][] record = new double[1000][time][2];
		int counter = 0; 
		int[] randoms = new int[record.length/2];
		Random random = new Random();
		for(int i = 0; i < randoms.length;i++)
		{
			randoms[i] = random.nextInt(time);
		}
		do
		{
			for(int i =0; i < record.length;i++)
			{
				if(counter == 0)
				{
					Random rand = new Random();
					record[i][counter] = new double[] {rand.nextInt(dimensions*100000+1)/100000.00,rand.nextInt(dimensions*100000+1)/100000.00};
					//record[i][counter] = new double[] {dimensions/2,dimensions/2};  
				}else
				{
					if(counter == 1)
					{

						record[i][counter] = cellGenGaus(record[i][counter-1][0],record[i][counter-1][1],record[i][counter-1][0],record[i][counter-1][1],2.00,x,y);
					}else
					{
						record[i][counter] = cellGenGaus(record[i][counter-1][0],record[i][counter-1][1],record[i][counter-2][0],record[i][counter-2][1],2.00,x,y);
					}
				}
				double[] dub = record[i][counter];
				record[i][counter] = dub;
				
			}
			
			counter++;
		}while(counter < time);

		if(overlap)
		{
			record = this.injectOverlap(record,randoms);
		}
		
			this.offLoadRecordInting(record,append);
		
	}
	
	public void doubleGenIntEndGausStalling(String append, int x, int y,boolean overlap) {
		double[][][] record = new double[1000][time][2];
		double speed = 2.00;
		double microSpeed = 0.2;
		int cnt = 0; 
		int cntLimit = 100;
		boolean cnting = false;
		boolean cntav = true;
		int[] randoms = new int[record.length/2];
		Random random = new Random();
		for(int i = 0; i < randoms.length;i++)
		{
			randoms[i] = random.nextInt(time);
		}
			for(int i =0; i < record.length;i++)
			{
				speed = 2.0;
				cnting = false;
				cntav = true;
				cnt = 0;
				for(int counter = 0;counter < record[i].length;counter++)
				{
					if(randoms[i/2] == counter&&cntav)
					{
						speed =microSpeed;
						cnting = true;
						//System.out.println("on "+speed+" "+cnt+" "+i+" "+counter);
					}
					if(counter == 0)
					{
						Random rand = new Random();
						record[i][counter] = new double[] {rand.nextInt(dimensions*100000+1)/100000.00,rand.nextInt(dimensions*100000+1)/100000.00};
						//record[i][counter] = new double[] {dimensions/2,dimensions/2};  
					}else
					{
						if(counter == 1)
						{
	
							record[i][counter] = cellGenGaus(record[i][counter-1][0],record[i][counter-1][1],record[i][counter-1][0],record[i][counter-1][1],speed,x,y);
						}else
						{
							record[i][counter] = cellGenGaus(record[i][counter-1][0],record[i][counter-1][1],record[i][counter-2][0],record[i][counter-2][1],speed,x,y);
						}
					}
					double[] dub = record[i][counter];
					record[i][counter] = dub;
					if(cnting)
					{
						cnt++;
					}
					if(cnt == cntLimit)
					{
						speed = 2.0;
						cnting = false;
						cntav = false;
						//System.out.println("off "+speed+" "+cnt+" "+i+" "+counter);
					}
				}
			}
			
		if(overlap)
		{
			record = this.injectOverlap(record,randoms);
		}
		
			this.offLoadRecordInting(record,append);
		
	}

	private double[][][] injectOverlap(double[][][] record, int[] randoms) {
		
		for(int x = 0; x < record.length;x+=2)
		{
			double tX =0.00;
			double tY =0.00;
			double[] recA = record[x][randoms[x/2]];
			double[] recB = record[x+1][randoms[x/2]];
			tX = recA[0]-recB[0];
			tY = recA[1]-recB[1];
			for(int y = 0; y < record[x+1].length;y++)
			{
				double[] rec = record[x+1][y];
				rec[0] +=tX;
				rec[1] +=tY;
				record[x+1][y] = rec;
			}
		}
		return record;
	}

	private double[] cellGenGaus(double x, double y, double x2, double y2, double d, int m, int s) {
		Random randomiser = new Random();
		double ang2 = Math.atan2(y - y2, x - x2);
		ang2 = Math.toDegrees(ang2);
		double gaus = randomiser.nextGaussian();
		gaus = gaus*(m*20)+(70*s);
		//gaus = m*45;
		if(randomiser.nextBoolean())
		{
			gaus += ang2;
		}else
		{

			gaus += -ang2;
		}
		gaus = StaticCalculations.counterClockwiseClean(gaus);
		//gaus = ang2;
		//gaus = 180.00;
		double jump = d;
		double vect = Math.toRadians(gaus);
		x = ((x + Math.cos(vect)*jump));
		y = ((y + Math.sin(vect)*jump));
		return new double[] {x,y};
	}

	private double[] cellGen(double x, double y, double d) {
		Random randomiser = new Random();
		double pointer = randomiser.nextInt(360001)/1000;
		double jump = d;
		double vect = Math.toRadians(pointer);
		x = ((x + Math.cos(vect)*jump));
		y = ((y + Math.sin(vect)*jump));
		return new double[] {x,y};
	}

	private int[] cellGen(int x, int y, int d) {
		Random randomiser = new Random();
		double pointer = randomiser.nextInt(360001)/1000;
		double jump = d;
		double vect = Math.toRadians(pointer);
		//double x2 = x + Math.cos(vect)*jump;
		//double y2 = y + Math.sin(vect)*jump;
		//x = (int) Math.round( (x2));
		//y = (int) Math.round( (y2));
		//x =  Math.toIntExact((long)(x + Math.cos(vect)*jump));
		//y =  Math.toIntExact((long)(y + Math.sin(vect)*jump));
		//x =  (int) Math.round((x + Math.cos(vect)*jump));
		//y =  (int) Math.round((y + Math.sin(vect)*jump));
		x = (int) ((x + Math.cos(vect)*jump));
		y = (int) ((y + Math.sin(vect)*jump));
		return new int[] {x,y};
	}

	private double[] gaussianGen(double x, double y, int i) {
		Random randomiser = SingletonHolder.getMasterRandom();
		double poiX = randomiser.nextGaussian()*i+x;
		double poiY = randomiser.nextGaussian()*i+y;
		double[] dub = new double[]{poiX,poiY};
		return dub;
	}

	private void offLoadRecord(double[][][] record) {
		String starts = "starting";
		StringBuilder st = new StringBuilder();
		st.append(starts);
		for(int i = 0; i < record.length; i++)
		{
			st.append(","+i);
		}
		String eventVect = "1";
		StringBuilder sb = new StringBuilder();
		sb.append(eventVect);
		for(int i = 0; i < record.length; i++)
		{
			sb.append(","+1);
		}
		String[] lines = new String[record.length];
		for(int l = 0; l < record.length;l++)
		{
			String row = "";
			StringBuilder sc = new StringBuilder();
			sc.append(row);
			for(int i = 0; i < record[l].length; i++)
			{
				double[] rec = record[l][i];
				sc.append(":"+rec[0]+","+rec[1]+","+i);
			}
			lines[l] = sc.toString();
		}
		File file2 = new File(System.getProperty("user.dir")+"//fakeRandomDavideRead.txt");
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println(st.toString());
			writer.println(sb.toString());
			writer.println(sb.toString());
			for(int i = 0; i < lines.length;i++)
			{
				writer.println(lines[i]);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void offLoadRecordInting(int[][][] record, String append) {
		double largestX = 0.00; 
		double smallestX = 100000.00;
		double largestY = 0.00; 
		double smallestY = 100000.00;
		for(int x = 0; x < record.length;x++)
		{
			for(int y = 0; y < record[x].length;y++)
			{
				double rX = record[x][y][0];
				double rY = record[x][y][1];
				if(rX > largestX)
				{
					largestX = rX;
				}
					if(rX < smallestX)
					{
						smallestX = rX;
					}
				
				if(rY > largestY)
				{
					largestY = rY;
				}
					if(rY < smallestY)
					{
						smallestY = rY;
					}
				
			}
		}
		double XInc = Math.sqrt(smallestX*smallestX);
		double XRatio = dimensions/(XInc+largestX);
		double YInc = Math.sqrt(smallestY*smallestY);
		double YRatio = dimensions/(YInc+largestY);
		//translate down and across 5
		for(int x = 0; x < record.length;x++)
		{
			for(int y = 0; y < record[x].length;y++)
			{
				record[x][y][0]= Math.toIntExact((long)(((record[x][y][0]+XInc)*XRatio)));
				record[x][y][1]= Math.toIntExact((long)(((record[x][y][1]+YInc)*YRatio)));
				//record[x][y][0]+=6;
				//record[x][y][1]+=6;
			}
		}
		
		
		
		String name = "fakeRandom"+append;
		String starts = "starting";
		StringBuilder st = new StringBuilder();
		st.append(starts);
		for(int i = 0; i < record.length; i++)
		{
			st.append(","+i);
		}
		String eventVect = "1";
		StringBuilder sb = new StringBuilder();
		sb.append(eventVect);
		for(int i = 0; i < record.length; i++)
		{
			sb.append(","+1);
		}
		String[] lines = new String[record.length];
		for(int l = 0; l < record.length;l++)
		{
			String row = "";
			StringBuilder sc = new StringBuilder();
			sc.append(row);
			for(int i = 0; i < record[l].length; i++)
			{
				int[] rec = record[l][i];
				String stin = ":"+rec[0]+","+rec[1]+","+i;
				sc.append(stin.replace(".0,", ","));
			}
			lines[l] = sc.toString();
		}
		File file2 = new File(System.getProperty("user.dir")+"//"+name+"Unclean.txt");
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println(st.toString());
			writer.println(sb.toString());
			writer.println(sb.toString());
			for(int i = 0; i < lines.length;i++)
			{
				writer.println(lines[i]);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		starts = "";
		lines = new String[record.length];
		for(int l = 0; l < record.length;l++)
		{
			String row = "";
			StringBuilder sc = new StringBuilder();
			sc.append(row);
			int[] rec = record[l][0];
			String stin = ""+rec[0];
			sc.append(stin.replace(".0", ""));
			for(int i = 1; i < record[l].length; i++)
			{
				rec = record[l][i];
				stin = ","+rec[0];
				sc.append(stin.replace(".0", ""));
			}
			lines[l] = sc.toString();
		}
		file2 = new File(System.getProperty("user.dir")+"//"+name+"X.csv");
		try {
			PrintWriter writer = new PrintWriter(file2);
			for(int i = 0; i < lines.length;i++)
			{
				writer.println(lines[i]);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		starts = "";
		lines = new String[record.length];
		for(int l = 0; l < record.length;l++)
		{
			String row = "";
			StringBuilder sc = new StringBuilder();
			sc.append(row);
			int[] rec = record[l][0];
			String stin = ""+rec[1];
			sc.append(stin.replace(".0", ""));
			for(int i = 1; i < record[l].length; i++)
			{
				rec = record[l][i];
				stin = ","+rec[1];
				sc.append(stin.replace(".0", ""));
			}
			lines[l] = sc.toString();
		}
		file2 = new File(System.getProperty("user.dir")+"//"+name+"Y.csv");
		try {
			PrintWriter writer = new PrintWriter(file2);
			for(int i = 0; i < lines.length;i++)
			{
				writer.println(lines[i]);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void offLoadRecordInting(double[][][] record, String append) {
		double largestX = 0.00; 
		double smallestX = 100000.00;
		double largestY = 0.00; 
		double smallestY = 100000.00;
		for(int x = 0; x < record.length;x++)
		{
			for(int y = 0; y < record[x].length;y++)
			{
				double rX = record[x][y][0];
				double rY = record[x][y][1];
				if(rX > largestX)
				{
					largestX = rX;
				}
					if(rX < smallestX)
					{
						smallestX = rX;
					}
				
				if(rY > largestY)
				{
					largestY = rY;
				}
					if(rY < smallestY)
					{
						smallestY = rY;
					}
				
			}
		}
		double XInc = Math.sqrt(smallestX*smallestX);
		double XRatio = dimensions/(XInc+largestX);
		double YInc = Math.sqrt(smallestY*smallestY);
		double YRatio = dimensions/(YInc+largestY);
		//translate down and across 5
		for(int x = 0; x < record.length;x++)
		{
			for(int y = 0; y < record[x].length;y++)
			{
				//record[x][y][0]= ((record[x][y][0]+XInc)*XRatio);
				//record[x][y][1]= ((record[x][y][1]+YInc)*YRatio);
				//record[x][y][0]= (int)(((record[x][y][0]+XInc)*XRatio)+0.5);
				//record[x][y][1]= (int)(((record[x][y][1]+YInc)*YRatio)+0.5);
				//record[x][y][0]= ((record[x][y][0]+XInc)*XRatio);
				//record[x][y][1]= ((record[x][y][1]+YInc)*YRatio);
				record[x][y][0]+= XInc;
				record[x][y][1]+= YInc;
				record[x][y][0]= record[x][y][0]*XRatio;
				record[x][y][1]= record[x][y][1]*YRatio;
				//record[x][y][0]= Math.round(record[x][y][0]);
				//record[x][y][1]= Math.round(record[x][y][1]);
				record[x][y][0]= Math.toIntExact((long) record[x][y][0]);
				record[x][y][1]= Math.toIntExact((long) record[x][y][1]);
				//record[x][y][0]+=6;
				//record[x][y][1]+=6;
			}
		}
		
		
		
		String name = "F"+append; 
		String starts = "starting";
		StringBuilder st = new StringBuilder();
		st.append(starts);
		for(int i = 0; i < record.length; i++)
		{
			st.append(","+i);
		}
		String eventVect = "1";
		StringBuilder sb = new StringBuilder();
		sb.append(eventVect);
		for(int i = 0; i < record.length; i++)
		{
			sb.append(","+1);
		}
		String[] lines = new String[record.length];
		for(int l = 0; l < record.length;l++)
		{
			String row = "";
			StringBuilder sc = new StringBuilder();
			sc.append(row);
			for(int i = 0; i < record[l].length; i++)
			{
				double[] rec = record[l][i];
				String stin = ":"+rec[0]+","+rec[1]+","+i;
				sc.append(stin.replace(".0,", ","));
			}
			lines[l] = sc.toString();
		}
		File file2 = new File(System.getProperty("user.dir")+"//"+name+"Unclean.txt");
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println(st.toString());
			writer.println(sb.toString());
			writer.println(sb.toString());
			for(int i = 0; i < lines.length;i++)
			{
				writer.println(lines[i]);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		starts = "";
		lines = new String[record.length];
		for(int l = 0; l < record.length;l++)
		{
			String row = "";
			StringBuilder sc = new StringBuilder();
			sc.append(row);
			double[] rec = record[l][0];
			String stin = ""+rec[0];
			sc.append(stin.replace(".0", ""));
			for(int i = 1; i < record[l].length; i++)
			{
				rec = record[l][i];
				stin = ","+rec[0];
				sc.append(stin.replace(".0", ""));
			}
			lines[l] = sc.toString();
		}
		file2 = new File(System.getProperty("user.dir")+"//"+name+"X.csv");
		try {
			PrintWriter writer = new PrintWriter(file2);
			for(int i = 0; i < lines.length;i++)
			{
				writer.println(lines[i]);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		starts = "";
		lines = new String[record.length];
		for(int l = 0; l < record.length;l++)
		{
			String row = "";
			StringBuilder sc = new StringBuilder();
			sc.append(row);
			double[] rec = record[l][0];
			String stin = ""+rec[1];
			sc.append(stin.replace(".0", ""));
			for(int i = 1; i < record[l].length; i++)
			{
				rec = record[l][i];
				stin = ","+rec[1];
				sc.append(stin.replace(".0", ""));
			}
			lines[l] = sc.toString();
		}
		file2 = new File(System.getProperty("user.dir")+"//"+name+"Y.csv");
		try {
			PrintWriter writer = new PrintWriter(file2);
			for(int i = 0; i < lines.length;i++)
			{
				writer.println(lines[i]);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void offLoadRecordDoubleing(double[][][] record, String append) {
		double largestX = 0.00; 
		double smallestX = 100000.00;
		double largestY = 0.00; 
		double smallestY = 100000.00;
		for(int x = 0; x < record.length;x++)
		{
			for(int y = 0; y < record[x].length;y++)
			{
				double rX = record[x][y][0];
				double rY = record[x][y][1];
				if(rX > largestX)
				{
					largestX = rX;
				}
					if(rX < smallestX)
					{
						smallestX = rX;
					}
				
				if(rY > largestY)
				{
					largestY = rY;
				}
					if(rY < smallestY)
					{
						smallestY = rY;
					}
				
			}
		}
		double XInc = Math.sqrt(smallestX*smallestX);
		double XRatio = dimensions/(XInc+largestX);
		double YInc = Math.sqrt(smallestY*smallestY);
		double YRatio = dimensions/(YInc+largestY);
		//translate down and across 5
		for(int x = 0; x < record.length;x++)
		{
			for(int y = 0; y < record[x].length;y++)
			{
				record[x][y][0]= ((record[x][y][0]+XInc)*XRatio);
				record[x][y][1]= ((record[x][y][1]+YInc)*YRatio);
				//record[x][y][0]+=6;
				//record[x][y][1]+=6;
			}
		}
		
		
		
		String name = "fakeRandomDouble"+append;
		String starts = "starting";
		StringBuilder st = new StringBuilder();
		st.append(starts);
		for(int i = 0; i < record.length; i++)
		{
			st.append(","+i);
		}
		String eventVect = "1";
		StringBuilder sb = new StringBuilder();
		sb.append(eventVect);
		for(int i = 0; i < record.length; i++)
		{
			sb.append(","+1);
		}
		String[] lines = new String[record.length];
		for(int l = 0; l < record.length;l++)
		{
			String row = "";
			StringBuilder sc = new StringBuilder();
			sc.append(row);
			for(int i = 0; i < record[l].length; i++)
			{
				double[] rec = record[l][i];
				String stin = ":"+rec[0]+","+rec[1]+","+i;
				sc.append(stin.replace(".0,", ","));
			}
			lines[l] = sc.toString();
		}
		File file2 = new File(System.getProperty("user.dir")+"//"+name+"Unclean.txt");
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println(st.toString());
			writer.println(sb.toString());
			writer.println(sb.toString());
			for(int i = 0; i < lines.length;i++)
			{
				writer.println(lines[i]);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		starts = "";
		lines = new String[record.length];
		for(int l = 0; l < record.length;l++)
		{
			String row = "";
			StringBuilder sc = new StringBuilder();
			sc.append(row);
			double[] rec = record[l][0];
			String stin = ""+rec[0];
			sc.append(stin.replace(".0", ""));
			for(int i = 1; i < record[l].length; i++)
			{
				rec = record[l][i];
				stin = ","+rec[0];
				sc.append(stin.replace(".0", ""));
			}
			lines[l] = sc.toString();
		}
		file2 = new File(System.getProperty("user.dir")+"//"+name+"X.csv");
		try {
			PrintWriter writer = new PrintWriter(file2);
			for(int i = 0; i < lines.length;i++)
			{
				writer.println(lines[i]);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		starts = "";
		lines = new String[record.length];
		for(int l = 0; l < record.length;l++)
		{
			String row = "";
			StringBuilder sc = new StringBuilder();
			sc.append(row);
			double[] rec = record[l][0];
			String stin = ""+rec[1];
			sc.append(stin.replace(".0", ""));
			for(int i = 1; i < record[l].length; i++)
			{
				rec = record[l][i];
				stin = ","+rec[1];
				sc.append(stin.replace(".0", ""));
			}
			lines[l] = sc.toString();
		}
		file2 = new File(System.getProperty("user.dir")+"//"+name+"Y.csv");
		try {
			PrintWriter writer = new PrintWriter(file2);
			for(int i = 0; i < lines.length;i++)
			{
				writer.println(lines[i]);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
