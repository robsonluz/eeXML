/*
 * Created on 18/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;

import br.ufpr.eeXML.v1.SchemaEvolution;
import br.ufpr.eeXML.v1.XMLUpdate;
import br.ufpr.eeXML.v1.XMLUpdateInsertElement;
import br.ufpr.eeXML.v1.XMLUpdateList;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class JXMLUpdateList extends JPanel 
{
	private JList list;
	private XMLUpdateList updateList;
	private DefaultListModel data;
	
	
	public JXMLUpdateList()
	{
		init();
	}
	
	private void init()
	{
		setLayout(new BorderLayout());
		updateList = new XMLUpdateList();
		
		list = new JList();
		list.setCellRenderer(new UpdateListCellRenderer());
		
//		data = new AbstractListModel() {
//			public int getSize() {
//				return updateList.list().size();
//			}
//			public Object getElementAt(int index) {
//				return updateList.list().get(index);
//			}
//		};
		data = new DefaultListModel();
		list.setModel(data);
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(list);
		
		JTabbedPane tab = new JTabbedPane();
		tab.add("Update List",scroll);
		super.add(tab,BorderLayout.CENTER);
		

	}
	

	
	public void addXMLUpdate(XMLUpdate xu)
	{
		updateList.add(xu);
		data.addElement(xu);
		//list.setModel(data);
		//list.repaint();
	}

	public XMLUpdateList getUpdateList() {
		return updateList;
	}
}

class UpdateListCellRenderer extends DefaultListCellRenderer
{
	final static ImageIcon insertElementIcon = new ImageIcon("long.gif");
	final static ImageIcon deleteElementIcon = new ImageIcon("long.gif");
	final static ImageIcon insertAttributeIcon = new ImageIcon("long.gif");
	final static ImageIcon deleteAttributeIcon = new ImageIcon("long.gif");

	@Override public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
	{
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		XMLUpdate u = (XMLUpdate) value;
		if(u instanceof XMLUpdateInsertElement)
			setIcon(insertElementIcon);
		setText(u.toString());
        return this;
	}
}

