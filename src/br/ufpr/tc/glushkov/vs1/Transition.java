/*
 * Created on 21/05/2005
 *
 */
package br.ufpr.tc.glushkov.vs1;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Transition 
{
	private String estadoAtual;
	private String transicao;
	private String estadoNovo;
	
	public Transition()
	{
	}
	
	public Transition(String estadoAtual,String estadoNovo)
	{
		this.estadoAtual = estadoAtual;
		this.estadoNovo = estadoNovo;
	}
	
	public String getEstadoAtual() {
		return estadoAtual;
	}
	public String getEstadoAtualParenteses() {
		return checkParenteses(estadoAtual);
	}

	public void setEstadoAtual(String estadoAtual) {
		this.estadoAtual = estadoAtual;
	}
	public String getEstadoNovo() {
		return estadoNovo;
	}
	
	public String getEstadoNovoParenteses() {
		
		return checkParenteses(estadoNovo);
	}
	
	private String checkParenteses(String t)
	{
		if(t.startsWith("(") && t.endsWith(")"))
			return t;
		if(t.indexOf("|")!=-1 || 
		   t.indexOf(".")!=-1)
			return "("+t+")";
		return t;
	}
	public void setEstadoNovo(String estadoNovo) {
		this.estadoNovo = estadoNovo;
	}
	public String getTransicao() {
		return transicao;
	}
	public void setTransicao(String transicao) {
		this.transicao = transicao;
	}
	public boolean equals(Object o)
	{
		if(o==null || !(o instanceof Transition))
			return false;
		Transition that = (Transition)o;
		return (that.estadoAtual.equals(this.estadoAtual) && that.estadoNovo.equals(this.estadoNovo));
	}
}