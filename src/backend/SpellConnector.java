package backend;

public class SpellConnector extends SandSConnector{
	public static String SPELL_TABLE_NAME = "spells";
	
	public SpellConnector(){
		super();
		table_name = SPELL_TABLE_NAME;
	}
}
