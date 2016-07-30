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
public class Rule1 extends ReduceRuleImpl
{
	
	public int reduce(List<Transition> lstT,Orbit orb,GraphModification gm) 
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
					
					if(!gm.isExecuted())
					{
//						Condição 1
						if(gm.getSnl().equals(t.getEstadoAtual()) && gm.getSnr().equals(t.getEstadoNovo()))
						{
							System.out.println("Modificação 1 em R1");
							Transition tnew = new Transition();
							tnew.setEstadoAtual(t.getEstadoAtual());
							tnew.setEstadoNovo(gm.getSnew());
							//tnew.setTransicao(t.getTransicao());
							lstT.add(tnew);
							
							tnew = new Transition();
							tnew.setEstadoAtual(gm.getSnew());
							tnew.setEstadoNovo(t.getEstadoNovo());
							//tnew.setTransicao(t.getTransicao());
							lstT.add(tnew);
							
							//lstT.add()
							gm.setExecuted(true);
							return 2;
						}
						
						//Condição 2
						Transition be = orb.getBackEdge();
						if(be!=null && be.getEstadoAtual().equals(gm.getSnl()) && be.getEstadoNovo().equals(gm.getSnr()))
						{
							System.out.println("Modificação 2 em R1");
							Transition tnew = new Transition();
							tnew.setEstadoAtual(gm.getSnew());
							tnew.setEstadoNovo(t.getEstadoAtual());
							lstT.add(tnew);
							orb.add(gm.getSnew());
							
							
							String[] st = ant(t.getEstadoAtual(),lstT);
							for(String stt: st)
							{
								if(!stt.equals(gm.getSnew()))
								{
									tnew = new Transition();
									tnew.setEstadoAtual(stt);
									tnew.setEstadoNovo(gm.getSnew());
									lstT.add(tnew);
								}
							}

							tnew = new Transition();
							tnew.setEstadoAtual(t.getEstadoNovo());
							tnew.setEstadoNovo(gm.getSnew());
							lstT.add(tnew);
							
							orb.setEstadoNovoBackEdge(gm.getSnew());
							orb.setEstadoAtualBackEdge(t.getEstadoNovo());
						
							
							gm.setExecuted(true);
							
							return 2;
						}
					}
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
		
		if(lstAplicar.size()>0) return 1; else return 0;
	}
}
