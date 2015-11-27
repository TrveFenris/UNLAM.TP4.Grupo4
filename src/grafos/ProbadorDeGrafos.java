package grafos;

public class ProbadorDeGrafos {
	public static void main(String[] args) {
		GrafoColoreado gc = new GrafoColoreado("Entradas/NoRegulares/70_porciento_adyacencia.in","Salidas/grafoColoreado.out");
		if(gc.hayNodosAdyacentesIgualColor()||gc.hayNodosRepetidos()||gc.hayNodosSinPintar()){
			System.out.println("Grafo MAL coloreado");
		}
		else
			System.out.println("Grafo BIEN coloreado");
	}
}
