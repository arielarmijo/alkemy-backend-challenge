package tk.monkeycode.backendchallenge.respository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tk.monkeycode.backendchallenge.model.entity.Genero;
import tk.monkeycode.backendchallenge.model.entity.Pelicula;

public interface PeliculaRepository extends CrudRepository<Pelicula, Integer> {
	
	List<Pelicula> findByTitulo(String titulo);
	
	@Query("from Pelicula p where p.id in :peliculasId and p.genero = :genero")
	List<Pelicula> filtrarPorGenero(List<Integer> peliculasId, Genero genero, Sort sort);

}
