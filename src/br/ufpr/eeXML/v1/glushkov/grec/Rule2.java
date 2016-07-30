/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov.grec;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.eeXML.v1.glushkov.Transition;
import br.ufpr.eeXML.v1.glushkov.regex.OrRegex;
import br.ufpr.eeXML.v1.glushkov.regex.Regex;


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
			Regex x = tx.getEstadoAtual();
			if(orb.contains(x))
			for(Transition ty: lstT)
			{
				Regex y = ty.getEstadoAtual();
				if(!x.equals(y) && orb.contains(y))
				{
					if(equals(ant(x,lstT),ant(y,lstT)) && equals(suc(x,lstT),suc(y,lstT)))
					{
						if(!in(lstAplicar,x,y))
						{

							
							if(!gm.isExecuted())
							{
								//Modificação 1
								//if(gm.getSnl().equals(x) && pertenceToFirst(gm.getSnr(),suc(y,lstT)))
								if(pertence(gm.getSnl(),last(x)) && pertenceToFirst(gm.getSnr(),suc(y,lstT)))
								{
									System.out.println("Modificação 1/1 em R2");
									modificacao1(x,y,lstT,gm);
									return 2;
								//}else if(gm.getSnl().equals(y) && pertenceToFirst(gm.getSnr(),suc(x,lstT))){
								}else if(pertence(gm.getSnl(),last(y)) && pertenceToFirst(gm.getSnr(),suc(x,lstT))){
									System.out.println("Modificação 1/2 em R2");
									modificacao1(y,x,lstT,gm);
									return 2;
								}								
								
								//Modificação 2
								if(gm.getSnr().equals(x) && pertence(gm.getSnl(),ant(x,lstT)))
								{
									System.out.println("Modificação 2 em R2");

									
									Regex[] ss = ant(x,lstT);
									for(Regex s:ss)
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
									
									Regex[] ex = ant(x,lstT);
									for(Regex ax:ex)
									{
										Transition tnew = new Transition();
										tnew.setEstadoAtual(ax);
										tnew.setEstadoNovo(gm.getSnew());
										lstT.add(tnew);
									}
									Regex[] ey = suc(y,lstT);
									for(Regex sy:ey)
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
							
							//Mudei aqui x,y para y,x//
							System.out.println("Aplicar R2: "+y+" : "+x);
							//System.out.println(x+" -> "+y);
							lstAplicar.add(new Transition(y,x));
						}
					}
				}
			}
		}
		
		for(Transition t: lstAplicar)
		{
			Regex x = t.getEstadoAtual();
			Regex y = t.getEstadoNovo();
			
			//String tt = "("+t.getEstadoAtualParenteses()+"|"+t.getEstadoNovoParenteses()+")";
			Regex tt = new OrRegex(x,y);
			
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
		if(lstAplicar.size()>0) return 1; else return 0;
	}
	
	private void modificacao1(Regex x, Regex y,List<Transition> lstT,GraphModification gm)
	{
		Regex[] ss = suc(y,lstT);
		for(Regex s:ss)
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
	}
}
