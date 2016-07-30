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
public class DGrec 
{
	private Regex regex;
//	private EvolutionR1 evR1;
//	private EvolutionR2 evR2;
//	private EvolutionR3 evR3;
//	private EvolutionOrbit evOrb;
	private EvolutionAnd evAnd;
	private EvolutionOr evOr;
	private EvolutionOpt evOpt;
	private EvolutionFecho evFecho;
	
	public DGrec(Regex regex)
	{
		this.regex = regex;
		this.evAnd = new EvolutionAnd();
		this.evOr = new EvolutionOr();
		this.evOpt = new EvolutionOpt();
		this.evFecho = new EvolutionFecho();
	}
	
	public Regex delete(WordList ws, int posDelete)
	{
		Regex exp = regex;
		exp.makePositions();
		Regex current = null;
		int c=0;
		while(ws.hasNext())
		{
			String w = ws.nextWord();
			System.out.println(w);
			Regex r = findMatch(w,current,exp);
			if(c==posDelete)
			{
				Regex snr = findMatch(ws.nextWord(),r,exp);
				//System.out.println(snr);
				if(snr!=null && current!=null && snr.belong(exp.follow(current)))
					return exp;
				return delete(exp,r);
			}
			current = r;
			c++;
		}
		return exp;
	}
	
	private Regex delete(Regex e,Regex n)
	{
		if(e instanceof SingleRegex)
		{
			return e;
		}
		if(e instanceof OrRegex){
			Regex v1 = delete(e.getParameter(0),n);
			if(v1==n) v1 = new OptRegex(v1);
			Regex v2 = delete(e.getParameter(1),n);
			if(v2==n) v2 = new OptRegex(v2);
			return new OrRegex(v1,v2);
		}
		if(e instanceof AndRegex){
			Regex v1 = delete(e.getParameter(0),n);
			if(v1==n) v1 = new OptRegex(v1);
			Regex v2 = delete(e.getParameter(1),n);
			if(v2==n) v2 = new OptRegex(v2);
			return new AndRegex(v1,v2);
		}
		if(e instanceof StarOneRegex){
			Regex v1 = delete(e.getParameter(0),n);
			if(v1==n) return new StarRegex(v1);
			return new StarOneRegex(v1);
		}
		if(e instanceof StarRegex)
			return new StarRegex(delete(e.getParameter(0),n));
		if(e instanceof OptRegex)
			return new OptRegex(delete(e.getParameter(0),n));
		if(e instanceof UpdateRegex)
			return e;
		return null;
	}
	

