/*
 * Created on 20/05/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.eeXML.v1.glushkov.regex.Exp;


/**
 * @author Robson João Padilha da Luz
 *
 */
public class PrefixExp implements Exp
{
	private String exp;
	private Operator operator;
	private List<String> parameters;
	
	public PrefixExp(String exp)
	{
		this.exp = exp;
		
		init();
	}
	private void init()
	{
		parameters = new LinkedList<String>();
		exp = exp.trim();
		int p = findOperator();
		if(p!=-1)
		{
			findParameters(p);
		}
	}
	private void findParameters(int p)
	{
		int t = -1;
		for(int i=p+1;i<exp.length();i++)
		{
			String c = get(i);
			if(c.equals("("))
			{
				t = i+1;
				break;
			}
		}
		findNextParameter(t);
	}
	private void findNextParameter(int pos)
	{
		String pr = "";
		int queue = 0;
		int i = -1;
		for(i=pos;i<exp.length()-1;i++)
		{
			String c = get(i);
			if((c.equals(",") || c.equals(")")) && queue <= 0){
				break;
			}
			if(c.equals("("))
				queue++;
			if(c.equals(")"))
				queue--;			
			pr+=c;
		}
		pr = pr.trim();
		if(!(pr.equals(",") || pr.equals(")") || pr.length()==0))
			parameters.add(pr);
		if(i<exp.length())
			findNextParameter(i+1);
	}
	private int findOperator()
	{
		operator = Operator.NULL;
		for(int i=0;i<exp.length();i++)
		{
			if(!get(i).equals(" "))
			{
				String s = get(i);
				if(s.equals("+"))
					operator = Operator.FECHO_ONE;
				if(s.equals("."))
					operator = Operator.CONCAT;
				if(s.equals("*"))
					operator = Operator.FECHO;
				if(s.equals("|"))
					operator = Operator.OR;
				if(operator != Operator.NULL)
					return i;
			}
		}
		return -1;
	}
	private String get(int i)
	{
		return exp.substring(i,i+1);
	}
	public Exp getParameter(int i)
	{
		try{
			return new PrefixExp(parameters.get(i));
		}catch(Exception e){
			return null;
		}
	}
	public Operator getOperator()
	{
		return this.operator;
	}
	public String getExp()
	{
		return this.exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
		init();
	}
	
	public String toString()
	{
		return this.exp;
	}
}