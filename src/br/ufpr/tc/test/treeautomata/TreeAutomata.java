/*
 * Created on 24/01/2006
 *
 */
package br.ufpr.tc.test.treeautomata;

import java.util.LinkedList;
import java.util.List;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class TreeAutomata 
{
	private List<State> lstState;
	
	public TreeAutomata()
	{
		lstState = new LinkedList();
	}
	
	public State getStateByName(String name)
	{
		for(State s:lstState)
		{
			if(s.getName().equals(name))
				return s;
		}
		return null;
	}

	public void add(State state)
	{
		lstState.add(state);
	}
	
	public List<State> getLstState() 
	{
		return lstState;
	}

	public void setLstState(List<State> lstState) 
	{
		this.lstState = lstState;
	}
	
	public void initTreeAutomata()
	{
		State s = new State();
		s.setName("data");
		s.setQName("q_data");
		s.setStrExp("0");
		lstState.add(s);
	}
	
	public void printTreeAutomata()
	{
		int i = 1;
		StringBuffer sb = new StringBuffer("");
		for(State s:lstState){
			sb.append("("+i+") ");
			sb.append(s.getName());
			sb.append(", {");
			addAttribute(sb,s.getLstComp());
			sb.append(",");
			addAttribute(sb,s.getLstOp());
			sb.append("}, ");
			sb.append(s.getStrExp());
			sb.append(" -> ");
			sb.append(s.getQName());
			sb.append("\n");
			i++;
		}
		System.out.println(sb.toString());
	}
	private void addAttribute(StringBuffer sb,List<String> lst)
	{
		if(lst.size()==0){
			sb.append("0");
			return;
		}
		sb.append("{");
		for(int i=0;i<lst.size();i++)
		{
			sb.append(lst.get(i));
			if(i<lst.size()-1)
				sb.append(",");
		}
		sb.append("}");
	}
}
