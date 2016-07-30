/*
 * Created on 11/05/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.LinkedList;
import java.util.List;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class RegexUpdateChoices
{
	private List<Regex> choices;
	private RegexList lstRegex;
	private Regex sorceRegex;
	
	public RegexUpdateChoices(RegexList lstRegex, Regex sorceRegex)
	{
		this.sorceRegex = sorceRegex;
		this.lstRegex = lstRegex;
		if(lstRegex!=null)
			this.lstRegex.removeStartEndExpressions();
	}
	
	private void generateChoices()
	{
		if(lstRegex!=null)
		{
			choices = new LinkedList();
			for(Regex r: lstRegex)
			{
				List<UpdateRegex> lstUpdate = new LinkedList();
				findUpdateRegex(r,lstUpdate);
				List<Regex> lstR[] = new LinkedList[lstUpdate.size()];
				int i=0;
				int x[] = new int[lstUpdate.size()];
				int t[] = new int[lstUpdate.size()];
				
				for(UpdateRegex up:lstUpdate){
					lstR[i]=up.generateChoices();
					x[i] = lstR[i].size();
					t[i++]=1;
				}
				
				RegexCloneCreatorUpdate cloneCreator = new RegexCloneCreatorUpdate(r,lstUpdate.toArray(new UpdateRegex[lstUpdate.size()]));
				
				int n = 1;
				for(i=0;i<x.length;i++)
					n*=x[i];
				int c = 0;
				while(c++<n)
				{
					Regex rnew[] = new Regex[t.length];
					for(i=0;i<t.length;i++)
						rnew[i]=lstR[i].get(t[i]-1);
					choices.add(cloneCreator.clone(rnew));
					
					//Update Index//
//					for(i=0;i<t.length;i++)
//					{
//						t[i]++;
//						if(t[i]>x[i])
//							t[i]=1;
//						else
//							break;
//					}
					for(i=0;i<t.length;i++)
					{
						if(++t[i]>x[i])	t[i]=1;
						else break;
					}
				}

				//generateChoices(r,r,choices);
			}
		}		
	}
	
	public void findUpdateRegex(Regex exp, List<UpdateRegex> lst)
	{
		if(exp instanceof SingleRegex)
			return;
		if(exp instanceof UpdateRegex)
		{
			lst.add((UpdateRegex)exp);
			return;
		}
		findUpdateRegex(exp.getParameter(0),lst);
		if(exp instanceof AndRegex || exp instanceof OrRegex)
			findUpdateRegex(exp.getParameter(1),lst);
	}
	
//	private void generateChoices(Regex exp,Regex s, List<Regex> lst)
//	{
//		if(s instanceof SingleRegex)
//			return;
//		if(s instanceof UpdateRegex)
//		{
//			((UpdateRegex) s).generateChoices(exp,lst);
//			return;
//		}
//		generateChoices(exp,s.getParameter(0),lst);
//		if(s instanceof AndRegex || s instanceof OrRegex)
//			generateChoices(exp,s.getParameter(1),lst);
//	}
	
	public List<Regex> getGenericChoices()
	{
		return this.lstRegex;
	}
	
	public List<Regex> getAllChoices()
	{
		if(choices==null) generateChoices();
		return choices;
	}

	public Regex getSorceRegex() {
		return sorceRegex;
	}
}