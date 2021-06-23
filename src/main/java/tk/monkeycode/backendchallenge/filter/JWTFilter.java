package tk.monkeycode.backendchallenge.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;
import tk.monkeycode.backendchallenge.respository.UsuarioRepository;
import tk.monkeycode.backendchallenge.util.JWTUtil;

@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UsuarioRepository usuarioRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.info("Header: {}", header);
		
		// Validación del header
		if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = header.split(" ")[1].trim();
		
		// Validación del token
		if (!jwtUtil.validate(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// Obtiene la identidad del usuario y configura el spring security context
		UserDetails userDetails = usuarioRepo.findByUsername(jwtUtil.getUsername(token)).orElse(null);
		
		var authentication = new UsernamePasswordAuthenticationToken(
					userDetails, null,
					userDetails == null ? List.of() : userDetails.getAuthorities()
				);
		
		authentication.setDetails(
					new WebAuthenticationDetailsSource().buildDetails(request)
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		filterChain.doFilter(request, response);
		
	}

}
