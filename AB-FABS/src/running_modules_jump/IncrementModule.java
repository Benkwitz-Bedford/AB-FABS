package running_modules_jump;

import running_modules_increment.Bundle;
import running_modules_increment.DataBundle;
import running_modules_increment.Module;
import singleton_holders.SingletonHolder;

public class IncrementModule extends Module{

	public IncrementModule()
	{

		modType = "inc";
	}
	
	public void genNext(Bundle sim)
	{
		
		sim.getCellMan().getStore().setCells(sim.getMoveMan().incrementAllCells(sim.getCellMan().getStore().getCells(), SingletonHolder.getSize(), sim.getCellMan().getCircles(), sim.getMoveMan().getPositionGrid()));
	}
}
