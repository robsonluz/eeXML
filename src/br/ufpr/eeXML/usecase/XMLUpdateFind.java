/*
 * Created on 24/10/2006
 *
 */
package br.ufpr.eeXML.usecase;

import java.util.ArrayList;
import java.util.List;

import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.IndexedTreeNodeType;
import br.ufpr.eeXML.v1.XMLUpdate;
import br.ufpr.eeXML.v1.XMLUpdateInsertAttribute;
import br.ufpr.eeXML.v1.XMLUpdateInsertElement;
import br.ufpr.eeXML.v1.XMLUpdateList;
import br.ufpr.eeXML.v1.glushkov.regex.WordList;
import br.ufpr.eeXML.v1.treeautomata.State;
import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;
import br.ufpr.eeXML.v1.treeautomata.State.Type;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class XMLUpdateFind {
	private TreeAutomata treeAutomata;
	
	private XMLUpdateList updateList;
	
	public XMLUpdateFind(TreeAutomata treeAutomata) {
		this.treeAutomata = treeAutomata;
	}
	
	public synchronized XMLUpdateList findUpdates(IndexedTreeNode xmlNode) {
		updateList = new XMLUpdateList();
		match(xmlNode,null);
		return updateList;
	}
	
	private void match(IndexedTreeNode node, MatchItem matchItem){
		MatchItem mItem = new MatchItem();
		if(node.getChildren()!=null){
			for(IndexedTreeNode child : node.getChildren()){
				match(child,mItem);
			}
		}
		try{
		if(matchItem!=null){
			if(node.getType()!=IndexedTreeNodeType.DATA){
			//if(node.getType()==IndexedTreeNodeType.ELEMENT){
				matchElements(node, mItem.elements);
			}
			//else
			if(node.getType()==IndexedTreeNodeType.ATTRIBUTE){
				matchItem.attributes.add(node);
			}else{
				if(node.getType()!=IndexedTreeNodeType.DATA){
					matchAttributes(node, mItem.attributes);
				}
				matchItem.elements.add(node);
			}
		}
		}catch(Exception e){}
		//System.out.println(node.getName()+":"+node.getType());
	}
	private void matchElements(IndexedTreeNode node, List<IndexedTreeNode> elements){
		State state = findStateByTreeNode(node);
		if(state == null){
			//throw new RuntimeException("Element "+node.getName()+" is not accept at this positions.");
			//XMLUpdateInsertElement insertElement = new XMLUpdateInsertElement(node.getName(),findLeftNode(node),XMLUpdateInsertElement.INSERT_AFTER, node);
			XMLUpdate xmlUpdate = null;
			if(node.getType()==IndexedTreeNodeType.ATTRIBUTE) {
				xmlUpdate  = new XMLUpdateInsertAttribute(node.getName(), node.getParent());
			}else{
				xmlUpdate  = new XMLUpdateInsertElement(node, node.getParent());
			}
			if(xmlUpdate!=null)
				updateList.add(xmlUpdate);
			throw new RuntimeException();
			//return;
			
		}
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
	
	
	private int findNodePosition(IndexedTreeNode node) {
		int pos = 0;
		if(node==null || node.getParent()==null)
			return pos;
		IndexedTreeNode aux = null;
		for(IndexedTreeNode n: node.getParent().getChildren()){
			pos++;
			if(n==node)
				return pos;
			aux = n;
		}
		return pos;
	}
	
	private IndexedTreeNode findLeftNode(IndexedTreeNode node) {
		if(node==null || node.getParent()==null)
			return null;
		IndexedTreeNode aux = null;
		for(IndexedTreeNode n: node.getParent().getChildren()){
			if(n==node)
				return aux;
			aux = n;
		}
		return null;
	}
	
	private void matchAttributes(IndexedTreeNode node, List<IndexedTreeNode> attributes) {
		State state = findStateByTreeNode(node);
		int required = state.getLstComp().size();
		int contRequired = 0;
		boolean stop = false;
		String attStr="";
		for(IndexedTreeNode att: attributes) {
			stop = false;
			attStr = "q_@"+att.getName();
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
		for(State state: treeAutomata.getLstState()){
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
