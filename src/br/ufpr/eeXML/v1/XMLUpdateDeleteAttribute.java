/*
 * Created on 30/11/2005
 *
 */
package br.ufpr.eeXML.v1;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.eeXML.v1.glushkov.Automato;
import br.ufpr.eeXML.v1.glushkov.MakeAutomato;
import br.ufpr.eeXML.v1.glushkov.grec.CheckAutomataWord;
import br.ufpr.eeXML.v1.glushkov.grec.GraphModification;
import br.ufpr.eeXML.v1.glushkov.grec.MakeExp;
import br.ufpr.eeXML.v1.glushkov.regex.DGrec;
import br.ufpr.eeXML.v1.glushkov.regex.Dgrec3;
import br.ufpr.eeXML.v1.glushkov.regex.Exp;
import br.ufpr.eeXML.v1.glushkov.regex.ExpToRegex;
import br.ufpr.eeXML.v1.glushkov.regex.Regex;
import br.ufpr.eeXML.v1.glushkov.regex.WordList;
import br.ufpr.eeXML.v1.treeautomata.ExpDTD;
import br.ufpr.eeXML.v1.treeautomata.State;
import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;


/*
 * @autor Robson João Padilha da Luz
 *
 */
public class XMLUpdateDeleteAttribute extends XMLUpdate
{
	/**
	 * Node refer to insert operation
	 */
	private IndexedTreeNode deleteNode;
	
	
	public XMLUpdateDeleteAttribute(IndexedTreeNode deleteNode)throws IllegalArgumentException
	{
		this.deleteNode = deleteNode;
	}
	
	public IndexedTreeNode getDeleteNode() {
		return deleteNode;
	}

	public void setDeleteNode(IndexedTreeNode deleteNode) {
		this.deleteNode = deleteNode;
	}


	@Override public void performUpdate(TreeAutomata treeAutomata, IndexedTreeNode root) 
	{
		State state = treeAutomata.getStateByElementName(deleteNode.getParent().getName());
		if(state!=null){
			state.getLstComp().remove("q_"+deleteNode.getName());
			state.getLstOp().add("q_"+deleteNode.getName());
		}
	}
	
	@Override public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("delete_attribute(");
		sb.append(this.deleteNode);
		sb.append(")");
		return sb.toString();
	}

	@Override
	public IndexedTreeNode getReferenceNode() {
		return deleteNode.getParent();
	}
	
	
}