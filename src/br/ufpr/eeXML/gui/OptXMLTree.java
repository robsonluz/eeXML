/*
 * Created on 08/06/2006
 *
 */
package br.ufpr.eeXML.gui;

import java.io.Serializable;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class OptXMLTree implements Serializable
{
	private boolean displayTreeColor=true;
	private boolean displayPositions=true;
	private int heightDistance=40;
	private int widthDistance=80;
	
	
	public boolean isDisplayPositions() {
		return displayPositions;
	}
	public boolean isDisplayTreeColor() {
		return displayTreeColor;
	}
	public void setDisplayPositions(boolean displayPositions) {
		this.displayPositions = displayPositions;
	}
	public void setDisplayTreeColor(boolean displayTreeColor) {
		this.displayTreeColor = displayTreeColor;
	}
	public int getHeightDistance() {
		return heightDistance;
	}
	public int getWidthDistance() {
		return widthDistance;
	}
	public void setHeightDistance(int heightDistance) {
		this.heightDistance = heightDistance;
	}
	public void setWidthDistance(int widthDistance) {
		this.widthDistance = widthDistance;
	}
	
}
