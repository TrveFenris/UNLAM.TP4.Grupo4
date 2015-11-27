package grafos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;



public class Grafo {

	private MatrizSimetrica matriz;
	private int cantNodos;
	private int cantAristas;
	private double porcentAdy;
	private int grMax;
	private int grMin;
	private int cantColores;
	private ArrayList<Nodo> nodos= new ArrayList<Nodo>();
	
	public Grafo(int cantNodos, MatrizSimetrica m, int cAristas, double pAdy){
		this.cantNodos=cantNodos;
		this.matriz=m;
		this.cantAristas=cAristas;
		this.porcentAdy=pAdy;
		this.nodos= new ArrayList<Nodo>();
		calcularGrados(); //Maximo y minimo
		completarGrados(); //Para todos los nodos
	}
	
	public Grafo(File archivo){
		FileReader input = null;
		BufferedReader bufInput =null;
		try {
			input = new FileReader(archivo);
			bufInput = new BufferedReader(input);
			String line;
			String[] datos;
			line = bufInput.readLine();
			datos=line.split(" ");
			cantNodos=Integer.parseInt(datos[0]);
			cantAristas=Integer.parseInt(datos[1]);
			porcentAdy=Double.parseDouble(datos[2]);
			grMax=Integer.parseInt(datos[3]);
			grMin=Integer.parseInt(datos[4]);
			this.matriz=new MatrizSimetrica(cantNodos);
			//Lectura
			for(int i=0;i<cantAristas;i++) {
				line=bufInput.readLine();
				datos = line.split(" ");	
				matriz.setValor(Integer.parseInt(datos[0])-1, Integer.parseInt(datos[1])-1, true);
			}
			for(int i = 0; i<matriz.getDimension(); i++) {
				int grado = 0;
				for(int j = 0; j<matriz.getDimension(); j++) {
					if(matriz.getValor(i, j) && i!=j) {
						grado++;
					}
				}
				nodos.add(new Nodo(i+1,0,grado));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
	        	try{                   
	            		if( null != bufInput ) {
	               			bufInput.close();     
	            		}
	         	}
	         	catch (Exception e2) { 
	            		e2.printStackTrace();
	         	}
	      	}
	}
	
	public void completarGrados(){
		int grado=0;
		for(int f=0;f<cantNodos;f++) {
			grado=0;
			for(int c=0;c<cantNodos;c++) {
				if(getValor(f, c)==true)
					grado++;
			}
			nodos.add(new Nodo(f+1, 0, grado));
		}
	}
	
	public void setValor(int f, int c, boolean v){
		this.matriz.setValor(f, c, v);
	}
	
	public boolean getValor(int f, int c){
		return matriz.getValor(f, c);
	}
	public int getCantidadNodos(){
		return cantNodos;
	}
	public boolean esAdyacente(int nodoA, int nodoB){
		return matriz.getValor(nodoA, nodoB);
	}
	public ArrayList<Integer> nodosAdyacentesA(int nodo) {
		ArrayList<Integer> nodosAdy = new ArrayList<Integer>();
		for(int i=0; i < matriz.getDimension(); i++) {
			if(esAdyacente(nodo, i) && nodo != i) {
				nodosAdy.add(i);
			}
		}
		return nodosAdy;
	}
	
	
	public boolean esConexo() {
		boolean result = true;
		BusquedaDFS busqueda = new BusquedaDFS(this, 0);
		for(int i = 0; i < cantNodos; i++) {
			if(!busqueda.marked(i)) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	public int getCantidadColores() {
		return cantColores;
	}
	public int getTam(){  
		return matriz.getTam();
	}
	
	public int getCantidadAristas() {
		return cantAristas;
	}
	
	public int getGradoMaximo() {
		return grMax;
	}
	public int getGradoMinimo() {
		return grMin;
	}
	public double getPorcentajeAdyacencia() {
		return porcentAdy;
	}
	public void calcularGrados() {
		grMax=1;
		int aux;
		for(int f=0;f<cantNodos;f++) {
			aux=0;
			for(int c=0;c<cantNodos;c++) {
				if(getValor(f, c)==true)
					aux++;
			}
			if(aux>grMax)
				grMax=aux;
		}
		grMax--;
		grMin=grMax;
		for(int f=0;f<cantNodos;f++) {
			aux=0;
			for(int c=0;c<cantNodos;c++) {
				if(getValor(f, c)==true)
					aux++;
			}
			if(aux<grMin)
				grMin=aux;
		}
		grMin--;
		System.out.println("GRADOS: \t"+grMax+"\t"+grMin);
	}
	
	public void mostrarGrafo() {
		matriz.mostrarMatriz();
	}
	
	public void guardarGrafo(String path) {
		FileWriter fichero = null; //!=FileReader
        PrintWriter pw = null;//!=BufferedReader
        try {
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);
            pw.println(cantNodos+" "+cantAristas+" "+porcentAdy+" "+grMax+" "+grMin);
            for(int f=0;f<cantNodos;f++) {
            	for(int c=f+1;c<cantNodos;c++) {
            		if(getValor(f, c)==true) {
            			pw.println((f+1)+" "+(c+1));
            		}
            	}
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
           try {
	           if (null != fichero)
	              fichero.close();
           } 
           catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
/////////////////////////////////////////////////////////////////////////////////////////////	
	public void colorearSecuencialAlternativo() {
		int color;
		cantColores = 0;
		for(int i=0; i< cantNodos; i++) {
			color = 1;
			while(!sePuedeColorear(i, color)){
				color++;
			}
			nodos.get(i).setColor(color);
			//System.out.println(nodos.get(i).getNumero()+"\t"+nodos.get(i).getGrado());
			if(color > cantColores)
				cantColores = color;
		}
	}	
	
	public void colorearSecuencial(){
		Collections.shuffle(nodos);
		colorearSecuencialAlternativo();
	}

	public void colorearPowell(){
		ordenarGradoDescendente(nodos,0,nodos.size()-1);
		mezclarPorGrado();
		colorearSecuencialAlternativo();
	}
	
	public void despintarGrafo(){
		for(int i=0;i<cantNodos;i++){
			nodos.get(i).setColor(0);
		}
	}

	public void colorearMatula(){		
		ordenarGradoAscendente(nodos,0,nodos.size()-1);
		mezclarPorGrado();
//		Collections.sort(nodos);
		colorearSecuencialAlternativo();
	}
	
	private boolean sePuedeColorear(int n, int color) {
		int i=0; 
		if(nodos.get(n).getColor() != 0) //si el nodo fue coloreado 
			return false;      //no puedo colorear
		while(i < cantNodos) {
			if(nodos.get(i).getColor() == color && i!=n) {  //si el nodo a colorear es adyacente a un nodo ya pintado de ese
				if(esAdyacente(nodos.get(i).getNumero()-1,nodos.get(n).getNumero()-1))  //color, no lo podré colorear
					return false;
			}
			i++;
		}
		return true;
	}
	
	private void mezclarPorGrado(){
		int i =0;
		int inicio=0,fin=0;
		int grado=0;
		Nodo aux;
		Random r = new Random();
		boolean[] mezclado= new boolean[cantNodos];
		while(i<cantNodos){
			inicio=i;
			grado=nodos.get(i).getGrado();
			while(i<cantNodos&&nodos.get(i).getGrado()==grado){
				i++;
			}
			fin=i;
			for(int k=inicio;k<(fin-inicio);k++){
				int res=r.nextInt(fin-inicio);
				if(mezclado[k])
					res=r.nextInt(fin-inicio);
				aux=nodos.get(k);
				mezclado[k]=true;
				nodos.set(k, nodos.get(inicio+res));
				nodos.set(inicio+res, aux);
			}
		}
		validarMezcla();
	}
	
	private void validarMezcla(){
		int grado = nodos.get(0).getGrado();
		for(Nodo n : nodos){
			if(n.getGrado()!=grado){
				if(n.getGrado()<grado){
					System.out.println("GRAFO MAL MEZCLADO**************************************************");
					System.exit(-1);
				}
				else
					grado=n.getGrado();
			}
		}
	}
	
	private void ordenarGradoAscendente(ArrayList<Nodo> nodo,int izq,int der)
	{
		Nodo pivote = nodos.get((izq+der)/2),aux;  	        
		int i=izq, d=der;	        
		do	        
		{	            
			while(nodos.get(i).getGrado() < pivote.getGrado()) 
				i++; 	            
			while(nodos.get(d).getGrado() > pivote.getGrado()) 
				d--;	            
			if(i<=d)	            
			{	                
				aux = nodo.get(i);
				nodo.set(i, nodo.get(d));
				nodo.set(d, aux);
				i++;	            	
				d--;	            
			}	        
		}while (i<=d); 	            
		if(izq<d) ordenarGradoAscendente(nodos,izq,d);	            
		if(i<der) ordenarGradoAscendente(nodos,i,der);	        
	}
	
	
	private void ordenarGradoDescendente (ArrayList<Nodo> nodo,int izq,int der) {
		Nodo pivote = nodos.get((izq+der)/2);
		Nodo aux;  	        
		int i=izq, d=der;	        
		do	        
		{	            
			while(nodo.get(i).getGrado() > pivote.getGrado()) 
				i++; 	            
			while(nodo.get(d).getGrado() < pivote.getGrado()) 
				d--;	            
			if(i<=d) {	                
				aux = nodo.get(i);
				nodo.set(i, nodo.get(d));
				nodo.set(d, aux);
				i++;	            	
				d--;	            
			}	        
		}while (i<=d); 	            
		if(izq<d) ordenarGradoDescendente(nodo,izq,d);	            
		if(i<der) ordenarGradoDescendente(nodo,i,der);	        
	}
	
///////////////////////////////////////////////////////////////////////////////////////
//	public void alterarOrdenNodos() {
//		cantColores=0;  //vuelvo a setearlo en 0 por si se ejecuta varias veces el coloreo
//		Nodo vectorAux[]= new Nodo[cantNodos];  //creo vector auxiliar donde voy a alterar posiciones 
//	    //int[] numeros= new int[cantNodos+1];
//	    Random rnd=new Random();
//	    int aux=cantNodos, pos;
//	    
//	    //se rellena una matriz ordenada del 1 al..n
//	    //for(int i=1; i<=cantNodos; i++) 
//	    	//numeros[i]=i;
//	   
//	    for(int i=0; i < cantNodos; i++) {
//	        pos = rnd.nextInt(aux);
//	        if(vectorAux[pos] == null)
//	        	vectorAux[ pos ] = new Nodo(pos+1,0,nodos[i].getGrado()); //cambio la pos del nodo, en un vector de nodos aux
//	        else i--;
//	    }
//		nodos = vectorAux;
//		vectorAux=null;
//	}
//	
	private void alterarOrdenNodos() {
		cantColores = 0;
		Collections.shuffle(nodos);
		
	}
	
//	public void mostrarNodosColoreados(){
//		System.out.println("Cantidad de colores: " + cantColores );
//		for(int i=1; i<=cantNodos; i++)
//			System.out.println( nodos[i].toString() );
//	}
//	
	
	public void colorear(Algoritmos codAlgoritmo)
	{
		switch (codAlgoritmo)
		{
		case SECUENCIAL: colorearSecuencial(); break;
		case WELSH_POWELL: colorearPowell(); break;
		case MATULA: colorearMatula(); break;
		}
	}
	
	public void ejecutarCaso (String pathCaso, Algoritmos cod_algoritmo)
	{
		int cantColor[] = new int[cantNodos];
		int nroCromatico= cantNodos;
		ArrayList<Nodo> grafoColoreado = new ArrayList<Nodo>();
		
		for(int i=0; i<10; i++)
		{
			colorear(cod_algoritmo);
			cantColor[cantColores]+=1 ;
			if(cantColores < nroCromatico) //me quedo con la menor cantidad de colores obtenidos hasta el
			{                             //momento.
				nroCromatico= cantColores;
			}
			alterarOrdenNodos();
		}
		
		//grabarResumenCaso(pathCaso,cod_algoritmo,cantColor, nroCromatico);
		//grabarGrafoColoreado(pathCaso,cod_algoritmo, grafoColoreado,nroCromatico);
	
	}
	
	
	public void grabarResumenCaso(String pathCaso, Algoritmos cod_algoritmo, int[]cantColor, int nroCromatico)
	{
		String pathOUT = "Lote de prueba/"+cod_algoritmo.getValor() + "_" + pathCaso + "_resumen.txt";
		FileWriter fw = null;
		PrintWriter pw= null;
		try {
			fw = new FileWriter(pathOUT);
			pw = new PrintWriter(fw);
			pw.println("Algoritmo: " + cod_algoritmo.getValor() + "    Caso: "+ pathCaso);
			pw.println("NRO CROMATICO: " + nroCromatico);
			pw.println("CantColores  CantRepeticiones");
			for(int i=0; i<cantColor.length; i++)
			{
				if(cantColor[i]>0)
					pw.println(i + " " + cantColor[i]);
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
				try {
					if(fw!=null)
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			} 
	}
	
	public void grabarGrafoColoreado (File archivo)
	{
		FileWriter fw = null;
		PrintWriter pw= null;
		try {
			fw = new FileWriter(archivo);
			pw = new PrintWriter(fw);
			pw.println(cantNodos + " " + cantColores);
			
			for(int i=0; i<cantNodos; i++)
				pw.println(nodos.get(i).getNumero()+" "+nodos.get(i).getColor());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
				try {
					if(fw!=null)
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			} 
	}
}
