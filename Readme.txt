Perceptron supervised learning model for binary classification of 
HAM and spam email classification

Assuming you are in the directory containing this README:

## To clean:
ant -buildfile perceptron/src/build.xml clean

-----------------------------------------------------------------------
## To compile: 
ant -buildfile perceptron/src/build.xml all

-----------------------------------------------------------------------
## To run by specifying arguments from command line 
## We will use this to run your code:

NOTE: For with stop words there is a different directory and for without stop words there is s different directory
I have done the stemming and removing the stop words in python so those files are to be passed to java program
Use paths accordingly
You will have to fire two commands one for with stop words and one for without stop words as follows

ant -buildfile perceptron/src/build.xml run -Darg0=/home/pchavan4/spring18/ml/assignment3/pchavan4_java_hw3/train/train/pc_ham -Darg1=/home/pchavan4/spring18/ml/assignment3/pchavan4_java_hw3/train/train/pc_spam -Darg2=/home/pchavan4/spring18/ml/assignment3/pchavan4_java_hw3/test/test/pc_ham -Darg3=/home/pchavan4/spring18/ml/assignment3/pchavan4_java_hw3/test/test/pc_spam -Darg4=/home/pchavan4/spring18/ml/assignment3/pchavan4_java_hw3/stopwords.txt -Darg5=40 -Darg6=0.1 -Darg7=1

NOTE: Please make sure that use this command as specified above and with correct arguments
Any deletion or addition of spaces and this will not work
For:
-Darg0= Path of training ham folder
-Darg1= Path of training spam folder
-Darg2= Path of test ham folder
-Darg3= Path of test spam folder
-Darg4= Path of stopwords
-Darg5= loop value
-Darg6= eta value
-Darg7= include stop words or not 1 to include 2 to exclude

-----------------------------------------------------------------------

## To create tarball for submission
ant -buildfile src/build.xml tarzip or tar -zcvf firstName_secondName_assign_number.tar.gz firstName_secondName_assign_number

-----------------------------------------------------------------------


Also included a folder for converting text files from a location to an arff file named ArffGenerator and the Traing and Testing .arff files as well



