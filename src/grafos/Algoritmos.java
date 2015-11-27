package grafos;

public enum Algoritmos {
	SECUENCIAL ("Secuencial"),
	WELSH_POWELL("Welsh-Powell"),
	MATULA("Matula"),
	;
	private String valor;
	
	private Algoritmos(String a) {
		valor = a;
	}
	public String getValor() {
		return valor;
	}
}
