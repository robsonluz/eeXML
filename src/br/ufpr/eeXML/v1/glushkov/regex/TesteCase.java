/*
 * Created on 22/02/2007
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.ArrayList;
import java.util.List;

import br.ufpr.eeXML.v1.treeautomata.ExpDTD;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class TesteCase {
	private int cont = 1;
	private List<RegexUpdateChoices> updateList;
	
	public TesteCase() {
		updateList = new ArrayList<RegexUpdateChoices>();
	}
	
	public void teste(String exp, String w, String wlinha) {
		this.teste(exp, w, wlinha, true);
	}
	
	public void teste(String exp, String w, String wlinha, boolean print) {
		int nTime = 200;
		long timeSum = 0;
		
		Regex reg = ExpToRegex.convert(new ExpDTD(exp));
		DGrec grec = new DGrec(reg);
		WordList w1 = new WordList(wlinha,",");
		RegexUpdateChoices updateChoices = null;
		for(int i=0;i<nTime;i++) {
			long ini = System.nanoTime();
			updateChoices = grec.evolution(w1);
			long end = System.nanoTime();
			long left = end - ini;
			timeSum += left;
		}
		if(print) {
			updateList.add(updateChoices);
			long timeLeft = timeSum/nTime;
			printResult(exp, w, wlinha, timeLeft);
		}
	}
	
	private void printResult(String exp, String w, String wlinha, long timeLeft) {
		exp = prepareExp(exp);
		w = prepareWord(w);
		wlinha = prepareWord(wlinha);
		System.out.println("\\hline ");
		//$a.(b+c)?.d$ & $ad$ & $and$ & 491812 \\
		
		System.out.print(cont+" & ");
		System.out.print("$"+exp+"$ & ");
		System.out.print("$"+w+"$ & ");
		System.out.print("$"+wlinha+"$ & ");
		System.out.print(timeLeft);
		System.out.println(" \\\\");
		System.out.flush();
		cont++;
	}
	
	private String prepareExp(String exp) {
		exp = exp.replace(',', '.');
		exp = exp.replaceAll("\\*", "^*");
		exp = exp.replaceAll("\\+", "%");
		exp = exp.replace('|', '+');
		exp = exp.replaceAll("%", "^+");
		
		return exp;
	}
	
	private String prepareWord(String w) {
		w = w.replaceAll(",", "");
		return w;
	}
	
	public void printUpdateList() {
		int i = 1;
		for(RegexUpdateChoices ch: this.updateList) {
			System.out.println("\\hline ");
			//$a.(b+c)?.d$ & $ad$ & $and$ & 491812 \\
			
			System.out.print(i+" & ");

//			Regex sourceRegex = ch.getSorceRegex().removeStartEndExpression();
//			String exp = prepareExp(sourceRegex.toString());
//			System.out.println(exp);
			int t = ch.getAllChoices().size();
			int j = 0;
			for(Regex regex: ch.getAllChoices()) {
				String exp = prepareExp(regex.toString());
				System.out.print("$"+exp+"$");
				if(j<t-1)
					System.out.print(", ");
				j++;
			}
			System.out.println(" \\\\");
			i++;
		}
	}
	
	
	public static void main(String args[]) {
		TesteCase teste = new TesteCase();
		teste.teste("a,b,c", "a,b,c", "a,n,b,c", false);
		teste.teste("a,(b|c)?,d", "a,d", "a,n,d", false);//EvOr
		
		teste.teste("a,(b|c)?,d", "a,d", "a,n,d");//EvOr
		
		teste.teste("a,(b|c)", "a,b,c", "a,n,b,c");//EvAnd
		teste.teste("(a,b)*", "a,b,a,b", "a,b,n,a,b");//EvAnd
		
		teste.teste("(a)*", "a,a", "a,n,a");//EvClosure
		teste.teste("a,b*", "a,b,b", "a,n,b,b");//EvClosure
		teste.teste("a*,b", "a,a,b", "a,a,n,b");//EvClosure
		
		teste.teste("a,b?,c", "a,c", "a,n,c");//EvOpt
		
		teste.teste("a,(b|c)?,d+", "a,b,d", "a,b,n,d");
		
		teste.teste("(a,b)+,c*", "a,b,a,b,c", "a,b,a,n,b,c");
		
		teste.teste("(a,b)*,c,(d|e|f)+,g?,h", "a,b,a,b,c,d,d,e,d,f,e,e,h", "a,b,a,b,c,d,n,d,e,d,f,e,e,h");
		teste.teste("(a,b)*,c,(d|e|f)+,g?,h", "a,b,a,b,c,d,d,e,d,f,e,e,h", "a,b,a,n,b,c,d,d,e,d,f,e,e,h");

		teste.teste("(a,b)*,c,(d|e|f)+,g?,h", "a,b,a,b,c,d,d,e,d,f,e,e,h", "a,b,a,b,c,d,d,e,d,f,e,e,h,n");
		
		
		
		teste.teste("a*,b+,c+,d*,e,f?,g", "b,b,b,c,d,e,g", "b,b,b,n,c,d,e,g");
		teste.teste("a*,b+,c+,d*,e,f?,g", "b,b,b,c,d,e,g", "b,b,b,c,d,e,n,g");

		
		teste.teste("(a*,b)+,(c,d,e,f)?,g?", "a,a,a,b,a,a,b,c,d,e,f", "a,a,n,a,b,a,a,b,c,d,e,f");
		teste.teste("(a*,b)+,(c,d,e,f)?,g?", "a,a,a,b,a,a,b,c,d,e,f", "a,a,a,b,n,a,a,b,c,d,e,f");
		teste.teste("(a*,b)+,(c,d,e,f)?,g?", "a,a,a,b,a,a,b,c,d,e,f", "a,a,a,b,a,a,b,n,c,d,e,f");
		teste.teste("(a*,b)+,(c,d,e,f)?,g?", "a,a,a,b,a,a,b,c,d,e,f", "a,a,a,b,a,a,b,c,d,n,e,f");
		teste.teste("(a*,b)+,(c,d,e,f)?,g?", "a,a,a,b,a,a,b,c,d,e,f", "a,a,a,b,a,a,b,c,d,e,f,n");
		
		System.out.println("\n\n\n\n-------------------------------------------------------\n\n\n\n");
		
		teste.printUpdateList();
	}
}
