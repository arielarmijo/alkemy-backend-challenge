package tk.monkeycode.backendchallenge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tk.monkeycode.backendchallenge.model.dto.UsuarioDTO;
import tk.monkeycode.backendchallenge.model.entity.Rol;
import tk.monkeycode.backendchallenge.model.entity.Usuario;
import tk.monkeycode.backendchallenge.respository.RolRepository;
import tk.monkeycode.backendchallenge.respository.UsuarioRepository;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private RolRepository rolRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepo.findByUsername(username)
						  .orElseThrow(() -> new UsernameNotFoundException(String.format("Usuario %s no encontrado.", username)));
	}

	@Override
	@Transactional	
	public Usuario registrar(UsuarioDTO usuario) {
		
		// Comprueba si el usuario exite
		if (usuarioRepo.findByUsername(usuario.getUsername()).isPresent()) {
			throw new ValidationException("Nombre de usuario ya existe.");
		}
		
		// Crea usuario con rol por defecto
		Optional<Rol> rol = rolRepo.findByNombre("ROLE_USER");
		List<Rol> roles = new ArrayList<>();
		
		if (rol.isPresent()) {
			roles.add(rol.get());
		}
		
		var nvoUsuario = new Usuario();
		nvoUsuario.setUsername(usuario.getUsername());
		// TODO: Comprobar password
		nvoUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		nvoUsuario.setEnabled(true);
		nvoUsuario.setRoles(roles);
		
		return usuarioRepo.save(nvoUsuario);
		
	}

}
