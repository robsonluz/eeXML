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
public class Teste2 
{

	
	public static void main(String args[])
	{
		try{
			String xmlFile = "usecase/7201532833308399.xml";
			String dtdFile = "usecase/hibernate-mapping-3.0.dtd";
			
			IndexedTreeNode xmlRoot = new XMLTreeDOMParser(new FileInputStream(xmlFile)).getRootNode();
			TreeAutomata treeAutomata = DTDToTreeAutomata.transform(dtdFile);

			//JXMLNode root = JXMLNodeParser.parse(xmlRoot);
			//root.addChild(createTeste());
			//root.addChild(createTeste());
			
//			JScrollPane scroll = new JScrollPane();
//			JPanel pnl = new JPanel(new BorderLayout());
//			pnl.add(scroll,BorderLayout.CENTER);
//			scroll.setViewportView(new JXMLTree(root));
			
			JFrame frm = new JFrame("Teste");
			RuntimeGui.getInstance().setMainFrame(frm);
			frm.setSize(800,600);
			frm.setContentPane(new PanelEvolution(xmlRoot,treeAutomata));
			frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frm.setVisible(true);
			
		}catch(Exception e){
		}
		
	}
}