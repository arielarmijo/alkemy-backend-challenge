package tk.monkeycode.backendchallenge.respository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import tk.monkeycode.backendchallenge.model.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
	
	Optional<Usuario> findByUsername(String username);

}
