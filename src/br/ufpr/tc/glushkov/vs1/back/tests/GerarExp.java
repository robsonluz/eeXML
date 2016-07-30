/*
 * Created on 31/05/2005
 *
 */
package br.ufpr.tc.glushkov.vs1.back.tests;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.ufpr.tc.glushkov.vs1.Automato;
import br.ufpr.tc.glushkov.vs1.Common;
import br.ufpr.tc.glushkov.vs1.MakeAutomato;
import br.ufpr.tc.glushkov.vs1.Transition;
import br.ufpr.tc.glushkov.vs1.back.Orbit;
import br.ufpr.tc.glushkov.vs1.back.TransitionComparator;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class GerarExp 
{
	private List<Transition> lstTransition;
	private Set<Transition> lstOrbitas;
	private Automato aut;
	public GerarExp()
	{
		lstOrbitas = new LinkedHashSet();
		lstTransition = init(tmpPreencheTransicoes(),null);
		System.out.println("\n\nFinal: ");
		printLstTransicoes(lstTransition);
		
	}
	public GerarExp(Automato aut)throws Exception
	{
		this.aut = aut;
		lstOrbitas = new LinkedHashSet();
		lstTransition = init(aut.getLstTransition(),null);
		System.out.println("\n\nFinal: ");
		printLstTransicoes(lstTransition);
	}
	
	private List<Transition> init(List<Transition> lst,Orbit orb)
	{
		
		//busca orbitas//
		Collections.sort(lst,new TransitionComparator());
		//System.out.println("QQ DEU:");
		//printLstTransicoes(lst);
		List<Orbit> lstO = buscaOrbitasMaximais(lst);
		for(Orbit o: lstO){
			o.setParentOrbit(orb);
			o.printOrbita();
			addBackEdge(o.getBackEdge());
			o.setSO(lst);
			System.out.println("Transicoes: ");
			printLstTransicoes(lst);
			System.out.println();
			//copyTo(init(lst,o),lst);
			init(lst,o);
		}
		
		//System.out.println("Aqui");
		
		System.out.println("Reduzir: ");
		//orb.printOrbita();
		//printLstTransicoes(lst);
		Collections.reverse(lst);
		lst = reduce(lst,orb);
		return lst;
		

		//printLstTransicoes(lstO);
		
		/*for(Transition t: lstT)
		{
			int x = new Integer(t.getEstadoAtual());
			int y = new Integer(t.getEstadoNovo());
			if(x>=y)//Back-Edge
			{
				//Verifica se é maximal//
				
			}
		}*/
	}
	
	private List<Transition> reduce(List<Transition> lstT,Orbit orb)
	{
		System.out.println("Grafo sem orbita:");
		if(orb!=null) orb.printOrbita();
		print(lstT);
		Orbit oclone = null;
		if(orb==null){
			orb = new Orbit(null);
			oclone = null;
		}else{
			oclone = orb.clone();
		}
		while(!isEnd(lstT,oclone))
		{
			regra1(lstT,orb);
			regra2(lstT,orb);
			regra3(lstT,orb);
			
			
			print(lstT);
		}
		return lstT;
	}
	private boolean isEnd(List<Transition> lstT,Orbit orb)
	{
		if(lstT.size()<=1)
			return true;
		//orb.printOrbita();
		if(orb==null)
			return false;
		
		if(orb.size()<=1)
			return true;
		
		for(Transition t: lstT)
		{
			if(orb.contains(t.getEstadoAtual()) || orb.contains(t.getEstadoNovo())){
				return false;
			}
		}
		/*if()
			return true;
			*/
		return true;
	}
	
	private void printOrbitas(List<Orbit> lstO)
	{
		for(Orbit o: lstO)
			o.printOrbita();
	}
	
	private List<Orbit> buscaOrbitasMaximais(List<Transition> lstTransicao)
	{
		List<Orbit> lst = new LinkedList();
		String tt = "-1";
		for(Transition t: lstTransicao)
		{
			try{
			int x = new Integer(t.getEstadoAtual());
			int y = new Integer(t.getEstadoNovo());
			int ti = new Integer(tt);
			if(x>=y && (x<ti || ti==-1))//Back-Edge
			{
				tt = t.getEstadoNovo();
				lst.add(buscaOrbita(t,lstTransicao));
			}
			}catch(Exception e){}
		}
		return lst;
	}
	
	private Orbit buscaOrbita(Transition backEdge,List<Transition> lstT)
	{
		Orbit orb = new Orbit();
		orb.setBackEdge(backEdge);
		orb.add(backEdge.getEstadoAtual());
		orb.add(backEdge.getEstadoNovo());
		for(Transition t: lstT)
		{
			if(t.getEstadoAtual().equals(backEdge.getEstadoNovo()))
			{
				System.out.println(t.getEstadoNovo()+" "+backEdge.getEstadoAtual());
				List<String> l = buscaCaminho(t.getEstadoNovo(),backEdge.getEstadoAtual(),lstT);
				
				for(String n:l)
					orb.add(n);
			}
		}
		return orb;
	}
	
	private List<String> buscaCaminho(String atual,String destino,List<Transition> lstT)
	{
		List<String> lstRet = new LinkedList();
		//System.out.println("AUAUAU: "+atual+" : "+destino);
		for(Transition t: lstT)
		{
			if(t.getEstadoAtual().equals(atual))
			{
				if(t.getEstadoNovo().equals(destino)){
						lstRet.add(t.getEstadoAtual());
				}else if(new Integer(t.getEstadoAtual()) < new Integer(t.getEstadoNovo())){
					List<String> ltmp = buscaCaminho(t.getEstadoNovo(),destino,lstT); 
					copyTo(ltmp,lstRet);
					if(ltmp.size()>0)
						lstRet.add(t.getEstadoAtual());
				}
			}
		}
		return lstRet;
	}
	
	private void copyTo(List lstSource,List lstTarget)
	{
		for(Object o:lstSource)
			lstTarget.add(o);
	}
	
	/*private String[] buscaOrbitas()
	{
		for(Transition t: lstT)
		{
			int x = new Integer(t.getEstadoAtual());
			int y = new Integer(t.getEstadoNovo());
			if(x>=y)//Back-Edge
			{
				//Verifica se é maximal//
				
			}
		}		
	}*/
	
	private List<Orbit> getOrbitas()
	{
		List<Orbit> lstO = new LinkedList();
		
		
		return lstO;
	}
	
	/*private void init()
	{
		tiraOrbita();
		System.out.println("Grafo sem orbita:");
		print();
		while(!isEnd())
		{
			regra3();
			regra2();
			regra1();
			print();
		}
	}*/
	
	/*private void tiraOrbita()
	{
		List<Transition> lstRemover = new LinkedList();
		lstOrbitas = new LinkedList();
		for(Transition t: lstT)
		{
			int x = new Integer(t.getEstadoAtual());
			int y = new Integer(t.getEstadoNovo());
			if(x>=y){
				lstRemover.add(t);
				addOrbita(t);
				
			}
		}
		for(Transition t: lstRemover)
			lstT.remove(t);
		//lstOrbitas = lstRemover;
	}*/
	private void addOrbita(Transition t)
	{
		//System.out.println("O: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
		for(Transition n: lstOrbitas)
		{
			//System.out.println("\tN: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
			String x1 = n.getEstadoAtual().trim();
			String y1 = n.getEstadoNovo().trim();

			String x2 = t.getEstadoAtual().trim();
			String y2 = t.getEstadoNovo().trim();
			if(x1.equals(x2) && y1.equals(y2))
				return;
		}
		System.out.println("Orbita: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
		lstOrbitas.add(t);
	}
	
	private void print(List<Transition> lstT)
	{
		for(Transition t: lstT)
		{
			System.out.println(t.getEstadoAtual()+" -> "+t.getEstadoNovo());
		}
		System.out.println("-------------------------------------------");
	}
	

	
	private void regra1(List<Transition> lstT,Orbit orb)
	{
		List<Transition> lstAplicar = new LinkedList();
		for(Transition t: lstT)
		{
			String x = t.getEstadoAtual();
			if(!x.equals("0"))
			{
				String y = t.getEstadoNovo();
				String[] anty = ant(y,lstT);
				String[] sucx = suc(x,lstT);
				if(orb.contains(t.getEstadoAtual()) && orb.contains(t.getEstadoNovo()))
				if( (anty.length==1 && anty[0].equals(x)) && (sucx.length==1 && sucx[0].equals(y)))
				{
					lstAplicar.add(t);
					System.out.println("Aplicar R1: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
					break;
					//System.out.println(t.getEstadoAtual()+" -> "+t.getEstadoNovo());
				}
			}
		}
		
		for(Transition t: lstAplicar)
		{
			String x = t.getEstadoAtual();
			String y = t.getEstadoNovo();
			//Transition tt = new Transition(antx)
			String tt = "("+x+"."+y+")";
			
			//if(orbAnterior!=null)
			//	orbAnterior.ajuste(x,y,tt);
			orb.ajuste(x,y,tt);
			
			String antx[] = ant(x,lstT);
			for(String ax: antx)
			{
				remover(ax,x,lstT);
				addT(ax,tt,lstT);
			}
			String sucy[] = suc(y,lstT);
			for(String sy: sucy)
			{
				remover(y,sy,lstT);
				addT(tt,sy,lstT);
			}
			lstT.remove(t);
		}
	}
	private void regra2(List<Transition> lstT,Orbit orb)
	{
		List<Transition> lstAplicar = new LinkedList();
		for(Transition tx: lstT)
		{
			String x = tx.getEstadoAtual();
			if(orb.contains(x))
			for(Transition ty: lstT)
			{
				String y = ty.getEstadoAtual();
				if(!x.equals(y) && orb.contains(y))
				{
					if(equals(ant(x,lstT),ant(y,lstT)) && equals(suc(x,lstT),suc(y,lstT)))
					{
						if(!in(lstAplicar,x,y))
						{
							System.out.println("Aplicar R2: "+x+" : "+y);
							//System.out.println(x+" -> "+y);
							lstAplicar.add(new Transition(x,y));
						}
					}
				}
			}
		}
		
		for(Transition t: lstAplicar)
		{
			String x = t.getEstadoAtual();
			String y = t.getEstadoNovo();
			
			//String tt = "("+x+")+("+y+")";
			String tt = "("+t.getEstadoAtualParenteses()+"|"+t.getEstadoNovoParenteses()+")";
			
			//if(orbAnterior!=null)
			//	orbAnterior.ajuste(x,y,tt);
			orb.ajuste(x,y,tt);
			
			String[] antx = ant(x,lstT);
			for(String ax: antx)
			{
				remover(ax,x,lstT);
				addT(ax,tt,lstT);
			}
			String[] sucx = suc(x,lstT);
			for(String sx: sucx)
			{
				remover(x,sx,lstT);
				addT(tt,sx,lstT);
			}
			
			String[] anty = ant(y,lstT);
			for(String ay: anty)
			{
				remover(ay,y,lstT);
				addT(ay,tt,lstT);
			}
			String[] sucy = suc(y,lstT);
			for(String sy: sucy)
			{
				remover(y,sy,lstT);
				addT(tt,sy,lstT);
			}			
		}
	}
	private boolean in(List<Transition> lst,String x, String y)
	{
		for(Transition t: lst)
		{
			if(t.getEstadoAtual().equals(x) && t.getEstadoNovo().equals(y))
				return true;
			if(t.getEstadoAtual().equals(y) && t.getEstadoNovo().equals(x))
				return true;
		}
		return false;
	}
	
	//Regra3
	private boolean regra3(List<Transition> lstT,Orbit orb)
	{
		List<Transition> lstAplicar = new LinkedList();
		
		for(Transition t: lstT)
		{
			String x = t.getEstadoAtual();
			String y[] = ant(x,lstT);
			String sucx[] = suc(x,lstT);
			if(sucx.length==1  && contido(sucx,sucs(y,lstT)))//Aplicar regra 3
			{
				if(orb.contains(t.getEstadoAtual()) && orb.contains(t.getEstadoNovo())){
					System.out.println("Aplicar R3: "+t.getEstadoAtual()+" -> "+t.getEstadoNovo());
					lstAplicar.add(t);
				}
			}
			//System.out.println("UE");
		}
		
		for(Transition t: lstAplicar)
		{
			String x = t.getEstadoAtual();
			String anttx[] = ant(x,lstT);
			String succx[] = suc(x,lstT);
			boolean b = false;
			for(int i=0;i<anttx.length;i++)
			{
				String antx = anttx[i];
				//if(succx.length>i)
				{
					remover(antx,succx[0],lstT);
					b = true;
				}
			}
			if(b){
				setOpcional(x,lstT);
				orb.ajuste(x,x+"?");
				System.out.println("OPcional x"+x);
				//if(orbAnterior!=null)
				//	orbAnterior.ajuste(x,x+"?");
			}
		}
		
		
		return lstAplicar.size()>0;
	}
	
	private void setOpcional(String estado,List<Transition> lstT)
	{
		for(Transition t: lstT)
		{
			if(t.getEstadoAtual().equals(estado) && !t.getEstadoAtual().endsWith("?"))
				t.setEstadoAtual(estado+"?");
			
			if(t.getEstadoNovo().equals(estado) && !t.getEstadoNovo().endsWith("?"))
				t.setEstadoNovo(estado+"?");
		}
	}
	
	private void remover(String x,String y,List<Transition> lstT)
	{
		List<Transition> lstRemover = new LinkedList();
		for(Transition t: lstT)
		{
			if(t.getEstadoAtual().equals(x) && t.getEstadoNovo().equals(y))
				lstRemover.add(t);
		}
		for(Transition t: lstRemover)
			lstT.remove(t);
	}
	
	private String[] ant(String x,List<Transition> lstT)
	{
		List<String> l = new LinkedList();
		for(Transition t: lstT)
		{
			if(t.getEstadoNovo().equals(x))
				l.add(t.getEstadoAtual());
		}
		return l.toArray(new String[l.size()]);
	}
	private String[] sucs(String x[],List<Transition> lstT)
	{
		String ret[] = new String[0];
		for(String t: x)
		{
			ret = Common.union(ret,suc(t,lstT));
		}
		return ret;
	}
	private String[] suc(String x,List<Transition> lstT)
	{
		List<String> l = new LinkedList();
		for(Transition t: lstT)
		{
			if(t.getEstadoAtual().equals(x))
				l.add(t.getEstadoNovo());
		}
		return l.toArray(new String[l.size()]);
	}
	private boolean contido(String[] a, String c[])
	{
		boolean tmp = false;
		for(String x: a)
		{
			for(String y: c)
			{
				//System.out.println(x+" - "+y);
				if(x.equals(y))
				{
					
					tmp = true;
					break;
				}
			}
			if(!tmp)
				return false;
			tmp = false;
		}
		return true;
	}
	private boolean pertence(String elemento,String set[])
	{
		for(String e: set)
		{
			if(e.equals(elemento))
				return true;
		}
		return false;
	}
	private boolean equals(String[] ant1,String[] ant2)
	{
		if(ant1.length!=ant2.length)
			return false;
		
		for(int i=0;i<ant1.length;i++)
			if(!ant1[i].equals(ant2[i]))
				return false;
		return true;
	}
	
	
	////////
	
	
	/*private void regra3tmp()
	{
		for(Transition t: lstT)
		{
			String antX[] = ant(t.getEstadoAtual(),null);
			String antY[] = ant(t.getEstadoNovo(),t.getEstadoAtual());
			if(equals(antX,antY) && antX.length==1)
			{
				System.out.println(t.getEstadoAtual()+" -> "+t.getEstadoNovo());
			}
		}
	}
	
	private String[] ant(String x,String menos)
	{
		List<String> l = new LinkedList();
		for(Transition t: lstT)
		{
			if(t.getEstadoNovo().equals(x) && (menos==null || !menos.equals(t.getEstadoAtual())))
				l.add(t.getEstadoAtual());
		}
		
		return l.toArray(new String[l.size()]);
	}*/
	
	/*private boolean equals(String[] ant1,String[] ant2)
	{
		if(ant1.length!=ant2.length)
			return false;
		
		for(int i=0;i<ant1.length;i++)
			if(!ant1[i].equals(ant2[i]))
				return false;
		return true;
	}*/
	
	
	private List<Transition> tmpPreencheTransicoes()
	{
		List<Transition> lstT = new LinkedList();
		addT("0","1",lstT);
		addT("1","2",lstT);
		addT("1","3",lstT);
		addT("2","2",lstT);
		addT("2","3",lstT);
		addT("2","4",lstT);
		addT("3","3",lstT);
		addT("3","2",lstT);
		addT("3","4",lstT);
		addT("4","1",lstT);
		addT("4","5",lstT);
		//Collections.reverse(lstT);
		/*addT("0","1");
		addT("0","2");
		addT("0","3");
		addT("0","4");
		addT("1","2");
		addT("1","3");
		addT("1","4");
		addT("2","3");
		addT("2","4");
		addT("3","7");
		addT("4","5");
		addT("4","6");
		addT("5","6");
		addT("6","8");
		addT("6","9");
		addT("7","8");
		addT("7","9");
		addT("8","9");
		addT("9","10");
		addT("9","11");
		addT("10","11");*/
		return lstT;
	}
	private void addT(String atual,String novo,List<Transition> lstT)
	{
		for(Transition t: lstT)
		{
			if(t.getEstadoAtual().equals(atual) && t.getEstadoNovo().equals(novo))
				return;
		}
		lstT.add(new Transition(atual,novo));
	}
	
	
	private void print(String t[])
	{
		for(String a: t)
		{
			System.out.println(a);
		}
	}
	

	public String getExp()
	{
		Transition t = lstTransition.get(0);
		String exp = colocarFechos(t.getEstadoNovo());
		System.out.println(exp);
		String s[] = aut.getSigma();
		int cont=1;
		for(String a:s)
		{
			exp = exp.replaceAll(""+cont,a);
			cont++;
		}
		exp = exp.replaceAll(""+cont,"#");
		return exp;
	}
	
	private String colocarFechos(String exp)
	{
		
		System.out.println("fechos: ");
		StringBuffer ret = new StringBuffer(exp);
		for(Transition t: lstOrbitas)
		{
			
			String x = t.getEstadoAtual();
			String y = t.getEstadoNovo();
			//System.out.println("Inserindo: "+x+" -> "+y);
			
			int ini = getInitFecho(exp,y);
			//ret.insert(ini,"(");
			

			exp = ret.toString();
			
			int fim = getEndFecho(exp,x);
			//if(fim!=ini+1)
				
				
			//System.out.println("Fim: "+fim);
			int f1 = getInitFecho(exp,x)+1;
			if(fim==f1)
				ret.insert(fim,"+");
			else{
				if(exp.toCharArray()[fim-1]=='?'){
					ret.delete(fim-1,fim);
					ret.insert(fim-1,"*");
				}else{
					ret.insert(fim,"+");
				}
			}
				
			
			System.out.println(x+" -> "+y);
			exp = ret.toString();
			//System.out.println(exp);
		}
		return ret.toString();
	}
	
	private int getInitFecho(String exp,String estado)
	{
		return exp.indexOf(estado);
	}
	
	private int getEndFecho(String exp,String estado)
	{
		char[] tmp = exp.toCharArray();
		int i = exp.indexOf(estado);
		int j =i;
		int n = i;
		boolean b = false;
		for(j=i;j<tmp.length;j++)
		{
			//b = true;
			
			if(isOperator(tmp[j]))
				break;
			n++;
		}
		//if(b)
			return n;
	}
	private boolean isOperator(char c)
	{
		if(c=='.' || c=='|')
			return true;
		return false;
	}
	
	private void printLstTransicoes(List<Transition> lst)
	{
		for(Transition t: lst)
		{
			System.out.println(t.getEstadoAtual()+" -> "+t.getEstadoNovo());
		}
	}
	private void addBackEdge(Transition backEdge)
	{
		lstOrbitas.add(backEdge);
	}	
	
	public static void main(String args[])
	{
		try{
			String t = "(((a)*.(b?))*)*|(((d)*.(e?).(f)+)*)*";
			/*Glushkov g = new Glushkov("|(a , .(a,b))");
			Automato a = g.gerarAutomato();
			*/
			//List<Transition> lst = a.getLstTransition();
			new GerarExp();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
