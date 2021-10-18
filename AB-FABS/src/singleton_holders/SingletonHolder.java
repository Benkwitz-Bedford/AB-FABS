package singleton_holders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import file_manipulation.DetailReader;
import trajectory_components.PotentialProcessor;

public class SingletonHolder {

	static int increment = 0;
	private static double jumpSize = 2;
	private static double jumpsPerIncrement = 2;
	private static int jumpCounter = 0;
	private static int incrementPointer = 0;
	private static int brakes = 25;
	private static String fileName = null;
	private static String vidFileName = null;
	private static ArrayList<String> valueSet = new ArrayList<String>();
	
	private static int panelSize = 1500;
	
	private static int push = 0;
	
	private static double jSizeInc = 1;
	private static double jPerIInc = 1;
	private static double braInc = 25;
	
	private static boolean changed = false;
	private static boolean boundCheckNeeded = false;

	private static String trajType = "roulette";
	
	//Deafult static values
	final private static int ITERATIONS_DEAFULT = 300;
	final private static int CHANGE_DEAFULT = 75000;
	final private static int CELLS_DEAFULT = 100;
	final private static int SIZE_DEAFULT = 1500;
	final private static int WONE_DEAFULT = 4145;
	final private static int WTWO_DEAFULT = 2441;
	final private static int WTHREE_DEAFULT = 1983;
	final private static int WFOUR_DEAFULT = 2152;
	final private static int WFIVE_DEAFULT = 2230;
	final private static int WSIX_DEAFULT = 1977;
	final private static int WSEVEN_DEAFULT = 2416;
	final private static int WEIGHT_DEAFULT = 4078;
	final private static int REPCHANCE_DEAFULT = 3000;
	final private static int REPTABOO_DEAFULT = 0;
	final private static int REPVARIENCE_DEAFULT = 100;
	final private static int DEATHCHANCE_DEAFULT = 2000;
	final private static String DEATHTYPE_DEAFULT = "clear";
	final private static int DEATHVARIENCE_DEAFULT = 100;
	final private static int DEATHTTR_DEAFULT = 100;
	final private static int BOUNDRYSIZE_DEAFULT = 500;
	final private static String BOUNDRYTYPE_DEAFULT = "circular";
	final private static int SPEED_DEAFULT = 3;
	final private static int SPEEDVAR_DEAFULT = 2;
	final private static String SPEEDTYPE_DEAFULT = "random";
	final private static int BEZMIN_DEAFULT = 5000;
	final private static int BEZMAX_DEAFULT = 50000;
	final private static int BEZCHANCE_DEAFULT = 90000;
	final private static int BEZSIZE_DEAFULT = 5;
	final private static int BEZNUM_DEAFULT = 20;
	final private static int BEZVAR_DEAFULT = 1000;
	private static int WONEVARIENCE_DEAFULT = 0;
	private static int WTWOVARIENCE_DEAFULT = 0;
	private static int WTHREEVARIENCE_DEAFULT = 0;
	private static int WFOURVARIENCE_DEAFULT = 0;
	private static int WFIVEVARIENCE_DEAFULT = 0;
	private static int WSIXVARIENCE_DEAFULT = 0;
	private static int WSEVENVARIENCE_DEAFULT = 0;
	private static int WEIGHTVARIENCE_DEAFULT = 0;
	private static int CHANGEVARIENCE_DEAFULT = 0;
	private static int CELLSIZE_DEAFULT = 5;
	private static int CELLSIZEVARIENCE_DEAFULT = 4;
	private static long ANTRATIO_DEAFULT = 10000;
	private static long ANTVARIENCERATIO_DEAFULT = 10000;
	
	
	
	private static int iterations = ITERATIONS_DEAFULT;
	private static int change = CHANGE_DEAFULT;
	private static int cells = CELLS_DEAFULT;
	private static int size = SIZE_DEAFULT;
	private static int wOne = WONE_DEAFULT;
	private static int wTwo = WTWO_DEAFULT;
	private static int wThree = WTHREE_DEAFULT;
	private static int wFour = WFOUR_DEAFULT;
	private static int wFive = WFIVE_DEAFULT;
	private static int wSix = WSIX_DEAFULT;
	private static int wSeven = WSEVEN_DEAFULT;
	private static int wEight = WEIGHT_DEAFULT;
	private static int repChance = REPCHANCE_DEAFULT;
	private static int repTaboo = REPTABOO_DEAFULT;
	private static int repVarience= REPVARIENCE_DEAFULT;
	private static int deathChance = DEATHCHANCE_DEAFULT;
	private static String deathType = DEATHTYPE_DEAFULT;
	private static int deathVarience= DEATHVARIENCE_DEAFULT;
	private static int deathTTR = getDeathTTRDeafult();
	private static int boundarySize = BOUNDRYSIZE_DEAFULT;
	private static String boundaryType = BOUNDRYTYPE_DEAFULT;
	private static double speed = getSpeedDeafult();
	private static int speedVar = getSpeedVarDeafult();
	private static String speedType = getSpeedTypeDeafult();
	private static int bezMin = BEZMIN_DEAFULT;
	private static int bezMax = BEZMAX_DEAFULT;
	private static int bezChance = BEZCHANCE_DEAFULT;
	private static int bezNum = BEZNUM_DEAFULT;
	private static String bezType = "random";
	private static int bezSize = BEZSIZE_DEAFULT;
	private static int bezVarience = BEZVAR_DEAFULT;
	private static int heatTopper = 0;
	private static int wOneVarience = getWONEVARIENCE_DEAFULT();
	private static int wTwoVarience = getWTWOVARIENCE_DEAFULT();
	private static int wThreeVarience = getWTHREEVARIENCE_DEAFULT();
	private static int wFourVarience = getWFOURVARIENCE_DEAFULT();
	private static int wFiveVarience = getWFIVEVARIENCE_DEAFULT();
	private static int wSixVarience = getWSIXVARIENCE_DEAFULT();
	private static int wSevenVarience = getWSEVENVARIENCE_DEAFULT();
	private static int wEightVarience = getWEIGHTVARIENCE_DEAFULT();
	private static int changeVarience = getCHANGEVARIENCE_DEAFULT();
	private static long antRatio = getANTRATIO_DEAFULT();
	private static long antVarienceRatio = getANTVARIENCERATIO_DEAFULT();
	
	private static double attractSize = 20;
	private static double attractStrength = 100;
	private static double attractFalloff = 0;
	private static double attractZones = 20;
	private static double attractSizeVar = 20;
	private static double attractStrengthVar = 1;
	private static double attractEye = 10;
	
	private static double deflectSize = 20;
	private static double deflectStrength = 100;
	private static double deflectFalloff = 0;
	private static double deflectZones = 20;
	private static double deflectSizeVar = 20;
	private static double deflectStrengthVar = 1;
	
	private static int bezSVar = 0;
	private static boolean bezDist = false;
	private static int bezRed = 0;
	private static int bezGreen = 0;
	private static boolean bezStrengthFlag = false;
	private static boolean bezGRChanges = false;
	private static boolean antChanges = false;
	private static boolean attractZoneFlag = false;
	private static boolean deflectZoneFlag = false;
	private static boolean attractZoneChanges = false;
	private static boolean deflectZoneChanges = false;
	
	private static int cellSize = getCELLSIZE_DEAFULT();
	private static int cellSizeVarience = getCELLSIZEVARIENCE_DEAFULT();
	
	private static ArrayList<ArrayList<double[]>> boxHolder =  new ArrayList<ArrayList<double[]>>();
	
	//trigger clauses
	private static boolean replication = false;
	private static boolean repVar = false;
	private static boolean death = false;
	private static boolean deathVar = false;
	private static boolean boundary = false;
	private static boolean speedFlag = false;
	private static boolean jumpsChanged = false;
	private static boolean bezFlag = false;
	private static boolean bezBounceFlag = false;
	private static boolean distChanges = false;
	
	private static boolean tail = false;
	private static boolean circ = false;
	private static boolean back = false;
	private static boolean mos = false;
	private static BufferedImage backImg = null;
	private static boolean music = false;
	
	
	private static boolean repChanges = false;
	private static boolean deathChanges = false;
	private static boolean speedChanges = false;
	private static boolean bezChanges = false;
	private static boolean bezRetainer = false;
	private static boolean shuffleFlag = true;
	private static boolean turnChanged = false;
	private static boolean showingOverlappers = false;
	private static boolean antPath = false;
	private static boolean showZones = false;
	
	private static boolean replicationFlag = true;
	private static boolean deathFlag = true;
	
	private static boolean showID = false;
	private static int drawMode = 0;
	
	private static int cellLength = 10;
	
	private static boolean running = true;
	
	private static boolean invertHeat = false;
	private static int colSelector = 2;
	
	//private static StatGenerator statEngine = new StatGenerator(); 
	
	private static double heat = 0;
	private static double gamma = 1;
	
	private static long masterSeed = System.nanoTime();
	private static Random masterRandom = new Random(getMasterSeed());
	//random
	//even
	//normal
	private static String distribution = "random";
	
	private static String[][] cellSets = new String[][]{SingletonHolder.createCellSetFromSettings()};
	
	private static String[] cellValues;
	
	private static int bezzLength = 2;
	
	//live
	//replay
	//stat
	private static String runType = "live";

	private static int varEnd = 85; 
	
	private static PotentialProcessor pot = null;
	
