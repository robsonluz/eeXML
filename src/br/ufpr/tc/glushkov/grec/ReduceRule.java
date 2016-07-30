/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.tc.glushkov.grec;

import java.util.List;

import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public interface ReduceRule 
{
	/**
	 * Aplica a regra nas transições com nós pertencentes à orbita.
	 * Caso a orbita seja vazia, é aplicada a regra em todos as transições.
	 * @param lstT Lista de transições
	 * @param orb Orbita
	 * @return retorna true se foi aplicada alguma regra
	 */
	public int reduce(List<Transition> lstT,Orbit orb,GraphModification gm); 
}