package running_modules_increment;

import java.util.ArrayList;

import file_manipulation.DataShuffler;
import gui_stat_gen.StatRunFeedbackFrame;
import gui_stat_gen.Top10Frame;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;
import stat_data_holders.SnapShotObfust;
import stat_data_holders.StatGeneratorObfust;

public class DataModule extends Module{

	ArrayList<ArrayList<SnapShotObfust>> snaps = new ArrayList<ArrayList<SnapShotObfust>>();

	private int statInts = 90;
	protected int dataStart = 1;
	protected int lastRead = 0;
	private int snapTrigger = 0;
	ArrayList<SnapShotObfust> shots = new ArrayList<SnapShotObfust>();
	private DataShuffler shuffle;
	private String vidName = "";
	private int targetRuns;
	private int shuckedRuns = 0;
	long timme = System.currentTimeMillis();
	String[] names;

	private ArrayList<ArrayList<String>> topData = new ArrayList<ArrayList<String>>();

	private ArrayList<ArrayList<String>> topSettings = new ArrayList<ArrayList<String>>();
	private double closest = 999999.99;
	private double furthest = 99999999.99;
	private ArrayList<Double> scores = new ArrayList<Double>();
	Double[][] fittingVals;
	private SnapShotObfust lastSnap = null;
	private boolean fitting = false;
	
	public DataModule(int i, int j, String name, int targetRuns,String[] names,boolean fitting, Double[][] fittingValues)
	{
		System.out.println("name "+name);
		statInts = i;
		dataStart = j;
		snapTrigger = getStatInts();
		vidName = name;
		shuffle = new DataShuffler(name);
		this.targetRuns = targetRuns;
		modType = "data";
		this.names = names;
		this.fittingVals = fittingValues; 
		this.fitting = fitting;
	}
	
