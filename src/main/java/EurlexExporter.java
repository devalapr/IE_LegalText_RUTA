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

public class EurlexExporter {
	public static final String REGULATION_TYPE = "EurLex.Regulation"; // Regulation
	public static final String DIRECTIVE_TYPE = "EurLex.Directive"; // Directive
	public static final String ARTICLE_TYPE = "EurLex.Article"; // Article
	// public static final String TOC_TYPE = "Main.toc";
	public static int start = 0;

	/** MAIN FUNCTION HERE */
	public static void main(String[] args) throws Exception {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		System.out.println(args[0]);
		System.out.println(args[1]);
		// final AnalysisEngine engine =
		// AnalysisEngineFactory.createEngine("C:\\Users\\Rakesh\\Documents\\RAKESH\\MAGDEBURG\\LegalHorizon\\DBSE\\descriptor\\MainEngine");
		final AnalysisEngine engine = AnalysisEngineFactory.createEngine("EurLexEngine");

		final CAS cas = engine.newCAS();
		File inputDir = new File(args[0]);

		// get a listing of all files in the directory
		String[] filesInDir = inputDir.list();
		for (int i = 0; i < filesInDir.length; i++) {
			int counter = 0;

			File fInput = new File(args[0] + "/" + filesInDir[i]);
			BufferedReader br_file = new BufferedReader(new FileReader(fInput));

			String st;
			String text = "";
			while ((st = br_file.readLine()) != null) {
				text += st;
			}
			cas.setDocumentText(text); // read from file - Still unable to set cas.setDocument to directly pass the
										// file
			engine.process(cas);

			// Writing to output file
			// String fileName = args[0] + "/output/" + filesInDir[i];
			String fileName = args[1] + "/" + filesInDir[i];
			FileWriter fileWriter = new FileWriter(fileName);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			if (CasUtil.select(cas, cas.getTypeSystem().getType(REGULATION_TYPE)).isEmpty() == false) {
				for (AnnotationFS Regulation : CasUtil.select(cas, cas.getTypeSystem().getType(REGULATION_TYPE))) {
					bufferedWriter.write("Regulation,");
					bufferedWriter.write(String.valueOf(Regulation.getBegin()));
					bufferedWriter.write(",");
					bufferedWriter.write(String.valueOf(Regulation.getEnd()));
					bufferedWriter.write(",");
					bufferedWriter.write("\"");
					bufferedWriter.write(Regulation.getCoveredText());
					bufferedWriter.write("\",");
					bufferedWriter.write("\"");
					bufferedWriter.write(String.valueOf(counter) + "_" + String.valueOf(filesInDir[i]));
					bufferedWriter.write("\"");
					bufferedWriter.write("\r\n");
					counter++;
				}
			}
			if (CasUtil.select(cas, cas.getTypeSystem().getType(DIRECTIVE_TYPE)).isEmpty() == false) {
				for (AnnotationFS Directive : CasUtil.select(cas, cas.getTypeSystem().getType(DIRECTIVE_TYPE))) {

					bufferedWriter.write("Directive,");
					bufferedWriter.write(String.valueOf(Directive.getBegin()));
					bufferedWriter.write(",");
					bufferedWriter.write(String.valueOf(Directive.getEnd()));
					bufferedWriter.write(",");
					bufferedWriter.write("\"");
					bufferedWriter.write(Directive.getCoveredText());
					bufferedWriter.write("\",");
					bufferedWriter.write("\"");
					bufferedWriter.write(String.valueOf(counter) + "_" + String.valueOf(filesInDir[i]));
					bufferedWriter.write("\"");
					bufferedWriter.write("\r\n");
					counter++;
				}
			}

			if (CasUtil.select(cas, cas.getTypeSystem().getType(ARTICLE_TYPE)).isEmpty() == false) {
				for (AnnotationFS Articles : CasUtil.select(cas, cas.getTypeSystem().getType(ARTICLE_TYPE))) {
					bufferedWriter.write("Article,");
					bufferedWriter.write(String.valueOf(Articles.getBegin()));
					bufferedWriter.write(",");
					bufferedWriter.write(String.valueOf(Articles.getEnd()));
					bufferedWriter.write(",");
					bufferedWriter.write("\"");
					bufferedWriter.write(Articles.getCoveredText());
					bufferedWriter.write("\",");
					bufferedWriter.write("\"");
					bufferedWriter.write(String.valueOf(counter) + "_" + String.valueOf(filesInDir[i]));
					bufferedWriter.write("\"");
					bufferedWriter.write("\r\n");
					counter++;
				}
			}
			bufferedWriter.close(); // close the output file writer
			br_file.close(); // close the input file reader as well
			cas.reset(); // reset the cas for next file
			counter = 0;
		}
		LocalDateTime now1 = LocalDateTime.now();
		System.out.println(dtf.format(now1));
	}
}
