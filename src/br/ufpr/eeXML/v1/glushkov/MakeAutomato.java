/*
 * Created on 21/05/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.ufpr.eeXML.v1.glushkov.regex.AndRegex;
import br.ufpr.eeXML.v1.glushkov.regex.Exp;
import br.ufpr.eeXML.v1.glushkov.regex.ExpToRegex;
import br.ufpr.eeXML.v1.glushkov.regex.OrRegex;
import br.ufpr.eeXML.v1.glushkov.regex.Regex;
import br.ufpr.eeXML.v1.glushkov.regex.SingleRegex;
import br.ufpr.eeXML.v1.treeautomata.ExpDTD;



/**
 * @author Robson João Padilha da Luz
 *
 */
public class MakeAutomato 
{
	private Regex eexp;
	private List<String> lstTerminais;
	
	public MakeAutomato(Regex exp)
	{
		this.lstTerminais = new LinkedList<String>();
		//this.eexp = prepareExpression(exp);
		this.eexp = new AndRegex(new AndRegex(new SingleRegex("#"),exp),"#");
		findTerminals(eexp);
		this.eexp = arrumaOpcional(eexp);
		//System.out.println(eexp);
		//findTerminals(eexp);
		//eexp.setExp(prepareExpression(eexp.getExp()));
	}
	
	private Regex arrumaOpcional(Regex exp)
	{
		if(exp!=null){
			if(exp.getOperator()!=Operator.NULL){
				exp.setParameter(0,arrumaOpcional(exp.getParameter(0)));
				exp.setParameter(1,arrumaOpcional(exp.getParameter(1)));
			}
			if(exp.getOperator()==Operator.OPTIONAL)
			{
				return new OrRegex(exp.getParameter(0),new SingleRegex("&"));
			}
			return exp;
		}
		return null;
	}
	
