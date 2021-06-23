package tk.monkeycode.backendchallenge.model.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NonNull;

@Data
public class PeliculaDTO {
	
	@NonNull
	private String titulo;

	@NonNull
	private String imagen;
	
	@NonNull
	private LocalDate fechaCreacion;
	
}
