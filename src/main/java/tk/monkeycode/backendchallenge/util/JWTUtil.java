package tk.monkeycode.backendchallenge.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import tk.monkeycode.backendchallenge.model.entity.Usuario;

@Slf4j
@Component
public class JWTUtil {
	
    private final String jwtIssuer = "localhost:8080";
    private final long expiration = 60 * 60 * 1000;
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public boolean validate(String token) {
		try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
	}

	public String getUsername(String token) {
		
		Jws<Claims> jws;
		
		try {
			jws = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			log.info("subject: {}", jws.getBody().getSubject());
			return jws.getBody().getSubject();
		} catch (JwtException e) {
			log.info("No se pudo leer el token: {}", e.getMessage());
			return null;
		}

	}

	public String generateAccessToken(Usuario usuario) {
		
		Map<String, Object> header = new HashMap<>();
		header.put("alg", "HS256");
		header.put("typ", "JWT");
		
		Map<String, String> claims = new HashMap<>();
		String roles = usuario.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.joining(", "));
		claims.put("roles", roles);
		
		return Jwts.builder()
					.setHeader(header)
					.setClaims(claims)
					.setSubject(String.format("%s", usuario.getUsername()))
					.setIssuer(jwtIssuer)
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + expiration))
					.signWith(key)
					.compact();
		
	}

}
