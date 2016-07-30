/*
 * Created on 21/05/2005
 *
 */
package br.ufpr.tc.glushkov.vs1.tests;

import br.ufpr.tc.glushkov.vs1.GerarGrafico;
import br.ufpr.tc.glushkov.vs1.MakeAutomato;
import br.ufpr.tc.glushkov.vs1.Automato;
import br.ufpr.tc.glushkov.vs1.PrefixExp;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Teste 
{
	public static void main(String args[])
	{
		//System.out.println("*.+|".indexOf("."));
		//String e = "+  ( |(a , b) , |(a , b) )";
		//String e = "+  ( a,b )";
		//String e = "|  ( |(a , *(a)) , |(a , b) )";// ( a|( (a,b)+ ) )|(a|b)
		//String e = "|  ( a,b )";
		//String e = "|(a , .(a,b))"; //a|(a.b)
		
		//String e = ".(.(|(a , b),*(c)),a)"; //(a|b).c+.a
		//String e = ".(.(|(a , b),+(|(c,d))),a)"; //(a|b).c+.a
		String e = ".(.(.(*(|(a,.(b,d))),+(c)),a),.(.(|(a , b),+(c)),a))"; //(a|(b.d))*.c+.a
		
		if(args.length>0)
			e = args[0];
		
		System.out.println("Expressão: "+e);
		//Exp exp = new Exp(e);
		
		//System.out.println(exp.getOperator());
		
		MakeAutomato g = new MakeAutomato(new PrefixExp(e));
		//System.out.println(g.prepareExpression(e));
		/*System.out.print("First: ");
		Common.printArray(g.first());
		System.out.println();
		System.out.print("Last: ");
		Common.printArray(g.last());
		
		
		System.out.println();
		System.out.print("Follow(3): ");
		Common.printArray(g.follow("3"));
		*/
		
		
		Automato aut = g.gerarAutomato();
		aut.printAutomato();
		new GerarGrafico(aut);
		
		/*String f[] = g.first();
		for(int i=0;i<f.length;i++)
			System.out.println(f[i]);
			*/
		
	}
}
