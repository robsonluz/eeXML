/*
 * Created on 24/05/2005
 *
 */
package br.ufpr.tc.glushkov.vs1.visual;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class ExpressionPanel extends JPanel
{
	private ActionListener al;
	private JTextField txtExpression;
	public ExpressionPanel(ActionListener cmdGerarClick)
	{
		this.al = cmdGerarClick;
		initializeComponents();
	}
	
	private void initializeComponents()
	{
		setLayout(new FlowLayout());
		Dimension d = new Dimension(500,25);
		
		txtExpression = new JTextField(".(.(|(a , b),*(c)),a)");
		txtExpression.setPreferredSize(d);
		JButton cmdGerar = new JButton("Gerar");
		cmdGerar.addActionListener(al);
		super.add(new JLabel("Expressão prefixa: "));
		super.add(txtExpression);
		super.add(cmdGerar);
	}
	
	public String getExpression()
	{
		return txtExpression.getText();
	}
	
}
