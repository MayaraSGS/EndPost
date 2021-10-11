package projeto.postit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.postit.model.Postagem;

/**
 * Metodo utilizado para pesquisar coluna titulo da tabela postagem
 * 
 * @param titulo
 * @return Lista de Postagens
 * @since 1.0
 * @author Mayara
 */
@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
	List<Postagem> findAllByTituloContainingIgnoreCase(String titulo);
}
