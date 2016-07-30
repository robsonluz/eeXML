/*
 * Created on 24/05/2005
 *
 */
package br.ufpr.tc.glushkov.vs1.visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import br.ufpr.tc.glushkov.vs1.Automato;
import br.ufpr.tc.glushkov.vs1.Common;
import br.ufpr.tc.glushkov.vs1.Transition;

import salvo.jesus.graph.DirectedEdgeImpl;
import salvo.jesus.graph.DirectedGraphImpl;
import salvo.jesus.graph.Edge;
import salvo.jesus.graph.Graph;
import salvo.jesus.graph.Vertex;
import salvo.jesus.graph.VertexImpl;
import salvo.jesus.graph.visual.GraphEditor;
import salvo.jesus.graph.visual.layout.ForceDirectedLayout;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class FormGraphics extends JPanel implements ActionListener
{
	private GraphEditor	gedit;
	private ForceDirectedLayout layout;
	public FormGraphics()
	{
		initializeComponents();
	}
	
	private void initializeComponents()
	{
		gedit = new GraphEditor(false);
		setLayout( new BorderLayout());
	    add( gedit, BorderLayout.CENTER);
	    
	    JPanel pnl = new JPanel();
	    JButton cmdOrganizar = new JButton("Organizar");
	    cmdOrganizar.setActionCommand("organizar");
	    cmdOrganizar.addActionListener(this);
	    
	    
	    pnl.add(cmdOrganizar);
	    //pnl.add(cmdParar);
	    add( pnl, BorderLayout.PAGE_END);
	}
	
	public void montarAutomato(Automato aut)
	{
		try{
			Graph graph = new DirectedGraphImpl();
			Hashtable<String,Vertex> hVertex = new Hashtable<String,Vertex>(aut.getQ().length);
			for(String q: aut.getQ())
			{
				Vertex v = new VertexImpl(q);
				if(q==aut.getSi()[0])
					v.setInitial(true);
				if(Common.contem(q,aut.getF()))
					v.setEnd(true);
				
				hVertex.put(q,v);
				graph.add(v);
			}
			for(Transition t: aut.getLstTransition())
			{
				Vertex i = hVertex.get(t.getEstadoAtual());
				Vertex f = hVertex.get(t.getEstadoNovo());
				Edge e = new DirectedEdgeImpl(i,f);
				
				e.setFollowVertexLabel(false);
				e.setLabel(t.getTransicao());
				graph.addEdge(e);
			}
			
			gedit.setGraph(graph);
			layout = new ForceDirectedLayout( gedit.getVisualGraph() );
		    layout.setIncrement(0.5050);
		    layout.setSpringLength(80);
		    gedit.setGraphLayoutManager( layout );
		    layout.drawLayout();
		    
		    
			//gerarJanela(gedit1);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e!=null)
		{
			if(e.getActionCommand().equals("organizar")){
				JButton cmd = (JButton) e.getSource();
				cmd.setText("Parar");
				cmd.setActionCommand("parar");
				organizar();
			}else if(e.getActionCommand().equals("parar")){
				JButton cmd = (JButton) e.getSource();
				cmd.setText("Organizar");
				cmd.setActionCommand("organizar");
				organizar();
			}
			//	parar();
		}
	}
	
	/*public void parar()
	{
		layout.parar();
	}*/
	
	public void organizar()
	{
		layout.layout();
	}
}