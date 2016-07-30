/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.tc.glushkov.vs2;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.tc.glushkov.regex.OrRegex;
import br.ufpr.tc.glushkov.regex.Regex;
import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Rule2 extends ReduceRuleImpl
{
	public boolean reduce(List<TransitionRegex> lstT, Orbit orb) 
	{
		List<TransitionRegex> lstAplicar = new LinkedList();
		for(TransitionRegex tx: lstT)
		{
			Regex x = tx.getEstadoAtual();
			if(orb.contains(x))
			for(TransitionRegex ty: lstT)
			{
				Regex y = ty.getEstadoAtual();
				if(!x.equals(y) && orb.contains(y))
				{
					if(equals(ant(x,lstT),ant(y,lstT)) && equals(suc(x,lstT),suc(y,lstT)))
					{
						if(!in(lstAplicar,x,y))
						{
							System.out.println("Aplicar R2: "+x+" : "+y);
							//System.out.println(x+" -> "+y);
							lstAplicar.add(new TransitionRegex(x,y));
						}
					}
				}
			}
		}
		
		for(TransitionRegex t: lstAplicar)
		{
			Regex x = t.getEstadoAtual();
			Regex y = t.getEstadoNovo();
			
			//String tt = "("+t.getEstadoAtualParenteses()+"|"+t.getEstadoNovoParenteses()+")";
			OrRegex tt = new OrRegex(x,y);
			
			orb.ajuste(x,y,tt);
			
			Regex[] antx = ant(x,lstT);
			for(Regex ax: antx)
			{
				remover(ax,x,lstT);
				addT(ax,tt,lstT);
			}
			Regex[] sucx = suc(x,lstT);
			for(Regex sx: sucx)
			{
				remover(x,sx,lstT);
				addT(tt,sx,lstT);
			}
			
			Regex[] anty = ant(y,lstT);
			for(Regex ay: anty)
			{
				remover(ay,y,lstT);
				addT(ay,tt,lstT);
			}
			Regex[] sucy = suc(y,lstT);
			for(Regex sy: sucy)
			{
				remover(y,sy,lstT);
				addT(tt,sy,lstT);
			}			
		}
		return lstAplicar.size()>0;
	}
}
