package running_modules_increment;

import java.util.ArrayList;

import cycle_components.BezManager;
import cycle_components.CellManager;
import cycle_components.DeathManager;
import cycle_components.MoveManager;
import cycle_components.MoveManagerPotential;
import cycle_components.RepManager;
import cycle_components.TrajectoryManager;
import gui_main.GridPanel;
import heatmaps.MosaicHeatmap;
import singleton_holders.SingletonHolder;

public class DataBundle extends Bundle{

	
	
	public DataBundle( int dim, int posSize, GridPanel parent) {
		bezMan = new BezManager();
	   	deathMan = new DeathManager();
	    if(SingletonHolder.getPot() ==null)
	    {
		    moveMan = new MoveManager();
	    	
	    }else
	    {
	    	moveMan = new MoveManagerPotential(SingletonHolder.getPot());
	    }
	   	repMan = new RepManager();
	   	trajMan = new TrajectoryManager();
	   	cellMan = new CellManager();
		dimensions = dim;
		this.posSize = posSize;
		this.parent = parent;
	   	mos =new MosaicHeatmap(dimensions/getPosSize(),dimensions/getPosSize(), moveMan.getPositionGrid());
	}
	
}
