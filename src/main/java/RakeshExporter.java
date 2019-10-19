import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.io.InputStreamReader;
//import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.CasUtil;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class RakeshExporter {
	public static final String GESETZ_TYPE = "Main.Gesetz";			// LAW
	public static final String RICHTLINIE_TYPE = "Main.Richtlinie";	// Guidelines
	public static final String VERORDNUNG_TYPE = "Main.Verordnung";	// Regulations
	public static final String GERICHT_TYPE = "Main.Gericht";		// Court of Law
	public static final String REF1_TYPE = "Main.REF1";
	//public static final String TOC_TYPE = "Main.toc";
	public static int start = 0;
 
    /**     MAIN FUNCTION HERE */
    public static void main(String[] args) throws Exception {
    	
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    	LocalDateTime now = LocalDateTime.now();  
    	System.out.println(dtf.format(now));  

    	//final AnalysisEngine engine =
          //      AnalysisEngineFactory.createEngine("C:\\Users\\Rakesh\\Documents\\RAKESH\\MAGDEBURG\\LegalHorizon\\DBSE\\descriptor\\MainEngine");
    	final AnalysisEngine engine = AnalysisEngineFactory.createEngine("MainEngine");
    	
        final CAS cas = engine.newCAS();
        
        //Path currentRelativePath = Paths.get("");
		//String s = currentRelativePath.toAbsolutePath().toString();
       // String s = args[0];

		//File inputDir = new File(args[0] + "/input");
        File inputDir = new File(args[0]);
		
		// get a listing of all files in the directory
	    String[] filesInDir = inputDir.list();

	    // sort the list of files (optional)
	    // Arrays.sort(filesInDir);
	    // have everything i need, just print it now
	    for ( int i=0; i<filesInDir.length; i++ )
	    {
	    	  int counter = 0;
		   //   System.out.println( "file: " + filesInDir[i] );
		   //   File fInput = new File(args[0] + "/input/" + filesInDir[i]);
	    	  File fInput = new File(args[0] + "/" + filesInDir[i]);
		      BufferedReader br_file = new BufferedReader(new FileReader(fInput));
				
		      //read the data from the file to a string
		      String st;
		      String text = "";
		      while ((st = br_file.readLine()) != null) {
		    	  text += st;
		      }
		      cas.setDocumentText(text); // read from file - Still unable to set cas.setDocument to directly pass the file
		      engine.process(cas);
	        
		      // Writing to output file 
		      //String fileName = args[0] + "/output/" + filesInDir[i];
		      String fileName = args[1] + "/" + filesInDir[i];
		      FileWriter fileWriter = new FileWriter(fileName);
			
		      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		   
		      
		      	
		     if (CasUtil.select(cas, cas.getTypeSystem().getType(GESETZ_TYPE)).isEmpty() == false)
		     {
		        for (AnnotationFS Gesetz : CasUtil.select(cas, cas.getTypeSystem().getType(GESETZ_TYPE))) 
		        {
		        	bufferedWriter.write("Gesetz,");
		        	bufferedWriter.write(String.valueOf(Gesetz.getBegin()));
		        	bufferedWriter.write(",");
		        	bufferedWriter.write(String.valueOf(Gesetz.getEnd()));
		        	bufferedWriter.write(",");
					bufferedWriter.write("\"");
					bufferedWriter.write(Gesetz.getCoveredText());
					bufferedWriter.write("\",");
					bufferedWriter.write("\"");
					bufferedWriter.write(String.valueOf(counter)+ "_"+ String.valueOf(filesInDir[i]) );
					bufferedWriter.write("\"");
					bufferedWriter.write("\r\n");
					counter++;
		        }
		     }
		     if (CasUtil.select(cas, cas.getTypeSystem().getType(RICHTLINIE_TYPE)).isEmpty() == false)
		     {
		        for (AnnotationFS Richtlinie : CasUtil.select(cas, cas.getTypeSystem().getType(RICHTLINIE_TYPE))) 
		        {	
	       
		        	bufferedWriter.write("Richtlinie,");
		        	bufferedWriter.write(String.valueOf(Richtlinie.getBegin()));
		        	bufferedWriter.write(",");
		        	bufferedWriter.write(String.valueOf(Richtlinie.getEnd()));
		        	bufferedWriter.write(",");
					bufferedWriter.write("\"");
					bufferedWriter.write(Richtlinie.getCoveredText());
					bufferedWriter.write("\",");
					bufferedWriter.write("\"");
					bufferedWriter.write(String.valueOf(counter)+ "_"+ String.valueOf(filesInDir[i]) );
					bufferedWriter.write("\"");
					bufferedWriter.write("\r\n");
					counter++;
				}
		     }
	        
	      	if (CasUtil.select(cas, cas.getTypeSystem().getType(VERORDNUNG_TYPE)).isEmpty() == false)
	      	{
	        for (AnnotationFS Verordnung : CasUtil.select(cas, cas.getTypeSystem().getType(VERORDNUNG_TYPE))) 
		        {
		        	bufferedWriter.write("Verordnung,");
		        	bufferedWriter.write(String.valueOf(Verordnung.getBegin()));
		        	bufferedWriter.write(",");
		        	bufferedWriter.write(String.valueOf(Verordnung.getEnd()));
		        	bufferedWriter.write(",");
					bufferedWriter.write("\"");
					bufferedWriter.write(Verordnung.getCoveredText());
					bufferedWriter.write("\",");
					bufferedWriter.write("\"");
					bufferedWriter.write(String.valueOf(counter)+ "_"+ String.valueOf(filesInDir[i]) );
					bufferedWriter.write("\"");
					bufferedWriter.write("\r\n");
					counter++;
		        }
	      	}
	      	if(CasUtil.select(cas, cas.getTypeSystem().getType(GERICHT_TYPE)).isEmpty() == false)
	      	{
		        for (AnnotationFS Gericht : CasUtil.select(cas, cas.getTypeSystem().getType(GERICHT_TYPE))) 
		        {
		        	bufferedWriter.write("Gericht,");
		        	bufferedWriter.write(String.valueOf(Gericht.getBegin()));
		        	bufferedWriter.write(",");
		        	bufferedWriter.write(String.valueOf(Gericht.getEnd()));
		        	bufferedWriter.write(",");
					bufferedWriter.write("\"");
					bufferedWriter.write(Gericht.getCoveredText());
					bufferedWriter.write("\",");
					bufferedWriter.write("\"");
					bufferedWriter.write(String.valueOf(counter)+ "_"+ String.valueOf(filesInDir[i]) );
					bufferedWriter.write("\"");
					bufferedWriter.write("\r\n");
					counter++;
		        }
	      	}
	        if(CasUtil.select(cas, cas.getTypeSystem().getType(REF1_TYPE)).isEmpty() == false)
	        {
		        for (AnnotationFS REF1 : CasUtil.select(cas, cas.getTypeSystem().getType(REF1_TYPE))) 
		        {
		        	bufferedWriter.write("REF,");
		        	bufferedWriter.write(String.valueOf(REF1.getBegin()));
		        	bufferedWriter.write(",");
		        	bufferedWriter.write(String.valueOf(REF1.getEnd()));
		        	bufferedWriter.write(",");
					bufferedWriter.write("\"");
					bufferedWriter.write(REF1.getCoveredText());
					bufferedWriter.write("\",");
					bufferedWriter.write("\"");
					bufferedWriter.write(String.valueOf(counter)+ "_"+String.valueOf( filesInDir[i]) );
					bufferedWriter.write("\"");
					bufferedWriter.write("\r\n");
					counter++;
		        }
	        }
	        bufferedWriter.close(); // close the output file writer
	        br_file.close(); // close the input file reader as well
	        cas.reset(); // reset the cas for next file
	        counter=0;
	    }
	    LocalDateTime now1 = LocalDateTime.now();  
    	System.out.println(dtf.format(now1));  
    } 
}
// java -cp RefExtractor1_lib -jar RefExtractor1.jar inputfolder
// java -cp C:\Users\Rakesh\Documents\RAKESH\MAGDEBURG\LegalHorizon\DBSE\RefExtractor1_lib -jar RefExtractor1.jar C:\Users\Rakesh\Documents\RAKESH\MAGDEBURG\LegalHorizon\DBSE