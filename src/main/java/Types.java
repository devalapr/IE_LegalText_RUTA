

import java.io.File;
import java.io.FileOutputStream;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.commons.io.IOUtils;
import org.apache.uima.resource.metadata.TypeSystemDescription;
/*
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import java.io.IOException;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.ruta.engine.RutaEngine;
import org.apache.uima.ruta.engine.XMIWriter;
import org.xml.sax.SAXException;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
*/
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;


public class Types {
	public static void main(String[] args) throws Exception {

		
		AnalysisEngineDescription ner = createEngineDescription(StanfordNamedEntityRecognizer.class);
		TypeSystemDescription cmTypeSystem1 = ner.getAnalysisEngineMetaData().getTypeSystem();
		FileOutputStream os = new FileOutputStream(new File("descriptor/Stanfordner.xml"));
		cmTypeSystem1.toXML(os);
		IOUtils.closeQuietly(os);
		
		AnalysisEngineDescription tagger = createEngineDescription(StanfordPosTagger.class, 
				StanfordPosTagger.PARAM_VARIANT, "C:/Users/alish/.m2/repository/de/tudarmstadt/ukp/dkpro/core/de.tudarmstadt.ukp.dkpro.core.stanfordnlp-model-tagger-de-hgc/20140827.1"
				);
		
		TypeSystemDescription cmTypeSystem = tagger.getAnalysisEngineMetaData().getTypeSystem();
		FileOutputStream os1 = new FileOutputStream(new File("descriptor/Stanfordtagger1.xml"));
		cmTypeSystem.toXML(os1);
		IOUtils.closeQuietly(os1);
	
	}
	/* main() */

}
