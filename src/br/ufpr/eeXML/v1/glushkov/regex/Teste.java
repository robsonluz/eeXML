/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.eeXML.v1.glushkov.regex;

import java.util.List;

import br.ufpr.eeXML.v1.treeautomata.ExpDTD;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class Teste
{
	public static void main(String args[])
	{
		//Regex reg = new StarRegex(new AndRegex(new StarRegex("A"),new SingleRegex("B")));
		//Regex reg = new AndRegex("nome",new AndRegex("endereco",new AndRegex("email",new AndRegex(new OptRegex("bairro"),"#"))));
		//Regex reg = new StarRegex("A");
		//Regex reg = new AndRegex("a",new AndRegex("b","c"));
		//Regex reg = new AndRegex(new OrRegex("a","b"),"c");
		//Regex reg = ExpToRegex.convert(new ExpDTD("a,(b|c),d"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("a,(b|c)?,d"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("a?,g?,(b|c)?,d"));
		//Regwex reg = ExpToRegex.convert(new ExpDTD("a?,b?,c"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("1?,2?,3,7|4,5?,6,(8?,9,10?,11)"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("a*"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("a,b*"));
//		Regex reg = ExpToRegex.convert(new ExpDTD("a,(b|c+)*"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("a,(b|c)?,d+"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("meta*,(column|formula)*,type?"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("meta*,column*,type?,generator?"));
		
		
		//Regex reg = ExpToRegex.convert(new ExpDTD("a,b?,c"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("nome,e-mail,telefone*"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("(a,b)*,c"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("a,b,c,d"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("(a,b)+,c*"));
//		System.out.println(reg);
//		
//		
//		WordList wss = new WordList("meta,meta,type,generator,a",",");
//		//System.out.println("aaa: "+reg.match(wss));
//		DGrec grec = new DGrec(reg);
//		RegexUpdateChoices updateChoices = grec.evolution(wss);
//		reg.makePositions();
//		System.out.println(":"+reg);
//		System.out.println(reg.match(wss));
//		System.out.println(updateChoices.getAllChoices());
//		if(1==1) System.exit(0);
//		
		//RegexRecognize rec = new RegexRecognize(reg);
		//System.out.println(rec.recognize("a","b","d"));
		
		
		//Regex r = rec.evolution("a","b","d");
		//System.out.println(r);
		
		
		//String w[] = new String[]{"a","n","d"};
		//String w[] = new String[]{"a","n","c"};
		//WordList w = new WordList("a,b,c,d,e,j",",");
//		WordList w = new WordList("a,i,j,b,b,c,n",",");
//		WordList w1 = new WordList("a,b,b,c",",");
//		WordList w = new WordList("a,b,c,e,n,e",",");
//		WordList w1 = new WordList("a,b,c,e,e",",");
		//Regex reg = ExpToRegex.convert(new ExpDTD("a,(b?,c)*,d"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("a,b,c"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("a,(b|c),d"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("a,b?,c"));
		//Regex reg = ExpToRegex.convert(new ExpDTD("a,b,(c,d)*,e"));
		Regex reg = ExpToRegex.convert(new ExpDTD("extensao-universitaria*,mba*,curso-curta-duracao*,outros*"));

		DGrec grec = new DGrec(reg);
		
		WordList w = new WordList("mba",",");
		WordList w1 = new WordList("mba,certificacao,curso",",");		

		RegexUpdateChoices updateChoices = grec.evolution(w1);
		
		
		List<Regex> lst = updateChoices.getAllChoices();
		for(Regex r: lst)
		{
			r.makePositions();
			System.out.println("> "+r+" | match: "+r.match(w)+" | match: "+r.match(w1));
		}
		
		//w = new WordList("a,b,n,c,d",",");
		//System.out.println(grec.match("a","b","a","b","c"));
		//List<Regex> lst = grec.evolution(false,"a","b","n","a","b","c");
		//List<Regex> lst = grec.evolution(false,"a","b","n","c");
		//List<Regex> lst = grec.evolution(false,"a","b","n","d");
		//List<Regex> lst = grec.evolution(false,"nome","sobrenome","e-mail","telefone");
		
		//List<Regex> lst = grec.evolution(false,"2","n","3","4","6","8","9","11");
		//List<Regex> lst = grec.evolution(false,"a","n","b");
		//List<Regex> lst = grec.evolution(false,"a","n","a");
		//List<Regex> lst = grec.evolution(false,"a","n","b","b");
		//List<Regex> lst = grec.evolution(false,"a","b","b","c","n");
		//List<Regex> lst = grec.evolution(false,"a","b","n","c");
		//List<Regex> lst = grec.evolution(false,"a","n","c");
		//System.out.println(grec.evolution("a","b","n","a","b","c"));
		

		
		
		
		/*
		System.out.println("\n\n\n");
		SingleRegex a = new SingleRegex("a");
		SingleRegex b = new SingleRegex("b");
		SingleRegex c = new SingleRegex("c");
		SingleRegex d = new SingleRegex("d");
		//Regex r = new AndRegex(a,new StarRegex(new AndRegex(b,new AndRegex(c,d))));
		Regex r = new AndRegex(a,new AndRegex(new OptRegex(new OrRegex(b,c)),d));
		Regex r2 = ExpToRegex.convert(new ExpDTD("(b,c+)?,d?"));
		
		System.out.println(r);
		System.out.println();
		//Regex rs[]=grec.follow(r,d);
		
		
		Regex rs[]=grec.previous(r,b);
		//Regex rs[]=grec.last(r2);
		
		for(Regex rr: rs){
			System.out.println(rr);
		}
		*/
		
		/*
		System.out.println("\n\n--------------");
		System.out.println(reg);
		Evolution ev = new Evolution(reg);
		System.out.println(ev.recognize("a","b","n","d"));
		System.out.println("\n\n--------------");
		for(Regex r: ev.getLstError())
		{
			System.out.println(r+":"+r.getParent());
		}
		*/
		
		
//		System.out.println("\n\n\n");
//		Regex rb = new SingleRegex("c");
//		Regex r1 = new OptRegex(new OrRegex("b",rb));
//		Regex rr = new AndRegex("a",new AndRegex(r1,"d"));
//		System.out.println(rr);
//		//Regex rs[] = rr.follow(r1.last());
//		Regex rs[] = rr.previous(new SingleRegex("d"));
//		for(Regex re:rs)
//			System.out.println(re);
		
		
		/*
		System.out.println("\n\n\n");
		Regex rs = ExpToRegex.convert(new ExpDTD("1?,2?,(3,7|4,5?,6),8?,9,10?,11"));
		System.out.println(rs.toPrefixString());
		for(Regex re:rs.last())
			System.out.println(re);
		*/
		//System.out.println("\n\n\n");
		
//		Regex rs = ExpToRegex.convert(new ExpDTD("a?,g?,(b|c)?,d"));
//		Regex ra = rs.getParameter(0);
//		//System.out.println(ra.first());
//		for(Regex re:rs.follow(ra.last()))
//			System.out.println(re);
		
		
		//System.out.println(rec.match(reg,"c"));
		//ReGexParser parser = new ReGexParser();
		//System.out.println(parser.parserToNormal(reg));
		//((nome,endereco),e-mail),(bairro?,#)
		
//		reg = ExpToRegex.convert(new ExpDTD("a,(b|d)+,c"));
//		System.out.println(reg);
//		w = new WordList("a,b,c",",");
//		reg = grec.delete(reg,w,1);
//		w = new WordList("a,c",",");
//		System.out.println(reg+" : "+reg.match(w));
	}
}