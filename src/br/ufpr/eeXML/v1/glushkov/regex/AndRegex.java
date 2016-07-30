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
public class AndRegex extends Regex
{
	private Regex value1;
	private Regex value2;

	public AndRegex()
	{
	}

	public AndRegex(Regex value1) 
	{
		setValue1(value1);
	}
	
	public AndRegex(Regex value1, Regex value2) 
	{
		setValue1(value1);
		setValue2(value2);
		//setExp(this);
	}
	public AndRegex(String value1, Regex value2) 
	{
		this(new SingleRegex(value1),value2);
	}
	public AndRegex(Regex value1, String value2) 
	{
		this(value1,new SingleRegex(value2));
	}
	public AndRegex(String value1, String value2) 
	{
		this(new SingleRegex(value1),new SingleRegex(value2));
	}

	public Regex getValue1() {
		return value1;
	}

	public Regex getValue2() {
		return value2;
	}

	public void setValue1(Regex value1) {
		this.value1 = value1;
		this.value1.setParent(this);
	}

	public void setValue2(Regex value2) {
		this.value2 = value2;
		this.value2.setParent(this);
	}
	
	public Operator getOperator() 
	{
		return Operator.CONCAT;
	}
	public Regex getParameter(int i) 
	{
		if(i==0) return value1;
		if(i==1) return value2;
		return null;
	}
	
	public void setParameter(int i, Regex value) 
	{
		if(i==0) setValue1(value);
		if(i==1) setValue2(value);
	}
	
	public Regex clone()
	{
		return new AndRegex(value1.clone(),value2.clone());
	}
	
	public void setParameter(Regex oldRegex, Regex newRegex) 
	{
		if(oldRegex==value1)
			setValue1(newRegex);
		if(oldRegex==value2)
			setValue2(newRegex);
	}
	@Override public String toPrefixString() 
	{
		return ".("+getValue1().toPrefixString()+","+getValue2().toPrefixString()+")";
	}

	@Override public String toString() 
	{
		return value1+","+value2;
	}
	@Override public String toStringPositions() 
	{
		return value1.toStringPositions()+","+value2.toStringPositions();
	}
	
}
