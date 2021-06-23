package tk.monkeycode.backendchallenge.service;

import java.util.List;

import tk.monkeycode.backendchallenge.model.Order;
import tk.monkeycode.backendchallenge.model.entity.Pelicula;
import tk.monkeycode.backendchallenge.model.entity.Personaje;

public interface DisneyService {
	
	List<Personaje> buscarPersonajes(String nombre, Integer edad, Integer idMovie);
	Personaje buscarPersonaje(Integer id);
	Personaje guardarPersonaje(Personaje personaje);
	void borrarPersonate(Integer id);
	
	List<Pelicula> buscarPeliculas(String nombre, Integer idGenero, Order idMovie);
	Pelicula buscarPelicula(Integer id);
	Pelicula guardarPelicula(Pelicula pelicula);
	void borrarPelicula(Integer id);

}
