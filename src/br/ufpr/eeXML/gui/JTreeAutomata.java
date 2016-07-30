/*
 * Created on 19/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import br.ufpr.eeXML.v1.glushkov.regex.Regex;
import br.ufpr.eeXML.v1.glushkov.regex.RegexUpdateChoices;
import br.ufpr.eeXML.v1.treeautomata.State;
import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class JTreeAutomata extends JPanel
{
	private TreeAutomata ta;
	
	private FontMetrics metrics;
	private FontMetrics metricsItalic;
    private int fontHeight;	
    
    private int initialX;
    private int currentY;
    private int currentX;
    private static final int space = 10;
    private static final int paragraphSpace = 1;
    private static final int tab = 15;
    private static final int underWrite = 3;
    
    private static final Font plainFont = new Font("Arial",Font.PLAIN,12);
    private static final Font italicFont = new Font("Arial",Font.ITALIC,12);
    
    private List<JRegexChoices> lstRegexChoices;
    //private static final Font plainFont = new Font("Courier New",Font.PLAIN,14);
    //private static final Font italicFont = new Font("Courier New",Font.ITALIC,12);
    
	
	public JTreeAutomata(TreeAutomata treeAutomata)
	{
		this.ta = treeAutomata;
		lstRegexChoices = new LinkedList();
		setBackground(Color.WHITE);
//		JRegexChoices rc = new JRegexChoices(null);
//		rc.setLocation(100,100);
//		add(rc);
	}

	@Override protected void paintComponent(Graphics g) 
	{
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
        initialX = 16;
        currentY = 0;
        if(metrics==null){
        	//Font font = new Font("Courier New",Font.PLAIN,10);
        	metricsItalic = g2.getFontMetrics(italicFont);
        	metrics = g2.getFontMetrics(plainFont);
        	g2.setFont(plainFont);
        	//metrics = g2.getFontMetrics();
        	fontHeight = metrics.getHeight();
        }
        int cont = 1;
		for(State state:ta.getLstState())
		{
			drawState(g2,state,cont++);
		}
	}
	
	private void drawState(Graphics2D g2,State state,int pos)
	{
		newLine();
		drawNumber(pos,g2);
		drawName(state.getName(),g2);
		drawAttributes(state,g2);
		drawExp(state.getExp(),g2);
		drawArrow(g2);
		drawState(state.getName(),g2);
		drawChoices(g2,state.getChoices());
	}
	
	private void drawChoices(Graphics2D g2,RegexUpdateChoices choices)
	{
		//lstRegexChoices
		JRegexChoices rc = new JRegexChoices(choices);
		rc.setLocation(50,10);
		//add(rc);
		if(choices!=null)
		{
			for(Regex r:choices.getAllChoices())
			{
				newLine();
				drawExp(r,g2);
			}
		}
	}
	
	private void drawNumber(int number, Graphics2D g2)
	{
		drawText("("+number+")",g2);
	}
	
	private void drawName(String name, Graphics2D g2)
	{
		drawText(" "+name+", ",g2);
	}
	
	private void drawAttributes(State state, Graphics2D g2)
	{
		drawText(" {",g2);
		
		if(state.getLstComp().size()>0)
		{
			drawAttributes(state.getLstComp(),g2);
		}else{
			drawText("0",g2);
		}
		
		drawText(", ",g2);
		
		if(state.getLstOp().size()>0)
		{
			drawAttributes(state.getLstOp(),g2);
		}else{
			drawText("0",g2);
		}		
		
		drawText("},",g2);
	}
	
	private void drawAttributes(List<String> lst,Graphics2D g2)
	{
		drawText("{",g2);
		int i = 0;
		for(String s:lst)
		{
			drawState(s,g2);
			if(i<lst.size()-1)
				drawText(",",g2);
		}
		drawText("}",g2);
	}
	
	private void drawState(String s,Graphics2D g2)
	{
		if(s.startsWith("q_")) s = s.substring(2);
		drawItalicText("q",g2);
		currentY+=underWrite;
		drawItalicText(s,g2);
		currentY-=underWrite;
	}
	
	private void drawExp(Regex exp,Graphics2D g2)
	{
		drawText(" "+exp.toString(),g2);
	}
	
	private void drawArrow(Graphics2D g2)
	{
		drawText(" -> ",g2);
	}
	
	private void drawText(String t,Graphics2D g2)
	{
		g2.drawString(t,currentX,currentY);
		currentX+=metrics.stringWidth(t);
	}
	
	private void drawItalicText(String t, Graphics2D g2)
	{
		g2.setFont(italicFont);
		g2.drawString(t,currentX,currentY);
		currentX+=metricsItalic.stringWidth(t);
		g2.setFont(plainFont);
	}
	
	private void newLine()
	{
		currentY+= (fontHeight)+paragraphSpace;
		currentX = initialX;
	}
}
