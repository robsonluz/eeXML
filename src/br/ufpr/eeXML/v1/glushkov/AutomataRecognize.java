/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class AutomataRecognize 
{
	private Automato automato;
	private List<Transition> lstTransition;
	
	public AutomataRecognize(Automato automato)
	{
		this.automato = automato;
		this.lstTransition = automato.getLstTransition();
	}
	
	public boolean recognize(String ... w)
	{
		List<String> lst = new ArrayList(w.length);
		for(int i=0;i<w.length;i++)
			lst.add(w[i]);
		return recognize(lst);
	}
	public boolean recognize(List<String> lstW)
	{
		String currentState = "0";
		for(String w:lstW)
		{
			Transition t = getTransition(currentState,w);
			if(t!=null)
			{
				currentState = t.getEstadoNovo().toString();
			}else{
				return false;
			}
		}
		if(isFinalState(currentState))
			return true;
		return false;
	}
	private boolean isFinalState(String state)
	{
		String f[] = automato.getF();
		for(String ff:f)
		{
			if(ff.equals(state))
				return true;
		}
		return false;
	}
	private Transition getTransition(String currentState,String w)
	{
		for(Transition t:lstTransition)
		{
			if(t.getEstadoAtual().equals(currentState) && t.getTransicao().equals(w))
				return t;
		}
		return null;
	}
}