	public RegexUpdateChoices evolution(WordList wss)
	{
		WordList word = wss.clone();
		Regex current=null;
		Regex exp = new AndRegex(SingleRegex.START,new AndRegex(regex,SingleRegex.END));
		//System.out.println(exp.toPrefixString()+"\r\n");
		int cont = 0;
		
		String ws[] = new String[word.getList().length+2];
		ws[0] = SingleRegex.START;
		ws[ws.length-1] = SingleRegex.END;
		System.arraycopy(word.getList(),0,ws,1,word.getList().length);
		word.setList(ws);
		word.reset();
		
		long t1 = System.currentTimeMillis();
		RegexList lst = null;
		exp.makePositions();
		while(word.hasNext())
		{
			String w = word.nextWord();
			Regex r = findMatch(w,current,exp);
			if(r==null){
				Regex snr = null;
				Regex snl = current;
				UpdateRegex snew = new UpdateRegex();
				snew.add(w);
				while(true){
					String t = word.nextWord();
					snr = findMatch(t,current,exp); 
					if(snr!=null)
						break;
					snew.add(t);
				}
				if(lst==null){
					lst = new RegexList();
					RegexModification rm = new RegexModification(exp,snl,snr,snew);
					//System.out.println("Ev: "+w+" ; "+rm.getSnl()+"-"+rm.getSnr()+" ; "+exp);
					dgrec(rm,lst);
				}else{
					List<Regex> tmp = lst;
					lst = new RegexList();
					for(Regex rr: tmp)
					{
						rr.makePositions();
						RegexModification rm = new RegexModification(rr,snl,snr,snew);
						//System.out.println("Eve: "+w+" ; "+rm.getSnl()+"-"+rm.getSnr()+" ; "+rr.toPrefixString());
						dgrec(rm,lst);
					}
				}
				r = snr;
				//break;
			}
			current = r;
			cont++;
		}
		//System.out.println("Evolution Time Left: "+(System.currentTimeMillis()-t1));
		return new RegexUpdateChoices(lst, exp);
	}
	
//	public List<Regex> evolution(boolean many,String ... word)
//	{
//		Regex current=null;
//		Regex exp = new AndRegex(SingleRegex.START,new AndRegex(regex,SingleRegex.END));
//		System.out.println(exp.toPrefixString()+"\r\n");
//		int cont = 0;
//		RegexList lst = new RegexList();
//		String ws[] = new String[word.length+2];
//		ws[0] = SingleRegex.START;
//		ws[ws.length-1] = SingleRegex.END;
//		System.arraycopy(word,0,ws,1,word.length);
//		
//		for(String w:ws)
//		{
//			Regex r = findMatch(w,current,exp);
//			if(r==null){
//				RegexModification rm = new RegexModification(exp,current,findMatch(ws[cont+1],current,exp),w);
//				System.out.println("Ev: "+w+" ; "+rm.getSnl()+"-"+rm.getSnr()+" ; "+current.getParent());
//
//				evolution(rm,lst,many);
//				break;
//			}
//			current = r;
//			cont++;
//		}
//		lst.removeStartEndExpressions();
//		return lst;
//	}
	
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
	
	private void dgrec(RegexModification rm, List<Regex> lst)
	{
		dgrec(rm.getRegex(),rm,lst);
	}
	
	private void dgrec(Regex r, RegexModification rm, List<Regex> lst)
	{
		if(r instanceof SingleRegex || r instanceof UpdateRegex)
			return;

		if(r instanceof AndRegex)
			evAnd.performEvolution(r,rm,lst);

		if(r instanceof OrRegex)
			evOr.performEvolution(r,rm,lst);
		
		if(r instanceof OptRegex)
			evOpt.performEvolution(r,rm,lst);
		
		if(r instanceof StarRegex || r instanceof StarOneRegex)
			evFecho.performEvolution(r,rm,lst);
		
		dgrec(r.getParameter(0),rm,lst);
		if(r instanceof AndRegex || r instanceof OrRegex)
			dgrec(r.getParameter(1),rm,lst);
	}
	
	
	
//	public RegexUpdateChoices evolSingle(WordList wss)
//	{
//		WordList word = wss.clone();
//		Regex exp = new AndRegex(SingleRegex.START,new AndRegex(regex,SingleRegex.END));
//		System.out.println(exp.toPrefixString()+"\r\n");
//		int cont = 0;
//		
//		String ws[] = new String[word.getList().length+2];
//		ws[0] = SingleRegex.START;
//		ws[ws.length-1] = SingleRegex.END;
//		System.arraycopy(word.getList(),0,ws,1,word.getList().length);
//		word.setList(ws);
//		word.reset();
//		
//		Regex current = findMatch(exp.first(),word.nextWord());
//		word.reset();
//		while(word.hasNext())
//		{
//			Regex currentL = findMatch(exp.follow(current),word.nextWord()); 
//			if(currentL==null)
//			{
//				Regex snl = current;
//				//Regex snr = 
//			}
//		}
//		return null;
//	}
//	private Regex findMatch(Regex r[],String word)
//	{
//		for(Regex reg: r)
//			if(reg.getOperator()==Operator.NULL && reg.toString().equals(word))
//				return reg;
//		return null;
//	}
}