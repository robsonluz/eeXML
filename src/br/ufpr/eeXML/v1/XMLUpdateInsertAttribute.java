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
import br.ufpr.eeXML.v1.treeautomata.State.Type;


/*
 * @autor Robson João Padilha da Luz
 *
 */
public class XMLUpdateInsertAttribute extends XMLUpdate
{
	/**
	 * Node refer to insert operation
	 */
	private IndexedTreeNode referenceNode;
	/**
	 * name of attribute to insert
	 */
	private String name;
	
	
	public XMLUpdateInsertAttribute(String name,IndexedTreeNode referenceNode)throws IllegalArgumentException
	{
		this.referenceNode = referenceNode;
		this.name = name;
	}
	
	public IndexedTreeNode getReferenceNode() {
		return referenceNode;
	}

	public void setReferenceNode(IndexedTreeNode referenceNode) {
		this.referenceNode = referenceNode;
	}


	@Override public void performUpdate(TreeAutomata treeAutomata, IndexedTreeNode root) 
	{
		State state = treeAutomata.getStateByElementName(referenceNode.getName());
		if(state!=null)
		{
			state.getLstOp().add(name);
			state.setEvolution(true);

			if(treeAutomata.getAttributeStateByElement(state, name) == null ){
				State nstate = new State();
				nstate.setType(Type.ATTRIBUTE);
				nstate.setName(name);
				nstate.setQName("q_"+name);
				nstate.setExp(ExpToRegex.convert(new ExpDTD("data")));
				nstate.setParentElement(state);
				treeAutomata.add(nstate);
			}
		}
	}
	
	@Override public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("insert_attribute('");
		sb.append(name);
		sb.append("',");
		sb.append(this.referenceNode);
		sb.append(")");
		return sb.toString();
	}
}