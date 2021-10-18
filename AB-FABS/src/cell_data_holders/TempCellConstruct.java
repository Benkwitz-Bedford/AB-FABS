package cell_data_holders;

import java.util.ArrayList;

public class TempCellConstruct {
	//create 'cell' constructs with b time d time l time start pos and x/y * l time positions
	//extrapolate positions into trajectory changes, time, cell, vector, speed
	private int iD = 0;
	private int birthTime = 0;
	private int deathTime = 0;
	private int lifeTime = 0;
	private double startX = 0.00;
	private double startY = 0.00;
	private double startVect = 0.00;
	private double startSpeed = 0.00;
	private double largestPoint = 0.00;
	private double largestXPoint = 0.00;
	private double largestYPoint = 0.00;
	private double smallestXPoint = 1000000.00;
	private double smallestYPoint = 1000000.00;
	private int interSize = 0;
	private ArrayList<Double[]> positions = new ArrayList<Double[]>();  
	private ArrayList<Double[]> events = new ArrayList<Double[]>();
	
	private int oldID = 0;
	
	public TempCellConstruct(String lineValues, int unique, int maxL, int intervalSize)
	{
		iD = unique;
		setInterSize(intervalSize);
		//events = this.extrapolateEventsFromPositions(intervalSize, positions);
	}
	public void figureMetaFromPositions() {
		birthTime =  positions.get(0)[2].intValue();
		deathTime = positions.get(positions.size()-1)[2].intValue();
		lifeTime = deathTime- birthTime;
		startX = positions.get(0)[0];
		startY = positions.get(0)[1];
		for(int i = 0; i < positions.size(); i++)
		{
			Double[] po = positions.get(i);
			if(po[0]> largestPoint)
			{
				largestPoint = po[0];
			}
			if(po[1]> largestPoint)
			{
				largestPoint = po[1];
			}
			if(po[0]> largestXPoint)
			{
				largestXPoint = po[0];
			}else
			{
				if(po[0]< smallestXPoint)
				{
					smallestXPoint = po[0];
				}
			}
			if(po[1]> largestYPoint)
			{
				largestYPoint = po[1];
			}else
			{
				if(po[1]< smallestYPoint)
				{
					smallestYPoint = po[1];
				}
			}
		}
	}

	public TempCellConstruct copy() {
		TempCellConstruct temp = new TempCellConstruct(iD,getInterSize());
		temp.setBirthTime(birthTime);
		temp.setDeathTime(deathTime);
		temp.setLifeTime(lifeTime);
		temp.setStartX(startX);
		temp.setStartY(startY);
		temp.setStartVect(startVect);
		temp.setStartSpeed(startSpeed);
		temp.setLargestPoint(largestPoint);
		temp.setLargestXPoint(largestXPoint);
		temp.setLargestYPoint(largestYPoint);
		temp.setSmallestXPoint(smallestXPoint);
		temp.setSmallestYPoint(smallestYPoint);
		temp.setInterSize(getInterSize());
		temp.setOldID(oldID);
		ArrayList<Double[]> posi = new ArrayList<Double[]>();  
		ArrayList<Double[]> even = new ArrayList<Double[]>();
		for(int i = 0; i < positions.size(); i++)
		{
			posi.add(new Double[]{positions.get(i)[0],positions.get(i)[1],positions.get(i)[2]});
		}
		for(int i = 0; i < events.size(); i++)
		{
			even.add(new Double[]{events.get(i)[0],events.get(i)[1],events.get(i)[2],events.get(i)[3]});
		}
		temp.setPositions(posi);
		temp.setEvents(even);
		return temp;
	}
	
