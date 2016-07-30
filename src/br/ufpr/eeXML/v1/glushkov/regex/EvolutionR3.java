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
public class EvolutionR3 
{
	public void performEvolution(RegexModification rm,List<Regex> lst,boolean many)
	{
		//Case 1 e 2//
		Regex suc[] = rm.getRegex().follow(rm.getSnl());
		for(Regex r:suc)
		{
			if(r.getParent().getOperator()==Operator.OPTIONAL)
			{
				perform(r,rm,lst,many);
				return;
			}
		}
		
		//Case 3//
		Regex ant[] = rm.getRegex().previous(rm.getSnr());
		for(Regex r:suc)
		{
			if(r.getParent().getOperator()==Operator.OPTIONAL)
			{
				perform(r,rm,lst,many);
				return;
			}
		}
	}
	
	private void perform(Regex r,RegexModification rm,List<Regex> lst,boolean many)
	{
		RegexCloneCreator cloneCreator = new RegexCloneCreator(rm.getRegex(),r);
		
		RegexCloneable cl = cloneCreator.clone();
		Regex snew = null;
		if(many)
			snew = new OrRegex(new StarRegex(rm.getSnew()),cl.getSubRegex().getParent().clone());
		else
			snew = new OrRegex(new OptRegex(rm.getSnew()),cl.getSubRegex().getParent().clone());
		
		Regex p = cl.getSubRegex().getParent().getParent();
		p.setParameter(cl.getSubRegex().getParent(),snew);
		lst.add(cl.getRegex());
		
		
		cl = cloneCreator.clone();
		if(many)
			snew = new AndRegex(new StarRegex(rm.getSnew()),cl.getSubRegex().getParent().clone());
		else
			snew = new AndRegex(new OptRegex(rm.getSnew()),cl.getSubRegex().getParent().clone());
		p = cl.getSubRegex().getParent().getParent();
		p.setParameter(cl.getSubRegex().getParent(),snew);
		lst.add(cl.getRegex());
						
		cl = cloneCreator.clone();
		if(many)
			snew = new AndRegex(cl.getSubRegex().getParent().clone(),new StarRegex(rm.getSnew()));
		else
			snew = new AndRegex(cl.getSubRegex().getParent().clone(),new OptRegex(rm.getSnew()));
		p = cl.getSubRegex().getParent().getParent();
		p.setParameter(cl.getSubRegex().getParent(),snew);
		lst.add(cl.getRegex());
	}
}