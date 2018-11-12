package perceptron;

/**
 * @author PrathameshChavan
 * This is the starting point of the application
 * The driver class validates the command line arguments
 * and then trains and tests the data 
 */

public class Driver 
{
	
	public static void main(String [] args)
	{
		if(args.length!=8)
		{
			System.out.println("Illegal number of arguments");
			System.exit(0);
		}
		
		String train_hampath = args[0];
		String train_spampath = args[1];
		String test_hampath = args[2];
		String test_spampath = args[3];
		String spamwordpath = args[4];
		double loop = 0;
		double eta = 0;
		double stopwordscheck = 1;
		try
		{
			loop = Double.parseDouble(args[5]);
			eta = Double.parseDouble(args[6]);
			stopwordscheck = Double.parseDouble(args[7]);
		}
		catch(Exception e)
		{
			System.out.println("Error parsing values");
			System.exit(0);
		}
				
		Perceptron nb = new Perceptron(train_hampath,train_spampath,loop,eta);
		
		System.out.println("*******************************");
		System.out.println("");
		
		//Exclude or include stop words while calculating accuracies
		if(stopwordscheck==2)
		{
			System.out.println("Calculating accuracy without stopwords");
			nb.setStopWords(spamwordpath);
		}
		else
		{
			System.out.println("Calculating accuracy with stopwords");
		}
		nb.trainData();
		
		System.out.println("Eta value : " +eta);
		System.out.println("No of loops : "+loop);
		nb.testData(test_hampath, test_spampath);
		System.out.println("");
		System.out.println("*******************************");
		
	}

}
