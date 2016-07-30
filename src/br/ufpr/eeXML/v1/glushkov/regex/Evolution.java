/*
 * Created on 19/04/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class Evolution 
{
	private Regex regex;
	private List<Regex> lstError;
	public Evolution(Regex regex)
	{
		this.regex = regex;
		lstError = new LinkedList();
	}
	
	public boolean evolution(String ... ws)
	{
		
		return false;
	}
	
	private String[] ws;
	private int index=0;
	public boolean recognize(String ... ws)
	{
		this.ws = ws;
		return match(regex) && (index==ws.length);
	}
	
	private boolean match(Regex r)
	{
		if(r.getOperator()==Operator.NULL)
		{
			SingleRegex s = (SingleRegex) r;
			boolean b = s.getValue().equals(getWord());
			if(b) nextWord();
			else{
				//System.out.println(r+"->"+r.getParent()+":"+getWord());
				lstError.add(r);
			}
			return b;
		}
		if(r.getOperator()==Operator.CONCAT)
		{
			AndRegex and = (AndRegex) r;
			return match(and.getValue1()) && match(and.getValue2());
		}
		if(r.getOperator()==Operator.OR)
		{
			OrRegex or = (OrRegex) r;
			return match(or.getValue1()) || match(or.getValue2());
		}
		if(r.getOperator()==Operator.FECHO)
		{
			int ind = index;
			StarRegex star = (StarRegex) r;
			boolean ret = true;
			boolean mc = true;
			while(mc){
				mc = match(star.getValue());
				if(ind!=index){
					ret = ret && mc;
					ind=index;
				}
			}
			return ret;
		}
		if(r.getOperator()==Operator.FECHO_ONE)
		{
			int ind = index;
			StarOneRegex star = (StarOneRegex) r;
			boolean ret = true;
			boolean mc = true;
			boolean ret1 = false;
			while(mc){
				mc = match(star.getValue());
				if(ind!=index){
					ret = ret && mc;
					ind=index;
					if(mc) ret1 = true;
				}
			}
			return ret&&ret1;
		}
		if(r.getOperator()==Operator.OPTIONAL)
		{
			OptRegex opt = (OptRegex) r;
			int ind = index;
			return match(opt.getValue()) || ind==index; 
		}
		return false;
	}
	
	private String getWord()
	{
		if(index<ws.length)
			return ws[index];
		return null;
	}
	
	private void nextWord()
	{
		index++;
	}

	public List<Regex> getLstError() {
		return lstError;
	}

	public void setLstError(List<Regex> lstError) {
		this.lstError = lstError;
	}
}
