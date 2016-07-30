/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.tc.test.treeautomata;

import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class EntityResolverParser implements EntityResolver
{

	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException 
	{
		return null;
	}
	
}
