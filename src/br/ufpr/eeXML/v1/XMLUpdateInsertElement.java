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
import br.ufpr.eeXML.v1.glushkov.regex.RegexList;
import br.ufpr.eeXML.v1.glushkov.regex.RegexUpdateChoices;
import br.ufpr.eeXML.v1.glushkov.regex.SingleRegex;
import br.ufpr.eeXML.v1.glushkov.regex.UpdateRegex;
import br.ufpr.eeXML.v1.glushkov.regex.WordList;
import br.ufpr.eeXML.v1.treeautomata.ExpDTD;
import br.ufpr.eeXML.v1.treeautomata.State;
import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;


/*
 * @autor Robson João Padilha da Luz
 *
 */
public class XMLUpdateInsertElement extends XMLUpdate
{
	/**
	 * Node inserted after referenceNode
	 */
	//public static final int INSERT_AFTER=0;
	/**
	 * Node inserted before referenceNode
	 */
	//public static final int INSERT_BEFORE=1;
	/**
	 * Node refer to insert operation
	 */
	private IndexedTreeNode referenceNode;
	/**
	 * Type of insertion, can be INSERT_AFTER or INSERT_BEFORE
	 */
	//private int type;
	
	private String elementToInsert;
	
	private boolean many;
	
	
	private IndexedTreeNode nodeToInsert;
	private IndexedTreeNode parentNode;
	
	public XMLUpdateInsertElement(IndexedTreeNode nodeToInsert, IndexedTreeNode parentNode)throws IllegalArgumentException {
		this.nodeToInsert = nodeToInsert;
		this.parentNode = parentNode;
	}
	
//	public XMLUpdateInsertElement(String elementName,IndexedTreeNode referenceNode,int type, IndexedTreeNode nodeToInsert)throws IllegalArgumentException
//	{
//		this.referenceNode = referenceNode;
//		this.elementToInsert = elementName;
//		setType(type);
//	}
	
//	public XMLUpdateInsertElement(String elementName,IndexedTreeNode referenceNode,int type,boolean many, IndexedTreeNode nodeToInsert)throws IllegalArgumentException
//	{
//		this(elementName,referenceNode,type, nodeToInsert);
//		this.many = many;
//	}


	
	public IndexedTreeNode getReferenceNode() {
		return parentNode;
	}

	public void setReferenceNode(IndexedTreeNode referenceNode) {
		this.referenceNode = referenceNode;
	}

	/**
	 * Get Type for this insert operation
	 * @return can be INSERT_AFTER or INSERT_BEFORE
	 */
//	public int getType()
//	{
//		return type;
//	}

	/**
	 * Set Type for this insert operation
	 * @param type can be INSERT_AFTER or INSERT_BEFORE
	 */
//	public void setType(int type)throws IllegalArgumentException
//	{
//		if(!(type==INSERT_AFTER || type==INSERT_BEFORE))
//			throw new IllegalArgumentException("type must be INSERT_AFTER or INSERT_BEFORE");
//		this.type = type;
//	}



