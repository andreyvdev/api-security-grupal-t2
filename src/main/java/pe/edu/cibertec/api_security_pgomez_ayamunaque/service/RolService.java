package pe.edu.cibertec.api_security_pgomez_ayamunaque.service;

import org.springframework.stereotype.Service;

import pe.edu.cibertec.api_security_pgomez_ayamunaque.model.Rol;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.repository.RolRepository;

@Service
public class RolService {
	private final RolRepository repository;

	public RolService(RolRepository repository) {
		this.repository = repository;
	}
	
	
	
	public Rol guardar(Rol rol) {
		return repository.save(rol);
	}
}