	public static String[] grabAllValues()
	{
		String[] values = new String[85];
		values[1] = ""+ jumpSize;
		values[2] = ""+ jumpsPerIncrement;
		values[5] =""+brakes;
		values[6] =""+fileName;
		values[7] =""+vidFileName;
		values[8] =""+panelSize;
		values[9] =""+push;
		values[10] =""+jSizeInc;
		values[11] =""+jPerIInc;
		values[12] =""+braInc;
		values[13] =""+changed;
		values[14] =""+boundCheckNeeded;
		values[15] =""+trajType;
		values[16] =""+iterations;
		values[17] =""+change;
		values[18] =""+cells;
		values[19] =""+size;
		values[20] =""+wOne;
		values[21] =""+wTwo;
		values[22] =""+wThree;
		values[23] =""+wFour;
		values[24] =""+wFive;
		values[25] =""+wSix;
		values[26] =""+wSeven;
		values[27] =""+wEight;
		values[28] =""+repChance;
		values[29] =""+repTaboo;
		values[30] =""+repVarience;
		values[31] =""+deathChance;
		values[32] =""+deathType;
		values[33] =""+deathVarience;
		values[34] =""+deathTTR;
		values[35] =""+boundarySize;
		values[36] =""+boundaryType;
		values[37] =""+speed;
		values[38] =""+speedVar;
		values[39] =""+speedType;
		values[40] =""+bezMin;
		values[41] =""+bezMax;
		values[42] =""+bezChance;
		values[43] =""+bezNum;
		values[44] =""+bezType;
		values[45] =""+bezSize ;
		values[46] =""+bezVarience;
		values[47] =""+heatTopper;
		values[48] =""+replication;
		values[49] =""+repVar;
		values[50] =""+death;
		values[51] =""+deathVar;
		values[52] =""+boundary;
		values[53] =""+speedFlag;
		values[54] =""+jumpsChanged;
		values[55] =""+bezFlag;
		values[56] =""+tail;
		values[57] =""+repChanges;
		values[58] =""+deathChanges;
		values[59] =""+speedChanges;
		values[60] =""+bezChanges;
		values[61] =""+bezRetainer;
		values[62] =""+cellLength;	
		values[63] =""+bezBounceFlag;	
		values[64] =""+bezSVar;	
		values[65] =""+bezDist;
		values[66] =""+antPath;
		values[67] =""+antRatio;
		values[68] =""+getAntVarienceRatio();
		values[69] =""+distribution;
		
		values[70] =""+attractSize; 
		values[71] =""+attractStrength; 
		values[72] =""+attractFalloff; 
		values[73] =""+attractZones; 
		values[74] =""+attractSizeVar; 
		values[75] =""+attractStrengthVar; 
		
		values[76] =""+deflectSize; 
		values[77] =""+deflectStrength; 
		values[78] =""+deflectFalloff; 
		values[79] =""+deflectZones; 
		values[80] =""+deflectSizeVar; 
		values[81] =""+deflectStrengthVar; 
		
		values[82] =""+attractZoneFlag;
		values[83] =""+deflectZoneFlag;
		values[84] =""+attractEye; 
		 
		if(cellSets.length >1)
		{

			ArrayList<String> cellVals = new ArrayList<String>();
			for(int i = 0; i < cellSets.length; i++)
			{
				for(int l = 0; l < cellSets[i].length; l++)
				{
					cellVals.add(cellSets[i][l]);
				}
			}
			String[] val = new String[values.length+cellVals.size()];
			for(int i = 0; i < values.length; i++)
			{
				val[i] = values[i];
			}
			for(int i = 0; i < cellVals.size();i++)
			{
				val[values.length+i] = cellVals.get(i);
			}
			values = val;
		}
		return values;
		
	}
	public static String[] grabAllValueNames()
	{
		String[] values = new String[85];
		values[1] = " jumpSize";
		values[2] = " jumpsPerIncrement";
		values[5] ="brakes";
		values[6] ="fileName";
		values[7] ="vidFileName";
		values[8] ="panelSize";
		values[9] ="push";
		values[10] ="jSizeInc";
		values[11] ="jPerIInc";
		values[12] ="braInc";
		values[13] ="changed";
		values[14] ="boundCheckNeeded";
		values[15] ="trajType";
		values[16] ="iterations";
		values[17] ="change";
		values[18] ="cells";
		values[19] ="size";
		values[20] ="wOne";
		values[21] ="wTwo";
		values[22] ="wThree";
		values[23] ="wFour";
		values[24] ="wFive";
		values[25] ="wSix";
		values[26] ="wSeven";
		values[27] ="wEight";
		values[28] ="repChance";
		values[29] ="repTaboo";
		values[30] ="repVarience";
		values[31] ="deathChance";
		values[32] ="deathType";
		values[33] ="deathVarience";
		values[34] ="deathTTR";
		values[35] ="boundarySize";
		values[36] ="boundaryType";
		values[37] ="speed";
		values[38] ="speedVar";
		values[39] ="speedType";
		values[40] ="bezMin";
		values[41] ="bezMax";
		values[42] ="bezChance";
		values[43] ="bezNum";
		values[44] ="bezType";
		values[45] ="bezSize ";
		values[46] ="bezVarience";
		values[47] ="heatTopper";
		values[48] ="replication";
		values[49] ="repVar";
		values[50] ="death";
		values[51] ="deathVar";
		values[52] ="boundary";
		values[53] ="speedFlag";
		values[54] ="jumpsChanged";
		values[55] ="bezFlag";
		values[56] ="tail";
		values[57] ="repChanges";
		values[58] ="deathChanges";
		values[59] ="speedChanges";
		values[60] ="bezChanges";
		values[61] ="bezRetainer";
		values[62] ="cellLength";	
		values[63] ="bezBounceFlag";	
		values[64] ="bezSVar";	
		values[65] ="bezDist";
		values[66] ="antPath";
		values[67] ="antRatio";
		values[68] ="getAntVarienceRatio()";
		values[69] ="distribution";
		
		values[70] ="attractSize"; 
		values[71] ="attractStrength"; 
		values[72] ="attractFalloff"; 
		values[73] ="attractZones"; 
		values[74] ="attractSizeVar"; 
		values[75] ="attractStrengthVar"; 
		
		values[76] ="deflectSize"; 
		values[77] ="deflectStrength"; 
		values[78] ="deflectFalloff"; 
		values[79] ="deflectZones"; 
		values[80] ="deflectSizeVar"; 
		values[81] ="deflectStrengthVar"; 
		
		values[82] ="attractZoneFlag";
		values[83] ="deflectZoneFlag";
		values[84] ="attractEye"; 
		return values;
		
	}
	public static String[] createCellSetFromSettings() {
		String[] values = new String[40];
		//37
		//0-4 repCheck, repChance, repTaboo, repVarianceCheck, repVarience
		//5-10 deathCheck, deathChance, deathVarienceCheck, deathVarience, deathType, deathTimedNum
		//11-14 speedCheck, speedAmount, speedVarience, speedType
		//15-16 changeChance, changeVarience
		//17 noOfCells
		//18-19 wOne, oneVar,
		//20-21 wTwo, twoVar,
		//22-23 wThree, threeVar,
		//24-25 wFour, fourVar,
		//26-27 wFive, fiveVar,
		//28-29 wSix, sixVar,
		//30-31 wSeven, sevenVar,
		//32-33 wEight, eightVar,
		//34rand/rou
		//35-36 cell size and cell varience
		values[0] = ""+replication;
		values[1] = ""+repChance;
		values[2] = ""+repTaboo;
		values[3] =  ""+repVar;
		values[4] = ""+repVarience;

		values[5] = ""+death;
		values[6] = ""+deathChance;
		values[7] = ""+deathVar;
		values[8] = ""+deathVarience;
		values[9] = ""+deathType;
		values[10] = ""+deathTTR;

		values[11] = ""+speedFlag;
		values[12] = ""+speed;
		values[13] = ""+speedVar;
		values[14] = ""+speedType;
		
		values[15] = ""+change;
		values[16] = ""+changeVarience;
		values[17] = ""+cells;
		
		values[18] = ""+wOne;
		values[19] = ""+wOneVarience;
		
		values[20] = ""+wTwo;
		values[21] = ""+wTwoVarience;
		
		values[22] = ""+wThree;
		values[23] = ""+wThreeVarience;
		
		values[24] = ""+wFour;
		values[25] = ""+wFourVarience;
		
		values[26] = ""+wFive;
		values[27] = ""+wFiveVarience;
		
		values[28] = ""+wSix;
		values[29] = ""+wSixVarience;
		
		values[30] = ""+wSeven;
		values[31] = ""+wSevenVarience;
		
		values[32] = ""+wEight;
		values[33] = ""+wEightVarience;
		
		values[34] = trajType;
		
		values[35] = ""+cellLength;
		values[36] = ""+cellSizeVarience;
		
		values[37] = ""+antPath;
		values[38] = ""+antRatio;
		values[39] = ""+getAntVarienceRatio();
		
				
		return values;
	}
	
	public static String[] createCellSetNamesFromSettings() {
		String[] values = new String[40];
		//37
		//0-4 repCheck, repChance, repTaboo, repVarianceCheck, repVarience
		//5-10 deathCheck, deathChance, deathVarienceCheck, deathVarience, deathType, deathTimedNum
		//11-14 speedCheck, speedAmount, speedVarience, speedType
		//15-16 changeChance, changeVarience
		//17 noOfCells
		//18-19 wOne, oneVar,
		//20-21 wTwo, twoVar,
		//22-23 wThree, threeVar,
		//24-25 wFour, fourVar,
		//26-27 wFive, fiveVar,
		//28-29 wSix, sixVar,
		//30-31 wSeven, sevenVar,
		//32-33 wEight, eightVar,
		//34rand/rou
		//35-36 cell size and cell varience
		values[0] = "replication";
		values[1] = "repChance";
		values[2] = "repTaboo";
		values[3] =  "repVar";
		values[4] = "repVarience";

		values[5] = "death";
		values[6] = "deathChance";
		values[7] = "deathVar";
		values[8] = "deathVarience";
		values[9] = "deathType";
		values[10] = "deathTTR";

		values[11] = "speedFlag";
		values[12] = "speed";
		values[13] = "speedVar";
		values[14] = "speedType";
		
		values[15] = "change";
		values[16] = "changeVarience";
		values[17] = "cells";
		
		values[18] = "wOne";
		values[19] = "wOneVarience";
		
		values[20] = "wTwo";
		values[21] = "wTwoVarience";
		
		values[22] = "wThree";
		values[23] = "wThreeVarience";
		
		values[24] = "wFour";
		values[25] = "wFourVarience";
		
		values[26] = "wFive";
		values[27] = "wFiveVarience";
		
		values[28] = "wSix";
		values[29] = "wSixVarience";
		
		values[30] = "wSeven";
		values[31] = "wSevenVarience";
		
		values[32] = "wEight";
		values[33] = "wEightVarience";
		
		values[34] = "trajType";
		
		values[35] = "cellLength";
		values[36] = "cellSizeVarience";
		
		values[37] = "antPath";
		values[38] = "antRatio";
		values[39] = "antVarienceRatio";
		
				
		return values;
	}
	public static void prepFromDetails(File file) {
		try {
			DetailReader dR = new DetailReader(file);
			masterSeed = Long.parseLong(dR.getSeed());
			masterRandom = new Random(masterSeed);
			setAllValues(dR.getValues());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void setAllValues(String[] values)
	{
		//System.out.println("wah here".split("\\.")[0]);
		//System.out.println("wah. here".split("\\.")[0]);
		 jumpSize = Double.parseDouble(values[1]);
		 jumpsPerIncrement = Double.parseDouble(values[2]);
		 brakes = Integer.parseInt(values[5].split("\\.")[0]); 
		 fileName = values[6];
		 vidFileName = values[7];
		 panelSize = Integer.parseInt(values[8].split("\\.")[0]); 
		 push = Integer.parseInt(values[9].split("\\.")[0]);
		 jSizeInc = Double.parseDouble(values[10]);
		 jPerIInc = Double.parseDouble(values[11]); 
		 braInc = Double.parseDouble(values[12]); 
		 changed = Boolean.parseBoolean(values[13]); 
		 boundCheckNeeded = Boolean.parseBoolean(values[14]); 
		 trajType = values[15]; 
		 iterations = Integer.parseInt(values[16].split("\\.")[0]); 
		 change = Integer.parseInt(values[17].split("\\.")[0]); 
		 cells = Integer.parseInt(values[18].split("\\.")[0]); 
		 size = Integer.parseInt(values[19].split("\\.")[0]);
		 wOne = Integer.parseInt(values[20].split("\\.")[0]);
		 wTwo = Integer.parseInt(values[21].split("\\.")[0]); 
		 wThree = Integer.parseInt(values[22].split("\\.")[0]); 
		 wFour = Integer.parseInt(values[23].split("\\.")[0]); 
		 wFive = Integer.parseInt(values[24].split("\\.")[0]); 
		 wSix = Integer.parseInt(values[25].split("\\.")[0]); 
		 wSeven = Integer.parseInt(values[26].split("\\.")[0]);
		 wEight = Integer.parseInt(values[27].split("\\.")[0]); 
		 repChance = Integer.parseInt(values[28].split("\\.")[0]);
		 repTaboo = Integer.parseInt(values[29].split("\\.")[0]); 
		 repVarience= Integer.parseInt(values[30].split("\\.")[0]); 
		 deathChance = Integer.parseInt(values[31].split("\\.")[0]); 
		 deathType = values[32];
		 deathVarience= Integer.parseInt(values[33].split("\\.")[0]); 
		 deathTTR = Integer.parseInt(values[34].split("\\.")[0]);
		 boundarySize = Integer.parseInt(values[35].split("\\.")[0]); 
		 boundaryType = values[36]; 
		 speed = Double.parseDouble(values[37]);
		 speedVar = Integer.parseInt(values[38].split("\\.")[0]); 
		 speedType = values[39]; 
		 bezMin = Integer.parseInt(values[40].split("\\.")[0]);
		 bezMax = Integer.parseInt(values[41].split("\\.")[0]); 
		 bezChance = Integer.parseInt(values[42].split("\\.")[0]); 
		 bezNum = Integer.parseInt(values[43].split("\\.")[0].split("\\.")[0]);
		 bezType = values[44];
		 bezSize = Integer.parseInt(values[45].split("\\.")[0]);
		 bezVarience = Integer.parseInt(values[46].split("\\.")[0]);
		 heatTopper = Integer.parseInt(values[47].split("\\.")[0]);
		 replication = Boolean.parseBoolean(values[48]);
		 repVar = Boolean.parseBoolean(values[49]); 
		 death = Boolean.parseBoolean(values[50]);
		 deathVar = Boolean.parseBoolean(values[51]); 
		 boundary = Boolean.parseBoolean(values[52]);
		 speedFlag = Boolean.parseBoolean(values[53]); 
		 jumpsChanged = Boolean.parseBoolean(values[54]);
		 bezFlag = Boolean.parseBoolean(values[55]); 
		 tail = Boolean.parseBoolean(values[56]); 
		 repChanges = Boolean.parseBoolean(values[57]); 
		 deathChanges = Boolean.parseBoolean(values[58]);
		 speedChanges = Boolean.parseBoolean(values[59]); 
		 bezChanges = Boolean.parseBoolean(values[60]); 
		 bezRetainer = Boolean.parseBoolean(values[61]);
		 cellLength = Integer.parseInt(values[62].split("\\.")[0]); 
		 bezBounceFlag = Boolean.parseBoolean(values[63]);
		 bezSVar = Integer.parseInt(values[64].split("\\.")[0]);
		 bezDist = Boolean.parseBoolean(values[65]);
		 antPath = Boolean.parseBoolean(values[66]);
		 antRatio = Long.parseLong(values[67]);
		 setAntVarienceRatio(Long.parseLong(values[68]));
		 distribution = values[69];
		 
		 attractSize = Double.parseDouble(values[70]); 
		 attractStrength = Double.parseDouble(values[71]); 
		 attractFalloff = Double.parseDouble(values[72]); 
		 attractZones = Double.parseDouble(values[73]); 
		 attractSizeVar = Double.parseDouble(values[74]); 
		 attractStrengthVar = Double.parseDouble(values[75]); 
		
		 deflectSize = Double.parseDouble(values[76]); 
		 deflectStrength = Double.parseDouble(values[77]); 
		 deflectFalloff = Double.parseDouble(values[78]); 
		 deflectZones = Double.parseDouble(values[79]); 
		 deflectSizeVar = Double.parseDouble(values[80]); 
		 deflectStrengthVar = Double.parseDouble(values[81]); 
		 
		 attractZoneFlag = Boolean.parseBoolean(values[82]);
		 deflectZoneFlag = Boolean.parseBoolean(values[83]);
		 attractEye = Double.parseDouble(values[84]); 
		 //was 37
		 int size = 40;
		 if(values.length > getVarEnd())
		 {
			 //39 currently
			 int num = (values.length-getVarEnd())/size;
			 String[][] cellVals = new String[num][];
			 for(int i = 0; i < num; i++)
			 {
				 String[] val = new String[size];
				 int start = getVarEnd()+i*size;
				 for(int l = 0; l < val.length; l++)
				 {
					 val[l] = values[start+l];
				 }
				 cellVals[i] = val;
			 }
			 cellSets = cellVals;
		 }
	}
	
	
	public static int getIncrement() {
		// TODO Auto-generated method stub
		return increment;
	}

	public static void setIncrement(int i)
	{
		
		increment = i;
	}

	public static boolean isRunning() {
		// TODO Auto-generated method stub
		return running;
	}

	public static double getJumpSize() {
		// TODO Auto-generated method stub
		return jumpSize;
	}

	public static double getJumpsPerIncrement() {
		// TODO Auto-generated method stub
		return jumpsPerIncrement;
	}

	public static int getBrakes() {
		// TODO Auto-generated method stub
		return brakes;
	}

	public static void setRunning(boolean b) {
		// TODO Auto-generated method stub
		running = b;
	}

	public static void incJumpCounter() {
		// TODO Auto-generated method stub
		jumpCounter++;		
	}

	public static void incIncrement() {
		// TODO Auto-generated method stub
		increment++;
	}

	public static double getJumpCounter() {
		// TODO Auto-generated method stub
		return jumpCounter;
	}
	
	public static void jumpSizeIncrement()
	{
		jumpSize = jumpSize + jSizeInc;
		double i = jumpSize;
		if(i<SingletonSanitisationFields.getMinJumpSize())
		{
			i = SingletonSanitisationFields.getMinJumpSize();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxJumpSize())
			{
				i = SingletonSanitisationFields.getMaxJumpSize();
			}
		}
		jumpSize = i;

		SingletonHolder.setSpeedChanges(true);
	}
	
	private static void resetCounters() {
		jumpCounter = 0;
		increment = 0;
		
	}

	public static void jumpSizeDecrement()
	{
		jumpSize = jumpSize - jSizeInc;
		double i = jumpSize;
		if(i<SingletonSanitisationFields.getMinJumpSize())
		{
			i = SingletonSanitisationFields.getMinJumpSize();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxJumpSize())
			{
				i = SingletonSanitisationFields.getMaxJumpSize();
			}
		}
		jumpSize = i;

		SingletonHolder.setSpeedChanges(true);
	}
	
