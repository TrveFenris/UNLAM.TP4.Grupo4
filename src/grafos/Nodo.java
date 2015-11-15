package grafos;

public class Nodo {
	
	private int color;
	private int grado;
	
	public Nodo(){
		this.color = 0;
		this.grado = 0;
	}
	
	public Nodo(int color, int grado){
		this.color = color;
		this.grado = grado;
	}
	
	public void setColor(int color){
		this.color = color;
	}
	
	public int getColor(){
		return this.color;
	}
	
	public void incrementarGrado(){
		this.grado++;
	}
	
	public int getGrado(){
		return this.grado;
	}
}
