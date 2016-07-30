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
public class XMLUpdateDeleteElement extends XMLUpdate
{
	/**
	 * Node refer to insert operation
	 */
	private IndexedTreeNode deleteNode;
	
	
	public XMLUpdateDeleteElement(IndexedTreeNode deleteNode)throws IllegalArgumentException
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
		IndexedTreeNode parent = deleteNode.getParent();
		State state = treeAutomata.getStateByElementName(parent.getName());
		WordList ws = new WordList();
		int pDelete = makeWord(deleteNode,ws);
		DGrec grec = new DGrec(state.getExp());
		state.setExp(grec.delete(ws,pDelete));
	}
	
	private int makeWord(IndexedTreeNode node,WordList wordList)
	{
		IndexedTreeNode parent = node.getParent();
		List<String> lstW = new LinkedList();
		int index = 0;
		boolean findIndex=true;
		for(IndexedTreeNode child : parent.getChildren())
		{
			if(child.getType()==IndexedTreeNodeType.ELEMENT){
				if(child == node)
					findIndex=false;
				lstW.add(child.getName());
				if(findIndex) index++;
			}
		}
		wordList.setList(lstW);
		return index;
	}
	
	@Override public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("delete_element('");
		sb.append(this.deleteNode+"'");
		sb.append(")");
		return sb.toString();
	}

	@Override
	public IndexedTreeNode getReferenceNode() {
		return deleteNode.getParent();
	}
	
	
}