package running_modules_increment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cycle_components.StaticCalculations;
import file_manipulation.Sifter;
import cell_data_holders.Cell;
import cell_data_holders.TempCellConstruct;
import singleton_holders.SingletonHolder;

public class PopSiftModule extends Module{
	private double[][] criteria = null;
	private boolean invert = false;
	private int wipe = 0;
	private int[][] active = null;
	private File extrap = null;
	int counter = 0;
	boolean only = false;
	private Double[][][] actions = null;
	
	public PopSiftModule(String[][] builders, int l, boolean b, boolean c)
	{
		wipe = l;
		invert = b;
		only = c;
		double[][] crit = new double[builders.length][];
		for(int i = 0; i < builders.length;i++ )
		{
			double point = Double.parseDouble(builders[i][0]);
			double inv = Double.parseDouble(builders[i][1]);
			/*if(builders[i][1].equals("true"))
			{
				inv = 1;
			}*/
			double low = Double.parseDouble(builders[i][2]);
			double top = Double.parseDouble(builders[i][3]);
		
			crit[i] = new double[]{point,inv,low,top};
		}
		criteria = crit;
		modType = "pop";
		
	}
	
	public void genNext(Bundle sim)
	{
		//if increment is not the final one
		if(SingletonHolder.getIncrement() != SingletonHolder.getIterations())
		{
			if(wipe !=0&&counter == wipe)
			{
				//System.out.println("cleaning all");
				for(int i = 0; i <sim.getCellMan().getCells().size();i++)
				{
					sim.getCellMan().getCells().get(i).getPack().clean();
				}
				counter =0;
			}
			if(only )
			{
				//only checked
				for(int i = 0; i < sim.getCellMan().getCells().size();i++)
				{
					checkOnly(i, sim);
				}

				counter++;
			}else
			{
				//check to see if each cell is active in this inc
				//nothing checked
				for(int i = 0; i < sim.getCellMan().getCells().size();i++)
				{
					//if so add it to the list
					//could be improved with a hash table or cell order array
					if(sim.getCellMan().getCells().get(i).isCorporeal())
					{
						active[SingletonHolder.getIncrement()][i] = checkAgainstCriteria(sim.getCellMan().getCells().get(i));
					}
				}
				counter++;
			}
		}else
		{
			if(only)
			{
				filerOnActivityRecordWhenApplicable(extrap,active,invert);
			}else
			{
				//if final increment filter file based on array
				Sifter sift = new Sifter();
				sift.filterOnActivityRecordPermInclusion(extrap,active,invert);
			}
		}
		
	}
	
	public void checkOnly(int i, Bundle sim) {
		//hot fix to fix the checking being before action but after stuff de-syncs
		int remover = 0;
		/*if(SingletonHolder.getIncrement() ==0||SingletonHolder.getIncrement() ==1)
		{
			remover = 0;
		}*/
		if(sim.getCellMan().getCells().get(i).isCorporeal())
		{
			/*if(checkAgainstCriteria(sim.getCellMan().getCells().get(i)) == 1)
			{
				this.addToActions(SingletonHolder.getIncrement()-remover,i,sim.getCellMan().getCells().get(i));
			}*/
			int checked = checkAgainstCriteria(sim.getCellMan().getCells().get(i));
			if(invert)
			{
				if(checked == 0 )
				{
					checked = 1;
				}else
				{
					checked = 0;
				}
			}
			
			if(checked == 1)
			{
				this.addToActions(SingletonHolder.getIncrement()-remover,i,sim.getCellMan().getCells().get(i));
				
			}else
			{
				System.out.println("not included "+i+" at "+(SingletonHolder.getIncrement()-remover));
			}
		}
		
	}

	private void addToActions(int increment,int i, Cell cell) {
		actions[increment][i] = new Double[]{(double) i,cell.getSpeed(),cell.getPositionX(),cell.getPositionY(),cell.getVect(),(double) increment};
		
	}

