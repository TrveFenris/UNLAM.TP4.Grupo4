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
		if(grado >= cantNodos || cantNodos <= 0 || grado < 0) throw new GrafoInvalidoException();
		MatrizSimetrica matriz = new MatrizSimetrica(cantNodos);
		int cantAristas = 0;
		if(cantNodos%2 == 0) { //par
			if(grado%2==1) {
				int saltoImpar = (cantNodos/2);
				for(int i = 0; i < cantNodos/2; i++) {
					matriz.setValor(i,i+saltoImpar, true);
					cantAristas++;
				}
			}
			int saltoNodo =  1;
			for(int g = 0; g < grado/2; g++) {
				for(int i = 0; i < cantNodos; i++) {
					matriz.setValor(i,(i+saltoNodo)%6, true);
					cantAristas++;
				}
				saltoNodo++;
			}
		}
		else { //impar
			if(grado%2==1) throw new GrafoInvalidoException();
		}
		Grafo g = new Grafo(cantNodos,matriz,cantAristas,(cantAristas*100)/((cantNodos*(cantNodos-1))/2));
		return g;
	}
	//Generador de grafos regulares dados N y el porcentaje de adyacencia.
	public static Grafo generarGrafoRegularConPorcentajeDeAdyacencia (int cantNodos, int porAdy){
		int grado = (porAdy * (cantNodos-1)) / 100;
		if( cantNodos<1 || grado<0 || grado>=cantNodos || (cantNodos!=1 && grado==0) ||(cantNodos!=2 && grado==1) || (cantNodos%2!=0 && grado%2!=0) ){
			System.out.println("no se puede generar el grafo");
			return null;
		}
		
		MatrizSimetrica matriz = new MatrizSimetrica(cantNodos);
		
		// Camino externo.
		for(int x=0 ; x<cantNodos-1 ; x++)
			matriz.setValor(x, x+1, true);
		if(cantNodos > 2){								// Si hay un nodo no necesita aristas. Si hay dos, la unica arista la completa en el for anterior.
			matriz.setValor(0, cantNodos-1,true);
			grado -= 2;								// Coloco todas las aristas entre grafos consecutivos, entonces cada nodo ya tiene 2 aristas.
			// Cruz.
			if(grado%2 != 0){
				for(int x=0 ; x<cantNodos/2 ; x++)
					matriz.setValor(x,x+(cantNodos/2),true);
				grado--;
			}
			// Salteando.
			int cantVeces = grado/2;
			int saltear = 2;
			for(int x=0 ; x<cantVeces ; x++){
				for(int nodoActual=0 ; nodoActual<cantNodos ; nodoActual++){
					int aux = nodoActual+saltear;
					if(aux>cantNodos-1)
						aux -= cantNodos;
					matriz.setValor(nodoActual,aux,true);
				}
				saltear++;
			}
		}
		return new Grafo(cantNodos, matriz, (cantNodos*grado)/2, porAdy);
	}
	
	public static Grafo generarGrafoNPartito(int cantNodos, int n) throws GrafoInvalidoException {
		if(cantNodos%n != 0) throw new GrafoInvalidoException();
		MatrizSimetrica matriz = new MatrizSimetrica(cantNodos);
		final int aristasContiguas = (cantNodos/n) - 1;
		int aux = aristasContiguas;
		int cantAristas = 0;
		for(int i = 0; i < cantNodos; i++) {
			if(aux > 0) {
				matriz.setValor(i, i+1, true);
				cantAristas++;
			}
			else
				aux = aristasContiguas;
			aux--;
		}
		return new Grafo(cantNodos,matriz,cantAristas,(cantAristas*100)/((cantNodos*(cantNodos-1))/2));
	}
}
