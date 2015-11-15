package grafos;

public class Test {
	public static void main(String[] args) {
		try {
			Grafo g = GeneradorDeGrafos.generarGrafoRegularGradoN(6, 5);
			g.mostrarGrafo();
			System.out.println(g.esConexo());
		} catch (GrafoInvalidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
