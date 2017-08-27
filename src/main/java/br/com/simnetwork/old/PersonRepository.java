package br.com.simnetwork.old;

import org.springframework.data.repository.CrudRepository;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;

public interface PersonRepository extends CrudRepository<Usuario, Integer> {

}
