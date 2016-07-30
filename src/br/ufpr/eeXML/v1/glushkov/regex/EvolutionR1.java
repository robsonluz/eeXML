/*
 * Created on 27/04/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.List;


import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class EvolutionR1 
{
	public void performEvolution(RegexModification rm,List<Regex> lst, boolean many)
	{
		
		Regex x = rm.getSnl();
		Regex y = rm.getSnr();
		
		
		//if(rm.getSnl().belong(rm.getSnrPrevious()) &&
		//   rm.getSnr().belong(rm.getSnlFollow()) )
		//if(RegexUtil.equals(rm.getSnrPrevious(),new Regex[]{rm.getSnl()}) &&
		//RegexUtil.equals(rm.getSnlFollow(),new Regex[]{rm.getSnr()}))
		if(rm.getSnl().getParent().getOperator()==Operator.CONCAT)
		{
			RegexModification clone = rm.clone();
			Regex parent = clone.getSnl().getParent();
			Regex snew = null;
			if(many)
				snew = new StarRegex(new SingleRegex(clone.getSnew().toString()));
			else
				snew = new OptRegex(new SingleRegex(clone.getSnew().toString()));
			
			if(parent.getParameter(0)==clone.getSnl())
				parent.setParameter(0,new AndRegex(parent.getParameter(0),snew));
			if(parent.getParameter(1)==clone.getSnl())
				parent.setParameter(1,new AndRegex(parent.getParameter(1),snew));
			lst.add(clone.getRegex());
		}
		
		if(rm.getSnr().belong(rm.getSnlPrevious()) &&
				   rm.getSnl().belong(rm.getSnrFollow()) )
		{
			//System.out.println("Teste");
		}
		
		/*
		//Verificar//
		if(rm.getSnr().getParent().getOperator()==Operator.CONCAT)
		{
			System.out.println("teste");
			RegexModification clone = rm.clone();
			Regex parent = clone.getSnr().getParent();
			Regex snew = null;
			if(many)
				snew = new StarRegex(new SingleRegex(clone.getSnew()));
			else
				snew = new OptRegex(new SingleRegex(clone.getSnew()));
			
			if(parent.getParameter(0)==clone.getSnr())
				//parent.setParameter(0,new AndRegex(parent.getParameter(0),snew));
				parent.setParameter(0,createAnd(parent.getParameter(0),snew));
			if(parent.getParameter(1)==clone.getSnr())
				//parent.setParameter(1,new AndRegex(parent.getParameter(1),snew));
				parent.setParameter(1,createAnd(parent.getParameter(1),snew));
			lst.add(clone.getRegex());			
		}
		*/
	}
	
	private AndRegex createAnd(Regex v1, Regex v2)
	{
		if(v1 instanceof SingleRegex)
		{
			if(((SingleRegex)v1).getValue()==SingleRegex.END)
			{
				return new AndRegex(v2,v1);
			}
		}
		return new AndRegex(v1,v2);
	}
}