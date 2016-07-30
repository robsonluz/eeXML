/*
 * Created on 10/04/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.grec;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JFrame;

import salvo.jesus.graph.DirectedEdgeImpl;
import salvo.jesus.graph.DirectedGraphImpl;
import salvo.jesus.graph.Edge;
import salvo.jesus.graph.Graph;
import salvo.jesus.graph.Vertex;
import salvo.jesus.graph.VertexImpl;
import salvo.jesus.graph.visual.GraphEditor;
import salvo.jesus.graph.visual.layout.ForceDirectedLayout;

import br.ufpr.eeXML.v1.glushkov.Automato;
import br.ufpr.eeXML.v1.glushkov.MakeAutomato;
import br.ufpr.eeXML.v1.glushkov.Transition;
import br.ufpr.eeXML.v1.glushkov.regex.Exp;
import br.ufpr.eeXML.v1.glushkov.regex.ExpToRegex;
import br.ufpr.eeXML.v1.glushkov.regex.Regex;
import br.ufpr.eeXML.v1.treeautomata.ExpDTD;
import br.ufpr.tc.glushkov.vs1.Common;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class Teste 
{
	public static void main(String args[])
	{
		Regex r = ExpToRegex.convert(new ExpDTD("a,(b|c),d"));
		//Regex r = ExpToRegex.convert(new ExpDTD("a,(b|c)*,d"));
		
		MakeAutomato ma = new MakeAutomato(r);
		Automato a = ma.gerarAutomato();
		a.printAutomato();
		//gerarGrafico(a);
		List<String> lst = getWordList("a","b","n","d");
		//List<String> lst = getWordList("a","n","d");
		GraphModification gm = CheckAutomataWord.checkAutomataWord(a,lst);
		if(gm!=null)
		{
			//System.out.println(gm.getSnew());
			
			try{
				MakeExp me = new MakeExp(a,gm);
				Regex e = me.makeExp();
				//String en = prepareExp(e.toString());
				System.out.println("\n\n:::::::"+e);
				
				//ma = new MakeAutomato(e);
				//ma.gerarAutomato().printAutomato();
				//gerarGrafico(ma.gerarAutomato());
				//state.setStrExp(en);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}
		
		//System.out.println(ExpToRegex.convert(new ExpDTD("a,(b|c)*,d")));
	}
	
	private static List<String> getWordList(String... word)
	{
		List<String> lstRet = new ArrayList(word.length);
		lstRet.add("#");
		for(String c:word)
			lstRet.add(c);
		lstRet.add("#");
		return lstRet;
	}
	
	
	
	private static void gerarGrafico(Automato aut)
	{
		try{
			Graph graph = new DirectedGraphImpl();
			GraphEditor	gedit = new GraphEditor();
			
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
				Vertex i = hVertex.get(t.getEstadoAtual().toString());
				Vertex f = hVertex.get(t.getEstadoNovo().toString());
				Edge e = new DirectedEdgeImpl(i,f);
				
				e.setFollowVertexLabel(false);
				e.setLabel(t.getTransicao());
				graph.addEdge(e);
			}
			
			gedit.setGraph(graph);
			ForceDirectedLayout layout = new ForceDirectedLayout( gedit.getVisualGraph() );
			layout.setIncrement(0.5050);
			layout.setSpringLength(80);
			gedit.setGraphLayoutManager( layout );
			layout.drawLayout();
			
			
			gerarJanela(gedit);
		}catch(Exception e){
			//e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private static void gerarJanela(GraphEditor ge)throws Exception
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
		f.setVisible(true);
		
		layout.layout();
	}
}