	public void genNext(Bundle sim)
	{
		if(getActive())
		{
			//System.out.println("womp");
			if(SingletonHolder.getIncrement() == lastRead + snapTrigger || SingletonHolder.getIncrement() == getDataStart() || SingletonHolder.getIncrement() == SingletonHolder.getIterations())
			{
				lastRead = SingletonHolder.getIncrement();
				System.out.println("creating read at "+SingletonHolder.getIncrement()+" shots size "+shots.size());
				int num = 0;
				for(int m = 0; m < sim.getCellMan().getStore().getCells().size();m++)
				{
					num = num +sim.getCellMan().getStore().getCells().get(m).getCollisions();
				}
				SnapShotObfust slap = null;
				//removed copy clause
				if(fitting)
				{
					//slap = new SnapShotObfust(sim.getDeathMan().getDeaths(), sim.getRepMan().getBirths(), sim.getCellMan().getCells());
					slap = new SnapShotObfust(sim.getDeathMan().getDeaths(), sim.getRepMan().getBirths(), sim.getCellMan().getCells(),sim.getMoveMan().getPositionGrid(),false);
				}else
				{

					slap = new SnapShotObfust(sim.getDeathMan().getDeaths(), sim.getRepMan().getBirths(), sim.getCellMan().getCells(),sim.getMoveMan().getPositionGrid(),true);
				}
				sim.getDeathMan().setDeaths(new ArrayList<int[]>());
				shots.add(slap);
				SingletonStatStore.clean();
				for(int i = 0; i < sim.getCellMan().getCells().size();i++)
				{
					sim.getCellMan().getCells().get(i).getPack().clean();
				}
			}
			
			if(shots.size()>0 && SingletonHolder.getIncrement() == SingletonHolder.getIterations())
			{
				System.out.println("womp womp" +SingletonHolder.getIncrement()+" "+SingletonHolder.getIterations());
				
				snaps.add(shots);
				do
				{
					System.out.println("shucks");
					if(fitting)
					{
						
							
						
						//get similarity to chosen stat 
						//if top 10 add its settings file to array
						boolean top10 = false;
						double ovrSim = 0;
						ArrayList<String> data = snaps.get(0).get(snaps.get(0).size()-1).getDataReport();;
						for(int l =1; l < snaps.get(0).size();l++)
						{
							for(int i = 0; i < fittingVals.length;i++)
							{
								data = snaps.get(0).get(l).getDataReport();
								double dat = Double.parseDouble(data.get(fittingVals[i][0].intValue()).split(";")[1]);
								double similarity = Math.pow(fittingVals[i][1]-dat, 2);
								similarity = Math.sqrt(similarity);
								similarity = (similarity / fittingVals[i][1]) *100;
								ovrSim+=similarity;
							}
						}
						int posi = 0;
						if(topData.isEmpty())
						{
							for(int i = 0; i< 10;i++)
							{
								topData.add(new ArrayList<String>());
								topSettings.add(new ArrayList<String>());
								scores.add(9999999999999999999.00);
							}
						}
						if(ovrSim <= furthest)
						{
							top10=true;
							if(ovrSim < closest)
							{
								closest = ovrSim;
							}
							if(scores.size()> 0)
							{
								for(int i = 0; i < scores.size();i++)
								{
									
									if(ovrSim <= scores.get(i))
									{
										scores.add(i,ovrSim);
										posi = i;
										i = scores.size()+1;
									}
									
								}
							}else
							{
								scores.add(ovrSim);
							}
							if(scores.size()>10)
							{
								do 
								{
									scores.remove(scores.size()-1);
								}while(scores.size()>10);
								furthest = scores.get(scores.size()-1);
							}
						}
						if(top10)
						{
							topData.add(posi,data);
							topSettings.add(posi,snaps.get(0).get(snaps.get(0).size()-1).getSettings());
							if(scores.size()>10)
							{
								do
								{
									topData.remove(10);
									scores.remove(10);
									topSettings.remove(10);
								}while(scores.size()>10);
							}
							shuffle.shuckObfustNoImg(snaps.remove(0),ovrSim);
						}else
						{
							snaps.remove(0);
						}
						
					}else
					{
						shuffle.shuckObfust(snaps.remove(0));
					}
				}while (snaps.size()>0);
				lastSnap = shots.get(shots.size()-1);
				shots = new ArrayList<SnapShotObfust>();
				shuckedRuns++;
				System.out.println(targetRuns+","+shuckedRuns);
				if(targetRuns == shuckedRuns )
				{
					if(fitting)
					{
						//output the top 10 similarity report + their settings files
						for(int i = 0; i < scores.size();i++)
						{
							System.out.println(scores.get(i));
						}
					}else
					{
						if(SingletonHolder.isFileShuffle())
						{
							StatGeneratorObfust statGen = new StatGeneratorObfust();
							statGen.processShuffledData(shuffle);
							StatRunFeedbackFrame data = new StatRunFeedbackFrame();
							data.handAndShowShuffleSet(shuffle,names);
						}else
						{
				
							StatGeneratorObfust statGen = new StatGeneratorObfust();
							statGen.generateMeta(snaps);
							StatRunFeedbackFrame data = new StatRunFeedbackFrame();
							data.handAndShowSnapSet(statGen.getSnapShots(),statGen.getTables(),vidName.replace(".txt", ""), names);
						}
						SingletonHolder.setRunning(false);
						long time = System.currentTimeMillis();
						System.out.println("Time taken: "+( time - timme)/1000+" s");
					}
					}
				
				}
		}
	}

	public void top10Check()
	{
		if(topData.isEmpty() == false)
		{
			Top10Frame top = new Top10Frame(topData,topSettings,scores);
			top.run();
		}
		
	}
	
	public int getStatInts() {
		return statInts;
	}

	public void setStatInts(int statInts) {
		this.statInts = statInts;
	}

	public int getDataStart() {
		return dataStart;
	}

	public void setDataStart(int dataStart) {
		this.dataStart = dataStart;
	}

	public DataShuffler getShuffle() {
		// TODO Auto-generated method stub
		return shuffle;
	}

	public SnapShotObfust getLastSnap() {
		// TODO Auto-generated method stub
		return lastSnap;
	}

	
}
