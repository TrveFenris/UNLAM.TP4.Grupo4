package grafos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
//			try {
				//Grafo g = GeneradorDeGrafos.generarGrafoAleatorioConPorcentajeDeAdyacencia(600, 70);
				//g.guardarGrafo("Entradas/Secuencial/50_porciento_adyacencia.in");
				File f = new File("Entradas/NoRegulares/70_porciento_adyacencia.in");
				Grafo g = new Grafo(f);
				generarSalida(g, new File("Salidas/Aleatorios/Secuencial/70_porciento_adyacencia.out"),Algoritmos.MATULA);	
//			}
//			catch (IOException e) {
//				System.out.println("Reintentar");
//			}
	}
	
	private static void generarSalida(Grafo g, File f, Algoritmos algoritmo) {
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(f);
			pw = new PrintWriter(fw);
			Map<Integer,Integer> frecuencias = new HashMap<Integer, Integer>();
			for(int i=0; i < 10000; i++) {
				g.colorear(algoritmo);
				if(frecuencias.containsKey(g.getCantidadColores()))
					frecuencias.put(g.getCantidadColores(), frecuencias.get(g.getCantidadColores())+1 );
				else
					frecuencias.put(g.getCantidadColores(), 1 );
				//g.grabarGrafoColoreado(new File("Salidas/grafoColoreado.out"));
				g.despintarGrafo();
				System.out.println("Grafos coloreados: "+(i+1)+"\t"+((i+1)*100)/10000+"%");
			}
			for(Integer key : frecuencias.keySet()) {
				pw.println(key + " " + frecuencias.get(key)/10000.);
			}
			System.out.println("FIN");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(pw!=null)
					pw.close();
		}
	}
}
