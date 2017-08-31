package backend;

import java.sql.SQLException;
import java.util.ArrayList;

public class SpellConnector extends SandSConnector{
	public static String SPELL_TABLE_NAME = "spells";
	public static String ADD_SPELL = "INSERT INTO spells (spell_name, school, ctime, v_comp, s_comp, m_comp, concentration, materials, spell_range, duration, description) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	public SpellConnector(){
		super();
		table_name = SPELL_TABLE_NAME;
	}

	public SpellConnector(boolean use_defaults){
		super(use_defaults);
		table_name = SPELL_TABLE_NAME;
	}
	
	public void AddSpell(Spell s){
		try {
			stmt = conn.prepareStatement(ADD_SPELL);
			stmt.setString(1, s.name);
			stmt.setString(2, s.school);
			stmt.setString(3, s.ctime);
			stmt.setString(4, (s.components & Spell.VERBAL) != 0 ? "true" : "false");
			stmt.setString(5, (s.components & Spell.SOMATIC) != 0 ? "true" : "false");
			stmt.setString(6, (s.components & Spell.MATERIAL) != 0 ? "true" : "false");
			stmt.setString(7, "" + s.concentration);
			stmt.setString(8, (s.materials == null) ? "" : s.materials);
			stmt.setString(9, s.range);
			stmt.setString(10, s.duration);
			stmt.setString(11, s.description);
			
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Everything is poop forever");
			e.printStackTrace();
		}
	}
	
	public void AddSpells(ArrayList<Spell> spells){
		for (Spell s:spells){
			AddSpell(s);
		}
	}
}
