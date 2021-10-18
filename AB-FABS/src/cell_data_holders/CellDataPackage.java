package cell_data_holders;

import java.util.ArrayList;

public class CellDataPackage {
	
	
	private ArrayList<String> dataNames = new ArrayList<String>();
	private ArrayList<Double> data = new ArrayList<Double>();
	private int lastChosen = 17;
	
	public CellDataPackage()
	{
		initializeDataNames();
		initializeData();
		
	}
	
	public CellDataPackage(CellDataPackage pack)
	{
		dataNames = new ArrayList<String>();
		data = new ArrayList<Double>();
		for(int i = 0; i < pack.getData().size();i++)
		{
			dataNames.add(pack.getDataNames().get(i));
			data.add(pack.getData().get(i));
		}
	}
	public void clean() {

		for(int i =0 ;i < data.size();i++)
		{
			data.set(i,0.00);
		}
	}

	private void initializeData() {
		for(int i = 0; i < dataNames.size();i++)
		{
			data.add(0.00);
		}
		
	}

	private void initializeDataNames() {
	dataNames.add("total distance travelled");
		//collisions
	dataNames.add("total collisions");
		//turns
	dataNames.add("total turns");
		//times chosen bez
	dataNames.add("total bez chosen");	
	
		//collision turn
	dataNames.add("collision sector One");
	dataNames.add("collision sector Two");
	dataNames.add("collision sector Three");
	dataNames.add("collision sector Four");
	dataNames.add("collision sector Five");
	dataNames.add("collision sector Six");
	dataNames.add("collision sector Seven");
	dataNames.add("collision sector Eight");
	
		//bounce turn
	dataNames.add("bounce sect One");
	dataNames.add("bounce sect Two");
	dataNames.add("bounce sect Three");
	dataNames.add("bounce sect Four");
	dataNames.add("bounce sect Five");
	dataNames.add("bounce sect Six");
	dataNames.add("bounce sect Seven");
	dataNames.add("bounce sect Eight");
	
		//chosen turn
	dataNames.add("chosen sect One");
	dataNames.add("chosen sect Two");
	dataNames.add("chosen sect Three");
	dataNames.add("chosen sect Four");
	dataNames.add("chosen sect Five");
	dataNames.add("chosen sect Six");
	dataNames.add("chosen sect Seven");
	dataNames.add("chosen sect Eight");
	dataNames.add("chosen sect Nine");
	dataNames.add("chosen sect Ten");
	dataNames.add("chosen sect Eleven");
	dataNames.add("chosen sect Twelve");
	dataNames.add("chosen sect Thirteen");
	dataNames.add("chosen sect Fourteen");
	dataNames.add("chosen sect Fifteen");
	dataNames.add("chosen sect Sixteen");
	//clustering metrics?
	
	//abs direction
	
	dataNames.add("absolute direction One");
	dataNames.add("absolute direction Two");
	dataNames.add("absolute direction Three");
	dataNames.add("absolute direction Four");
	dataNames.add("absolute direction Five");
	dataNames.add("absolute direction Six");
	dataNames.add("absolute direction Seven");
	dataNames.add("absolute direction Eight");
	
	//chosen dist
	dataNames.add("chosen sect travelled One");
	dataNames.add("chosen sect travelled Two");
	dataNames.add("chosen sect travelled Three");
	dataNames.add("chosen sect travelled Four");
	dataNames.add("chosen sect travelled Five");
	dataNames.add("chosen sect travelled Six");
	dataNames.add("chosen sect travelled Seven");
	dataNames.add("chosen sect travelled Eight");
	dataNames.add("chosen sect travelled Nine");
	dataNames.add("chosen sect travelled Ten");
	dataNames.add("chosen sect travelled Eleven");
	dataNames.add("chosen sect travelled Twelve");
	dataNames.add("chosen sect travelled Thirteen");
	dataNames.add("chosen sect travelled Fourteen");
	dataNames.add("chosen sect travelled Fifteen");
	dataNames.add("chosen sect travelled Sixteen");
	
	dataNames.add("lifetime");
	
	}

	//public double getTotalDistanceTravelled(){return data.get(0);}
	//collisions
	public double getTotalCollisions(){return data.get(1);}
		//turns
	public double getTotalTurns(){return data.get(2);}
		//timesChosenbez
	public double getTotalBezChosen(){return data.get(3);}	
	
