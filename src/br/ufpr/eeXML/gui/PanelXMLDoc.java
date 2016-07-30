/*
 * Created on 19/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import br.ufpr.eeXML.v1.IndexedTreeNode;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class PanelXMLDoc extends JPanel
{
	private IndexedTreeNode root;
	private JXMLTree xmlTree;
	private JXMLText xmlText;
	private JXMLUpdateList xmlUpdateList;
	
	public PanelXMLDoc(IndexedTreeNode root,JXMLUpdateList xmlUpdateList)
	{
		this.xmlUpdateList = xmlUpdateList;
		this.root = root;
		initComponent();
	}
	
	private void initComponent()
	{
		JTabbedPane tab = new JTabbedPane();
		
		OptXMLTree optXMLTree = new OptXMLTree();
		xmlTree = new JXMLTree(JXMLNodeParser.parse(root),xmlUpdateList,optXMLTree);
		
		xmlText = new JXMLText(root);
		
		
		tab.add("XML document",new JScrollPane(xmlText));
		tab.add("Indexed XML Tree",createPanel(new JXMLTreeTool(xmlTree,optXMLTree),xmlTree));
		
		setLayout(new BorderLayout());
		add(tab,BorderLayout.CENTER);
	}
	
	private JPanel createPanel(JPanel tool, JPanel comp)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(comp),BorderLayout.CENTER);
		panel.add(tool, BorderLayout.PAGE_START);
		return panel;
	}
}
