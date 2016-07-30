/*
 * Created on 20/05/2005
 *
 */
package br.ufpr.tc.glushkov.vs1;

/**
 * @author Robson Jo�o Padilha da Luz
 *
 */
public enum Operator 
{
	/** Fecho = '*'  **/
	FECHO,

	/** Fecho pelo menos 1 = '+'  **/
	FECHO_ONE,
	
	/** ou = '|'  **/
	OR,
	
	/** concatena��o = '.'  **/
	CONCAT,
	
	/** Nenhum operador **/
	NULL
}