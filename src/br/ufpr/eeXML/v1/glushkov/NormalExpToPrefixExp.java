/*
 * Created on 25/05/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class NormalExpToPrefixExp 
{
	private List<Param> lstParam;
	
	public char[] transformToPrefix(char[] exp)
	{
		String s = new String(exp);
		if(s.indexOf(".")==-1 && s.indexOf("|")==-1)
		{
			if(exp.length==1)
				return exp;
			if(exp.length==2)
			{
				return new char[]{exp[1],'(',exp[0],')'};
			}
			
			return exp;
		}
		
		List<char[]> lstParam = new LinkedList();
		List<String> lstOperador = new LinkedList();
		int queue = 0;
		int t = 0;
		int i = 0;
		for(i=0;i<exp.length;i++)
		{
			if(exp[i]=='(')
				queue++;
			if(exp[i]==')')
				queue--;
			
			if((exp[i]=='.'||exp[i]=='|') && queue==0)
			{
				lstParam.add(copyArray(exp,t,i-1));
				lstOperador.add(""+exp[i]);
				t = i+1;
			}
			//t++;
		}
		
		if(t<i)
			lstParam.add(copyArray(exp,t,i-1));
		
		
		
		StringBuffer sb = new StringBuffer();
		for(String o: lstOperador)
		{
			//System.out.println(o);
			sb.append(o+"(");
		}
		
		boolean temp = false;
		i = 0;
		for(char[] p: lstParam)
		{
			sb.append(transformToPrefix(p));
			if(temp)
				sb.append(")");
			if(i<lstParam.size()-1){
				sb.append(",");
				temp = true;
			}else{
				temp = false;
			}
			i++;
		}
		
		return sb.toString().toCharArray();
	}
	
	
	private class Param
	{
	}
	
	private char[] copyArray(char[] src,int ini, int fim)
	{
		char[] ret = new char[fim-ini+1];
		System.arraycopy(src,ini,ret,0,ret.length);
		return ret;
	}
	
	public String transformToPrefix(String exp)
	{
		return new String(transformToPrefix(exp.toCharArray()));
	}
}