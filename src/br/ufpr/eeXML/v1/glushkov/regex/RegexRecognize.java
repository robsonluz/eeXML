/*
 * Created on 26/01/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class RegexRecognize 
{
	private Regex reg;
	
	public RegexRecognize(Regex regex)
	{
		this.reg = regex;
	}
	
	public boolean recognize(String ... ws)
	{
		Regex r = reg;
		for(String w:ws){
			Regex t = findRegexMatch(r,w);
			if(t==null){
				System.out.println("aqui: "+r.getParent());
				return false;
			}
			r = t;
			/*if(rr(w,reg)==false)
				return false;
			*/
		}
		return true;
	}
	
	public Regex evolution(String ... ws)
	{
		Regex r = reg;
		for(String w:ws){
			Regex t = findRegexMatch(r,w);
			if(t==null){
				System.out.println("aqui: "+r);
				System.out.println("aqui: "+r.getParent());
				evolution(w,r);
				//return false;
				break;
			}
			r = t;
		}
		return reg;
	}
	
	private void evolution(String snew,Regex r)
	{
		if(r.getParent()!=null)
		{
			Regex parent = r.getParent();
			if(parent.getOperator()==Operator.CONCAT)
			{
				AndRegex and = (AndRegex) parent;
				Regex n = new AndRegex(new OptRegex(snew),r);
				if(and.getValue1()==r)
					and.setValue1(n);
				if(and.getValue2()==r)
					and.setValue2(n);
			}
		}
	}
	

	private Regex findRegexMatch(Regex r, String w)
	{
		if(r instanceof SingleRegex)
		{
			if(match(r,w))
				return r;
		}
		if(r instanceof AndRegex)
		{
			AndRegex and = (AndRegex) r;
			if(match(and.getValue1(),w))
				return and.getValue2();
		}
		if(r instanceof OrRegex)
		{
			OrRegex or = (OrRegex) r;
			if(match(or.getValue1(),w))
				return or.getValue2();
			
		}
		return null;
	}
	
	public boolean match(Regex r, String w)
	{
		if(r instanceof SingleRegex)
		{
			if(((SingleRegex)r).getValue().equals(w))
				return true;
		}
		if(r instanceof StarRegex)
		{
			return match(((StarRegex)r).getValue(),w); 
		}
		if(r instanceof AndRegex)
		{
			return match(((AndRegex)r).getValue1(),w); 
		}
		if(r instanceof OrRegex)
		{
			return match(((OrRegex)r).getValue1(),w) || match(((OrRegex)r).getValue2(),w); 
		}		
		return false;

	}
	
	private boolean rr(String w,Regex r)
	{
		this.reg = r;
		if(r instanceof SingleRegex)
		{
			if(((SingleRegex)r).getValue().equals(w))
				return true;
		}
		if(r instanceof StarRegex)
		{
			return rr(w,((StarRegex)r).getValue()); 
		}
		if(r instanceof AndRegex)
		{
			return rr(w,((AndRegex)r).getValue1()) || rr(w,((AndRegex)r).getValue2()); 
		}		
		return false;
	}
}
