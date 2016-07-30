/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.tc.glushkov.regex;

import br.ufpr.tc.glushkov.vs1.Operator;

/**
 * @author Robson João Padilha da Luz
 *
 */
public interface Exp 
{
	public Exp getParameter(int i);
	public Operator getOperator();
	public String getExp();
	public void setExp(String exp);
}