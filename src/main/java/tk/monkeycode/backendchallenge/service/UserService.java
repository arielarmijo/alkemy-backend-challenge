package tk.monkeycode.backendchallenge.service;

import tk.monkeycode.backendchallenge.model.dto.UsuarioDTO;
import tk.monkeycode.backendchallenge.model.entity.Usuario;

public interface UserService {
	
	Usuario registrar(UsuarioDTO usuario);

}
