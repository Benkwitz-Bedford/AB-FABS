package running_modules_jump;

import javax.sound.sampled.LineUnavailableException;

import cycle_components.MusicBox;
import running_modules_increment.Bundle;
import running_modules_increment.DataBundle;
import running_modules_increment.Module;
import singleton_holders.SingletonHolder;
import singleton_holders.SingletonStatStore;

public class MusicModule extends Module{

	protected int[] previousTrack = new int[]{0,0,0,0,0,0,0,0};
   	protected int[] currentTrack = new int[8];
   	protected MusicBox muse; 
   	private boolean music = true;
   	
   	public MusicModule()
   	{
   		modType  = "music";
	   	try {
			muse = new MusicBox();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	}
	
	
	public void genNext(Bundle sim)
	{
		if(SingletonHolder.isMusic())
		{
			currentTrack = this.pollTurnTrack();
			muse.playDifferential(currentTrack,previousTrack);
			for(int i = 0; i < previousTrack.length; i++)
			{
				previousTrack[i] = currentTrack[i];
			}
		}
	}
	
	protected int[] pollTurnTrack() {
		int[] in = new int[8];
		/*in[0] = SingletonStatStore.getChosenSectOne();
		in[1] = SingletonStatStore.getChosenSectTwo();
		in[2] = SingletonStatStore.getChosenSectThree();
		in[3] = SingletonStatStore.getChosenSectFour();
		in[4] = SingletonStatStore.getChosenSectFive();
		in[5] = SingletonStatStore.getChosenSectSix();
		in[6] = SingletonStatStore.getChosenSectSeven();
		in[7] = SingletonStatStore.getChosenSectEight();
		*/
		return in;
	}
}
