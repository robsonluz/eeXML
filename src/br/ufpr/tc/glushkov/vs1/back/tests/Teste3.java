/*
 * Created on 02/06/2005
 *
 */
package br.ufpr.tc.glushkov.vs1.back.tests;

import br.ufpr.tc.glushkov.vs1.Automato;
import br.ufpr.tc.glushkov.vs1.NormalExpToPrefixExp;
import br.ufpr.tc.glushkov.vs1.MakeAutomato;
import br.ufpr.tc.glushkov.vs1.PrefixExp;
import br.ufpr.tc.glushkov.vs1.back.MakeExp;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Teste3 
{
	public static void main(String args[])
	{
		try{
			NormalExpToPrefixExp ep = new NormalExpToPrefixExp();
			
			//String exp = ep.transformToPrefix("a.b.c*.d.e");
			
			
			String exp = ".(.(*(|(a,b)),c),+(d))";
			//String exp = ".(.(+(.(a,b)),+(c)),*(d))";
			//String exp = ".(d,.(g,.(.(b,*(.(a,b))),d)))";
			//String exp = ".(+(.(a,b)),*(|(d,.(e,f))))";
			//String exp = ".(.(+(.(a,b)),c),*(|(d,.(+(.(e,h)),g))))";
			//String exp = ".(a,&)";
			//String exp = "*(.(a,&))";
			
			
//			String exp = ".(.(.(a,b),*(c)),d)";
			
			exp = ".("+exp+",#)";
			//System.out.println("Expressao: "+exp);
			MakeAutomato g = new MakeAutomato(new PrefixExp(exp));
			Automato a = g.gerarAutomato();
			a.printAutomato();
			MakeExp make = new MakeExp(a);
			System.out.println("Expressão gerada: "+make.makeExp().getExp());
			/*GerarExp t = new GerarExp(a);
			System.out.println("Expressao: "+t.getExp());
			*/
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
