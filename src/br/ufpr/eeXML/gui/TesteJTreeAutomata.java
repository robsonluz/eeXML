/*
 * Created on 12/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.BorderLayout;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.ufpr.eeXML.v1.SchemaEvolution;
import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.IndexedTreeNodeHelper;
import br.ufpr.eeXML.v1.XMLTreeDOMParser;
import br.ufpr.eeXML.v1.XMLUpdateInsertElement;
import br.ufpr.eeXML.v1.XMLUpdateList;
import br.ufpr.eeXML.v1.treeautomata.DTDToTreeAutomata;
import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class TesteJTreeAutomata 
{
	
	public static void main(String args[])
	{
		try{
			/*
			String xmlFile = "cliente.xml";
			//String xmlFile = "server.xml";
			//String xmlFile = "sdn.xml";
			
			String dtdFile = "cliente.dtd";
			
			//IndexedTreeNode xmlRoot = new XMLTreeDOMParser(new FileInputStream(xmlFile)).getRootNode();
			TreeAutomata treeAutomata = DTDToTreeAutomata.transform(dtdFile);
			*/
			
			String xmlFile = "teste1.xml";
			String dtdFile = "teste1.dtd";
			
			IndexedTreeNode xmlRoot = new XMLTreeDOMParser(new FileInputStream(xmlFile)).getRootNode();
			TreeAutomata treeAutomata = DTDToTreeAutomata.transform(dtdFile);
			
			XMLUpdateList lstUpdate = new XMLUpdateList();
			
			IndexedTreeNode elementNome = IndexedTreeNodeHelper.findByPosition(xmlRoot,0,3);
			lstUpdate.add(new XMLUpdateInsertElement("bairro",elementNome,XMLUpdateInsertElement.INSERT_AFTER,true));

			//IndexedTreeNode clienteNode = IndexedTreeNodeHelper.findByPosition(xmlRoot,-1,0);
			//lstUpdate.add(new XMLUpdateInsertElement("duplicatas",clienteNode,XMLUpdateInsertElement.INSERT_AFTER));
			
			
			
			
			SchemaEvolution ev = new SchemaEvolution(xmlRoot,treeAutomata,lstUpdate);
			TreeAutomata evTreeAutomata = ev.performEvolution();
			
			JFrame frm = new JFrame("Teste");
			RuntimeGui.getInstance().setMainFrame(frm);
			frm.setSize(400,400);
			frm.setContentPane(new JTreeAutomata(evTreeAutomata));
			frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frm.setVisible(true);
		}catch(Exception e){
		}
		
	}
}