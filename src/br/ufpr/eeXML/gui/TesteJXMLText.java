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

import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.XMLTreeDOMParser;
import br.ufpr.eeXML.v1.treeautomata.DTDToTreeAutomata;
import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class TesteJXMLText 
{
	
	public static void main(String args[])
	{
		try{
			String xmlFile = "usecase/7201532833308399.xml";
			//String xmlFile = "server.xml";
			//String xmlFile = "sdn.xml";
			
			//String dtdFile = "cliente.dtd";
			
			IndexedTreeNode xmlRoot = new XMLTreeDOMParser(new FileInputStream(xmlFile)).getRootNode();
			
			JFrame frm = new JFrame("Teste");
			RuntimeGui.getInstance().setMainFrame(frm);
			frm.setSize(400,400);
			frm.setContentPane(new JXMLText(xmlRoot));
			frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frm.setVisible(true);
		}catch(Exception e){
		}
		
	}
}