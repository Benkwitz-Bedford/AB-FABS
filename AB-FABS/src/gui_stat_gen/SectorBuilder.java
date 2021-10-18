package gui_stat_gen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cycle_components.StaticCalculations;

public class SectorBuilder {

	private BufferedImage sector;
	
	public SectorBuilder(double width, double height, double ovalLength, double sectorLength, double[] sects, boolean kant )
	{
		int empty = 0;
		for(int i = 0; i < sects.length;i++)
		{
			if(sects[i] == 0)
			{
				empty++;
			}
		}
		if(empty == 8)
		{
			int count = 0;
			double[] sec = new double[8];
			for(int i = 0; i < sects.length;i++)
			{
				if(sects[i]!= 0)
				{
					sec[count] = sects[i];
					count++;
				}
			}
			kant = true;
			sects = sec;
		}
		if(kant)
		{
			setSector(this.genCantedBuffer(width, height, ovalLength, sectorLength, sects));
		}else
		{
			setSector(this.genBuffer(width, height, ovalLength, sectorLength, sects));
		}
	}
	
	private BufferedImage genBuffer(double width, double height, double ovalLength, double sectorLength, double[] sects) 
	{
		BufferedImage image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setColor(Color.white);
		Rectangle2D.Double rec = new Rectangle2D.Double(0, 0, image.getWidth(), image.getHeight());
		g2.fill(rec);
		
		double largestSec = 0; 
		for(int i =0; i < sects.length;i++)
		{
			if(largestSec< sects[i])
			{
				largestSec = sects[i];
			}
		}
		double[][] lineAngles = new double[sects.length*2][2];
		for(int i = 0; i < sects.length;i++)
		{
			lineAngles[i*2][0] = StaticCalculations.counterClockwiseClean((360.00/(sects.length))*i-90.00);
			lineAngles[i*2][1] = (sectorLength/largestSec)*sects[i];
			lineAngles[i*2+1][0] = StaticCalculations.counterClockwiseClean((360.00/(sects.length))*(i+1)-90.00);
			lineAngles[i*2+1][1] = (sectorLength/largestSec)*sects[i];
		}
		double[][] lines = new double[lineAngles.length][2];
		for(int i = 0; i < lineAngles.length;i++)
		{
			double x = width/2;
			double y = height/2;
			double vect = Math.toRadians(lineAngles[i][0]);
			x = x + Math.cos(vect)*lineAngles[i][1];
			y = y + Math.sin(vect)*lineAngles[i][1];
			
			lines[i][0] = x;
			lines[i][1] = y;
		}
		g2.setColor(Color.BLACK);
		g2.drawOval((int)(width/2-ovalLength/2/2), (int)(height/2-ovalLength/2), (int)(ovalLength/2), (int)(ovalLength));
		for(int i = 0; i < lines.length;i++)
		{
			g2.drawLine((int)(width/2), (int)(height/2), (int)(lines[i][0]), (int)(lines[i][1]));
		}
		for(int i = 0; i < lines.length-1;i++)
		{
			g2.drawLine((int)(lines[i][0]), (int)(lines[i][1]), (int)(lines[i+1][0]), (int)(lines[i+1][1]));
		}
		return image;
	}
	
	private BufferedImage genCantedBuffer(double width, double height, double ovalLength, double sectorLength, double[] sects) 
	{
		BufferedImage image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setColor(Color.white);
		Rectangle2D.Double rec = new Rectangle2D.Double(0, 0, image.getWidth(), image.getHeight());
		g2.fill(rec);
		
		double largestSec = 0; 
		for(int i =0; i < sects.length;i++)
		{
			if(largestSec< sects[i])
			{
				largestSec = sects[i];
			}
		}
		double[][] lineAngles = new double[sects.length*2][2];
		for(int i = 0; i < sects.length;i++)
		{
			lineAngles[i*2][0] = StaticCalculations.counterClockwiseClean((360.00/(sects.length))*i-112.50);
			lineAngles[i*2][1] = (sectorLength/largestSec)*sects[i];
			lineAngles[i*2+1][0] = StaticCalculations.counterClockwiseClean((360.00/(sects.length))*(i+1)-112.50);
			lineAngles[i*2+1][1] = (sectorLength/largestSec)*sects[i];
		}
		double[][] lines = new double[lineAngles.length][2];
		for(int i = 0; i < lineAngles.length;i++)
		{
			double x = width/2;
			double y = height/2;
			double vect = Math.toRadians(lineAngles[i][0]);
			x = x + Math.cos(vect)*lineAngles[i][1];
			y = y + Math.sin(vect)*lineAngles[i][1];
			
			lines[i][0] = x;
			lines[i][1] = y;
		}
		g2.setColor(Color.BLACK);
		g2.drawOval((int)(width/2-ovalLength/2/2), (int)(height/2-ovalLength/2), (int)(ovalLength/2), (int)(ovalLength));
		for(int i = 0; i < lines.length;i++)
		{
			g2.drawLine((int)(width/2), (int)(height/2), (int)(lines[i][0]), (int)(lines[i][1]));
		}
		for(int i = 0; i < lines.length-1;i++)
		{
			g2.drawLine((int)(lines[i][0]), (int)(lines[i][1]), (int)(lines[i+1][0]), (int)(lines[i+1][1]));
		}
		return image;
	}

	public BufferedImage getSector() {
		return sector;
	}

	public void setSector(BufferedImage sector) {
		this.sector = sector;
	}

	
}
