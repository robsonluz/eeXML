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
public class Teste 
{
	public static void main(String args[])
	{
		try{
			String xmlFile = "cliente.xml";
			String dtdFile = "cliente.dtd";
			
			IndexedTreeNode xmlRoot = new XMLTreeDOMParser(new FileInputStream(xmlFile)).getRootNode();
			TreeAutomata treeAutomata = DTDToTreeAutomata.transform(dtdFile);
			
			XMLUpdateList lstUpdate = new XMLUpdateList();
			
			IndexedTreeNode elementNome = IndexedTreeNodeHelper.findByPosition(xmlRoot,0,1);
			lstUpdate.add(new XMLUpdateInsertElement("telefone",elementNome,XMLUpdateInsertElement.INSERT_AFTER));

			//IndexedTreeNode clienteNode = IndexedTreeNodeHelper.findByPosition(xmlRoot,-1,0);
			//lstUpdate.add(new XMLUpdateInsertElement("duplicatas",clienteNode,XMLUpdateInsertElement.INSERT_AFTER));
			
			
			
			
			SchemaEvolution ev = new SchemaEvolution(xmlRoot,treeAutomata,lstUpdate);
			TreeAutomata evTreeAutomata = ev.performEvolution();
			System.out.println("\n\n-----------------------------------RESULTADO--------------------------------");
			evTreeAutomata.printTreeAutomata();
			
			//System.out.println();
			System.out.println(IndexedTreeNodeHelper.findByPosition(xmlRoot,-1,0));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
