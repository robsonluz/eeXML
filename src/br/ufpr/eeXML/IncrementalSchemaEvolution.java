/*
 * Created on 07/12/2005
 *
 */
package br.ufpr.eeXML;

import java.util.List;

import br.ufpr.eeXML.v1.XMLUpdate;
import br.ufpr.eeXML.v1.XMLUpdateList;

/*
 * @autor Robson João Padilha da Luz
 *
 */
public class IncrementalSchemaEvolution 
{
	public IncrementalSchemaEvolution()
	{
	}
	
	/**
	 * Evolui o esquema passado em originalSchema de maneira que o mesmo aceite o documento representado em 
	 * originalXML e todos os documentos que estão de acordo com as alterações expressadas em lstXMLUpdate
	 * @param originalSchema Esquema original a ser evoluído
	 * @param originalXML Documento que é aceito pelo esquema originalSchema
	 * @param lstXMLUpdate Lista de alterações realizadas em originalXML
	 * @return Retorna uma lista de esquemas que foram gerados a partir da evolução de originalSchema
	 */
	public List<Schema> evolution(Schema originalSchema, XMLTree originalXML, XMLUpdateList lstXMLUpdate)
	{
		for(XMLUpdate mu: lstXMLUpdate.list())
		{
			
		}
		return null;
	}
}
