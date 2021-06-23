package tk.monkeycode.backendchallenge.exception;

public class PeliculaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PeliculaNotFoundException() {
		super("Película no encontrada.");
	}
	
	public PeliculaNotFoundException(String message) {
		super(message);
	}
	
	public PeliculaNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}