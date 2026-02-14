package pe.edu.cibertec.api_security_pgomez_ayamunaque.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.model.Usuario;

public interface IJwtService {
	String generarToken(Usuario usuario, List<GrantedAuthority> authorities);
	Claims obtenerClaims(String token);
	boolean tokenValido(String token);
	String extraerToken(HttpServletRequest request);
	void generarAuthenticacion(Claims claims);
}
