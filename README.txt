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




