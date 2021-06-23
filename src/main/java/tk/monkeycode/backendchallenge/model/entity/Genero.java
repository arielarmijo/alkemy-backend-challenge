package tk.monkeycode.backendchallenge.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "generos")
@SequenceGenerator(name = "GeneroSeq", sequenceName="genero_seq", initialValue = 3000, allocationSize = 5)
public class Genero {

	@Id
	@GeneratedValue(generator = "GeneroSeq", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(unique = true)
	private String nombre;
	
	@Column(nullable = true)
	private String imagen;
	
	// Se ocupa por temas de eficiencia una relación OneToMany bidireccional.
	@OneToMany(mappedBy = "genero") // Parent side
	private List<Pelicula> peliculas;
	
	public void addMovie(Pelicula pelicula) {
		peliculas.add(pelicula);
		pelicula.setGenero(this);
	}
	
	public void removeMovie(Pelicula pelicula) {
		peliculas.remove(pelicula);
		pelicula.setGenero(null);
	}
	
}
