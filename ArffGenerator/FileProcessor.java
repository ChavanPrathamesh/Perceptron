
import java.io.*;

/**
 * @author prathamesh chavan
 * Input file initialization is done in this class
 */

public class FileProcessor {

	private File file;
	private FileReader fr;
	private BufferedReader br;
	
	/**
	 * Class constructor
	 */
	public FileProcessor(String s)
	{
		try
		{
			file = new File(s);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("File not present");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * This method reads one line at a time from the input file
	 */
	public String readLine()
	{
		
		int checkexception = 0;
		try
		{
			String s="";
			if(((s=br.readLine())!=null))
			{
				checkexception = 1;
				return s;
			}
		}
		catch(IOException e)
		{
			System.out.println("Exception caught while reading from file");
			e.printStackTrace();
			System.exit(1);
		}
		finally
		{
			if(checkexception==0)
			{
				try
				{
					fr.close();
					br.close();
				}
				catch(IOException e)
				{
					System.exit(1);
				}
			}
		}
		return null;
	}
	
	/**
	 * This method closes the input file
	 */
	public void closeInputFile()
	{
		try
		{
			fr.close();
		}
		catch (IOException e)
		{
			System.exit(1);
		}
	}
}