	public static void jumpsPerIncrementIncrement()
	{
		jumpsPerIncrement = jumpsPerIncrement + jPerIInc;
		double i = jumpsPerIncrement;
		if(i<SingletonSanitisationFields.getMinJumpsPerIncrement())
		{
			i = SingletonSanitisationFields.getMinJumpsPerIncrement();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxJumpsPerIncrement())
			{
				i = SingletonSanitisationFields.getMaxJumpsPerIncrement();
			}
		}
		jumpsPerIncrement = i;

		SingletonHolder.setJumpsChanged(true);
		//resetCounters();
	}
	
	public static void jumpsPerIncrementDecrement()
	{
		jumpsPerIncrement = jumpsPerIncrement - jPerIInc;
		double i = jumpsPerIncrement;
		if(i<SingletonSanitisationFields.getMinJumpsPerIncrement())
		{
			i = SingletonSanitisationFields.getMinJumpsPerIncrement();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxJumpsPerIncrement())
			{
				i = SingletonSanitisationFields.getMaxJumpsPerIncrement();
			}
		}
		jumpsPerIncrement = i;

		SingletonHolder.setJumpsChanged(true);
		//resetCounters();
	}
	
	public static void brakesIncrement()
	{
		brakes = (int) (brakes + braInc);
		int i = brakes;
		if(i<SingletonSanitisationFields.getMinBrakes())
		{
			i = SingletonSanitisationFields.getMinBrakes();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxBrakes())
			{
				i = SingletonSanitisationFields.getMaxBrakes();
			}
		}
		brakes = i;
	}
	
	public static void brakesDecrement()
	{
		brakes = (int) (brakes - braInc);
		int i = brakes;
		if(i<SingletonSanitisationFields.getMinBrakes())
		{
			i = SingletonSanitisationFields.getMinBrakes();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxBrakes())
			{
				i = SingletonSanitisationFields.getMaxBrakes();
			}
		}
		brakes = i;
	}

	public static String getFileName() {
		return fileName;
	}

	public static void setFileName(String filename) {
		SingletonHolder.fileName = filename;
	}

	public static String getVidFileName() {
		return vidFileName;
	}

	public static void setVidFileName(String vidFilename) {
		SingletonHolder.vidFileName = vidFilename;
	}

	public static ArrayList<String> getValueSet() {
		return valueSet;
	}

	public static void setValueSet(ArrayList<String> valueSet) {
		SingletonHolder.valueSet = valueSet;
		SingletonHolder.processValues();
	}

	private static void processValues() {
		ArrayList<String> values = SingletonHolder.getValueSet();
		for(int i = 0; i < values.size(); i++)
		{
			if(values.get(i).equals(""))
			{
				values.set(i, "0");
			}
		}
		SingletonHolder.setTrajType(values.get(0));
		SingletonHolder.setFileName(values.get(1));
		SingletonHolder.setIterations(Integer.parseInt(values.get(2)));
		SingletonHolder.setChange(Integer.parseInt(values.get(3)));
		SingletonHolder.setCells(Integer.parseInt(values.get(4)));
		SingletonHolder.setSize(Integer.parseInt(values.get(5)));
		
		if(SingletonHolder.getTrajType().equals("roulette"))
		{
			SingletonHolder.setWOne(Integer.parseInt(values.get(6)));
			SingletonHolder.setWTwo(Integer.parseInt(values.get(7)));
			SingletonHolder.setWThree(Integer.parseInt(values.get(8)));
			SingletonHolder.setWFour(Integer.parseInt(values.get(9)));
			SingletonHolder.setWFive(Integer.parseInt(values.get(10)));
			SingletonHolder.setWSix(Integer.parseInt(values.get(11)));
			SingletonHolder.setWSeven(Integer.parseInt(values.get(12)));
			SingletonHolder.setWeight(Integer.parseInt(values.get(13)));
		}
		
		SingletonHolder.setChanged(true);
		
	}

	public static int getITERATIONS_DEAFULT() {
		return ITERATIONS_DEAFULT;
	}

	public static void setIterations(int iTERATIONS_DEAFULT) {
		iterations = iTERATIONS_DEAFULT;
		int i = iterations;
		if(i<SingletonSanitisationFields.getMinITERATIONS_DEAFULT())
		{
			i = SingletonSanitisationFields.getMinITERATIONS_DEAFULT();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxITERATIONS_DEAFULT())
			{
				i = SingletonSanitisationFields.getMaxITERATIONS_DEAFULT();
			}
		}
		System.out.println("time set to "+i);
		iterations = i;
	}

	public static int getCHANGE_DEAFULT() {
		return CHANGE_DEAFULT;
	}

	public static void setChange(int cHANGE_DEAFULT) {
		change = cHANGE_DEAFULT;
		int i = change;
		if(i<SingletonSanitisationFields.getMinCHANGE_DEAFULT())
		{
			i = SingletonSanitisationFields.getMinCHANGE_DEAFULT();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxCHANGE_DEAFULT())
			{
				i = SingletonSanitisationFields.getMaxCHANGE_DEAFULT();
			}
		}
		//System.out.println(change);
		change = i;
	}

	public static int getCELLS_DEAFULT() {
		return CELLS_DEAFULT;
	}

	public static void setCells(int cELLS_DEAFULT) {
		cells = cELLS_DEAFULT;
		int i = cells;
		if(i<SingletonSanitisationFields.getMinCELLS_DEAFULT())
		{
			i = SingletonSanitisationFields.getMinCELLS_DEAFULT();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxCELLS_DEAFULT())
			{
				i = SingletonSanitisationFields.getMaxCELLS_DEAFULT();
			}
		}
		cells = i;
	}

	public static int getSIZE_DEAFULT() {
		return SIZE_DEAFULT;
	}

	public static void setSize(int sIZE_DEAFULT) {
		if(size != sIZE_DEAFULT)
		{

			SingletonHolder.setChanged(true);
		}
		size = sIZE_DEAFULT;
		/*int i = size;
		if(i<SingletonSanitisationFields.getMinSIZE_DEAFULT())
		{
			i = SingletonSanitisationFields.getMinSIZE_DEAFULT();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxSIZE_DEAFULT())
			{
				i = SingletonSanitisationFields.getMaxSIZE_DEAFULT();
			}
		}
		size = i;*/
		calcPush();
	}

	private static void calcPush() {
		int size = getPanelSize();
		int area = getSize();
		size = size/2;
		area = area/2;
		push = size-area;
		
	}

	public static int getWONE_DEAFULT() {
		return WONE_DEAFULT;
	}

	public static void setWOne(int wONE_DEAFULT) {
		wOne = wONE_DEAFULT;
		int i = wOne;
		if(i<SingletonSanitisationFields.getMinWONE_DEAFULT())
		{
			i = SingletonSanitisationFields.getMinWONE_DEAFULT();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxWONE_DEAFULT())
			{
				i = SingletonSanitisationFields.getMaxWONE_DEAFULT();
			}
		}
		wOne = i;
	}

	public static int getWTWO_DEAFULT() {
		return WTWO_DEAFULT;
	}

	public static void setWTwo(int wTWO_DEAFULT) {
		wTwo = wTWO_DEAFULT;
		int i = wTwo;
		if(i<SingletonSanitisationFields.getMinWTWO_DEAFULT())
		{
			i = SingletonSanitisationFields.getMinWTWO_DEAFULT();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxWTWO_DEAFULT())
			{
				i = SingletonSanitisationFields.getMaxWTWO_DEAFULT();
			}
		}
		wTwo = i;
	}

	public static int getWTHREE_DEAFULT() {
		return WTHREE_DEAFULT;
	}

	public static void setWThree(int wTHREE_DEAFULT) {
		wThree = wTHREE_DEAFULT;
		int i = wThree;
		if(i<SingletonSanitisationFields.getMinWTHREE_DEAFULT())
		{
			i = SingletonSanitisationFields.getMinWTHREE_DEAFULT();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxWTHREE_DEAFULT())
			{
				i = SingletonSanitisationFields.getMaxWTHREE_DEAFULT();
			}
		}
		wThree = i;
	}

	public static int getWFOUR_DEAFULT() {
		return WFOUR_DEAFULT;
	}

	public static void setWFour(int wFOUR_DEAFULT) {
		wFour = wFOUR_DEAFULT;
		int i = wFour;
		if(i<SingletonSanitisationFields.getMinWFOUR_DEAFULT())
		{
			i = SingletonSanitisationFields.getMinWFOUR_DEAFULT();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxWFOUR_DEAFULT())
			{
				i = SingletonSanitisationFields.getMaxWFOUR_DEAFULT();
			}
		}
		wFour = i;
	}

	public static int getWFIVE_DEAFULT() {
		return WFIVE_DEAFULT;
	}

	public static void setWFive(int wFIVE_DEAFULT) {
		wFive = wFIVE_DEAFULT;
		int i = wFive;
		if(i<SingletonSanitisationFields.getMinWFIVE_DEAFULT())
		{
			i = SingletonSanitisationFields.getMinWFIVE_DEAFULT();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxWFIVE_DEAFULT())
			{
				i = SingletonSanitisationFields.getMaxWFIVE_DEAFULT();
			}
		}
		wFive = i;
	}

	public static int getWSIX_DEAFULT() {
		return WSIX_DEAFULT;
	}

	public static void setWSix(int wSIX_DEAFULT) {
		wSix = wSIX_DEAFULT;
		int i = wSix;
		if(i<SingletonSanitisationFields.getMinWSIX_DEAFULT())
		{
			i = SingletonSanitisationFields.getMinWSIX_DEAFULT();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxWSIX_DEAFULT())
			{
				i = SingletonSanitisationFields.getMaxWSIX_DEAFULT();
			}
		}
		wSix = i;
	}

	public static int getWSEVEN_DEAFULT() {
		return WSEVEN_DEAFULT;
	}

	public static void setWSeven(int wSEVEN_DEAFULT) {
		wSeven = wSEVEN_DEAFULT;
		int i = wSeven;
		if(i<SingletonSanitisationFields.getMinWSEVEN_DEAFULT())
		{
			i = SingletonSanitisationFields.getMinWSEVEN_DEAFULT();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxWSEVEN_DEAFULT())
			{
				i = SingletonSanitisationFields.getMaxWSEVEN_DEAFULT();
			}
		}
		wSeven = i;
	}

	public static int getWEIGHT_DEAFULT() {
		return WEIGHT_DEAFULT;
	}

	public static void setWeight(int wEIGHT_DEAFULT) {
		wEight = wEIGHT_DEAFULT;
		int i = wEight;
		if(i<SingletonSanitisationFields.getMinWEIGHT_DEAFULT())
		{
			i = SingletonSanitisationFields.getMinWEIGHT_DEAFULT();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxWEIGHT_DEAFULT())
			{
				i = SingletonSanitisationFields.getMaxWEIGHT_DEAFULT();
			}
		}
		wEight = i;
	}

	public static ArrayList<String> getDeafultValueSet() {
		ArrayList<String> values = new ArrayList<String>();
		values.add("random");
		values.add(fileName);
		values.add(""+ITERATIONS_DEAFULT);
		values.add(""+CHANGE_DEAFULT);
		values.add(""+CELLS_DEAFULT);
		values.add(""+SIZE_DEAFULT);
		values.add(""+WONE_DEAFULT);
		values.add(""+WTWO_DEAFULT);
		values.add(""+WTHREE_DEAFULT);
		values.add(""+WFOUR_DEAFULT);
		values.add(""+WFIVE_DEAFULT);
		values.add(""+WSIX_DEAFULT);
		values.add(""+WSEVEN_DEAFULT);
		values.add(""+WEIGHT_DEAFULT);
		return values;
	}

	public static String getTrajType() {
		return trajType;
	}

	public static void setTrajType(String trajType) {
		SingletonHolder.trajType = trajType;
	}

	public static boolean isChanged() {
		return changed;
	}

	public static void setChanged(boolean changed2) {
		changed = changed2;
	}

	

	public static int getPush() {
		return push;
	}

	public static void setPush(int push) {
		SingletonHolder.push = push;
	}

	public static int getPanelSize() {
		return panelSize;
	}

	public static void setPanelSize(int panelSize) {
		SingletonHolder.panelSize = panelSize;
	}

	public static ArrayList<String> generateAllValues() {
		ArrayList<String> values = new ArrayList<String>();
		values.add("seed: "+getMasterSeed());
		values.add("increments: "+increment);
		values.add("jump size: "+jumpSize);
		values.add("jumps per increment: "+jumpsPerIncrement);
		values.add("brakes: "+brakes);
		values.add("file name: "+fileName);
		values.add("vid file name: "+vidFileName);
		values.add("heat topper: "+heatTopper);
		values.add("jumps changed: "+jumpsChanged);
		values.add("tail: "+tail);
		
		values.add("panel size: "+panelSize);
		
		values.add("push: "+push);
		
		values.add("jump size increment: "+jSizeInc);
		values.add("jumps per increment: "+jPerIInc);
		values.add("brake increments: "+braInc);
		
		values.add("changed: "+changed);

		values.add("type of traj: "+trajType);
		
		values.add("iterations: "+iterations);
		values.add("change frequency: "+change);
		values.add("number of cells: "+cells);
		values.add("size of cells: "+cellLength);
		values.add("one: "+wOne);
		values.add("two: "+wTwo);
		values.add("three: "+wThree);
		values.add("four: "+wFour);
		values.add("five: "+wFive);
		values.add("six: "+wSix);
		values.add("seven: "+wSeven);
		values.add("eight: "+wEight);
		values.add("distribution: "+distribution);
		
		//replication values
		values.add("replication on: "+replication);
		values.add("rep chance: "+repChance);
		values.add("rep taboo time: "+repTaboo);
		values.add("rep varience on: "+repVar);
		values.add("rep varience: "+repVarience);
		
		//death values
		values.add("death on: "+death);
		values.add("death chance: "+deathChance);
		values.add("death type: "+deathType);
		values.add("death varience on: "+deathVar);
		values.add("death varience: "+deathVarience);
		values.add("death time till removal: "+deathTTR);
		
		//boundary values
		values.add("boundary on: "+boundary);
		values.add("bound size: "+boundarySize);
		values.add("bound type: "+boundaryType);
		
		//speed
		values.add("speed on: "+speedFlag);
		values.add("speed: "+speed);
		values.add("speed varience: "+speedVar);
		values.add("speed spreadType: "+speedType);
		
		//bezier
		values.add("bez on: "+bezFlag);
		values.add("bez bounce on: "+bezBounceFlag);
		values.add("bez min distance: "+bezMin);
		values.add("bez max distance: "+bezMax);
		values.add("bez chance: "+bezChance);
		values.add("bez number: "+bezNum);
		values.add("bez size: "+bezSize);
		values.add("bez type: "+bezType);
		values.add("bez varience: "+bezVarience);
		values.add("bez retainer: "+bezRetainer);
		values.add("bez S var: "+bezSVar);
		values.add("bez dist: "+bezDist);
		
		//ant path
		values.add("ant path: "+antPath);
		values.add("ant ratio: "+antRatio);
		values.add("ant varience ratio: "+antVarienceRatio);
		
		
		//cell species
		for(int i = 0; i < cellSets.length; i++)
		{
			values.add("species "+i+"");
			String[] dets = getSpeciesDets(cellSets[i]);
			for(int l = 0; l < dets.length;l++)
			{
				values.add(dets[l]);
			}
		}
		
		
		return values ;
	}
	
	public static void inputAllValues(String[] strings) {
		int i = 0;
		masterSeed= Long.parseLong(strings[i]);i++;
		masterRandom = new Random(masterSeed);
		increment= (int) Double.parseDouble(strings[i]);i++;
		jumpSize= Double.parseDouble(strings[i]);i++;
		jumpsPerIncrement= Double.parseDouble(strings[i]);i++;
		brakes= (int) Double.parseDouble(strings[i]);i++;
		fileName= strings[i];i++;
		vidFileName= strings[i];i++;
		heatTopper= (int) Double.parseDouble(strings[i]);i++;
		jumpsChanged= Boolean.parseBoolean(strings[i]);i++;
		tail= Boolean.parseBoolean(strings[i]);i++;
		
		panelSize= (int) Double.parseDouble(strings[i]);i++;
		
		push= (int) Double.parseDouble(strings[i]);i++;
		
		jSizeInc= Double.parseDouble(strings[i]);i++;
		jPerIInc= Double.parseDouble(strings[i]);i++;
		braInc= Double.parseDouble(strings[i]);i++;
		
		changed= Boolean.parseBoolean(strings[i]);i++;

		trajType= strings[i];i++;
		
		iterations= (int) Double.parseDouble(strings[i]);i++;
		change= (int) Double.parseDouble(strings[i]);i++;
		cells= (int) Double.parseDouble(strings[i]);i++;
		cellLength= (int) Double.parseDouble(strings[i]);i++;
		wOne= (int) Double.parseDouble(strings[i]);i++;
		wTwo= (int) Double.parseDouble(strings[i]);i++;
		wThree= (int) Double.parseDouble(strings[i]);i++;
		wFour=(int)Double.parseDouble(strings[i]);i++;
		wFive=(int)Double.parseDouble(strings[i]);i++;
		wSix=(int)Double.parseDouble(strings[i]);i++;
		wSeven=(int)Double.parseDouble(strings[i]);i++;
		wEight=(int)Double.parseDouble(strings[i]);i++;
		distribution=strings[i];i++;
		
		//replication values
		replication=Boolean.parseBoolean(strings[i]);i++;
		repChance=(int)Double.parseDouble(strings[i]);i++;
		repTaboo=(int)Double.parseDouble(strings[i]);i++;
		repVar=Boolean.parseBoolean(strings[i]);i++;
		repVarience=(int)Double.parseDouble(strings[i]);i++;
		
		//death values
		death=Boolean.parseBoolean(strings[i]);i++;
		deathChance=(int)Double.parseDouble(strings[i]);i++;
		deathType=strings[i];i++;
		deathVar=Boolean.parseBoolean(strings[i]);i++;
		deathVarience=(int)Double.parseDouble(strings[i]);i++;
		deathTTR=(int)Double.parseDouble(strings[i]);i++;
		
		//boundary values
		boundary=Boolean.parseBoolean(strings[i]);i++;
		boundarySize=(int)Double.parseDouble(strings[i]);i++;
		boundaryType=strings[i];i++;
		
		//speed
		speedFlag=Boolean.parseBoolean(strings[i]);i++;
		speed=(int)Double.parseDouble(strings[i]);i++;
		speedVar=(int)Double.parseDouble(strings[i]);i++;
		speedType=strings[i];i++;
		
		//bezier
		bezFlag=Boolean.parseBoolean(strings[i]);i++;
		bezBounceFlag=Boolean.parseBoolean(strings[i]);i++;
		bezMin=(int)Double.parseDouble(strings[i]);i++;
		bezMax=(int)Double.parseDouble(strings[i]);i++;
		bezChance=(int)Double.parseDouble(strings[i]);i++;
		bezNum=(int)Double.parseDouble(strings[i]);i++;
		bezSize=(int)Double.parseDouble(strings[i]);i++;
		bezType=strings[i];i++;
		bezVarience=(int)Double.parseDouble(strings[i]);i++;
		bezRetainer=Boolean.parseBoolean(strings[i]);i++;
		bezSVar=(int)Double.parseDouble(strings[i]);i++;
		bezDist=Boolean.parseBoolean(strings[i]);i++;
		
		//ant path
		antPath=Boolean.parseBoolean(strings[i]);i++;
		antRatio=(int)Double.parseDouble(strings[i]);i++;
		antVarienceRatio=(int)Double.parseDouble(strings[i]);i++;
		
		ArrayList<String[]> valBox = new ArrayList<String[]>();
		//cell species
		if(i < strings.length)
		{
			do
			{
					
				String[] vals = new String[40];
				for(int m = 0; m < vals.length;m++)
				{
					vals[m] = strings[i+m];
				}
				valBox.add(vals);
				i = i+vals.length;
				
			}while (i < strings.length);
		}
		cellSets = new String[valBox.size()][];
		for(int l = 0; l < valBox.size();l++)
		{
			cellSets[l] = valBox.get(l);
		}
		
		
	}
	
	

