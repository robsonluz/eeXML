/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.tc.test.treeautomata;

import br.ufpr.tc.glushkov.regex.Exp;
import br.ufpr.tc.glushkov.vs1.AutomataRecognize;
import br.ufpr.tc.glushkov.vs1.Automato;
import br.ufpr.tc.glushkov.vs1.MakeAutomato;
import br.ufpr.tc.glushkov.vs1.Operator;
import br.ufpr.tc.glushkov.vs1.PrefixExp;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class TesteExpDTD 
{
	public static void main(String args[])
	{
		
		
//		State st = new State();
//		System.out.println(st.prepareExpression("(cliente,teste,aaa|dfdf)*"));
//		
//		if(1==1)
//			return;
		
		//ExpDTD exp = new ExpDTD("(cliente,teste,aaa|dfdf)*");
		String tt = "(cliente,endereco,e-mail),#";
		//String tt = "(cliente+)";
		//String tt = "(nome+),#";
		ExpDTD expd = new ExpDTD(tt);
//		System.out.println(expd.getOperator());
//		Exp e = expd.getParameter(0);
//		System.out.println(e.getOperator());
		
//		Exp exp = new PrefixExp("*(a)");
		MakeAutomato ma = new MakeAutomato(expd);
		Automato a = ma.gerarAutomato();
		a.printAutomato();
		
		System.out.println("");
		String w[] = {"nome","endereco","e-mail","#"};
		//String w[] = {"cliente","cliente","cliente"};
		AutomataRecognize r = new AutomataRecognize(a);
		System.out.println(r.recognize(w));
		
	}
	
	public static void print(Exp exp)
	{
		if(exp!=null){
			System.out.println(exp.getOperator());
			System.out.println(exp.getExp());
			System.out.println("------");
			if(exp.getOperator()!=Operator.NULL){
			print(exp.getParameter(0));
			print(exp.getParameter(1));
			}
		}
	}
}
