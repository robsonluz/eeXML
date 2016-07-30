/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.tc.test.xml.sax;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

public class Teste2 extends DefaultHandler
{
	private List<Pos> lstData;
	
    public static void main(String argv[])
    {

    	String path = "src/br/ufpr/tc/test/xml/sax/";
    	argv = new String[]{path+"teste.xml"};
        
        Teste2 handler = new Teste2();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try{
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse( new File(argv[0]), handler);

        }catch(Throwable t){
            t.printStackTrace();
        }
        System.out.println("\n\n\n");
        for(Pos pos:handler.lstData)
        {
        	//System.out.println(pos);
        	handler.printBottomUP(pos);
        	System.out.println("--------");
        	
        }
    }
    
    public void printBottomUP(Pos pos)
    {
    	if(pos!=null){
    		System.out.println(pos);
    		printBottomUP(pos.getParent());
    	}
    }
    
    public Teste2()
    {
    	lstData = new LinkedList();
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
                	lstData.add(pData);
                	
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
        	lstData.add(p);
        	//System.out.println("data("+p+")");
        	System.out.println(p);
        	//System.out.println(s);
        }
        data = false;
        //emit(s);
    }
}
