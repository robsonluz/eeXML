/*
 * Created on 17/10/2005
 *
 */
package br.ufpr.tc.glushkov.grec;

import br.ufpr.tc.glushkov.regex.Exp;
import br.ufpr.tc.glushkov.vs1.Automato;
import br.ufpr.tc.glushkov.vs1.MakeAutomato;
import br.ufpr.tc.glushkov.vs1.PrefixExp;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class Grec 
{
	public Grec()
	{
		try{
//			String exp = ".(a,b)";
//			String w = "ab";
//			String wlinha = "abn";
			
			String exp = ".(.(a,b),c)";
			String w = "abc";
			String wlinha = "abnc";			
			
//			String exp = ".(*(.(a,b)),c)";
//			String w = "ababc";
//			String wlinha = "abnabc";
			
//			String exp = ".(.(a,|(b,c)),d)";
//			String w = "abd";
//			String wlinha = "abnd";

//			String exp = ".(.(a,|(b,&)),c)";
//			String w = "ac";
//			String wlinha = "anc";
			
//			String exp = ".(.(a,|(|(b,c),&)),d)";
//			String w = "ad";
//			String wlinha = "axd";			
			
			
			exp = ".("+exp+",#)";
			Exp e = new PrefixExp(exp);
			MakeAutomato ma = new MakeAutomato(e);
			Automato a = ma.gerarAutomato();
			a.printAutomato();
			GraphModification gm = CheckAutomataWord.checkAutomataWord(a,wlinha);
			MakeExp me = new MakeExp(a,gm);
			Exp elinha = me.makeExp();
			System.out.println(elinha.getExp());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		new Grec();
	}
}	