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
public class EvolutionOpt 
{
	
	public void performEvolution(Regex r, RegexModification rm,List<Regex> lst)
	{
		//System.out.println("---"+r.getParameter(0));
		if(rm.getSnl().belong(rm.getRegex().previous(r.getParameter(0).first())) &&
			(rm.getSnr().belong(rm.getRegex().follow(r.getParameter(0).last())) 
			&& rm.getSnr().belong(rm.getRegex().follow(rm.getRegex().previous(r.getParameter(0).first())))
			))
		{
			//System.out.println("r3: "+r);
			RegexCloneCreator cloneCreator = new RegexCloneCreator(rm.getRegex(),r);
			RegexCloneable clone = cloneCreator.clone();

			//prop 1//
			Regex p = clone.getSubRegex();
			Regex snew = new AndRegex(p.clone(),rm.getSnew().clone());
//			if(many)
//				snew = new AndRegex(p.clone(),new StarRegex(new SingleRegex(rm.getSnew())));
//			else
//				snew = new AndRegex(p.clone(),new OptRegex(new SingleRegex(rm.getSnew())));
			if(p.getParent()!=null)
			{
				p.getParent().setParameter(p,snew);
				lst.add(clone.getRegex());
			}else{
				lst.add(snew);
			}
			

			//prop 2//
			clone = cloneCreator.clone();
			p = clone.getSubRegex();
			snew = new AndRegex(rm.getSnew().clone(),p.clone());;
//			if(many)
//				snew = new AndRegex(new StarRegex(new SingleRegex(rm.getSnew())),p.clone());
//			else
//				snew = new AndRegex(new OptRegex(new SingleRegex(rm.getSnew())),p.clone());
			if(p.getParent()!=null)
			{
				p.getParent().setParameter(p,snew);
				lst.add(clone.getRegex());
			}else{
				lst.add(snew);
			}
			
			//prop 3//
			clone = cloneCreator.clone();
			p = clone.getSubRegex();
			snew = new OrRegex(rm.getSnew().clone(),p.clone());
//			if(many)
//				snew = new OrRegex(new StarRegex(new SingleRegex(rm.getSnew())),p.clone());
//			else
//				snew = new OrRegex(new OptRegex(new SingleRegex(rm.getSnew())),p.clone());
			if(p.getParent()!=null)
			{
				p.getParent().setParameter(p,snew);
				lst.add(clone.getRegex());
			}else{
				lst.add(snew);
			}			
		}

	}
	
//	public void performEvolution(Regex r, RegexModification rm,List<Regex> lst, boolean many)
//	{
//		System.out.println("---"+r.getParameter(0));
//		if(rm.getSnl().belong(rm.getRegex().previous(r.getParameter(0).first())) &&
//			(rm.getSnr().belong(rm.getRegex().follow(r.getParameter(0).last())) && 
//			 rm.getSnr().belong(rm.getRegex().follow(rm.getRegex().previous(r.getParameter(0).first())))))
//		{
//			System.out.println("r3: "+r);
//			RegexCloneCreator cloneCreator = new RegexCloneCreator(rm.getRegex(),r);
//			RegexCloneable clone = cloneCreator.clone();
//
//			//prop 1//
//			Regex p = clone.getSubRegex();
//			Regex snew = null;
//			if(many)
//				snew = new AndRegex(p.clone(),new StarRegex(new SingleRegex(rm.getSnew())));
//			else
//				snew = new AndRegex(p.clone(),new OptRegex(new SingleRegex(rm.getSnew())));
//			if(p.getParent()!=null)
//			{
//				p.getParent().setParameter(p,snew);
//				lst.add(clone.getRegex());
//			}else{
//				lst.add(snew);
//			}
//			
//
//			//prop 2//
//			clone = cloneCreator.clone();
//			p = clone.getSubRegex();
//			snew = null;
//			if(many)
//				snew = new AndRegex(new StarRegex(new SingleRegex(rm.getSnew())),p.clone());
//			else
//				snew = new AndRegex(new OptRegex(new SingleRegex(rm.getSnew())),p.clone());
//			if(p.getParent()!=null)
//			{
//				p.getParent().setParameter(p,snew);
//				lst.add(clone.getRegex());
//			}else{
//				lst.add(snew);
//			}
//			
//			//prop 3//
//			clone = cloneCreator.clone();
//			p = clone.getSubRegex();
//			snew = null;
//			if(many)
//				snew = new OrRegex(new StarRegex(new SingleRegex(rm.getSnew())),p.clone());
//			else
//				snew = new OrRegex(new OptRegex(new SingleRegex(rm.getSnew())),p.clone());
//			if(p.getParent()!=null)
//			{
//				p.getParent().setParameter(p,snew);
//				lst.add(clone.getRegex());
//			}else{
//				lst.add(snew);
//			}			
//		}
//
//	}
}