package backend;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SpellXMLParser extends SandSXML{
	///Default variables
	public static final String DEFAULT_FNAME = "SpellDescriptions.xml";
	public static final String DEFAULT_PATH  = SandSXML.default_path;
	
	///Tag variables for hash table
	public static final String ROOT_TAG   = "all_spells";
	public static final String SPELL_TAG  = "spell";
	public static final String SCHOOL_TAG = "school";
	public static final String CTIME_TAG  = "casting_time";
	public static final String LEVEL_TAG  = "level";
	public static final String RANGE_TAG  = "range";
	public static final String COMP_TAG   = "components";
	public static final String MATR_TAG   = "materials";
	public static final String DUR_TAG    = "duration";
	public static final String DESC_TAG	  = "description";
	public static final String CONC_TAG   = "concentration";
	
	private ArrayList<Spell> SpellList;
	private Spell current;
	private String last_val;
	
	public SpellXMLParser(String path, String fname){
		super(path, fname);
		InitializeTagMap();
		SpellList = new ArrayList<Spell>();
	}
	
	/**
	 * InitializeTagMap
	 * 
	 * Performs the associations with tag names and
	 * handlers as described below.
	 */
	protected void InitializeTagMap(){
		TagMap.put(ROOT_TAG, new RootHandler());
		TagMap.put(SPELL_TAG, new SpellHandler());
		TagMap.put(SCHOOL_TAG, new SchoolHandler());
		TagMap.put(CTIME_TAG, new CTimeHandler());
		TagMap.put(LEVEL_TAG, new LevelHandler());
		TagMap.put(RANGE_TAG, new RangeHandler());
		TagMap.put(COMP_TAG, new ComponentsHandler());
		TagMap.put(MATR_TAG, new MaterialsHandler());
		TagMap.put(DUR_TAG, new DurationHandler());
		TagMap.put(DESC_TAG, new DescriptionHandler());
		TagMap.put(CONC_TAG, new ConcentrationHandler());
		///break tag and text tag defined in parent.
		TagMap.put(TEXT_TAG, new TextHandler());
		TagMap.put(BREAK_TAG, new BreakHandler());
		INDENT_LEVEL = 0;
	}
	
	@Override
	public void ReadFile() {
		Node root = GetRoot();
		XMLNodeTypeHandler handler = TagMap.get(root.getNodeName());
		handler.HandleNode(root);
	}
	
	public ArrayList<Spell> SpellList() {
		// TODO Auto-generated method stub
		return SpellList;
	}
	
	public String GetSpellName(Node n){
		NamedNodeMap attr = n.getAttributes();
		Node s = attr.getNamedItem("name");
		//System.out.print("Spell has name: ");
		//System.out.println(s.getNodeValue());
		if (s == null) return "SPELL";
		return s.getNodeValue();
	}
	/////////////////////////////////////////////////////////////
	/////////////////////// HANDLERS ////////////////////////////
	/////////////////////////////////////////////////////////////
	
	/*
	 * GetNodeHandler -> Returns the appropriate type of node
	 * 	handler for the specified node.
	 * String s -> Name of the node tag to be handled.
	 * */
	public XMLNodeTypeHandler GetNodeHandler(String s){
		XMLNodeTypeHandler n = TagMap.get(s);
		if (n == null) return new GenericNodeHandler();
		return n;
	}
	
	///Root node -> outermost closed set of brackets.
	protected class RootHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			if (n == null) return;
			PrettyPrint(n.getNodeName());
			INDENT_LEVEL++;
			Node c = n.getFirstChild();
			while (c != null){
				XMLNodeTypeHandler handler = GetNodeHandler(c.getNodeName());
				handler.HandleNode(c);
				c = c.getNextSibling();
			}
			INDENT_LEVEL--;
		}
	}
	
	///Spell node -> Node with tag spell, attribute "name"
	protected class SpellHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			PrettyPrint(n.getNodeName());
			INDENT_LEVEL++;
			current = new Spell();
			current.name = GetSpellName(n);
			Node c = n.getFirstChild();
			while (c != null){
				XMLNodeTypeHandler handler = GetNodeHandler(c.getNodeName());
				handler.HandleNode(c);
				c = c.getNextSibling();
			}
			INDENT_LEVEL--;
			SpellList.add(current);
		}
	}
	
	/*
	 * School node should have only one child, the value of the school.
	 * It should only be a text node.
	 * */
	protected class SchoolHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			
			INDENT_LEVEL++;
			Node c = n.getFirstChild();
			XMLNodeTypeHandler handler = GetNodeHandler(c.getNodeName());
			handler.HandleNode(c);
			current.school = last_val;
			INDENT_LEVEL--;
			PrettyPrint(n.getNodeName() + ":" + last_val);
		}
	}
	
	/**
	 * Level handler retrieves spell level from the XML doc.
	 * Should work in the same fashion as school handler,
	 * e.g. only one child which is a text node.
	 * */
	protected class LevelHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			PrettyPrint(n.getNodeName());
			INDENT_LEVEL++;
			Node c = n.getFirstChild();
			XMLNodeTypeHandler handler = GetNodeHandler(c.getNodeName());
			handler.HandleNode(c);
			current.SetLevel(last_val);
			INDENT_LEVEL--;
		}
	}
	
	/*
	 * CTime handler has one child, a text node.
	 * */
	protected class CTimeHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			PrettyPrint(n.getNodeName());
			INDENT_LEVEL++;
			Node c = n.getFirstChild();
			XMLNodeTypeHandler handler = GetNodeHandler(c.getNodeName());
			handler.HandleNode(c);
			current.ctime = last_val;
			INDENT_LEVEL--;
		}
	}
	
	/*
	 * Range handler should have one child, a single text node.
	 */
	protected class RangeHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			PrettyPrint(n.getNodeName());
			INDENT_LEVEL++;
			Node c = n.getFirstChild();
			XMLNodeTypeHandler handler = GetNodeHandler(c.getNodeName());
			handler.HandleNode(c);
			current.range = last_val;
			INDENT_LEVEL--;
		}
	}
	
	/*
	 * Components should have one value, a single text value
	 * It's a bit-masked integer.
	 */
	protected class ComponentsHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			PrettyPrint(n.getNodeName());
			INDENT_LEVEL++;
			Node c = n.getFirstChild();
			XMLNodeTypeHandler handler = GetNodeHandler(c.getNodeName());
			handler.HandleNode(c);
			current.SetComponents(last_val);
			INDENT_LEVEL--;
		}
	}
	
	/*
	Materials should have one value, a single text value. It may be
	empty.
	 */
	protected class MaterialsHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			PrettyPrint(n.getNodeName());
			INDENT_LEVEL++;
			Node c = n.getFirstChild(); if (c == null ) return;
			XMLNodeTypeHandler handler = GetNodeHandler(c.getNodeName());
			handler.HandleNode(c);
			current.materials = last_val;
			INDENT_LEVEL--;
		}
	}
	
	/*
	 * Duration should have a single child node, a text node.
	 */
	protected class DurationHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			PrettyPrint(n.getNodeName());
			INDENT_LEVEL++;
			Node c = n.getFirstChild();
			XMLNodeTypeHandler handler = GetNodeHandler(c.getNodeName());
			handler.HandleNode(c);
			current.duration = last_val;
			INDENT_LEVEL--;
		}
	}
	/*
	 * Description may have multiple nodes. 
	 */
	protected class DescriptionHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			PrettyPrint(n.getNodeName());
			INDENT_LEVEL++;
			Node c = n.getFirstChild();
			while (c != null){
				XMLNodeTypeHandler handler = GetNodeHandler(c.getNodeName());
				handler.HandleNode(c);
				current.description = current.description +  (last_val != null ? last_val : "");
				c = c.getNextSibling();
			}
			INDENT_LEVEL--;
		}
	}
	
	protected class ConcentrationHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			PrettyPrint(n.getNodeName());
			current.concentration = true;
		}
	}
	
	protected class GenericNodeHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			System.out.println("Unsupported node type: " + n.getNodeName());
		}
	}
	
	protected class TextHandler extends XMLNodeTypeHandler{
		@Override
		public void HandleNode(Node n) {
			last_val = n.getNodeValue();
		}
	}
	
	protected class BreakHandler extends XMLNodeTypeHandler {

		@Override
		public void HandleNode(Node n) {
			last_val = "\n";
		}
		
	}
	
	/////////////////////////////////////////////////////////////
	///////////////////////   MAIN   ////////////////////////////
	/////////////////////////////////////////////////////////////
	
	public static void main(String [] args){
		String fname = "SpellDescriptions.xml";
		SpellXMLParser s = new SpellXMLParser(SandSXML.default_path, fname);
		s.ReadFile();
		
		for (Spell p : s.SpellList()){
			p.Print();
		}
		
	}
}
