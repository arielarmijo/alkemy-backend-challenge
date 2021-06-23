package tk.monkeycode.backendchallenge.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
@SequenceGenerator(name = "RolSeq", sequenceName="rol_seq", initialValue = 10, allocationSize = 5)
public class Rol {
	
	@Id
	@GeneratedValue(generator = "GeneroSeq", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable = false, unique = true, length = 20)
	private String nombre;

}
