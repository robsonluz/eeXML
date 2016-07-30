/*
 * Created on 05/05/2005
 *
 */
package br.ufpr.tc.glushkov;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Common 
{
	public static final int[] EMPTY = new int[0];
	
	public static char[] union(char[] l, char m[])
	{
		if(m==null)
			return l;
		if(l==null)
			return m;
		if(l==null && m==null)
			return null;
		char[] r = new char[l.length+m.length];
		System.arraycopy(l,0,r,0,l.length);
		System.arraycopy(m,0,r,l.length,m.length);
		return r;
	}
	
	public static int[] union(int[] l, int m[])
	{
		if(m==null)
			return l;
		if(l==null)
			return m;
		if(l==null && m==null)
			return null;
		int[] r = new int[l.length+m.length];
		System.arraycopy(l,0,r,0,l.length);
		System.arraycopy(m,0,r,l.length,m.length);
		return r;
	}
	
	public static Exp[] union(Exp[] l, Exp m[])
	{
		if(m==null)
			return l;
		if(l==null)
			return m;
		if(l==null && m==null)
			return null;
		Exp[] r = new Exp[l.length+m.length];
		System.arraycopy(l,0,r,0,l.length);
		System.arraycopy(m,0,r,l.length,m.length);
		return r;
	}
	
	public static int[] first(Exp er)
	{
		if(er==null || er.lengthExp()==0)
			return EMPTY;
		if(er.lengthExp()==1)
			System.out.println(er.getExp());
		else{
			Exp subs[] = er.getSubExps();
			for(Exp sub: subs){
				//System.out.println(sub);
				//if(sub!=null)
				Common.union(new int[0],first(sub));
			}
		}
		//if(sub.)
		//if(er.length())	
		return EMPTY;
	}
	

}