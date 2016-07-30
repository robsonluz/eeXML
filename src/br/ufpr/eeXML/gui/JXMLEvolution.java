/*
 * Created on 06/06/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.SchemaEvolution;
import br.ufpr.eeXML.v1.XMLUpdateList;
import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class JXMLEvolution extends JPanel
{
	private JXMLUpdateList jUpdateList;
	private TreeAutomata treeAutomata;
	private IndexedTreeNode xmlDoc;
	
	public JXMLEvolution(IndexedTreeNode xmlDoc,TreeAutomata treeAutomata)
	{
		this.xmlDoc = xmlDoc;
		this.treeAutomata = treeAutomata;
		initComponents();
	}
	
	private void initComponents()
	{
		setLayout(new BorderLayout());
		
		jUpdateList = new JXMLUpdateList();
		super.add(jUpdateList,BorderLayout.CENTER);
		
		JButton cmdTmp = new JButton("Perform Evolution");
		cmdTmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performEvolution();
			}
		});
		super.add(cmdTmp,BorderLayout.PAGE_END);
	}
	
	private void performEvolution()
	{
		XMLUpdateList updateList = jUpdateList.getUpdateList();
		if(updateList.list().size()>0)
		{
			try{
				SchemaEvolution ev = new SchemaEvolution(xmlDoc,treeAutomata,updateList);
				TreeAutomata ta = ev.performEvolution();
				ta.printTreeAutomata();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public JXMLUpdateList getJUpdateList() {
		return jUpdateList;
	}
}
