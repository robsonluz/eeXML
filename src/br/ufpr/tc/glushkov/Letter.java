/*
 * Created on 05/05/2005
 *
 */
package br.ufpr.tc.glushkov;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class Letter 
{
	private int pos;
	private char letter;
	
	public Letter(int pos, char letter) 
	{
		this.pos = pos;
		this.letter = letter;
	}
	
	public char getLetter() {
		return letter;
	}
	public void setLetter(char letter) {
		this.letter = letter;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
}