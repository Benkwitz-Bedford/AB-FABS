package running_modules_increment;

import singleton_holders.SingletonHolder;

public class TurnModule extends Module{

	public TurnModule()
	{

		modType = "turn";
	}
	public void genNext(Bundle sim)
	{
		if(SingletonHolder.isAntPath())
		{
			sim.getCellMan().getStore().setCells(sim.getTrajMan().genNextAntTragForAll(sim.getCellMan().getStore().getCells(),sim.getMoveMan().getPositionGrid()));
		}else
		{
			sim.getCellMan().getStore().setCells(sim.getTrajMan().genNextTragForAll(sim.getCellMan().getStore().getCells(), sim.getMoveMan().getPositionGrid()));
		}
	}
}
