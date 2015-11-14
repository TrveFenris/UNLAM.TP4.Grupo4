package grafos;

import java.util.Random;

public class GeneradorDeGrafos {
	//Generador de grafos aleatorios dados N y el porcentaje de adyacencia.
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
	//Generador de grafos aleatorios dados N y una probabilidad para cada arista.
	public static Grafo generarGrafoAleatorioProbabilistico(int cantNodos, double probabilidad) throws GrafoNoConexoException{
		Random r = new Random();
		MatrizSimetrica m = new MatrizSimetrica(cantNodos);
		final int maxAristas = m.getTam();
		int cantAristas = 0;
		for(int i=0; i<maxAristas; i++) {
			double porc = r.nextDouble();
			if(porc < probabilidad){
				m.setValor(i, true);
				cantAristas++;
			}
			else{
				m.setValor(i, false);
			}
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
	//Generador de grafos regulares dados N y el grado.
	public static Grafo generarGrafoRegularGradoN(int cantNodos, int grado) throws GrafoInvalidoException{
		if(grado >= cantNodos || cantNodos == 0) throw new GrafoInvalidoException();
		if(cantNodos%2 == 0) { //par
			
		}
		else { //impar
			if(grado==1) throw new GrafoInvalidoException();
		}
		Grafo g = new Grafo();
		return g;
	}
	//Generador de grafos regulares dados N y el porcentaje de adyacencia.
	public static Grafo generarGrafoRegularConPorcentajeDeAdyacencia(int cantNodos, double porcAdy){
		Grafo g = new Grafo();
		return g;
	}
}
