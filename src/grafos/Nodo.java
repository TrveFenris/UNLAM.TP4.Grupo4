package grafos;

public class Nodo implements Comparable<Nodo>{
	private int numero;
	private int color;
	private int grado;
	
	public Nodo(){
		this.color = 0;
		this.grado = 0;
		numero = 0;
	}
	
	public Nodo(int num, int color, int grado){
		this.color = color;
		this.grado = grado;
		numero = num;
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
	
	public int getNumero(){
		return numero;
	}

	@Override
	public int compareTo(Nodo o) {
		if(this.grado<o.grado)
			return -1;
		else
			if(this.grado>o.grado)
				return 1;
		return 0;
	}
	
	
}
