package file_manipulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DetailReader {
	
	private String[] values = null;
	private String seed = "";
	
	public DetailReader(File file) throws FileNotFoundException
	{
		Scanner read = new Scanner (file);
		setSeed(read.nextLine());
		ArrayList<String> vals = new ArrayList<String>();
		do
		{
			vals.add(read.nextLine());
		}while(read.hasNextLine());
		setValues(new String[vals.size()]);
		for(int i = 0; i < vals.size();i++)
		{
			getValues()[i] = vals.get(i);
		}
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

}
