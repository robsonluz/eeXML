/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov.grec;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.ufpr.eeXML.v1.glushkov.Automato;
import br.ufpr.eeXML.v1.glushkov.Operator;
import br.ufpr.eeXML.v1.glushkov.Transition;
import br.ufpr.eeXML.v1.glushkov.regex.OptRegex;
import br.ufpr.eeXML.v1.glushkov.regex.Regex;
import br.ufpr.eeXML.v1.glushkov.regex.SingleRegex;
import br.ufpr.eeXML.v1.glushkov.regex.StarOneRegex;
import br.ufpr.eeXML.v1.glushkov.regex.StarRegex;



/**
 * @author Robson Jo�o Padilha da Luz
 *
 */
public class MakeExp 
{
	private List<Transition> lstTransition;
	private Set<Transition> lstOrbitas;
	private Automato aut;
	private GraphModification gm;
	
	public MakeExp(Automato aut,GraphModification gm)throws Exception
	{
		this.aut = aut;
		this.gm = gm;
	}
	
	private void init()
	{
		lstOrbitas = new LinkedHashSet();
		lstTransition = init(aut.getLstTransition(),null);
		System.out.println("\n\nFinal: ");
		print(lstTransition);
	}
	
	private List<Transition> init(List<Transition> lst,Orbit orb)
	{
		
		Collections.sort(lst,new TransitionComparator());
		List<Orbit> lstO = buscaOrbitasMaximais(lst);
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
	private void colocarFecho(List<Transition> lstT,Orbit orb)
	{
		if(orb!=null)
		{
			if(orb.size()==1)
			{
				Regex t = orb.getFirst();
				Regex n = t;
				
				if(n instanceof OptRegex)
					n = new StarRegex(n);
				else
					n = new StarOneRegex(n);
				
				orb.ajuste(t,n);
				for(Transition tr: lstT)
				{
					if(tr.getEstadoAtual().equals(t))
						tr.setEstadoAtual(n);
					if(tr.getEstadoNovo().equals(t))
						tr.setEstadoNovo(n);
				}
			}
		}
	}
	
	private void reduce(List<Transition> lstT,Orbit orb)
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
		int ret = 0;
		while(!isEnd(lstT,oclone,orb))
		{
			ret = ReduceRuleFactory.getInstance().getRule1().reduce(lstT,orb,gm);
//			if(ret == 2)
//			{
//				init(lstT,orb);
//				//reduce()
//				return;
//			}
			ret = ReduceRuleFactory.getInstance().getRule2().reduce(lstT,orb,gm);
			ret = ReduceRuleFactory.getInstance().getRule3().reduce(lstT,orb,gm);
			print(lstT);
		}
		System.out.println("UE");
	}
	
	private boolean isEnd(List<Transition> lstT,Orbit orb,Orbit currentOrbit)
	{
		if(currentOrbit!=null && currentOrbit.size()>1)
			return false;
			
		
		if(lstT.size()<=1)
			return true;
		if(orb==null)
			return false;
		
		if(orb.size()<=1)
			return true;
		
		for(Transition t: lstT)
		{
			if(orb.contains(t.getEstadoAtual()) || orb.contains(t.getEstadoNovo())){
				return false;
			}
		}
		return true;
	}
	private List<Orbit> buscaOrbitasMaximais(List<Transition> lstTransicao)
	{
		List<Orbit> lst = new LinkedList();
		Regex tt = new SingleRegex("-1");
		for(Transition t: lstTransicao)
		{
			try{
			int x = new Integer(t.getEstadoAtual().toString());
			int y = new Integer(t.getEstadoNovo().toString());
			int ti = new Integer(tt.toString());
			if(x>=y && (x<ti || ti==-1))//Back-Edge
			{
				tt = t.getEstadoNovo();
				lst.add(buscaOrbita(t,lstTransicao));
			}
			}catch(Exception e){}
		}
		return lst;
	}
	
	private Orbit buscaOrbita(Transition backEdge,List<Transition> lstT)
	{
		Orbit orb = new Orbit();
		orb.setBackEdge(backEdge);
		orb.add(backEdge.getEstadoAtual());
		orb.add(backEdge.getEstadoNovo());
		for(Transition t: lstT)
		{
			if(t.getEstadoAtual().equals(backEdge.getEstadoNovo()))
			{
				System.out.println(t.getEstadoNovo()+" "+backEdge.getEstadoAtual());
				List<Regex> l = buscaCaminho(t.getEstadoNovo(),backEdge.getEstadoAtual(),lstT);
				
				for(Regex n:l)
					orb.add(n);
			}
		}
		return orb;
	}
	private List<Regex> buscaCaminho(Regex atual,Regex destino,List<Transition> lstT)
	{
		List<Regex> lstRet = new LinkedList();
		for(Transition t: lstT)
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
	
	private int getInt(Regex r)
	{
		return new Integer(r.toString()).intValue();
	}
	private void copyTo(List lstSource,List lstTarget)
	{
		for(Object o:lstSource)
			lstTarget.add(o);
	}
	
//	private void addOrbita(Transition t)
//	{
//		for(Transition n: lstOrbitas)
//		{
//			String x1 = n.getEstadoAtual().trim();
//			String y1 = n.getEstadoNovo().trim();
//
//			String x2 = t.getEstadoAtual().trim();
//			String y2 = t.getEstadoNovo().trim();
//			if(x1.equals(x2) && y1.equals(y2))
//				return;
//		}
//		System.out.println("Orbita: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
//		lstOrbitas.add(t);
//	}
	
	private void print(List<Transition> lstT)
	{
		for(Transition t: lstT)
		{
			System.out.println(t.getEstadoAtual()+" -> "+t.getEstadoNovo());
		}
		System.out.println("-------------------------------------------");
	}
	
	private void addBackEdge(Transition backEdge)
	{
		lstOrbitas.add(backEdge);
	}
	
	public Regex makeExp()
	{
		init();
		Transition t = lstTransition.get(0);
		Regex exp = t.getEstadoNovo();
		prepareExp(exp);
		return exp;
	}
	

	/** Substitui os indices **/
	private void prepareExp(Regex exp)
	{
		if(exp.getOperator()==Operator.NULL){
			try{
				int value = Integer.parseInt(((SingleRegex)exp).getValue()); 
				((SingleRegex)exp).setValue(aut.getSigma()[value-1]);
			}catch(Exception e){
			}
		}
		if(exp.getOperator()==Operator.OR ||exp.getOperator()==Operator.CONCAT)
		{
			prepareExp(exp.getParameter(0));
			prepareExp(exp.getParameter(1));
		}
		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE || exp.getOperator()==Operator.OPTIONAL)
		{
			prepareExp(exp.getParameter(0));
		}
	}
	
/*	public Exp makeExp()
	{
		init();
		Transition t = lstTransition.get(0);
		//String exp = colocarFechos(t.getEstadoNovo());
		String exp = t.getEstadoNovo();
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
*/
	
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