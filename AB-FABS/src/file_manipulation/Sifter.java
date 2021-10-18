package file_manipulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Sifter {

	
	public void filterOnActivityRecordPermInclusion(File file, int[][] active,
			boolean perm) 
	{
		
		int[] act = new int[active[0].length]; 
		
		//perm = true;
		//create 1d active array
		for(int l = 0; l < act.length;l++)
		{
			int i = 0;
			int found = 0;
			do
			{
				found = active[i][l];
				i++;
			}while (found == 0 && i < active.length);
			act[l] = found;
		}
		sift(file, act, perm, file.getName());
	}

	
	public void sift(File file, int[] act, boolean perm, String nam)
	{
		//first do while if perm = true to create 1d active list to check vs
		String first = null;
		String second = null;
		String third = null;
		Scanner read;
		String fileName = nam;
		ArrayList<String> starts = new ArrayList<String>();
		ArrayList<String> turns = new ArrayList<String>();
		//invert array
		if(perm)
		{			
			for(int i = 0; i < act.length;i++)
			{
				
				if(act[i] == 0)
				{
					act[i] = 1;
				}else
				{
					act[i] = 0;
				}
					
			}
		}
		try {
			read = new Scanner (file);
		
		first = read.nextLine();
		second = read.nextLine();
		third = read.nextLine();
		read.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("loading failed");
			System.exit(0);
			e.printStackTrace();
		}
		//read in extrap
		String[] secondBreak = third.split(":");
		String[] breakOrigin = secondBreak[1].split("n");
		double[][] trajEvn = new double[breakOrigin.length][4];
		//cellNum:4776 timeSteps:400 stepSize:3 gridSize:1500
		String[] firstBreak = first.split(" ");
		String[][][] sorter = new String[Integer.parseInt(firstBreak[0].split(":")[1])][Integer.parseInt(firstBreak[1].split(":")[1])][];
		//int[] actr = new int[Integer.parseInt(firstBreak[0].split(":")[1])];
		/*for(int i = 0; i < act.length;i++)
		{
			actr[act[i]] = 1;
		}*/
		//for each action if its an active cell add it to the sorter [time][cell][action]
		for(int i = 1; i < trajEvn.length;i++)
		{
			String[] startBreakOne = breakOrigin[i].split(",");
			if(act[(int)Double.parseDouble(startBreakOne[1])]==1)
			{
				sorter[(int)Double.parseDouble(startBreakOne[1])][(int)Double.parseDouble(startBreakOne[0])] = startBreakOne;
				//turns.add(breakOrigin[i]);
			}
			
		}
		
		secondBreak = second.split(":");
		breakOrigin = secondBreak[1].split("n");
		//for each that isnt selected shift all left 1
		for(int i = 1; i < breakOrigin.length;i++)
		{
			if(act[i-1]==1)
			{
				starts.add(breakOrigin[i]);
				//change all with breakOri pointer -1 to starts.size-1
				for(int l = 0; l < sorter[i-1].length;l++)
				{
					String[] s = sorter[i-1][l];
					if(s != null)
					{
						s[1] = ""+(starts.size()-1);
						sorter[i-1][l] = s;
					}
				}
			}
			
		}
		//re-interpolate turns
		for(int i = 0; i < sorter[0].length;i++)
		{
			for(int l = 0; l < sorter.length;l++)
			{
				if(sorter[l][i]!=null && sorter[l][i].length > 1)
				{
					
					StringBuilder sb = new StringBuilder();
					sb.append(sorter[l][i][0]);
					for(int m = 1; m < sorter[l][i].length; m++)
					{
						sb.append(","+sorter[l][i][m]);
					}
					turns.add(sb.toString());
				}
			}
		}
			
			//split traj events that are [time, cell, vector, speed]
			//[time interval, cell num, vector, speed]
		
		
		//String[] firstBreak = first.split(" ");
		String fir = "cellNum:"+starts.size()+" "+firstBreak[1]+" "+firstBreak[2]+" "+firstBreak[3];
		this.offLoad(fir,starts,turns,fileName);

	}

	private void offLoad(String fir, ArrayList<String> startsA,
			ArrayList<String> turnsA, String name) {
		String starts = "starting vectors:";
		System.out.println("c "+startsA.size());
		StringBuilder st = new StringBuilder();
		st.append(starts);
		for(int i = 0; i < startsA.size(); i++)
		{
			st.append("n"+startsA.get(i));
			//starts += "n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime();
		}
		//output events
		//all changes need to be [time, cell, vector, speed] sorted by time
		String eventVect = "changing vectors:";
		StringBuilder sb = new StringBuilder();
		sb.append(eventVect);
		for(int i = 0; i < turnsA.size(); i++)
		{
			sb.append("n"+turnsA.get(i));
			//eventVect += "n"+evn[0]+","+evn[1]+","+evn[2]+","+evn[3];
		}
		File file2 = new File(System.getProperty("user.dir")+"//"+name.replace(".txt", "")+"thinned.txt");
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println(fir);
			writer.println(st.toString());
			writer.println(sb.toString());
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
