/*
 * Created on 24/05/2005
 *
 */
package br.ufpr.tc.glushkov.vs1;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.swing.JFrame;

import salvo.jesus.graph.DirectedEdgeImpl;
import salvo.jesus.graph.DirectedGraphImpl;
import salvo.jesus.graph.Edge;
import salvo.jesus.graph.EdgeImpl;
import salvo.jesus.graph.Graph;
import salvo.jesus.graph.GraphImpl;
import salvo.jesus.graph.Vertex;
import salvo.jesus.graph.VertexImpl;
import salvo.jesus.graph.visual.GraphEditor;
import salvo.jesus.graph.visual.layout.ForceDirectedLayout;
import salvo.jesus.graph.visual.layout.StraightLineLayout;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class GerarGrafico 
{
	private Automato aut;
	
	public GerarGrafico(Automato aut)
	{
		this.aut = aut;
		init();
	}
	
	private void init()
	{
		try{
			Graph graph = new DirectedGraphImpl();
			GraphEditor	gedit1 = new GraphEditor();
			Hashtable<String,Vertex> hVertex = new Hashtable<String,Vertex>(aut.getQ().length);
			for(String q: aut.getQ())
			{
				Vertex v = new VertexImpl(q);
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
			
			gedit1.setGraph(graph);
			gerarJanela(gedit1);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void gerarJanela(GraphEditor ge)throws Exception
	{
		JFrame f = new JFrame("Automato Gerado");
		f.getContentPane().setLayout( new GridLayout(1,1));
	    f.getContentPane().add( ge );
	    //this.getContentPane().add( gedit2 );

	    /*ForceDirectedLayout  layout;

	    layout = new ForceDirectedLayout( vGraph );
        vGraph.setGraphLayoutManager( layout );
        */
	    ForceDirectedLayout layout = new ForceDirectedLayout( ge.getVisualGraph() );
	    //layout.setEletricalRepulsion(20);
	    //layout.setStiffness(30);
	    layout.setIncrement(0.5050);
	    layout.setSpringLength(80);
	    ge.setGraphLayoutManager( layout );
	    //gedit2.setGraphLayoutManager( new OrthogonalLineLayout( gedit2.getVisualGraph() ) );

	    f.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) { System.exit(0); }
	      });

	    f.pack();
	    
	    
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension frameSize = new Dimension( screenSize.width - 80, screenSize.height - 80 );

	    f.setSize( frameSize );
	    f.setLocation((int)(screenSize.getWidth() - frameSize.getWidth()) / 2, (int)(screenSize.getHeight() - frameSize.getHeight()) / 2);
	    f.show();
	    
	    layout.layout();
	}
}
