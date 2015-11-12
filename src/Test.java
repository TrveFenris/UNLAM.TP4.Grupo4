package grafos;

public class Test {
	public static void main(String[] args) {
		Grafo g = GeneradorDeGrafos.generarGrafoAleatorioConPorcentajeDeAdyacencia(4, 50);
		g.mostrarGrafo();
		System.out.println(g.esConexo());
	}
}
