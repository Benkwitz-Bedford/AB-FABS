package heatmaps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import singleton_holders.SingletonHolder;
import environment_data_holders.PositionGrid;

public class HeatMap {

	private double prefWidth = 900;//600
	private double prefHeight = 900;//600
	ArrayList<Double[]> rectangles = new ArrayList<Double[]>();
	
	private double topValue = 255.00 *3.00;
	
	private int[][] heats = new int[100][100];
	
	private BufferedImage buffer = new BufferedImage((int)prefWidth, (int)prefHeight,BufferedImage.TYPE_INT_RGB);
	
	
	public HeatMap() {
		//setPrefWidth(i);
		//setPrefHeight(j);
	}
	public HeatMap(int i, int j) {
		setPrefWidth(i);
		setPrefHeight(j);
	}

	public void drawMap(int[][] heat, int topper, double gamma, int colSelection, boolean invert)
	{
		int[][] heatGrid = new int[heat.length][heat[0].length];
		for(int x = 0; x < heatGrid.length; x++)
		{
			for(int y = 0; y < heatGrid[x].length;y++)
			{
				int xy = heat[x][y];
				heatGrid[x][y] = xy;
			}
		}
		int highest = 0;
		if(topper == 0)
		{
			highest = this.findHighest(heatGrid);
		}else
		{
			highest = topper;
		}
		
		double ratio = (topValue)/highest;
		//System.out.println("..."+positions.getHeat());
		//System.out.println("..."+positions.getHeat()[0][0]);
		//System.out.println(ratio+" "+highest);
		heats = heat;
		this.drawMap(ratio,heatGrid, gamma, colSelection, invert);
	    //this.setPreferredSize(new Dimension((int)prefWidth,(int)prefHeight));
	    
	}
	
	public void drawPureMap(int[][] heat, int topper, double gamma, int colSelection, boolean invert)
	{
		int[][] heatGrid = new int[heat.length][heat[0].length];
		for(int x = 0; x < heatGrid.length; x++)
		{
			for(int y = 0; y < heatGrid[x].length;y++)
			{
				int xy = heat[x][y];
				heatGrid[x][y] = xy;
			}
		}
		int highest = 0;
		if(topper == 0)
		{
			highest = this.findHighest(heatGrid);
		}else
		{
			highest = topper;
		}
		
		double ratio = (topValue)/highest;
		//System.out.println("..."+positions.getHeat());
		//System.out.println("..."+positions.getHeat()[0][0]);
		//System.out.println(ratio+" "+highest);
		heats = heat;
		this.drawPureMap(ratio,heatGrid, gamma, colSelection, invert);
	    //this.setPreferredSize(new Dimension((int)prefWidth,(int)prefHeight));
	    
	}
	
	public void drawPureMap(int[][][] heat, int i, int topper, double gamma, int colSelection,
			boolean invert) {
		//System.out.println("targ pass "+i);
		int[][] heatGrid = new int[heat.length][heat[0].length];
		for(int x = 0; x < heat.length; x++)
		{
			for(int y = 0; y < heat[x].length;y++)
			{
				int xy = heat[x][y][i];
				heatGrid[x][y] = xy;
			}
		}
		int highest = 0;
		if(topper == 0)
		{
			highest = this.findHighest(heatGrid);
		}else
		{
			highest = topper;
		}
		
		double ratio = (topValue)/highest;
		//System.out.println("..."+positions.getHeat());
		//System.out.println("..."+positions.getHeat()[0][0]);
		//System.out.println(ratio+" "+highest);
		heats = heatGrid;
		this.drawPureMap(ratio,heatGrid, gamma, colSelection, invert);
	    //this.setPreferredSize(new Dimension((int)prefWidth,(int)prefHeight));
		
	}
	
	public void drawPureSubMap(int[][] heat, int topper, double gamma, int colSelection, boolean invert, int x1, int y1, int x2, int y2)
	{
		int[][] heatGrid = new int[heat.length][heat[0].length];
		for(int x = 0; x < heatGrid.length; x++)
		{
			for(int y = 0; y < heatGrid[x].length;y++)
			{
				int xy = heat[x][y];
				heatGrid[x][y] = xy;
			}
		}
		int highest = 0;
		if(topper == 0)
		{
			highest = this.findHighest(heatGrid);
		}else
		{
			highest = topper;
		}
		
		double ratio = (topValue)/highest;
		//System.out.println("..."+positions.getHeat());
		//System.out.println("..."+positions.getHeat()[0][0]);
		//System.out.println(ratio+" "+highest);
		heats = heat;
		this.drawPureSubMap(ratio,heatGrid, gamma, colSelection, invert,x1,y1,x2,y2);
		//this.drawPureMap(ratio,heatGrid, gamma, colSelection, invert);
	    //this.setPreferredSize(new Dimension((int)prefWidth,(int)prefHeight));
	    
	}
	
	

