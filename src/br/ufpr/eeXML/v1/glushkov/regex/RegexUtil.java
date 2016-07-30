/*
 * Created on 27/04/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.ArrayList;
import java.util.Collections;

import br.ufpr.eeXML.v1.glushkov.Common;
import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class RegexUtil 
{
	public static Regex[] follow(Regex exp, Regex set[])
	{
		Regex ret[] = new Regex[0];
		for(Regex r: set)
			ret = union(ret,exp.follow(r));
		return ret;
	}
	//public static int f = 0;
	public static Regex[] follow(Regex exp, Regex x)
	{
		//System.out.println("follow["+(++f)+"]: "+exp+" ; "+x);
		if(exp==null || exp.getOperator()==Operator.NULL)
		{
			return new Regex[0];
		}
		Regex ret[] = new Regex[0];
		if(exp.getOperator()==Operator.OR){
			if(ix(exp.getParameter(0).pos(),x))
				ret = follow(exp.getParameter(0),x);
			if(ix(pos(exp.getParameter(1)),x))
				ret = union(ret,follow(exp.getParameter(1),x));
			return ret;
		}
		if(exp.getOperator()==Operator.CONCAT){
			if(ix(exp.getParameter(0).pos(),x))
				ret = follow(exp.getParameter(0),x);
			if(ix(exp.getParameter(1).pos(),x))
				ret = union(ret,follow(exp.getParameter(1),x));
			if(ix(exp.getParameter(0).last(),x))
				ret = union(ret,exp.getParameter(1).first());
			return ret;
		}
		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE ){
			if(ix(exp.getParameter(0).pos(),x))//Duvidoso : pode dar errado
				ret = follow(exp.getParameter(0),x);
			if(ix(exp.getParameter(0).last(),x))
				ret = union(ret,exp.getParameter(0).first());
			return ret;
		}
		if(exp.getOperator()==Operator.OPTIONAL)
			return follow(exp.getParameter(0),x);
		return new Regex[0];
	}
	
	public static Regex[] previous(Regex exp, Regex set[])
	{
		Regex ret[] = new Regex[0];
		for(Regex r: set)
			ret = union(ret,exp.previous(r));
		return ret;
	}	
	
	//private static int f=0;
	public static Regex[] previous(Regex exp, Regex x)
	{
		//System.out.println("previous["+(++f)+"]: "+exp+" ; "+x);
		if(exp==null || exp.getOperator()==Operator.NULL)
		{
			return new Regex[0];
		}
		Regex ret[] = new Regex[0];
		if(exp.getOperator()==Operator.OR){
			if(ix(exp.getParameter(0).pos(),x))
				ret = previous(exp.getParameter(0),x);
			if(ix(exp.getParameter(1).pos(),x))
				ret = union(ret,previous(exp.getParameter(1),x));
//			return union(product(ix(pos(exp.getParameter(0)),x),previous(exp.getParameter(0),x)),
//								product(ix(pos(exp.getParameter(1)),x),previous(exp.getParameter(1),x)));
		}
		if(exp.getOperator()==Operator.CONCAT){
			if(ix(exp.getParameter(0).pos(),x))
				ret = previous(exp.getParameter(0),x);
			if(ix(exp.getParameter(1).pos(),x))
				ret = union(ret,previous(exp.getParameter(1),x));
			if(ix(exp.getParameter(1).first(),x))
				ret = union(ret,exp.getParameter(0).last());
			return ret;
//			return union(union(product(ix(pos(exp.getParameter(0)),x),previous(exp.getParameter(0),x)),
//					product(ix(pos(exp.getParameter(1)),x),previous(exp.getParameter(1),x))),
//					product(ix(exp.getParameter(1).first(),x),exp.getParameter(0).last() ));
		}
		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE){
			ret = previous(exp.getParameter(0),x);
			if(ix(exp.getParameter(0).first(),x))
				ret = union(ret,exp.getParameter(0).last());
			return ret;
			//return union(previous(exp.getParameter(0),x), product( ix(exp.getParameter(0).first(),x) , exp.getParameter(0).last()   ));
		}
		if(exp.getOperator()==Operator.OPTIONAL)
			return previous(exp.getParameter(0),x);		
		return new Regex[0];
	}	
	
	public static boolean nullE(Regex exp)
	{
		if(exp==null)
			return false;
		if(exp.getOperator()==Operator.NULL)
		{
			if(exp.toString().equals("&"))
				return true;
			return false;
		}
		if(exp.getOperator()==Operator.OR)
			return nullE(exp.getParameter(0)) || nullE(exp.getParameter(1));
		if(exp.getOperator()==Operator.CONCAT)
			return nullE(exp.getParameter(0)) && nullE(exp.getParameter(1));
		if(exp.getOperator()==Operator.FECHO_ONE)
			return nullE(exp.getParameter(0));
		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.OPTIONAL || exp.getOperator()==Operator.UPDATE)
			return true;
		return false;
	}
	
	//private static int f = 0;
	public static Regex[] first(Regex exp)
	{
		//System.out.println("First["+(++f)+"]: "+exp);
		if(exp==null)
			return new Regex[0];
		
		//PrefixExp exp = new PrefixExp(e);
		if(exp.getOperator()==Operator.NULL)
		{
			if(exp.toString().equals("&"))
				return new Regex[0];
			return new Regex[]{exp};
		}
			
//		if(exp.getOperator()==Operator.OR)
//			return union(first(exp.getParameter(0)) , first(exp.getParameter(1)));
//		if(exp.getOperator()==Operator.CONCAT)
//			return union(first(exp.getParameter(0)) , product(nullE(exp.getParameter(0)),first(exp.getParameter(1))));
//		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE || exp.getOperator()==Operator.OPTIONAL)
//			return first(exp.getParameter(0));
		if(exp.getOperator()==Operator.OR)
			return union(exp.getParameter(0).first() , exp.getParameter(1).first());
		if(exp.getOperator()==Operator.CONCAT){
			Regex ret[] = exp.getParameter(0).first();
			if(nullE(exp.getParameter(0)))
				ret = union(ret,exp.getParameter(1).first());
			return ret;
//			return union(exp.getParameter(0).first() , product(nullE(exp.getParameter(0)),exp.getParameter(1).first()));
		}
		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE || exp.getOperator()==Operator.OPTIONAL)
			return exp.getParameter(0).first();
		
		return new Regex[0];
	}
	
	//private static int l=0;
	public static Regex[] last(Regex exp)
	{
		//System.out.println("Last["+(++l)+"]: "+exp);
		if(exp==null)
			return new Regex[0];
		if(exp.getOperator()==Operator.NULL)
		{
			if(exp.toString().equals("&"))
				return new Regex[0];
			return new Regex[]{exp};
		}
//		if(exp.getOperator()==Operator.OR)
//			return union(last(exp.getParameter(0)) , last(exp.getParameter(1)));
//		if(exp.getOperator()==Operator.CONCAT)
//			return union(last(exp.getParameter(1)) , product(nullE(exp.getParameter(1)),last(exp.getParameter(0))));
//		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE || exp.getOperator()==Operator.OPTIONAL)
//			return last(exp.getParameter(0));
		if(exp.getOperator()==Operator.OR)
			return union(exp.getParameter(0).last() , exp.getParameter(1).last());
		if(exp.getOperator()==Operator.CONCAT){
			Regex[] ret = exp.getParameter(1).last();
			if(nullE(exp.getParameter(1)))
				ret = union(ret,exp.getParameter(0).last());
			return ret;
//			return union(exp.getParameter(1).last() , product(nullE(exp.getParameter(1)),exp.getParameter(0).last()));
		}
		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE || exp.getOperator()==Operator.OPTIONAL)
			return exp.getParameter(0).last();
		
		return new Regex[0];
	}
	private static boolean ix(Regex[] X, Regex x)
	{
		for(int i=0;i<X.length;i++)
			if(X[i].equalsPosition(x))
				return true;
		return false;
	}
	private static boolean ix(int[] X, Regex x)
	{
		if(X==null) return false;
		for(int i=0;i<X.length;i++)
			if(x.isPosition(X[i]))
				return true;
		return false;
	}
	
	
	private static Regex[] pos(Regex e)
	{
		if(e.getOperator()==Operator.NULL)
			return new Regex[]{e};
		if(e.getOperator()==Operator.CONCAT || e.getOperator()==Operator.OR)
			return union(pos(e.getParameter(0)),pos(e.getParameter(1)));
		if(e.getOperator()==Operator.FECHO || e.getOperator()==Operator.FECHO_ONE || e.getOperator()==Operator.OPTIONAL)
			return pos(e.getParameter(0));
		return new Regex[0];
	}
	
	private static Regex[] union(Regex[] l, Regex m[])
	{
		if(m==null || m.length == 0)
			return l;
		if(l==null || l.length == 0)
			return m;
		if(l==null && m==null)
			return null;
		Regex[] r = new Regex[l.length+m.length];
		System.arraycopy(l,0,r,0,l.length);
		System.arraycopy(m,0,r,l.length,m.length);
		return r;
	}
	
	public static Regex[] product(boolean x, Regex m[])
	{
		if(x)
			return m;
		else
			return new Regex[0];
	}
	
	public static boolean contained(Regex src[], Regex in[])
	{
		for(Regex r: src)
		{
			if(!belong(r,in))
				return false;
		}
		return true;
	}
	
	public static boolean belong(Regex regex, Regex set[])
	{
		if(regex==null) return false;
		for(Regex r:set)
			if(regex.equals(r))
				return true;
		return false;
	}
	
	public static boolean equals(Regex r1[],Regex r2[])
	{
		if(r1==null || r2==null || r1.length!=r2.length)
			return false;
		for(int i=0;i<r1.length;i++)
			if(r1[i]!=r2[i])
				return false;
		return true;
	}
	
	public static void makePositions(Regex regex)
	{
		makePositions(regex,new ListPosition());
	}
	
	private static void makePositions(Regex r, ListPosition p)
	{
		if(r instanceof SingleRegex)
		{
			((SingleRegex)r).setPosition(p.add());
		}else
		if(r instanceof AndRegex || r instanceof OrRegex)
		{
			makePositions(r.getParameter(0),p);
			makePositions(r.getParameter(1),p);
			r.setPos(p.toArray());
		}else
		if(r instanceof StarRegex || r instanceof StarOneRegex || r instanceof OptRegex)
		{
			makePositions(r.getParameter(0),p);
			r.setPos(p.toArray());
		}		
	}
	
}