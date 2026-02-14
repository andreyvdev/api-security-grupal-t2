package pe.edu.cibertec.api_security_pgomez_ayamunaque.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.cibertec.api_security_pgomez_ayamunaque.dto.MensajePruebaResponse;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.dto.UsuarioPrueba;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.model.Rol;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.model.Usuario;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.repository.RolRepository;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.service.UsuarioService;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/v1/pruebas2")
public class Pruebas2Controller {
	private final UsuarioService usuarioService;
	private final RolRepository rolRepository;
	
	public Pruebas2Controller(UsuarioService usuarioService, RolRepository rolRepository) {
		this.usuarioService = usuarioService;
		this.rolRepository = rolRepository;
	}
	
	@PostMapping("/prueba1")
	public ResponseEntity<Rol> prueba1(@RequestBody Rol rol){
		return ResponseEntity.ok(rolRepository.save(rol));
	}
	
	@PostMapping("/prueba2")
	public ResponseEntity<MensajePruebaResponse> prueba2(@RequestBody MensajePruebaResponse mensaje){
		MensajePruebaResponse msg = new MensajePruebaResponse();
		msg.setMensaje("Usted tiene rol ADMIN: " + mensaje);
		return ResponseEntity.ok(mensaje);
	}
	
	@PutMapping("/prueba3/{id}")
	public ResponseEntity<UsuarioPrueba> prueba3(@PathVariable Integer id, @RequestBody UsuarioPrueba usuario){
		usuarioService.obtenerPorId(id).orElseThrow();
		usuario.setId(id);
		UsuarioPrueba prueba = usuarioService.actualizarPrueba(usuario);
		return ResponseEntity.ok(prueba);
	}
}
