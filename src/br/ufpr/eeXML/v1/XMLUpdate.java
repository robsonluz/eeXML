/*
 * Created on 30/11/2005
 *
 */
package br.ufpr.eeXML.v1;

import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public abstract class XMLUpdate
{
	//public abstract String getOperator();
	public abstract void performUpdate(TreeAutomata treeAutomata,IndexedTreeNode root);
	public abstract IndexedTreeNode getReferenceNode();
}