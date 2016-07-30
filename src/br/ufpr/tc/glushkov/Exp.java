/*
 * Created on 05/05/2005
 *
 */
package br.ufpr.tc.glushkov;

import java.util.List;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Exp 
{
	private String exp;
	private char[] cexp;
	private List<Letter> letters;
	private char operator;
	

	public Exp(String exp)
	{
		this.exp = exp;
		this.cexp = exp.toCharArray();
	}
	
	private Exp(String exp,char operator)
	{
		this(exp);
		this.operator = operator;
	}
	
	public Exp[] getSubExps()
	{
		return getSubExps(0);
	}
	
	private Exp[] getSubExps(int ini)
	{
		if(length()==ini+0)
			return null;
		
		if(length()==ini+1)
			return new Exp[]{new Exp(exp)};
		//if(exp.startsWith("("));
		
		int next = ini+0;
		
		char letter = cexp[next++];
		char op = cexp[next];
		String subexp = ""+letter;
		if(op =='*' || op== '+')
		{
			subexp+= ""+op;
			next++;
		}
		if(length()>next)
		{
			char t = cexp[next++];
			//System.out.println("Sub: "+subexp+ " - "+next);
			Exp e = new Exp(subexp,t);
			Exp nextExp = new Exp(exp.substring(next));
			return Common.union(new Exp[]{e},nextExp.getSubExps(0));
		}else{
			/*Exp e = new Exp(subexp);
			return Common.union(new Exp[]{e},nextExp.getSubExps(0));
			*/
			return null;
		}
		/*String letter = exp.substring(0,1);
		String operator = exp.substring(1,2);
		if(operator.equals("*") || operator.equals("+"))
			return new Exp[]{new Exp(letter+operator)};
		*/
	}
	
	private int length()
	{
		if(exp==null)
		{
			return 0;
		}
		return exp.length();
	}
	
	public int lengthExp()
	{
		if(exp==null)
		{
			return 0;
		}
		int length = length();
		
		if(length==2)
			if(cexp[1]=='+'||cexp[1]=='*')
				return 1;
			
		return exp.length();
	}
	
	public String toString()
	{
		return exp+" "+operator;
	}
	//public SubExp get()
	public String getExp() {
		return exp;
	}
}