/*
 * Created on 10/05/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class UpdateRegex extends Regex
{
	private List<Regex> lstRegex;
	
	public UpdateRegex()
	{
		lstRegex = new LinkedList();
	}
	
	public void add(Regex regex)
	{
		lstRegex.add(regex);
	}
	public void add(String word)
	{
		lstRegex.add(new SingleRegex(word));
	}

	@Override public Operator getOperator() 
	{
		return Operator.UPDATE;
	}
	
	@Override public Regex clone() 
	{
		UpdateRegex clone = new UpdateRegex();
		clone.lstRegex = (LinkedList<Regex>)((LinkedList)this.lstRegex).clone();
		return clone;
	}
	

	
//	public void generateChoices(Regex exp, List<Regex> lst)
//	{
//		if(lstRegex.size()==1)
//		{
//			Regex r = lstRegex.get(0);
//			lst.add(new RegexCloneCreatorUpdate(exp,this,new OptRegex(r.clone())).clone());
//			lst.add(new RegexCloneCreatorUpdate(exp,this,new StarRegex(r.clone())).clone());
//			return;
//		}
//		
//		
//		//(a|b)*/////////////
//		OrRegex or = new OrRegex();
//		int c = 0;
//		for(Regex r: lstRegex)
//		{
//			if(c<2)
//				or.setParameter(c,r.clone());
//			else
//				or = new OrRegex(or,r.clone());
//			c++;
//		}
//		lst.add(new RegexCloneCreatorUpdate(exp,this,new StarRegex(or)).clone());
//		/////////////////////
//		
//		//(a,b)!/////////////
//		AndRegex and = new AndRegex();
//		c = 0;
//		for(Regex r: lstRegex)
//		{
//			if(c<2)
//				and.setParameter(c,r.clone());
//			else
//				and = new AndRegex(and,r.clone());
//			c++;
//		}
//		lst.add(new RegexCloneCreatorUpdate(exp,this,new StarRegex(and.clone())).clone());
//		lst.add(new RegexCloneCreatorUpdate(exp,this,new OptRegex(and.clone())).clone());
//		/////////////////////
//		
//		//a!,b!//////////////
//		int n = pow(2,lstRegex.size());
//		//System.out.println(n);
//		int cont[] = new int[lstRegex.size()];
//		int max[] = new int[lstRegex.size()];
//		int ant = 0;
//		for(int i=0;i<max.length;i++)
//		{
//			if(ant==0)
//				max[i] = 1;
//			else
//				max[i] = ant*2;  
//			ant = max[i];
//		}
//		
//		boolean state[] = new boolean[lstRegex.size()];
//		for(int i=0;i<n;i++)
//		{
//			and = new AndRegex();
//			c = 0;
//			for(Regex r: lstRegex)
//			{
//				Regex t = generateChoice(r.clone(),i,n,c,cont,state,max);
//				if(c<2)
//					and.setParameter(c,t);
//				else
//					and = new AndRegex(and,t);
//				c++;
//			}
//			//System.out.println(and);
//			lst.add(new RegexCloneCreatorUpdate(exp,this,and).clone());
//		}
//		/////////////////////
//	}
	
	public List<Regex> generateChoices()
	{
		List<Regex> lst = new LinkedList();
		if(lstRegex.size()==1)
		{
			Regex r = lstRegex.get(0);
			lst.add(new OptRegex(r.clone()));
			lst.add(new StarRegex(r.clone()));
			return lst;
		}
		
		//(a|b)*/////////////
		OrRegex or = new OrRegex();
		int c = 0;
		for(Regex r: lstRegex)
		{
			if(c<2)
				or.setParameter(c,r.clone());
			else
				or = new OrRegex(or,r.clone());
			c++;
		}
		lst.add(new StarRegex(or));
		/////////////////////
		
		//(a,b)!/////////////
		AndRegex and = new AndRegex();
		c = 0;
		for(Regex r: lstRegex)
		{
			if(c<2)
				and.setParameter(c,r.clone());
			else
				and = new AndRegex(and,r.clone());
			c++;
		}
		lst.add(new StarRegex(and.clone()));
		lst.add(new OptRegex(and.clone()));
		/////////////////////
		
		//a!,b!//////////////
		int n = pow(2,lstRegex.size());
		int cont[] = new int[lstRegex.size()];
		int max[] = new int[lstRegex.size()];
		for(int i=0;i<max.length;i++)
		{
			if(i==0) max[i] = 1;
			else max[i] = max[i-1]*2;  
		}
		
		boolean state[] = new boolean[lstRegex.size()];
		for(int i=0;i<n;i++)
		{
			and = new AndRegex();
			c = 0;
			for(Regex r: lstRegex)
			{
				Regex t = generateChoice(r.clone(),i,n,c,cont,state,max);
				if(c<2)
					and.setParameter(c,t);
				else
					and = new AndRegex(and,t);
				c++;
			}
			lst.add(and);
		}
		/////////////////////
		return lst;
	}	
	
	private Regex generateChoice(Regex r,int i, int n, int c,int cont[],boolean state[],int max[])
	{
		
		if(cont[c]>=max[c])
		{
			state[c] = !state[c];
			cont[c]=0;
		}
		cont[c]++;
		
		if(state[c])
			return new OptRegex(r);
		else
			return new StarRegex(r);
		//return new StartRegex(r);
	}
	private int pow(int base, int n)
	{
		if(n==0) return 1;
		int t = base;
		for(int i=0;i<n-1;i++)
			base = base*t;
		return base;
	}
	
	
	@Override public void setParameter(int i, Regex value){}
	@Override public void setParameter(Regex oldRegex, Regex newRegex){}
	@Override public Regex getParameter(int i){return null;}

	public List<Regex> getLstRegex()
	{
		return lstRegex;
	}

	@Override public String toPrefixString() {
		StringBuffer sb = new StringBuffer();
		int c=0;
		for(Regex r:getLstRegex())
		{
			sb.append(r.toString());
			if(c<getLstRegex().size()-1)
				sb.append(";");
			c++;
		}
		return "$("+sb.toString()+")";
	}
	
	@Override public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		int c=0;
		for(Regex r:getLstRegex())
		{
			sb.append(r.toString());
			if(c<getLstRegex().size()-1)
				sb.append(";");
			c++;
		}
		return "("+sb.toString()+")$";
	}
}
