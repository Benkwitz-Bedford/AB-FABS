package file_manipulation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import singleton_holders.SingletonHolder;
import stat_data_holders.AggregateSnapShotObfust;
import stat_data_holders.SnapShotObfust;

public class DataShuffler {

	private ArrayList<ArrayList<String>> snapFiles = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> snapPopFiles = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> snapHeatFiles = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> snapPosiFiles = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> snapDirectionalHeatFiles = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> snapDirectionalConstantHeatFiles = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> snapRelativeHeatFiles = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> snapAngleHeatFiles = new ArrayList<ArrayList<String>>();
	
	private ArrayList<String> snapMetaFiles = new ArrayList<String>();
	private ArrayList<String> snapPopMetaFiles = new ArrayList<String>();
	private ArrayList<String> snapHeatMetaFiles = new ArrayList<String>();
	private ArrayList<String> snapDirectionalMetaFiles = new ArrayList<String>();
	private ArrayList<String> snapDirectionalConstantMetaFiles = new ArrayList<String>();
	private ArrayList<String> snapRelativeMetaFiles = new ArrayList<String>();
	private ArrayList<String> snapAngleMetaFiles = new ArrayList<String>();
	
	private ArrayList<String> snapTables = new ArrayList<String>();
	private String snapMetaTable = "";
	private ArrayList<String> snapTopTables = new ArrayList<String>();
	
	File file = new File(System.getProperty("user.dir"));
	File overAllFolder = new File(file.getAbsolutePath()+"//Shuffle folder");
	File snapFolder = new File(overAllFolder.getAbsolutePath()+"//snap files");
	File popFolder = new File(overAllFolder.getAbsolutePath()+"//snap pops");
	File heatFolder = new File(overAllFolder.getAbsolutePath()+"//snap heats");
	File posiFolder = new File(overAllFolder.getAbsolutePath()+"//cell positions");
	File directionalHeatFolder = new File(overAllFolder.getAbsolutePath()+"//snap directional heats");
	File directionalConstantHeatFolder = new File(overAllFolder.getAbsolutePath()+"//snap directional constant heats");
	File relativeHeatFolder = new File(overAllFolder.getAbsolutePath()+"//snap relative heats");
	File angleHeatFolder = new File(overAllFolder.getAbsolutePath()+"//snap angle heats");
	File snapMetaFolder = new File(overAllFolder.getAbsolutePath()+"//snap meta files");
	File popMetaFolder = new File(overAllFolder.getAbsolutePath()+"//snap meta pops");
	File heatMetaFolder = new File(overAllFolder.getAbsolutePath()+"//snap meta heats");
	File directionalMetaFolder = new File(overAllFolder.getAbsolutePath()+"//snap meta directionals");
	File directionalConstantMetaFolder = new File(overAllFolder.getAbsolutePath()+"//snap meta directionalConstants");
	File relativeMetaFolder = new File(overAllFolder.getAbsolutePath()+"//snap meta relatives");
	File angleMetaFolder = new File(overAllFolder.getAbsolutePath()+"//snap meta angles");
	File snapTableFolder = new File(overAllFolder.getAbsolutePath()+"//snap tables");
	File snapMetaTableFolder = new File(overAllFolder.getAbsolutePath()+"//snap meta tables");
	File snapTopTableFolder = new File(overAllFolder.getAbsolutePath()+"//snap top tables");
	
	private String name;
	

	int dShuffleSize = 157;
	
	
	public DataShuffler(String name)
	{
	File overAllFolder = new File(file.getAbsolutePath()+"//Shuffle folder");
	overAllFolder.mkdir();
	deleteFolder(overAllFolder);
	overAllFolder.mkdir();
	snapFolder.mkdir();
	popFolder.mkdir();
	heatFolder.mkdir();
	posiFolder.mkdir();
	directionalHeatFolder.mkdir();
	directionalConstantHeatFolder.mkdir();
	relativeHeatFolder.mkdir();
	angleHeatFolder.mkdir();
	snapMetaFolder.mkdir();
	popMetaFolder.mkdir();
	heatMetaFolder.mkdir();
	directionalMetaFolder.mkdir();
	directionalConstantMetaFolder.mkdir();
	relativeMetaFolder.mkdir();
	angleMetaFolder.mkdir();
	snapTableFolder.mkdir();
	snapMetaTableFolder.mkdir();
	snapTopTableFolder.mkdir();
	this.name = name;
	}
	
