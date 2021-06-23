package tk.monkeycode.backendchallenge.exception;

public class PersonajeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PersonajeNotFoundException() {
		super("Personaje no encontrado.");
	}
	
	public PersonajeNotFoundException(String message) {
		super(message);
	}
	
	public PersonajeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
