package pe.edu.cibertec.api_security_pgomez_ayamunaque.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pe.edu.cibertec.api_security_pgomez_ayamunaque.service.DetalleUsuarioService;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
	private final DetalleUsuarioService detalleusuarioService;

	public SecurityConfig(DetalleUsuarioService detalleusuarioService) {
		this.detalleusuarioService = detalleusuarioService;
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationProvider daoAuth() {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider(detalleusuarioService);
		dao.setPasswordEncoder(passwordEncoder());
		return dao;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, IJwtService jwtService) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(auth -> 
				auth.requestMatchers(HttpMethod.POST, "/api/v1/auth/login")
				.permitAll().anyRequest().authenticated())
		.authenticationProvider(daoAuth())
		.addFilterBefore(new FiltroJwtAuth(jwtService), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
