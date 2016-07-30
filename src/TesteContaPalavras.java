import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/*
 * Created on 13/05/2007
 *
 */
/*
 * @autor Robson João Padilha da Luz
 *
 */
public class TesteContaPalavras {
	public static void main(String args[]) throws Exception {
		String file = "usecase/LMPLCurriculo.xml";
		FileInputStream fin = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
		String w = "<!ATTLIST";
		
		String line = null;
		
		int count = 0;
		while((line = reader.readLine())!=null) {
			int i = -1;
			do{
				i = line.indexOf(w);
				if(i!=-1) {
					count++;
					line = line.replaceFirst(w, "");
				}
			}while(i!=-1);
			
		}
		reader.close();
		
		System.out.println("Ocorrencias da palavra '"+w+"': "+count);
		
	}
}
