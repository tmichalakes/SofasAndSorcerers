package backend;

public class SandS {
	public static void main(String [] args){
		String path = SpellXMLParser.DEFAULT_PATH;
		String fname = SpellXMLParser.DEFAULT_FNAME;
		
		if(args.length == 3){
			path = args[1];
			fname = args[2];
		}
		
		SpellXMLParser parse = new SpellXMLParser(path, fname);
	}
}
