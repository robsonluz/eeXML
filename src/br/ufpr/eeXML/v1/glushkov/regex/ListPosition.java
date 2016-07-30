/*
 * Created on 06/06/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;
/*
 * @autor Robson João Padilha da Luz
 *
 */
public class ListPosition 
{
	private NodePosition root;
	private NodePosition last;
	private int size;
	
	public int add()
	{
		add(size+1);
		return size;
	}
	
	public void add(int p)
	{
		NodePosition node = new NodePosition(p);
		if(root==null){
			root = node;
			last = root;
		}else{
			last.next = node;
			last = node;
		}
		size++;
	}
	
	public int[] toArray()
	{
		if(size==0) return new int[0];
		int ret[] = new int[size];
		NodePosition node = root;
		for(int i=0;i<size;i++)
		{
			ret[i] = node.position;
			node = node.next;
		}
		return ret;
	}

	public int size() {
		return size;
	}
}

class NodePosition
{
	int position;
	NodePosition next;
	NodePosition(int p)
	{
		this.position = p;
	}
}