/*
 * Created on 21/05/2005
 *
 */
package br.ufpr.tc.glushkov.vs2;

import br.ufpr.tc.glushkov.regex.Regex;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class TransitionRegex 
{
	private Regex estadoAtual;
	private String transicao;
	private Regex estadoNovo;
	
	public TransitionRegex()
	{
	}
	
	
	public TransitionRegex(Regex atual, Regex novo) 
	{
		estadoAtual = atual;
		estadoNovo = novo;
	}



	public Regex getEstadoAtual() {
		return estadoAtual;
	}

	public Regex getEstadoNovo() {
		return estadoNovo;
	}

	public String getTransicao() {
		return transicao;
	}

	public void setEstadoAtual(Regex estadoAtual) {
		this.estadoAtual = estadoAtual;
	}

	public void setEstadoNovo(Regex estadoNovo) {
		this.estadoNovo = estadoNovo;
	}

	public void setTransicao(String transicao) {
		this.transicao = transicao;
	}
}