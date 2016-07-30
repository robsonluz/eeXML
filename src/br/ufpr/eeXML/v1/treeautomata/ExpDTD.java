/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.eeXML.v1.treeautomata;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.eeXML.v1.glushkov.Operator;
import br.ufpr.eeXML.v1.glushkov.regex.Exp;


/*
 * @autor Robson João Padilha da Luz
 *
 */
public class ExpDTD implements Exp
{
	private String exp;
	private Operator operator;
	private List<String> parameters;
	
	public ExpDTD(String exp)
	{
		this.exp = exp;
		init();
	}
	
	private void init()
	{
		this.operator = Operator.NULL;
		parameters = new LinkedList<String>();
		exp = tiraParenteses(exp);
		findNextParameter(0);
	}
	private String tiraParenteses(String exp)
	{
		String ret = exp.trim();
		if(ret.startsWith("(") && ret.endsWith(")"))
			return tiraParenteses(ret.substring(1,ret.length()-1));
		return ret;
	}
	
//	private void findParameters(int p)
//	{
//		int t = -1;
//		for(int i=p+1;i<exp.length();i++)
//		{
//			String c = get(i);
//			if(c.equals("("))
//			{
//				t = i+1;
//				break;
//			}
//		}
//		findNextParameter(t);
//	}
	private void findNextParameter(int pos)
	{
		String pr = "";
		int queue = 0;
		int i = -1;
		exp = exp.trim();
		for(i=pos;i<exp.length();i++)
		{
			
			String c = get(i);
			
			
			if((c.equals(",") || c.equals("|") || c.equals("*") || c.equals("+") || c.equals("?")) && queue <= 0){
				findOperator(c);
				if((operator == Operator.FECHO || operator == Operator.FECHO_ONE || operator == Operator.OPTIONAL))
				{
					if(i==exp.length()-1)
						break;
				}else{
					break;
				}
			}
			if(c.equals("("))
				queue++;
			if(c.equals(")"))
				queue--;
			
			pr+=c;
		}
		pr = pr.trim();
		
		//System.out.println("pp:"+pr);
		//if(!(pr.equals(",") || pr.equals(")") || pr.length()==0))
		parameters.add(pr);
		if((operator==Operator.CONCAT) || (operator==Operator.OR))
		{
			//System.out.println(exp.substring(i+1));
			parameters.add(exp.substring(i+1));
		}
//		if(i<exp.length() && (operator!=Operator.FECHO) && (operator!=Operator.FECHO_ONE)  && (operator!=Operator.NULL))
//			findNextParameter(i+1);
	}
	private void findOperator(String s)
	{
		operator = Operator.NULL;
		if(s.equals("+"))
			operator = Operator.FECHO_ONE;
		if(s.equals(","))
			operator = Operator.CONCAT;
		if(s.equals("*"))
			operator = Operator.FECHO;
		if(s.equals("|"))
			operator = Operator.OR;
		if(s.equals("?"))
			operator = Operator.OPTIONAL;
	}
	
	private String get(int i)
	{
		return exp.substring(i,i+1);
	}
	
	public String getExp() 
	{
		return exp;
	}

	public Operator getOperator() 
	{
		return operator;
	}

	public Exp getParameter(int i) 
	{
		try{
			return new ExpDTD(parameters.get(i));
		}catch(Exception e){
			return null;
		}
	}

	public void setExp(String exp) 
	{
		this.exp = exp;
		init();
	}
	
	public String toString()
	{
		return this.exp;
	}
}