	public TempCellConstruct(int step, int uni) {
		setInterSize(step);
		iD = uni;
	}
	public void setIdUpdate(int i) {
		iD = i;
		for(int l = 0; l < events.size(); l++)
		{
			events.get(l)[1] = (double) i;
		}
	}
	public void parseDavideLineForDupes(String first) {
		String[] lineValues = first.split(":");
		String[] values = lineValues[1].split(",");
		String[] val = lineValues[lineValues.length-1].split(",");
		//for last instruct get the time
		//last instruct time = life
		ArrayList<Double[]> pos = new ArrayList<Double[]>();

		for(int i = 1; i < lineValues.length; i++)
		{
			String[] valu = lineValues[i].split(",");
			//x,y,time
			Double[] po = new Double[3];
			po[0] = Double.parseDouble(valu[0]);
			po[1] = Double.parseDouble(valu[1]);
			po[2] = (double) Integer.parseInt(valu[2]); 
			pos.add(po);	
			if(po[0]> largestPoint)
			{
				largestPoint = po[0];
			}
			if(po[1]> largestPoint)
			{
				largestPoint = po[1];
			}
			if(po[0]> largestXPoint)
			{
				largestXPoint = po[0];
			}else
			{
				if(po[0]< smallestXPoint)
				{
					smallestXPoint = po[0];
				}
			}
			if(po[1]> largestYPoint)
			{
				largestYPoint = po[1];
			}else
			{
				if(po[1]< smallestYPoint)
				{
					smallestYPoint = po[1];
				}
			}
		}
		pos = this.filterFrontBack(pos);
		birthTime = (int) Math.round(pos.get(0)[2]);
		deathTime = (int) Math.round(pos.get(pos.size()-1)[2]);
		lifeTime = deathTime - birthTime;
		startX = pos.get(0)[0];
		startY = pos.get(0)[1];

		positions = pos;
	}
	
	public Boolean parseDavideLineForNaN(String first) {
		String[] lineValues = first.split(":");
		String[] values = lineValues[1].split(",");
		String[] val = lineValues[lineValues.length-1].split(",");
		//for last instruct get the time
		//last instruct time = life
		ArrayList<Double[]> pos = new ArrayList<Double[]>();

		for(int i = 1; i < lineValues.length; i++)
		{
			String[] valu = lineValues[i].split(",");
			//x,y,time
			Double[] po = new Double[3];
			po[0] = Double.parseDouble(valu[0]);
			po[1] = Double.parseDouble(valu[1]);
			po[2] = (double) Integer.parseInt(valu[2]); 
			pos.add(po);	
			if(po[0]> largestPoint)
			{
				largestPoint = po[0];
			}
			if(po[1]> largestPoint)
			{
				largestPoint = po[1];
			}
			if(po[0]> largestXPoint)
			{
				largestXPoint = po[0];
			}else
			{
				if(po[0]< smallestXPoint)
				{
					smallestXPoint = po[0];
				}
			}
			if(po[1]> largestYPoint)
			{
				largestYPoint = po[1];
			}else
			{
				if(po[1]< smallestYPoint)
				{
					smallestYPoint = po[1];
				}
			}
		}
		boolean full = true;
		pos = this.filterFrontBackNaN(pos);
		if(pos.isEmpty())
		{
			full = false;
		}else
		{
			startX = pos.get(0)[0];
			startY = pos.get(0)[1];
			positions = pos;
			birthTime = (int) Math.round((pos.get(0)[2]));
			/*if(birthTime !=0)
			{
				for(int i = 0; i <pos.size();i++)
				{
					pos.get(i)[2] +=-birthTime; 
				}
				birthTime = 0;
			}*/
			deathTime = (int) Math.round(pos.get(pos.size()-1)[2]+1);
			//last instruct time = life
			lifeTime = deathTime - birthTime;
	
		}
		return full;
	}
	
