/*
 * Created on 07/12/2005
 *
 */
package br.ufpr.eeXML;

import java.util.List;

import br.ufpr.eeXML.v1.XMLUpdate;
import br.ufpr.eeXML.v1.XMLUpdateList;

/*
 * @autor Robson Jo�o Padilha da Luz
 *
 */
public class IncrementalSchemaEvolution 
{
	public IncrementalSchemaEvolution()
	{
	}
	
	/**
	 * Evolui o esquema passado em originalSchema de maneira que o mesmo aceite o documento representado em 
	 * originalXML e todos os documentos que est�o de acordo com as altera��es expressadas em lstXMLUpdate
	 * @param originalSchema Esquema original a ser evolu�do
	 * @param originalXML Documento que � aceito pelo esquema originalSchema
	 * @param lstXMLUpdate Lista de altera��es realizadas em originalXML
	 * @return Retorna uma lista de esquemas que foram gerados a partir da evolu��o de originalSchema
	 */
	public List<Schema> evolution(Schema originalSchema, XMLTree originalXML, XMLUpdateList lstXMLUpdate)
	{
		for(XMLUpdate mu: lstXMLUpdate.list())
		{
			
		}
		return null;
	}
}
