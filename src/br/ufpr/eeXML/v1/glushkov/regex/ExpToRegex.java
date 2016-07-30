/*
 * Created on 07/04/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import br.ufpr.eeXML.v1.glushkov.Operator;


/*
 * @autor Robson João Padilha da Luz
 *
 */
public class ExpToRegex 
{
	public static Regex convert(Exp exp)
	{
		if(exp.getOperator()==Operator.NULL)
			return new SingleRegex(exp.getExp());
		if(exp.getOperator()==Operator.CONCAT)
			return new AndRegex(convert(exp.getParameter(0)),convert(exp.getParameter(1)));
		if(exp.getOperator()==Operator.OR)
			return new OrRegex(convert(exp.getParameter(0)),convert(exp.getParameter(1)));
		if(exp.getOperator()==Operator.FECHO)
			return new StarRegex(convert(exp.getParameter(0)));
		if(exp.getOperator()==Operator.FECHO_ONE)
			return new StarOneRegex(convert(exp.getParameter(0)));
		if(exp.getOperator()==Operator.OPTIONAL)
			return new OptRegex(convert(exp.getParameter(0)));
		return null;
	}
}
