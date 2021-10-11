package projeto.postit.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import projeto.postit.model.Usuario;
import projeto.postit.model.UsuarioDTO;
import projeto.postit.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
		Optional<Usuario> usuarioExiste = repository.findByEmail(usuario.getEmail());
		if(usuarioExiste.isPresent()) {
			return Optional.empty();
		}
		else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String sengaCriptografada = encoder.encode(usuario.getSenha());
			usuario.setSenha(sengaCriptografada);
			return Optional.ofNullable(repository.save(usuario));
		}
	}
	
	public Optional<UsuarioDTO> logarUsuario(Optional<UsuarioDTO> usuarioLogin) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuarioPresente = repository.findByEmail(usuarioLogin.get().getEmail());
		if(usuarioPresente.isPresent()) {
			if(encoder.matches(usuarioLogin.get().getSenha(), usuarioPresente.get().getSenha())) {
				String auth = usuarioLogin.get().getEmail() + ":" + usuarioLogin.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				
				usuarioLogin.get().setToken(authHeader);
				usuarioLogin.get().setNome(usuarioPresente.get().getNome());
				usuarioLogin.get().setSenha(usuarioPresente.get().getSenha());
				
				return usuarioLogin;
			}
		}
		return null;
	}

}
