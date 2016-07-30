/*
 * Created on 25/04/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.LinkedList;
import java.util.List;

import org.openide.util.datatransfer.ExTransferable.Single;

import br.ufpr.eeXML.v1.glushkov.Common;
import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class Dgrec3 
{
	private Regex regex;
	private EvolutionR1 evR1;
	private EvolutionR2 evR2;
	private EvolutionR3 evR3;
	private EvolutionOrbit evOrb;
	
	public Dgrec3(Regex regex)
	{
		this.regex = regex;
		this.evR1 = new EvolutionR1();
		this.evR2 = new EvolutionR2();
		this.evR3 = new EvolutionR3();
		this.evOrb = new EvolutionOrbit();
	}
	
	public List<Regex> evolution(boolean many,String ... word)
	{
		Regex current=null;
		Regex exp = new AndRegex(SingleRegex.START,new AndRegex(regex,SingleRegex.END));
		System.out.println(exp.toPrefixString()+"\r\n");
		int cont = 0;
		RegexList lst = new RegexList();
		String ws[] = new String[word.length+2];
		ws[0] = SingleRegex.START;
		ws[ws.length-1] = SingleRegex.END;
		System.arraycopy(word,0,ws,1,word.length);
		
		for(String w:ws)
		{
			Regex r = findMatch(w,current,exp);
			if(r==null){
//				RegexModification rm = new RegexModification(exp,current,findMatch(ws[cont+1],current,exp),w);
//				System.out.println("Ev: "+w+" ; "+rm.getSnl()+"-"+rm.getSnr()+" ; "+current.getParent());
//
//				evR1.performEvolution(rm,lst,many);
//				evR2.performEvolution(rm,lst,many);
//				evR3.performEvolution(rm,lst,many);
//				evOrb.performEvolution(rm,lst,many);
				break;
			}
			current = r;
			cont++;
		}
		lst.removeStartEndExpressions();
		return lst;
	}
	
	private Regex findMatch(String w,Regex current,Regex regex)
	{
		Regex r[] = null;
		if(current==null){
			r = regex.first();
		}else{
			r = regex.follow(current);
		}
		if(r!=null)
		{
			for(Regex reg: r)
				if(reg.getOperator()==Operator.NULL && reg.toString().equals(w))
					return reg;
		}
		return null;
	}
}