		//Collisionturn
	public double getCollisionSectorOne(){return data.get(4);}
	public double getCollisionSectorTwo(){return data.get(5);}
	public double getCollisionSectorThree(){return data.get(6);}
	public double getCollisionSectorFour(){return data.get(7);}
	public double getCollisionSectorFive(){return data.get(8);}
	public double getCollisionSectorSix(){return data.get(9);}
	public double getCollisionSectorSeven(){return data.get(10);}
	public double getCollisionSectorEight(){return data.get(11);}
	
		//Bounceturn
	public double getBounceSectOne(){return data.get(12);}
	public double getBounceSectTwo(){return data.get(13);}
	public double getBounceSectThree(){return data.get(14);}
	public double getBounceSectFour(){return data.get(15);}
	public double getBounceSectFive(){return data.get(16);}
	public double getBounceSectSix(){return data.get(17);}
	public double getBounceSectSeven(){return data.get(18);}
	public double getBounceSectEight(){return data.get(19);}
	
		//Chosenturn
	public double getChosenSectOne(){return data.get(20);}
	public double getChosenSectTwo(){return data.get(21);}
	public double getChosenSectThree(){return data.get(22);}
	public double getChosenSectFour(){return data.get(23);}
	public double getChosenSectFive(){return data.get(24);}
	public double getChosenSectSix(){return data.get(25);}
	public double getChosenSectSeven(){return data.get(26);}
	public double getChosenSectEight(){return data.get(27);}
	public double getChosenSectNine(){return data.get(28);}
	public double getChosenSectTen(){return data.get(29);}
	public double getChosenSectEleven(){return data.get(30);}
	public double getChosenSectTwelve(){return data.get(31);}
	public double getChosenSectThirteen(){return data.get(32);}
	public double getChosenSectFourteen(){return data.get(33);}
	public double getChosenSectFifteen(){return data.get(34);}
	public double getChosenSectSixteen(){return data.get(35);}
	//clusteringmetrics?
	
	//absDirection
	
	public double getAbsoluteDirectionOne(){return data.get(36);}
	public double getAbsoluteDirectionTwo(){return data.get(37);}
	public double getAbsoluteDirectionThree(){return data.get(38);}
	public double getAbsoluteDirectionFour(){return data.get(39);}
	public double getAbsoluteDirectionFive(){return data.get(40);}
	public double getAbsoluteDirectionSix(){return data.get(41);}
	public double getAbsoluteDirectionSeven(){return data.get(42);}
	public double getAbsoluteDirectionEight(){return data.get(43);}
	
	public double getChosenSectTravelledOne(){return data.get(44);}
	public double getChosenSectTravelledTwo(){return data.get(45);}
	public double getChosenSectTravelledThree(){return data.get(46);}
	public double getChosenSectTravelledFour(){return data.get(47);}
	public double getChosenSectTravelledFive(){return data.get(48);}
	public double getChosenSectTravelledSix(){return data.get(49);}
	public double getChosenSectTravelledSeven(){return data.get(50);}
	public double getChosenSectTravelledEight(){return data.get(51);}
	public double getChosenSectTravelledNine(){return data.get(52);}
	public double getChosenSectTravelledTen(){return data.get(53);}
	public double getChosenSectTravelledEleven(){return data.get(54);}
	public double getChosenSectTravelledTwelve(){return data.get(55);}
	public double getChosenSectTravelledThirteen(){return data.get(56);}
	public double getChosenSectTravelledFourteen(){return data.get(57);}
	public double getChosenSectTravelledFifteen(){return data.get(58);}
	public double getChosenSectTravelledSixteen(){return data.get(59);}
	
	public double getLifetime(){return data.get(60);}
	
	public void  setTotalDistanceTravelled(double set){data.set(0, set);}
	//Collisions
	public void  setTotalCollisions(double set){data.set(1, set);}
		//turns
	public void  setTotalTurns(double set){data.set(2, set);}
		//timesChosenbez
	public void  setTotalBezChosen(double set){data.set(3, set);}	
	
		//Collisionturn
	public void  setCollisionSectorOne(double set){data.set(4, set);}
	public void  setCollisionSectorTwo(double set){data.set(5, set);}
	public void  setCollisionSectorThree(double set){data.set(6, set);}
	public void  setCollisionSectorFour(double set){data.set(7, set);}
	public void  setCollisionSectorFive(double set){data.set(8, set);}
	public void  setCollisionSectorSix(double set){data.set(9, set);}
	public void  setCollisionSectorSeven(double set){data.set(10, set);}
	public void  setCollisionSectorEight(double set){data.set(11, set);}
	
