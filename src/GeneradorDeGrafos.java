package grafos;

import java.util.Random;

public class GeneradorDeGrafos {
	
	public static Grafo generarGrafoAleatorioProbabilistico(int cantNodos, double porcAdy){
		Random r=new Random();
		MatrizSimetrica m=new MatrizSimetrica(cantNodos);
		int rand;
		int maxAristas=(cantNodos*(cantNodos-1))/2;
		Double aux=(porcAdy*m.getTam())/100;;
		int cantAristas=(int)Math.round(aux);
		System.out.println(maxAristas+"\t"+cantAristas);
		for(int i=0;i<cantAristas;i++){
			if(m.getValor(rand=r.nextInt(cantAristas))==true)
				i--;
			else
				m.setValor(rand, true);
		}
		double porcAdyReal=(cantAristas*100)/maxAristas;
		return new Grafo(cantNodos,m,cantAristas,porcAdyReal);
	}
}
