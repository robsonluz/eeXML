/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.tc.glushkov.regex;
/*
 * @autor Robson João Padilha da Luz
 *
 */
public class StarRegex extends RegexImpl
{
	private Regex value;
	
	public StarRegex(Regex value)
	{
		this.value = value;
		setExp(this);
	}
	public StarRegex(String value)
	{
		this(new SingleRegex(value));
	}
	
	public Regex getValue()
	{
		return this.value;
	}
}
