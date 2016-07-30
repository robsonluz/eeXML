/*
 * Created on 06/04/2006
 *
 */
package br.ufpr.eeXML.v1;
/*
 * @autor Robson João Padilha da Luz
 *
 */
public class IndexedTreeNodeHelper 
{
	public static IndexedTreeNode findByPosition(IndexedTreeNode root,int parentPosition, int position)
	{
		if(root.getPosition()==position)
		{
			if(parentPosition==-1)
				return root;
			if(root.getParent()!=null && root.getParent().getPosition()==parentPosition)
				return root;
		}
		for(IndexedTreeNode node:root.getChildren())
		{
			IndexedTreeNode n = findByPosition(node,parentPosition, position);
			if(n!=null)
				return n;
		}
		return null;
	}
	
	public static IndexedTreeNode findByPositions(IndexedTreeNode root,int... position) {
		IndexedTreeNode current = root;
		for(int p: position) {
			for(IndexedTreeNode node: current.getChildren()) {
				if(node.getPosition()==p) {
					current = node;
					break;
				}
			}
		}
		if(current!=root)
			return current;
		return null;
	}
}