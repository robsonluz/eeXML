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
public class Validation 
{
	private List<Pos>lstData;
	private Hashtable<String,ElementValidation> hsElement;
	private TreeAutomata ta;
	
	public Validation(List<Pos>lstData,TreeAutomata ta)
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
	
	public boolean validar()
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
        	
        	if(s!=null){
	        	boolean v = checkExp(s.getExp(),e.getLstElements());
	        	if(!v)
	        		valid = false;
	        	System.out.println("Elementos: "+v);
	        	System.out.println("-----------");
        	}
        }
		return valid;
	}
	
	private boolean checkExp(Exp exp,List<Pos> lstP)
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
		return ar.recognize(lstS);
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
