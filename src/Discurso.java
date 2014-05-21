import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;


public class Discurso {
	
	//declaração de variáveis
	private static ArrayList<String> palavrasDiscurso;
	private static Collator collator;
	
	
	//método contrutor
	public Discurso() {
		palavrasDiscurso = new ArrayList<>();
		collator = Collator.getInstance (new Locale ("pt", "BR"));
		collator.setStrength(Collator.PRIMARY);
	}
	
	private void separarPalavras(String linha, ArrayList<String> palavrasDiscurso){
		
		StringTokenizer st = new StringTokenizer(linha);
		String palavra;
		
	    while (st.hasMoreTokens()) {//enquanto existir palavras na linha
	    	palavra = st.nextToken(); //pega proxima palavra da string
	    	palavra = palavra.replaceAll("[!,.-]:;", ""); //removendo sinais ortograficos
	    	palavrasDiscurso.add(palavra); //adiciona ao vetor de palavras
	    }	    
	}
	
	public void salvarArquivo(){
		File arquivo = new File("nome_do_arquivo.txt");
		try { 
			if (!arquivo.exists()) {
			//cria um arquivo (vazio)			
				arquivo.createNewFile();			
			}
			 
			//caso seja um diretório, é possível listar seus arquivos e diretórios
			File[] arquivos = arquivo.listFiles();
			 
			//escreve no arquivo
			FileWriter fw = new FileWriter(arquivo, true);
			 
			BufferedWriter bw = new BufferedWriter(fw);
			 
			bw.write("Texto a ser escrito no txt");
			 
			bw.newLine();
			
			bw.close();
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			
		}
	}
	
	private void lerArquivo(String nomeArquivo){
		try { 
			FileReader arq = new FileReader(nomeArquivo);
		
			BufferedReader lerArq = new BufferedReader(arq);
			
			String linha = lerArq.readLine(); // inicializa com a primeira linha
			
			while (linha != null) {	//enquanto não chegar ao final do arquivo		
				separarPalavras(linha, palavrasDiscurso);//separa as palavras da linha para vetor final			    
				linha = lerArq.readLine(); // lê a proxima linha até chegar em NULL (fim do arquivo)			
			} 
			arq.close();
			
		} catch (IOException e) { 
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
		}
	}
	
	public ArrayList<String> ordenarDiscursoBubbleSort(){
		
			
		 
        for (int i = 0; i < palavrasDiscurso.size() - 1; ++i)  
            for (int j = i + 1; j < palavrasDiscurso.size(); ++j)  
                if (collator.compare(palavrasDiscurso.get(i),palavrasDiscurso.get(j)) > 0) {  
                    String temp = palavrasDiscurso.get(i);  
                    palavrasDiscurso.set(i, palavrasDiscurso.get(j));
                    palavrasDiscurso.set(j, temp);
                }
        
		return palavrasDiscurso;		
	}
	
	public void ordenarDiscursoQuickSort(ArrayList<String> arr, int low, int high) {
		 
		if (arr == null || arr.size() == 0)
			return;
 
		if (low >= high)
			return;
 
		//pick the pivot
		int middle = low + (high - low) / 2;
		String pivot = arr.get(middle);
 
		//make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (collator.compare(arr.get(i),pivot) < 0) {
				i++;
			}
 
			while ( collator.compare(arr.get(j),pivot) > 0 ) {
				j--;
			}
 
			if (i <= j) {
				String temp = arr.get(i);
				arr.set(i, arr.get(j));
				arr.set(i,temp);
				i++;
				j--;
			}
		}
 
		//recursively sort two sub parts
		if (low < j)
			ordenarDiscursoQuickSort(arr, low, j);
 
		if (high > i)
			ordenarDiscursoQuickSort(arr, i, high);
	}
	
	public void imprimirDiscurso(){
		for (int i = 0; i < palavrasDiscurso.size() - 1; ++i)
			System.out.println(palavrasDiscurso.get(i)+" ");
	}

	public static void main(String[] args) {
		
		Discurso discursoRodrigo = new Discurso();		
		discursoRodrigo.lerArquivo("C:/Rodrigo/IFMG/3_semestre/AEDS/TP1/dilma.txt");
		//discursoRodrigo.ordenarDiscursoQuickSort(palavrasDiscurso, 0, palavrasDiscurso.size()-10);
		discursoRodrigo.ordenarDiscursoBubbleSort();
		discursoRodrigo.imprimirDiscurso();
		
				 
	}

}
