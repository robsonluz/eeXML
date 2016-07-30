/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.tc.glushkov.regex;
/*
 * @autor Robson João Padilha da Luz
 *
 */
public class SingleRegex extends RegexImpl
{
	private String value;

	public SingleRegex(String value)
	{
		this.value = value;
		setExp(this);
	}
	
	public String getValue() 
	{
		return value;
	}

	public void setValue(String value) 
	{
		this.value = value;
	}

}