/*
 * Created on 18/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.IndexedTreeNodeType;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class JXMLText extends JPanel 
{
	//private IndexedTreeNode root;
	private FontMetrics metrics;
    private int fontHeight;

    private int initialX;
    private int currentY;
    private static final int space = 10;
    private static final int paragraphSpace = 0;
    private static final int tab = 15;
    
    private static Color elementColor = new Color(0,0,180);
    private static Color attributeColor = new Color(120,0,0);
    private static Color textColor = new Color(0,120,0);
    private static Color dataColor = Color.BLACK;
    
    private List<NodeExpansor> lstExpansor;
    private Element root;
    private NodeExpansor selectedExpansor;
    
    private int widthSize;
	
	public JXMLText(IndexedTreeNode root)
	{
		//this.root = root;
		lstExpansor = new LinkedList();
		setXMLDoc(root);
		setBackground(Color.WHITE);
		initActions();
	}
	
	@Override protected void paintComponent(Graphics g) 
	{
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        initialX = 16;
        currentY = 0;
        if(metrics==null){
        	metrics = g2.getFontMetrics();
        	fontHeight = metrics.getHeight();
        }
        
        //FontMetrics metrics = g2.getFontMetrics();
        
		paint(g2,root,0);
		g2.setColor(Color.DARK_GRAY);
		g2.drawLine(initialX-3,5,initialX-3,currentY);
		
        setPreferredSize(new Dimension(widthSize+10,currentY+10));
        revalidate();
	}
	
	
	private void paint(Graphics2D g2, Element node,int level)
	{
		
		int currentX = initialX+(level*tab);
		currentY+= (fontHeight)+paragraphSpace;
		
        String text = "<"+node.name;
        if(!node.hasAttributes())
        	text += ">";
        drawElement(g2,text,currentX,currentY);
        currentX+=metrics.stringWidth(text);

        int expansorY = currentY;
        
        //Attributes
        if(node.hasAttributes())
        {
            for(Attribute att:node.attributes)
            {
            	currentX+=drawAttribute(g2,att,currentX,currentY);
            }
			drawElement(g2,">",currentX,currentY);
			currentX++;
        }
        
        //Data
        if(!node.hasElements()){
    		drawData(g2,node.value,currentX,currentY);
    		currentX+=metrics.stringWidth(node.value);
        }
        if(currentX>widthSize)
        	widthSize = currentX;
        
        if(node.expansed){
	        //Elements
	        for(Element ele:node.elements)
	        {
	        	paint(g2,ele,level+1);
	        }
	        
	        if(node.hasElements()){
	        	currentY+= (fontHeight)+paragraphSpace;
	        	currentX = initialX+(level*tab);
	        }
	        text = "</"+node.name+">";
	        drawElement(g2,text,currentX,currentY);
        }
        
        //Expansor
        if(node.hasElements())
        {
        	node.expansor.draw(g2,expansorY,currentY);
        }

	}
	
	private int drawAttribute(Graphics2D g2, Attribute att, int x, int y)
	{
		g2.setColor(attributeColor);
		String text = " "+att.name+"=";
		g2.drawString(text,x,y);
		int add = metrics.stringWidth(text);
		text = "\""+att.value+"\"";
		g2.setColor(textColor);
		g2.drawString(text,x+add,y);
		add += metrics.stringWidth(text);
		return add;
	}
	
	private void drawElement(Graphics2D g2,String text,int x,int y)
	{
		g2.setColor(elementColor);
		g2.drawString(text,x,y);
	}
	
	private void drawData(Graphics2D g2,String text,int x,int y)
	{
		g2.setColor(dataColor);
		g2.drawString(text,x,y);
	}	
	
	
	private void initActions()
	{
		addMouseListener(new MouseAdapter() {
			@Override public void mouseReleased(MouseEvent e) {
				mouseReleasedAction(e);
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override public void mouseMoved(MouseEvent e) {
				mouseMovedAction(e);
			}
		});
	}
	
	public void mouseReleasedAction(MouseEvent e)
	{
		if(selectedExpansor!=null)
		{
			selectedExpansor.element.expansed=!selectedExpansor.element.expansed;
			repaint();
		}
	}
	
	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	private boolean actionSelectPerformed=false;
	public void mouseMovedAction(MouseEvent e)
	{
		//if(e.getX()>2 && e.getX()<10)
		{
			if(selectedExpansor!=null && !actionSelectPerformed)
			{
				selectedExpansor.selected = true;
				actionSelectPerformed=true;
				repaint();
				setCursor(HAND_CURSOR);
			}
			NodeExpansor n = findPick(e.getY());
			if(n==null && selectedExpansor!=null)
			{
				selectedExpansor.selected=false;
				repaint();
				actionSelectPerformed=false;
				setCursor(DEFAULT_CURSOR);
			}
			selectedExpansor = n;
			//actionSelectPerformed = selectedExpansor!=null;
		}		
	}
	private NodeExpansor findPick(int y)
	{
		return findPick(y,this.root);
	}
	private NodeExpansor findPick(int y,Element ele)
	{
		if(ele.expansor!=null && ele.expansor.y>y-2 && ele.expansor.y<y+11)
			return ele.expansor;
		if(ele.expansed){
			for(Element e: ele.elements)
			{
				NodeExpansor node = findPick(y,e);
				if(node!=null)
					return node;
			}
		}
		return null;
	}
	
	private static final Color normalNodeColor = new Color(0.7f,0.8f,0.7f);
	private static final Color selectedNodeColor = new Color(0.1f,0.1f,0.1f);
	private static final Color lineNodeColor = new Color(0.5f,0.5f,0.5f);
	private class NodeExpansor
	{
		Element element;
		boolean selected;
		int y;
		
		public NodeExpansor(Element element)
		{
			this.element = element;
		}
		
		public void draw(Graphics2D g2,int y,int y2)
		{
			this.y = y;
			if(element.expansed)
			{
				g2.setColor(Color.BLACK);
				g2.drawString("-",5,y);
				if(selected){
					g2.setColor(lineNodeColor);
					g2.drawLine(6,y,6,y2-5);
					g2.drawLine(6,y2-5,9,y2-5);
					g2.setColor(selectedNodeColor);
				}else{ 
					g2.setColor(normalNodeColor);
				}
				g2.drawRect(2,y-8,8,8);
				
				
			}else{
				g2.setColor(Color.BLACK);
				g2.drawString("+",3,y+1);
				if(selected) g2.setColor(selectedNodeColor);
				else g2.setColor(normalNodeColor);
				g2.drawRect(2,y-8,8,8);
			}
		}
	}
	
	public void setXMLDoc(IndexedTreeNode root)
	{
		this.root = parse(root);
	}
	
	private Element parse(IndexedTreeNode node)
	{
		Element e = new Element(node.getName(),getData(node));
		for(IndexedTreeNode n:node.getChildren())
		{
			if(n.getType()==IndexedTreeNodeType.ATTRIBUTE)
			{
				e.attributes.add(new Attribute(n.getName(),"data"));
			}else if(n.getType()==IndexedTreeNodeType.ELEMENT){
				e.elements.add(parse(n));
			}
		}
		if(e.hasElements())
			e.expansor = new NodeExpansor(e);
		return e;
	}
	
	private String getData(IndexedTreeNode node)
	{
		try{
			for(IndexedTreeNode n:node.getChildren())
				if(n.getType()==IndexedTreeNodeType.DATA)
					return n.getName();
		}catch(Exception e){}
		return "";
	}
	
	private class Element
	{
		private String name;
		private String value;
		private boolean expansed=true;
		private List<Attribute> attributes;
		private List<Element> elements;
		private NodeExpansor expansor;

		public Element(String name,String value)
		{
			this.name = name;
			this.value = value;
			this.attributes = new LinkedList();
			this.elements = new LinkedList();
		}
		public boolean hasAttributes()
		{
			return attributes.size()>0;
		}
		public boolean hasElements()
		{
			return elements.size()>0;
		}
	}
	
	private class Attribute
	{
		private String name;
		private String value;
		public Attribute(String name,String value)
		{
			this.name = name;
			this.value = value;
		}
	}
}
