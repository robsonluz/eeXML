/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.tc.glushkov.vs2;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.tc.glushkov.regex.Regex;
import br.ufpr.tc.glushkov.vs1.Common;
import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public abstract class ReduceRuleImpl implements ReduceRule 
{
	public boolean in(List<TransitionRegex> lst,Regex x, Regex y)
	{
		for(TransitionRegex t: lst)
		{
			if(t.getEstadoAtual().equals(x) && t.getEstadoNovo().equals(y))
				return true;
			if(t.getEstadoAtual().equals(y) && t.getEstadoNovo().equals(x))
				return true;
		}
		return false;
	}
	
	public Regex[] ant(Regex x,List<TransitionRegex> lstT)
	{
		List<Regex> l = new LinkedList();
		for(TransitionRegex t: lstT)
		{
			if(t.getEstadoNovo().equals(x))
				l.add(t.getEstadoAtual());
		}
		return l.toArray(new Regex[l.size()]);
	}
	public Regex[] sucs(Regex x[],List<TransitionRegex> lstT)
	{
		Regex ret[] = new Regex[0];
		for(Regex t: x)
		{
			ret = union(ret,suc(t,lstT));
		}
		return ret;
	}
	
	public Regex[] union(Regex[] l, Regex m[])
	{
		if(m==null)
			return l;
		if(l==null)
			return m;
		if(l==null && m==null)
			return null;
		Regex[] r = new Regex[l.length+m.length];
		System.arraycopy(l,0,r,0,l.length);
		System.arraycopy(m,0,r,l.length,m.length);
		return r;
	}
	public Regex[] suc(Regex x,List<TransitionRegex> lstT)
	{
		List<Regex> l = new LinkedList();
		for(TransitionRegex t: lstT)
		{
			if(t.getEstadoAtual().equals(x))
				l.add(t.getEstadoNovo());
		}
		return l.toArray(new Regex[l.size()]);
	}
	public boolean contido(Regex[] a, Regex c[])
	{
		boolean tmp = false;
		for(Regex x: a)
		{
			for(Regex y: c)
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
	
	public boolean pertence(Regex elemento,Regex set[])
	{
		for(Regex e: set)
		{
			if(e.equals(elemento))
				return true;
		}
		return false;
	}
	public boolean equals(Regex[] ant1,Regex[] ant2)
	{
		if(ant1.length!=ant2.length)
			return false;
		
		for(int i=0;i<ant1.length;i++)
			if(!ant1[i].equals(ant2[i]))
				return false;
		return true;
	}
	
	public void addT(Regex atual,Regex novo,List<TransitionRegex> lstT)
	{
		for(TransitionRegex t: lstT)
		{
			if(t.getEstadoAtual().equals(atual) && t.getEstadoNovo().equals(novo))
				return;
		}
		lstT.add(new TransitionRegex(atual,novo));
	}
	
	public void remover(Regex x,Regex y,List<TransitionRegex> lstT)
	{
		List<TransitionRegex> lstRemover = new LinkedList();
		for(TransitionRegex t: lstT)
		{
			if(t.getEstadoAtual().equals(x) && t.getEstadoNovo().equals(y))
				lstRemover.add(t);
		}
		for(TransitionRegex t: lstRemover)
			lstT.remove(t);
	}
}