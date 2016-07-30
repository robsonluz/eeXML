/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.tc.glushkov.regex;
/*
 * @autor Robson João Padilha da Luz
 *
 */
public class StarOneRegex extends RegexImpl
{
	private Regex value;
	
	public StarOneRegex(Regex value)
	{
		this.value = value;
		setExp(this);
	}
	public StarOneRegex(String value)
	{
		this.value = new SingleRegex(value);
	}
	
	public Regex getValue()
	{
		return this.value;
	}
}