	private void drawMap(double ratio, int[][] heatGrid, double gamma, int colSelection, boolean invert)
	{
		rectangles = new ArrayList<Double[]>(); 
		double xRatio = prefWidth/heatGrid.length;
		double yRatio = prefHeight/heatGrid.length;
		//System.out.println("ratios: "+xRatio+" "+yRatio+" "+heatGrid.length);
		//xRatio = 1;
		//yRatio = 1;
		for(int x = 0; x < heatGrid.length; x++)
		{
			for(int y = 0; y < heatGrid[x].length; y++)
			{
				rectangles.add(new Double[]{ (heatGrid[x][y]*ratio),(x*xRatio), (y*yRatio), xRatio, yRatio});
				
			}
		}
		buffer = this.genBuffer((int)prefWidth,(int)prefHeight,rectangles, gamma, colSelection, invert);
	}
	private void drawPureMap(double ratio, int[][] heatGrid, double gamma, int colSelection, boolean invert)
	{
		rectangles = new ArrayList<Double[]>(); 
		double xRatio = prefWidth/heatGrid.length;
		double yRatio = prefHeight/heatGrid.length;
		//System.out.println("ratios: "+xRatio+" "+yRatio+" "+heatGrid.length);
		//xRatio = 1;
		//yRatio = 1;
		for(int x = 0; x < heatGrid.length; x++)
		{
			for(int y = 0; y < heatGrid[x].length; y++)
			{
				rectangles.add(new Double[]{ (heatGrid[x][y]*ratio),(x*xRatio), (y*yRatio), xRatio, yRatio});
				
			}
		}
		buffer = this.genPureBuffer((int)prefWidth,(int)prefHeight,rectangles, gamma, colSelection, invert);
	}
	
