/*
 * Created on 17/10/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov.grec;

import java.util.List;
import java.util.StringTokenizer;

import br.ufpr.eeXML.v1.glushkov.Automato;
import br.ufpr.eeXML.v1.glushkov.Transition;
import br.ufpr.eeXML.v1.glushkov.regex.SingleRegex;


/*
 * @autor Robson João Padilha da Luz
 *
 */
public class CheckAutomataWord 
{
	public static GraphModification checkAutomataWord(Automato a, String word)
	{
		char[] st = word.toCharArray();
		String currentState = "0";
		List<Transition> lt = a.getLstTransition();
		Transition tmp = null;
		boolean error = true;
		for(char c: st)
		{
			String n = String.valueOf(c);
			tmp = null;
			for(Transition t:lt)
			{
				//System.out.println(n+":"+t.getEstadoAtual()+":"+currentState+"-"+t.getTransicao()+":"+n);
				if(t.getEstadoAtual().equals(currentState))
				{
					if(t.getTransicao().equals(n))
					{
						currentState = t.getEstadoNovo().toString();
						error = false;
					}else{
						if(tmp==null)
							tmp = t;
					}
				}
			}
			if(error)
			{
				System.out.println("snew: "+n);
				System.out.println("snl: "+tmp.getEstadoAtual());
				System.out.println("snr: "+tmp.getEstadoNovo());
				GraphModification gm = new GraphModification();
				gm.setSnew(new SingleRegex(n));
				gm.setSnl(tmp.getEstadoAtual());
				gm.setSnr(tmp.getEstadoNovo());
				System.out.println("Falhou");
				return gm;
			}
			error = true;
		}
		

		return null;
	}
	
	public static GraphModification checkAutomataWord(Automato a, List<String> lstS)
	{
		String currentState = "0";
		List<Transition> lt = a.getLstTransition();
		Transition tmp = null;
		boolean error = true;
		for(String n:lstS)
		{
			//String n = String.valueOf(c);
			tmp = null;
			for(Transition t:lt)
			{
				//System.out.println(n+":"+t.getEstadoAtual()+":"+currentState+"-"+t.getTransicao()+":"+n);
				if(t.getEstadoAtual().equals(currentState))
				{
					if(t.getTransicao().equals(n))
					{
						currentState = t.getEstadoNovo().toString();
						error = false;
					}else{
						if(tmp==null)
							tmp = t;
					}
				}
			}
			if(error && tmp!=null)
			{
				System.out.println("snew: "+n);
				System.out.println("snl: "+tmp.getEstadoAtual());
				System.out.println("snr: "+tmp.getEstadoNovo());
				GraphModification gm = new GraphModification();
				gm.setSnew(new SingleRegex(n));
				gm.setSnl(tmp.getEstadoAtual());
				gm.setSnr(tmp.getEstadoNovo());
				System.out.println("Falhou");
				return gm;
			}
			error = true;
		}
		

		return null;
	}
	
	
}	