	@Override public void performUpdate(TreeAutomata treeAutomata, IndexedTreeNode root) 
	{
		//#######Old implementation##########//
//		IndexedTreeNode parent = referenceNode.getParent();
//		State state = treeAutomata.getStateByElementName(parent.getName());
//		WordList ws = makeWord(referenceNode);
//		DGrec grec = new DGrec(state.getExp());
//		state.setChoices(grec.evolution(ws));
		//###################################//
		
		
		State state = treeAutomata.getStateByElementName(parentNode.getName());
		WordList ws = makeWord(nodeToInsert);
		if(state.isRegexData()) {
			//FIXME: Acertar para agrupar as modificações de mesmo pai e mandar junto para o dGREC.
			UpdateRegex regex = new UpdateRegex();
			regex.add(new SingleRegex(nodeToInsert.getName()));
			RegexList regexList = new RegexList();
			regexList.add(regex);
			RegexUpdateChoices choices = new RegexUpdateChoices(regexList, null);
			state.setChoices(choices);
			state.setRegexData(false);
			state.setExp(regex);
		}else{
			DGrec grec = new DGrec(state.getExp());
			state.setChoices(grec.evolution(ws));
		}
		state.setEvolution(true);
		if(nodeToInsert.getName().equals("DOI")) {
			System.out.println("Teste");
		}
		if(!treeAutomata.hasStateByElementName(nodeToInsert.getName())) {
			State newState = new State();
			newState.setName(nodeToInsert.getName());
			newState.setParentElement(state);
			newState.setQName("q_"+nodeToInsert.getName());
			newState.setType(State.Type.ELEMENT);
			newState.setExp(ExpToRegex.convert(new ExpDTD("data")));
			newState.setRegexData(true);
			treeAutomata.add(newState);
		}

		
		//state.setLstEvolutionRegex(grec.)
		//state.setLstEvolutionRegex(grec.evolution(many,(String[]) lstW.toArray(new String[lstW.size()])));
		/*
		Exp eclone = new ExpDTD(state.getExp().getExp());
		System.out.println(state.getExp());
		MakeAutomato ma = new MakeAutomato(ExpToRegex.convert(eclone));
		Automato a = ma.gerarAutomato();
		GraphModification gm = CheckAutomataWord.checkAutomataWord(a,lstW);
		if(gm!=null)
		{
			//System.out.println(gm.getSnew());
			try{
				MakeExp me = new MakeExp(a,gm);
				Regex e = me.makeExp();
				String en = prepareExp(e.toString());
				System.out.println(":::::::"+en);
				state.setStrExp(en);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		*/
	}
	
	private WordList makeWord(IndexedTreeNode node)
	{
		IndexedTreeNode parent = node.getParent();
		List<String> lstW = new LinkedList();
		for(IndexedTreeNode child : parent.getChildren())
		{
			if(child.getType()==IndexedTreeNodeType.ELEMENT){
				//if(child == node)
				//{
					//if(type==INSERT_BEFORE)
						;//lstW.add(elementToInsert);
					//lstW.add(child.getName());
					//if(type==INSERT_AFTER)
						;//lstW.add(elementToInsert);
				//}else{
				if(child.getType()!=IndexedTreeNodeType.DATA)
					lstW.add(child.getName());
				//}
			}
		}
		return new WordList(lstW);
	}
	
//	private String prepareExp(String exp)
//	{
//		if(exp!=null)
//		{
//			exp = tirarParentese(exp);
//			exp = exp.replaceAll("\\.",",");
//		}
//		return exp;
//	}
//	private String tirarParentese(String exp)
//	{
//		exp=exp.trim();
//		if(exp.startsWith("(") && exp.endsWith(")"))
//		{
//			int queue = 0;
//			for(int i=0;i<exp.length();i++)
//			{
//				String t = exp.substring(i,i+1);
//				if(t.equals("("))
//					queue++;
//				if(t.equals(")"))
//					queue--;
//				if(queue==0 && i<exp.length()-1)
//				{
//					queue = -1;
//					break;
//				}
//			}
//			if(queue==0)
//				exp = tirarParentese(exp.substring(1,exp.length()-1));
//		}
//		return exp;
//	}



	@Override public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("insert_element('");
		sb.append(this.nodeToInsert+"'");
		sb.append(", ");
		sb.append(this.parentNode);
		sb.append(", ");
		sb.append(this.nodeToInsert.getPosition());
		
		//sb.append(this.referenceNode.toString());
		//sb.append(", ");
		/*if(type==INSERT_AFTER)
			sb.append("AFTER");
		else
			sb.append("BEFORE");
		*/
		sb.append(")");
		return sb.toString();
	}

	public boolean isMany() {
		return many;
	}

	public void setMany(boolean many) {
		this.many = many;
	}
	
	
}