	private void drawPureSubMap(double ratio, int[][] heatGrid, double gamma, int colSelection, boolean invert, int x1, int y1, int x2, int y2)
	{
		rectangles = new ArrayList<Double[]>(); 
		double xRatio = prefWidth/heatGrid.length;
		double yRatio = prefHeight/heatGrid.length;
		//System.out.println("ratios: "+xRatio+" "+yRatio+" "+heatGrid.length);
		//xRatio = 1;
		//yRatio = 1;
		//needs sim size / grid size to give x to x in grid conversion, that times ratio to give location in real terms
		double size = SingletonHolder.getSize();
		double xLen = heatGrid.length;
		double yLen = heatGrid[0].length;
		double xGridRatio = size/xLen;
		//System.out.println(" size "+SingletonHolder.getSize()+" div by "+heatGrid.length+" eq "+xGridRatio);
		double yGridRatio = size/yLen;
		for(int x = (int) (x1/xGridRatio); x < heatGrid.length; x++)
		{
			//System.out.println("x "+x);
			for(int y = (int) (y1/yGridRatio); y < heatGrid[x].length; y++)
			{
				//System.out.println("y "+y+" "+heatGrid[x].length);
				rectangles.add(new Double[]{ (heatGrid[x][y]*ratio),(x*xRatio), (y*yRatio), xRatio, yRatio});
				if(y>= y2/yGridRatio)
				{
					
					y = heatGrid[0].length;
				}
			}
			if(x>=x2/xGridRatio)
			{
				x = heatGrid.length;
			}
		}
		buffer = this.genPureSubBuffer((int)prefWidth,(int)prefHeight,rectangles, gamma, colSelection, invert,x1,y1);
	}
	private BufferedImage genBuffer(int width, int height, ArrayList<Double[]> recs, double gamma, int colSelection, boolean invert) 
	{
		gamma = 1/gamma;
		BufferedImage image = new BufferedImage((int)width+100, (int)height+100, BufferedImage.TYPE_INT_RGB);
				//this.createImage((int)width+100, (int)height+100);
		image.createGraphics();
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setColor(Color.white);
		Rectangle2D.Double rec = new Rectangle2D.Double(0, 0, image.getWidth(), image.getHeight());
		g2.fill(rec);
		for(int i =0; i < recs.size(); i++)
		{
			Double[] rect = recs.get(i);
			double d = rect[0];
			if(d > topValue)
			{
				d = topValue;
			}
			int[] rgb =  null;
			if(invert)
			{
				rgb =this.getRGBValueInvert( (int) d, colSelection);	
			}else
			{
				rgb =this.getRGBValue( (int) d, colSelection);	
			}
			if(gamma !=1)
			{
				for(int l = 0; l < rgb.length; l++)
				{
					rgb[l] =  (int) (255.00 * Math.pow(((Double.parseDouble(""+rgb[l]) / 255.00)),gamma));
				}
			}
			/*if(rgb[0] < 10 && rgb[1] < 10 && rgb[2]< 10)
			{
				g2.setColor(Color.WHITE);
			}else
			{*/
				g2.setColor(new Color(rgb[0],rgb[1],rgb[2]));
			//}
			rec = new Rectangle2D.Double(rect[1], rect[2], rect[3], rect[4]);
			g2.fill(rec);
			  //System.out.println("rect: "+rect[1]+" "+rect[2]+" "+rect[3]+" "+rect[4]);
		}
		int pox = 0;
		double poy = 0;
		double poyInc = 0.25;
		int total = (int) topValue;
		int inc = 5;
		double gridTotal = recs.size()*recs.get(recs.size()-1)[3];
		double ratio = gridTotal/total;
		int posX = 0;
		int posY = (int) (recs.get(recs.size()-1)[2]+recs.get(recs.size()-1)[4]+(poy/2+poyInc*(total/inc)));
		//System.out.println(posY);
		g2.drawString("Movement density ===>", posX,posY+4);
		posX = posX+160;
		for(int i = 0; i < total; i= i+inc)
		{
			int[] rgb =  null;
			if(invert)
			{
				rgb =this.getRGBValueInvert( (int) i, colSelection);	
			}else
			{
				rgb =this.getRGBValue( (int) i, colSelection);	
			}
			if(gamma !=1)
			{
				for(int l = 0; l < rgb.length; l++)
				{
					rgb[l] =  (int) (255.00 * Math.pow(((Double.parseDouble(""+rgb[l]) / 255.00)),gamma));
				}
			}
			/*if(rgb[0] < 10 && rgb[1] < 10 && rgb[2]< 10)
			{
				g2.setColor(Color.WHITE);
			}else
			{*/
				g2.setColor(new Color(rgb[0],rgb[1],rgb[2]));
			//}
			g2.drawLine(posX+pox, (int) (posY-poy), posX+pox, (int) (posY+poy));
			pox++;
			poy = poy+poyInc;
			//System.out.println("r "+rgb[0]+" g "+rgb[1]+" b "+rgb[2]);
		}
		  
		return image;
	}
	
	private BufferedImage genPureBuffer(int width, int height, ArrayList<Double[]> recs, double gamma, int colSelection, boolean invert) 
	{
		gamma = 1/gamma;
		BufferedImage image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
				//this.createImage((int)width+100, (int)height+100);
		image.createGraphics();
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setColor(Color.white);
		Rectangle2D.Double rec = new Rectangle2D.Double(0, 0, image.getWidth(), image.getHeight());
		g2.fill(rec);
		//System.out.println("dealing with  "+recs.size()+" recs");
		for(int i =0; i < recs.size(); i++)
		{
			Double[] rect = recs.get(i);
			double d = rect[0];
			if(d > topValue)
			{
				d = topValue;
			}
			int[] rgb =  null;
			if(invert)
			{
				rgb = getRGBValueInvert( (int) d, colSelection);	
			}else
			{
				rgb = this.getRGBValue( (int) d, colSelection);	
			}
			if(gamma !=1)
			{
				for(int l = 0; l < rgb.length; l++)
				{
					rgb[l] =  (int) (255.00 * Math.pow(((Double.parseDouble(""+rgb[l]) / 255.00)),gamma));
				}
			}
			/*if(rgb[0] < 10 && rgb[1] < 10 && rgb[2]< 10)
			{
				g2.setColor(Color.WHITE);
			}else
			{*/
				g2.setColor(new Color(rgb[0],rgb[1],rgb[2]));
			//}
			rec = new Rectangle2D.Double(rect[1], rect[2], rect[3], rect[4]);
			g2.fill(rec);
			  //System.out.println("rect: "+rect[1]+" "+rect[2]+" "+rect[3]+" "+rect[4]);
		}
		int pox = 0;
		double poy = 0;
		double poyInc = 0.25;
		int total = (int) topValue;
		int inc = 5;
		double gridTotal = recs.size()*recs.get(recs.size()-1)[3];
		double ratio = gridTotal/total;
		int posX = 0;
		int posY = (int) (recs.get(recs.size()-1)[2]+recs.get(recs.size()-1)[4]+(poy/2+poyInc*(total/inc)));
		//System.out.println(posY);
		
		  
		return image;
	}
	
