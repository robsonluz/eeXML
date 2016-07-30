/*
 * Created on 06/04/2006
 *
 */
package br.ufpr.eeXML.v1;

import java.io.FileInputStream;

import br.ufpr.eeXML.v1.treeautomata.DTDToTreeAutomata;
import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class SchemaEvolution 
{
	private IndexedTreeNode xmlRoot;
	private TreeAutomata treeAutomata;
	private XMLUpdateList lstUpdate;
	
	public SchemaEvolution(IndexedTreeNode xmlRoot, TreeAutomata treeAutomata,XMLUpdateList lstUpdate)throws Exception
	{
		this.xmlRoot = xmlRoot;
		this.treeAutomata = treeAutomata;
		//setDTDFile(dtdFile);
		setLstUpdate(lstUpdate);
	}
	
	public TreeAutomata performEvolution()
	{
		for(XMLUpdate up:lstUpdate.list())
		{
			up.performUpdate(treeAutomata,xmlRoot);
		}
		return treeAutomata;
	}
	
	public void setXMLFile(String xmlFile)throws Exception
	{
		xmlRoot = new XMLTreeDOMParser(new FileInputStream(xmlFile)).getRootNode();
	}
	public void setDTDFile(String dtdFile)throws Exception
	{
		treeAutomata = DTDToTreeAutomata.transform(dtdFile);
	}

	public XMLUpdateList getLstUpdate(){
		return lstUpdate;
	}
	public void setLstUpdate(XMLUpdateList lstUpdate) {
		this.lstUpdate = lstUpdate;
	}
}