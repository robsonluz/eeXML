/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.tc.test.treeautomata;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.SchemaFactoryLoader;

import org.xml.sax.DTDHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class Teste 
{
	public static void main(String args[])
	{
		try{
			
	    	String path = "src/br/ufpr/tc/test/treeautomata/";
	    	String file = path+"teste.xml";
	        
	    	
	    	TreeAutomata ta = new TreeAutomata();
	    	XMLReader reader = XMLReaderFactory.createXMLReader();
	    	reader.setProperty("http://xml.org/sax/properties/declaration-handler",new DTDDeclHandler(ta));
	    	//reader.setFeature()
	    	//SchemaFactory.newInstance("DTD").;
	    	
	        //DefaultHandler handler = new DTDHandlerParser();
	        
	        //reader.setDTDHandler(handler);
        	reader.parse(new InputSource(new FileInputStream(new File(file))));
	        System.out.println();
	        ta.initTreeAutomata();
        	ta.printTreeAutomata();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