	private BufferedImage genPureSubBuffer(int width, int height, ArrayList<Double[]> recs, double gamma, int colSelection, boolean invert, int x1, int y1) 
	{
		gamma = 1/gamma;
		/*double xOffSet = 1.00;
		double yOffSet = 1.00;
		if(width > 3000)
		{
			xOffSet = 3000.00/width;
			width = 3000;
		}
		if(height >3000)
		{
			yOffSet = 3000.00/height;
			height = 3000;
		}*/
		BufferedImage image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
				//this.createImage((int)width+100, (int)height+100);
		image.createGraphics();
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setColor(Color.white);
		Rectangle2D.Double rec = new Rectangle2D.Double(0, 0, image.getWidth(), image.getHeight());
		g2.fill(rec);
		//System.out.println("dealing with  "+recs.size()+" recs");
		for(int i =0; i < recs.size(); i++)
		{
			Double[] rect = recs.get(i);
			double d = rect[0];
			if(d > topValue)
			{
				d = topValue;
			}
			int[] rgb =  null;
			if(invert)
			{
				rgb = getRGBValueInvert( (int) d, colSelection);	
			}else
			{
				rgb = this.getRGBValue( (int) d, colSelection);	
			}
			if(gamma !=1)
			{
				for(int l = 0; l < rgb.length; l++)
				{
					rgb[l] =  (int) (255.00 * Math.pow(((Double.parseDouble(""+rgb[l]) / 255.00)),gamma));
				}
			}
			/*if(rgb[0] < 10 && rgb[1] < 10 && rgb[2]< 10)
			{
				g2.setColor(Color.WHITE);
			}else
			{*/
				g2.setColor(new Color(rgb[0],rgb[1],rgb[2]));
			//}
			rec = new Rectangle2D.Double(rect[1], rect[2], rect[3], rect[4]);
			g2.fill(rec);
			  //System.out.println("rect: "+rect[1]+" "+rect[2]+" "+rect[3]+" "+rect[4]);
		}
		
		
		  
		return image;
	}

	private int findHighest(int[][] heatGrid) {
		int highest = 0;
		for(int x = 0; x < heatGrid.length; x++)
		{
			for(int y = 0; y < heatGrid[x].length; y++)
			{
				if(heatGrid[x][y]>highest)
				{
					highest = heatGrid[x][y];
				}
			}
		}
		return highest;
	}

	public int[] getRGBValue(int i, int l) {
		int r = 0;
		int g = 0;
		int b = 0;
		
		if(i< 255)
		{
		  r = i;
		}else
		{
			if(i < 255*2)
			{
			  r = 255;
			  g = i - r;
			}else
			{
			  r = 255;
			  g = 255;
			  b = i-r-g;
			}
		 }
		/*rgb = red through yellow
		 *rbg = red through yellow
		 *grb = green through yellow
		 *gbr = blue through pink
		 *brg = green through blue
		 *bgr = blue through teal 
		 */
		int[] col = new int[3];
		if(l == 0)
		{
			col[0] = r;
			col[1] = g;
			col[2] = b;
		}else
		{
			if(l == 1)
			{
				col[0] = r;
				col[1] = b;
				col[2] = g;
			}else
			{
				if(l == 2)
				{
					col[0] = g;
					col[1] = r;
					col[2] = b;
				}else
				{
					if(l == 3)
					{
						col[0] = g;
						col[1] = b;
						col[2] = r;
					}else
					{
						if(l == 4)
						{
							col[0] = b;
							col[1] = r;
							col[2] = g;
						}else
						{
							if(l == 5)
							{
								col[0] = b;
								col[1] = g;
								col[2] = r;
							}
						}
					}
				}
			}
		}
		return col;
	}
	
