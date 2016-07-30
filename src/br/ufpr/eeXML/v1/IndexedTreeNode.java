/*
 * Created on 05/04/2006
 *
 */
package br.ufpr.eeXML.v1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class IndexedTreeNode implements Comparable<IndexedTreeNode>
{
	private int position;
	private IndexedTreeNode parent;
	private IndexedTreeNodeType type;
	private String name;
	private List<IndexedTreeNode> lstChild;
	
	/**
	 * When true this node is a node of a modification
	 */
	private boolean insertedNew;
	
	public IndexedTreeNode(String name,int position, IndexedTreeNodeType type,IndexedTreeNode parent) 
	{
		this.name = name;
		this.parent = parent;
		this.position = position;
		this.type = type;
		this.lstChild = new ArrayList<IndexedTreeNode>();
	}
	
	/**
	 * Create a TreeNode with type = TreeNodeType.ELEMENT
	 * @param parent parent this TreeNode
	 * @param position position this TreeNode
	 */
	public IndexedTreeNode(String name, int position, IndexedTreeNode parent) 
	{
		this.parent = parent;
		this.position = position;
		this.type = IndexedTreeNodeType.ELEMENT;
		setName(name);
		this.lstChild = new LinkedList();
	}
	
	public IndexedTreeNodeType getType() {
		return type;
	}
	public void setType(IndexedTreeNodeType type) {
		this.type = type;
	}
	public IndexedTreeNode getParent() {
		return parent;
	}
	public void setParent(IndexedTreeNode parent) {
		this.parent = parent;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name!=null && name.equals("#text"))
		{
			this.name = "data";
			this.type = IndexedTreeNodeType.DATA;
		}else{
			this.name = name;
		}
	}
	
	public String getPositionDesc()
	{
		if(parent!=null && parent.getPosition()!=-1){
			return parent.getPositionDesc()+position;
		}else{
			if(position==-1)
				return "&";
			else
				return ""+position;
		}
	}
	
	public void addChild(IndexedTreeNode node)
	{
		lstChild.add(node);
	}
	public List<IndexedTreeNode> getChildren() {
		return lstChild;
	}
	
	public String toString()
	{
		return getName()+"("+getPositionDesc()+")";
	}

	public boolean isInsertedNew() {
		return insertedNew;
	}

	public void setInsertedNew(boolean insertedNew) {
		this.insertedNew = insertedNew;
	}

	public int compareTo(IndexedTreeNode o) {
		if(this.getName().equals("RESUMO-CV")) {
			System.out.println("ISto");
		}
		int[] posThis = this.getPositions();
		int[] posThat = o.getPositions();
		if(posThis.length<posThat.length)
			return -1;
		if(posThis.length>posThat.length)
			return 1;
		for(int i=0;i<posThis.length;i++) {
			if(posThis[i]<posThat[i])
				return -1;
			if(posThis[i]>posThat[i])
				return 1;
		}
		if(this.getType()==IndexedTreeNodeType.ELEMENT && o.getType()!=IndexedTreeNodeType.ELEMENT)
			return 1;
		return 0;
	}
	
	public int[] getPositions() {
		int ret[] = {position};
		if(getParent()!=null) {
			int parentPositions[] = getParent().getPositions();
			if(parentPositions.length>0) {
				int aux[] = new int[ret.length+parentPositions.length];
				System.arraycopy(ret, 0, aux, 0, ret.length);
				System.arraycopy(parentPositions, 0, aux, ret.length, parentPositions.length);
				ret = aux;
			}
		}
		return ret;
	}
	
}