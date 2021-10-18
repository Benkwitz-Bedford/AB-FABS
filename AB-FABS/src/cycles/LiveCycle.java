package cycles;

import running_modules_increment.DataBundleCA;

public class LiveCycle extends Cycle{
	
	
    public LiveCycle(boolean CA)
    {
    	if(CA)
    	{
    		sim = new DataBundleCA(dimensions, posSize,parent);
    	}
    }
	
}
