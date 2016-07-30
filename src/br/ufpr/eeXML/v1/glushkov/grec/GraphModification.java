/*
 * Created on 17/10/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov.grec;

import br.ufpr.eeXML.v1.glushkov.regex.Regex;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class GraphModification 
{
	private Regex snl;
	private Regex snr;
	private Regex snew;
	private boolean executed;
	
	
	public Regex getSnew() {
		return snew;
	}
	public void setSnew(Regex snew) {
		this.snew = snew;
	}

	public Regex getSnl() {
		return snl;
	}
	public Regex getSnr() {
		return snr;
	}
	public void setSnl(Regex snl) {
		this.snl = snl;
	}
	public void setSnr(Regex snr) {
		this.snr = snr;
	}
	public boolean isExecuted() {
		return executed;
	}
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
}