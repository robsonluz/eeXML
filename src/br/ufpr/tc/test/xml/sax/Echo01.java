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

public class Echo01 extends DefaultHandler
{
    public static void main(String argv[])
    {
    	String path = "src/br/ufpr/tc/test/xml/sax/";
    	argv = new String[]{path+"slideSample01.xml"};
        if (argv.length != 1) {
            System.err.println("Usage: cmd filename");
            System.exit(1);
        }
        
        // Use an instance of ourselves as the SAX event handler
        DefaultHandler handler = new Echo01();
        // Use the default (non-validating) parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            // Set up output stream
            out = new OutputStreamWriter(System.out, "UTF8");

            // Parse the input
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse( new File(argv[0]), handler);

        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(0);
    }

    static private Writer  out;

    //===========================================================
    // SAX DocumentHandler methods
    //===========================================================

    public void startDocument()throws SAXException
    {
        emit("<?xml version='1.0' encoding='UTF-8'?>");
        nl();
    }

    public void endDocument()throws SAXException
    {
        try {
            nl();
            out.flush();
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }

    public void startElement(String namespaceURI,
                             String lName, // local name
                             String qName, // qualified name
                             Attributes attrs)throws SAXException
    {
        String eName = lName; // element name
        if ("".equals(eName)) eName = qName; // namespaceAware = false
        emit("<"+eName);
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                String aName = attrs.getLocalName(i); // Attr name 
                if ("".equals(aName)) aName = attrs.getQName(i);
                emit(" ");
                emit(aName+"=\""+attrs.getValue(i)+"\"");
            }
        }
        emit(">");
    }

    public void endElement(String namespaceURI,
                           String sName, // simple name
                           String qName  // qualified name
                          )
    throws SAXException
    {
        emit("</"+sName+">");
    }

    public void characters(char buf[], int offset, int len)
    throws SAXException
    {
        String s = new String(buf, offset, len);
        emit(s);
    }

    //===========================================================
    // Utility Methods ...
    //===========================================================

    // Wrap I/O exceptions in SAX exceptions, to
    // suit handler signature requirements
    private void emit(String s)
    throws SAXException
    {
        try {
            out.write(s);
            out.flush();
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }

    // Start a new line
    private void nl()
    throws SAXException
    {
        String lineEnd =  System.getProperty("line.separator");
        try {
            out.write(lineEnd);
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }
}
