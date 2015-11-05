package grafos;

import java.util.Random;

public class GeneradorDeGrafos {
	
	public static Grafo generarGrafoAleatorioProbabilistico(int cantNodos, double porcAdy){
		Random r=new Random();
		MatrizSimetrica m=new MatrizSimetrica(cantNodos);
		int rand;
		Double aux=(porcAdy*m.getTam())/100;;
		int cantAristas=(int)Math.round(aux);
		System.out.println(cantAristas);
		for(int i=0;i<cantAristas;i++){
			if(m.getValor(rand=r.nextInt(cantAristas+1))==true)
				i--;
			else
				m.setValor(rand, true);
		}
		return new Grafo(cantNodos,m,cantAristas,porcAdy);
	}
}
