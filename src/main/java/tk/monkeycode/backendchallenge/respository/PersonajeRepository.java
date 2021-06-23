package tk.monkeycode.backendchallenge.respository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tk.monkeycode.backendchallenge.model.entity.Personaje;

public interface PersonajeRepository extends CrudRepository<Personaje, Integer> {

	List<Personaje> findByNombre(String nombre);
	List<Personaje> findByIdInAndEdad(List<Integer> personajesId, Integer edad);
	
}
