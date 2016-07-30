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

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class Teste 
{
	/*
	private static JXMLNode createTeste()
	{
		JXMLNode root = new JXMLNode("node root",200,100);
		root.addChild(new JXMLNode("teste1"));
		root.addChild(new JXMLNode("teste2"));
		root.addChild(new JXMLNode("teste3"));
		
		
		JXMLNode teste4 = new JXMLNode("teste4");
		teste4.addChild(new JXMLNode("sub1"));
		teste4.addChild(new JXMLNode("sub2"));
		teste4.addChild(new JXMLNode("sub3"));
		teste4.addChild(new JXMLNode("sub4"));
		teste4.addChild(new JXMLNode("sub5"));
		teste4.addChild(new JXMLNode("sub6"));
		root.addChild(teste4);
		
		JXMLNode teste5 = new JXMLNode("teste5");
		teste5.addChild(new JXMLNode("sub1"));
		teste5.addChild(new JXMLNode("sub2"));
		teste5.addChild(new JXMLNode("sub3"));
		teste5.addChild(new JXMLNode("sub4"));
		teste5.addChild(new JXMLNode("sub5"));
		root.addChild(teste5);
		
		return root;
	}
	*/
	
	public static void main(String args[])
	{
		try{
			//String xmlFile = "cliente.xml";
			String xmlFile = "usecase/7201532833308399.xml";
			//String xmlFile = "server.xml";
			//String xmlFile = "sdn.xml";
			
			//String dtdFile = "cliente.dtd";
			
			IndexedTreeNode xmlRoot = new XMLTreeDOMParser(new FileInputStream(xmlFile)).getRootNode();
			JXMLNode root = JXMLNodeParser.parse(xmlRoot);
			//root.addChild(createTeste());
			//root.addChild(createTeste());
			
			JScrollPane scroll = new JScrollPane();
			JPanel pnl = new JPanel(new BorderLayout());
			pnl.add(scroll,BorderLayout.CENTER);
			scroll.setViewportView(new JXMLTree(root,null,new OptXMLTree()));
			
			JFrame frm = new JFrame("Teste");
			RuntimeGui.getInstance().setMainFrame(frm);
			frm.setSize(400,400);
			frm.setContentPane(pnl);
			frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frm.setVisible(true);
			
		}catch(Exception e){
		}
		
	}
}