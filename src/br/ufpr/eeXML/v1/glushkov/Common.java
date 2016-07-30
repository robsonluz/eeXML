/*
 * Created on 21/05/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Common 
{

	public static String[] union(String[] l, String m[])
	{
		if(m==null)
			return l;
		if(l==null)
			return m;
		if(l==null && m==null)
			return null;
		String[] r = new String[l.length+m.length];
		System.arraycopy(l,0,r,0,l.length);
		System.arraycopy(m,0,r,l.length,m.length);
		return r;
	}
	
	public static String[] product(boolean x, String m[])
	{
		if(x)
			return m;
		else
			return new String[0];
	}
	
	public static boolean contem(String elemento,String[] conjunto)
	{
		for(String t: conjunto)
		{
			if(elemento.equals(t))
				return true;
		}
		return false;
	}
	
	public static void printArray(Object obj[])
	{
		System.out.print(" {");
		for(int i=0;i<obj.length;i++){
			System.out.print(obj[i]);
			if(i<obj.length-1)
				System.out.print(",");
		}
		System.out.print("}");
	}
}