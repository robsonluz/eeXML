/*
 * Created on 17/10/2006
 *
 */
package br.ufpr.eeXML.usecase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;

import br.ufpr.eeXML.v1.IndexedTreeNode;
import br.ufpr.eeXML.v1.IndexedTreeNodeHelper;
import br.ufpr.eeXML.v1.IndexedTreeNodeType;
import br.ufpr.eeXML.v1.SchemaEvolution;
import br.ufpr.eeXML.v1.XMLTreeDOMParser;
import br.ufpr.eeXML.v1.XMLUpdate;
import br.ufpr.eeXML.v1.XMLUpdateDeleteAttribute;
import br.ufpr.eeXML.v1.XMLUpdateDeleteElement;
import br.ufpr.eeXML.v1.XMLUpdateList;
import br.ufpr.eeXML.v1.treeautomata.DTDToTreeAutomata;
import br.ufpr.eeXML.v1.treeautomata.State;
import br.ufpr.eeXML.v1.treeautomata.TreeAutomata;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class PerformanceEvolutionTest {
	public PerformanceEvolutionTest() {
		try{
			System.out.println("Init...");
			System.out.flush();
			
			String dtdFile = "usecase/LMPLCurriculo.xml";			
			TreeAutomata treeAutomata = DTDToTreeAutomata.transform(dtdFile);
			IndexedTreeNode xmlRoot = new XMLTreeDOMParser(new FileInputStream("usecase/"+"perf_1.xml")).getRootNode();
			IndexedTreeNode xmlRootUpdated = xmlRoot;
			
//			System.out.println(xmlRoot.getChildren());
//			System.out.println(IndexedTreeNodeHelper.findByPositions(xmlRoot, 3, 18));
//			System.exit(0);
			//testPerformance("perf_1.xml", new String[]{"3,4"});
			
			XMLUpdateFind xmlUpdateFind = new XMLUpdateFind(treeAutomata);
			XMLUpdateList updateList = xmlUpdateFind.findUpdates(xmlRootUpdated);

			
			
			
			SchemaEvolution ev = new SchemaEvolution(xmlRoot,treeAutomata,updateList);
			
			long ini = System.nanoTime();
			TreeAutomata evTreeAutomata = ev.performEvolution();
			long end = System.nanoTime();
			
			
			System.out.println("=======================================");
			int i=1;
			try{
				while(true) {
					testPerformance("perf_"+i+".xml", null);
					i++;
				}
			}catch(FileNotFoundException e){
			}
			
			testPerformance("perf_1.xml", new String[]{"3,4"});
			testPerformance("perf_2.xml", new String[]{"3,4","3,18"});
			testPerformance("perf_3.xml", new String[]{"3"});
			testPerformance("perf_4.xml", new String[]{"3,4"});
			testPerformance("perf_5.xml", new String[]{"3,4"});
			testPerformance("perf_6.xml", new String[]{"3,4"});
			


			

			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void makeDeletePositions(String deletePositions[], XMLUpdateList list, IndexedTreeNode root) {
		if(deletePositions!=null) {
			for(String t: deletePositions) {
				StringTokenizer st = new StringTokenizer(t, ",");
				int pos[] = new int[st.countTokens()];
				int i=0;
				while(st.hasMoreTokens()){
					pos[i++] = new Integer(st.nextToken());
				}
				IndexedTreeNode node = IndexedTreeNodeHelper.findByPositions(root, pos);
				if(node!=null){
					if(node.getType()==IndexedTreeNodeType.ATTRIBUTE) {
						list.add(new XMLUpdateDeleteAttribute(node));
					}else if(node.getType()==IndexedTreeNodeType.ELEMENT) {
						list.add(new XMLUpdateDeleteElement(node));
					}
				}
			}
		}
	}
	
	public void testPerformance(String xmlUpdatedFile, String deletePositions[]) throws Exception {
		long time = 0;
		int updateSize = 0;
		int n = 10;
		
		IndexedTreeNode xmlRoot = new XMLTreeDOMParser(new FileInputStream("usecase/"+xmlUpdatedFile)).getRootNode();
		IndexedTreeNode xmlRootUpdated = xmlRoot;

		String dtdFile = "usecase/LMPLCurriculo.xml";			
		TreeAutomata treeAutomata = DTDToTreeAutomata.transform(dtdFile);
		
		XMLUpdateFind xmlUpdateFind = new XMLUpdateFind(treeAutomata);
		XMLUpdateList updateList = xmlUpdateFind.findUpdates(xmlRootUpdated);
		makeDeletePositions(deletePositions, updateList, xmlRoot);
		
		for(int i=0;i<n;i++) {
			
	
	//		for(XMLUpdate updates: updateList.list()){
	//			System.out.println(updates);
	//		}
			
			SchemaEvolution ev = new SchemaEvolution(xmlRoot,treeAutomata,updateList);
			
			long ini = System.nanoTime();
			TreeAutomata evTreeAutomata = ev.performEvolution();
			long end = System.nanoTime();
			
			time += end - ini;
			updateSize = updateList.list().size();
			
			treeAutomata = DTDToTreeAutomata.transform(dtdFile);
		}
		time = time/n;
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("Evolution time left: "+(time));
		System.out.println("Updates: "+updateSize);
		for(XMLUpdate updates: updateList.list()){
			System.out.println(updates);
		}
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		

	}
	
	public static void main(String args[]){
		new PerformanceEvolutionTest();
	}
}
