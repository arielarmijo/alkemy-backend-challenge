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
import tk.monkeycode.backendchallenge.model.Order;
import tk.monkeycode.backendchallenge.model.dto.PeliculaDTO;
import tk.monkeycode.backendchallenge.model.entity.Pelicula;
import tk.monkeycode.backendchallenge.service.DisneyService;

@Slf4j
@RestController
@RequestMapping("/movies")
@Secured({"ROLE_USER"})
public class PeliculaController {
	
	@Autowired
	private DisneyService ds;
	
	@GetMapping
	public List<PeliculaDTO> mostrarPeliculas(
			@RequestParam(name = "name", required = false) String nombre,
			@RequestParam(name = "genre", required = false) Integer idGenero,
			@RequestParam(name = "order", required = false) Order order
			) {
		log.info("Query parameters: name={}, genre={}, order={}", nombre, idGenero, order);
		return ds.buscarPeliculas(nombre, idGenero, order).stream().map(this::pelicula2DTO).collect(Collectors.toList());
	}
	
	private PeliculaDTO pelicula2DTO(Pelicula p) {
		return new PeliculaDTO(p.getTitulo(), p.getImagen(), p.getFechaCreacion());
	}
	
	@GetMapping("/{id}")
	public Pelicula mostrarPelicula(@PathVariable Integer id) {
		return ds.buscarPelicula(id);
	}
	
	@PostMapping
	public Pelicula crearPelicula(@RequestBody Pelicula pelicula) {
		return ds.guardarPelicula(pelicula);
	}
	
	@PutMapping("/{id}")
	public Pelicula actualizarPelicula(@PathVariable Integer id, @RequestBody Pelicula pelicula) {
		return ds.guardarPelicula(actualizarPelicula(ds.buscarPelicula(id), pelicula));
	}

	@DeleteMapping("/{id}")
	public void borrarPelicula(@PathVariable Integer id) {
		ds.borrarPelicula(id);
	}

	private Pelicula actualizarPelicula(Pelicula old, Pelicula update) {
		old.setTitulo(update.getTitulo());
		old.setImagen(update.getImagen());
		old.setFechaCreacion(update.getFechaCreacion());
		old.setCalificacion(update.getCalificacion());
		old.setPersonajes(update.getPersonajes());
		old.setGenero(update.getGenero());
		return old;
	}
	
}
