package salvo.jesus.graph;

import java.io.*;

/**
 * A vertex in a graph.
 *
 * @author		Jesus M. Salvo Jr.
 */
public interface Vertex extends LabeledGraphComponent 
{
	boolean isInitial();
	boolean isEnd();
	
	void setInitial(boolean d);
	void setEnd(boolean b);
}
