
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author prathamesh chavan
 * Stores results, implements two methods one to write results to file 
 * and other to display results on screen
 */
public class Results implements FileDisplayInterface,StdoutDisplayInterface {
	
	private List<String> resultList = new ArrayList<String>();
	private File file;
	private FileWriter fw;
	
	/**
	 * Class constructor 
	 */
	public Results(String s)
	{
		int checkexception = 0;
		try
		{	
			file = new File(s);
				if(!(file.exists()))
				{
					file.createNewFile();
				}
				fw = new FileWriter(file);
				checkexception=1;
		}
		catch(IOException e){}
		finally
		{
			if(checkexception==0)
			{
				try
				{
					fw.close();
				}
				catch(IOException e)
				{
					
				}
			}
		}
	}
	
	/**
	 * Adds string s to the result list
	 */
	public void storeNewResult(String s)
	{
		resultList.add(s);
	}
	
	@Override
	public void writeToFile()
	{
		int checkexception = 0;
		try
		{
			for(String str :resultList)
			{
				fw.write(str + "\n");
			}
			checkexception=1;
		}
		catch (IOException e)
		{
			System.out.println("Error writing to the file");
			e.printStackTrace();
			System.exit(1);
		}
		finally
		{
			if(checkexception==0)
			{
				try
				{
					fw.close();
				}
				catch(IOException e)
				{
					System.exit(1);
				}
			}
		}
	}
	
	@Override
	public void writeToStdout()
	{
		for(String str :resultList)
		{
			System.out.println(str +"\n");
		}
	}
	
	/**
	 * This method closes the output file 
	 */
	public void closeOutputFile()
	{
		try
		{
			fw.close();
		}
		catch (IOException e)
		{
			System.exit(1);
		}
	}

}
