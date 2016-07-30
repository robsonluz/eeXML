/*
 * Created on 20/06/2005
 *
 */
package br.ufpr.tc.glushkov.grec;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Orbit implements Cloneable
{
	private Set<String> lstNo;
	private Transition backEdge;
	private Orbit parentOrbit;
	
	public Orbit()
	{
		lstNo = new LinkedHashSet();
	}
	
	public Orbit(Object snull)
	{
		lstNo = null;
	}
	
	public void add(String no)
	{
		lstNo.add(no);
		//Collections.sort(lstNo,new OrbComparator());
	}
	
	public void setSO(List<Transition> lst)
	{
		List<Transition> lstRemove = new LinkedList();
		for(Transition t:lst)
		{
			if(lstNo.contains(t.getEstadoAtual()) && lstNo.contains(t.getEstadoNovo()))
			{
				//System.out.println(t.getEstadoAtual()+":"+t.getEstadoNovo());
				if(isSaida(t.getEstadoAtual(),lst) && isEntrada(t.getEstadoNovo(),lst)){
					lstRemove.add(t);
				}
			}
		}
		for(Transition t: lstRemove)
			lst.remove(t);
	}
	
	public boolean contains(String n)
	{
		if(lstNo==null)
			return true;
		return lstNo.contains(n);
	}
	
	private boolean isSaida(String n,List<Transition> lst)
	{
		for(Transition t:lst)
		{
			
			if(t.getEstadoAtual().equals(n) && (!lstNo.contains(t.getEstadoNovo()))){
				//System.out.println("-"+t.getEstadoAtual()+":"+t.getEstadoNovo());
				return true;
			}
		}
		return false;
	}
	
	private boolean isEntrada(String n,List<Transition> lst)
	{
		for(Transition t:lst)
		{
			if(t.getEstadoNovo().equals(n) && (!lstNo.contains(t.getEstadoAtual())))
				return true;
		}
		return false;
	}
	
	public void ajuste(String x,String y,String n)
	{
		if(lstNo!=null)
		{
			if(lstNo.contains(x) && lstNo.contains(y))
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
	
	public void ajuste(String x,String n)
	{
		
		if(lstNo!=null)
		{
			if(lstNo.contains(x))
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
		for(String o: lstNo)
		{
			System.out.print(o+" ");
		}
		System.out.println("}");
	}
	
	public Orbit clone()
	{
		Orbit o = new Orbit();
		Set<String> l = new LinkedHashSet();
		if(lstNo!=null)
		for(String t:this.lstNo)
			l.add(t);
		o.lstNo = l;
		o.parentOrbit = this.parentOrbit;
		return o;
	}
	
	public Transition getBackEdge() 
	{
		return backEdge;
	}
	
	public void setEstadoAtualBackEdge(String x)
	{
		if(backEdge!=null)
		getBackEdge().setEstadoAtual(x);
		if(this.parentOrbit!=null)
			parentOrbit.getBackEdge().setEstadoAtual(x);
	}
	public void setEstadoNovoBackEdge(String x)
	{
		if(backEdge!=null)
		getBackEdge().setEstadoNovo(x);
		if(this.parentOrbit!=null)
			parentOrbit.getBackEdge().setEstadoNovo(x);		
	}
	
	public void setBackEdge(Transition backEdge) {
		this.backEdge = backEdge;
	}
	
	public int size()
	{
		if(lstNo==null)
			return 0;
		return lstNo.size();
	}
	
	
	public String getFirst()
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