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
public class SingleRegex extends Regex
{
	private String value;
	public static final String START="#";
	public static final String END="#";
	private int position;

	public SingleRegex(String value)
	{
		this.value = value;
		//setExp(this);
	}
	
	public String getValue() 
	{
		return value;
	}

	public void setValue(String value) 
	{
		this.value = value;
		//setExp(this);
	}
	public Operator getOperator() 
	{
		return Operator.NULL;
	}
	public Regex getParameter(int i) 
	{
		return this;
	}
	public void setParameter(int i, Regex value) 
	{
		if(i==0) this.value=value.toString();
	}
	
	public Regex clone()
	{
		return new SingleRegex(value);
	}
	
	public void setParameter(Regex oldRegex, Regex newRegex) 
	{
	}
	
	public boolean equals(Object obj)
	{
		if(obj==null) return false;
		if(obj instanceof String) return obj.equals(value);
		if(obj instanceof SingleRegex) return ((SingleRegex)obj).value.equals(value);
		return false;
	}
	
	@Override public String toPrefixString() 
	{
		return getValue();
	}
	@Override public String toString()
	{
		return value;
	}
	@Override public String toStringPositions()
	{
		return ""+getPosition();
	}

	public int getPosition()
	{
		return position;
	}
	public void setPosition(int position)
	{
		this.position = position;
	}
	@Override public boolean isPosition(int p)
	{
		return p==position;
	}
	@Override public int[] pos()
	{
		return new int[]{position};
	}
	@Override public boolean equalsPosition(Regex r)
	{
		if(r==null || !(r instanceof SingleRegex))
			return false;
		return position == ((SingleRegex)r).position;
	}
}