	public boolean parseDavideLine(String first) {
		String[] lineValues = first.split(":");
		String[] values = lineValues[1].split(",");
		String[] val = lineValues[lineValues.length-1].split(",");
		//for last instruct get the time
		
		ArrayList<Double[]> pos = new ArrayList<Double[]>();
		boolean full = true;
		for(int i = 1; i < lineValues.length; i++)
		{
			String[] valu = lineValues[i].split(",");
			if(valu[0].equals("NaN")||valu[1].equals("NaN"))
			{
				
			}else
			{
				//x,y,time
				Double[] po = new Double[3];
				po[0] = round(Double.parseDouble(valu[0]),1);
				po[1] = round(Double.parseDouble(valu[1]),1);
				po[2] = (double) Integer.parseInt(valu[2]); 
				pos.add(po);	
				if(po[0]> largestPoint)
				{
					largestPoint = po[0];
				}
				if(po[1]> largestPoint)
				{
					largestPoint = po[1];
				}
				if(po[0]> largestXPoint)
				{
					largestXPoint = po[0];
				}else
				{
					if(po[0]< smallestXPoint)
					{
						smallestXPoint = po[0];
					}
				}
				if(po[1]> largestYPoint)
				{
					largestYPoint = po[1];
				}else
				{
					if(po[1]< smallestYPoint)
					{
						smallestYPoint = po[1];
					}
				}
			}
		}
		if(pos.size()>0)
		{
			startX = pos.get(0)[0];
			startY = pos.get(0)[1];
			positions = pos;
			birthTime = (int) Math.round((pos.get(0)[2]));
			/*if(birthTime !=0)
			{
				for(int i = 0; i <pos.size();i++)
				{
					pos.get(i)[2] +=-birthTime; 
				}
				birthTime = 0;
			}*/
			deathTime = (int) Math.round(pos.get(pos.size()-1)[2]+1);
			//last instruct time = life
			lifeTime = deathTime - birthTime;
			
			
		}else
		{
			full = false;
		}
		if(this.checkSequential(pos) == false)
		{
			
		}
		return full;
	}
	
	private boolean checkSequential(ArrayList<Double[]> pos) {
		boolean seq = true;
		for(int i = 0; i < pos.size()-1;i++)
		{
			Double[] po1 = pos.get(i);
			Double[] po2 = pos.get(i+1);
			Double b =po1[2]+1.00;
			if(b.compareTo(po2[2])==1)
			{
				seq = false;
				i = pos.size();
			}
		}
		return seq;
	}
	private ArrayList<Double[]> filterFrontBack(ArrayList<Double[]> pos) {
		Double[] first = pos.get(0);
		Double[] last = pos.get(pos.size()-1);
		int firstCount = 0;
		int lastCount = 0;
		int pointer = 0;
		do
		{
			firstCount++;
			pointer++;
		}while(this.dubEquals(first,pos.get(pointer)));
		pointer = 0;
		do
		{
			lastCount++;
			pointer++;
		}while(this.dubEquals(last,pos.get(pos.size()-1-pointer)));
		int size = pos.size()-(firstCount+lastCount);
		ArrayList<Double[]> pos2 = new ArrayList<Double[]>();
		for(int i = 0; i < size;i++)
		{
			pos2.add(pos.get(firstCount+i));
		}
		//pos2.add(pos.get(pos.size()-lastCount));
		if(pos2.isEmpty())
		{

			System.out.println("empty "+iD);
			//pos2.add(pos.get(0));
		}
			//first[2] = pos2.get(0)[2]-1;
			//last[2] = pos2.get(pos2.size()-1)[2]+1;
			first = pos.get(firstCount-1);
			last = pos.get(pos.size()-lastCount);
			pos2.add(last);
			pos2.add(0, first);
		
		return pos2;
	}
	
