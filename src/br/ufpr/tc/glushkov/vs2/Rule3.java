/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.tc.glushkov.vs2;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.tc.glushkov.regex.OptRegex;
import br.ufpr.tc.glushkov.regex.Regex;
import br.ufpr.tc.glushkov.regex.StarOneRegex;
import br.ufpr.tc.glushkov.regex.StarRegex;
import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Rule3 extends ReduceRuleImpl
{
	
	public boolean reduce(List<TransitionRegex> lstT, Orbit orb) 
	{
		List<TransitionRegex> lstAplicar = new LinkedList();
		
		for(TransitionRegex t: lstT)
		{
			Regex x = t.getEstadoAtual();
			Regex y[] = ant(x,lstT);
			Regex sucx[] = suc(x,lstT);
			if(sucx.length==1  && contido(sucx,sucs(y,lstT)))//Aplicar regra 3
			{
				if(orb.contains(t.getEstadoAtual()) && orb.contains(t.getEstadoNovo())){
					System.out.println("Aplicar R3: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
					lstAplicar.add(t);
				}
			}
		}
		
		for(TransitionRegex t: lstAplicar)
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
		
		
		return lstAplicar.size()>0;
	}
	
	private void setOpcional(Regex estado,List<TransitionRegex> lstT)
	{
		for(TransitionRegex t: lstT)
		{
//			if(t.getEstadoAtual().equals(estado) && !t.getEstadoAtual().endsWith("?"))
//				t.setEstadoAtual(getOpcional(estado));
//			
//			if(t.getEstadoNovo().equals(estado) && !t.getEstadoNovo().endsWith("?"))
//				t.setEstadoNovo(getOpcional(estado));
			
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
//		if(estado.endsWith("+"))
//		{
//			StringBuffer sb = new StringBuffer(estado);
//			sb.delete(estado.length()-1,estado.length());
//			sb.insert(estado.length()-1,"*");
//			return sb.toString();
//		}
//		return estado+"?";
	}
}
