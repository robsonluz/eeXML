/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.tc.glushkov.vs1.back;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Rule1 extends ReduceRuleImpl
{
	
	public boolean reduce(List<Transition> lstT,Orbit orb) 
	{
		List<Transition> lstAplicar = new LinkedList();
		for(Transition t: lstT)
		{
			String x = t.getEstadoAtual();
			if(!x.equals("0"))
			{
				String y = t.getEstadoNovo();
				String[] anty = ant(y,lstT);
				String[] sucx = suc(x,lstT);
				if(orb.contains(t.getEstadoAtual()) && orb.contains(t.getEstadoNovo()))
				if( (anty.length==1 && anty[0].equals(x)) && (sucx.length==1 && sucx[0].equals(y)))
				{
					lstAplicar.add(t);
					System.out.println("Aplicar R1: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
					break;
				}
			}
		}
		
		for(Transition t: lstAplicar)
		{
			String x = t.getEstadoAtual();
			String y = t.getEstadoNovo();
			String tt = "("+x+"."+y+")";
			
			orb.ajuste(x,y,tt);
			
			String antx[] = ant(x,lstT);
			for(String ax: antx)
			{
				remover(ax,x,lstT);
				addT(ax,tt,lstT);
			}
			String sucy[] = suc(y,lstT);
			for(String sy: sucy)
			{
				remover(y,sy,lstT);
				addT(tt,sy,lstT);
			}
			lstT.remove(t);
		}
		
		return lstAplicar.size()>0;
	}
}
