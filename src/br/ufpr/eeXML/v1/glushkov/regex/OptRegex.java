/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class OptRegex extends Regex
{
	private Regex value;
	
	public OptRegex(Regex value)
	{
		setValue(value);
		//setExp(this);
	}
	public OptRegex(String value)
	{
		this(new SingleRegex(value));
	}
	
	public Regex getValue()
	{
		return this.value;
	}
	
	public void setValue(Regex value)
	{
		this.value = value;
		this.value.setParent(this);
	}
	
	public Operator getOperator() 
	{
		return Operator.OPTIONAL;
	}
	
	public Regex getParameter(int i) 
	{
		if(i==0) return value;
		return null;
	}
	
	public void setParameter(int i, Regex value) 
	{
		if(i==0) setValue(value);
	}
	
	public Regex clone()
	{
		return new OptRegex(value.clone());
	}
	public void setParameter(Regex oldRegex, Regex newRegex) 
	{
		if(oldRegex==value)
			setValue(newRegex);
	}
	@Override public String toPrefixString() 
	{
		return "?("+getValue().toPrefixString()+")";
	}
	@Override public String toString() 
	{
		if(value instanceof SingleRegex || value instanceof OrRegex)
			return value+"?";
		else
			return "("+value+")?";
	}
	@Override public String toStringPositions() 
	{
		if(value instanceof SingleRegex || value instanceof OrRegex)
			return value.toStringPositions()+"?";
		else
			return "("+value.toStringPositions()+")?";
	}
}
