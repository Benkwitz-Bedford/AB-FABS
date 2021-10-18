package running_modules_increment;

import java.util.ArrayList;

import cycle_components.BezManager;
import cycle_components.CellManager;
import cycle_components.DeathManager;
import cycle_components.MoveManager;
import cycle_components.RepManager;
import cycle_components.TrajectoryManager;
import gui_main.GridPanel;
import heatmaps.MosaicHeatmap;
import singleton_holders.SingletonHolder;

public class Bundle {

	protected BezManager bezMan = null;
	protected DeathManager deathMan = null;
	protected MoveManager moveMan = null;
	protected RepManager repMan = null;
	protected TrajectoryManager trajMan = null;
	protected CellManager cellMan = null;
	
	protected int dimensions = 0;
	protected int posSize = 0;
	protected GridPanel parent ;
	protected MosaicHeatmap mos;
	
	
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
