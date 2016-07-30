/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.tc.glushkov.vs2;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.tc.glushkov.regex.AndRegex;
import br.ufpr.tc.glushkov.regex.Regex;
import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Rule1 extends ReduceRuleImpl
{
	
	public boolean reduce(List<TransitionRegex> lstT,Orbit orb) 
	{
		List<TransitionRegex> lstAplicar = new LinkedList();
		for(TransitionRegex t: lstT)
		{
			Regex x = t.getEstadoAtual();
			if(!x.equals("0"))
			{
				Regex y = t.getEstadoNovo();
				Regex[] anty = ant(y,lstT);
				Regex[] sucx = suc(x,lstT);
				if(orb.contains(t.getEstadoAtual()) && orb.contains(t.getEstadoNovo()))
				if( (anty.length==1 && anty[0].equals(x)) && (sucx.length==1 && sucx[0].equals(y)))
				{
					lstAplicar.add(t);
					System.out.println("Aplicar R1: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
					break;
				}
			}
		}
		
		for(TransitionRegex t: lstAplicar)
		{
			Regex x = t.getEstadoAtual();
			Regex y = t.getEstadoNovo();
			//String tt = "("+x+"."+y+")";
			AndRegex tt = new AndRegex(x,y);
			
			orb.ajuste(x,y,tt);
			
			Regex antx[] = ant(x,lstT);
			for(Regex ax: antx)
			{
				remover(ax,x,lstT);
				addT(ax,tt,lstT);
			}
			Regex sucy[] = suc(y,lstT);
			for(Regex sy: sucy)
			{
				remover(y,sy,lstT);
				addT(tt,sy,lstT);
			}
			lstT.remove(t);
		}
		
		return lstAplicar.size()>0;
	}
}
