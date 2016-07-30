/*
 * Created on 09/06/2006
 *
 */
package br.ufpr.eeXML.gui;
import java.awt.*;
import java.awt.image.*;

public class PixelConsumer
implements ImageConsumer
{
	boolean complete = false;
	int xdim;
	int ydim;
	int pix[][];
	
	PixelConsumer(Image picture) {
		int t;
		
		picture.getSource().startProduction(this);
		t = 1000;
		while(t>0 && !complete) {
			try {
				Thread.currentThread().sleep(100);
			} catch (Throwable ex) {
			}
			t -= 100;
		}
	}
	
	public void imageComplete(int param) {
		complete = true;
	}
	
	public void setColorModel(ColorModel param) {
	}
	
	public void setDimensions(int x, int y) {
		xdim = x;
		ydim = y;
		pix = new int[x][y];
	}
	
	public void setHints(int param) {
	}
	
	public void setPixels(int x1, int y1, int w, int h, 
			ColorModel model, byte pixels[], int off, int scansize) {
		int x, y, x2, y2, sx, sy;
		// we're ignoring the ColorModel, mostly for speed reasons.
		x2 = x1+w;
		y2 = y1+h;
		sy = off;
		for(y=y1; y<y2; y++) {
			sx = sy;
			for(x=x1; x<x2; x++) 
				pix[x][y] = pixels[sx++];
			sy += scansize;
		}
	}
	
	public void setPixels(int x1, int y1, int w, int h, 
			ColorModel model, int pixels[], int off, int scansize) {
		int x, y, x2, y2, sx, sy;
		// we're ignoring the ColorModel, mostly for speed reasons.
		x2 = x1+w;
		y2 = y1+h;
		sy = off;
		for(y=y1; y<y2; y++) {
			sx = sy;
			for(x=x1; x<x2; x++) 
				pix[x][y] = pixels[sx++];
			sy += scansize;
		}
	}
	
	public void setProperties(java.util.Hashtable param) {
	}
}
