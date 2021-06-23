package tk.monkeycode.backendchallenge.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import tk.monkeycode.backendchallenge.model.Rating;

@Data
@Entity
@Table(name = "peliculas")
@SequenceGenerator(name = "PeliculaSeq", sequenceName="pelicula_seq", initialValue = 2000, allocationSize=5)
public class Pelicula {

	@Id
	@GeneratedValue(generator = "PeliculaSeq", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable = true)
	private String imagen;
	
	@Column(nullable = false)
	private String titulo;
	
	@Column(name = "fecha_creacion", nullable = true, columnDefinition = "DATE")
	private LocalDate fechaCreacion;
	
	@Column(nullable = true)
	private Rating calificacion;
	
	@JsonIgnoreProperties(value = {"peliculas"})
	@ManyToMany
	@JoinTable(
		name = "personajes_peliculas",
		joinColumns = @JoinColumn(name = "pelicula_id"),
		inverseJoinColumns = @JoinColumn(name = "personaje_id")
	)
	private List<Personaje> personajes;
	
	// Se asume que cada película puede pertenecer a un solo género.
	@JsonIgnoreProperties(value = {"peliculas"})
	@ManyToOne // Child side
	@JoinColumn(name = "genero_id")
	private Genero genero;
	
}
