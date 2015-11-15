package grafos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class GrafoColoreado {
	private Grafo grafoOriginal;
	private Map<Integer,Integer> salidaColoreada; //Mapa que asocia numero de grafo con numero de color
	private boolean nodosRepetidos;
	private boolean nodosSinPintar;
	private boolean	nodosAdyacentesIgualColor;
	
	public GrafoColoreado(String entrada, String salida) {
		try{
			File fileEntrada = new File(entrada);
			grafoOriginal = new Grafo(fileEntrada);
			File fileSalida = new File(salida);
			FileReader fr = new FileReader(fileSalida);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			String [] datos = line.split(" ");
			int cantColores = Integer.parseInt(datos[1]);
			int cantNodos = Integer.parseInt(datos[0]);
			salidaColoreada = new HashMap<Integer, Integer>();
			for(int i=0; i<cantNodos;i++){
				line = br.readLine();
				datos = line.split(" ");
				if(salidaColoreada.containsKey(Integer.parseInt(datos[0]))) {
					nodosRepetidos = true;
				}
				else {
					salidaColoreada.put(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]));
				}
			}
			
			for(Integer key : salidaColoreada.keySet()) {
				if(!(salidaColoreada.get(key).compareTo(0) > 0)) {
					nodosSinPintar = true;
					break;
				}
			}
			
			for(int i=1; i<=cantNodos ; i++){
				for(int j=i;j<=cantNodos;j++){
					if(grafoOriginal.getValor(i-1,j-1)==true && i!=j){
						if(salidaColoreada.get(i).equals(salidaColoreada.get(j))) {
							nodosAdyacentesIgualColor = true;
						}
					}
				}
			}
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();			
		}
	}
	
	public boolean hayNodosRepetidos() {
		return nodosRepetidos;
	}
	public boolean hayNodosSinPintar() {
		return nodosSinPintar;
	}
	public boolean hayNodosAdyacentesIgualColor() {
		return nodosAdyacentesIgualColor;
	}
}
