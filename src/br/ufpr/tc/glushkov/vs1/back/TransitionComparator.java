/*
 * Created on 22/06/2005
 *
 */
package br.ufpr.tc.glushkov.vs1.back;

import java.util.Comparator;

import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class TransitionComparator  implements Comparator
{
	public int compare(Object o1, Object o2) 
	{
		try{
			Transition t1 = (Transition) o1;
			Transition t2 = (Transition) o2;
			return (new Integer(t2.getEstadoAtual()).intValue()) - (new Integer(t1.getEstadoAtual()).intValue());
		}catch(Exception e){}
		return 0;
	}
}
