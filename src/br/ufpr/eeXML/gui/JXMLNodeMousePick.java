/*
 * Created on 12/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.event.MouseEvent;


/*
 * @autor Robson João Padilha da Luz
 *
 */
public class JXMLNodeMousePick
{
	private OptXMLTree options;
	private JXMLNode root;
	private int w = 0;
	private int h = 10; 
	public JXMLNodeMousePick(JXMLNode root,OptXMLTree options)
	{
		this.root=root;
		this.options = options;
		this.w = options.getWidthDistance()/2;
	}
	
	public JXMLNode findPick(MouseEvent ev)
	{
		int x = ev.getX();
		int y = ev.getY();
		return findPick(x,y,root);
	}
	
	private JXMLNode findPick(int x, int y, JXMLNode node)
	{
		if(checkPick(node,x,y))
			return node;
		if(node.isChildsVisible())
		for(JXMLNode child:node.getChilds())
		{
			JXMLNode n = findPick(x,y,child);
			if(n!=null) return n;
		}
		return null;
	}
	
	private boolean checkPick(JXMLNode node,int x, int y)
	{
		int nx = node.getPosX();
		int ny = node.getPosY()-8;
		
		if(x>=nx-w && x<=nx+w &&
		   y>=ny-h && y<=ny+h) 
			return true;
		return false;
	}
}