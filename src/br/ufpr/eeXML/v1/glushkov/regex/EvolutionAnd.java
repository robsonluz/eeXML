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
public class EvolutionAnd 
{
	public void performEvolution(Regex r, RegexModification rm,List<Regex> lst)
	{
		if(rm.getSnl().belong(r.getParameter(0).last()) &&
				   rm.getSnr().belong(r.getParameter(1).first()) )
		{
			//System.out.println("r1.1: "+r);
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
				   rm.getSnl().belong(r.getParameter(1).first()) )
		{
			//System.out.println("r1.2: "+r);
			RegexCloneCreator cloneCreator = new RegexCloneCreator(rm.getRegex(),r);
			
			//a:)//
			RegexCloneable clone = cloneCreator.clone();
			Regex p = clone.getSubRegex();
			Regex snew = rm.getSnew().clone();
//			if(many)
//				snew = new StarRegex(new SingleRegex(rm.getSnew()));
//			else
//				snew = new OptRegex(new SingleRegex(rm.getSnew()));
			
			//FIXME este if aqui não foi testado, coloquei porque estava dando nulo no parente para o caso
			//E=meta*,column*,type?,generator? w'=meta,meta,type,generator,a
			if(p.getParent()!=null){
				p.getParent().setParameter(p,new AndRegex(p.clone(),snew));
				lst.add(clone.getRegex());
				
				//b:)//
				clone = cloneCreator.clone();
				p = clone.getSubRegex();
				snew = rm.getSnew().clone();
	//			if(many)
	//				snew = new StarRegex(new SingleRegex(rm.getSnew()));
	//			else
	//				snew = new OptRegex(new SingleRegex(rm.getSnew()));
				p.getParent().setParameter(p,new AndRegex(snew,p.clone()));
				lst.add(clone.getRegex());
			}
		}		
	}
	
	
	
	
//	public void performEvolution(Regex r, RegexModification rm,List<Regex> lst, boolean many)
//	{
//		if(rm.getSnl().belong(r.getParameter(0).last()) &&
//				   rm.getSnr().belong(r.getParameter(1).first()) )
//		{
//			System.out.println("r1.1: "+r);
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
//				   rm.getSnl().belong(r.getParameter(1).first()) )
//		{
//			System.out.println("r1.2: "+r);
//			RegexCloneCreator cloneCreator = new RegexCloneCreator(rm.getRegex(),r);
//			
//			//a:)//
//			RegexCloneable clone = cloneCreator.clone();
//			Regex p = clone.getSubRegex();
//			Regex snew = null;
//			if(many)
//				snew = new StarRegex(new SingleRegex(rm.getSnew()));
//			else
//				snew = new OptRegex(new SingleRegex(rm.getSnew()));
//			p.getParent().setParameter(p,new AndRegex(p.clone(),snew));
//			lst.add(clone.getRegex());
//			
//			//b:)//
//			clone = cloneCreator.clone();
//			p = clone.getSubRegex();
//			snew = null;
//			if(many)
//				snew = new StarRegex(new SingleRegex(rm.getSnew()));
//			else
//				snew = new OptRegex(new SingleRegex(rm.getSnew()));
//			p.getParent().setParameter(p,new AndRegex(snew,p.clone()));
//			lst.add(clone.getRegex());
//		}		
//	}
}