package grafos;

import java.util.Random;

public class GeneradorDeGrafos {
	
	public static Grafo generarGrafoAleatorioConPorcentajeDeAdyacencia(int cantNodos, double porcAdy) throws GrafoNoConexoException{
		Random r=new Random();
		MatrizSimetrica m=new MatrizSimetrica(cantNodos);
		int rand;
		int maxAristas=(cantNodos*(cantNodos-1))/2;
		Double aux=(porcAdy*m.getTam())/100;
		int cantAristas=(int)Math.round(aux);
		System.out.println(maxAristas+"\t"+cantAristas);
		for(int i=0;i<cantAristas;i++){
			rand=r.nextInt(maxAristas);
			if(m.getValor(rand) == true)
				i--;
			else
				m.setValor(rand, true);
		}
		double porcAdyReal=(cantAristas*100)/maxAristas;
		Grafo g = new Grafo(cantNodos,m,cantAristas,porcAdyReal);
		if(g.esConexo()) {
			return g;
		}
		else {
			throw new GrafoNoConexoException();
		}
	}
	
}
