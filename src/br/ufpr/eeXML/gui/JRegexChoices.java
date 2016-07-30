/*
 * Created on 15/05/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import br.ufpr.eeXML.v1.glushkov.regex.RegexUpdateChoices;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class JRegexChoices extends JPanel
{
	private RegexUpdateChoices choices;
	
	public JRegexChoices(RegexUpdateChoices choices)
	{
		this.choices = choices;
		init();
	}
	
	private void init()
	{
		//setBorder(new LineBorder(Color.BLACK,1));
		//setOpaque(false);
		setBackground(Color.WHITE);
		setBorder(new EtchedBorder());
		Dimension d = new Dimension(100,150);
		setPreferredSize(d);
	}

	@Override protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	}
}