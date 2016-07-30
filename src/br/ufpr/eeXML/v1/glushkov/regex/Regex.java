/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import br.ufpr.eeXML.v1.glushkov.Operator;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public abstract class Regex implements Comparable<Regex>
{
//	private String exp;
	private Regex parent;
	private int pos[];
	
	//Cache//
	private Regex cFollow[][];
	private Regex cPrevious[][];
	private Regex cFirst[];
	private Regex cLast[];
	/////////
	
	private Regex[] findCache(int pos,Regex r[][])
	{
		try{
			return r[pos];
		}catch(Exception e){
			return null;
		}
	}
	
//	protected void setExp(Regex value)
//	{
//		this.exp = new ReGexParser().parserToNormal(value);
//	}
	
	public int compareTo(Regex o) 
	{
		if(o.equals(this))
			return 0;
		return -1;
	}
	
//	public String toString()
//	{
//		setExp(this);
//		return exp;
//	}
	
	public abstract String toString();

	
	public boolean equals(Object obj)
	{
		if(obj!=null){
//			if(obj instanceof String)
//				return exp.equals(obj);
			if(obj instanceof Regex){
				
				Regex r = (Regex) obj;
				boolean is = r.toString().equals(this.toString());
				return is;
			}
		}
		return false;
	}

	public Regex getParent() {
		return parent;
	}

	public void setParent(Regex parent) {
		this.parent = parent;
	}
	
	public Regex[] first()
	{
		if(cFirst==null)
			cFirst = RegexUtil.first(this); 
		return cFirst;
		//return RegexUtil.first(this);
	}
	public Regex[] last()
	{
		if(cLast==null)
			cLast = RegexUtil.last(this); 
		return cLast;
		//return RegexUtil.last(this);
	}
	public Regex[] follow(Regex set[])
	{
		//System.out.println("G: "+this+" ; ");
		return RegexUtil.follow(this,set);
	}
	public Regex[] follow(Regex x)
	{
		if(x.pos().length==1)
		{
			Regex cache[] = findCache(x.pos()[0]-1,cFollow);
			if(cache!=null){
				return cache;
			}else{
				cache = RegexUtil.follow(this,x);
				this.cFollow[x.pos()[0]-1] = cache;
				return cache;
			}
		}
		System.out.println("ERROR access in follows: "+this+" ; "+x);
		return RegexUtil.follow(this,x);
	}
	public Regex[] previous(Regex set[])
	{
		return RegexUtil.previous(this,set);
	}
	public Regex[] previous(Regex x)
	{
		if(x.pos().length==1)
		{
			Regex cache[] = findCache(x.pos()[0]-1,cPrevious);
			if(cache!=null){
				return cache;
			}else{
				cache = RegexUtil.previous(this,x);
				this.cPrevious[x.pos()[0]-1] = cache;
				return cache;
			}
		}
		System.out.println("ERROR access in previous: "+this+" ; "+x);
		return RegexUtil.previous(this,x);
	}
	public boolean belong(Regex regex[])
	{
		return RegexUtil.belong(this,regex);
	}
	
	public boolean match(WordList word)
	{
		word.reset();
		Regex current=null;
		while(word.hasNext())
		{
			Regex r = findMatch(word.nextWord(),current);
			if(r==null)
				break;
			current = r;
		}
		//Verificação para quando a palavra é vazia
		//Verifica se os first são iguais aos last
		//FIXME Não testado
		if(word.getList()!=null && word.getList().length==0 && RegexUtil.contained(first(), last()))
			return true;
		
		
		return current!=null && !word.hasNext() && current.belong(this.last());
	}
	
	public boolean match(String...word)
	{
		Regex current=null;
		int cont = 0;
		for(String w:word)
		{
			Regex r = findMatch(w,current);
			if(r==null)
				break;
			current = r;
			cont++;
		}
		return cont==word.length && current.belong(this.last());
	}
	
	private Regex findMatch(String w,Regex current)
	{
		Regex r[] = null;
		if(current==null){
			r = this.first();
		}else{
			r = this.follow(current);
		}
		if(r!=null)
		{
			for(Regex reg: r)
				if(reg.getOperator()==Operator.NULL && reg.equals(w))
					return reg;
		}
		return null;
	}
	
	
	public abstract Regex getParameter(int i);
	public abstract Operator getOperator();
	public abstract void setParameter(int i,Regex value);
	public abstract void setParameter(Regex oldRegex, Regex newRegex);
	public abstract Regex clone();
	public abstract String toPrefixString();

	public void makePositions()
	{
		RegexUtil.makePositions(this);
	}
	public String toStringPositions()
	{
		return toString();
	}

	public int[] pos() {
		return pos;
	}
	public void setPos(int[] pos) {
		this.pos = pos;
		this.cFollow = new Regex[pos.length][0];
		this.cPrevious = new Regex[pos.length][0];
		for(int i=0;i<pos.length;i++){
			cFollow[i]=null;
			cPrevious[i]=null;
		}
	}
	
	public boolean isPosition(int p)
	{
		return false;
	}
	public boolean equalsPosition(Regex r)
	{
		return false;
	}
	
	public Regex removeStartEndExpression() {
		return removeStartEndExpression(this);
	}
	
	private Regex removeStartEndExpression(Regex r)
	{
		if(r instanceof UpdateRegex)
			return r;
		if(r instanceof AndRegex)
		{
			if(r.getParameter(0) instanceof SingleRegex && r.getParameter(0).equals(SingleRegex.START))
				return removeStartEndExpression(r.getParameter(1));
			if(r.getParameter(1) instanceof SingleRegex && r.getParameter(1).equals(SingleRegex.END))
				return removeStartEndExpression(r.getParameter(0));
			return new AndRegex(removeStartEndExpression(r.getParameter(0)),removeStartEndExpression(r.getParameter(1)));
		}
		if(r instanceof OrRegex)
		{
			return new OrRegex(removeStartEndExpression(r.getParameter(0)),removeStartEndExpression(r.getParameter(1)));
		}
		if(r instanceof OptRegex)
		{
			return new OptRegex(removeStartEndExpression(r.getParameter(0)));
		}
		if(r instanceof StarRegex)
		{
			return new StarRegex(removeStartEndExpression(r.getParameter(0)));
		}
		if(r instanceof StarOneRegex)
		{
			return new StarOneRegex(removeStartEndExpression(r.getParameter(0)));
		}
		if(r instanceof SingleRegex)
		{
			return r;
		}
		return null;
	}	
}