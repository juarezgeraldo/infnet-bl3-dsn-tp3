package br.edu.infnet.appUsuarios.model.repository;

import br.edu.infnet.appUsuarios.model.domain.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    boolean existsByEmail(String email);


}
