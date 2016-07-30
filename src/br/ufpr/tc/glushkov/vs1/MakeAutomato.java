/*
 * Created on 21/05/2005
 *
 */
package br.ufpr.tc.glushkov.vs1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.ufpr.tc.glushkov.regex.Exp;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class MakeAutomato 
{
	private Exp eexp;
	private List<String> lstTerminais;
	
	public MakeAutomato(Exp expression)
	{
		this.lstTerminais = new LinkedList<String>();
		this.eexp = expression;
		eexp.setExp(prepareExpression(eexp.getExp()));
	}
	private String prepareExpression(String expression)
	{
//		int c = 1;
//		StringBuffer ret = new StringBuffer(expression);
//		for(int i=0;i<expression.length();i++)
//		{
//			String t = expression.substring(i,i+1);
//			if("*.+| (),".indexOf(t)==-1)
//			{
//				ret.replace(i,i+1,""+c);
//				lstTerminais.add(t);
//				c++;
//			}
//		}
//		return ret.toString();
		
		int c = 1;
		String ret = expression;
		String r = "";
		for(int i=0;i<expression.length();i++)
		{
			String t = expression.substring(i,i+1);
			if("*,+| ()".indexOf(t)==-1)
			{
				r+=t;
			}else if(r.length()>0){
				lstTerminais.add(r);
				ret = ret.replaceAll(r,""+c);
				c++;
				r = "";
			}
		}
		if(r!=null && r.length()>0){
			lstTerminais.add(r);
			ret = ret.replaceAll(r,""+c);
		}
		return ret;		
	}
	public boolean nullE(Exp exp)
	{
		if(exp==null)
			return false;
		if(exp.getExp().equals("&"))
			return true;
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
	public String[] first(Exp exp)
	{
		if(exp==null || exp.getExp().equals("&"))
			return new String[0];
		
		//PrefixExp exp = new PrefixExp(e);
		if(exp.getOperator()==Operator.NULL)
			return new String[]{exp.getExp()};
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
	public String[] last(Exp exp)
	{
		if(exp==null || exp.getExp().equals("&"))
			return new String[0];
		if(exp.getOperator()==Operator.NULL)
			return new String[]{exp.getExp()};
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
	public String[] pos(Exp e)
	{
		String exp = e.toString();
		List<String> lst = new LinkedList<String>();
		for(int i=0;i<exp.length();i++)
		{
			String t = exp.substring(i,i+1);
			if("*.+| (),".indexOf(t)==-1)
				lst.add(t);
		}
		return lst.toArray(new String[lst.size()]);
	}
	public String[] follow(String x)
	{
		return follow(this.eexp,x);
	}
	public String[] follow(Exp exp, String x)
	{
		if(exp==null || exp.getExp().equals("&"))
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
					t.setEstadoAtual(si[0]);
					t.setTransicao(getLetterPos(f));
					t.setEstadoNovo(f);
					aut.getLstTransition().add(t);
				}
			}else{
				String[] follows = follow(state);
				for(String f: follows)
				{
					Transition t = new Transition();
					t.setEstadoAtual(state);
					t.setTransicao(getLetterPos(f));
					t.setEstadoNovo(f);
					aut.getLstTransition().add(t);
				}
			}
		}
		arrumarOpcional(aut.getLstTransition());
		String ultimoEstado = aut.getLstTransition().get(aut.getLstTransition().size()-1).getEstadoNovo();
		aut.setF(new String[]{ultimoEstado});
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
}