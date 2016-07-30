/*
 * Created on 17/10/2006
 *
 */
package br.ufpr.eeXML.usecase;

import java.io.FileInputStream;

import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.SchemaEvolution;
import br.ufpr.eeXML.v1.XMLTreeDOMParser;
import br.ufpr.eeXML.v1.XMLUpdate;
import br.ufpr.eeXML.v1.XMLUpdateList;
import br.ufpr.eeXML.v1.treeautomata.DTDToTreeAutomata;
import br.ufpr.eeXML.v1.treeautomata.State;
import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class LattesMappingEvolutionTest {
	public LattesMappingEvolutionTest() {
		try{
			System.out.println("Init...");
			System.out.flush();
//			String xmlFile = "usecase/positive1.xml";
//			String xmlFileUpdated = "usecase/negative1.xml";
//			String dtdFile = "usecase/hibernate-mapping-3.0.dtd";
			
			String xmlFile = 		"usecase/7201532833308399.xml";
			String xmlFileUpdated = "usecase/7201532833308399.xml";
//			String xmlFile = 		"usecase/8620467182264219.xml";
//			String xmlFileUpdated = "usecase/8620467182264219.xml";			
			String dtdFile = "usecase/LMPLCurriculo.xml";			
			
//			String xmlFile = "cliente.xml";
//			String dtdFile = "cliente.dtd";

			
			
			TreeAutomata treeAutomata = DTDToTreeAutomata.transform(dtdFile);
			treeAutomata.printTreeAutomataLattes();
			System.exit(0);
//			for(State st: treeAutomata.getLstState()) {
//				System.out.println(st.getName());
//			}
			
			
			IndexedTreeNode xmlRoot = new XMLTreeDOMParser(new FileInputStream(xmlFile)).getRootNode();
			//IndexedTreeNode xmlRootUpdated = new XMLTreeDOMParser(new FileInputStream(xmlFileUpdated)).getRootNode();
			IndexedTreeNode xmlRootUpdated = xmlRoot;
			
			
			//System.out.println(treeAutomata.match(xmlRoot));
			//treeAutomata.printTreeAutomata();
			//long time = System.currentTimeMillis();
			
			XMLUpdateFind xmlUpdateFind = new XMLUpdateFind(treeAutomata);
			
			XMLUpdateList updateList = xmlUpdateFind.findUpdates(xmlRootUpdated);
			for(XMLUpdate updates: updateList.list()){
				System.out.println(updates);
			}
			//System.exit(0);
			//treeAutomata.printTreeAutomata();
			
			SchemaEvolution ev = new SchemaEvolution(xmlRoot,treeAutomata,updateList);
			
			//TreeAutomata evTreeAutomata = ev.performEvolution();
			
			long ini = System.currentTimeMillis();
			TreeAutomata evTreeAutomata = ev.performEvolution();
			long end = System.currentTimeMillis();
			//evTreeAutomata.printTreeAutomata();
			
			System.out.println("##### Evoluation States ######");
			evTreeAutomata.printEvolutionStatesTreeAutomata();
			
			
			System.out.println("Evolution time left: "+(end-ini));
			System.out.println("Updates: "+updateList.list().size());
			//System.out.println(treeAutomata.match(xmlRoot));
			//System.out.println(evTreeAutomata.match(xmlRootUpdated));
			
			
			//System.out.println("Match: "+treeAutomata.match(xmlRoot));
			//System.out.println(System.currentTimeMillis()-time);
			//treeAutomata.printTreeAutomata();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		new LattesMappingEvolutionTest();
	}
}
