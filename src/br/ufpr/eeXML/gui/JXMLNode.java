/*
 * Created on 12/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.IndexedTreeNodeType;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class JXMLNode 
{
	private IndexedTreeNode value;
	private int posX;
	private int posY;
	private JXMLNode parent;
	private List<JXMLNode> childs;
	private boolean childsVisible;
//	public static final int LENGTH = 80;
//	public static final int DISTANCE_HEIGHT = 40;
	private boolean selected;
	private boolean deleted;
	
	private static final Color dataColor = new Color(0,150,0);
	private static final Color deleteColor = new Color(180,0,0);
	
    final static float dash1[] = {3.0f};	
    final static BasicStroke dashed = new BasicStroke(1.0f, 
            BasicStroke.CAP_BUTT, 
            BasicStroke.JOIN_MITER, 
            10.0f, dash1, 0.0f);
    final static BasicStroke stroke = new BasicStroke(1.0f);    
    final static BasicStroke wideStroke = new BasicStroke(3.0f);
    
    final static AlphaComposite alphaTransparency = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f);
    final static AlphaComposite alphaNormal = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
    
    private int widthSize=0;
    private int heightSize=0;
	
	
	public JXMLNode(IndexedTreeNode value,int posX, int posY)
	{
		childs = new LinkedList();
		this.value = value;
		this.posX = posX;
		this.posY = posY;
	}
	public JXMLNode(IndexedTreeNode value)
	{
		childs = new LinkedList();
		this.value = value;
		this.posX = posX;
		this.posY = posY;
	}	
	
	public void addChild(JXMLNode node)
	{
		node.setParent(this);
		childs.add(node);
		//updateChildsPosition(options);
	}
	
	private void adjustSize(Dimension treeSize)
	{
		if(posY>treeSize.height)
			treeSize.height = posY;
	}
	
	public void paint(Graphics2D g2,Dimension treeSize,OptXMLTree options)
	{
		adjustSize(treeSize);
        if(childsVisible)
        {
        	for(JXMLNode node:childs)
        	{
        		
        		g2.setColor(new Color(180,180,180));
        		if(node.getValue().isInsertedNew())
        			g2.setStroke(this.dashed);
        		else
        			g2.setStroke(this.stroke);
        		g2.drawLine(posX,posY,node.posX,node.posY);
        		node.paint(g2,treeSize,options);
        	}
        }
        
        String text = null;
        if(options.isDisplayPositions())
        	text = value.toString();
        else
        	text = value.getName();
        
        if(value.getType()==IndexedTreeNodeType.ATTRIBUTE)
        	text = "@"+text;

        FontMetrics metrics = g2.getFontMetrics();
        int width = metrics.stringWidth( text );
        int height = metrics.getHeight();
        
        if(isSelected()){
        	g2.setStroke(this.dashed);
        	g2.setColor(Color.BLACK);
        	g2.drawRect(posX-(width/2)-5,posY-(height)-5,width+8,height);
        	g2.setStroke(this.stroke);
       		g2.setColor(Color.RED);
        }else{
        	if(options.isDisplayTreeColor())
        	{
	        	if(value.getType()==IndexedTreeNodeType.ATTRIBUTE)
	        		g2.setColor(Color.BLUE);
	        	else if(value.getType()==IndexedTreeNodeType.ELEMENT)
	        		g2.setColor(Color.BLACK);
	        	else if(value.getType()==IndexedTreeNodeType.DATA)
	        		g2.setColor(dataColor);
        	}else{
        		g2.setColor(Color.BLACK);
        	}
        }

        g2.drawString( text, posX-(width/2), posY-(height/2) );
        
        g2.fillOval(posX-2,posY-3,5,5);
        
        if(isDeleted())
        	drawX(g2,posX,posY-10);
	}
	
	private void drawX(Graphics2D g2, int x, int y)
	{
		
		g2.setComposite(alphaTransparency); 
		int len = 7;
		g2.setColor(deleteColor);
		g2.setStroke(wideStroke);
		g2.drawLine(x-len,y-len,x+len,y+len);
		g2.drawLine(x-len,y+len,x+len,y-len);
		g2.setStroke(stroke);
		g2.setComposite(alphaNormal);
	}
	
	public void updateChildsPosition(OptXMLTree options)
	{
		int size = childs.size();
		int ini = posX-(((size-1)*options.getWidthDistance())/2);
    	for(JXMLNode node:childs)
    	{
    		node.posY=this.posY+options.getHeightDistance();
    		node.posX=ini;
    		ini+=options.getWidthDistance();
    		node.updateChildsPosition(options);
    	}		
	}

	public boolean isChildsVisible() 
	{
		return childsVisible;
	}

	public void setChildsVisible(boolean childsVisible) 
	{
		this.childsVisible = childsVisible;
	}

	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public List<JXMLNode> getChilds() {
		return childs;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public JXMLNode getParent() {
		return parent;
	}
	public void setParent(JXMLNode parent) {
		this.parent = parent;
	}
	public void setPosition(int x, int y)
	{
		this.posX = x;
		this.posY = y;
	}
	
	@Override public String toString()
	{
		return value.toString();
	}
	public IndexedTreeNode getValue() {
		return value;
	}
	public void setValue(IndexedTreeNode value) {
		this.value = value;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}