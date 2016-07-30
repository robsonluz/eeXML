/*
 * Created on 27/04/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;
/*
 * @autor Robson João Padilha da Luz
 *
 */
public class RegexCloneable
{
	private Regex regex;
	private Regex subRegex;
	public Regex getRegex(){
		return regex;
	}
	public Regex getSubRegex(){
		return subRegex;
	}
	public void setRegex(Regex regex) {
		this.regex = regex;
	}
	public void setSubRegex(Regex snl) {
		this.subRegex = snl;
	}
}