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
public class EvolutionFecho 
{
	public void performEvolution(Regex r, RegexModification rm,List<Regex> lst)
	{
		boolean case1 = rm.getSnl().getParent()==r && rm.getSnl()==rm.getSnr();
		boolean case2 = rm.getSnr().getParent()==r && RegexUtil.belong(rm.getSnr(),rm.getRegex().follow(rm.getSnl()));
		boolean case3 = rm.getSnl().getParent()==r && RegexUtil.belong(rm.getSnr(),rm.getRegex().follow(rm.getSnl()));
		
		if(case1 || case2)//Ev 1
		{
			RegexCloneCreator cloneCreator = new RegexCloneCreator(rm.getRegex(),rm.getSnr());
			RegexCloneable cl = cloneCreator.clone();
			Regex snew = new AndRegex(rm.getSnew().clone(),cl.getSubRegex().clone());
//			if(many)
//				snew = new AndRegex(new StarRegex(rm.getSnew()),cl.getSubRegex().clone());
//			else
//				snew = new AndRegex(new OptRegex(rm.getSnew()),cl.getSubRegex().clone());
			cl.getSubRegex().getParent().setParameter(0,snew);
			lst.add(cl.getRegex());
		}
		
		if(case1 || case3)//Ev 2
		{
			RegexCloneCreator cloneCreator = new RegexCloneCreator(rm.getRegex(),rm.getSnl());
			RegexCloneable cl = cloneCreator.clone();
			Regex snew = new AndRegex(cl.getSubRegex().clone(),rm.getSnew().clone());
//			if(many)
//				snew = new AndRegex(cl.getSubRegex().clone(),new StarRegex(rm.getSnew()));
//			else
//				snew = new AndRegex(cl.getSubRegex().clone(),new OptRegex(rm.getSnew()));
			cl.getSubRegex().getParent().setParameter(0,snew);
			lst.add(cl.getRegex());
		}
		
		//Aqui pode dar erro
		if(case2 || case3)//Ev 3
		{
			RegexCloneCreator cloneCreator = null;
			if(case2)
				cloneCreator = new RegexCloneCreator(rm.getRegex(),rm.getSnr());
			else
				cloneCreator = new RegexCloneCreator(rm.getRegex(),rm.getSnl());

			RegexCloneable cl = cloneCreator.clone();
			Regex snew = null;
			/*
			if(many)
				snew = new OrRegex(new StarRegex(rm.getSnew()),cl.getSnl().clone());
			else
				snew = new OrRegex(rm.getSnew(),cl.getSnl().clone());
			*/
			snew = new OrRegex(rm.getSnew().clone(),cl.getSubRegex().clone());
			cl.getSubRegex().getParent().setParameter(0,snew);
			lst.add(cl.getRegex());
		}
		
	}
	
//	public void performEvolution(Regex r, RegexModification rm,List<Regex> lst, boolean many)
//	{
//		boolean case1 = rm.getSnl().getParent()==r && rm.getSnl()==rm.getSnr();
//		boolean case2 = rm.getSnr().getParent()==r && RegexUtil.belong(rm.getSnr(),rm.getRegex().follow(rm.getSnl()));
//		boolean case3 = rm.getSnl().getParent()==r && RegexUtil.belong(rm.getSnr(),rm.getRegex().follow(rm.getSnl()));
//		
//		if(case1 || case2)//Ev 1
//		{
//			RegexCloneCreator cloneCreator = new RegexCloneCreator(rm.getRegex(),rm.getSnr());
//			RegexCloneable cl = cloneCreator.clone();
//			Regex snew = null;
//			if(many)
//				snew = new AndRegex(new StarRegex(rm.getSnew()),cl.getSubRegex().clone());
//			else
//				snew = new AndRegex(new OptRegex(rm.getSnew()),cl.getSubRegex().clone());
//			cl.getSubRegex().getParent().setParameter(0,snew);
//			lst.add(cl.getRegex());
//		}
//		
//		if(case1 || case3)//Ev 2
//		{
//			RegexCloneCreator cloneCreator = new RegexCloneCreator(rm.getRegex(),rm.getSnl());
//			RegexCloneable cl = cloneCreator.clone();
//			Regex snew = null;
//			if(many)
//				snew = new AndRegex(cl.getSubRegex().clone(),new StarRegex(rm.getSnew()));
//			else
//				snew = new AndRegex(cl.getSubRegex().clone(),new OptRegex(rm.getSnew()));
//			cl.getSubRegex().getParent().setParameter(0,snew);
//			lst.add(cl.getRegex());
//		}
//		
//		if(case2 || case3)//Ev 3
//		{
//			RegexCloneCreator cloneCreator = null;
//			if(case2)
//				cloneCreator = new RegexCloneCreator(rm.getRegex(),rm.getSnr());
//			else
//				cloneCreator = new RegexCloneCreator(rm.getRegex(),rm.getSnl());
//
//			RegexCloneable cl = cloneCreator.clone();
//			Regex snew = null;
//			/*
//			if(many)
//				snew = new OrRegex(new StarRegex(rm.getSnew()),cl.getSnl().clone());
//			else
//				snew = new OrRegex(rm.getSnew(),cl.getSnl().clone());
//			*/
//			snew = new OrRegex(rm.getSnew(),cl.getSubRegex().clone());
//			cl.getSubRegex().getParent().setParameter(0,snew);
//			lst.add(cl.getRegex());
//		}
//		
//	}
}