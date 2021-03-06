package tk.monkeycode.backendchallenge.model;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class AuthRequest {

	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
}
