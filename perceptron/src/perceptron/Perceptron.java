package perceptron;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author PrathameshChavan
 * This is the Perceptron Class
 * This class trains and tests data using the perceptron algorithm
 */
public class Perceptron {
	
	//The data structures used for this class
	private String train_hampath;
	private String train_spampath;
	
	private StopWords sword;
	private boolean STOPWORDCHECK = false;
	
	private File hamfolder;
	private File[] listOfHamFiles;
	
	private File spamfolder;
	private File[] listOfSpamFiles;
	
	private int HAM_OUTPUT;
	private int SPAM_OUTPUT;
	
	private List<String> vocabulary = new ArrayList<String>();
	
	private int m;
	private int n;
	
	private double [][] data;
	private double [] w;
	
	private double loop;
	private double eta;
	
	//Constructor for initializing train, test path and eta value
	public Perceptron(String ham_path,String spam_path, double lopI, double etaI)
	{
		train_hampath = ham_path;
		train_spampath = spam_path;
		
		hamfolder = new File(train_hampath);
		listOfHamFiles = hamfolder.listFiles();
		
		spamfolder = new File(train_spampath);
		listOfSpamFiles = spamfolder.listFiles();
		
		HAM_OUTPUT = 1;
		SPAM_OUTPUT = -1;
		
		m = 0;
		eta = etaI;
		loop = lopI;
	}
	
	/**
	 * This method generates the stop-word list
	 */
	public void setStopWords(String stopword_path)
	{
		STOPWORDCHECK = true;
		sword = new StopWords(stopword_path);
		sword.generateList();
	}
	
	/**
	 * Train the data in this function 
	 */
	public void trainData()
	{
		m = listOfHamFiles.length+listOfSpamFiles.length;
		
		//Creating the vocabulary
		createVocab();
		
		n = vocabulary.size();
		
		data = new double[m][n+2];			//Data array
		
		w = new double[n+1];				//Weight array
		
		//Initialize the data array 
		initializeData();
		
		//Start training based on the dataset
		startTraining();
	}
	
	/**
	 * Crux of the class
	 * The main logic is implemented here 
	 */
	public void startTraining()
	{
		for(int z=0;z<loop;z++)
		{
			for(int x=0;x<m;x++)
			{
				double originalop = data[x][n+1];
				double predictedop = calculateoutput(x);
				double err = originalop - predictedop;
				
				if(err!=0)
				{
					for(int y=0;y<n;y++)
					{
						w[y+1]=w[y+1]+eta*err*data[x][y+1];
					}
				}
			}
		}
	}
	
	/**
	 * Predict the output class (o) 
	 */
	public int calculateoutput(int value)
	{
		double finalweight = w[0];
		for(int j=1;j<n+1;j++)
		{
			finalweight = finalweight + w[j]*data[value][j];
		}
		return finalweight>0 ? HAM_OUTPUT:SPAM_OUTPUT;
	}
	
		
	/**
	 * This function is used during testing purpose to calculate probabilities 
	 */
	public double calculateTestProbability(double a[])
	{
		double summation = w[0];
		
		for(int i=0;i<a.length;i++)
		{
			summation = summation + w[i+1]*a[i];
		}
		return summation>0 ? HAM_OUTPUT:SPAM_OUTPUT;
	}
	
	/**
	 * Initializing the data array and w[] array 
	 */
	public void initializeData()
	{
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n+2;j++)
			{
				if(j==0)
				{
					data[i][j]=(double)1;
				}
				else
				{
					data[i][j]=(double)0;
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
							if((STOPWORDCHECK&&sword.isStopWord(featuresarray[i])))
							{
								continue;
							}
							else 
							{
								int j = vocabulary.indexOf(featuresarray[i]);
								data[mcounter][j+1]=data[mcounter][j+1]+1;
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
			data[mcounter][n+1]=1;
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
							if((STOPWORDCHECK&&sword.isStopWord(featuresarray[i])))
							{
								continue;
							}
							else 
							{
								int j = vocabulary.indexOf(featuresarray[i]);
								data[mcounter][j+1]=data[mcounter][j+1]+1;
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
			data[mcounter][n+1]=-1;
			mcounter++;
		}
		
		//Initialize the w[] array to random values
		for(int i=0;i<n+1;i++)
		{
			w[i]=Math.random();
		}	
	}
	
	/**
	 * Vocabulary created on the basis of unique words 
	 */
	public void createVocab()
	{
		for (File file : listOfHamFiles) 
		{
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
							if((STOPWORDCHECK&&sword.isStopWord(featuresarray[i])))
							{
								continue;
							}
							else if(!(vocabulary.contains(featuresarray[i])))
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
							if((STOPWORDCHECK&&sword.isStopWord(featuresarray[i])))
							{
								continue;
							}
							else if(!(vocabulary.contains(featuresarray[i])))
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
	
	
	/**
	 * Test the data and print the accuracy
	 */
	public void testData(String testhampath,String testspampath)
	{
		File testhamfolder;
		File[] testlistOfHamFiles;
		
		File testspamfolder;
		File[] testlistOfSpamFiles;
		
		testhamfolder = new File(testhampath);
		testlistOfHamFiles = testhamfolder.listFiles();
		
		testspamfolder = new File(testspampath);
		testlistOfSpamFiles = testspamfolder.listFiles();
		
		double correctlyclassified = 0;
		double totalclassified = 0;
		
		
		for (File file : testlistOfHamFiles) 
		{
			if(classify(file)==(double)1)
			{
				correctlyclassified++;
				totalclassified++;
			}
			else
			{
				totalclassified++;
			}
		}
		
		for(File file : testlistOfSpamFiles)
		{
			if(classify(file)==(double)-1)
			{
				correctlyclassified++;
				totalclassified++;
			}
			else
			{
				totalclassified++;
			}
		}
		
		System.out.println("Accuracy : "+100*correctlyclassified/totalclassified);
		
	}
	
	/**
	 * Classify if a new test data belongs to a ham class or a spam class
	 */
	public double classify(File f)
	{
		double [] arr = new double[vocabulary.size()];
		Arrays.fill(arr, 0);
		
		try
		{
			String line;
			String featuresarray[]=null;
			BufferedReader br = new BufferedReader(new FileReader(f));
			try
			{	
				while((line=br.readLine())!=null)
				{
					featuresarray = line.split(" ");
					for(int i=0;i<featuresarray.length;i++)
					{
						if((STOPWORDCHECK&&sword.isStopWord(featuresarray[i])))
						{
							continue;
						}
						else if((vocabulary.contains(featuresarray[i])))
						{
							int vocabindex = vocabulary.indexOf(featuresarray[i]);
							arr[vocabindex]++;
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
		
		return  calculateTestProbability(arr);
	
	}
}
