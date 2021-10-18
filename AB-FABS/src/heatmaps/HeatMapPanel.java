package heatmaps;

import java.awt.Color;
import java.awt.Dimension;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import singleton_holders.SingletonHolder;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;

@SuppressWarnings("serial")
public class HeatMapPanel extends JPanel{
	
	private double prefWidth = 800;//600
	private double prefHeight = 800;//600
	ArrayList<Double[]> rectangles = new ArrayList<Double[]>();
	
	private PositionGrid position = new PositionGrid(100, 100);
	
	private Image buffer = this.createImage((int)prefWidth, (int)prefHeight);
	
	private HeatMap heat = null;
	
	private int target = 0;
	
	public HeatMapPanel(int i, int j) {
		setPrefWidth(i);
		setPrefHeight(j);
		setHeat(new HeatMap(i,j));
		buffer = this.createImage((int)i, (int)j);
	}

	public void drawMap(PositionGrid positions, int topper, double gamma)
	{
		getHeat().drawMap(positions.getHeat(), topper, gamma, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
		buffer = getHeat().getBuffer();
		this.setVisible(true);
	    //this.setPreferredSize(new Dimension((int)prefWidth,(int)prefHeight));
	    this.repaint();
	}
	
	public void drawPureMap(Posi posi2, int topper, double gamma)
	{
		SingletonHolder.setHeat(topper);
		SingletonHolder.setGamma(gamma);
		if(target == 0)
		{
			getHeat().drawPureMap(posi2.getHeat(), topper, gamma, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			
		}else
		{
			if(target == 1)
			{
				getHeat().drawDirectionMap(posi2.getDirectionHeat(), topper, gamma, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			}else
			{
			if(target == 2)
			{
				getHeat().drawDirectionMap(posi2.getDirectionConstantHeat(), topper, gamma, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				
			}else
			{
			if(target == 3)
			{
				getHeat().drawDirectionMap(posi2.getRelativeHeat(), topper, gamma, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				
			}else
			{
				getHeat().drawPureMap(posi2.getAngleHeat(target-4), topper, gamma, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				
			}
			}
			}
		}
		
		buffer = getHeat().getBuffer();
		this.setVisible(true);
	    //this.setPreferredSize(new Dimension((int)prefWidth,(int)prefHeight));
	    this.repaint();
	}
	
	public void drawPureSubMap(Posi posi, int topper, double gamma, int x1, int y1, int x2, int y2) {
		SingletonHolder.setHeat(topper);
		SingletonHolder.setGamma(gamma);
		getHeat().drawPureSubMap(posi.getHeat(), topper, gamma, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat(),x1,y1,x2,y2);
		buffer = getHeat().getBuffer();
		this.setVisible(true);
	    //this.setPreferredSize(new Dimension((int)prefWidth,(int)prefHeight));
	    this.repaint();
		
	}

	 @Override
	 protected void paintComponent(Graphics g) 
	 {

		  super.paintComponent(g);
		  Graphics2D g2 = (Graphics2D) g;
		  g2.drawImage(buffer,0,0,this);
		 
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

	public Image getBuffer() {
		return buffer;
	}

	public void setBuffer(Image buffer) {
		this.buffer = buffer;
	}

	public PositionGrid getPositions() {
		return position;
	}

	public void setPositions(PositionGrid positions) {
		// TODO Auto-generated method stub
		position = positions;
	}

	public HeatMap getHeat() {
		return heat;
	}

	public void setHeat(HeatMap heat) {
		this.heat = heat;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	

	
	
}
