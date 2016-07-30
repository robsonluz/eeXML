/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.tc.test.xml.sax;
import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

public class Teste extends DefaultHandler
{
    public static void main(String argv[])
    {
    	Pos p = new Pos();
    	Pos p0 = new Pos(p,"0");
    	Pos p00 = new Pos(p0,"0");
    	Pos p01 = new Pos(p0,"1");
    	Pos p010 = new Pos(p01,"0");
    	//System.out.println(p010);
    	
    	
    	String path = "src/br/ufpr/tc/test/xml/sax/";
    	argv = new String[]{path+"teste.xml"};
        
        DefaultHandler handler = new Teste();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try{
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse( new File(argv[0]), handler);

        }catch(Throwable t){
            t.printStackTrace();
        }
    }

    static private Writer  out;

    private Pos pos = new Pos();
    private Pos posAnt = pos;

    private Pos getPos()
    {
    	return pos;
    }
    
    private void incrementaP()
    {
    	
    }
    
    public void startDocument()throws SAXException
    {
    }

    public void endDocument()throws SAXException
    {
    }

    public void startElement(String namespaceURI,String lName,String qName,Attributes attrs)throws SAXException
    {
    	pos.setName(qName);
    	System.out.println(pos);
    	//System.out.println(qName+"("+pos+")");
    	
    	posAnt = pos;
    	pos = new Pos(pos,"0");
    	data = true;
    	
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                String aName = attrs.getLocalName(i); // Attr name 
                if ("".equals(aName)) aName = attrs.getQName(i);
                	
                	//pos.setElement()
                	pos.setName(aName);
                	pos.setAttribute(true);
                	//System.out.println("@"+aName+"("+pos+")");
                	System.out.println(pos);
                	
                	Pos pData = new Pos(pos,"0");
                	pData.setName("data");
                	pData.setData(true);
                	//System.out.println("-data("+pData+")");
                	System.out.println(pData);
                	
                	pos = new Pos(pos.getParent(),""+(i+1));
                	//pos = new Pos(pos.getParent(),""+(i+1));
                	
            }
        }

    }

    public void endElement(String namespaceURI,String sName,String qName)throws SAXException
    {
    	//System.out.println("END:"+qName);
	    if(posAnt.getPos()!=null){
	    	int n = Integer.parseInt(posAnt.getPos());
	    	pos = new Pos(posAnt.getParent(),""+(n+1));
	    	posAnt = pos.getParent();
    	}
    	//pos = pos + "0";
    }
    boolean data = false;

    public void characters(char buf[], int offset, int len)throws SAXException
    {
        String s = new String(buf, offset, len);
        s = s.trim();
        //if(s!=null && s.trim().length()>0)
        if(data && s.length()>0){
        	int n = Integer.parseInt(pos.getPos());
        	Pos p = new Pos(posAnt,""+(n));
        	p.setData(true);
        	p.setName("data");
        	//System.out.println("data("+p+")");
        	System.out.println(p);
        	//System.out.println(s);
        }
        data = false;
        //emit(s);
    }
}
