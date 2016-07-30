/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.tc.glushkov.regex;
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
			if(value instanceof SingleRegex)
				return parserToNormal(value)+"*";
			else
				return "("+parserToNormal(value)+")*";
		}
		if(exp instanceof OptRegex)
		{
			OptRegex star = (OptRegex) exp;
			Regex value = star.getValue();
			if(value instanceof SingleRegex)
				return parserToNormal(value)+"?";
			else
				return "("+parserToNormal(value)+")?";
		}		
		if(exp instanceof StarOneRegex)
		{
			StarOneRegex star = (StarOneRegex) exp;
			Regex value = star.getValue();
			if(value instanceof SingleRegex)
				return parserToNormal(value)+"+";
			else
				return "("+parserToNormal(value)+")+";
		}
		if(exp instanceof AndRegex)
		{
//			String ret = "";
//			AndRegex and = (AndRegex) exp;
//			ReGex value = and.getValue1();
//			if(value instanceof StarRegex)
//			{
//				ret += "("+parserToNormal(value)+")";
//			}else{
//				ret += parserToNormal(value);
//			}
//			ret += ",";
//			
//			value = and.getValue2();
//			if(value instanceof StarRegex)
//			{
//				ret += "("+parserToNormal(value)+")";
//			}else{
//				ret += parserToNormal(value);
//			}
//			return ret;
			return parserToNormal(((AndRegex) exp).getValue1())+","+parserToNormal(((AndRegex) exp).getValue2());
		}
		if(exp instanceof OrRegex)
		{
			return parserToNormal(((OrRegex) exp).getValue1())+"|"+parserToNormal(((OrRegex) exp).getValue2());
		}
		return "";
	}
}
