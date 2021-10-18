package file_manipulation;

import gui_stat_gen.SectorBuilder;
import heatmaps.HeatMap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import singleton_holders.SingletonHolder;
import stat_data_holders.SnapShotObfust;

public class StatOffLoader {

	

	public void generateFullFileSystemFromShuffle(DataShuffler files, int j, double d) {
		System.out.println(" 1 ");
		final JFileChooser fc = new JFileChooser();
		HeatMap heater = new HeatMap();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fc.setDialogTitle("Location");
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		if(file.getPath() == null)
		{
			file = new File(System.getProperty("user.dir"));
		}
		
		File overAllFolder = new File(file.getAbsolutePath()+"//"+files.getName()+" folder");
		overAllFolder.mkdir();
		File imageFolder = new File(overAllFolder.getAbsolutePath()+"//"+files.getName()+" images");
		imageFolder.mkdir();
		File dataFolder = new File(overAllFolder.getAbsolutePath()+"//"+files.getName()+" data");
		dataFolder.mkdir();
		File file3 = new File(overAllFolder.getAbsolutePath()+"// details.txt");
		try {
			PrintWriter writer = new PrintWriter(file3);
			String[] values = SingletonHolder.grabAllValues();
			writer.println(SingletonHolder.getMasterSeed());
			for(int i = 0 ; i < values.length;i++)
			{
				writer.println(values[i]);
			}
			
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < files.getSnapFiles().size();i++)
		{
			System.out.println(" 2 ");
			File runHeatImageDir = new File(imageFolder.getAbsolutePath()+"//"+"run "+i+" heat images");
			runHeatImageDir.mkdir();
			File runPopImageDir = new File(imageFolder.getAbsolutePath()+"//"+"run "+i+" pop images");
			runPopImageDir.mkdir();
			File runTurnImageDir = new File(imageFolder.getAbsolutePath()+"//"+"run "+i+" turn images");
			runTurnImageDir.mkdir();
			File runBounceImageDir = new File(imageFolder.getAbsolutePath()+"//"+"run "+i+" bounce images");
			runBounceImageDir.mkdir();
			File runCollisionImageDir = new File(imageFolder.getAbsolutePath()+"//"+"run "+i+" collision images");
			runCollisionImageDir.mkdir();
			File runAbsTurnImageDir = new File(imageFolder.getAbsolutePath()+"//"+"run "+i+" absolute turn distance images");
			runAbsTurnImageDir.mkdir();
			File runTurnDistImageDir = new File(imageFolder.getAbsolutePath()+"//"+"run "+i+" turn distance images");
			runTurnDistImageDir.mkdir();
			File runDataDir = new File(dataFolder.getAbsolutePath()+"//"+"run "+i+" data");
			runDataDir.mkdir();
			for(int l = 0 ; l < files.getSnapFiles().get(i).size();l++)
			{
				try {
				    // retrieve image
					heater.drawPureMap(files.getHeatGridFast(new File(files.getSnapHeatFiles().get(i).get(l))), j, d, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				    BufferedImage bi = heater.getBuffer();
				    File iterImage = new File(runHeatImageDir.getAbsolutePath()+"//"+"inc "+l+".png");
				    ImageIO.write(bi, "png", iterImage);
				} catch (IOException e) {
					
				}
				try {
				    // retrieve image
					heater.drawPureMap(files.getHeatGridFast(new File(files.getSnapPopFiles().get(i).get(l))), j, d, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
				    BufferedImage bi = heater.getBuffer();
				    File iterImage = new File(runPopImageDir.getAbsolutePath()+"//"+"inc "+l+".png");
				    ImageIO.write(bi, "png", iterImage);
				} catch (IOException e) {
					
				}
				/*
				 * this.genTurnImage(60,2,180,6,16,false);
					
					}else
					{
						if(imageType.getSelectedIndex() == 3)
						
						{
							//bounce
							//this.genTurnImage(34,2,105,6,8);
							this.genTurnImage(44,2,132,6,8,false);
						}
					else
					{
						if(imageType.getSelectedIndex() == 4)
						
						{
							//collision
							this.genTurnImage(28,2,84,6,8,false);
						
						}else
						{
							if(imageType.getSelectedIndex() == 5)
							
							{
								//abs
								this.genTurnImage(92,2,276,6,8,true);
							
							}else
							{
								if(imageType.getSelectedIndex() == 6)
								
								{
									//turn+dist
									this.genTurnImage(109,2,325,6,16,false);
				 */
				try {
				    // retrieve image
					
				    BufferedImage bi = genTurnImageClean(60,2,180,6,16,false,files,i,l);
				    File iterImage = new File(runTurnImageDir.getAbsolutePath()+"//"+"inc "+l+".png");
				    ImageIO.write(bi, "png", iterImage);
				} catch (IOException e) {
					
				}
				try {
				    // retrieve image
					
				    BufferedImage bi = genTurnImageClean(44,2,132,6,8,false,files,i,l);
				    File iterImage = new File(runBounceImageDir.getAbsolutePath()+"//"+"inc "+l+".png");
				    ImageIO.write(bi, "png", iterImage);
				} catch (IOException e) {
					
				}
				try {
				    // retrieve image
					
				    BufferedImage bi = genTurnImageClean(28,2,84,6,8,false,files,i,l);
				    File iterImage = new File(runCollisionImageDir.getAbsolutePath()+"//"+"inc "+l+".png");
				    ImageIO.write(bi, "png", iterImage);
				} catch (IOException e) {
					
				}
				try {
				    // retrieve image
					
				    BufferedImage bi = genTurnImageClean(92,2,276,6,8,true,files,i,l);
				    File iterImage = new File(runAbsTurnImageDir.getAbsolutePath()+"//"+"inc "+l+".png");
				    ImageIO.write(bi, "png", iterImage);
				} catch (IOException e) {
					
				}
				try {
				    // retrieve image
					
				    BufferedImage bi = genTurnImageClean(109,2,325,6,16,false,files,i,l);
				    File iterImage = new File(runTurnDistImageDir.getAbsolutePath()+"//"+"inc "+l+".png");
				    ImageIO.write(bi, "png", iterImage);
				} catch (IOException e) {
					
				}
				
			}
			try {
				Files.copy(Paths.get(files.getSnapTables().get(i)), Paths.get(runDataDir.getAbsolutePath()+"//"+"run "+i+".txt"), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		File metaHeatImageDir = new File(imageFolder.getAbsolutePath()+"//"+"meta heat images");
		metaHeatImageDir.mkdir();
		File metaPopImageDir = new File(imageFolder.getAbsolutePath()+"//"+"meta pop images");
		metaPopImageDir.mkdir();
		File metaTurnImageDir = new File(imageFolder.getAbsolutePath()+"//"+"meta turn images");
		metaTurnImageDir.mkdir();
		File metaBounceImageDir = new File(imageFolder.getAbsolutePath()+"//"+"meta bounce images");
		metaBounceImageDir.mkdir();
		File metaCollisionImageDir = new File(imageFolder.getAbsolutePath()+"//"+"meta collision images");
		metaCollisionImageDir.mkdir();
		File metaAbsTurnImageDir = new File(imageFolder.getAbsolutePath()+"//"+"meta absolute turn distance images");
		metaAbsTurnImageDir.mkdir();
		File metaTurnDistImageDir = new File(imageFolder.getAbsolutePath()+"//"+"meta turn distance images");
		metaTurnDistImageDir.mkdir();
		File metaDataDir = new File(dataFolder.getAbsolutePath()+"//"+"meta data");
		metaDataDir.mkdir();
		File metaTableDataDir = new File(dataFolder.getAbsolutePath()+"//"+"cross run table data");
		metaTableDataDir.mkdir();
		try {
			Files.copy(Paths.get(files.getSnapMetaTables()), Paths.get(metaDataDir.getAbsolutePath()+"//"+"meta table.txt"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < files.getSnapHeatMetaFiles().size();i++)
		{
			System.out.println(" 3 ");
			try {
			    // retrieve image
				heater.drawPureMap(files.getMetaHeatGrid(i),j,d, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			    BufferedImage bi = heater.getBuffer();
			    File iterImage = new File(metaHeatImageDir.getAbsolutePath()+"//"+"inc "+i+".png");
			    ImageIO.write(bi, "png", iterImage);
			} catch (IOException e) {
					
			}
			try {
			    // retrieve image
				heater.drawPureMap(files.getMetaPopGrid(i),j,d, SingletonHolder.getColSelector(), SingletonHolder.isInvertHeat());
			    BufferedImage bi = heater.getBuffer();
			    File iterImage = new File(metaPopImageDir.getAbsolutePath()+"//"+"inc "+i+".png");
			    ImageIO.write(bi, "png", iterImage);
			} catch (IOException e) {
					
			}
			try {
			    // retrieve image
				
			    BufferedImage bi = genMetaTurnImageClean(60,2,180,6,16,false,files,i);
			    File iterImage = new File(metaTurnImageDir.getAbsolutePath()+"//"+"inc "+i+".png");
			    ImageIO.write(bi, "png", iterImage);
			} catch (IOException e) {
				
			}
			try {
			    // retrieve image
				
			    BufferedImage bi = genMetaTurnImageClean(44,2,132,6,8,false,files,i);
			    File iterImage = new File(metaBounceImageDir.getAbsolutePath()+"//"+"inc "+i+".png");
			    ImageIO.write(bi, "png", iterImage);
			} catch (IOException e) {
				
			}
			try {
			    // retrieve image
				
			    BufferedImage bi = genMetaTurnImageClean(28,2,84,6,8,false,files,i);
			    File iterImage = new File(metaCollisionImageDir.getAbsolutePath()+"//"+"inc "+i+".png");
			    ImageIO.write(bi, "png", iterImage);
			} catch (IOException e) {
				
			}
			try {
			    // retrieve image
				
			    BufferedImage bi = genMetaTurnImageClean(92,2,276,6,8,true,files,i);
			    File iterImage = new File(metaAbsTurnImageDir.getAbsolutePath()+"//"+"inc "+i+".png");
			    ImageIO.write(bi, "png", iterImage);
			} catch (IOException e) {
				
			}
			try {
			    // retrieve image
				
			    BufferedImage bi = genMetaTurnImageClean(109,2,325,6,16,false,files,i);
			    File iterImage = new File(metaTurnDistImageDir.getAbsolutePath()+"//"+"inc "+i+".png");
			    ImageIO.write(bi, "png", iterImage);
			} catch (IOException e) {
				
			}
			
			try {
				Files.copy(Paths.get(files.getSnapTopTables().get(i)), Paths.get(metaTableDataDir.getAbsolutePath()+"//"+"inc "+i+".txt"), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
	}

	public BufferedImage genTurnImageClean(int a, int b, int c, int d,int f,boolean bool, DataShuffler fil, int run, int inter) {
		BufferedImage returner;
		
			double[] sects = new double[f];
			int pointer = a;
			for(int i = 0; i < sects.length;i++)
			{
				sects[i] = Double.parseDouble(fil.fishOutSnap(pointer, run, inter).split(";")[1]);
				pointer+=b;
			}
			
			SectorBuilder sec = new SectorBuilder(800, 800, 160, 400, sects, bool);
			returner = sec.getSector();
			
			
		
		return returner;
	}
	
	public BufferedImage genMetaTurnImageClean(int a, int b, int c, int d,int f,boolean bool,DataShuffler fil, int inter) {
		BufferedImage returner;
		
			
			double[] sects = new double[f];
			int pointer = c;
			for(int i = 0; i < sects.length;i++)
			{
				sects[i] = Double.parseDouble(fil.fishOutMetaSnap(pointer, inter).split(";")[1]);
				pointer+=d;
			}
			
			SectorBuilder sec = new SectorBuilder(800, 800, 160, 400, sects, bool);
			returner = sec.getSector();
		
		
		return returner;
	}
	
	public void generateFullFileSystemFromObfust(ArrayList<ArrayList<SnapShotObfust>> snaps,ArrayList<ArrayList<ArrayList<String>>> tables, String name,int parseInt, double parseDouble) 
	{
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fc.setDialogTitle("Location");
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		if(file.getPath() == null)
		{
			file = new File(System.getProperty("user.dir"));
		}
		
		File overAllFolder = new File(file.getAbsolutePath()+"//"+name+" folder");
		overAllFolder.mkdir();
		File imageFolder = new File(overAllFolder.getAbsolutePath()+"//"+name+" images");
		imageFolder.mkdir();
		File dataFolder = new File(overAllFolder.getAbsolutePath()+"//"+name+" data");
		dataFolder.mkdir();
		File file3 = new File(overAllFolder.getAbsolutePath()+"// details.txt");
		try {
			PrintWriter writer = new PrintWriter(file3);
			String[] values = SingletonHolder.grabAllValues();
			writer.println(SingletonHolder.getMasterSeed());
			for(int i = 0 ; i < values.length;i++)
			{
				writer.println(values[i]);
			}
			
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < snaps.size()-1;i++)
		{
			File runImageDir = new File(imageFolder.getAbsolutePath()+"//"+"run "+i+" images");
			runImageDir.mkdir();
			File runDataDir = new File(dataFolder.getAbsolutePath()+"//"+"run "+i+" data");
			runDataDir.mkdir();
			for(int l = 0 ; l < snaps.get(i).size();l++)
			{
				try {
				    // retrieve image
				    BufferedImage bi = snaps.get(i).get(l).genHeat().getBuffer();
				    File iterImage = new File(runImageDir.getAbsolutePath()+"//"+"inc "+l+".png");
				    ImageIO.write(bi, "png", iterImage);
				} catch (IOException e) {
					
				}
				File iterData = new File(runDataDir.getAbsolutePath()+"//"+"inc "+l+".txt");
				ArrayList<ArrayList<String>> table = tables.get(i);
				ArrayList<String> tableLines = new ArrayList<String>();
				for(int m = 0; m < table.get(0).size(); m ++)
				{
					String s = "";
					StringBuilder sb = new StringBuilder();
					sb.append(s);
					for(int n = 0 ; n < table.size(); n++)
					{
						sb.append(table.get(n).get(m));
					}
					tableLines.add(sb.toString());
				}
				tableLines.add("");
				tableLines.add("");
				tableLines.add("");
				ArrayList<String> settings = snaps.get(i).get(l).getSettings();
				
				for(int m = 0; m < settings.size(); m++)
				{
					tableLines.add(settings.get(m));
				}
				try {
					PrintWriter writer = new PrintWriter(iterData);
					for(int m = 0 ; m < tableLines.size();m++)
					{
						writer.println(tableLines.get(m));
					}
					
					
					writer.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		File metaImageDir = new File(imageFolder.getAbsolutePath()+"//"+"meta images");
		metaImageDir.mkdir();
		File metaDataDir = new File(dataFolder.getAbsolutePath()+"//"+"meta data");
		metaDataDir.mkdir();
		int i = snaps.size()-1;
		for(int l = 0 ; l < snaps.get(i).size();l++)
		{
			try {
			    // retrieve image
			    BufferedImage bi = snaps.get(i).get(l).genHeat().getBuffer();
			    File iterImage = new File(metaImageDir.getAbsolutePath()+"//"+"inc "+l+".png");
			    ImageIO.write(bi, "png", iterImage);
			} catch (IOException e) {
				
			}
			File iterData = new File(metaDataDir.getAbsolutePath()+"//"+"inc "+l+".txt");
			ArrayList<ArrayList<String>> table = tables.get(i);
			ArrayList<String> tableLines = new ArrayList<String>();
			for(int m = 0; m < table.get(0).size(); m ++)
			{
				String s = "";
				StringBuilder sb = new StringBuilder();
				sb.append(s);
				for(int n = 0 ; n < table.size(); n++)
				{
					sb.append(table.get(n).get(m));
				}
				tableLines.add(sb.toString());
			}
			tableLines.add("");
			tableLines.add("");
			tableLines.add("");
			
			table = tables.get(i+l+1);
			for(int m = 0; m < table.get(0).size(); m ++)
			{
				String s = "";
				StringBuilder sb = new StringBuilder();
				sb.append(s);
				for(int n = 0 ; n < table.size(); n++)
				{
					sb.append(table.get(n).get(m));
				}
				tableLines.add(sb.toString());
			}

			tableLines.add("");
			tableLines.add("");
			tableLines.add("");
			
			ArrayList<String> settings = snaps.get(i).get(l).getSettings();
			
			for(int m = 0; m < settings.size(); m++)
			{
				tableLines.add(settings.get(m));
			}
			try {
				PrintWriter writer = new PrintWriter(iterData);
				for(int m = 0 ; m < tableLines.size();m++)
				{
					writer.println(tableLines.get(m));
				}
				
				
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
