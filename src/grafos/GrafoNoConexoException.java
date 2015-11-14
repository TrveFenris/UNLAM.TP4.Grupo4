package grafos;

public class GrafoNoConexoException extends Exception{
	private static final long serialVersionUID = 6342701439552300954L;
	public GrafoNoConexoException() {}
    public GrafoNoConexoException(String message) {
       super(message);
    }
}
