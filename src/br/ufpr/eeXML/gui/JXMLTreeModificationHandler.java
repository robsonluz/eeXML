/*
 * Created on 17/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import br.ufpr.eeXML.v1.IndexedTreeNodeType;
import br.ufpr.eeXML.v1.XMLUpdateDeleteAttribute;
import br.ufpr.eeXML.v1.XMLUpdateDeleteElement;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class JXMLTreeModificationHandler implements ActionListener
{
	private JXMLNode root;

    private javax.swing.JMenuItem mnuDeleteAttribute;
    private javax.swing.JMenuItem mnuDeleteElement;
    private javax.swing.JMenuItem mnuInsertAttribute;
    private javax.swing.JMenuItem mnuInsertElement;
    private javax.swing.JPopupMenu mnuOptionElement;
    private javax.swing.JPopupMenu mnuOptionAttribute;
    
    private JXMLNode node;
    private JXMLTree xmlTree;
	
	public JXMLTreeModificationHandler(JXMLNode root, JXMLTree xmlTree)
	{
		this.root = root;
		this.xmlTree = xmlTree;
		initPopMenu();
	}
	
	private void initPopMenu()
	{
        mnuOptionElement = new javax.swing.JPopupMenu();
        mnuOptionAttribute = new JPopupMenu();
        mnuInsertElement = new javax.swing.JMenuItem("Insert Element (After/Before)");
        mnuDeleteElement = new javax.swing.JMenuItem("Delete Element (This)");
        mnuInsertAttribute = new javax.swing.JMenuItem("Insert Attribute");
        mnuDeleteAttribute = new javax.swing.JMenuItem("Delete Attribute");
        
        mnuInsertElement.addActionListener(this);
        mnuInsertElement.setActionCommand("insert_element");
        
        mnuDeleteElement.addActionListener(this);
        mnuDeleteElement.setActionCommand("delete_element");
        
        mnuInsertAttribute.addActionListener(this);
        mnuInsertAttribute.setActionCommand("insert_attribute");
        
        mnuDeleteAttribute.addActionListener(this);
        mnuDeleteAttribute.setActionCommand("delete_attribute");
        
        mnuOptionElement.add(mnuInsertElement);
        mnuOptionElement.add(mnuDeleteElement);
        mnuOptionElement.add(mnuInsertAttribute);
        //mnuOptionElement.add(mnuDeleteAttribute);
        mnuOptionAttribute.add(mnuDeleteAttribute);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(node!=null){
			String act = e.getActionCommand();
			if(act!=null)
			{
				if(act.equals("insert_element"))
				{
					new PanelInsertElementHandler(node,this.xmlTree);
				}else if(act.equals("delete_element")){
					XMLUpdateDeleteElement del = new XMLUpdateDeleteElement(node.getValue());
					xmlTree.getJUpdateList().addXMLUpdate(del);
					node.setDeleted(true);
					xmlTree.repaint();
				}else if(act.equals("insert_attribute")){
					new PanelInsertAttributeHandler(node,this.xmlTree);
				}else if(act.equals("delete_attribute")){
					XMLUpdateDeleteAttribute del = new XMLUpdateDeleteAttribute(node.getValue());
					xmlTree.getJUpdateList().addXMLUpdate(del);
					node.setDeleted(true);
					xmlTree.repaint();
				}
			}
		}
	}
	
	public void displayPopMenu(JXMLNode selectedNode, MouseEvent e)
	{
        if (e.isPopupTrigger() && selectedNode!=null) {
        	node = selectedNode;
        	if(node.getValue().getType()==IndexedTreeNodeType.ELEMENT)
        	{
        		mnuOptionElement.show(e.getComponent(),e.getX(),e.getY());
        	}else if(node.getValue().getType()==IndexedTreeNodeType.ATTRIBUTE){
        		mnuOptionAttribute.show(e.getComponent(),e.getX(),e.getY());
        	}
        }
	}

	public JPopupMenu getMnuOptionElement() {
		return mnuOptionElement;
	}
}