import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.ufpr.eeXML.v1.glushkov.regex.AndRegex;
import br.ufpr.eeXML.v1.glushkov.regex.ExpToRegex;
import br.ufpr.eeXML.v1.glushkov.regex.ListPosition;
import br.ufpr.eeXML.v1.glushkov.regex.Regex;
import br.ufpr.eeXML.v1.glushkov.regex.SingleRegex;
import br.ufpr.eeXML.v1.treeautomata.ExpDTD;

/*
 * Created on 07/06/2005
 *
 */

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Teste 
{
	
	public static void testeCombinacao()
	{
		int x[] = {3,2,3};
		int t[] = {1,1,1};
		int n = 1;
		for(int i=0;i<x.length;i++)
			n*=x[i];
		int c = 0;
		while(c++<n)
		{
			print(t);
			for(int i=0;i<t.length;i++)
			{
				t[i]++;
				if(t[i]>x[i])
					t[i]=1;
				else
					break;
			}
		}
	}
	
	private static void print(int t[])
	{
		for(int i=t.length-1;i>=0;i--)
			System.out.print(t[i]+" ");
		System.out.println();
	}
	
	
	private static void delete()
	{
		Regex exp = ExpToRegex.convert(new ExpDTD("a,(b|d)*,c?,j"));
		Regex snl = new SingleRegex("a");
		Regex snr = new SingleRegex("j");
		Regex del = find(find(exp,snl),snr);
		System.out.println(del);
		Regex follow[] = exp.follow(del.last());
		for(Regex r: follow)
		{
			System.out.println(r);
		}
	}
	private static Regex find(Regex e, Regex s)
	{
		if(e==null)return null;
		if(e instanceof AndRegex)
		{
			Regex v1 = e.getParameter(0);	
			Regex v2 = e.getParameter(1);
			if(s.belong(v1.last())) return v2;
			if(s.belong(v2.first())) return v1;
		}
		return null;
	}
	
	private static void testeListPosition()
	{
		ListPosition lstP = new ListPosition();
		lstP.add(2);
		lstP.add(4);
		lstP.add(5);
		lstP.add(6);
		int t[] = lstP.toArray();
		for(int i=0;i<t.length;i++)
		{
			System.out.println(t[i]);
		}
	}
	
	public static void main(String args[])
	{
		testeListPosition();
	}
}
