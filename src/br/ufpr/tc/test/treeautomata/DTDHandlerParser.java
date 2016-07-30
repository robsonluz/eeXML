/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.tc.test.treeautomata;

import org.xml.sax.DTDHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class DTDHandlerParser extends DefaultHandler
{

	@Override public void notationDecl(String name, String publicId, String systemId) throws SAXException 
	{
		System.out.println("IRRU");
	}

	@Override public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException 
	{
		System.out.println("IRRU");
	}

	
//	public void notationDecl(String name, String publicId, String systemId) throws SAXException 
//	{
//		
//	}
//
//	public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException 
//	{
//		
//	}
	
}
