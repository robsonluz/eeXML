/*
 * Created on 03/05/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.List;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class EvolutionOr 
{
	public void performEvolution(Regex r, RegexModification rm,List<Regex> lst)
	{
		if(rm.getSnl().belong(r.getParameter(0).last()) && 
				   rm.getSnr().belong(rm.getSnlFollow()))
		{
			//System.out.println("r2.1: "+r);
			RegexCloneable clone = new RegexCloneCreator(rm.getRegex(),r).clone();
			Regex parent = clone.getSubRegex();
			Regex snew = rm.getSnew().clone();
//			if(many)
//				snew = new StarRegex(new SingleRegex(rm.getSnew()));
//			else
//				snew = new OptRegex(new SingleRegex(rm.getSnew()));

			parent.setParameter(0,new AndRegex(parent.getParameter(0),snew));
			lst.add(clone.getRegex());
		}
		
		if(rm.getSnr().belong(r.getParameter(0).last()) &&
		   rm.getSnl().belong(rm.getRegex().previous(r.getParameter(0).first())))
		{
			//System.out.println("r2.2: "+r);
			RegexCloneable clone = new RegexCloneCreator(rm.getRegex(),r).clone();
			Regex parent = clone.getSubRegex();
			Regex snew = rm.getSnew().clone();
//			if(many)
//				snew = new StarRegex(new SingleRegex(rm.getSnew()));
//			else
//				snew = new OptRegex(new SingleRegex(rm.getSnew()));

			parent.setParameter(0,new AndRegex(snew,parent.getParameter(0)));
			lst.add(clone.getRegex());		}
		
		if(rm.getSnl().belong(rm.getRegex().previous(r.getParameter(0).first())) && 
		   rm.getSnr().belong(rm.getRegex().follow(r.getParameter(0).last())) )
		{
			//System.out.println("r2.3: "+r);
			RegexCloneable clone = new RegexCloneCreator(rm.getRegex(),r).clone();
			Regex parent = clone.getSubRegex();
			Regex snew = rm.getSnew().clone();
//			if(many)
//				snew = new StarRegex(new SingleRegex(rm.getSnew()));
//			else
//				snew = new OptRegex(new SingleRegex(rm.getSnew()));

			parent.setParameter(1,new OrRegex(parent.getParameter(1),snew));
			lst.add(clone.getRegex());				
		}
	}
//	public void performEvolution(Regex r, RegexModification rm,List<Regex> lst, boolean many)
//	{
//		if(rm.getSnl().belong(r.getParameter(0).last()) && 
//				   rm.getSnr().belong(rm.getSnlFollow()))
//		{
//			System.out.println("r2.1: "+r);
//			RegexCloneable clone = new RegexCloneCreator(rm.getRegex(),r).clone();
//			Regex parent = clone.getSubRegex();
//			Regex snew = null;
//			if(many)
//				snew = new StarRegex(new SingleRegex(rm.getSnew()));
//			else
//				snew = new OptRegex(new SingleRegex(rm.getSnew()));
//
//			parent.setParameter(0,new AndRegex(parent.getParameter(0),snew));
//			lst.add(clone.getRegex());
//		}
//		
//		if(rm.getSnr().belong(r.getParameter(0).last()) &&
//		   rm.getSnl().belong(rm.getRegex().previous(r.getParameter(0).first())))
//		{
//			System.out.println("r2.2: "+r);
//			RegexCloneable clone = new RegexCloneCreator(rm.getRegex(),r).clone();
//			Regex parent = clone.getSubRegex();
//			Regex snew = null;
//			if(many)
//				snew = new StarRegex(new SingleRegex(rm.getSnew()));
//			else
//				snew = new OptRegex(new SingleRegex(rm.getSnew()));
//
//			parent.setParameter(0,new AndRegex(snew,parent.getParameter(0)));
//			lst.add(clone.getRegex());		}
//		
//		if(rm.getSnl().belong(rm.getRegex().previous(r.getParameter(0).first())) && 
//		   rm.getSnr().belong(rm.getRegex().follow(r.getParameter(0).last())) )
//		{
//			System.out.println("r2.3: "+r);
//			RegexCloneable clone = new RegexCloneCreator(rm.getRegex(),r).clone();
//			Regex parent = clone.getSubRegex();
//			Regex snew = null;
//			if(many)
//				snew = new StarRegex(new SingleRegex(rm.getSnew()));
//			else
//				snew = new OptRegex(new SingleRegex(rm.getSnew()));
//
//			parent.setParameter(1,new OrRegex(parent.getParameter(1),snew));
//			lst.add(clone.getRegex());				
//		}
//	}
}