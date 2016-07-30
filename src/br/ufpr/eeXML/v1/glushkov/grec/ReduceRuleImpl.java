/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov.grec;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.eeXML.v1.glushkov.Common;
import br.ufpr.eeXML.v1.glushkov.Operator;
import br.ufpr.eeXML.v1.glushkov.Transition;
import br.ufpr.eeXML.v1.glushkov.regex.Regex;



/**
 * @author Robson João Padilha da Luz
 *
 */
public abstract class ReduceRuleImpl implements ReduceRule 
{
	public boolean in(List<Transition> lst,Regex x, Regex y)
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
	
	public Regex[] ant(Regex x,List<Transition> lstT)
	{
		List<Regex> l = new LinkedList();
		for(Transition t: lstT)
		{
			if(t.getEstadoNovo().equals(x))
				l.add(t.getEstadoAtual());
		}
		return l.toArray(new Regex[l.size()]);
	}
	public Regex[] sucs(Regex x[],List<Transition> lstT)
	{
		Regex ret[] = new Regex[0];
		for(Regex t: x)
		{
			ret = union(ret,suc(t,lstT));
		}
		return ret;
	}
	public Regex[] suc(Regex x,List<Transition> lstT)
	{
		List<Regex> l = new LinkedList();
		for(Transition t: lstT)
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
	
	public void addT(Regex atual,Regex novo,List<Transition> lstT)
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
	
	public void remover(Regex x,Regex y,List<Transition> lstT)
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
	
	protected String[] last(Regex exp)
	{
		if(exp==null || exp.toString().equals("&"))
			return new String[0];
		if(exp.getOperator()==Operator.NULL)
			return new String[]{exp.toString()};
		if(exp.getOperator()==Operator.OR)
			return Common.union(last(exp.getParameter(0)) , last(exp.getParameter(1)));
		if(exp.getOperator()==Operator.CONCAT)
			return Common.union(last(exp.getParameter(1)) , Common.product(nullE(exp.getParameter(1)),last(exp.getParameter(0))));
		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE)
			return last(exp.getParameter(0));
		return new String[0];
	}
	protected String[] first(Regex exp)
	{
		if(exp==null || exp.toString().equals("&"))
			return new String[0];
		
		//PrefixExp exp = new PrefixExp(e);
		if(exp.getOperator()==Operator.NULL)
			return new String[]{exp.toString()};
		if(exp.getOperator()==Operator.OR)
			return Common.union(first(exp.getParameter(0)) , first(exp.getParameter(1)));
		if(exp.getOperator()==Operator.CONCAT)
			return Common.union(first(exp.getParameter(0)) , Common.product(nullE(exp.getParameter(0)),first(exp.getParameter(1))));
		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE)
			return first(exp.getParameter(0));
		return new String[0];
	}
	protected boolean nullE(Regex exp)
	{
		if(exp==null)
			return false;
		if(exp.toString().equals("&"))
			return true;
//		if(exp.getOperator()==Operator.OPTIONAL)
//			return true;
		//PrefixExp exp = new PrefixExp(e);
		if(exp.getOperator()==Operator.NULL)
			return false;
		if(exp.getOperator()==Operator.OR)
			return nullE(exp.getParameter(0)) || nullE(exp.getParameter(1));
		if(exp.getOperator()==Operator.CONCAT)
			return nullE(exp.getParameter(0)) && nullE(exp.getParameter(1));
		if(exp.getOperator()==Operator.FECHO_ONE)
			return nullE(exp.getParameter(0));
		if(exp.getOperator()==Operator.FECHO)
			return true;
		return false;
	}
	protected boolean pertence(Regex exp,String[] st)
	{
		for(String s:st)
		{
			if(s.equals(exp.toString()))
				return true;
		}
		return false;
	}
	
	protected boolean pertenceToFirst(Regex elemento,Regex set[])
	{
		for(Regex e: set)
		{
			if(pertence(elemento,first(e)))
				return true;
		}
		return false;
	}
	protected boolean pertenceToLast(Regex elemento,Regex set[])
	{
		for(Regex e: set)
		{
			if(pertence(elemento,last(e)))
				return true;
		}
		return false;
	}	
}