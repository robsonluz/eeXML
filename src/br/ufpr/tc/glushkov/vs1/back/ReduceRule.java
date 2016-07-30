/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.tc.glushkov.vs1.back;

import java.util.List;

import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson Jo�o Padilha da Luz
 *
 */
public interface ReduceRule 
{
	/**
	 * Aplica a regra nas transi��es com n�s pertencentes � orbita.
	 * Caso a orbita seja vazia, � aplicada a regra em todos as transi��es.
	 * @param lstT Lista de transi��es
	 * @param orb Orbita
	 * @return retorna true se foi aplicada alguma regra
	 */
	public boolean reduce(List<Transition> lstT,Orbit orb); 
}