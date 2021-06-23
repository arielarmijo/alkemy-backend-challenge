package tk.monkeycode.backendchallenge.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Entity
@Table(name = "personajes")
@SequenceGenerator(name = "PersonajeSeq", sequenceName="personaje_seq", initialValue = 1000, allocationSize = 5)
public class Personaje {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersonajeSeq")
	private Integer id;
	
	@Column(nullable = true)
	private String imagen;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = true)
	private Integer edad;
	
	@Column(nullable = true)
	private Double peso;
	
	@Column(nullable = true)
	private String historia;
	
	@JsonIgnoreProperties(value = {"personajes"})
	@ManyToMany
	@JoinTable(
		name = "personajes_peliculas",
		joinColumns = @JoinColumn(name = "personaje_id"),
		inverseJoinColumns = @JoinColumn(name = "pelicula_id")
	)
	private List<Pelicula> peliculas;

	public void setImagen(String imagen) {
		if (imagen != null && !imagen.isBlank())
			this.imagen = imagen;
	}

	public void setNombre(String nombre) {
		if (nombre != null && !nombre.isBlank())
			this.nombre = nombre;
	}

	public void setEdad(Integer edad) {
		if (edad != null)
			this.edad = edad;
	}

	public void setPeso(Double peso) {
		if (peso != null)
			this.peso = peso;
	}

	public void setHistoria(String historia) {
		if (historia != null && !historia.isBlank())
			this.historia = historia;
	}

	public void setPeliculas(List<Pelicula> peliculas) {
		if (peliculas != null)
			this.peliculas = peliculas;
	}
	
}
