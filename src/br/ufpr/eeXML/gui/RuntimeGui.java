/*
 * Created on 17/04/2006
 *
 */
package br.ufpr.eeXML.gui;

import javax.swing.JFrame;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class RuntimeGui 
{
	private static RuntimeGui instance;
	private JFrame mainFrame;
	
	private RuntimeGui()
	{
	}
	
	public static RuntimeGui getInstance()
	{
		if(instance==null)
			instance = new RuntimeGui();
		return instance;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
}