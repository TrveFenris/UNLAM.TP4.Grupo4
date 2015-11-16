package grafos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Grafo {

	private MatrizSimetrica matriz;
	private int cantNodos;
	private int cantAristas;
	private double porcentAdy;
	private int grMax;
	private int grMin;
	private int cantColores;
	private Nodo[] nodos;
	
	public Grafo(int cantNodos, MatrizSimetrica m, int cAristas, double pAdy){
		this.cantNodos=cantNodos;
		this.matriz=m;
		this.cantAristas=cAristas;
		this.porcentAdy=pAdy;
		this.nodos=new Nodo[cantNodos];
		calcularGrados();
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
			nodos=new Nodo[cantNodos];
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
		int i, color;
		cantColores = 0;
		for(i=1; i<=cantNodos; i++) {
			color = 1;
			while(!sePuedeColorear(i, color))
				color++;
			nodos[i].setColor(color);
			if(color > cantColores)
				cantColores = color;
		}
	}	
	
	

	public void colorearPowell(){
		ordenarGradoDescendente(nodos,1,nodos.length-1);
		colorearSecuencialAlternativo();
	}
	

	public void colorearMatula(){
		
		ordenarGradoAscendente(nodos,1,nodos.length-1);
		colorearSecuencialAlternativo();
	}
	
	private boolean sePuedeColorear(int n, int color) {
		int i=1; 
		if(nodos[n].getColor() != 0) //si el nodo fue coloreado 
			return false;      //no puedo colorear
		while(i <= cantNodos) {
			if(nodos[i].getColor() == color) {  //si el nodo a colorear es adyacente a un nodo ya pintado de ese
				if(esAdyacente(i,n))  //color, no lo podré colorear
					return false;
			}
			i++;
		}
		return true;
	}
	
	
	private void ordenarGradoAscendente(Nodo nodos[],int izq,int der)
	{
		Nodo pivote = nodos[(izq+der)/2],aux;  	        
		int i=izq, d=der;	        
		do	        
		{	            
			while(nodos[i].getGrado() < pivote.getGrado()) 
				i++; 	            
			while(nodos[d].getGrado() > pivote.getGrado()) 
				d--;	            
			if(i<=d)	            
			{	                
				aux=nodos[i];
				nodos[i]=nodos[d];
				nodos[d]=aux;
				i++;	            	
				d--;	            
			}	        
		}while (i<=d); 	            
		if(izq<d) ordenarGradoAscendente(nodos,izq,d);	            
		if(i<der) ordenarGradoAscendente(nodos,i,der);	        
	}
	
	
	private void ordenarGradoDescendente (Nodo nodo[],int izq,int der)
	{
		Nodo pivote = nodos[(izq+der)/2], aux;  	        
		int i=izq, d=der;	        
		do	        
		{	            
			while(nodo[i].getGrado() > pivote.getGrado()) 
				i++; 	            
			while(nodo[d].getGrado() < pivote.getGrado()) 
				d--;	            
			if(i<=d)	            
			{	                
				aux = nodo[i];
				nodo[i]=nodo[d];
				nodo[d]=aux;
				i++;	            	
				d--;	            
			}	        
		}while (i<=d); 	            
		if(izq<d) ordenarGradoDescendente(nodo,izq,d);	            
		if(i<der) ordenarGradoDescendente(nodo,i,der);	        
	}
	
/////////////////////////////////////////////////////////////////////////////////////////
//	public void alterarOrdenNodos()
//	{
//		cantColores=0;  //vuelvo a setearlo en 0 por si se ejecuta varias veces el coloreo
//		Nodo vectorAux[]= new Nodo[cantNodos+1];  //creo vector auxiliar donde voy a alterar posiciones 
//	    int[] numeros= new int[cantNodos+1];
//	    Random rnd=new Random();
//	    int aux=cantNodos, pos;
//	    
//	    //se rellena una matriz ordenada del 1 al..n
//	    for(int i=1; i<=cantNodos; i++) 
//	    	numeros[i]=i;
//	   
//	    for(int i=1; i<=cantNodos; i++)
//	    {
//	        pos= rnd.nextInt(aux);  
//	        vectorAux[ numeros[pos]+1 ] = new Nodo(0,nodos[i].getGrado()); //cambio la pos del nodo, en un vector de nodos aux
//	        numeros[pos]=numeros[aux-1]; aux--; 
//	    }
//		nodos = vectorAux;
//		vectorAux=null;
//	}
//
//	public void mostrarNodosColoreados(){
//		System.out.println("Cantidad de colores: " + cantColores );
//		for(int i=1; i<=cantNodos; i++)
//			System.out.println( nodos[i].toString() );
//	}
//	
	
	public void colorear(int codAlgoritmo)
	{
		switch (codAlgoritmo)
		{
		case 1: colorearSecuencialAlternativo(); break;
		case 2: colorearPowell(); break;
		case 3: colorearMatula(); break;
		}
	}
	
	public void ejecutarCaso (String pathCaso, int cod_algoritmo)
	{
		int cantColor[] = new int[cantNodos];
		int nroCromatico= cantNodos;
		Nodo[] grafoColoreado=null;
		
		for(int i=0; i<10; i++)
		{
			colorear(cod_algoritmo);
			cantColor[cantColores]+=1 ;
			if(cantColores < nroCromatico) //me quedo con la menor cantidad de colores obtenidos hasta el
			{                             //momento.
				nroCromatico= cantColores;
				grafoColoreado = nodos;
			}
			//alterarOrdenNodos();
		}
		
		//grabarResumenCaso(pathCaso,cod_algoritmo,cantColor, nroCromatico);
		//grabarGrafoColoreado(pathCaso,cod_algoritmo, grafoColoreado,nroCromatico);
	
	}
	
	
//	public void grabarResumenCaso(String pathCaso, int cod_algoritmo, int[]cantColor, int nroCromatico)
//	{
//		String pathOUT = "Lote de prueba/"+cod_algoritmo + "_" + pathCaso + "_resumen.txt";
//		FileWriter fw = null;
//		PrintWriter pw= null;
//		try {
//			fw = new FileWriter(pathOUT);
//			pw = new PrintWriter(fw);
//			pw.println("Algoritmo: " + cod_algoritmo + "    Caso: "+ pathCaso);
//			pw.println("NRO CROMATICO: " + nroCromatico);
//			pw.println("CantColores  CantRepeticiones");
//			for(int i=0; i<cantColor.length; i++)
//			{
//				if(cantColor[i]>0)
//					pw.println(i + " " + cantColor[i]);
//			}	
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		finally{
//				try {
//					if(fw!=null)
//						fw.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//			} 
//	}
//	
//	public void grabarGrafoColoreado (String pathCaso, int cod_algoritmo, Nodo[] grafoColoreado, int nroCromatico)
//	{
//		String pathOUT = "Lote de prueba/OUT/" + pathCaso + ".out";
//		FileWriter fw = null;
//		PrintWriter pw= null;
//		try {
//			fw = new FileWriter(pathOUT);
//			pw = new PrintWriter(fw);
//			pw.println(cantNodos + " "+ nroCromatico +" " + grMax);
//			
//			for(int i=1; i<=cantNodos; i++)
//				pw.println(grafoColoreado[i] + " " + grafoColoreado[i].getColor());
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		finally{
//				try {
//					if(fw!=null)
//						fw.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//			} 
//	}
}
