/*
 * Created on 17/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;

import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.IndexedTreeNodeType;
import br.ufpr.eeXML.v1.XMLUpdateInsertElement;
import br.ufpr.eexmlide.PanelInsertElement;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class PanelInsertElementHandler extends PanelInsertElement 
{
	private JDialog dialog;
	private JXMLTree xmlTree;
	private JXMLNode node;
	
	public PanelInsertElementHandler(JXMLNode node,JXMLTree xmlTree)
	{
		super();
		this.node = node;
		this.xmlTree = xmlTree;
		init();
	}
	
	private void init()
	{
		dialog = new JDialog(RuntimeGui.getInstance().getMainFrame(),"Insert Element",true);
		dialog.setContentPane(this);
		dialog.pack();
		dialog.setVisible(true);
	}

	@Override protected void cmdCancelActionPerformed(ActionEvent evt) 
	{
		dialog.setVisible(false);
	}

	@Override protected void cmdInsertActionPerformed(ActionEvent evt) 
	{
		if(tabElement.getSelectedIndex()==0)//Single Element
		{
			IndexedTreeNode parent = node.getValue().getParent();
			if(parent!=null)
			{
				int index = parent.getChildren().indexOf(node.getValue());
				if(rdoInsertAfter.isSelected())
					index++;
				
				IndexedTreeNode snew = new IndexedTreeNode(txtElement.getText(),0,IndexedTreeNodeType.ELEMENT,parent);
				snew.setInsertedNew(true);

				parent.getChildren().add(index,snew);
				//parent.addChild(snew);
				JXMLNode xmlNode = new JXMLNode(snew);
				xmlNode.setParent(node.getParent());
				node.getParent().getChilds().add(index,xmlNode);
				
				
				IndexedTreeNode data = new IndexedTreeNode("#text",0,snew);
				data.setInsertedNew(false);
				snew.addChild(data);
				JXMLNode xmlData = new JXMLNode(data);
				xmlData.setParent(xmlNode);
				xmlNode.addChild(xmlData);
				
				node.getParent().updateChildsPosition(xmlTree.getOptions());
				dialog.setVisible(false);
				xmlTree.repaint();
				//XMLUpdateInsertElement xmlUpdate = new XMLUpdateInsertElement(txtElement.getText(),node.getValue(),XMLUpdateInsertElement.INSERT_AFTER);
				//if(rdoInsertBefore.isSelected())
				//	xmlUpdate.setType(XMLUpdateInsertElement.INSERT_BEFORE);
				//xmlTree.getJUpdateList().addXMLUpdate(xmlUpdate);
			}
		}else{//SubTree Element
		}
	}
}