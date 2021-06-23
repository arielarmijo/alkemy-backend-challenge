package tk.monkeycode.backendchallenge.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tk.monkeycode.backendchallenge.model.dto.UsuarioDTO;
import tk.monkeycode.backendchallenge.model.entity.Usuario;
import tk.monkeycode.backendchallenge.service.EmailService;
import tk.monkeycode.backendchallenge.service.UserService;
import tk.monkeycode.backendchallenge.util.JWTUtil;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UsuarioController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/login")
	public ResponseEntity<?> doLogin(@Valid @RequestBody UsuarioDTO usuario, BindingResult result) {
		
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
										 .map(fe -> String.format("%s: %s", fe.getField(), fe.getDefaultMessage()))
										 .collect(Collectors.toList());
			response.put("errores", errores);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		try {
			
			Authentication authentication = authenticationManager.authenticate(
										  	new UsernamePasswordAuthenticationToken(
										  			usuario.getUsername(), usuario.getPassword()
												)
										  );
			
			Usuario user = (Usuario) authentication.getPrincipal();
			String token = jwtUtil.generateAccessToken(user);
			response.put("token", token);
			log.info("username: {}", jwtUtil.getUsername(token));
			
			return ResponseEntity.ok().body(response);
			
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UsuarioDTO usuario, BindingResult result) {
		
		Map<String, Object> response = new HashMap<>();
		
		// Valida los datos del usuario
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors()
										 .stream()
								   		 .map(fe -> String.format("%s: %s", fe.getField(), fe.getDefaultMessage()))
								   		 .collect(Collectors.toList());
			response.put("errores", errores);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		// Registrar usuario
		try {
			userService.registrar(usuario);
			response.put("message", "Usuario agregado con éxito.");
			Runnable emailSender = () -> {	
				emailService.sendTextEmail(usuario.getUsername());
			};
			Thread thread = new Thread(emailSender);
			thread.start();
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}

}
