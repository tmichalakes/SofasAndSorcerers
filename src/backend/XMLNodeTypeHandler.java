package backend;

import org.w3c.dom.Node;

/**
 * 
 * @author Ted Michalakes
 * 
 * XMLNodeTypeHandler Abstract Class
 * Purpose is to get some modicum of functional programming
 * out of java. NodeTypeHandler objects will take the name tag of the
 * xml node and the HandleNode method can be called on that node.
 * 
 * Node names will be stored in a HashMap of strings (node names)
 * and Handlers.
 */

public abstract class XMLNodeTypeHandler {
	public abstract void HandleNode(Node n);
}