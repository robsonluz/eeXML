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
public class EvolutionR2 
{
	public void performEvolution(RegexModification rm,List<Regex> lst,boolean many)
	{
		//1 e 2//
		if(rm.getSnl().getParent().getOperator()==Operator.OR)
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

		//3//
		Regex suc[] = rm.getRegex().follow(rm.getSnl());
		for(Regex r: suc)
		{
			if(r.getParent().getOperator()==Operator.OR)
			{
				OrRegex or = (OrRegex) r.getParent();
				Regex suc1[] = rm.getRegex().follow(or.getValue1());
				Regex suc2[] = rm.getRegex().follow(or.getValue2());
				if(RegexUtil.contained(suc1,suc) && RegexUtil.contained(suc2,suc)){
					RegexCloneable clone = new RegexCloneCreator(rm.getRegex(),r).clone();
					Regex snew = null;
					if(many)
						snew = new StarRegex(new SingleRegex(rm.getSnew().toString()));
					else
						snew = new OptRegex(new SingleRegex(rm.getSnew().toString()));
					or = (OrRegex) clone.getSubRegex().getParent();
					or.setParameter(1,new OrRegex(or.getParameter(1),snew));
					lst.add(clone.getRegex());
					break;
				}
			}
		}
	}
}