	private void filerOnActivityRecordWhenApplicable(File extrap,
			int[][] active, boolean perm) {
		//could eventually add a field in extraps to say if cell is existent or trackable for period of time
		//invert active
		/*if(perm)
		{
			for(int x = 0; x < active.length;x++)
			{
				for(int y = 0; y < active[x].length;y++)
				{
					if(active[x][y] == 0)
					{
						active[x][y] = 1;
					}else
					{

						active[x][y] = 0;
					}
				}
			}
		}*/
		//have array of cells and their actions, link those without changes
		//gap = new cell otherwise create a cell for each occurrance
		//sequences - consecutive in bounds commands
		ArrayList<ArrayList<ArrayList<Double[]>>> sequences = new ArrayList<ArrayList<ArrayList<Double[]>>>();
		for(int i = 0; i < actions.length;i++)
		{
			sequences.add(new ArrayList<ArrayList<Double[]>>());
		}
		for(int i = 0; i < actions[0].length;i++)
		{
			for(int l = 0; l < sequences.size();l++)
			{
				sequences.get(l).add(new ArrayList<Double[]>());
			}
		}
		for(int x = 0; x < actions[0].length;x++)
		{
			ArrayList<Double[]> seq = new ArrayList<Double[]>();
			for(int y = 0; y < actions.length;y++)
			{
				boolean missed = false;
				if(actions[y][x] == null)
				{
					//System.out.println("missed "+x+" "+y);
					missed = true;
				}
				if(missed || y == actions.length-1)
				{
					if(seq.size() > 0)
					{
						sequences.get(seq.get(0)[5].intValue()).set(x,seq);
						seq = new ArrayList<Double[]>();
					}
				}
				if(missed == false)
				{
					seq.add(actions[y][x]);
				}
			}
		}
		//births - each sequence needs a birth at the start of the sequence with initial vector data
		//for sequences assign them a birth in order and a new cell num based on it
		ArrayList<Double[]> births = new ArrayList<Double[]>();
		for(int i =0; i < sequences.size();i++)
		{
			for(int l = 0; l < sequences.get(i).size();l++)
			{
				if(sequences.get(i).get(l).size() > 0)
				{
					Double[] start = sequences.get(i).get(l).get(0);
					//actions[increment][i] = new Double[]{(double) i,cell.getSpeed(),cell.getPositionX(),cell.getPositionY(),cell.getVect()};
					//time,cell name, speed, x, y, vect, life time
					int count = i;
					if(i != -1)
					{
						//count --;
					}
					Double[] birth = null;
					/*if(count >0)
					{
						birth = new Double[]{(double) count+1,start[0],start[1],start[2],start[3],start[4],(double) sequences.get(i).get(l).size()-1};
					}else
					{*/
						birth = new Double[]{(double) count,start[0],start[1],start[2],start[3],start[4],(double) sequences.get(i).get(l).size()};
					//}
					
					births.add(birth);
					for(int m = 0; m < sequences.get(i).get(l).size();m++)
					{
						//ressign cell num
						sequences.get(i).get(l).get(m)[0] = (double) (births.size()-1);
					}
				}
			}
		}
		
		//change points - each birth only then needs the start time and the change times
		//for each sequence remove duplicate vectors
		for(int i =0; i < sequences.size();i++)
		{
			for(int l = 0; l < sequences.get(i).size();l++)
			{
				for(int m = 1; m < sequences.get(i).get(l).size() && sequences.get(i).get(l).get(m).length > 1;m++)
				{
					if(Double.compare(sequences.get(i).get(l).get(m)[1] , sequences.get(i).get(l).get(m-1)[1]) ==0&& Double.compare(sequences.get(i).get(l).get(m)[4] , sequences.get(i).get(l).get(m-1)[4])==0)
					{
						sequences.get(i).get(l).remove(m);
						m--;
					}
				}
			}
		}
		
		//sort by time and output births on line 1 and change points on line 2
		//pile sequences
		List<Double[]>[] pile = new List[actions.length];
		for(int i = 0; i < sequences.size();i++)
		{
			pile[i] = new ArrayList<Double[]>();
		}
		for(int i =0; i < sequences.size();i++)
		{
			for(int l = 0; l < sequences.get(i).size();l++)
			{
				if(sequences.get(i).get(l).size()>0)
				{
					ArrayList<Double[]> set = sequences.get(i).get(l);
					//set.remove(0);
					for(int m = 0; m < set.size();m++)
					{
						pile[set.get(m)[5].intValue()].add(set.get(m));
					}
				}
			}
		}
		/*for(int i =1; i < pile.length;i++)
		{
			if(pile[i].size() == pile[i-1].size())
			{
				boolean same = true;
				int l = 0;
				do 
				{
					Double[] seq1 = pile[i].get(l);
					Double[] seq2 = pile[i-1].get(l);
					if(seq1[0] == seq2[0]&&seq1[4]==seq2[4]&&seq1[1]==seq2[1])
					{
						
					}else
					{
						same = false;
					}
					l++;
				}while(same||l < pile[i].size());
			}
		}*/
		/*String first = null;
		//now we have births and events we just need to print them out
		try {
			Scanner read;
			read = new Scanner (extrap);
		
		first = read.nextLine();
		read.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("loading failed");
			System.exit(0);
			e.printStackTrace();
		}
		String[] firstBreak = first.split(" ");*/
		
		String top = "cellNum:"+actions[0].length+" timeSteps:"+actions.length+" stepSize:"+(int)SingletonHolder.getJumpsPerIncrement()+" gridSize:"+SingletonHolder.getSize();
		//output starts
		//for all cells need start vector, position x, position y, start speed, start time and life time
		
		String starts = "starting vectors:";
		StringBuilder st = new StringBuilder();
		st.append(starts);
		for(int i = 0; i < births.size(); i++)
		{
			//time,cell name, speed, x, y, vect, life time
			Double[] dub = births.get(i);
			//st.append("n"+sub.getStartVect()+","+sub.getStartX()+","+sub.getStartY()+","+sub.getStartSpeed()+","+sub.getBirthTime()+","+sub.getLifeTime()+","+sub.getOldID());
			st.append("n"+dub[5]+","+dub[3]+","+dub[4]+","+dub[2]+","+dub[0].intValue()+","+dub[6].intValue()+","+0/*dub[1].intValue()*/);
		}
		
		//output events
		//all changes need to be [time, cell, vector, speed] sorted by time
		String eventVect = "changing vectors:";
		StringBuilder sb = new StringBuilder();
		sb.append(eventVect);
		for(int i = 0; i < pile.length; i++)
		{
			for(int l = 0; l < pile[i].size();l++)
			{
				//actions[increment][i] = new Double[]{(double) i,cell.getSpeed(),cell.getPositionX(),cell.getPositionY(),cell.getVect(),(double) increment};
				Double[] evn = pile[i].get(l);
				sb.append("n"+evn[5]+","+evn[0]+","+evn[4]+","+evn[1]);
			}
		}
		File file2 = new File(System.getProperty("user.dir")+"//"+extrap.getName().replace(".txt", "")+"thinnedOnly.txt");
		try {
			PrintWriter writer = new PrintWriter(file2);
			writer.println(top);
			writer.println(st.toString());
			writer.println(sb.toString());
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//print out
		
		System.out.println("births "+births.size());
	}

	

	private int checkAgainstCriteria(Cell cell) {
		int checks = 1;
		//do while false isnt true
		int i = 0;
		do
		{
			double[] crit = criteria[i];
			double value = 0.00;
			//if crit is cell based
			if(crit[0] < cell.getPack().getDataNames().size())
			{
				value = cell.getPack().getData().get((int)crit[0]);
				//System.out.println("val "+value);
			}else
			{
				//if its +1 its euc dist
				if(crit[0] == cell.getPack().getDataNames().size())
				{
					//System.out.println("wompin");
					
					value = StaticCalculations.getEuclidean(cell.getPositionX(), cell.getPositionY(), cell.getBrownianList().get(0)[0], cell.getBrownianList().get(0)[1]);
				}else
				{
					if(crit[0] == cell.getPack().getDataNames().size()+1)
					{
						ArrayList<Double> cong = new ArrayList<Double>();
						//collision, 4 +8
						int cellValStart = 20;
						for(int l = cellValStart;l<cellValStart+8;l++)
						{
							cong.add(cell.getPack().getData().get(l));
							//System.out.println(cell.getPack().getDataNames().get(l));
						}
						value = this.congSet(cong).get(0);
						if(value != 0)
						{
							//System.out.println(value);
						}
					}
					
					
				}
			}
			checks = checkValue(value, crit[1], crit[2], crit[3]);
			//System.out.println(SingletonHolder.getIncrement());
			i++;
		}while(checks == 1 && i < criteria.length);
		
		return checks;
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
	
	private int checkValue(double value, double d, double e, double f) {
		int returner = 0;
	
		if(value >= e && value <= f)
		{
			returner = 1;
		}
		if(d == 1)
		{
			if(returner == 0)
			{
				returner = 1;
			}else
			{
				returner = 0;
			}
		}
				
			
		
		
		return returner;
	}

	public void setCellArray(int cells, int time)
	{
		active =new int[time][cells];
		if(only)
		{
			//time by cells
			actions = new Double[time+1][cells][];
		}
	}

	public File getExtrap() {
		return extrap;
	}

	public void setExtrap(File extrap) {
		this.extrap = extrap;
	}

	
}
