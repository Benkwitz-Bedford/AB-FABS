package running_modules_increment;

import java.util.ArrayList;

import cycle_components.BezManager;
import cycle_components.CellManager;
import cycle_components.DeathManager;
import cycle_components.MoveManager;
import cycle_components.MoveManagerCA;
import cycle_components.RepManager;
import cycle_components.RepManagerCA;
import cycle_components.TrajectoryManager;
import cycle_components.TrajectoryManagerCA;
import gui_main.GridPanel;
import heatmaps.MosaicHeatmap;
import singleton_holders.SingletonHolder;

public class DataBundleCA extends Bundle{

	private BezManager bezMan = null;
	private DeathManager deathMan = null;
	private MoveManager moveMan = null;
	private RepManager repMan = null;
	private TrajectoryManager trajMan = null;
	private CellManager cellMan = null;
	
	private int dimensions = 0;
	private int posSize = 0;
	private GridPanel parent ;
	private MosaicHeatmap mos;
	
	public DataBundleCA( int dim, int posSize, GridPanel parent) {
		bezMan = new BezManager();
	   	deathMan = new DeathManager();
	    moveMan = new MoveManagerCA(posSize);
	   	repMan = new RepManagerCA();
	   	trajMan = new TrajectoryManagerCA();
	   	cellMan = new CellManager();
		dimensions = dim;
		this.posSize = posSize;
		this.parent = parent;
	   	mos =new MosaicHeatmap(dimensions/getPosSize(),dimensions/getPosSize(), moveMan.getPositionGrid());
	}
	public void resetMos() {
		getMos().fullUpdate(getMoveMan().getPositionGrid(), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
		
	}

	public  ArrayList<double[]> getUpdatedMosSites() {
		// TODO Auto-generated method stub
		return mos.getUpdatedSites(getCellMan().getCells(), getMoveMan().getPositionGrid(),(int) SingletonHolder.getHeat(),SingletonHolder.getGamma(), SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
	}
	public BezManager getBezMan() {
		return bezMan;
	}

	public void setBezMan(BezManager bezMan) {
		this.bezMan = bezMan;
	}

	public DeathManager getDeathMan() {
		return deathMan;
	}

	public void setDeathMan(DeathManager deathMan) {
		this.deathMan = deathMan;
	}

	public MoveManager getMoveMan() {
		return moveMan;
	}

	public void setMoveMan(MoveManager moveMan) {
		this.moveMan = moveMan;
	}

	public RepManager getRepMan() {
		return repMan;
	}

	public void setRepMan(RepManager repMan) {
		this.repMan = repMan;
	}

	public TrajectoryManager getTrajMan() {
		return trajMan;
	}

	public void setTrajMan(TrajectoryManager trajMan) {
		this.trajMan = trajMan;
	}

	public CellManager getCellMan() {
		return cellMan;
	}

	public void setCellMan(CellManager cellMan) {
		this.cellMan = cellMan;
	}

	public int getDimensions() {
		// TODO Auto-generated method stub
		return dimensions;
	}

	public void setDimensions(int dim) {
		// TODO Auto-generated method stub
		dimensions = dim;
	}

	public int getPosSize() {
		return posSize;
	}

	public void setPosSize(int posSize) {
		this.posSize = posSize;
	}

	public GridPanel getParent() {
		return parent;
	}

	public void setParent(GridPanel parent) {
		this.parent = parent;
	}

	public MosaicHeatmap getMos() {
		return mos;
	}

	public void setMos(MosaicHeatmap mos) {
		this.mos = mos;
	}

}
