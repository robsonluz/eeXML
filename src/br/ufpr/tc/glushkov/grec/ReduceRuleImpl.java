/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.tc.glushkov.grec;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.tc.glushkov.vs1.Common;
import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public abstract class ReduceRuleImpl implements ReduceRule 
{
	public boolean in(List<Transition> lst,String x, String y)
	{
		for(Transition t: lst)
		{
			if(t.getEstadoAtual().equals(x) && t.getEstadoNovo().equals(y))
				return true;
			if(t.getEstadoAtual().equals(y) && t.getEstadoNovo().equals(x))
				return true;
		}
		return false;
	}
	
	public String[] ant(String x,List<Transition> lstT)
	{
		List<String> l = new LinkedList();
		for(Transition t: lstT)
		{
			if(t.getEstadoNovo().equals(x))
				l.add(t.getEstadoAtual());
		}
		return l.toArray(new String[l.size()]);
	}
	public String[] sucs(String x[],List<Transition> lstT)
	{
		String ret[] = new String[0];
		for(String t: x)
		{
			ret = Common.union(ret,suc(t,lstT));
		}
		return ret;
	}
	public String[] suc(String x,List<Transition> lstT)
	{
		List<String> l = new LinkedList();
		for(Transition t: lstT)
		{
			if(t.getEstadoAtual().equals(x))
				l.add(t.getEstadoNovo());
		}
		return l.toArray(new String[l.size()]);
	}
	public boolean contido(String[] a, String c[])
	{
		boolean tmp = false;
		for(String x: a)
		{
			for(String y: c)
			{
				if(x.equals(y))
				{
					
					tmp = true;
					break;
				}
			}
			if(!tmp)
				return false;
			tmp = false;
		}
		return true;
	}
	
	public boolean pertence(String elemento,String set[])
	{
		for(String e: set)
		{
			if(e.equals(elemento))
				return true;
		}
		return false;
	}
	public boolean equals(String[] ant1,String[] ant2)
	{
		if(ant1.length!=ant2.length)
			return false;
		
		for(int i=0;i<ant1.length;i++)
			if(!ant1[i].equals(ant2[i]))
				return false;
		return true;
	}
	
	public void addT(String atual,String novo,List<Transition> lstT)
	{
		for(Transition t: lstT)
		{
			if(t.getEstadoAtual().equals(atual) && t.getEstadoNovo().equals(novo))
				return;
			if(atual.equals(novo))
				return;
		}
		//System.out.println("Adicionando: "+atual+" -> "+novo);
		lstT.add(new Transition(atual,novo));
	}
	
	public void remover(String x,String y,List<Transition> lstT)
	{
		List<Transition> lstRemover = new LinkedList();
		for(Transition t: lstT)
		{
			if(t.getEstadoAtual().equals(x) && t.getEstadoNovo().equals(y)){
				lstRemover.add(t);
				//System.out.println("Removendo: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
			}
		}
		for(Transition t: lstRemover)
			lstT.remove(t);
	}
}