		//Bounceturn
	public void  setBounceSectOne(double set){data.set(12, set);}
	public void  setBounceSectTwo(double set){data.set(13, set);}
	public void  setBounceSectThree(double set){data.set(14, set);}
	public void  setBounceSectFour(double set){data.set(15, set);}
	public void  setBounceSectFive(double set){data.set(16, set);}
	public void  setBounceSectSix(double set){data.set(17, set);}
	public void  setBounceSectSeven(double set){data.set(18, set);}
	public void  setBounceSectEight(double set){data.set(19, set);}
	
		//Chosenturn
	public void  setChosenSectOne(double set){data.set(20, set);}
	public void  setChosenSectTwo(double set){data.set(21, set);}
	public void  setChosenSectThree(double set){data.set(22, set);}
	public void  setChosenSectFour(double set){data.set(23, set);}
	public void  setChosenSectFive(double set){data.set(24, set);}
	public void  setChosenSectSix(double set){data.set(25, set);}
	public void  setChosenSectSeven(double set){data.set(26, set);}
	public void  setChosenSectEight(double set){data.set(27, set);}
	public void  setChosenSectNine(double set){data.set(28, set);}
	public void  setChosenSectTen(double set){data.set(29, set);}
	public void  setChosenSectEleven(double set){data.set(30, set);}
	public void  setChosenSectTwelve(double set){data.set(31, set);}
	public void  setChosenSectThirteen(double set){data.set(32, set);}
	public void  setChosenSectFourteen(double set){data.set(33, set);}
	public void  setChosenSectFifteen(double set){data.set(34, set);}
	public void  setChosenSectSixteen(double set){data.set(35, set);}
	//clusteringmetrics?
	
	//absDirection
	
	public void  setAbsoluteDirectionOne(double set){data.set(36, set);}
	public void  setAbsoluteDirectionTwo(double set){data.set(37, set);}
	public void  setAbsoluteDirectionThree(double set){data.set(38, set);}
	public void  setAbsoluteDirectionFour(double set){data.set(39, set);}
	public void  setAbsoluteDirectionFive(double set){data.set(40, set);}
	public void  setAbsoluteDirectionSix(double set){data.set(41, set);}
	public void  setAbsoluteDirectionSeven(double set){data.set(42, set);}
	public void  setAbsoluteDirectionEight(double set){data.set(43, set);}
	
	public void  setChosenSectTravelledOne(double set){data.set(44, set);}
	public void  setChosenSectTravelledTwo(double set){data.set(45, set);}
	public void  setChosenSectTravelledThree(double set){data.set(46, set);}
	public void  setChosenSectTravelledFour(double set){data.set(47, set);}
	public void  setChosenSectTravelledFive(double set){data.set(48, set);}
	public void  setChosenSectTravelledSix(double set){data.set(49, set);}
	public void  setChosenSectTravelledSeven(double set){data.set(50, set);}
	public void  setChosenSectTravelledEight(double set){data.set(51, set);}
	public void  setChosenSectTravelledNine(double set){data.set(52, set);}
	public void  setChosenSectTravelledTen(double set){data.set(53, set);}
	public void  setChosenSectTravelledEleven(double set){data.set(54, set);}
	public void  setChosenSectTravelledTwelve(double set){data.set(55, set);}
	public void  setChosenSectTravelledThirteen(double set){data.set(56, set);}
	public void  setChosenSectTravelledFourteen(double set){data.set(57, set);}
	public void  setChosenSectTravelledFifteen(double set){data.set(58, set);}
	public void  setChosenSectTravelledSixteen(double set){data.set(59, set);}

	public void  setLifetime(double set){data.set(60, set);}
	
	public void  addTotalDistanceTravelled(double val)
	{
		data.set(0,data.get(0)+val);
		if(lastChosen <16)
		{
			data.set(44+lastChosen, data.get(44+lastChosen)+val);
		}
	}
	//Collisions
	public void  addTotalCollisions(double val){data.set(1,data.get(1)+val);}
		//turns
	public void  addTotalTurns(double val){data.set(2,data.get(2)+val);}
		//timesChosenbez
	public void  addTotalBezChosen(double val){data.set(3,data.get(3)+val);}	
	
