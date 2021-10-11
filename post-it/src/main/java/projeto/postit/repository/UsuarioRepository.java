package projeto.postit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.postit.model.Usuario;

/**
 * Metodo utilizado para pesquisar coluna nome, sobrenome e mail da tabela usuario
 * 
 * @param Usuario
 * @return Lista de nomes, sobrenomes e email
 * @since 1.0
 * @author Mayara
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByEmail(String email);
	List<Usuario> findAllByNomeContainingIgnoreCase(String nome);
	List<Usuario> findAllBySobrenomeContainingIgnoreCase(String sobrenome);
}
