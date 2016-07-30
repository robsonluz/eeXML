/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.tc.test.treeautomata;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.tc.glushkov.regex.Exp;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class State 
{
	private String qName;
	private String name;
	private Exp exp;
	private String strExp;
	private List<String> lstComp;
	private List<String> lstOp;
	
	private List<String> lstTerminais;
	
	public State()
	{
		lstComp = new LinkedList();
		lstOp = new LinkedList();
		lstTerminais = new LinkedList();
	}
	
	public Exp getExp() {
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

	public void setStrExp(String strExp) 
	{
		//this.strExp = "("+strExp+"),#";
		this.strExp = strExp;
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
}