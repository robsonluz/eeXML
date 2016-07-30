/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.tc.glushkov.vs2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.ufpr.tc.glushkov.regex.Exp;
import br.ufpr.tc.glushkov.regex.OptRegex;
import br.ufpr.tc.glushkov.regex.Regex;
import br.ufpr.tc.glushkov.regex.ReGexParser;
import br.ufpr.tc.glushkov.regex.SingleRegex;
import br.ufpr.tc.glushkov.regex.StarOneRegex;
import br.ufpr.tc.glushkov.regex.StarRegex;
import br.ufpr.tc.glushkov.vs1.Automato;
import br.ufpr.tc.glushkov.vs1.NormalExp;
import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class MakeExp 
{
	private List<TransitionRegex> lstTransition;
	private Set<TransitionRegex> lstOrbitas;
	private Automato aut;
	
	public MakeExp(Automato aut)throws Exception
	{
		this.aut = aut;
	}
	
	private void init()
	{
		lstOrbitas = new LinkedHashSet();
		lstTransition = transform(aut.getLstTransition());
		//Collections.reverse(lstTransition);
		lstTransition = init(lstTransition,null);
		System.out.println("\n\nFinal: ");
		print(lstTransition);
	}
	
	private List<TransitionRegex> transform(List<Transition> lst)
	{
		List<TransitionRegex> lstR = new ArrayList(lst.size());
		for(Transition t:lst)
		{
			TransitionRegex tr = new TransitionRegex();
			tr.setEstadoAtual(new SingleRegex(t.getEstadoAtual()));
			tr.setEstadoNovo(new SingleRegex(t.getEstadoNovo()));
			tr.setTransicao(t.getTransicao());
			lstR.add(tr);
		}
		return lstR;
	}
	
	private List<TransitionRegex> init(List<TransitionRegex> lst,Orbit orb)
	{
		
		Collections.sort(lst,new TransitionComparator());
		List<Orbit> lstO = buscaOrbitasMaximais(lst);
		System.out.println("-"+lstO.size());
		for(Orbit o: lstO){
			o.setParentOrbit(orb);
			o.printOrbita();
			addBackEdge(o.getBackEdge());
			o.setSO(lst);
			System.out.println("Transicoes: ");
			print(lst);
			System.out.println();
			init(lst,o);
		}
		
		System.out.println("Reduzir: ");
		Collections.reverse(lst);
		
		reduce(lst,orb);
		colocarFecho(lst,orb);
		return lst;
	}
	private void colocarFecho(List<TransitionRegex> lstT,Orbit orb)
	{
		if(orb!=null)
		{
			if(orb.size()==1)
			{
				Regex t = orb.getFirst();
				Regex n = t;
				
				//String n = t.trim();
				if(n instanceof OptRegex)
					n = new StarRegex(n);
				else
					n = new StarOneRegex(n);
//				if(n.endsWith("?"))
//					n = n +"*";
//				else
//					n = n +"+";
				orb.ajuste(t,n);
				for(TransitionRegex tr: lstT)
				{
					if(tr.getEstadoAtual().equals(t))
						tr.setEstadoAtual(n);
					if(tr.getEstadoNovo().equals(t))
						tr.setEstadoNovo(n);
				}
			}
		}
	}
	
	private void reduce(List<TransitionRegex> lstT,Orbit orb)
	{
		System.out.println("Grafo sem orbita:");
		if(orb!=null) orb.printOrbita();
		print(lstT);
		Orbit oclone = null;
		if(orb==null){
			orb = new Orbit(null);
			oclone = null;
		}else{
			oclone = orb.clone();
		}
		while(!isEnd(lstT,oclone))
		{
			ReduceRuleFactory.getInstance().getRule1().reduce(lstT,orb);
			ReduceRuleFactory.getInstance().getRule2().reduce(lstT,orb);
			ReduceRuleFactory.getInstance().getRule3().reduce(lstT,orb);
			print(lstT);
		}
	}
	
	private boolean isEnd(List<TransitionRegex> lstT,Orbit orb)
	{
		if(lstT.size()<=1)
			return true;
		if(orb==null)
			return false;
		
		if(orb.size()<=1)
			return true;
		
		for(TransitionRegex t: lstT)
		{
			if(orb.contains(t.getEstadoAtual()) || orb.contains(t.getEstadoNovo())){
				return false;
			}
		}
		return true;
	}
	private List<Orbit> buscaOrbitasMaximais(List<TransitionRegex> lstTransicao)
	{
		List<Orbit> lst = new LinkedList();
		//String tt = "-1";
		int ti = -1;
		for(TransitionRegex t: lstTransicao)
		{
			try{
//			int x = new Integer(t.getEstadoAtual());
//			int y = new Integer(t.getEstadoNovo());
			int x = getInt(t.getEstadoAtual());
			int y = getInt(t.getEstadoNovo());

			//int ti = new Integer(tt);
			if(x>=y && (x<ti || ti==-1))//Back-Edge
			{
				//System.out.println("-"+t.getEstadoNovo());
				//tt = t.getEstadoNovo();
				ti = getInt(t.getEstadoNovo());
				lst.add(buscaOrbita(t,lstTransicao));
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return lst;
	}
	
	private int getInt(Regex r)
	{
		return new Integer(r.toString()).intValue();
	}
	
	private Orbit buscaOrbita(TransitionRegex backEdge,List<TransitionRegex> lstT)
	{
		Orbit orb = new Orbit();
		orb.setBackEdge(backEdge);
		orb.add(backEdge.getEstadoAtual());
		orb.add(backEdge.getEstadoNovo());
		for(TransitionRegex t: lstT)
		{
			if(t.getEstadoAtual().equals(backEdge.getEstadoNovo()))
			{
				System.out.println(t.getEstadoNovo()+" % "+backEdge.getEstadoAtual());
				List<Regex> l = buscaCaminho(t.getEstadoNovo(),backEdge.getEstadoAtual(),lstT);
				
				//System.out.println(l.size());
				for(Regex n:l)
					orb.add(n);
			}
		}
		return orb;
	}
	private List<Regex> buscaCaminho(Regex atual,Regex destino,List<TransitionRegex> lstT)
	{
		List<Regex> lstRet = new LinkedList();
		for(TransitionRegex t: lstT)
		{
			if(t.getEstadoAtual().equals(atual))
			{
				if(t.getEstadoNovo().equals(destino)){
						lstRet.add(t.getEstadoAtual());
				}else if(getInt(t.getEstadoAtual()) < getInt(t.getEstadoNovo())){
					List<Regex> ltmp = buscaCaminho(t.getEstadoNovo(),destino,lstT); 
					copyTo(ltmp,lstRet);
					if(ltmp.size()>0)
						lstRet.add(t.getEstadoAtual());
				}
			}
		}
		return lstRet;
	}
	private void copyTo(List lstSource,List lstTarget)
	{
		for(Object o:lstSource)
			lstTarget.add(o);
	}
	
//	private void addOrbita(TransitionRegex t)
//	{
//		for(TransitionRegex n: lstOrbitas)
//		{
//			Regex x1 = n.getEstadoAtual();
//			Regex y1 = n.getEstadoNovo();
//
//			Regex x2 = t.getEstadoAtual();
//			Regex y2 = t.getEstadoNovo();
//			if(x1.equals(x2) && y1.equals(y2))
//				return;
//		}
//		System.out.println("Orbita: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
//		lstOrbitas.add(t);
//	}
	
	private void print(List<TransitionRegex> lstT)
	{
		for(TransitionRegex t: lstT)
		{
			System.out.println(t.getEstadoAtual()+" -> "+t.getEstadoNovo());
		}
		System.out.println("-------------------------------------------");
	}
	
	private void addBackEdge(TransitionRegex backEdge)
	{
		lstOrbitas.add(backEdge);
	}
	
	public Exp makeExp()
	{
		init();
		TransitionRegex t = lstTransition.get(0);
		ReGexParser parser = new ReGexParser();
		//String exp = colocarFechos(t.getEstadoNovo());
		String exp = parser.parserToNormal(t.getEstadoNovo());
		System.out.println(exp);
		String s[] = aut.getSigma();
		int cont=1;
		for(String a:s)
		{
			exp = exp.replaceAll(""+cont,a);
			cont++;
		}
		exp = exp.replaceAll(""+cont,"#");
		
		
		return new NormalExp(exp);
	}
	
	/*private String colocarFechos(String exp)
	{
		
		System.out.println("fechos: ");
		StringBuffer ret = new StringBuffer(exp);
		for(Transition t: lstOrbitas)
		{
			String x = t.getEstadoAtual();
			String y = t.getEstadoNovo();
			int ini = getInitFecho(exp,y);
			exp = ret.toString();
			
			//int fim = getEndFecho(exp,x);
			int fim = exp.indexOf(x);
			int tt = 2;
			if(x.equals(y))
			{
				tt--;
			}
			String s = exp.substring(fim+tt,fim+tt+1);
			if(s.equals("?"))
			{
				ret.delete(fim+tt,fim+tt+1);
				ret.insert(fim+tt,"*");
			}else{
				ret.insert(fim+tt,"+");
			}
			int f1 = getInitFecho(exp,x)+1;
			
			System.out.println(fim);
			if(fim==f1)
				ret.insert(fim,"+");
			else{
				if(exp.toCharArray()[fim-1]=='?'){
					ret.delete(fim-1,fim);
					ret.insert(fim-1,"*");
				}else{
					if(x.equals(y))
						fim--;
					ret.insert(fim,"+");
				}
			}
			System.out.println(x+" -> "+y);
			exp = ret.toString();
		}
		return ret.toString();
	}
	
	private int getInitFecho(String exp,String estado)
	{
		return exp.indexOf(estado);
	}
	
	private int getEndFecho(String exp,String estado)
	{
		char[] tmp = exp.toCharArray();
		int i = exp.indexOf(estado);
		int j =i;
		int n = i;
		boolean b = false;
		for(j=i;j<tmp.length;j++)
		{
			//b = true;
			
			if(isOperator(tmp[j]))
				break;
			n++;
		}
		//if(b)
			return n;
	}
	private boolean isOperator(char c)
	{
		if(c=='.' || c=='|')
			return true;
		return false;
	}
	*/
	
}