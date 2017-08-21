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
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class SandSXML {
	public static final String default_path = "C:/XML Files/";
	public static final String test_filename = "fighter.xml";
	public static final String INDENT = "  ";
	private String path;
	private String filename;
	
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
	
	public void initialize(){
		path = new String();
	}
	
	public void FileToStandardOut(){
		
		try{
			File input = new File(path + filename);
			Scanner s = new Scanner(input);
			///line by line reading.
			/*while (s.hasNextLine()){
				System.out.println(s.nextLine());
			}
			s.close();*/
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			Document doc = db.parse(input); ///at this point, we should have our xml file ready to go.
			doc.getDocumentElement().normalize();
			Element r = doc.getDocumentElement();
			Node root = (Node) r;	///treat the root as a node, but keep the reference to the element form
			
			RecursiveNodePrint(root, "");
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
		
		
	}
	
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
	
	public static void main(String [] args){
		SandSXML s = new SandSXML(true);
		s.FileToStandardOut();
	}
}
