/*
 * Created on 27/04/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import com.sun.corba.se.impl.ior.OldPOAObjectKeyTemplate;

import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class RegexCloneCreatorUpdate 
{
	private Regex regex;
	private Regex[] oldRegex;
	
	private Regex[] newRegex;
	
	public RegexCloneCreatorUpdate(Regex regex, Regex oldRegex[])
	{
		this.regex = regex;
		this.oldRegex = oldRegex;
	}
	
	public Regex clone(Regex[] newRegex)
	{
		this.newRegex = newRegex;
		return cloneRegex(regex);
	}

	private Regex cloneRegex(Regex r)
	{
		for(int i=0;i<oldRegex.length;i++){
			if(r==oldRegex[i])
				return newRegex[i];
		}
		
		if(r.getOperator()==Operator.UPDATE)
		{
			Regex clone = r.clone();
			return clone;
		}		
		if(r.getOperator()==Operator.NULL)
		{
			Regex clone = r.clone();
			return clone;
		}
		if(r.getOperator()==Operator.CONCAT)
		{
			Regex clone = new AndRegex(cloneRegex(r.getParameter(0)),cloneRegex(r.getParameter(1)));
			return clone;
		}
		if(r.getOperator()==Operator.OR){
			Regex clone = new OrRegex(cloneRegex(r.getParameter(0)),cloneRegex(r.getParameter(1)));
			return clone;
		}
		if(r.getOperator()==Operator.OPTIONAL){
			Regex clone = new OptRegex(cloneRegex(r.getParameter(0)));
			return clone;
		}
		if(r.getOperator()==Operator.FECHO){
			Regex clone = new StarRegex(cloneRegex(r.getParameter(0)));
			return clone;
		}
		if(r.getOperator()==Operator.FECHO_ONE){
			Regex clone = new StarOneRegex(cloneRegex(r.getParameter(0)));
			return clone;
		}
		throw new RuntimeException("Operator not implemented: "+r.getOperator());
//		return null;
	}
}