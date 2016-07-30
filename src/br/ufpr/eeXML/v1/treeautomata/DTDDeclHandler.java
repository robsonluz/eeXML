/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.eeXML.v1.treeautomata;

import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;

import br.ufpr.eeXML.v1.glushkov.regex.ExpToRegex;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class DTDDeclHandler implements DeclHandler
{
	private TreeAutomata ta;
	private boolean elementFirst;
	public DTDDeclHandler(TreeAutomata ta, boolean elementFirst) {
		this.ta = ta;
		this.elementFirst=elementFirst;
	}
	
	public void attributeDecl(String eName, String aName, String type, String mode, String value) throws SAXException 
	{
		//System.out.println(eName+":"+type+":"+mode+":"+value);
//		if(eName.equals("class") && aName.equals("name")){
//			System.out.println("aqui");
//		}
		if(!elementFirst){
			State stElement = ta.getElementStateByName(eName);
			
			State state = ta.getAttributeStateByElement(stElement, aName); 
			if(state==null){
				state = new State();
				state.setName(aName);
				state.setQName("q_@"+aName);
				state.setType(State.Type.ATTRIBUTE);
				state.setParentElement(stElement);
				//state.setStrExp("data");
				if(type==null || "CDATA".equals(type)){
					setStrExp(state,"data");
					state.setRegexData(true);
				}else
					setStrExp(state,type);
				ta.add(state);
			}
			
			
			if(stElement!=null)
			{
				if(mode!=null && mode.toUpperCase().equals("#REQUIRED")){
					stElement.getLstComp().add(state.getName());
				}else{
					stElement.getLstOp().add(state.getName());
				}
			}
		}
		//System.out.println("-"+aName+"%"+mode);
	}
	private void setStrExp(State s,String exp)
	{
		//exp = "("+exp+"),#";
		s.setExp(ExpToRegex.convert(new ExpDTD(exp)));
	}

	public void elementDecl(String name, String model) throws SAXException 
	{
		if(elementFirst){
			State state = new State();
			state.setName(name);
			state.setQName("q_"+name);
			state.setType(State.Type.ELEMENT);
			
			model = model.trim();
			if(model.toUpperCase().equals("(#PCDATA)"))
				setStrExp(state,"data");
			else if(model.toUpperCase().equals("(EMPTY)"))
				setStrExp(state,"0");
			else
				setStrExp(state,model);
			
			ta.add(state);
		}
		//System.out.println(":"+name+" -> "+getModel(model));
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