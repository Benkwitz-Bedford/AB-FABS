package cycle_components;

import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import singleton_holders.SingletonHolder;

public class MusicBox {
	
	final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
	private SourceDataLine line = AudioSystem.getSourceDataLine(af);
	final AudioFormat af2 = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
	private SourceDataLine line2 = AudioSystem.getSourceDataLine(af2);
	protected static final int SAMPLE_RATE = 16 * 1024;
	private boolean running = false;
	private int mode = 0;
    HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
	
	public MusicBox() throws LineUnavailableException
	{
		start();
	}

	private void start() {
		try {
			line.open(af, SAMPLE_RATE);
			line2.open(af2, SAMPLE_RATE);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        line.start();
        line2.start();
        running = true;
		
	}

	public void playDifferential(int[] cT, int[] pT) {
		int[] diff = new int[8];
		int largest = 0;
		int lar = 0;
		for(int i = 0; i < diff.length; i++)
		{
			diff[i] = cT[i] - pT[i];
			if(i == 0)
			{
				largest = diff[i];
			}else
			{
				if(diff[i] > largest)
				{
					largest = diff[i];
					lar = i;
				}
			}
		}
		try {
			this.play((int) (lar+1));
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 mode = addMode(lar+1);
		 if(hm.size() >= 8)
		 {
			 hm = new HashMap<Integer,Integer>();
				//this.playMode();
			
		 }
	}
	
	public int addMode(int arr)
	{
	    int max  = 1;
	    int temp = 1;

        if (hm.get(arr) != null) {

            int count = hm.get(arr);
            count++;
            hm.put(arr, count);

            if(count > max) {
                max  = count;
                temp = arr;
            }
        }

        else 
            hm.put(arr,1);
	    
	    return temp;
	}
	
	private void play(int i) throws LineUnavailableException 
	{
		int freq = (i*10)*(mode*10);
		//System.out.println("freq "+freq+" "+mode);
       byte [] toneBuffer = createSinWaveBuffer(freq, SingletonHolder.getBrakes()*2);
	   line.drain();
	   
       line.write(toneBuffer, 0, toneBuffer.length);


    }
	private void playMode() throws LineUnavailableException 
	{

       byte [] toneBuffer = createSinWaveBuffer(mode*400+400, SingletonHolder.getBrakes()*8);
	   line2.drain();
       line2.write(toneBuffer, 0, toneBuffer.length);
       //System.out.println("wooo");

    }
	
	public void end()
	{
	   line.drain();

	   line.close();
	   running = false;
	}
	public static byte[] createSinWaveBuffer(double freq, int ms) {
	       int samples = (int)((ms * SAMPLE_RATE) / 1000);
	       byte[] output = new byte[samples];
	           //
	       double period = (double)SAMPLE_RATE / freq;
	       for (int i = 0; i < output.length; i++) {
	           double angle = 2.0 * Math.PI * i / period;
	           output[i] = (byte)(Math.sin(angle) * 127f);  }

	       return output;
	   }

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

}
