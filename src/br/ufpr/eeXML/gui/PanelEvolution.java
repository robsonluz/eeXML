/*
 * Created on 18/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class PanelEvolution extends JPanel
{
	//private JXMLNode xmlDoc;
	private IndexedTreeNode xmlDoc;
	//private JXMLUpdateList jUpdateList;
	private JXMLEvolution jXMLEvolution;
	private PanelXMLDoc pnlXmlDoc;
	private PanelXMLDtd pnlXmlDtd;
	private TreeAutomata ta;
	
	public PanelEvolution(IndexedTreeNode xmlDoc,TreeAutomata ta)
	{
		this.xmlDoc = xmlDoc;
		this.ta = ta;
		initComponent();
	}
	
	private void initComponent()
	{
		jXMLEvolution = new JXMLEvolution(xmlDoc,ta);
		pnlXmlDoc = new PanelXMLDoc(xmlDoc,jXMLEvolution.getJUpdateList());
		pnlXmlDtd = new PanelXMLDtd(ta);
		
		JSplitPane s1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pnlXmlDoc,pnlXmlDtd);
        s1.setOneTouchExpandable(true);
        s1.setDividerLocation(400);
		
		JSplitPane s2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,s1,jXMLEvolution);
        s2.setOneTouchExpandable(true);
        s2.setDividerLocation(400);

		
		
		setLayout(new BorderLayout());
		add(s2,BorderLayout.CENTER);
		
		
		//s2.setDividerLocation(100);
		//s1.setDividerLocation(0.5d);
	}
}