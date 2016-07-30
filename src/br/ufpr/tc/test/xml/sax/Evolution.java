/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.tc.test.xml.sax;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import br.ufpr.tc.glushkov.grec.CheckAutomataWord;
import br.ufpr.tc.glushkov.grec.GraphModification;
import br.ufpr.tc.glushkov.grec.MakeExp;
import br.ufpr.tc.glushkov.regex.Exp;
import br.ufpr.tc.glushkov.vs1.AutomataRecognize;
import br.ufpr.tc.glushkov.vs1.Automato;
import br.ufpr.tc.glushkov.vs1.MakeAutomato;
import br.ufpr.tc.test.treeautomata.ExpDTD;
import br.ufpr.tc.test.treeautomata.State;
import br.ufpr.tc.test.treeautomata.TreeAutomata;


/*
 * @autor Robson João Padilha da Luz
 *
 */
public class Evolution 
{
	private List<Pos>lstData;
	private Hashtable<String,ElementValidation> hsElement;
	private TreeAutomata ta;
	
	public Evolution(List<Pos>lstData,TreeAutomata ta)
	{
		this.lstData = lstData;
		this.hsElement = new Hashtable();
		this.ta = ta;
	}
	
	private void addElement(Pos pos,Pos posA)
	{
		if(!pos.isData())
		{
			//System.out.println("Name: "+pos.getName()+"="+pos.getPosSTR());
    		ElementValidation e = getElementValidation(pos);
    		if(posA!=null)
    		{
    			if(posA.isAttribute())
    				e.addAttribute(posA);
    			else
    				e.addElement(posA);
    		}
		}
		if(pos.getParent()!=null)
			addElement(pos.getParent(),pos);
	}
	
	public TreeAutomata evolution()
	{
		Pos posA = null;
        for(Pos pos:lstData)
        {
        	addElement(pos,null);
        }
        boolean valid = true;
        
        Enumeration<String> en = hsElement.keys();
        while(en.hasMoreElements())
        {
        	String pp = en.nextElement();
        	ElementValidation e = hsElement.get(pp);
        	System.out.println("Validando: "+e.getName()+"("+pp+")");
        	State s = ta.getStateByName(e.getName());
        	
        	for(Pos p:e.getLstAttributes())
        	{
        		System.out.println("@"+p.getName());
        	}
        	
        	for(Pos p:e.getLstElements())
        	{
        		System.out.println("-"+p.getName());
        	}
        	
        	boolean v = false;
        	if(s!=null){
	        	v = checkExp(s.getExp(),e.getLstElements(),s);
	        	if(!v){
	        		valid = false;
	        		
	        	}
        	}else{
        		State st = new State();
        		st.setName(e.getName());
        		st.setQName("q_"+e.getName());
        		st.setStrExp("(data),#");
        		ta.add(st);
        		System.out.println(e.getName());
        	}
        	System.out.println("Elementos: "+v);
        	System.out.println("-----------");

        }
		return ta;
	}
	
	private void evoluir(Automato a, Exp exp,List<String> lstS,State s)
	{
		GraphModification gm = CheckAutomataWord.checkAutomataWord(a,lstS);
		if(gm!=null)
		{
			//System.out.println(gm.getSnew());
			try{
				MakeExp me = new MakeExp(a,gm);
				Exp e = me.makeExp();
				String en = prepareExp(e.getExp());
				System.out.println(":::::::"+en);
				s.setStrExp(en);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	private String prepareExp(String exp)
	{
		if(exp!=null)
		{
			exp = tirarParentese(exp);
			exp = exp.replaceAll("\\.",",");
		}
		return exp;
	}
	public String tirarParentese(String exp)
	{
		exp=exp.trim();
		if(exp.startsWith("(") && exp.endsWith(")"))
		{
			int queue = 0;
			for(int i=0;i<exp.length();i++)
			{
				String t = exp.substring(i,i+1);
				if(t.equals("("))
					queue++;
				if(t.equals(")"))
					queue--;
				if(queue==0 && i<exp.length()-1)
				{
					queue = -1;
					break;
				}
			}
			if(queue==0)
				exp = tirarParentese(exp.substring(1,exp.length()-1));
		}
		return exp;
	}
	
	private boolean checkExp(Exp exp,List<Pos> lstP,State s)
	{
		System.out.println("Exp: "+exp.getExp());
		List<String> lstS = new ArrayList(lstP.size());
		for(Pos p:lstP)
		{
			lstS.add(p.getName());
		}
		lstS.add("#");
		Exp eclone = new ExpDTD(exp.getExp());
		MakeAutomato ma = new MakeAutomato(eclone);
		Automato a = ma.gerarAutomato();
		AutomataRecognize ar = new AutomataRecognize(a);
		boolean recognize = ar.recognize(lstS);
		if(!recognize)
		{
			evoluir(a,eclone,lstS,s);
		}
		return recognize;
	}
	
	public ElementValidation getElementValidation(Pos pos)
	{
		ElementValidation e = hsElement.get(pos.getPosSTR());
		if(e==null)
		{
			e = new ElementValidation(pos.getName());
			hsElement.put(pos.getPosSTR(),e);
		}
		return e;
	}
}
