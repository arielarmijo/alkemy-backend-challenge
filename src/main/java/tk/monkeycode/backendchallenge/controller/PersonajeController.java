package tk.monkeycode.backendchallenge.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tk.monkeycode.backendchallenge.model.dto.PersonajeDTO;
import tk.monkeycode.backendchallenge.model.entity.Personaje;
import tk.monkeycode.backendchallenge.service.DisneyService;

@Slf4j
@RestController
@RequestMapping("/characters")
@Secured({"ROLE_USER"})
public class PersonajeController {
	
	@Autowired
	private DisneyService ds;

	@GetMapping
	public List<PersonajeDTO> mostrarPersonajes(
			@RequestParam(name = "name", required = false) String nombre,
			@RequestParam(name = "age", required = false) Integer edad,
			@RequestParam(name = "movie", required = false) Integer idMovie
			) {
		log.info("Query parameters: name={}, age={}, movies={}", nombre, edad, idMovie);
		return ds.buscarPersonajes(nombre, edad, idMovie).stream().map(this::personaje2DTO).collect(Collectors.toList());
	}
	
	private PersonajeDTO personaje2DTO(Personaje p) {
		return new PersonajeDTO(p.getNombre(), p.getImagen());
	}
	
	@GetMapping("/{id}")
	public Personaje mostrarPersonaje(@PathVariable Integer id) {
		return ds.buscarPersonaje(id);
	}
	
	@PostMapping
	public Personaje crearPersonaje(@RequestBody Personaje personaje) {
		return ds.guardarPersonaje(personaje);
	}
	
	@PutMapping("/{id}")
	public Personaje actualizarPersonaje(@PathVariable Integer id, @RequestBody Personaje personaje) {
		return ds.guardarPersonaje(actualizarPersonaje(ds.buscarPersonaje(id), personaje));
	}
	
	@DeleteMapping("/{id}")
	public void borrarPersonaje(@PathVariable Integer id) {
		ds.borrarPersonate(id);
	}
	
	private Personaje actualizarPersonaje(Personaje pOld, Personaje pNew) {
		pOld.setImagen(pNew.getImagen());
		pOld.setNombre(pNew.getNombre());
		pOld.setEdad(pNew.getEdad());
		pOld.setPeso(pNew.getPeso());
		pOld.setHistoria(pNew.getHistoria());
		pOld.setPeliculas(pNew.getPeliculas());
		return pOld;
	}
}
