/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.eeXML.v1.treeautomata;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.eeXML.v1.glushkov.regex.Exp;
import br.ufpr.eeXML.v1.glushkov.regex.Regex;
import br.ufpr.eeXML.v1.glushkov.regex.RegexUpdateChoices;



/*
 * @autor Robson João Padilha da Luz
 *
 */
public class State 
{
	public static enum Type {ELEMENT,ATTRIBUTE};
	private String qName;
	private String name;
	private Regex exp;
	private String strExp;
	private List<String> lstComp;
	private List<String> lstOp;
	
	private List<String> lstTerminais;
	private Type type;
	
	//private List<Regex> lstEvolutionRegex;
	private RegexUpdateChoices choices;
	
	private State parentElement;
	
	private boolean evolution;
	
	/** Representa que o state correpodente a um elemento com o conteúdo texto,
	 * onde a expressão regular fica igual a 'data'
	 */ 
	private boolean regexData;
	
	public State()
	{
		lstComp = new LinkedList();
		lstOp = new LinkedList();
		lstTerminais = new LinkedList();
	}
	
	public void setExp(Regex exp)
	{
		this.exp = exp;
	}
	public Regex getExp() {
		return exp;
	}
	public List<String> getLstComp() {
		return lstComp;
	}
	public List<String> getLstOp() {
		return lstOp;
	}
	public String getName() {
		return name;
	}
	public String getQName() {
		return qName;
	}
//	public void setExp(Exp exp) {
//		this.exp = exp;
//	}
	public void setLstComp(List<String> lstComp) {
		this.lstComp = lstComp;
	}
	public void setLstOp(List<String> lstOp) {
		this.lstOp = lstOp;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setQName(String name) {
		qName = name;
	}

	public String getStrExp() {
		return strExp;
	}
/*
	public void setStrExp(String strExp) 
	{
		//this.strExp = "("+strExp+"),#";
		
		this.strExp = prepareExpression(strExp);
		transformExp();
	}
	
	private void transformExp()
	{
		this.exp = new ExpDTD(this.strExp);
	}
	
	public String prepareExpression(String expression)
	{
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
		return ret;
	}
	*/
	public RegexUpdateChoices getChoices() {
		return choices;
	}

	public void setChoices(RegexUpdateChoices choices) {
		this.choices = choices;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public State getParentElement() {
		return parentElement;
	}

	public void setParentElement(State parentElement) {
		this.parentElement = parentElement;
	}

	public boolean isEvolution() {
		return evolution;
	}

	public void setEvolution(boolean evolution) {
		this.evolution = evolution;
	}

	public boolean isRegexData() {
		return regexData;
	}

	public void setRegexData(boolean regexData) {
		this.regexData = regexData;
	}
}