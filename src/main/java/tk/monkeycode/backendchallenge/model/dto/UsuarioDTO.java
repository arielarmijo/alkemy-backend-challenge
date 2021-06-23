package tk.monkeycode.backendchallenge.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UsuarioDTO {
	
	@NotEmpty
	@Email
	private String username;
	
	@NotEmpty
	private String password;
	
	private String rePassword;

}
