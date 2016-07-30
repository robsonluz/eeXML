/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.tc.glushkov.regex;
/*
 * @autor Robson João Padilha da Luz
 *
 */
public class AndRegex extends RegexImpl<AndRegex>
{
	private Regex value1;
	private Regex value2;

	public AndRegex(Regex value1, Regex value2) 
	{
		this.value1 = value1;
		this.value2 = value2;
		setExp(this);
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
	}

	public void setValue2(Regex value2) {
		this.value2 = value2;
	}
}
