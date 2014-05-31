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
	
	//declara��o de vari�veis
	private ArrayList<String> palavrasDiscurso;
	private Collator collator;
	private long tempoInicial, tempoFinal;
	private double tempo;
	
	
	//m�todo contrutor
	public Discurso() {
		//inicia vetor de palavras e Collator para compara��es
		palavrasDiscurso = new ArrayList<>();
		collator = Collator.getInstance (new Locale ("pt", "BR"));
		collator.setStrength(Collator.PRIMARY);
	}
	
	//tempo de execu��o
	public double getTempo(){
		return tempo;
	}
	
	//limpa objeto para instancia��o de nova entrada
	public void clearALL(){
		palavrasDiscurso.clear();
		tempoFinal = 0;
		tempoInicial = 0;
		tempo = 0;
	}
	
	//metodo utilizado para separar palavras de uma string tratar sinais e armazenar em vetor final
	private void separarPalavras(String linha, ArrayList<String> palavrasDiscurso){
		
		//String tokenizer � uma biblioteca para separa��o de Tokens(Palavras) numa string
		StringTokenizer st = new StringTokenizer(linha);
		String palavra;
		
	    while (st.hasMoreTokens()) {//enquanto existir palavras na linha
	    	palavra = st.nextToken(); //pega proxima palavra da string
	    	palavra = palavra.replaceAll("[\"!?��,.:;()�]", ""); //removendo sinais ortograficos
	    	palavrasDiscurso.add(palavra); //adiciona ao vetor de palavras
	    }	    
	}
	
	//metodos utilizado para salvar os dados do vetor em um arquivo de saida
	public void salvarArquivo(String arqSaida){
		System.out.println("Salvando arquivo de saida...");
		File arquivo = new File(arqSaida);
		String ultimaPalavra;
		try { 
			if (!arquivo.exists()) {
			//cria um arquivo caso n�o exista		
				arquivo.createNewFile();			
			}
			
			//escreve no arquivo
			FileWriter fw = new FileWriter(arquivo);			 
			BufferedWriter bw = new BufferedWriter(fw);
			
			ultimaPalavra = "";
			
			for(int i = 0; i < palavrasDiscurso.size(); i++){				
				//verifica se h� repeti��o de palavras
				if(collator.compare(palavrasDiscurso.get(i),ultimaPalavra) != 0){
					ultimaPalavra = palavrasDiscurso.get(i);//caso encontro nova palavra armazena em varivel
					bw.write(ultimaPalavra); //insere palavra no arquivo
					bw.newLine(); //nova linha
				}
			}
			//feixa buffer e arquivo
			bw.close();
			fw.close();
			
		} catch (IOException e) {
			System.err.printf("Erro ao salvar arquivo: %s.\n", e.getMessage()); 
		}
	}
	
	private void lerArquivo(String nomeArquivo){
		try { 
			System.out.println("Lendo arquivo de entrada...");
			//abre arquivo de entrada
			FileReader arq = new FileReader(nomeArquivo);
		
			BufferedReader lerArq = new BufferedReader(arq);
			
			// inicializa com a primeira linha
			String linha = lerArq.readLine(); 
			
			while (linha != null) {	//enquanto n�o chegar ao final do arquivo		
				separarPalavras(linha, palavrasDiscurso);//separa as palavras da linha para vetor final			    
				linha = lerArq.readLine(); // l� a proxima linha at� chegar em NULL (fim do arquivo)			
			}
			//fecha linha
			arq.close();
			
		} catch (IOException e) { 
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
		}
	}
	
	//metodo de ordena��o bubble sort
	public void bubbleSort(){
		System.out.println("Ordenando...");
		iniciarCronometro();
		//percorre todas as posicoes do vetor
        for (int i = 0; i < palavrasDiscurso.size() - 1; ++i){
        	//para cada posicao ele verifica se ele � maior que os itens a sua frente
            for (int j = i + 1; j < palavrasDiscurso.size(); ++j){
            	//efetua troca se for maior
                if (collator.compare(palavrasDiscurso.get(i),palavrasDiscurso.get(j)) > 0) {  
                    String temp = palavrasDiscurso.get(i);  
                    palavrasDiscurso.set(i, palavrasDiscurso.get(j));
                    palavrasDiscurso.set(j, temp);
                }
            }
        }
        finalizarCronometro();
	}
	
	//metodo de particionamento de vetor chamado recursivamente pelo quickSort
	private int partition(ArrayList<String> arr, int left, int right)
	{
	      int i = left, j = right; //  indices de posicionamento
	      String temp; //variavel tempor�ria para troca
	      //pega o pivot como a posi��o centrar do vetor
	      String pivot = arr.get((left + right) / 2);
	     
	      while (i <= j) {
	    	  	//procura posi��o com valor menor que o pivot a sua direita
	            while (collator.compare(arr.get(i),pivot) < 0)
	                  i++;
	            //procura uma posicao maior que o pivot a sua esquerda
	            while (collator.compare(arr.get(j),pivot) > 0)
	                  j--;
	            //caso os itens n�o se cruzara ele faz a troca de itens
	            if (i <= j) {
	            	temp = arr.get(i);
					arr.set(i, arr.get(j));
					arr.set(j,temp);
	                //anda uma posicao em cada lado at� chegar ao pivot
					i++;
					j--;
	            }
	      };
	      return i;
	}
	
	//metodo de ordenamento QuickSort
	private void quickSort(ArrayList<String> arr, int left, int right) {
	      int index = partition(arr, left, right);
	      if (left < index - 1)
	            quickSort(arr, left, index - 1);
	      if (index < right)
	            quickSort(arr, index, right);
	}
	
	//sobrecarregamento de fun��o para facilitar chamada para o usuario
	public void quickSort(){
		System.out.println("Ordenando...");
		iniciarCronometro();
		quickSort(palavrasDiscurso, 0, palavrasDiscurso.size()-1);
		finalizarCronometro();
	}
	
	public void selectionSort(){
		System.out.println("Ordenando...");
		iniciarCronometro();
	    // armazenam o menor valor e o �ndice do menor valor
	    String menor;
	    int indiceMenor;
	    
	    for (int i = 0; i < palavrasDiscurso.size() - 1; i++) {
	        // antes de comparar, considera-se menor o valor atual do loop
	        menor = palavrasDiscurso.get(i);
	        indiceMenor = i;
	
	        // compara com os outros valores do vetor
	        for (int j = i + 1; j < palavrasDiscurso.size(); j++){
	            if (collator.compare(palavrasDiscurso.get(j),menor)<0){
	                menor = palavrasDiscurso.get(j);
	                indiceMenor = j;
	            }
	        }
	
	        // troca os valores menor e maior
	        palavrasDiscurso.set(indiceMenor, palavrasDiscurso.get(i));
	        palavrasDiscurso.set(i, menor);

	    }
	    finalizarCronometro();
	}
	//inicia contagem de tempo
	public void iniciarCronometro(){
		tempoInicial = System.currentTimeMillis();		
	}
	
	//finaliza cronometro e armazena o tempo de execu��o
	public void finalizarCronometro(){
		tempoFinal = System.currentTimeMillis();
		tempo = (tempoFinal - tempoInicial)/1000.0;		
	}
	
	//metodo que imprime palavras do vetor em console
	public void imprimirDiscurso(){
		for (int i = 0; i < palavrasDiscurso.size() - 1; ++i)
			System.out.println(palavrasDiscurso.get(i)+" ");
	}
	

	public static void main(String[] args) {
		//arqivos de entrada
		
		String pastaSaida = "C:/Rodrigo/IFMG/3_semestre/AEDS/TP1/saida/";
		
		String arquivoDilma = "C:/Rodrigo/IFMG/3_semestre/AEDS/TP1/dilma.txt";
		String arquivoLula = "C:/Rodrigo/IFMG/3_semestre/AEDS/TP1/lula.txt";
		String arquivoAecio = "C:/Rodrigo/IFMG/3_semestre/AEDS/TP1/aecio.txt";
		String arquivoTiririca = "C:/Rodrigo/IFMG/3_semestre/AEDS/TP1/tiririca.txt";
		
		//DISCURSO DILMA
		
		//bubble sort
		Discurso discursoDilma = new Discurso();
		System.out.println("\n-- Dilma: BubbleSort --");
		discursoDilma.lerArquivo(arquivoDilma);
		discursoDilma.bubbleSort();
		System.out.println("Tempo: "+discursoDilma.getTempo()+"s");		
		//se�ection sort
		System.out.println("\n-- Dilma: SelectionSort --");
		discursoDilma.clearALL();
		discursoDilma.lerArquivo(arquivoDilma);
		discursoDilma.selectionSort();
		System.out.println("Tempo: "+discursoDilma.getTempo()+"s");		
		//quick sort
		System.out.println("\n-- Dilma: Quick Sort --");
		discursoDilma.clearALL();
		discursoDilma.lerArquivo(arquivoDilma);
		discursoDilma.quickSort();
		System.out.println("Tempo: "+discursoDilma.getTempo()+"s");
		discursoDilma.salvarArquivo(pastaSaida+"dilma.txt");		
		
		//DISCURSO AECIO
		
		//bubble sort
		Discurso discursoAecio = new Discurso();
		System.out.println("\n-- Aecio: BubbleSort --");
		discursoAecio.lerArquivo(arquivoAecio);
		discursoAecio.bubbleSort();
		System.out.println("Tempo: "+discursoAecio.getTempo()+"s");		
		//se�ection sort
		System.out.println("\n-- Aecio: SelectionSort --");
		discursoAecio.clearALL();
		discursoAecio.lerArquivo(arquivoAecio);
		discursoAecio.selectionSort();
		System.out.println("Tempo: "+discursoAecio.getTempo()+"s");		
		//quick sort
		System.out.println("\n-- Aecio: Quick Sort --");
		discursoAecio.clearALL();
		discursoAecio.lerArquivo(arquivoAecio);
		discursoAecio.quickSort();
		System.out.println("Tempo: "+discursoAecio.getTempo()+"s");
	discursoAecio.salvarArquivo(pastaSaida+"aecio.txt");
		
		//DISCURSO LULA
		
		//bubble sort
		Discurso discursoLula = new Discurso();
		System.out.println("\n-- Lula: BubbleSort --");
		discursoLula.lerArquivo(arquivoLula);
		discursoLula.bubbleSort();
		System.out.println("Tempo: "+discursoLula.getTempo()+"s");		
		//se�ection sort
		System.out.println("\n-- Lula: SelectionSort --");
		discursoLula.clearALL();
		discursoLula.lerArquivo(arquivoLula);
		discursoLula.selectionSort();
		System.out.println("Tempo: "+discursoLula.getTempo()+"s");		
		//quick sort
		System.out.println("\n-- Lula: Quick Sort --");
		discursoLula.clearALL();
		discursoLula.lerArquivo(arquivoLula);
		discursoLula.quickSort();
		System.out.println("Tempo: "+discursoLula.getTempo()+"s");
		discursoLula.salvarArquivo(pastaSaida+"lula.txt");
		
		//DISCURSO TIRIRICA
		
		//bubble sort
		Discurso discursoTiririca = new Discurso();
		System.out.println("\n-- Tiririca: BubbleSort --");
		discursoTiririca.lerArquivo(arquivoTiririca);
		discursoTiririca.bubbleSort();
		System.out.println("Tempo: "+discursoTiririca.getTempo()+"s");		
		//se�ection sort
		System.out.println("\n-- Tiririca: SelectionSort --");
		discursoTiririca.clearALL();
		discursoTiririca.lerArquivo(arquivoTiririca);
		discursoTiririca.selectionSort();
		System.out.println("Tempo: "+discursoTiririca.getTempo()+"s");		
		//quick sort
		System.out.println("\n-- Tiririca: Quick Sort --");
		discursoTiririca.clearALL();
		discursoTiririca.lerArquivo(arquivoTiririca);
		discursoTiririca.quickSort();
		System.out.println("Tempo: "+discursoTiririca.getTempo()+"s");
		discursoTiririca.salvarArquivo(pastaSaida+"tirirca.txt");
				
	}

}
