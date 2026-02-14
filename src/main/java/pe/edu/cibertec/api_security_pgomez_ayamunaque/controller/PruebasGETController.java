package pe.edu.cibertec.api_security_pgomez_ayamunaque.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.cibertec.api_security_pgomez_ayamunaque.model.Usuario;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.service.UsuarioService;

@PreAuthorize("hasAnyRole('ADMIN', 'JEFE')")
@RestController
@RequestMapping("/api/v1/pruebas")
public class PruebasGETController {
	private final UsuarioService usuarioService;
	
	public PruebasGETController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping("/prueba1")
	public ResponseEntity<String> prueba1(){
		return ResponseEntity.ok("Usted tiene rol ADMIN o JEFE");
	}
	
	@GetMapping("/prueba2")
	public ResponseEntity<List<Usuario>> prueba2(){
		return ResponseEntity.ok(usuarioService.obtenerUsuarios());
	}
}