	private ArrayList<Double[]> filterFrontBackNaN(ArrayList<Double[]> pos) {
		//Double[] first = pos.get(0);
		//Double[] last = pos.get(pos.size()-1);
		int firstCount = 0;
		int lastCount = 0;
		int pointer = 0;
		do
		{
			firstCount++;
			pointer++;
		}while(Double.isNaN(pos.get(pointer)[0])&& pointer !=pos.size()-1||Double.isNaN(pos.get(pointer)[1])&& pointer !=pos.size()-1);
		pointer = 0;
		do
		{
			lastCount++;
			pointer++;
		}while(Double.isNaN(pos.get(pos.size()-1-pointer)[0])&& pointer !=pos.size()-1||Double.isNaN(pos.get(pos.size()-1-pointer)[1])&& pointer !=pos.size()-1);
		int size = pos.size()-(firstCount+lastCount);
		ArrayList<Double[]> pos2 = new ArrayList<Double[]>();
		for(int i = 0; i < size;i++)
		{
			pos2.add(pos.get(firstCount+i));
		}
		//first[2] = pos2.get(0)[2]-1;
		//last[2] = pos2.get(pos2.size()-1)[2]+1;
		//pos2.add(last);
		//pos2.add(0, first);
		for(int i = 0;i< pos2.size();i++)
		{
			if(Double.isNaN(pos2.get(i)[0]) || Double.isNaN(pos2.get(i)[1]) || Double.isNaN(pos2.get(i)[2]))
			{
				pos2.remove(i);
				i--;
			}
		}
		return pos2;
	}
	private boolean dubEquals(Double[] first, Double[] second) {
		boolean same = false;
		if(first[0].compareTo(second[0]) == 0&& first[1].compareTo(second[1])== 0)
		{
			same = true;
			//System.out.println(""+first[0]+","+first[1]+" "+second[0]+","+second[1]);
		}
		return same;
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	public ArrayList<Double[]> extrapolateFromString(String lineValues, int maxL) 
	{
		String[] values = lineValues.split(",");
		birthTime = Integer.parseInt(values[0])-1;
		deathTime = Integer.parseInt(values[1])-1;
		lifeTime = Integer.parseInt(values[2])-1;
		ArrayList<Double[]> pos = new ArrayList<Double[]>();
		for(int i = birthTime; i < birthTime+lifeTime-1; i++)
		{
			//x,y,time
			Double[] po = new Double[3];
			po[0] = Double.parseDouble(values[3+i]);
			po[1] = Double.parseDouble(values[3+i+maxL]);
			po[2] = (double) i; 
			pos.add(po);	
			if(po[0]> largestPoint)
			{
				largestPoint = po[0];
			}
			if(po[1]> largestPoint)
			{
				largestPoint = po[1];
			}
			if(po[0]> largestXPoint)
			{
				largestXPoint = po[0];
			}else
			{
				if(po[0]< smallestXPoint)
				{
					smallestXPoint = po[0];
				}
			}
			if(po[1]> largestYPoint)
			{
				largestYPoint = po[1];
			}else
			{
				if(po[1]< smallestYPoint)
				{
					smallestYPoint = po[1];
				}
			}
		}
		return pos;
	}

	public ArrayList<Double[]> extrapolateEventsFromPositions() 
	{
		ArrayList<Double[]> evn = new ArrayList<Double[]>();
		if(positions.size() == 1)
		{
			System.out.println("id "+iD);
		}
		for(int i =0; i < positions.size()-1;i++)
		{
			Double[] en = new Double[4];
			//time
			en[0] = positions.get(i)[2];
			//cell
			en[1] = (double) iD;
			//vector
			en[2] = this.getVectorFromPositions(positions.get(i),positions.get(i+1));
			/*if(en[2] == 0.00)
			{
				Double[] pos = positions.get(i);
				Double[] pos2 = positions.get(i+1);
				System.out.println(""+pos[0]+","+pos[1]+" "+pos2[0]+","+pos2[1]+" "+i);
			}*/
			//speed
			//System.out.println(getInterSize());
			//System.out.println("inter "+getInterSize());
			en[3] = this.getEucFromPositions(positions.get(i), positions.get(i+1))/getInterSize();
			/*if(en[3] >10)
			{
				System.out.println("speed too high");
			}*/
			
			evn.add(en);
		}
		setStartVect(evn.get(0)[2]);
		setStartSpeed(evn.get(0)[3]);
		startX = positions.get(0)[0];
		startY = positions.get(0)[1];
		return evn;
	}
	
	
	
	private double getEucFromPositions(Double[] doubles, Double[] doubles2) {
		double y = Math.pow(doubles2[0]-doubles[0], 2);
		double x = Math.pow(doubles2[1]-doubles[1], 2);
		double res = Math.sqrt(y+x);
		if(res > 30)
		{
			//System.out.println("euge");
		}
		return res;
		
	}

	private double getVectorFromPositions(Double[] doubles, Double[] doubles2) {
		double poss = Math.atan2((doubles2[1]-doubles[1]), (doubles2[0]-doubles[0]));
		poss= Math.toDegrees(poss);
		/*if(poss > 0)
		{
			System.out.println("");
		}*/
		poss = (poss+360)%360;
		//System.out.println("poss "+poss);
		return poss;
	}
	
	public void moveAllPositionsBy(double d, double e) {
		for(int i =0;i < positions.size();i++)
		{
			positions.get(i)[0] += d;
			positions.get(i)[1] += e;
		}
		startX = positions.get(0)[0];
		startY = positions.get(0)[1];
	}

	public void compressAllPositionsByWithFlip(double d, double e, int gridSize) {
		for(int i =0;i < positions.size();i++)
		{
			double a = positions.get(i)[0];
			a = a*d;
			positions.get(i)[0] =  a;
			a = positions.get(i)[1];
			a = a*e;
			positions.get(i)[1] =  gridSize-a;
		}
		//System.out.println("pos "+positions.get(0)[0]+", "+positions.get(0)[1]);
		startX = positions.get(0)[0];
		startY = positions.get(0)[1];
	}
	public void compressAllPositionsByWithoutFlip(double d, double e, int gridSize) {
		for(int i =0;i < positions.size();i++)
		{
			double a = positions.get(i)[0];
			a = a*d;
			positions.get(i)[0] =  a;
			a = positions.get(i)[1];
			a = a*e;
			positions.get(i)[1] =  a;
		}
		//System.out.println("pos "+positions.get(0)[0]+", "+positions.get(0)[1]);
		startX = positions.get(0)[0];
		startY = positions.get(0)[1];
	}

	public int getiD() {
		return iD;
	}
	public void setiD(int iD) {
		this.iD = iD;
	}
	public int getBirthTime() {
		return birthTime;
	}
	public void setBirthTime(int birthTime) {
		this.birthTime = birthTime;
	}
	public int getDeathTime() {
		return deathTime;
	}
	public void setDeathTime(int deathTime) {
		this.deathTime = deathTime;
	}
	public int getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}
	public double getStartX() {
		return startX;
	}
	public void setStartX(double startX) {
		this.startX = startX;
	}
	public double getStartY() {
		return startY;
	}
	public void setStartY(double startY) {
		this.startY = startY;
	}
	public ArrayList<Double[]> getPositions() {
		return positions;
	}
	public void setPositions(ArrayList<Double[]> positions2) {
		this.positions = positions2;
	}
	public ArrayList<Double[]> getEvents() {
		return events;
	}
	public void setEvents(ArrayList<Double[]> even) {
		this.events = even;
	}

