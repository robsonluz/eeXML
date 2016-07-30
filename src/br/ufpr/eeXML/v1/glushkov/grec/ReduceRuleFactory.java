/*
 * Created on 23/06/2005
 *
 */
package br.ufpr.eeXML.v1.glushkov.grec;

/**
 * @author Robson João Padilha da Luz
 *
 */
public class ReduceRuleFactory 
{
	private static ReduceRuleFactory instance;
	
	private ReduceRule rule1;
	private ReduceRule rule2;
	private ReduceRule rule3;
	
	private ReduceRuleFactory()
	{
		rule1 = new Rule1();
		rule2 = new Rule2();
		rule3 = new Rule3();
	}
	
	public static ReduceRuleFactory getInstance()
	{
		if(instance==null)
			instance = new ReduceRuleFactory();
		return instance;
	}
	
	public ReduceRule getRule1() {
		return rule1;
	}
	public ReduceRule getRule2() {
		return rule2;
	}
	public ReduceRule getRule3() {
		return rule3;
	}
}