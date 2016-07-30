/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.List;

import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class ReGexParser 
{
	public String parserToNormal(Regex exp)
	{
		if(exp instanceof SingleRegex)
		{
			return ((SingleRegex) exp).getValue();
		}
		if(exp instanceof StarRegex)
		{
			StarRegex star = (StarRegex) exp;
			Regex value = star.getValue();
			if(value instanceof SingleRegex || value instanceof OrRegex)
				return parserToNormal(value)+"*";
			else
				return "("+parserToNormal(value)+")*";
		}
		if(exp instanceof OptRegex)
		{
			OptRegex star = (OptRegex) exp;
			Regex value = star.getValue();
			if(value instanceof SingleRegex || value instanceof OrRegex)
				return parserToNormal(value)+"?";
			else
				return "("+parserToNormal(value)+")?";
		}		
		if(exp instanceof StarOneRegex)
		{
			StarOneRegex star = (StarOneRegex) exp;
			Regex value = star.getValue();
			if(value instanceof SingleRegex || value instanceof OrRegex)
				return parserToNormal(value)+"+";
			else
				return "("+parserToNormal(value)+")+";
		}
		if(exp instanceof AndRegex)
		{
			return parserToNormal(((AndRegex) exp).getValue1())+","+parserToNormal(((AndRegex) exp).getValue2());
		}
		if(exp instanceof OrRegex)
		{
			if(exp.getParent()!=null && exp.getParent() instanceof OrRegex)
				return parserToNormal(((OrRegex) exp).getValue1())+"|"+parserToNormal(((OrRegex) exp).getValue2());
			else
				return "("+parserToNormal(((OrRegex) exp).getValue1())+"|"+parserToNormal(((OrRegex) exp).getValue2())+")";
		}
		if(exp instanceof UpdateRegex)
		{
			UpdateRegex up = (UpdateRegex) exp;
			StringBuffer sb = new StringBuffer();
			int c=0;
			for(Regex r:up.getLstRegex())
			{
				sb.append(r.toString());
				if(c<up.getLstRegex().size()-1)
					sb.append(",");
				c++;
			}
			return "("+sb.toString()+")$";
		}
		return "";
	}
}
