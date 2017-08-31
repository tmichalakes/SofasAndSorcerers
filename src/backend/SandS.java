package backend;

import java.util.ArrayList;

public class SandS {
	public static final String APP_TITLE = "Sofas and Sorcerers";
	
	public static void main(String [] args){
		System.out.println(APP_TITLE);
		
		String path = SpellXMLParser.DEFAULT_PATH;
		String fname = SpellXMLParser.DEFAULT_FNAME;
		
		if(args.length == 3){
			path = args[1];
			fname = args[2];
		}
		
		SpellXMLParser parse = new SpellXMLParser(path, fname);
		SpellConnector conn  = new SpellConnector(true);
		conn.debug = true;
		
		parse.ReadFile();
		ArrayList<Spell> spells = parse.SpellList();
		conn.ConnectToDB();
		conn.AddSpells(spells);
	}
}
