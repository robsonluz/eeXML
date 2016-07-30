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
public class Rule2 extends ReduceRuleImpl
{
	public boolean reduce(List<Transition> lstT, Orbit orb) 
	{
		List<Transition> lstAplicar = new LinkedList();
		for(Transition tx: lstT)
		{
			String x = tx.getEstadoAtual();
			if(orb.contains(x))
			for(Transition ty: lstT)
			{
				String y = ty.getEstadoAtual();
				if(!x.equals(y) && orb.contains(y))
				{
					if(equals(ant(x,lstT),ant(y,lstT)) && equals(suc(x,lstT),suc(y,lstT)))
					{
						if(!in(lstAplicar,x,y))
						{
							System.out.println("Aplicar R2: "+x+" : "+y);
							//System.out.println(x+" -> "+y);
							lstAplicar.add(new Transition(x,y));
						}
					}
				}
			}
		}
		
		for(Transition t: lstAplicar)
		{
			String x = t.getEstadoAtual();
			String y = t.getEstadoNovo();
			
			String tt = "("+t.getEstadoAtualParenteses()+"|"+t.getEstadoNovoParenteses()+")";
			
			orb.ajuste(x,y,tt);
			
			String[] antx = ant(x,lstT);
			for(String ax: antx)
			{
				remover(ax,x,lstT);
				addT(ax,tt,lstT);
			}
			String[] sucx = suc(x,lstT);
			for(String sx: sucx)
			{
				remover(x,sx,lstT);
				addT(tt,sx,lstT);
			}
			
			String[] anty = ant(y,lstT);
			for(String ay: anty)
			{
				remover(ay,y,lstT);
				addT(ay,tt,lstT);
			}
			String[] sucy = suc(y,lstT);
			for(String sy: sucy)
			{
				remover(y,sy,lstT);
				addT(tt,sy,lstT);
			}			
		}
		return lstAplicar.size()>0;
	}
}
