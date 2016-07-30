/*
 * Created on 20/05/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov;

/**
 * @author Robson João Padilha da Luz
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
	
	/** concatenação = '.'  **/
	CONCAT,
	
	/** Nenhum operador **/
	NULL,
	
	/** opcional = '?' **/
	OPTIONAL,
	
	/** update exp **/
	UPDATE
}