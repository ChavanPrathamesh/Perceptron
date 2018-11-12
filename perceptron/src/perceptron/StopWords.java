package perceptron;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PrathameshChavan
 * This class contains the logic to check for stop-words
 */

public class StopWords {
	
	private List<String> wordList = new ArrayList<String>();		//List which holds the stop words
	String filePath;
	
	public StopWords(String swordFilePath)
	{
		filePath = swordFilePath;
	}
	
	/**
	 * Generate the stop-word list from the words specified in the file 
	 */
	public void generateList()
	{
		String line = null;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			try
			{	
				while((line=br.readLine())!=null)
				{
					wordList.add(line);
				}				
			}
			catch(IOException e)
			{
				System.out.println("Incorrect arguments present in file");
			}	
		}
		 catch(FileNotFoundException e)
		 {
			 System.out.println("Incorrect file path or file does not exist so exiting");
			 System.exit(0);
		 }
	}
	
	/**
	 * @return boolean if the stop-word list contains a particular word 
	 */
	public boolean isStopWord(String word)
	{
		return wordList.contains(word) ? true:false;
	}

}
