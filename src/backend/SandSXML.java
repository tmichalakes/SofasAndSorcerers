package backend;

/*!
 * @author Ted Michalakes
 * @email tmichalakes@gmail.com
 * 
 * -XML Utilities class for Sofas and Sorcerers.
 * -Reads, writes, and exports XML Files for use
 * -by Sofas and Sorcerers app.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public abstract class SandSXML {
	/**
	 * TagMap - HashMap
	 * 
	 * Associates names of XML Tags with handlers that know
	 * how to deal with tags of that name. See private class
	 * declarations below and XMLNodeTypeHandler abstract class.
	 */
	protected HashMap<String, XMLNodeTypeHandler> TagMap;
	
	public static final String default_path = "C:/XML Files/";
	public static final String test_filename = "fighter.xml";
	public static final String INDENT = " ";
	
	public static final String TEXT_TAG   = "#text";
	public static final String BREAK_TAG  = "br";
	
	public int INDENT_LEVEL;
	
	private String path;
	private String filename;
	
	public final boolean DEBUG = false;
	
	public SandSXML(){
		initialize();
	}
	
	public SandSXML(boolean use_defaults){
		initialize();
		if (use_defaults){
			path = default_path;
			filename = test_filename;
		}
	}
	
	public SandSXML(String fpath, String fname){
		initialize();
		path = fpath;
		filename = fname;
	}
	
	public void initialize(){
		path = new String();
		filename = new String();
		TagMap = new HashMap<>();
		INDENT_LEVEL = 0;
	}
	
	public Node GetRoot(){
		Node _ret = null;
		try{
			File input = new File(path + filename);
			Scanner s = new Scanner(input);
			
			///Read and open the document, generate DOM.
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			Document doc = db.parse(input); ///at this point, we should have our xml file ready to go.
			doc.getDocumentElement().normalize();
			Element r = doc.getDocumentElement();
			Node root = (Node) r;	///treat the root as a node, but keep the reference to the element form
			
			if (DEBUG)
			RecursiveNodePrint(root, "");
			
			///return the object
			_ret = root;
		}
		/**
		 * Catch block functionality to be determined later.
		 */
		catch(FileNotFoundException e){
			System.out.println("Unable to locate file " + path + filename);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("PARSER CONFIGURATION EXCEPTION!");
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("SAX EXCEPTION");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
		
		return _ret;
	}
	
	public abstract void ReadFile();
	
	///InitializTagMap needs to be called in 
	///the constructor or initialization function
	///of the child class.
	protected abstract void InitializeTagMap();
	
	
	/////////////////// STRING OUTPUT METHODS ////////////////////
	
	public static String NameOrText(Node n){
		if (IsTextNode(n)){
			return n.getNodeValue();
		}else{
			return n.getNodeName();
		}
	}
	
	public static boolean TextWhiteSpace(Node n){
		return (n.getNodeValue().trim().equals(""));
	}
	
	public static boolean IsTextNode(Node n){
		return n.getNodeName().equals("#text");
	}
	
	public static void PrintIfNotEmpty(Node n, String indent){
		String s = NameOrText(n).trim();
		if (s != null && !s.equals("")) System.out.println(indent + s);
	}
	
	public void RecursiveNodePrint(Node n, String indent){;
		//System.out.println("-");
		PrintIfNotEmpty(n, indent);
		for(Node c = n.getFirstChild(); c != null; c = c.getNextSibling()){
			RecursiveNodePrint(c, indent + INDENT);
		}
	}
	
	public void PrettyPrint(String s){
		if (s == "null" || s.trim().equals("")) return;
		for (int i = 0; i < INDENT_LEVEL; i++){
			System.out.print(INDENT);
		}
		System.out.println(s);
	}

	
}
