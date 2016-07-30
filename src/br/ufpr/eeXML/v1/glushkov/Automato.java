/*
 * Created on 05/05/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Automato 
{
	private String[] q;
	private String[] sigma;
	private String si[];
	private String f[];
	private List<String[]> lstFollows;
	private List<Transition> lstTransition;
	
	public Automato()
	{
		this.lstFollows = new LinkedList();
		this.lstTransition = new LinkedList();
	}
	
	public void addTransition(Transition t)
	{
		for(Transition n: lstTransition)
		{
			if(n.equals(t))
				return;
		}
		lstTransition.add(t);
	}
	
	public void printAutomato()
	{
		System.out.println("Automato");
		System.out.println("M = < Q , Sigma , {si} , F , Transitions > ");
		Automato aut = this;
		
		System.out.print("Q =");
		Common.printArray(aut.getQ());
		System.out.println();
		
		System.out.print("Sigma =");
		Common.printArray(aut.getSigma());
		System.out.println();
		
		System.out.print("SI =");
		Common.printArray(aut.getSi());
		System.out.println();
		
		System.out.print("F =");
		Common.printArray(aut.getF());
		System.out.println();
		
		printTransitions();
		printFollows();
	}
	
	private void printTransitions()
	{
		System.out.println("Transitions = ");
		for(Transition t: lstTransition)
		{
			System.out.println("\t       "+t.getEstadoAtual()+" "+
								t.getTransicao()+" "+t.getEstadoNovo());
		}
	}
	
	private void printFollows()
	{
		System.out.println("\nFollows:");
		System.out.println("i | Follow(E,i)");
		System.out.println("--+------------");
		int cont = 1;
		for(String[] t : lstFollows)
		{
			System.out.print(cont+" |");
			Common.printArray(t);
			System.out.println();
			cont++;
		}
	}
	
	public String[] getQ() {
		return q;
	}
	
	public void setQ(String[] q) {
		this.q = q;
	}
	public String[] getF() {
		return f;
	}
	public void setF(String[] f) {
		this.f = f;
	}
	public String[] getSigma() {
		return sigma;
	}
	public void setSigma(String[] sigma) {
		this.sigma = sigma;
	}

	public String[] getSi() {
		return si;
	}
	public void setSi(String[] si) {
		this.si = si;
	}
	public List<String[]> getLstFollows() {
		return lstFollows;
	}
	public void setLstFollows(List<String[]> lstFollows) {
		this.lstFollows = lstFollows;
	}
	public List<Transition> getLstTransition() {
		return lstTransition;
	}
	public void setLstTransition(List<Transition> lstTransition) {
		this.lstTransition = lstTransition;
	}
}