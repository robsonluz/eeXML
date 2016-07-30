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
public class Rule3 extends ReduceRuleImpl
{
	
	public boolean reduce(List<Transition> lstT, Orbit orb) 
	{
		List<Transition> lstAplicar = new LinkedList();
		
		for(Transition t: lstT)
		{
			String x = t.getEstadoAtual();
			String y[] = ant(x,lstT);
			String sucx[] = suc(x,lstT);
			if(sucx.length==1  && contido(sucx,sucs(y,lstT)))//Aplicar regra 3
			{
				if(orb.contains(t.getEstadoAtual()) && orb.contains(t.getEstadoNovo())){
					System.out.println("Aplicar R3: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
					lstAplicar.add(t);
				}
			}
		}
		
		for(Transition t: lstAplicar)
		{
			String x = t.getEstadoAtual();
			String anttx[] = ant(x,lstT);
			String succx[] = suc(x,lstT);
			boolean b = false;
			for(int i=0;i<anttx.length;i++)
			{
				String antx = anttx[i];
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
		
		
		return lstAplicar.size()>0;
	}
	
	private void setOpcional(String estado,List<Transition> lstT)
	{
		for(Transition t: lstT)
		{
			if(t.getEstadoAtual().equals(estado) && !t.getEstadoAtual().endsWith("?"))
				t.setEstadoAtual(getOpcional(estado));
			
			if(t.getEstadoNovo().equals(estado) && !t.getEstadoNovo().endsWith("?"))
				t.setEstadoNovo(getOpcional(estado));
		}
	}
	
	private String getOpcional(String estado)
	{
		if(estado.endsWith("+"))
		{
			StringBuffer sb = new StringBuffer(estado);
			sb.delete(estado.length()-1,estado.length());
			sb.insert(estado.length()-1,"*");
			return sb.toString();
		}
		return estado+"?";
	}
}