	public int[] getRGBValueInvert(int i, int l) {
		int r = 0;
		int g = 0;
		int b = 0;
		//System.out.println("eh "+i);
		i = (255*3)-i;

		if(i <0)
		{
			i = 0;
		}
		if(i< 255)
		{
		  r = i;
			//System.out.println("a");
		}else
		{
			if(i < 255*2)
			{
			  r = 255;
			  g = i - r;
				//System.out.println("b");
			}else
			{
			  r = 255;
			  g = 255;
			  b = i-r-g;

			//System.out.println("c");
			}
		 }
		/*rgb = green through yellow
		 *rbg = red through yellow
		 *grb = green through yellow
		 *gbr = blue through pink
		 *brg = green through blue
		 *bgr = blue through teal 
		 */
		if(r <0)
		{
			System.out.println("overcolour "+r+" "+g+" "+b+" "+i);
			System.exit(0);
		}
		if(g <0)
		{
			System.out.println("overcolour "+r+" "+g+" "+b+" "+i);
			System.exit(0);
		}
		if(b <0)
		{
			System.out.println("overcolour "+r+" "+g+" "+b+" "+i);
			System.exit(0);
		}
		int[] col = new int[3];
		if(l == 0)
		{
			col[0] = r;
			col[1] = g;
			col[2] = b;
		}else
		{
			if(l == 1)
			{
				col[0] = r;
				col[1] = b;
				col[2] = g;
			}else
			{
				if(l == 2)
				{
					col[0] = g;
					col[1] = r;
					col[2] = b;
				}else
				{
					if(l == 3)
					{
						col[0] = g;
						col[1] = b;
						col[2] = r;
					}else
					{
						if(l == 4)
						{
							col[0] = b;
							col[1] = r;
							col[2] = g;
						}else
						{
							if(l == 5)
							{
								col[0] = b;
								col[1] = g;
								col[2] = r;
							}
						}
					}
				}
			}
		}
		return col;
	}

	public int getPrefHeight() {
		return (int) prefHeight;
	}

	public void setPrefHeight(int prefHeight) {
		this.prefHeight = prefHeight;
	}

	public int getPrefWidth() {
		return (int) prefWidth;
	}

	public void setPrefWidth(int prefWidth) {
		this.prefWidth = prefWidth;
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferedImage buffer) {
		this.buffer = buffer;
	}

	public int[][] getHeats() {
		return heats;
	}

	public void setHeats(int[][] positions) {
		// TODO Auto-generated method stub
		heats = positions;
	}
	public void drawDirectionMap(int[][][] heat, int topper, double gamma, int colSelection,
			boolean invert) {
		int[][][] heatGrid = new int[heat.length][heat[0].length][2];
		for(int x = 0; x < heatGrid.length; x++)
		{
			for(int y = 0; y < heatGrid[x].length;y++)
			{
				//create the dominant side and its %
				int[] direct = heat[x][y];
				double largest = 0.00;
				int lPointer = 9;
				double total = 0.00;
				for(int i = 0; i < direct.length;i++)
				{
					if(direct[i]>largest)
					{
						largest = direct[i];
						lPointer = i;
					}
					total+=direct[i];
				}
				double percent = 0.00;
				if(lPointer !=9)
				{
					if(topper != 0)
					{
						total = topper;
					}
					percent = (largest/total)*100.00;
					percent = percent*7.65;
				}
				int[] xy = new int[] {lPointer,(int) Math.round(percent)};
				heatGrid[x][y] = xy;
			}
		}
		this.drawTriangleMap(heatGrid, gamma, colSelection, invert);
	    //this.setPreferredSize(new Dimension((int)prefWidth,(int)prefHeight));
		
	}
	
	public void drawDirectionMapDom(int[][][] heat, int topper, double gamma, int colSelection,
			boolean invert) {
		int[][][] heatGrid = new int[heat.length][heat[0].length][2];
		for(int x = 0; x < heatGrid.length; x++)
		{
			for(int y = 0; y < heatGrid[x].length;y++)
			{
				//create the dominant side and its %
				int[] direct = heat[x][y];
				double largest2 = 0.00;
				double largest = 0.00;
				int lPointer = 9;
				double total = 0.00;
				for(int i = 0; i < direct.length;i++)
				{
					if(direct[i]>largest)
					{
						largest = direct[i];
						lPointer = i;
					}else
					{
						if(direct[i]>largest2)
						{

							largest2 = direct[i];
						}
					}
					total+=direct[i];
				}
				double percent = 0.00;
				if(lPointer !=9)
				{
					if(topper != 0)
					{
						total = topper;
					}
					percent = ((largest-largest2)/total)*100.00;
					percent = percent*7.65;
				}
				int[] xy = new int[] {lPointer,(int) Math.round(percent)};
				heatGrid[x][y] = xy;
			}
		}
		this.drawTriangleMap(heatGrid, gamma, colSelection, invert);
	    //this.setPreferredSize(new Dimension((int)prefWidth,(int)prefHeight));
		
	}