	private int index = 1;
	private void findTerminals(Regex exp)
	{
		if(exp.getOperator()==Operator.NULL){
			lstTerminais.add(exp.toString());
			((SingleRegex)exp).setValue(""+index);
			index++;
		}
		
		if(exp.getOperator()==Operator.OR ||exp.getOperator()==Operator.CONCAT)
		{
			findTerminals(exp.getParameter(0));
			findTerminals(exp.getParameter(1));
		}
		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE || exp.getOperator()==Operator.OPTIONAL)
		{
			findTerminals(exp.getParameter(0));
		}
	}
	
	public boolean nullE(Regex exp)
	{
		if(exp==null)
			return false;
		if(exp.toString().equals("&"))
			return true;
//		if(exp.getOperator()==Operator.OPTIONAL)
//			return true;
		//PrefixExp exp = new PrefixExp(e);
		if(exp.getOperator()==Operator.NULL)
			return false;
		if(exp.getOperator()==Operator.OR)
			return nullE(exp.getParameter(0)) || nullE(exp.getParameter(1));
		if(exp.getOperator()==Operator.CONCAT)
			return nullE(exp.getParameter(0)) && nullE(exp.getParameter(1));
		if(exp.getOperator()==Operator.FECHO_ONE)
			return nullE(exp.getParameter(0));
		if(exp.getOperator()==Operator.FECHO)
			return true;
		return false;
	}
	public String[] first()
	{
		return first(eexp);
	}
	public String[] first(Regex exp)
	{
		if(exp==null || exp.toString().equals("&"))
			return new String[0];
		
		//PrefixExp exp = new PrefixExp(e);
		if(exp.getOperator()==Operator.NULL)
			return new String[]{exp.toString()};
		if(exp.getOperator()==Operator.OR)
			return Common.union(first(exp.getParameter(0)) , first(exp.getParameter(1)));
		if(exp.getOperator()==Operator.CONCAT)
			return Common.union(first(exp.getParameter(0)) , Common.product(nullE(exp.getParameter(0)),first(exp.getParameter(1))));
		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE)
			return first(exp.getParameter(0));
		return new String[0];
	}
	public String[] last()
	{
		return last(eexp);
	}
	public String[] last(Regex exp)
	{
		if(exp==null || exp.toString().equals("&"))
			return new String[0];
		if(exp.getOperator()==Operator.NULL)
			return new String[]{exp.toString()};
		if(exp.getOperator()==Operator.OR)
			return Common.union(last(exp.getParameter(0)) , last(exp.getParameter(1)));
		if(exp.getOperator()==Operator.CONCAT)
			return Common.union(last(exp.getParameter(1)) , Common.product(nullE(exp.getParameter(1)),last(exp.getParameter(0))));
		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE)
			return last(exp.getParameter(0));
		return new String[0];
	}
	public boolean ix(String[] X, String x)
	{
		for(int i=0;i<X.length;i++)
			if(X[i].equals(x))
				return true;
		return false;
	}
	public String[] pos(Regex e)
	{
		String exp = e.toString();
		List<String> lst = new LinkedList<String>();
		for(int i=0;i<exp.length();i++)
		{
			String t = exp.substring(i,i+1);
			if("*.+| (),".indexOf(t)==-1){
				if(!t.equals("?"))
				lst.add(t);
			}
		}
		return lst.toArray(new String[lst.size()]);
	}
	public String[] follow(String x)
	{
		return follow(this.eexp,x);
	}
	public String[] follow(Regex exp, String x)
	{
		if(exp==null || exp.toString().equals("&"))
			return new String[0];
		if(exp.getOperator()==Operator.NULL)
			return new String[0];
		if(exp.getOperator()==Operator.OR)
			return Common.union(Common.product(ix(pos(exp.getParameter(0)),x),follow(exp.getParameter(0),x)),
								Common.product(ix(pos(exp.getParameter(1)),x),follow(exp.getParameter(1),x)));
		if(exp.getOperator()==Operator.CONCAT)
			return Common.union(Common.union(Common.product(ix(pos(exp.getParameter(0)),x),follow(exp.getParameter(0),x)),
					Common.product(ix(pos(exp.getParameter(1)),x),follow(exp.getParameter(1),x))),
					Common.product(ix(last(exp.getParameter(0)),x),first(exp.getParameter(1)) ));
		if(exp.getOperator()==Operator.FECHO || exp.getOperator()==Operator.FECHO_ONE)
			return Common.union(follow(exp.getParameter(0),x), Common.product( ix(last(exp.getParameter(0)),x) , first(exp.getParameter(0))   ));
		return new String[0];
	}
	public String getLetterPos(String pos)
	{
		int p = new Integer(pos).intValue();
		return lstTerminais.get(p-1);
	}
	public Automato gerarAutomato()
	{
		Automato aut = new Automato();
		String[] si = new String[]{"0"};
		aut.setSi(si);
		aut.setQ(Common.union(si,pos(this.eexp)));
		aut.setF(Common.union(last(this.eexp),Common.product(nullE(eexp),si)));
		aut.setSigma(this.lstTerminais.toArray(new String[lstTerminais.size()]));
		//Preenche Follows//
		String pos[] = pos(this.eexp);
		for(int i=0;i<pos.length;i++)
		{
			aut.getLstFollows().add(follow(pos[i]));
		}
		//Prenche Transicoes
		for(String state : aut.getQ())
		{
			if(state.equals(si[0]))
			{
				String first[] = first();
				for(String f: first)
				{
					Transition t = new Transition();
					t.setEstadoAtual(new SingleRegex(si[0]));
					t.setTransicao(getLetterPos(f));
					t.setEstadoNovo(new SingleRegex(f));
					aut.getLstTransition().add(t);
				}
			}else{
				String[] follows = follow(state);
				for(String f: follows)
				{
					Transition t = new Transition();
					t.setEstadoAtual(new SingleRegex(state));
					t.setTransicao(getLetterPos(f));
					t.setEstadoNovo(new SingleRegex(f));
					aut.getLstTransition().add(t);
				}
			}
		}
		arrumarOpcional(aut.getLstTransition());
		Regex ultimoEstado = aut.getLstTransition().get(aut.getLstTransition().size()-1).getEstadoNovo();
		aut.setF(new String[]{ultimoEstado.toString()});
		return aut;
	}
	
	private void arrumarOpcional(List<Transition> lst)
	{
		List<Transition> lstOp = new LinkedList();
		for(Iterator<Transition> it=lst.iterator();it.hasNext();)
		{
			Transition t = it.next();
			if(t.getTransicao().equals("&")){
				lstOp.add(t);
				//lst.remove(t);
			}
		}
		
		for(Transition t:lstOp)
		{
			lst.remove(t);
			for(Iterator<Transition> it=lst.iterator();it.hasNext();)
			{
				Transition tt = it.next();
				if(t.getEstadoNovo().equals(tt.getEstadoAtual()))
				{
					tt.setEstadoAtual(t.getEstadoAtual());
				}
			}
		}
	}
	
	/*
	private Regex prepareExpression(String expression)
	{
		int c = 1;
		String ret = expression;
		String r = "";
		//int ini=0;
		//int end=0;
		for(int i=0;i<expression.length();i++)
		{
			String t = expression.substring(i,i+1);
			if("*,+| ()".indexOf(t)==-1)
			{
				r+=t;
				//end++;
			}else if(r.length()>0){
				lstTerminais.add(r);
				ret = ret.replaceFirst(r,""+c);
				//ret = ret.re
				c++;
				r = "";
				//ini=end;
			}
		}
		if(r!=null && r.length()>0){
			lstTerminais.add(r);
			ret = ret.replaceAll(r,""+c);
		}
		Exp e = new ExpDTD(ret);
		return ExpToRegex.convert(e);
	}
	*/
}