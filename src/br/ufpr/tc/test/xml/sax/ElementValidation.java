/*
 * Created on 25/01/2006
 *
 */
package br.ufpr.tc.test.xml.sax;

import java.util.LinkedList;
import java.util.List;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class ElementValidation 
{
	private List<Pos> lstElements;
	private List<Pos> lstAttributes;
	private String name;
	
	public ElementValidation(String name)
	{
		lstElements = new LinkedList();
		lstAttributes = new LinkedList();
		this.name = name;
	}
	public void addElement(Pos c)
	{
		for(Pos p:lstElements)
		{
			if(p.getPos()!=null && p.getPos().equals(c.getPos()))
				return;
		}
		lstElements.add(c);
	}
	
	public void addAttribute(Pos c)
	{
		for(Pos p:lstAttributes)
		{
			if(p.getPos().equals(c.getPos()))
				return;
		}
		lstAttributes.add(c);
	}
	public List<Pos> getLstAttributes() {
		return lstAttributes;
	}
	public List<Pos> getLstElements() {
		return lstElements;
	}
	public String getName() {
		return name;
	}
	public void setLstAttributes(List<Pos> lstAttributes) {
		this.lstAttributes = lstAttributes;
	}
	public void setLstElements(List<Pos> lstElements) {
		this.lstElements = lstElements;
	}
	public void setName(String name) {
		this.name = name;
	}
}