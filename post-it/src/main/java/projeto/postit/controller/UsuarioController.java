package projeto.postit.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.postit.model.Usuario;
import projeto.postit.model.UsuarioDTO;
import projeto.postit.repository.UsuarioRepository;
import projeto.postit.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
		return usuarioRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Usuario>> getAll() {
		List<Usuario> listaDeUsuario = usuarioRepository.findAll();
		if(!listaDeUsuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(listaDeUsuario);
		}
		else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Usuario>> getByNome(@PathVariable String nome) {
		List<Usuario> listaDeNomes = usuarioRepository.findAllByNomeContainingIgnoreCase(nome);
		if(!listaDeNomes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(listaDeNomes);
		}
		else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@GetMapping("/sobrenome/{sobrenome}")
	public ResponseEntity<List<Usuario>> getBySobrenome(@PathVariable String sobrenome) {
		List<Usuario> listaDeSobrenome = usuarioRepository.findAllBySobrenomeContainingIgnoreCase(sobrenome);
		if(!listaDeSobrenome.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(listaDeSobrenome);
		}
		else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	/* Parte de login s cadastro */
	
	@PostMapping("/novo")
	public ResponseEntity<Usuario> newUsuario(@Valid @RequestBody Usuario usuario) {
		return service.cadastrarUsuario(usuario)
				.map(usuarioCadastrado -> ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PostMapping("/login")
	public ResponseEntity<UsuarioDTO> autorizarUsuario(@RequestBody Optional<UsuarioDTO> usuarioLogin) {
		return service.logarUsuario(usuarioLogin)
				.map(usuarioAutorizado -> ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioAutorizado))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

}
