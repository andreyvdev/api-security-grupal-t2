package pe.edu.cibertec.api_security_pgomez_ayamunaque.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.edu.cibertec.api_security_pgomez_ayamunaque.model.Rol;
import pe.edu.cibertec.api_security_pgomez_ayamunaque.model.Usuario;

@Service
public class DetalleUsuarioService implements UserDetailsService {
	private final UsuarioService service;
	

	public DetalleUsuarioService(UsuarioService service) {
		this.service = service;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = service.obtenerPorNomUsuario(username);
		return obtenerUsuarioPorSpringSecurity(usuario, obtenerAuthorizacionesPorRol(usuario.getRoles()));
	}
	
	public UserDetails obtenerUsuarioPorSpringSecurity(Usuario usuario, List<GrantedAuthority> authorities) {
		return new User(usuario.getNomusuario(), usuario.getPassword(), true, true, true, true, authorities);
	}
	
	public List<GrantedAuthority> obtenerAuthorizacionesPorRol(Set<Rol> roles){
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Rol rol: roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getNomrol()));
		}
		return authorities;
	}
	
}
