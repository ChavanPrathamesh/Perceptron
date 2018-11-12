import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArffGenerator {
	
	
	private static String [][] data;
	private static int m;
	private static int n;
	private static File hamfolder;
	private static File[] listOfHamFiles;
	private static File spamfolder;
	private static File[] listOfSpamFiles;
	private static List<String> vocabulary = new ArrayList<String>();
	private static int hamdoccount;
	private static int spamdoccount;
	
	public static void main(String args[])
	{
		hamfolder = new File(args[0]);
		listOfHamFiles= hamfolder.listFiles();
		
		spamfolder = new File(args[1]);
		listOfSpamFiles =  spamfolder.listFiles();
		m=0;
		n=0;
		
		createVocab();
		n=vocabulary.size();
		data = new String[m][n+2];
		initializeData();
		
		try
		{
		Results res = new Results("Testing2.arff");
		res.storeNewResult("% 1. Title: Machine Learning");
		res.storeNewResult("% ");
		res.storeNewResult("% 2. Sources:");
		res.storeNewResult("%      (a) Creator: Prathamesh Chavan");
		res.storeNewResult("%      (b) Donor: Binghamton University");
		res.storeNewResult("%      (c) Date: March, 2018");
		res.storeNewResult("% ");
		res.storeNewResult("@RELATION prathamesh");
		
		for(String s:vocabulary)
		{
			String str ="@ATTRIBUTE ";
			str = str + s+" ";
			str = str + "NUMERIC";
			res.storeNewResult(str);
		}
		
		
		res.storeNewResult("@ATTRIBUTE outputclass {ham,spam}");
		res.storeNewResult("@Data");
		
		for(int lm=0;lm<m;lm++)
		{
		String str="";
		for(int ln = 1;ln<n+2;ln++)
		{
			
			if(ln==n+1)
			{
				str=str+data[lm][ln];
			}
			else
			{
				str=str+data[lm][ln]+",";
			}
		}
		System.out.println("File number"+lm);
		res.storeNewResult(str);
		}
		
		
		
		int j=0;
		
		res.writeToFile();
		res.closeOutputFile();
		System.out.println("File generated successfully");
		}
		catch(Exception e)
		{
			System.out.println("Some error");
		}
		
		System.out.println("Program complete");
	}
	
	
	
	public static void initializeData()
	{
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n+2;j++)
			{
				if(j==0)
				{
					data[i][j]="1";
				}
				else
				{
					data[i][j]="0";
				}
			}
		}
		
		int mcounter=0;
		
		//Initialization from the ham path
		for (File file : listOfHamFiles) 
		{
			try
			{
				String line;
				String featuresarray[]=null;
				BufferedReader br = new BufferedReader(new FileReader(file));
				try
				{	
					while((line=br.readLine())!=null)
					{
						featuresarray = line.split(" ");
						for(int i=0;i<featuresarray.length;i++)
						{
							
								int j = vocabulary.indexOf(featuresarray[i]);
								data[mcounter][j+1]=Integer.toString(Integer.parseInt(data[mcounter][j+1])+1);
							
						}
					}
					br.close();
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
			data[mcounter][n+1]="Ham";
			mcounter++;
		}
		
		//Initialization from the spam path
		for (File file : listOfSpamFiles) 
		{
			try
			{
				String line;
				String featuresarray[]=null;
				BufferedReader br = new BufferedReader(new FileReader(file));
				try
				{	
					while((line=br.readLine())!=null)
					{
						featuresarray = line.split(" ");
						for(int i=0;i<featuresarray.length;i++)
						{
							
								int j = vocabulary.indexOf(featuresarray[i]);
								data[mcounter][j+1]=Integer.toString(Integer.parseInt(data[mcounter][j+1])+1);
						}
					}
					br.close();
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
			data[mcounter][n+1]="Spam";
			mcounter++;
		}
		
		
		
	}
	
	
	/**
	 * Vocab created on the basis of unique words 
	 */
	public static void createVocab()
	{
		for (File file : listOfHamFiles) 
		{
			hamdoccount++;
			m++;
			try
			{
				String line;
				String featuresarray[]=null;
				BufferedReader br = new BufferedReader(new FileReader(file));
				try
				{	
					while((line=br.readLine())!=null)
					{
						featuresarray = line.split(" ");
						for(int i=0;i<featuresarray.length;i++)
						{
							if(!(vocabulary.contains(featuresarray[i])))
							{
								vocabulary.add(featuresarray[i]);
							}
						}
					}
					br.close();
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
		
		
		
		for (File file : listOfSpamFiles) 
		{
			spamdoccount++;
			m++;
			try
			{
				String line;
				String featuresarray[]=null;
				BufferedReader br = new BufferedReader(new FileReader(file));
				try
				{	
					while((line=br.readLine())!=null)
					{
						featuresarray = line.split(" ");
						for(int i=0;i<featuresarray.length;i++)
						{
							if(!(vocabulary.contains(featuresarray[i])))
							{
								vocabulary.add(featuresarray[i]);
							}
						}
					}
					br.close();
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
		
	}

}
