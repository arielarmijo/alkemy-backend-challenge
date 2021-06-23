package tk.monkeycode.backendchallenge.respository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import tk.monkeycode.backendchallenge.model.entity.Rol;

public interface RolRepository extends CrudRepository<Rol, Integer> {
	
	Optional<Rol> findByNombre(String nombre);

}
