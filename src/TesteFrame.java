import java.awt.Graphics;

import javax.swing.JFrame;

/*
 * Created on 07/06/2006
 *
 */
/*
 * @autor Robson João Padilha da Luz
 *
 */
public class TesteFrame extends JFrame 
{
	public TesteFrame()
	{
		setUndecorated(true);
	}
	
	
	
	
	@Override public void paint(Graphics g) {
		//super.paintAll(g);
	}


	

	public static void main(String args[])
	{
		TesteFrame frm = new TesteFrame();
		frm.setSize(200,200);
		frm.setVisible(true);
	}
}
