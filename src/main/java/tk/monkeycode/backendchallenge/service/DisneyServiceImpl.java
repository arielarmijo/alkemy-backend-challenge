package tk.monkeycode.backendchallenge.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import tk.monkeycode.backendchallenge.exception.PeliculaNotFoundException;
import tk.monkeycode.backendchallenge.exception.PersonajeNotFoundException;
import tk.monkeycode.backendchallenge.model.Order;
import tk.monkeycode.backendchallenge.model.entity.Genero;
import tk.monkeycode.backendchallenge.model.entity.Pelicula;
import tk.monkeycode.backendchallenge.model.entity.Personaje;
import tk.monkeycode.backendchallenge.respository.GeneroRepository;
import tk.monkeycode.backendchallenge.respository.PeliculaRepository;
import tk.monkeycode.backendchallenge.respository.PersonajeRepository;

@Slf4j
@Service
public class DisneyServiceImpl implements DisneyService {

	@Autowired
	private PersonajeRepository personajesRepo;

	@Autowired
	private PeliculaRepository peliculasRepo;

	@Autowired
	private GeneroRepository generosRepo;

	@Override
	@Transactional(readOnly = true)
	public List<Personaje> buscarPersonajes(String nombre, Integer edad, Integer idMovie) {

		List<Personaje> personajes = (nombre == null) ? (List<Personaje>) personajesRepo.findAll(): personajesRepo.findByNombre(nombre);

		if (edad != null) {
			//personajes = personajes.stream().filter(p -> p.getEdad() == edad).collect(Collectors.toList());
			personajes = personajesRepo.findByIdInAndEdad(
							personajes.stream().map(p -> p.getId()).collect(Collectors.toList()),
							edad
						);
		}

		if (idMovie != null) {
			
			Optional<Pelicula> pelicula = peliculasRepo.findById(idMovie);
			
			if (pelicula.isPresent()) {
				personajes = personajes.stream().filter(p -> p.getPeliculas().contains(pelicula.get()))
												.collect(Collectors.toList());
			}
			
			if (pelicula.isEmpty()) {
				personajes.clear();
			}
			
		}

		return personajes;

	}

	@Override
	@Transactional(readOnly = true)
	public Personaje buscarPersonaje(Integer id) {
		return personajesRepo.findById(id).orElseThrow(PersonajeNotFoundException::new);
	}

	@Override
	@Transactional
	public Personaje guardarPersonaje(Personaje personaje) {

		List<Pelicula> peliculas = personaje.getPeliculas();

		if (peliculas != null && !peliculas.isEmpty()) {
			List<Pelicula> peliculasPersistidas = peliculas.stream().map(p -> {
				List<Pelicula> pelicula = peliculasRepo.findByTitulo(p.getTitulo());
				// Se asume que las películas tienen títulos únicos
				return pelicula.isEmpty() ? peliculasRepo.save(p) : pelicula.get(0);
			}).collect(Collectors.toList());
			personaje.setPeliculas(peliculasPersistidas);
		}

		return personajesRepo.save(personaje);

	}

	@Override
	@Transactional
	public void borrarPersonate(Integer id) {
		personajesRepo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pelicula> buscarPeliculas(String nombre, Integer idGenero, Order order) {
		
		List<Pelicula> peliculas =  (nombre == null) ? (List<Pelicula>) peliculasRepo.findAll() : peliculasRepo.findByTitulo(nombre);
		
		Sort sort = Sort.by(Sort.Direction.ASC, "titulo");
		
		if (order != null) {
			sort = Sort.by(Sort.Direction.valueOf(order.name()), "fechaCreacion");
		}
		
		if (idGenero != null) {
			
			Optional<Genero> genero = generosRepo.findById(idGenero);
			
			if (genero.isPresent()) {
				log.info("Filtrando por genero {}...", genero.get().getNombre());
				peliculas = peliculasRepo.filtrarPorGenero(
								peliculas.stream().map(p -> p.getId()).collect(Collectors.toList()),
								genero.get(),
								sort
							);
			}
			
			if (genero.isEmpty()) {
				log.info("No se encontro genero con id: {}", idGenero);
				//peliculas = new ArrayList<>();
				peliculas.clear();
			}
			
		}
		
		return peliculas;
		
	}

	@Override
	@Transactional(readOnly = true)
	public Pelicula buscarPelicula(Integer id) {
		return peliculasRepo.findById(id).orElseThrow(PeliculaNotFoundException::new);
	}

	@Override
	@Transactional
	public Pelicula guardarPelicula(Pelicula pelicula) {

		// Persistencia del genero
		if (pelicula.getGenero() != null) {
			
			Optional<Genero> genero = generosRepo.findByNombre(pelicula.getGenero().getNombre());
			
			if (genero.isEmpty()) {
				generosRepo.save(pelicula.getGenero());
			}
			
		}

		// Persistencia de los personajes
		List<Personaje> personajes = pelicula.getPersonajes();
		if (personajes != null && !personajes.isEmpty()) {
			List<Personaje> personajesPersistidos = personajes.stream().map(p -> {
				List<Personaje> pjes = personajesRepo.findByNombre(p.getNombre());
				log.info("Personajes encontrados = {}", pjes.size());
				// Se asume que los personajes tienen nombres únicos
				return pjes.isEmpty() ? personajesRepo.save(p) : pjes.get(0);
			}).collect(Collectors.toList());
			pelicula.setPersonajes(personajesPersistidos);
		}

		return peliculasRepo.save(pelicula);

	}

	@Override
	@Transactional
	public void borrarPelicula(Integer id) {
		peliculasRepo.deleteById(id);
	}

}
