package grafos;

public class Test {
	public static void main(String[] args) {
		try {
			Grafo g = GeneradorDeGrafos.generarGrafoNPartito(6, 1);
			g.mostrarGrafo();
			if(g.esConexo())
				System.out.println("Es conexo");
			else
				System.out.println("No es conexo");
		} catch (GrafoInvalidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
