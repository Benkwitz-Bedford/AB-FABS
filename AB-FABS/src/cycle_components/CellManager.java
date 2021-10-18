package cycle_components;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cell_data_holders.Cell;
import cell_data_holders.CellHandler;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;

public class CellManager {


	private ArrayList<Cell> cells = new ArrayList<Cell>();
	private List<Ellipse2D> circles = new ArrayList<Ellipse2D>();
	private List<Rectangle2D> recs = new ArrayList<Rectangle2D>();
	
	private CellHandler store = new CellHandler();
	private CellHandler store2 = null;
	
	public CellHandler createCells(CellManager cellMan) 
   {
	   ArrayList<Cell> cellArray = new ArrayList<Cell>();
	   
	   Random randomiser = SingletonHolder.getMasterRandom();
	   //make cells and give vect and position
	   for(int i = 0; i < SingletonHolder.getCellSets().length; i++)
	   {
		   String[] set = SingletonHolder.getCellSets()[i];
		   	//0-4 repCheck, repChance, repTaboo, repVarianceCheck, repVarience
			//5-10 deathCheck, deathChance, deathVarienceCheck, deathVarience, deathType, deathTimedNum
			//11-14 speedCheck, speedAmount, speedVarience, speedType
			//15-16 changeChance, changeVarience
			//17 noOfCells
			//18-19 wOne, oneVar,
			//20-21 wTwo, twoVar,
			//22-23 wThree, threeVar,
			//24-25 wFour, fourVar,
			//26-27 wFive, fiveVar,
			//28-29 wSix, sixVar,
			//30-31 wSeven, sevenVar,
			//32-33 wEight, eightVar,
			//34rand/rou
			//35-36 cell size and cell varience
		   boolean evenDist = true;
		   if(Integer.parseInt(set[17]) < 200)
		   {
			   evenDist = false;
		   }
		   Cell c;
		   //System.out.println("pop "+SingletonHolder.getDistribution());
		   if(SingletonHolder.getDistribution().equals("even"))
		   {
			   double sqr = Math.sqrt(Integer.parseInt(set[17]));
			   double size = SingletonHolder.getSize();
			   size = size -sqr;
			   double xMod = size/sqr;
			   double yMod = size/sqr;
			   int uni = 0;
			   for(int x = 0; x < sqr; x ++)
			   {
				   for(int y = 0; y < sqr; y ++)
				   {
					   if(cellArray.size()< Integer.parseInt(set[17]))
					   {
						   c = new Cell(SingletonHolder.getSize(),SingletonHolder.genCellValuesFromSet(set), i, uni,x*xMod,y*yMod);
						   uni++;
	
						   cellArray.add(c);
						   SingletonStatStore.incTotalCells();
					   }
				   } 
			   }
		   }else
		   {
			   if(SingletonHolder.getDistribution().equals("random"))
			   {
				   int uni = 0;
				   for(int l = 0; l < Integer.parseInt(set[17]); l++)
				   {
				   
					   c = new Cell(SingletonHolder.getSize(),SingletonHolder.genCellValuesFromSet(set), i, uni);
					   uni++;
					   cellArray.add(c);
					   SingletonStatStore.incTotalCells();
				   } 
			   }else
			   {
				   if(SingletonHolder.getDistribution().equals("norm"))
				   {
					   for(int l = 0; l < Integer.parseInt(set[17]); l++)
					   {
					   
						   Random rand = SingletonHolder.getMasterRandom();
						   int size = SingletonHolder.getSize()/2;
						   double xG = rand.nextGaussian()*0.5;
						   double yG = rand.nextGaussian()*0.5;
						   do 
						   {
							   xG = rand.nextGaussian()*0.5;
						   }while(xG > 1 || xG < -1);
						   do 
						   {
							   yG = rand.nextGaussian()*0.5;
						   }while(yG > 1 || yG < -1);
						   double xE = size+size*xG;
						   double yE = size+size*yG;
						   c = new Cell(size*2,SingletonHolder.genCellValuesFromSet(set), i, l,xE,yE);
	
						   cellArray.add(c);
						   SingletonStatStore.incTotalCells();
					   } 
				   }
			   }
		   }
		   
	   }
	   cellMan.setStore(new CellHandler());
	   cellMan.getStore().setCells(cellArray);

	   //System.out.println("Created "+cellArray.size());
	   //System.out.println("CellManager actually held "+cellMan.getStore().getCells().size());
	   //System.exit(0);
	   return cellMan.getStore();
	
   }
	
	public ArrayList<Cell> getCells() {
		return cells;
	}
	public void setCells(ArrayList<Cell> cells) {
		this.cells = cells;
	}
	public List<Ellipse2D> getCircles() {
		return circles;
	}
	public void setShapes(List<Ellipse2D> circles, List<Rectangle2D> rec) {
		this.circles = circles;
		recs = rec;
	}
	public CellHandler getStore() {
		return store;
	}
	public void setStore(CellHandler store) {
		this.store = store;
	}
	public CellHandler getStore2() {
		return store2;
	}
	public void setStore2(CellHandler store2) {
		this.store2 = store2;
	}

	public List<Rectangle2D> getRecs() {
		return recs;
	}

	public void setRecs(List<Rectangle2D> recs) {
		this.recs = recs;
	}

	public void addShape(Cell c) {

		//System.out.println(+x+" "+y);
		Ellipse2D circ = new Ellipse2D.Double(c.getPositionX(),c.getPositionY(),c.getCellSize(),c.getCellSize());
		circles.add(circ);
		Rectangle2D rec = new Rectangle2D.Double(c.getPositionX(),c.getPositionY(),c.getCellSize(),c.getCellSize());
		recs.add(rec);
	}
	
}
