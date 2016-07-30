/*
 * Created on 19/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class PanelXMLDtd extends JPanel
{
	private TreeAutomata ta;
	private JTreeAutomata jTa;
	
	public PanelXMLDtd(TreeAutomata ta)
	{
		this.ta = ta;
		initComponent();
	}
	
	private void initComponent()
	{
		JTabbedPane tab = new JTabbedPane();
		jTa = new JTreeAutomata(ta);
		tab.add("DTD document",new JLabel("DTD"));
		tab.add("Tree Automata",jTa);
		
		setLayout(new BorderLayout());
		add(tab,BorderLayout.CENTER);
	}
}
