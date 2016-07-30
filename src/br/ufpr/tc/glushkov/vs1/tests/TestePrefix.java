/*
 * Created on 25/05/2005
 *
 */
package br.ufpr.tc.glushkov.vs1.tests;

import java.util.StringTokenizer;

import br.ufpr.tc.glushkov.vs1.NormalExpToPrefixExp;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class TestePrefix 
{
	public static void main(String args[])
	{
		//String exp = "(a|b).c*.a";
		String exp = "i*.a*.c*|a+.d+.f*.g+.d+|h*.d";
		//String exp = "a.b.c.d.e";
		NormalExpToPrefixExp et = new NormalExpToPrefixExp();
		System.out.println(et.transformToPrefix(exp));
		
		/*StringTokenizer st = new StringTokenizer(exp,".");
		while(st.hasMoreElements())
		{
			System.out.println(st.nextToken());
		}*/
	}
}