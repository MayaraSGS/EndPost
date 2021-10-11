package projeto.postit.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import projeto.postit.model.Usuario;
import projeto.postit.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImplements implements UserDetailsService{

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuario = repository.findByEmail(email);
		if(usuario.isPresent()) {
			return new UserDetailsImplements(usuario.get());
		}
		else {
			throw new UsernameNotFoundException(email + "not found.");
		}
	}
}
