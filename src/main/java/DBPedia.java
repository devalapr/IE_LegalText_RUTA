//import java.awt.List;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class DBPedia {

	public static void main(String[] args) {
		try {
			//get the relative path for the directory
			Path currentRelativePath = Paths.get("");
			String s = currentRelativePath.toAbsolutePath().toString();
			//set the absolute path to the input folder of the project to read the text file
			File fInput = new File(s + "/input/2_Geschäftsbeziehung_und_Bankvertrag_raw.txt");
			BufferedReader br_file = new BufferedReader(new FileReader(fInput));
			//read the data from the file to a string
			String st;
			String text = "";
			while ((st = br_file.readLine()) != null) {
				text += st;
			}
		br_file.close(); // close the buffered reader
		//have to split the text file into parts as there is a limit on the size of the url 
		//hence we are splitting the file in to parts of acceptable url length and passing the data	
		double strParts= Math.ceil((text.length())/6274);
		int len=0;
		String finText="";
		for(int i=0;i<strParts;i++)
		{
			//build the url with the text by encoding text string read from the file
			
			String sPart=text.substring(len, len+6274);
			len=len+6274;
			String stringUrl = "http://model.dbpedia-spotlight.org/de/annotate?text=" + URLEncoder.encode(sPart, "UTF-8")+"&confidence=0.9&support=10";
			URL url = new URL(stringUrl);
			URLConnection uc = url.openConnection();

			uc.setRequestProperty("X-Requested-With", "Curl");
			uc.setRequestProperty("accept", "application/json");

			BufferedReader br = new BufferedReader(new InputStreamReader((uc.getInputStream())));

			String output;
			String parseOutput = "";
			//get the output from the api and append it to a string
			while ((output = br.readLine()) != null) {
				parseOutput += output;
			}

			//parse the data in to an object using json parser
			JSONParser parser = new JSONParser();
			Object obj = new Object();
			
			try {
				obj = parser.parse(parseOutput);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//fetch the resource part from the json object in to a jsonarray
			JSONObject obj2 = (JSONObject) obj;
			JSONArray jArray = (JSONArray) obj2.get("Resources");
			String jString = "";
			
			//build a string with the text and its url
			for (int j = 0; j < jArray.size(); j++) {
				JSONObject jobj = (JSONObject) jArray.get(j);
				jString += jobj.get("@surfaceForm");
				jString += ";";
				jString += jobj.get("@URI");
				jString += ";";

				jString += jobj.get("@similarityScore");
				jString += ";";
				
				jString += jobj.get("@support");
				jString += ";";
				
				jString += jobj.get("@offset");
				jString += ";";
				
				jString += jobj.get("@types");
				
				
				
				jString += "\n";
				
			}
			finText=finText+jString;
		}
			
			
			System.out.println(finText);
			
			
						
			//writes the data to a output file.
			File fOutput = new File(s + "/resources/dboutput.txt");
			if (!fOutput.exists()) {
				fOutput.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(fOutput));
			writer.write(finText);
			writer.close();
			
			File fOutput1 = new File(s + "/src/main/resources/dboutput.txt");
			if (!fOutput1.exists()) {
				fOutput1.createNewFile();
			}
			BufferedWriter writer1 = new BufferedWriter(new FileWriter(fOutput1));
			writer1.write(finText);
			writer1.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
