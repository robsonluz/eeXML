/*
 * Created on 09/06/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class EPSExporter 
{
	public EPSExporter()
	{
		
	}
	
	public void export(JComponent parent, JXMLNode root, OptXMLTree options,int width, int height)throws Exception
	{
		JFileChooser fc = new JFileChooser();
		if(fc.showSaveDialog(parent)==JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile();
			if(file!=null){
				PSGr ps = new PSGr(new FileOutputStream(file),parent.getGraphics());
				Graphics2D g2 = ps.create(0,0,width,height);
				root.paint(g2,new Dimension(0,0),options);
				ps.dispose();
//				Document document = new Document();
//				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
//				document.open();
//				
//				int w = width;
//				int h = height;
//				PdfContentByte cb = writer.getDirectContent();
//				PdfTemplate tp = cb.createTemplate(w, h);
//				Graphics2D g2 = tp.createGraphics(w, h);
//				tp.setWidth(w);
//				tp.setHeight(h);
//				root.paint(g2,new Dimension(0,0),options);
//				
//				g2.dispose();
//				cb.addTemplate(tp, 0, 0);
//				
//				document.close();
				JOptionPane.showMessageDialog(parent,"XML Tree exported to PDF with success!");
			}
		}
	}
}