*************** INSTALLING RUTA AND RUNNING THE PROJECT ********************

1. Install ECLIPSE and add UIMA and UIMA RUTA.  Refer (https://uima.apache.org/ruta.html)
2. Select the RUTA Perspective
3. Import the project as MAVEN Project.


*************** EXECUTION FILES FOR UIMA RUTA ********************

Files to be run in the following sequence:
1. DBPedia.java stored at /src/main/java               -- Get the DBPedia Links
2. Main.ruta stored at /script                         -- Run and View the Annotations
3. ConfigurableExporter.java stored at /src/main/java  -- Generate the output in .txt format

Output Files:
1. 2_Geschäftsbeziehung_und_Bankvertrag_raw.txt.xmi stored at /output -- Output in .xmi format 
2. toc.txt stored at /output                                          -- Output in .txt format 
3. dboutput.txt stored at /resources                                  -- Output for DBpedia Annotations

*************** GETTING STATISTICS IN UIMA RUTA ********************

Evaluation setup:
UIMA(statistics view)

To get RUTA statistics: "view eclipse->window->show view->other->UIMA RUTA->statistics view"

By default the statistics is set to false in RUTA in order to change this configuration parameter. This needs to be TRUE to get the statistics populated.

 Go to the  BasicEngine.xml  in  resources of the project and add the following:
 
 <configurationParameterSettings>
            <nameValuePair>
                <name>statistics</name>
                <value>
                    <boolean>true</boolean>
                </value>
            </nameValuePair>
</configurationPrameterSettings>

Add the above name value pair in the configuration parameter settings and then run RUTA script that will give the statistics of time spent on each  step of executing the RUTA Script


********* DBPEDIA TAGGER WORK AROUND *************

For dbpedia api we had used the following parameters and end points
Endpoint:http://model.dbpedia-spotlight.org/de/annotate
input params:
1. Text
2. Confidence
3. Support



****** ADDING MORE INFORMATION ABOUT THE PYTHON SCRIPT AND THE JAR FILE ******
Python script to find lengthy annotations so that false positives can be identified manually.
The script takes two arguments: 
Usage: python3 Lengthy_Annotations.py inputfolder outputfolder

The program asks for a standard deviation number. The idea behind this is that if there are 10 gerichts with mean length of 10, and 20 Gesetz with mean length of 20, and if I choose 2 as my std, I output only those references which are more than 2 std apart from the mean.

The program produces two files in the output folder: outputfile and outputfilewhole.csv

Outputfile consists of annotations for which the above calculation is done at each file level
Outputfilewhole.csv consists of annotations for which the above calculation is done at the entire folder level.

----------------------------------------------------------------------------------------
----------- END OF INFORMATION ABOUT THE PYTHON SCRIPT. NOW THE JAR FILE ---------
----------------------------------------------------------------------------------------

After loading the folder as Maven Project, 
1. Export the project to a runnable jar.
  1.1 Choose the option: "Package the required libraries into a new folder".
  1.2 Give a name to the extracted jar file. In this case, I named it "RefExtractor.jar"
2. This should create a jar file and is available in the system.

P.S: In case rules are modified, follow steps 1 and 2 to create the new JAR File.

Now, to use this JAR file in a pipeline, issue the following command:

java -cp <<path_to_the_library_folder>> -jar RefExtractor.jar <<input_folder_path>> <<output_folder_path>>

Eg: java -cp C:\RefExtractor_lib -jar C:\RefExtractor.jar C:\Rakesh\input C:\Rakesh\output

This will create REF annotations for the files present in the input folder and write those annotations to respective files in the output folder. 

NOTE: In case, there are input files which do not contain any annotations, still a respective file is created in the output folder.
One can delete these zero sized files by using the following command:

find . -type f -size 0 -delete
