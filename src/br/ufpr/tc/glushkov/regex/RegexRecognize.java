/*
 * Created on 26/01/2006
 *
 */
package br.ufpr.tc.glushkov.regex;
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
		//Regex r = reg;
		for(String w:ws){
			if(rr(w,reg)==false)
				return false;
		}
		return true;
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
