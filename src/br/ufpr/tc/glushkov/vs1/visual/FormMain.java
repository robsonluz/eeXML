/*
 * Created on 24/05/2005
 *
 */
package br.ufpr.tc.glushkov.vs1.visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import br.ufpr.tc.glushkov.regex.Exp;
import br.ufpr.tc.glushkov.vs1.Automato;
import br.ufpr.tc.glushkov.vs1.MakeAutomato;
import br.ufpr.tc.glushkov.vs1.PrefixExp;
import br.ufpr.tc.glushkov.vs1.back.MakeExp;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class FormMain extends JPanel
{
	private JTextArea txtLog;
	private ExpressionPanel ep;
	private FormGraphics fg;
	public FormMain()
	{
		initializeComponents();
		redirectOut();
	}
	
	private void redirectOut()
	{
		LogWriter log = new LogWriter(txtLog);
		System.setOut(new PrintStream(log));
		System.setErr(new PrintStream(log));
	}
	
	private void initializeComponents()
	{
		setLayout(new BorderLayout());
		
		Dimension d = new Dimension(100,150);
		txtLog = new JTextArea("");
		//txtLog.setPreferredSize(d);
		//txtLog.setMinimumSize(d);
		ep = new ExpressionPanel(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gerar();
			}
		});
		super.add(ep,BorderLayout.PAGE_START);
		
		Dimension df = new Dimension(100,200);
		fg = new FormGraphics();
		fg.setPreferredSize(df);
		
		
		//super.add(fg,BorderLayout.CENTER);
		
		JScrollPane stxtLog = new JScrollPane();
		stxtLog.setViewportView(txtLog);
		stxtLog.setPreferredSize(d);
		stxtLog.setMinimumSize(d);
		
		stxtLog.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		stxtLog.setHorizontalScrollBarPolicy(
						JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,fg,stxtLog);
		
		splitPane.setResizeWeight(1.0);


		
		super.add(splitPane,BorderLayout.CENTER);
		//super.add(stxtLog,BorderLayout.PAGE_END);
	}
	
	private void gerar()
	{
		try{
			txtLog.setText("");
			MakeAutomato g = new MakeAutomato(new PrefixExp(".("+ep.getExpression()+",#)"));
			Automato a = g.gerarAutomato();
			a.printAutomato();
			fg.montarAutomato(a);
			gerarExp(a);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void gerarExp(Automato aut)
	{
		try{
			MakeExp make = new MakeExp(aut);
			Exp exp = make.makeExp();
			System.out.println("Expressão gerada: "+exp.getExp());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		JFrame frm = new JFrame("Glushkov Algorithm");
		frm.setContentPane(new FormMain());
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.pack();
		frm.setVisible(true);
		//frm.show();
	}
}