	public double getStartVect() {
		return startVect;
	}

	public void setStartVect(double startVect) {
		this.startVect = startVect;
	}

	public double getLargestPoint() {
		return largestPoint;
	}

	public void setLargestPoint(double largestPoint) {
		this.largestPoint = largestPoint;
	}

	public double getStartSpeed() {
		return startSpeed;
	}

	public void setStartSpeed(double startSpeed) {
		this.startSpeed = startSpeed;
	}

	public double getLargestXPoint() {
		return largestXPoint;
	}

	public void setLargestXPoint(double largestXPoint) {
		this.largestXPoint = largestXPoint;
	}

	public double getLargestYPoint() {
		return largestYPoint;
	}

	public void setLargestYPoint(double largestYPoint) {
		this.largestYPoint = largestYPoint;
	}

	public double getSmallestXPoint() {
		return smallestXPoint;
	}

	public void setSmallestXPoint(double smallestXPoint) {
		this.smallestXPoint = smallestXPoint;
	}

	public double getSmallestYPoint() {
		return smallestYPoint;
	}

	public void setSmallestYPoint(double smallestYPoint) {
		this.smallestYPoint = smallestYPoint;
	}

	public int getInterSize() {
		return interSize;
	}

	public void setInterSize(int interSize) {
		this.interSize = interSize;
	}

	public int getOldID() {
		return oldID;
	}

	public void setOldID(int oldID) {
		this.oldID = oldID;
	}

	

	

	

	
	
	
	

}
