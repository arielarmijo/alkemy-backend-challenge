package tk.monkeycode.backendchallenge.respository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import tk.monkeycode.backendchallenge.model.entity.Genero;

public interface GeneroRepository extends CrudRepository<Genero, Integer> {

	Optional<Genero> findByNombre(String nombre);
}
