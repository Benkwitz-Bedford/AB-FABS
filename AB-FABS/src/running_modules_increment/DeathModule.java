package running_modules_increment;

import singleton_holders.SingletonHolder;

public class DeathModule extends Module{

	public DeathModule()
	{

		modType = "death";
	}
	
	public void genNext(Bundle sim)
	{
		sim.getCellMan().getStore().setCells(sim.getDeathMan().checkAll(sim.getCellMan().getStore().getCells()));
		
		if(SingletonHolder.getDeathType().equals("timed"));
		{
			sim.getDeathMan().checkDeathQueue(sim.getCellMan().getStore().getCells());
		}
	}
}
