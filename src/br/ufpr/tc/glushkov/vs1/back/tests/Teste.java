/*
 * Created on 31/05/2005
 *
 */
package br.ufpr.tc.glushkov.vs1.back.tests;

import java.util.LinkedList;
import java.util.List;

import br.ufpr.tc.glushkov.vs1.Automato;
import br.ufpr.tc.glushkov.vs1.MakeAutomato;
import br.ufpr.tc.glushkov.vs1.PrefixExp;
import br.ufpr.tc.glushkov.vs1.Transition;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Teste 
{
	private List<Transition> lstT;
	public Teste(Automato aut)throws Exception
	{
		//lstT = aut.getLstTransition();
		tmpPreencheTransicoes();
		//print();
		//print(ant("1"));
		int cont = 0;
		//while(!isEnd())
		while(cont<10)
		{
			//regra3();
			//regra2();
			regra1();
//			boolean b = true;
//			while(b)
//			{
//				System.out.println("A");
//				b = regra3();
//			}
			/*regra3();
			regra1();
			regra2();
			regra3();
			regra1();
			regra1();
			regra3();
			*/
			
			
			cont++;
			print();
			//break;
		}
		
	}
	
	private void print()
	{
		for(Transition t: lstT)
		{
			System.out.println(t.getEstadoAtual()+" -> "+t.getEstadoNovo());
		}
		System.out.println("-------------------------------------------");
	}
	
	private boolean isEnd()
	{
		return lstT.size()==1;
	}
	
	private void regra1()
	{
		List<Transition> lstAplicar = new LinkedList();
		for(Transition t: lstT)
		{
			String x = t.getEstadoAtual();
			String y = t.getEstadoNovo();
			String[] anty = ant(y);
			String[] sucx = suc(x);
			if( (anty.length==1 && anty[0].equals(x)) && (sucx.length==1 && sucx[0].equals(y)))
			{
				lstAplicar.add(t);
				//System.out.println(t.getEstadoAtual()+" -> "+t.getEstadoNovo());
			}
		}
		
		for(Transition t: lstAplicar)
		{
			String x = t.getEstadoAtual();
			String y = t.getEstadoNovo();
			//Transition tt = new Transition(antx)
			String tt = x+"."+y;
			String antx[] = ant(x);
			for(String ax: antx)
			{
				remover(ax,x);
				addT(ax,tt);
			}
			String sucy[] = suc(y);
			for(String sy: sucy)
			{
				remover(y,sy);
				addT(tt,sy);
			}
			lstT.remove(t);
		}
	}
	private void regra2()
	{
		List<Transition> lstAplicar = new LinkedList();
		for(Transition tx: lstT)
		{
			String x = tx.getEstadoAtual();
			for(Transition ty: lstT)
			{
				String y = ty.getEstadoAtual();
				if(!x.equals(y))
				{
					if(equals(ant(x),ant(y)) && equals(suc(x),suc(y)))
					{
						if(!in(lstAplicar,x,y))
						{
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
			
			String tt = "("+x+")+("+y+")";
			
			String[] antx = ant(x);
			for(String ax: antx)
			{
				remover(ax,x);
				addT(ax,tt);
			}
			String[] sucx = suc(x);
			for(String sx: sucx)
			{
				remover(x,sx);
				addT(tt,sx);
			}
			
			String[] anty = ant(y);
			for(String ay: anty)
			{
				remover(ay,y);
				addT(ay,tt);
			}
			String[] sucy = suc(y);
			for(String sy: sucy)
			{
				remover(y,sy);
				addT(tt,sy);
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
	private boolean regra3()
	{
		List<Transition> lstAplicar = new LinkedList();
		List<Transition> lstRemover = new LinkedList();
		
		for(Transition t: lstT)
		{
			String x = t.getEstadoAtual();
			String ty[] = ant(x);
			String y = t.getEstadoNovo();
			String[] sucx = suc(x);
			if(sucx.length==1 && ty.length==1 && contido(sucx,suc(ty[0])))//Aplicar regra 3
			{
				//System.out.println(t.getEstadoAtual()+" -> "+t.getEstadoNovo());
				lstAplicar.add(t);
				
			}else if(sucx.length==1){
				 //&& contido(sucx,suc(ty[0]))
				boolean remover = false;
				for(String ax: ty)
				{
					if(contido(sucx,suc(ax)))
					{
						remover=true;
					}else{
						remover=false;
						break;
					}
				}
				if(remover){
					for(String ax: ty)
					{
						System.out.println("Remover: "+ax+" -> "+sucx[0]);
						if(!(ax.equals("6") || ax.equals("7")))
						lstRemover.add(new Transition(ax,sucx[0]));
					}
				}
			}
			//System.out.println("UE");
		}
		
		for(Transition t: lstAplicar)
		{
			String x = t.getEstadoAtual();
			String y = t.getEstadoNovo();
			String antx = ant(x)[0];
			
			String tt = x+"?."+y; 
			addT(antx,tt);
			String sucx[] = suc(x);

			String sucxt[] = suc(sucx[0]);
			if(sucxt.length>0)
			{
				for(String sc: sucxt)
				{
					remover(sucx[0],sc);
					addT(tt,sc);
				}
			}
			remover(antx,x);
			remover(antx,y);
			remover(x,y);
		}
		
		for(Transition t: lstRemover)
		{
			System.out.println("Removendo: "+t.getEstadoAtual());
			remover(t.getEstadoAtual(),t.getEstadoNovo());
		}
		
		return lstAplicar.size()>0 || lstRemover.size()>0;
	}
	
	private void remover(String x,String y)
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
	
	private String[] ant(String x)
	{
		List<String> l = new LinkedList();
		for(Transition t: lstT)
		{
			if(t.getEstadoNovo().equals(x))
				l.add(t.getEstadoAtual());
		}
		return l.toArray(new String[l.size()]);
	}
	private String[] suc(String x)
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
	
	
	private void tmpPreencheTransicoes()
	{
		lstT = new LinkedList();
		addT("0","1");
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
		addT("10","11");
	}
	private void addT(String atual,String novo)
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
	
	
	public static void main(String args[])
	{
		try{
			MakeAutomato g = new MakeAutomato(new PrefixExp("|(a , .(a,b))"));
			Automato a = g.gerarAutomato();
			//List<Transition> lst = a.getLstTransition();
			new Teste(a);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
