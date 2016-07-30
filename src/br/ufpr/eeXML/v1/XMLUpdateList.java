/*
 * Created on 30/11/2005
 *
 */
package br.ufpr.eeXML.v1;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @autor Robson João Padilha da Luz
 * List of XML updates
 */
public class XMLUpdateList
{
	private List<XMLUpdate> lstUpdate;
	private static final XMLUpdateListComparator comparator = new XMLUpdateListComparator();
	private boolean sorted = false;
	
	/**
	 * Create a new empty list of XMLUpdate
	 */
	public XMLUpdateList()
	{
		
		lstUpdate = new LinkedList();
	}
	
	/**
	 * Add a new XMLUpdate to list
	 * @param u Object XMLUpdate the inserted being
	 */
	public void add(XMLUpdate u)
	{
		lstUpdate.add(u);
		sorted = false;
	}
	
	public List<XMLUpdate> list()
	{
		if(!sorted) {
			Collections.sort(lstUpdate, comparator);
			sorted = true;
		}
		return lstUpdate;
	}
	
	private static class XMLUpdateListComparator implements Comparator<XMLUpdate> {
		public int compare(XMLUpdate o1, XMLUpdate o2) {
			//o1.getReferenceNode().getPosition()
			if(o2 instanceof XMLUpdateInsertElement && o1 instanceof XMLUpdateInsertAttribute)
				return 1;
			return o1.getReferenceNode().compareTo(o2.getReferenceNode());
		}
	}
}