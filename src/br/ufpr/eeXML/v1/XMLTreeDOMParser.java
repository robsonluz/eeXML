/*
 * Created on 05/04/2006
 *
 */
package br.ufpr.eeXML.v1;

import java.io.InputStream;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class XMLTreeDOMParser 
{
	private InputStream xmlStream;
	private Document xmlDoc;
	private IndexedTreeNode rootNode;
	
	public XMLTreeDOMParser(InputStream xmlStream)
	{
		this.xmlStream = xmlStream;
		init();
	}
	
	private void init()
	{
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			xmlDoc = builder.parse(xmlStream);
			
			Node r = xmlDoc.getFirstChild();
			rootNode = createTreeNode(r,-1,null);
			parse(r,rootNode);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void parse(Node node, IndexedTreeNode treeNode)
	{
		NodeList list = node.getChildNodes();
		int t = treeNode.getChildren().size();
		if(list!=null)
		for(int i=0;i<list.getLength();i++){
			Node n = list.item(i);
			if(n.getNodeName().equals("#text"))
			{
				String value = n.getNodeValue();
				value = value.replaceAll("\n","");
				value = value.replaceAll("\r\n","");
				value = value.replaceAll("\r","");
				if(value.trim().length()>0){
					IndexedTreeNode tr = createTreeNode(n,t++,treeNode);
					treeNode.addChild(tr);
					parse(n,tr);
				}
			}else{
				IndexedTreeNode tr = createTreeNode(n,t++,treeNode);
				treeNode.addChild(tr);
				parse(n,tr);
			}
		}
	}	

	private IndexedTreeNode createTreeNode(Node node, int position, IndexedTreeNode parent)
	{
		//System.out.println(node);
		IndexedTreeNode n = new IndexedTreeNode(node.getNodeName(),position,parent);
		
		//Attributes
		NamedNodeMap atts = node.getAttributes();
		if(atts!=null)
		for(int i=0;i<atts.getLength();i++)
		{
			Node att = atts.item(i);
			IndexedTreeNode at = new IndexedTreeNode(att.getNodeName(),i,IndexedTreeNodeType.ATTRIBUTE,n);
			
			//Cria data para o node//
			//System.out.println(node.getFirstChild().getNodeValue().trim().replaceAll("\r\n", ""));
			IndexedTreeNode data = new IndexedTreeNode(att.getNodeValue(),i,IndexedTreeNodeType.DATA,at);
			at.addChild(data);
			/////////////////////////
			
			n.addChild(at);
		}
		return n;
	}

	public IndexedTreeNode getRootNode() {
		return rootNode;
	}
}
