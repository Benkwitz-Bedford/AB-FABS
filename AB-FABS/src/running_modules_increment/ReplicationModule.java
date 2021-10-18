package running_modules_increment;

public class ReplicationModule extends Module{

	public ReplicationModule()
	{

		modType = "rep";
	}
	public void genNext(Bundle sim)
	{
		sim.getCellMan().getStore().setCells(sim.getRepMan().replicateAll(sim.getCellMan().getStore().getCells(), sim.getMoveMan()));
	}
}
