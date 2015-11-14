package grafos;

public class GrafoInvalidoException extends Exception{
	private static final long serialVersionUID = 2829155132435952429L;
	public GrafoInvalidoException() {}
    public GrafoInvalidoException(String message) {
       super(message);
    }
}
