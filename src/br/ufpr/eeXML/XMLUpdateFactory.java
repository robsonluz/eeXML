/*
 * Created on 30/11/2005
 *
 */
package br.ufpr.eeXML;

import br.ufpr.eeXML.v1.XMLUpdate;
import br.ufpr.eeXML.v1.XMLUpdateInsertElement;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class XMLUpdateFactory 
{
	private static XMLUpdateFactory instance;
	
	private XMLUpdateFactory()
	{
	}
	
	public static XMLUpdateFactory getInstance()
	{
		if(instance==null)
			instance = new XMLUpdateFactory();
		return instance;
	}
	
	public XMLUpdate getXMLUpdate(String operator)throws NoSuchXMLUpdateException
	{
		try{
			//if(operator.equals("insert_element")) return new XMLUpdateInsertElement();
		}catch(Exception e){}
		throw new NoSuchXMLUpdateException();
	}
}