private static String[] getSpeciesDets(String[] strings) {
		
		String[] values = new String[40];
		//37
		//0-4 repCheck, repChance, repTaboo, repVarianceCheck, repVarience
		//5-10 deathCheck, deathChance, deathVarienceCheck, deathVarience, deathType, deathTimedNum
		//11-14 speedCheck, speedAmount, speedVarience, speedType
		//15-16 changeChance, changeVarience
		//17 noOfCells
		//18-19 wOne, oneVar,
		//20-21 wTwo, twoVar,
		//22-23 wThree, threeVar,
		//24-25 wFour, fourVar,
		//26-27 wFive, fiveVar,
		//28-29 wSix, sixVar,
		//30-31 wSeven, sevenVar,
		//32-33 wEight, eightVar,
		//34rand/rou
		//35-36 cell size and cell varience
		values[0] = "replication: "+strings[0];
		values[1] = "rep chance: "+strings[1];
		values[2] = "rep taboo: "+strings[2];
		values[3] =  "rep varience: "+strings[3];
		values[4] = "er rep varience 2: "+strings[4];

		values[5] = "death: "+strings[5];
		values[6] = "death chance: "+strings[6];
		values[7] = "death varience: "+strings[7];
		values[8] = "death varience 2: "+strings[8];
		values[9] = "death type: "+strings[9];
		values[10] = "time till removal: "+strings[10];

		values[11] = "speed flag: "+strings[11];
		values[12] = "speed: "+strings[12];
		values[13] = "speedVar: "+strings[13];
		values[14] = "speed type: "+strings[14];
		
		values[15] = "change flag: "+strings[15];
		values[16] = "change varience: "+strings[16];
		values[17] = "cell num: "+strings[17];
		
		values[18] = "wOne: "+strings[18];
		values[19] = "wOne var: "+strings[19];
		
		values[20] = "wTwo: "+strings[20];
		values[21] = "wTwo varience: "+strings[21];
		
		values[22] = "wThree: "+strings[22];
		values[23] = "wThree varience: "+strings[23];
		
		values[24] = "wFour: "+strings[24];
		values[25] = "wFour varicne: "+strings[25];
		
		values[26] = "wFive: "+strings[26];
		values[27] = "wFive varience: "+strings[27];
		
		values[28] = "wSix: "+strings[28];
		values[29] = "wSix varience: "+strings[29];
		
		values[30] = "wSeven: "+strings[30];
		values[31] = "wSeven varience: "+strings[31];
		
		values[32] = "wEight: "+strings[32];
		values[33] = "wEight varience: "+strings[33];
		
		values[34] = "traj type: "+strings[34];
		
		values[35] = "cell length: "+strings[35];
		values[36] = "cell length var: "+strings[36];
		
		values[37] = "ant path flag: "+strings[37];
		values[38] = "ant ratio: "+strings[38];
		values[39] = "ant varience: "+strings[39];
		
				
		return values;
	
	}



	private static String[] getCurrentAllSpeciesDets() {
		
		String[] values = new String[40];
		//37
		//0-4 repCheck, repChance, repTaboo, repVarianceCheck, repVarience
		//5-10 deathCheck, deathChance, deathVarienceCheck, deathVarience, deathType, deathTimedNum
		//11-14 speedCheck, speedAmount, speedVarience, speedType
		//15-16 changeChance, changeVarience
		//17 noOfCells
		//18-19 wOne, oneVar,
		//20-21 wTwo, twoVar,
		//22-23 wThree, threeVar,
		//24-25 wFour, fourVar,
		//26-27 wFive, fiveVar,
		//28-29 wSix, sixVar,
		//30-31 wSeven, sevenVar,
		//32-33 wEight, eightVar,
		//34rand/rou
		//35-36 cell size and cell varience
		values[0] = "replication: "+replication;
		values[1] = "rep chance: "+repChance;
		values[2] = "rep taboo: "+repTaboo;
		values[3] =  "rep varience: "+repVar;
		values[4] = "er rep varience 2: "+repVarience;

		values[5] = "death: "+death;
		values[6] = "death chance: "+deathChance;
		values[7] = "death varience: "+deathVar;
		values[8] = "death varience 2: "+deathVarience;
		values[9] = "death type: "+deathType;
		values[10] = "time till removal: "+deathTTR;

		values[11] = "speed flag: "+speedFlag;
		values[12] = "speed: "+speed;
		values[13] = "speedVar: "+speedVar;
		values[14] = "speed type: "+speedType;
		
		values[15] = "change flag: "+change;
		values[16] = "change varience: "+changeVarience;
		values[17] = "cell num: "+cells;
		
		values[18] = "wOne: "+wOne;
		values[19] = "wOne var: "+wOneVarience;
		
		values[20] = "wTwo: "+wTwo;
		values[21] = "wTwo varience: "+wTwoVarience;
		
		values[22] = "wThree: "+wThree;
		values[23] = "wThree varience: "+wThreeVarience;
		
		values[24] = "wFour: "+wFour;
		values[25] = "wFour varicne: "+wFourVarience;
		
		values[26] = "wFive: "+wFive;
		values[27] = "wFive varience: "+wFiveVarience;
		
		values[28] = "wSix: "+wSix;
		values[29] = "wSix varience: "+wSixVarience;
		
		values[30] = "wSeven: "+wSeven;
		values[31] = "wSeven varience: "+wSevenVarience;
		
		values[32] = "wEight: "+wEight;
		values[33] = "wEight varience: "+wEightVarience;
		
		values[34] = "traj type: "+trajType;
		
		values[35] = "cell length: "+cellLength;
		values[36] = "cell length var: "+cellSizeVarience;
		
		values[37] = "ant path flag: "+antPath;
		values[38] = "ant ratio: "+antRatio;
		values[39] = "ant varience: "+getAntVarienceRatio();
		
				
		return values;
	
	}

	public static int getBoundarySize() {
		return boundarySize;
	}

	public static void setBoundarySize(int boundarySize) {
		
		if(boundarySize!=SingletonHolder.boundarySize)
		{
				SingletonHolder.setBoundCheckNeeded(true);
		}
		if(boundarySize > size/2)
		{
			SingletonHolder.boundarySize = size/2-10;
		}else
		{
			SingletonHolder.boundarySize = boundarySize;
		}
	}

	public static String getBoundaryType() {
		return boundaryType;
	}

	public static void setBoundaryType(String boundaryType) {
		if(boundaryType.equals(SingletonHolder.boundaryType) == false)
		{
				SingletonHolder.setBoundCheckNeeded(true);
		}
		SingletonHolder.boundaryType = boundaryType;
	}

	public static boolean isBoundary() {
		return boundary;
	}

	public static void setBoundary(boolean boundary) {
		if(boundary != SingletonHolder.boundary)
		{
				SingletonHolder.setBoundCheckNeeded(true);
		}
		SingletonHolder.boundary = boundary;
	}

	public static int getBOUNDRYSIZE_DEAFULT() {
		return BOUNDRYSIZE_DEAFULT;
	}

	public static boolean isBoundCheckNeeded() {
		return boundCheckNeeded;
	}

	public static void setBoundCheckNeeded(boolean boundCheckNeeded) {
		SingletonHolder.boundCheckNeeded = boundCheckNeeded;
	}

	public static int getIterations() {
		return iterations;
	}

	public static int getChange() {
		return change;
	}

	public static int getwOne() {
		return wOne;
	}

	public static int getCells() {
		return cells;
	}

	public static int getSize() {
		return size;
	}

	public static int getwTwo() {
		return wTwo;
	}

	public static int getwThree() {
		return wThree;
	}

	public static int getwFour() {
		return wFour;
	}

	public static int getwFive() {
		return wFive;
	}

	public static int getwSix() {
		return wSix;
	}

	public static int getwSeven() {
		return wSeven;
	}

	public static int getwEight() {
		return wEight;
	}

	public static int getRepTabooDeafult() {
		return REPTABOO_DEAFULT;
	}

	public static int getRepChanceDeafult() {
		return REPCHANCE_DEAFULT;
	}

	public static String getBoundryTypeDeafult() {
		return BOUNDRYTYPE_DEAFULT;
	}

	public static int getRepChance() {
		return repChance;
	}

	public static void setRepChance(int repChance2) {
		//System.out.println(repChance+" "+repChance2);
		if(repChance2 != repChance)
		{
			repChanges = true;
		}
		repChance = repChance2;
		int i = repChance;
		if(i<SingletonSanitisationFields.getMinRepChanceDeafult())
		{
			i = SingletonSanitisationFields.getMinRepChanceDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxRepChanceDeafult())
			{
				i = SingletonSanitisationFields.getMaxRepChanceDeafult();
			}
		}
		repChance = i;
	}

	public static int getRepTaboo() {
		return repTaboo;
	}

	public static void setRepTaboo(int repTaboo2) {
		if(repTaboo2 != repTaboo)
		{
			repChanges = true;
		}
		repTaboo = repTaboo2;
		int i = repTaboo;
		if(i<SingletonSanitisationFields.getMinRepTabooDeafult())
		{
			i = SingletonSanitisationFields.getMinRepTabooDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxRepTabooDeafult())
			{
				i = SingletonSanitisationFields.getMaxRepTabooDeafult();
			}
		}
		repTaboo = i;
	}

	

	public static int getDeathChance() {
		return deathChance;
	}

	public static void setDeathChance(int deathChance2) 
	{
		if(deathChance2 != deathChance)
		{
			deathChanges = true;
		}
		deathChance = deathChance2;
		int i = deathChance;
		if(i<SingletonSanitisationFields.getMinDeathChanceDeafult())
		{
			i = SingletonSanitisationFields.getMinDeathChanceDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxDeathChanceDeafult())
			{
				i = SingletonSanitisationFields.getMaxDeathChanceDeafult();
			}
		}
		deathChance = i;
	}

	public static String getDeathType() {
		return deathType;
	}

	public static void setDeathType(String deathType2) {
		if(deathType2.equals(deathType))
		{
		}else
		{
			deathChanges = true;
		}
		SingletonHolder.deathType = deathType2;
	}

	public static boolean isReplication() {
		return replication;
	}

	public static void setReplication(boolean replication) {
		SingletonHolder.replication = replication;
	}

	public static boolean isDeath() {
		return death;
	}

	public static void setDeath(boolean death) {
		//System.out.println("death "+death);
		SingletonHolder.death = death;
	}

	public static int getRepVarienceDeafult() {
		return REPVARIENCE_DEAFULT;
	}

	

	public static int getRepVarience() {
		return repVarience;
	}

	public static void setRepVarience(int repVarience2) {
		if(repVarience2 != repVarience)
		{
			repChanges = true;
		}
		repVarience = repVarience2;
		int i = repVarience;
		if(i<SingletonSanitisationFields.getMinRepVarienceDeafult())
		{
			i = SingletonSanitisationFields.getMinRepVarienceDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxRepVarienceDeafult())
			{
				i = SingletonSanitisationFields.getMaxRepVarienceDeafult();
			}
		}
		repVarience = i;
	}

	public static boolean isRepVar() {
		return repVar;
	}

	public static void setRepVar(boolean repVar2) {
		if(repVar2 != repVar)
		{
			repChanges = true;
		}
		SingletonHolder.repVar = repVar2;
	}

	public static boolean isDeathVar() {
		return deathVar;
	}

	public static void setDeathVar(boolean deathVar2) {
		if(deathVar2 != deathVar)
		{
			deathChanges = true;
		}
		SingletonHolder.deathVar = deathVar2;
	}

	

	public static int getDeathVarienceDeafult() {
		return DEATHVARIENCE_DEAFULT;
	}

	public static int getDeathVarience() {
		return deathVarience;
	}

	public static void setDeathVarience(int deathVarience2) {
		if(deathVarience2 != deathVarience)
		{
			deathChanges = true;
		}
		deathVarience = deathVarience2;
		int i = deathVarience;
		if(i<SingletonSanitisationFields.getMinDeathVarienceDeafult())
		{
			i = SingletonSanitisationFields.getMinDeathVarienceDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxDeathVarienceDeafult())
			{
				i = SingletonSanitisationFields.getMaxDeathVarienceDeafult();
			}
		}
		deathVarience = i;
	}

	public static int getDeathTTRDeafult() {
		return DEATHTTR_DEAFULT;
	}

	

	public static int getDeathTTR() {
		return deathTTR;
	}

	public static void setDeathTTR(int deathTTR2) {
		if(deathTTR2 != deathTTR)
		{
			deathChanges = true;
		}
		deathTTR = deathTTR2;
		int i = deathTTR;
		if(i<SingletonSanitisationFields.getMinDeathTTRDeafult())
		{
			i = SingletonSanitisationFields.getMinDeathTTRDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxDeathTTRDeafult())
			{
				i = SingletonSanitisationFields.getMaxDeathTTRDeafult();
			}
		}
		deathTTR = i;
	}

	public static int getSpeedDeafult() {
		return SPEED_DEAFULT;
	}

	public static int getSpeedVarDeafult() {
		return SPEEDVAR_DEAFULT;
	}

	public static String getSpeedTypeDeafult() {
		return SPEEDTYPE_DEAFULT;
	}

	

	public static double getSpeed() {
		return speed;
	}

	public static void setSpeed(int speed2) {
		if(speed2 != speed)
		{
			speedChanges = true;
		}
		speed = speed2;
		double i = speed;
		if(i<SingletonSanitisationFields.getMinSpeedDeafult())
		{
			i = SingletonSanitisationFields.getMinSpeedDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxSpeedDeafult())
			{
				i = SingletonSanitisationFields.getMaxSpeedDeafult();
			}
		}
		speed = i;
	}

	public static int getSpeedVar() {
		return speedVar;
	}

	public static void setSpeedVar(int speedVar2) {
		if(speedVar2 != speedVar)
		{
			speedChanges = true;
		}
		speedVar = speedVar2;
		int i = speedVar;
		if(i<SingletonSanitisationFields.getMinSpeedVarDeafult())
		{
			i = SingletonSanitisationFields.getMinSpeedVarDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxSpeedVarDeafult())
			{
				i = SingletonSanitisationFields.getMaxSpeedVarDeafult();
			}
		}
		speedVar = i;
	}

	public static String getSpeedType() {
		return speedType;
	}

	public static void setSpeedType(String speedType2) {
		if(speedType2.equals(speedType))
		{
		}else
		{
			speedChanges = true;
		}
		SingletonHolder.speedType = speedType2;
	}

	public static boolean isSpeedFlag() {
		return speedFlag;
	}

	public static void setSpeedFlag(boolean speedFlag) {
		SingletonHolder.speedFlag = speedFlag;
	}

	public static boolean isJumpsChanged() {
		return jumpsChanged;
	}

	public static void setJumpsChanged(boolean jumpsChanged) {
		SingletonHolder.jumpsChanged = jumpsChanged;
	}

	

	public static boolean isRepChanges() {
		return repChanges;
	}

	public static void setRepChanges(boolean repChanges) {
		SingletonHolder.repChanges = repChanges;
	}

	public static boolean isDeathChanges() {
		return deathChanges;
	}

	public static void setDeathChanges(boolean deathChanges) {
		SingletonHolder.deathChanges = deathChanges;
	}

	public static boolean isSpeedChanges() {
		return speedChanges;
	}

	public static void setSpeedChanges(boolean speedChanges) {
		SingletonHolder.speedChanges = speedChanges;
	}

	public static int getBezMinDeafult() {
		return BEZMIN_DEAFULT;
	}

	public static int getBezChanceDeafult() {
		return BEZCHANCE_DEAFULT;
	}

	public static int getBezMaxDeafult() {
		return BEZMAX_DEAFULT;
	}

	public static int getBezNumDeafult() {
		return BEZNUM_DEAFULT;
	}

	

	public static int getBezMin() {
		return bezMin;
	}

	public static void setBezMin(int bezMin2) 
	{
		if(bezMin2 != bezMin)
		{

			bezChanges = true;
		}
		bezMin = bezMin2;
		int i = bezMin;
		if(i<SingletonSanitisationFields.getMinBezMinDeafult())
		{
			i = SingletonSanitisationFields.getMinBezMinDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxBezMinDeafult())
			{
				i = SingletonSanitisationFields.getMaxBezMinDeafult();
			}
		}
		bezMin = i;
		if(bezMin>bezMax)
		{
			int a = bezMin;
			bezMin = bezMax;
			bezMax = a;
		}
	}

	public static int getBezMax() {
		return bezMax;
	}

	public static void setBezMax(int bezMax2) {
		if(bezMax2 != bezMax)
		{
			bezChanges = true;
		}
		bezMax = bezMax2;
		int i = bezMax;
		if(i<SingletonSanitisationFields.getMinBezMaxDeafult())
		{
			i = SingletonSanitisationFields.getMinBezMaxDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxBezMaxDeafult())
			{
				i = SingletonSanitisationFields.getMaxBezMaxDeafult();
			}
		}
		bezMax = i;
		if(bezMin>bezMax)
		{
			int a = bezMin;
			bezMin = bezMax;
			bezMax = a;
		}
	}

	public static int getBezChance() {
		return bezChance;
	}

	public static void setBezChance(int bezChance2) {
		if(bezChance2 != bezChance)
		{
			bezChanges = true;
		}
		bezChance = bezChance2;
		int i = bezChance;
		if(i<SingletonSanitisationFields.getMinBezChanceDeafult())
		{
			i = SingletonSanitisationFields.getMinBezChanceDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxBezChanceDeafult())
			{
				i = SingletonSanitisationFields.getMaxBezChanceDeafult();
			}
		}
		bezChance = i;
	}

	public static int getBezNum() {
		return bezNum;
	}

	public static void setBezNum(int bezNum2) {
		if(bezNum2 != bezNum)
		{
			bezChanges = true;
		}
		bezNum = bezNum2;
		int i = bezNum;
		if(i<SingletonSanitisationFields.getMinBezNumDeafult())
		{
			i = SingletonSanitisationFields.getMinBezNumDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxBezNumDeafult())
			{
				i = SingletonSanitisationFields.getMaxBezNumDeafult();
			}
		}
		bezNum = i;
	
	}

	public static String getBezType() {
		return bezType;
	}

	public static void setBezType(String bezType2) {
		if(bezType2.equals(bezType) )
		{
		}else
		{
			bezChanges = true;
		}
		if(bezType2.equals(bezType))
		{
		}else
		{
			bezChanges = true;
		}
		SingletonHolder.bezType = bezType2;
	}

	public static boolean isBezFlag() {
		return bezFlag;
	}

	public static void setBezFlag(boolean bezFlag) {
		SingletonHolder.bezFlag = bezFlag;
	}

	public static boolean isBezChanges() {
		return bezChanges;
	}

	public static void setBezChanges(boolean bezChanges) {
		SingletonHolder.bezChanges = bezChanges;
	}

	

	public static int getBezSizeDeafult() {
		return BEZSIZE_DEAFULT;
	}

	

	public static int getBezSize() {
		return bezSize;
	}

	public static void setBezSize(int bezSize2) {
		if(bezSize2 != bezSize)
		{
			bezChanges = true;
		}
		bezSize = bezSize2;
		int i = bezSize;
		if(i<SingletonSanitisationFields.getMinBezSizeDeafult())
		{
			i = SingletonSanitisationFields.getMinBezSizeDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxBezSizeDeafult())
			{
				i = SingletonSanitisationFields.getMaxBezSizeDeafult();
			}
		}
		bezSize = i;
	
	}

	public static int getBezVarience() {
		return bezVarience;
	}

	public static void setBezVarience(int bezVarience2) {
		if(bezVarience2 != bezVarience)
		{
			bezChanges = true;
		}
		bezVarience = bezVarience2;
		int i = bezVarience;
		if(i<SingletonSanitisationFields.getMinBezVarDeafult())
		{
			i = SingletonSanitisationFields.getMinBezVarDeafult();
		}else
		{
			if(i>SingletonSanitisationFields.getMaxBezVarDeafult())
			{
				i = SingletonSanitisationFields.getMaxBezVarDeafult();
			}
		}
		bezVarience = i;
	}

	

	public static int getBezVarDeafult() {
		return BEZVAR_DEAFULT;
	}

	public static void clean() 
	{		
		increment = 0;
		jumpCounter = 0;
		incrementPointer = 0;
		//totalCells = 0;
		//panelSize = 0;

		SingletonStatStore.clean();
		
		//push = 0;
		calcPush();
	}

	/*public static StatGenerator getStatEngine() {
		return statEngine;
	}

	public static void setStatEngine(StatGenerator statEngine) {
		SingletonHolder.statEngine = statEngine;
	}*/
	
	
	

	public static int getDeathChanceDeafult() {
		return DEATHCHANCE_DEAFULT;
	}

	

	public static int getCellLength() {
		return cellLength;
	}

	public static void setCellLength(int cellLength) {
		SingletonHolder.cellLength = cellLength;
	}

	public static void setJumpSize(double d) {
		jumpSize = d;
		
	}

	public static void setJumpsPerIncrement(double d) {
		jumpsPerIncrement = d;
		
	}

	public static boolean isBezRetainer() {
		return bezRetainer;
	}

	public static void setBezRetainer(boolean bezRetainer) {
		SingletonHolder.bezRetainer = bezRetainer;
	}

	public static int getHeatTopper() {
		return heatTopper;
	}

	public static void setHeatTopper(int heatTopper) {
		SingletonHolder.heatTopper = heatTopper;
	}
	
	public static boolean isTail() {
		return tail;
	}

	public static void setTail(boolean tail) {
		SingletonHolder.tail = tail;
	}

	

	public static boolean isFileShuffle() {
		return shuffleFlag;
	}

	public static void setFileShuffleFlag(boolean fileShuffle) {
		SingletonHolder.shuffleFlag = fileShuffle;
	}

	public static int getDrawMode() {
		return drawMode;
	}

	public static void setDrawMode(int drawMode2) {
		drawMode = drawMode2;
	}

	public static int getwOneVarience() {
		return wOneVarience;
	}

	public static void setwOneVarience(int wOneVarience) {
		SingletonHolder.wOneVarience = wOneVarience;
	}

	public static int getwTwoVarience() {
		return wTwoVarience;
	}

	public static void setwTwoVarience(int wTwoVarience) {
		SingletonHolder.wTwoVarience = wTwoVarience;
	}

	public static int getwThreeVarience() {
		return wThreeVarience;
	}

	public static void setwThreeVarience(int wThreeVarience) {
		SingletonHolder.wThreeVarience = wThreeVarience;
	}

	public static int getwFourVarience() {
		return wFourVarience;
	}

	public static void setwFourVarience(int wFourVarience) {
		SingletonHolder.wFourVarience = wFourVarience;
	}

	public static int getwFiveVarience() {
		return wFiveVarience;
	}

	public static void setwFiveVarience(int wFiveVarience) {
		SingletonHolder.wFiveVarience = wFiveVarience;
	}

	public static int getwSixVarience() {
		return wSixVarience;
	}

	public static void setwSixVarience(int wSixVarience) {
		SingletonHolder.wSixVarience = wSixVarience;
	}

	public static int getwSevenVarience() {
		return wSevenVarience;
	}

	public static void setwSevenVarience(int wSevenVarience) {
		SingletonHolder.wSevenVarience = wSevenVarience;
	}

	public static int getwEightVarience() {
		return wEightVarience;
	}

	public static void setwEightVarience(int wEightVarience) {
		SingletonHolder.wEightVarience = wEightVarience;
	}

	public static int getChangeVarience() {
		return changeVarience;
	}

	public static void setChangeVarience(int changeVarience) {
		SingletonHolder.changeVarience = changeVarience;
	}

	public static int getWONEVARIENCE_DEAFULT() {
		return WONEVARIENCE_DEAFULT;
	}

	public static void setWONEVARIENCE_DEAFULT(int wONEVARIENCE_DEAFULT) {
		WONEVARIENCE_DEAFULT = wONEVARIENCE_DEAFULT;
	}

	public static int getWTWOVARIENCE_DEAFULT() {
		return WTWOVARIENCE_DEAFULT;
	}

	public static void setWTWOVARIENCE_DEAFULT(int wTWOVARIENCE_DEAFULT) {
		WTWOVARIENCE_DEAFULT = wTWOVARIENCE_DEAFULT;
	}

	public static int getWTHREEVARIENCE_DEAFULT() {
		return WTHREEVARIENCE_DEAFULT;
	}

	public static void setWTHREEVARIENCE_DEAFULT(int wTHREEVARIENCE_DEAFULT) {
		WTHREEVARIENCE_DEAFULT = wTHREEVARIENCE_DEAFULT;
	}

	public static int getWFOURVARIENCE_DEAFULT() {
		return WFOURVARIENCE_DEAFULT;
	}

	public static void setWFOURVARIENCE_DEAFULT(int wFOURVARIENCE_DEAFULT) {
		WFOURVARIENCE_DEAFULT = wFOURVARIENCE_DEAFULT;
	}

	public static int getWFIVEVARIENCE_DEAFULT() {
		return WFIVEVARIENCE_DEAFULT;
	}

	public static void setWFIVEVARIENCE_DEAFULT(int wFIVEVARIENCE_DEAFULT) {
		WFIVEVARIENCE_DEAFULT = wFIVEVARIENCE_DEAFULT;
	}

	public static int getWSIXVARIENCE_DEAFULT() {
		return WSIXVARIENCE_DEAFULT;
	}

	public static void setWSIXVARIENCE_DEAFULT(int wSIXVARIENCE_DEAFULT) {
		WSIXVARIENCE_DEAFULT = wSIXVARIENCE_DEAFULT;
	}

	public static int getWSEVENVARIENCE_DEAFULT() {
		return WSEVENVARIENCE_DEAFULT;
	}

	public static void setWSEVENVARIENCE_DEAFULT(int wSEVENVARIENCE_DEAFULT) {
		WSEVENVARIENCE_DEAFULT = wSEVENVARIENCE_DEAFULT;
	}

	public static int getWEIGHTVARIENCE_DEAFULT() {
		return WEIGHTVARIENCE_DEAFULT;
	}

	public static void setWEIGHTVARIENCE_DEAFULT(int wEIGHTVARIENCE_DEAFULT) {
		WEIGHTVARIENCE_DEAFULT = wEIGHTVARIENCE_DEAFULT;
	}

	public static int getCHANGEVARIENCE_DEAFULT() {
		return CHANGEVARIENCE_DEAFULT;
	}

	public static void setCHANGEVARIENCE_DEAFULT(int cHANGEVARIENCE_DEAFULT) {
		CHANGEVARIENCE_DEAFULT = cHANGEVARIENCE_DEAFULT;
	}

	public static void giveFullCellValueSet(String[][] values) {
		cellSets = values;

		SingletonHolder.fullExtrapolateCellValues(values[0]);
		repChanges = true;
		replication = false;
		death = false;
		speedFlag = false;
		antPath = false;
		for(int i = 0 ; i < values.length; i ++)
		{
			if(Boolean.parseBoolean(values[i][0]) == true)
			{
				replication  = true;
			}
			if(Boolean.parseBoolean(values[i][5]) == true)
			{
				death = true;
			}
			if(Boolean.parseBoolean(values[i][11]) == true)
			{
				speedFlag = true;
			}
			if(Boolean.parseBoolean(values[i][37]) == true)
			{
				antPath = true;
			}
		}
		
	}

	private static void fullExtrapolateCellValues(String[] values) {
		//0-4 repCheck, repChance, repTaboo, repVarianceCheck, repVarience
		//5-10 deathCheck, deathChance, deathVarienceCheck, deathVarience, deathType, deathTimedNum
		//11-14 speedCheck, speedAmount, speedVarience, speedType
		//15-16 changeChance, changeVarience
		//17 noOfCells
		//18-19 wOne, oneVar,
		//20-21 wTwo, twoVar,
		//22-23 wThree, threeVar,
		//24-25 wFour, fourVar,
		//26-27 wFive, fiveVar,
		//28-29 wSix, sixVar,
		//30-31 wSeven, sevenVar,
		//32-33 wEight, eightVar,
		//34rand/rou
		//35-36 cell size and cell varience
		
		if(repChanges == false && replication != Boolean.parseBoolean(values[0]) || repChance != (int) Double.parseDouble(values[1]) || repTaboo != (int) Double.parseDouble(values[2]) || repVar != Boolean.parseBoolean(values[3]) || repVarience != (int) Double.parseDouble(values[4]))
		{
			repChanges = true;
		}else
		{
			repChanges = false;
		}
		
		replication = Boolean.parseBoolean(values[0]);
		repChance = (int) Double.parseDouble(values[1]);
		repTaboo = (int) Double.parseDouble(values[2]);
		repVar = Boolean.parseBoolean(values[3]);
		repVarience= (int) Double.parseDouble(values[4]);

		if(deathChanges == false && death != Boolean.parseBoolean(values[5]) || deathChance != (int) Double.parseDouble(values[6]) || deathVar != Boolean.parseBoolean(values[7]) || deathVarience != (int) Double.parseDouble(values[8]) || deathType.equals(values[9]) == false || deathTTR != (int) Double.parseDouble(values[10]))
		{
			deathChanges = true;
		}else
		{
			deathChanges = false;
		}
		
		death = Boolean.parseBoolean(values[5]);
		deathChance = (int) Double.parseDouble(values[6]);
		deathVar = Boolean.parseBoolean(values[7]);
		deathVarience= (int) Double.parseDouble(values[8]);
		deathType = values[9];
		deathTTR = (int) Double.parseDouble(values[10]);

		if(speedChanges == false && speedFlag != Boolean.parseBoolean(values[11]) || speed != (int) Double.parseDouble(values[12]) || speedVar != (int) Double.parseDouble(values[13]) || speedType.equals(values[14]) == false )
		{
			speedChanges = true;
		}else
		{
			speedChanges = false;
		}
		
		speedFlag = Boolean.parseBoolean(values[11]);
		speed = (int) Double.parseDouble(values[12]);
		speedVar = (int) Double.parseDouble(values[13]);
		speedType = values[14];
		
		if(turnChanged == false && change != (int) Double.parseDouble(values[15])||	changeVarience != (int) Double.parseDouble(values[16]) ||
		wOne != (int) Double.parseDouble(values[18]) || wOneVarience != (int) Double.parseDouble(values[19])||
		wTwo != (int) Double.parseDouble(values[20])|| wTwoVarience != (int) Double.parseDouble(values[21])||
		wThree != (int) Double.parseDouble(values[22])|| wThreeVarience != (int) Double.parseDouble(values[23])||
		wFour != (int) Double.parseDouble(values[24])|| wFourVarience != (int) Double.parseDouble(values[25])||
		wFive != (int) Double.parseDouble(values[26])||	wFiveVarience != (int) Double.parseDouble(values[27])||
		wSix != (int) Double.parseDouble(values[28])|| wSixVarience != (int) Double.parseDouble(values[29])||
		wSeven != (int) Double.parseDouble(values[30])|| wSevenVarience != (int) Double.parseDouble(values[31])||
		wEight != (int) Double.parseDouble(values[32])|| wEightVarience != (int) Double.parseDouble(values[33]))
		{
			SingletonHolder.setTurnChanged(true);
		}
		change = (int) Double.parseDouble(values[15]);
		changeVarience = (int) Double.parseDouble(values[16]);
		
		if(cells != Double.parseDouble(values[17]))
		{
			SingletonHolder.setChanged(true);
		}
		cells = (int) Double.parseDouble(values[17]);

		
		
		wOne = (int) Double.parseDouble(values[18]);
		wOneVarience = (int) Double.parseDouble(values[19]);
		
		wTwo = (int) Double.parseDouble(values[20]);
		wTwoVarience = (int) Double.parseDouble(values[21]);
		
		wThree = (int) Double.parseDouble(values[22]);
		wThreeVarience = (int) Double.parseDouble(values[23]);
		
		wFour = (int) Double.parseDouble(values[24]);
		wFourVarience = (int) Double.parseDouble(values[25]);
		
		wFive = (int) Double.parseDouble(values[26]);
		wFiveVarience = (int) Double.parseDouble(values[27]);
		
		wSix = (int) Double.parseDouble(values[28]);
		wSixVarience = (int) Double.parseDouble(values[29]);
		
		wSeven = (int) Double.parseDouble(values[30]);
		wSevenVarience = (int) Double.parseDouble(values[31]);
		
		wEight = (int) Double.parseDouble(values[32]);
		wEightVarience = (int) Double.parseDouble(values[33]);
		
		trajType = values[34];
		
		cellSize = (int) Double.parseDouble(values[35]);
		cellSizeVarience = (int) Double.parseDouble(values[36]);
		
		SingletonHolder.setCellValues(values);
		
	}

	public static int getCELLSIZE_DEAFULT() {
		return CELLSIZE_DEAFULT;
	}

	public static void setCELLSIZE_DEAFULT(int cELLSIZE_DEAFULT) {
		CELLSIZE_DEAFULT = cELLSIZE_DEAFULT;
	}

	public static int getCELLSIZEVARIENCE_DEAFULT() {
		return CELLSIZEVARIENCE_DEAFULT;
	}

	public static void setCELLSIZEVARIENCE_DEAFULT(int cELLSIZEVARIENCE_DEAFULT) {
		CELLSIZEVARIENCE_DEAFULT = cELLSIZEVARIENCE_DEAFULT;
	}

	public static int getCellSize() {
		return cellSize;
	}

	public static void setCellSize(int cellSize) {
		SingletonHolder.cellSize = cellSize;
	}

	public static int getCellSizeVarience() {
		return cellSizeVarience;
	}

	public static void setCellSizeVarience(int cellSizeVarience) {
		SingletonHolder.cellSizeVarience = cellSizeVarience;
	}

	public static boolean isTurnChanged() {
		return turnChanged;
	}

	public static void setTurnChanged(boolean turnChanged) {
		turnChanged = turnChanged;
	}

	public static String[] getCellValues() {
		return cellValues;
	}

	public static void setCellValues(String[] cellValues) {
		SingletonHolder.cellValues = cellValues;
	}

	public static double[] genWeights(String[] val) {
		//0-4 repCheck, repChance, repTaboo, repVarianceCheck, repVarience
		//5-10 deathCheck, deathChance, deathVarienceCheck, deathVarience, deathType, deathTimedNum
		//11-14 speedCheck, speedAmount, speedVarience, speedType
		//15-16 changeChance, changeVarience
		//17 noOfCells
		//18-19 wOne, oneVar,
		//20-21 wTwo, twoVar,
		//22-23 wThree, threeVar,
		//24-25 wFour, fourVar,
		//26-27 wFive, fiveVar,
		//28-29 wSix, sixVar,
		//30-31 wSeven, sevenVar,
		//32-33 wEight, eightVar,
		//34rand/rou
		//35-36 cell size and cell varience
		
		double[] weights = new double[8];
		for(int i = 0; i < 16;i+=2)
		{
			if(Long.parseLong(val[19+i]) == 0)
			{
				weights[i/2] = Long.parseLong(val[18+i]);
			}else
			{
				
				weights[i/2] = SingletonHolder.generateFromRange(Long.parseLong(val[18+i]),Long.parseLong(val[19+i]));
			}
		}
		return weights;
	}

	private static double generateFromRange(double x, double y) {
		//System.out.println(""+x+" "+y);
		Random rand = SingletonHolder.getMasterRandom();
		double wei =  (rand.nextDouble()*(y*2));
		wei += x-y;
		if(y == 0)
		{
			wei = x;
			
		}
		return wei;
	}

	public static String[][] getCellSets() {
		return cellSets;
	}

	public static void setCellSets(String[][] cellSets) {
		SingletonHolder.cellSets = cellSets;
	}

	public static String[] genCellValuesFromSet(String[] set) {
		//0-4 repCheck, repChance, repTaboo, repVarianceCheck, repVarience
		//5-10 deathCheck, deathChance, deathVarienceCheck, deathVarience, deathType, deathTimedNum
		//11-14 speedCheck, speedAmount, speedVarience, speedType
		//15-16 changeChance, changeVarience
		//17 noOfCells
		//18-19 wOne, oneVar,
		//20-21 wTwo, twoVar,
		//22-23 wThree, threeVar,
		//24-25 wFour, fourVar,
		//26-27 wFive, fiveVar,
		//28-29 wSix, sixVar,
		//30-31 wSeven, sevenVar,
		//32-33 wEight, eightVar,
		//34rand/rou
		//35-36 cell size and cell varience
		
		//0-1  repChance, repTaboo
		//2-4  deathChance, deathType, deathTimed
		//5 speed
		//6 change
		//7 wOne
		//8 wTwo
		//9 wThree
		//10 wFour
		//11 wFive
		//12 wSix
		//13 wSeven
		//14 wEight
		//15 trajType
		//16 size
		//17 antWeight
		String[] vals = new String[18];
		if(set[0].equals("true"))
		{
			vals[0] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[1]),Double.parseDouble(set[4]));
		}else
		{
			vals[0] = "0";
		}
		vals[1] = set[2];
		if(set[5].equals("true"))
		{
			vals[2] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[6]),Double.parseDouble(set[8]));
		}else
		{
			vals[2] = "0";
		}
		vals[3] = set[9];
		vals[4] = set[10];
		
		vals[5] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[12]),Double.parseDouble(set[13]),set[14]);
		vals[6] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[15]),Double.parseDouble(set[16])); 
		vals[7] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[18]),Double.parseDouble(set[19])); 
		vals[8] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[20]),Double.parseDouble(set[21])); 
		vals[9] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[22]),Double.parseDouble(set[23])); 
		vals[10] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[24]),Double.parseDouble(set[25])); 
		vals[11] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[26]),Double.parseDouble(set[27])); 
		vals[12] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[28]),Double.parseDouble(set[29])); 
		vals[13] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[30]),Double.parseDouble(set[31])); 
		vals[14] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[32]),Double.parseDouble(set[33])); 
		vals[15] = set[34]; 
		vals[16] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[35]),Double.parseDouble(set[36])); 
		vals[17] = ""+SingletonHolder.generateFromRange(Double.parseDouble(set[38]),Double.parseDouble(set[39])); 
		
		return vals;
	}

	private static double generateFromRange(Double a,
			Double b, String type) {
		double returner = 0;
		if(type.equals("normal"))
		{
			Random rand = SingletonHolder.getMasterRandom();
			
				double gaus = rand.nextGaussian()*0.5;
				do 
				{
					gaus = rand.nextGaussian()*0.5;
				}while(gaus > 1 || gaus < -1);
				gaus = gaus-0.5;
				double bottom = a-b;
				double top = a+b;
				double range = top-bottom;
				double mid = a;
				double chosen =  (mid + (range*gaus));
				returner =  chosen;
		}else
		{
			returner = generateFromRange(a,b);
		}
		return returner;
	}

	public static int getCellTotalFromSets() {
		int tot = 0;
		for(int i = 0; i < cellSets.length;i++)
		{
			tot += Integer.parseInt(cellSets[i][17]);
		}
		return tot;
	}

	public static int getBezzLength() {
		return bezzLength;
	}

	public static void setBezzLength(int bezzLength) {
		SingletonHolder.bezzLength = bezzLength;
	}

	public static void toggleDeath() {
		if(isDeathFlag() == true)
		{
			setDeathFlag(false);
		}else
		{
			setDeathFlag(true);
		}
		if(isReplication() == true)
		{
			setReplication(false);
		}else
		{
			setReplication(true);
		}
		setDeathChanges(true);
	}

	public static void toggleRep() {
		if(isReplicationFlag() == true)
		{
			setReplicationFlag(false);
		}else
		{
			setReplicationFlag(true);
		}
		if(isDeath() == true)
		{
			setDeath(false);
		}else
		{
			setDeath(true);
		}
		setRepChanges(true);
	}

	public static boolean isReplicationFlag() {
		return replicationFlag;
	}

	public static void setReplicationFlag(boolean replicationFlag) {
		SingletonHolder.replicationFlag = replicationFlag;
	}

	public static boolean isDeathFlag() {
		return deathFlag;
	}

	public static void setDeathFlag(boolean deathFlag) {
		SingletonHolder.deathFlag = deathFlag;
	}

	public static boolean isCirc() {
		return circ;
	}

	public static void setCirc(boolean circ) {
		SingletonHolder.circ = circ;
	}

	public static boolean isBack() {
		return back;
	}

	public static void setBack(boolean back) {
		SingletonHolder.back = back;
	}

	public static BufferedImage getBackImg() {
		return backImg;
	}

	public static void setBackImg(BufferedImage bufferedImage) {
		SingletonHolder.backImg = bufferedImage;
	}

	public static boolean isMos() {
		return mos;
	}

	public static void setMos(boolean mos) {
		SingletonHolder.mos = mos;
	}

	public static boolean isMusic() {
		return music;
	}

	public static void setMusic(boolean music) {
		SingletonHolder.music = music;
	}

	public static double getHeat() {
		return heat;
	}

	public static void setHeat(double heat) {
		SingletonHolder.heat = heat;
	}

	public static double getGamma() {
		return gamma;
	}

	public static void setGamma(double gamma) {
		SingletonHolder.gamma = gamma;
	}

	public static boolean isShowID() {
		return showID;
	}

	public static void setShowID(boolean showID) {
		SingletonHolder.showID = showID;
	}

	public static boolean isShowingOverlappers() {
		return showingOverlappers;
	}

	public static void setShowingOverlappers(boolean showingOverlappers) {
		SingletonHolder.showingOverlappers = showingOverlappers;
	}

	public static boolean isBezBounceFlag() {
		return bezBounceFlag;
	}

	public static void setBezBounceFlag(boolean bezBounceFlag) {
		SingletonHolder.bezBounceFlag = bezBounceFlag;
	}

	public static boolean isInvertHeat() {
		return invertHeat;
	}

	public static void setInvertHeat(boolean invertHeat) {
		SingletonHolder.invertHeat = invertHeat;
	}

	public static int getColSelector() {
		return colSelector;
	}

	public static void setColSelector(int colSelector) {
		SingletonHolder.colSelector = colSelector;
	}

	public static int getBezSVar() {
		return bezSVar;
	}

	public static void setBezSVar(int bezSVar2) {
		if(bezSVar2 != bezSVar)
		{
			setBezChanges(true);
		}
		SingletonHolder.bezSVar = bezSVar2;
	}

	public static boolean isBezDist() {
		return bezDist;
	}

	public static void setBezDist(boolean bezDist2) {
		if(bezDist2 != bezDist)
		{
			setBezChanges(true);
		}
		SingletonHolder.bezDist = bezDist2;
	}

	public static int getBezRed() {
		return bezRed;
	}

	public static void setBezRed(int bezRed2) {
		if(bezRed2 != bezRed)
		{
			setBezGRChanges(true);
		}
		SingletonHolder.bezRed = bezRed2;
	}

	public static int getBezGreen() {
		return bezGreen;
	}

	public static void setBezGreen(int bezGreen2) {
		if(bezGreen2 != bezGreen)
		{
			setBezGRChanges(true);
		}
		SingletonHolder.bezGreen = bezGreen2;
	}

	public static boolean isBezStrengthFlag() {
		return bezStrengthFlag;
	}

	public static void setBezStrengthFlag(boolean bezStrengthFlag) {
		
		SingletonHolder.bezStrengthFlag = bezStrengthFlag;
	}

	public static boolean isBezGRChanges() {
		return bezGRChanges;
	}

	public static void setBezGRChanges(boolean bezGRChanges) {
		SingletonHolder.bezGRChanges = bezGRChanges;
	}

	public static ArrayList<ArrayList<double[]>> getBoxHolder() {
		return boxHolder;
	}

	public static void setBoxHolder(ArrayList<ArrayList<double[]>> newBoxes) {
		SingletonHolder.boxHolder = newBoxes;
	}

	public static long getANTRATIO_DEAFULT() {
		return ANTRATIO_DEAFULT;
	}

	public static void setANTRATIO_DEAFULT(long aNTRATIO_DEAFULT) {
		ANTRATIO_DEAFULT = aNTRATIO_DEAFULT;
	}

	public static long getAntRatio() {
		return antRatio;
	}

	public static void setAntRatio(long antRatio2) {
		if(antRatio2 != antRatio)
		{
			setAntChanges(true);
		}
		SingletonHolder.antRatio = antRatio2;
	}

	public static boolean isAntPath() {
		return antPath;
	}

	public static void setAntPath(boolean antPath) {
		SingletonHolder.antPath = antPath;
	}

	public static long getANTVARIENCERATIO_DEAFULT() {
		return ANTVARIENCERATIO_DEAFULT;
	}

	public static void setANTVARIENCERATIO_DEAFULT(long aNTVARIENCERATIO_DEAFULT) {
		ANTVARIENCERATIO_DEAFULT = aNTVARIENCERATIO_DEAFULT;
	}

	public static boolean isAntChanges() {
		return antChanges;
	}

	public static void setAntChanges(boolean antChanges) {
		SingletonHolder.antChanges = antChanges;
	}

	public static long getAntVarienceRatio() {
		return antVarienceRatio;
	}

	public static void setAntVarienceRatio(long antVarienceRatio2) {
		if(antVarienceRatio2 != antVarienceRatio)
		{
			setAntChanges(true);
		}
		SingletonHolder.antVarienceRatio = antVarienceRatio2;
	}

	public static String getDistribution() {
		return distribution;
	}

	public static void setDistribution(String distribution2) {
		if(distribution2.equals(distribution) == false)
		{
			setDistChanges(true);
		}
		SingletonHolder.distribution = distribution2;
	}

	public static boolean isDistChanges() {
		return distChanges;
	}

	public static void setDistChanges(boolean distChanges) {
		SingletonHolder.distChanges = distChanges;
	}

	public static Random getMasterRandom() {
		return masterRandom;
	}

	public static void setMasterRandom(Random masterRandom) {
		SingletonHolder.masterRandom = masterRandom;
	}

	public static long getMasterSeed() {
		return masterSeed;
	}

	public static void setMasterSeed(long masterSeed) {
		SingletonHolder.masterSeed = masterSeed;
	}

	public static String getRunType() {
		return runType;
	}

	public static void setRunType(String runType) {
		SingletonHolder.runType = runType;
	}

	public static void setBrakes(int i) {
		// TODO Auto-generated method stub
		brakes = i;
	}

	public static boolean isAttractZoneFlag() {
		return attractZoneFlag;
	}

	public static void setAttractZoneFlag(boolean attractZoneFlag) {
		if(attractZoneFlag != SingletonHolder.attractZoneFlag)
		{
			attractZoneChanges = true;
		}
		SingletonHolder.attractZoneFlag = attractZoneFlag;
	}

	public static boolean isDeflectZoneFlag() {
		return deflectZoneFlag;
	}

	public static void setDeflectZoneFlag(boolean deflectZoneFlag) {
		if(deflectZoneFlag != SingletonHolder.deflectZoneFlag)
		{
			deflectZoneChanges = true;
		}
		SingletonHolder.deflectZoneFlag = deflectZoneFlag;
	}

	public static boolean isAttractZoneChanges() {
		return attractZoneChanges;
	}

	public static void setAttractZoneChanges(boolean attractZoneChanges) {
		SingletonHolder.attractZoneChanges = attractZoneChanges;
	}

	public static boolean isDeflectZoneChanges() {
		return deflectZoneChanges;
	}

	public static void setDeflectZoneChanges(boolean deflectZoneChanges) {
		SingletonHolder.deflectZoneChanges = deflectZoneChanges;
	}

	public static boolean isShowZones() {
		return showZones;
	}

	public static void setShowZones(boolean showZones) {
		SingletonHolder.showZones = showZones;
	}

	public static double getAttractSize() {
		return attractSize;
	}

	public static void setAttractSize(double attractSize) {
		if(attractSize != SingletonHolder.attractSize)
		{
			attractZoneChanges = true;
		}
		SingletonHolder.attractSize = attractSize;
	}

	public static double getAttractStrength() {
		return attractStrength;
	}

	public static void setAttractStrength(double attractStrength) {
		if(attractStrength != SingletonHolder.attractStrength)
		{
			attractZoneChanges = true;
		}
		SingletonHolder.attractStrength = attractStrength;
	}

	public static double getAttractFalloff() {
		return attractFalloff;
	}

	public static void setAttractFalloff(double attractFalloff) {
		if(attractFalloff != SingletonHolder.attractFalloff)
		{
			attractZoneChanges = true;
		}
		SingletonHolder.attractFalloff = attractFalloff;
	}

	public static double getAttractZones() {
		return attractZones;
	}

	public static void setAttractZones(double attractZones) {
		if(attractZones != SingletonHolder.attractZones)
		{
			attractZoneChanges = true;
		}
		SingletonHolder.attractZones = attractZones;
	}

	public static double getAttractSizeVar() {
		return attractSizeVar;
	}

	public static void setAttractSizeVar(double attractSizeVar) {
		if(attractSizeVar != SingletonHolder.attractSizeVar)
		{
			attractZoneChanges = true;
		}
		SingletonHolder.attractSizeVar = attractSizeVar;
	}

	public static double getAttractStrengthVar() {
		return attractStrengthVar;
	}

	public static void setAttractStrengthVar(double attractStrengthVar) {
		if(attractStrengthVar != SingletonHolder.attractStrengthVar)
		{
			attractZoneChanges = true;
		}
		SingletonHolder.attractStrengthVar = attractStrengthVar;
	}

	public static double getDeflectSize() {
		return deflectSize;
	}

	public static void setDeflectSize(double deflectSize) {
		if(deflectSize != SingletonHolder.deflectSize)
		{
			deflectZoneChanges = true;
		}
		SingletonHolder.deflectSize = deflectSize;
	}

	public static double getDeflectStrength() {
		return deflectStrength;
	}

	public static void setDeflectStrength(double deflectStrength) {
		if(deflectStrength != SingletonHolder.deflectStrength)
		{
			deflectZoneChanges = true;
		}
		SingletonHolder.deflectStrength = deflectStrength;
	}

	public static double getDeflectFalloff() {
		return deflectFalloff;
	}

	public static void setDeflectFalloff(double deflectFalloff) {
		if(deflectFalloff != SingletonHolder.deflectFalloff)
		{
			deflectZoneChanges = true;
		}
		SingletonHolder.deflectFalloff = deflectFalloff;
	}

	public static double getDeflectZones() {
		return deflectZones;
	}

	public static void setDeflectZones(double deflectZones) {
		if(deflectZones != SingletonHolder.deflectZones)
		{
			deflectZoneChanges = true;
		}
		SingletonHolder.deflectZones = deflectZones;
	}

	public static double getDeflectSizeVar() {
		return deflectSizeVar;
	}

	public static void setDeflectSizeVar(double deflectSizeVar) {
		if(deflectSizeVar != SingletonHolder.deflectSizeVar)
		{
			deflectZoneChanges = true;
		}
		SingletonHolder.deflectSizeVar = deflectSizeVar;
	}

	public static double getDeflectStrengthVar() {
		return deflectStrengthVar;
	}

	public static void setDeflectStrengthVar(double deflectStrengthVar) {
		if(deflectStrengthVar != SingletonHolder.deflectStrengthVar)
		{
			deflectZoneChanges = true;
		}
		SingletonHolder.deflectStrengthVar = deflectStrengthVar;
	}

	public static void setJumpCounter(double d) {
		jumpCounter = (int) d;
		
	}

	public static double getAttractEye() {
		return attractEye;
	}

	public static void setAttractEye(double attractEye) {
		if(attractEye != SingletonHolder.attractEye)
		{
			attractZoneChanges = true;
		}
		SingletonHolder.attractEye = attractEye;
	}
	public static int getVarEnd() {
		return varEnd;
	}
	public static void setVarEnd(int varEnd) {
		SingletonHolder.varEnd = varEnd;
	}
	public static PotentialProcessor getPot() {
		return pot;
	}
	public static void setPot(PotentialProcessor pot) {
		SingletonHolder.pot = pot;
	}

	

}
