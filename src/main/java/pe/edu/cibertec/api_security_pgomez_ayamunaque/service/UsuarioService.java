package pe.edu.cibertec.api_security_pgomez_ayamunaque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pe.edu.cibertec.api_security_pgomez_ayamunaque.dto.UsuarioPrueba;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.model.Usuario;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.repository.UsuarioRepository;

@Service
public class UsuarioService {
	private final UsuarioRepository repository;

	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public Usuario obtenerPorNomUsuario(String nomusuario) {
		return repository.findByNomusuario(nomusuario);
	}
	
	public List<Usuario> obtenerUsuarios() {
		return repository.findAll();
	}
	
	public Optional<Usuario> obtenerPorId(Integer id){
		Usuario usuario = repository.findById(id).orElse(null);
		if (usuario == null) {
			return Optional.empty();
		}
		return Optional.of(usuario);
	}
	
	public UsuarioPrueba actualizarPrueba(UsuarioPrueba usuario) {
		Usuario user = obtenerPorId(usuario.getId()).orElse(null);
		if (user == null) {
			return null;
		}
		user.setNombres(usuario.getNombre());
		user.setApellidos(usuario.getApellido());
		repository.save(user);
		
		usuario.setNombre(user.getNombres());
		usuario.setApellido(user.getApellidos());
		return usuario;
	}
}
