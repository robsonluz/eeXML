/*
 * Created on 28/04/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.List;

import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class EvolutionOrbit 
{
	public void performEvolution(RegexModification rm,List<Regex> lst)
	{
		boolean case1 = isOrbit(rm.getSnl().getParent()) && rm.getSnl()==rm.getSnr();
		boolean case2 = isOrbit(rm.getSnr().getParent()) && RegexUtil.belong(rm.getSnr(),rm.getRegex().follow(rm.getSnl()));
		boolean case3 = isOrbit(rm.getSnl().getParent()) && RegexUtil.belong(rm.getSnr(),rm.getRegex().follow(rm.getSnl()));
		
		if(case1 || case2)//Ev 1
		{
			RegexCloneCreator cloneCreator = new RegexCloneCreator(rm.getRegex(),rm.getSnr());
			RegexCloneable cl = cloneCreator.clone();
			Regex snew = new AndRegex(rm.getSnew().clone(),cl.getSubRegex().clone());;
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
		
		
		//Aqui pode dar errado
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
	
//	public void performEvolution(RegexModification rm,List<Regex> lst, boolean many)
//	{
//		boolean case1 = isOrbit(rm.getSnl().getParent()) && rm.getSnl()==rm.getSnr();
//		boolean case2 = isOrbit(rm.getSnr().getParent()) && RegexUtil.belong(rm.getSnr(),rm.getRegex().follow(rm.getSnl()));
//		boolean case3 = isOrbit(rm.getSnl().getParent()) && RegexUtil.belong(rm.getSnr(),rm.getRegex().follow(rm.getSnl()));
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
//		
//		/*
//		//Case 1//
//		Regex r = rm.getSnl();
//		if(rm.getSnl()==rm.getSnr() && (r.getParent().getOperator() == Operator.FECHO || r.getParent().getOperator() == Operator.FECHO_ONE))
//		{
//			System.out.println("Orbit 1");
//			RegexCloneCreator cloneCreator = new RegexCloneCreator(rm.getRegex(),r);
//			
//			//1//
//			RegexCloneable cl = cloneCreator.clone();
//			Regex snew = null;
//			if(many)
//				snew = new AndRegex(new StarRegex(rm.getSnew()),cl.getSnl().clone());
//			else
//				snew = new AndRegex(new OptRegex(rm.getSnew()),cl.getSnl().clone());
//			cl.getSnl().getParent().setParameter(0,snew);
//			lst.add(cl.getRegex());
//			
//			//2//
//			cl = cloneCreator.clone();
//			snew = null;
//			if(many)
//				snew = new AndRegex(cl.getSnl().clone(),new StarRegex(rm.getSnew()));
//			else
//				snew = new AndRegex(cl.getSnl().clone(),new OptRegex(rm.getSnew()));
//			cl.getSnl().getParent().setParameter(0,snew);
//			lst.add(cl.getRegex());
//		}
//		
//		
//		//Case 2//
//		if(isOrbit(rm.getSnr().getParent()))
//		{
//			if(RegexUtil.belong(rm.getSnr(),rm.getRegex().follow(rm.getSnl())))
//			{
//				System.out.println("Orbit 2");
//				RegexCloneCreator cloneCreator = new RegexCloneCreator(rm.getRegex(),r);
//				
//				//1//
//				RegexCloneable cl = cloneCreator.clone();
//				Regex snew = null;
//				if(many)
//					snew = new AndRegex(new StarRegex(rm.getSnew()),cl.getSnl().clone());
//				else
//					snew = new AndRegex(new OptRegex(rm.getSnew()),cl.getSnl().clone());
//				cl.getSnl().getParent().setParameter(0,snew);
//				lst.add(cl.getRegex());
//				
//				//2//
//				cl = cloneCreator.clone();
//				snew = null;
//				if(many)
//					snew = new OrRegex(cl.getSnl().clone(),new StarRegex(rm.getSnew()));
//				else
//					snew = new OrRegex(cl.getSnl().clone(),new OptRegex(rm.getSnew()));
//				cl.getSnl().getParent().setParameter(0,snew);
//				lst.add(cl.getRegex());
//			}
//		}
//		//////////
//		
//		//Case 3//
//		
//		//////////
//		*/
//	}

	private boolean isOrbit(Regex r)
	{
		return r.getOperator() == Operator.FECHO || r.getOperator() == Operator.FECHO_ONE;
	}
}
