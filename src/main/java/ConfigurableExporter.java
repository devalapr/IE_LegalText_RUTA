import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.CasUtil;


public class ConfigurableExporter {

	public static final String CHAPTER_TYPE = "Main.Chapter";
	public static final String PART_TYPE = "Main.Part";
	public static final String SUBCHAPTER_TYPE = "Main.SubChapter";
	public static final String SUBSUBCHAPTER_TYPE = "Main.SubSubChapter";
	public static final String GESETZ_TYPE = "Main.Gesetz";
	public static final String REF1_TYPE = "Main.REF1";
	public static final String RFC_NE1_TYPE = "Main.RFC_NE1";
	public static final String RFC_NE2_TYPE = "Main.RFC_NE2";
	public static final String RFC_NE3_TYPE = "Main.RFC_NE3";
	public static final String RFC_NE4_TYPE = "Main.RFC_NE4";
	public static final String DBPedia_TYPE = "Main.DBPedia";
    public static final String VERORDNUNG_TYPE = "Main.Verordnung";
    public static final String RICHTLINIE_TYPE = "Main.Richtlinie";
    public static final String EXPLREF = "Main.explicitReference2";

    
    /**     MAIN FUNCTION HERE */
    public static void main(String[] args) throws Exception {
    	
        final AnalysisEngine engine =
                AnalysisEngineFactory.createEngine("MainEngine");
        
        final CAS cas = engine.newCAS();
        
        Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();

		File inputDir = new File(s + "/input");
		
		// get a listing of all files in the directory
	    String[] filesInDir = inputDir.list();

	    // sort the list of files (optional)
	    // Arrays.sort(filesInDir);

	    // have everything i need, just print it now
	    for ( int i=0; i<filesInDir.length; i++ )
	    {
		      System.out.println( "file: " + filesInDir[i] );
		      File fInput = new File(s + "/input/" + filesInDir[i]);
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
		      String fileName = "output/toc_" + filesInDir[i];
		      FileWriter fileWriter = new FileWriter(fileName);
			
		      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	      
	        for (AnnotationFS Chapter : CasUtil.select(cas, cas.getTypeSystem().getType(CHAPTER_TYPE))) 
	        {
	        	bufferedWriter.write("Chapter: ");
				bufferedWriter.write("\"");
				bufferedWriter.write(Chapter.getCoveredText());
				bufferedWriter.write("\", ");
				bufferedWriter.write("\r\n");
	        }
	        
	        for (AnnotationFS SubChapter : CasUtil.select(cas, cas.getTypeSystem().getType(SUBCHAPTER_TYPE))) 
	        {
	        	bufferedWriter.write("SubChapter: ");
				bufferedWriter.write("\"");
				bufferedWriter.write(SubChapter.getCoveredText());
				bufferedWriter.write("\", ");
				bufferedWriter.write("\r\n");
			}
	        for (AnnotationFS SubSubChapter : CasUtil.select(cas, cas.getTypeSystem().getType(SUBSUBCHAPTER_TYPE))) 
	        {
	        	bufferedWriter.write("SubSubChapter: ");
				bufferedWriter.write("\"");
				bufferedWriter.write(SubSubChapter.getCoveredText());
				bufferedWriter.write("\", ");
				bufferedWriter.write("\r\n");
			}
	        for (AnnotationFS Part : CasUtil.select(cas, cas.getTypeSystem().getType(PART_TYPE))) 
	        {
	        	bufferedWriter.write("Part: ");
				bufferedWriter.write("\"");
				bufferedWriter.write(Part.getCoveredText());
				bufferedWriter.write("\", ");
				bufferedWriter.write("\r\n");
			}
	        for (AnnotationFS Gesetz : CasUtil.select(cas, cas.getTypeSystem().getType(GESETZ_TYPE))) 
	        {
	        	bufferedWriter.write("GESETZ: ");
				bufferedWriter.write("\"");
				bufferedWriter.write(Gesetz.getCoveredText());
				bufferedWriter.write("\", ");
				bufferedWriter.write("\r\n");
			}
	        for (AnnotationFS REF1 : CasUtil.select(cas, cas.getTypeSystem().getType(REF1_TYPE))) 
	        {
	        	bufferedWriter.write("REF: ");
				bufferedWriter.write("\"");
				bufferedWriter.write(REF1.getCoveredText());
				bufferedWriter.write("\", ");
				bufferedWriter.write("\r\n");
			}
	        for (AnnotationFS RFC_NE1 : CasUtil.select(cas, cas.getTypeSystem().getType(RFC_NE1_TYPE))) 
	        {
	        	bufferedWriter.write("RFC_NE(posPatterNeDef2): ");
				bufferedWriter.write("\"");
				bufferedWriter.write(RFC_NE1.getCoveredText());
				bufferedWriter.write("\", ");
				bufferedWriter.write("\r\n");
			}
	        for (AnnotationFS RFC_NE2 : CasUtil.select(cas, cas.getTypeSystem().getType(RFC_NE2_TYPE))) 
	        {
	        	bufferedWriter.write("RFC_NE(patternAufz): ");
				bufferedWriter.write("\"");
				bufferedWriter.write(RFC_NE2.getCoveredText());
				bufferedWriter.write("\", ");
				bufferedWriter.write("\r\n");
			}
	        for (AnnotationFS RFC_NE3 : CasUtil.select(cas, cas.getTypeSystem().getType(RFC_NE3_TYPE))) 
	        {
	        	bufferedWriter.write("RFC_NE(patternNE): ");
				bufferedWriter.write("\"");
				bufferedWriter.write(RFC_NE3.getCoveredText());
				bufferedWriter.write("\", ");
				bufferedWriter.write("\r\n");
			}
	        for (AnnotationFS RFC_NE4 : CasUtil.select(cas, cas.getTypeSystem().getType(RFC_NE4_TYPE))) 
	        {
	        	bufferedWriter.write("RFC_NE(patternwithbrackets): ");
				bufferedWriter.write("\"");
				bufferedWriter.write(RFC_NE4.getCoveredText());
				bufferedWriter.write("\", ");
				bufferedWriter.write("\r\n");
			}
	       /* Commented below lines to avoid the JAVA NULL POINTER EXCEPTION in CAS
	        * Occurs when there is no annotation with the tag name.
	        for (AnnotationFS Rich : CasUtil.select(cas, cas.getTypeSystem().getType(RICHTLINIE_TYPE))) 
            {
                bufferedWriter.write("EXPLREF: ");
                bufferedWriter.write("\"");
                bufferedWriter.write(Rich.getCoveredText());
                bufferedWriter.write("\", ");
                bufferedWriter.write("\r\n");
            }*/

	        bufferedWriter.close(); // close the output file writer
	        br_file.close(); // close the input file reader as well
	        cas.reset(); // reset the cas for next file
	    }  
    }
}
// To convert all pdf files in a folder to txt files. Open Gitbash or powershell and 
// for file in *.pdf; do pdftotext "$file" "$file.txt"; done