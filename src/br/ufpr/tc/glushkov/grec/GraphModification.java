/*
 * Created on 17/10/2005
 *
 */
package br.ufpr.tc.glushkov.grec;
/*
 * @autor Robson João Padilha da Luz
 *
 */
public class GraphModification 
{
	private String snl;
	private String snr;
	private String snew;
	private boolean executed;
	
	
	public String getSnew() {
		return snew;
	}
	public void setSnew(String snew) {
		this.snew = snew;
	}
	public String getSnl() {
		return snl;
	}
	public void setSnl(String snl) {
		this.snl = snl;
	}
	public String getSnr() {
		return snr;
	}
	public void setSnr(String snr) {
		this.snr = snr;
	}
	public boolean isExecuted() {
		return executed;
	}
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
}