/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.tc.glushkov.regex;
/*
 * @autor Robson João Padilha da Luz
 *
 */
public abstract class RegexImpl<REGEX extends Regex> implements Regex
{
	private String exp;
	
	protected void setExp(Regex value)
	{
		this.exp = new ReGexParser().parserToNormal(value);
	}
	
	public int compareTo(Regex o) 
	{
		if(o.equals(this))
			return 0;
		return -1;
	}
	
	public final String toString()
	{
		return exp;
	}
	
	private String tmp;
	public void setTmp(String tmp)
	{
		this.tmp = tmp;
	}
	
	public final boolean equals(Object obj)
	{
		if(obj!=null){
			if(obj instanceof String)
				return exp.equals(obj);
			if(obj instanceof Regex){
				
				Regex r = (Regex) obj;
				boolean is = r.toString().equals(this.toString());
				//System.out.println("equals["+r+"]["+this+"]="+is+">"+tmp);
				return is;
//				try{
//				return equals((REGEX)obj);
//				}catch(Exception e){}
			}
		}
		return false;
	}
	
	//protected abstract boolean equals(REGEX reg);
}
