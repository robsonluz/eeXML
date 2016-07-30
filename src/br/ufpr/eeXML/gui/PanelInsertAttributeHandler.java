/*
 * Created on 17/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.IndexedTreeNodeType;
import br.ufpr.eeXML.v1.XMLUpdateInsertAttribute;
import br.ufpr.eeXML.v1.XMLUpdateInsertElement;
import br.ufpr.eexmlide.PanelInsertElement;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class PanelInsertAttributeHandler extends JPanel 
{
	private JDialog dialog;
	private JXMLTree xmlTree;
	private JXMLNode node;
	private JTextField txtName;
	
	public PanelInsertAttributeHandler(JXMLNode node,JXMLTree xmlTree)
	{
		super();
		this.node = node;
		this.xmlTree = xmlTree;
		init();
	}
	
	private void init()
	{
		Dimension d = new Dimension(200,20);
		txtName = new JTextField("");
		txtName.setPreferredSize(d);
		JPanel pnl = new JPanel();
		pnl.add(new JLabel("Name: "));
		pnl.add(txtName);
		
		setLayout(new BorderLayout());
		add(pnl,BorderLayout.CENTER);
		
		pnl = new JPanel();
		d = new Dimension(120,26);
		JButton cmdInsert = new JButton("Insert Attribute");
		cmdInsert.setPreferredSize(d);
		cmdInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmdInsertActionPerformed(e);
			}
		});
		JButton cmdCancel = new JButton("Cancel");
		cmdCancel.setPreferredSize(d);
		cmdCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmdCancelActionPerformed(e);
			}
		});
		pnl.add(cmdInsert);
		pnl.add(cmdCancel);
		
		add(pnl,BorderLayout.PAGE_END);
		
		dialog = new JDialog(RuntimeGui.getInstance().getMainFrame(),"Insert Attribute",true);
		dialog.setResizable(false);
		dialog.setContentPane(this);
		dialog.pack();
		dialog.setLocation(RuntimeGui.getInstance().getMainFrame().getMousePosition());
		dialog.setVisible(true);
		
	}

	private void cmdCancelActionPerformed(ActionEvent evt) 
	{
		dialog.setVisible(false);
	}

	private void cmdInsertActionPerformed(ActionEvent evt) 
	{
		if(txtName.getText().length()<=0)
		{
			JOptionPane.showMessageDialog(this,"Name is obligatory!");
			txtName.setFocusable(true);
			return;
		}
		XMLUpdateInsertAttribute up = new XMLUpdateInsertAttribute(txtName.getText(), node.getValue());
		xmlTree.getJUpdateList().addXMLUpdate(up);
		dialog.setVisible(false);
	}
}