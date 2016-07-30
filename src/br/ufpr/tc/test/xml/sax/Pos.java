/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.tc.test.xml.sax;
/*
 * @autor Robson João Padilha da Luz
 *
 */
public class Pos 
{
	private Pos parent;
	private String pos;
	private String name;
	
	private boolean attribute;
	private boolean data;
	
	public Pos() 
	{
	}
	
	public Pos(Pos parent, String pos) {
		this.parent = parent;
		this.pos = pos;
	}
	public Pos(String pos) 
	{
		this.pos = pos;
	}

	public Pos getParent() 
	{
		return parent;
	}

	public String getPos() 
	{
		return pos;
	}

	public void setParent(Pos parent) 
	{
		this.parent = parent;
	}

	public void setPos(String pos) 
	{
		this.pos = pos;
	}

	public String getPosSTR()
	{
		if(pos==null)
			return "&";
		if(parent!=null){
			String t = parent.getPosSTR();
			if(!t.equals("&"))
				return t+pos;
		}
		return pos;
		
	}
	
	@Override public String toString() 
	{
		return prepareName()+"("+getPosSTR()+")";
	}
	
	private String prepareName()
	{
		String ret = "";
		if(attribute)
			ret += "@";
		if(data)
			ret += "%";
		ret += name;
		return ret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAttribute() {
		return attribute;
	}

	public boolean isData() {
		return data;
	}

	public void setAttribute(boolean attribute) {
		this.attribute = attribute;
	}

	public void setData(boolean data) {
		this.data = data;
	}
}
