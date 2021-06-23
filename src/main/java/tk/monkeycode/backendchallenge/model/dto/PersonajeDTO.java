package tk.monkeycode.backendchallenge.model.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class PersonajeDTO {
	
	@NonNull
	private String nombre;

	@NonNull
	private String imagen;

}
