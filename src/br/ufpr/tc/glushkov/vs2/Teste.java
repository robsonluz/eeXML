/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.tc.glushkov.vs2;

import br.ufpr.tc.glushkov.regex.Exp;
import br.ufpr.tc.glushkov.vs1.Automato;
import br.ufpr.tc.glushkov.vs1.MakeAutomato;
import br.ufpr.tc.glushkov.vs1.back.MakeExp;
import br.ufpr.tc.test.treeautomata.ExpDTD;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class Teste {
	public static void main(String args[])
	{
		//String tt = "(nome+),cliente,#";
		String tt = "(titulo|data),(texto*),#";
		ExpDTD expd = new ExpDTD(tt);
//		System.out.println(expd.getOperator());
//		Exp e = expd.getParameter(0);
//		System.out.println(e.getOperator());
		
//		Exp exp = new PrefixExp("*(a)");
		MakeAutomato ma = new MakeAutomato(expd);
		Automato a = ma.gerarAutomato();
		a.printAutomato();
		
		try{
			br.ufpr.tc.glushkov.vs2.MakeExp me = new br.ufpr.tc.glushkov.vs2.MakeExp(a);
			//MakeExp me = new MakeExp(a);
			Exp e = me.makeExp();
			System.out.println(":"+e.getExp());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
