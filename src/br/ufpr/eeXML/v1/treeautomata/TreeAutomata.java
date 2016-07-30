/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.eeXML.v1.treeautomata;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.IndexedTreeNodeType;
import br.ufpr.eeXML.v1.glushkov.regex.Regex;
import br.ufpr.eeXML.v1.glushkov.regex.SingleRegex;
import br.ufpr.eeXML.v1.glushkov.regex.WordList;
import br.ufpr.eeXML.v1.treeautomata.State.Type;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class TreeAutomata 
{
	private List<State> lstState;
	
	public TreeAutomata()
	{
		lstState = new LinkedList();
	}
	
	public State getStateByName(String name)
	{
		for(State s:lstState)
		{
			if(s.getName().equals(name) && s.getType()==Type.ELEMENT)
				return s;
		}
		return null;
	}
	
	public State getElementStateByName(String name)
	{
		for(State s:lstState)
		{
			if(s.getName().equals(name) && s.getType()==Type.ELEMENT)
				return s;
		}
		return null;
	}
	public State getAttributeStateByName(String name)
	{
		for(State s:lstState)
		{
			if(s.getName().equals(name) && s.getType()==Type.ATTRIBUTE)
				return s;
		}
		return null;
	}	
	
	public State getAttributeStateByElement(State element,String attributeName)
	{
		try{
		for(State s:lstState)
		{
			if(s.getType()==Type.ATTRIBUTE){
				if(s.getParentElement()!=null && s.getParentElement().getName().equals(element.getName()) && s.getName().equals(attributeName))
					return s;
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public void add(State state)
	{
		lstState.add(state);
	}
	
	public List<State> getLstState() 
	{
		return lstState;
	}

	public void setLstState(List<State> lstState) 
	{
		this.lstState = lstState;
	}
	
	public void initTreeAutomata()
	{
		State s = new State();
		s.setName("data");
		s.setQName("q_data");
		s.setExp(new SingleRegex("0"));
		lstState.add(s);
	}
	
	public void printTreeAutomata()
	{
		int i = 1;
		//StringBuffer sb = new StringBuffer("");
		for(State s:lstState){
			print(s, i);
			i++;
		}
		//System.out.println(sb.toString());
	}
	
	public void printTreeAutomataLattes()
	{
		int i = 1;
		//StringBuffer sb = new StringBuffer("");
		for(State s:lstState){
			printLattes(s, i);
			i++;
		}
		//System.out.println(sb.toString());
	}
	
	private void printLattes(State s, int count) {
		//"\emph{(1) clientes, \{$\emptyset, \emptyset\}, q_{cliente}*\ \rightarrow q_{clientes}$}";
		StringBuffer sb = new StringBuffer("");
		sb.append("\\emph{("+count+") ");
		
		if(s.getType()==Type.ATTRIBUTE){
			sb.append(s.getParentElement().getName().toLowerCase());
			sb.append("@");
		}
		sb.append(s.getName().toLowerCase());
		sb.append(",\\ \\{$");
		addAttributeLattes(sb,s.getLstComp());
		sb.append(",\\ ");
		addAttributeLattes(sb,s.getLstOp());
		sb.append("\\},\\ ");
//		if(s.getLstEvolutionRegex().size()>0)
//			sb.append(s.getLstEvolutionRegex().get(0));
//		else
		sb.append(s.getExp().toString().toLowerCase());
		sb.append("\\ \\rightarrow q_{");
		sb.append(s.getName().toLowerCase());
		sb.append("}$}");
		System.out.println(sb.toString());
	}
	
	private void addAttributeLattes(StringBuffer sb,List<String> lst) {
		if(lst.size()==0){
			sb.append("\\emptyset");
			return;
		}
		sb.append("\\{");
		for(int i=0;i<lst.size();i++)
		{
			sb.append("q_{");
			sb.append(lst.get(i).toLowerCase());
			sb.append("}");
			if(i<lst.size()-1)
				sb.append(",\\ ");
		}
		sb.append("\\}");
	}
	
	public void printEvolutionStatesTreeAutomata()
	{
		int i = 1;
		//StringBuffer sb = new StringBuffer("");
		for(State s:lstState){
			if(s.isEvolution()) {
				System.out.println("------------------------------------------------");
				print(s, i);
				if(s.getChoices()!=null) {
					List<Regex> exps = s.getChoices().getAllChoices();
					if(exps.size()>0) {
						for(Regex exp: exps) {
							System.out.println("--> "+ exp);
						}
					}
				}
				System.out.println("------------------------------------------------");
			}
			i++;
		}
	}	
	
	private void print(State s, int count) {
		StringBuffer sb = new StringBuffer("");
		sb.append("("+count+") ");
		if(s.getType()==Type.ATTRIBUTE){
			sb.append(s.getParentElement().getName());
			sb.append("@");
		}
		sb.append(s.getName());
		sb.append(", {");
		addAttribute(sb,s.getLstComp());
		sb.append(",");
		addAttribute(sb,s.getLstOp());
		sb.append("}, ");
//		if(s.getLstEvolutionRegex().size()>0)
//			sb.append(s.getLstEvolutionRegex().get(0));
//		else
			sb.append(s.getExp());
		sb.append(" -> ");
		sb.append(s.getQName());
		System.out.println(sb.toString());
	}
	
	private void addAttribute(StringBuffer sb,List<String> lst)
	{
		if(lst.size()==0){
			sb.append("0");
			return;
		}
		sb.append("{");
		for(int i=0;i<lst.size();i++)
		{
			sb.append(lst.get(i));
			if(i<lst.size()-1)
				sb.append(",");
		}
		sb.append("}");
	}
	
	public State getStateByElementName(String elementName)throws NoSuchElementException
	{
		for(State s:lstState)
		{
			if(s.getName().equals(elementName))
				return s;
		}
		throw new NoSuchElementException("State with element "+elementName+" not found");
	}
	
	public boolean hasStateByElementName(String elementName)throws NoSuchElementException
	{
		try{
			return getStateByElementName(elementName) != null;
		}catch(Exception e) {
		}
		return false;
	}
	
	public boolean match(IndexedTreeNode xmlNode){
		match(xmlNode,null);
		return true;
	}
	
	private void match(IndexedTreeNode node, MatchItem matchItem){
		MatchItem mItem = new MatchItem();
		if(node.getChildren()!=null){
			for(IndexedTreeNode child : node.getChildren()){
				match(child,mItem);
			}
		}
		if(matchItem!=null){
			if(node.getType()!=IndexedTreeNodeType.DATA){
				matchElements(node, mItem.elements);
			}
			if(node.getType()==IndexedTreeNodeType.ATTRIBUTE){
				matchItem.attributes.add(node);
			}else{
				if(node.getType()!=IndexedTreeNodeType.DATA){
					matchAttributes(node, mItem.attributes);
				}
				matchItem.elements.add(node);
			}
		}
		//System.out.println(node.getName()+":"+node.getType());
	}
	private void matchElements(IndexedTreeNode node, List<IndexedTreeNode> elements){
		State state = findStateByTreeNode(node);
		if(state == null)
			throw new RuntimeException("Element "+node.getName()+" is not accept at this positions.");
		if(node.getType()==IndexedTreeNodeType.ATTRIBUTE && state.getExp().toString().equals("data"))
			return;
		WordList ws = new WordList(elements,true);
		state.getExp().makePositions();
		if(!state.getExp().match(ws)){
			StringBuffer sb = new StringBuffer("The ");
			if(state.getType()==Type.ATTRIBUTE)
				sb.append("Attribute '");
			else
				sb.append("Element '");
			sb.append(state.getName()+"' require ");
			sb.append(state.getExp().toString());
			throw new RuntimeException(sb.toString());
		}
		
	}
	
	private void matchAttributes(IndexedTreeNode node, List<IndexedTreeNode> attributes) {
		State state = findStateByTreeNode(node);
		int required = state.getLstComp().size();
		int contRequired = 0;
		boolean stop = false;
		String attStr="";
		for(IndexedTreeNode att: attributes) {
			stop = false;
			//attStr = "q_@"+att.getName();
			attStr = att.getName();
			for(String comp: state.getLstComp()){
				if(comp.equals(attStr)) {
					contRequired++;
					stop = true;
					break;
				}
			}
			if(stop) continue;
			stop = false;
			for(String op: state.getLstOp()){
				if(op.equals(attStr)){
					stop = true;
					break;
				}
			}
			if(stop) continue;
			throw new RuntimeException("The Attribute '"+att.getName()+"' is not accept by Element '"+node.getName()+"'");
		}
		if(required>0 && contRequired!=required){
			StringBuffer sb = new StringBuffer("Element '"+node.getName()+"' required the attributes");
			for(String comp: state.getLstComp())
				sb.append(" '"+comp+"'");
			sb.append(".");
			throw new RuntimeException(sb.toString());
		}
	}
	
	private State findStateByTreeNode(IndexedTreeNode node){
		for(State state: lstState){
//			if(state.getName().equals("name") && state.getParentElement()!=null && state.getParentElement().getName().equals("class")){
//				System.out.println("aqui");
//			}
			if(node.getType()==IndexedTreeNodeType.ATTRIBUTE && state.getType()==State.Type.ELEMENT)
				continue;
			if((node.getType()==IndexedTreeNodeType.ELEMENT||node.getType()==IndexedTreeNodeType.DATA) && state.getType()==State.Type.ATTRIBUTE)
				continue;
			if(node.getType()==IndexedTreeNodeType.ATTRIBUTE){
				if( node.getParent()!=null && state.getName().equals(node.getName()) && 
						state.getParentElement()!=null && state.getParentElement().getName().equals(node.getParent().getName()))
					return state;
			}else if(state.getName().equals(node.getName()))
				return state;
		}
		return null;
	}
	//private boolean matchAttribute()
	
	private class MatchItem {
		private List<IndexedTreeNode> elements=new ArrayList();
		private List<IndexedTreeNode> attributes=new ArrayList();
	}
}
