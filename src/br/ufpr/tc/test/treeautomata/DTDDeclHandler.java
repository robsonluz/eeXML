/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.tc.test.treeautomata;

import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class DTDDeclHandler implements DeclHandler
{
	private TreeAutomata ta;
	public DTDDeclHandler(TreeAutomata ta)
	{
		this.ta = ta;
	}
	
	public void attributeDecl(String eName, String aName, String type, String mode, String value) throws SAXException 
	{
		State state = new State();
		state.setName(aName);
		state.setQName("q_"+aName);
		//state.setStrExp("data");
		setStrExp(state,"data");
		ta.add(state);
		
		State stElement = ta.getStateByName(eName);
		if(stElement!=null)
		{
			if(mode!=null && mode.toUpperCase().equals("#REQUIRED")){
				stElement.getLstComp().add(state.getQName());
			}else{
				stElement.getLstOp().add(state.getQName());
			}
		}
		System.out.println("-"+aName+"%"+mode);
	}
	private void setStrExp(State s,String exp)
	{
		exp = "("+exp+"),#";
		s.setStrExp(exp);
	}

	public void elementDecl(String name, String model) throws SAXException 
	{
		State state = new State();
		state.setName(name);
		state.setQName("q_"+name);
		
		model = model.trim();
		if(model.toUpperCase().equals("(#PCDATA)"))
			setStrExp(state,"data");
		else if(model.toUpperCase().equals("(EMPTY)"))
			setStrExp(state,"0");
		else
			setStrExp(state,model);
		
		ta.add(state);
		System.out.println(":"+name+" -> "+getModel(model));
	}

	
	private String getModel(String model)
	{
		return model.trim();
	}
	
	public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException 
	{
	}

	public void internalEntityDecl(String name, String value) throws SAXException 
	{
	}
}