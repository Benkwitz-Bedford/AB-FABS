package running_modules_jump;

import running_modules_increment.Bundle;
import running_modules_increment.DataBundle;
import running_modules_increment.Module;

public class BoundryModule extends Module{

	public BoundryModule()
	{

		modType = "bound";
	}
	
	public void genNext(Bundle sim)
	{
		sim.getMoveMan().detectBoundaryViolationAndFix(sim.getCellMan().getStore().getCells(),sim.getMoveMan().getPositionGrid());
	}
	
}
