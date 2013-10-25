import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.*; 

import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.Morphology;
import edu.stanford.nlp.process.PTBTokenizer;

import org.apache.lucene.analysis.*;
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
	  //StanfordLemma("wsj_0063.txt","stanlemma.txt");
  }
  public static void LuceneToken(String readin, String printout) throws IOException {
	  FileReader fr = new FileReader(readin);
	  FileWriter fw = new FileWriter(printout);
	  String in = new Scanner(fr).useDelimiter("\\Z").next();
	  String[] splitted = in.split("\\n");
	  System.out.println(splitted.length);
	  //FileWriter fw = new FileWriter(printout);
	  Analyzer tk = new StandardAnalyzer(Version.LUCENE_45);
	  ArrayList<String> result = new ArrayList<String>();
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


public static void StanfordLemma(String readin, String printout) throws IOException {
//Properties props = new Properties(); 
//props.put("annotators", "tokenize, ssplit, pos, lemma"); 
//StanfordCoreNLP pipeline = new StanfordCoreNLP(props, false);
FileReader fr = new FileReader(readin);
FileWriter fw = new FileWriter(printout);
String str = new Scanner(fr).useDelimiter("\\Z").next();
Morphology morph = new Morphology(fr);
fw.write("Joseph Riley\n\n");
fw.write("Printing tokens by sentence...\n\n");
//System.out.println(morph.next());

fw.write("hello"+ morph);
//for(CoreMap sentence: document.get(SentencesAnnotation.class)) {    
//  for(CoreLabel token: sentence.get(TokensAnnotation.class)) {       
//  String word = token.get(TextAnnotation.class);      
//  String lemma = token.get(LemmaAnnotation.class); 
//  fw.write("lemmatized version :" + lemma);
//  } 
// } 
fw.close();
}
}
