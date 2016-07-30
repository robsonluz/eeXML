/*
 * Created on 21/05/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov;

import br.ufpr.eeXML.v1.glushkov.regex.Exp;
import br.ufpr.eeXML.v1.glushkov.regex.Regex;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Transition 
{
//	private String estadoAtual;
//	private String transicao;
//	private String estadoNovo;
	private Regex estadoAtual;
	private String transicao;
	private Regex estadoNovo;
	
	public Transition()
	{
	}
	
	public Transition(Regex estadoAtual,Regex estadoNovo)
	{
		this.estadoAtual = estadoAtual;
		this.estadoNovo = estadoNovo;
	}
//	
	public Regex getEstadoAtual() {
		return estadoAtual;
	}
	public Regex getEstadoAtualParenteses() {
		return estadoAtual;
	}

	public void setEstadoAtual(Regex estadoAtual) {
		this.estadoAtual = estadoAtual;
	}
	public Regex getEstadoNovo() {
		return estadoNovo;
	}
	public void setEstadoNovo(Regex estadoNovo) {
		this.estadoNovo = estadoNovo;
	}	

//	
//	private String checkParenteses(String t)
//	{
//		if(t.startsWith("(") && t.endsWith(")"))
//			return t;
//		if(t.indexOf("|")!=-1 || 
//		   t.indexOf(".")!=-1)
//			return "("+t+")";
//		return t;
//	}

	public String getTransicao() {
		return transicao;
	}
	public void setTransicao(String transicao) {
		this.transicao = transicao;
	}
//	public boolean equals(Object o)
//	{
//		if(o==null || !(o instanceof Transition))
//			return false;
//		Transition that = (Transition)o;
//		return (that.estadoAtual.equals(this.estadoAtual) && that.estadoNovo.equals(this.estadoNovo));
//	}
}