package tk.monkeycode.backendchallenge.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import tk.monkeycode.backendchallenge.filter.JWTFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTFilter jwtFilter;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
        	.passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// Habilita CORS y desactiva CSRF
    	http.cors().and().csrf().disable();
    	// Evita la creación sesiones
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	// Establece los permisos de acceso para los endpoints
        http.authorizeRequests()
        	// public endpoints 
        	.antMatchers("/auth/**").permitAll()
        	.antMatchers("/h2-console/**").permitAll()
        	// private endpoints
        	.anyRequest().authenticated();
        // Establece el manejador de excepciones
        http.exceptionHandling()
        	.authenticationEntryPoint((reques, response, exception) -> {
        		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
        	});
        // Agrega el filtro para JWT
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        // Permite acceso a la consola de la base de datos H2
        http.headers().frameOptions().disable(); // Acceso a ./h2-console
    }
    
    @Bean
	public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
	}
    
    @Bean
    public CorsFilter corsFilter() {
    	
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
        
    }
    
    // Expone el authentication manager para poder usarlo en el controlador
    @Override @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
