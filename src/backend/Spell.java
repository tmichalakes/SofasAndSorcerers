package backend;

/**
 * Class Spell
 * @author Ted
 * 
 * 
 * Data container for D&D 5e spells.
 * Some values are stored as integeres. These values
 * are private to the spell and have to use interface
 * methods to change.
 */
public class Spell {
	public String name;
	public String school;
	private int level;
	public String ctime;
	public String range;
	private int components;
	public String materials;
	public String duration;
	public String description;
	
	public static final int VERBAL = 4;
	public static final int SOMATIC = 2;
	public static final int MATERIAL = 1;
	
	public Spell(){
		initialize();
	}
	
	private void initialize(){
		name = "";
		school = "";
		level = 0;
		ctime = "";
		range = "";
		components = 0;
		materials = null;
		duration = "";
		description = "";
	}
	
	public void Print(){
		System.out.println("Spell: " + name);
		System.out.println("Level: " + level);
		System.out.println("School: " + school);
		System.out.println("Casting Time: " + ctime);
		System.out.println("Range: " + range);
		System.out.println("Components: " + Components());
		Materials();
		System.out.println("Duration: " + duration);
		System.out.println("----------------------");
		System.out.println(description);
		System.out.println("");
	}
	
	public void SetComponents(String s){
		try{
			components = Integer.parseInt(s);
		}catch(NumberFormatException e){
			components = 0;
		}
	}
	
	public void SetLevel(String s){
		try{
			level = Integer.parseInt(s);
		}catch(NumberFormatException e){
			level = 0;
		}
	}
	
	public final int Level(){
		return level;
	}
	
	public String Components(){
		String comp = "";
		if ((components & VERBAL) != 0){
			comp = comp + " Verbal ";
		}
		if ((components & SOMATIC) != 0){
			comp = comp + " Somatic ";
		}
		if ((components & MATERIAL) != 0){
			comp = comp + " Material ";
		}
		return comp;
	}
	
	public void Materials(){
		if (materials != null){
			System.out.println("Materials: " + materials);
		}
	}
}
