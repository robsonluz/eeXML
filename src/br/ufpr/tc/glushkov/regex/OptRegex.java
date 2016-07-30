/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.tc.glushkov.regex;
/*
 * @autor Robson João Padilha da Luz
 *
 */
public class OptRegex extends RegexImpl
{
	private Regex value;
	
	public OptRegex(Regex value)
	{
		this.value = value;
		setExp(this);
	}
	public OptRegex(String value)
	{
		this.value = new SingleRegex(value);
	}
	
	public Regex getValue()
	{
		return this.value;
	}
}
