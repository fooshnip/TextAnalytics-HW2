import java.util.Scanner;
import java.io.*;
import static java.lang.System.*;
import java.util.regex.*;

public class TimeRegEx {

	/**
	 * Joseph Riley
	 * 
	 * This program will use regex to search for temporal patterns in a loaded text document.
	 */
	public static void main(String[] args) {
		
		try
		{
			FileReader fr = new FileReader("classbios.txt");
			FileWriter fw = new FileWriter("classbios_timepoints.txt");
			String str = new Scanner(fr).useDelimiter("\\Z").next();
			str = str.replace("\n", " ").replace("\r", " ");//remove spacing issues
			
			//Sentence tokenizing start
			String sentStart = "[A-Z]";
			String nonPunc = "[^.!?]*";
			String sentEnd = "[.!?]";
			//Sentence patterns end
			
			//Temporal patterns start
			String month = "((January|February|March|April|May|June|July|August|September|October|November|December)(\\b))";
			String monthshort = "((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sept|Oct|Nov|Dec)(\\b))";
			String descriptive = "(((|n)ext|(|l)ast|(|t)his|previous)\\s(year|quarter|month)(\\b))";
			String yearnum = "(((|i)n|(|d)uring|(|o)ver|(|b)y)\\s((19)|(20))([0-9]{2}))";
			String seasons = "(((|i)n|(|d)uring|(|o)ver|(|b)y)\\s(the)\\s((S|s)ummer|(W|w)inter|(F|f)all|(S|s)pring))";
			String descSeasons = "(((|n)ext|(|l)ast|(|t)his|previous)\\s((S|s)ummer|(W|w)inter|(F|f)all|(S|s)pring))";
			//Temporal patterns end
			
			
			int i = 0;
			Pattern p = Pattern.compile(sentStart+
					nonPunc+"("+
					month+"|"+
					monthshort+"|"+
					seasons+"|"+
					yearnum+"|"+
					descriptive+"|"+
					descSeasons+")"+
					nonPunc+
					sentEnd);
			
			//start output to file
			fw.write("Joseph Riley\n\n");
			fw.write("Printing Regex pattern...\n\n");
			fw.write(sentStart+nonPunc+"("+month+"|"+monthshort+"|"+seasons+"|"+yearnum+"|"+descriptive+"|"+descSeasons+")"+nonPunc+sentEnd);
			fw.write("\n\n");
			fw.write("Printing positive matches...\n\n");
			Matcher m = p.matcher(str);
			while (m.find()){
				i++;
				fw.write(i+":   "+str.substring(m.start(),m.end())+"\n");
			}
			fw.close();//end file output

		}
		catch (IOException e){
			out.println("File not found.");
		}

	}//end main

}//end TimeRegEx
