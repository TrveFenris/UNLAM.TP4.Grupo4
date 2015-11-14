package grafos;

public class Test {
	public static void main(String[] args) {
		try {
			Grafo g = GeneradorDeGrafos.generarGrafoAleatorioConPorcentajeDeAdyacencia(4, 50);
			g.mostrarGrafo();
			System.out.println(g.esConexo());
		} catch (GrafoNoConexoException e) {
			e.printStackTrace();
		}
		
	}
}
