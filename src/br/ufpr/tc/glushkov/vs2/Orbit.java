/*
 * Created on 20/06/2005
 *
 */
package br.ufpr.tc.glushkov.vs2;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.ufpr.tc.glushkov.regex.Regex;
import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Orbit implements Cloneable
{
	private List<Regex> lstNo;
	private TransitionRegex backEdge;
	private Orbit parentOrbit;
	
	public Orbit()
	{
		lstNo = new LinkedList();
	}
	
	public Orbit(Object snull)
	{
		lstNo = null;
	}
	
	public void add(Regex no)
	{
		//no.setTmp("t");
		if(!contains(no)){
			//System.out.println("IRURIUR"+no);
			lstNo.add(no);
		}
		//Collections.sort(lstNo,new OrbComparator());
	}
	
	public void setSO(List<TransitionRegex> lst)
	{
		List<TransitionRegex> lstRemove = new LinkedList();
		for(TransitionRegex t:lst)
		{
			if(contains(t.getEstadoAtual()) && contains(t.getEstadoNovo()))
			{
				//System.out.println(t.getEstadoAtual()+":"+t.getEstadoNovo());
				if(isSaida(t.getEstadoAtual(),lst) && isEntrada(t.getEstadoNovo(),lst)){
					lstRemove.add(t);
				}
			}
		}
		for(TransitionRegex t: lstRemove)
			lst.remove(t);
	}
	
	public boolean contains(Regex n)
	{
		if(lstNo==null)
			return true;
		for(Regex r:lstNo)
			if(r.equals(n))
				return true;
//		return lstNo.contains(n);
		return false;
	}
	
	private boolean isSaida(Regex n,List<TransitionRegex> lst)
	{
		for(TransitionRegex t:lst)
		{
			
			if(t.getEstadoAtual().equals(n) && (!contains(t.getEstadoNovo()))){
				//System.out.println("-"+t.getEstadoAtual()+":"+t.getEstadoNovo());
				return true;
			}
		}
		return false;
	}
	
	private boolean isEntrada(Regex n,List<TransitionRegex> lst)
	{
		for(TransitionRegex t:lst)
		{
			if(t.getEstadoNovo().equals(n) && (!contains(t.getEstadoAtual())))
				return true;
		}
		return false;
	}
	
	public void ajuste(Regex x,Regex y,Regex n)
	{
		if(lstNo!=null)
		{
			if(contains(x) && contains(y))
			{
				System.out.println("Ajuste: "+x+" "+y+" "+n);
				lstNo.remove(x);
				lstNo.remove(y);
				lstNo.add(n);
			}
		}
		if(parentOrbit!=null)
			parentOrbit.ajuste(x,y,n);
	}
	
	public void ajuste(Regex x,Regex n)
	{
		
		if(lstNo!=null)
		{
			if(contains(x))
			{
				System.out.println("Ajuste: "+x+" -> "+n);
				lstNo.remove(x);
				lstNo.add(n);
			}
		}
		if(parentOrbit!=null)
			parentOrbit.ajuste(x,n);
	}
	
	public void printOrbita()
	{
		System.out.println();
		System.out.print("O = { ");
		for(Regex o: lstNo)
		{
			System.out.print(o+" ");
		}
		System.out.println("}");
	}
	
	public Orbit clone()
	{
		Orbit o = new Orbit();
		List<Regex> l = new LinkedList();
		if(lstNo!=null)
		for(Regex t:this.lstNo)
			l.add(t);
		o.lstNo = l;
		o.parentOrbit = this.parentOrbit;
		return o;
	}
	
	public TransitionRegex getBackEdge() 
	{
		return backEdge;
	}
	public void setBackEdge(TransitionRegex backEdge) {
		this.backEdge = backEdge;
	}
	
	public int size()
	{
		if(lstNo==null)
			return 0;
		return lstNo.size();
	}
	
	
	public Regex getFirst()
	{
		try{
			return lstNo.iterator().next();
		}catch(Exception e){
		}
		return null;
	}
//	private class OrbComparator implements Comparator
//	{
//		
//		public int compare(Object o1, Object o2) 
//		{
//			try{
//				return (new Integer(o2.toString()).intValue()) - (new Integer(o1.toString()).intValue());
//			}catch(Exception e){
//			}
//			return 0;
//		}
//	}
	public Orbit getParentOrbit() {
		return parentOrbit;
	}
	public void setParentOrbit(Orbit parentOrbita) {
		this.parentOrbit = parentOrbita;
	}
}