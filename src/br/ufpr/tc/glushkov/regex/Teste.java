/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.tc.glushkov.regex;
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
		Regex reg = new AndRegex("A","B");
		System.out.println(reg);
		
		RegexRecognize rec = new RegexRecognize(reg);
		System.out.println(rec.recognize("A","B"));
		//ReGexParser parser = new ReGexParser();
		//System.out.println(parser.parserToNormal(reg));
		//((nome,endereco),e-mail),(bairro?,#)
	}
	

}
