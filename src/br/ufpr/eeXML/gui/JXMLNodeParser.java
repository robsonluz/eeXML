/*
 * Created on 12/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import br.ufpr.eeXML.v1.IndexedTreeNode;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class JXMLNodeParser 
{
	public static JXMLNode parse(IndexedTreeNode node)
	{
		JXMLNode n = new JXMLNode(node);
		for(IndexedTreeNode child:node.getChildren())
		{
			n.addChild(parse(child));
		}
		return n;
	}
}