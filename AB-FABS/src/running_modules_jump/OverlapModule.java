package running_modules_jump;

import running_modules_increment.Bundle;
import running_modules_increment.DataBundle;
import running_modules_increment.Module;
import singleton_holders.SingletonHolder;

public class OverlapModule extends Module{

	public OverlapModule()
	{

		modType = "overlap";
	}
	
	public void genNext(Bundle sim)
	{
		sim.getCellMan().setCells(sim.getMoveMan().detectAllOverlapAndFix(sim.getCellMan().getCells(),sim.getBezMan().getCurves(), sim.getMoveMan().getPositionGrid(), sim.getTrajMan(), sim.getBezMan(), SingletonHolder.getSize(), sim.getCellMan().getCircles()));
	}
	
}
