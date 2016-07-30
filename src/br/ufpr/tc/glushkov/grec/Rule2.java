/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.tc.glushkov.grec;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Rule2 extends ReduceRuleImpl
{
	public int reduce(List<Transition> lstT, Orbit orb,GraphModification gm) 
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
							
							if(!gm.isExecuted())
							{
								//Modificação 1
								if(gm.getSnl().equals(x) && pertence(gm.getSnr(),suc(y,lstT)))
								{
									System.out.println("Modificação 1 em R2");
									
									String[] ss = suc(y,lstT);
									for(String s:ss)
									{
										Transition tnew = new Transition();
										tnew.setEstadoAtual(gm.getSnew());
										tnew.setEstadoNovo(s);
										lstT.add(tnew);
									}
									
									Transition tnew = new Transition();
									tnew.setEstadoAtual(x);
									tnew.setEstadoNovo(gm.getSnew());
									lstT.add(tnew);
									gm.setExecuted(true);
									return 2;
								}
								
								//Modificação 2
								if(gm.getSnr().equals(x) && pertence(gm.getSnl(),ant(x,lstT)))
								{
									System.out.println("Modificação 2 em R2");

									
									String[] ss = ant(x,lstT);
									for(String s:ss)
									{
										Transition tnew = new Transition();
										tnew.setEstadoAtual(s);
										tnew.setEstadoNovo(gm.getSnew());
										lstT.add(tnew);
									}
									Transition tnew = new Transition();
									tnew.setEstadoAtual(gm.getSnew());
									tnew.setEstadoNovo(x);
									lstT.add(tnew);
									gm.setExecuted(true);
									return 2;
								}
								
								//Modificação 3
								if(pertence(gm.getSnl(),ant(x,lstT)) && pertence(gm.getSnr(),suc(y,lstT)))
								{
									System.out.println("Modificação 3 em R2");
									
									String[] ex = ant(x,lstT);
									for(String ax:ex)
									{
										Transition tnew = new Transition();
										tnew.setEstadoAtual(ax);
										tnew.setEstadoNovo(gm.getSnew());
										lstT.add(tnew);
									}
									String[] ey = suc(y,lstT);
									for(String sy:ey)
									{
										Transition tnew = new Transition();
										tnew.setEstadoAtual(gm.getSnew());
										tnew.setEstadoNovo(sy);
										lstT.add(tnew);
									}
									gm.setExecuted(true);
									return 2;
								}								
							}
							
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
		if(lstAplicar.size()>0) return 1; else return 0;
	}
}
