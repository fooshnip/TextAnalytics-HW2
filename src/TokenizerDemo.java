import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.*; 

import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.Morphology;
import edu.stanford.nlp.process.PTBTokenizer;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.*; 
import edu.stanford.nlp.ling.CoreAnnotations.*; 

public class TokenizerDemo {

	public static void main(String[] args) throws IOException {
		StanfordToken("wsj_0063.txt","pa2_tokens_wsj0063_nlp.txt");
		LuceneToken("wsj_0063.txt","pa2_tokens_wsj0063_lucene.txt");
		LucenePorter("wsj_0063.txt","luceneporter.txt");
		LuceneKStem("wsj_0063.txt","lucenekstem.txt");
		LuceneEngAnalyzer("wsj_0063.txt","luceneengstem.txt");
		ClassBios("classbios.txt");
	}
	public static void LuceneToken(String readin, String printout) throws IOException {
		FileReader fr = new FileReader(readin);
		FileWriter fw = new FileWriter(printout);
		String in = new Scanner(fr).useDelimiter("\\Z").next();
		String[] splitted = in.split("\\n");
		//System.out.println(splitted.length);
		//FileWriter fw = new FileWriter(printout);
		Analyzer tk = new StandardAnalyzer(Version.LUCENE_45);
		//ArrayList<String> result = new ArrayList<String>();
		fw.write("Joseph Riley\n\n");
		fw.write("Printing tokens for Lucene...\n\n");
		for(int i=0;i<splitted.length;i++){
			try {
				TokenStream stream  = tk.tokenStream(null, new StringReader(splitted[i]));
				stream.reset();
				while (stream.incrementToken()) {
					//result.add(stream.getAttribute(CharTermAttribute.class).toString());
					fw.write(""+stream.getAttribute(CharTermAttribute.class).toString()+" ");
				}
			} catch (IOException e) {
				// not thrown b/c we're using a string reader...
				throw new RuntimeException(e);
			}
			fw.write("\n");
		}
		fw.close();
	}

	@SuppressWarnings("unchecked")
	public static void StanfordToken(String readin, String printout) throws IOException {
		/*
		 * This method will use the stanford tokenizer to generate tokens by sentence.
		 */
		FileReader fr = new FileReader(readin);
		FileWriter fw = new FileWriter(printout);
		String in = new Scanner(fr).useDelimiter("\\Z").next();
		String[] splitted = in.split("\\n");
		//DocumentPreprocessor dp = new DocumentPreprocessor(fr);
		fw.write("Joseph Riley\n\n");
		fw.write("Printing tokens by sentence...\n\n");
		for(int i=0;i<splitted.length;i++){
			PTBTokenizer ptbt = new PTBTokenizer(new StringReader(splitted[i]),
					new CoreLabelTokenFactory(), "");
			for (CoreLabel label; ptbt.hasNext(); ) {
				label = (CoreLabel) ptbt.next();
				fw.write(label+" ");
			}
			fw.write("\n");
		}
		fw.close();
	}


	public static void LucenePorter(String readin, String printout) throws IOException {
		FileReader fr = new FileReader(readin);
		FileWriter fw = new FileWriter(printout);
		String in = new Scanner(fr).useDelimiter("\\Z").next();
		String[] splitted = in.split("\\n");
		fw.write("Joseph Riley\n\n");
		fw.write("Printing Porter Stems by sentence...\n\n");
		Analyzer tk = new StandardAnalyzer(Version.LUCENE_45);
		for(int i=0;i<splitted.length;i++){
			Tokenizer tokenizer = new LowerCaseTokenizer(Version.LUCENE_45,new StringReader(splitted[i]));
		    TokenStream stream = new PorterStemFilter(tokenizer);
			stream.reset();
			while (stream.incrementToken()) {
				//result.add(stream.getAttribute(CharTermAttribute.class).toString());
				fw.write(""+stream.getAttribute(CharTermAttribute.class).toString()+" ");
			}
			fw.write("\n");
		}
		fw.close();
	}
	
	
	public static void LuceneKStem(String readin, String printout) throws IOException {
		FileReader fr = new FileReader(readin);
		FileWriter fw = new FileWriter(printout);
		String in = new Scanner(fr).useDelimiter("\\Z").next();
		String[] splitted = in.split("\\n");
		fw.write("Joseph Riley\n\n");
		fw.write("Printing KStems by sentence...\n\n");
		Analyzer tk = new StandardAnalyzer(Version.LUCENE_45);
		for(int i=0;i<splitted.length;i++){
			Tokenizer tokenizer = new LowerCaseTokenizer(Version.LUCENE_45,new StringReader(splitted[i]));
		    TokenStream stream = new KStemFilter(tokenizer);
			stream.reset();
			while (stream.incrementToken()) {
				//result.add(stream.getAttribute(CharTermAttribute.class).toString());
				fw.write(""+stream.getAttribute(CharTermAttribute.class).toString()+" ");
			}
			fw.write("\n");
		}
		fw.close();
	}
	
	
	public static void LuceneEngAnalyzer(String readin, String printout) throws IOException {
		FileReader fr = new FileReader(readin);
		FileWriter fw = new FileWriter(printout);
		String in = new Scanner(fr).useDelimiter("\\Z").next();
		String[] splitted = in.split("\\n");
		//System.out.println(splitted.length);
		//FileWriter fw = new FileWriter(printout);
		Analyzer tk = new EnglishAnalyzer(Version.LUCENE_45);
		//ArrayList<String> result = new ArrayList<String>();
		fw.write("Joseph Riley\n\n");
		fw.write("Printing English Analyzer stemming for Lucene...\n\n");
		for(int i=0;i<splitted.length;i++){
			try {
				TokenStream stream  = tk.tokenStream(null, new StringReader(splitted[i]));
				stream.reset();
				while (stream.incrementToken()) {
					//result.add(stream.getAttribute(CharTermAttribute.class).toString());
					fw.write(""+stream.getAttribute(CharTermAttribute.class).toString()+" ");
				}
			} catch (IOException e) {
				// not thrown b/c we're using a string reader...
				throw new RuntimeException(e);
			}
			fw.write("\n");
		}
		fw.close();
	}
	
	//WORK IN PROGRESS
	public static void ClassBios(String readin) throws IOException {
		FileReader fr = new FileReader(readin);
		String in = new Scanner(fr).useDelimiter("\\Z").next();
		//in = in.replaceAll("\\p{Punct}+|â€™|â", " ");
		String[] splitted = in.split("([0-9][0-9]\\s-+)|(---+)");
		//System.out.println(splitted[0]);
		
		for(int i=1;i<splitted.length-1;i++){
			FileWriter fw = new FileWriter("classbiosfile"+i+".txt");
			Tokenizer tokenizer = new LowerCaseTokenizer(Version.LUCENE_45,new StringReader(splitted[i].replaceAll("\\p{Punct}+|â€™|â", " ")));
			TokenStream stream = new StopFilter(Version.LUCENE_45, tokenizer, StandardAnalyzer.STOP_WORDS_SET); 
		    stream = new KStemFilter(stream);
			stream.reset();
			while (stream.incrementToken()) {
				//result.add(stream.getAttribute(CharTermAttribute.class).toString());
				fw.write(""+stream.getAttribute(CharTermAttribute.class).toString()+" ");
			}
			fw.close();
		}
	}
	

}
