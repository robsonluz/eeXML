/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov.grec;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.eeXML.v1.glushkov.Transition;
import br.ufpr.eeXML.v1.glushkov.regex.OptRegex;
import br.ufpr.eeXML.v1.glushkov.regex.Regex;
import br.ufpr.eeXML.v1.glushkov.regex.StarOneRegex;
import br.ufpr.eeXML.v1.glushkov.regex.StarRegex;


/**
 * @author Robson João Padilha da Luz
 *
 */
public class Rule3 extends ReduceRuleImpl
{
	
	public int reduce(List<Transition> lstT, Orbit orb,GraphModification gm) 
	{
		List<Transition> lstAplicar = new LinkedList();
		
		for(Transition t: lstT)
		{
			Regex x = t.getEstadoAtual();
			Regex y[] = ant(x,lstT);
			Regex sucx[] = suc(x,lstT);
			if(sucx.length==1  && contido(sucx,sucs(y,lstT)))//Aplicar regra 3
			{
				if(orb.contains(t.getEstadoAtual()) && orb.contains(t.getEstadoNovo())){
					System.out.println("Aplicar R3: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
					lstAplicar.add(t);
					
					if(!gm.isExecuted())
					{
						//Modificação 1
						if(pertence(gm.getSnl(),ant(x,lstT)) && pertence(gm.getSnr(),suc(x,lstT)))
						{
							System.out.println("Modificação 1 em R3");
							
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
						
						//Modificação 2
						if(pertence(gm.getSnl(),ant(x,lstT)) && !pertence(gm.getSnr(),suc(x,lstT)))
						{
							System.out.println("Modificação 2 em R3");

							Regex[] ss = suc(x,lstT);
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
							return 2;
						}
						
						//Modificação 3
						if(!pertence(gm.getSnl(),ant(x,lstT)) && pertence(gm.getSnr(),suc(x,lstT)))
						{
							System.out.println("Modificação 3 em R3");
							
							Regex[] ss = ant(x,lstT);
							for(Regex s:ss)
							{
								Transition tnew = new Transition();
								tnew.setEstadoAtual(s);
								tnew.setEstadoNovo(gm.getSnew());
								lstT.add(tnew);
							}
							
							ss = suc(x,lstT);
							for(Regex s:ss)
							{
								Transition tnew = new Transition();
								tnew.setEstadoAtual(gm.getSnew());
								tnew.setEstadoNovo(s);
								lstT.add(tnew);
							}
							gm.setExecuted(true);
							return 2;
						}								
					}
					
				}
			}
		}
		
		for(Transition t: lstAplicar)
		{
			Regex x = t.getEstadoAtual();
			Regex anttx[] = ant(x,lstT);
			Regex succx[] = suc(x,lstT);
			boolean b = false;
			for(int i=0;i<anttx.length;i++)
			{
				Regex antx = anttx[i];
				//if(succx.length>i)
				{
					remover(antx,succx[0],lstT);
					b = true;
				}
			}
			if(b){
				setOpcional(x,lstT);
				
				orb.ajuste(x,getOpcional(x));
				System.out.println("OPcional x"+x);
			}
		}
		
		
		if(lstAplicar.size()>0) return 1; else return 0;
	}
	
	private void setOpcional(Regex estado,List<Transition> lstT)
	{
		for(Transition t: lstT)
		{

			if(t.getEstadoAtual().equals(estado) && !(t.getEstadoAtual() instanceof OptRegex))
				t.setEstadoAtual(getOpcional(estado));
			
			if(t.getEstadoNovo().equals(estado) && !(t.getEstadoNovo() instanceof OptRegex))
				t.setEstadoNovo(getOpcional(estado));			
		}
	}
	
	private Regex getOpcional(Regex estado)
	{
		if(estado instanceof StarOneRegex)
		{
			return new StarRegex(((StarOneRegex) estado).getValue());
		}
		return new OptRegex(estado);
	}
}