	private void drawTriangleMap(int[][][] heatGrid, double gamma, int colSelection, boolean invert)
	{
		rectangles = new ArrayList<Double[]>(); 
		double xRatio = prefWidth/heatGrid.length;
		double yRatio = prefHeight/heatGrid.length;

		/*if(xRatio <6|| yRatio < 6)
		{
			xRatio = 6;
			yRatio = 6;
		}
		System.out.println("ratios: "+xRatio+" "+yRatio+" "+heatGrid.length);*/
		//xRatio = 1;
		//yRatio = 1;
		for(int x = 0; x < heatGrid.length; x++)
		{
			for(int y = 0; y < heatGrid[x].length; y++)
			{
				rectangles.add(new Double[]{ (double) heatGrid[x][y][0],(double) heatGrid[x][y][1],(x*xRatio), (y*yRatio), xRatio, yRatio});
				
			}
		}
		buffer = this.genPureTriangleBuffer((int)prefWidth,(int)prefHeight,rectangles, gamma, colSelection, invert);
	}
	
	private BufferedImage genPureTriangleBuffer(int width, int height, ArrayList<Double[]> recs, double gamma, int colSelection, boolean invert) 
	{
		gamma = 1/gamma;
		BufferedImage image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
		//BufferedImage image = new BufferedImage((int)(recs.size()*recs.get(0)[4]),(int)(recs.size()*recs.get(0)[5]), BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		Color gb = Color.black;
		if(invert)
		{
			gb = Color.white;
		}
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		AffineTransform oldXForm = g2.getTransform();
		g2.setColor(gb);
		Rectangle2D.Double rec = new Rectangle2D.Double(0, 0, image.getWidth(), image.getHeight());
		g2.fill(rec);

		for(int i =0; i < recs.size(); i++)
		{
			Double[] rect = recs.get(i);
			double x = rect[2];
			double y = rect[3];
			double wid = rect[4];
			double hei = rect[5];
			if(rect[0] == 9)
			{
				g2.setColor(gb);
				rec = new Rectangle2D.Double(x, y, wid, hei);
				g2.fill(rec);
			}else
			{
				double d = rect[1];
				if(d > topValue)
				{
					d = topValue;
				}
				int[] rgb =  null;
				if(invert)
				{
					rgb = getRGBValueInvert( (int) Math.round(d), colSelection);	
				}else
				{
					rgb = this.getRGBValue( (int)Math.round(d), colSelection);	
				}
				if(gamma !=1)
				{
					for(int l = 0; l < rgb.length; l++)
					{
						rgb[l] =  (int) (255.00 * Math.pow(((Double.parseDouble(""+rgb[l]) / 255.00)),gamma));
					}
				}
				//System.out.println(d);
				g2.setColor(new Color(rgb[0],rgb[1],rgb[2]));
				rec = new Rectangle2D.Double(x, y, wid, hei);
				Polygon poly = new Polygon(new int[] {(int) Math.round(x+(wid/2)),(int) Math.round(x+wid),(int) Math.round(x+(wid/2)),(int) Math.round(x)},new int[] {(int) Math.round(y),(int) Math.round(y+hei),(int)Math.round(y+(hei/2)),(int) Math.round(y+hei)}, 4);

				//g2.rotate((360/8)*rect[0],(int) Math.round(x+(wid/2)),(int) Math.round(y+(hei/2)));
				g2.rotate(Math.toRadians((45*rect[0])),(int) Math.round(x+(wid/2)),(int) Math.round(y+(hei/2)));
				
				//g2.rotate((360/8)*rect[0],(int) Math.round(x),(int) Math.round(y));
				g2.fill(poly);
				g2.setTransform(oldXForm);
			}
		}
		
		  
		return image;
	}
	public void setDimensions(Dimension heatSize) {
		this.setPrefHeight((int)Math.round(heatSize.getHeight()));
		this.setPrefWidth((int)Math.round(heatSize.getWidth()));
		
	}
	public Dimension getDimensions() {
		Dimension dim = new Dimension((int)Math.round(prefWidth),(int)Math.round(prefHeight));
		return dim;
	}
	
	
	
}