		//Collisionturn
	public void  addCollisionSectorOne(double val){data.set(4,data.get(4)+val);}
	public void  addCollisionSectorTwo(double val){data.set(5,data.get(5)+val);}
	public void  addCollisionSectorThree(double val){data.set(6,data.get(6)+val);}
	public void  addCollisionSectorFour(double val){data.set(7,data.get(7)+val);}
	public void  addCollisionSectorFive(double val){data.set(8,data.get(8)+val);}
	public void  addCollisionSectorSix(double val){data.set(9,data.get(9)+val);}
	public void  addCollisionSectorSeven(double val){data.set(10,data.get(10)+val);}
	public void  addCollisionSectorEight(double val){data.set(11,data.get(11)+val);}
	
		//Bounceturn
	public void  addBounceSectOne(double val){data.set(12,data.get(12)+val);}
	public void  addBounceSectTwo(double val){data.set(13,data.get(13)+val);}
	public void  addBounceSectThree(double val){data.set(14,data.get(14)+val);}
	public void  addBounceSectFour(double val){data.set(15,data.get(15)+val);}
	public void  addBounceSectFive(double val){data.set(16,data.get(16)+val);}
	public void  addBounceSectSix(double val){data.set(17,data.get(17)+val);}
	public void  addBounceSectSeven(double val){data.set(18,data.get(18)+val);}
	public void  addBounceSectEight(double val){data.set(19,data.get(19)+val);}
	
		//Chosenturn
	public void  addChosenSectOne(double val){data.set(20,data.get(20)+val);lastChosen =0;}
	public void  addChosenSectTwo(double val){data.set(21,data.get(21)+val);lastChosen =1;}
	public void  addChosenSectThree(double val){data.set(22,data.get(22)+val);lastChosen =2;}
	public void  addChosenSectFour(double val){data.set(23,data.get(23)+val);lastChosen =3;}
	public void  addChosenSectFive(double val){data.set(24,data.get(24)+val);lastChosen =4;}
	public void  addChosenSectSix(double val){data.set(25,data.get(25)+val);lastChosen =5;}
	public void  addChosenSectSeven(double val){data.set(26,data.get(26)+val);lastChosen =6;}
	public void  addChosenSectEight(double val){data.set(27,data.get(27)+val);lastChosen =7;}
	public void  addChosenSectNine(double val){data.set(28,data.get(28)+val);lastChosen =8;}
	public void  addChosenSectTen(double val){data.set(29,data.get(29)+val);lastChosen =9;}
	public void  addChosenSectEleven(double val){data.set(30,data.get(30)+val);lastChosen =10;}
	public void  addChosenSectTwelve(double val){data.set(31,data.get(31)+val);lastChosen =11;}
	public void  addChosenSectThirteen(double val){data.set(32,data.get(32)+val);lastChosen =12;}
	public void  addChosenSectFourteen(double val){data.set(33,data.get(33)+val);lastChosen =13;}
	public void  addChosenSectFifteen(double val){data.set(34,data.get(34)+val);lastChosen =14;}
	public void  addChosenSectSixteen(double val){data.set(35,data.get(35)+val);lastChosen =15;}
	//clusteringmetrics?
	
	//absDirection
	
	public void  addAbsoluteDirectionOne(double val){data.set(36,data.get(36)+val);}
	public void  addAbsoluteDirectionTwo(double val){data.set(37,data.get(37)+val);}
	public void  addAbsoluteDirectionThree(double val){data.set(38,data.get(38)+val);}
	public void  addAbsoluteDirectionFour(double val){data.set(39,data.get(39)+val);}
	public void  addAbsoluteDirectionFive(double val){data.set(40,data.get(40)+val);}
	public void  addAbsoluteDirectionSix(double val){data.set(41,data.get(41)+val);}
	public void  addAbsoluteDirectionSeven(double val){data.set(42,data.get(42)+val);}
	public void  addAbsoluteDirectionEight(double val){data.set(43,data.get(43)+val);}
	public void  addLifeTime(double val){data.set(60,data.get(60)+val);}
	
	public ArrayList<String> getDataNames() {
		return dataNames;
	}
	public void setDataNames(ArrayList<String> dataNames) {
		this.dataNames = dataNames;
	}
	public ArrayList<Double> getData() {
		return data;
	}
	public void setData(ArrayList<Double> data) {
		this.data = data;
	}

	
	
}
