package grafos;

import java.io.*;

public class Grafo {
	
	private MatrizSimetrica matriz;
	private int cantNodos;
	private int cantAristas;
	private double porcentAdy;
	private int grMax;
	private int grMin;
	
	public Grafo(int cantNodos, MatrizSimetrica m, int cAristas, double pAdy){
		this.cantNodos=cantNodos;
		this.matriz=m;
		this.cantAristas=cAristas;
		this.porcentAdy=pAdy;
		calcularGrados();
	}
	
	public Grafo(File archivo){
		FileReader input = null;
		BufferedReader bufInput =null;
		try
		{
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
			line = bufInput.readLine();
			for(int i=0;i<cantAristas;i++)
			{
				datos = line.split(" ");	
				matriz.setValor(Integer.parseInt(datos[0])-1, Integer.parseInt(datos[1])-1, true);
				line=bufInput.readLine();
			}
			bufInput.close();
			matriz.mostrarMatriz();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
	      {
	         try{                    
	            if( null != bufInput ){   
	               bufInput.close();     
	            }                  
	         }catch (Exception e2){ 
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
	
	public boolean esAdyacente(int nodoA, int nodoB){
		return matriz.getValor(nodoA, nodoB);
	}
	
	public int getTam(){
		return matriz.getTam();
	}
	
	public void calcularGrados(){
		grMax=1;
		int aux;
		for(int f=0;f<cantNodos;f++){
			aux=0;
			for(int c=0;c<cantNodos;c++){
				if(getValor(f, c)==true)
					aux++;
			}
			if(aux>grMax)
				grMax=aux;
		}
		grMax--;
		grMin=grMax;
		for(int f=0;f<cantNodos;f++){
			aux=0;
			for(int c=0;c<cantNodos;c++){
				if(getValor(f, c)==true)
					aux++;
			}
			if(aux<grMin)
				grMin=aux;
		}
		grMin--;
		System.out.println("GRADOS: \t"+grMax+"\t"+grMin);
	}
	
	public void mostrarGrafo(){
		matriz.mostrarMatriz();
	}
	
	public void guardarGrafo(String path){
		FileWriter fichero = null; //!=FileReader
        PrintWriter pw = null;//!=BufferedReader
        try
        {
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);
            pw.println(cantNodos+" "+cantAristas+" "+porcentAdy+" "+grMax+" "+grMin);
            for(int f=0;f<cantNodos;f++){
            	for(int c=f+1;c<cantNodos;c++)
            		if(getValor(f, c)==true){
            			pw.println((f+1)+" "+(c+1));
            		}
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
           try 
           {
	           if (null != fichero)
	              fichero.close();
           } 
           catch (Exception e2) 
           {
              e2.printStackTrace();
           }
        }
    }
}
