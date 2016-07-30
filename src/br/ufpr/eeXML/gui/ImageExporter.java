/*
 * Created on 08/06/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class ImageExporter 
{
	public ImageExporter(JComponent parent,BufferedImage img)throws Exception
	{
		export(parent,img);
	}
	
	private void export(JComponent parent,BufferedImage img)throws Exception
	{
		JFileChooser fc = new JFileChooser();
		if(fc.showSaveDialog(parent)==JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile();
			if(file!=null){
				ImageIO.write(img,"png",file);
				JOptionPane.showMessageDialog(parent,"XML Tree exported with success!");
				return;
			}
		}else{
			return;
		}
		JOptionPane.showMessageDialog(parent,"An error ocurred in export!");
		//
	}
}
