package file_manipulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class TrackOffloader {

	
	
	private File trackLocation;
	private File targetFolder;
	private int trackZone = 100;
	private String name;
	private int iter;

	public TrackOffloader(String name, int iter,File current,  File file) throws IOException {
		trackLocation = current;
		targetFolder = file;
		this.name = name;
		this.iter = iter;
		this.generateTracks(this.grabTracks(current));
	}

	private ArrayList<ArrayList<Double[]>> grabTracks(File current) throws FileNotFoundException {
		ArrayList<ArrayList<Double[]>> tracks = new ArrayList<ArrayList<Double[]>>();
		
		ArrayList<String> lines = new ArrayList<>();
		Scanner read;
		read = new Scanner(current);
		lines.add(read.nextLine());
		do
		{
			lines.add(read.nextLine());
		}while(read.hasNextLine());
		read.close();
		for(int i = 0; i < lines.size();i++)
		{
			String[] lineBreak = lines.get(i).split(":");
			ArrayList<Double[]> posi = new ArrayList<Double[]>();
			for(int l = 0; l < lineBreak.length;l++)
			{
				String[] brak = lineBreak[l].split(",");
				posi.add(new Double[] {Double.parseDouble(brak[0]),Double.parseDouble(brak[1])});
				
			}
			tracks.add(posi);
		}
		return tracks;
	}

	private void generateTracks(ArrayList<ArrayList<Double[]>> tracks) throws IOException 
	{
		ArrayList<ArrayList<Double[]>> trackSet = this.normalise(tracks);
		for(int i =0; i < trackSet.size();i++)
		{

			this.drawAndOutput(targetFolder,trackSet.get(i),i);
		}
		
	}

	private void drawAndOutput(File file, ArrayList<Double[]> track,int  l) throws IOException {
		BufferedImage buffer = new BufferedImage(trackZone, trackZone,BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = buffer.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, trackZone, trackZone);
		g.setColor(Color.BLACK);
		for(int i = 4;i< track.size()-1;i++)
		{
			g.drawLine((int)track.get(i)[0].longValue(), (int)track.get(i)[1].longValue(), (int)track.get(i+1)[0].longValue(), (int)track.get(i+1)[1].longValue());
			
		}
		File iterImage = new File(file.getAbsolutePath().replace(".txt", "")+"//"+this.name+"_"+iter+"_track"+l+".png");
	    ImageIO.write(buffer, "png", iterImage);
		/*Double[] tra = new Double[track.size()*2];
		for(int i = 0;i< track.size();i++)
		{
			tra[i*2] = track.get(i)[0];
			tra[i*2+1] = track.get(i)[1];
		}
		Polyline polyline = new Polyline();
		polyline.getPoints().addAll(tra);
		buffer.getGraphics().drawPolyLine(polyline);*/
	}

	private ArrayList<ArrayList<Double[]>> normalise(ArrayList<ArrayList<Double[]>> tracks) {
		ArrayList<ArrayList<Double[]>> trackSet = new ArrayList<ArrayList<Double[]>>();
		for(int i = 0; i < tracks.size();i++)
		{
			ArrayList<Double[]> track = tracks.get(i);
			double lowestX = 999999.00;
			double lowestY = 999999.00;
			double highestX = 00.00;
			double highestY = 00.00;
			
			for(int l = 0; l < track.size();l++)
			{
				Double[] poi = track.get(l);
				if(poi[0]<lowestX)
				{
					lowestX = poi[0];
				} 
					
				
				if(poi[1]<lowestY)
				{
					lowestY = poi[1];
				}
				
				
				
			}

			
			Double xDec = lowestX;
			Double yDec = lowestY;
			
			for(int l = 0;l<track.size();l++)
			{
				Double[] poi = track.get(l);
				poi[0] = poi[0]-xDec;
				poi[1] = poi[1]-yDec;
				track.set(l, poi);
			}
			
			for(int l = 0;l<track.size();l++)
			{
				Double[] poi = track.get(l);
				if((poi[0] > highestX))
				{
					highestX = poi[0];
				}
				if((poi[1] > highestY))
				{
					highestY = poi[1];
				}
			}
			Double xRatio = trackZone/highestX;
			Double yRatio = trackZone/highestY;
			for(int l = 0; l < track.size();l++)
			{
				Double[] poi = track.get(l);
				poi[0] = poi[0]*xRatio;
				poi[1] = poi[1]*yRatio;
				track.set(l, poi);
			}
			trackSet.add( track);
		}
		return trackSet;
	}

}
