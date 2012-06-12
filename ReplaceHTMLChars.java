
public class ReplaceHTMLChars {
	public static String decodeHtml(String line){
		
		// Simple decode html class. Extend for further use. Only used for זרו not special characters
		// &AAOG  = ז
		// &AAOF  = ו
		// &AAPI = ר
		line = line.replaceAll("&AAOG", "ז");
		line = line.replaceAll("&AAOF", "ו");
		line = line.replaceAll("&AAPI", "ר");
		
		return line;
	}
}