	public DataShuffler(File file) {
		overAllFolder = file;
		snapFolder = new File(overAllFolder.getAbsolutePath()+"//snap files");
		popFolder = new File(overAllFolder.getAbsolutePath()+"//snap pops");
		heatFolder = new File(overAllFolder.getAbsolutePath()+"//snap heats");
		posiFolder = new File(overAllFolder.getAbsolutePath()+"//cell positions");
		snapMetaFolder = new File(overAllFolder.getAbsolutePath()+"//snap meta files");
		popMetaFolder = new File(overAllFolder.getAbsolutePath()+"//snap meta pops");
		heatMetaFolder = new File(overAllFolder.getAbsolutePath()+"//snap meta heats");
		snapTableFolder = new File(overAllFolder.getAbsolutePath()+"//snap tables");
		snapMetaTableFolder = new File(overAllFolder.getAbsolutePath()+"//snap meta tables");
		snapTopTableFolder = new File(overAllFolder.getAbsolutePath()+"//snap top tables");
		snapFiles = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < snapFolder.listFiles().length;i++)
		{
			ArrayList<String> snaps = new ArrayList<String>();
			for(int l = 0; l < snapFolder.listFiles()[i].listFiles().length;l++)
			{
				snaps.add(snapFolder.listFiles()[i].listFiles()[l].getAbsolutePath());
			}
			snapFiles.add(snaps);
		}
		snapPopFiles = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < popFolder.listFiles().length;i++)
		{
			ArrayList<String> snaps = new ArrayList<String>();
			for(int l = 0; l < popFolder.listFiles()[i].listFiles().length;l++)
			{
				snaps.add(popFolder.listFiles()[i].listFiles()[l].getAbsolutePath());
			}
			snapPopFiles.add(snaps);
		}
		snapHeatFiles = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < heatFolder.listFiles().length;i++)
		{
			ArrayList<String> snaps = new ArrayList<String>();
			for(int l = 0; l < heatFolder.listFiles()[i].listFiles().length;l++)
			{
				snaps.add(heatFolder.listFiles()[i].listFiles()[l].getAbsolutePath());
			}
			snapHeatFiles.add(snaps);
		}
		snapPosiFiles = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < posiFolder.listFiles().length;i++)
		{
			ArrayList<String> snaps = new ArrayList<String>();
			for(int l = 0; l < posiFolder.listFiles()[i].listFiles().length;l++)
			{
				snaps.add(posiFolder.listFiles()[i].listFiles()[l].getAbsolutePath());
			}
			snapPosiFiles.add(snaps);
		}
		
		
		snapMetaFiles = new ArrayList<String>();
		for(int i = 0; i <snapMetaFolder.listFiles().length;i++)
		{
			snapMetaFiles.add(snapMetaFolder.listFiles()[i].getAbsolutePath());
		}
		snapPopMetaFiles = new ArrayList<String>();
		for(int i = 0; i <popMetaFolder.listFiles().length;i++)
		{
			snapPopMetaFiles.add(popMetaFolder.listFiles()[i].getAbsolutePath());
		}
		snapHeatMetaFiles = new ArrayList<String>();
		for(int i = 0; i <heatMetaFolder.listFiles().length;i++)
		{
			snapHeatMetaFiles.add(heatMetaFolder.listFiles()[i].getAbsolutePath());
		}
		
		snapTables = new ArrayList<String>();
		for(int i = 0; i <snapTableFolder.listFiles().length;i++)
		{
			snapTables.add(snapTableFolder.listFiles()[i].getAbsolutePath());
		}
		snapMetaTable = snapMetaTableFolder.listFiles()[0].getAbsolutePath();
		snapTopTables = new ArrayList<String>();
		for(int i = 0; i <snapTopTableFolder.listFiles().length;i++)
		{
			snapTopTables.add(snapTopTableFolder.listFiles()[i].getAbsolutePath());
		}
		
	}

	public static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}
	
	
	
	public void shuckObfust(ArrayList<SnapShotObfust> snap) 
	{
		File runSnapDir = new File(snapFolder.getAbsolutePath()+"//"+"run "+snapFiles.size()+" snaps");
		File runPopDir = new File(popFolder.getAbsolutePath()+"//"+"run "+snapPopFiles.size()+" pops");
		File runHeatDir = new File(heatFolder.getAbsolutePath()+"//"+"run "+snapHeatFiles.size()+" heats");
		File runPosiDir = new File(posiFolder.getAbsolutePath()+"//"+"run "+snapPosiFiles.size()+" heats");
		File runDirectionalHeatDir = new File(directionalHeatFolder.getAbsolutePath()+"//"+"run "+snapDirectionalHeatFiles.size()+" directionalHeats");
		File runDirectionalConstantHeatDir = new File(directionalConstantHeatFolder.getAbsolutePath()+"//"+"run "+snapDirectionalConstantHeatFiles.size()+" directionalConstantHeats");
		File runRelativeHeatDir = new File(relativeHeatFolder.getAbsolutePath()+"//"+"run "+snapRelativeHeatFiles.size()+" relativeHeats");
		File runAngleHeatDir = new File(angleHeatFolder.getAbsolutePath()+"//"+"run "+snapAngleHeatFiles.size()+" angleHeats");
		
		runSnapDir.mkdir();
		runPopDir.mkdir();
		runHeatDir.mkdir();
		runPosiDir.mkdir();
		runDirectionalHeatDir.mkdir();
		runDirectionalConstantHeatDir.mkdir();
		runRelativeHeatDir.mkdir();
		runAngleHeatDir.mkdir();
		ArrayList<String> snaps = new ArrayList<String>();
		ArrayList<String> pops = new ArrayList<String>();
		ArrayList<String> heats = new ArrayList<String>();
		ArrayList<String> posi = new ArrayList<String>();
		ArrayList<String> directionalHeats = new ArrayList<String>();
		ArrayList<String> directionalConstantHeats = new ArrayList<String>();
		ArrayList<String> relativeHeats = new ArrayList<String>();
		ArrayList<String> angleHeats = new ArrayList<String>();
		for(int i = 0 ; i < snap.size(); i++)
		{
			File runSnap = new File(runSnapDir.getAbsolutePath()+"//"+"snap "+i+" snaps");
			File runPop = new File(runPopDir.getAbsolutePath()+"//"+"run "+i+" pops");
			File runHeat = new File(runHeatDir.getAbsolutePath()+"//"+"run "+i+" heats");
			File runPosi = new File(runPosiDir.getAbsolutePath()+"//"+"run "+i+" posi");
			File runDirectionalHeat = new File(runDirectionalHeatDir.getAbsolutePath()+"//"+"run "+i+" Directional heats");
			File runDirectionalConstantHeat = new File(runDirectionalConstantHeatDir.getAbsolutePath()+"//"+"run "+i+" Directional Constant heats");
			File runRelativeHeat = new File(runRelativeHeatDir.getAbsolutePath()+"//"+"run "+i+" Relative heats");
			File runAngleHeat = new File(runAngleHeatDir.getAbsolutePath()+"//"+"run "+i+" Angle heats");
			snaps.add(runSnap.getAbsolutePath());
			pops.add(runPop.getAbsolutePath());
			heats.add(runHeat.getAbsolutePath());
			posi.add(runPosi.getAbsolutePath());
			directionalHeats.add(runDirectionalHeat.getAbsolutePath());
			directionalConstantHeats.add(runDirectionalConstantHeat.getAbsolutePath());
			relativeHeats.add(runRelativeHeat.getAbsolutePath());
			angleHeats.add(runAngleHeat.getAbsolutePath());
			
			ArrayList<String> snapReport = snap.get(i).getDataReport();
			ArrayList<String> snapPopReport = snap.get(i).getFastReport(snap.get(i).getPopSectors());
			ArrayList<String> snapHeatReport = snap.get(i).getFastReport(snap.get(i).getPositions());
			ArrayList<String> snapPosiReport = snap.get(i).getPosiReport();
			//ArrayList<String> snapFastHeatReport = snap.get(i).getFastReport(snap.get(i).getPositions());
			ArrayList<String> snapHeatDirectionalReport = snap.get(i).getFastReport(snap.get(i).getDirectionHeat());
			ArrayList<String> snapHeatDirectionalConstantReport = snap.get(i).getFastReport(snap.get(i).getDirectionConstantHeat());
			ArrayList<String> snapHeatRelativeReport = snap.get(i).getFastReport(snap.get(i).getRelativeHeat());
			ArrayList<String> snapHeatAngleReport = snap.get(i).getFastReport(snap.get(i).getAngleHeat());
			
			PrintWriter writer;
			try {
				writer = new PrintWriter(runSnap);

				for(int m = 0 ; m < snapReport.size();m++)
				{
					writer.println(snapReport.get(m));
				}
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				writer = new PrintWriter(runPop);
				writer.println(snapPopReport.get(0));
				for(int m = 1 ; m < snapPopReport.size();m++)
				{
					writer.println(snapPopReport.get(m));
				}
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				writer = new PrintWriter(runHeat);
				writer.println(snapHeatReport.get(0));
				for(int m = 1 ; m < snapHeatReport.size();m++)
				{
					writer.println(snapHeatReport.get(m));
				}
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				writer = new PrintWriter(runPosi);
				writer.println(snapPosiReport.get(0));
				for(int m = 1 ; m < snapPosiReport.size();m++)
				{
					writer.println(snapPosiReport.get(m));
				}
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				writer = new PrintWriter(runDirectionalHeat);
				writer.println(snapHeatDirectionalReport.get(0));
				for(int m = 1 ; m < snapHeatDirectionalReport.size();m++)
				{
					writer.println(snapHeatDirectionalReport.get(m));
				}
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				writer = new PrintWriter(runDirectionalConstantHeat);
				writer.println(snapHeatDirectionalConstantReport.get(0));
				for(int m = 1 ; m < snapHeatDirectionalConstantReport.size();m++)
				{
					writer.println(snapHeatDirectionalConstantReport.get(m));
				}
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				writer = new PrintWriter(runRelativeHeat);
				writer.println(snapHeatRelativeReport.get(0));
				for(int m = 1 ; m < snapHeatRelativeReport.size();m++)
				{
					writer.println(snapHeatRelativeReport.get(m));
				}
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				writer = new PrintWriter(runAngleHeat);
				writer.println(snapHeatAngleReport.get(0));
				for(int m = 1 ; m < snapHeatAngleReport.size();m++)
				{
					writer.println(snapHeatAngleReport.get(m));
				}
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		snapFiles.add(snaps);
		snapPopFiles.add(pops);
		snapHeatFiles.add(heats);
		snapPosiFiles.add(posi);
		snapDirectionalHeatFiles.add(directionalHeats);
		snapDirectionalConstantHeatFiles.add(directionalConstantHeats);
		snapRelativeHeatFiles.add(relativeHeats);
		snapAngleHeatFiles.add(angleHeats);
		
		
		
	}
	
	public void shuckObfustNoImg(ArrayList<SnapShotObfust> snap, double similarity) {
		File runSnapDir = new File(snapFolder.getAbsolutePath()+"//"+"run "+snapFiles.size()+" snaps similarity "+similarity);
		runSnapDir.mkdir();
		ArrayList<String> snaps = new ArrayList<String>();
		for(int i = 0 ; i < snap.size(); i++)
		{
			File runSnap = new File(runSnapDir.getAbsolutePath()+"//"+"snap "+i+" snaps");
			snaps.add(runSnap.getAbsolutePath());
			
			ArrayList<String> snapReport = snap.get(i).getDataReport();
			
			PrintWriter writer;
			try {
				writer = new PrintWriter(runSnap);
				
				for(int m = 0 ; m < snapReport.size();m++)
				{
					writer.println(snapReport.get(m));
				}
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		snapFiles.add(snaps);
		
		
		
	}
	
	public ArrayList<Double> getObfustValues(String string) {
		File file = new File(string);
		ArrayList<Double> values = new ArrayList<Double>();
		Scanner read;
		try
		{
			read = new Scanner(file);
			String[] str = read.nextLine().split(";");
			do
			{
				values.add(Double.parseDouble(str[1]));
				str = read.nextLine().split(";");
			}while(str.length >1);
			
		}catch(FileNotFoundException e)
		{
			
		}
		return values;
	}
	
	public ArrayList<String> getObfustValueNames(String string) {
		File file = new File(string);
		ArrayList<String> values = new ArrayList<String>();
		Scanner read;
		try
		{
			read = new Scanner(file);
			String[] str = read.nextLine().split(";");
			do
			{
				values.add(str[0]);
				str = read.nextLine().split(";");
			}while(str.length >1);
			
		}catch(FileNotFoundException e)
		{
			
		}
		return values;
	}
	
	
	
	public void metaShuckObfust(AggregateSnapShotObfust shot, int i) 
	{		
		File runSnap = new File(snapMetaFolder.getAbsolutePath()+"//"+"snap "+i+" snaps");
		File runPop = new File(popMetaFolder.getAbsolutePath()+"//"+"run "+i+" pops");
		File runDirectionalHeat = new File(directionalMetaFolder.getAbsolutePath()+"//"+"run "+i+" directionals");
		File runDirectionalConstantHeat = new File(directionalConstantMetaFolder.getAbsolutePath()+"//"+"run "+i+" directionalConstants");
		File runRelativeHeat = new File(relativeMetaFolder.getAbsolutePath()+"//"+"run "+i+" relatives");
		File runAngleHeat = new File(angleMetaFolder.getAbsolutePath()+"//"+"run "+i+" angles");
		File runHeat = new File(heatMetaFolder.getAbsolutePath()+"//"+"run "+i+" heats");
		snapMetaFiles.add(runSnap.getAbsolutePath());
		snapPopMetaFiles.add(runPop.getAbsolutePath());
		snapHeatMetaFiles.add(runHeat.getAbsolutePath());
		snapDirectionalMetaFiles.add(runDirectionalHeat.getAbsolutePath());
		snapDirectionalConstantMetaFiles.add(runDirectionalConstantHeat.getAbsolutePath());
		snapRelativeMetaFiles.add(runRelativeHeat.getAbsolutePath());
		snapAngleMetaFiles.add(runAngleHeat.getAbsolutePath());
		
		ArrayList<String> snapReport = shot.getFullDataReport();
		ArrayList<String> snapPopReport = shot.getFastReport(shot.getPopSectors());
		ArrayList<String> snapHeatReport = shot.getFastReport(shot.getPositions());
		ArrayList<String> snapDirectionalHeatReport = shot.getFastReport(shot.getDirectionHeat());
		ArrayList<String> snapDirectionalConstantHeatReport = shot.getFastReport(shot.getDirectionConstantHeat());
		ArrayList<String> snapRelativeHeatReport = shot.getFastReport(shot.getRelativeHeat());
		ArrayList<String> snapAngleHeatReport = shot.getFastReport(shot.getAngleHeat());
		
		PrintWriter writer;
		try {
			writer = new PrintWriter(runSnap);
			
			for(int m = 0 ; m < snapReport.size();m++)
			{
				writer.println(snapReport.get(m));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer = new PrintWriter(runPop);
			for(int m = 0 ; m < snapPopReport.size();m++)
			{
				writer.println(snapPopReport.get(m));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer = new PrintWriter(runHeat);
			for(int m = 0 ; m < snapHeatReport.size();m++)
			{
				writer.println(snapHeatReport.get(m));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer = new PrintWriter(runDirectionalHeat);
			for(int m = 0 ; m < snapDirectionalHeatReport.size();m++)
			{
				writer.println(snapDirectionalHeatReport.get(m));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}try {
			writer = new PrintWriter(runDirectionalConstantHeat);
			for(int m = 0 ; m < snapDirectionalConstantHeatReport.size();m++)
			{
				writer.println(snapDirectionalConstantHeatReport.get(m));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}try {
			writer = new PrintWriter(runRelativeHeat);
			for(int m = 0 ; m < snapRelativeHeatReport.size();m++)
			{
				writer.println(snapRelativeHeatReport.get(m));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}try {
			writer = new PrintWriter(runAngleHeat);
			for(int m = 0 ; m < snapAngleHeatReport.size();m++)
			{
				writer.println(snapAngleHeatReport.get(m));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public ArrayList<ArrayList<String>> getSnapFiles() {
		return snapFiles;
	}

	public void setSnapFiles(ArrayList<ArrayList<String>> snapFiles) {
		this.snapFiles = snapFiles;
	}

	public ArrayList<ArrayList<String>> getSnapPopFiles() {
		return snapPopFiles;
	}

	public void setSnapPopFiles(ArrayList<ArrayList<String>> snapPopFiles) {
		this.snapPopFiles = snapPopFiles;
	}

	public ArrayList<ArrayList<String>> getSnapHeatFiles() {
		return snapHeatFiles;
	}

	public void setSnapHeatFiles(ArrayList<ArrayList<String>> snapHeatFiles) {
		this.snapHeatFiles = snapHeatFiles;
	}
	
	public ArrayList<ArrayList<String>> getSnapPosiFiles() {
		return snapPosiFiles;
	}

	public void setSnapPosiFiles(ArrayList<ArrayList<String>> snapPosiFiles) {
		this.snapPosiFiles = snapPosiFiles;
	}

	public ArrayList<String> getSnapMetaFiles() {
		return snapMetaFiles;
	}

	public void setSnapMetaFiles(ArrayList<String> snapMetaFiles) {
		this.snapMetaFiles = snapMetaFiles;
	}

	public ArrayList<String> getSnapPopMetaFiles() {
		return snapPopMetaFiles;
	}

	public void setSnapPopMetaFiles(ArrayList<String> snapPopMetaFiles) {
		this.snapPopMetaFiles = snapPopMetaFiles;
	}

	public ArrayList<String> getSnapHeatMetaFiles() {
		return snapHeatMetaFiles;
	}

	public void setSnapHeatMetaFiles(ArrayList<String> snapHeatMetaFiles) {
		this.snapHeatMetaFiles = snapHeatMetaFiles;
	}

	public ArrayList<String> getSnapTables() {
		return snapTables;
	}

	public void setSnapTables(ArrayList<String> snapTables) {
		this.snapTables = snapTables;
	}

	public String getSnapMetaTables() {
		return snapMetaTable;
	}

	public void setSnapMetaTables(String snapMetaTables) {
		this.snapMetaTable = snapMetaTables;
	}

	public String getValueSnap(String swatch, int l, int i) {
		
		String returner = "crap";
		switch (swatch) {
			case "totalDistanceTravelled": 
				returner = this.fishOutSnap(0,l,i);
				break;
			case "averageDistanceTravelled": 
				returner = this.fishOutSnap(1,l,i);
				break;
					//collisions
			case "totalCollisions": 
				returner = this.fishOutSnap(2,l,i);
				break;
			case "averageCollisions": 
				returner = this.fishOutSnap(3,l,i);
				break;
					//turns
			case "totalTurns": 
				returner = this.fishOutSnap(4,l,i); 
				break;
			case "averageTurns": 
				returner = this.fishOutSnap(5,l,i);
				break;
					//times chosen bez
			case "totalChosenBez": 
				returner = this.fishOutSnap(6,l,i);
				break;
			case "averageChosenBez": 
				returner = this.fishOutSnap(7,l,i);
				break;
					//deaths
			case "totalDeaths": 
				returner = this.fishOutSnap(8,l,i);
				break;
			case "averageDeaths": 
				returner = this.fishOutSnap(9,l,i);
				break;
					//births
			case "totalBirths": 
				returner = this.fishOutSnap(10,l,i);
				break;
			case "averageBirths": 
				returner = this.fishOutSnap(11,l,i);
				break;
					//cell number
			case "totalCells": 
				returner = this.fishOutSnap(12,l,i); 
				break;
			case "averageCells": 
				returner = this.fishOutSnap(13,l,i);
				break;
					//increments
			case "increments": 
				returner = this.fishOutSnap(14,l,i);
				break;
					//jumps
			case "jumps": 
				returner = this.fishOutSnap(15,l,i); 
				break;		
			
				//collision turn
			case "collisionSectOne": 
				returner = this.fishOutSnap(16,l,i); 
				break;
			case "collisionSectTwo": 
				returner = this.fishOutSnap(17,l,i); 
				break;
			case "collisionSectThree": 
				returner = this.fishOutSnap(18,l,i); 
				break;
			case "collisionSectFour": 
				returner = this.fishOutSnap(19,l,i); 
				break;
			case "collisionSectFive": 
				returner = this.fishOutSnap(20,l,i); 
				break;
			case "collisionSectSix": 
				returner = this.fishOutSnap(21,l,i); 
				break;
			case "collisionSectSeven": 
				returner = this.fishOutSnap(22,l,i); 
				break;
			case "collisionSectEight": 
				returner = this.fishOutSnap(23,l,i);
				break;
			
				//bounce turn
			case "bounceSectOne": 
				returner = this.fishOutSnap(24,l,i); 
				break;
			case "bounceSectTwo": 
				returner = this.fishOutSnap(25,l,i); 
				break;
			case "bounceSectThree": 
				returner = this.fishOutSnap(26,l,i); 
				break;
			case "bounceSectFour": 
				returner = this.fishOutSnap(27,l,i); 
				break;
			case "bounceSectFive": 
				returner = this.fishOutSnap(28,l,i); 
				break;
			case "bounceSectSix": 
				returner = this.fishOutSnap(29,l,i); 
				break;
			case "bounceSectSeven": 
				returner = this.fishOutSnap(30,l,i); 
				break;
			case "bounceSectEight": 
				returner = this.fishOutSnap(31,l,i); 
				break;
			
				//chosen turn
			case "chosenSectOne": 
				returner = this.fishOutSnap(32,l,i); 
				break;
			case "chosenSectTwo": 
				returner = this.fishOutSnap(33,l,i); 
				break;
			case "chosenSectThree": 
				returner = this.fishOutSnap(34,l,i); 
				break;
			case "chosenSectFour": 
				returner = this.fishOutSnap(35,l,i); 
				break;
			case "chosenSectFive": 
				returner = this.fishOutSnap(36,l,i); 
				break;
			case "chosenSectSix": 
				returner = this.fishOutSnap(37,l,i); 
				break;
			case "chosenSectSeven": 
				returner = this.fishOutSnap(38,l,i); 
				break;
			case "chosenSectEight": 
				returner = this.fishOutSnap(39,l,i); 
				break;
				
			//clustering metrics
			case "totalNearest": 
				returner = this.fishOutSnap(40,l,i); 
				break;
			case "averageNearest": 
				returner = this.fishOutSnap(41,l,i); 
				break;
			case "closest": 
				returner = this.fishOutSnap(42,l,i); 
				break;
			case "furthest": 
				returner = this.fishOutSnap(43,l,i); 
				break;
			
		}
		return returner;
	}
	
public String getValueMetaSnap(String swatch, int l) {
		
		String returner = "crap";
		int iterator = 0;
		if(swatch.equals("totalDistanceTravelled")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageDistanceTravelled")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
				//collisions
		if(swatch.equals("totalCollisions")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageCollisions")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
				//turns
		if(swatch.equals("totalTurns")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("averageTurns")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
				//times chosen bez
		if(swatch.equals("totalChosenBez")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageChosenBez")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
				//deaths
		if(swatch.equals("totalDeaths")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageDeaths")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
				//births
		if(swatch.equals("totalBirths")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageBirths")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
				//cell number
		if(swatch.equals("totalCells")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("averageCells")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
				//increments
		if(swatch.equals("increments")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
				//jumps
		if(swatch.equals("jumps")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;		
		
			//collision turn
		if(swatch.equals("collisionSectOne")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("collisionSectTwo")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("collisionSectThree")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("collisionSectFour")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("collisionSectFive")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("collisionSectSix")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("collisionSectSeven")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("collisionSectEight")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		
			//bounce turn
		if(swatch.equals("bounceSectOne")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("bounceSectTwo")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("bounceSectThree")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("bounceSectFour")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("bounceSectFive")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("bounceSectSix")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("bounceSectSeven")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("bounceSectEight")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		
			//chosen turn
		if(swatch.equals("chosenSectOne")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("chosenSectTwo")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("chosenSectThree")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("chosenSectFour")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("chosenSectFive")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("chosenSectSix")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("chosenSectSeven")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("chosenSectEight")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
			
		//clustering metrics
		if(swatch.equals("totalNearest")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("averageNearest")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("closest")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("furthest")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator+= 55;
			
			//distance travelled
		if(swatch.equals("averageTotalDistanceTravelled")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("averageAverageDistanceTravelled")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDTotalDistanceTravelled")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDAverageDistanceTravelled")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
				//collisions
		if(swatch.equals("averageTotalCollisions")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageAverageCollisions")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDTotalCollisions")){
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDAverageCollisions")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
				//turns
		if(swatch.equals("averageTotalTurns")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("averageAverageTurns")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalTurns")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDAverageTurns")){
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
				//times chosen bez
		if(swatch.equals("averageTotalChosenBez")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageAverageChosenBez")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalChosenBez")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDAverageChosenBez")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
				//deaths
		if(swatch.equals("averageTotalDeaths")){
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("averageAverageDeaths")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalDeaths")){
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDAverageDeaths")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
				//births
		if(swatch.equals("averageTotalBirths")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageAverageBirths")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalBirths")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDAverageBirths")){
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
				//cell number
		if(swatch.equals("averageTotalCells")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("averageAverageCells")){
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDTotalCells")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDAverageCells")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		
		//turns
		//collision turn
		if(swatch.equals("averageTotalCollisionSectOne")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalCollisionSectOne")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalCollisionSectTwo")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalCollisionSectTwo")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalCollisionSectThree")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalCollisionSectThree")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalCollisionSectFour")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalCollisionSectFour")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalCollisionSectFive")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalCollisionSectFive")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalCollisionSectSix")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalCollisionSectSix")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalCollisionSectSeven")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDTotalCollisionSectSeven")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalCollisionSectEight")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalCollisionSectEight")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		
			//bounce turn
		if(swatch.equals("averageTotalBounceSectOne")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalBounceSectOne")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalBounceSectTwo")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalBounceSectTwo")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalBounceSectThree")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDTotalBounceSectThree")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("averageTotalBounceSectFour")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDTotalBounceSectFour")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalBounceSectFive")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalBounceSectFive")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("averageTotalBounceSectSix")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDTotalBounceSectSix")){
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("averageTotalBounceSectSeven")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalBounceSectSeven")){
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("averageTotalBounceSectEight")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDTotalBounceSectEight")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		
			//chosen turn
		if(swatch.equals("averageTotalChosenSectOne")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalChosenSectOne")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalChosenSectTwo")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalChosenSectTwo")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalChosenSectThree")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalChosenSectThree")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalChosenSectFour")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalChosenSectFour")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalChosenSectFive")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalChosenSectFive")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalChosenSectSix")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalChosenSectSix")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageTotalChosenSectSeven")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalChosenSectSeven")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("averageTotalChosenSectEight")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("sDTotalChosenSectEight")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		
			//clustering metrics
		if(swatch.equals("averageTotalNearest")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageAverageNearest")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageClosest")){ 
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		if(swatch.equals("averageFurthest")){
			returner = this.fishOutMetaSnap(iterator,l);
			}else{ iterator++;
		
		if(swatch.equals("sDTotalNearest")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDAverageNearest")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDClosest")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}else{ iterator++;
		if(swatch.equals("sDFurthest")){ 
			returner = this.fishOutMetaSnap(iterator,l); 
			}
		}
			}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}
			//fucking mess but case statements don't allow iterator++ and I'm not incrementing 200 values every time I want to add a new stat ffs
		return returner;
	}

	public String fishOutSnap(int a, int b, int c) {
		String line = "broke";
		//System.out.println("fish "+a+" from "+b+" "+c);
		/*if(a ==0)
		{
			try {
				try (Stream<String> lines = Files.lines(Paths.get(snapFiles.get(b).get(c)))) {
				    line = lines.findFirst().get();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{*/
			try {
				try (Stream<String> lines = Files.lines(Paths.get(snapFiles.get(b).get(c)))) {
				    line = lines.skip(a).findFirst().get();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
		return line;
	}
	
	public String fishOutMetaSnap(int a, int b) {
		String line = "broke";
		/*if(a ==0)
		{
			try {
				try (Stream<String> lines = Files.lines(Paths.get(snapFiles.get(b).get(c)))) {
				    line = lines.findFirst().get();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{*/
			try {
				try (Stream<String> lines = Files.lines(Paths.get(snapMetaFiles.get(b)))) {
				    line = lines.skip(a).findFirst().get();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
		return line;
	}

	public ArrayList<String> getSnapSettings(int i) {
		ArrayList<String> returner = new ArrayList<String>();
		boolean reading = false;
		Scanner read;
		try
		{
			read = new Scanner(Paths.get(snapFiles.get(0).get(i)));
			
			String line;
			do
			{
				line = read.nextLine();
				if(line.split(":")[0].equals("seed"))
				{
					reading = true;
				}
				if(reading)
				{
					returner.add(line);
				}
			}while(read.hasNextLine());
			
		}catch(IOException e)
		{
			
		}
		/*for(int a = 0; a < 54;a++ )
		{
			returner.add(fishOutSnap(44+a,0,i));
		}*/
		return returner;
	}

	/*public int[][] getHeatGrid(int x, int y) {
		File file = new File(snapHeatFiles.get(x).get(y));
		ArrayList<String> lines = new ArrayList<String>();
		Scanner read;
		try
		{
			read = new Scanner(file);
			do
			{
				lines.add(read.nextLine());
			}while(read.hasNextLine());
			
		}catch(FileNotFoundException e)
		{
			
		}
		String[][] lines2 = new String[lines.size()][];
		for(int i = 0; i < lines.size(); i++)
		{
			lines2[i] = lines.get(i).split(":");
		}
		int[][] grid = new int[lines2.length][lines2[0].length];
		for(int x2 = 0; x2 < lines2[0].length;x2++)
		{
			for(int y2 = 0; y2 < lines2[0].length;y2++)
			{
				grid[x2][y2] = Integer.parseInt(lines2[x2][y2]);
			}
		}
		return grid;
	}*/
	
	public int[][][] getDirectionalGrid(int x, int y, String target) {

		File file = null;
		if(target.equals("Directional"))
		{
			file = new File(snapDirectionalHeatFiles.get(x).get(y));
			
		}else
		{
		if(target.equals("DirectionalConstant"))
		{
			file = new File(snapDirectionalConstantHeatFiles.get(x).get(y));
			
		}else
		{
		if(target.equals("Relative"))
		{
			file = new File(snapRelativeHeatFiles.get(x).get(y));
			
		}else
		{
		if(target.equals("Angle"))
		{
			file = new File(snapAngleHeatFiles.get(x).get(y));
			
		}else
		{
			System.out.println("switch missed line 1293 data shuffle");
		}}}}
			
		ArrayList<String> lines = new ArrayList<String>();
		Scanner read;
		try
		{
			read = new Scanner(file);
			lines.add(read.next());
			do
			{
				lines.add(read.nextLine());
			}while(read.hasNextLine());
			
		}catch(FileNotFoundException e)
		{
			
		}
		String[][] lines2 = new String[lines.size()][];
		for(int i = 0; i < lines.size(); i++)
		{
			lines2[i] = lines.get(i).split(",");
		}
		String[][][]lines3 = new String[lines.size()][lines2[0].length-1][];
		for(int i = 0; i < lines2.length; i++)
		{
			for(int l = 0; l < lines2[i].length-1;l++)
			{
			
				lines3[i][l] = lines2[i][l].split(":");
			}
		}
		int[][][] grid = new int[lines3.length][lines3[0].length][lines3[0][0].length];
		for(int x2 = 0; x2 < lines3[0].length;x2++)
		{
			for(int y2 = 0; y2 < lines3[0].length;y2++)
			{
				for(int z = 0; z < lines3[0][0].length;z++)
				{
					grid[x2][y2][z] = Integer.parseInt(lines3[x2][y2][z]);
				}
			}
		}
		return grid;
	}
	
	public int[][] getHeatGridFast(File file) {
		ArrayList<String> lines = new ArrayList<String>();
		Scanner read;
		try
		{
			read = new Scanner(file);
			lines.add(read.nextLine());
			do
			{
				lines.add(read.nextLine());
			}while(read.hasNextLine());
			
		}catch(FileNotFoundException e)
		{
			
		}
		
		String[][] lines2 = new String[lines.size()][];
		for(int i = 0; i < lines.size(); i++)
		{
			
				lines2[i] = lines.get(i).split(",");
			
		}
		String[][][] chainLines = new String[lines.size()][][];
		for(int i = 0; i < lines2.length; i++)
		{
			if(lines2[i][0].equals(""))
			{
				
			}else
			{
				chainLines[i] = new String[lines2[i].length][];
				for(int l = 0; l < lines2[i].length;l++)
				{ 
					chainLines[i][l] = lines2[i][l].split(":");
				}
			}
		}
		int[][]lines3 = new int[lines.size()][lines.size()];
		for(int x2 = 0; x2 < chainLines.length;x2++)
		{
			if(chainLines[x2] != null)
			{
				for(int y2 = 0; y2 < chainLines[x2].length;y2++)
				{
					if(chainLines[x2][y2][0].equals("null")||chainLines[x2][y2][0].equals("l"))
					{
						
					}else
					{
					int posi = Integer.parseInt(chainLines[x2][y2][0]);
					for(int z = 1; z < chainLines[x2][y2].length;z++)
					{
						lines3[x2][posi+z-1] = Integer.parseInt(chainLines[x2][y2][z]);
					}
					}
				}
			}
		}
		return lines3;
	}
	
	public int[][][] getDirectionalGridFast(int x, int y, String target) {

		File file = null;
		int size = 8;
		if(target.equals("Directional"))
		{
			file = new File(snapDirectionalHeatFiles.get(x).get(y));
			
		}else
		{
		if(target.equals("DirectionalConstant"))
		{
			file = new File(snapDirectionalConstantHeatFiles.get(x).get(y));
			
		}else
		{
		if(target.equals("Relative"))
		{
			file = new File(snapRelativeHeatFiles.get(x).get(y));
			size = 16;
			
		}else
		{
		if(target.equals("Angle"))
		{
			file = new File(snapAngleHeatFiles.get(x).get(y));
			size = 16;
		}else
		{
			System.out.println("switch missed line 1293 data shuffle");
		}}}}
			
		ArrayList<String> lines = new ArrayList<String>();
		Scanner read;
		try
		{
			read = new Scanner(file);
			lines.add(read.nextLine());
			do
			{
				lines.add(read.nextLine());
			}while(read.hasNextLine());
			
		}catch(FileNotFoundException e)
		{
			
		}
		String[][] lines2 = new String[lines.size()][];
		for(int i = 0; i < lines.size(); i++)
		{
			
				lines2[i] = lines.get(i).split(",");
			
		}
		String[][][] chainLines = new String[lines.size()][][];
		for(int i = 0; i < lines2.length; i++)
		{
			if(lines2[i][0].equals(""))
			{
				
			}else
			{
				chainLines[i] = new String[lines2[i].length][];
				for(int l = 0; l < lines2[i].length;l++)
				{ 
					chainLines[i][l] = lines2[i][l].split(":");
				}
			}
		}
		
		int[][][]lines3 = new int[lines.size()][lines.size()][size];
		for(int x2 = 0; x2 < chainLines.length;x2++)
		{
			if(chainLines[x2] != null)
			{
				for(int y2 = 0; y2 < chainLines[x2].length;y2++)
				{
					if(chainLines[x2][y2][0].equals("null")||chainLines[x2][y2][0].equals("l"))
					{
						
					}else
					{
					int posi = Integer.parseInt(chainLines[x2][y2][0]);
					for(int z = 1; z < chainLines[x2][y2].length;z++)
					{
						String[] lin = chainLines[x2][y2][z].split("'");
						int[] linParse = new int[lin.length];
						for(int i = 0; i < lin.length;i++)
						{
							linParse[i] = Integer.parseInt(lin[i]);
						}
						if(posi+z-1>=lines3[x2].length)
						{
							System.out.println("out of bound error d grid fast"+(posi+z-1)+" in "+x2);
						}else
						{
							lines3[x2][posi+z-1] = linParse;
						}
					}
					}
				}
			}
		}
		
		
		return lines3;
	}
	
	public int[][] getMetaHeatGrid(int x) {
		File file = new File(snapHeatMetaFiles.get(x));
		ArrayList<String> lines = new ArrayList<String>();
		Scanner read;
		try
		{
			read = new Scanner(file);
			lines.add(read.next());
			do
			{
				lines.add(read.nextLine());
			}while(read.hasNextLine());
			
		}catch(FileNotFoundException e)
		{
			
		}
		String[][] lines2 = new String[lines.size()][];
		for(int i = 0; i < lines.size(); i++)
		{
			
				lines2[i] = lines.get(i).split(",");
			
		}
		String[][][] chainLines = new String[lines.size()][][];
		for(int i = 0; i < lines2.length; i++)
		{
			if(lines2[i][0].equals(""))
			{
				
			}else
			{
				chainLines[i] = new String[lines2[i].length][];
				for(int l = 0; l < lines2[i].length;l++)
				{ 
					chainLines[i][l] = lines2[i][l].split(":");
				}
			}
		}
		int[][]lines3 = new int[lines.size()][lines.size()];
		for(int x2 = 0; x2 < chainLines.length;x2++)
		{
			if(chainLines[x2] != null)
			{
				for(int y2 = 0; y2 < chainLines[x2].length;y2++)
				{
					if(chainLines[x2][y2][0].equals("null")||chainLines[x2][y2][0].equals("l"))
					{
						
					}else
					{
					int posi = Integer.parseInt(chainLines[x2][y2][0]);
					for(int z = 1; z < chainLines[x2][y2].length;z++)
					{
						lines3[x2][posi+z-1] = Integer.parseInt(chainLines[x2][y2][z]);
					}
					}
				}
			}
		}
		return lines3;
	}
	
	public int[][][] getMetaDirectionalGrid(int x, String target) {

		File file = null;
		if(target.equals("Directional"))
		{
			file = new File(snapDirectionalMetaFiles.get(x));
			
		}else
		{
		if(target.equals("DirectionalConstant"))
		{
			file = new File(snapDirectionalConstantMetaFiles.get(x));
			
		}else
		{
		if(target.equals("Relative"))
		{
			file = new File(snapRelativeMetaFiles.get(x));
			
		}else
		{
		if(target.equals("Angle"))
		{
			file = new File(snapAngleMetaFiles.get(x));
			
		}else
		{
			System.out.println("switch missed line 1579 data shuffel");
		}}}}
			
		ArrayList<String> lines = new ArrayList<String>();
		Scanner read;
		try
		{
			read = new Scanner(file);
			lines.add(read.next());
			do
			{
				lines.add(read.nextLine());
			}while(read.hasNextLine());
			
		}catch(FileNotFoundException e)
		{
			
		}
		String[][] lines2 = new String[lines.size()][];
		for(int i = 0; i < lines.size(); i++)
		{
			
				lines2[i] = lines.get(i).split(",");
			
		}
		String[][][] chainLines = new String[lines.size()][][];
		for(int i = 0; i < lines2.length; i++)
		{
			if(lines2[i][0].equals(""))
			{
				
			}else
			{
				chainLines[i] = new String[lines2[i].length][];
				for(int l = 0; l < lines2[i].length;l++)
				{ 
					chainLines[i][l] = lines2[i][l].split(":");
				}
			}
		}
		int[][][]lines3 = new int[lines.size()][lines.size()][16];
		for(int x2 = 0; x2 < chainLines.length;x2++)
		{
			if(chainLines[x2] != null)
			{
				for(int y2 = 0; y2 < chainLines[x2].length;y2++)
				{
					if(chainLines[x2][y2][0].equals("null")||chainLines[x2][y2][0].equals("l"))
					{
						
					}else
					{
					int posi = Integer.parseInt(chainLines[x2][y2][0]);
					for(int z = 1; z < chainLines[x2][y2].length;z++)
					{
						String[] lin = chainLines[x2][y2][z].split("'");
						int[] linParse = new int[lin.length];
						for(int i = 0; i < lin.length;i++)
						{
							linParse[i] = Integer.parseInt(lin[i]);
						}
						lines3[x2][posi+z-1] = linParse;
					}
					}
				}
			}
		}
		
		
		return lines3;
	}
	
	public int[][] getMetaPopGrid(int x) {
		File file = new File(snapPopMetaFiles.get(x));
		ArrayList<String> lines = new ArrayList<String>();
		Scanner read;
		try
		{
			read = new Scanner(file);
			lines.add(read.next());
			do
			{
				lines.add(read.nextLine());
			}while(read.hasNextLine());
			
		}catch(FileNotFoundException e)
		{
			
		}
		String[][] lines2 = new String[lines.size()][];
		for(int i = 0; i < lines.size(); i++)
		{
			
				lines2[i] = lines.get(i).split(",");
			
		}
		String[][][] chainLines = new String[lines.size()][][];
		for(int i = 0; i < lines2.length; i++)
		{
			if(lines2[i][0].equals(""))
			{
				
			}else
			{
				chainLines[i] = new String[lines2[i].length][];
				for(int l = 0; l < lines2[i].length;l++)
				{ 
					chainLines[i][l] = lines2[i][l].split(":");
				}
			}
		}
		int[][]lines3 = new int[lines.size()][lines.size()];
		for(int x2 = 0; x2 < chainLines.length;x2++)
		{
			if(chainLines[x2] != null)
			{
				for(int y2 = 0; y2 < chainLines[x2].length;y2++)
				{
					if(chainLines[x2][y2][0].equals("null")||chainLines[x2][y2][0].equals("l"))
					{
						
					}else
					{
					int posi = Integer.parseInt(chainLines[x2][y2][0]);
					for(int z = 1; z < chainLines[x2][y2].length;z++)
					{
						lines3[x2][posi+z-1] = Integer.parseInt(chainLines[x2][y2][z]);
					}
					}
				}
			}
		}
		return lines3;
	}
	
	/*public int[][] getPopGrid(int x, int y) {
		File file = new File(snapPopFiles.get(x).get(y));
		ArrayList<String> lines = new ArrayList<String>();
		Scanner read;
		try
		{
			read = new Scanner(file);
			do
			{
				lines.add(read.nextLine());
			}while(read.hasNextLine());
			
		}catch(FileNotFoundException e)
		{
			
		}
		String[][] lines2 = new String[lines.size()][];
		for(int i = 0; i < lines.size(); i++)
		{
			lines2[i] = lines.get(i).split(":");
		}
		int[][] grid = new int[lines2.length][lines2[0].length];
		for(int x2 = 0; x2 < lines2[0].length;x2++)
		{
			for(int y2 = 0; y2 < lines2[0].length;y2++)
			{
				grid[x2][y2] = Integer.parseInt(lines2[x2][y2]);
			}
		}
		return grid;
	}*/

	public void shuckTableStandardTable(ArrayList<ArrayList<String>> table, int i) {
		
		
		File tableData = new File(snapTableFolder.getAbsolutePath()+"//"+"inc "+i+".txt");
		snapTables.add(tableData.getAbsolutePath());
		ArrayList<String> tableLines = new ArrayList<String>();
		for(int m = 0; m < table.get(0).size(); m ++)
		{
			String s = "";
			StringBuilder sb = new StringBuilder();
			sb.append(s);
			for(int n = 0 ; n < table.size(); n++)
			{
				sb.append(table.get(n).get(m));
			}
			tableLines.add(sb.toString());
		}
		tableLines.add("");
		tableLines.add("");
		tableLines.add("");
		
		ArrayList<String> settings = this.getSnapSettings(0);
		
		for(int m = 0; m < settings.size(); m++)
		{
			tableLines.add(settings.get(m));
		}
		try {
			PrintWriter writer = new PrintWriter(tableData);
			for(int m = 0 ; m < tableLines.size();m++)
			{
				writer.println(tableLines.get(m));
			}
			
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
	
	}

	public void shuckMetaTable(ArrayList<ArrayList<String>> table) {
		File tableData = new File(snapMetaTableFolder.getAbsolutePath()+"//"+"meta");
		snapMetaTable = tableData.getAbsolutePath();
		ArrayList<String> tableLines = new ArrayList<String>();
		for(int m = 0; m < table.get(0).size(); m ++)
		{
			String s = "";
			StringBuilder sb = new StringBuilder();
			sb.append(s);
			for(int n = 0 ; n < table.size(); n++)
			{
				sb.append(table.get(n).get(m));
			}
			tableLines.add(sb.toString());
		}
		tableLines.add("");
		tableLines.add("");
		tableLines.add("");
		
		ArrayList<String> settings = this.getSnapSettings(0);
		
		for(int m = 0; m < settings.size(); m++)
		{
			tableLines.add(settings.get(m));
		}
		try {
			PrintWriter writer = new PrintWriter(tableData);
			for(int m = 0 ; m < tableLines.size();m++)
			{
				writer.println(tableLines.get(m));
			}
			
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void shuckTopTable(ArrayList<ArrayList<String>> table, int i) {

		File tableData = new File(snapTopTableFolder.getAbsolutePath()+"//"+"inc "+i+".txt");
		getSnapTopTables().add(tableData.getAbsolutePath());
		ArrayList<String> tableLines = new ArrayList<String>();
		for(int m = 0; m < table.get(0).size(); m ++)
		{
			String s = "";
			StringBuilder sb = new StringBuilder();
			sb.append(s);
			for(int n = 0 ; n < table.size(); n++)
			{
				sb.append(table.get(n).get(m));
			}
			tableLines.add(sb.toString());
		}
		tableLines.add("");
		tableLines.add("");
		tableLines.add("");
		
		ArrayList<String> settings = this.getSnapSettings(i);
		
		for(int m = 0; m < settings.size(); m++)
		{
			tableLines.add(settings.get(m));
		}
		try {
			PrintWriter writer = new PrintWriter(tableData);
			for(int m = 0 ; m < tableLines.size();m++)
			{
				writer.println(tableLines.get(m));
			}
			
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
	
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public ArrayList<String> getSnapTableWithSettings(int i) {
		ArrayList<String> returner = new ArrayList<String>();
		for(int a = 0; a < dShuffleSize;a++ )
		{
			returner.add(fishOutSnapTable(a,i));
		}
		return returner;
	}

	
	private String fishOutSnapTable(int a, int b) {
		String line = "broke";
		
			try {
				try (Stream<String> lines = Files.lines(Paths.get(snapTables.get(b)))) {
				    line = lines.skip(a).findFirst().get();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return line;
	}
	
	public ArrayList<String> getMetaTableWithoutSettings() {
		ArrayList<String> returner = new ArrayList<String>();
		
		Scanner read;
		try {
			read = new Scanner(Paths.get(snapMetaTable));
		String ret = read.nextLine();
		do
		{
			returner.add(ret);
			ret = read.nextLine();
		}while(ret.isEmpty() == false);
		read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*String ret = fishOutMetaTable(0);
		int a = 0;
		do
		{
			returner.add(ret);
			a++;
			ret =fishOutMetaTable(a);
		}while (ret !=null);*/ 
		return returner;
	}

	
	private String fishOutMetaTable(int a) {
		String line = "broke";
		
		try {
			try (Stream<String> lines = Files.lines(Paths.get(snapMetaTable))) {
			    line = lines.skip(a).findFirst().get();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
	
	public ArrayList<String> getTopTableWithSettings(int i) {
		ArrayList<String> returner = new ArrayList<String>();
		Scanner read;
		try {
			read = new Scanner(Paths.get(getSnapTopTables().get(i)));
		
		do
		{
			returner.add(read.nextLine());
		}while(read.hasNextLine());
		read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*for(int a = 0; a < 99;a++ )
		{
			returner.add(fishOutTableTop(a,i));
		}*/
		return returner;
	}
	
	private String fishOutTableTop(int a, int b) {
		String line = "broke";
		
			try {
				try (Stream<String> lines = Files.lines(Paths.get(getSnapTopTables().get(b)))) {
				    line = lines.skip(a).findFirst().get();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return line;
	}

	public ArrayList<String> getSnapTopTables() {
		return snapTopTables;
	}

	public void setSnapTopTables(ArrayList<String> snapTopTables) {
		this.snapTopTables = snapTopTables;
	}

	public File getTopFolder() {
		// TODO Auto-generated method stub
		return overAllFolder;
	}

	public ArrayList<ArrayList<String>> getSnapDirectionalHeatFiles() {
		return snapDirectionalHeatFiles;
	}

	public void setSnapDirectionalHeatFiles(ArrayList<ArrayList<String>> snapDirectionalHeatFiles) {
		this.snapDirectionalHeatFiles = snapDirectionalHeatFiles;
	}

	public ArrayList<ArrayList<String>> getSnapDirectionalConstantHeatFiles() {
		return snapDirectionalConstantHeatFiles;
	}

	public void setSnapDirectionalConstantHeatFiles(ArrayList<ArrayList<String>> snapDirectionalConstantHeatFiles) {
		this.snapDirectionalConstantHeatFiles = snapDirectionalConstantHeatFiles;
	}

	public ArrayList<ArrayList<String>> getSnapRelativeHeatFiles() {
		return snapRelativeHeatFiles;
	}

	public void setSnapRelativeHeatFiles(ArrayList<ArrayList<String>> snapRelativeHeatFiles) {
		this.snapRelativeHeatFiles = snapRelativeHeatFiles;
	}

	public ArrayList<ArrayList<String>> getSnapAngleHeatFiles() {
		return snapAngleHeatFiles;
	}

	public void setSnapAngleHeatFiles(ArrayList<ArrayList<String>> snapAngleHeatFiles) {
		this.snapAngleHeatFiles = snapAngleHeatFiles;
	}

	public ArrayList<String> getSnapDirectionalMetaFiles() {
		return snapDirectionalMetaFiles;
	}

	public void setSnapDirectionalMetaFiles(ArrayList<String> snapDirectionalMetaFiles) {
		this.snapDirectionalMetaFiles = snapDirectionalMetaFiles;
	}

	public ArrayList<String> getSnapDirectionalConstantMetaFiles() {
		return snapDirectionalConstantMetaFiles;
	}

	public void setSnapDirectionalConstantMetaFiles(ArrayList<String> snapDirectionalConstantMetaFiles) {
		this.snapDirectionalConstantMetaFiles = snapDirectionalConstantMetaFiles;
	}

	public ArrayList<String> getSnapRelativeMetaFiles() {
		return snapRelativeMetaFiles;
	}

	public void setSnapRelativeMetaFiles(ArrayList<String> snapRelativeMetaFiles) {
		this.snapRelativeMetaFiles = snapRelativeMetaFiles;
	}

	public ArrayList<String> getSnapAngleMetaFiles() {
		return snapAngleMetaFiles;
	}

	public void setSnapAngleMetaFiles(ArrayList<String> snapAngleMetaFiles) {
		this.snapAngleMetaFiles = snapAngleMetaFiles;
	}

	

	

	

}
