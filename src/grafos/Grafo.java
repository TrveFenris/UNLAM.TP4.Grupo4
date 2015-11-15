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
}
