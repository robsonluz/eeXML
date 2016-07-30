/*
 * Created on 08/06/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JToolTip;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class JXMLTreeTool extends JPanel implements ActionListener
{
	private OptXMLTree optXMLTree;
	private JXMLTree xmlTree;
	public JXMLTreeTool(JXMLTree xmlTree,OptXMLTree optXMLTree)
	{
		this.xmlTree = xmlTree;
		this.optXMLTree = optXMLTree;
		initComponents();
	}
	
	private void initComponents()
	{
		setLayout(new BorderLayout());
		JToolBar bar = new JToolBar();
		//bar.setFloatable(false);
		add(bar,BorderLayout.CENTER);
		
		bar.add(createButton("open.gif","open","Open XML file"));
		bar.add(createButton("image.gif","export_image","Export as Image"));
		bar.add(createButton("pdf.gif","export_pdf","Export as PDF"));
		
		bar.add(new JToolBar.Separator());
		
		bar.add(createTButton("color.gif","color","Display colored tree",optXMLTree.isDisplayTreeColor()));
		bar.add(createTButton("positions.gif","positions","Display node positions",optXMLTree.isDisplayPositions()));
		
		bar.add(new JToolBar.Separator());
		
		bar.add(createTButton("adjust.gif","adjust","Automatic adjust",false));
		bar.add(createTButton("expandall.gif","expand","Expand All Nodes",false));
		
		
		bar.add(new JToolBar.Separator());
		bar.add(createLabel("width.gif","Width"));
		bar.add(createSpinner("width",optXMLTree.getWidthDistance(),"Width"));
		bar.add(new JToolBar.Separator());
		bar.add(createLabel("height.gif","Height"));
		bar.add(createSpinner("height",optXMLTree.getHeightDistance(),"Height"));
		
		
		setPreferredSize(new Dimension(-1,30));
	}
	
	
	
	public void actionPerformed(ActionEvent e) 
	{
		String act = e.getActionCommand();
		if(act!=null)
		{
			if(act.equals("color"))
			{
				optXMLTree.setDisplayTreeColor(((JToggleButton)e.getSource()).isSelected());
				xmlTree.repaint();
			}else
			if(act.equals("positions"))
			{
				optXMLTree.setDisplayPositions(((JToggleButton)e.getSource()).isSelected());
				xmlTree.repaint();
			}else
			if(act.equals("export_image"))
			{
				try{
					Dimension size = xmlTree.getSize();
					BufferedImage buf = new BufferedImage(size.width,size.height,BufferedImage.TYPE_INT_RGB);
					Graphics2D g2 = buf.createGraphics();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setColor(Color.WHITE);
					g2.fillRect(0,0,size.width,size.height);
					xmlTree.getRootNode().paint(g2,new Dimension(0,0),optXMLTree);
					new ImageExporter(this,buf);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			if(act.equals("export_pdf"))
			{
				try{
					Dimension size = xmlTree.getSize();
					new PDFExporter().export(this,xmlTree.getRootNode(),optXMLTree,size.width,size.height);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}	
			if(act.equals("open"))
			{
				try{
					Dimension size = xmlTree.getSize();
					new EPSExporter().export(this,xmlTree.getRootNode(),optXMLTree,size.width,size.height);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}	
		}
	}
	
	private void spinnerStateChanged(ChangeEvent e)
	{
		JSpinner sp = (JSpinner) e.getSource();
		if(sp!=null)
		{
			if(sp.getName().equals("width"))
			{
				Integer value = (Integer)sp.getValue();
				if(optXMLTree.getWidthDistance()!=value.intValue())
				{
					optXMLTree.setWidthDistance(value.intValue());
					xmlTree.getRootNode().updateChildsPosition(optXMLTree);
					xmlTree.repaint();
				}
			}else if(sp.getName().equals("height")){
				Integer value = (Integer)sp.getValue();
				if(optXMLTree.getHeightDistance()!=value.intValue())
				{
					optXMLTree.setHeightDistance(value.intValue());
					xmlTree.getRootNode().updateChildsPosition(optXMLTree);
					xmlTree.repaint();
				}
			}

		}
		
	}

	private JLabel createLabel(String image, String tipText)
	{
		ImageIcon img = new ImageIcon("resources/"+image);
		JLabel lbl = new JLabel(img);
		lbl.setOpaque(false);
		lbl.setToolTipText(tipText);
		return lbl;
	}
	
	private JSpinner createSpinner(String name,int current, String tipText)
	{
		JSpinner sp = new JSpinner();
		sp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spinnerStateChanged(e);
			}
		});
		sp.setName(name);
		sp.setValue(new Integer(current));
		Dimension d = new Dimension(40,25);
		sp.setSize(d);
		sp.setPreferredSize(d);
		sp.setMinimumSize(d);
		sp.setMaximumSize(d);
		sp.setToolTipText(tipText);
		return sp;
	}
	
	private JButton createButton(String image, String actionCommand, String tipText)
	{
		ImageIcon img = new ImageIcon("resources/"+image);
		JButton tb = new JButton(img);
		tb.setToolTipText(tipText);
		tb.setActionCommand(actionCommand);
		tb.addActionListener(this);
		return tb;
	}
	private JToggleButton createTButton(String image, String actionCommand, String tipText,boolean selected)
	{
//		File f = new File("resources/"+image);
//		System.out.println("resources/"+image+" : "+f.exists());
		ImageIcon img = new ImageIcon("resources/"+image);
		JToggleButton tb = new JToggleButton(img,selected);
		tb.setToolTipText(tipText);
		tb.setActionCommand(actionCommand);
		tb.addActionListener(this);
		return tb;
	}
}
