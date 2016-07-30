/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.eeXML.v1.treeautomata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class DTDToTreeAutomata 
{
	public static TreeAutomata transform(String DTDFile)throws Exception
	{
    	TreeAutomata ta = new TreeAutomata();
    	XMLReader reader = XMLReaderFactory.createXMLReader();
    	reader.setProperty("http://xml.org/sax/properties/declaration-handler",new DTDDeclHandler(ta,true));
    	reader.parse(createDTDInputSource(DTDFile));
    	
    	reader = XMLReaderFactory.createXMLReader();
    	reader.setProperty("http://xml.org/sax/properties/declaration-handler",new DTDDeclHandler(ta,false));
    	reader.parse(createDTDInputSource(DTDFile));
    	
    	ta.initTreeAutomata();

		return ta;
	}
	
	private static InputSource createDTDInputSource(String DTDFile)throws Exception
	{
		StringBuffer sb = new StringBuffer();
//		ByteArrayOutputStream bout = new ByteArrayOutputStream();
//		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(bout));
		sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		FileInputStream fin = new FileInputStream(DTDFile);
		BufferedReader in = new BufferedReader(new InputStreamReader(fin));
		String st = null;
		while((st=in.readLine())!=null)
		{
			sb.append(st+"\r\n");
		}
		sb.append("<root></root>");
		//System.out.println(sb.toString());
		ByteArrayInputStream bin = new ByteArrayInputStream(sb.toString().getBytes());
		return new InputSource(bin);
	}
}