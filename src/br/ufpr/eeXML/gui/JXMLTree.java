/*
 * Created on 12/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class JXMLTree extends JPanel implements MouseListener,MouseMotionListener
{
	private JXMLNode root;
	
	private JXMLNodeMousePick nodeMousePick;
	private JXMLNode selectedNode;
	private boolean selectPerformed=false;
	private JXMLTreeModificationHandler modificationHandler;
	private JXMLUpdateList jUpdateList;
	
	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	
	private OptXMLTree options;
	private BufferedImage bufImage;
	
	public JXMLTree(JXMLNode root,JXMLUpdateList jUpdateList,OptXMLTree options)
	{
		this.options = options;
		this.jUpdateList = jUpdateList;
		this.root = root;
		this.root.setPosition(200,30);
		this.root.updateChildsPosition(options);
		root.setChildsVisible(true);
		nodeMousePick = new JXMLNodeMousePick(root,options);
		addMouseMotionListener(this);
		addMouseListener(this);
		setBackground(Color.WHITE);
		modificationHandler = new JXMLTreeModificationHandler(this.root,this);
		add(modificationHandler.getMnuOptionElement());
	}
	
	@Override public void paintComponent(Graphics g)
	{
		
		//revalidate();
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //g2.clearRect(0,0,400,400);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        Dimension treeSize = new Dimension(0,0);
		root.paint(g2,treeSize,options);
		
		treeSize.height+=10;
        setPreferredSize(treeSize);
        revalidate();

		
	}

	
	public void mouseMoved(MouseEvent e) 
	{
		if(selectedNode!=null && !selectPerformed)
			performNodeSelection();
		
		JXMLNode node = nodeMousePick.findPick(e);
		if(node!=null){
			if(selectedNode!=null && node!=selectedNode)
				selectedNode.setSelected(false);
			selectPerformed = false;
			selectedNode = node;
		}else if(selectedNode!=null){
			selectedNode.setSelected(false);
			repaint();
			selectedNode=null;
			setCursor(DEFAULT_CURSOR);
		}
	}
	
	private void performNodeSelection()
	{
		selectedNode.setSelected(true);
		selectPerformed=true;
		repaint();
		setCursor(HAND_CURSOR);
	}
	
	public void mouseDragged(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton()==MouseEvent.BUTTON1){
			if(selectedNode!=null)
			{
				//if(e.getButton()!=MouseEvent.BUTTON3 || !selectedNode.isChildsVisible())
				selectedNode.setChildsVisible(!selectedNode.isChildsVisible());
				JXMLNode parent = selectedNode.getParent();
				if(parent!=null)
				{
					for(JXMLNode n:parent.getChilds())
						if(n!=selectedNode)
							n.setChildsVisible(false);
				}
				repaint();
			}
		}else if(e.getButton()==MouseEvent.BUTTON3){
			modificationHandler.displayPopMenu(selectedNode,e);
		}
	}



	public Dimension getPreferredScrollableViewportSize() {
		return getSize();
	}



	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        switch(orientation) {
        case SwingConstants.VERTICAL:
            return visibleRect.height;
        case SwingConstants.HORIZONTAL:
            return visibleRect.width;
        default:
            throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
	}



	public boolean getScrollableTracksViewportHeight() {
		if (getParent() instanceof JViewport) {
		    return (((JViewport)getParent()).getHeight() > getPreferredSize().height);
		}
		return false;
	}



	public boolean getScrollableTracksViewportWidth() {
		if (getParent() instanceof JViewport) {
		    return (((JViewport)getParent()).getWidth() > getPreferredSize().width);
		}
		return false;
	}



	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        switch(orientation) {
        case SwingConstants.VERTICAL:
            return visibleRect.height / 10;
        case SwingConstants.HORIZONTAL:
            return visibleRect.width / 10;
        default:
            throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
	}



	public JXMLUpdateList getJUpdateList() {
		return jUpdateList;
	}



	public OptXMLTree getOptions() {
		return options;
	}



	public JXMLNode getRootNode() {
		return root;
	}
}
