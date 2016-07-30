/*
 * Created on 28/04/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.LinkedList;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class RegexList extends LinkedList<Regex>
{

	@Override public boolean add(Regex o) 
	{
		if(!contains(o))
			return super.add(o);
		return false;
	}
	
	public void removeStartEndExpressions()
	{
		int c=0;
		for(Regex r: this)
			set(c++,removeStartEndExpression(r));
	}
	
	private Regex removeStartEndExpression(Regex r)
	{
		if(r instanceof UpdateRegex)
			return r;
		if(r instanceof AndRegex)
		{
			if(r.getParameter(0) instanceof SingleRegex && r.getParameter(0).equals(SingleRegex.START))
				return removeStartEndExpression(r.getParameter(1));
			if(r.getParameter(1) instanceof SingleRegex && r.getParameter(1).equals(SingleRegex.END))
				return removeStartEndExpression(r.getParameter(0));
			return new AndRegex(removeStartEndExpression(r.getParameter(0)),removeStartEndExpression(r.getParameter(1)));
		}
		if(r instanceof OrRegex)
		{
			return new OrRegex(removeStartEndExpression(r.getParameter(0)),removeStartEndExpression(r.getParameter(1)));
		}
		if(r instanceof OptRegex)
		{
			return new OptRegex(removeStartEndExpression(r.getParameter(0)));
		}
		if(r instanceof StarRegex)
		{
			return new StarRegex(removeStartEndExpression(r.getParameter(0)));
		}
		if(r instanceof StarOneRegex)
		{
			return new StarOneRegex(removeStartEndExpression(r.getParameter(0)));
		}
		if(r instanceof SingleRegex)
		{
			return r;
		}
		return null;
	}
}