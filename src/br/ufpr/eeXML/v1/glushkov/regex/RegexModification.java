/*
 * Created on 27/04/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class RegexModification 
{
	private Regex regex;
	private Regex snl;
	private Regex snr;
	
	private Regex[] snlFollow;
	private Regex[] snlPrevious;
	
	private Regex[] snrFollow;
	private Regex[] snrPrevious;	
	
	
	private Regex snew;
	
	private Regex snlClone;
	private Regex snrClone;
	
	public RegexModification(Regex regex, Regex snl,  Regex snr, Regex snew) 
	{
		this.regex = regex;
		this.snew = snew;
		this.snl = snl;
		this.snr = snr;
		
		this.snlFollow = regex.follow(snl);
		this.snrFollow = regex.follow(snr);
		this.snlPrevious = regex.previous(snl);
		this.snrPrevious = regex.previous(snr);
	}

	public Regex getRegex() {
		return regex;
	}

	public Regex getSnew() {
		return snew;
	}

	public Regex getSnl() {
		return snl;
	}
	
	public Regex getSnr() {
		return snr;
	}

	public void setSnr(Regex snr) {
		this.snr = snr;
	}
	
	public RegexModification clone()
	{
		return new RegexModification(cloneRegex(regex),snlClone,snrClone,snew);
	}
	
	private Regex cloneRegex(Regex r)
	{
		Regex parent = r.getParent();
		if(r.getOperator()==Operator.NULL)
		{
			Regex clone = r.clone();
			if(r==snl)
				snlClone = clone;
			if(r==snr)
				snrClone = clone;
			return clone;
		}
		if(r.getOperator()==Operator.CONCAT)
			return new AndRegex(cloneRegex(r.getParameter(0)),cloneRegex(r.getParameter(1)));
		if(r.getOperator()==Operator.OR)
			return new OrRegex(cloneRegex(r.getParameter(0)),cloneRegex(r.getParameter(1)));
		if(r.getOperator()==Operator.OPTIONAL)
			return new OptRegex(cloneRegex(r.getParameter(0)));
		if(r.getOperator()==Operator.FECHO)
			return new StarRegex(cloneRegex(r.getParameter(0)));
		if(r.getOperator()==Operator.FECHO_ONE)
			return new StarOneRegex(cloneRegex(r.getParameter(0)));
		return null;
	}

	public Regex[] getSnlFollow() {
		return snlFollow;
	}

	public Regex[] getSnlPrevious() {
		return snlPrevious;
	}

	public Regex[] getSnrFollow() {
		return snrFollow;
	}

	public Regex[] getSnrPrevious() {
		return snrPrevious;
	}
	
}