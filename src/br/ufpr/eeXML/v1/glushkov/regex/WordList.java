/*
 * Created on 10/05/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import br.ufpr.eeXML.v1.IndexedTreeNode;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class WordList 
{
	private String[] list;
	private int index;

	public WordList()
	{
		list = new String[0];
	}
	
	public WordList(List<IndexedTreeNode> lstNode,boolean b)
	{
		list = new String[lstNode.size()];
		int i=0;
		for(IndexedTreeNode node:lstNode){
			list[i++] = node.getName();
		}
	}
	
	public WordList(List<String> lstWord)
	{
		this(lstWord.toArray(new String[lstWord.size()]));
	}
	
	public WordList(String[] list)
	{
		this.list = list;
	}
	public WordList(StringTokenizer st)
	{
		list = new String[st.countTokens()];
		int i=0;
		while(st.hasMoreTokens())
			list[i++]=st.nextToken();
	}
	public WordList(String str, String delim)
	{
		this(new StringTokenizer(str,delim));
	}

	public int getIndex()
	{
		return index;
	}
	public String nextWord()
	{
		if(hasNext())
			return list[index++];
		return null;
	}
	public String currentWord()
	{
		if(index<list.length)
			return list[index];
		return null;
	}
	public boolean hasNext()
	{
		return index<list.length; 
	}
	public void reset()
	{
		index = 0;
	}
	
	public int size()
	{
		return list.length;
	}
	public String[] getList() {
		return list;
	}
	public void setList(List<String> lstWord)
	{
		this.list = lstWord.toArray(new String[lstWord.size()]);
	}
	public void setList(String[] list)
	{
		this.list = list;
	}
	
	public WordList clone()
	{
		String cl[] = new String[list.length];
		int i=0;
		for(String st: list)
			cl[i++] = new String(st.toCharArray());
		return new WordList(cl);
	}
}