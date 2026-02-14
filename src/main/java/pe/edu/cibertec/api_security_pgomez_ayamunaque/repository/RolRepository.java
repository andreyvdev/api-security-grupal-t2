package pe.edu.cibertec.api_security_pgomez_ayamunaque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.cibertec.api_security_pgomez_ayamunaque.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
	
}
