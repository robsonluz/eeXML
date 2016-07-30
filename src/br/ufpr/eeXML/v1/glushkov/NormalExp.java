/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov;

import br.ufpr.eeXML.v1.glushkov.regex.Exp;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class NormalExp implements Exp
{
	private String exp;
	
	public NormalExp(String exp)
	{
		this.exp = exp;
	}
	
	public String getExp() 
	{
		return exp;
	}
	
	public Operator getOperator() 
	{
		throw new NotImplementedException();
	}
	
	public Exp getParameter(int i) 
	{
		throw new NotImplementedException();
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
}