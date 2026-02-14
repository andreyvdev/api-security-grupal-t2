package pe.edu.cibertec.api_security_pgomez_ayamunaque.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.dto.LoginDto;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.dto.UsuarioSecurityDto;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.model.Usuario;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.security.IJwtService;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.service.DetalleUsuarioService;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private final UsuarioService usuarioService;
	private final DetalleUsuarioService detalleUsuarioService;
	private final IJwtService jwtService;
	private final AuthenticationManager authManager;
	public AuthController(UsuarioService usuarioService, DetalleUsuarioService detalleUsuarioService,
			IJwtService jwtService, AuthenticationManager authManager) {
		this.usuarioService = usuarioService;
		this.detalleUsuarioService = detalleUsuarioService;
		this.jwtService = jwtService;
		this.authManager = authManager;
	}
	
	@PostMapping("/login")
	public ResponseEntity<UsuarioSecurityDto> login(@RequestBody LoginDto login) {
		UsuarioSecurityDto usuarioSecurity = new UsuarioSecurityDto();
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
			
			Usuario usuario = usuarioService.obtenerPorNomUsuario(login.getUsername());
			String token = jwtService.generarToken(usuario, detalleUsuarioService.obtenerAuthorizacionesPorRol(usuario.getRoles()));
			usuarioSecurity.setIdUsuario(usuario.getId());
			usuarioSecurity.setNomUsuario(usuario.getNomusuario());
			usuarioSecurity.setToken(token);
			return ResponseEntity.ok(usuarioSecurity);
		} catch (AuthenticationException e) {
			usuarioSecurity.setMensajeError("Credenciales incorrectas.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(usuarioSecurity);
		}
	}
}
