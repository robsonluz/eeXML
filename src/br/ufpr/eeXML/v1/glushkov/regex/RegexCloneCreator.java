/*
 * Created on 27/04/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class RegexCloneCreator 
{
	private Regex regex;
	private Regex snl;
	private Regex snlClone;
	
	public RegexCloneCreator(Regex regex, Regex snl)
	{
		this.regex = regex;
		this.snl = snl;
	}
	
	public RegexCloneable clone()
	{
		RegexCloneable clone = new RegexCloneable();
		clone.setRegex(cloneRegex(this.regex));
		clone.setSubRegex(snlClone);
		return clone;
	}

	private Regex cloneRegex(Regex r)
	{
		Regex parent = r.getParent();
		if(r.getOperator()==Operator.UPDATE)
		{
			Regex clone = r.clone();
			if(r==snl)
				snlClone = clone;
			return clone;		}
		if(r.getOperator()==Operator.NULL)
		{
			Regex clone = r.clone();
			if(r==snl)
				snlClone = clone;
			return clone;
		}
		if(r.getOperator()==Operator.CONCAT)
		{
			Regex clone = new AndRegex(cloneRegex(r.getParameter(0)),cloneRegex(r.getParameter(1)));
			if(r==snl)
				snlClone = clone;
			return clone;
		}
		if(r.getOperator()==Operator.OR){
			Regex clone = new OrRegex(cloneRegex(r.getParameter(0)),cloneRegex(r.getParameter(1)));
			if(r==snl)
				snlClone = clone;
			return clone;
		}
		if(r.getOperator()==Operator.OPTIONAL){
			Regex clone = new OptRegex(cloneRegex(r.getParameter(0)));
			if(r==snl)
				snlClone = clone;
			return clone;
		}
		if(r.getOperator()==Operator.FECHO){
			Regex clone = new StarRegex(cloneRegex(r.getParameter(0)));
			if(r==snl)
				snlClone = clone;
			return clone;
		}
		if(r.getOperator()==Operator.FECHO_ONE){
			Regex clone = new StarOneRegex(cloneRegex(r.getParameter(0)));
			if(r==snl)
				snlClone = clone;
			return clone;
		}
		throw new RuntimeException("Operator not implemented: "+r.getOperator());
	}
}